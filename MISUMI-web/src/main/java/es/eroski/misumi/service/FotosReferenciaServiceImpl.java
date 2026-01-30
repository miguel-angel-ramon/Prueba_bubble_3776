package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.FotosReferenciaDao;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.service.iface.FotosReferenciaService;

@Service(value = "FotosReferenciaService")
public class FotosReferenciaServiceImpl implements FotosReferenciaService {
	
	@Autowired
	private FotosReferenciaDao fotosReferenciaDao;

	@Override
	public FotosReferencia findImage(FotosReferencia fotosReferencia) throws Exception {
		// TODO Auto-generated method stub
		return fotosReferenciaDao.findImage(fotosReferencia);
	}

	@Override
	public boolean checkImage(FotosReferencia fotosReferencia) throws Exception {
		// TODO Auto-generated method stub
		boolean existe = false;
		Long total =  fotosReferenciaDao.checkImage(fotosReferencia);
		if (total > 0){
			existe = true;
		}
		
		return existe;
	}

}
