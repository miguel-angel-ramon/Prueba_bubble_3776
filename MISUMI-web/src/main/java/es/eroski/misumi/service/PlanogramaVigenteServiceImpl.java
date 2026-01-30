package es.eroski.misumi.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PlanogramaVigenteDao;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.service.iface.PlanogramaVigenteService;

@Service(value = "PlanogramaVigenteService")
public class PlanogramaVigenteServiceImpl implements PlanogramaVigenteService {
	//private static Logger logger = LoggerFactory.getLogger(PlanogramaVigenteServiceImpl.class);
    @Autowired
	private PlanogramaVigenteDao planogramaVigenteDao;
	
    @Override
	 public List<PlanogramaVigente> findAll(PlanogramaVigente vMapaAprov) throws Exception {
		return this.planogramaVigenteDao.findAll(vMapaAprov);
	}
	
    @Override
	public PlanogramaVigente findOne(PlanogramaVigente planogramaVigente) throws Exception {
		PlanogramaVigente planogramaVigenteRes = null;
		List<PlanogramaVigente> listPlanogramaVigente = this.planogramaVigenteDao.findAll(planogramaVigente);
		if (!listPlanogramaVigente.isEmpty()){
			planogramaVigenteRes = listPlanogramaVigente.get(0);
			
			//Cálculo del campo fecha generación
			Date fechaGenLineal = planogramaVigenteRes.getFechaGenLineal();
			String fechaGenLinealPantalla = "";
			if (fechaGenLineal != null){
				final SimpleDateFormat sdfFormateador = new SimpleDateFormat();
		        sdfFormateador.applyPattern("dd/MM/yyyy");

				fechaGenLinealPantalla = sdfFormateador.format(fechaGenLineal);
			}
			planogramaVigenteRes.setFechaGenLinealPantalla(fechaGenLinealPantalla);
		}
		
		return planogramaVigenteRes;

	}

	@Override
	public String updatePlanogramaVigente(PlanogramaVigente planogramaVigente) throws Exception {
		// TODO Auto-generated method stub
		return this.planogramaVigenteDao.updatePlanogramaVigente(planogramaVigente);
	}

}
