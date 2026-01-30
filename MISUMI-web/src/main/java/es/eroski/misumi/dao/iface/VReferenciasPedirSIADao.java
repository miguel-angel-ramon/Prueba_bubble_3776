package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VReferenciasPedirSIA;

public interface VReferenciasPedirSIADao  {

	public List<VReferenciasPedirSIA> findAll(VReferenciasPedirSIA vReferenciasPedirSIA) throws Exception ;
	
}
