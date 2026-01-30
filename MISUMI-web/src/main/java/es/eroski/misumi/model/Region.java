package es.eroski.misumi.model;

import java.io.Serializable;

public class Region implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
 
	private Long codRegion;
	private Long codArea;
	private Long codEnsena;
	private Long codNegocio;
	private String descripcion;
	
    public Region() {
		super();
	}

	public Region(Long codRegion, Long codArea, Long codEnsena, Long codNegocio,
			String descripcion) {
		super();
		this.codRegion = codRegion;
		this.codArea = codArea;
		this.codEnsena = codEnsena;
		this.codNegocio = codNegocio;
		this.descripcion = descripcion;
	}

	public Long getCodRegion() {
		return codRegion;
	}

	public void setCodRegion(Long codRegion) {
		this.codRegion = codRegion;
	}

	public Long getCodArea() {
		return codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public Long getCodEnsena() {
		return codEnsena;
	}

	public void setCodEnsena(Long codEnsena) {
		this.codEnsena = codEnsena;
	}

	public Long getCodNegocio() {
		return codNegocio;
	}

	public void setCodNegocio(Long codNegocio) {
		this.codNegocio = codNegocio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}