package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.VPlanograma;

public interface VPlanogramaDao {

	 public List<VPlanograma> findAll(VPlanograma vPlanoframa) throws Exception;

	 public PlanogramaVigente findDatosVegalsa(PlanogramaVigente planogramaVigente);
	 
	 /**
	  * Obtener los datos de la IMC para centros Vegalsa.
	  * @param planogramaVigente
	  * @return
	  */
	 public PlanogramaVigente findDatosIMCVegalsa(PlanogramaVigente planogramaVigente);

	 public Boolean vieneDeSIA(Long codCentro, Long codArt);
}
