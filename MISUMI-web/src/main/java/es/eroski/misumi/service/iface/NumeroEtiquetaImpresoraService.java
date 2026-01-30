package es.eroski.misumi.service.iface;

import java.util.HashMap;


public interface NumeroEtiquetaImpresoraService {

	public HashMap<String, Integer> getMapNumEtiquetaInBBDD(Long codCentro,String mac) throws Exception;
	
	public int getNumEtiquetaInBBDD(String codArt,Long codCentro,String mac) throws Exception;
	
	public void updateNumEtiquetaInBBDD(String codArt, int numEti,Long codCentro,String mac) throws Exception;
	
	public int incNumEtiqueta(String codArt, int numEti,Long codCentro,String mac) throws Exception;
	
	public Integer getNumEtiqueta(Long codArt,Long codCentro,String mac) throws Exception;
	
	public boolean existeMapNumEtiquetaInBBDD(Long codCentro,String mac) throws Exception;
	
	public void removeMapNumEtiquetaInBBDD(Long codCentro,String mac) throws Exception;

	public String getNumEtiquetaInSessionEnviados() throws Exception;
	
	public void siNumEtiquetaInSessionEnviados() throws Exception;
	
	public void noNumEtiquetaInSessionEnviados() throws Exception;
	
	public void removeNumEtiquetaInSessionEnviados() throws Exception;

	public String getImpresoraActiva(String codArt,Long codCentro,String mac) throws Exception;
}

