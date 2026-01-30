package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AvisosSiec implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codAviso;
	private Date fechaIni;
	private String horaIni;
	private Date fechaFin;
	private String horaFin;
	private String mensajePc;
	private String mensajePda;
	private String flgEroski;
	private String flgCpb;
	private String flgVegalsa;
	private String flgMercat;
	private String flgHiper;
	private String flgSuper;
	private String flgFranquicia;
	
	private List<String> lstBorrar;

	/**
	 * Constructor
	 */
    public AvisosSiec() {
		super();
	}
    
    public AvisosSiec(String codAviso, Date fechaIni, String horaIni, Date fechaFin, String horaFin, String mensajePc, String mensajePda
    		, String flgEroski, String flgCpb, String flgVegalsa, String flgMercat, String flgHiper, String flgSuper,String flgFranquicia,
			List<String> lstBorrar) {
		super();
		this.codAviso = codAviso;
		this.fechaIni = fechaIni;
		this.horaIni = horaIni;
		this.fechaFin = fechaFin;
		this.horaFin = horaFin;
		this.mensajePc = mensajePc;
		this.mensajePda = mensajePda;
		this.flgEroski = flgEroski;
		this.flgCpb = flgCpb;
		this.flgVegalsa = flgVegalsa;
		this.flgMercat = flgMercat;
		this.flgHiper = flgHiper;
		this.flgSuper = flgSuper;
		this.flgFranquicia = flgFranquicia;
		this.lstBorrar = lstBorrar;
	}

	public String getCodAviso() {
		return codAviso;
	}

	public void setCodAviso(String codAviso) {
		this.codAviso = codAviso;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getHoraIni() {
		return horaIni;
	}

	public void setHoraIni(String horaIni) {
		this.horaIni = horaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getMensajePc() {
		return mensajePc;
	}

	public void setMensajePc(String mensajePc) {
		this.mensajePc = mensajePc;
	}

	public String getMensajePda() {
		return mensajePda;
	}

	public void setMensajePda(String mensajePda) {
		this.mensajePda = mensajePda;
	}

	public String getFlgEroski() {
		return flgEroski;
	}

	public void setFlgEroski(String flgEroski) {
		this.flgEroski = flgEroski;
	}

	public String getFlgCpb() {
		return flgCpb;
	}

	public void setFlgCpb(String flgCpb) {
		this.flgCpb = flgCpb;
	}

	public String getFlgVegalsa() {
		return flgVegalsa;
	}

	public void setFlgVegalsa(String flgVegalsa) {
		this.flgVegalsa = flgVegalsa;
	}

	public String getFlgMercat() {
		return flgMercat;
	}

	public void setFlgMercat(String flgMercat) {
		this.flgMercat = flgMercat;
	}

	public String getFlgHiper() {
		return flgHiper;
	}

	public void setFlgHiper(String flgHiper) {
		this.flgHiper = flgHiper;
	}

	public String getFlgSuper() {
		return flgSuper;
	}

	public void setFlgSuper(String flgSuper) {
		this.flgSuper = flgSuper;
	}

	public String getFlgFranquicia() {
		return flgFranquicia;
	}

	public void setFlgFranquicia(String flgFranquicia) {
		this.flgFranquicia = flgFranquicia;
	}

	public List<String> getLstBorrar() {
		return lstBorrar;
	}

	public void setLstBorrar(List<String> lstBorrar) {
		this.lstBorrar = lstBorrar;
	}

}