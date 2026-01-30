package es.eroski.misumi.dao.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.PedidoAdicionalM;
import es.eroski.misumi.model.PedidoAdicionalMO;

public interface PedidoAdicionalMODao  {

	public List<PedidoAdicionalMO> findAll(PedidoAdicionalMO pedidoAdicionalMO, HttpSession session) throws Exception ;
	
	public List<PedidoAdicionalMO> removeAll(List<PedidoAdicionalMO> listaPedidoAdicionalMO, HttpSession session) throws Exception ;
	
	public List<PedidoAdicionalMO> modifyAll(List<PedidoAdicionalMO> listaPedidoAdicionalMO, String tipoModificado, HttpSession session) throws Exception ;
	
	public List<PedidoAdicionalMO> validateAll(List<PedidoAdicionalMO> listaPedidoAdicionalMO, HttpSession session) throws Exception ;

	public List<PedidoAdicionalMO> findAllVegalsa(PedidoAdicionalMO PedidoAdicionalMO, HttpSession session) throws Exception;
}
