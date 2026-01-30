package es.eroski.misumi.model;

import java.io.Serializable;

public class ValoresStock implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Double stock;
	private Double stockBajo;
	private Double sobreStockInferior;
	private Double sobreStockSuperior;
	private String mostrarMotivosStock;
	private Double diasStock;
	private Float ventaMedia;
	private boolean existeVentaMedia;
	private String stockPrincipal;

	private Double stockInicial;
	private Double salidas;
	private Double totalVentaTarifa;
	private Double totalVentaAnticipada;
	private Double totalVentaOferta;
	private Double totalVentaCompetencia;
	private Double totalEntradas;
	private Double totalModifAjuste;
	private Double totalModifRegul;
	private Integer centroParametrizado;
	
	
	//Campo para control de error de WS 
	private int flgErrorWSVentasTienda;

	
	public Double getStock() {
		return stock;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}
	public Double getStockBajo() {
		return stockBajo;
	}
	public void setStockBajo(Double stockBajo) {
		this.stockBajo = stockBajo;
	}
	public Double getSobreStockInferior() {
		return sobreStockInferior;
	}
	public void setSobreStockInferior(Double sobreStockInferior) {
		this.sobreStockInferior = sobreStockInferior;
	}
	public Double getSobreStockSuperior() {
		return sobreStockSuperior;
	}
	public void setSobreStockSuperior(Double sobreStockSuperior) {
		this.sobreStockSuperior = sobreStockSuperior;
	}
	public int getFlgErrorWSVentasTienda() {
		return this.flgErrorWSVentasTienda;
	}
	public void setFlgErrorWSVentasTienda(int flgErrorWSVentasTienda) {
		this.flgErrorWSVentasTienda = flgErrorWSVentasTienda;
	}
	public String getMostrarMotivosStock() {
		return this.mostrarMotivosStock;
	}
	public void setMostrarMotivosStock(String mostrarMotivosStock) {
		this.mostrarMotivosStock = mostrarMotivosStock;
	}
	public Double getDiasStock() {
		return diasStock;
	}
	public void setDiasStock(Double diasStock) {
		this.diasStock = diasStock;
	}
	public Float getVentaMedia() {
		return ventaMedia;
	}
	public void setVentaMedia(Float ventaMedia) {
		this.ventaMedia = ventaMedia;
	}
	public boolean getExisteVentaMedia() {
		return existeVentaMedia;
	}
	public void setExisteVentaMedia(boolean existeVentaMedia) {
		this.existeVentaMedia = existeVentaMedia;
	}
	public String getStockPrincipal() {
		return stockPrincipal;
	}
	public void setStockPrincipal(String stockPrincipal) {
		this.stockPrincipal = stockPrincipal;
	}
	public Double getTotalVentaTarifa() {
		return totalVentaTarifa;
	}
	public void setTotalVentaTarifa(Double totalVentaTarifa) {
		this.totalVentaTarifa = totalVentaTarifa;
	}
	public Double getTotalVentaAnticipada() {
		return totalVentaAnticipada;
	}
	public void setTotalVentaAnticipada(Double totalVentaAnticipada) {
		this.totalVentaAnticipada = totalVentaAnticipada;
	}
	public Double getTotalVentaOferta() {
		return totalVentaOferta;
	}
	public void setTotalVentaOferta(Double totalVentaOferta) {
		this.totalVentaOferta = totalVentaOferta;
	}
	public Double getTotalVentaCompetencia() {
		return totalVentaCompetencia;
	}
	public void setTotalVentaCompetencia(Double totalVentaCompetencia) {
		this.totalVentaCompetencia = totalVentaCompetencia;
	}
	public Double getTotalEntradas() {
		return totalEntradas;
	}
	public void setTotalEntradas(Double totalEntradas) {
		this.totalEntradas = totalEntradas;
	}
	public Double getTotalModifAjuste() {
		return totalModifAjuste;
	}
	public void setTotalModifAjuste(Double totalModifAjuste) {
		this.totalModifAjuste = totalModifAjuste;
	}
	public Double getTotalModifRegul() {
		return totalModifRegul;
	}
	public void setTotalModifRegul(Double totalModifRegul) {
		this.totalModifRegul = totalModifRegul;
	}
	public Double getStockInicial() {
		return stockInicial;
	}
	public void setStockInicial(Double stockInicial) {
		this.stockInicial = stockInicial;
	}
	public Double getSalidas() {
		return salidas;
	}
	public void setSalidas(Double salidas) {
		this.salidas = salidas;
	}
	public Integer getCentroParametrizado() {
		return centroParametrizado;
	}
	public void setCentroParametrizado(Integer centroParametrizado) {
		this.centroParametrizado = centroParametrizado;
	}

	
}
