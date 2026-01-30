package es.eroski.misumi.model.pda;

import java.io.Serializable;

import es.eroski.misumi.model.ui.Page;

public class PdaDatosPopupMotivosTengoMP implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private String descArt;
	private String procede;
	private String flgMotivosTengoMP;
	private String flgTipoListadoMotivos;
	private Page<PdaMotivoTengoMP> pagMotivosTengoMP;
	
	public PdaDatosPopupMotivosTengoMP() {
	    super();
	}

	public PdaDatosPopupMotivosTengoMP(Long codArt, String descArt, String procede, String flgMotivosTengoMP,
			String flgTipoListadoMotivos, Page<PdaMotivoTengoMP> pagMotivosTengoMP) {
	    super();
	    this.codArt=codArt;
	    this.descArt=descArt;
	    this.procede=procede;
	    this.flgMotivosTengoMP=flgMotivosTengoMP;
	    this.flgTipoListadoMotivos = flgTipoListadoMotivos;
	    this.pagMotivosTengoMP=pagMotivosTengoMP;
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
	
	public String getFlgMotivosTengoMP() {
		return this.flgMotivosTengoMP;
	}

	public void setFlgMotivosTengoMP(String flgMotivosTengoMP) {
		this.flgMotivosTengoMP = flgMotivosTengoMP;
	}

	public String getFlgTipoListadoMotivos() {
		return this.flgTipoListadoMotivos;
	}

	public void setFlgTipoListadoMotivos(String flgTipoListadoMotivos) {
		this.flgTipoListadoMotivos = flgTipoListadoMotivos;
	}

	public Page<PdaMotivoTengoMP> getPagMotivosTengoMP() {
		return this.pagMotivosTengoMP;
	}

	public void setPagMotivosTengoMP(Page<PdaMotivoTengoMP> pagMotivosTengoMP) {
		this.pagMotivosTengoMP = pagMotivosTengoMP;
	}
}