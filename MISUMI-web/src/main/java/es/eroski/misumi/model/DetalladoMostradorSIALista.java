package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class DetalladoMostradorSIALista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<DetalladoMostradorSIA> datos;
    private Long estado;
	private String descEstado;
	
	
	public DetalladoMostradorSIALista() {
		super();
	}

	public DetalladoMostradorSIALista(List<DetalladoMostradorSIA> datos, Long estado, String descEstado) {
		super();
		this.datos = datos;
		this.estado = estado;
		this.descEstado = descEstado;
	}
	
	public List<DetalladoMostradorSIA> getDatos() {
		return this.datos;
	}
	
	public void setDatos(List<DetalladoMostradorSIA> datos) {
		this.datos = datos;
	}
	
	public Long getEstado() {
		return this.estado;
	}
	
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	public String getDescEstado() {
		return this.descEstado;
	}
	
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

}