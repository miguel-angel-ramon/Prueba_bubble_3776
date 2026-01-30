package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VMapaAprovDao;
import es.eroski.misumi.model.VMapaAprov;
import es.eroski.misumi.service.iface.VMapaAprovService;

@Service(value = "VMapaAprovService")
public class VMapaAprovServiceImpl implements VMapaAprovService {
	//private static Logger logger = LoggerFactory.getLogger(VMapaAprovServiceImpl.class);
    @Autowired
	private VMapaAprovDao vMapaAprovDao;
	
    @Override
	 public List<VMapaAprov> findAll(VMapaAprov vMapaAprov) throws Exception {
		return this.vMapaAprovDao.findAll(vMapaAprov);
	}
	
    @Override
	 public Long count(VMapaAprov vMapaAprov) throws Exception {
		return this.vMapaAprovDao.count(vMapaAprov);
	}

    @Override
	public VMapaAprov findOne(VMapaAprov vMapaAprov) throws Exception {
		VMapaAprov vMapaAprovRes = null;
		List<VMapaAprov> listVMapaAprov = this.vMapaAprovDao.findAll(vMapaAprov);
		if (!listVMapaAprov.isEmpty()){
			vMapaAprovRes = listVMapaAprov.get(0);
		}
		return vMapaAprovRes;

	}

}
