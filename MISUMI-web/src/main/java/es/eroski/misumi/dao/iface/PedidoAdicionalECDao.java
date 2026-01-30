package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.PedidoAdicionalEC;

public interface PedidoAdicionalECDao  {

	public List<PedidoAdicionalEC> findAll(PedidoAdicionalEC pedidoAdicionalEC) throws Exception ;
	
	public List<PedidoAdicionalEC> removeAll(List<PedidoAdicionalEC> listaPedidoAdicionalEC) throws Exception ;
	
	public List<PedidoAdicionalEC> modifyAll(List<PedidoAdicionalEC> listaPedidoAdicionalEC) throws Exception ;	
}
