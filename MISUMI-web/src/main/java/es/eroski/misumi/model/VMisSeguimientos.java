package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VMisSeguimientos implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long nivel;
	private Long ident;
	private Long parentident;
	private Long codCentro;
	private Date fechaPrevisEnt;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private String descripcion;
	private Long artPed;
	private Float cajPed;
	private Long artNsr;
	private Float cajNoServ;
	private Long artEnt;
	private Float cajEnt;
	private Long albTot;
	private Long albConf;
	private Long codArt;
	private Long codArtATransformar;
	private String tipo;
	private String mapa;
	
	//Campos para control de errores
	private Long codError;
	private String descError;

	public VMisSeguimientos() {
	    super();
	}

	public VMisSeguimientos(Long nivel, Long ident, Long parentident,
			Long codCentro, Date fechaPrevisEnt, Long grupo1, Long grupo2,
			Long grupo3, String descripcion, Long artPed, Float cajPed,
			Long artNsr, Float cajNoServ, Long artEnt, Float cajEnt,
			Long albTot, Long albConf, Long codArt, String tipo) {
		super();
		this.nivel = nivel;
		this.ident = ident;
		this.parentident = parentident;
		this.codCentro = codCentro;
		this.fechaPrevisEnt = fechaPrevisEnt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.descripcion = descripcion;
		this.artPed = artPed;
		this.cajPed = cajPed;
		this.artNsr = artNsr;
		this.cajNoServ = cajNoServ;
		this.artEnt = artEnt;
		this.cajEnt = cajEnt;
		this.albTot = albTot;
		this.albConf = albConf;
		this.codArt = codArt;
		this.tipo = tipo;
	}

	public Long getNivel() {
		return this.nivel;
	}

	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}

	public Long getIdent() {
		return this.ident;
	}

	public void setIdent(Long ident) {
		this.ident = ident;
	}

	public Long getParentident() {
		return this.parentident;
	}

	public void setParentident(Long parentident) {
		this.parentident = parentident;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Date getFechaPrevisEnt() {
		return this.fechaPrevisEnt;
	}

	public void setFechaPrevisEnt(Date fechaPrevisEnt) {
		this.fechaPrevisEnt = fechaPrevisEnt;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getArtPed() {
		return this.artPed;
	}

	public void setArtPed(Long artPed) {
		this.artPed = artPed;
	}

	public Float getCajPed() {
		return this.cajPed;
	}

	public void setCajPed(Float cajPed) {
		this.cajPed = cajPed;
	}

	public Long getArtNsr() {
		return this.artNsr;
	}

	public void setArtNsr(Long artNsr) {
		this.artNsr = artNsr;
	}

	public Float getCajNoServ() {
		return this.cajNoServ;
	}

	public void setCajNoServ(Float cajNoServ) {
		this.cajNoServ = cajNoServ;
	}

	public Long getArtEnt() {
		return this.artEnt;
	}

	public void setArtEnt(Long artEnt) {
		this.artEnt = artEnt;
	}

	public Float getCajEnt() {
		return this.cajEnt;
	}

	public void setCajEnt(Float cajEnt) {
		this.cajEnt = cajEnt;
	}

	public Long getAlbTot() {
		return this.albTot;
	}

	public void setAlbTot(Long albTot) {
		this.albTot = albTot;
	}

	public Long getAlbConf() {
		return this.albConf;
	}

	public void setAlbConf(Long albConf) {
		this.albConf = albConf;
	}
	
	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}	
	
	public Long getCodArtATransformar() {
		return codArtATransformar;
	}

	public void setCodArtATransformar(Long codArtATransformar) {
		this.codArtATransformar = codArtATransformar;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getCodError() {
		return this.codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return this.descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	@Override
	public String toString() {
		return "VMisSeguimientos [nivel=" + nivel + ", ident=" + ident + ", parentident=" + parentident + ", codCentro="
				+ codCentro + ", fechaPrevisEnt=" + fechaPrevisEnt + ", grupo1=" + grupo1 + ", grupo2=" + grupo2
				+ ", grupo3=" + grupo3 + ", descripcion=" + descripcion + ", artPed=" + artPed + ", cajPed=" + cajPed
				+ ", artNsr=" + artNsr + ", cajNoServ=" + cajNoServ + ", artEnt=" + artEnt + ", cajEnt=" + cajEnt
				+ ", albTot=" + albTot + ", albConf=" + albConf + ", codArt=" + codArt + ", codArtATransformar="
				+ codArtATransformar + ", tipo=" + tipo + ", mapa=" + mapa + ", codError=" + codError + ", descError="
				+ descError + "]";
	}
}