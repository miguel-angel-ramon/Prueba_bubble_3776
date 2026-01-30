package es.eroski.misumi.model;

import java.io.Serializable;

public class MotivoTengoMuchoPoco implements Cloneable,Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private String tipo;
	private String idSesion;
	private Long codArticulo;
	private Double stockBajo;
	private Double stockAlto;
	private String descripcion;
	private Double stock;
	
	public MotivoTengoMuchoPoco() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MotivoTengoMuchoPoco(Long codCentro, String tipo, String idSesion,
			Long codArticulo, Double stockBajo, Double stockAlto,
			String descripcion, Double stock) {
		super();
		this.codCentro = codCentro;
		this.tipo = tipo;
		this.idSesion = idSesion;
		this.codArticulo = codArticulo;
		this.stockBajo = stockBajo;
		this.stockAlto = stockAlto;
		this.descripcion = descripcion;
		this.stock = stock;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdSesion() {
		return this.idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Double getStockBajo() {
		return this.stockBajo;
	}

	public void setStockBajo(Double stockBajo) {
		this.stockBajo = stockBajo;
	}

	public Double getStockAlto() {
		return this.stockAlto;
	}

	public void setStockAlto(Double stockAlto) {
		this.stockAlto = stockAlto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Double getStock() {
		return this.stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}
}