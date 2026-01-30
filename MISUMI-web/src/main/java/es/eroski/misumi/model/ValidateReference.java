package es.eroski.misumi.model;

import java.io.Serializable;

public class ValidateReference implements Serializable {

	private static final long serialVersionUID = 1L;

	private String valido;
	private Long codArtEroski;
	private Long codArtCaprabo;
	private boolean esCaprabo;

	public String getValido() {
		return valido;
	}

	public void setValido(String valido) {
		this.valido = valido;
	}

	public Long getCodArtEroski() {
		return codArtEroski;
	}

	public void setCodArtEroski(Long codArtEroski) {
		this.codArtEroski = codArtEroski;
	}

	public Long getCodArtCaprabo() {
		return codArtCaprabo;
	}

	public void setCodArtCaprabo(Long codArtCaprabo) {
		this.codArtCaprabo = codArtCaprabo;
	}

	public boolean getEsCaprabo() {
		return esCaprabo;
	}

	public void setEsCaprabo(boolean esCaprabo) {
		this.esCaprabo = esCaprabo;
	}

}
