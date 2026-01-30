package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VAgruComerRefDao;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VAgruComerRefService;

@Service(value = "VAgruComerRefService")
public class VAgruComerRefServiceImpl implements VAgruComerRefService {
	//private static Logger logger = LoggerFactory.getLogger(VAgruComerRefServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VAgruComerRefServiceImpl.class);
    @Autowired
	private VAgruComerRefDao VAgruComerRefDao;
	@Override
	 public List<VAgruComerRef> findAll(VAgruComerRef vAgruComerRef, Pagination pagination) throws Exception {
		return this.VAgruComerRefDao.findAll(vAgruComerRef, pagination);
	}
	
}
