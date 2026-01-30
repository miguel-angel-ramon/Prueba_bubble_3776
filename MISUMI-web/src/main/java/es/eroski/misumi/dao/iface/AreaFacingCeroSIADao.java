package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.AreaFacingCeroSIALista;

public interface AreaFacingCeroSIADao  {

	public AreaFacingCeroSIALista consultaAreas(Long codCentro) throws Exception;

}
