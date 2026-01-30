package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.TPedidoAdicional;


public interface PedidoAdicionalVCService {
	
	public void modifyAll(TPedidoAdicional tPedidoAdicional, HttpSession session) throws Exception;
	
}
