package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VDatosDiarioCapDao;
import es.eroski.misumi.model.VDatosDiarioCap;
import es.eroski.misumi.service.iface.VDatosDiarioCapService;

@Service(value = "VDatosDiarioCapService")
public class VDatosDiarioCapServiceImpl implements VDatosDiarioCapService {
	//private static Logger logger = LoggerFactory.getLogger(VDatosDiarioCapServiceImpl.class);

	@Autowired
	private VDatosDiarioCapDao vDatosDiarioCapDao;
	
    @Override
	 public List<VDatosDiarioCap> findAll(VDatosDiarioCap vDatosDiarioCap) throws Exception {
		return this.vDatosDiarioCapDao.findAll(vDatosDiarioCap);
	}
	
	@Override
	public VDatosDiarioCap findOne(VDatosDiarioCap vDatosDiarioCap) throws Exception {
		VDatosDiarioCap vDatosDiarioCapRes = null;
		List<VDatosDiarioCap> listDatosDiarioCap = this.vDatosDiarioCapDao.findAll(vDatosDiarioCap);
		if (!listDatosDiarioCap.isEmpty()){
			vDatosDiarioCapRes = listDatosDiarioCap.get(0);
		}
		return vDatosDiarioCapRes;
	}
}
