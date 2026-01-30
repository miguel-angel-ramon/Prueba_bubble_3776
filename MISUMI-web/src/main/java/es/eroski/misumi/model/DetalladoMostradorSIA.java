package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class DetalladoMostradorSIA implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc;
	private String codN1Ref;
	private String denomArea;
	private String codN2Ref;
	private String denomSeccion;
	private String codN3Ref;
	private String denomCategoria;
	private String codN4Ref;
	private String denomSubcategoria;
	private String codN5Ref;
	private String denomSegmento;
	private Long codArticulo;
	private String denomRef;
	private String tipoAprov;
	private Long referenciaCompra;
	private String tipoGama;
	private String tipoDetallado;
	private Date fecTransmision;
	private Date fecVentaDiaTrans;
	private Date fecSigTransmision;
	private Date fecVentaSigTrans;
	private Long diasCubrePed;
	private Double pdteRecManana;
	private String empujePdteRecManana;
	private Double pdteRecFecVenta;
	private Double unidadesCaja;
	private String marcaCompra;
	private String marcaVenta;
	private Long refCompra;
	private Double multCompraVenta;
	private Double pvpTarifa;
	private Double costo;
	private Double iva;
	private Double tirado;
	private Double tiradoParasitos;
	private Double previsionVenta;
	private Long propuestaPedido;
	private Long propuestaPedidoAnt;
	private String redondeoPropPedido;
	private Double totVenFecEspejo;
	private Double totImpVenFecEspejo;
	
	private Long numOrden;  
	private Long codNecesidad;  
	private String estado;    
	private String horaFinBomba;
	
	private Long unidEncargoFl;    
	private Long unidPropuestasFlOrigen;
	private Long unidPropuestasFlAnt;     
	private Long unidPropuestasFlModif;  
	
	private Double pendienteTienda;
	private Double pendienteTiendaManana;
	private Date fechaPedidoProx;
	private Long unidFlEmpuje;  
	private Long codError;
	private String descError;
	private Double ufp;  
	private String flgOferta;
	private String oferta;
	private Long tipoOferta;  
	private String flgSIA;
	private String converArt;
	
	private Double precioCostoInicial;
	private Double precioCostoFinal;

	private String soloVenta;
	private String gamaLocal;
	private String fechaEspejo;
	
	private Double ventaEspejoTarifa;
	private Double ventaEspejoOferta;

	public DetalladoMostradorSIA() {
		super();
	}

	public DetalladoMostradorSIA( Long codLoc, String codN1Ref, String codN2Ref, String codN3Ref, String codN4Ref, String codN5Ref
								, Long codArticulo, String denomRef, Double stockTienda, Double pendienteTienda
								, Double pendienteTiendaManana, Double unidCaja, Long unidEncargoFl
								, Long unidPropuestasFlOrigen, Long unidPropuestasFlAnt, Long unidPropuestasFlModif
								, String tipoDetallado, String horaLimite, String estado, Date fechaEntrega
								, Date fechaPedidoProx, Long unidFlEmpuje
								, Long codNecesidad, Long codError, String descError
								) {
		
		super();
		this.codLoc=codLoc;
		this.codN1Ref=codN1Ref;
		this.codN2Ref=codN2Ref;
		this.codN3Ref=codN3Ref;
		this.codN4Ref=codN4Ref;
		this.codN5Ref=codN5Ref;
		this.codArticulo=codArticulo;
		this.denomRef=denomRef;
		this.pendienteTienda=pendienteTienda;
		this.pendienteTiendaManana=pendienteTiendaManana;
		this.tipoDetallado=tipoDetallado;
		this.unidadesCaja=unidCaja;    
		this.unidEncargoFl=unidEncargoFl;    
		this.unidPropuestasFlOrigen=unidPropuestasFlOrigen;
		this.unidPropuestasFlAnt=unidPropuestasFlAnt;     
		this.unidPropuestasFlModif=unidPropuestasFlModif;  
		this.horaFinBomba=horaLimite;
		this.estado=estado;  
		this.fecVentaSigTrans=fechaEntrega; 
		this.fechaPedidoProx=fechaPedidoProx; 
		this.unidFlEmpuje=unidFlEmpuje; 
		this.codNecesidad=codNecesidad; 
		this.codError=codError;
		this.descError=descError;
	}
	
	public DetalladoMostradorSIA( Long codLoc
							    , String codN1Ref, String denomArea
							    , String codN2Ref, String denomSeccion
							    , String codN3Ref, String denomCategoria
							    , String codN4Ref, String denomSubcategoria
							    , String codN5Ref, String denomSegmento
							    , Long codArticulo, String denomRef
							    , String tipoAprov
							    , String tipoGama
							    , Date fecTransmision
							    , Date fecVentaDiaTrans
							    , Date fecSigTransmision
							    , Date fecVentaSigTrans
							    , Long diasCubrePed
							    , Double pdteRecManana
							    , String empujePdteRecManana
							    , Double pdteRecFecVenta
							    , Double unidadesCaja
							    , String marcaCompra
							    , String marcaVenta
							    , Long refCompra
							    , Double multCompraVenta
							    , Double pvpTarifa
							    , Double costo
							    , Double iva
							    , Double tirado
							    , Double tiradoParasitos
							    , Double previsionVenta
							    , Long propuestaPedido
							    , String redondeoPropPedido
							    , Double totVenFecEspejo
							    , Double totImpVenFecEspejo
							    , Long orden
							    , Long codNecesidad
							    , String estado
							    , String horaFinBomba
							    , Double ventaEspejoTarifa
							    , Double ventaEspejoOferta
							    ) {
		super();
		this.codLoc = codLoc;
		this.codN1Ref = codN1Ref;
		this.denomArea = denomArea;
		this.codN2Ref = codN2Ref;
		this.denomSeccion = denomSeccion;
		this.codN3Ref = codN3Ref;
		this.denomCategoria = denomCategoria;
		this.codN4Ref = codN4Ref;
		this.denomSubcategoria = denomSubcategoria;
		this.codN5Ref = codN5Ref;
		this.denomSegmento = denomSegmento;
		
		this.codArticulo = codArticulo;
		this.denomRef = denomRef;
		this.tipoAprov = tipoAprov;
		this.tipoGama = tipoGama;
		this.fecTransmision = fecTransmision;
		this.fecVentaDiaTrans = fecVentaDiaTrans;
		this.fecSigTransmision = fecSigTransmision;
		this.fecVentaSigTrans = fecVentaSigTrans;
		this.diasCubrePed = diasCubrePed;
		this.pdteRecManana = pdteRecManana;
		this.empujePdteRecManana = empujePdteRecManana;
		this.pdteRecFecVenta = pdteRecFecVenta;
		this.unidadesCaja = unidadesCaja;
		this.marcaCompra = marcaCompra;
		this.marcaVenta = marcaVenta;
		this.referenciaCompra = refCompra;
		this.multCompraVenta = multCompraVenta;
		this.pvpTarifa = pvpTarifa;
		this.costo = costo;
		this.iva = iva;
		this.tirado = tirado;
		this.tiradoParasitos = tiradoParasitos;
		this.previsionVenta = previsionVenta;
		this.propuestaPedido = propuestaPedido;
		this.redondeoPropPedido = redondeoPropPedido;
	    this.totVenFecEspejo = totVenFecEspejo;
	    this.totImpVenFecEspejo = totImpVenFecEspejo;
	    this.numOrden = orden;
		this.codNecesidad = codNecesidad;
		this.estado = estado;
		this.horaFinBomba = horaFinBomba;
	    this.ventaEspejoTarifa = ventaEspejoTarifa; 
	    this.ventaEspejoOferta = ventaEspejoOferta; 
	}
	
	public Long getCodLoc() {
		return codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}
	
	public String getCodN1Ref() {
		return codN1Ref;
	}

	public void setCodN1Ref(String codN1Ref) {
		this.codN1Ref = codN1Ref;
	}

	public String getCodN2Ref() {
		return codN2Ref;
	}

	public void setCodN2Ref(String codN2Ref) {
		this.codN2Ref = codN2Ref;
	}

	public String getCodN3Ref() {
		return codN3Ref;
	}

	public void setCodN3Ref(String codN3Ref) {
		this.codN3Ref = codN3Ref;
	}

	public String getCodN4Ref() {
		return codN4Ref;
	}

	public void setCodN4Ref(String codN4Ref) {
		this.codN4Ref = codN4Ref;
	}

	public String getCodN5Ref() {
		return codN5Ref;
	}

	public void setCodN5Ref(String codN5Ref) {
		this.codN5Ref = codN5Ref;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getDenomRef() {
		return denomRef;
	}

	public void setDenomRef(String denomRef) {
		this.denomRef = denomRef;
	}

	public String getTipoAprov() {
		return tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public Double getPendienteTienda() {
		return pendienteTienda;
	}

	public void setPendienteTienda(Double pendienteTienda) {
		this.pendienteTienda = pendienteTienda;
	}
	
	public Double getPendienteTiendaManana() {
		return pendienteTiendaManana;
	}

	public void setPendienteTiendaManana(Double pendienteTiendaManana) {
		this.pendienteTiendaManana = pendienteTiendaManana;
	}

	public Long getUnidEncargoFl() {
		return unidEncargoFl;
	}

	public void setUnidEncargoFl(Long unidEncargoFl) {
		this.unidEncargoFl = unidEncargoFl;
	}

	public Long getUnidPropuestasFlOrigen() {
		return unidPropuestasFlOrigen;
	}

	public void setUnidPropuestasFlOrigen(Long unidPropuestasFlOrigen) {
		this.unidPropuestasFlOrigen = unidPropuestasFlOrigen;
	}

	public Long getUnidPropuestasFlAnt() {
		return unidPropuestasFlAnt;
	}

	public void setUnidPropuestasFlAnt(Long unidPropuestasFlAnt) {
		this.unidPropuestasFlAnt = unidPropuestasFlAnt;
	}

	public Long getUnidPropuestasFlModif() {
		return unidPropuestasFlModif;
	}

	public void setUnidPropuestasFlModif(Long unidPropuestasFlModif) {
		this.unidPropuestasFlModif = unidPropuestasFlModif;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Date getFechaPedidoProx() {
		return fechaPedidoProx;
	}

	public void setFechaPedidoProx(Date fechaPedidoProx) {
		this.fechaPedidoProx = fechaPedidoProx;
	}

	public Long getUnidFlEmpuje() {
		return unidFlEmpuje;
	}

	public void setUnidFlEmpuje(Long unidFlEmpuje) {
		this.unidFlEmpuje = unidFlEmpuje;
	}

	public Long getCodNecesidad() {
		return codNecesidad;
	}

	public void setCodNecesidad(Long codNecesidad) {
		this.codNecesidad = codNecesidad;
	}

	
	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public String getFlgOferta() {
		return flgOferta;
	}

	public void setFlgOferta(String flgOferta) {
		this.flgOferta = flgOferta;
	}
	
	public Double getUfp() {
		return ufp;
	}

	public void setUfp(Double ufp) {
		this.ufp = ufp;
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

	public String getFlgSIA() {
		return flgSIA;
	}

	public void setFlgSIA(String flgSIA) {
		this.flgSIA = flgSIA;
	}
	
	public String getConverArt() {
		return converArt;
	}

	public void setConverArt(String converArt) {
		this.converArt = converArt;
	}
	
	public String getTipoGama() {
		return tipoGama;
	}

	public void setTipo(String tipoGama) {
		this.tipoGama = tipoGama;
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
	
	public String getDenomArea() {
		return denomArea;
	}

	public void setDenomArea(String denomArea) {
		this.denomArea = denomArea;
	}

	public String getDenomSeccion() {
		return denomSeccion;
	}

	public void setDenomSeccion(String denomSeccion) {
		this.denomSeccion = denomSeccion;
	}

	public String getDenomCategoria() {
		return denomCategoria;
	}

	public void setDenomCategoria(String denomCategoria) {
		this.denomCategoria = denomCategoria;
	}

	public String getDenomSubcategoria() {
		return denomSubcategoria;
	}

	public void setDenomSubcategoria(String denomSubcategoria) {
		this.denomSubcategoria = denomSubcategoria;
	}

	public String getDenomSegmento() {
		return denomSegmento;
	}

	public void setDenomSegmento(String denomSegmento) {
		this.denomSegmento = denomSegmento;
	}

	public String getSoloVenta() {
		return soloVenta;
	}

	public void setSoloVenta(String soloVenta) {
		this.soloVenta = soloVenta;
	}

	public String getGamaLocal() {
		return gamaLocal;
	}

	public void setGamaLocal(String gamaLocal) {
		this.gamaLocal = gamaLocal;
	}

	public String getFechaEspejo() {
		return fechaEspejo;
	}

	public void setFechaEspejo(String fechaEspejo) {
		this.fechaEspejo = fechaEspejo;
	}

	public void setTipoGama(String tipoGama) {
		this.tipoGama = tipoGama;
	}

	public Long getReferenciaCompra() {
		return referenciaCompra;
	}

	public void setReferenciaCompra(Long referenciaCompra) {
		this.referenciaCompra = referenciaCompra;
	}

	public Date getFecTransmision() {
		return fecTransmision;
	}

	public void setFecTransmision(Date fecTransmision) {
		this.fecTransmision = fecTransmision;
	}

	public Date getFecVentaDiaTrans() {
		return fecVentaDiaTrans;
	}

	public void setFecVentaDiaTrans(Date fecVentaDiaTrans) {
		this.fecVentaDiaTrans = fecVentaDiaTrans;
	}

	public Date getFecSigTransmision() {
		return fecSigTransmision;
	}

	public void setFecSigTransmision(Date fecSigTransmision) {
		this.fecSigTransmision = fecSigTransmision;
	}

	public Date getFecVentaSigTrans() {
		return fecVentaSigTrans;
	}

	public void setFecVentaSigTrans(Date fecVentaSigTrans) {
		this.fecVentaSigTrans = fecVentaSigTrans;
	}

	public Long getDiasCubrePed() {
		return diasCubrePed;
	}

	public void setDiasCubrePed(Long diasCubrePed) {
		this.diasCubrePed = diasCubrePed;
	}

	public Double getPdteRecManana() {
		return pdteRecManana;
	}

	public void setPdteRecManana(Double pdteRecManana) {
		this.pdteRecManana = pdteRecManana;
	}

	public String getEmpujePdteRecManana() {
		return empujePdteRecManana;
	}

	public void setEmpujePdteRecManana(String empujePdteRecManana) {
		this.empujePdteRecManana = empujePdteRecManana;
	}

	public Double getPdteRecFecVenta() {
		return pdteRecFecVenta;
	}

	public void setPdteRecFecVenta(Double pdteRecFecVenta) {
		this.pdteRecFecVenta = pdteRecFecVenta;
	}

	public Double getUnidadesCaja() {
		return unidadesCaja;
	}

	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
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

	public Long getRefCompra() {
		return refCompra;
	}

	public void setRefCompra(Long refCompra) {
		this.refCompra = refCompra;
	}

	public Double getMultCompraVenta() {
		return multCompraVenta;
	}

	public void setMultCompraVenta(Double multCompraVenta) {
		this.multCompraVenta = multCompraVenta;
	}

	public Double getPvpTarifa() {
		return pvpTarifa;
	}

	public void setPvpTarifa(Double pvpTarifa) {
		this.pvpTarifa = pvpTarifa;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
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

	public Double getPrevisionVenta() {
		return previsionVenta;
	}

	public void setPrevisionVenta(Double previsionVenta) {
		this.previsionVenta = previsionVenta;
	}

	public String getRedondeoPropPedido() {
		return redondeoPropPedido;
	}

	public void setRedondeoPropPedido(String redondeoPropPedido) {
		this.redondeoPropPedido = redondeoPropPedido;
	}

	public Double getTotVenFecEspejo() {
		return totVenFecEspejo;
	}

	public void setTotVenFecEspejo(Double totVenFecEspejo) {
		this.totVenFecEspejo = totVenFecEspejo;
	}

	public Double getTotImpVenFecEspejo() {
		return totImpVenFecEspejo;
	}

	public void setTotImpVenFecEspejo(Double totImpVenFecEspejo) {
		this.totImpVenFecEspejo = totImpVenFecEspejo;
	}

	public Long getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(Long numOrden) {
		this.numOrden = numOrden;
	}

	public String getHoraFinBomba() {
		return horaFinBomba;
	}

	public void setHoraFinBomba(String horaFinBomba) {
		this.horaFinBomba = horaFinBomba;
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

	public String getTipoDetallado() {
		return tipoDetallado;
	}

	public void setTipoDetallado(String tipoDetallado) {
		this.tipoDetallado = tipoDetallado;
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

}