package es.eroski.misumi.model;

import java.util.Date;
import java.util.List;

public class FechaEntregaDiasServicio {
	private Long pCodError;
	private String pDescError;
	private List<Date> listFechasServicio;
	
	public FechaEntregaDiasServicio() {
		super();
	}

	public FechaEntregaDiasServicio(Long pCodError, String pDescError,
			List<Date> listFechasServicio) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.listFechasServicio = listFechasServicio;
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
	public List<Date> getListFechasServicio() {
		return listFechasServicio;
	}
	public void setListFechasServicio(List<Date> listFechasServicio) {
		this.listFechasServicio = listFechasServicio;
	}
}
