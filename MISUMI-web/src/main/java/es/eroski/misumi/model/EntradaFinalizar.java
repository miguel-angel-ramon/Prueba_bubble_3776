package es.eroski.misumi.model;

public class EntradaFinalizar {
	private Long codError;
	private String descError;
	
	public EntradaFinalizar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntradaFinalizar(Long codError, String descError) {
		super();
		this.codError = codError;
		this.descError = descError;
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
