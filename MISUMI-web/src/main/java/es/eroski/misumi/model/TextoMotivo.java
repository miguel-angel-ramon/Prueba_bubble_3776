package es.eroski.misumi.model;

import java.io.Serializable;

public class TextoMotivo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String texto1;
	private String texto2;
	
	public TextoMotivo() {
		super();
	}

	public TextoMotivo(String texto1, String texto2) {
		super();
		this.texto1 = texto1;
		this.texto2 = texto2;
	}

	public String getTexto1() {
		return this.texto1;
	}

	public void setTexto1(String texto1) {
		this.texto1 = texto1;
	}

	public String getTexto2() {
		return this.texto2;
	}

	public void setTexto2(String texto2) {
		this.texto2 = texto2;
	}
}