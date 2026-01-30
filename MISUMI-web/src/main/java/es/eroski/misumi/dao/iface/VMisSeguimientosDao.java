package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.ReferenciasPedido;
import es.eroski.misumi.model.VMisSeguimientos;

public interface VMisSeguimientosDao  {

	public List<VMisSeguimientos> findAll(VMisSeguimientos vMisSeguimientos, boolean controlFechaActual) throws Exception ;
	public List<ReferenciasPedido> findAllReferenciasPedido(VMisSeguimientos vMisSeguimientos, List<Long> listaReferencias) throws Exception;
	public VMisSeguimientos recargaDatosTienda(VMisSeguimientos vMisSeguimientos) throws Exception;
}
