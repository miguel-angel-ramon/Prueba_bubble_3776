package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VSicFPromocionesDao;
import es.eroski.misumi.model.VSicFPromociones;
import es.eroski.misumi.service.iface.VSicFPromocionesService;

@Service(value = "VSicFPromocionesService")
public class VSicFPromocionesServiceImpl implements VSicFPromocionesService {
	//private static Logger logger = LoggerFactory.getLogger(VSicFPromocionesServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VSicFPromocionesServiceImpl.class);
    @Autowired
	private VSicFPromocionesDao vSicFPromocionesDao;
	
    @Override
	 public List<VSicFPromociones> findAll(VSicFPromociones vSicFPromociones) throws Exception {
		return this.vSicFPromocionesDao.findAll(vSicFPromociones);
	}

    @Override
	 public List<VSicFPromociones> findAllGondola(VSicFPromociones vSicFPromociones) throws Exception {
		return this.vSicFPromocionesDao.findAllGondola(vSicFPromociones);
	}
}
