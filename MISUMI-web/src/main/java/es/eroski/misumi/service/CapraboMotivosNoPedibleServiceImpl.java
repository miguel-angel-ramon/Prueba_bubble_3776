package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CapraboMotivosNoPedibleDao;
import es.eroski.misumi.model.CapraboMotivoNoPedible;
import es.eroski.misumi.service.iface.CapraboMotivosNoPedibleService;

@Service(value = "CapraboMotivosNoPedibleService")
public class CapraboMotivosNoPedibleServiceImpl implements CapraboMotivosNoPedibleService {
	//private static Logger logger = LoggerFactory.getLogger(CapraboMotivosNoPedibleServiceImpl.class);
	//private static Logger logger = Logger.getLogger(CapraboMotivosNoPedibleServiceImpl.class);
    @Autowired
	private CapraboMotivosNoPedibleDao capraboMotivosNoPedibleDao;
    
	@Override
	public CapraboMotivoNoPedible findCentroRefTipo(final CapraboMotivoNoPedible capraboMotivoNoPedible) throws Exception  {
		return this.capraboMotivosNoPedibleDao.findCentroRefTipo(capraboMotivoNoPedible);
	}
}
