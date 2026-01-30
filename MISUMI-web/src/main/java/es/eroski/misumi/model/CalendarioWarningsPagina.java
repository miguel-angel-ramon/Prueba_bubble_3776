package es.eroski.misumi.model;

import java.io.Serializable;

import es.eroski.misumi.model.ui.Page;

public class CalendarioWarningsPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<CalendarioDiaWarning> datos;
    private Long codError;
	private String descrError;
	
	public CalendarioWarningsPagina() {
		super();
	}

	public Page<CalendarioDiaWarning> getDatos() {
		return datos;
	}

	public void setDatos(Page<CalendarioDiaWarning> datos) {
		this.datos = datos;
	}

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescrError() {
		return descrError;
	}

	public void setDescrError(String descrError) {
		this.descrError = descrError;
	}
}