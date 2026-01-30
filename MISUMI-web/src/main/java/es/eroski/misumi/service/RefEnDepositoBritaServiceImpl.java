package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RefEnDepositoBritaDao;
import es.eroski.misumi.model.RefEnDepositoBrita;
import es.eroski.misumi.service.iface.RefEnDepositoBritaService;

@Service(value = "RefEnDepositoBritaService")
public class RefEnDepositoBritaServiceImpl implements RefEnDepositoBritaService {
	//private static Logger logger = LoggerFactory.getLogger(RefEnDepositoBritaServiceImpl.class);
	//private static Logger logger = Logger.getLogger(RefEnDepositoBritaServiceImpl.class);
    @Autowired
	private RefEnDepositoBritaDao refEnDepositoBritaDao;
	
    @Override
	 public List<RefEnDepositoBrita> findAll(RefEnDepositoBrita refEnDepositoBrita) throws Exception {
		return this.refEnDepositoBritaDao.findAll(refEnDepositoBrita);
	}
	
	@Override
	public RefEnDepositoBrita findOne(RefEnDepositoBrita refEnDepositoBrita) throws Exception {
		RefEnDepositoBrita refEnDepositoBritaRes = null;
		List<RefEnDepositoBrita> listRefEnDepositoBrita = this.refEnDepositoBritaDao.findAll(refEnDepositoBrita);
		if (!listRefEnDepositoBrita.isEmpty()){
			refEnDepositoBritaRes = listRefEnDepositoBrita.get(0);
		}
		return refEnDepositoBritaRes;
	}
	
	@Override
	public Long findAllCont(RefEnDepositoBrita refEnDepositoBrita) throws Exception {
		return this.refEnDepositoBritaDao.findAllCont(refEnDepositoBrita);
	}

	@Override
	public boolean enDepositoBrita(RefEnDepositoBrita refEnDepositoBrita) throws Exception{
		return this.findAllCont(refEnDepositoBrita) > 0;
	}
}
