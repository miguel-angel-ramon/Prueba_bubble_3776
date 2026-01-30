package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

//Cada objeto de este tipo contiene una lista de Devoluciones con el mismo estado.
public class DevolucionEstado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long estado;
	private Long numeroRegistros;
	private List<Devolucion> listDevolucion;

	public DevolucionEstado(Long estado, Long numeroRegistros, List<Devolucion> listDevolucion) {
		super();
		this.estado = estado;
		this.numeroRegistros = numeroRegistros;
		this.listDevolucion = listDevolucion;
	}
	
	public DevolucionEstado() {
		super();
	}
	
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	public Long getNumeroRegistros() {
		return numeroRegistros;
	}
	public void setNumeroRegistros(Long numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}
	public List<Devolucion> getListDevolucion() {
		return listDevolucion;
	}
	public void setListDevolucion(List<Devolucion> listDevolucion) {
		this.listDevolucion = listDevolucion;
	}	
}
