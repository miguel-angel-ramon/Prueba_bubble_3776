package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VReferenciasPedirSIADao;
import es.eroski.misumi.model.VReferenciasPedirSIA;
import es.eroski.misumi.service.iface.VReferenciasPedirSIAService;

@Service(value = "VReferenciasPedirSIAService")
public class VReferenciasPedirSIAServiceImpl implements VReferenciasPedirSIAService {

	@Autowired
	private VReferenciasPedirSIADao vReferenciasPedirSIADao;
	
    @Override
	 public List<VReferenciasPedirSIA> findAll(VReferenciasPedirSIA vReferenciasPedirSIA) throws Exception {
		return this.vReferenciasPedirSIADao.findAll(vReferenciasPedirSIA);
	}
	
	@Override
	public VReferenciasPedirSIA findOne(VReferenciasPedirSIA vReferenciasPedirSIA) throws Exception {
		VReferenciasPedirSIA vReferenciasPedirSIARes = null;
		List<VReferenciasPedirSIA> listVReferenciasPedirSIA = this.vReferenciasPedirSIADao.findAll(vReferenciasPedirSIA);
		if (listVReferenciasPedirSIA!=null && !listVReferenciasPedirSIA.isEmpty()){
			vReferenciasPedirSIARes = listVReferenciasPedirSIA.get(0);
		}
		return vReferenciasPedirSIARes;
	}
	
	@Override
	public boolean tratarReferenciaSIA(VReferenciasPedirSIA vReferenciasPedirSIA) throws Exception {

		return this.findOne(vReferenciasPedirSIA) != null;
	}
}
