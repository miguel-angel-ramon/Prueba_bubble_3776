package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class EncargosClienteLista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<EncargoCliente> datos;
    private Long estado;
	private String descEstado;
	
	public EncargosClienteLista() {
		super();
	}
	
	public EncargosClienteLista(List<EncargoCliente> datos, Long estado,
			String descEstado) {
		super();
		this.datos = datos;
		this.estado = estado;
		this.descEstado = descEstado;
	}
	
	public List<EncargoCliente> getDatos() {
		return this.datos;
	}
	
	public void setDatos(List<EncargoCliente> datos) {
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