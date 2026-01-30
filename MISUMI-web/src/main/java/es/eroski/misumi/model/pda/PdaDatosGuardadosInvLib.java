package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaDatosGuardadosInvLib implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer totalRegistros;
	private Integer totalGuardadosOk;
	private Integer totalGuardadosError;
	private Integer totalGuardadosAviso;
	private String origenInventario;
	
	public Integer getTotalRegistros() {
		return this.totalRegistros;
	}
	
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	
	public Integer getTotalGuardadosOk() {
		return this.totalGuardadosOk;
	}
	
	public void setTotalGuardadosOk(Integer totalGuardadosOk) {
		this.totalGuardadosOk = totalGuardadosOk;
	}
	
	public Integer getTotalGuardadosError() {
		return this.totalGuardadosError;
	}
	
	public void setTotalGuardadosError(Integer totalGuardadosError) {
		this.totalGuardadosError = totalGuardadosError;
	}
	
	public Integer getTotalGuardadosAviso() {
		return this.totalGuardadosAviso;
	}
	
	public void setTotalGuardadosAviso(Integer totalGuardadosAviso) {
		this.totalGuardadosAviso = totalGuardadosAviso;
	}
	
	public String getOrigenInventario() {
		return this.origenInventario;
	}
	
	public void setOrigenInventario(String origenInventario) {
		this.origenInventario = origenInventario;
	}
}
