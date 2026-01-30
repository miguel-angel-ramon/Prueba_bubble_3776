package es.eroski.misumi.model;

import es.eroski.misumi.model.ui.Page;

public class EntradaActualizada {
	private Page<TEntradaLinea> datos;
	private int countGuardados;
	private int countError;		
	private String descError;
	private Long codError;
	
	public EntradaActualizada() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntradaActualizada(Page<TEntradaLinea> datos, int countGuardados, int countError, String descError,
			Long codError) {
		super();
		this.datos = datos;
		this.countGuardados = countGuardados;
		this.countError = countError;
		this.descError = descError;
		this.codError = codError;
	}
	
	public Page<TEntradaLinea> getDatos() {
		return datos;
	}
	public void setDatos(Page<TEntradaLinea> datos) {
		this.datos = datos;
	}
	public int getCountGuardados() {
		return countGuardados;
	}
	public void setCountGuardados(int countGuardados) {
		this.countGuardados = countGuardados;
	}
	public int getCountError() {
		return countError;
	}
	public void setCountError(int countError) {
		this.countError = countError;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
}
