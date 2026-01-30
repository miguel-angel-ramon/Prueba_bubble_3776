package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType;

public interface VentasTiendaService {

	public abstract VentasTiendaResponseType consultaVentas(
			VentasTiendaRequestType ventasTiendaRequest, HttpSession session) throws Exception;

	/**
	 * Indica si el centro está parametrizado.
	 * @param codCentro
	 * @param tipoPermiso
	 * @return
	 * @throws Exception
	 */
	public boolean isCentroParametrizado(Long codCentro, String tipoPermiso) throws Exception;	
	
}