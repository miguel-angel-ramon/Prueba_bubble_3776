package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VAgruComerParamSfmcap implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private String nivel;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String descripcion;
	private Date fechaGen;
	private String flgStockFinal;
	private String flgCapacidad;
	private String flgFacing;
	private String flgFacingCapacidad;

	public VAgruComerParamSfmcap() {
	    super();
	}

	public VAgruComerParamSfmcap(Long codCentro, String nivel, Long grupo1,
			Long grupo2, Long grupo3, Long grupo4, Long grupo5,
			String descripcion, Date fechaGen, String flgStockFinal, String flgCapacidad, String flgFacing, String flgFacingCapacidad) {
		super();
		this.codCentro = codCentro;
		this.nivel = nivel;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripcion = descripcion;
		this.fechaGen = fechaGen;
		this.flgStockFinal = flgStockFinal;
		this.flgCapacidad = flgCapacidad;
		this.flgFacing = flgFacing;
		this.flgFacingCapacidad = flgFacingCapacidad;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
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

	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public String getFlgStockFinal() {
		return this.flgStockFinal;
	}

	public void setFlgStockFinal(String flgStockFinal) {
		this.flgStockFinal = flgStockFinal;
	}

	public String getFlgCapacidad() {
		return this.flgCapacidad;
	}

	public void setFlgCapacidad(String flgCapacidad) {
		this.flgCapacidad = flgCapacidad;
	}

	public String getFlgFacing() {
		return this.flgFacing;
	}

	public void setFlgFacing(String flgFacing) {
		this.flgFacing = flgFacing;
	}
	
	public String getFlgFacingCapacidad() {
		return this.flgFacingCapacidad;
	}

	public void setFlgFacingCapacidad(String flgFacingCapacidad) {
		this.flgFacingCapacidad = flgFacingCapacidad;
	}
	
	
	
}