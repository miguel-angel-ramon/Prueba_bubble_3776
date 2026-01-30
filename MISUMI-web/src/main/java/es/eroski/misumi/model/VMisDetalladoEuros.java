package es.eroski.misumi.model;

import java.io.Serializable;

public class VMisDetalladoEuros implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long nivel;
	private Long ident;
	private Long parentident;
	private Long codCentro;
	private String idSesion;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private String descripcion;
	private Long precioCostoInicial;
	private Long cajasInicial;
	private Long precioCostoFinal;
	private Long cajasFinales;
	private Long codArt;
	private String tipo;
	private Long mapa;
	
	//Campos para control de errores
	private Long codError;
	private String descError;
	
	public VMisDetalladoEuros(Long nivel, Long ident, Long parentident, Long codCentro, String idSesion, Long grupo1,
			Long grupo2, Long grupo3, String descripcion, Long precioCostoInicial, Long cajasInicial,
			Long precioCostoFinal, Long cajasFinales, Long codArt, String tipo) {
		super();
		this.nivel = nivel;
		this.ident = ident;
		this.parentident = parentident;
		this.codCentro = codCentro;
		this.idSesion = idSesion;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.descripcion = descripcion;
		this.precioCostoInicial = precioCostoInicial;
		this.cajasInicial = cajasInicial;
		this.precioCostoFinal = precioCostoFinal;
		this.cajasFinales = cajasFinales;
		this.codArt = codArt;
		this.tipo = tipo;
	}
	
	public VMisDetalladoEuros() {
		super();
	}

	public Long getNivel() {
		return nivel;
	}
	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}
	public Long getIdent() {
		return ident;
	}
	public void setIdent(Long ident) {
		this.ident = ident;
	}
	public Long getParentident() {
		return parentident;
	}
	public void setParentident(Long parentident) {
		this.parentident = parentident;
	}
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public String getIdSesion() {
		return idSesion;
	}
	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}
	public Long getGrupo1() {
		return grupo1;
	}
	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}
	public Long getGrupo2() {
		return grupo2;
	}
	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}
	public Long getGrupo3() {
		return grupo3;
	}
	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getPrecioCostoInicial() {
		return precioCostoInicial;
	}
	public void setPrecioCostoInicial(Long precioCostoInicial) {
		this.precioCostoInicial = precioCostoInicial;
	}
	public Long getCajasInicial() {
		return cajasInicial;
	}
	public void setCajasInicial(Long cajasInicial) {
		this.cajasInicial = cajasInicial;
	}
	public Long getPrecioCostoFinal() {
		return precioCostoFinal;
	}
	public void setPrecioCostoFinal(Long precioCostoFinal) {
		this.precioCostoFinal = precioCostoFinal;
	}
	public Long getCajasFinales() {
		return cajasFinales;
	}
	public void setCajasFinales(Long cajasFinales) {
		this.cajasFinales = cajasFinales;
	}
	public Long getCodArt() {
		return codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Long getMapa() {
		return mapa;
	}
	public void setMapa(Long mapa) {
		this.mapa = mapa;
	}
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
