package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VReferenciasPedirSIADetall;

public interface VReferenciasPedirSIADetallDao  {

	public List<VReferenciasPedirSIADetall> findAll(VReferenciasPedirSIADetall vReferenciasPedirSIADetall) throws Exception ;
	
}
