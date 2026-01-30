package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VarAprpvDao;
import es.eroski.misumi.model.VarAprpv;
import es.eroski.misumi.service.iface.VarAprpvService;

@Service(value = "VarAprpvService")
public class VarAprpvServiceImpl implements VarAprpvService {
	//private static Logger logger = LoggerFactory.getLogger(VarAprpvServiceImpl.class);
    @Autowired
	private VarAprpvDao varAprpvDao;
	
    @Override
	 public List<VarAprpv> findAll(VarAprpv varAprpv) throws Exception {
		return this.varAprpvDao.findAll(varAprpv);
	}
	
	@Override
	public VarAprpv findOne(VarAprpv varAprpv) throws Exception {
		VarAprpv varAprpvRes = null;
		List<VarAprpv> listVarAprpv = this.varAprpvDao.findAll(varAprpv);
		if (!listVarAprpv.isEmpty()){
			varAprpvRes = listVarAprpv.get(0);
		}
		return varAprpvRes;

	}

}
