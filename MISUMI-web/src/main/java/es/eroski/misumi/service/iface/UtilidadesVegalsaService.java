package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.Centro;

/**
 * Interfaz que contiene los metodos de obtención y transformacion de datos para Vegalsa
 * @author BICAGAAN
 *
 */
public interface UtilidadesVegalsaService {

	/**
	 * Comprueba si un centro-referencia tiene tratamiento VEGALSA.
	 *
	 */
	public boolean esTratamientoVegalsa(Centro centro, Long codArticulo) throws Exception;
	
	public Boolean esCentroVegalsa(HttpSession session);
}
