package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

public class ECAreaFacingCero implements Serializable {

	private static final long serialVersionUID = 1L;

	// Centro
	private Long codCentro;
	// Area
	private Integer nivel1;

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Integer getNivel1() {
		return nivel1;
	}

	public void setNivel1(Integer nivel1) {
		this.nivel1 = nivel1;
	}

}