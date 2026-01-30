package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class Roturas implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc; 
	private Long codArticulo;
	private Date fechaRotura;
	private Long codRotura;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private Long lastUpdateLogin;
	private Long tecle;
	private Long tcn;

	public Roturas() {
		super();
	}

	public Roturas(Long codLoc, Long codArticulo, Date fechaRotura,
			Long codRotura, Long createdBy, Date creationDate,
			Long lastUpdatedBy, Date lastUpdateDate, Long lastUpdateLogin,
			Long tecle, Long tcn) {
		super();
		this.codLoc = codLoc;
		this.codArticulo = codArticulo;
		this.fechaRotura = fechaRotura;
		this.codRotura = codRotura;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdateLogin = lastUpdateLogin;
		this.tecle = tecle;
		this.tcn = tcn;
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Date getFechaRotura() {
		return this.fechaRotura;
	}

	public void setFechaRotura(Date fechaRotura) {
		this.fechaRotura = fechaRotura;
	}

	public Long getCodRotura() {
		return this.codRotura;
	}

	public void setCodRotura(Long codRotura) {
		this.codRotura = codRotura;
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