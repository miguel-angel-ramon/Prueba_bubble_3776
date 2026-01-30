package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VReferenciasPedirSIA;


public interface VReferenciasPedirSIAService {

	  public List<VReferenciasPedirSIA> findAll(VReferenciasPedirSIA vReferenciasPedirSIA) throws Exception  ;
		  
	  public VReferenciasPedirSIA findOne(VReferenciasPedirSIA vReferenciasPedirSIA) throws Exception  ;
	  
	  public boolean tratarReferenciaSIA(VReferenciasPedirSIA vReferenciasPedirSIA) throws Exception  ;
	 
}
