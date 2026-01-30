package es.eroski.misumi.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class InformeListadoPesca implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long orden;

	private String identificaSubcat;
	
	private String descSubcategoria;
	
	public InformeListadoPesca(Long orden, String identificaSubcat, String descSubcategoria) {
		super();
		this.orden = orden;
		this.identificaSubcat = identificaSubcat;
		this.descSubcategoria = descSubcategoria;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getIdentificaSubcat() {
		return identificaSubcat;
	}

	public void setIdentificaSubcat(String identificaSubcat) {
		this.identificaSubcat = identificaSubcat;
	}

	public String getDescSubcategoria() {
		return descSubcategoria;
	}

	public void setDescSubcategoria(String descSubcategoria) {
		this.descSubcategoria = descSubcategoria;
	}

	public String getDescripcion() {
		StringBuilder sbDescripcion = new StringBuilder();
		if (StringUtils.isNotBlank(identificaSubcat)){
			sbDescripcion.append(identificaSubcat);
			sbDescripcion.append(" ");
		}
		if (StringUtils.isNotBlank(descSubcategoria)){
			sbDescripcion.append(descSubcategoria);
		}
		return sbDescripcion.toString();
	}

}
