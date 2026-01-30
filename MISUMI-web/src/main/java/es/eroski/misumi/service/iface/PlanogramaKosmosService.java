package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.PlanogramaVigente;

public interface PlanogramaKosmosService {
	public List<PlanogramaVigente> findAll(PlanogramaVigente planogramaVigente) throws Exception  ;  
	public PlanogramaVigente findOne(PlanogramaVigente planogramaVigente) throws Exception  ;
}
