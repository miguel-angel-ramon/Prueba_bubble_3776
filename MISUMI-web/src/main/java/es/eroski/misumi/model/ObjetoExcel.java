package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;



public class ObjetoExcel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object valor;
	
	
	public ObjetoExcel() {
		super();

	}
	
	public ObjetoExcel(Object valor) {
		super();
		this.valor = valor;


	}
	
	public Object getValor() {
		return this.valor;
	}
	public void setValor(Object valor) {
		this.valor = valor;
	}

	

}