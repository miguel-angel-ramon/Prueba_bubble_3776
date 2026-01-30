package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetalleMostrador implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idSesion;
	
	// referencia
	private Long codCentro;
	private Long referencia;
	private String descReferencia;
	
	private Long referenciaEroski;
	private String descReferenciaEroski;
	
	private Long area;
	private String denomArea;
	// Seccion
	private Long seccion;
	private String denomSeccion;
	// Categoria
	private Long categoria;
	private String denomCategoria;
	// SubCategoria
	private Long subcategoria;
	private String denomSubcategoria;
	// Segmento
	private Long segmento;
	private String denomSegmento;

	
	private Date fechaTransmision;
	private Date fechaVenta;
	private Double unidadesCaja;
	
	//GESTION DE EUROS
	private Double precioCostoArticulo;  
	private Double precioCostoInicial;  
	private Double precioCostoFinal;  
	private Double precioCostoFinalFinal;
	
	private Date fechaPedido;
	private Long cajasPedidas;
	private Double pendienteRecibirManana;
	private Double previsionVenta;
	private Long propuestaPedido;
	private Long propuestaPedidoAnt;
	private Long propuestaPedidoOriginal;
	private Long propuestaPedidoUltValida;

	private String marcaCompra;
	private String marcaVenta;
	private Long referenciaCompra;
	
	private Double pvpTarifa;
	private Double iva;
	private Double tirado;
	private Double tiradoParasitos;
	private String redondeoPropuesta;
	private Double totalVentasEspejo;
	private Double totImporteVentasEspejo;
	private Double multiplicadorVentas;
	private Long orden;
	private Date ofertaABInicio;
	private Date ofertaABFin;
	private Date ofertaCDInicio;
	private Date ofertaCDFin;
	private Double pvp;
	private Long nivel;
	private Date diaEspejo;
	
	private String estado;
	private Long estadoGrid;
	private String descripcionError;
	
	/*
	 * Hora límite de la bomba en pantalla.
	 */
	private String horaLimite;
	
	private String resultadoWS;
	
	private Integer totalGuardar;
	private Integer totalError;
	
	private List <DetalleMostradorModificados> listaModificados= new ArrayList<DetalleMostradorModificados>();
	
	private String tipoGama;

	private String id;  //id para enlazar con las referencias de un lote.

//	private String nextDayPedido;
	private Date fechaSgteTransmision;
	
	private String flgSIA;

	//HORIZONTE Detallado
	private Double pdteRecibirVenta;
	private Long pdteRecibirManana;
	private Long codNecesidad;
	private Long diasCubrePedido;
	private String empujePdteRecibir;
	
	private Long codErrorRedondeo;
	private String descErrorRedondeo;

	//Sirve para colorear las filas de azul en el grid
	private String flgOferta;

	//Sirve para mostrar el estado del checkbox. Si el check está habilitado muestra "todo"
	//Si el check está deshabilitado NO tiene que mostrar las previsionVentas de pedido = 0 SIN propuestaPedido.
	private String flgIncluirPropPed;
	private String flgPropuesta; //Lo manda el procedimiento

	private String oferta;
	private Long tipoOferta;
	
	private String converArt;
	
	private String respetarIMC;
	private Long diferencia;
	private String refCumple;
	private String aviso;
	

	//Se ha creado este error para que avise si el ws de modificacion ha fallado.
	private Integer errorModificarWS;
	
	//Se ha creado este error para que avise si el procedimiento ha fallado.
	private Integer errorModificarProc;
	
	//Se ha creado este error para que avise si el procedimiento ha fallado.
	private Integer codError;

	private String flgNoRealizarUpdate;
	
	private Double ventaEspejoTarifa;
	private Double ventaEspejoOferta;

	private String tipoAprov;
	
	public DetalleMostrador() {
		super();
	}
	
//	public DetalleMostrador(String flgIncluirPropPed) {
//		super();
//		this.flgIncluirPropPed = flgIncluirPropPed;
//	}

	public DetalleMostrador( Long codCentro,  Long area, Long seccion, Long categoria
						   , Long subcategoria, Long segmento, Long referencia
						   , String descReferencia, Double unidadesCaja, Long cajasPedidas
						   , Double previsionVenta, Long propuestaPedido
						   , Long estadoGrid
						   , String estado
						   ) {
		super();
		this.codCentro = codCentro;
		this.referencia = referencia;
		this.descReferencia = descReferencia;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.unidadesCaja = unidadesCaja;
		this.cajasPedidas = cajasPedidas;
		this.previsionVenta = previsionVenta;
		this.propuestaPedido = propuestaPedido;
		this.estadoGrid = estadoGrid;
		this.estado = estado;
	}
	
	public DetalleMostrador( Long codCentro, Long area, Long seccion, Long categoria
						   , Long subcategoria, Long segmento
						   , Long referencia, String descReferencia
						   , Double pendienteRecibirManana
						   , Double unidadesCaja
						   , Double previsionVenta
						   , Long propuestaPedido
						   , Long propuestaPedidoOriginal
						   , Long propuestaPedidoUltValida
						   , String estado
						   , Long estadoGrid
						   , String horalimite
						   , String descripcionError
						   , Long propuestaPedidoAnt
						   , String id
						   ) {
		super();
		this.codCentro = codCentro;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.referencia = referencia;
		this.descReferencia = descReferencia;
		this.pendienteRecibirManana = pendienteRecibirManana;
		this.unidadesCaja = unidadesCaja;
		this.previsionVenta = previsionVenta;
		this.propuestaPedido = propuestaPedido;
		this.propuestaPedidoOriginal = propuestaPedidoOriginal;
		this.propuestaPedidoUltValida = propuestaPedidoUltValida;
		this.estado = estado;
		this.estadoGrid = estadoGrid;
		this.horaLimite=horalimite;
		this.propuestaPedidoAnt = propuestaPedidoAnt;
		this.id = id;
	}
	
	public DetalleMostrador( Long codCentro
							, Long area
							, Long seccion
							, Long categoria
							, Long subcategoria
							, Long segmento
							, Long referencia
							, String descReferencia
							, Double pendienteRecibirManana
							, Double unidadesCaja
							//, Long cajasPedidas
							, Double previsionVenta
							, Long propuestaPedido
							, Long propuestaPedidoOriginal
							, Long propuestaPedidoUltValida
							, Long estadoGrid
							, String estado
							, String horalimite
							, String descripcionError
							, Long propuestaPedidoAnt
							//, String tipo
							, Long nivel
							, Long referenciaEroski
							, String descReferenciaEroski
	) {
		super();
		this.codCentro = codCentro;
		this.referencia = referencia;
		this.descReferencia = descReferencia;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.pendienteRecibirManana = pendienteRecibirManana;
		this.unidadesCaja = unidadesCaja;
		//this.cajasPedidas = cajasPedidas;
		this.previsionVenta = previsionVenta;
		this.propuestaPedido = propuestaPedido;
		this.propuestaPedidoOriginal = propuestaPedidoOriginal;
		this.propuestaPedidoUltValida = propuestaPedidoUltValida;
		this.estado = estado;
		this.estadoGrid = estadoGrid;
		this.horaLimite=horalimite;
		//this.tipo = tipo;
		//this.id = id;
		this.referenciaEroski = referenciaEroski;
		this.descReferenciaEroski = descReferenciaEroski;
	}
	
	public DetalleMostrador( Long referencia, String descReferencia, Long area, Long seccion, Long categoria
				   , Long subcategoria, Long segmento, Date fechaPedido, Double unidadesCaja
				   , Long cajasPedidas, Double previsionVenta, Long propuestaPedido
				   , Long estadoGrid, String estado
				   ) {
		super();
		this.referencia = referencia;
		this.descReferencia = descReferencia;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.fechaPedido = fechaPedido;
		this.unidadesCaja = unidadesCaja;
		this.cajasPedidas = cajasPedidas;
		this.previsionVenta = previsionVenta;
		this.propuestaPedido = propuestaPedido;
		this.estadoGrid = estadoGrid;
		this.estado = estado;
	}
	
	public DetalleMostrador( Long codCentro, Long area, Long seccion, Long categoria
				   , Long subcategoria, Long segmento, Long referencia
				   , String descReferencia, Double unidadesCaja, Long cajasPedidas
				   , Double previsionVenta, Long propuestaPedido,  Long propuestaPedidoOriginal
				   , Long propuestaPedidoUltValida, Long estadoGrid, String estado
				   , String horalimite, String resultadoWS, Long propuestaPedidoAnt
				   , String aprovisionamiento, String tipo, String id
				   , String flgOferta
				   ) {
		super();
		this.codCentro = codCentro;
		this.referencia = referencia;
		this.descReferencia = descReferencia;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.unidadesCaja = unidadesCaja;
		this.cajasPedidas = cajasPedidas;
		this.previsionVenta = previsionVenta;
		this.propuestaPedido = propuestaPedido;
		this.propuestaPedidoOriginal = propuestaPedidoOriginal;
		this.propuestaPedidoUltValida = propuestaPedidoUltValida;
		this.estadoGrid = estadoGrid;
		this.estado = estado;
		this.horaLimite=horalimite;
		this.resultadoWS=resultadoWS;
		this.propuestaPedidoAnt = propuestaPedidoAnt;
		this.id = id;
		this.flgOferta = flgOferta;
	}

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getReferencia() {
		return referencia;
	}

	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}

	public String getDescReferencia() {
		return descReferencia;
	}

	public void setDescReferencia(String descReferencia) {
		this.descReferencia = descReferencia;
	}

	public Long getReferenciaEroski() {
		return referenciaEroski;
	}

	public void setReferenciaEroski(Long referenciaEroski) {
		this.referenciaEroski = referenciaEroski;
	}

	public String getDescReferenciaEroski() {
		return descReferenciaEroski;
	}

	public void setDescReferenciaEroski(String descReferenciaEroski) {
		this.descReferenciaEroski = descReferenciaEroski;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public String getDenomArea() {
		return denomArea;
	}

	public void setDenomArea(String denomArea) {
		this.denomArea = denomArea;
	}

	public Long getSeccion() {
		return seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public String getDenomSeccion() {
		return denomSeccion;
	}

	public void setDenomSeccion(String denomSeccion) {
		this.denomSeccion = denomSeccion;
	}

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public String getDenomCategoria() {
		return denomCategoria;
	}

	public void setDenomCategoria(String denomCategoria) {
		this.denomCategoria = denomCategoria;
	}

	public Long getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Long subcategoria) {
		this.subcategoria = subcategoria;
	}

	public String getDenomSubcategoria() {
		return denomSubcategoria;
	}

	public void setDenomSubcategoria(String denomSubcategoria) {
		this.denomSubcategoria = denomSubcategoria;
	}

	public Long getSegmento() {
		return segmento;
	}

	public void setSegmento(Long segmento) {
		this.segmento = segmento;
	}

	public String getDenomSegmento() {
		return denomSegmento;
	}

	public void setDenomSegmento(String denomSegmento) {
		this.denomSegmento = denomSegmento;
	}

	public Date getFechaTransmision() {
		return fechaTransmision;
	}

	public void setFechaTransmision(Date fechaTransmision) {
		this.fechaTransmision = fechaTransmision;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Double getUnidadesCaja() {
		return unidadesCaja;
	}

	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}

	public Double getPrecioCostoArticulo() {
		return precioCostoArticulo;
	}

	public void setPrecioCostoArticulo(Double precioCostoArticulo) {
		this.precioCostoArticulo = precioCostoArticulo;
	}

	public Double getPrecioCostoInicial() {
		return precioCostoInicial;
	}

	public void setPrecioCostoInicial(Double precioCostoInicial) {
		this.precioCostoInicial = precioCostoInicial;
	}

	public Double getPrecioCostoFinal() {
		return precioCostoFinal;
	}

	public void setPrecioCostoFinal(Double precioCostoFinal) {
		this.precioCostoFinal = precioCostoFinal;
	}

	public Double getPrecioCostoFinalFinal() {
		return precioCostoFinalFinal;
	}

	public void setPrecioCostoFinalFinal(Double precioCostoFinalFinal) {
		this.precioCostoFinalFinal = precioCostoFinalFinal;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Long getCajasPedidas() {
		return cajasPedidas;
	}

	public void setCajasPedidas(Long cajasPedidas) {
		this.cajasPedidas = cajasPedidas;
	}

	public Double getPendienteRecibirManana() {
		return pendienteRecibirManana;
	}

	public void setPendienteRecibirManana(Double pendienteRecibirManana) {
		this.pendienteRecibirManana = pendienteRecibirManana;
	}

	public Double getPrevisionVenta() {
		return previsionVenta;
	}

	public void setPrevisionVenta(Double previsionVenta) {
		this.previsionVenta = previsionVenta;
	}

	public Long getPropuestaPedido() {
		return propuestaPedido;
	}

	public void setPropuestaPedido(Long propuestaPedido) {
		this.propuestaPedido = propuestaPedido;
	}

	public Long getPropuestaPedidoAnt() {
		return propuestaPedidoAnt;
	}

	public void setPropuestaPedidoAnt(Long propuestaPedidoAnt) {
		this.propuestaPedidoAnt = propuestaPedidoAnt;
	}

	public Long getPropuestaPedidoOriginal() {
		return propuestaPedidoOriginal;
	}

	public void setPropuestaPedidoOriginal(Long propuestaPedidoOriginal) {
		this.propuestaPedidoOriginal = propuestaPedidoOriginal;
	}

	public Long getPropuestaPedidoUltValida() {
		return propuestaPedidoUltValida;
	}

	public void setPropuestaPedidoUltValida(Long propuestaPedidoUltValida) {
		this.propuestaPedidoUltValida = propuestaPedidoUltValida;
	}

	public String getMarcaCompra() {
		return marcaCompra;
	}

	public void setMarcaCompra(String marcaCompra) {
		this.marcaCompra = marcaCompra;
	}

	public String getMarcaVenta() {
		return marcaVenta;
	}

	public void setMarcaVenta(String marcaVenta) {
		this.marcaVenta = marcaVenta;
	}

	public Long getReferenciaCompra() {
		return referenciaCompra;
	}

	public void setReferenciaCompra(Long referenciaCompra) {
		this.referenciaCompra = referenciaCompra;
	}

	public Double getPvpTarifa() {
		return pvpTarifa;
	}

	public void setPvpTarifa(Double pvpTarifa) {
		this.pvpTarifa = pvpTarifa;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getTirado() {
		return tirado;
	}

	public void setTirado(Double tirado) {
		this.tirado = tirado;
	}

	public Double getTiradoParasitos() {
		return tiradoParasitos;
	}

	public void setTiradoParasitos(Double tiradoParasitos) {
		this.tiradoParasitos = tiradoParasitos;
	}

	public String getRedondeoPropuesta() {
		return redondeoPropuesta;
	}

	public void setRedondeoPropuesta(String redondeoPropuesta) {
		this.redondeoPropuesta = redondeoPropuesta;
	}

	public Double getTotalVentasEspejo() {
		return totalVentasEspejo;
	}

	public void setTotalVentasEspejo(Double totalVentasEspejo) {
		this.totalVentasEspejo = totalVentasEspejo;
	}

	public Double getTotImporteVentasEspejo() {
		return totImporteVentasEspejo;
	}

	public void setTotImporteVentasEspejo(Double totImporteVentasEspejo) {
		this.totImporteVentasEspejo = totImporteVentasEspejo;
	}

	public Double getMultiplicadorVentas() {
		return multiplicadorVentas;
	}

	public void setMultiplicadorVentas(Double multiplicadorVentas) {
		this.multiplicadorVentas = multiplicadorVentas;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Date getOfertaABInicio() {
		return ofertaABInicio;
	}

	public void setOfertaABInicio(Date ofertaABInicio) {
		this.ofertaABInicio = ofertaABInicio;
	}

	public Date getOfertaABFin() {
		return ofertaABFin;
	}

	public void setOfertaABFin(Date ofertaABFin) {
		this.ofertaABFin = ofertaABFin;
	}

	public Date getOfertaCDInicio() {
		return ofertaCDInicio;
	}

	public void setOfertaCDInicio(Date ofertaCDInicio) {
		this.ofertaCDInicio = ofertaCDInicio;
	}

	public Date getOfertaCDFin() {
		return ofertaCDFin;
	}

	public void setOfertaCDFin(Date ofertaCDFin) {
		this.ofertaCDFin = ofertaCDFin;
	}

	public Double getPvp() {
		return pvp;
	}

	public void setPvp(Double pvp) {
		this.pvp = pvp;
	}

	public Long getNivel() {
		return nivel;
	}

	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}

	public Date getDiaEspejo() {
		return diaEspejo;
	}

	public void setDiaEspejo(Date diaEspejo) {
		this.diaEspejo = diaEspejo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getEstadoGrid() {
		return estadoGrid;
	}

	public void setEstadoGrid(Long estadoGrid) {
		this.estadoGrid = estadoGrid;
	}

	public String getHoraLimite() {
		return horaLimite;
	}

	public void setHoraLimite(String horaLimite) {
		this.horaLimite = horaLimite;
	}

	public String getResultadoWS() {
		return resultadoWS;
	}

	public void setResultadoWS(String resultadoWS) {
		this.resultadoWS = resultadoWS;
	}

	public Integer getTotalGuardar() {
		return totalGuardar;
	}

	public void setTotalGuardar(Integer totalGuardar) {
		this.totalGuardar = totalGuardar;
	}

	public Integer getTotalError() {
		return totalError;
	}

	public void setTotalError(Integer totalError) {
		this.totalError = totalError;
	}

//	public List<DetalleMostradorModificados> getListaModificados() {
//		return listaModificados;
//	}
//
//
//	public void setListaModificados(List<DetalleMostradorModificados> listaModificados) {
//		this.listaModificados = listaModificados;
//	}

	public String getTipoGama() {
		return tipoGama;
	}

	public void setTipoGama(String tipoGama) {
		this.tipoGama = tipoGama;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFechaSgteTransmision() {
		return fechaSgteTransmision;
	}

	public void setFechaSgteTransmision(Date fechaSgteTransmision) {
		this.fechaSgteTransmision = fechaSgteTransmision;
	}

	public String getFlgSIA() {
		return flgSIA;
	}

	public void setFlgSIA(String flgSIA) {
		this.flgSIA = flgSIA;
	}

	public Double getPdteRecibirVenta() {
		return pdteRecibirVenta;
	}

	public void setPdteRecibirVenta(Double pdteRecibirVenta) {
		this.pdteRecibirVenta = pdteRecibirVenta;
	}

	public Long getPdteRecibirManana() {
		return pdteRecibirManana;
	}

	public void setPdteRecibirManana(Long pdteRecibirManana) {
		this.pdteRecibirManana = pdteRecibirManana;
	}

	public Long getCodNecesidad() {
		return codNecesidad;
	}

	public void setCodNecesidad(Long codNecesidad) {
		this.codNecesidad = codNecesidad;
	}

	public Long getDiasCubrePedido() {
		return diasCubrePedido;
	}

	public void setDiasCubrePedido(Long diasCubrePedido) {
		this.diasCubrePedido = diasCubrePedido;
	}

	public String getEmpujePdteRecibir() {
		return empujePdteRecibir;
	}

	public void setEmpujePdteRecibir(String empujePdteRecibir) {
		this.empujePdteRecibir = empujePdteRecibir;
	}

	public Long getCodErrorRedondeo() {
		return codErrorRedondeo;
	}

	public void setCodErrorRedondeo(Long codErrorRedondeo) {
		this.codErrorRedondeo = codErrorRedondeo;
	}

	public String getDescErrorRedondeo() {
		return descErrorRedondeo;
	}

	public void setDescErrorRedondeo(String descErrorRedondeo) {
		this.descErrorRedondeo = descErrorRedondeo;
	}

	public String getFlgOferta() {
		return flgOferta;
	}

	public void setFlgOferta(String flgOferta) {
		this.flgOferta = flgOferta;
	}

	public String getFlgIncluirPropPed() {
		return flgIncluirPropPed;
	}

	public void setFlgIncluirPropPed(String flgIncluirPropPed) {
		this.flgIncluirPropPed = flgIncluirPropPed;
	}

	public String getFlgPropuesta() {
		return flgPropuesta;
	}

	public void setFlgPropuesta(String flgPropuesta) {
		this.flgPropuesta = flgPropuesta;
	}

	public String getOferta() {
		return oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}

	public Long getTipoOferta() {
		return tipoOferta;
	}

	public void setTipoOferta(Long tipoOferta) {
		this.tipoOferta = tipoOferta;
	}

	public String getConverArt() {
		return converArt;
	}

	public void setConverArt(String converArt) {
		this.converArt = converArt;
	}

	public String getRespetarIMC() {
		return respetarIMC;
	}

	public void setRespetarIMC(String respetarIMC) {
		this.respetarIMC = respetarIMC;
	}

	public Long getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(Long diferencia) {
		this.diferencia = diferencia;
	}

	public String getRefCumple() {
		return refCumple;
	}

	public void setRefCumple(String refCumple) {
		this.refCumple = refCumple;
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public Integer getErrorModificarWS() {
		return errorModificarWS;
	}

	public void setErrorModificarWS(Integer errorModificarWS) {
		this.errorModificarWS = errorModificarWS;
	}

	public Integer getErrorModificarProc() {
		return errorModificarProc;
	}

	public void setErrorModificarProc(Integer errorModificarProc) {
		this.errorModificarProc = errorModificarProc;
	}

	public Integer getCodError() {
		return codError;
	}

	public void setCodError(Integer codError) {
		this.codError = codError;
	}

	public String getFlgNoRealizarUpdate() {
		return flgNoRealizarUpdate;
	}

	public void setFlgNoRealizarUpdate(String flgNoRealizarUpdate) {
		this.flgNoRealizarUpdate = flgNoRealizarUpdate;
	}

	public List<DetalleMostradorModificados> getListaModificados() {
		return listaModificados;
	}

	public void setListaModificados(List<DetalleMostradorModificados> listaModificados) {
		this.listaModificados = listaModificados;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	public Double getVentaEspejoTarifa() {
		return ventaEspejoTarifa;
	}

	public void setVentaEspejoTarifa(Double ventaEspejoTarifa) {
		this.ventaEspejoTarifa = ventaEspejoTarifa;
	}

	public Double getVentaEspejoOferta() {
		return ventaEspejoOferta;
	}

	public void setVentaEspejoOferta(Double ventaEspejoOferta) {
		this.ventaEspejoOferta = ventaEspejoOferta;
	}

	public String getTipoAprov() {
		return tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

}