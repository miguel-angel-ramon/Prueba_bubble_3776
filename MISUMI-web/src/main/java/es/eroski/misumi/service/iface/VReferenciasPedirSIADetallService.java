package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VReferenciasPedirSIADetall;


public interface VReferenciasPedirSIADetallService {

	  public List<VReferenciasPedirSIADetall> findAll(VReferenciasPedirSIADetall vReferenciasPedirSIADetall) throws Exception  ;
		  
	  public VReferenciasPedirSIADetall findOne(VReferenciasPedirSIADetall vReferenciasPedirSIADetall) throws Exception  ;
	  
	  public boolean tratarReferenciaSIA(VReferenciasPedirSIADetall vReferenciasPedirSIADetall) throws Exception  ;
	 
}
