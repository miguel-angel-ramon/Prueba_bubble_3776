package es.eroski.misumi.model;

import java.io.Serializable;

public class AgrupacionBalanza implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Long codAgrupacionBalanza;
	private String descAgrupacionBalanza;
	private Long plu;
	private Boolean guardado;

	
    public AgrupacionBalanza() {
		super();
	}

	public AgrupacionBalanza( Long codAgrupacionBalanza, String descAgrupacionBalanza, Long plu, Boolean guardado) {
		super();
		this.codAgrupacionBalanza = codAgrupacionBalanza;
		this.descAgrupacionBalanza = descAgrupacionBalanza;
		this.plu = plu;
		this.guardado = guardado;
	}

	public Long getCodAgrupacionBalanza() {
		return codAgrupacionBalanza;
	}

	public void setCodAgrupacionBalanza(Long codAgrupacionBalanza) {
		this.codAgrupacionBalanza = codAgrupacionBalanza;
	}

	public String getDescAgrupacionBalanza() {
		return descAgrupacionBalanza;
	}

	public void setDescAgrupacionBalanza(String descAgrupacionBalanza) {
		this.descAgrupacionBalanza = descAgrupacionBalanza;
	}

	public Long getPlu() {
		return plu;
	}

	public void setPlu(Long plu) {
		this.plu = plu;
	}

	public Boolean getGuardado() {
		return guardado;
	}

	public void setGuardado(Boolean guardado) {
		this.guardado = guardado;
	}

}