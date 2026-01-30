package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CambioGamaDao;
import es.eroski.misumi.model.CambioGama;
import es.eroski.misumi.service.iface.CambioGamaService;

@Service(value = "CambioGamaService")
public class CambioGamaServiceImpl implements CambioGamaService {

	@Autowired
	private CambioGamaDao cambioGamaDao;
	
	@Override
	public CambioGama existeGama(Long codCentro, Long codArt) throws Exception {
		return cambioGamaDao.existeGama(codCentro, codArt);
	}
	
	@Override
	public void updateCambioGama(CambioGama cambioGama, String accion, String usuario)  throws Exception {
		cambioGamaDao.updateCambioGama(cambioGama, accion, usuario);
	}
	
	@Override
	public void insertarCambioGama(Long codCentro, Long codArt,String accion, String usuario) throws Exception{
		cambioGamaDao.insertarCambioGama(codCentro, codArt, accion, usuario);
	}

}
