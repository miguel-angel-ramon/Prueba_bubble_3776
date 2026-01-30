package es.eroski.misumi.model;

import java.io.Serializable;

public class ArticuloMatricula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Centro centro;
	private Long codArt;

	
    public ArticuloMatricula() {
		super();
	}

	public ArticuloMatricula(Centro centro, Long codArt) {
		super();
		this.centro = centro;
		this.codArt = codArt;
		
	
	}

	public Centro getCentro() {
		return this.centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	
}