package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidoHTNoPblDao;
import es.eroski.misumi.model.PedidoHTNoPbl;
import es.eroski.misumi.model.PedidoHTNoPblLista;
import es.eroski.misumi.service.iface.PedidoHTNoPblService;

@Service(value = "PedidoHTNoPblService")
public class PedidoHTNoPblServiceImpl implements PedidoHTNoPblService {
	//private static Logger logger = LoggerFactory.getLogger(PedidoHTNoPblServiceImpl.class);
	//private static Logger logger = Logger.getLogger(PedidoHTNoPblServiceImpl.class);
    @Autowired
	private PedidoHTNoPblDao pedidoHTNoPblDao;
	@Override
	 public PedidoHTNoPblLista consultaPedidosHTNoPbl(PedidoHTNoPbl pedidoHTNoPbl) throws Exception {
		return this.pedidoHTNoPblDao.consultaPedidosHTNoPbl(pedidoHTNoPbl);
	}
	
	@Override
	 public int countPedidosHTNoPbl(PedidoHTNoPbl pedidoHTNoPbl) throws Exception {
		return this.pedidoHTNoPblDao.countPedidosHTNoPbl(pedidoHTNoPbl);
	}

}
