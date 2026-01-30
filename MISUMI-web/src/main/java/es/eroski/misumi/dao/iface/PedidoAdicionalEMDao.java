package es.eroski.misumi.dao.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.PedidoAdicionalEM;

public interface PedidoAdicionalEMDao  {

	public List<PedidoAdicionalEM> findAll(PedidoAdicionalEM pedidoAdicionalEM, HttpSession session) throws Exception ;
	
}
