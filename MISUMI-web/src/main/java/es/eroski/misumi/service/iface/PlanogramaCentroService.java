package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;



public interface PlanogramaCentroService {

   
	 public ConsultaPlanogramaPorReferenciaResponseType findPlanogramasCentroWS(ReferenciasCentro vReferenciasCentro, HttpSession session) throws Exception;
	
}
