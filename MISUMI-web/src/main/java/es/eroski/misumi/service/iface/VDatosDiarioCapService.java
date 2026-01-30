package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VDatosDiarioCap;


public interface VDatosDiarioCapService {

	  public List<VDatosDiarioCap> findAll(VDatosDiarioCap vDatosDiarioCap) throws Exception  ;
		  
	  public VDatosDiarioCap findOne(VDatosDiarioCap vDatosDiarioCap) throws Exception  ;
	  
}
