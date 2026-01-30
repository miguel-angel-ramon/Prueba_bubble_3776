package es.eroski.misumi.model.pda.prehuecos;

import java.io.Serializable;

public class StockAlmacen  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codArt;
	private String stockLineal;
	private String stock;
	private String descArt;
	private String fecha;
	private Long estado;
	private Long pagTotal;

	public StockAlmacen() {
		super();
	}

	public Long getCodArt() {
		return codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getStockLineal() {
		return stockLineal;
	}

	public void setStockLineal(String stockLineal) {
		this.stockLineal = stockLineal;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getDescArt() {
		return descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Long getPagTotal() {
		return pagTotal;
	}

	public void setPagTotal(Long pagTotal) {
		this.pagTotal = pagTotal;
	}

}
