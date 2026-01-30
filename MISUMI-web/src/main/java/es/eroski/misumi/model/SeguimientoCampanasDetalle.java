package es.eroski.misumi.model;

import java.io.Serializable;

public class SeguimientoCampanasDetalle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Centro centro;
	private Long referencia;
	private String descripcion;
	private String servidoPendiente;
	private String noServido;
	private String ventasPrevision;
	private String stock;
	
	 //Campos Para Textil
    private String color;
	private String talla;
    private String modeloProveedor;

    public SeguimientoCampanasDetalle() {
		super();
	}

	public SeguimientoCampanasDetalle(Centro centro, Long referencia, String descripcion,
			String servidoPendiente, String noServido, String ventasPrevision,
			String stock,String color ,String talla ,String modelo_proveedor ) {
		super();
		this.centro = centro;
		this.referencia = referencia;
		this.descripcion = descripcion;
		this.servidoPendiente = servidoPendiente;
		this.noServido = noServido;
		this.ventasPrevision = ventasPrevision;
		this.stock = stock;
		this.color = color;
		this.talla = talla;
		this.modeloProveedor= modelo_proveedor;
	}
	

	public Centro getCentro() {
		return this.centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public Long getReferencia() {
		return this.referencia;
	}

	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getServidoPendiente() {
		return this.servidoPendiente;
	}

	public void setServidoPendiente(String servidoPendiente) {
		this.servidoPendiente = servidoPendiente;
	}

	public String getNoServido() {
		return this.noServido;
	}

	public void setNoServido(String noServido) {
		this.noServido = noServido;
	}

	public String getVentasPrevision() {
		return this.ventasPrevision;
	}

	public void setVentasPrevision(String ventasPrevision) {
		this.ventasPrevision = ventasPrevision;
	}

	public String getStock() {
		return this.stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}


}