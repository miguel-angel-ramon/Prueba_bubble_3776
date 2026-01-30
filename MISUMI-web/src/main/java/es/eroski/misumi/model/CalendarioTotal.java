package es.eroski.misumi.model;

import java.util.List;

public class CalendarioTotal {
	private Long pCodError;
	private String pDescError;
	private String tipoEjercicio;
	private Long ejercicio;
	private String validarCalendario;
	private Long numeroDiasAdelanteAtrasVerde;
	private List<CalendarioDia> listadoFecha;
	
	private List<TCalendarioDia> listadoFechaTemporal;
	
	//Lista que guarda los meses y años distintos del calendario obtenido. Esta información se calcula mediante un select en la temporal,
	//no en procedimiento de PLSQL.
	private List<String> mesAnioLst;
	
	public CalendarioTotal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioTotal(Long pCodError, String pDescError, String validarCalendario, Long numeroDiasAdelanteAtrasVerde,
			List<CalendarioDia> listadoFecha) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.validarCalendario = validarCalendario;
		this.numeroDiasAdelanteAtrasVerde = numeroDiasAdelanteAtrasVerde;
		this.listadoFecha = listadoFecha;
	}
	
	public CalendarioTotal(Long pCodError, String pDescError, String validarCalendario, Long numeroDiasAdelanteAtrasVerde, List<CalendarioDia> listadoFecha,
			List<TCalendarioDia> listadoFechaTemporal) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.validarCalendario = validarCalendario;
		this.numeroDiasAdelanteAtrasVerde = numeroDiasAdelanteAtrasVerde;
		this.listadoFechaTemporal = listadoFechaTemporal;
	}
	
	public CalendarioTotal(Long pCodError, String pDescError, String validarCalendario,
			Long numeroDiasAdelanteAtrasVerde, List<CalendarioDia> listadoFecha,
			List<TCalendarioDia> listadoFechaTemporal, List<String> mesAnioLst) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.validarCalendario = validarCalendario;
		this.numeroDiasAdelanteAtrasVerde = numeroDiasAdelanteAtrasVerde;
		this.listadoFecha = listadoFecha;
		this.listadoFechaTemporal = listadoFechaTemporal;
		this.mesAnioLst = mesAnioLst;
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
	public String getValidarCalendario() {
		return validarCalendario;
	}
	public void setValidarCalendario(String validarCalendario) {
		this.validarCalendario = validarCalendario;
	}
	public Long getNumeroDiasAdelanteAtrasVerde() {
		return numeroDiasAdelanteAtrasVerde;
	}
	public void setNumeroDiasAdelanteAtrasVerde(Long numeroDiasAdelanteAtrasVerde) {
		this.numeroDiasAdelanteAtrasVerde = numeroDiasAdelanteAtrasVerde;
	}
	public List<CalendarioDia> getListadoFecha() {
		return listadoFecha;
	}
	public void setListadoFecha(List<CalendarioDia> listadoFecha) {
		this.listadoFecha = listadoFecha;
	}
	public List<TCalendarioDia> getListadoFechaTemporal() {
		return listadoFechaTemporal;
	}
	public void setListadoFechaTemporal(List<TCalendarioDia> listadoFechaTemporal) {
		this.listadoFechaTemporal = listadoFechaTemporal;
	}
	public List<String> getMesAnioLst() {
		return mesAnioLst;
	}
	public void setMesAnioLst(List<String> mesAnioLst) {
		this.mesAnioLst = mesAnioLst;
	}
}
