package es.eroski.misumi.service.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.PedidoAdicionalEM;


public interface PedidoAdicionalEMService {

	public List<PedidoAdicionalEM> findAll(PedidoAdicionalEM pedidoAdicionalEM, HttpSession session) throws Exception;
	
}
