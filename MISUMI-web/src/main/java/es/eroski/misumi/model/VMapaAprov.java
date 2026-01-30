package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VMapaAprov implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private Long codPlat;
	private Long pedLun;
	private Long pedMar;
	private Long pedMie;
	private Long pedJue;
	private Long pedVie;
	private Long pedSab;
	private Long pedDom;
	private String tipoDiaLN;
	private Long cicloSemanal;
	private String estado;
	private Date fechaGen;
	private String codN1;
	private String codN2;
	private String codN3;
	private String horaTrans;
	
	public VMapaAprov() {
	    super();
	}
	
	public VMapaAprov(Long codCentro, Long codArt, Long codPlat, Long pedLun,
			Long pedMar, Long pedMie, Long pedJue, Long pedVie, Long pedSab,
			Long pedDom, String tipoDiaLN, Long cicloSemanal, String estado,
			Date fechaGen, String codN1, String codN2, String codN3,
			String horaTrans) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.codPlat = codPlat;
		this.pedLun = pedLun;
		this.pedMar = pedMar;
		this.pedMie = pedMie;
		this.pedJue = pedJue;
		this.pedVie = pedVie;
		this.pedSab = pedSab;
		this.pedDom = pedDom;
		this.tipoDiaLN = tipoDiaLN;
		this.cicloSemanal = cicloSemanal;
		this.estado = estado;
		this.fechaGen = fechaGen;
		this.codN1 = codN1;
		this.codN2 = codN2;
		this.codN3 = codN3;
		this.horaTrans = horaTrans;
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

	public Long getPedLun() {
		return this.pedLun;
	}

	public void setPedLun(Long pedLun) {
		this.pedLun = pedLun;
	}

	public Long getPedMar() {
		return this.pedMar;
	}

	public void setPedMar(Long pedMar) {
		this.pedMar = pedMar;
	}

	public Long getPedMie() {
		return this.pedMie;
	}

	public void setPedMie(Long pedMie) {
		this.pedMie = pedMie;
	}

	public Long getPedJue() {
		return this.pedJue;
	}

	public void setPedJue(Long pedJue) {
		this.pedJue = pedJue;
	}

	public Long getPedVie() {
		return this.pedVie;
	}

	public void setPedVie(Long pedVie) {
		this.pedVie = pedVie;
	}

	public Long getPedSab() {
		return this.pedSab;
	}

	public void setPedSab(Long pedSab) {
		this.pedSab = pedSab;
	}

	public Long getPedDom() {
		return this.pedDom;
	}

	public void setPedDom(Long pedDom) {
		this.pedDom = pedDom;
	}

	public String getTipoDiaLN() {
		return this.tipoDiaLN;
	}

	public void setTipoDiaLN(String tipoDiaLN) {
		this.tipoDiaLN = tipoDiaLN;
	}

	public Long getCicloSemanal() {
		return this.cicloSemanal;
	}

	public void setCicloSemanal(Long cicloSemanal) {
		this.cicloSemanal = cicloSemanal;
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
}