package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class OfertaPVP implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArticulo;
	private Long centro; 
	private Date fecha;
	private Double tarifa;
	private Double competencia;
	private Long codOferta;
	private Long annoOferta;
	private Date fecIniOfer;
	private Date fecFinOfer;
	private Double pvpOfer;
	private String folleto;
	private Long codigo;
	private String descripcion;
	private Double costo; 
	private Date envioCosto;
	private Double pvpTarifaAnterior;
	
	//Los datos de los campos tarifa y pvpOfer con coma.
	private String tarifaStr;
	private String pvpOferStr;
	
	//Flag creado para indicar si hay que mostrar la oferta. Este flag es S cuando hoy estamos entre las fechas de inicio y fin de oferta y si la
	//oferta existe. Para pistola.
	private String flgMostrarOfertaPistola;
	
	//Flag creado para indicar si hay que mostrar la oferta. Este flag es S cuando  la oferta existe. Para PC.
	private String flgMostrarOfertaPC;

	//Flag creado para indicar si hay que mostrar el PVP. .
	private String flgMostrarPVP;
	
	

	public OfertaPVP() {
		super();
	}

	public OfertaPVP(Long codArticulo, Long centro, Date fecha) {
		super();
		this.codArticulo = codArticulo;
		this.centro = centro;
		this.fecha = fecha;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getCentro() {
		return this.centro;
	}

	public void setCentro(Long codLoc) {
		this.centro = codLoc;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}

	public Double getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Double competencia) {
		this.competencia = competencia;
	}

	public Long getCodOferta() {
		return codOferta;
	}

	public void setCodOferta(Long codOferta) {
		this.codOferta = codOferta;
	}

	public Long getAnnoOferta() {
		return annoOferta;
	}

	public void setAnnoOferta(Long annoOferta) {
		this.annoOferta = annoOferta;
	}

	public Date getFecIniOfer() {
		return fecIniOfer;
	}

	public void setFecIniOfer(Date fecIniOfer) {
		this.fecIniOfer = fecIniOfer;
	}

	public Date getFecFinOfer() {
		return fecFinOfer;
	}

	public void setFecFinOfer(Date fecFinOfer) {
		this.fecFinOfer = fecFinOfer;
	}

	public Double getPvpOfer() {
		return pvpOfer;
	}

	public void setPvpOfer(Double pvpOfer) {
		this.pvpOfer = pvpOfer;
	}

	public String getFolleto() {
		return folleto;
	}

	public void setFolleto(String folleto) {
		this.folleto = folleto;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Date getEnvioCosto() {
		return envioCosto;
	}

	public void setEnvioCosto(Date envioCosto) {
		this.envioCosto = envioCosto;
	}

	public Double getPvpTarifaAnterior() {
		return pvpTarifaAnterior;
	}

	public void setPvpTarifaAnterior(Double pvpTarifaAnterior) {
		this.pvpTarifaAnterior = pvpTarifaAnterior;
	}

	public String getFlgMostrarOfertaPistola() {
		return flgMostrarOfertaPistola;
	}

	public void setFlgMostrarOfertaPistola(String flgMostrarOfertaPistola) {
		this.flgMostrarOfertaPistola = flgMostrarOfertaPistola;
	}

	public String getFlgMostrarOfertaPC() {
		return flgMostrarOfertaPC;
	}

	public void setFlgMostrarOfertaPC(String flgMostrarOfertaPC) {
		this.flgMostrarOfertaPC = flgMostrarOfertaPC;
	}

	public String getTarifaStr() {
		return tarifaStr;
	}

	public void setTarifaStr(String tarifaStr) {
		this.tarifaStr = tarifaStr;
	}

	public String getPvpOferStr() {
		return pvpOferStr;
	}

	public void setPvpOferStr(String pvpOferStr) {
		this.pvpOferStr = pvpOferStr;
	}
	
	public String getFlgMostrarPVP() {
		return flgMostrarPVP;
	}

	public void setFlgMostrarPVP(String flgMostrarPVP) {
		this.flgMostrarPVP = flgMostrarPVP;
	}
}