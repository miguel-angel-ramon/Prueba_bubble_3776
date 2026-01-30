package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.VRelacionArticulo;


public interface VRelacionArticuloService {

	  public List<VRelacionArticulo> findAll(VRelacionArticulo vRelacionArticulo) throws Exception  ;
		  
	  public VRelacionArticulo findOne(VRelacionArticulo vRelacionArticulo) throws Exception  ;
	  
	  public ReferenciasCentro obtenerFfppActivaOUnitaria(ReferenciasCentro referenciasCentro, boolean tratamientoVegalsa) throws Exception;
}
