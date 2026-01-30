package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ImagenComercialDao;
import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.service.iface.ImagenComercialService;

@Service(value = "ImagenComercialService")
public class ImagenComercialServiceImpl implements ImagenComercialService{

	@Autowired
	private ImagenComercialDao imagenComercialDao;
	
	@Override
	public ImagenComercial consultaImc(Long codLoc, Long codArtFormlog) {
		// TODO Auto-generated method stub
		return imagenComercialDao.consultaImc(codLoc, codArtFormlog);
	}

	@Override
	public ImagenComercial simularImc(ImagenComercial imagenComercial) {
		// TODO Auto-generated method stub
		return imagenComercialDao.simularImc(imagenComercial);
	}

	@Override
	public ImagenComercial modificarImc(ImagenComercial imagenComercial) {
		// TODO Auto-generated method stub
		return imagenComercialDao.modificarImc(imagenComercial);
	}

}
