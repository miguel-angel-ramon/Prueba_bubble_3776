package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.IncluirExcluirRefGamaDao;
import es.eroski.misumi.model.IncluirExcluirRefGama;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.service.iface.IncluirExcluirRefGamaService;

@Service(value = "IncluirExcluirRefGamaService")
public class IncluirExcluirRefGamaServiceImpl implements IncluirExcluirRefGamaService {
	
    @Autowired
	private IncluirExcluirRefGamaDao incluirExcluirRefGamaDao;
    
	
	@Override
	public IncluirExcluirRefGama incluirExluirRefGama(ReferenciasCentro vReferenciasCentro, String incluirExluir, String usuario) throws Exception {
		return incluirExcluirRefGamaDao.incluirExcluirRefGama(vReferenciasCentro, incluirExluir, usuario);
	}

}
