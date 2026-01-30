package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VOfertaPaAyuda implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private Long anoOferta;
	private Long numOferta;
	private Long tipoOferta;
	private Date fechaIni;
	private Date fechaFin;
	private Date fechaGen;
	private String doftip;
	private String tipoMensaje;
	private Double precio;
	private Long area;
	private Long seccion;
	private Long categoria;
	private Long segmento;
	private String tipoRegOfe;
	private Long unidCobro;
	private Long unidVenta;
	
	public VOfertaPaAyuda() {
		super();
	}

	public VOfertaPaAyuda(Long codCentro, Long codArt, Long anoOferta,
			Long numOferta, Long tipoOferta, Date fechaIni, Date fechaFin,
			Date fechaGen, String doftip, String tipoMensaje, Double precio,
			Long area, Long seccion, Long categoria, Long segmento,
			String tipoRegOfe, Long unidCobro, Long unidVenta) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
		this.tipoOferta = tipoOferta;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.fechaGen = fechaGen;
		this.doftip = doftip;
		this.tipoMensaje = tipoMensaje;
		this.precio = precio;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.segmento = segmento;
		this.tipoRegOfe = tipoRegOfe;
		this.unidCobro = unidCobro;
		this.unidVenta = unidVenta;
	}

	public VOfertaPaAyuda(Long anoOferta, Long numOferta) {
		super();
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
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

	public Long getAnoOferta() {
		return this.anoOferta;
	}

	public void setAnoOferta(Long anoOferta) {
		this.anoOferta = anoOferta;
	}

	public Long getNumOferta() {
		return this.numOferta;
	}

	public void setNumOferta(Long numOferta) {
		this.numOferta = numOferta;
	}

	public Long getTipoOferta() {
		return this.tipoOferta;
	}

	public void setTipoOferta(Long tipoOferta) {
		this.tipoOferta = tipoOferta;
	}

	public Date getFechaIni() {
		return this.fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public String getDoftip() {
		return this.doftip;
	}

	public void setDoftip(String doftip) {
		this.doftip = doftip;
	}

	public String getTipoMensaje() {
		return this.tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public Double getPrecio() {
		return this.precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Long getArea() {
		return this.area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getSeccion() {
		return this.seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public Long getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public Long getSegmento() {
		return this.segmento;
	}

	public void setSegmento(Long segmento) {
		this.segmento = segmento;
	}

	public String getTipoRegOfe() {
		return this.tipoRegOfe;
	}

	public void setTipoRegOfe(String tipoRegOfe) {
		this.tipoRegOfe = tipoRegOfe;
	}
	
	public Long getUnidCobro() {
		return this.unidCobro;
	}

	public void setUnidCobro(Long unidCobro) {
		this.unidCobro = unidCobro;
	}

	public Long getUnidVenta() {
		return this.unidVenta;
	}

	public void setUnidVenta(Long unidVenta) {
		this.unidVenta = unidVenta;
	}
}