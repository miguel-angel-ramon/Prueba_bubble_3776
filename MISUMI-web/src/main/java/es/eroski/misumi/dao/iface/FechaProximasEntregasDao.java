package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.FechaProximaEntregaRef;

public interface FechaProximasEntregasDao {
	public FechaProximaEntregaRef getFechaProximasEntregas(final Long codLoc, final Long codArt);
}
