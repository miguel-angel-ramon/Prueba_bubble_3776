package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.PedidosAdCentral;


public interface PedidosAdCentralService {

	  public List<PedidosAdCentral> findAll(PedidosAdCentral pedidosAdCentral) throws Exception  ;
}
