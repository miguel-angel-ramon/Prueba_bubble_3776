package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class BorrUniAux implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private Long seqBorrUniAux;
	private Long familia;
	private Long ordenFam;
	private String nomTabla;
	private String fechaRef;
	private Long numDias;
	private String wherePlus;
	private String flgActivo;
	private String observaciones;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private Long lastUpdateLogin;
	private Long tecle;
	private Long tcn;

	public BorrUniAux() {
	    super();
	}

	public BorrUniAux(Long seqBorrUniAux, Long familia, Long ordenFam,
			String nomTabla, String fechaRef, Long numDias, String wherePlus,
			String flgActivo, String observaciones, Long createdBy,
			Date creationDate, Long lastUpdatedBy, Date lastUpdateDate,
			Long lastUpdateLogin, Long tecle, Long tcn) {
		super();
		this.seqBorrUniAux = seqBorrUniAux;
		this.familia = familia;
		this.ordenFam = ordenFam;
		this.nomTabla = nomTabla;
		this.fechaRef = fechaRef;
		this.numDias = numDias;
		this.wherePlus = wherePlus;
		this.flgActivo = flgActivo;
		this.observaciones = observaciones;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdateLogin = lastUpdateLogin;
		this.tecle = tecle;
		this.tcn = tcn;
	}

	public Long getSeqBorrUniAux() {
		return this.seqBorrUniAux;
	}

	public void setSeqBorrUniAux(Long seqBorrUniAux) {
		this.seqBorrUniAux = seqBorrUniAux;
	}

	public Long getFamilia() {
		return this.familia;
	}

	public void setFamilia(Long familia) {
		this.familia = familia;
	}

	public Long getOrdenFam() {
		return this.ordenFam;
	}

	public void setOrdenFam(Long ordenFam) {
		this.ordenFam = ordenFam;
	}

	public String getNomTabla() {
		return this.nomTabla;
	}

	public void setNomTabla(String nomTabla) {
		this.nomTabla = nomTabla;
	}

	public String getFechaRef() {
		return this.fechaRef;
	}

	public void setFechaRef(String fechaRef) {
		this.fechaRef = fechaRef;
	}

	public Long getNumDias() {
		return this.numDias;
	}

	public void setNumDias(Long numDias) {
		this.numDias = numDias;
	}

	public String getWherePlus() {
		return this.wherePlus;
	}

	public void setWherePlus(String wherePlus) {
		this.wherePlus = wherePlus;
	}

	public String getFlgActivo() {
		return this.flgActivo;
	}

	public void setFlgActivo(String flgActivo) {
		this.flgActivo = flgActivo;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdateLogin() {
		return this.lastUpdateLogin;
	}

	public void setLastUpdateLogin(Long lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Long getTecle() {
		return this.tecle;
	}

	public void setTecle(Long tecle) {
		this.tecle = tecle;
	}

	public Long getTcn() {
		return this.tcn;
	}

	public void setTcn(Long tcn) {
		this.tcn = tcn;
	}
}