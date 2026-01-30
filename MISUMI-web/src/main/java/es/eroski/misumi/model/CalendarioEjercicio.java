package es.eroski.misumi.model;

import java.util.Date;

public class CalendarioEjercicio {

	private String tipoEjercicio;
	private Long anioEjercicio;
	private String flgPendienteValidarEjercicio;
	private Date fechaLimiteValidacion;
	private String strFechaLimiteValidacion;
	

	private String flgEjercicioValidado;
	private String flgCambioEstacional;
	private String flgModificarServicioCentro;
	private String flgModificarCalendarioCentro;
	private String flgModificarServicioTecnico;
	private String flgModificarCalendarioTecnico;
	
	public CalendarioEjercicio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioEjercicio(String tipoEjercicio, Long anioEjercicio, String flgPendienteValidarEjercicio,
			Date fechaLimiteValidacion, String flgEjercicioValidado, String flgCambioEstacional,
			String flgModificarServicioCentro, String flgModificarCalendarioCentro, String flgModificarServicioTecnico,
			String flgModificarCalendarioTecnico) {
		super();
		this.tipoEjercicio = tipoEjercicio;
		this.anioEjercicio = anioEjercicio;
		this.flgPendienteValidarEjercicio = flgPendienteValidarEjercicio;
		this.fechaLimiteValidacion = fechaLimiteValidacion;
		this.flgEjercicioValidado = flgEjercicioValidado;
		this.flgCambioEstacional = flgCambioEstacional;
		this.flgModificarServicioCentro = flgModificarServicioCentro;
		this.flgModificarCalendarioCentro = flgModificarCalendarioCentro;
		this.flgModificarServicioTecnico = flgModificarServicioTecnico;
		this.flgModificarCalendarioTecnico = flgModificarCalendarioTecnico;
	}

	public String getTipoEjercicio() {
		return tipoEjercicio;
	}

	public void setTipoEjercicio(String tipoEjercicio) {
		this.tipoEjercicio = tipoEjercicio;
	}

	public Long getAnioEjercicio() {
		return anioEjercicio;
	}

	public void setAnioEjercicio(Long anioEjercicio) {
		this.anioEjercicio = anioEjercicio;
	}

	public String getFlgPendienteValidarEjercicio() {
		return flgPendienteValidarEjercicio;
	}

	public void setFlgPendienteValidarEjercicio(String flgPendienteValidarEjercicio) {
		this.flgPendienteValidarEjercicio = flgPendienteValidarEjercicio;
	}

	public Date getFechaLimiteValidacion() {
		return fechaLimiteValidacion;
	}

	public void setFechaLimiteValidacion(Date fechaLimiteValidacion) {
		this.fechaLimiteValidacion = fechaLimiteValidacion;
	}
	
	public String getStrFechaLimiteValidacion() {
		return strFechaLimiteValidacion;
	}

	public void setStrFechaLimiteValidacion(String strFechaLimiteValidacion) {
		this.strFechaLimiteValidacion = strFechaLimiteValidacion;
	}

	public String getFlgEjercicioValidado() {
		return flgEjercicioValidado;
	}

	public void setFlgEjercicioValidado(String flgEjercicioValidado) {
		this.flgEjercicioValidado = flgEjercicioValidado;
	}

	public String getFlgCambioEstacional() {
		return flgCambioEstacional;
	}

	public void setFlgCambioEstacional(String flgCambioEstacional) {
		this.flgCambioEstacional = flgCambioEstacional;
	}

	public String getFlgModificarServicioCentro() {
		return flgModificarServicioCentro;
	}

	public void setFlgModificarServicioCentro(String flgModificarServicioCentro) {
		this.flgModificarServicioCentro = flgModificarServicioCentro;
	}

	public String getFlgModificarCalendarioCentro() {
		return flgModificarCalendarioCentro;
	}

	public void setFlgModificarCalendarioCentro(String flgModificarCalendarioCentro) {
		this.flgModificarCalendarioCentro = flgModificarCalendarioCentro;
	}

	public String getFlgModificarServicioTecnico() {
		return flgModificarServicioTecnico;
	}

	public void setFlgModificarServicioTecnico(String flgModificarServicioTecnico) {
		this.flgModificarServicioTecnico = flgModificarServicioTecnico;
	}

	public String getFlgModificarCalendarioTecnico() {
		return flgModificarCalendarioTecnico;
	}

	public void setFlgModificarCalendarioTecnico(String flgModificarCalendarioTecnico) {
		this.flgModificarCalendarioTecnico = flgModificarCalendarioTecnico;
	}
}
