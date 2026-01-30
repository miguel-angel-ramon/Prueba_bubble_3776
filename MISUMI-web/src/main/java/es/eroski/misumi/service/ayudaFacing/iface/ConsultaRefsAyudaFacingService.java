package es.eroski.misumi.service.ayudaFacing.iface;

import es.eroski.misumi.model.pda.ayudaFacing.RefAyudaFacingLista;

public interface ConsultaRefsAyudaFacingService {

	/**
	 * Obtener los datos que devuelve el procedimiento PK_APR_MISUMI.P_QUITAR_FACING.
	 * 
	 * @param centro
	 * @param referencia
	 * @return
	 */
	public RefAyudaFacingLista obtenerListaRefsAyudaFacing(Long centro, Long referencia);
	
}
