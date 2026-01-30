package es.eroski.misumi.model;

import java.util.Date;

public class TCalendarioDiaCambioServicio {
	//Atributos para búsqueda
	private String idSesion;
	private Long codCentro;
	private Date fechaCalendario;
	
	//Atributos de eliminación tabla temporal
	private Date creationDate;
	
	//Atributos del servicio
	private Long codigoServicio;
	private String denominacionServicio;
	private String servicioHabitual;
	private String cambioEstacional;
	private String cambioManual;
	
	private String eCambioPlataforma;
	private String eObservaConfirmaPlataforma;
	
	private String tipoEjercicio;
	
	//Atributo de error
	private Long codError;
	
	//Atributo de estado del servicio (guardado, modificado, con error, etc.)
	private Long estadoDia;
	
	
	private String cambioManualOriginal;

	private Long ejercicio;
	
	//MISUMI-237
	private String puedeSolicitarServicio;
	
	public TCalendarioDiaCambioServicio() {
		super();
	}

	public TCalendarioDiaCambioServicio(String idSesion, Long codCentro, Date fechaCalendario, Date creationDate,
			Long codigoServicio, String denominacionServicio, String servicioHabitual, String cambioEstacional,
			String cambioManual, Long codError, Long estadoDia) {
		super();
		this.idSesion = idSesion;
		this.codCentro = codCentro;
		this.fechaCalendario = fechaCalendario;
		this.creationDate = creationDate;
		this.codigoServicio = codigoServicio;
		this.denominacionServicio = denominacionServicio;
		this.servicioHabitual = servicioHabitual;
		this.cambioEstacional = cambioEstacional;
		this.cambioManual = cambioManual;
		this.codError = codError;
		this.estadoDia = estadoDia;
	}

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Date getFechaCalendario() {
		return fechaCalendario;
	}

	public void setFechaCalendario(Date fechaCalendario) {
		this.fechaCalendario = fechaCalendario;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public Long getEstadoDia() {
		return estadoDia;
	}

	public void setEstadoDia(Long estadoDia) {
		this.estadoDia = estadoDia;
	}

	public String getCambioManualOriginal() {
		return cambioManualOriginal;
	}

	public void setCambioManualOriginal(String cambioManualOriginal) {
		this.cambioManualOriginal = cambioManualOriginal;
	}
	
	public String geteCambioPlataforma() {
		return eCambioPlataforma;
	}

	public void seteCambioPlataforma(String eCambioPlataforma) {
		this.eCambioPlataforma = eCambioPlataforma;
	}

	public String geteObservaConfirmaPlataforma() {
		return eObservaConfirmaPlataforma;
	}

	public void seteObservaConfirmaPlataforma(String eObservaConfirmaPlataforma) {
		this.eObservaConfirmaPlataforma = eObservaConfirmaPlataforma;
	}

	public String getTipoEjercicio() {
		return tipoEjercicio;
	}

	public void setTipoEjercicio(String tipoEjercicio) {
		this.tipoEjercicio = tipoEjercicio;
	}

	public Long getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Long ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getPuedeSolicitarServicio() {
		return puedeSolicitarServicio;
	}

	public void setPuedeSolicitarServicio(String puedeSolicitarServicio) {
		this.puedeSolicitarServicio = puedeSolicitarServicio;
	}
}
