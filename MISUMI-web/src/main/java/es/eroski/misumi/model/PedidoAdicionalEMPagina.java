package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.ui.Page;

public class PedidoAdicionalEMPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<PedidoAdicionalEM> datos;
    private List<CamposSeleccionadosEM> listadoSeleccionados;
    private String esTecnico = "";
    private String esModificable = "";
    private String fechaMinima = "";
    private String fechaFin = "";
    private Long contadorEmpujes;
    
    //Campos para control de errores
   	private Long codError;
   	private String descError;
	
	public PedidoAdicionalEMPagina() {
		super();
	}

	public PedidoAdicionalEMPagina(Page<PedidoAdicionalEM> datos, List<CamposSeleccionadosEM> listadoSeleccionados, String esTecnico,
			String esModificable, String fechaMinima, String fechaFin, Long contadorEmpujes) {
		super();
		this.datos = datos;
		this.listadoSeleccionados = listadoSeleccionados;
		this.esTecnico = esTecnico;
		this.esModificable = esModificable;
		this.fechaMinima = fechaMinima;
		this.fechaFin = fechaFin;
		this.contadorEmpujes = contadorEmpujes;
	}

	public Page<PedidoAdicionalEM> getDatos() {
		return this.datos;
	}

	public void setDatos(Page<PedidoAdicionalEM> datos) {
		this.datos = datos;
	}

	public List<CamposSeleccionadosEM> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(List<CamposSeleccionadosEM> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}
	
	public String getEsTecnico() {
		return this.esTecnico;
	}

	public void setEsTecnico(String esTecnico) {
		this.esTecnico = esTecnico;
	}
	
	public String getEsModificable() {
		return this.esModificable;
	}

	public void setEsModificable(String esModificable) {
		this.esModificable = esModificable;
	}
	
	public String getFechaMinima() {
		return this.fechaMinima;
	}

	public void setFechaMinima(String fechaMinima) {
		this.fechaMinima = fechaMinima;
	}
	
	public String getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getContadorEmpujes() {
		return this.contadorEmpujes;
	}

	public void setContadorEmpujes(Long contadorEmpujes) {
		this.contadorEmpujes = contadorEmpujes;
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