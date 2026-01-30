package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VPlanograma implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Long codCentro;   
	public Long codArt;  
	public Long capMax;
	public Double stckMin;
	public String anoOfertaPilada;
	public Long numOferta;
	public String simulado;
	public Date fechaGen;
	public Date lastUpdateDate;
	public String tipoPlano;
	public Long facingAlto;
	public Long facingAncho;
	public String esCajaExp;
	
	public VPlanograma() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VPlanograma(Long codCentro, Long codArt, Long capMax, Double stckMin, String anoOfertaPilada, Long numOferta,
			String simulado, Date fechaGen, Date lastUpdateDate, String tipoPlano, Long facingAlto, Long facingAncho) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.capMax = capMax;
		this.stckMin = stckMin;
		this.anoOfertaPilada = anoOfertaPilada;
		this.numOferta = numOferta;
		this.simulado = simulado;
		this.fechaGen = fechaGen;
		this.lastUpdateDate = lastUpdateDate;
		this.tipoPlano = tipoPlano;
		this.facingAlto = facingAlto;
		this.facingAncho = facingAncho;
	}
	
	public VPlanograma(Long codCentro, Long codArt, Long capMax, Double stckMin, String anoOfertaPilada, Long numOferta,
			String simulado, Date fechaGen, Date lastUpdateDate, String tipoPlano, Long facingAlto, Long facingAncho,
			String esCajaExp) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.capMax = capMax;
		this.stckMin = stckMin;
		this.anoOfertaPilada = anoOfertaPilada;
		this.numOferta = numOferta;
		this.simulado = simulado;
		this.fechaGen = fechaGen;
		this.lastUpdateDate = lastUpdateDate;
		this.tipoPlano = tipoPlano;
		this.facingAlto = facingAlto;
		this.facingAncho = facingAncho;
		this.esCajaExp = esCajaExp;
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
	public Long getCapMax() {
		return capMax;
	}
	public void setCapMax(Long capMax) {
		this.capMax = capMax;
	}
	public Double getStckMin() {
		return stckMin;
	}
	public void setStckMin(Double stckMin) {
		this.stckMin = stckMin;
	}
	public String getAnoOfertaPilada() {
		return anoOfertaPilada;
	}
	public void setAnoOfertaPilada(String anoOfertaPilada) {
		this.anoOfertaPilada = anoOfertaPilada;
	}
	public Long getNumOferta() {
		return numOferta;
	}
	public void setNumOferta(Long numOferta) {
		this.numOferta = numOferta;
	}
	public String getSimulado() {
		return simulado;
	}
	public void setSimulado(String simulado) {
		this.simulado = simulado;
	}
	public Date getFechaGen() {
		return fechaGen;
	}
	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getTipoPlano() {
		return tipoPlano;
	}
	public void setTipoPlano(String tipoPlano) {
		this.tipoPlano = tipoPlano;
	}
	public Long getFacingAlto() {
		return facingAlto;
	}
	public void setFacingAlto(Long facingAlto) {
		this.facingAlto = facingAlto;
	}
	public Long getFacingAncho() {
		return facingAncho;
	}
	public void setFacingAncho(Long facingAncho) {
		this.facingAncho = facingAncho;
	}
	public String getEsCajaExp() {
		return esCajaExp;
	}
	public void setEsCajaExp(String esCajaExp) {
		this.esCajaExp = esCajaExp;
	}
}
