package es.eroski.misumi.dao.iface;

import java.util.HashMap;

public interface ImpresionEtiquetasDao  {

	public boolean existsReferencias(Long codCentro, String mac);
	
	public boolean existReferenciaInBBDD(Long codCentro, String mac,String codArt);
	
	public HashMap<String, Integer> getNumEtiquetasReferencias(Long codCentro, String mac);
	
	public int getNumEtiquetaInBBDD(String codArt,Long codCentro, String mac);
	
	public void updateNumEtiquetasReferencias(Long codCentro,String codArt, String mac,int numVeces);
	
	public void insertNumEtiquetasReferencias(Long codCentro,String codArt, String mac,int numVeces);
	
	public void deleteNumEtiquetasReferencias(Long codCentro, String mac);
}
