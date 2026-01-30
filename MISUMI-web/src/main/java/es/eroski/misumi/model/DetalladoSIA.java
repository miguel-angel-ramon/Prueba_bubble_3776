package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class DetalladoSIA implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc;
	private String codN1Ref;
	private String codN2Ref;
	private String codN3Ref;
	private String codN4Ref;
	private String codN5Ref;
	private Long codArticulo;
	private String denomRef;
	private Double stockTienda;
	private Double pendienteTienda;
	private Double pendienteTiendaManana;
	private Double unidCaja;    
	private Long unidEncargoFl;    
	private Long unidPropuestasFlOrigen;
	private Long unidPropuestasFlAnt;     
	private Double unidPropuestasFlModif;  
	private String tipoDetallado;
	private String   horaLimite;
	private String estado;    
	private Date fechaEntrega;
	private Date fechaPedidoProx;
	private Long unidFlEmpuje;  
	private Long codNecesidad;  
	private Long codError;
	private String descError;
	private Double ufp;  
	private String flgOferta;
	private String oferta;
	private Long tipoOferta;  
	private String flgSIA;
	private String tipoAprov;
	private String temporada;
	private Long numOrden; 
	private String modeloProveedor;
	private String descrTalla;
	private String descrColor;
	private String converArt;
	private String tipo;
	private Long nivelLote;
	private Long facing;
	private String flgPropuesta;
	
	

	private String denomArea;
	private String denomSeccion;
	private String denomCategoria;
	private String denomSubcategoria;
	private String denomSegmento;
	private Double precioCostoArticulo;  
	private Double precioCostoInicial;  
	private Double precioCostoFinal;  
	


	public DetalladoSIA() {
		super();
	}


	public DetalladoSIA( Long codLoc, String codN1Ref, String codN2Ref, String codN3Ref, String codN4Ref
					   , String codN5Ref, Long codArticulo, String denomRef, Double stockTienda, Double pendienteTienda
					   , Double pendienteTiendaManana, Double unidCaja, Long unidEncargoFl, Long unidPropuestasFlOrigen
					   , Long unidPropuestasFlAnt, Double unidPropuestasFlModif, String tipoDetallado, String horaLimite
					   , String estado, Date fechaEntrega, Date fechaPedidoProx, Long unidFlEmpuje, Long codNecesidad, Long codError
					   , String descError, Double ufp, String flgOferta, String oferta, Long tipoOferta,  String flgSIA
					   , String tipoAprov,String temporada, Long numOrden, String modeloProveedor, String descrTalla
					   , String descrColor, String converArt, String tipo, Long nivelLote, Long facing, String flgPropuesta
					   , String denomArea,String denomSeccion,String denomCategoria,String denomSubcategoria,String denomSegmento
					   , Double precioCostoArticulo, Double precioCostoInicial, Double precioCostoFinal
					   ) {
			
		super();
		this.codLoc = codLoc;
		this.codN1Ref = codN1Ref;
		this.codN2Ref = codN2Ref;
		this.codN3Ref = codN3Ref;
		this.codN4Ref = codN4Ref;
		this.codN5Ref = codN5Ref;
		this.codArticulo = codArticulo;
		this.denomRef = denomRef;
		this.stockTienda = stockTienda;
		this.pendienteTienda = pendienteTienda;
		this.pendienteTiendaManana = pendienteTiendaManana;
		this.unidCaja = unidCaja;
		this.unidEncargoFl = unidEncargoFl;
		this.unidPropuestasFlOrigen = unidPropuestasFlOrigen;
		this.unidPropuestasFlAnt = unidPropuestasFlAnt;
		this.unidPropuestasFlModif = unidPropuestasFlModif;
		this.tipoDetallado = tipoDetallado;
		this.horaLimite = horaLimite;
		this.estado = estado;
		this.fechaEntrega = fechaEntrega;
		this.fechaPedidoProx = fechaPedidoProx;
		this.unidFlEmpuje = unidFlEmpuje;
		this.codNecesidad = codNecesidad;
		this.codError = codError;
		this.descError = descError;
		this.ufp = ufp;
		this.flgOferta = flgOferta;
		this.oferta = oferta;
		this.tipoOferta = tipoOferta;
		this.flgSIA = flgSIA;
		this.tipoAprov = tipoAprov;
		this.temporada = temporada;
		this.numOrden = numOrden;
		this.modeloProveedor = modeloProveedor;
		this.descrTalla = descrTalla;
		this.descrColor = descrColor;
		this.converArt = converArt;
		this.tipo = tipo;
		this.nivelLote = nivelLote;
		this.facing = facing;
		this.flgPropuesta = flgPropuesta;
		
		this.denomArea = denomArea;
		this.denomSeccion = denomSeccion;
		this.denomCategoria = denomCategoria;
		this.denomSubcategoria = denomSubcategoria;
		this.denomSegmento = denomSegmento;
		
		this.precioCostoArticulo = precioCostoArticulo;
		this.precioCostoInicial = precioCostoInicial;
		this.precioCostoFinal = precioCostoFinal;

	
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

	public Double getStockTienda() {
		return stockTienda;
	}

	public void setStockTienda(Double stockTienda) {
		this.stockTienda = stockTienda;
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


	public Double getUnidCaja() {
		return unidCaja;
	}

	public void setUnidCaja(Double unidCaja) {
		this.unidCaja = unidCaja;
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

	public Double getUnidPropuestasFlModif() {
		return unidPropuestasFlModif;
	}

	public void setUnidPropuestasFlModif(Double unidPropuestasFlModif) {
		this.unidPropuestasFlModif = unidPropuestasFlModif;
	}

	public String getTipoDetallado() {
		return tipoDetallado;
	}

	public void setTipoDetallado(String tipoDetallado) {
		this.tipoDetallado = tipoDetallado;
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
	
	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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
		oferta = oferta;
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
	
	public String getTipoAprov() {
		return tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public Long getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(Long numOrden) {
		this.numOrden = numOrden;
	}

	public String getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}

	public String getDescrTalla() {
		return descrTalla;
	}

	public void setDescrTalla(String descrTalla) {
		this.descrTalla = descrTalla;
	}

	public String getDescrColor() {
		return descrColor;
	}

	public void setDescrColor(String descrColor) {
		this.descrColor = descrColor;
	}
	
	public String getConverArt() {
		return converArt;
	}

	public void setConverArt(String converArt) {
		this.converArt = converArt;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getNivelLote() {
		return nivelLote;
	}

	public void setNivelLote(Long nivelLote) {
		this.nivelLote = nivelLote;
	}

	public Long getFacing() {
		return facing;
	}

	public void setFacing(Long facing) {
		this.facing = facing;
	}

	public String getFlgPropuesta() {
		return flgPropuesta;
	}

	public void setFlgPropuesta(String flgPropuesta) {
		this.flgPropuesta = flgPropuesta;
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
	
	
}