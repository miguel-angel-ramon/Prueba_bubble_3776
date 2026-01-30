package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.PedidoAdicionalContadores;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoAdicionalM;
import es.eroski.misumi.model.PedidoAdicionalMO;
import es.eroski.misumi.model.TPedidoAdicional;



public interface PedidoAdicionalService {

	public PedidoAdicionalE obtenerArticuloTablaSesionE(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA) throws Exception;
	
	public PedidoAdicionalM obtenerArticuloTablaSesionM(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA, Long identificadorVegalsa) throws Exception;
	
	public PedidoAdicionalMO obtenerArticuloTablaSesionMO(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA, Long identificadorVegalsa) throws Exception;
	
	public PedidoAdicionalContadores loadContadores(PedidoAdicionalE pedidoAdicionalE,HttpSession session, HttpServletResponse response) throws Exception;
	
	public void modifyPedidoAdCentral(TPedidoAdicional tPedidoAdicional, HttpSession session) throws Exception;

	public PedidoAdicionalContadores loadContadoresVegalsa(PedidoAdicionalE pedidoAdicionalE) throws Exception;

	public void insertarPedidosVegalsa(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception;
	
	public PedidoAdicionalContadores loadContadoresVegalsaReferencia(TPedidoAdicional pedidoAdicional) throws Exception;
}