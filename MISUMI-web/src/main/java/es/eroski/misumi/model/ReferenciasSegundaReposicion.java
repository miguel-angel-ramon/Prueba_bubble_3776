package es.eroski.misumi.model;

import java.util.List;

public class ReferenciasSegundaReposicion {
	private Long pCodError;
	private String pDescError;
	private List<ListadoRefSegundaReposicionSalida> listadoRefSegundaReposicionSalidaList;
	
	public ReferenciasSegundaReposicion() {
		super();
	}

	public ReferenciasSegundaReposicion(Long pCodError, String pDescError,
			List<ListadoRefSegundaReposicionSalida> listadoRefSegundaReposicionSalidaList) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.listadoRefSegundaReposicionSalidaList = listadoRefSegundaReposicionSalidaList;
	}
	
	public Long getpCodError() {
		return pCodError;
	}
	public void setpCodError(Long pCodError) {
		this.pCodError = pCodError;
	}
	public String getpDescError() {
		return pDescError;
	}
	public void setpDescError(String pDescError) {
		this.pDescError = pDescError;
	}
	public List<ListadoRefSegundaReposicionSalida> getListadoRefSegundaReposicionSalidaList() {
		return listadoRefSegundaReposicionSalidaList;
	}
	public void setListadoRefSegundaReposicionSalidaList(
			List<ListadoRefSegundaReposicionSalida> listadoRefSegundaReposicionSalidaList) {
		this.listadoRefSegundaReposicionSalidaList = listadoRefSegundaReposicionSalidaList;
	}
	
}
