package es.eroski.misumi.model;

import java.io.Serializable;

public class DetalladoMostradorRedondeo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArticulo;
	private Long unidPropuestasFlModif; //Cantidad
	private Double unidCaja;
	private Long codError;
	private String descError;
	

	public DetalladoMostradorRedondeo() {
		super();
	}

	public DetalladoMostradorRedondeo(Long codArticulo,  Long unidPropuestasFlModif, Double unidCaja, Long codError, String descError) {
		
		super();

		this.codArticulo=codArticulo;
		this.unidPropuestasFlModif=unidPropuestasFlModif;  
		this.unidCaja=unidCaja;    
		this.codError=codError;
		this.descError=descError;
	
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getUnidPropuestasFlModif() {
		return unidPropuestasFlModif;
	}

	public void setUnidPropuestasFlModif(Long unidPropuestasFlModif) {
		this.unidPropuestasFlModif = unidPropuestasFlModif;
	}

	public Double getUnidCaja() {
		return unidCaja;
	}

	public void setUnidCaja(Double unidCaja) {
		this.unidCaja = unidCaja;
	}

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}


	
	
	
	
}