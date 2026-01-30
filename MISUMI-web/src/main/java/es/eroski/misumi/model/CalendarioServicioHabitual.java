package es.eroski.misumi.model;

import java.util.List;

public class CalendarioServicioHabitual {
	private String servicioHabitualLunes;
	private String servicioHabitualMartes;
	private String servicioHabitualMiercoles;
	private String servicioHabitualJueves;
	private String servicioHabitualViernes;
	private String servicioHabitualSabado;
	private String servicioHabitualDomingo;
	
	private List<CalendarioServicio> calendarioServicioLst;
	
	
	public CalendarioServicioHabitual() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioServicioHabitual(String servicioHabitualLunes,
			String servicioHabitualMartes, String servicioHabitualMiercoles, String servicioHabitualJueves,
			String servicioHabitualViernes, String servicioHabitualSabado, String servicioHabitualDomingo,
			List<CalendarioServicio> calendarioServicioLst) {
		super();
		this.servicioHabitualLunes = servicioHabitualLunes;
		this.servicioHabitualMartes = servicioHabitualMartes;
		this.servicioHabitualMiercoles = servicioHabitualMiercoles;
		this.servicioHabitualJueves = servicioHabitualJueves;
		this.servicioHabitualViernes = servicioHabitualViernes;
		this.servicioHabitualSabado = servicioHabitualSabado;
		this.servicioHabitualDomingo = servicioHabitualDomingo;
		this.calendarioServicioLst = calendarioServicioLst;
	}
	
	public String getServicioHabitualLunes() {
		return servicioHabitualLunes;
	}

	public void setServicioHabitualLunes(String servicioHabitualLunes) {
		this.servicioHabitualLunes = servicioHabitualLunes;
	}

	public String getServicioHabitualMartes() {
		return servicioHabitualMartes;
	}

	public void setServicioHabitualMartes(String servicioHabitualMartes) {
		this.servicioHabitualMartes = servicioHabitualMartes;
	}

	public String getServicioHabitualMiercoles() {
		return servicioHabitualMiercoles;
	}

	public void setServicioHabitualMiercoles(String servicioHabitualMiercoles) {
		this.servicioHabitualMiercoles = servicioHabitualMiercoles;
	}

	public String getServicioHabitualJueves() {
		return servicioHabitualJueves;
	}

	public void setServicioHabitualJueves(String servicioHabitualJueves) {
		this.servicioHabitualJueves = servicioHabitualJueves;
	}

	public String getServicioHabitualViernes() {
		return servicioHabitualViernes;
	}

	public void setServicioHabitualViernes(String servicioHabitualViernes) {
		this.servicioHabitualViernes = servicioHabitualViernes;
	}

	public String getServicioHabitualSabado() {
		return servicioHabitualSabado;
	}

	public void setServicioHabitualSabado(String servicioHabitualSabado) {
		this.servicioHabitualSabado = servicioHabitualSabado;
	}

	public String getServicioHabitualDomingo() {
		return servicioHabitualDomingo;
	}

	public void setServicioHabitualDomingo(String servicioHabitualDomingo) {
		this.servicioHabitualDomingo = servicioHabitualDomingo;
	}

	public List<CalendarioServicio> getCalendarioServicioLst() {
		return calendarioServicioLst;
	}

	public void setCalendarioServicioLst(List<CalendarioServicio> calendarioServicioLst) {
		this.calendarioServicioLst = calendarioServicioLst;
	}
}
