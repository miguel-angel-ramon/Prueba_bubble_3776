package es.eroski.misumi.model;

import java.io.Serializable;

public class DetalladoContadores implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long contadorSinOferta;
	private Long contadorOferta;
	
	
	//Campos para control de errores
   	private Long codError;
   	private String descError;
   	
   	//Campo para el valor por defecto combo Validar Cantidad Extra
   	private String defaultDescription;
	
	
	public DetalladoContadores() {
	    super();
	    this.contadorSinOferta = new Long(0);
	    this.contadorOferta = new Long(0);
	  
	}

	public DetalladoContadores(Long contadorSinOferta, Long contadorOferta, Long codError, String descError) {
		super();
		this.contadorSinOferta = contadorSinOferta;
		this.contadorOferta = contadorOferta;
		
		this.codError = codError;
		this.descError = descError;
	}

	public Long getContadorSinOferta() {
		return this.contadorSinOferta;
	}

	public void setContadorSinOferta(Long contadorSinOferta) {
		this.contadorSinOferta = contadorSinOferta;
	}

	public Long getContadorOferta() {
		return this.contadorOferta;
	}

	public void setContadorOferta(Long contadorOferta) {
		this.contadorOferta = contadorOferta;
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

	

	public String getDefaultDescription() {
		return defaultDescription;
	}

	public void setDefaultDescription(String defaultDescription) {
		this.defaultDescription = defaultDescription;
	}

}