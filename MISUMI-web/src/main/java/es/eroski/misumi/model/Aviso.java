package es.eroski.misumi.model;

import java.io.Serializable;

public class Aviso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private String claseMensaje;
	private String fechaIni;
	private String fechaFin;
	private String mensaje;
	private boolean isPda;
	
	public Aviso() {
	    super();
	}
	
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public String getClaseMensaje() {
		return claseMensaje;
	}
	public void setClaseMensaje(String claseMensaje) {
		this.claseMensaje = claseMensaje;
	}
	public String getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getMensaje() {
		return mensaje;
	}
	public boolean isPda() {
		return isPda;
	}
	public void setPda(boolean isPda) {
		this.isPda = isPda;
	}


}
