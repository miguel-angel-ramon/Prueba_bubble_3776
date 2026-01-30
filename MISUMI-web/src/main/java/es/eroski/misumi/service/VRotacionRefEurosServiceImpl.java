package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VRotacionRefEurosDao;
import es.eroski.misumi.model.VRotacionRefEuros;
import es.eroski.misumi.service.iface.VRotacionRefEurosService;

@Service(value = "VRotacionRefEurosService")
public class VRotacionRefEurosServiceImpl implements VRotacionRefEurosService {
	//private static Logger logger = LoggerFactory.getLogger(VRotacionRefEurosServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VRotacionRefEurosServiceImpl.class);
	
    @Autowired
	private VRotacionRefEurosDao vRotacionRefEurosDao;
    
    @Override
	public List<VRotacionRefEuros> findAll(VRotacionRefEuros vRotacionRefEuros) throws Exception{
		return this.vRotacionRefEurosDao.findAll(vRotacionRefEuros);
	}
	
    @Override
	public Long findAllCont(VRotacionRefEuros vRotacionRefEuros) throws Exception{
		return this.vRotacionRefEurosDao.findAllCont(vRotacionRefEuros);
	}
	
    @Override
	public VRotacionRefEuros findOne(VRotacionRefEuros vRotacionRefEuros) throws Exception{
		return this.vRotacionRefEurosDao.findOne(vRotacionRefEuros);
	}
}
