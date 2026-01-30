package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidoAdicionalNuevoDao;
import es.eroski.misumi.dao.iface.TPedidoAdicionalDao;
import es.eroski.misumi.model.PedidoAdicionalNuevo;
import es.eroski.misumi.model.VReferenciasNuevasVegalsa;
import es.eroski.misumi.service.iface.PedidoAdicionalNuevoService;

@Service(value = "PedidoAdicionalNuevoService")
public class PedidoAdicionalNuevoServiceImpl implements PedidoAdicionalNuevoService{

    @Autowired
	private PedidoAdicionalNuevoDao pedidoAdicionalDao;

    @Autowired
    private TPedidoAdicionalDao tPedidoAdicionalDao;
	
	@Override
	public VReferenciasNuevasVegalsa getDatosValidacionVegalsa(PedidoAdicionalNuevo pedidoAdicionalNuevo) {
		return pedidoAdicionalDao.getDatosValidacionVegalsa(pedidoAdicionalNuevo);
	}

	@Override
	public void savePedidoAdicionalNuevo(PedidoAdicionalNuevo pedidoAdicionalNuevo) throws Exception {
		tPedidoAdicionalDao.insertMontajesVegalsa(pedidoAdicionalNuevo);
	}

}
