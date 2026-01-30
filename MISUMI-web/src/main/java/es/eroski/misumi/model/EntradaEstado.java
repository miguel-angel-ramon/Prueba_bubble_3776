package es.eroski.misumi.model;

import java.util.List;

public class EntradaEstado {
	private Long estado;
	private Long numeroEntradas;
	private List<Entrada> lstEntrada;
	
	public EntradaEstado() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntradaEstado(Long estado, Long numeroEntradas, List<Entrada> lstEntrada) {
		super();
		this.estado = estado;
		this.numeroEntradas = numeroEntradas;
		this.lstEntrada = lstEntrada;
	}
	
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	public Long getNumeroEntradas() {
		return numeroEntradas;
	}
	public void setNumeroEntradas(Long numeroEntradas) {
		this.numeroEntradas = numeroEntradas;
	}
	public List<Entrada> getLstEntrada() {
		return lstEntrada;
	}
	public void setLstEntrada(List<Entrada> lstEntrada) {
		this.lstEntrada = lstEntrada;
	}
}
