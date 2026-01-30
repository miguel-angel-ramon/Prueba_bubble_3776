package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VOfertaPaAyuda;


public interface VOfertaPaAyudaService {

	  public List<VOfertaPaAyuda> findCountNOVigentes(VOfertaPaAyuda vOfertaPaAyuda, int vRow) throws Exception  ;
	  
	  public VOfertaPaAyuda findOfertaPasadaMasReciente(VOfertaPaAyuda vOfertaPaAyuda) throws Exception;
}
