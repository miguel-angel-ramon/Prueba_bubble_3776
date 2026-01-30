package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VMisCampanaOfer implements Serializable{
	private static final long serialVersionUID = 1L;

	private String idSesion;
	private Long nivel;
	private String ident;
	private String parentident;
	private String identificador;
	private Long codCentro;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private String descripcion;
	private String tipoOC;
	private String tipo;
	private Long codArt;
	private Long anoOferta;
	private Long numOferta;
	private Date fInicio;
	private Date fFin;
	private Float cantPrevista;
	private Float cantPrevistaVenta;
	private Float ventaMedia;
	private Float ventaTotal;
	private Float cantImp;
	private Float totalImpEmpServ;
	private Float cantServida;
	private Long nsrNumero;
	private Float nsrUnidades;
	
	//Campos para control de errores
	private Long codError;
	private String descError;
	
	//Campo de oferta auxiliar para paso por parámetro desde pantalla
	private String oferta;
	
	public VMisCampanaOfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VMisCampanaOfer(String idSesion, Long nivel, String ident,
			String parentident, String identificador, Long codCentro,
			Long grupo1, Long grupo2, Long grupo3, Long grupo4,
			String descripcion, String tipoOC, String tipo, Long codArt,
			Long anoOferta, Long numOferta, Date fInicio, Date fFin,
			Float cantPrevista, Float cantPrevistaVenta, Float ventaMedia, Float ventaTotal,
			Float cantImp, Float totalImpEmpServ, Float cantServida,
			Long nsrNumero, Float nsrUnidades) {
		super();
		this.idSesion = idSesion;
		this.nivel = nivel;
		this.ident = ident;
		this.parentident = parentident;
		this.identificador = identificador;
		this.codCentro = codCentro;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.descripcion = descripcion;
		this.tipoOC = tipoOC;
		this.tipo = tipo;
		this.codArt = codArt;
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
		this.fInicio = fInicio;
		this.fFin = fFin;
		this.cantPrevista = cantPrevista;
		this.cantPrevistaVenta = cantPrevistaVenta;
		this.ventaMedia = ventaMedia;
		this.ventaTotal = ventaTotal;
		this.cantImp = cantImp;
		this.totalImpEmpServ = totalImpEmpServ;
		this.cantServida = cantServida;
		this.nsrNumero = nsrNumero;
		this.nsrUnidades = nsrUnidades;
	}

	public String getIdSesion() {
		return this.idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public Long getNivel() {
		return this.nivel;
	}

	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}

	public String getIdent() {
		return this.ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getParentident() {
		return this.parentident;
	}

	public void setParentident(String parentident) {
		this.parentident = parentident;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoOC() {
		return this.tipoOC;
	}

	public void setTipoOC(String tipoOC) {
		this.tipoOC = tipoOC;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public Date getFInicio() {
		return this.fInicio;
	}

	public void setFInicio(Date fInicio) {
		this.fInicio = fInicio;
	}

	public Date getFFin() {
		return this.fFin;
	}

	public void setFFin(Date fFin) {
		this.fFin = fFin;
	}

	public Float getCantPrevista() {
		return this.cantPrevista;
	}

	public void setCantPrevista(Float cantPrevista) {
		this.cantPrevista = cantPrevista;
	}

	public Float getCantPrevistaVenta() {
		return this.cantPrevistaVenta;
	}

	public void setCantPrevistaVenta(Float cantPrevistaVenta) {
		this.cantPrevistaVenta = cantPrevistaVenta;
	}

	public Float getVentaMedia() {
		return this.ventaMedia;
	}

	public void setVentaMedia(Float ventaMedia) {
		this.ventaMedia = ventaMedia;
	}

	public Float getVentaTotal() {
		return this.ventaTotal;
	}

	public void setVentaTotal(Float ventaTotal) {
		this.ventaTotal = ventaTotal;
	}

	public Float getCantImp() {
		return this.cantImp;
	}

	public void setCantImp(Float cantImp) {
		this.cantImp = cantImp;
	}

	public Float getTotalImpEmpServ() {
		return this.totalImpEmpServ;
	}

	public void setTotalImpEmpServ(Float totalImpEmpServ) {
		this.totalImpEmpServ = totalImpEmpServ;
	}

	public Float getCantServida() {
		return this.cantServida;
	}

	public void setCantServida(Float cantServida) {
		this.cantServida = cantServida;
	}

	public Long getNsrNumero() {
		return this.nsrNumero;
	}

	public void setNsrNumero(Long nsrNumero) {
		this.nsrNumero = nsrNumero;
	}

	public Float getNsrUnidades() {
		return this.nsrUnidades;
	}

	public void setNsrUnidades(Float nsrUnidades) {
		this.nsrUnidades = nsrUnidades;
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
	
	public String getOferta() {
		return this.oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}

}