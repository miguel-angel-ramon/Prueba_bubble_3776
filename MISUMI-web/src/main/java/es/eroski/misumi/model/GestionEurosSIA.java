package es.eroski.misumi.model;

public class GestionEurosSIA {
	private Long codError;
	private String descError;
	private GestionEuros gestionEuros;

	public GestionEurosSIA() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GestionEurosSIA(Long codError, String descError, GestionEuros gestionEuros) {
		super();
		this.codError = codError;
		this.descError = descError;
		this.gestionEuros = gestionEuros;
	}

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public GestionEuros getGestionEuros() {
		return gestionEuros;
	}

	public void setGestionEuros(GestionEuros gestionEuros) {
		this.gestionEuros = gestionEuros;
	}
}
