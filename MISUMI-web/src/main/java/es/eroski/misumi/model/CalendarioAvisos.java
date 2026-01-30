package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class CalendarioAvisos implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private String flgPendValidarEjer;
	private Long anoEjerPendValid;
	private Date fechaLimiteValid;
	private String flgModifCalendarioCentro;
	private String avisoKoPlataforma;
	private Long pCodError;
	private String pDescError;

	public CalendarioAvisos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioAvisos(Long codCentro, String flgPendValidarEjer, Long anoEjerPendValid, Date fechaLimiteValid, String flgModifCalendarioCentro, String avisoKoPlataforma,
			Long pCodError, String pDescError) {
		super();
		this.codCentro = codCentro;
		this.flgPendValidarEjer = flgPendValidarEjer;
		this.anoEjerPendValid = anoEjerPendValid;
		this.fechaLimiteValid = fechaLimiteValid;
		this.flgModifCalendarioCentro = flgModifCalendarioCentro;
		this.avisoKoPlataforma = avisoKoPlataforma;
		this.pCodError = pCodError;
		this.pDescError = pDescError;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getFlgPendValidarEjer() {
		return flgPendValidarEjer;
	}

	public void setFlgPendValidarEjer(String flgPendValidarEjer) {
		this.flgPendValidarEjer = flgPendValidarEjer;
	}

	public Long getAnoEjerPendValid() {
		return anoEjerPendValid;
	}

	public void setAnoEjerPendValid(Long anoEjerPendValid) {
		this.anoEjerPendValid = anoEjerPendValid;
	}

	public Date getFechaLimiteValid() {
		return fechaLimiteValid;
	}

	public void setFechaLimiteValid(Date fechaLimiteValid) {
		this.fechaLimiteValid = fechaLimiteValid;
	}

	public String getFlgModifCalendarioCentro() {
		return flgModifCalendarioCentro;
	}

	public void setFlgModifCalendarioCentro(String flgModifCalendarioCentro) {
		this.flgModifCalendarioCentro = flgModifCalendarioCentro;
	}
	
	public String getAvisoKoPlataforma() {
		return avisoKoPlataforma;
	}

	public void setAvisoKoPlataforma(String avisoKoPlataforma) {
		this.avisoKoPlataforma = avisoKoPlataforma;
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

}
