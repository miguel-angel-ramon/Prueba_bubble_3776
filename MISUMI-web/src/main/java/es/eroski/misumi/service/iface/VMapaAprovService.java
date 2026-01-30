package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VMapaAprov;


public interface VMapaAprovService {

	  public List<VMapaAprov> findAll(VMapaAprov vMapaAprov) throws Exception  ;
	  public Long count(VMapaAprov vMapaAprov) throws Exception  ;
	  
	  public VMapaAprov findOne(VMapaAprov vMapaAprov) throws Exception  ;
}
