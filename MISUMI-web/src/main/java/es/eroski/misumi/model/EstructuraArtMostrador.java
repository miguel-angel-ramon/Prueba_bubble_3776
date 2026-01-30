package es.eroski.misumi.model;

import java.io.Serializable;

public class EstructuraArtMostrador implements Serializable {

	private static final long serialVersionUID = 7041614946888955219L;
	
	private Long codCentro;
	private Integer nivel1;
	private Integer nivel2;
	private Integer nivel3;
	private Integer nivel4;
	private Integer nivel5;
	private Integer estado;

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

	public Integer getNivel2() {
		return nivel2;
	}

	public void setNivel2(Integer nivel2) {
		this.nivel2 = nivel2;
	}

	public Integer getNivel3() {
		return nivel3;
	}

	public void setNivel3(Integer nivel3) {
		this.nivel3 = nivel3;
	}

	public Integer getNivel4() {
		return nivel4;
	}

	public void setNivel4(Integer nivel4) {
		this.nivel4 = nivel4;
	}

	public Integer getNivel5() {
		return nivel5;
	}

	public void setNivel5(Integer nivel5) {
		this.nivel5 = nivel5;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

}