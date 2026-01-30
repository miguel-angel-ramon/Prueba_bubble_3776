package es.eroski.misumi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.NuevoPedidoOfertaDao;
import es.eroski.misumi.model.NuevoPedidoOferta;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.NuevoPedidoOfertaService;


@Service(value = "NuevoPedidoOfertaService")
public class NuevoPedidoOfertaServiceImpl implements NuevoPedidoOfertaService {
    @Autowired
	private NuevoPedidoOfertaDao nuevoPedidoOfertaDao;
	@Override
	 public List<NuevoPedidoOferta> findAll(NuevoPedidoOferta nuevoPedidoOferta, String idSession, String usuario, HttpSession session) throws Exception {
		return this.nuevoPedidoOfertaDao.findAll(nuevoPedidoOferta, idSession, usuario, session);
	}
	
	@Override
	public void insertarTablaSesionNuevoOferta(List<NuevoPedidoOferta> list, String idSesion, String usuario, Long clasePedido) throws Exception{
		this.nuevoPedidoOfertaDao.insertarTablaSesionNuevoOferta(list, idSesion, usuario, clasePedido);
	}
	
	@Override
	public void recargaDiasServicioArticulosPagina (NuevoPedidoOferta nuevoPedidoOferta, List<NuevoPedidoOferta> listaNuevosPedidos, String idSession) throws Exception{
		this.nuevoPedidoOfertaDao.recargaDiasServicioArticulosPagina(nuevoPedidoOferta, listaNuevosPedidos, idSession);
	}
	
	@Override
	public void comprobacionBloqueos(NuevoPedidoOferta nuevoPedidoOferta, User user, String tipoListado, boolean validandoPedidos) throws Exception{
		this.nuevoPedidoOfertaDao.comprobacionBloqueos(nuevoPedidoOferta, user, tipoListado, validandoPedidos);
	}
}
