package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;



public class MensajeErrorExcel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object mensajeError;

	
	public MensajeErrorExcel() {
		super();

	}
	
	public MensajeErrorExcel(Object mensajeError) {
		super();
		this.mensajeError = mensajeError;

	}
	
	public Object getMensajeError() {
		return this.mensajeError;
	}
	public void setMensajeError(Object mensajeError) {
		this.mensajeError = mensajeError;
	}
	
	

}