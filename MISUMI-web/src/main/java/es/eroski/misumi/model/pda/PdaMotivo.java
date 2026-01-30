package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaMotivo implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String tipoMensaje;
	private PdaTextoMotivo textoMotivo;
	private int posicion;
	
	public PdaMotivo() {
		super();
	}

	public PdaMotivo( PdaTextoMotivo textoMotivo,String tipoMensaje,int posicion) {
		super();
		
		this.textoMotivo = textoMotivo;
		this.tipoMensaje=tipoMensaje;
		this.posicion=posicion;
	}

	public PdaTextoMotivo getTextoMotivo() {
		return this.textoMotivo;
	}

	public void setTextoMotivo(PdaTextoMotivo textoMotivo) {
		this.textoMotivo = textoMotivo;
	}

	public String getTipoMensaje() {
		return this.tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}
	
	public int getPosicion() {
		return this.posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	
}
