package es.eroski.misumi.model;

import java.util.List;

public class CalendarioDescripcionServicios {
	private Long pCodError;
	private String pDescError;
	private List<CalendarioDescripcionServicio> listCatalogoDescripcion;
	
	public CalendarioDescripcionServicios() {
		super();
	}

	public CalendarioDescripcionServicios(Long pCodError, String pDescError,
			List<CalendarioDescripcionServicio> listCatalogoDescripcion) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.listCatalogoDescripcion = listCatalogoDescripcion;
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
	public List<CalendarioDescripcionServicio> getListCatalogoDescripcion() {
		return listCatalogoDescripcion;
	}
	public void setListCatalogoDescripcion(List<CalendarioDescripcionServicio> listCatalogoDescripcion) {
		this.listCatalogoDescripcion = listCatalogoDescripcion;
	}
}
