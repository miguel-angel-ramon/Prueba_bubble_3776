package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class MontajeVegalsa implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long identificadorVegalsa;          
	private Long codCentro;
	private Long codArt;
	private String oferta;
	private Date fechaInicio;
	private Date fechaFin;
	private Long cantidad;
	
	public Long getIdentificadorVegalsa() {
		return identificadorVegalsa;
	}
	public void setIdentificadorVegalsa(Long identificadorVegalsa) {
		this.identificadorVegalsa = identificadorVegalsa;
	}
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public Long getCodArt() {
		return codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public String getOferta() {
		return oferta;
	}
	public void setOferta(String oferta) {
		this.oferta = oferta;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	
	
}