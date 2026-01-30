package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VAgruComerRefMostrador implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private String nivel;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String descripcion;
	private Date fechaEspejo;
//	private String flgPropuesta;

	public VAgruComerRefMostrador() {
	    super();
	}

	public VAgruComerRefMostrador( Long codCentro, String nivel, Long grupo1, Long grupo2, Long grupo3
								 , Long grupo4, Long grupo5, String descripcion
								 , Date fechaEspejo
								 ) {
		super();
		this.codCentro = codCentro;
		this.nivel = nivel;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripcion = descripcion;
		this.fechaEspejo = fechaEspejo;
	}

	public String getNivel() {
		return this.nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public Long getGrupo1() {
		return this.grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public Long getGrupo2() {
		return this.grupo2;
	}

	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}

	public Long getGrupo3() {
		return this.grupo3;
	}

	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}

	public Long getGrupo4() {
		return this.grupo4;
	}

	public void setGrupo4(Long grupo4) {
		this.grupo4 = grupo4;
	}

	public Long getGrupo5() {
		return this.grupo5;
	}

	public void setGrupo5(Long grupo5) {
		this.grupo5 = grupo5;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaEspejo() {
		return fechaEspejo;
	}

	public void setFechaEspejo(Date fechaEspejo) {
		this.fechaEspejo = fechaEspejo;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	
}