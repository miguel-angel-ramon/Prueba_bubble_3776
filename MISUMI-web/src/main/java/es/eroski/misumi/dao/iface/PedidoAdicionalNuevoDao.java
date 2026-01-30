package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.PedidoAdicionalNuevo;
import es.eroski.misumi.model.VReferenciasNuevasVegalsa;

public interface PedidoAdicionalNuevoDao {

	/**
	 * Obtiene de la BBDD los datos necesarios para el pedido adicional de VEGALSA.
	 * @param pedidoAdicionalNuevo
	 * @return
	 */
	public VReferenciasNuevasVegalsa getDatosValidacionVegalsa(PedidoAdicionalNuevo pedidoAdicionalNuevo);
	
}
