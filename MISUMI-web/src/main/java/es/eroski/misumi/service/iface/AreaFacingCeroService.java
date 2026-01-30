package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.AreaFacingCero;

public interface AreaFacingCeroService {
	/**
	 * Obtiene los estados de las estructuras de un centro
	 *
	 */
	public AreaFacingCero getAreasFacingCero(Long codCentro) throws Exception;

}
