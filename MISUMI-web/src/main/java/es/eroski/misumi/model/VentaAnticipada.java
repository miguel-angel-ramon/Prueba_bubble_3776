package es.eroski.misumi.model;

import java.util.Date;

public class VentaAnticipada {
	
	private Long codCentro;
	private Long codArt;
	private String fechaGen;
	private Double cantidad;
	private Date creationDate;
	private Date lastUpdate;
	private String flgEnvioAC;
	private Boolean existe;
	private Date nextDate;
	
	private String descArt;
	private String fechaGenFormated;
	private String descError;
	
	public VentaAnticipada(){
		
	}
	
	public VentaAnticipada(Long codCentro, Long codArt, String fechaGen,
			Double cantidad, Date creationDate, Date lastUpdate, String flgEnvioAC) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.fechaGen = fechaGen;
		this.cantidad = cantidad;
		this.creationDate = creationDate;
		this.lastUpdate = lastUpdate;
		this.flgEnvioAC = flgEnvioAC;
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
	public String getFechaGen() {
		return this.fechaGen;
	}
	public void setFechaGen(String fechaGen) {
		this.fechaGen = fechaGen;
	}
	public Double getCantidad() {
		return this.cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Date getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getFlgEnvioAC() {
		return this.flgEnvioAC;
	}
	public void setFlgEnvioAC(String flgEnvioAC) {
		this.flgEnvioAC = flgEnvioAC;
	}

	public Boolean getExiste() {
		return this.existe;
	}

	public void setExiste(Boolean existe) {
		this.existe = existe;
	}

	public Date getNextDate() {
		return this.nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}

	public String getDescArt() {
		return descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}

	public String getFechaGenFormated() {
		return fechaGenFormated;
	}

	public void setFechaGenFormated(String fechaGenFormated) {
		this.fechaGenFormated = fechaGenFormated;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}
	
	

}
