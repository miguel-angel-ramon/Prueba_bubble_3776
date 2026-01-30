package es.eroski.misumi.dao.iface;

public interface GoogleDao {

	public String obtenerDireccionGoogle(Long codCentro) throws Exception;
	public void guardarDireccionGoogle(Long codCentro, String direccionGoogle) throws Exception;
}
