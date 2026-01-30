package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VMapaAprovFestivo;

public interface VMapaAprovFestivoDao  {

	public List<VMapaAprovFestivo> findAll(VMapaAprovFestivo vMapaAprovFestivo) throws Exception ;
	
	public Long count(VMapaAprovFestivo vMapaAprovFestivo) throws Exception ;

}
