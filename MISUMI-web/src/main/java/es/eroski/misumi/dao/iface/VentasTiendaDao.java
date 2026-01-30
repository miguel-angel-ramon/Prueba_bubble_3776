package es.eroski.misumi.dao.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType;

public interface VentasTiendaDao extends CentroParametrizadoDao{

	//@Override
	public abstract VentasTiendaResponseType consultaVentas(
			VentasTiendaRequestType ventasTiendaRequest, HttpSession session) throws Exception;

}