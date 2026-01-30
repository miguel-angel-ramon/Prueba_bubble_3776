package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class EncargosClientePlataforma implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private String area;
	private String seccion;
	private String categoria;
	private String subcategoria;
	private String segmento;
	private Long codReferencia;
	private String descripcionArt;
	private Double unidadesCaja;
	private String idSession;
	private Date fechaCreacion;
	
	private String generico;
	private String vitrina;
	
	private String flgEspec;
	
	private String tieneFoto;
	
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getSubcategoria() {
		return subcategoria;
	}
	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}
	public String getSegmento() {
		return segmento;
	}
	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}
	public Long getCodReferencia() {
		return codReferencia;
	}
	public void setCodReferencia(Long codReferencia) {
		this.codReferencia = codReferencia;
	}
	public String getDescripcionArt() {
		return descripcionArt;
	}
	public void setDescripcionArt(String descripcionArt) {
		this.descripcionArt = descripcionArt;
	}
	public Double getUnidadesCaja() {
		return unidadesCaja;
	}
	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}
	public String getIdSession() {
		return idSession;
	}
	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getGenerico() {
		return generico;
	}
	public void setGenerico(String generico) {
		this.generico = generico;
	}
	public String getVitrina() {
		return vitrina;
	}
	public void setVitrina(String vitrina) {
		this.vitrina = vitrina;
	}
	public String getFlgEspec() {
		return flgEspec;
	}
	public void setFlgEspec(String flgEspec) {
		this.flgEspec = flgEspec;
	}
	
	public String getTieneFoto() {
		return tieneFoto;
	}

	public void setTieneFoto(String tieneFoto) {
		this.tieneFoto = tieneFoto;
	}	

}
