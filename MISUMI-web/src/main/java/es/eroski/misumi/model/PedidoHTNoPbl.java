package es.eroski.misumi.model;

import java.io.Serializable;

public class PedidoHTNoPbl implements Cloneable,Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long identificador;
	private Long codArticulo;
	private String anoOferta;
	private String numOferta;
	private String descriptionArt;
	private Double uniCajaServ;
	private String tipoAprovisionamiento;
	private String agrupacion;
	private String fechaInicio;
	private String fecha2;
	private String fecha3;
	private String fecha4;
	private String fecha5;
	private String fechaFin;
	private Double cantidad1;
	private Double cantidad2;
	private Double cantidad3;
	private Double cantidad4;
	private Double cantidad5;
	private String descOferta;
	private Double cantMin;
	private Double cantMax;
	private String fechaHasta;
	private Long clasePedido;
	private String tipoPedido;
	
	//Campos adicionales de búsqueda
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private String validados;

	public PedidoHTNoPbl() {
	    super();
	}
	
	public PedidoHTNoPbl(Long codCentro, Long identificador, Long codArticulo,
			String anoOferta, String numOferta, String descriptionArt,
			Double uniCajaServ, String tipoAprovisionamiento,
			String agrupacion, String fechaInicio, String fecha2,
			String fecha3, String fecha4, String fecha5, String fechaFin,
			Double cantidad1, Double cantidad2, Double cantidad3,
			Double cantidad4, Double cantidad5, String descOferta,
			Double cantMin, Double cantMax, String fechaHasta,
			Long clasePedido, String tipoPedido, Long grupo1, Long grupo2,
			Long grupo3, String validados) {
		super();
		this.codCentro = codCentro;
		this.identificador = identificador;
		this.codArticulo = codArticulo;
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
		this.descriptionArt = descriptionArt;
		this.uniCajaServ = uniCajaServ;
		this.tipoAprovisionamiento = tipoAprovisionamiento;
		this.agrupacion = agrupacion;
		this.fechaInicio = fechaInicio;
		this.fecha2 = fecha2;
		this.fecha3 = fecha3;
		this.fecha4 = fecha4;
		this.fecha5 = fecha5;
		this.fechaFin = fechaFin;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.cantidad3 = cantidad3;
		this.cantidad4 = cantidad4;
		this.cantidad5 = cantidad5;
		this.descOferta = descOferta;
		this.cantMin = cantMin;
		this.cantMax = cantMax;
		this.fechaHasta = fechaHasta;
		this.clasePedido = clasePedido;
		this.tipoPedido = tipoPedido;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.validados = validados;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getAnoOferta() {
		return this.anoOferta;
	}

	public void setAnoOferta(String anoOferta) {
		this.anoOferta = anoOferta;
	}

	public String getNumOferta() {
		return this.numOferta;
	}

	public void setNumOferta(String numOferta) {
		this.numOferta = numOferta;
	}

	public String getDescriptionArt() {
		return this.descriptionArt;
	}

	public void setDescriptionArt(String descriptionArt) {
		this.descriptionArt = descriptionArt;
	}

	public Double getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(Double uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}

	public String getTipoAprovisionamiento() {
		return this.tipoAprovisionamiento;
	}

	public void setTipoAprovisionamiento(String tipoAprovisionamiento) {
		this.tipoAprovisionamiento = tipoAprovisionamiento;
	}

	public String getAgrupacion() {
		return this.agrupacion;
	}

	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
	}

	public String getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFecha2() {
		return this.fecha2;
	}

	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
	}

	public String getFecha3() {
		return this.fecha3;
	}

	public void setFecha3(String fecha3) {
		this.fecha3 = fecha3;
	}

	public String getFecha4() {
		return this.fecha4;
	}

	public void setFecha4(String fecha4) {
		this.fecha4 = fecha4;
	}

	public String getFecha5() {
		return this.fecha5;
	}

	public void setFecha5(String fecha5) {
		this.fecha5 = fecha5;
	}

	public String getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Double getCantidad1() {
		return this.cantidad1;
	}

	public void setCantidad1(Double cantidad1) {
		this.cantidad1 = cantidad1;
	}

	public Double getCantidad2() {
		return this.cantidad2;
	}

	public void setCantidad2(Double cantidad2) {
		this.cantidad2 = cantidad2;
	}

	public Double getCantidad3() {
		return this.cantidad3;
	}

	public void setCantidad3(Double cantidad3) {
		this.cantidad3 = cantidad3;
	}

	public Double getCantidad4() {
		return this.cantidad4;
	}

	public void setCantidad4(Double cantidad4) {
		this.cantidad4 = cantidad4;
	}

	public Double getCantidad5() {
		return this.cantidad5;
	}

	public void setCantidad5(Double cantidad5) {
		this.cantidad5 = cantidad5;
	}

	public String getDescOferta() {
		return this.descOferta;
	}

	public void setDescOferta(String descOferta) {
		this.descOferta = descOferta;
	}

	public Double getCantMin() {
		return this.cantMin;
	}

	public void setCantMin(Double cantMin) {
		this.cantMin = cantMin;
	}

	public Double getCantMax() {
		return this.cantMax;
	}

	public void setCantMax(Double cantMax) {
		this.cantMax = cantMax;
	}

	public String getFechaHasta() {
		return this.fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Long getClasePedido() {
		return this.clasePedido;
	}

	public void setClasePedido(Long clasePedido) {
		this.clasePedido = clasePedido;
	}

	public String getTipoPedido() {
		return this.tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public Long getGrupo1() {
		return this.grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public Long getGrupo2() {
		return this.grupo2;
	}

	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}

	public Long getGrupo3() {
		return this.grupo3;
	}

	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}

	public String getValidados() {
		return this.validados;
	}

	public void setValidados(String validados) {
		this.validados = validados;
	}
}