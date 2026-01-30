package es.eroski.misumi.dao.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.PedidoAdicionalContadores;
import es.eroski.misumi.model.PedidoAdicionalE;

public interface PedidoAdicionalEDao  {

	public List<PedidoAdicionalE> findAll(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception ;
	
	public List<PedidoAdicionalE> removeAll(List<PedidoAdicionalE> listaPedidoAdicionalE, HttpSession session) throws Exception ;
	
	public List<PedidoAdicionalE> validateAll(List<PedidoAdicionalE> listaPedidoAdicionalE, HttpSession session) throws Exception ;
	
	public PedidoAdicionalContadores getContadores(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception ;
}
