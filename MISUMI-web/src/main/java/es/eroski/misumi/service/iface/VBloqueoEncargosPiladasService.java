package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.ui.Pagination;


public interface VBloqueoEncargosPiladasService {

	  public List<VBloqueoEncargosPiladas> findAll(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception  ;
	  
	  public List<VBloqueoEncargosPiladas> findAllPaginate(VBloqueoEncargosPiladas vBloqueoEncargosPiladas, Pagination pagination) throws Exception ;
	  
	  public List<VBloqueoEncargosPiladas> findMotivosRefBloqueada(VBloqueoEncargosPiladas vBloqueoEncargosPiladas, Pagination pagination) throws Exception ;
	  
	  public Long findMotivosRefBloqueadaCont(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception;
	  
	  public Long registrosRefBloqueadaCont(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception;
	  
	  public VBloqueoEncargosPiladas getBloqueoFecha(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception;
		  
}
