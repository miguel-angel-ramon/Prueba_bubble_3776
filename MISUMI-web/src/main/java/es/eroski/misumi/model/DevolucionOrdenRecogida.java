package es.eroski.misumi.model;

import java.io.Serializable;

public class DevolucionOrdenRecogida implements Serializable {

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
	private Long codLocalizador;
	private String fecha;
	private String hora;
	private String marca;
	private String formato;
	private String tipo;
	private Long cantidad;

	public DevolucionOrdenRecogida() {
		super();
	}

	public DevolucionOrdenRecogida(Long codCentro, Long codArticulo, String descriptionArt, Long grupo1, Long grupo2,
			Long grupo3, Long grupo4, Long grupo5, Long codProveedor, String descriptionProveedor,
			String fechaDevolucion, Long codLocalizador, String fecha, String hora, String marca, String formato,
			String tipo, Long cantidad) {
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
		this.codLocalizador = codLocalizador;
		this.fecha = fecha;
		this.hora = hora;
		this.marca = marca;
		this.formato = formato;
		this.tipo = tipo;
		this.cantidad = cantidad;
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

	public Long getCodLocalizador() {
		return codLocalizador;
	}

	public void setCodLocalizador(Long codLocalizador) {
		this.codLocalizador = codLocalizador;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

}