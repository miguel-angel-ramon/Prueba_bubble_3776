package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaDatosPopupImplantacion implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private String procede;
	private String implantacion;
	private String tituloImplantacion;
	private String flgColorImplantacion;
	private String estructura;
	
	public PdaDatosPopupImplantacion() {
	    super();
	}

	public PdaDatosPopupImplantacion(Long codArt, String procede, String implantacion, String flgColorImplantacion) {
	    super();
	    this.codArt=codArt;
	    this.procede=procede;
	    this.implantacion=implantacion;
	    this.flgColorImplantacion=flgColorImplantacion;
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
	
	public String getImplantacion() {
		return this.implantacion;
	}

	public void setImplantacion(String implantacion) {
		this.implantacion = implantacion;
	}

	public String getTituloImplantacion() {
		return tituloImplantacion;
	}

	public void setTituloImplantacion(String tituloImplantacion) {
		this.tituloImplantacion = tituloImplantacion;
	}

	public String getFlgColorImplantacion() {
		return flgColorImplantacion;
	}

	public void setFlgColorImplantacion(String flgColorImplantacion) {
		this.flgColorImplantacion = flgColorImplantacion;
	}

	public String getEstructura() {
		return estructura;
	}

	public void setEstructura(String estructura) {
		this.estructura = estructura;
	}

}