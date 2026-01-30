package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.TMisMcgCapraboDao;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.service.iface.TMisMcgCapraboService;

@Service(value = "TMisMcgCapraboService")
public class TMisMcgCapraboServiceImpl implements TMisMcgCapraboService {

    @Autowired
	private TMisMcgCapraboDao tMisMcgCapraboDao;
	
    @Override
	 public List<TMisMcgCaprabo> findAll(TMisMcgCaprabo tMisMcgCaprabo) throws Exception {
		return this.tMisMcgCapraboDao.findAll(tMisMcgCaprabo);
	}
	
	@Override
	public TMisMcgCaprabo findOne(TMisMcgCaprabo tMisMcgCaprabo) throws Exception {
		TMisMcgCaprabo tMisMcgCapraboRes = null;
		List<TMisMcgCaprabo> listMisMcgCaprabo = this.tMisMcgCapraboDao.findAll(tMisMcgCaprabo);
		if (listMisMcgCaprabo!= null && !listMisMcgCaprabo.isEmpty()){
			tMisMcgCapraboRes = listMisMcgCaprabo.get(0);
		}
		return tMisMcgCapraboRes;

	}


}
