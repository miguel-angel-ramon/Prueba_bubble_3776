package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Planograma;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.VPlanograma;

public interface PlanogramaService {
	public List<Planograma> findAll(PlanogramaVigente planogramaVigente) throws Exception;
	public Planograma findOne(PlanogramaVigente planogramaVigente) throws Exception;
	public String updatePlanograma(PlanogramaVigente planogramaVigenteReq) throws Exception;
	public String insertPlanograma(PlanogramaVigente planogramaVigenteReq, VPlanograma vPlanograma) throws Exception;
}
