package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.EansDao;
import es.eroski.misumi.model.Eans;
import es.eroski.misumi.service.iface.EansService;

@Service(value = "EansService")
public class EansServiceImpl implements EansService {
    @Autowired
	private EansDao eansDao;
	
    @Override
	public List<Eans> findAll(Eans eans) throws Exception {
		return this.eansDao.findAll(eans);
	}
	
    @Override
	public Long findAllCont(Eans eans) throws Exception {
		return this.eansDao.findAllCont(eans);
	}
	
    @Override
	public Eans findOne(Eans eans) throws Exception {
		Eans eansRes = null;
		List<Eans> listEans = this.eansDao.findAll(eans);
		if (!listEans.isEmpty()){
			eansRes = listEans.get(0);
		}
		return eansRes;
	}
	
    @Override
	public Long obtenerReferenciaEan(Long codigoEan) throws Exception {
		Eans eans = new Eans();
		Eans eansRes;
		Long codigoReferencia = null;
		
		eans.setCodEan(codigoEan);
		
		eansRes = this.findOne(eans);
		if (eansRes != null &&  eansRes.getCodArticulo() != null){
			codigoReferencia = eansRes.getCodArticulo();
		}
		
		return codigoReferencia;
	}
    
    @Override
	public Long obtenerReferenciaEan14(Long codigoEan) throws Exception {
		return this.eansDao.obtenerReferenciaEan14(codigoEan);
	}

}
