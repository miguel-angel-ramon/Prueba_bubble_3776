package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.TMisMcgCaprabo;


public interface TMisMcgCapraboService {

	  public List<TMisMcgCaprabo> findAll(TMisMcgCaprabo tMisMcgCaprabo) throws Exception;	  
	  public TMisMcgCaprabo findOne(TMisMcgCaprabo tMisMcgCaprabo) throws Exception;
	  
	  
}
