package es.eroski.misumi.model;

public class ReposicionSeccion {
	private String seccion;
	private String descripcion;
	
	public ReposicionSeccion(String seccion, String descripcion) {
		super();
		this.seccion = seccion;
		this.descripcion = descripcion;
	}
	
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
