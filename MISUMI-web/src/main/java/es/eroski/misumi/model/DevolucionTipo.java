package es.eroski.misumi.model;

public class DevolucionTipo {
	private Long codTpCa;
    private String denominacion;
    
	public DevolucionTipo() {
		super();
	}

	public DevolucionTipo(Long codTpCa, String denominacion) {
		super();
		this.codTpCa = codTpCa;
		this.denominacion = denominacion;
	}
	
	public Long getCodTpCa() {
		return codTpCa;
	}
	public void setCodTpCa(Long codTpCa) {
		this.codTpCa = codTpCa;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
}
