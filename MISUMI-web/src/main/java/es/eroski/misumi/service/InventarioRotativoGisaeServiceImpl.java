package es.eroski.misumi.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CausasInactividadDao;
import es.eroski.misumi.dao.iface.CcRefCentroDao;
import es.eroski.misumi.dao.iface.InventarioRotativoGisaeDao;
import es.eroski.misumi.dao.iface.PedidoCentroDao;
import es.eroski.misumi.dao.iface.StockTiendaDao;
import es.eroski.misumi.model.CausaInactividad;
import es.eroski.misumi.model.CcRefCentro;
import es.eroski.misumi.model.InformeHuecos;
import es.eroski.misumi.model.InventarioRotativoGisae;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.pedidosCentroWS.MotivosType;
import es.eroski.misumi.model.pedidosCentroWS.ReferenciasValidadasType;
import es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.InventarioRotativoGisaeService;
import es.eroski.misumi.util.Constantes;

@Service(value = "InventarioRotativoGisaeService")
public class InventarioRotativoGisaeServiceImpl implements InventarioRotativoGisaeService {
	
	@Autowired
	private InventarioRotativoGisaeDao inventarioRotativoGisaeDao;
	
	@Autowired
	private CcRefCentroDao ccRefCentroDao;
	
	@Autowired
	private PedidoCentroDao pedidoCentroDao;
	
	@Autowired
	private CausasInactividadDao causasInactividadDao;
	
	@Autowired
	private StockTiendaDao stockTiendaDao;
	
	@Override
	public LinkedHashMap<Long, String> findAllSeccion(InventarioRotativoGisae inventarioRotativo) throws Exception {
		return this.inventarioRotativoGisaeDao.findAllSeccion(inventarioRotativo);
	}
	
	@Override
	public void updateInventarioATratado(InventarioRotativoGisae inventarioRotativoGisae) throws Exception{
		this.inventarioRotativoGisaeDao.updateInventarioATratado(inventarioRotativoGisae);
	}
	
	@Override
	public Long getInformeHuecosCount(Long codCentro) throws Exception{
		return this.inventarioRotativoGisaeDao.getInformeHuecosCount(codCentro);
	}
	
	@Override
	public List<InformeHuecos> getInformeHuecos(Long codCentro, HttpSession session) throws Exception{
		List<InformeHuecos> listaInformeHuecos =  this.inventarioRotativoGisaeDao.getInformeHuecos(codCentro);
		List<BigInteger> listaRef = new ArrayList<BigInteger>();
		for(InformeHuecos informeHuecos : listaInformeHuecos){
			
			CcRefCentro ccRefCentro = new CcRefCentro();
			ccRefCentro.setCodLoc(informeHuecos.getCodCentro());
			ccRefCentro.setCodArticulo(informeHuecos.getCodArticulo());
			ccRefCentro.setFecha(new Date());
			informeHuecos.setCc(this.ccRefCentroDao.consultaCc(ccRefCentro));
			
			informeHuecos.setVentaMedia(new Double(0));
			informeHuecos.setStockTeorico(new Double(0));
			ReferenciasCentro vReferenciasCentro = new ReferenciasCentro();
		  	vReferenciasCentro.setCodArt(informeHuecos.getCodArticulo());
		  	vReferenciasCentro.setCodCentro(informeHuecos.getCodCentro());
		  	List<Integer> causasInactividad = new ArrayList<Integer>();
        	try {
				ValidarReferenciasResponseType resultado= this.pedidoCentroDao.findPedidosCentroWS(vReferenciasCentro);
				
				for(ReferenciasValidadasType refValidada :resultado.getReferenciasValidadas()){
					if (refValidada.getReferencia().equals(new BigDecimal(informeHuecos.getCodArticulo()))){
						listaRef.add(refValidada.getReferencia().toBigInteger());
						for(MotivosType motivos :refValidada.getMotivos()){
							String descripcion = motivos.getDescripcion();
							if (null != descripcion){
								try {
									CausaInactividad causaInactividad = this.causasInactividadDao.findOne(descripcion);
									causasInactividad.add(causaInactividad.getCodCausa());
								} catch (EmptyResultDataAccessException e) {
									CausaInactividad causaInactividad = new CausaInactividad();
									causaInactividad.setCodCausa(this.causasInactividadDao.getCausaSequence() + 1);
									causaInactividad.setDescCausa(descripcion);
									this.causasInactividadDao.insertCausaInactividad(causaInactividad);
									causasInactividad.add(causaInactividad.getCodCausa());
								}		
							}
						}
						informeHuecos.setCausasInactividad(causasInactividad);
					}
				}
			} catch (Exception e) {
				causasInactividad.add(-1);
				informeHuecos.setCausasInactividad(causasInactividad);
			}
        	
        	informeHuecos.setCausasFormatted();
        	informeHuecos.setTipoFormatted();
		}
		ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
    	stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
    	stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
    	BigInteger[] refArr = new BigInteger[listaRef.size()];
     	listaRef.toArray(refArr);
    	stockTiendaRequest.setListaCodigosReferencia(refArr);
    	ConsultarStockResponseType stockTiendaResponse = this.stockTiendaDao.consultaStock(stockTiendaRequest, session);
    	for(ReferenciaType referencia : stockTiendaResponse.getListaReferencias()){
    		InformeHuecos informeHuecos = new InformeHuecos();
    		informeHuecos.setCodCentro(codCentro);
    		informeHuecos.setCodArticulo(referencia.getCodigoReferencia().longValue());
    		Integer index = listaInformeHuecos.indexOf(informeHuecos);
    		if (index != -1){
    			listaInformeHuecos.get(index).setStockTeorico(referencia.getStock().doubleValue());
    		}
    	}
		return listaInformeHuecos;
	}

}
