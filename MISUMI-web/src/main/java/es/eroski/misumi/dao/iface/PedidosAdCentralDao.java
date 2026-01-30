package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.PedidosAdCentral;

public interface PedidosAdCentralDao  {

	public List<PedidosAdCentral> findAll(PedidosAdCentral pedidosAdCentral) throws Exception ;
}
