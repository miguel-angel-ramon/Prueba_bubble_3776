package es.eroski.misumi.service.iface;

public interface SmfCapacidadService {

	/**
	 * Obtiene la descripcion a emplear para el botón de SmfCapacidad
	 * @param codCentro Codigo de centro o nulo.
	 * @return Descripcion a utilizar para el boton de SmfCapacidad.
	 */
	public String getMetodosBoton(Long codCentro) throws Exception;
}
