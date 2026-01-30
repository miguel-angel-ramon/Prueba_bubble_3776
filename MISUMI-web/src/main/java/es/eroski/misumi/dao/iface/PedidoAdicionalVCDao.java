package es.eroski.misumi.dao.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.PedidoAdicionalVC;

public interface PedidoAdicionalVCDao  {

	public List<PedidoAdicionalVC> findAll(PedidoAdicionalVC pedidoAdicionalVC, HttpSession session) throws Exception ;
	
	public int count(PedidoAdicionalVC pedidoAdicionalVC, HttpSession session) throws Exception ;
	
	public List<PedidoAdicionalVC> modifyAll(List<PedidoAdicionalVC> listaPedidoAdicionalVC, String tipoModificado, HttpSession session) throws Exception ;
	
	public List<PedidoAdicionalVC> validateAll(List<PedidoAdicionalVC> listaPedidoAdicionalVC, HttpSession session) throws Exception ;
	
}
