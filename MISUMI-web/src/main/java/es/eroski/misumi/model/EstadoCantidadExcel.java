package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;



public class EstadoCantidadExcel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object estado;
	private Object catidad;
	
	
	public EstadoCantidadExcel() {
		super();

	}
	
	public EstadoCantidadExcel(Object estado,Object catidad) {
		super();
		this.estado = estado;
		this.catidad = catidad;

	}
	
	public Object getEstado() {
		return this.estado;
	}
	public void setEstado(Object estado) {
		this.estado = estado;
	}
	public Object getCantidad() {
		return this.catidad;
	}
	public void setCantidad(Object catidad) {
		this.catidad = catidad;
	}


}