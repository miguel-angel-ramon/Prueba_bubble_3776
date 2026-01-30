package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class StockNoServido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codArt;
	private Date fechaNsr;
	private String fechaNsrDDMMYYYY;
	private Float uniNoServ;
	private Long motivo;
	private String motivoDes;
	private Long codPlat;
	private String codPlatDes;
	private Date fechaPrevisEnt;
	private String fechaPrevisEntDDMMYYYY;
    
	public StockNoServido(){
			super();
	}
	
	public StockNoServido(Long codCentro, Long codArt, Date fechaNsr, Float uniNoServ, 
			              Long motivo, String motivoDes, Long codPlat, String codPlatDes){
    	super();
    	this.codCentro = codCentro;
    	this.codArt = codArt;
    	this.fechaNsr = fechaNsr;
    	this.uniNoServ = uniNoServ;
    	this.motivo = motivo;
    	this.motivoDes = motivoDes;
    	this.codPlat = codPlat;
    	this.codPlatDes = codPlatDes;   	
    }
	
    public StockNoServido(Long codCentro, Long codArt){
    	super();
    	this.codCentro = codCentro;
    	this.codArt = codArt;
    	this.fechaNsr = null;
    	this.uniNoServ = null;
    	this.motivo = null;
    	this.motivoDes = null;
    	this.codPlat = null;
    	this.codPlatDes = null;   	
    }

	public StockNoServido(Float uniNoServ, Long motivo, String motivoDes, Date fechaPrevisEnt, String fechaPrevisEntDDMMYYYY){
		super();
		this.codCentro = null;
		this.codArt = null;
		this.fechaNsr = null;
		this.fechaNsrDDMMYYYY = "";
		this.uniNoServ = uniNoServ;
		this.motivo = motivo;
		this.motivoDes = motivoDes;
		this.codPlat = null;
		this.codPlatDes = "";   	
		this.fechaPrevisEnt = fechaPrevisEnt;
		this.fechaPrevisEntDDMMYYYY = fechaPrevisEntDDMMYYYY;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public Date getFechaNsr() {
		return this.fechaNsr;
	}

	public void setFechaNsr(Date fechaNsr) {
		this.fechaNsr = fechaNsr;
	}

	public String getFechaNsrDDMMYYYY() {
		return this.fechaNsrDDMMYYYY;
	}

	public void setFechaNsrDDMMYYYY(String fechaNsrDDMMYYYY) {
		this.fechaNsrDDMMYYYY = fechaNsrDDMMYYYY;
	}

	public Float getUniNoServ() {
		return this.uniNoServ;
	}

	public void setUniNoServ(Float uniNoServ) {
		this.uniNoServ = uniNoServ;
	}

	public Long getMotivo() {
		return this.motivo;
	}

	public void setMotivo(Long motivo) {
		this.motivo = motivo;
	}

	public String getMotivoDes() {
		return this.motivoDes;
	}

	public void setMotivoDes(String motivoDes) {
		this.motivoDes = motivoDes;
	}

	public Long getCodPlat() {
		return this.codPlat;
	}

	public void setCodPlat(Long codPlat) {
		this.codPlat = codPlat;
	}

	public String getCodPlatDes() {
		return this.codPlatDes;
	}

	public void setCodPlatDes(String codPlatDes) {
		this.codPlatDes = codPlatDes;
	}

	public Date getFechaPrevisEnt() {
		return fechaPrevisEnt;
	}

	public void setFechaPrevisEnt(Date fechaPrevisEnt) {
		this.fechaPrevisEnt = fechaPrevisEnt;
	}

	public String getFechaPrevisEntDDMMYYYY() {
		return fechaPrevisEntDDMMYYYY;
	}

	public void setFechaPrevisEntDDMMYYYY(String fechaPrevisEntDDMMYYYY) {
		this.fechaPrevisEntDDMMYYYY = fechaPrevisEntDDMMYYYY;
	}
	
	

	
}
