package es.eroski.misumi.model;

import java.io.Serializable;

public class CamposModificadosNO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codArticulo;
	private String fechaInicio;
	private String fechaFin;
	private String fecha2;
	private String fecha3;
	private String fechaPilada;
	private Double cantidad1;
	private Double cantidad2;
	private Double cantidad3;
	private Double implInicial;
	private Double implFinal;
    private Long codError;
    private String descError;
	
    public CamposModificadosNO() {
		super();
	}
    
	public CamposModificadosNO(Long codArticulo, String fechaInicio, String fechaFin,
			String fecha2, String fecha3, String fechaPilada,
			Double cantidad1, Double cantidad2, Double cantidad3,
			Double implInicial, Double implFinal, Long codError, String descError) {
		super();
		this.codArticulo = codArticulo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fecha2 = fecha2;
		this.fecha3 = fecha3;
		this.fechaPilada = fechaPilada;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.cantidad3 = cantidad3;
		this.implInicial = implInicial;
		this.implFinal = implFinal;
		this.codError = codError;
		this.descError = descError;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}
	
	public void setIndice(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	
	public String getFechaInicio() {
		return this.fechaInicio;
	}
	
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public String getFechaFin() {
		return this.fechaFin;
	}
	
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public Double getCantidad1() {
		return this.cantidad1;
	}
	
	public void setCantidad1(Double cantidad1) {
		this.cantidad1 = cantidad1;
	}
	
	public Double getCantidad2() {
		return this.cantidad2;
	}
	
	public void setCantidad2(Double cantidad2) {
		this.cantidad2 = cantidad2;
	}

	public Double getCantidad3() {
		return this.cantidad3;
	}
	
	public void setCantidad3(Double cantidad3) {
		this.cantidad3 = cantidad3;
	}
	
	public Double getImplInicial() {
		return this.implInicial;
	}
	
	public void setImplInicial(Double implInicial) {
		this.implInicial = implInicial;
	}
	
	public Double getImplFinal() {
		return this.implFinal;
	}
	
	public void setImplFinal(Double implFinal) {
		this.implFinal = implFinal;
	}
	
	public Long getCodError() {
		return this.codError;
	}
	
	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getFecha2() {
		return fecha2;
	}

	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
	}

	public String getFecha3() {
		return fecha3;
	}

	public void setFecha3(String fecha3) {
		this.fecha3 = fecha3;
	}

	public String getFechaPilada() {
		return fechaPilada;
	}

	public void setFechaPilada(String fechaPilada) {
		this.fechaPilada = fechaPilada;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}
}