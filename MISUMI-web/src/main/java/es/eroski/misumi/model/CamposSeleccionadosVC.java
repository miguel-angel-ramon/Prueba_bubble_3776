package es.eroski.misumi.model;

import java.io.Serializable;

public class CamposSeleccionadosVC implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Long identificador;
    private Double cantidad1;
    private Double cantidad2;
    private Double cantidad3;
    private Double cantidad4;
    private Double cantidad5;
	private String codError;
	private String descError;
	private Long codCentro;
	private String noGestionaPbl;
	private Long identificadorSIA;
	
	
	public Long getIdentificador() {
		return identificador;
	}
	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}
	public Double getCantidad1() {
		return cantidad1;
	}
	public void setCantidad1(Double cantidad1) {
		this.cantidad1 = cantidad1;
	}
	public Double getCantidad2() {
		return cantidad2;
	}
	public void setCantidad2(Double cantidad2) {
		this.cantidad2 = cantidad2;
	}
	public Double getCantidad3() {
		return cantidad3;
	}
	public void setCantidad3(Double cantidad3) {
		this.cantidad3 = cantidad3;
	}
	public Double getCantidad4() {
		return cantidad4;
	}
	public void setCantidad4(Double cantidad4) {
		this.cantidad4 = cantidad4;
	}
	public Double getCantidad5() {
		return cantidad5;
	}
	public void setCantidad5(Double cantidad5) {
		this.cantidad5 = cantidad5;
	}
	public String getCodError() {
		return codError;
	}
	public void setCodError(String codError) {
		this.codError = codError;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public String getNoGestionaPbl() {
		return noGestionaPbl;
	}
	public void setNoGestionaPbl(String noGestionaPbl) {
		this.noGestionaPbl = noGestionaPbl;
	}
	public Long getIdentificadorSIA() {
		return identificadorSIA;
	}
	public void setIdentificadorSIA(Long identificadorSIA) {
		this.identificadorSIA = identificadorSIA;
	}
}