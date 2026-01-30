package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RefAsociadasDao;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.service.iface.RefAsociadasService;

@Service(value = "RefAsociadasService")
public class RefAsociadasServiceImpl implements RefAsociadasService {

	@Autowired
	private RefAsociadasDao refAsociadasDao;
    
	@Override
	 public List<RefAsociadas> findAll(RefAsociadas refAsociadas) throws Exception {
		return this.refAsociadasDao.findAll(refAsociadas);
	}

	@Override
	public Long findAllCont(RefAsociadas refAsociadas) throws Exception{
		return this.refAsociadasDao.findAllCont(refAsociadas);
	}
}
