package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class MapaAprovFestivo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long codArt;
	private String estado;
	private Date fechaCambio;
	private Integer plazo;

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArt() {
		return codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Integer getPlazo() {
		return plazo;
	}

	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	public int hashCode() {

		return new HashCodeBuilder(17, 31).append(this.fechaCambio).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MapaAprovFestivo)) {
			return false;
		}
		MapaAprovFestivo dp = (MapaAprovFestivo) obj;
		return new EqualsBuilder().append(this.fechaCambio, dp.fechaCambio).isEquals();
	}

}
