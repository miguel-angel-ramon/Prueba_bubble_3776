package es.eroski.misumi.model;

import java.util.List;

public class CalendarioAnual {
	private List<CalendarioTotal> lstMesAnual;
	
	public CalendarioAnual() {
		super();
	}

	public CalendarioAnual(List<CalendarioTotal> lstMesAnual) {
		super();
		this.lstMesAnual = lstMesAnual;
	}

	public List<CalendarioTotal> getLstMesAnual() {
		return lstMesAnual;
	}

	public void setLstMesAnual(List<CalendarioTotal> lstMesAnual) {
		this.lstMesAnual = lstMesAnual;
	}
}
