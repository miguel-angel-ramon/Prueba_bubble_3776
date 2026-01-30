package es.eroski.misumi.model;

import java.io.Serializable;

public class DevolucionOrdenRetirada implements Serializable {

	private static final long serialVersionUID = 1L;

	// referencia
	private Long codCentro;
	private Long codArticulo;
	private String descriptionArt;
	private Long grupo1; // Area
	private Long grupo2; // Seccion
	private Long grupo3; // Categoria
	private Long grupo4; // SubCategoria
	private Long grupo5; // Segmento

	// proveedor
	private Long codProveedor;
	private String descriptionProveedor;

	// devolución
	private String fechaDevolucion;
	private String fechaLimiteDevolucion;
	private String observaciones;
	private Long codLocalizador;
	private String abono;
	private String recogida;
	private String fecha;
	private String hora;
	private String marca;
	private String formato;
	private String lote;
	private String caducidad;
	private Long cantidadDevolverCentro;

	// pedido
	private Double stock;
	private Double unidadesCaja;
	private Long cajasPedidas;

	public DevolucionOrdenRetirada() {
		super();
	}

	public DevolucionOrdenRetirada(Long codCentro, Long codArticulo, String descriptionArt, Long grupo1, Long grupo2,
			Long grupo3, Long grupo4, Long grupo5, Long codProveedor, String descriptionProveedor,
			String fechaDevolucion, String fechaLimiteDevolucion, String observaciones, Long codLocalizador,
			String abono, String recogida, String fecha, String hora, String marca, String formato, String lote,
			String caducidad, Long cantidadDevolverCentro, Double stock, Double unidadesCaja, Long cajasPedidas) {
		super();
		this.codCentro = codCentro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.codProveedor = codProveedor;
		this.descriptionProveedor = descriptionProveedor;
		this.fechaDevolucion = fechaDevolucion;
		this.fechaLimiteDevolucion = fechaLimiteDevolucion;
		this.observaciones = observaciones;
		this.codLocalizador = codLocalizador;
		this.abono = abono;
		this.recogida = recogida;
		this.fecha = fecha;
		this.hora = hora;
		this.marca = marca;
		this.formato = formato;
		this.lote = lote;
		this.caducidad = caducidad;
		this.cantidadDevolverCentro = cantidadDevolverCentro;
		this.stock = stock;
		this.unidadesCaja = unidadesCaja;
		this.cajasPedidas = cajasPedidas;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getDescriptionArt() {
		return descriptionArt;
	}

	public void setDescriptionArt(String descriptionArt) {
		this.descriptionArt = descriptionArt;
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

	public Long getCodProveedor() {
		return codProveedor;
	}

	public void setCodProveedor(Long codProveedor) {
		this.codProveedor = codProveedor;
	}

	public String getDescriptionProveedor() {
		return descriptionProveedor;
	}

	public void setDescriptionProveedor(String descriptionProveedor) {
		this.descriptionProveedor = descriptionProveedor;
	}

	public String getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(String fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public String getFechaLimiteDevolucion() {
		return fechaLimiteDevolucion;
	}

	public void setFechaLimiteDevolucion(String fechaLimiteDevolucion) {
		this.fechaLimiteDevolucion = fechaLimiteDevolucion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Long getCodLocalizador() {
		return codLocalizador;
	}

	public void setCodLocalizador(Long codLocalizador) {
		this.codLocalizador = codLocalizador;
	}

	public String getAbono() {
		return abono;
	}

	public void setAbono(String abono) {
		this.abono = abono;
	}

	public String getRecogida() {
		return recogida;
	}

	public void setRecogida(String recogida) {
		this.recogida = recogida;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}

	public Long getCantidadDevolverCentro() {
		return cantidadDevolverCentro;
	}

	public void setCantidadDevolverCentro(Long cantidadDevolverCentro) {
		this.cantidadDevolverCentro = cantidadDevolverCentro;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
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

}