package es.eroski.misumi.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

public class DetalleMostradorModificados implements Serializable {

	private static final long serialVersionUID = 1L;

	// referencia
	private Long codArticulo;
	private Long propuestaPedido;
	private Long propuestaPedidoAnt;
	private Long propuestaPedidoOriginal;
	private Long propuestaPedidoUltValida;
	private String estadoGrid;
	private Long area;
	// Seccion
	private Long seccion;
	// Categoria
	private Long categoria;
	// SubCategoria
	private Long subcategoria;
	// Segmento
	private Long segmento;
	
	private String fechaPedido;
	private Double pendienteRecibirManana;
	private Double unidadesCaja;
	private Long cajasPedidas;
	private Long previsionVenta;
	private String estadoPedido;
	private String descriptionArt;
	private String resultadoWS;
	
	private String descError;
	
	private Long codNecesidad;

	public DetalleMostradorModificados() {
		super();
	}

	public DetalleMostradorModificados( Long codArticulo, Long propuestaPedido, String estadoGrid
									  , Long area, Long seccion, Long categoria, Long subcategoria, Long segmento
									  , String fechaPedido, Double pendienteRecibirManana, Double unidadesCaja
									  , Long cajasPedidas, Long previsionVenta, String estadoPedido, String descriptionArt
									  ) {
		super();
		this.codArticulo = codArticulo;
		this.propuestaPedido = propuestaPedido;
		this.estadoGrid = estadoGrid;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.fechaPedido = fechaPedido;
		this.pendienteRecibirManana = pendienteRecibirManana;
		this.unidadesCaja = unidadesCaja;
		this.cajasPedidas = cajasPedidas;
		this.previsionVenta = previsionVenta;
		this.estadoPedido = estadoPedido;
		this.descriptionArt = descriptionArt;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getPropuestaPedido() {
		return propuestaPedido;
	}

	public void setPropuestaPedido(Long propuestaPedido) {
		this.propuestaPedido = propuestaPedido;
	}
	
	public Long getPropuestaPedidoOriginal() {
		return propuestaPedidoOriginal;
	}

	public void setPropuestaPedidoOriginal(Long propuestaPedidoOriginal) {
		this.propuestaPedidoOriginal = propuestaPedidoOriginal;
	}

	public Long getPropuestaPedidoUltValida() {
		return propuestaPedidoUltValida;
	}

	public void setPropuestaPedidoUltValida(Long propuestaPedidoUltValida) {
		this.propuestaPedidoUltValida = propuestaPedidoUltValida;
	}

	public String getEstadoGrid() {
		return estadoGrid;
	}

	public void setEstadoGrid(String estadoGrid) {
		this.estadoGrid = estadoGrid;
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
	
	@JsonIgnore
	public String getFechaPedido() {
		return fechaPedido;
	}
	
	@JsonIgnore
	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Double getPendienteRecibirManana() {
		return pendienteRecibirManana;
	}

	public void setPendienteRecibirManana(Double pendienteRecibirManana) {
		this.pendienteRecibirManana = pendienteRecibirManana;
	}

	public Double getUnidadesCaja() {
		return unidadesCaja;
	}

	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}

	public Long getCajasPedidas() {
		return cajasPedidas;
	}

	public void setCajasPedidas(Long cajasPedidas) {
		this.cajasPedidas = cajasPedidas;
	}

	public Long getPrevisionVenta() {
		return previsionVenta;
	}

	public void setPrevisionVenta(Long previsionVenta) {
		this.previsionVenta = previsionVenta;
	}

	public String getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public String getDescriptionArt() {
		return descriptionArt;
	}

	public void setDescriptionArt(String descriptionArt) {
		this.descriptionArt = descriptionArt;
	}

	public String getResultadoWS() {
		return resultadoWS;
	}

	public void setResultadoWS(String resultadoWS) {
		this.resultadoWS = resultadoWS;
	}

	public Long getPropuestaPedidoAnt() {
		return propuestaPedidoAnt;
	}

	public void setPropuestaPedidoAnt(Long propuestaPedidoAnt) {
		this.propuestaPedidoAnt = propuestaPedidoAnt;
	}
	
	public Long getCodNecesidad() {
		return codNecesidad;
	}

	public void setCodNecesidad(Long codNecesidad) {
		this.codNecesidad = codNecesidad;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}
	
}