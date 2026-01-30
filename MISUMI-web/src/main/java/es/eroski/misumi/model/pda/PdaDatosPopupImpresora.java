package es.eroski.misumi.model.pda;

import java.io.Serializable;

import es.eroski.misumi.model.ui.Page;

public class PdaDatosPopupImpresora implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private String procede;
	private String mensajeErrorWS;
	
	
	public PdaDatosPopupImpresora() {
	    super();
	}

	public PdaDatosPopupImpresora(Long codArt, String procede, String mensajeErrorWS) {
	    super();
	    this.codArt=codArt;
	    this.procede=procede;
	    this.mensajeErrorWS=mensajeErrorWS;
	}
	
	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	
	public String getProcede() {
		return this.procede;
	}

	public void setProcede(String procede) {
		this.procede = procede;
	}
	
	public String getMensajeErrorWS() {
		return this.mensajeErrorWS;
	}

	public void setMensajeErrorWS(String mensajeErrorWS) {
		this.mensajeErrorWS = mensajeErrorWS;
	}
	
}