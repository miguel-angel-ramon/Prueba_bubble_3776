package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VFacingX implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long codN1;
	private Long codN2;
	private Long codN3;
	private Long codCentro;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private Long codArticulo;
	private Long facingX;
	private Date fecInicio;

	public VFacingX() {
		super();
	}

	public VFacingX(Long codN1, Long codN2, Long codN3, Long codCentro, Long grupo1,
			Long grupo2, Long grupo3, Long grupo4, Long grupo5, Long codArticulo, Long facingX, Date fecInicio) {
		super();
		this.codN1 = codN1;
		this.codN2 = codN2;
		this.codN3 = codN3;
		this.codCentro = codCentro;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.codArticulo = codArticulo;
		this.facingX = facingX;
		this.fecInicio = fecInicio;
	}

	public Long getCodN1() {
		return this.codN1;
	}

	public void setCodN1(Long codN1) {
		this.codN1 = codN1;
	}

	public Long getCodN2() {
		return this.codN2;
	}

	public void setCodN2(Long codN2) {
		this.codN2 = codN2;
	}

	public Long getCodN3() {
		return this.codN3;
	}

	public void setCodN3(Long codN3) {
		this.codN3 = codN3;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
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

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getFacingX() {
		return this.facingX;
	}

	public void setFacingX(Long facingX) {
		this.facingX = facingX;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}
}