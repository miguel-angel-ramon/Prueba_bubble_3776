package es.eroski.misumi.model;

import java.io.Serializable;

public class VPescaMostrador implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long area;
	private Long seccion;
	private Long categoria;
	private Long subcategoria;
	private Long segmento;
	private String descSubcategoria;
	private Long ordenListado; 
	private String identificaSubcat;
	private Long codArt; 
	private String denominacion;
	private Double unidadesCaja;
	private Long ean; 
	private Double importeTirado;
	private Long porImpTirado; 
	private Long propPedir; 
	private Long pedMananaCajas; 
	private String ofertaVigorIni; 
	private String ofertaVigorFin; 
	private String ofertaFuturaIni;
	private String ofertaFuturaFin;
	private String flgHabitual;
	private boolean pedirHoy = false; 
	
	
	public VPescaMostrador() {
		super();
	}
	
	public VPescaMostrador(Long codCentro, Long area, Long seccion, Long categoria, Long subcategoria, Long segmento,
			String descSubcategoria, Long ordenListado, String identificaSubcat, Long codArt, String denominacion,
			Double unidadesCaja, Long ean, Double importeTirado, Long porImpTirado, Long propPedir, Long pedMananaCajas,
			String ofertaVigorIni, String ofertaVigorFin, String ofertaFuturaIni, String ofertaFuturaFin, String flgHabitual, boolean pedirHoy) {
		super();
		this.codCentro = codCentro;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.descSubcategoria = descSubcategoria;
		this.ordenListado = ordenListado;
		this.identificaSubcat = identificaSubcat;
		this.codArt = codArt;
		this.denominacion = denominacion;
		this.unidadesCaja = unidadesCaja;
		this.ean = ean;
		this.importeTirado = importeTirado;
		this.porImpTirado = porImpTirado;
		this.propPedir = propPedir;
		this.pedMananaCajas = pedMananaCajas;
		this.ofertaVigorIni = ofertaVigorIni;
		this.ofertaVigorFin = ofertaVigorFin;
		this.ofertaFuturaIni = ofertaFuturaIni;
		this.ofertaFuturaFin = ofertaFuturaFin;
		this.flgHabitual = flgHabitual;
		this.pedirHoy = pedirHoy;
	}

	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public Long getArea() {
		return area;
	}
	public void setArea(Long area) {
		this.area = area;
	}
	public Long getSeccion() {
		return seccion;
	}
	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}
	public Long getCategoria() {
		return categoria;
	}
	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}
	public Long getSubcategoria() {
		return subcategoria;
	}
	public void setSubcategoria(Long subcategoria) {
		this.subcategoria = subcategoria;
	}
	public Long getSegmento() {
		return segmento;
	}
	public void setSegmento(Long segmento) {
		this.segmento = segmento;
	}
	public String getDescSubcategoria() {
		return descSubcategoria;
	}
	public void setDescSubcategoria(String descSubcategoria) {
		this.descSubcategoria = descSubcategoria;
	}
	public Long getOrdenListado() {
		return ordenListado;
	}
	public void setOrdenListado(Long ordenListado) {
		this.ordenListado = ordenListado;
	}
	public String getIdentificaSubcat() {
		return identificaSubcat;
	}
	public void setIdentificaSubcat(String identificaSubcat) {
		this.identificaSubcat = identificaSubcat;
	}
	public Long getCodArt() {
		return codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public Double getUnidadesCaja() {
		return unidadesCaja;
	}
	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}
	public Long getEan() {
		return ean;
	}
	public void setEan(Long ean) {
		this.ean = ean;
	}
	public Double getImporteTirado() {
		return importeTirado;
	}
	public void setImporteTirado(Double importeTirado) {
		this.importeTirado = importeTirado;
	}
	public Long getPorImpTirado() {
		return porImpTirado;
	}
	public void setPorImpTirado(Long porImpTirado) {
		this.porImpTirado = porImpTirado;
	}
	public Long getPropPedir() {
		return propPedir;
	}
	public void setPropPedir(Long propPedir) {
		this.propPedir = propPedir;
	}
	public Long getPedMananaCajas() {
		return pedMananaCajas;
	}
	public void setPedMananaCajas(Long pedMananaCajas) {
		this.pedMananaCajas = pedMananaCajas;
	}
	public String getOfertaVigorIni() {
		return ofertaVigorIni;
	}
	public void setOfertaVigorIni(String ofertaVigorIni) {
		this.ofertaVigorIni = ofertaVigorIni;
	}
	public String getOfertaVigorFin() {
		return ofertaVigorFin;
	}
	public void setOfertaVigorFin(String ofertaVigorFin) {
		this.ofertaVigorFin = ofertaVigorFin;
	}
	public String getOfertaFuturaIni() {
		return ofertaFuturaIni;
	}
	public void setOfertaFuturaIni(String ofertaFuturaIni) {
		this.ofertaFuturaIni = ofertaFuturaIni;
	}
	public String getOfertaFuturaFin() {
		return ofertaFuturaFin;
	}
	public void setOfertaFuturaFin(String ofertaFuturaFin) {
		this.ofertaFuturaFin = ofertaFuturaFin;
	}
	public String getFlgHabitual() {
		return flgHabitual;
	}
	public void setFlgHabitual(String flgHabitual) {
		this.flgHabitual = flgHabitual;
	}
	public boolean isPedirHoy() {
		return pedirHoy;
	}
	public void setPedirHoy(boolean pedirHoy) {
		this.pedirHoy = pedirHoy;
	} 
}