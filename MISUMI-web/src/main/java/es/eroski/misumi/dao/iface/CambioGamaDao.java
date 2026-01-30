package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.CambioGama;

public interface CambioGamaDao  {
	public CambioGama existeGama(Long codCentro, Long codArt)  throws Exception;
	public void updateCambioGama(CambioGama cambioGama, String accion, String usuario) throws Exception;
	public int insertarCambioGama(Long codCentro, Long codArt,String accion, String usuario) throws Exception;
}
