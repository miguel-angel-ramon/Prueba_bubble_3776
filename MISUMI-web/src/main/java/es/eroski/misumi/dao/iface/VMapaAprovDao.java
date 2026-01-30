package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VMapaAprov;

public interface VMapaAprovDao  {

	public List<VMapaAprov> findAll(VMapaAprov vMapaAprov) throws Exception ;
	
	public Long count(VMapaAprov vMapaAprov) throws Exception ;

}
