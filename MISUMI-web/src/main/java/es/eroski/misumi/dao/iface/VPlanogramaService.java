package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.VPlanograma;

public interface VPlanogramaService {

	 public List<VPlanograma> findAll(VPlanograma vPlanoframa) throws Exception;
	 public VPlanograma findOne(VPlanograma vPlanoframa) throws Exception;
	 public VPlanograma findTipoP(VPlanograma vPlanograma) throws Exception;
	 public PlanogramaVigente findDatosVegalsa(PlanogramaVigente planogramaVigente) throws Exception;
	 public Boolean vieneDeSIA(Long codCentro, Long codArt) throws Exception;
}
