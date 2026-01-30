package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.ReferenciasPedido;
import es.eroski.misumi.model.VMisSeguimientos;



public interface VMisSeguimientosService {

	public List<VMisSeguimientos> findAll(VMisSeguimientos vMisSeguimientos, boolean controlFechaActual) throws Exception ;
	public List<ReferenciasPedido> findAllReferenciasPedido(VMisSeguimientos vMisSeguimientos) throws Exception ;
	public VMisSeguimientos recargaDatosTienda(VMisSeguimientos vMisSeguimientos) throws Exception ;
}
