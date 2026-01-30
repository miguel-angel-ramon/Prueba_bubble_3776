package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaDatosPopupFechaActivacion implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private Long codArtRel;
	private String mostrarFFPP;
	private String origenGISAE;
	private String fechaActivacion;
	private String origen;
	
	public PdaDatosPopupFechaActivacion() {
	    super();
	}

	public PdaDatosPopupFechaActivacion(Long codArt, Long codArtRel, String mostrarFFPP, String origenGISAE, String fechaActivacion, String origen) {
	    super();
	    this.codArt=codArt;
	    this.codArtRel=codArtRel;
	    this.mostrarFFPP=mostrarFFPP;
	    this.origenGISAE=origenGISAE;
	    this.fechaActivacion=fechaActivacion;
	    this.origen=origen;
	}
	
	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	
	public Long getCodArtRel() {
		return codArtRel;
	}

	public void setCodArtRel(Long codArtRel) {
		this.codArtRel = codArtRel;
	}

	public String getMostrarFFPP() {
		return mostrarFFPP;
	}

	public void setMostrarFFPP(String mostrarFFPP) {
		this.mostrarFFPP = mostrarFFPP;
	}

	public String getOrigenGISAE() {
		return origenGISAE;
	}

	public void setOrigenGISAE(String origenGISAE) {
		this.origenGISAE = origenGISAE;
	}

	public String getFechaActivacion() {
		return this.fechaActivacion;
	}

	public void setFechaActivacion(String fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
}