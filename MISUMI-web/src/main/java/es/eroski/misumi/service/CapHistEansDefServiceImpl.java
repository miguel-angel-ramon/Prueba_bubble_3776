package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CapHistEansDefDao;
import es.eroski.misumi.model.CapHistEansDef;
import es.eroski.misumi.service.iface.CapHistEansDefService;

@Service(value = "CapHistEansDefService")
public class CapHistEansDefServiceImpl implements CapHistEansDefService {
    @Autowired
	private CapHistEansDefDao capHistEansDefDao;
	
    @Override
	public List<CapHistEansDef> findAll(CapHistEansDef capHistEansDef) throws Exception {
		return this.capHistEansDefDao.findAll(capHistEansDef);
	}
	
    @Override
	public Long findAllCont(CapHistEansDef capHistEansDef) throws Exception {
		return this.capHistEansDefDao.findAllCont(capHistEansDef);
	}
	
    @Override
	public CapHistEansDef findOne(CapHistEansDef capHistEansDef) throws Exception {
    	CapHistEansDef capHistEansDefRes = null;
		List<CapHistEansDef> listCapHistEansDef = this.capHistEansDefDao.findAll(capHistEansDef);
		if (!listCapHistEansDef.isEmpty()){
			capHistEansDefRes = listCapHistEansDef.get(0);
		}
		return capHistEansDefRes;
	}
	
    @Override
	public Long obtenerReferenciaEan(Long codigoEan) throws Exception {
    	CapHistEansDef capHistEansDef = new CapHistEansDef();
		CapHistEansDef capHistEansDefRes;
		Long codigoReferencia = null;
		
		capHistEansDef.setCodEan(codigoEan);
		
		capHistEansDefRes = this.findOne(capHistEansDef);
		if (capHistEansDefRes != null &&  capHistEansDefRes.getCodArticulo() != null){
			codigoReferencia = capHistEansDefRes.getCodArticulo();
		}
		
		return codigoReferencia;
	}

}
