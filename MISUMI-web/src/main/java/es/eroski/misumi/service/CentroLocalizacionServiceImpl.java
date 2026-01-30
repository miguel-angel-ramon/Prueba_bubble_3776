package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CentroLocalizacionDao;
import es.eroski.misumi.dao.iface.GoogleDao;
import es.eroski.misumi.model.DireccionCentro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.CentroLocalizacionService;

@Service(value = "centroLocalizacionService")
public class CentroLocalizacionServiceImpl implements CentroLocalizacionService{
	
	@Autowired
	private CentroLocalizacionDao centroLocalizacionDao;
	
	@Autowired 
	private GoogleDao googleDao;
	
	public DireccionCentro obtenerCentroLocalizacion(User user) throws Exception{
		DireccionCentro dirCentro = centroLocalizacionDao.obtenerCentroLocalizacion(user);
		return dirCentro;
	}

	@Override
	public String obtenerDireccionGoogle(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		return this.googleDao.obtenerDireccionGoogle(codCentro);
	}

	@Override
	public void guardarDireccionGoogle(Long codCentro, String direccionGoogle) throws Exception {
		// TODO Auto-generated method stub
		this.googleDao.guardarDireccionGoogle(codCentro,direccionGoogle);
	}
}
