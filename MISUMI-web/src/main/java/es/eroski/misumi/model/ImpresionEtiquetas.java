package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class ImpresionEtiquetas implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private String mac;
	private Long nVeces;
	private Date creationDate;
	private Date lastUpdate;
	
	public ImpresionEtiquetas() {
	    super();
	}

	public ImpresionEtiquetas(Long codCentro, Long codArt, String mac, Long nVeces,
			 Date creationDate, Date lastUpdate) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.mac = mac;
		this.nVeces = nVeces;
		this.creationDate = creationDate;
		this.lastUpdate = lastUpdate;
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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Long getnVeces() {
		return nVeces;
	}

	public void setnVeces(Long nVeces) {
		this.nVeces = nVeces;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}