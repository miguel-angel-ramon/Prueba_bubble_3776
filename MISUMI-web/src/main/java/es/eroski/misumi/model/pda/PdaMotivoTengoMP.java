package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaMotivoTengoMP implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String textoMotivo;
	private int posicion;
	
	public PdaMotivoTengoMP() {
		super();
	}

	public PdaMotivoTengoMP(String textoMotivo,int posicion) {
		super();
		
		this.textoMotivo = textoMotivo;
		this.posicion=posicion;
	}

	public String getTextoMotivo() {
		return this.textoMotivo;
	}

	public void setTextoMotivo(String textoMotivo) {
		this.textoMotivo = textoMotivo;
	}

	public int getPosicion() {
		return this.posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	
}
