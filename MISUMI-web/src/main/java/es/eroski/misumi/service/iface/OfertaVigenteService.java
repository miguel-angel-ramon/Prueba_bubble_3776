package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.OfertaVigente;


public interface OfertaVigenteService {

	  public List<OfertaVigente> findAll(OfertaVigente ofertaVigente) throws Exception  ;
		  
	  public OfertaVigente findOne(OfertaVigente ofertaVigente) throws Exception  ;
	  
	  public OfertaPVP recuperarAnnoOferta(OfertaPVP ofertaPVP) throws Exception ;
}
