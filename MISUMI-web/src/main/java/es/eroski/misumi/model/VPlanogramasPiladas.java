package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VPlanogramasPiladas implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codArt;
	private Double stockMinCorner;
	private Long capacidadMax;
	private Long impFinal;
	private String anoOferta;
	private Long numOferta;
	private Date fInicio;
	private Date fFin;
	private Date fechaGen;
	
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
	public Double getStockMinCorner() {
		return stockMinCorner;
	}
	public void setStockMinCorner(Double stockMinCorner) {
		this.stockMinCorner = stockMinCorner;
	}
	public Long getCapacidadMax() {
		return capacidadMax;
	}
	public void setCapacidadMax(Long capacidadMax) {
		this.capacidadMax = capacidadMax;
	}
	public Long getImpFinal() {
		return impFinal;
	}
	public void setImpFinal(Long impFinal) {
		this.impFinal = impFinal;
	}
	public String getAnoOferta() {
		return anoOferta;
	}
	public void setAnoOferta(String anoOferta) {
		this.anoOferta = anoOferta;
	}
	public Long getNumOferta() {
		return numOferta;
	}
	public void setNumOferta(Long numOferta) {
		this.numOferta = numOferta;
	}
	public Date getfInicio() {
		return fInicio;
	}
	public void setfInicio(Date fInicio) {
		this.fInicio = fInicio;
	}
	public Date getfFin() {
		return fFin;
	}
	public void setfFin(Date fFin) {
		this.fFin = fFin;
	}
	public Date getFechaGen() {
		return fechaGen;
	}
	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
	
	

}
