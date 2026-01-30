package es.eroski.misumi.model;

public class DevolucionDescripcion {

	private String descripcion;
	private String localizador;
	
	public DevolucionDescripcion(String descripcion, String localizador) {
		super();
		this.descripcion = descripcion;
		this.localizador = localizador;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getLocalizador() {
		return localizador;
	}
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}
}
