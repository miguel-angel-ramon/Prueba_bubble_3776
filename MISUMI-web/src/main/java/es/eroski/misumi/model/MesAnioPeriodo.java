package es.eroski.misumi.model;

import java.util.List;

public class MesAnioPeriodo {
	//Atributo
	private String mes;
	private String anio;
	private String periodo;
	private String periodoIni;
	private String periodoFin;

	//Constructor
	public MesAnioPeriodo() {
		super();
	}		

	public MesAnioPeriodo(String mes, String anio, String periodo, String periodoIni, String periodoFin) {
		super();
		this.mes = mes;
		this.anio = anio;
		this.periodo = periodo;
		this.periodoIni = periodoIni;
		this.periodoFin = periodoFin;
	}

	//Setter && Getter
	public String getMes() {
		return mes;
	}
	
	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getPeriodoIni() {
		return periodoIni;
	}

	public void setPeriodoIni(String periodoIni) {
		this.periodoIni = periodoIni;
	}

	public String getPeriodoFin() {
		return periodoFin;
	}

	public void setPeriodoFin(String periodoFin) {
		this.periodoFin = periodoFin;
	}
}
