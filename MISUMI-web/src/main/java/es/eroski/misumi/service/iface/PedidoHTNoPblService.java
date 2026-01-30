package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.PedidoHTNoPbl;
import es.eroski.misumi.model.PedidoHTNoPblLista;


public interface PedidoHTNoPblService {

	public PedidoHTNoPblLista consultaPedidosHTNoPbl(PedidoHTNoPbl pedidoHTNoPbl) throws Exception;
	public int countPedidosHTNoPbl(PedidoHTNoPbl pedidoHTNoPbl) throws Exception;
}
