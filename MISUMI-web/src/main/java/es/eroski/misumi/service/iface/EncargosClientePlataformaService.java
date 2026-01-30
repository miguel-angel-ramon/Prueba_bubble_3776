package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.EncargosClientePlataforma;
import es.eroski.misumi.model.EncargosClientePlataformaLista;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.ValidarReferenciaEncargo;
import es.eroski.misumi.model.ui.Pagination;

public interface EncargosClientePlataformaService {
	
	public abstract EncargosClientePlataformaLista cargarReferencias(EncargosClientePlataforma encargosCliente) throws Exception;

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
	
	public abstract ValidarReferenciaEncargo validarReferencia(ValidarReferenciaEncargo validarReferenciaEncargo, String codUser) throws Exception;
	
	public abstract EncargosClienteLista insertarReferencia(PedidoAdicionalCompleto pedidoAdicional, String codUser) throws Exception;
	
	public abstract Boolean comprobarReferencia(EncargosClientePlataforma encargosCliente) throws Exception;

}