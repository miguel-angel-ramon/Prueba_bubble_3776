package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class PedidoAdSP implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<PedidosAdCentral> datos;
	private Long estado;
	private String descEstado;
	
	public List<PedidosAdCentral> getDatos() {
		return datos;
	}
	public void setDatos(List<PedidosAdCentral> datos) {
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
