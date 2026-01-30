package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class AreaFacingCero implements Serializable {

	private static final long serialVersionUID = 1L;

	// Lista de las diferentes áreas de centros
	// parametrizados con Facing cero.
	private List<ECAreaFacingCero> lstAreaReferenciasFacingCero;

	// Codigo que nos indica si el centro tiene áreas de Facing cero.
	// Si codError = 0  --> Tiene avisos.
	// Resto de casos   --> No mostrar el aviso.
	private Long codError;
	
	// Descripción del error si es que lo hubiera al invocar al procedimiento de obtención de los avisos de facing cero.
	private String mensajeError;

	public List<ECAreaFacingCero> getLstAreaReferenciasFacingCero() {
		return lstAreaReferenciasFacingCero;
	}

	public void setLstAreaReferenciasFacingCero(List<ECAreaFacingCero> lstAreaReferenciasFacingCero) {
		this.lstAreaReferenciasFacingCero = lstAreaReferenciasFacingCero;
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