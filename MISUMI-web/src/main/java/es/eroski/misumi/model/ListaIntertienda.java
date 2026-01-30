package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class ListaIntertienda implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Intertienda> lista;
	private Long numeroRegistros;
	
	public List<Intertienda> getLista() {
		return lista;
	}
	public void setLista(List<Intertienda> lista) {
		this.lista = lista;
	}
	public Long getNumeroRegistros() {
		return numeroRegistros;
	}
	public void setNumeroRegistros(Long numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}
	
}