package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Roturas;

public interface RoturasDao  {

	public List<Roturas> findAll(Roturas roturas) throws Exception ;
	
	public Long count(Roturas roturas) throws Exception ;

}
