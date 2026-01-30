package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.PedidoAdicionalNuevo;
import es.eroski.misumi.model.VReferenciasNuevasVegalsa;

public interface PedidoAdicionalNuevoService {

	/**
	 * Obtener los datos necesarios 
	 * @return
	 */
	VReferenciasNuevasVegalsa getDatosValidacionVegalsa(PedidoAdicionalNuevo pedidoAdicionalNuevo);
	
	void savePedidoAdicionalNuevo(PedidoAdicionalNuevo pedidoAdicionalNuevo) throws Exception;
}
