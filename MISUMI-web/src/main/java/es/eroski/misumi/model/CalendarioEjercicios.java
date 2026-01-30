package es.eroski.misumi.model;

import java.util.List;

public class CalendarioEjercicios {
	private Long codError;
	private String descrError;
	private List<CalendarioEjercicio> lstCalendarioEjercicio;
	
	//Guarda el rol del usuario.tecnicRole=1 centerRole=2 consultaRole=3
	private String rol;
	
	public CalendarioEjercicios() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioEjercicios(Long codError, String descrError, List<CalendarioEjercicio> lstCalendarioEjercicio) {
		super();
		this.codError = codError;
		this.descrError = descrError;
		this.lstCalendarioEjercicio = lstCalendarioEjercicio;
	}
	
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getDescrError() {
		return descrError;
	}
	public void setDescrError(String descrError) {
		this.descrError = descrError;
	}
	public List<CalendarioEjercicio> getLstCalendarioEjercicio() {
		return lstCalendarioEjercicio;
	}
	public void setLstCalendarioEjercicio(List<CalendarioEjercicio> lstCalendarioEjercicio) {
		this.lstCalendarioEjercicio = lstCalendarioEjercicio;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
}
