package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.EncargoCliente;
import es.eroski.misumi.model.EncargosClienteLista;

public interface GestionEncargosDao {

	public abstract EncargosClienteLista gestionEncargos(
			List<EncargoCliente> listaActualizacion) throws Exception;

}