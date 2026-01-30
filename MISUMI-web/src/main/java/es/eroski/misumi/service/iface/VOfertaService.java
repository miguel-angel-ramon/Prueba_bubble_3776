package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VOferta;


public interface VOfertaService {

	  public List<VOferta> findAll(VOferta vOferta) throws Exception  ;
		  
	  public VOferta findOne(VOferta vOferta) throws Exception  ;
}
