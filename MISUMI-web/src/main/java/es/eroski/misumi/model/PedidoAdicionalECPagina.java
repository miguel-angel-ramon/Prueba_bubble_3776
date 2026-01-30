package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.ui.Page;

public class PedidoAdicionalECPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<PedidoAdicionalEC> datos;
    private List<CamposSeleccionadosEC> listadoSeleccionados;
    private String esModificable = "";
    private Long contadorEncargosCliente;
    
    //Campos para control de errores
   	private Long codError;
   	private String descError;
	
	public PedidoAdicionalECPagina() {
		super();
	}

	public PedidoAdicionalECPagina(Page<PedidoAdicionalEC> datos, List<CamposSeleccionadosEC> listadoSeleccionados,
			String esModificable, Long contadorEncargosCliente) {
		super();
		this.datos = datos;
		this.listadoSeleccionados = listadoSeleccionados;
		this.esModificable = esModificable;
		this.contadorEncargosCliente = contadorEncargosCliente;
	}

	public Page<PedidoAdicionalEC> getDatos() {
		return this.datos;
	}

	public void setDatos(Page<PedidoAdicionalEC> datos) {
		this.datos = datos;
	}

	public List<CamposSeleccionadosEC> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(List<CamposSeleccionadosEC> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}
	
	public String getEsModificable() {
		return this.esModificable;
	}

	public void setEsModificable(String esModificable) {
		this.esModificable = esModificable;
	}
	
	public Long getContadorEncargosCliente() {
		return this.contadorEncargosCliente;
	}

	public void setContadorEncargosCliente(Long contadorEncargosCliente) {
		this.contadorEncargosCliente = contadorEncargosCliente;
	}
	
	public Long getCodError() {
		return this.codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return this.descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

}