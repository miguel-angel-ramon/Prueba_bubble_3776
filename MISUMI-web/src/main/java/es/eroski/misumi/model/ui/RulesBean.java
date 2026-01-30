/**
 * 
 */
package es.eroski.misumi.model.ui;

import java.io.Serializable;

/**
 * Representacion de los datos para cada columna que viajan en el jqgrid cuando se realiza una busqueda mediante la toolbar del grid 
 * @author BICUGUAL
 *
 */
@SuppressWarnings("serial")
public class RulesBean implements Serializable {

	
	private String field;
	private String op;
	private String data;
	
	public RulesBean() {
		super();
	}
	
	public RulesBean(String field, String op, String data) {
		super();
		this.field = field;
		this.op = op;
		this.data = data;
	}

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
}