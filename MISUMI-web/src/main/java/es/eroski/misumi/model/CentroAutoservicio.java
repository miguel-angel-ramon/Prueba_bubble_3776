package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class CentroAutoservicio implements Serializable{

	private static final long serialVersionUID = 1L;

	private Date creationDate;
	private Long codCentro;
	private Float porcentajeCapacidad;
	private Long tipo;
	private String descripcion;

	public CentroAutoservicio() {
	    super();
	}

	public CentroAutoservicio(Date creationDate, Long codCentro,
			Float porcentajeCapacidad, Long tipo, String descripcion) {
		super();
		this.creationDate = creationDate;
		this.codCentro = codCentro;
		this.porcentajeCapacidad = porcentajeCapacidad;
		this.tipo = tipo;
		this.descripcion = descripcion;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Float getPorcentajeCapacidad() {
		return this.porcentajeCapacidad;
	}

	public void setPorcentajeCapacidad(Float porcentajeCapacidad) {
		this.porcentajeCapacidad = porcentajeCapacidad;
	}

	public Long getTipo() {
		return this.tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}