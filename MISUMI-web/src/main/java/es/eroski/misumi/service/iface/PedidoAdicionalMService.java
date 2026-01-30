package es.eroski.misumi.service.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoAdicionalM;


public interface PedidoAdicionalMService {

	public List<PedidoAdicionalM> findAll(PedidoAdicionalM pedidoAdicionalM, HttpSession session) throws Exception;
	
	public List<PedidoAdicionalM> removeAll(List<PedidoAdicionalM> listaPedidoAdicionalM, HttpSession session) throws Exception;
	
	public List<PedidoAdicionalM> modifyAll(List<PedidoAdicionalM> listaPedidoAdicionalM, String tipoModificado,HttpSession session) throws Exception;	
	
	public List<PedidoAdicionalM> validateAll(List<PedidoAdicionalM> listaPedidoAdicionalM, HttpSession session ) throws Exception;

	public List<PedidoAdicionalM> findAllVegalsa(PedidoAdicionalM pedidoAdicionalM, HttpSession session) throws Exception;

}
