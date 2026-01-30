package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.ui.Page;

public class DetallePedidoPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<DetallePedido> datos;
    private String esTodoSIA = "";
    private Double eurosIniciales = null;
    private Double eurosFinales = null;
    private Double cajasIniciales = null;
    private Double cajasFinales = null;
    private String mostrarEmpuje = "N";
   
	


	public DetallePedidoPagina() {
		super();
	}

	public DetallePedidoPagina(Page<DetallePedido> datos, String esTodoSIA) {
		super();
		this.datos = datos;	
		this.esTodoSIA = esTodoSIA;
		
	}

	public Page<DetallePedido> getDatos() {
		return this.datos;
	}

	public void setDatos(Page<DetallePedido> datos) {
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