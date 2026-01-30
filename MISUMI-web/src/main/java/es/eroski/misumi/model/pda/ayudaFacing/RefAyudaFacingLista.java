package es.eroski.misumi.model.pda.ayudaFacing;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.DetalladoMostradorSIA;

public class RefAyudaFacingLista implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<RefAyudaFacing> datos;
    private Long estado;
	private String descEstado;
		
	public RefAyudaFacingLista() {
		super();
	}

	public List<RefAyudaFacing> getDatos() {
		return datos;
	}

	public void setDatos(List<RefAyudaFacing> datos) {
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
