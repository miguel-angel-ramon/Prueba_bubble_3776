package es.eroski.misumi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TEncargosClte implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long localizador;
	private Long centro;
	private String area;
	private String seccion;
	private String categoria;
	private String subcategoria;
	private String segmento;
	private Long referencia;
	private Long referenciaMadre;
	private String descripcion;
	private Double unidadescaja;
	private String iddsesion;
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
	private String especificacion;
	private Double pesoDesde;
	private Double pesoHasta;
	private String confirmarEspecificaciones;
	private String faltaRef;
	private String cambioRef;
	private String confirmarPrecio;
	private Double cantEncargo;
	private Double cantFinalCompra;
	private Double cantServido;
	private Double cantNoServido;
	private String estado;
	private String observacionesMisumi;
	private BigDecimal codigoPedidoInterno;
	private String flgModificable;
	private String codTpAprov;
	private String vitrina;
	private String relCompraVenta;
	private Long codigoError;
	private String descripcionError;
	private Date fechacreacion;
	private Long nivel;
	private String descripcionGestadic;
	private String estadoGestadic;
	private String txtDetalleGestadic;
	private String txtSituacionGestadic;
	
	private Long codArticuloGrid;
	private String descriptionArtGrid;   
	
	private String orderBy;
	private String sortOrder;
	
	//Tabla padre
	private boolean padre;
	
	private String flgEspec;
	
	
	
	
	public TEncargosClte() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TEncargosClte(Long localizador, Long centro, String area,
			String seccion, String categoria, String subcategoria,
			String segmento, Long referencia, Long referenciaMadre,
			String descripcion, Double unidadescaja, String iddsesion,
			String contactoCentro, String telefonoCentro, String nombreCliente,
			String apellidoCliente, String telefonoCliente,
			Date fechaHoraEncargo, String tipoEncargo, Date fechaVenta,
			Date fechaVentaModificada, String fechaInferior,
			String especificacion, Double pesoDesde, Double pesoHasta,
			String confirmarEspecificaciones, String faltaRef,
			String cambioRef, String confirmarPrecio, Double cantEncargo,
			Double cantFinalCompra, Double cantServido, Double cantNoServido,
			String estado, String observacionesMisumi,
			BigDecimal codigoPedidoInterno, String flgModificable, String codTpAprov,
			String vitrina, String relCompraVenta, Long codigoError,
			String descripcionError, Long nivel, Date fechacreacion, 
			String descripcionGestadic, String estadoGestadic,
			String txtDetalleGestadic, String txtSituacionGestadic,
			String flgEspec, Long codArticuloGrid, String descriptionArtGrid) {
		super();
		this.localizador = localizador;
		this.centro = centro;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.referencia = referencia;
		this.referenciaMadre = referenciaMadre;
		this.descripcion = descripcion;
		this.unidadescaja = unidadescaja;
		this.iddsesion = iddsesion;
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
		this.estado = estado;
		this.observacionesMisumi = observacionesMisumi;
		this.codigoPedidoInterno = codigoPedidoInterno;
		this.flgModificable = flgModificable;
		this.codTpAprov = codTpAprov;
		this.vitrina = vitrina;
		this.relCompraVenta = relCompraVenta;
		this.codigoError = codigoError;
		this.descripcionError = descripcionError;
		this.nivel = nivel;
		this.fechacreacion = fechacreacion;
		this.descripcionGestadic = descripcionGestadic;
		this.estadoGestadic = estadoGestadic;
		this.txtDetalleGestadic = txtDetalleGestadic;
		this.txtSituacionGestadic = txtSituacionGestadic;
		this.flgEspec = flgEspec;
		this.codArticuloGrid = codArticuloGrid;
		this.descriptionArtGrid = descriptionArtGrid;
	}
	
	public Long getLocalizador() {
		return this.localizador;
	}
	
	public void setLocalizador(Long localizador) {
		this.localizador = localizador;
	}
	
	public Long getCentro() {
		return this.centro;
	}
	
	public void setCentro(Long centro) {
		this.centro = centro;
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
	
	public Long getReferencia() {
		return this.referencia;
	}
	
	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}
	
	public Long getReferenciaMadre() {
		return this.referenciaMadre;
	}
	
	public void setReferenciaMadre(Long referenciaMadre) {
		this.referenciaMadre = referenciaMadre;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Double getUnidadescaja() {
		return this.unidadescaja;
	}
	
	public void setUnidadescaja(Double unidadescaja) {
		this.unidadescaja = unidadescaja;
	}
	
	public String getIddsesion() {
		return this.iddsesion;
	}
	
	public void setIddsesion(String iddsesion) {
		this.iddsesion = iddsesion;
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
	
	public String getCodTpAprov() {
		return this.codTpAprov;
	}
	
	public void setCodTpAprov(String codTpAprov) {
		this.codTpAprov = codTpAprov;
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
	
	public Long getNivel() {
		return this.nivel;
	}
	
	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}

	public Date getFechacreacion() {
		return this.fechacreacion;
	}
	
	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}
	
	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	
	public boolean isPadre() {
		return padre;
	}

	public void setPadre(boolean padre) {
		this.padre = padre;
	}

	public String getEstadoExcel(){
		
		if (this.padre){
			return this.estado;
		} else {
			return null;
		}
	}
	
	public String getReferenciaExcel(){
		StringBuffer referenciaExcel = new StringBuffer();
		if (this.padre){
			if ((("S".equals(this.faltaRef) && !"S".equals(this.cambioRef)) ||
				("PENDIENTE".equals(this.estado)) && (null != this.nivel && this.nivel != new Long(1)))){
				referenciaExcel.append("-----");
			} else {
				referenciaExcel.append(this.referencia);
			}
		} else {
			if ("PENDIENTE".equals(this.estado)){
				if ("S".equals(this.faltaRef) && !"S".equals(this.cambioRef)){
					referenciaExcel.append("-----");
				} else {
					referenciaExcel.append(this.referencia);
				}
			}
		}
		return referenciaExcel.toString();
	}
	
	public String getDescripcionExcel(){
		
		StringBuffer descripcionExcel = new StringBuffer();
		if (this.padre){
			if ("PENDIENTE".equals(this.estado) && (null != this.nivel && !new Long(1).equals(this.nivel))	){
				descripcionExcel.append("-----");
			} else {
				descripcionExcel.append(this.descripcion);
			}
		} else {
			if ("PENDIENTE".equals(this.estado)){
				if ("S".equals(this.faltaRef) && "S".equals(this.cambioRef)){
					descripcionExcel.append("-----");
				} else {
					descripcionExcel.append(this.descripcion);
				}
			}
		}
		return descripcionExcel.toString();
	}
	
	public String getFechaVentaExcel(){
		StringBuffer fechaVentaExcel = new StringBuffer();
		if (this.padre){
			if ("PENDIENTE".equals(this.estado) && !this.nivel.equals(new Long(1))){
				fechaVentaExcel.append("-----");
			} else {
				if (null != this.fechaVentaModificada && !this.fechaVentaModificada.equals(this.fechaVenta)){
					fechaVentaExcel.append(this.formatDate(this.fechaVentaModificada));
				} else {
					if (null != this.fechaVenta){
						fechaVentaExcel.append(this.formatDate(this.fechaVenta));
					}
				}
			}
		} else {
			if ("PENDIENTE".equals(this.estado)){
				if (null != this.fechaVenta){
					fechaVentaExcel.append(this.formatDate(this.fechaVenta));
				}
			}
		}
		return fechaVentaExcel.toString();
	}
	
	public String getCantEncargoExcel(){
		StringBuffer cantEncargoExcel = new StringBuffer();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		if (null != this.cantEncargo){
			cantEncargoExcel.append(nf.format(this.cantEncargo));
		} 

		return cantEncargoExcel.toString();
	}
	
	public String getCantFinalExcel(){
		StringBuffer cantFinalExcel = new StringBuffer();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		if (this.padre){
			if (null != this.cantFinalCompra){
				cantFinalExcel.append(nf.format(this.cantFinalCompra));
			}
		} 
		

		return cantFinalExcel.toString();
	}
	
	public String getCantServExcel(){
		StringBuffer cantServExcel = new StringBuffer();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		if (this.padre){
			if (null != this.cantServido && !this.cantServido.equals(new Double(0))){
				cantServExcel.append(nf.format(this.cantServido));
			}
		} else {
			if (null != this.estado && (estado.equals("CONFIRMADO") || 
					estado.equals("NO SERVIDO") || estado.equals("CONFIR/NO SERV"))){
				if (null != this.cantServido && !this.cantServido.equals(new Double(0))){
					cantServExcel.append(nf.format(this.cantServido));
				}
			}
		}
		

		return cantServExcel.toString();
	}
	
	public String getCantNSRExcel(){
		StringBuffer cantNSR = new StringBuffer();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		if (null != this.cantNoServido && !this.getCantNoServido().equals(new Double(0))){
			cantNSR.append(nf.format(this.cantNoServido));
		}

		return cantNSR.toString();
	}
	
	
	public String getEspecificacionExcel(){
		
		if (this.padre){
			if (this.nivel.equals(new Long(1))){
				return this.getEspecificacionCompleta();
			} else {
				return null;
			}
		} else {
			return this.getEspecificacionCompleta();
		}
	}
	
	private String getEspecificacionCompleta(){
		StringBuffer especificacionCompleta = new StringBuffer();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		if (null != this.pesoDesde){
			especificacionCompleta.append(nf.format(this.pesoDesde));
			especificacionCompleta.append(" KG");
		}
		if (null != this.pesoHasta){
			especificacionCompleta.append(" a ");
			especificacionCompleta.append(nf.format(this.pesoHasta));
			especificacionCompleta.append(" KG");
		}
		if (!this.especificacion.isEmpty()){
			if (!especificacionCompleta.toString().isEmpty()){
				especificacionCompleta.append(" / ");
			}
			especificacionCompleta.append(this.especificacion);
		}
		return especificacionCompleta.toString();
	}
	
	public String getContactoClienteExcel(){
		
		if (this.padre){
			if (this.nivel.equals(new Long(1))){
				return this.getContactoCliente();
			} else {
				return null;
			}
		} else {
			return this.getContactoCliente();
		}
	}
	
	private String getContactoCliente(){
		StringBuffer contacto = new StringBuffer();
		contacto.append(this.nombreCliente);
		if (!this.apellidoCliente.isEmpty()){
			contacto.append(" ").append(this.apellidoCliente);
		}
		if (!this.telefonoCliente.isEmpty()){
			contacto.append(" / ").append(this.telefonoCliente);
		}
		return contacto.toString();
	}
	
	public String getPrecioExcel(){
		
		if (this.padre){
			if (this.nivel.equals(new Long(1))){
				return this.confirmarPrecio;
			} else {
				return null;
			}
		} else {
			return this.confirmarPrecio;
		}
	}
	
	private String formatDate(Date fecha) {
		StringBuffer strFecha = new StringBuffer();
		String[] dias = { "D", "L", "M", "X", "J", "V", "S" };
		int numeroDia = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		numeroDia = cal.get(Calendar.DAY_OF_WEEK);
		strFecha.append(dias[numeroDia - 1]).append(" ");
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM");
		strFecha.append(df.format(cal.getTime()).toUpperCase());
		return strFecha.toString();
	}
	
	public String getLocalizadorExcel(){
		StringBuffer localizadorExcel = new StringBuffer();
		if (!new Long(0).equals(this.localizador)){
			localizadorExcel.append(this.localizador);
		}

		return localizadorExcel.toString();
	}

	public String getFlgEspec() {
		return flgEspec;
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

}
