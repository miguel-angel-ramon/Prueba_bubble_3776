package es.eroski.misumi.dao.ayudaFacing.iface;

import es.eroski.misumi.model.pda.ayudaFacing.RefAyudaFacingLista;

public interface ConsultaRefsAyudaFacingSIADao {

	/**
	 * Recupera las cinco primeras referencias de la lista de SIA.
	 * 
	 * @return
	 */
	public RefAyudaFacingLista getRefsAyudaFacing(Long centro, Long Referencia);
}
