package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Centro;



public interface VCentrosPlataformasService {

	public List<Centro> findAll(Centro centro) throws Exception  ;
		  
	public Centro findOne(Centro centro) throws Exception  ;

	List<Centro> findByCodDesc(String matcher) throws Exception;
	
	List<Centro> listZonaByRegion(Centro centro) throws Exception;
	
	public Boolean isLotesCentroActivo(Long codCentro) throws Exception;
}
