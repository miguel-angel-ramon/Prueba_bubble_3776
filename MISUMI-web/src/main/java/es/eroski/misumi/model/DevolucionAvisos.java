package es.eroski.misumi.model;

import java.io.Serializable;

public class DevolucionAvisos implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private String flgDevoluciones;
	private String flgFrescos;
	private String flgAli;
	private String flgNoAli;
	
	private String flgUrgenteFrescos;
	private String flgUrgenteAli;
	private String flgUrgenteNoAli;
	
	private Long pCodError;
	private String pDescError;

	public DevolucionAvisos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DevolucionAvisos(Long codCentro, String flgDevoluciones, String flgFrescos, String flgAli, String flgNoAli,
			Long pCodError, String pDescError) {
		super();
		this.codCentro = codCentro;
		this.flgDevoluciones = flgDevoluciones;
		this.flgFrescos = flgFrescos;
		this.flgAli = flgAli;
		this.flgNoAli = flgNoAli;
		this.pCodError = pCodError;
		this.pDescError = pDescError;
	}

	public DevolucionAvisos(Long codCentro, String flgDevoluciones, String flgFrescos, String flgAli, String flgNoAli,
			String flgUrgenteFrescos, String flgUrgenteAli, String flgUrgenteNoAli, Long pCodError, String pDescError) {
		super();
		this.codCentro = codCentro;
		this.flgDevoluciones = flgDevoluciones;
		this.flgFrescos = flgFrescos;
		this.flgAli = flgAli;
		this.flgNoAli = flgNoAli;
		this.flgUrgenteFrescos = flgUrgenteFrescos;
		this.flgUrgenteAli = flgUrgenteAli;
		this.flgUrgenteNoAli = flgUrgenteNoAli;
		this.pCodError = pCodError;
		this.pDescError = pDescError;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getFlgDevoluciones() {
		return flgDevoluciones;
	}

	public void setFlgDevoluciones(String flgDevoluciones) {
		this.flgDevoluciones = flgDevoluciones;
	}

	public String getFlgFrescos() {
		return flgFrescos;
	}

	public void setFlgFrescos(String flgFrescos) {
		this.flgFrescos = flgFrescos;
	}

	public String getFlgAli() {
		return flgAli;
	}

	public void setFlgAli(String flgAli) {
		this.flgAli = flgAli;
	}

	public String getFlgNoAli() {
		return flgNoAli;
	}

	public void setFlgNoAli(String flgNoAli) {
		this.flgNoAli = flgNoAli;
	}

	public Long getpCodError() {
		return pCodError;
	}

	public void setpCodError(Long pCodError) {
		this.pCodError = pCodError;
	}

	public String getpDescError() {
		return pDescError;
	}

	public void setpDescError(String pDescError) {
		this.pDescError = pDescError;
	}

	public String getFlgUrgenteFrescos() {
		return flgUrgenteFrescos;
	}

	public void setFlgUrgenteFrescos(String flgUrgenteFrescos) {
		this.flgUrgenteFrescos = flgUrgenteFrescos;
	}

	public String getFlgUrgenteAli() {
		return flgUrgenteAli;
	}

	public void setFlgUrgenteAli(String flgUrgenteAli) {
		this.flgUrgenteAli = flgUrgenteAli;
	}

	public String getFlgUrgenteNoAli() {
		return flgUrgenteNoAli;
	}

	public void setFlgUrgenteNoAli(String flgUrgenteNoAli) {
		this.flgUrgenteNoAli = flgUrgenteNoAli;
	}
}
