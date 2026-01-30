package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase que mantendrá los datos de la vista V_MIS_DETALLADO_MOSTRADOR.
 * @author BICAGAAN
 *
 */
public class VMisDetalladoMostrador implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long 	nivel;
	private String 	ident;		 // Identificador del nivel.
	private String 	parentident; // Identificador del nivel superior
	private String  tieneVentas; // Indicador de si tiene referencias asociadas a nivel inferior (S/null)
	private String 	idSesion;
	private Long 	codCentro;
	private Long 	area;
	private String 	denomArea;
	private Long	seccion;
	private String 	denomSeccion;
	private Long 	categoria;
	private String	denomCategoria;
	private Long	subcategoria;
	private String  denomSubcategoria;
	private Long 	segmento;
	private String	tipoAprov;
	private String  seccionGrid; // Nombre de la sección que se mostrará en el GRID.
	private Long	referencia;
	private String  descripcion;
	private Long 	referenciaEroski;
	private String  descripcionEroski;
	private Date	fechaTransmision;
	private Date 	fechaVenta;
	private Double 	unidadesCaja;
	private Double 	precioCostoArticulo;
	private Double	precioCostoInicial;
	private Double 	precioCostoFinal;
	private Long	codNecesidad;
	private Date	fechaSgteTransmision;   
	private Long	diasCubrePedido;
	private Double	margen;
	private String 	tipoGama; 
	private Double	pdteRecibirManana;
	private String 	empujePdteRecibir;
	private String 	pdteRecibirVentaGrid;
	private Double	pdteRecibirVenta;
	private String 	marcaCompra; // Flag indicador de referencia de Compra.              
	private String 	marcaVenta;  // Flag indicador de referencia de Venta.
	private Long	referenciaCompra;
	private Double	pvpTarifa;
	private Double	iva;
	private Double	tirado;
	private String	tiradoParasitos;
	private Double	previsionVenta;
	private Long	propuestaPedido;
	private Long	propuestaPedidoAnt;
	private String	redondeoPropuesta;
	private Double	totalVentasEspejo;
	private Double	totImporteVentasEspejo;
	private Double	multiplicadorVentas;
	private Long	orden;
	private String 	ofertaAB;
	private String 	ofertaCD;
	private	Date	ofertaABInicio;
	private Date	ofertaABFin;
	private Date	ofertaCDInicio;
	private Date	ofertaCDFin;
	private Double	pvp;
	private Date 	diaEspejo;
	private String 	horaLimite;            
	private Double	ventaEspejoTarifa;
	private Double	ventaEspejoOferta;

	// Estado en la que se encuentra el PEDIDO.
	// P-Pendiente(negro).
	// R-Revisada (verde).
	// B-Bloqueada (fucsia).
	// I-Integrada (rojo).
	//
	// Solo se podra modificar la cantidad cuando esta en estado P o R.
	private String	estado;
	
	//Campos para control de errores
	private Long	estadoGrid;
	private String	descripcionError;

	// Propiedades auxiliares
	private String rotacion;
	
	//Stock
	private Double stock=null;
	
	public VMisDetalladoMostrador() {
		super();
	}

	public VMisDetalladoMostrador( Long nivel
								 , String ident
								 , String parentident
								 , String tieneVentas
								 , String idSesion
								 , Long codCentro
								 , Long area, String denomArea
								 , Long seccion, String denomSeccion
								 , Long categoria, String denomCategoria
								 , Long subcategoria, String denomSubcategoria
								 , Long segmento
								 , String tipoAprov
								 , String seccionGrid
								 , Long referencia, String descripcion, Long referenciaEroski, String descripcionEroski
								 , Date fechaTransmision, Date fechaVenta
								 , Double unidadesCaja, Double precioCostoArticulo, Double precioCostoInicial, Double precioCostoFinal
								 , Long codNecesidad
								 , Date fechaSgteTransmision
								 , Long diasCubrePedido
								 , Double margen
								 , String tipoGama
								 , Double pdteRecibirManana
								 , String empujePdteRecibir
								 , String pdteRecibirVentaGrid
								 , Double pdteRecibirVenta
								 , String marcaCompra
								 , String marcaVenta
								 , Long referenciaCompra
								 , Double pvpTarifa
								 , Double iva
								 , Double tirado
								 , String tiradoParasitos
								 , Double previsionVenta
								 , Long propuestaPedido
								 , Long propuestaPedidoAnt
								 , String redondeoPropuesta
								 , Double totalVentasEspejo
								 , Double totImporteVentasEspejo
								 , Double multiplicadorVentas
								 , Long orden
								 , String ofertaAB, String ofertaCD
								 , Date ofertaABInicio, Date ofertaABFin
								 , Date ofertaCDInicio, Date ofertaCDFin
								 , Double pvp
								 , Date dia_espejo
								 , String horaLimite
								 , String estado
								 , Long estadoGrid
								 , String descripcionError
								 , Double ventaEspejoTarifa
								 , Double ventaEspejoOferta
								 ) {
		super();
		this.nivel = nivel;
		this.ident = ident;
		this.parentident = parentident;
		this.tieneVentas = tieneVentas;
		this.idSesion = idSesion;
		this.codCentro = codCentro;
		this.area = area;
		this.denomArea = denomArea;
		this.seccion = seccion;
		this.denomSeccion = denomSeccion;
		this.categoria = categoria;
		this.denomCategoria = denomCategoria;
		this.subcategoria = subcategoria;
		this.denomSubcategoria = denomSubcategoria;
		this.segmento = segmento;
		this.tipoAprov = tipoAprov;
		this.seccionGrid = seccionGrid;
		this.referencia = referencia;
		this.descripcion = descripcion;
		this.referenciaEroski = referenciaEroski;
		this.descripcionEroski = descripcionEroski;
		this.fechaTransmision = fechaTransmision;
		this.fechaVenta = fechaVenta;
		this.unidadesCaja = unidadesCaja;
		this.precioCostoArticulo = precioCostoArticulo;
		this.precioCostoInicial = precioCostoInicial;
		this.precioCostoFinal = precioCostoFinal;
		this.codNecesidad = codNecesidad;
		this.fechaSgteTransmision = fechaSgteTransmision;
		this.diasCubrePedido = diasCubrePedido;
		this.margen = margen;
		this.tipoGama = tipoGama;
		this.pdteRecibirManana = pdteRecibirManana;
		this.empujePdteRecibir = empujePdteRecibir;
		this.pdteRecibirVentaGrid = pdteRecibirVentaGrid;
		this.pdteRecibirVenta = pdteRecibirVenta;
		this.marcaCompra = marcaCompra;
		this.marcaVenta = marcaVenta;
		this.referenciaCompra = referenciaCompra;
		this.pvpTarifa = pvpTarifa;
		this.iva = iva;
		this.tirado = tirado;
		this.tiradoParasitos = tiradoParasitos;
		this.previsionVenta = previsionVenta;
		this.propuestaPedido = propuestaPedido;
		this.propuestaPedidoAnt = propuestaPedidoAnt;
		this.redondeoPropuesta = redondeoPropuesta;
		this.totalVentasEspejo = totalVentasEspejo;
		this.totImporteVentasEspejo = totImporteVentasEspejo;
		this.multiplicadorVentas = multiplicadorVentas;
		this.orden = orden;
		this.ofertaAB = ofertaAB;
		this.ofertaCD = ofertaCD;
		this.ofertaABInicio = ofertaABInicio;
		this.ofertaABFin = ofertaABFin;
		this.ofertaCDInicio = ofertaCDInicio;
		this.ofertaCDFin = ofertaCDFin;
		this.pvp = pvp;
		this.diaEspejo = dia_espejo;
		this.horaLimite = horaLimite;
		this.estado = estado;
		this.estadoGrid = estadoGrid;
		this.descripcionError = descripcionError;
		this.ventaEspejoTarifa = ventaEspejoTarifa;
		this.ventaEspejoOferta = ventaEspejoOferta;

	}

	public Long getNivel() {
		return nivel;
	}

	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getParentident() {
		return parentident;
	}

	public void setParentident(String parentident) {
		this.parentident = parentident;
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

	public String getTipoAprov() {
		return tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public String getSeccionGrid() {
		return seccionGrid;
	}

	public void setSeccionGrid(String seccionGrid) {
		this.seccionGrid = seccionGrid;
	}

	public Long getReferencia() {
		return referencia;
	}

	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getReferenciaEroski() {
		return referenciaEroski;
	}

	public void setReferenciaEroski(Long referenciaEroski) {
		this.referenciaEroski = referenciaEroski;
	}

	public String getDescripcionEroski() {
		return descripcionEroski;
	}

	public void setDescripcionEroski(String descripcionEroski) {
		this.descripcionEroski = descripcionEroski;
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

	public Long getCodNecesidad() {
		return codNecesidad;
	}

	public void setCodNecesidad(Long codNecesidad) {
		this.codNecesidad = codNecesidad;
	}

	public Date getFechaSgteTransmision() {
		return fechaSgteTransmision;
	}

	public void setFechaSgteTransmision(Date fechaSgteTransmision) {
		this.fechaSgteTransmision = fechaSgteTransmision;
	}

	public Long getDiasCubrePedido() {
		return diasCubrePedido;
	}

	public void setDiasCubrePedido(Long diasCubrePedido) {
		this.diasCubrePedido = diasCubrePedido;
	}

	public String getTipoGama() {
		return tipoGama;
	}

	public void setTipoGama(String tipoGama) {
		this.tipoGama = tipoGama;
	}

	public Double getPdteRecibirManana() {
		return pdteRecibirManana;
	}

	public void setPdteRecibirManana(Double pdteRecibirManana) {
		this.pdteRecibirManana = pdteRecibirManana;
	}

	public String getEmpujePdteRecibir() {
		return empujePdteRecibir;
	}

	public void setEmpujePdteRecibir(String empujePdteRecibir) {
		this.empujePdteRecibir = empujePdteRecibir;
	}

	public Double getPdteRecibirVenta() {
		return pdteRecibirVenta;
	}

	public void setPdteRecibirVenta(Double pdteRecibirVenta) {
		this.pdteRecibirVenta = pdteRecibirVenta;
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

	public String getTiradoParasitos() {
		return tiradoParasitos;
	}

	public void setTiradoParasitos(String tiradoParasitos) {
		this.tiradoParasitos = tiradoParasitos;
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

	public String getOfertaAB() {
		return ofertaAB;
	}

	public void setOfertaAB(String ofertaAB) {
		this.ofertaAB = ofertaAB;
	}

	public String getOfertaCD() {
		return ofertaCD;
	}

	public void setOfertaCD(String ofertaCD) {
		this.ofertaCD = ofertaCD;
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

	public Date getDiaEspejo() {
		return diaEspejo;
	}

	public void setDiaEspejo(Date diaEspejo) {
		this.diaEspejo = diaEspejo;
	}

	public String getHoraLimite() {
		return horaLimite;
	}

	public void setHoraLimite(String horaLimite) {
		this.horaLimite = horaLimite;
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

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	public String getRotacion() {
		return rotacion;
	}

	public void setRotacion(String rotacion) {
		this.rotacion = rotacion;
	}

	public Double getMargen() {
		return margen;
	}

	public void setMargen(Double margen) {
		this.margen = margen;
	}

	public String getTieneVentas() {
		return tieneVentas;
	}

	public void setTieneVentas(String tieneVentas) {
		this.tieneVentas = tieneVentas;
	}

	public String getPdteRecibirVentaGrid() {
		return pdteRecibirVentaGrid;
	}

	public void setPdteRecibirVentaGrid(String pdteRecibirVentaGrid) {
		this.pdteRecibirVentaGrid = pdteRecibirVentaGrid;
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

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}	

}