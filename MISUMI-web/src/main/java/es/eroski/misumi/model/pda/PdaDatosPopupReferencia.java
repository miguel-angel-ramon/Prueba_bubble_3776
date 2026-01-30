package es.eroski.misumi.model.pda;

import java.io.Serializable;
import java.util.Date;

import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.util.Utilidades;

public class PdaDatosPopupReferencia implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private String descArt;
	private String procede;
	private String flgNoActivo;
	private Page<PdaMotivo> pagNoActivo;
	private String flgMMC;
	private Page<PdaMotivo> pagMMC;
	
	private boolean tratamientoVegalsa;
	private String soloReparto;
	private Date fechaMmc;
	private String mapaReferencia;
	
	public PdaDatosPopupReferencia() {
	    super();
	}

	public PdaDatosPopupReferencia(Long codArt, String descArt, String procede, String flgNoActivo,
			Page<PdaMotivo> pagNoActivo, String flgMMC, Page<PdaMotivo> pagMMC) {
	    super();
	    this.codArt=codArt;
	    this.descArt=descArt;
	    this.procede=procede;
	    this.flgNoActivo=flgNoActivo;
	    this.pagNoActivo=pagNoActivo;
	    this.flgMMC=flgMMC;
	    this.pagMMC=pagMMC;
	}
	
	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	
	public String getDescArt() {
		return this.descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}
	
	public String getProcede() {
		return this.procede;
	}

	public void setProcede(String procede) {
		this.procede = procede;
	}
	
	public Page<PdaMotivo> getPagNoActivo() {
		return this.pagNoActivo;
	}

	public void setPagNoActivo(Page<PdaMotivo> pagNoActivo) {
		this.pagNoActivo = pagNoActivo;
	}
	
	public Page<PdaMotivo> getPagMMC() {
		return this.pagMMC;
	}

	public void setPagMMC(Page<PdaMotivo> pagMMC) {
		this.pagMMC = pagMMC;
	}
	
	public String getFlgNoActivo() {
		return this.flgNoActivo;
	}

	public void setFlgNoActivo(String flgNoActivo) {
		this.flgNoActivo = flgNoActivo;
	}
	
	public String getFlgMMC() {
		return this.flgMMC;
	}

	public void setFlgMMC(String flgMMC) {
		this.flgMMC = flgMMC;
	}

	public boolean isTratamientoVegalsa() {
		return tratamientoVegalsa;
	}

	public void setTratamientoVegalsa(boolean tratamientoVegalsa) {
		this.tratamientoVegalsa = tratamientoVegalsa;
	}

	public String getSoloReparto() {
		return soloReparto;
	}

	public void setSoloReparto(String soloReparto) {
		this.soloReparto = soloReparto;
	}

	public Date getFechaMmc() {
		return fechaMmc;
	}

	public void setFechaMmc(Date fechaMmc) {
		this.fechaMmc = fechaMmc;
	}
	
	public String getFechaMmcStr() {
		if (null != fechaMmc) {
			return Utilidades.formatearFecha_ddMMyyyyBarra(fechaMmc);
		} else {
			return "";
		}
	}

	public String getMapaReferencia() {
		return mapaReferencia;
	}

	public void setMapaReferencia(String mapaReferencia) {
		this.mapaReferencia = mapaReferencia;
	}
	
	
}