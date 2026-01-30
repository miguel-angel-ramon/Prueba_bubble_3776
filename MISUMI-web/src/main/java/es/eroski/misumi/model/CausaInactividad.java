package es.eroski.misumi.model;

import java.io.Serializable;

public class CausaInactividad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer codCausa;
	private String descCausa;
	
	public Integer getCodCausa() {
		return codCausa;
	}
	public void setCodCausa(Integer codCausa) {
		this.codCausa = codCausa;
	}
	public String getDescCausa() {
		return descCausa;
	}
	public void setDescCausa(String descCausa) {
		this.descCausa = descCausa;
	}
	
	

}
