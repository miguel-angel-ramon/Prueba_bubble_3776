/**
 * 
 */
package es.eroski.misumi.model.calendariovegalsa;

import java.util.List;

/**
 * MISUMI-301
 * Pojo para la representacion de un mes en el calendario Vegalsa
 * @author BICUGUAL
 *
 */
public class MesCalendarioVegalsa {


	public String mes;
	public List<DiaCalendarioVegalsa> lstDias;
	
	public MesCalendarioVegalsa() {
		super();
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<DiaCalendarioVegalsa> getLstDias() {
		return lstDias;
	}

	public void setLstDias(List<DiaCalendarioVegalsa> lstDias) {
		this.lstDias = lstDias;
	}
}
