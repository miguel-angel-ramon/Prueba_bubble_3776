package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class CalendarioWarnings implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private List<CalendarioDiaWarning> listaWarnings;
	private Long codError;
	private String descrError;
	
	public CalendarioWarnings() {
		super();
	}

	public CalendarioWarnings(Long codCentro, List<CalendarioDiaWarning> listaWarnings, Long codError,
			String descrError) {
		super();
		this.codCentro = codCentro;
		this.listaWarnings = listaWarnings;
		this.codError = codError;
		this.descrError = descrError;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public List<CalendarioDiaWarning> getListaWarnings() {
		return listaWarnings;
	}

	public void setListaWarnings(List<CalendarioDiaWarning> listaWarnings) {
		this.listaWarnings = listaWarnings;
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