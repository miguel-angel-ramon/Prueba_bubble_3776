package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.PlanogramaVigente;

public interface PlanogramaKosmosDaoSIA {
	public List<PlanogramaVigente> findAll(PlanogramaVigente planogramaVigente) throws Exception  ;  
}
