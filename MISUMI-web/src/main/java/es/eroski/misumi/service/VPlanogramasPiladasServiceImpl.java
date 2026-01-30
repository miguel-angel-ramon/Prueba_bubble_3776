package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VPlanogramasPiladasDao;
import es.eroski.misumi.model.VPlanogramasPiladas;
import es.eroski.misumi.service.iface.VPlanogramasPiladasService;

@Service(value = "VPlanogramasPiladasService")
public class VPlanogramasPiladasServiceImpl implements VPlanogramasPiladasService {

	@Autowired
	private VPlanogramasPiladasDao vPlanogramasPiladasDao;
	
	@Override
	public Long findDatosCabecera(VPlanogramasPiladas vPlanogramasPiladas)throws Exception {
		return this.vPlanogramasPiladasDao.findDatosCabecera(vPlanogramasPiladas);
	}

}
