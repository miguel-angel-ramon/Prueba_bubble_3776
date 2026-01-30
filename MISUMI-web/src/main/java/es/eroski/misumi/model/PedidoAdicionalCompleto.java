package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PedidoAdicionalCompleto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private Integer tipoPedido;
	private Long codCentro;
	private Long codArt;
	private String descArt;
	private String esRefErronea;
	private Date fechaIniWS;
	private Boolean bloqueoPilada;
	private Boolean bloqueoEncargo;
	private Boolean frescoPuro;
	private String oferta;
	private List<String> listaOfertas;
	private String errorWS;
	private Boolean referenciaNueva;
	private Boolean conCajas;
	private String cajas;
	private Date fechaIniOferta;
	private Date fechaFinOferta;
	private Date fechaIni;
	private Date minFechaIni;
	private Double cantidad1;
	private Double uniCajas;
	private Date fecha2;
	private Double cantidad2;
	private Date fecha3;
	private Double cantidad3;
	private Date fechaPilada;
	private Date fechaFin;
	private Double capacidadMaxima;
	private Double implantacionMinima;
	private String estado;
	private String error;
	private String user;
	private Long identificador;
	private String excluir;
	private Long perfil;
	private String tipoAprovisionamiento;
	private String descError;
	private String sesion;
	private String strFechaIni;
	private String strFechaFin;
	private String strFechaFinMin;
	private String strFecha2;
	private String strFecha3;
	private String strFechaPilada;
	private String strFechaIniWS;
	private String strFechaIniOferta;
	private String strFechaFinOferta;
	private String strMinFechaIni;
	private Long tipoOferta;
	private Long referenciaUnitaria;
	private Long referenciaFFPP;
	private Double stock;
	private Boolean esStock;
	private Double stockPlataforma;
	private Date fecha4;
	private Double cantidad4;
	private Date fecha5;
	private Double cantidad5;
	private Boolean fechaNoDisponible;
	private Long grupo1;
	
	//Control para recálculo de fechas
	private String calcularFechaIniFin;
	
	//Control de Baja/Alta Catalogo
	private String catalogo;
	
	//Control de fechas con Bloqueos
	private String fechaBloqueoEncargo;
	private String fechaBloqueoEncargoPilada;
	
	//Control de leyendas de Bloqueos
	private String mostrarLeyendaBloqueo;
	
	//Control de pedidos no gestionados por PBL
	private String noGestionaPbl;
	
	private Long area;
	private Long seccion;
	private Long categoria;
	private Long subcategoria;
	private boolean esGenerica;
	private boolean esEncargoEspecial;
	private Double datosPedidoUnidadesPedir;
	private String datosPedidoRadioPeso;
	private Double datosPedidoPesoDesde;
	private Double datosPedidoPesoHasta;
	private String datosPedidoDescripcion;
	private String flgEspec;
	
	private Date primeraFechaEntrega;
	private List<Date> fechasVenta;
	private String bloqueoEncargoCliente;
	private String errorValidar;
	
	private String nombreCliente;
	private String apellidoCliente;
	private String telefonoCliente;
	private String contactoCentro;
	
	private Boolean mantieneReferenciaTextil;
	private Boolean showPopUpTextil;
	
	private String flgAvisoEncargoLote = "";
	
	private String idSession;
	
	private Long identificadorSIA;
	
	//Flag utilizado para la inserción desde nuevo por referencia de pedidos tratados por SIA
	private String flgGestionaSIA;
	
	private Long codArtCaprabo;
	
	private Long codArtEroski;
	private String descArtEroski;
	
	private Long codArtGrid;
	private String descArtGrid;
		
	//Franquicidados - Foto
	private String tieneFoto;

	//Flag para mostrar los campos de Excluir y Cajas en el pda.
	//No se muestran si es una referencia de PEDIR SIA y de tipo HORIZONTE
	private boolean showExcluirAndCajas;

	//Guardamos las unidades pedidas del primer día que sugiere el lanzar encargo para mostrar el mensaje de aviso
	//al usuario de la pda y las unidades pedidas en ese día. 
	private Long unidadesPedidas;
	
	private String errorPedirSIA;
	
	//MISUMI-270
	private String ofertaErr;

	// MISUMI-323
	private String formatoArticulo;
	
	// MISUMI-437
	private String tratamiento;

	// MISUMI-352
	private Long identificadorVegalsa;
	private boolean tratamientoVegalsa;
	
	// MISUMI-377
	private Long cantHoy;
	private Long cantFutura;
	private String fechaProximaEntrega;
	
	// Para PRUEBAS.
	private Long codFpMadre;
	
	private String msgUNIDADESCAJAS;

	public Long getCantHoy() {
		return cantHoy;
	}

	public void setCantHoy(Long cantHoy) {
		this.cantHoy = cantHoy;
	}

	public Long getCantFutura() {
		return cantFutura;
	}

	public void setCantFutura(Long cantFutura) {
		this.cantFutura = cantFutura;
	}

	public String getFechaProximaEntrega() {
		return fechaProximaEntrega;
	}

	public void setFechaProximaEntrega(String fechaProximaEntrega) {
		this.fechaProximaEntrega = fechaProximaEntrega;
	}

	// Ambas variables empleadas para centros VEGALSA en "Pedido Adicional".
	private Long diasMaximos;
	private Long cantidadMaxima;
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Integer getTipoPedido() {
		return this.tipoPedido;
	}

	public void setTipoPedido(Integer tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getDescArt() {
		return this.descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}

	public String getEsRefErronea() {
		return this.esRefErronea;
	}

	public void setEsRefErronea(String esRefErronea) {
		this.esRefErronea = esRefErronea;
	}

	public Date getFechaIniWS() {
		return this.fechaIniWS;
	}

	public void setFechaIniWS(Date fechaIniWS) {
		this.fechaIniWS = fechaIniWS;
	}

	public Boolean getBloqueoPilada() {
		return this.bloqueoPilada;
	}

	public void setBloqueoPilada(Boolean bloqueoPilada) {
		this.bloqueoPilada = bloqueoPilada;
	}
	
	public Boolean getBloqueoEncargo() {
		return this.bloqueoEncargo;
	}

	public void setBloqueoEncargo(Boolean bloqueoEncargo) {
		this.bloqueoEncargo = bloqueoEncargo;
	}
	
	public Boolean getFrescoPuro() {
		return this.frescoPuro;
	}



	public void setFrescoPuro(Boolean frescoPuro) {
		this.frescoPuro = frescoPuro;
	}

	public String getOferta() {
		return this.oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}

	public List<String> getListaOfertas() {
		return this.listaOfertas;
	}

	public void setListaOfertas(List<String> listaOfertas) {
		this.listaOfertas = listaOfertas;
	}

	public String getErrorWS() {
		return this.errorWS;
	}

	public void setErrorWS(String errorWS) {
		this.errorWS = errorWS;
	}

	public Boolean getReferenciaNueva() {
		return this.referenciaNueva;
	}

	public void setReferenciaNueva(Boolean referenciaNueva) {
		this.referenciaNueva = referenciaNueva;
	}

	public Boolean getConCajas() {
		return this.conCajas;
	}

	public void setConCajas(Boolean conCajas) {
		this.conCajas = conCajas;
	}
	
	public String getCajas() {
		return this.cajas;
	}

	public void setCajas(String cajas) {
		this.cajas = cajas;
	}

	public Date getFechaIniOferta() {
		return this.fechaIniOferta;
	}

	public void setFechaIniOferta(Date fechaIniOferta) {
		this.fechaIniOferta = fechaIniOferta;
	}

	public Date getFechaFinOferta() {
		return this.fechaFinOferta;
	}

	public void setFechaFinOferta(Date fechaFinOferta) {
		this.fechaFinOferta = fechaFinOferta;
	}

	public Date getMinFechaIni() {
		return this.minFechaIni;
	}

	public void setMinFechaIni(Date minFechaIni) {
		this.minFechaIni = minFechaIni;
	}
	
	public Date getFechaIni() {
		return this.fechaIni;
	}



	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	

	public Double getCantidad1() {
		return this.cantidad1;
	}

	public void setCantidad1(Double cantidad1) {
		this.cantidad1 = cantidad1;
	}

	public Double getUniCajas() {
		return this.uniCajas;
	}

	public void setUniCajas(Double uniCajas) {
		this.uniCajas = uniCajas;
	}

	public Date getFecha2() {
		return this.fecha2;
	}

	public void setFecha2(Date fecha2) {
		this.fecha2 = fecha2;
	}

	public Double getCantidad2() {
		return this.cantidad2;
	}

	public void setCantidad2(Double cantidad2) {
		this.cantidad2 = cantidad2;
	}

	public Date getFecha3() {
		return this.fecha3;
	}

	public void setFecha3(Date fecha3) {
		this.fecha3 = fecha3;
	}

	public Double getCantidad3() {
		return this.cantidad3;
	}

	public void setCantidad3(Double cantidad3) {
		this.cantidad3 = cantidad3;
	}

	public Date getFechaPilada() {
		return this.fechaPilada;
	}

	public void setFechaPilada(Date fechaPilada) {
		this.fechaPilada = fechaPilada;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Double getCapacidadMaxima() {
		return this.capacidadMaxima;
	}

	public void setCapacidadMaxima(Double capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}

	public Double getImplantacionMinima() {
		return this.implantacionMinima;
	}

	public void setImplantacionMinima(Double implantacionMinima) {
		this.implantacionMinima = implantacionMinima;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}



	public String getUser() {
		return this.user;
	}



	public void setUser(String user) {
		this.user = user;
	}

	public Long getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}



	public String getExcluir() {
		return this.excluir;
	}



	public void setExcluir(String excluir) {
		this.excluir = excluir;
	}



	public Long getPerfil() {
		return this.perfil;
	}



	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}

	public String getTipoAprovisionamiento() {
		return this.tipoAprovisionamiento;
	}

	public void setTipoAprovisionamiento(String tipoAprovisionamiento) {
		this.tipoAprovisionamiento = tipoAprovisionamiento;
	}

	public String getDescError() {
		return this.descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}
	
	public String getSesion() {
		return this.sesion;
	}

	public void setSesion(String sesion) {
		this.sesion = sesion;
	}
	
	public String getStrFechaIni() {
		return this.strFechaIni;
	}

	public void setStrFechaIni(String strFechaIni) {
		this.strFechaIni = strFechaIni;
	}

	public String getStrFechaFin() {
		return this.strFechaFin;
	}

	public void setStrFechaFin(String strFechaFin) {
		this.strFechaFin = strFechaFin;
	}

	public String getStrFechaFinMin() {
		return this.strFechaFinMin;
	}

	public void setStrFechaFinMin(String strFechaFinMin) {
		this.strFechaFinMin = strFechaFinMin;
	}


	public String getStrFecha2() {
		return this.strFecha2;
	}

	public void setStrFecha2(String strFecha2) {
		this.strFecha2 = strFecha2;
	}

	public String getStrFecha3() {
		return this.strFecha3;
	}

	public void setStrFecha3(String strFecha3) {
		this.strFecha3 = strFecha3;
	}

	public String getStrFechaPilada() {
		return this.strFechaPilada;
	}

	public void setStrFechaPilada(String strFechaPilada) {
		this.strFechaPilada = strFechaPilada;
	}

	public String getStrFechaIniWS() {
		return this.strFechaIniWS;
	}

	public void setStrFechaIniWS(String strFechaIniWS) {
		this.strFechaIniWS = strFechaIniWS;
	}

	public String getStrFechaIniOferta() {
		return this.strFechaIniOferta;
	}

	public void setStrFechaIniOferta(String strFechaIniOferta) {
		this.strFechaIniOferta = strFechaIniOferta;
	}

	public String getStrFechaFinOferta() {
		return this.strFechaFinOferta;
	}

	public void setStrFechaFinOferta(String strFechaFinOferta) {
		this.strFechaFinOferta = strFechaFinOferta;
	}

	public String getStrMinFechaIni() {
		return this.strMinFechaIni;
	}

	public void setStrMinFechaIni(String strMinFechaIni) {
		this.strMinFechaIni = strMinFechaIni;
	}

	public Long getTipoOferta() {
		return tipoOferta;
	}

	public void setTipoOferta(Long tipoOferta) {
		this.tipoOferta = tipoOferta;
	}

	public Long getReferenciaUnitaria() {
		return referenciaUnitaria;
	}

	public void setReferenciaUnitaria(Long referenciaUnitaria) {
		this.referenciaUnitaria = referenciaUnitaria;
	}



	public Long getReferenciaFFPP() {
		return referenciaFFPP;
	}



	public void setReferenciaFFPP(Long referenciaFFPP) {
		this.referenciaFFPP = referenciaFFPP;
	}



	public Double getStock() {
		return stock;
	}



	public void setStock(Double stock) {
		this.stock = stock;
	}



	public Boolean getEsStock() {
		return esStock;
	}



	public void setEsStock(Boolean esStock) {
		this.esStock = esStock;
	}



	public Double getStockPlataforma() {
		return stockPlataforma;
	}



	public void setStockPlataforma(Double stockPlataforma) {
		this.stockPlataforma = stockPlataforma;
	}



	public Date getFecha4() {
		return fecha4;
	}



	public void setFecha4(Date fecha4) {
		this.fecha4 = fecha4;
	}



	public Double getCantidad4() {
		return cantidad4;
	}



	public void setCantidad4(Double cantidad4) {
		this.cantidad4 = cantidad4;
	}



	public Date getFecha5() {
		return fecha5;
	}



	public void setFecha5(Date fecha5) {
		this.fecha5 = fecha5;
	}



	public Double getCantidad5() {
		return cantidad5;
	}



	public void setCantidad5(Double cantidad5) {
		this.cantidad5 = cantidad5;
	}



	public Boolean getFechaNoDisponible() {
		return fechaNoDisponible;
	}



	public void setFechaNoDisponible(Boolean fechaNoDisponible) {
		this.fechaNoDisponible = fechaNoDisponible;
	}

	public String getCalcularFechaIniFin() {
		return this.calcularFechaIniFin;
	}

	public void setCalcularFechaIniFin(String calcularFechaIniFin) {
		this.calcularFechaIniFin = calcularFechaIniFin;
	}

	public String getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	public String getFechaBloqueoEncargo() {
		return this.fechaBloqueoEncargo;
	}

	public void setFechaBloqueoEncargo(String fechaBloqueoEncargo) {
		this.fechaBloqueoEncargo = fechaBloqueoEncargo;
	}

	public String getFechaBloqueoEncargoPilada() {
		return this.fechaBloqueoEncargoPilada;
	}

	public void setFechaBloqueoEncargoPilada(String fechaBloqueoEncargoPilada) {
		this.fechaBloqueoEncargoPilada = fechaBloqueoEncargoPilada;
	}
	
	public String getMostrarLeyendaBloqueo() {
		return this.mostrarLeyendaBloqueo;
	}

	public void setMostrarLeyendaBloqueo(String mostrarLeyendaBloqueo) {
		this.mostrarLeyendaBloqueo = mostrarLeyendaBloqueo;
	}
	
	public Long getGrupo1() {
		return this.grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(this.codCentro).append(this.codArt).append(this.uuid).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof PedidoAdicionalCompleto)){
			return false;
		}
		PedidoAdicionalCompleto npr = (PedidoAdicionalCompleto) obj;
		return new EqualsBuilder().append(this.codCentro, npr.codCentro).append(this.codArt, npr.codArt).append(this.uuid, npr.uuid).isEquals();
	}
	
	public String getNoGestionaPbl() {
		return this.noGestionaPbl;
	}

	public void setNoGestionaPbl(String noGestionaPbl) {
		this.noGestionaPbl = noGestionaPbl;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getSeccion() {
		return seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public Long getCategoria() {
		return categoria;
	}



	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}



	public Long getSubcategoria() {
		return subcategoria;
	}



	public void setSubcategoria(Long subcategoria) {
		this.subcategoria = subcategoria;
	}



	public boolean isEsGenerica() {
		return esGenerica;
	}

	public void setEsGenerica(boolean esGenerica) {
		this.esGenerica = esGenerica;
	}

	public boolean isEsEncargoEspecial() {
		return esEncargoEspecial;
	}

	public void setEsEncargoEspecial(boolean esEncargoEspecial) {
		this.esEncargoEspecial = esEncargoEspecial;
	}

	public Double getDatosPedidoUnidadesPedir() {
		return datosPedidoUnidadesPedir;
	}

	public void setDatosPedidoUnidadesPedir(Double datosPedidoUnidadesPedir) {
		this.datosPedidoUnidadesPedir = datosPedidoUnidadesPedir;
	}

	public String getDatosPedidoRadioPeso() {
		return datosPedidoRadioPeso;
	}

	public void setDatosPedidoRadioPeso(String datosPedidoRadioPeso) {
		this.datosPedidoRadioPeso = datosPedidoRadioPeso;
	}

	public Double getDatosPedidoPesoDesde() {
		return datosPedidoPesoDesde;
	}

	public void setDatosPedidoPesoDesde(Double datosPedidoPesoDesde) {
		this.datosPedidoPesoDesde = datosPedidoPesoDesde;
	}

	public Double getDatosPedidoPesoHasta() {
		return datosPedidoPesoHasta;
	}

	public void setDatosPedidoPesoHasta(Double datosPedidoPesoHasta) {
		this.datosPedidoPesoHasta = datosPedidoPesoHasta;
	}

	public String getDatosPedidoDescripcion() {
		return datosPedidoDescripcion;
	}

	public void setDatosPedidoDescripcion(String datosPedidoDescripcion) {
		this.datosPedidoDescripcion = datosPedidoDescripcion;
	}

	public String getFlgEspec() {
		return flgEspec;
	}

	public void setFlgEspec(String flgEspec) {
		this.flgEspec = flgEspec;
	}

	public Date getPrimeraFechaEntrega() {
		return primeraFechaEntrega;
	}

	public void setPrimeraFechaEntrega(Date primeraFechaEntrega) {
		this.primeraFechaEntrega = primeraFechaEntrega;
	}



	public List<Date> getFechasVenta() {
		return fechasVenta;
	}

	public void setFechasVenta(List<Date> fechasVenta) {
		this.fechasVenta = fechasVenta;
	}

	public String getBloqueoEncargoCliente() {
		return bloqueoEncargoCliente;
	}

	public void setBloqueoEncargoCliente(String bloqueoEncargoCliente) {
		this.bloqueoEncargoCliente = bloqueoEncargoCliente;
	}

	public String getErrorValidar() {
		return errorValidar;
	}

	public void setErrorValidar(String errorValidar) {
		this.errorValidar = errorValidar;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getApellidoCliente() {
		return apellidoCliente;
	}

	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
	}

	public String getTelefonoCliente() {
		return telefonoCliente;
	}

	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}

	public String getContactoCentro() {
		return contactoCentro;
	}

	public void setContactoCentro(String contactoCentro) {
		this.contactoCentro = contactoCentro;
	}

	public Boolean getMantieneReferenciaTextil() {
		return mantieneReferenciaTextil;
	}

	public void setMantieneReferenciaTextil(Boolean mantieneReferenciaTextil) {
		this.mantieneReferenciaTextil = mantieneReferenciaTextil;
	}

	public Boolean getShowPopUpTextil() {
		return showPopUpTextil;
	}

	public void setShowPopUpTextil(Boolean showPopUpTextil) {
		this.showPopUpTextil = showPopUpTextil;
	}
	
	public String getFlgAvisoEncargoLote() {
		return this.flgAvisoEncargoLote;
	}


	public void setFlgAvisoEncargoLote(String flgAvisoEncargoLote) {
		this.flgAvisoEncargoLote = flgAvisoEncargoLote;
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	} 
	
	public Long getIdentificadorSIA() {
		return this.identificadorSIA;
	}

	public void setIdentificadorSIA(Long identificadorSIA) {
		this.identificadorSIA = identificadorSIA;
	}

	public String getFlgGestionaSIA() {
		return this.flgGestionaSIA;
	}


	public void setFlgGestionaSIA(String flgGestionaSIA) {
		this.flgGestionaSIA = flgGestionaSIA;
	}

	public Long getCodArtCaprabo() {
		return codArtCaprabo;
	}

	public void setCodArtCaprabo(Long codArtCaprabo) {
		this.codArtCaprabo = codArtCaprabo;
	}

	public Long getCodArtEroski() {
		return codArtEroski;
	}

	public void setCodArtEroski(Long codArtEroski) {
		this.codArtEroski = codArtEroski;
	}

	public String getDescArtEroski() {
		return descArtEroski;
	}

	public void setDescArtEroski(String descArtEroski) {
		this.descArtEroski = descArtEroski;
	}
	
	public Long getCodArtGrid() {
		return codArtGrid;
	}

	public void setCodArtGrid(Long codArtGrid) {
		this.codArtGrid = codArtGrid;
	}

	public String getDescArtGrid() {
		return descArtGrid;
	}

	public void setDescArtGrid(String descArtGrid) {
		this.descArtGrid = descArtGrid;
	}

	public String getTieneFoto() {
		return tieneFoto;
	}

	public void setTieneFoto(String tieneFoto) {
		this.tieneFoto = tieneFoto;
	}

	public boolean isShowExcluirAndCajas() {
		return showExcluirAndCajas;
	}

	public void setShowExcluirAndCajas(boolean showExcluirAndCajas) {
		this.showExcluirAndCajas = showExcluirAndCajas;
	}

	public Long getUnidadesPedidas() {
		return unidadesPedidas;
	}

	public void setUnidadesPedidas(Long unidadesPedidas) {
		this.unidadesPedidas = unidadesPedidas;
	}

	public String getErrorPedirSIA() {
		return errorPedirSIA;
	}

	public void setErrorPedirSIA(String errorPedirSIA) {
		this.errorPedirSIA = errorPedirSIA;
	}

	public String getOfertaErr() {
		return ofertaErr;
	}

	public void setOfertaErr(String ofertaErr) {
		this.ofertaErr = ofertaErr;
	}

	public String getFormatoArticulo() {
		return formatoArticulo;
	}

	public void setFormatoArticulo(String formatoArticulo) {
		this.formatoArticulo = formatoArticulo;
	}

	public Long getIdentificadorVegalsa() {
		return identificadorVegalsa;
	}

	public void setIdentificadorVegalsa(Long identificadorVegalsa) {
		this.identificadorVegalsa = identificadorVegalsa;
	}

	public boolean isTratamientoVegalsa() {
		return tratamientoVegalsa;
	}

	public void setTratamientoVegalsa(boolean tratamientoVegalsa) {
		this.tratamientoVegalsa = tratamientoVegalsa;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public Long getDiasMaximos() {
		return diasMaximos;
	}

	public void setDiasMaximos(Long diasMaximos) {
		this.diasMaximos = diasMaximos;
	}

	public Long getCantidadMaxima() {
		return cantidadMaxima;
	}

	public void setCantidadMaxima(Long cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}

	public Long getCodFpMadre() {
		return codFpMadre;
	}

	public void setCodFpMadre(Long codFpMadre) {
		this.codFpMadre = codFpMadre;
	}

	public String getMsgUNIDADESCAJAS() {
		return msgUNIDADESCAJAS;
	}

	public void setMsgUNIDADESCAJAS(String msgUNIDADESCAJAS) {
		this.msgUNIDADESCAJAS = msgUNIDADESCAJAS;
	}

}
