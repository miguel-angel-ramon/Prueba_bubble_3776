package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class CampanasOfertasMisumi implements Serializable{

	private static final long serialVersionUID = 1L;

	private String identificador;
	private Long codLoc;
	private Long codArt;
	private Long codArtRl;
	private String tipo;
	private Long anoOferta;
	private Long numOferta;
	private Date fInicio;
	private Date fFin;
	private Float cantPrevista;
	private Float ventaMedia;
	private Float ventaTotal;
	private Float cantImp;
	private Date fechaImp;
	
	public CampanasOfertasMisumi() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CampanasOfertasMisumi(String identificador, Long codLoc,
			Long codArt, Long codArtRl, String tipo, Long anoOferta,
			Long numOferta, Date fInicio, Date fFin, Float cantPrevista,
			Float ventaMedia, Float ventaTotal, Float cantImp, Date fechaImp) {
		super();
		this.identificador = identificador;
		this.codLoc = codLoc;
		this.codArt = codArt;
		this.codArtRl = codArtRl;
		this.tipo = tipo;
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
		this.fInicio = fInicio;
		this.fFin = fFin;
		this.cantPrevista = cantPrevista;
		this.ventaMedia = ventaMedia;
		this.ventaTotal = ventaTotal;
		this.cantImp = cantImp;
		this.fechaImp = fechaImp;
	}
	public String getIdentificador() {
		return this.identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Long getCodLoc() {
		return this.codLoc;
	}
	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}
	public Long getCodArt() {
		return this.codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public Long getCodArtRl() {
		return this.codArtRl;
	}
	public void setCodArtRl(Long codArtRl) {
		this.codArtRl = codArtRl;
	}
	public String getTipo() {
		return this.tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	public Date getfInicio() {
		return this.fInicio;
	}
	public void setfInicio(Date fInicio) {
		this.fInicio = fInicio;
	}
	public Date getfFin() {
		return this.fFin;
	}
	public void setfFin(Date fFin) {
		this.fFin = fFin;
	}
	public Float getCantPrevista() {
		return this.cantPrevista;
	}
	public void setCantPrevista(Float cantPrevista) {
		this.cantPrevista = cantPrevista;
	}
	public Float getVentaMedia() {
		return this.ventaMedia;
	}
	public void setVentaMedia(Float ventaMedia) {
		this.ventaMedia = ventaMedia;
	}
	public Float getVentaTotal() {
		return this.ventaTotal;
	}
	public void setVentaTotal(Float ventaTotal) {
		this.ventaTotal = ventaTotal;
	}
	public Float getCantImp() {
		return this.cantImp;
	}
	public void setCantImp(Float cantImp) {
		this.cantImp = cantImp;
	}
	public Date getFechaImp() {
		return this.fechaImp;
	}
	public void setFechaImp(Date fechaImp) {
		this.fechaImp = fechaImp;
	}
}