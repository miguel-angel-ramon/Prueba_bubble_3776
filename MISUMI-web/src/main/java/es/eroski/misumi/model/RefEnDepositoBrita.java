package es.eroski.misumi.model;

import java.io.Serializable;
 
public class RefEnDepositoBrita implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private Long codProvrGen;
	private Long codProvrTrabajo;
	private String nombre;
	
	public RefEnDepositoBrita() {
	    super();
	}

	public RefEnDepositoBrita(Long codCentro, Long codArt, Long codProvrGen, Long codProvrTrabajo, String nombre) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.codProvrGen = codProvrGen;
		this.codProvrTrabajo = codProvrTrabajo;
		this.nombre = nombre;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public Long getCodProvrGen() {
		return this.codProvrGen;
	}

	public void setCodProvrGen(Long codProvrGen) {
		this.codProvrGen = codProvrGen;
	}

	public Long getCodProvrTrabajo() {
		return this.codProvrTrabajo;
	}

	public void setCodProvrTrabajo(Long codProvrTrabajo) {
		this.codProvrTrabajo = codProvrTrabajo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}