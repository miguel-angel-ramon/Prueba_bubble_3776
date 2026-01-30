package es.eroski.misumi.service.iface;


import es.eroski.misumi.model.PendientesRecibir;
import es.eroski.misumi.model.VRelacionArticulo;

public interface PendientesRecibirService {

	public PendientesRecibir find(PendientesRecibir pr) throws Exception  ;
	public PendientesRecibir find(PendientesRecibir pr, VRelacionArticulo vRelacionArticulo) throws Exception  ;
}
