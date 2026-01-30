package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VSicFPromociones;


public interface VSicFPromocionesService {

	  public List<VSicFPromociones> findAll(VSicFPromociones vSicFPromociones) throws Exception  ;
	  public List<VSicFPromociones> findAllGondola(VSicFPromociones vSicFPromociones) throws Exception  ;
		  
}
