package es.eroski.misumi.model;

import java.io.Serializable;

public class ListadoRefSegundaReposicionSalidaExcel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String area;
	private String seccion;
	private String categoria;
	private String subcategoria;
	private String segmento;
	private String referencia;
	private String referenciaDesc;
	private String facing;
	private String capacidad;
	private String cajaExpositora;
	private String tendencia;
	private String ventaPrevista;
	
	public ListadoRefSegundaReposicionSalidaExcel() {
	    super();
	}

	public ListadoRefSegundaReposicionSalidaExcel(String areaDesc, String seccionDesc, String categoriaDesc, String subcategoriaDesc, String segmentoDesc, 
			String referencia, String referenciaDesc, String facing, String capacidad, String cajaExpositora, String tendencia, String ventaPrevista) {
		super();
		this.area = areaDesc;
		this.seccion = seccionDesc;
		this.categoria = categoriaDesc;
		this.subcategoria = subcategoriaDesc;
		this.segmento = segmentoDesc;
		this.referencia = referencia;
		this.referenciaDesc = referenciaDesc;
		this.facing = facing;
		this.capacidad = capacidad;
		this.cajaExpositora = cajaExpositora;
		this.tendencia = tendencia;
		this.ventaPrevista = ventaPrevista;
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

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getReferenciaDesc() {
		return referenciaDesc;
	}

	public void setReferenciaDesc(String referenciaDesc) {
		this.referenciaDesc = referenciaDesc;
	}

	public String getFacing() {
		return facing;
	}

	public void setFacing(String facing) {
		this.facing = facing;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getCajaExpositora() {
		return cajaExpositora;
	}

	public void setCajaExpositora(String cajaExpositora) {
		this.cajaExpositora = cajaExpositora;
	}

	public String getTendencia() {
		return tendencia;
	}

	public void setTendencia(String tendencia) {
		this.tendencia = tendencia;
	}

	public String getVentaPrevista() {
		return ventaPrevista;
	}

	public void setVentaPrevista(String ventaPrevista) {
		this.ventaPrevista = ventaPrevista;
	}

}