package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VOfertaPaDao;
import es.eroski.misumi.model.VOfertaPa;
import es.eroski.misumi.service.iface.VOfertaPaService;

@Service(value = "VOfertaPaService")
public class VOfertaPaServiceImpl implements VOfertaPaService {
	//private static Logger logger = LoggerFactory.getLogger(VOfertaServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VOfertaServiceImpl.class);
    @Autowired
	private VOfertaPaDao vOfertaPaDao;
	
    @Override
	 public List<VOfertaPa> findAllVigentes(VOfertaPa vOfertaPa) throws Exception {
		return this.vOfertaPaDao.findAllVigentes(vOfertaPa);
	}
    
    @Override
	 public List<VOfertaPa> findAllVigentesCentro(VOfertaPa vOfertaPa) throws Exception {
		return this.vOfertaPaDao.findAllVigentesCentro(vOfertaPa);
	}
	
	@Override
	public VOfertaPa findOneVigente(VOfertaPa vOfertaPa) throws Exception {
		return this.vOfertaPaDao.findOneVigente(vOfertaPa);
	}
	
	@Override
	 public List<VOfertaPa> findCountNOVigentes(VOfertaPa vOfertaPa, int vRow) throws Exception  {
		 return this.vOfertaPaDao.findCountNOVigentes(vOfertaPa, vRow); 
	 }
	
}
