package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VArtSfmDao;
import es.eroski.misumi.service.iface.SmfCapacidadService;

@Service(value = "SmfCapacidadService")
public class SmfCapacidadServiceImpl implements SmfCapacidadService {

    @Autowired
	private VArtSfmDao vArtSfmDao;
    
	@Override
	public String getMetodosBoton(Long codCentro) throws Exception {
		return this.vArtSfmDao.obtenerMetodosBoton(codCentro);
	}

}
