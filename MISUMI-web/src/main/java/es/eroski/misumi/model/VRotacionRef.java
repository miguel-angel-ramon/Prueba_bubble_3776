package es.eroski.misumi.model;

import java.io.Serializable;

public class VRotacionRef implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codArt;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private Double totVentaRef;
	private Double totVentaGrupo;
	private Double porcentajeRef;
	private Double porcentajeTot;
	private String tipoRotRef;
	private String tipoRotTotal;
	
	public VRotacionRef() {
		super();
	}

	public VRotacionRef(Long codCentro, Long codArt, Long grupo1, Long grupo2, Long grupo3, Long grupo4, Long grupo5,
			Double totVentaRef, Double totVentaGrupo, Double porcentajeRef, Double porcentajeTot, String tipoRotRef,
			String tipoRotTotal) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.totVentaRef = totVentaRef;
		this.totVentaGrupo = totVentaGrupo;
		this.porcentajeRef = porcentajeRef;
		this.porcentajeTot = porcentajeTot;
		this.tipoRotRef = tipoRotRef;
		this.tipoRotTotal = tipoRotTotal;
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

	public Double getTotVentaRef() {
		return this.totVentaRef;
	}

	public void setTotVentaRef(Double totVentaRef) {
		this.totVentaRef = totVentaRef;
	}

	public Double getTotVentaGrupo() {
		return this.totVentaGrupo;
	}

	public void setTotVentaGrupo(Double totVentaGrupo) {
		this.totVentaGrupo = totVentaGrupo;
	}

	public Double getPorcentajeRef() {
		return this.porcentajeRef;
	}

	public void setPorcentajeRef(Double porcentajeRef) {
		this.porcentajeRef = porcentajeRef;
	}

	public Double getPorcentajeTot() {
		return this.porcentajeTot;
	}

	public void setPorcentajeTot(Double porcentajeTot) {
		this.porcentajeTot = porcentajeTot;
	}

	public String getTipoRotRef() {
		return this.tipoRotRef;
	}

	public void setTipoRotRef(String tipoRotRef) {
		this.tipoRotRef = tipoRotRef;
	}

	public String getTipoRotTotal() {
		return this.tipoRotTotal;
	}

	public void setTipoRotTotal(String tipoRotTotal) {
		this.tipoRotTotal = tipoRotTotal;
	}
}