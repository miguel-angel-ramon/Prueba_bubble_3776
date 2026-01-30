package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.ui.Page;

public class ExclusionVentasPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<ExclusionVentas> datos;
    private List<CamposSeleccionadosExclVenta> listadoSeleccionados;
    private List<CamposSeleccionadosExclVenta> listadoSeleccionadosNuevo;
    
  //Campos para control de errores
  	private Long codError;
  	private String descError;
	
  	public ExclusionVentasPagina() {
		super();
		// TODO Auto-generated constructor stub
	}
	
  	public ExclusionVentasPagina(Page<ExclusionVentas> datos,
			List<CamposSeleccionadosExclVenta> listadoSeleccionados,
			List<CamposSeleccionadosExclVenta> listadoSeleccionadosNuevo,
			Long codError, String descError) {
		super();
		this.datos = datos;
		this.listadoSeleccionados = listadoSeleccionados;
		this.codError = codError;
		this.descError = descError;
	}
	
  	public Page<ExclusionVentas> getDatos() {
		return this.datos;
	}
	
  	public void setDatos(Page<ExclusionVentas> datos) {
		this.datos = datos;
	}
	
  	public List<CamposSeleccionadosExclVenta> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}
	
  	public void setListadoSeleccionados(
			List<CamposSeleccionadosExclVenta> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}
	
  	public List<CamposSeleccionadosExclVenta> getListadoSeleccionadosNuevo() {
		return this.listadoSeleccionadosNuevo;
	}
	
  	public void setListadoSeleccionadosNuevo(
			List<CamposSeleccionadosExclVenta> listadoSeleccionadosNuevo) {
		this.listadoSeleccionadosNuevo = listadoSeleccionadosNuevo;
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