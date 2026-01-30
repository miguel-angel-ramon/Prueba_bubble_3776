package es.eroski.misumi.model;

import java.io.Serializable;

public class VMapaReferencia implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codMapa; 
	private String descMapa;
	private String bloqueo;
	
	public VMapaReferencia() {
	    super();
	}

	public VMapaReferencia(Long codMapa, String descMapa, String bloqueo) {
		super();
		this.codMapa = codMapa;
		this.descMapa = descMapa;
		this.bloqueo = bloqueo;
	}

	public Long getCodMapa() {
		return codMapa;
	}

	public void setCodMapa(Long codMapa) {
		this.codMapa = codMapa;
	}

	public String getDescMapa() {
		return descMapa;
	}

	public void setDescMapa(String descMapa) {
		this.descMapa = descMapa;
	}

	public String getMapa() {
		return codMapa + " - " + descMapa;
	}

	public String getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(String bloqueo) {
		this.bloqueo = bloqueo;
	}
	
}