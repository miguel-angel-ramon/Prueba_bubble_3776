package es.eroski.misumi.model;

import java.io.Serializable;

public class CamposModificadosSFM implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

    private int indice;
    private Double sfm;
	private Double coberturaSfm;
    private Double capacidad;
    private Long codError;
    private Long facingCentro;

    public CamposModificadosSFM() {
		super();
	}

	public CamposModificadosSFM(	int indice, 
			Double sfm, Double coberturaSfm,
			Double capacidad, Long codError,
			Long facingCentro) {
		super();
		
		this.indice = indice;
		this.sfm = sfm;
		this.coberturaSfm = coberturaSfm;
		this.capacidad = capacidad;
		this.codError = codError;
		this.facingCentro = facingCentro;
	
	}

	public int getIndice() {
		return this.indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public Double getSfm() {
		return this.sfm;
	}

	public void setSfm(Double sfm) {
		this.sfm = sfm;
	}

	public Double getCoberturaSfm() {
		return this.coberturaSfm;
	}

	public void setCoberturaSfm(Double coberturaSfm) {
		this.coberturaSfm = coberturaSfm;
	}
	
	public Double getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Double capacidad) {
		this.capacidad = capacidad;
	}
	
	public Long getCodError() {
		return this.codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}
	
	public Long getFacingCentro() {
		return this.facingCentro;
	}

	public void setFacingCentro(Long facingCentro) {
		this.facingCentro = facingCentro;
	}
}