package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaVentaAnticipada implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long codArt;
	private String descArt;
	private String cantidad;
	private String fechaGen;
	private String fecha;
	private String flgEnvio;
	private Boolean existe;
	private String actionSave;
	private String actionReset;
	private String esError;
	private String guardadoCorrecto;
	
	public Long getCodArt() {
		return this.codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public String getDescArt() {
		return descArt;
	}
	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}
	public String getCantidad() {
		return this.cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getFechaGen() {
		return this.fechaGen;
	}
	public void setFechaGen(String fechaGen) {
		this.fechaGen = fechaGen;
	}
	public String getFecha() {
		return this.fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFlgEnvio() {
		return this.flgEnvio;
	}
	public void setFlgEnvio(String flgEnvio) {
		this.flgEnvio = flgEnvio;
	}
	public Boolean getExiste() {
		return this.existe;
	}
	public void setExiste(Boolean existe) {
		this.existe = existe;
	}
	public String getActionSave() {
		return this.actionSave;
	}
	public void setActionSave(String actionSave) {
		this.actionSave = actionSave;
	}
	public String getActionReset() {
		return this.actionReset;
	}
	public void setActionReset(String actionReset) {
		this.actionReset = actionReset;
	}
	public String getEsError() {
		return this.esError;
	}
	public void setEsError(String esError) {
		this.esError = esError;
	}
	public String getGuardadoCorrecto() {
		return this.guardadoCorrecto;
	}
	public void setGuardadoCorrecto(String guardadoCorrecto) {
		this.guardadoCorrecto = guardadoCorrecto;
	}
	
	public String getDescArtConCodigo() {
		return this.codArt + "-" + this.descArt;
	}
	
}
