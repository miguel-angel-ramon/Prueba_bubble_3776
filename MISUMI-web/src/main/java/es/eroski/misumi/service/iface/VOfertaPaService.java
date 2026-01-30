package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VOfertaPa;


public interface VOfertaPaService {

	  public List<VOfertaPa> findAllVigentes(VOfertaPa vOfertaPa) throws Exception  ;
	  
	  public List<VOfertaPa> findAllVigentesCentro(VOfertaPa vOfertaPa) throws Exception;
		  
	  public VOfertaPa findOneVigente(VOfertaPa vOfertaPa) throws Exception  ;
	  
	  public List<VOfertaPa> findCountNOVigentes(VOfertaPa vOfertaPa, int vRow) throws Exception  ;

}
