package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.UtilidadesCapraboDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.VDatosDiarioCap;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.VCentrosUsuariosService;
import es.eroski.misumi.service.iface.VDatosDiarioCapService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;

/**
 * 
 * Clase que contiene los métodos de obtención y transformación de datos para Caprabo
 *
 */
@Service(value = " UtilidadesCapraboService")
public class UtilidadesCapraboServiceImpl implements UtilidadesCapraboService{

	@Autowired
	private VDatosDiarioCapService vDatosDiarioCapService;

	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;
	
	@Autowired
	private VCentrosUsuariosService vCentrosUsuariosService;
	
	@Autowired 
	private UtilidadesCapraboDao utilidadesCapraboDao;

	/**
	 * Obtiene si un centro es de Caprabo.
	 *
	 */
	public boolean esCentroCaprabo(Long codCentro, String codUser) throws Exception{
		Centro centro = new Centro(); 

		centro.setCodCentro(codCentro);
		
		//Insertamos el código 0 en flgCpbEspecial al ser necesario para que un centro sea caprabo
		centro.setFlgCpbEspecial(Constantes.CODIGO_FLG_CAPRABO_ESPECIAL_NO);
	
		
		centro = this.vCentrosUsuariosService.findOne(centro, codUser);
		
		return (centro!= null && centro.esCentroCaprabo());
	}

	/**
	 * Obtiene el código Eroski asociado a un códiog Caprabo.
	 *
	 */
	public Long obtenerCodigoEroski(Long codCentro, Long codArtCaprabo) throws Exception{
		/*VSurtidoTienda vSurtidoTiendaRes;
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		vSurtidoTienda.setCodArtCaprabo(codArtCaprabo);
		vSurtidoTienda.setCodCentro(codCentro);
		
		vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);
		if(null != vSurtidoTiendaRes){
			return vSurtidoTiendaRes.getCodArt();
		}else{
			return null;
		}*/
		return utilidadesCapraboDao.obtenerCodigoEroski(codCentro,codArtCaprabo);
	}

	/**
	 * Obtiene el código Caprabo asociado a un códiog Eroski.
	 *
	 */
	public Long obtenerCodigoCaprabo(Long codCentro, Long codArtEroski) throws Exception{
		VSurtidoTienda vSurtidoTiendaRes;
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		vSurtidoTienda.setCodArt(codArtEroski);
		vSurtidoTienda.setCodCentro(codCentro);
		
		
	
		vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

		if(null != vSurtidoTiendaRes){
			return vSurtidoTiendaRes.getCodArtCaprabo();
		}else{
			return null;
		}
	}

	/**
	 * Obtiene la descripción de un artículo de Caprabo.
	 *
	 */
	public String obtenerDescArtCaprabo(Long codArtCaprabo) throws Exception{
		
		if (codArtCaprabo == null){
			return "";
		} 
		VDatosDiarioCap vDatosDiarioCapRes;
		VDatosDiarioCap vDatosDiarioCap = new VDatosDiarioCap();
		vDatosDiarioCap.setCodArt(codArtCaprabo);
		vDatosDiarioCapRes = this.vDatosDiarioCapService.findOne(vDatosDiarioCap);
		
		if(null != vDatosDiarioCapRes){
			return vDatosDiarioCapRes.getDescripArt();
		}else{
			return null;
		}
	}

	@Override
	public boolean esCentroCapraboEspecial(Long codCentro, String codUser) throws Exception {
		// TODO Auto-generated method stub
		Centro centro = new Centro(); 

		centro.setCodCentro(codCentro);
		
		//Insertamos el código 1 en flgCpbEspecial al ser necesario para que un centro sea caprabo
		centro.setFlgCpbEspecial(Constantes.CODIGO_FLG_CAPRABO_ESPECIAL_SI);
		
		centro = this.vCentrosUsuariosService.findOne(centro, codUser);
		
		return (centro!= null && centro.esCentroCapraboEspecial());
	}
	
	@Override
	public boolean esCentroCapraboNuevo(Long codCentro, String codUser) throws Exception {
		// TODO Auto-generated method stub
		Centro centro = new Centro(); 

		centro.setCodCentro(codCentro);
		
		//Insertamos el código 1 en flgCpbNuevo al ser necesario para que un centro sea caprabo
		centro.setFlgCpbNuevo(Constantes.CODIGO_FLG_CAPRABO_NUEVO_SI);
		
		centro = this.vCentrosUsuariosService.findOne(centro, codUser);
		
		return (centro!= null && centro.esCentroCapraboNuevo());
	}

	@Override
	public String obtenerMotivoCapraboEspecial(String motivoTxt, String codArtSustituidaPorEroski,
			String codArtSustituidaPorCaprabo) throws Exception {
		// TODO Auto-generated method stub
		return this.utilidadesCapraboDao.obtenerMotivoCapraboEspecial(motivoTxt, codArtSustituidaPorEroski, codArtSustituidaPorCaprabo);
	}
}