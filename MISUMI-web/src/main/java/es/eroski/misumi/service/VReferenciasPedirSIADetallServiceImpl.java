package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VReferenciasPedirSIADetallDao;
import es.eroski.misumi.model.VReferenciasPedirSIADetall;
import es.eroski.misumi.service.iface.VReferenciasPedirSIADetallService;

@Service(value = "VReferenciasPedirSIADetallService")
public class VReferenciasPedirSIADetallServiceImpl implements VReferenciasPedirSIADetallService {

	@Autowired
	private VReferenciasPedirSIADetallDao vReferenciasPedirSIADetallDao;
	
    @Override
	 public List<VReferenciasPedirSIADetall> findAll(VReferenciasPedirSIADetall vReferenciasPedirSIADetall) throws Exception {
		return this.vReferenciasPedirSIADetallDao.findAll(vReferenciasPedirSIADetall);
	}
	
	@Override
	public VReferenciasPedirSIADetall findOne(VReferenciasPedirSIADetall vReferenciasPedirSIADetall) throws Exception {
		VReferenciasPedirSIADetall vReferenciasPedirSIADetallRes = null;
		List<VReferenciasPedirSIADetall> listVReferenciasPedirSIA = this.vReferenciasPedirSIADetallDao.findAll(vReferenciasPedirSIADetall);
		if (!listVReferenciasPedirSIA.isEmpty()){
			vReferenciasPedirSIADetallRes = listVReferenciasPedirSIA.get(0);
		}
		return vReferenciasPedirSIADetallRes;
	}
	
	@Override
	public boolean tratarReferenciaSIA(VReferenciasPedirSIADetall vReferenciasPedirSIADetall) throws Exception {

		return this.findOne(vReferenciasPedirSIADetall) != null;
	}
}
