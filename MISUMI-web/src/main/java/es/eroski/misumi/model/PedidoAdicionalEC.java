package es.eroski.misumi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class PedidoAdicionalEC implements Cloneable,Serializable{

	private static final long serialVersionUID = 1L;

	private Long localizador;
	private Long codLoc;
	private String contactoCentro;
	private String telefonoCentro;
	private String nombreCliente;
	private String apellidoCliente;
	private String telefonoCliente;
	private Date fechaHoraEncargo;
	private String tipoEncargo;
	private Date fechaVenta;
	private Date fechaVentaModificada;
	private String fechaInferior;
	private String area;
	private String seccion;
	private String categoria;
	private String subcategoria;
	private String segmento;
	private Long codArtFormlogMisumi;
	private Long codArtFormlog;
	private String denomArticulo;
	private String especificacion;
	private Double pesoDesde;
	private Double pesoHasta;
	private String confirmarEspecificaciones;
	private String faltaRef;
	private String cambioRef;
	private String flgEspec;
	private String confirmarPrecio;
	private Double cantEncargo;
	private Double cantFinalCompra;
	private Double cantServido;
	private Double cantNoServido;
	private Double unidServ;
	private String estado;
	private String observacionesMisumi;
	private BigDecimal codigoPedidoInterno;
	private String flgModificable;
	private String tipoAprov;
	private String vitrina;
	private String relCompraVenta;
	private Long codigoError;
	private String descripcionError;
	private Long nivel;
	
	private String id; //id para enlazar con detalle
	
	private String idSession;
	
	//Campos fecha de pantalla
	private String fechaVentaPantalla;
	private String fechaVentaModificadaPantalla;
	
	private List<CamposSeleccionadosEC> listadoSeleccionados;
	
	private Date primeraFechaEntrega;
	private List<Date> fechasVenta;
	private String bloqueoEncargoCliente;
	private String errorValidar;

	private String descripcionGestadic;
	private String estadoGestadic;
	private String txtDetalleGestadic;
	private String txtSituacionGestadic;
	
	//Caprabo
	private Long codArticuloGrid;
	private String descriptionArtGrid;   
	private boolean esCaprabo;  
	
	private Long codArtEroski;
		
	


	

	public PedidoAdicionalEC() {
	    super();
	}
	
	public PedidoAdicionalEC(Long localizador, Long codLoc,
			String contactoCentro, String telefonoCentro, String nombreCliente,
			String apellidoCliente, String telefonoCliente,
			Date fechaHoraEncargo, String tipoEncargo, Date fechaVenta,
			Date fechaVentaModificada, String fechaInferior, String area,
			String seccion, String categoria, String subcategoria,
			String segmento, Long codArtFormlogMisumi, Long codArtFormlog,
			String denomArticulo, String especificacion, Double pesoDesde,
			Double pesoHasta, String confirmarEspecificaciones, String faltaRef,
			String cambioRef, String confirmarPrecio, Double cantEncargo,
			Double cantFinalCompra, Double cantServido, Double cantNoServido,
			Double unidServ, String estado, String observacionesMisumi,
			BigDecimal codigoPedidoInterno, String flgModificable, String tipoAprov,
			String vitrina, String relCompraVenta, Long codigoError,
			String descripcionError,
			String descripcionGestadic, String estadoGestadic,
			String txtDetalleGestadic, String txtSituacionGestadic,
			List<CamposSeleccionadosEC> listadoSeleccionados, String flgEspec) {
		super();
		this.localizador = localizador;
		this.codLoc = codLoc;
		this.contactoCentro = contactoCentro;
		this.telefonoCentro = telefonoCentro;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.telefonoCliente = telefonoCliente;
		this.fechaHoraEncargo = fechaHoraEncargo;
		this.tipoEncargo = tipoEncargo;
		this.fechaVenta = fechaVenta;
		this.fechaVentaModificada = fechaVentaModificada;
		this.fechaInferior = fechaInferior;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.codArtFormlogMisumi = codArtFormlogMisumi;
		this.codArtFormlog = codArtFormlog;
		this.denomArticulo = denomArticulo;
		this.especificacion = especificacion;
		this.pesoDesde = pesoDesde;
		this.pesoHasta = pesoHasta;
		this.confirmarEspecificaciones = confirmarEspecificaciones;
		this.faltaRef = faltaRef;
		this.cambioRef = cambioRef;
		this.confirmarPrecio = confirmarPrecio;
		this.cantEncargo = cantEncargo;
		this.cantFinalCompra = cantFinalCompra;
		this.cantServido = cantServido;
		this.cantNoServido = cantNoServido;
		this.unidServ = unidServ;
		this.estado = estado;
		this.observacionesMisumi = observacionesMisumi;
		this.codigoPedidoInterno = codigoPedidoInterno;
		this.flgModificable = flgModificable;
		this.tipoAprov = tipoAprov;
		this.vitrina = vitrina;
		this.relCompraVenta = relCompraVenta;
		this.codigoError = codigoError;
		this.descripcionError = descripcionError;
		this.descripcionGestadic = descripcionGestadic;
		this.estadoGestadic = estadoGestadic;
		this.txtDetalleGestadic = txtDetalleGestadic;
		this.txtSituacionGestadic = txtSituacionGestadic;
		this.listadoSeleccionados = listadoSeleccionados;
		this.flgEspec = flgEspec;
	}

	public  PedidoAdicionalEC clone() throws CloneNotSupportedException{
		return (PedidoAdicionalEC)super.clone();
	}

	public Long getLocalizador() {
		return this.localizador;
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

	public Date getFechaHoraEncargo() {
		return this.fechaHoraEncargo;
	}

	public void setFechaHoraEncargo(Date fechaHoraEncargo) {
		this.fechaHoraEncargo = fechaHoraEncargo;
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

	public String getFechaInferior() {
		return this.fechaInferior;
	}

	public void setFechaInferior(String fechaInferior) {
		this.fechaInferior = fechaInferior;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSeccion() {
		return this.seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSubcategoria() {
		return this.subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

	public String getSegmento() {
		return this.segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public Long getCodArtFormlogMisumi() {
		return this.codArtFormlogMisumi;
	}

	public void setCodArtFormlogMisumi(Long codArtFormlogMisumi) {
		this.codArtFormlogMisumi = codArtFormlogMisumi;
	}

	public Long getCodArtFormlog() {
		return this.codArtFormlog;
	}

	public void setCodArtFormlog(Long codArtFormlog) {
		this.codArtFormlog = codArtFormlog;
	}

	public String getDenomArticulo() {
		return this.denomArticulo;
	}

	public void setDenomArticulo(String denomArticulo) {
		this.denomArticulo = denomArticulo;
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

	public String getConfirmarEspecificaciones() {
		return this.confirmarEspecificaciones;
	}

	public void setConfirmarEspecificaciones(String confirmarEspecificaciones) {
		this.confirmarEspecificaciones = confirmarEspecificaciones;
	}

	public String getFaltaRef() {
		return this.faltaRef;
	}

	public void setFaltaRef(String faltaRef) {
		this.faltaRef = faltaRef;
	}

	public String getCambioRef() {
		return this.cambioRef;
	}

	public void setCambioRef(String cambioRef) {
		this.cambioRef = cambioRef;
	}

	public String getConfirmarPrecio() {
		return this.confirmarPrecio;
	}

	public void setConfirmarPrecio(String confirmarPrecio) {
		this.confirmarPrecio = confirmarPrecio;
	}

	public Double getCantEncargo() {
		return this.cantEncargo;
	}

	public void setCantEncargo(Double cantEncargo) {
		this.cantEncargo = cantEncargo;
	}

	public Double getCantFinalCompra() {
		return this.cantFinalCompra;
	}

	public void setCantFinalCompra(Double cantFinalCompra) {
		this.cantFinalCompra = cantFinalCompra;
	}

	public Double getCantServido() {
		return this.cantServido;
	}

	public void setCantServido(Double cantServido) {
		this.cantServido = cantServido;
	}

	public Double getCantNoServido() {
		return this.cantNoServido;
	}

	public void setCantNoServido(Double cantNoServido) {
		this.cantNoServido = cantNoServido;
	}

	public Double getUnidServ() {
		return this.unidServ;
	}

	public void setUnidServ(Double unidServ) {
		this.unidServ = unidServ;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservacionesMisumi() {
		return this.observacionesMisumi;
	}

	public void setObservacionesMisumi(String observacionesMisumi) {
		this.observacionesMisumi = observacionesMisumi;
	}

	public BigDecimal getCodigoPedidoInterno() {
		return this.codigoPedidoInterno;
	}

	public void setCodigoPedidoInterno(BigDecimal codigoPedidoInterno) {
		this.codigoPedidoInterno = codigoPedidoInterno;
	}

	public String getFlgModificable() {
		return this.flgModificable;
	}

	public void setFlgModificable(String flgModificable) {
		this.flgModificable = flgModificable;
	}

	public String getTipoAprov() {
		return this.tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public String getVitrina() {
		return this.vitrina;
	}

	public void setVitrina(String vitrina) {
		this.vitrina = vitrina;
	}

	public String getRelCompraVenta() {
		return this.relCompraVenta;
	}

	public void setRelCompraVenta(String relCompraVenta) {
		this.relCompraVenta = relCompraVenta;
	}

	public Long getCodigoError() {
		return this.codigoError;
	}

	public void setCodigoError(Long codigoError) {
		this.codigoError = codigoError;
	}

	public String getDescripcionError() {
		return this.descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	public List<CamposSeleccionadosEC> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(
			List<CamposSeleccionadosEC> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public Long getNivel() {
		return this.nivel;
	}
	
	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}

	public String getFechaVentaPantalla() {
		return this.fechaVentaPantalla;
	}

	public void setFechaVentaPantalla(String fechaVentaPantalla) {
		this.fechaVentaPantalla = fechaVentaPantalla;
	}

	public String getFechaVentaModificadaPantalla() {
		return this.fechaVentaModificadaPantalla;
	}

	public void setFechaVentaModificadaPantalla(String fechaVentaModificadaPantalla) {
		this.fechaVentaModificadaPantalla = fechaVentaModificadaPantalla;
	}

	public Date getPrimeraFechaEntrega() {
		return this.primeraFechaEntrega;
	}

	public void setPrimeraFechaEntrega(Date primeraFechaEntrega) {
		this.primeraFechaEntrega = primeraFechaEntrega;
	}

	public List<Date> getFechasVenta() {
		return this.fechasVenta;
	}

	public void setFechasVenta(List<Date> fechasVenta) {
		this.fechasVenta = fechasVenta;
	}

	public String getBloqueoEncargoCliente() {
		return this.bloqueoEncargoCliente;
	}

	public void setBloqueoEncargoCliente(String bloqueoEncargoCliente) {
		this.bloqueoEncargoCliente = bloqueoEncargoCliente;
	}

	public String getErrorValidar() {
		return this.errorValidar;
	}

	public void setErrorValidar(String errorValidar) {
		this.errorValidar = errorValidar;
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
	
	public Long getCodArticuloGrid() {
		return codArticuloGrid;
	}

	public void setCodArticuloGrid(Long codArticuloGrid) {
		this.codArticuloGrid = codArticuloGrid;
	}

	public String getDescriptionArtGrid() {
		return descriptionArtGrid;
	}

	public void setDescriptionArtGrid(String descriptionArtGrid) {
		this.descriptionArtGrid = descriptionArtGrid;
	}

	public boolean isEsCaprabo() {
		return esCaprabo;
	}

	public void setEsCaprabo(boolean esCaprabo) {
		this.esCaprabo = esCaprabo;
	}
	
	public Long getCodArtEroski() {
		return codArtEroski;
	}

	public void setCodArtEroski(Long codArtEroski) {
		this.codArtEroski = codArtEroski;
	}
	
}