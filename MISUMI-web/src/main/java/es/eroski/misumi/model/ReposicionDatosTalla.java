package es.eroski.misumi.model;

public class ReposicionDatosTalla {
	private Long codArt;
	private String proveedor;
	private String estrEroski;
	private String modeloEroski;
	private String refSonae;
	private Long codError;
	private String descError;
	
	public ReposicionDatosTalla(Long codArt, String proveedor, String estrEroski, String modeloEroski, String refSonae,
			Long codError, String descError) {
		super();
		this.codArt = codArt;
		this.proveedor = proveedor;
		this.estrEroski = estrEroski;
		this.modeloEroski = modeloEroski;
		this.refSonae = refSonae;
		this.codError = codError;
		this.descError = descError;
	}

	public Long getCodArt() {
		return codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getEstrEroski() {
		return estrEroski;
	}

	public void setEstrEroski(String estrEroski) {
		this.estrEroski = estrEroski;
	}

	public String getModeloEroski() {
		return modeloEroski;
	}

	public void setModeloEroski(String modeloEroski) {
		this.modeloEroski = modeloEroski;
	}

	public String getRefSonae() {
		return refSonae;
	}

	public void setRefSonae(String refSonae) {
		this.refSonae = refSonae;
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
}
