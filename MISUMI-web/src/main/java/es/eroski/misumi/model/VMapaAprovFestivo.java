package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VMapaAprovFestivo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private Long codPlat;
	private String tipoDiaLN;
	private String estado;
	private Date fechaGen;
	private String codN1;
	private String codN2;
	private String codN3;
	private String horaTrans;
	private Date fechaCambio;
	
	public VMapaAprovFestivo() {
	    super();
	}

	public VMapaAprovFestivo(Long codCentro, Long codArt, Long codPlat,
			String tipoDiaLN, String estado, Date fechaGen, String codN1,
			String codN2, String codN3, String horaTrans, Date fechaCambio) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.codPlat = codPlat;
		this.tipoDiaLN = tipoDiaLN;
		this.estado = estado;
		this.fechaGen = fechaGen;
		this.codN1 = codN1;
		this.codN2 = codN2;
		this.codN3 = codN3;
		this.horaTrans = horaTrans;
		this.fechaCambio = fechaCambio;
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

	public Long getCodPlat() {
		return this.codPlat;
	}

	public void setCodPlat(Long codPlat) {
		this.codPlat = codPlat;
	}

	public String getTipoDiaLN() {
		return this.tipoDiaLN;
	}

	public void setTipoDiaLN(String tipoDiaLN) {
		this.tipoDiaLN = tipoDiaLN;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public String getCodN1() {
		return this.codN1;
	}

	public void setCodN1(String codN1) {
		this.codN1 = codN1;
	}

	public String getCodN2() {
		return this.codN2;
	}

	public void setCodN2(String codN2) {
		this.codN2 = codN2;
	}

	public String getCodN3() {
		return this.codN3;
	}

	public void setCodN3(String codN3) {
		this.codN3 = codN3;
	}

	public String getHoraTrans() {
		return this.horaTrans;
	}

	public void setHoraTrans(String horaTrans) {
		this.horaTrans = horaTrans;
	}

	public Date getFechaCambio() {
		return this.fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
}