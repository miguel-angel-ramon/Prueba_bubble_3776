package es.eroski.misumi.model;

import java.util.List;

public class CalendarioProcesosDiarios {
	private Long pCodError;
	private String pDescError;
	private List<CalendarioDiaCambioServicio> listadoServiciosCentro ;
	
	private List<TCalendarioDiaCambioServicio> listadoServiciosCentroTemporal;
	
	public CalendarioProcesosDiarios() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioProcesosDiarios(Long pCodError, String pDescError,
			List<CalendarioDiaCambioServicio> listadoServiciosCentro, List<TCalendarioDiaCambioServicio> listadoServiciosCentroTemporal) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.listadoServiciosCentro = listadoServiciosCentro;
		this.listadoServiciosCentroTemporal = listadoServiciosCentroTemporal;
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
	public List<CalendarioDiaCambioServicio> getListadoServiciosCentro() {
		return listadoServiciosCentro;
	}
	public void setListadoServiciosCentro(List<CalendarioDiaCambioServicio> listadoServiciosCentro) {
		this.listadoServiciosCentro = listadoServiciosCentro;
	}
	public List<TCalendarioDiaCambioServicio> getListadoServiciosCentroTemporal() {
		return listadoServiciosCentroTemporal;
	}
	public void setListadoServiciosCentroTemporal(List<TCalendarioDiaCambioServicio> listadoServiciosCentroTemporal) {
		this.listadoServiciosCentroTemporal = listadoServiciosCentroTemporal;
	}
}
