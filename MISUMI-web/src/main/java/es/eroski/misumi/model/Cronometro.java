package es.eroski.misumi.model;

public class Cronometro {

	private String horaLimite;
	private String numHorasLimite;
	private String cuenta;
	
	public Cronometro(String horaLimite, String numHorasLimite) {
		super();
		this.horaLimite = horaLimite;
		this.numHorasLimite = numHorasLimite;
	}
	
	public Cronometro(String horaLimite, String numHorasLimite, String cuenta) {
		super();
		this.horaLimite = horaLimite;
		this.numHorasLimite = numHorasLimite;
		this.cuenta = cuenta;
	}
	
	public String getHoraLimite() {
		return horaLimite;
	}
	
	public void setHoraLimite(String horaLimite) {
		this.horaLimite = horaLimite;
	}
	
	public String getNumHorasLimite() {
		return numHorasLimite;
	}
	
	public void setNumHorasLimite(String numHorasLimite) {
		this.numHorasLimite = numHorasLimite;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}	
}
