package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.DireccionCentro;
import es.eroski.misumi.model.User;

public interface CentroLocalizacionDao {
	public DireccionCentro obtenerCentroLocalizacion(User user) throws Exception;
}
