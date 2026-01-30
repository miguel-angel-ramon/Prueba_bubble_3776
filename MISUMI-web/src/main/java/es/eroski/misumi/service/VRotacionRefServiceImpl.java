package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VRotacionRefDao;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.service.iface.VRotacionRefService;

@Service(value = "VRotacionRefService")
public class VRotacionRefServiceImpl implements VRotacionRefService {
	//private static Logger logger = LoggerFactory.getLogger(VRotacionRefServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VRotacionRefServiceImpl.class);
	
    @Autowired
	private VRotacionRefDao vRotacionRefDao;
    
    @Override
	public List<VRotacionRef> findAll(VRotacionRef vRotacionRef) throws Exception{
		return this.vRotacionRefDao.findAll(vRotacionRef);
	}
	
    @Override
	public Long findAllCont(VRotacionRef vRotacionRef) throws Exception{
		return this.vRotacionRefDao.findAllCont(vRotacionRef);
	}
	
    @Override
	public VRotacionRef findOne(VRotacionRef vRotacionRef) throws Exception{
		return this.vRotacionRefDao.findOne(vRotacionRef);
	}
}
