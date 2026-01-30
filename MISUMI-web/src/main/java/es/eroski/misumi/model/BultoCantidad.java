package es.eroski.misumi.model;

import java.io.Serializable;

//Equivalen a cada línea de la tabla relacionada con una devolución.
public class BultoCantidad implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Double stock;
	private Long bulto;
	private String estadoCerrado;
	private Long stockBandejas;
	private Long codError;
    private String descError;
   
	
    public BultoCantidad() {
		super();
	}

	public BultoCantidad(Double stock, Long bulto, String estadoCerrado, Long stockBandejas, Long codError, String descError) {
		super();
		this.stock = stock;
		this.bulto = bulto;
		this.estadoCerrado = estadoCerrado;
		this.stockBandejas = stockBandejas;
		this.codError = codError;
		this.descError = descError;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Long getBulto() {
		return bulto;
	}

	public void setBulto(Long bulto) {
		this.bulto = bulto;
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

	public String getEstadoCerrado() {
		return estadoCerrado;
	}

	public void setEstadoCerrado(String estadoCerrado) {
		this.estadoCerrado = estadoCerrado;
	}

	public Long getStockBandejas() {
		return stockBandejas;
	}

	public void setStockBandejas(Long stockBandejas) {
		this.stockBandejas = stockBandejas;
	}

}
