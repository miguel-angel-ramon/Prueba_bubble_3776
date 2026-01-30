package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.FechaProximaEntregaRef;


public interface FechaProximasEntregasService {
	public FechaProximaEntregaRef getFechaProximasEntregas(final Long codLoc, final Long codArt);
}
