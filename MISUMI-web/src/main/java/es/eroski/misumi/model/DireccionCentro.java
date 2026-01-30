package es.eroski.misumi.model;

import java.io.Serializable;

public class DireccionCentro implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String direccion;
	private String latitud;
	private String longitud;
	
	public DireccionCentro() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DireccionCentro(String direccion, String latitud, String longitud) {
		super();
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
