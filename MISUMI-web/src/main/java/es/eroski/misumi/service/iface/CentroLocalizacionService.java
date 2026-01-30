package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.DireccionCentro;
import es.eroski.misumi.model.User;

public interface CentroLocalizacionService {
	public DireccionCentro obtenerCentroLocalizacion(User user) throws Exception;
	public String obtenerDireccionGoogle(Long codCentro) throws Exception;
	public void guardarDireccionGoogle(Long codCentro,String direccionGoogle) throws Exception;
}
