package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MovimientoStock implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String fecha;
	private String esFestivo;
	private String esHoy;
	private Long codCentro;
	private Long codArt;
	private Float stockInicial;
	private Float stockKgVal;
	private Float stockPenReci;
	private Float entradas;
	private Float salidas;
	private Float salidasPromo;
	private Float salidasForz;
	private Float ajusteConteo;
	private Float regular;
	private Date fechaGen;
	private String rotura;
	private Float stockFinal;
	
	private Double totalVentaTarifa;
	private Double totalVentaAnticipada;
	private Double totalVentaOferta;
	private Double totalVentaCompetencia;
	private Double totalEntradas;
	private Double totalModifAjuste;
	private Double totalModifRegul;
	
	//Campo para control de error de WS en el día de hoy
	private int flgErrorWSStockTienda;
	private String mensajeErrorWSStockTienda;
	
	//Referencias para movimientos
	private List<Long> referencias;

	public MovimientoStock(){
			super();
	}
	
    public MovimientoStock( String fecha, String esFestivo, String esHoy, Long codCentro, Long codArt, Float stockInicial
    					  , Float stockKgVal, Float stockPenReci, Float entradas, Float salidas, Float salidasPromo
    					  , Float salidasForz, Float ajusteConteo, Float regular, Date fechaGen, String rotura, Float stockFinal
    					  ){
    	
    	super();
    	this.fecha = fecha;
    	this.esFestivo = esFestivo;
    	this.esHoy = esHoy;
    	this.codCentro = codCentro;
    	this.codArt = codArt;
    	this.stockInicial = stockInicial;
    	this.stockKgVal = stockKgVal;
    	this.stockPenReci = stockPenReci;
    	this.entradas = entradas;
    	this.salidas = salidas;
    	this.salidasPromo = salidasPromo;
    	this.salidasForz = salidasForz;
    	this.ajusteConteo = ajusteConteo;
    	this.regular = regular;
    	this.fechaGen = fechaGen;
    	this.rotura = rotura;
    	this.stockFinal = stockFinal;
    	
    }

    public MovimientoStock(Long codCentro, Long codArt){

    	super();
    	this.fecha = null;
    	this.esFestivo = null;
    	this.esHoy = null;
    	this.codCentro = codCentro;
		this.codArt = codArt;
		this.stockInicial = null;
		this.stockKgVal = null;
		this.stockPenReci = null;
		this.entradas = null;
		this.salidas = null;
		this.salidasPromo = null;
		this.salidasForz = null;
		this.ajusteConteo = null;
		this.regular = null;
		this.fechaGen = null;
		this.rotura = null;
		this.stockFinal = null;

    	this.totalVentaTarifa = null;
    	this.totalVentaAnticipada = null;
    	this.totalVentaOferta = null;
    	this.totalVentaCompetencia = null;
    	this.totalEntradas = null;
    	this.totalModifAjuste = null;
    	this.totalModifRegul = null;

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

	public Float getStockInicial() {
		return this.stockInicial;
	}

	public void setStockInicial(Float stockInicial) {
		this.stockInicial = stockInicial;
	}

	public Float getStockKgVal() {
		return this.stockKgVal;
	}

	public void setStockKgVal(Float stockKgVal) {
		this.stockKgVal = stockKgVal;
	}

	public Float getStockPenReci() {
		return this.stockPenReci;
	}

	public void setStockPenReci(Float stockPenReci) {
		this.stockPenReci = stockPenReci;
	}

	public Float getEntradas() {
		return this.entradas;
	}

	public void setEntradas(Float entradas) {
		this.entradas = entradas;
	}

	public Float getSalidas() {
		return this.salidas;
	}

	public void setSalidas(Float salidas) {
		this.salidas = salidas;
	}

	public Float getSalidasPromo() {
		return this.salidasPromo;
	}

	public void setSalidasPromo(Float salidasPromo) {
		this.salidasPromo = salidasPromo;
	}

	public Float getSalidasForz() {
		return this.salidasForz;
	}

	public void setSalidasForz(Float salidasForz) {
		this.salidasForz = salidasForz;
	}

	public Float getAjusteConteo() {
		return this.ajusteConteo;
	}

	public void setAjusteConteo(Float ajusteConteo) {
		this.ajusteConteo = ajusteConteo;
	}

	public Float getRegular() {
		return this.regular;
	}

	public void setRegular(Float regular) {
		this.regular = regular;
	}

	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public String getRotura() {
		return this.rotura;
	}

	public void setRotura(String rotura) {
		this.rotura = rotura;
	}

	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEsFestivo() {
		return this.esFestivo;
	}

	public void setEsFestivo(String esFestivo) {
		this.esFestivo = esFestivo;
	}
    
	public String getEsHoy() {
		return this.esHoy;
	}

	public void setEsHoy(String esHoy) {
		this.esHoy = esHoy;
	}

	public int getFlgErrorWSStockTienda() {
		return this.flgErrorWSStockTienda;
	}

	public void setFlgErrorWSStockTienda(int flgErrorWSStockTienda) {
		this.flgErrorWSStockTienda = flgErrorWSStockTienda;
	}

	public String getMensajeErrorWSStockTienda() {
		return this.mensajeErrorWSStockTienda;
	}

	public void setMensajeErrorWSStockTienda(String mensajeErrorWSStockTienda) {
		this.mensajeErrorWSStockTienda = mensajeErrorWSStockTienda;
	}

	public Float getStockFinal() {
		return this.stockFinal;
	}

	public void setStockFinal(Float stockFinal) {
		this.stockFinal = stockFinal;
	}
	
	public List<Long> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<Long> referencias) {
		this.referencias = referencias;
	}

	public Double getTotalVentaTarifa() {
		return totalVentaTarifa;
	}

	public void setTotalVentaTarifa(Double totalVentaTarifa) {
		this.totalVentaTarifa = totalVentaTarifa;
	}

	public Double getTotalVentaAnticipada() {
		return totalVentaAnticipada;
	}

	public void setTotalVentaAnticipada(Double totalVentaAnticipada) {
		this.totalVentaAnticipada = totalVentaAnticipada;
	}

	public Double getTotalVentaOferta() {
		return totalVentaOferta;
	}

	public void setTotalVentaOferta(Double totalVentaOferta) {
		this.totalVentaOferta = totalVentaOferta;
	}

	public Double getTotalVentaCompetencia() {
		return totalVentaCompetencia;
	}

	public void setTotalVentaCompetencia(Double totalVentaCompetencia) {
		this.totalVentaCompetencia = totalVentaCompetencia;
	}

	public Double getTotalEntradas() {
		return totalEntradas;
	}

	public void setTotalEntradas(Double totalEntradas) {
		this.totalEntradas = totalEntradas;
	}

	public Double getTotalModifAjuste() {
		return totalModifAjuste;
	}

	public void setTotalModifAjuste(Double totalModifAjuste) {
		this.totalModifAjuste = totalModifAjuste;
	}

	public Double getTotalModifRegul() {
		return totalModifRegul;
	}

	public void setTotalModifRegul(Double totalModifRegul) {
		this.totalModifRegul = totalModifRegul;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
