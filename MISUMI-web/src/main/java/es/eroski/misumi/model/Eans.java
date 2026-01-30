package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class Eans implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codEan;
	private Long codArticulo;
	private String flgNlu;
	private Date fecDesdeEdi;
	private Date fecHastaEdi;
	private Long codArticuloAnt;
	private Long cretedBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private Long lastUpdateLogin;
	private Long tecle;
	private Long tcn;
	
	public Eans() {
		super();
	}

	public Eans(Long codEan, Long codArticulo, String flgNlu, Date fecDesdeEdi,
			Date fecHastaEdi, Long codArticuloAnt, Long cretedBy,
			Date creationDate, Long lastUpdatedBy, Date lastUpdateDate,
			Long lastUpdateLogin, Long tecle, Long tcn) {
		super();
		this.codEan = codEan;
		this.codArticulo = codArticulo;
		this.flgNlu = flgNlu;
		this.fecDesdeEdi = fecDesdeEdi;
		this.fecHastaEdi = fecHastaEdi;
		this.codArticuloAnt = codArticuloAnt;
		this.cretedBy = cretedBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdateLogin = lastUpdateLogin;
		this.tecle = tecle;
		this.tcn = tcn;
	}

	public Long getCodEan() {
		return this.codEan;
	}

	public void setCodEan(Long codEan) {
		this.codEan = codEan;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getFlgNlu() {
		return this.flgNlu;
	}

	public void setFlgNlu(String flgNlu) {
		this.flgNlu = flgNlu;
	}

	public Date getFecDesdeEdi() {
		return this.fecDesdeEdi;
	}

	public void setFecDesdeEdi(Date fecDesdeEdi) {
		this.fecDesdeEdi = fecDesdeEdi;
	}

	public Date getFecHastaEdi() {
		return this.fecHastaEdi;
	}

	public void setFecHastaEdi(Date fecHastaEdi) {
		this.fecHastaEdi = fecHastaEdi;
	}

	public Long getCodArticuloAnt() {
		return this.codArticuloAnt;
	}

	public void setCodArticuloAnt(Long codArticuloAnt) {
		this.codArticuloAnt = codArticuloAnt;
	}

	public Long getCretedBy() {
		return this.cretedBy;
	}

	public void setCretedBy(Long cretedBy) {
		this.cretedBy = cretedBy;
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