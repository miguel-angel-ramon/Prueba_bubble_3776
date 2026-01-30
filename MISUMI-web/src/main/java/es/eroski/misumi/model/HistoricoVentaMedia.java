package es.eroski.misumi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import es.eroski.misumi.util.Constantes;

public class HistoricoVentaMedia implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc; 
	private Long codArticulo;
	private Date fechaVentaMedia;
	private Float unidTotVentaTarifa;
	private Float unidTotVentaCompetencia;
	private Float unidTotVentaOferta;
	private Float unidTotVentaAnticipada;
	private Long dias;
	private String tipoVenta;
	
	//Campos calculados 
	private Float tarifa;
	private Float competencia;
	private Float oferta;
	private Float anticipada;
	private Float media;
	
	public HistoricoVentaMedia() {
		super();
	}

	public HistoricoVentaMedia(Long codLoc, Long codArticulo,
			Date fechaVentaMedia, Float unidTotVentaTarifa,
			Float unidTotVentaCompetencia, Float unidTotVentaOferta,
			Float unidTotVentaAnticipada, Long dias, String tipoVenta) {
		super();
		this.codLoc = codLoc;
		this.codArticulo = codArticulo;
		this.fechaVentaMedia = fechaVentaMedia;
		this.unidTotVentaTarifa = unidTotVentaTarifa;
		this.unidTotVentaCompetencia = unidTotVentaCompetencia;
		this.unidTotVentaOferta = unidTotVentaOferta;
		this.unidTotVentaAnticipada = unidTotVentaAnticipada;
		this.dias = dias;
		this.tipoVenta = tipoVenta;
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

	public Date getFechaVentaMedia() {
		return this.fechaVentaMedia;
	}

	public void setFechaVentaMedia(Date fechaVentaMedia) {
		this.fechaVentaMedia = fechaVentaMedia;
	}

	public Float getUnidTotVentaTarifa() {
		return this.unidTotVentaTarifa;
	}

	public void setUnidTotVentaTarifa(Float unidTotVentaTarifa) {
		this.unidTotVentaTarifa = unidTotVentaTarifa;
	}

	public Float getUnidTotVentaCompetencia() {
		return this.unidTotVentaCompetencia;
	}

	public void setUnidTotVentaCompetencia(Float unidTotVentaCompetencia) {
		this.unidTotVentaCompetencia = unidTotVentaCompetencia;
	}

	public Float getUnidTotVentaOferta() {
		return this.unidTotVentaOferta;
	}

	public void setUnidTotVentaOferta(Float unidTotVentaOferta) {
		this.unidTotVentaOferta = unidTotVentaOferta;
	}

	public Float getUnidTotVentaAnticipada() {
		return this.unidTotVentaAnticipada;
	}

	public void setUnidTotVentaAnticipada(Float unidTotVentaAnticipada) {
		this.unidTotVentaAnticipada = unidTotVentaAnticipada;
	}

	public Long getDias() {
		return this.dias;
	}

	public void setDias(Long dias) {
		this.dias = dias;
	}

	public Float getTarifa() {
		return this.tarifa;
	}

	public void setTarifa(Float tarifa) {
		this.tarifa = tarifa;
	}

	public Float getCompetencia() {
		return this.competencia;
	}

	public void setCompetencia(Float competencia) {
		this.competencia = competencia;
	}

	public Float getOferta() {
		return this.oferta;
	}

	public void setOferta(Float oferta) {
		this.oferta = oferta;
	}

	public Float getAnticipada() {
		return this.anticipada;
	}

	public void setAnticipada(Float anticipada) {
		this.anticipada = anticipada;
	}

	public String getTipoVenta() {
		return this.tipoVenta;
	}

	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public Float getMedia() {
		return this.media;
	}

	public void setMedia(Float media) {
		this.media = media;
	}
	
	public void recalcularVentasMedia(){
		int decimales = Constantes.POSICIONES_DECIMALES;
		Float valorTarifa = new Float(0.0);
		Float valorCompetencia = new Float(0.0);
		Float valorOferta = new Float(0.0);
		Float valorAnticipada = new Float(0.0);
		Float valorMedia = new Float(0.0);
		
		BigDecimal bdUnidadTotVentaTarifa = new BigDecimal(this.getUnidTotVentaTarifa().doubleValue());
		BigDecimal bdUnidadTotVentaCompetencia = new BigDecimal(this.getUnidTotVentaCompetencia().doubleValue());
		BigDecimal bdUnidadTotVentaOferta = new BigDecimal(this.getUnidTotVentaOferta().doubleValue());
		BigDecimal bdUnidadTotVentaAnticipada = new BigDecimal(this.getUnidTotVentaAnticipada().doubleValue());
		BigDecimal bdDias = new BigDecimal(this.getDias().doubleValue());
		
		if (this.getDias() != 0){
			valorTarifa = new Float(bdUnidadTotVentaTarifa.divide(bdDias, decimales, BigDecimal.ROUND_HALF_UP).floatValue());
			valorCompetencia = new Float(bdUnidadTotVentaCompetencia.divide(bdDias, decimales, BigDecimal.ROUND_HALF_UP).floatValue());
			valorOferta = new Float(bdUnidadTotVentaOferta.divide(bdDias, decimales, BigDecimal.ROUND_HALF_UP).floatValue());
			valorAnticipada = new Float(bdUnidadTotVentaAnticipada.divide(bdDias, decimales, BigDecimal.ROUND_HALF_UP).floatValue());
			valorMedia = valorTarifa + valorCompetencia + valorOferta + valorAnticipada;
		}

		this.setTarifa(valorTarifa);
		this.setCompetencia(valorCompetencia);
		this.setOferta(valorOferta);
		this.setAnticipada(valorAnticipada);
		this.setMedia(valorMedia);
	}
}