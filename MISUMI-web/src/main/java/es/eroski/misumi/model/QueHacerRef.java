package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class QueHacerRef implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long codArticulo;
	private String denomInformeRef;
	private String refProveedor;
	private String talla;
	private String color;
	private String flgLote;
	private Long codColeccion;
	private Long codTemporada;
	private String descrTemporadaAbr;
	private String anioColeccion;
	private Date fecInicio;
	private Date fecFin;
	private String converArt;
	private String accion;
	
	//Campos para control de errores
	private Long estado;
	private String descEstado;
	
	//Campos para búsqueda en el plsql
	private Long codArtFormlog;
	private Long codLoc;
	
	//Campo del stock actual
	private String stockActual;
	
	public QueHacerRef() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QueHacerRef(Long codArticulo, String denomInformeRef,
			String refProveedor, String talla, String color, String flgLote,
			Long codColeccion, Long codTemporada, String descrTemporadaAbr,
			String anioColeccion, Date fecInicio, Date fecFin,
			String converArt, String accion, Long estado, String descEstado) {
		super();
		this.codArticulo = codArticulo;
		this.denomInformeRef = denomInformeRef;
		this.refProveedor = refProveedor;
		this.talla = talla;
		this.color = color;
		this.flgLote = flgLote;
		this.codColeccion = codColeccion;
		this.codTemporada = codTemporada;
		this.descrTemporadaAbr = descrTemporadaAbr;
		this.anioColeccion = anioColeccion;
		this.fecInicio = fecInicio;
		this.fecFin = fecFin;
		this.converArt = converArt;
		this.accion = accion;
		this.estado = estado;
		this.descEstado = descEstado;
	}
	
	public Long getCodArticulo() {
		return this.codArticulo;
	}
	
	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	
	public String getDenomInformeRef() {
		return this.denomInformeRef;
	}
	
	public void setDenomInformeRef(String denomInformeRef) {
		this.denomInformeRef = denomInformeRef;
	}
	
	public String getRefProveedor() {
		return this.refProveedor;
	}
	
	public void setRefProveedor(String refProveedor) {
		this.refProveedor = refProveedor;
	}
	
	public String getTalla() {
		return this.talla;
	}
	
	public void setTalla(String talla) {
		this.talla = talla;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	public String getFlgLote() {
		return this.flgLote;
	}
	
	public void setFlgLote(String flgLote) {
		this.flgLote = flgLote;
	}
	
	public Long getCodColeccion() {
		return this.codColeccion;
	}
	
	public void setCodColeccion(Long codColeccion) {
		this.codColeccion = codColeccion;
	}
	
	public Long getCodTemporada() {
		return this.codTemporada;
	}
	
	public void setCodTemporada(Long codTemporada) {
		this.codTemporada = codTemporada;
	}
	
	public String getDescrTemporadaAbr() {
		return this.descrTemporadaAbr;
	}
	
	public void setDescrTemporadaAbr(String descrTemporadaAbr) {
		this.descrTemporadaAbr = descrTemporadaAbr;
	}
	
	public String getAnioColeccion() {
		return this.anioColeccion;
	}
	
	public void setAnioColeccion(String anioColeccion) {
		this.anioColeccion = anioColeccion;
	}
	
	public Date getFecInicio() {
		return this.fecInicio;
	}
	
	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}
	
	public Date getFecFin() {
		return this.fecFin;
	}
	
	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}
	
	public String getConverArt() {
		return this.converArt;
	}
	
	public void setConverArt(String converArt) {
		this.converArt = converArt;
	}
	
	public String getAccion() {
		return this.accion;
	}
	
	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	public Long getEstado() {
		return this.estado;
	}
	
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	public String getDescEstado() {
		return this.descEstado;
	}
	
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

	public Long getCodArtFormlog() {
		return this.codArtFormlog;
	}

	public void setCodArtFormlog(Long codArtFormlog) {
		this.codArtFormlog = codArtFormlog;
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public String getStockActual() {
		return this.stockActual;
	}

	public void setStockActual(String stockActual) {
		this.stockActual = stockActual;
	}
}
