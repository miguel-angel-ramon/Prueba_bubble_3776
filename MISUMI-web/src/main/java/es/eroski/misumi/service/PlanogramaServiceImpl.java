package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PlanogramaDao;
import es.eroski.misumi.model.Planograma;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.VPlanograma;
import es.eroski.misumi.service.iface.PlanogramaService;

@Service(value = "PlanogramaService")
public class PlanogramaServiceImpl implements PlanogramaService{

    @Autowired
	private PlanogramaDao planogramaDao;
    
	@Override
	public List<Planograma> findAll(PlanogramaVigente planogramaVigente) throws Exception {
		// TODO Auto-generated method stub
		return planogramaDao.findAll(planogramaVigente);
	}

	@Override
	public Planograma findOne(PlanogramaVigente planogramaVigente) throws Exception {
		// TODO Auto-generated method stub
		Planograma planogramaRes = null;
		List<Planograma> listPlanograma = this.planogramaDao.findAll(planogramaVigente);
		if (listPlanograma != null && !listPlanograma.isEmpty()){
			planogramaRes = listPlanograma.get(0);
		}
		
		return planogramaRes;
	}

	@Override
	public String updatePlanograma(PlanogramaVigente planogramaVigenteReq) throws Exception {
		// TODO Auto-generated method stub
		return this.planogramaDao.updatePlanograma(planogramaVigenteReq);
	}

	@Override
	public String insertPlanograma(PlanogramaVigente planogramaVigenteReq, VPlanograma vPlanograma) throws Exception {
		// TODO Auto-generated method stub
		return this.planogramaDao.insertPlanograma(planogramaVigenteReq,vPlanograma);
	}

}
