package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.ValidarReferenciaEncargo;

public interface ValidarReferenciaEncargosDao {

	public abstract ValidarReferenciaEncargo validarReferenciaEncargos(
			ValidarReferenciaEncargo validarReferenciaEncargo
			) throws Exception;

}