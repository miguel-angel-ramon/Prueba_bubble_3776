package es.eroski.misumi.model;

public class CalendarioDiaCambioServicio {
	private Long codigoServicio;
	private String denominacionServicio;
	private String servicioHabitual;
	private String cambioEstacional;
	private String cambioManual;
	private String eCambioPlataforma;
	private String eObservacionesConfirmacionPlataforma;
	private String puedesolicitarServicio;
	

	public CalendarioDiaCambioServicio(Long codigoServicio, String denominacionServicio, String servicioHabitual,
			String cambioEstacional, String cambioManual, String eCambioPlataforma, String eObservacionesConfirmacionPlataforma, String puedesolicitarServicio) {
		super();
		this.codigoServicio = codigoServicio;
		this.denominacionServicio = denominacionServicio;
		this.servicioHabitual = servicioHabitual;
		this.cambioEstacional = cambioEstacional;
		this.cambioManual = cambioManual;
		this.eCambioPlataforma = eCambioPlataforma;
		this.eObservacionesConfirmacionPlataforma = eObservacionesConfirmacionPlataforma;
		this.puedesolicitarServicio = puedesolicitarServicio;
	}
	
	public CalendarioDiaCambioServicio() {
		super();
	}

	public Long getCodigoServicio() {
		return codigoServicio;
	}
	public void setCodigoServicio(Long codigoServicio) {
		this.codigoServicio = codigoServicio;
	}
	public String getDenominacionServicio() {
		return denominacionServicio;
	}
	public void setDenominacionServicio(String denominacionServicio) {
		this.denominacionServicio = denominacionServicio;
	}
	public String getServicioHabitual() {
		return servicioHabitual;
	}
	public void setServicioHabitual(String servicioHabitual) {
		this.servicioHabitual = servicioHabitual;
	}
	public String getCambioEstacional() {
		return cambioEstacional;
	}
	public void setCambioEstacional(String cambioEstacional) {
		this.cambioEstacional = cambioEstacional;
	}
	public String getCambioManual() {
		return cambioManual;
	}
	public void setCambioManual(String cambioManual) {
		this.cambioManual = cambioManual;
	}
	public String geteCambioPlataforma() {
		return eCambioPlataforma;
	}
	public void seteCambioPlataforma(String eCambioPlataforma) {
		this.eCambioPlataforma = eCambioPlataforma;
	}
	public String geteObservacionesConfirmacionPlataforma() {
		return eObservacionesConfirmacionPlataforma;
	}
	public void seteObservacionesConfirmacionPlataforma(String eObservacionesConfirmacionPlataforma) {
		this.eObservacionesConfirmacionPlataforma = eObservacionesConfirmacionPlataforma;
	}
	public String getPuedesolicitarServicio() {
		return puedesolicitarServicio;
	}
	public void setPuedesolicitarServicio(String puedesolicitarServicio) {
		this.puedesolicitarServicio = puedesolicitarServicio;
	}
}
