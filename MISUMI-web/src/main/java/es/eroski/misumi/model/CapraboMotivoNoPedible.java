package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class CapraboMotivoNoPedible implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<CapraboMotivoNoPedibleCentroArt> datos;
    private Long estado;
	private String descEstado;
	
	//Campos para búsqueda en procedimientos
	private Long codLocBusqueda;
	private Long codArticuloBusqueda;
	private String tipoMovimientoBusqueda;
	
	public CapraboMotivoNoPedible() {
		super();
	}

	public CapraboMotivoNoPedible(List<CapraboMotivoNoPedibleCentroArt> datos, Long estado, String descEstado) {
		super();
		this.datos = datos;
		this.estado = estado;
		this.descEstado = descEstado;
	}
	
	public List<CapraboMotivoNoPedibleCentroArt> getDatos() {
		return this.datos;
	}
	
	public void setDatos(List<CapraboMotivoNoPedibleCentroArt> datos) {
		this.datos = datos;
	}
	
	public Long getEstado() {
		return this.estado;
	}
	
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	public String getDescEstado() {
		return this.descEstado;
	}
	
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

	public Long getCodLocBusqueda() {
		return this.codLocBusqueda;
	}

	public void setCodLocBusqueda(Long codLocBusqueda) {
		this.codLocBusqueda = codLocBusqueda;
	}

	public Long getCodArticuloBusqueda() {
		return this.codArticuloBusqueda;
	}

	public void setCodArticuloBusqueda(Long codArticuloBusqueda) {
		this.codArticuloBusqueda = codArticuloBusqueda;
	}

	public String getTipoMovimientoBusqueda() {
		return this.tipoMovimientoBusqueda;
	}

	public void setTipoMovimientoBusqueda(String tipoMovimientoBusqueda) {
		this.tipoMovimientoBusqueda = tipoMovimientoBusqueda;
	}
	
	
}
