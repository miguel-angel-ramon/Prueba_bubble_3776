package es.eroski.misumi.model;

public class DevolucionPlataforma {
	private Long  centro;
	private Long codArticulo;
	private Long codAbono;
	private Long codRecogida;
	private String descripPlatAbono;
	private String descripPlatRecogida; 
	private String marca;
	private Long codError;
	private String msgError;
	
	
	public DevolucionPlataforma() {
		super();
	}

	public DevolucionPlataforma(Long centro, Long codArticulo, Long codAbono, Long codRecogida, Long codError,
			String msgError) {
		super();
		this.centro = centro;
		this.codArticulo = codArticulo;
		this.codAbono = codAbono;
		this.codRecogida = codRecogida;
		this.codError = codError;
		this.msgError = msgError;
	}
	
	public DevolucionPlataforma(Long centro, Long codArticulo, Long codAbono, Long codRecogida, String descripPlatAbono,
			String descripPlatRecogida, String marca, Long codError, String msgError) {
		super();
		this.centro = centro;
		this.codArticulo = codArticulo;
		this.codAbono = codAbono;
		this.codRecogida = codRecogida;
		this.descripPlatAbono = descripPlatAbono;
		this.descripPlatRecogida = descripPlatRecogida;
		this.marca = marca;
		this.codError = codError;
		this.msgError = msgError;
	}

	public Long getCentro() {
		return centro;
	}
	public void setCentro(Long centro) {
		this.centro = centro;
	}
	public Long getCodArticulo() {
		return codArticulo;
	}
	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	public Long getCodAbono() {
		return codAbono;
	}
	public void setCodAbono(Long codAbono) {
		this.codAbono = codAbono;
	}
	public Long getCodRecogida() {
		return codRecogida;
	}
	public void setCodRecogida(Long codRecogida) {
		this.codRecogida = codRecogida;
	}
	public String getDescripPlatAbono() {
		return descripPlatAbono;
	}

	public void setDescripPlatAbono(String descripPlatAbono) {
		this.descripPlatAbono = descripPlatAbono;
	}

	public String getDescripPlatRecogida() {
		return descripPlatRecogida;
	}

	public void setDescripPlatRecogida(String descripPlatRecogida) {
		this.descripPlatRecogida = descripPlatRecogida;
	}
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getMsgError() {
		return msgError;
	}
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
}
