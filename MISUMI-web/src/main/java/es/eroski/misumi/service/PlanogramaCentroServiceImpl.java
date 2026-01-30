package es.eroski.misumi.service;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PlanogramaCentroDao;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;
import es.eroski.misumi.service.iface.PlanogramaCentroService;


@Service(value = "PlanogramasCentroService")
public class PlanogramaCentroServiceImpl implements PlanogramaCentroService {
	   @Autowired
		private PlanogramaCentroDao planogramasCentroDao;
		
	   
	@Override
	 public ConsultaPlanogramaPorReferenciaResponseType findPlanogramasCentroWS(ReferenciasCentro vReferenciasCentro, HttpSession session) throws Exception{
			// TODO Auto-generated method stub
		return this.planogramasCentroDao.findPlanogramasCentroWS(vReferenciasCentro,session);
	}
	
}
