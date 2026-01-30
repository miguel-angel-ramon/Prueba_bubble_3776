package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RoturasDao;
import es.eroski.misumi.model.Roturas;
import es.eroski.misumi.service.iface.RoturasService;

@Service(value = "RoturasService")
public class RoturasServiceImpl implements RoturasService {
	//private static Logger logger = LoggerFactory.getLogger(RoturasServiceImpl.class);
    @Autowired
	private RoturasDao roturasDao;
	
    @Override
	 public List<Roturas> findAll(Roturas roturas) throws Exception {
		return this.roturasDao.findAll(roturas);
	}
	
    @Override
	 public Long count(Roturas roturas) throws Exception {
		return this.roturasDao.count(roturas);
	}

    @Override
	public Roturas findOne(Roturas roturas) throws Exception {
		Roturas roturasRes = null;
		List<Roturas> listRoturas = this.roturasDao.findAll(roturas);
		if (!listRoturas.isEmpty()){
			roturasRes = listRoturas.get(0);
		}
		return roturasRes;

	}

}
