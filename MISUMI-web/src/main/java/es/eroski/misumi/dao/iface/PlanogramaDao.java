package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Planograma;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.VPlanograma;

public interface PlanogramaDao {
	public List<Planograma> findAll(PlanogramaVigente planogramaVigente) throws Exception;
	public String updatePlanograma(PlanogramaVigente planogramaVigenteReq) throws Exception;
	public String insertPlanograma(PlanogramaVigente planogramaVigenteReq, VPlanograma vPlanograma) throws Exception;
}
