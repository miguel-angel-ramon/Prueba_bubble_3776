package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HistoricoVentaUltimoMes implements Serializable{

	private static final long serialVersionUID = 1L;

	private String fechaDia;
	private String esFestivo;
	private Long codLoc; 
	private Long codArticulo;
	private Date fechaVenta;
	private Float unidVentaTarifa;
	private Float unidVentaCompetencia;
	private Float unidVentaOferta;
	private Float unidVentaAnticipada;
	private String tipoVenta;
	private String esOferta;
	private List<Long> referencias;
	

	//Campos calculados
	private Float unidades;
	private String anticipada;
	private String fechaVentaDDMMYYYY;
	private Float totalVentas;
	

	
	public HistoricoVentaUltimoMes() {
		super();
	}
	
	public HistoricoVentaUltimoMes(Long codLoc, Long codArticulo,
			Date fechaVenta, Float unidVentaTarifa, Float unidVentaCompetencia,
			Float unidVentaOferta, Float unidVentaAnticipada, String tipoVenta, String fechaDia, String esFestivo) {
		super();
		this.fechaDia = fechaDia;
		this.esFestivo = esFestivo;
		this.codLoc = codLoc;
		this.codArticulo = codArticulo;
		this.fechaVenta = fechaVenta;
		this.unidVentaTarifa = unidVentaTarifa;
		this.unidVentaCompetencia = unidVentaCompetencia;
		this.unidVentaOferta = unidVentaOferta;
		this.unidVentaAnticipada = unidVentaAnticipada;
		this.tipoVenta = tipoVenta;
		
	}
	
	public HistoricoVentaUltimoMes(Long codLoc, Long codArticulo,
			Float unidades, Date fechaVenta, String anticipada, String fechaVentaDDMMYYYY, String fechaDia, String esFestivo, String esOferta) {
		super();
		this.fechaDia = fechaDia;
		this.esFestivo = esFestivo;
		this.codLoc = codLoc;
		this.codArticulo = codArticulo;
		this.unidades = unidades;
		this.fechaVenta = fechaVenta;
		this.anticipada = anticipada;
		this.fechaVentaDDMMYYYY = fechaVentaDDMMYYYY;
		this.esOferta = esOferta;
	}
	public HistoricoVentaUltimoMes(Float unidVentaTarifa, Float unidVentaCompetencia,
			Float unidVentaOferta, Float unidVentaAnticipada) {
		super();

		this.unidVentaTarifa = unidVentaTarifa;
		this.unidVentaCompetencia = unidVentaCompetencia;
		this.unidVentaOferta = unidVentaOferta;
		this.unidVentaAnticipada = unidVentaAnticipada;
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
	
	public Date getFechaVenta() {
		return this.fechaVenta;
	}
	
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	
	public Float getUnidVentaTarifa() {
		return this.unidVentaTarifa;
	}
	
	public void setUnidVentaTarifa(Float unidVentaTarifa) {
		this.unidVentaTarifa = unidVentaTarifa;
	}
	
	public Float getUnidVentaCompetencia() {
		return this.unidVentaCompetencia;
	}
	
	public void setUnidVentaCompetencia(Float unidVentaCompetencia) {
		this.unidVentaCompetencia = unidVentaCompetencia;
	}
	
	public Float getUnidVentaOferta() {
		return this.unidVentaOferta;
	}
	
	public void setUnidVentaOferta(Float unidVentaOferta) {
		this.unidVentaOferta = unidVentaOferta;
	}
	
	public Float getUnidVentaAnticipada() {
		return this.unidVentaAnticipada;
	}
	
	public void setUnidVentaAnticipada(Float unidVentaAnticipada) {
		this.unidVentaAnticipada = unidVentaAnticipada;
	}
	
	public String getTipoVenta() {
		return this.tipoVenta;
	}

	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public Float getUnidades() {
		return this.unidades;
	}

	public void setUnidades(Float unidades) {
		this.unidades = unidades;
	}

	public String getAnticipada() {
		return this.anticipada;
	}

	public void setAnticipada(String anticipada) {
		this.anticipada = anticipada;
	}

	public String getFechaVentaDDMMYYYY() {
		return this.fechaVentaDDMMYYYY;
	}

	public void setFechaVentaDDMMYYYY(String fechaVentaDDMMYYYY) {
		this.fechaVentaDDMMYYYY = fechaVentaDDMMYYYY;
	}

	public Float getTotalVentas() {
		return this.totalVentas;
	}

	public void setTotalVentas(Float totalVentas) {
		this.totalVentas = totalVentas;
	}
	
	public void recalcularTotalVentas(){
		
		Float totalVentas = this.getUnidVentaTarifa() + 
							this.getUnidVentaCompetencia() + 
							this.getUnidVentaOferta() + 
							this.getUnidVentaAnticipada();
		this.setTotalVentas(totalVentas);
	}

	public String getFechaDia() {
		return this.fechaDia;
	}

	public void setFechaDia(String fechaDia) {
		this.fechaDia = fechaDia;
	}

	public String getEsFestivo() {
		return this.esFestivo;
	}

	public void setEsFestivo(String esFestivo) {
		this.esFestivo = esFestivo;
	}

	public String getEsOferta() {
		return esOferta;
	}

	public void setEsOferta(String esOferta) {
		this.esOferta = esOferta;
	}	
	
	public List<Long> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<Long> referencias) {
		this.referencias = referencias;
	}

	
}