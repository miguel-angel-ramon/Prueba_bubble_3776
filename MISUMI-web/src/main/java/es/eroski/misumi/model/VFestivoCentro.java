package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VFestivoCentro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Date fechaFestivo;
	private String tipoFestivo;
	private String estado;
	private Date fechaGen;
	private Date fechaInicio;
	private Date fechaFin;
	
	public Long getCodCentro() {
		return this.codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public Date getFechaFestivo() {
		return this.fechaFestivo;
	}
	public void setFechaFestivo(Date fechaFestivo) {
		this.fechaFestivo = fechaFestivo;
	}
	public String getTipoFestivo() {
		return this.tipoFestivo;
	}
	public void setTipoFestivo(String tipoFestivo) {
		this.tipoFestivo = tipoFestivo;
	}
	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaGen() {
		return this.fechaGen;
	}
	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
	public Date getFechaInicio() {
		return this.fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return this.fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	

}
