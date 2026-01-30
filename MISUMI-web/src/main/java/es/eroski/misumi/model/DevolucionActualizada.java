package es.eroski.misumi.model;

import es.eroski.misumi.model.ui.Page;

public class DevolucionActualizada {
	private Page<TDevolucionLinea> datos;
	private int countGuardados;
	private int countError;		
	private String descErrorPLSQL;
	private Long codErrorPLSQL;
	
	public DevolucionActualizada() {
		super();
	}

	public DevolucionActualizada(Page<TDevolucionLinea> datos, int countGuardados, int countError) {
		super();
		this.datos = datos;
		this.countGuardados = countGuardados;
		this.countError = countError;
	}
	
	public DevolucionActualizada(Page<TDevolucionLinea> datos, int countGuardados, int countError,
			String descErrorPLSQL, Long codErrorPLSQL) {
		super();
		this.datos = datos;
		this.countGuardados = countGuardados;
		this.countError = countError;
		this.descErrorPLSQL = descErrorPLSQL;
		this.codErrorPLSQL = codErrorPLSQL;
	}

	public Page<TDevolucionLinea> getDatos() {
		return datos;
	}
	public void setDatos(Page<TDevolucionLinea> datos) {
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

	public String getDescErrorPLSQL() {
		return descErrorPLSQL;
	}

	public void setDescErrorPLSQL(String descErrorPLSQL) {
		this.descErrorPLSQL = descErrorPLSQL;
	}

	public Long getCodErrorPLSQL() {
		return codErrorPLSQL;
	}

	public void setCodErrorPLSQL(Long codErrorPLSQL) {
		this.codErrorPLSQL = codErrorPLSQL;
	}			
	
}
