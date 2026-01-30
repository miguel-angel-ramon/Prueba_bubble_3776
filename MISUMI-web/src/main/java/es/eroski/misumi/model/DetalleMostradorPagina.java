package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

import es.eroski.misumi.model.ui.Page;

public class DetalleMostradorPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<VMisDetalladoMostrador> datos;
    private String esTodoSIA = "";
    private Double eurosIniciales = null;
    private Double eurosFinales = null;
    private Double eurosPVPIniciales = null;
    private Double eurosPVPFinales = null;
    private Double cajasIniciales = null;
    private Double cajasFinales = null;
    private Date   fechaEntrega = null;
    
    private String mostrarEmpuje = "N";

	public DetalleMostradorPagina() {
		super();
	}

	public DetalleMostradorPagina(Page<VMisDetalladoMostrador> datos, String esTodoSIA) {
		super();
		this.datos = datos;	
		this.esTodoSIA = esTodoSIA;
	}

	public Page<VMisDetalladoMostrador> getDatos() {
		return this.datos;
	}

	public void setDatos(Page<VMisDetalladoMostrador> datos) {
		this.datos = datos;
	}
	
	public String getEsTodoSIA() {
		return this.esTodoSIA;
	}

	public void setEsTodoSIA(String esTodoSIA) {
		this.esTodoSIA = esTodoSIA;
	}
	
	public Double getEurosIniciales() {
		return eurosIniciales;
	}

	public void setEurosIniciales(Double eurosIniciales) {
		this.eurosIniciales = eurosIniciales;
	}

	public Double getEurosFinales() {
		return eurosFinales;
	}

	public void setEurosFinales(Double eurosFinales) {
		this.eurosFinales = eurosFinales;
	}
	
	public Double getEurosPVPIniciales() {
		return eurosPVPIniciales;
	}

	public void setEurosPVPIniciales(Double eurosPVPIniciales) {
		this.eurosPVPIniciales = eurosPVPIniciales;
	}

	public Double getEurosPVPFinales() {
		return eurosPVPFinales;
	}

	public void setEurosPVPFinales(Double eurosPVPFinales) {
		this.eurosPVPFinales = eurosPVPFinales;
	}

	public Double getCajasIniciales() {
		return cajasIniciales;
	}

	public void setCajasIniciales(Double cajasIniciales) {
		this.cajasIniciales = cajasIniciales;
	}

	public Double getCajasFinales() {
		return cajasFinales;
	}

	public void setCajasFinales(Double cajasFinales) {
		this.cajasFinales = cajasFinales;
	}

	public String getMostrarEmpuje() {
		return mostrarEmpuje;
	}

	public void setMostrarEmpuje(String mostrarEmpuje) {
		this.mostrarEmpuje = mostrarEmpuje;
	}
	
}