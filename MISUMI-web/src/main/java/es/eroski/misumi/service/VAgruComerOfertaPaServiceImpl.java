package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VAgruComerOfertaPaDao;
import es.eroski.misumi.model.VAgruComerOfertaPa;
import es.eroski.misumi.service.iface.VAgruComerOfertaPaService;

@Service(value = "VAgruComerOfertaPaService")
public class VAgruComerOfertaPaServiceImpl implements VAgruComerOfertaPaService {
    @Autowired
	private VAgruComerOfertaPaDao vAgruComerOfertaPaDao;
	@Override
	 public List<VAgruComerOfertaPa> findAll(VAgruComerOfertaPa vAgruComerOfertaPa) throws Exception {
		return this.vAgruComerOfertaPaDao.findAll(vAgruComerOfertaPa);
	}
	
}
