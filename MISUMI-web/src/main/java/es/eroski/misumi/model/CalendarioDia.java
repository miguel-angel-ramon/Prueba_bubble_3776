package es.eroski.misumi.model;

import java.util.Date;

public class CalendarioDia {
	private Date fechaCalendario;
	private String festivo;
	private String ponerDiaVerde;
	private String cerrado;
	private String servicioHabitual;
	private String cambioEstacional;
	private String cambioManual;
	private String eCambioPlataforma;
	private String eSePuedeModificarServicio;
	private String eAprobadoCambio;
	private String suministro;
	private CalendarioProcesosDiarios calendarioProcesosDiarios;
	
	//MISUMI-237
	private String verdePlataforma;
	private String puedeSolicitarServicio;
	private String noServicio;
	public CalendarioDia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioDia(Date fechaCalendario, String festivo, String ponerDiaVerde, String cerrado,
			String servicioHabitual, String cambioEstacional, String cambioManual, String eCambioPlataforma, String eSePuedeModificarServicio, String eAprobadoCambio, String verdePlataforma, String puedeSolicitarServicio, String noServicio) {
		super();
		this.fechaCalendario = fechaCalendario;
		this.festivo = festivo;
		this.ponerDiaVerde = ponerDiaVerde;
		this.cerrado = cerrado;
		this.servicioHabitual = servicioHabitual;
		this.cambioEstacional = cambioEstacional;
		this.cambioManual = cambioManual;
		this.eCambioPlataforma = eCambioPlataforma;
		this.eSePuedeModificarServicio = eSePuedeModificarServicio;
		this.eAprobadoCambio = eAprobadoCambio;
		this.verdePlataforma = verdePlataforma;
		this.puedeSolicitarServicio = puedeSolicitarServicio;
		this.noServicio = noServicio;
	}
	
	public CalendarioDia(Date fechaCalendario, String festivo, String ponerDiaVerde, String cerrado,
			String servicioHabitual, String cambioEstacional, String cambioManual, String suministro,
			CalendarioProcesosDiarios calendarioProcesosDiarios) {
		super();
		this.fechaCalendario = fechaCalendario;
		this.festivo = festivo;
		this.ponerDiaVerde = ponerDiaVerde;
		this.cerrado = cerrado;
		this.servicioHabitual = servicioHabitual;
		this.cambioEstacional = cambioEstacional;
		this.cambioManual = cambioManual;
		this.suministro = suministro;
		this.calendarioProcesosDiarios = calendarioProcesosDiarios;
	}

	public CalendarioDia(Date fechaCalendario, String festivo, String ponerDiaVerde, String cerrado,
			String servicioHabitual, String cambioEstacional, String cambioManual,
			CalendarioProcesosDiarios calendarioProcesosDiarios) {
		super();
		this.fechaCalendario = fechaCalendario;
		this.festivo = festivo;
		this.ponerDiaVerde = ponerDiaVerde;
		this.cerrado = cerrado;
		this.servicioHabitual = servicioHabitual;
		this.cambioEstacional = cambioEstacional;
		this.cambioManual = cambioManual;
		this.calendarioProcesosDiarios = calendarioProcesosDiarios;
	}

	public Date getFechaCalendario() {
		return fechaCalendario;
	}
	public void setFechaCalendario(Date fechaCalendario) {
		this.fechaCalendario = fechaCalendario;
	}
	public String getFestivo() {
		return festivo;
	}
	public void setFestivo(String festivo) {
		this.festivo = festivo;
	}
	public String getPonerDiaVerde() {
		return ponerDiaVerde;
	}
	public void setPonerDiaVerde(String ponerDiaVerde) {
		this.ponerDiaVerde = ponerDiaVerde;
	}
	public String getCerrado() {
		return cerrado;
	}
	public void setCerrado(String cerrado) {
		this.cerrado = cerrado;
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
	
	public String getECambioPlataforma() {
		return eCambioPlataforma;
	}

	public void setECambioPlataforma(String eCambioPlataforma) {
		this.eCambioPlataforma = eCambioPlataforma;
	}

	public String getESePuedeModificarServicio() {
		return eSePuedeModificarServicio;
	}

	public void setESePuedeModificarServicio(String eSePuedeModificarServicio) {
		this.eSePuedeModificarServicio = eSePuedeModificarServicio;
	}

	public String getEAprobadoCambio() {
		return eAprobadoCambio;
	}

	public void setEAprobadoCambio(String eAprobadoCambio) {
		this.eAprobadoCambio = eAprobadoCambio;
	}
	public String getSuministro() {
		return suministro;
	}
	public void setSuministro(String suministro) {
		this.suministro = suministro;
	}
	public CalendarioProcesosDiarios getCalendarioProcesosDiarios() {
		return calendarioProcesosDiarios;
	}
	public void setCalendarioProcesosDiarios(CalendarioProcesosDiarios calendarioProcesosDiarios) {
		this.calendarioProcesosDiarios = calendarioProcesosDiarios;
	}

	public String geteCambioPlataforma() {
		return eCambioPlataforma;
	}

	public void seteCambioPlataforma(String eCambioPlataforma) {
		this.eCambioPlataforma = eCambioPlataforma;
	}

	public String geteSePuedeModificarServicio() {
		return eSePuedeModificarServicio;
	}

	public void seteSePuedeModificarServicio(String eSePuedeModificarServicio) {
		this.eSePuedeModificarServicio = eSePuedeModificarServicio;
	}

	public String geteAprobadoCambio() {
		return eAprobadoCambio;
	}

	public void seteAprobadoCambio(String eAprobadoCambio) {
		this.eAprobadoCambio = eAprobadoCambio;
	}

	public String getVerdePlataforma() {
		return verdePlataforma;
	}

	public void setVerdePlataforma(String verdePlataforma) {
		this.verdePlataforma = verdePlataforma;
	}

	public String getPuedeSolicitarServicio() {
		return puedeSolicitarServicio;
	}

	public void setPuedeSolicitarServicio(String puedeSolicitarServicio) {
		this.puedeSolicitarServicio = puedeSolicitarServicio;
	}

	public String getNoServicio() {
		return noServicio;
	}

	public void setNoServicio(String noServicio) {
		this.noServicio = noServicio;
	}
}
