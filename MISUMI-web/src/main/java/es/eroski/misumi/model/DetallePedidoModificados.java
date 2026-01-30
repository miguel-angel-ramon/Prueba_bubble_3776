package es.eroski.misumi.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

public class DetallePedidoModificados implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	
	// referencia
	private Long codArticulo;
	private Long cantidad;
	private Long cantidadOriginal;
	private Long cantidadUltValida;
	private String estadoGrid;
	private String codigoError;
	private Long grupo1;
	// Seccion
	private Long grupo2;
	// Categoria
	private Long grupo3;
	// SubCategoria
	private Long grupo4;
	// Segmento
	private Long grupo5;
	
	private String fechaPedido;
	private Double stock;
	private Double enCurso1;
	private Double enCurso2;
	private Double unidadesCaja;
	private Long cajasPedidas;
	private Long propuesta;
	private String tipoDetallado;
	private String estadoPedido;
	private String descriptionArt;
	private String resultadoWS;
	
	private Long cantidadAnt;
	private Long codNecesidad;
	
	private String flgSIA;
	private Long codMapa;

	public DetallePedidoModificados() {
		super();
	}

	public DetallePedidoModificados(Long codArticulo, Long cantidad,
			String estadoGrid, Long grupo1, Long grupo2, Long grupo3,
			Long grupo4, Long grupo5, String fechaPedido, Double stock,
			Double enCurso1, Double enCurso2, Double unidadesCaja,
			Long cajasPedidas, Long propuesta, String tipoDetallado,
			String estadoPedido, String descriptionArt) {
		super();
		this.codArticulo = codArticulo;
		this.cantidad = cantidad;
		this.estadoGrid = estadoGrid;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.fechaPedido = fechaPedido;
		this.stock = stock;
		this.enCurso1 = enCurso1;
		this.enCurso2 = enCurso2;
		this.unidadesCaja = unidadesCaja;
		this.cajasPedidas = cajasPedidas;
		this.propuesta = propuesta;
		this.tipoDetallado = tipoDetallado;
		this.estadoPedido = estadoPedido;
		this.descriptionArt = descriptionArt;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	
	public Long getCantidadOriginal() {
		return cantidadOriginal;
	}

	public void setCantidadOriginal(Long cantidadOriginal) {
		this.cantidadOriginal = cantidadOriginal;
	}

	public Long getCantidadUltValida() {
		return cantidadUltValida;
	}

	public void setCantidadUltValida(Long cantidadUltValida) {
		this.cantidadUltValida = cantidadUltValida;
	}

	public String getEstadoGrid() {
		return estadoGrid;
	}

	public void setEstadoGrid(String estadoGrid) {
		this.estadoGrid = estadoGrid;
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

	public Long getGrupo4() {
		return grupo4;
	}

	public void setGrupo4(Long grupo4) {
		this.grupo4 = grupo4;
	}

	public Long getGrupo5() {
		return grupo5;
	}

	public void setGrupo5(Long grupo5) {
		this.grupo5 = grupo5;
	}
	@JsonIgnore
	public String getFechaPedido() {
		return fechaPedido;
	}
	@JsonIgnore
	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Double getEnCurso1() {
		return enCurso1;
	}

	public void setEnCurso1(Double enCurso1) {
		this.enCurso1 = enCurso1;
	}

	public Double getEnCurso2() {
		return enCurso2;
	}

	public void setEnCurso2(Double enCurso2) {
		this.enCurso2 = enCurso2;
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

	public Long getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(Long propuesta) {
		this.propuesta = propuesta;
	}

	public String getTipoDetallado() {
		return tipoDetallado;
	}

	public void setTipoDetallado(String tipoDetallado) {
		this.tipoDetallado = tipoDetallado;
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

	public Long getCantidadAnt() {
		return cantidadAnt;
	}

	public void setCantidadAnt(Long cantidadAnt) {
		this.cantidadAnt = cantidadAnt;
	}
	
	
	public Long getCodNecesidad() {
		return codNecesidad;
	}

	public void setCodNecesidad(Long codNecesidad) {
		this.codNecesidad = codNecesidad;
	}

	public String getFlgSIA() {
		return flgSIA;
	}

	public void setFlgSIA(String flgSIA) {
		this.flgSIA = flgSIA;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	
	public Long getCodMapa() {
		return codMapa;
	}

	public void setCodMapa(Long codMapa) {
		this.codMapa = codMapa;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
	
}