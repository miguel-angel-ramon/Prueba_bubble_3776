package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Pagination;


public interface VAgruComerRefService {

	  public List<VAgruComerRef> findAll(VAgruComerRef vAgruComerRef, Pagination pagination) throws Exception  ;
		  
	
}
