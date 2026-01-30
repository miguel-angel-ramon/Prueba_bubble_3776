package es.eroski.misumi.model;

import java.util.Date;
import java.util.List;

public class TCalendarioDia {
	private String idSesion;
	private Date creationDate;
	private Long codCentro;
	private Long codServicio;
	
	private Date fechaCalendario;
	private String festivo;
	private String ponerDiaVerde;
	private String cerrado;
	private String servicioHabitual;
	private String cambioEstacional;
	private String cambioManual;
	private String suministro;
	private Long estado;
	private Long estadoServicio;
	private Long codError;

	//Flg que indica si hay que mostrar el día. Si es N, no se muestra.
	//Este atributo se utiliza cuando en javascript creamos la lista de
	//días a no mostrar, para que sepa cuando no hay que pintar.
	private String mostrarDia;
	
	//Lista con los valores temporales de los servicios del día.
	private List<TCalendarioDiaCambioServicio> tCalendarioDiaCambioServicioLst;

	//Flag que guarda si se han buscado los servicios de un día o no. De esta forma sabemos si hay que llamar al PLSQL de los servicios del día
	//o por el contrario si hay que tirar de tabla temporal.
	private String flgServiciosBuscados;
	
	//Campo de la tabla que guarda el mes y el año del día. Esto se usará para luego hacer las paginaciones del calendario.
	private String mesAnio;
	
	//Guarda el valor original del cambio manual.
	private String cambioManualOri;
	
	//Flag que controla si se ha quitado o puesto el cambio manual.
	private String cambioManualServiciosNulos;
	
	
	private String tipoEjercicio;
	private String eCambioPlataforma;
	private String eSePuedeModificarServicio;
	private String eAprobadoCambio;
	private String diaPasado; //Indica si el dia es anterior al dia consultado (SYSDATE)

	
	private Long ejercicio; //El año del calendario
	
	//MISUMI-237
	private String verdePlataforma;
	private String puedeSolicitarServicio;
	private String noServicio;
	
	public TCalendarioDia(String idSesion, Date creationDate, Date fechaCalendario, String festivo,
			String ponerDiaVerde, String cerrado, String servicioHabitual, String cambioEstacional, String cambioManual,
			String suministro,Long codCentro, Long estado, Long codError) {
		super();
		this.idSesion = idSesion;
		this.creationDate = creationDate;
		this.fechaCalendario = fechaCalendario;
		this.festivo = festivo;
		this.ponerDiaVerde = ponerDiaVerde;
		this.cerrado = cerrado;
		this.servicioHabitual = servicioHabitual;
		this.cambioEstacional = cambioEstacional;
		this.cambioManual = cambioManual;
		this.suministro = suministro;
		this.codCentro = codCentro;
		this.estado = estado;
		this.codError = codError;
	}

	public TCalendarioDia(String idSesion, Date creationDate, Long codCentro, Date fechaCalendario, String festivo,
			String ponerDiaVerde, String cerrado, String servicioHabitual, String cambioEstacional, String cambioManual,
			String suministro, Long estado, Long codError, String mostrarDia,
			List<TCalendarioDiaCambioServicio> tCalendarioDiaCambioServicioLst, String flgServiciosBuscados,
			String mesAnio) {
		super();
		this.idSesion = idSesion;
		this.creationDate = creationDate;
		this.codCentro = codCentro;
		this.fechaCalendario = fechaCalendario;
		this.festivo = festivo;
		this.ponerDiaVerde = ponerDiaVerde;
		this.cerrado = cerrado;
		this.servicioHabitual = servicioHabitual;
		this.cambioEstacional = cambioEstacional;
		this.cambioManual = cambioManual;
		this.suministro = suministro;
		this.estado = estado;
		this.codError = codError;
		this.mostrarDia = mostrarDia;
		this.tCalendarioDiaCambioServicioLst = tCalendarioDiaCambioServicioLst;
		this.flgServiciosBuscados = flgServiciosBuscados;
		this.mesAnio = mesAnio;
	}

	public TCalendarioDia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIdSesion() {
		return idSesion;
	}
	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	public String getSuministro() {
		return suministro;
	}
	public void setSuministro(String suministro) {
		this.suministro = suministro;
	}
	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Long getEstadoServicio() {
		return estadoServicio;
	}

	public void setEstadoServicio(Long estadoServicio) {
		this.estadoServicio = estadoServicio;
	}

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getMostrarDia() {
		return mostrarDia;
	}

	public void setMostrarDia(String mostrarDia) {
		this.mostrarDia = mostrarDia;
	}

	public List<TCalendarioDiaCambioServicio> gettCalendarioDiaCambioServicioLst() {
		return tCalendarioDiaCambioServicioLst;
	}

	public void settCalendarioDiaCambioServicioLst(List<TCalendarioDiaCambioServicio> tCalendarioDiaCambioServicioLst) {
		this.tCalendarioDiaCambioServicioLst = tCalendarioDiaCambioServicioLst;
	}

	public String getFlgServiciosBuscados() {
		return flgServiciosBuscados;
	}

	public void setFlgServiciosBuscados(String flgServiciosBuscados) {
		this.flgServiciosBuscados = flgServiciosBuscados;
	}

	public String getMesAnio() {
		return mesAnio;
	}

	public void setMesAnio(String mesAnio) {
		this.mesAnio = mesAnio;
	}

	public String getCambioManualOri() {
		return cambioManualOri;
	}

	public void setCambioManualOri(String cambioManualOri) {
		this.cambioManualOri = cambioManualOri;
	}

	public String getCambioManualServiciosNulos() {
		return cambioManualServiciosNulos;
	}

	public void setCambioManualServiciosNulos(String cambioManualServiciosNulos) {
		this.cambioManualServiciosNulos = cambioManualServiciosNulos;
	}

	public Long getCodServicio() {
		return codServicio;
	}

	public void setCodServicio(Long codServicio) {
		this.codServicio = codServicio;
	}
	

	public String getTipoEjercicio() {
		return tipoEjercicio;
	}

	public void setTipoEjercicio(String tipoEjercicio) {
		this.tipoEjercicio = tipoEjercicio;
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

	public String getDiaPasado() {
		return diaPasado;
	}

	public void setDiaPasado(String diaPasado) {
		this.diaPasado = diaPasado;
	}
	

	public Long getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Long ejercicio) {
		this.ejercicio = ejercicio;
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
