package es.eroski.misumi.model;

import java.io.Serializable;
import java.math.BigDecimal;

import es.eroski.misumi.util.Constantes;

public class StockFinalMinimo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc; 
	private Long codArticulo;
	private Float stockFinMinL;
	private Float stockFinMinM;
	private Float stockFinMinX;
	private Float stockFinMinJ;
	private Float stockFinMinV;
	private Float stockFinMinS;
	private Float stockFinMinD;
	private Float ventaMedia;
	private Float diasVentaL;
	private Float diasVentaM;
	private Float diasVentaX;
	private Float diasVentaJ;
	private Float diasVentaV;
	private Float diasVentaS;
	private Float diasVentaD;
	private Float capacidad;
	
	private Long facingCentro;
	private Long facingPrevio;
	
	private Long vidaUtil;
	
	//Campos calculados
	private Float cantidadManualSIA;
	private Long facingCentroSIA;
	private Float capacidadSIA;
	

	public StockFinalMinimo() {
		super();
	}

	public StockFinalMinimo(Long codLoc, Long codArticulo, Float stockFinMinL,
			Float stockFinMinM, Float stockFinMinX, Float stockFinMinJ,
			Float stockFinMinV, Float stockFinMinS, Float stockFinMinD,
			Float capacidad, Float ventaMedia, Long facingCentro, 
			Long facingPrevio, Long vidaUtil) {
		super();
		this.codLoc = codLoc;
		this.codArticulo = codArticulo;
		this.stockFinMinL = stockFinMinL;
		this.stockFinMinM = stockFinMinM;
		this.stockFinMinX = stockFinMinX;
		this.stockFinMinJ = stockFinMinJ;
		this.stockFinMinV = stockFinMinV;
		this.stockFinMinS = stockFinMinS;
		this.stockFinMinD = stockFinMinD;
		this.capacidad = capacidad;
		this.ventaMedia = ventaMedia;
		this.facingCentro = facingCentro;
		this.facingPrevio = facingPrevio;
		this.vidaUtil = vidaUtil;
	}
	
	public StockFinalMinimo(Long codLoc, Long codArticulo, Float stockFinMinL,
			Float stockFinMinM, Float stockFinMinX, Float stockFinMinJ,
			Float stockFinMinV, Float stockFinMinS, Float stockFinMinD,
			Float capacidad, Float ventaMedia, Long facingCentro, 
			Long facingPrevio, Long vidaUtil, Integer multiplicadorFac, Long imc) {
		super();
		this.codLoc = codLoc;
		this.codArticulo = codArticulo;
		this.stockFinMinL = stockFinMinL;
		this.stockFinMinM = stockFinMinM;
		this.stockFinMinX = stockFinMinX;
		this.stockFinMinJ = stockFinMinJ;
		this.stockFinMinV = stockFinMinV;
		this.stockFinMinS = stockFinMinS;
		this.stockFinMinD = stockFinMinD;
		this.capacidad = capacidad;
		this.ventaMedia = ventaMedia;
		this.facingCentro = facingCentro;
		this.facingPrevio = facingPrevio;
		this.vidaUtil = vidaUtil;
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

	public Float getStockFinMinL() {
		return this.stockFinMinL;
	}

	public void setStockFinMinL(Float stockFinMinL) {
		this.stockFinMinL = stockFinMinL;
	}

	public Float getStockFinMinM() {
		return this.stockFinMinM;
	}

	public void setStockFinMinM(Float stockFinMinM) {
		this.stockFinMinM = stockFinMinM;
	}

	public Float getStockFinMinX() {
		return this.stockFinMinX;
	}

	public void setStockFinMinX(Float stockFinMinX) {
		this.stockFinMinX = stockFinMinX;
	}

	public Float getStockFinMinJ() {
		return this.stockFinMinJ;
	}

	public void setStockFinMinJ(Float stockFinMinJ) {
		this.stockFinMinJ = stockFinMinJ;
	}

	public Float getStockFinMinV() {
		return this.stockFinMinV;
	}

	public void setStockFinMinV(Float stockFinMinV) {
		this.stockFinMinV = stockFinMinV;
	}

	public Float getStockFinMinS() {
		return this.stockFinMinS;
	}

	public void setStockFinMinS(Float stockFinMinS) {
		this.stockFinMinS = stockFinMinS;
	}

	public Float getStockFinMinD() {
		return this.stockFinMinD;
	}

	public void setStockFinMinD(Float stockFinMinD) {
		this.stockFinMinD = stockFinMinD;
	}

	public Float getVentaMedia() {
		return this.ventaMedia;
	}

	public void setVentaMedia(Float ventaMedia) {
		this.ventaMedia = ventaMedia;
	}

	public Float getDiasVentaL() {
		return this.diasVentaL;
	}

	public void setDiasVentaL(Float diasVentaL) {
		this.diasVentaL = diasVentaL;
	}

	public Float getDiasVentaM() {
		return this.diasVentaM;
	}

	public void setDiasVentaM(Float diasVentaM) {
		this.diasVentaM = diasVentaM;
	}

	public Float getDiasVentaX() {
		return this.diasVentaX;
	}

	public void setDiasVentaX(Float diasVentaX) {
		this.diasVentaX = diasVentaX;
	}

	public Float getDiasVentaJ() {
		return this.diasVentaJ;
	}

	public void setDiasVentaJ(Float diasVentaJ) {
		this.diasVentaJ = diasVentaJ;
	}

	public Float getDiasVentaV() {
		return this.diasVentaV;
	}

	public void setDiasVentaV(Float diasVentaV) {
		this.diasVentaV = diasVentaV;
	}

	public Float getDiasVentaS() {
		return this.diasVentaS;
	}

	public void setDiasVentaS(Float diasVentaS) {
		this.diasVentaS = diasVentaS;
	}

	public Float getDiasVentaD() {
		return this.diasVentaD;
	}

	public void setDiasVentaD(Float diasVentaD) {
		this.diasVentaD = diasVentaD;
	}

	public Float getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Float capacidad) {
		this.capacidad = capacidad;
	}
	
	public Long getFacingCentro() {
		return facingCentro;
	}

	public void setFacingCentro(Long facingCentro) {
		this.facingCentro = facingCentro;
	}

	public Long getFacingPrevio() {
		return facingPrevio;
	}

	public void setFacingPrevio(Long facingPrevio) {
		this.facingPrevio = facingPrevio;
	}

	
	
	//Recalcula los d�as de venta a partir de los stocks y la venta media
	public void recalcularDiasVenta(){
		this.setDiasVentaL(recalcularDiaVenta(this.getStockFinMinL(), this.getVentaMedia()));
		this.setDiasVentaM(recalcularDiaVenta(this.getStockFinMinM(), this.getVentaMedia()));
		this.setDiasVentaX(recalcularDiaVenta(this.getStockFinMinX(), this.getVentaMedia()));
		this.setDiasVentaJ(recalcularDiaVenta(this.getStockFinMinJ(), this.getVentaMedia()));
		this.setDiasVentaV(recalcularDiaVenta(this.getStockFinMinV(), this.getVentaMedia()));
		this.setDiasVentaS(recalcularDiaVenta(this.getStockFinMinS(), this.getVentaMedia()));
		this.setDiasVentaD(recalcularDiaVenta(this.getStockFinMinD(), this.getVentaMedia()));
	}
	
	private Float recalcularDiaVenta(Float stockFinMin, Float ventaMedia){
		int decimales = Constantes.POSICIONES_DECIMALES;
		
		Float diaVenta = new Float(0.0);
		if (stockFinMin == new Float(0.0)){
			diaVenta = new Float(0.0);
		}else if (ventaMedia == 0) {
			diaVenta = stockFinMin;
		}else{
			BigDecimal bdStockFinMin = new BigDecimal(stockFinMin.doubleValue());
			BigDecimal bdVentaMedia = new BigDecimal(ventaMedia.doubleValue());
			diaVenta = new Float(bdStockFinMin.divide(bdVentaMedia, decimales, BigDecimal.ROUND_HALF_UP).floatValue());
		}
		return diaVenta;
	}

	public Float getCantidadManualSIA() {
		return this.cantidadManualSIA;
	}

	public void setCantidadManualSIA(Float cantidadManualSIA) {
		this.cantidadManualSIA = cantidadManualSIA;
	}
	
	public Long getFacingCentroSIA() {
		return facingCentroSIA;
	}

	public void setFacingCentroSIA(Long facingCentroSIA) {
		this.facingCentroSIA = facingCentroSIA;
	}

	public Long getVidaUtil() {
		return vidaUtil;
	}

	public void setVidaUtil(Long vidaUtil) {
		this.vidaUtil = vidaUtil;
	}

	public Float getCapacidadSIA() {
		return capacidadSIA;
	}

	public void setCapacidadSIA(Float capacidadSIA) {
		this.capacidadSIA = capacidadSIA;
	}
	
}