package es.eroski.misumi.model;

import java.util.List;

public class CalendarioValidacionTratado {
	private Long codigoServicio;
	private String denominacionServicio;

	private List<CalendarioServicioHabitual> calServHabLst;
	
	//Flag que indica si existe cambio en alguno de los servicios temporales de los servicios habituales para este código de servicio.
	private Boolean existeCambio;
	
	public CalendarioValidacionTratado() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioValidacionTratado(Long codigoServicio, String denominacionServicio,
			List<CalendarioServicioHabitual> calServHabLst) {
		super();
		this.codigoServicio = codigoServicio;
		this.denominacionServicio = denominacionServicio;
		this.calServHabLst = calServHabLst;
	}

	public Long getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(Long codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	public String getDenominacionServicio() {
		return denominacionServicio;
	}

	public void setDenominacionServicio(String denominacionServicio) {
		this.denominacionServicio = denominacionServicio;
	}

	public List<CalendarioServicioHabitual> getCalServHabLst() {
		return calServHabLst;
	}

	public void setCalServHabLst(List<CalendarioServicioHabitual> calServHabLst) {
		this.calServHabLst = calServHabLst;
	}

	public Boolean getExisteCambio() {
		return existeCambio;
	}

	public void setExisteCambio(Boolean existeCambio) {
		this.existeCambio = existeCambio;
	}
}
