package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class AreaFacingCeroSIALista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<ECAreaFacingCero> areas;
	private Long codError;
	private String mensajeError;
	
	public List<ECAreaFacingCero> getAreas() {
		return areas;
	}
	public void setAreas(List<ECAreaFacingCero> areas) {
		this.areas = areas;
	}
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

}