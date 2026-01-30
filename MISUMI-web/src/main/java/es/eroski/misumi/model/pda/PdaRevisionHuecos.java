package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaRevisionHuecos implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long codArt;
	private String area;
	private String error;
	private String actionSave;
	private String actionReset;
	private String actionLogin;
	private String actionVolver;
	private String actionConfirmarYes;
	private String actionConfirmarNo;
	private Boolean mostrarConf;
	private Boolean mostrarContenido;
	private String guardadoCorrecto;
	private String borradoCorrecto;
	private String gisaeCorrecto;
	private String refBorrada;
	private String descError;
	
	public Long getCodArt() {
		return codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getActionSave() {
		return actionSave;
	}
	public void setActionSave(String actionSave) {
		this.actionSave = actionSave;
	}
	public String getActionReset() {
		return actionReset;
	}
	public void setActionReset(String actionReset) {
		this.actionReset = actionReset;
	}
	public String getActionLogin() {
		return actionLogin;
	}
	public void setActionLogin(String actionLogin) {
		this.actionLogin = actionLogin;
	}
	public String getActionVolver() {
		return actionVolver;
	}
	public void setActionVolver(String actionVolver) {
		this.actionVolver = actionVolver;
	}
	public String getActionConfirmarYes() {
		return actionConfirmarYes;
	}
	public void setActionConfirmarYes(String actionConfirmarYes) {
		this.actionConfirmarYes = actionConfirmarYes;
	}
	public String getActionConfirmarNo() {
		return actionConfirmarNo;
	}
	public void setActionConfirmarNo(String actionConfirmarNo) {
		this.actionConfirmarNo = actionConfirmarNo;
	}
	public Boolean getMostrarConf() {
		return mostrarConf;
	}
	public void setMostrarConf(Boolean mostrarConf) {
		this.mostrarConf = mostrarConf;
	}
	public Boolean getMostrarContenido() {
		return mostrarContenido;
	}
	public void setMostrarContenido(Boolean mostrarContenido) {
		this.mostrarContenido = mostrarContenido;
	}
	public String getGuardadoCorrecto() {
		return guardadoCorrecto;
	}
	public void setGuardadoCorrecto(String guardadoCorrecto) {
		this.guardadoCorrecto = guardadoCorrecto;
	}
	public String getBorradoCorrecto() {
		return borradoCorrecto;
	}
	public void setBorradoCorrecto(String borradoCorrecto) {
		this.borradoCorrecto = borradoCorrecto;
	}
	public String getGisaeCorrecto() {
		return gisaeCorrecto;
	}
	public void setGisaeCorrecto(String gisaeCorrecto) {
		this.gisaeCorrecto = gisaeCorrecto;
	}
	public String getRefBorrada() {
		return refBorrada;
	}
	public void setRefBorrada(String refBorrada) {
		this.refBorrada = refBorrada;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	
	
	
}
