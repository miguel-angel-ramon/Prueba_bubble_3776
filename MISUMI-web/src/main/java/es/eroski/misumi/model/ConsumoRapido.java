package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;

public class ConsumoRapido implements Cloneable,Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codArt;
	private String descripArt;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String descripGrupo1;
	private String descripGrupo2;
	private String descripGrupo3;
	private String descripGrupo4;
	private String descripGrupo5;
	private Long cantidad;
	private String fechaGrab;
	private Long vidaUtil;
	private Long imc;

	public ConsumoRapido() {
	    super();
	}
	
	public  ConsumoRapido clone() throws CloneNotSupportedException{
		return (ConsumoRapido)super.clone();
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

	public String getDescripGrupo1() {
		return this.descripGrupo1;
	}

	public void setDescripGrupo1(String descripGrupo1) {
		this.descripGrupo1 = descripGrupo1;
	}

	public String getDescripGrupo2() {
		return this.descripGrupo2;
	}

	public void setDescripGrupo2(String descripGrupo2) {
		this.descripGrupo2 = descripGrupo2;
	}

	public String getDescripGrupo3() {
		return this.descripGrupo3;
	}

	public void setDescripGrupo3(String descripGrupo3) {
		this.descripGrupo3 = descripGrupo3;
	}

	public String getDescripGrupo4() {
		return this.descripGrupo4;
	}

	public void setDescripGrupo4(String descripGrupo4) {
		this.descripGrupo4 = descripGrupo4;
	}

	public String getDescripGrupo5() {
		return this.descripGrupo5;
	}

	public void setDescripGrupo5(String descripGrupo5) {
		this.descripGrupo5 = descripGrupo5;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getFechaGrab() {
		return fechaGrab;
	}

	public void setFechaGrab(String fechaGrab) {
		this.fechaGrab = fechaGrab;
	}

	public Long getVidaUtil() {
		return vidaUtil;
	}

	public void setVidaUtil(Long vidaUtil) {
		this.vidaUtil = vidaUtil;
	}

	public Long getImc() {
		return imc;
	}

	public void setImc(Long imc) {
		this.imc = imc;
	}

}