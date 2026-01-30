package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.VPlanogramasPiladas;

public interface VPlanogramasPiladasDao {

	public abstract Long findDatosCabecera(
			VPlanogramasPiladas vPlanogramasPiladas) throws Exception;

}