package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class EstadoMostradorSIALista implements Serializable {

	private static final long serialVersionUID = -5987084340512157037L;

	private List<EstructuraArtMostrador> datos;
	private Long estado;
	private String descEstado;

	public List<EstructuraArtMostrador> getDatos() {
		return datos;
	}

	public void setDatos(List<EstructuraArtMostrador> datos) {
		this.datos = datos;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public String getDescEstado() {
		return descEstado;
	}

	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

}