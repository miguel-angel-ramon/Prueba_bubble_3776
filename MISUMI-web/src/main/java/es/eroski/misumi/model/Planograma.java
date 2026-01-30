package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class Planograma implements Serializable{
	private static final long serialVersionUID = 1L;

	private String nombreFichero;
	private String flgEnviado;
	private Date creationDate;
	private Date lastUpdateDate;
	private String msgid;
	private Long codCentro;
	private Long codArt;
	private Double stockMinComer;
	private Long capacidadMax;
	private String anoOfertaPilada;
	private Long numOferta;
	private Date fechaGen;
	private String flgEnviadoPbl;
	private Long codOferta;
	private String tipoPlano;
	private Long facingAlto;
	private Long facingAncho;
	
	public Planograma() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Planograma(String nombreFichero, String flgEnviado, Date creationDate, Date lastUpdateDate, String msgid,
			Long codCentro, Long codArt, Double stockMinComer, Long capacidadMax, String anoOfertaPilada,
			Long numOferta, Date fechaGen, String flgEnviadoPbl, Long codOferta, String tipoPlano, Long facingAlto,
			Long facingAncho) {
		super();
		this.nombreFichero = nombreFichero;
		this.flgEnviado = flgEnviado;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.msgid = msgid;
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.stockMinComer = stockMinComer;
		this.capacidadMax = capacidadMax;
		this.anoOfertaPilada = anoOfertaPilada;
		this.numOferta = numOferta;
		this.fechaGen = fechaGen;
		this.flgEnviadoPbl = flgEnviadoPbl;
		this.codOferta = codOferta;
		this.tipoPlano = tipoPlano;
		this.facingAlto = facingAlto;
		this.facingAncho = facingAncho;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public String getFlgEnviado() {
		return flgEnviado;
	}
	public void setFlgEnviado(String flgEnviado) {
		this.flgEnviado = flgEnviado;
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
	public Double getStockMinComer() {
		return stockMinComer;
	}
	public void setStockMinComer(Double stockMinComer) {
		this.stockMinComer = stockMinComer;
	}
	public Long getCapacidadMax() {
		return capacidadMax;
	}
	public void setCapacidadMax(Long capacidadMax) {
		this.capacidadMax = capacidadMax;
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
	public Date getFechaGen() {
		return fechaGen;
	}
	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
	public String getFlgEnviadoPbl() {
		return flgEnviadoPbl;
	}
	public void setFlgEnviadoPbl(String flgEnviadoPbl) {
		this.flgEnviadoPbl = flgEnviadoPbl;
	}
	public Long getCodOferta() {
		return codOferta;
	}
	public void setCodOferta(Long codOferta) {
		this.codOferta = codOferta;
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
}
