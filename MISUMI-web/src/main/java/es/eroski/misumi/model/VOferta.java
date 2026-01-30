package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VOferta implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private Long anoOferta;
	private Long numOferta;
	private Long tipoOferta;
	private Date fechaIni;
	private Date fechaFin;
	private String dTipoOferta;
	
	public VOferta() {
	    super();
	}

	public VOferta(Long codCentro, Long codArt, Long anoOferta, Long numOferta,
			Long tipoOferta, Date fechaIni, Date fechaFin, String dTipoOferta) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
		this.tipoOferta = tipoOferta;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.dTipoOferta = dTipoOferta;
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

	public Long getAnoOferta() {
		return this.anoOferta;
	}

	public void setAnoOferta(Long anoOferta) {
		this.anoOferta = anoOferta;
	}

	public Long getNumOferta() {
		return this.numOferta;
	}

	public void setNumOferta(Long numOferta) {
		this.numOferta = numOferta;
	}

	public Long getTipoOferta() {
		return this.tipoOferta;
	}

	public void setTipoOferta(Long tipoOferta) {
		this.tipoOferta = tipoOferta;
	}

	public Date getFechaIni() {
		return this.fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getdTipoOferta() {
		return this.dTipoOferta;
	}

	public void setdTipoOferta(String dTipoOferta) {
		this.dTipoOferta = dTipoOferta;
	}

}