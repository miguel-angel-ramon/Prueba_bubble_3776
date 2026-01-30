package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VMapaReferenciaDao;
import es.eroski.misumi.model.VMapaReferencia;
import es.eroski.misumi.service.iface.VMapaReferenciaService;

@Service(value = "VMapaReferenciaService")
public class VMapaReferenciaServiceImpl implements VMapaReferenciaService {
	//private static Logger logger = LoggerFactory.getLogger(VMapaAprovServiceImpl.class);
	
    @Autowired
	private VMapaReferenciaDao vMapaReferenciaDao;
	
    @Override
	 public VMapaReferencia findMapa(Long codArt) throws Exception {
		return this.vMapaReferenciaDao.findMapa(codArt);
	}
	
    @Override
	 public boolean existsMapa(Long codArt) {
		return this.vMapaReferenciaDao.existsMapa(codArt);
	}

}
