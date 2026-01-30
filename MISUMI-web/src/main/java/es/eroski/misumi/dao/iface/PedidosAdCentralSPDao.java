package es.eroski.misumi.dao.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.TPedidoAdicional;

public interface PedidosAdCentralSPDao {

	public abstract void actualizacionPedidosAd(
			TPedidoAdicional tPedidoAdicional, HttpSession session) throws Exception;

}