package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidosAdCentralDao;
import es.eroski.misumi.model.PedidosAdCentral;
import es.eroski.misumi.service.iface.PedidosAdCentralService;

@Service(value = "PedidosAdCentralService")
public class PedidosAdCentralServiceImpl implements PedidosAdCentralService {
	//private static Logger logger = LoggerFactory.getLogger(PedidosAdCentralServiceImpl.class);
    @Autowired
	private PedidosAdCentralDao pedidosAdCentralDao;
	@Override
	 public List<PedidosAdCentral> findAll(PedidosAdCentral pedidosAdCentral) throws Exception {
		return this.pedidosAdCentralDao.findAll(pedidosAdCentral);
	}
	
}
