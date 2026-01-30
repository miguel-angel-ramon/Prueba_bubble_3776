package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Centro;

public interface VCentrosPlataformasDao  {

	public List<Centro> findAll(Centro centro) throws Exception ;

	List<Centro> findByCodDesc(String matcher) throws Exception;

	List<Centro> listZonasByRegion(Centro centro) throws Exception;

	/**
	 * Comprueba si el centro tiene activos los lotes de navidad para mostrar o no el boton de cestas de navidad en el menu
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
	public Boolean isLotesCentroActivo(Long codCentro) throws Exception;
}
