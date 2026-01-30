package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.BorrUniAuxDao;
import es.eroski.misumi.model.BorrUniAux;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.BorrUniAuxService;

@Service(value = "BorrUniAuxService")
public class BorrUniAuxServiceImpl implements BorrUniAuxService {
	//private static Logger logger = LoggerFactory.getLogger(BorrUniAuxServiceImpl.class);
	//private static Logger logger = Logger.getLogger(BorrUniAuxServiceImpl.class);
    @Autowired
	private BorrUniAuxDao borrUniAuxDao;
	@Override
	 public List<BorrUniAux> findAll(BorrUniAux borrUniAux, Pagination pagination) throws Exception {
		return this.borrUniAuxDao.findAll(borrUniAux, pagination);
	}
	
}
