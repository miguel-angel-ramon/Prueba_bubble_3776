package es.eroski.misumi.model;

import java.io.Serializable;

public class CamposSeleccionadosExclVenta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codArt;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private Long identificador;
	private Long identificadorSIA;
	private String fecha;
	private Long codError;
	private String descripError;

	private String seleccionado;

    public CamposSeleccionadosExclVenta() {
		super();
	}

	public CamposSeleccionadosExclVenta(Long codCentro, Long codArt, 
			Long grupo1, Long grupo2, Long grupo3, Long grupo4, Long grupo5,
			Long identificador, String fecha, Long codError, String descripError, String seleccionado) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.identificador = identificador;
		this.fecha = fecha;
		this.codError = codError;
		this.descripError = descripError;
		this.seleccionado = seleccionado;
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

	public Long getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public Long getIdentificadorSIA() {
		return identificadorSIA;
	}

	public void setIdentificadorSIA(Long identificadorSIA) {
		this.identificadorSIA = identificadorSIA;
	}

	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getSeleccionado() {
		return this.seleccionado;
	}

	public void setSeleccionado(String seleccionado) {
		this.seleccionado = seleccionado;
	}
	
	public Long getCodError() {
		return this.codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescripError() {
		return this.descripError;
	}

	public void setDescripError(String descripError) {
		this.descripError = descripError;
	}
}