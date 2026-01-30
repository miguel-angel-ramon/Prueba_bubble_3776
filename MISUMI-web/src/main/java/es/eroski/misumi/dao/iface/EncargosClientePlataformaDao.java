package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.EncargosClientePlataforma;
import es.eroski.misumi.model.ui.Pagination;

public interface EncargosClientePlataformaDao {

	public abstract List<String> getSecciones(
			EncargosClientePlataforma encargosCliente) throws Exception;

	public abstract List<String> getCategorias(
			EncargosClientePlataforma encargosCliente) throws Exception;

	public abstract List<EncargosClientePlataforma> getReferencias(
			EncargosClientePlataforma encargosCliente, Pagination pagination) throws Exception;
	
	public abstract Long getReferenciasCount(EncargosClientePlataforma encargosCliente) throws Exception;

	public abstract void insertReferencias(
			List<EncargosClientePlataforma> listaReferencias) throws Exception;

	public abstract void deleteReferencias(
			EncargosClientePlataforma encargosCliente) throws Exception;

}