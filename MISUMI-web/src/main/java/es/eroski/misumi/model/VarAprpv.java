package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VarAprpv implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc; 
	private Long codArticulo;
	private String marcaReapro;
	private Date fechaInicioReapro;
	private Date fechaFinReapro;
	private String gamaDiscontinua;
	private Long gamaDiscLunes;
	private Long gamaDiscMartes;
	private Long gamaDiscMiercoles;
	private Long gamaDiscJueves;
	private Long gamaDiscViernes;
	private Long gamaDiscSabado;
	private Long gamaDiscDomingo;
	private Float ufp;
	
	public VarAprpv() {
	    super();
	}

	public VarAprpv(Long codLoc, Long codArticulo, String marcaReapro,
			Date fechaInicioReapro, Date fechaFinReapro,
			String gamaDiscontinua, Long gamaDiscLunes, Long gamaDiscMartes,
			Long gamaDiscMiercoles, Long gamaDiscJueves, Long gamaDiscViernes,
			Long gamaDiscSabado, Long gamaDiscDomingo, Float ufp) {
		super();
		this.codLoc = codLoc;
		this.codArticulo = codArticulo;
		this.marcaReapro = marcaReapro;
		this.fechaInicioReapro = fechaInicioReapro;
		this.fechaFinReapro = fechaFinReapro;
		this.gamaDiscontinua = gamaDiscontinua;
		this.gamaDiscLunes = gamaDiscLunes;
		this.gamaDiscMartes = gamaDiscMartes;
		this.gamaDiscMiercoles = gamaDiscMiercoles;
		this.gamaDiscJueves = gamaDiscJueves;
		this.gamaDiscViernes = gamaDiscViernes;
		this.gamaDiscSabado = gamaDiscSabado;
		this.gamaDiscDomingo = gamaDiscDomingo;
		this.ufp = ufp;
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

	public String getMarcaReapro() {
		return this.marcaReapro;
	}

	public void setMarcaReapro(String marcaReapro) {
		this.marcaReapro = marcaReapro;
	}

	public Date getFechaInicioReapro() {
		return this.fechaInicioReapro;
	}

	public void setFechaInicioReapro(Date fechaInicioReapro) {
		this.fechaInicioReapro = fechaInicioReapro;
	}

	public Date getFechaFinReapro() {
		return this.fechaFinReapro;
	}

	public void setFechaFinReapro(Date fechaFinReapro) {
		this.fechaFinReapro = fechaFinReapro;
	}

	public String getGamaDiscontinua() {
		return this.gamaDiscontinua;
	}

	public void setGamaDiscontinua(String gamaDiscontinua) {
		this.gamaDiscontinua = gamaDiscontinua;
	}

	public Long getGamaDiscLunes() {
		return this.gamaDiscLunes;
	}

	public void setGamaDiscLunes(Long gamaDiscLunes) {
		this.gamaDiscLunes = gamaDiscLunes;
	}

	public Long getGamaDiscMartes() {
		return this.gamaDiscMartes;
	}

	public void setGamaDiscMartes(Long gamaDiscMartes) {
		this.gamaDiscMartes = gamaDiscMartes;
	}

	public Long getGamaDiscMiercoles() {
		return this.gamaDiscMiercoles;
	}

	public void setGamaDiscMiercoles(Long gamaDiscMiercoles) {
		this.gamaDiscMiercoles = gamaDiscMiercoles;
	}

	public Long getGamaDiscJueves() {
		return this.gamaDiscJueves;
	}

	public void setGamaDiscJueves(Long gamaDiscJueves) {
		this.gamaDiscJueves = gamaDiscJueves;
	}

	public Long getGamaDiscViernes() {
		return this.gamaDiscViernes;
	}

	public void setGamaDiscViernes(Long gamaDiscViernes) {
		this.gamaDiscViernes = gamaDiscViernes;
	}

	public Long getGamaDiscSabado() {
		return this.gamaDiscSabado;
	}

	public void setGamaDiscSabado(Long gamaDiscSabado) {
		this.gamaDiscSabado = gamaDiscSabado;
	}

	public Long getGamaDiscDomingo() {
		return this.gamaDiscDomingo;
	}

	public void setGamaDiscDomingo(Long gamaDiscDomingo) {
		this.gamaDiscDomingo = gamaDiscDomingo;
	}

	public Float getUfp() {
		return this.ufp;
	}

	public void setUfp(Float ufp) {
		this.ufp = ufp;
	}
}