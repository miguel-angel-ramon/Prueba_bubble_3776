package es.eroski.misumi.model;

import java.util.Date;

public class CalendarioPendienteValidar {
	private Long pCodError;
	private String pDescError;
	private Date fechaLimite;
	private String fechaLimiteStr;
	
	public CalendarioPendienteValidar(Long pCodError, String pDescError, Date fechaLimite, String fechaLimiteStr) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.fechaLimite = fechaLimite;
		this.fechaLimiteStr = fechaLimiteStr;
	}
	
	public Long getpCodError() {
		return pCodError;
	}
	public void setpCodError(Long pCodError) {
		this.pCodError = pCodError;
	}
	public String getpDescError() {
		return pDescError;
	}
	public void setpDescError(String pDescError) {
		this.pDescError = pDescError;
	}
	public Date getFechaLimite() {
		return fechaLimite;
	}
	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	public String getFechaLimiteStr() {
		return fechaLimiteStr;
	}
	public void setFechaLimiteStr(String fechaLimiteStr) {
		this.fechaLimiteStr = fechaLimiteStr;
	}
}
