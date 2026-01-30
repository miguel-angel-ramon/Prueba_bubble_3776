package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaError implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codError;
	private String descError;
	
	
	public PdaError() {
	    super();
	}

	public PdaError(Long codError, String descError) {
	    super();
	    this.codError = codError;
	    this.descError = descError;
	    
	}
	
	public Long getCodError() {
		return this.codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}
	
	public String getDescError() {
		return this.descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}
}