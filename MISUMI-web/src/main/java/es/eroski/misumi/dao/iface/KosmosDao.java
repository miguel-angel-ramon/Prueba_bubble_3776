package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.OfertaPVP;

public interface KosmosDao {

	public OfertaPVP obtenerDatosPVP(OfertaPVP ofertaPVP) throws Exception;

	public Double obtenerCosto(OfertaPVP ofertaPVP) throws Exception;

}
