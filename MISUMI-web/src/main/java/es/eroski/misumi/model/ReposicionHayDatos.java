package es.eroski.misumi.model;

public class ReposicionHayDatos {
	private String hayDatos;
	private Long codError;
	private String descError;
	
	
	public ReposicionHayDatos() {
		super();
	
	}
	
	
	public ReposicionHayDatos(String hayDatos, Long codError, String descError) {
		super();
		this.hayDatos = hayDatos;
		this.codError = codError;
		this.descError = descError;
	}
	
	public String getHayDatos() {
		return hayDatos;
	}
	public void setHayDatos(String hayDatos) {
		this.hayDatos = hayDatos;
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
