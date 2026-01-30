package es.eroski.misumi.model;

import java.io.Serializable;

public class CapraboMotivoNoPedibleMotivo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tipo;
	private String descripcion;
	private String accion;

	public CapraboMotivoNoPedibleMotivo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CapraboMotivoNoPedibleMotivo(String tipo, String descripcion, String accion) {
		super();
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.accion = accion;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getAccion() {
		return this.accion;
	}
	
	public void setAccion(String accion) {
		this.accion = accion;
	}
}
