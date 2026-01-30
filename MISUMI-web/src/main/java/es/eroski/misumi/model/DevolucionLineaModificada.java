package es.eroski.misumi.model;

import java.io.Serializable;

public class DevolucionLineaModificada implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long bulto;
	private Double stockDevuelto;
	private Double stockDevolver;
	private Double stockActual;
	private Long codError;
	private Long codArticulo;
	private Long stockDevueltoBandejas;
	private String estadoCerrado;
	private String bultoEstadoCerrado;

	public DevolucionLineaModificada() {
		super();
	}

	public DevolucionLineaModificada(Long bulto, Double stockDevuelto, Long codError, Long codArticulo) {
		super();
		this.bulto = bulto;
		this.stockDevuelto = stockDevuelto;
	
		this.codError = codError;
		this.codArticulo = codArticulo;
	}

	public DevolucionLineaModificada(Long bulto, Double stockDevuelto, Double stockActual, Long codError,
			Long codArticulo, Long stockDevueltoBandejas) {
		super();
		this.bulto = bulto;
		this.stockDevuelto = stockDevuelto;
	
		this.stockActual = stockActual;
		this.codError = codError;
		this.codArticulo = codArticulo;
		this.stockDevueltoBandejas = stockDevueltoBandejas;
	}
	
	public DevolucionLineaModificada(Long bulto, Double stockDevuelto, Double stockActual, Long codError,
			Long codArticulo) {
		super();
		this.bulto = bulto;
		this.stockDevuelto = stockDevuelto;
	
		this.stockActual = stockActual;
		this.codError = codError;
		this.codArticulo = codArticulo;
	}
	
	public DevolucionLineaModificada(Long bulto, Double stockDevuelto, Double stockDevolver, Double stockActual
									, Long codError,Long codArticulo) {
		super();
		this.bulto = bulto;
		this.stockDevuelto = stockDevuelto;
		this.stockDevolver = stockDevolver;
		this.stockActual = stockActual;
		this.codError = codError;
		this.codArticulo = codArticulo;
	}
	public DevolucionLineaModificada(Long bulto, Double stockDevuelto, Double stockDevolver, Double stockActual
									, Long codError, Long codArticulo, Long stockDevueltoBandejas) {
		super();
		this.bulto = bulto;
		this.stockDevuelto = stockDevuelto;
		this.stockDevolver = stockDevolver;
		this.stockActual = stockActual;
		this.codError = codError;
		this.codArticulo = codArticulo;
		this.stockDevueltoBandejas = stockDevueltoBandejas;
	}
	
	public Long getBulto() {
		return bulto;
	}
	public void setBulto(Long bulto) {
		this.bulto = bulto;
	}
	public Double getStockDevuelto() {
		return stockDevuelto;
	}
	public void setStockDevuelto(Double stockDevuelto) {
		this.stockDevuelto = stockDevuelto;
	}
	public Double getStockDevolver() {
		return stockDevolver;
	}
	public void setStockDevolver(Double stockDevolver) {
		this.stockDevolver = stockDevolver;
	}
	public Double getStockActual() {
		return stockActual;
	}
	public void setStockActual(Double stockActual) {
		this.stockActual = stockActual;
	}
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public Long getCodArticulo() {
		return codArticulo;
	}
	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	public Long getStockDevueltoBandejas() {
		return stockDevueltoBandejas;
	}
	public void setStockDevueltoBandejas(Long stockDevueltoBandejas) {
		this.stockDevueltoBandejas = stockDevueltoBandejas;
	}
	public String getEstadoCerrado() {
		return estadoCerrado;
	}
	public void setBultoEstadoCerrado(String bultoEstadoCerrado) {
		this.bultoEstadoCerrado = bultoEstadoCerrado;
	}	
	public String getBultoEstadoCerrado() {
		return bultoEstadoCerrado;
	}
	public void setEstadoCerrado(String bultoEstadoCerrado) {
		this.bultoEstadoCerrado = bultoEstadoCerrado;
	}	
}
