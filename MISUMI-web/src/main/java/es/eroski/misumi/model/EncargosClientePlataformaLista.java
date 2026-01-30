package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class EncargosClientePlataformaLista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<EncargosClientePlataforma> datos;
    private String notas;
    private Boolean generico;
    private Long estado;
	private String descEstado;
	
	public EncargosClientePlataformaLista() {
		super();
	}
	
	public EncargosClientePlataformaLista(List<EncargosClientePlataforma> datos, Long estado,
			String descEstado) {
		super();
		this.datos = datos;
		this.estado = estado;
		this.descEstado = descEstado;
	}
	
	public List<EncargosClientePlataforma> getDatos() {
		return this.datos;
	}
	
	public void setDatos(List<EncargosClientePlataforma> datos) {
		this.datos = datos;
	}
	
	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public Boolean getGenerico() {
		return generico;
	}

	public void setGenerico(Boolean generico) {
		this.generico = generico;
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