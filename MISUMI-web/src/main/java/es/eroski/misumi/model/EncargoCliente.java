package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class EncargoCliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long localizador;
	private Long codLoc;
	private Long codArticulo;
	private String descripcionArt;
	private Date fechaHoraEncargo;
	
	private String contactoCentro;
	private String telefonoCentro;
	
	private String nombreCliente;
	private String apellidoCliente;
	private String telefonoCliente;
	
	private String tipoEncargo;
	private Date fechaVenta;
	private Date fechaVentaModificada;
	private Boolean fechaInferior;
	
	private String especificacion;
	private Double pesoDesde;
	private Double pesoHasta;
	private Boolean confirmarEspecificacion;
	private Boolean faltaReferencia;
	private Boolean cambioReferencia;
	
	private String confirmarPrecio;
	private Double cantidadEncargo;
	private Double cantidadFinalCompra;
	private Double cantidadServida;
	private Double cantidadNoServida;
	
	private Double unidadesCaja;
	private String estado;
	private String observacionesRechazo;
	private String codigoPedidoInterno;
	
	private Long codError;
	private String descError;
	
	private String flgEspec;
	
	private String descripcionGestadic;
	private String estadoGestadic;
	private String txtDetalleGestadic;
	private String txtSituacionGestadic;
	
	public Long getLocalizador() {
		return localizador;
	}
	public void setLocalizador(Long localizador) {
		this.localizador = localizador;
	}
	public Long getCodLoc() {
		return this.codLoc;
	}
	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}
	public Long getCodArticulo() {
		return this.codArticulo;
	}
	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	public String getDescripcionArt() {
		return this.descripcionArt;
	}
	public void setDescripcionArt(String descripcionArt) {
		this.descripcionArt = descripcionArt;
	}
	public Date getFechaHoraEncargo() {
		return this.fechaHoraEncargo;
	}
	public void setFechaHoraEncargo(Date fechaHoraEncargo) {
		this.fechaHoraEncargo = fechaHoraEncargo;
	}
	public String getContactoCentro() {
		return this.contactoCentro;
	}
	public void setContactoCentro(String contactoCentro) {
		this.contactoCentro = contactoCentro;
	}
	public String getTelefonoCentro() {
		return this.telefonoCentro;
	}
	public void setTelefonoCentro(String telefonoCentro) {
		this.telefonoCentro = telefonoCentro;
	}
	public String getNombreCliente() {
		return this.nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getApellidoCliente() {
		return this.apellidoCliente;
	}
	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
	}
	public String getTelefonoCliente() {
		return this.telefonoCliente;
	}
	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}
	public String getTipoEncargo() {
		return this.tipoEncargo;
	}
	public void setTipoEncargo(String tipoEncargo) {
		this.tipoEncargo = tipoEncargo;
	}
	public Date getFechaVenta() {
		return this.fechaVenta;
	}
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	public Date getFechaVentaModificada() {
		return this.fechaVentaModificada;
	}
	public void setFechaVentaModificada(Date fechaVentaModificada) {
		this.fechaVentaModificada = fechaVentaModificada;
	}
	public Boolean getFechaInferior() {
		return this.fechaInferior;
	}
	public void setFechaInferior(Boolean fechaInferior) {
		this.fechaInferior = fechaInferior;
	}
	public String getEspecificacion() {
		return this.especificacion;
	}
	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}
	public Double getPesoDesde() {
		return this.pesoDesde;
	}
	public void setPesoDesde(Double pesoDesde) {
		this.pesoDesde = pesoDesde;
	}
	public Double getPesoHasta() {
		return this.pesoHasta;
	}
	public void setPesoHasta(Double pesoHasta) {
		this.pesoHasta = pesoHasta;
	}
	public Boolean getConfirmarEspecificacion() {
		return this.confirmarEspecificacion;
	}
	public void setConfirmarEspecificacion(Boolean confirmarEspecificacion) {
		this.confirmarEspecificacion = confirmarEspecificacion;
	}
	public Boolean getFaltaReferencia() {
		return this.faltaReferencia;
	}
	public void setFaltaReferencia(Boolean faltaReferencia) {
		this.faltaReferencia = faltaReferencia;
	}
	public Boolean getCambioReferencia() {
		return this.cambioReferencia;
	}
	public void setCambioReferencia(Boolean cambioReferencia) {
		this.cambioReferencia = cambioReferencia;
	}
	public String getConfirmarPrecio() {
		return this.confirmarPrecio;
	}
	public void setConfirmarPrecio(String confirmarPrecio) {
		this.confirmarPrecio = confirmarPrecio;
	}
	public Double getCantidadEncargo() {
		return this.cantidadEncargo;
	}
	public void setCantidadEncargo(Double cantidadEncargo) {
		this.cantidadEncargo = cantidadEncargo;
	}
	public Double getCantidadFinalCompra() {
		return this.cantidadFinalCompra;
	}
	public void setCantidadFinalCompra(Double cantidadFinalCompra) {
		this.cantidadFinalCompra = cantidadFinalCompra;
	}
	public Double getCantidadServida() {
		return this.cantidadServida;
	}
	public void setCantidadServida(Double cantidadServida) {
		this.cantidadServida = cantidadServida;
	}
	public Double getCantidadNoServida() {
		return this.cantidadNoServida;
	}
	public void setCantidadNoServida(Double cantidadNoServida) {
		this.cantidadNoServida = cantidadNoServida;
	}
	public Double getUnidadesCaja() {
		return this.unidadesCaja;
	}
	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}
	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getObservacionesRechazo() {
		return this.observacionesRechazo;
	}
	public void setObservacionesRechazo(String observacionesRechazo) {
		this.observacionesRechazo = observacionesRechazo;
	}
	public String getCodigoPedidoInterno() {
		return this.codigoPedidoInterno;
	}
	public void setCodigoPedidoInterno(String codigoPedidoInterno) {
		this.codigoPedidoInterno = codigoPedidoInterno;
	}
	public Long getCodError() {
		return this.codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getDescError() {
		return this.descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	public String getFlgEspec() {
		return this.flgEspec;
	}
	public void setFlgEspec(String flgEspec) {
		this.flgEspec = flgEspec;
	}
	public String getDescripcionGestadic() {
		return this.descripcionGestadic;
	}
	public void setDescripcionGestadic(String descripcionGestadic) {
		this.descripcionGestadic = descripcionGestadic;
	}
	public String getEstadoGestadic() {
		return this.estadoGestadic;
	}
	public void setEstadoGestadic(String estadoGestadic) {
		this.estadoGestadic = estadoGestadic;
	}
	public String getTxtDetalleGestadic() {
		return this.txtDetalleGestadic;
	}
	public void setTxtDetalleGestadic(String txtDetalleGestadic) {
		this.txtDetalleGestadic = txtDetalleGestadic;
	}
	public String getTxtSituacionGestadic() {
		return this.txtSituacionGestadic;
	}
	public void setTxtSituacionGestadic(String txtSituacionGestadic) {
		this.txtSituacionGestadic = txtSituacionGestadic;
	}
}
