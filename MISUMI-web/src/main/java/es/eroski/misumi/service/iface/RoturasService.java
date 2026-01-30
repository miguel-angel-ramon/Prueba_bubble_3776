package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Roturas;


public interface RoturasService {

	public List<Roturas> findAll(Roturas roturas) throws Exception  ;
	public Long count(Roturas roturas) throws Exception  ;
	  
	public Roturas findOne(Roturas roturas) throws Exception  ;
}
