package es.eroski.misumi.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ImpresionEtiquetasDao;
import es.eroski.misumi.service.iface.NumeroEtiquetaImpresoraService;
import es.eroski.misumi.util.Constantes;


@Service(value = "numeroEtiquetaImpresoraService")
public class NumeroEtiquetaImpresoraServiceImpl implements NumeroEtiquetaImpresoraService {

    @Autowired
	private HttpSession httpSession;
    
    @Autowired
	private ImpresionEtiquetasDao impresionEtiquetasDao;

	@Override
	public HashMap<String, Integer> getMapNumEtiquetaInBBDD(Long codCentro,String mac) throws Exception {
		HashMap<String, Integer> mapNumEti = new HashMap<String, Integer>();
		if (impresionEtiquetasDao.existsReferencias(codCentro, mac)){
			mapNumEti=impresionEtiquetasDao.getNumEtiquetasReferencias(codCentro, mac);
		}
		return mapNumEti;
	}
	
	@Override
	public int getNumEtiquetaInBBDD(String codArt,Long codCentro,String mac) throws Exception {
		int numEti = 0;
		if (impresionEtiquetasDao.existReferenciaInBBDD(codCentro, mac,codArt)){
			numEti=impresionEtiquetasDao.getNumEtiquetaInBBDD(codArt,codCentro, mac);
		}
		return numEti;
	}

	@Override
	public void updateNumEtiquetaInBBDD(String codArt, int numEti,Long codCentro,String mac) throws Exception {
		//Guardamos en sesión el map
		if (impresionEtiquetasDao.existReferenciaInBBDD(codCentro, mac,codArt)){
			//Si existe en BBDD hay que actualizar el registro
			impresionEtiquetasDao.updateNumEtiquetasReferencias(codCentro, codArt, mac,numEti);
		}else {
			//Si no existe en BBDD hay que hacer insertar el registro en la tabla
			impresionEtiquetasDao.insertNumEtiquetasReferencias(codCentro, codArt, mac,numEti);
		}
	}

	@Override
	public int incNumEtiqueta(String codArt, int numEti,Long codCentro,String mac) throws Exception {
		//int numEtiBBDD = this.getNumEtiquetaInBBDD(codArt,codCentro,mac);
		//Incrementamos el número de etiqueta recibido en la request
		Integer numeroEtiquetaSesion = new Integer(++numEti);
		this.updateNumEtiquetaInBBDD(codArt,numeroEtiquetaSesion,codCentro,mac);
		this.noNumEtiquetaInSessionEnviados();
		return numeroEtiquetaSesion.intValue();
	}

	@Override
	public Integer getNumEtiqueta(Long codArt,Long codCentro,String mac) throws Exception {
		Integer numeroEtiquetaSesion = null;
		if (codArt != null){
			//HashMap<String, Integer> mapNumEti = this.getMapNumEtiquetaInBBDD(codCentro,mac);
			numeroEtiquetaSesion = this.getNumEtiquetaInBBDD(codArt.toString(),codCentro,mac);
			//Recuperamos del map el número de etiqueta de la referencia dada
			//numeroEtiquetaSesion = (mapNumEti.get(codArt.toString()) != null ? mapNumEti.get(codArt.toString()) : new Integer(0));
		}
		return numeroEtiquetaSesion;
	}

	@Override
	public boolean existeMapNumEtiquetaInBBDD(Long codCentro,String mac) throws Exception {
		return impresionEtiquetasDao.existsReferencias(codCentro, mac);
	}

	@Override
	public void removeMapNumEtiquetaInBBDD(Long codCentro,String mac) throws Exception {
		//Borramos de sesión las etiquetas existentes en BBDD
		if (impresionEtiquetasDao.existsReferencias(codCentro, mac)){
			impresionEtiquetasDao.deleteNumEtiquetasReferencias(codCentro, mac);
		}
		this.removeNumEtiquetaInSessionEnviados();
	}

	@Override
	public String getNumEtiquetaInSessionEnviados() throws Exception {
		String numEtiEnviados = "NO";
		if (httpSession != null && httpSession.getAttribute(Constantes.IS_NUMERO_ETIQUETA_IN_SESSION_ENVIADOS) != null){
			numEtiEnviados = (String) httpSession.getAttribute(Constantes.IS_NUMERO_ETIQUETA_IN_SESSION_ENVIADOS);
		}
		return numEtiEnviados;
	}

	@Override
	public void siNumEtiquetaInSessionEnviados() throws Exception {
		//Guardamos en sesión que SI se ha enviado
		if (httpSession != null){
			httpSession.setAttribute(Constantes.IS_NUMERO_ETIQUETA_IN_SESSION_ENVIADOS, "SI");
		}
	}

	@Override
	public void noNumEtiquetaInSessionEnviados() throws Exception {
		//Guardamos en sesión que NO se ha enviado
		if (httpSession != null){
			httpSession.setAttribute(Constantes.IS_NUMERO_ETIQUETA_IN_SESSION_ENVIADOS, "NO");
		}
	}

	@Override
	public void removeNumEtiquetaInSessionEnviados() throws Exception {
		//Borramos de sesión si se ha enviado
		if (httpSession != null){
			httpSession.removeAttribute(Constantes.IS_NUMERO_ETIQUETA_IN_SESSION_ENVIADOS);
		}
	}

	@Override
	public String getImpresoraActiva(String codArt,Long codCentro,String mac) throws Exception {
		String impresoraActiva = "NO";
		//if (this.getNumEtiqueta(codArt) != null && this.getNumEtiqueta(codArt).intValue() != 0){ //Si codArt no es nueva comprobamos en sessión si se ha enviado
		if (this.getNumEtiquetaInBBDD(codArt,codCentro,mac) > 0 ){
			impresoraActiva = this.getNumEtiquetaInSessionEnviados();
		}
		return impresoraActiva;
	}

}
