package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.PedidoHTNoPbl;
import es.eroski.misumi.model.PedidoHTNoPblLista;

public interface PedidoHTNoPblDao  {

	public PedidoHTNoPblLista consultaPedidosHTNoPbl(PedidoHTNoPbl pediHtNoPbl) throws Exception ;
	public int countPedidosHTNoPbl(PedidoHTNoPbl pediHtNoPbl) throws Exception ;
}
