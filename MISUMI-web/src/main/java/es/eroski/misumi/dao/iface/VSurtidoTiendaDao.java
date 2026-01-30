package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VSurtidoTienda;

public interface VSurtidoTiendaDao  {

	public List<VSurtidoTienda> findAll(VSurtidoTienda vSurtidoTienda) throws Exception ;
	public List<VSurtidoTienda> findAllGama(VSurtidoTienda vSurtidoTienda) throws Exception ;
	 public List<VSurtidoTienda> findAllVegalsa(VSurtidoTienda vSurtidoTienda) throws Exception ;
	public List<VSurtidoTienda> obtenerFechaGeneracionSurtidoTienda(VSurtidoTienda vSurtidoTienda) throws Exception ;
	public Long comprobarStockMayorACero(VSurtidoTienda vSurtidoTienda) throws Exception ;
	public Long comprobarStockMayorACeroCaprabo(VSurtidoTienda vSurtidoTienda) throws Exception ;

}
