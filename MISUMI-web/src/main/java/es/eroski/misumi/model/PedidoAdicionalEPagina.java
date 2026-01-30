package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.ui.Page;

public class PedidoAdicionalEPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<PedidoAdicionalE> datos;
    private List<CamposSeleccionadosE> listadoSeleccionados;
    private String esTecnico = "";
    private Long contadorEncargos;
    
    //Campos para control de errores
   	private Long codError;
   	private String descError;
	
	public PedidoAdicionalEPagina() {
		super();
	}

	public PedidoAdicionalEPagina(Page<PedidoAdicionalE> datos, List<CamposSeleccionadosE> listadoSeleccionados, String esTecnico,
			Long contadorEncargos, Long codError, String descError) {
		super();
		this.datos = datos;
		this.listadoSeleccionados = listadoSeleccionados;
		this.esTecnico = esTecnico;
		this.contadorEncargos = contadorEncargos;
		this.codError = codError;
		this.descError = descError;
	}

	public Page<PedidoAdicionalE> getDatos() {
		return this.datos;
	}

	public void setDatos(Page<PedidoAdicionalE> datos) {
		this.datos = datos;
	}

	public List<CamposSeleccionadosE> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(List<CamposSeleccionadosE> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}
	
	public String getEsTecnico() {
		return this.esTecnico;
	}

	public void setEsTecnico(String esTecnico) {
		this.esTecnico = esTecnico;
	}
	
	public Long getContadorEncargos() {
		return this.contadorEncargos;
	}

	public void setContadorEncargos(Long contadorEncargos) {
		this.contadorEncargos = contadorEncargos;
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