package es.eroski.misumi.dao.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.NuevoPedidoOferta;
import es.eroski.misumi.model.User;

public interface NuevoPedidoOfertaDao  {

	public List<NuevoPedidoOferta> findAll(NuevoPedidoOferta nuevoPedidoOferta, String idSession, String usuario, HttpSession session) throws Exception ;
	
	public void insertarTablaSesionNuevoOferta(List<NuevoPedidoOferta> list, String idSesion, String usuario, Long clasePedido) throws Exception;
	
	public void recargaDiasServicioArticulosPagina (NuevoPedidoOferta nuevoPedidoOferta, List<NuevoPedidoOferta> listaNuevosPedidos, String idSession) throws Exception;
	
	public void comprobacionBloqueos(NuevoPedidoOferta nuevoPedidoOferta, User user, String tipoListado, boolean validandoPedidos) throws Exception;
}
