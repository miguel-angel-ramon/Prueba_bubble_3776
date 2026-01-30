package es.eroski.misumi.model;

import java.util.Date;

public class CalendarioDiaWarning {
	private Date fechaAfectada;
	private String descripcionAviso;
	private String leido;
	private String fechaAfectadaDDMMYYYY;
	
	public CalendarioDiaWarning() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CalendarioDiaWarning(Date fechaAfectada, String descripcionAviso, String leido,
			String fechaAfectadaDDMMYYYY) {
		super();
		this.fechaAfectada = fechaAfectada;
		this.descripcionAviso = descripcionAviso;
		this.leido = leido;
		this.fechaAfectadaDDMMYYYY = fechaAfectadaDDMMYYYY;
	}

	public Date getFechaAfectada() {
		return fechaAfectada;
	}

	public void setFechaAfectada(Date fechaAfectada) {
		this.fechaAfectada = fechaAfectada;
	}

	public String getDescripcionAviso() {
		return descripcionAviso;
	}

	public void setDescripcionAviso(String descripcionAviso) {
		this.descripcionAviso = descripcionAviso;
	}

	public String getLeido() {
		return leido;
	}

	public void setLeido(String leido) {
		this.leido = leido;
	}

	public String getFechaAfectadaDDMMYYYY() {
		return fechaAfectadaDDMMYYYY;
	}

	public void setFechaAfectadaDDMMYYYY(String fechaAfectadaDDMMYYYY) {
		this.fechaAfectadaDDMMYYYY = fechaAfectadaDDMMYYYY;
	}
	
}
