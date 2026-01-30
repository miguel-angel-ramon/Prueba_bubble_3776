package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaSeleccionStock implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codArtOrig;
	private Long codArt;
	private Long codArtSel;
	private String MMC;
	private String origen;
	private String origenInventario;
	private String origenGISAE;
	private String noGuardar;
	private String seccion;
	
	
	public Long getCodArtOrig() {
		return codArtOrig;
	}
	public void setCodArtOrig(Long codArtOrig) {
		this.codArtOrig = codArtOrig;
	}
	public Long getCodArt() {
		return this.codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public Long getCodArtSel() {
		return this.codArtSel;
	}
	public void setCodArtSel(Long codArtSel) {
		this.codArtSel = codArtSel;
	}
	public String getMMC() {
		return this.MMC;
	}
	public void setMMC(String MMC) {
		this.MMC = MMC;
	}
	public String getOrigen() {
		return this.origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getOrigenInventario() {
		return this.origenInventario;
	}
	public void setOrigenInventario(String origenInventario) {
		this.origenInventario = origenInventario;
	}
	public String getOrigenGISAE() {
		return origenGISAE;
	}
	public void setOrigenGISAE(String origenGISAE) {
		this.origenGISAE = origenGISAE;
	}
	public String getNoGuardar() {
		return noGuardar;
	}
	public void setNoGuardar(String noGuardar) {
		this.noGuardar = noGuardar;
	}
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
}
