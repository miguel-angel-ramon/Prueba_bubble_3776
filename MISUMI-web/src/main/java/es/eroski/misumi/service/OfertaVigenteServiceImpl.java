package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.OfertaVigenteDao;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.OfertaVigente;
import es.eroski.misumi.service.iface.OfertaVigenteService;

@Service(value = "OfertaVigenteService")
public class OfertaVigenteServiceImpl implements OfertaVigenteService {
	//private static Logger logger = LoggerFactory.getLogger(OfertaVigenteServiceImpl.class);
	//private static Logger logger = Logger.getLogger(OfertaVigenteServiceImpl.class);
    @Autowired
	private OfertaVigenteDao ofertaVigenteDao;
	
    @Override
	 public List<OfertaVigente> findAll(OfertaVigente ofertaVigente) throws Exception {
		return this.ofertaVigenteDao.findAll(ofertaVigente);
	}
	
	@Override
	public OfertaVigente findOne(OfertaVigente ofertaVigente) throws Exception {
		OfertaVigente ofertaVigenteRes = null;
		List<OfertaVigente> listOferta = this.ofertaVigenteDao.findAll(ofertaVigente);
		if (!listOferta.isEmpty()){
			ofertaVigenteRes = listOferta.get(0);
		}
		return ofertaVigenteRes;

	}
	
	// MISUMI-391 MISUMIS-JAVA VEGALSA-FAMILIA sacar las ofertas de oferta vigente
	@Override
	 public OfertaPVP recuperarAnnoOferta(OfertaPVP ofertaPVP) throws Exception {
		return this.ofertaVigenteDao.recuperarAnnoOferta(ofertaPVP);
	}
	
}
