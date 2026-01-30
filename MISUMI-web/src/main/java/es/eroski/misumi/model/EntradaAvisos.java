package es.eroski.misumi.model;

public class EntradaAvisos {
	private String flgPendienteEntrada;
	private String flgFrescos;
	private String flgAlimentacion;
	private String flgNoAlimentacion;
	
	private Long codError;
	private String descError;
	
	public EntradaAvisos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntradaAvisos(String flgPendienteEntrada, String flgFrescos, String flgAlimentacion,
			String flgNoAlimentacion, Long codError, String descError) {
		super();
		this.flgPendienteEntrada = flgPendienteEntrada;
		this.flgFrescos = flgFrescos;
		this.flgAlimentacion = flgAlimentacion;
		this.flgNoAlimentacion = flgNoAlimentacion;
		this.codError = codError;
		this.descError = descError;
	}
	
	public String getFlgPendienteEntrada() {
		return flgPendienteEntrada;
	}
	public void setFlgPendienteEntrada(String flgPendienteEntrada) {
		this.flgPendienteEntrada = flgPendienteEntrada;
	}
	public String getFlgFrescos() {
		return flgFrescos;
	}
	public void setFlgFrescos(String flgFrescos) {
		this.flgFrescos = flgFrescos;
	}
	public String getFlgAlimentacion() {
		return flgAlimentacion;
	}
	public void setFlgAlimentacion(String flgAlimentacion) {
		this.flgAlimentacion = flgAlimentacion;
	}
	public String getFlgNoAlimentacion() {
		return flgNoAlimentacion;
	}
	public void setFlgNoAlimentacion(String flgNoAlimentacion) {
		this.flgNoAlimentacion = flgNoAlimentacion;
	}
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
}
