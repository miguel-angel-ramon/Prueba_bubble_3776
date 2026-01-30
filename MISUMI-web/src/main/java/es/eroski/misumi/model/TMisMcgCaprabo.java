package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class TMisMcgCaprabo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArtCaprabo;
	private Long ean;
	private String tipoMov;
	private String descripArt;
	private String descripArtFind;
	private Long sustituidaPor;
	private Long sustitutaDe;
	private String implantacion;
	private Date fechaActivacion;
	private String motivo;
	private Double stock;
	private Double stockDias;
	private Date fechaGen;
	private String nombreFichero;
	private Date creationDate;
	private Date lastUpdateDate;
	private String msgid;
	
	
		
	public TMisMcgCaprabo() {
	    super();
	}

	
	public TMisMcgCaprabo(Long codCentro, Long codArtCaprabo, Long ean, String tipoMov, String descripArt,
			String descripArtFind, Long sustituidaPor, Long sustitutaDe, String implantacion, Date fechaActivacion,
			String motivo, Double stock, Double stockDias, Date fechaGen, String nombreFichero, Date creationDate,
			Date lastUpdateDate, String msgid) {
		super();
		this.codCentro = codCentro;
		this.codArtCaprabo = codArtCaprabo;
		this.ean = ean;
		this.tipoMov = tipoMov;
		this.descripArt = descripArt;
		this.descripArtFind = descripArtFind;
		this.sustituidaPor = sustituidaPor;
		this.sustitutaDe = sustitutaDe;
		this.implantacion = implantacion;
		this.fechaActivacion = fechaActivacion;
		this.motivo = motivo;
		this.stock = stock;
		this.stockDias = stockDias;
		this.fechaGen = fechaGen;
		this.nombreFichero = nombreFichero;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.msgid = msgid;
	}
	
	
	
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public Long getCodArtCaprabo() {
		return codArtCaprabo;
	}
	public void setCodArtCaprabo(Long codArtCaprabo) {
		this.codArtCaprabo = codArtCaprabo;
	}
	public Long getEan() {
		return ean;
	}
	public void setEan(Long ean) {
		this.ean = ean;
	}
	public String getTipoMov() {
		return tipoMov;
	}
	public void setTipoMov(String tipoMov) {
		this.tipoMov = tipoMov;
	}
	public String getDescripArt() {
		return descripArt;
	}
	public void setDescripArt(String descripArt) {
		this.descripArt = descripArt;
	}
	public String getDescripArtFind() {
		return descripArtFind;
	}
	public void setDescripArtFind(String descripArtFind) {
		this.descripArtFind = descripArtFind;
	}
	public Long getSustituidaPor() {
		return sustituidaPor;
	}
	public void setSustituidaPor(Long sustituidaPor) {
		this.sustituidaPor = sustituidaPor;
	}
	public Long getSustitutaDe() {
		return sustitutaDe;
	}
	public void setSustitutaDe(Long sustitutaDe) {
		this.sustitutaDe = sustitutaDe;
	}
	public String getImplantacion() {
		return implantacion;
	}
	public void setImplantacion(String implantacion) {
		this.implantacion = implantacion;
	}
	public Date getFechaActivacion() {
		return fechaActivacion;
	}
	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Double getStock() {
		return stock;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}
	public Double getStockDias() {
		return stockDias;
	}
	public void setStockDias(Double stockDias) {
		this.stockDias = stockDias;
	}
	public Date getFechaGen() {
		return fechaGen;
	}
	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	
}