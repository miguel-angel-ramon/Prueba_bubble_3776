package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.VMapaReferencia;

public interface VMapaReferenciaDao  {

	/**
	 * Obtiene el detalle del mapa (código mapa - descripción mapa)
	 * @param codArt
	 * @return
	 * @throws Exception
	 */
	public VMapaReferencia findMapa(Long codArt) throws Exception;

	/**
	 * Obtiene el número de mapas de Vegalsa para el código de articulo.
	 * @param codArt
	 * @return
	 * @throws Exception
	 */
	public boolean existsMapa(Long codArt);
	
}
