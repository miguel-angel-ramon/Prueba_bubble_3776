package es.eroski.misumi.model;

import es.eroski.misumi.model.ui.Page;

public class EntradaPagina {
	private Page<TEntradaLinea> datos;
	private Entrada entradaCab;
	
	public EntradaPagina() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntradaPagina(Page<TEntradaLinea> datos, Entrada entradaCab) {
		super();
		this.datos = datos;
		this.entradaCab = entradaCab;
	}
	
	public Page<TEntradaLinea> getDatos() {
		return datos;
	}
	public void setDatos(Page<TEntradaLinea> datos) {
		this.datos = datos;
	}
	public Entrada getEntradaCab() {
		return entradaCab;
	}
	public void setEntradaCab(Entrada entradaCab) {
		this.entradaCab = entradaCab;
	}
}
