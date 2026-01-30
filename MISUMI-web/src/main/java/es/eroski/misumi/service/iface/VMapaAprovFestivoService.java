package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VMapaAprovFestivo;


public interface VMapaAprovFestivoService {

	  public List<VMapaAprovFestivo> findAll(VMapaAprovFestivo vMapaAprovFestivo) throws Exception  ;
	  public Long count(VMapaAprovFestivo vMapaAprovFestivo) throws Exception  ;
		  
	  public VMapaAprovFestivo findOne(VMapaAprovFestivo vMapaAprovFestivo) throws Exception  ;
}
