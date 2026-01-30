package es.eroski.misumi.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.InventarioRotativoGisaeDao;
import es.eroski.misumi.dao.iface.InventarioTiendaDao;
import es.eroski.misumi.dao.iface.TOptimaDao;
import es.eroski.misumi.model.InventarioRotativoGisae;
import es.eroski.misumi.model.TOptima;
import es.eroski.misumi.model.inventarioWS.ComunicarNoServidoRequestType;
import es.eroski.misumi.model.inventarioWS.ComunicarNoServidoResponseType;
import es.eroski.misumi.model.inventarioWS.ReferenciaRespuestaType;
import es.eroski.misumi.model.inventarioWS.ReferenciaType;
import es.eroski.misumi.service.iface.TOptimaService;
import es.eroski.misumi.util.Constantes;

@Service(value = "TOptimaService")
public class TOptimaServiceImpl implements TOptimaService {
	
	@Autowired
	public TOptimaDao tOptimaDao;
	
	@Autowired
	public InventarioTiendaDao inventarioTiendaDao;
	
	@Autowired
	public InventarioRotativoGisaeDao inventarioRotativoGisaeDao;
	
	 @Resource
	 private MessageSource messageSource;
	
	@Override
	public List<TOptima> getHuecos(TOptima tOptima) throws Exception{
		return tOptimaDao.getHuecos(tOptima);
	}
	
	@Override
	public void deleteHuecos(TOptima tOptima) throws Exception{
		tOptimaDao.borrarHuecos(tOptima);
	}
	
	@Override
	public void insertHueco(TOptima tOptima) throws Exception{
		tOptimaDao.insertHueco(tOptima);
	}
	
	@Override
	public List<TOptima> sendGISAE(TOptima tOptima) throws Exception {
		tOptima.setEnviado(false);
		tOptima.setFechaGen(new Date());
		List<TOptima> errores = new ArrayList<TOptima>();
		List<TOptima>listHuecos = tOptimaDao.getHuecos(tOptima);
		if (!listHuecos.isEmpty()){
			ComunicarNoServidoRequestType comunicarRequest = new ComunicarNoServidoRequestType();
			comunicarRequest.setCodigoCentro(BigInteger.valueOf(listHuecos.get(0).getCodCentro()));

			ReferenciaType[] listaReferencias = new ReferenciaType[listHuecos.size()];
			
			for(int i=0; i < listHuecos.size(); i++){
				TOptima hueco = listHuecos.get(i);
				BigInteger codArt = BigInteger.valueOf(hueco.getCodArt());
				String nsr = hueco.isNsr()?"S":"N";	

				ReferenciaType referencia = new ReferenciaType(codArt,nsr);

				listaReferencias[i] = referencia;
			}
			
			comunicarRequest.setListaReferencias(listaReferencias);
			ComunicarNoServidoResponseType comunicarResponse;
			try {
				comunicarResponse = inventarioTiendaDao.comunicarNoServido(comunicarRequest);
			} catch (Exception e) {
				Locale locale = LocaleContextHolder.getLocale();
				throw new Exception(messageSource.getMessage("pda_p47_revisionHuecos.errorWS", null, locale));
			}
			if (null != comunicarResponse){
			if (comunicarResponse.getCodigoRespuesta().equals("OK")){
			for(ReferenciaRespuestaType respuesta : comunicarResponse.getListaReferencias()){
				TOptima updateOptima = new TOptima();
				updateOptima.setEnviado(false);
				updateOptima.setFechaGen(new Date());
				updateOptima.setCodMac(tOptima.getCodMac());
				updateOptima.setCodCentro(tOptima.getCodCentro());
				updateOptima.setCodArt(respuesta.getCodigoReferencia().longValue());
				updateOptima.setCodError(respuesta.getCodigoError().longValue());
				updateOptima.setDescError(respuesta.getMensajeError());
				if(respuesta.getCodigoError().equals(new BigInteger("0"))){
					updateOptima.setEnviado(true);
				} else {
					updateOptima.setEnviado(false);
				}
				tOptimaDao.updateHueco(updateOptima);
				if(respuesta.getCodigoError().equals(new BigInteger("0"))){
					
						InventarioRotativoGisae inventarioRotativo = new InventarioRotativoGisae();
						inventarioRotativo.setCodCentro(updateOptima.getCodCentro());
						inventarioRotativo.setCodArea(respuesta.getCodArea().longValue());
						inventarioRotativo.setDescArea(respuesta.getDescArea());
						inventarioRotativo.setCodSeccion(respuesta.getCodSeccion().longValue());
						inventarioRotativo.setDescSeccion(respuesta.getDescSeccion());
						inventarioRotativo.setCodArticulo(respuesta.getCodigoReferencia().longValue());
						inventarioRotativo.setNsr(respuesta.getNsr().equals("S") ? true : false);
						inventarioRotativo.setTipoListado(respuesta.getTipoListado());
						inventarioRotativo.setFechaGen(tOptima.getFechaGen());
						if (respuesta.getCodArea().toString().equals(Constantes.AREA_FRESCOS)){
							inventarioRotativo.setTratado(false);
						} else {
							inventarioRotativo.setTratado(true);
						}
						List<InventarioRotativoGisae> lista = this.inventarioRotativoGisaeDao.findAll(inventarioRotativo);
						if (lista.isEmpty()){
							this.inventarioRotativoGisaeDao.insert(inventarioRotativo);
						
					}
				} else {
					errores.add(updateOptima);
				}
			

			}
			
			} else {
				throw new Exception(comunicarResponse.getDescripcionRespuesta());
			}
			} else {
				throw new Exception("Error al enviar la información a GISAE");
			}
		}
		return errores;
	}

}
