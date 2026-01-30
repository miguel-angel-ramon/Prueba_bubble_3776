package es.eroski.misumi.model;

import java.util.List;

public class FechaProximaEntregaRef {
	private List<FechaProximaEntrega> listaFechaProximasEntregas;
	private Long estado;
	private String descEstado;
	
	public FechaProximaEntregaRef(List<FechaProximaEntrega> listaFechaProximasEntregas, Long estado,
			String descEstado) {
		super();
		this.listaFechaProximasEntregas = listaFechaProximasEntregas;
		this.estado = estado;
		this.descEstado = descEstado;
	}
	
	public List<FechaProximaEntrega> getListaFechaProximasEntregas() {
		return listaFechaProximasEntregas;
	}
	public void setListaFechaProximasEntregas(List<FechaProximaEntrega> listaFechaProximasEntregas) {
		this.listaFechaProximasEntregas = listaFechaProximasEntregas;
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
