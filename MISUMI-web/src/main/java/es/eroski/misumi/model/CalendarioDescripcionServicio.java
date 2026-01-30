package es.eroski.misumi.model;

public class CalendarioDescripcionServicio {
	private String descripcion;
	private Long localizador;
		
	public CalendarioDescripcionServicio() {
		super();
	}

	public CalendarioDescripcionServicio(String descripcion, Long localizador) {
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
	public Long getLocalizador() {
		return localizador;
	}
	public void setLocalizador(Long localizador) {
		this.localizador = localizador;
	}
}
