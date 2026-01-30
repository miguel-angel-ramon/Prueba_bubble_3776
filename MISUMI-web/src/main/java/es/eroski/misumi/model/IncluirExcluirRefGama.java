package es.eroski.misumi.model;

public class IncluirExcluirRefGama {
	private Long codError;
	private String descError;
	private String accion;
	
	public IncluirExcluirRefGama() {
	    super();
	}
	
	public IncluirExcluirRefGama(Long codError, String descError) {
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

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

}
