package es.eroski.misumi.service.iface;

import java.util.Date;
import java.util.List;

import es.eroski.misumi.model.VSurtidoTienda;


public interface VSurtidoTiendaService {

	  public List<VSurtidoTienda> findAll(VSurtidoTienda vSurtidoTienda) throws Exception;
		  
	  public VSurtidoTienda findOne(VSurtidoTienda vSurtidoTienda) throws Exception;
	  
	  public VSurtidoTienda findOneGama(VSurtidoTienda vSurtidoTienda) throws Exception;
	  
	  public VSurtidoTienda findOneVegalsa(VSurtidoTienda vSurtidoTienda) throws Exception;
	  
	  public Date obtenerFechaGeneracionSurtidoTienda(VSurtidoTienda vSurtidoTienda) throws Exception;
	  
	  public Long comprobarStockMayorACero(VSurtidoTienda vSurtidoTienda) throws Exception;
	  public Long comprobarStockMayorACeroCaprabo(VSurtidoTienda vSurtidoTienda) throws Exception;
	  
}
