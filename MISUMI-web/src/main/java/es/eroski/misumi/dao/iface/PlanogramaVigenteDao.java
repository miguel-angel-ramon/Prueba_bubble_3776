package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.PlanogramaVigente;

public interface PlanogramaVigenteDao  {

	public List<PlanogramaVigente> findAll(PlanogramaVigente planogramaVigente) throws Exception;
	public String updatePlanogramaVigente(PlanogramaVigente planogramaVigenteReq) throws Exception;
}
