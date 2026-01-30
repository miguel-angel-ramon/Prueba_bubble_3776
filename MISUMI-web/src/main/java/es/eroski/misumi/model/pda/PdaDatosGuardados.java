package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaDatosGuardados implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer totalRegistros;
	private Integer totalGuardados;
	private String origenPantalla;
	
	public Integer getTotalRegistros() {
		return this.totalRegistros;
	}
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	public Integer getTotalGuardados() {
		return this.totalGuardados;
	}
	public void setTotalGuardados(Integer totalGuardados) {
		this.totalGuardados = totalGuardados;
	}
	public String getOrigenPantalla() {
		return this.origenPantalla;
	}
	public void setOrigenPantalla(String origenPantalla) {
		this.origenPantalla = origenPantalla;
	}
}
