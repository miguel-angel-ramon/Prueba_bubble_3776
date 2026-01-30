package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.PescaPedirHoy;

public interface VPescaMostradorSIADao  {

	public PescaPedirHoy findPescaPedirHoy(PescaPedirHoy pescaPedirHoy) throws Exception;
}
