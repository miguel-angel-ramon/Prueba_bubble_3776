package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VDatosDiarioCap implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long codArt; 
	private String descripArt;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private Long estlogN1;
	private Long estlogN2;
	private Long estlogN3;
	private String bloqCom;
	private String estado;
	private Double kilosUni;
	private String pesoVari;
	private String formato;
	private Date fechaGen;
	
	public VDatosDiarioCap() {
	    super();
	}

	public VDatosDiarioCap(Long codArt, String descripArt, Long grupo1, Long grupo2, Long grupo3, Long grupo4,
			Long grupo5, Long estlogN1, Long estlogN2, Long estlogN3, String bloqCom, String estado, Double kilosUni,
			String pesoVari, String formato, Date fechaGen) {
		super();
		this.codArt = codArt;
		this.descripArt = descripArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.estlogN1 = estlogN1;
		this.estlogN2 = estlogN2;
		this.estlogN3 = estlogN3;
		this.bloqCom = bloqCom;
		this.estado = estado;
		this.kilosUni = kilosUni;
		this.pesoVari = pesoVari;
		this.formato = formato;
		this.fechaGen = fechaGen;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getDescripArt() {
		return this.descripArt;
	}

	public void setDescripArt(String descripArt) {
		this.descripArt = descripArt;
	}

	public Long getGrupo1() {
		return this.grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public Long getGrupo2() {
		return this.grupo2;
	}

	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}

	public Long getGrupo3() {
		return this.grupo3;
	}

	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}

	public Long getGrupo4() {
		return this.grupo4;
	}

	public void setGrupo4(Long grupo4) {
		this.grupo4 = grupo4;
	}

	public Long getGrupo5() {
		return this.grupo5;
	}

	public void setGrupo5(Long grupo5) {
		this.grupo5 = grupo5;
	}

	public Long getEstlogN1() {
		return this.estlogN1;
	}

	public void setEstlogN1(Long estlogN1) {
		this.estlogN1 = estlogN1;
	}

	public Long getEstlogN2() {
		return this.estlogN2;
	}

	public void setEstlogN2(Long estlogN2) {
		this.estlogN2 = estlogN2;
	}

	public Long getEstlogN3() {
		return this.estlogN3;
	}

	public void setEstlogN3(Long estlogN3) {
		this.estlogN3 = estlogN3;
	}

	public String getBloqCom() {
		return this.bloqCom;
	}

	public void setBloqCom(String bloqCom) {
		this.bloqCom = bloqCom;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Double getKilosUni() {
		return this.kilosUni;
	}

	public void setKilosUni(Double kilosUni) {
		this.kilosUni = kilosUni;
	}

	public String getPesoVari() {
		return this.pesoVari;
	}

	public void setPesoVari(String pesoVari) {
		this.pesoVari = pesoVari;
	}

	public String getFormato() {
		return this.formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
}