package es.eroski.misumi.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PlanogramaKosmosDaoSIA;
import es.eroski.misumi.dao.iface.PlanogramaVigenteDao;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.service.iface.PlanogramaKosmosService;

@Service(value = "PlanogramaKosmosService")
public class PlanogramaKosmosServiceImpl implements PlanogramaKosmosService{

    @Autowired
	private PlanogramaKosmosDaoSIA planogramaKosmosDaoSIA;
    
	@Override
	public List<PlanogramaVigente> findAll(PlanogramaVigente planogramaVigente) throws Exception {
		// TODO Auto-generated method stub
		return this.planogramaKosmosDaoSIA.findAll(planogramaVigente);
	}

	@Override
	public PlanogramaVigente findOne(PlanogramaVigente planogramaVigente) throws Exception {
		// TODO Auto-generated method stub
		PlanogramaVigente planogramaVigenteRes = null;
		List<PlanogramaVigente> listPlanogramaVigente = this.planogramaKosmosDaoSIA.findAll(planogramaVigente);
		if (!listPlanogramaVigente.isEmpty()){
			planogramaVigenteRes = listPlanogramaVigente.get(0);
		}
		return planogramaVigenteRes;
	}

}
