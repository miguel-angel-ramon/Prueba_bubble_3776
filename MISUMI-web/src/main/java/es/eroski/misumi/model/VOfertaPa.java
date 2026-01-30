package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VOfertaPa implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private Long anoOferta;
	private Long numOferta;
	private Long tipoOferta;
	private Date fechaIni;
	private Date fechaFin;
	private Date fechaGen;
	private String dTipoOferta;
	private String tipoMensaje;
	private Long pvp;
	private Long area;
	private Long seccion;
	private Long categoria;
	private Long subcategoria;
	private Long segmento;
	private String tipoRegOfe;
	
	public VOfertaPa() {
	    super();
	}
	
	public VOfertaPa(Long codCentro, Long codArt, Long anoOferta,
			Long numOferta, Long tipoOferta, Date fechaIni, Date fechaFin, Date fechaGen,
			String dTipoOferta, String tipoMensaje, Long pvp, Long area, Long seccion, 
			Long categoria, Long subcategoria, Long segmento, String tipoRegOfe) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
		this.tipoOferta = tipoOferta;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.fechaGen = fechaGen;
		this.dTipoOferta = dTipoOferta;
		this.tipoMensaje = tipoMensaje;
		this.pvp = pvp;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.tipoRegOfe = tipoRegOfe;
	}
	
	public VOfertaPa(Long anoOferta, Long numOferta) {
		super();

		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
	}
	
	public Long getPvp() {
		return pvp;
	}

	public void setPvp(Long pvp) {
		this.pvp = pvp;
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

	public String getdTipoOferta() {
		return this.dTipoOferta;
	}

	public void setdTipoOferta(String dTipoOferta) {
		this.dTipoOferta = dTipoOferta;
	}

	public String getTipoMensaje() {
		return this.tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
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

	public Long getSubcategoria() {
		return this.subcategoria;
	}

	public void setSubcategoria(Long subcategoria) {
		this.subcategoria = subcategoria;
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

	@Override
	public String toString() {
		return "VOfertaPa [codCentro=" + codCentro + ", codArt=" + codArt
				+ ", anoOferta=" + anoOferta + ", numOferta=" + numOferta
				+ ", tipoOferta=" + tipoOferta + ", fechaIni=" + fechaIni
				+ ", fechaGen=" + fechaGen
				+ ", fechaFin=" + fechaFin + ", dTipoOferta=" + dTipoOferta
				+ ", tipoMensaje=" + tipoMensaje
				+ ", pvp=" + pvp + ", area=" + area + ", seccion=" + seccion 
				+ ", categoria=" + categoria + ", subcategoria=" + subcategoria
				+ ", segmento=" + segmento + ", tipoRegOfe=" + tipoRegOfe + "]";
	}

}