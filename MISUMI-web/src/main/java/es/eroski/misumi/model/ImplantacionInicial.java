package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class ImplantacionInicial implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long codLoc; 
	private Long codArticulo;
	private Date fechaInicio;
	private Date fechaFin;
	private Double ventaMedia;
	
	public Long getCodLoc() {
		return codLoc;
	}
	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}
	public Long getCodArticulo() {
		return codArticulo;
	}
	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
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
	public Double getVentaMedia() {
		return ventaMedia;
	}
	public void setVentaMedia(Double ventaMedia) {
		this.ventaMedia = ventaMedia;
	}
	

}
