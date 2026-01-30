package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.EstadoMostrador;

public interface EstadoMostradorService {

	/**
	 * Obtiene los estados de las estructuras de un centro
	 *
	 */
	public EstadoMostrador getEstados(Long codCentro) throws Exception;
	
}
