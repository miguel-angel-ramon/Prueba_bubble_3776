package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VCentrosPlataformasDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.service.iface.VCentrosPlataformasService;

@Service(value = "VCentrosPlataformasService")
public class VCentrosPlataformasServiceImpl implements VCentrosPlataformasService {
	//private static Logger logger = LoggerFactory.getLogger(VCentrosPlataformasServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VCentrosPlataformasServiceImpl.class);
	 @Autowired
	private VCentrosPlataformasDao vCentrosPlataformasDao;
	
	@Override
	public List<Centro> findAll(Centro centro) throws Exception {
		
		return this.vCentrosPlataformasDao.findAll(centro);
	}

	@Override
	public Centro findOne(Centro centro) throws Exception {
		Centro centroRes = null;
		List<Centro> listaCentros = this.vCentrosPlataformasDao.findAll(centro);
		
		if (listaCentros!=null && listaCentros.size()>0){
			centroRes = listaCentros.get(0);
		}	
		
		return centroRes;
	}
	
	@Override
	public List<Centro> findByCodDesc(String matcher) throws Exception {
	
		return this.vCentrosPlataformasDao.findByCodDesc(matcher);
	}

	@Override
	public List<Centro> listZonaByRegion(Centro centro) throws Exception {
		
		return this.vCentrosPlataformasDao.listZonasByRegion(centro);
	}
	
	@Override
	public Boolean isLotesCentroActivo(Long codCentro) throws Exception{
		return this.vCentrosPlataformasDao.isLotesCentroActivo(codCentro);
	}

}
