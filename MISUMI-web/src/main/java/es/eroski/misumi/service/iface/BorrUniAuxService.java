package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.BorrUniAux;
import es.eroski.misumi.model.ui.Pagination;


public interface BorrUniAuxService {

	  public List<BorrUniAux> findAll(BorrUniAux borrUniAux, Pagination pagination) throws Exception  ;
		  
	
}
