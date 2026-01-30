package es.eroski.misumi.model;

import java.util.HashMap;
import java.util.List;

public class CalendarioCambioEstacional {
	private Long pCodError;
	private String pDescError;
	
	private List<CalendarioValidacion> listadoValidaciones ;
	
	//Se ha creado una lista de este tipo porque en principio se pensó que la aplicación tuviera una semana habitual por servicio para varias semanas
	//temporales. Más tarde se les ocurrió que por servicio querían varias semanas habituales y varias temporales relacionadas con las habituales. Se pidió
	//el cambio a SIA pero no quisieron hacerlo, por lo que se tuvo que tratar en JAVA.
	private HashMap<Long,CalendarioValidacionTratado> listadoValidacionesTratadas ;
	
	public CalendarioCambioEstacional() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioCambioEstacional(Long pCodError, String pDescError,
			List<CalendarioValidacion> listadoValidaciones) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.listadoValidaciones = listadoValidaciones;
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

	public List<CalendarioValidacion> getListadoValidaciones() {
		return listadoValidaciones;
	}

	public void setListadoValidaciones(List<CalendarioValidacion> listadoValidaciones) {
		this.listadoValidaciones = listadoValidaciones;
	}

	public HashMap<Long, CalendarioValidacionTratado> getListadoValidacionesTratadas() {
		return listadoValidacionesTratadas;
	}

	public void setListadoValidacionesTratadas(HashMap<Long, CalendarioValidacionTratado> listadoValidacionesTratadas) {
		this.listadoValidacionesTratadas = listadoValidacionesTratadas;
	}
}
