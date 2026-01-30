package es.eroski.misumi.model;

import java.io.Serializable;

public class StockPlataforma implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codArt;
	private Long stock;
	
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public Long getCodArt() {
		return codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}

}
