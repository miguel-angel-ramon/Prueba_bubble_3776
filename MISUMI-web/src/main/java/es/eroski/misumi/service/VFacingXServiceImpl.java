package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VFacingXDao;
import es.eroski.misumi.model.VFacingX;
import es.eroski.misumi.service.iface.VFacingXService;

@Service(value = "VFacingXService")
public class VFacingXServiceImpl implements VFacingXService {
	//private static Logger logger = LoggerFactory.getLogger(VFacingXServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VFacingXServiceImpl.class);
	
    @Autowired
	private VFacingXDao vFacingXDao;
    
    @Override
	public List<VFacingX> findAll(VFacingX vFacingX) throws Exception{
		return this.vFacingXDao.findAll(vFacingX);
	}
	
    @Override
	public Long findAllCont(VFacingX vFacingX) throws Exception{
		return this.vFacingXDao.findAllCont(vFacingX);
	}
	
    @Override
	public VFacingX findOne(VFacingX vFacingX) throws Exception{
		return this.vFacingXDao.findOne(vFacingX);
	}
}
