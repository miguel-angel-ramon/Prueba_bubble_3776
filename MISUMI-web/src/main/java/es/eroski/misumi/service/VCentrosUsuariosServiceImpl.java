package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VCentrosUsuariosDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.service.iface.VCentrosUsuariosService;

@Service(value = "VCentrosUsuariosService")
public class VCentrosUsuariosServiceImpl implements VCentrosUsuariosService{

	@Autowired
	private VCentrosUsuariosDao vCentrosUsuariosDao;

	@Override
	public List<Centro> findByCodDesc(String matcher, String codUser) throws Exception {
		// TODO Auto-generated method stub
		return vCentrosUsuariosDao.findByCodDesc(matcher, codUser);
	}

	@Override
	public List<Centro> findAll(Centro centro, String codUser) throws Exception {
		// TODO Auto-generated method stub
		return vCentrosUsuariosDao.findAll(centro, codUser);
	}

	@Override
	public Centro findOne(Centro centro, String codUser) throws Exception {
		// TODO Auto-generated method stub
		Centro centroRes = null;
		List<Centro> listaCentros = this.vCentrosUsuariosDao.findAll(centro, codUser);
		
		if (listaCentros!=null && listaCentros.size()>0){
			centroRes = listaCentros.get(0);
		}	
		
		return centroRes;
	}
	
	@Override
	public List<Centro> listZonaByRegion(Centro centro, String codUser) throws Exception {
		
		return this.vCentrosUsuariosDao.listZonasByRegion(centro, codUser);
	}
}
