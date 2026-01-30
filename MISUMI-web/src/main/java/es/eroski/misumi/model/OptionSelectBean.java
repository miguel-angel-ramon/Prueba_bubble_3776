/**
 * 
 */
package es.eroski.misumi.model;

import java.io.Serializable;

/**
 * Representacion de clave y valor para cargar los datos de los options de los selects.
 * @author BICUGUAL
 */
@SuppressWarnings("serial")
public class OptionSelectBean implements Serializable {

	public String codigo;
	public String descripcion;
	
	public OptionSelectBean() {
		super();
	}
	
	public OptionSelectBean(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
