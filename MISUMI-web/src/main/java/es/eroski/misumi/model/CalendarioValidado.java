package es.eroski.misumi.model;

public class CalendarioValidado {
	private Long codError;
	private String descError;
	
	public CalendarioValidado(Long codError, String descError) {
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
