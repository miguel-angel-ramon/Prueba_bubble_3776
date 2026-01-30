package es.eroski.misumi.model;

import java.util.Date;

public class CalendarioServicio {
	private String servicioEstacionalLunes;
	private String servicioEstacionalMartes;
	private String servicioEstacionalMiercoles;
	private String servicioEstacionalJueves;
	private String servicioEstacionalViernes;
	private String servicioEstacionalSabado;
	private String servicioEstacionalDomingo;
	private Date fechaEstacionalInicio;
	private Date fechaEstacionalFin;
	
	//Fechas inicio y fin introducidas por el usuario
	private Date fechaEstacionalInicioCentro;
	private Date fechaEstacionalFinCentro;
	
	//MISUMI-81. Flag no lo quiero que sirve para que los CAMBIOS ESTACIONALES se pueden anular. Si es 'S'
	//se quieren anular, si no es 'N'. Por defecto es N.
	private String noLoQuiero;
	
	//No lo quiero introducido por el usuario.
	private String noLoQuieroCentro;
	
	//MISUMI-273 - MISUMI-JAVA CALENDARIO Control en Cambios estacionales
	private Long plazoMaxDuracionTotal;
	private Long plazoMaxAdelantarAtrasar;
	
	public CalendarioServicio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalendarioServicio(String servicioEstacionalLunes, String servicioEstacionalMartes,
			String servicioEstacionalMiercoles, String servicioEstacionalJueves, String servicioEstacionalViernes,
			String servicioEstacionalSabado, String servicioEstacionalDomingo, Date fechaEstacionalInicio,
			Date fechaEstacionalFin) {
		super();
		this.servicioEstacionalLunes = servicioEstacionalLunes;
		this.servicioEstacionalMartes = servicioEstacionalMartes;
		this.servicioEstacionalMiercoles = servicioEstacionalMiercoles;
		this.servicioEstacionalJueves = servicioEstacionalJueves;
		this.servicioEstacionalViernes = servicioEstacionalViernes;
		this.servicioEstacionalSabado = servicioEstacionalSabado;
		this.servicioEstacionalDomingo = servicioEstacionalDomingo;
		this.fechaEstacionalInicio = fechaEstacionalInicio;
		this.fechaEstacionalFin = fechaEstacionalFin;
	}
	
	public CalendarioServicio(String servicioEstacionalLunes, String servicioEstacionalMartes,
			String servicioEstacionalMiercoles, String servicioEstacionalJueves, String servicioEstacionalViernes,
			String servicioEstacionalSabado, String servicioEstacionalDomingo, Date fechaEstacionalInicio,
			Date fechaEstacionalFin, Date fechaEstacionalInicioCentro, Date fechaEstacionalFinCentro) {
		super();
		this.servicioEstacionalLunes = servicioEstacionalLunes;
		this.servicioEstacionalMartes = servicioEstacionalMartes;
		this.servicioEstacionalMiercoles = servicioEstacionalMiercoles;
		this.servicioEstacionalJueves = servicioEstacionalJueves;
		this.servicioEstacionalViernes = servicioEstacionalViernes;
		this.servicioEstacionalSabado = servicioEstacionalSabado;
		this.servicioEstacionalDomingo = servicioEstacionalDomingo;
		this.fechaEstacionalInicio = fechaEstacionalInicio;
		this.fechaEstacionalFin = fechaEstacionalFin;
		this.fechaEstacionalInicioCentro = fechaEstacionalInicioCentro;
		this.fechaEstacionalFinCentro = fechaEstacionalFinCentro;
	}
	
	public CalendarioServicio(String servicioEstacionalLunes, String servicioEstacionalMartes,
			String servicioEstacionalMiercoles, String servicioEstacionalJueves, String servicioEstacionalViernes,
			String servicioEstacionalSabado, String servicioEstacionalDomingo, Date fechaEstacionalInicio,
			Date fechaEstacionalFin, Date fechaEstacionalInicioCentro, Date fechaEstacionalFinCentro,String noLoQuiero,String noLoQuieroCentro, Long plazoMaxDuracionTotal, Long plazoMaxAdelantarAtrasar) {
		super();
		this.servicioEstacionalLunes = servicioEstacionalLunes;
		this.servicioEstacionalMartes = servicioEstacionalMartes;
		this.servicioEstacionalMiercoles = servicioEstacionalMiercoles;
		this.servicioEstacionalJueves = servicioEstacionalJueves;
		this.servicioEstacionalViernes = servicioEstacionalViernes;
		this.servicioEstacionalSabado = servicioEstacionalSabado;
		this.servicioEstacionalDomingo = servicioEstacionalDomingo;
		this.fechaEstacionalInicio = fechaEstacionalInicio;
		this.fechaEstacionalFin = fechaEstacionalFin;
		this.fechaEstacionalInicioCentro = fechaEstacionalInicioCentro;
		this.fechaEstacionalFinCentro = fechaEstacionalFinCentro;
		this.noLoQuiero = noLoQuiero;
		this.noLoQuieroCentro = noLoQuieroCentro;
		this.plazoMaxDuracionTotal = plazoMaxDuracionTotal;
		this.plazoMaxAdelantarAtrasar = plazoMaxAdelantarAtrasar;
	}

	public String getServicioEstacionalLunes() {
		return servicioEstacionalLunes;
	}
	public void setServicioEstacionalLunes(String servicioEstacionalLunes) {
		this.servicioEstacionalLunes = servicioEstacionalLunes;
	}
	public String getServicioEstacionalMartes() {
		return servicioEstacionalMartes;
	}
	public void setServicioEstacionalMartes(String servicioEstacionalMartes) {
		this.servicioEstacionalMartes = servicioEstacionalMartes;
	}
	public String getServicioEstacionalMiercoles() {
		return servicioEstacionalMiercoles;
	}
	public void setServicioEstacionalMiercoles(String servicioEstacionalMiercoles) {
		this.servicioEstacionalMiercoles = servicioEstacionalMiercoles;
	}
	public String getServicioEstacionalJueves() {
		return servicioEstacionalJueves;
	}
	public void setServicioEstacionalJueves(String servicioEstacionalJueves) {
		this.servicioEstacionalJueves = servicioEstacionalJueves;
	}
	public String getServicioEstacionalViernes() {
		return servicioEstacionalViernes;
	}
	public void setServicioEstacionalViernes(String servicioEstacionalViernes) {
		this.servicioEstacionalViernes = servicioEstacionalViernes;
	}
	public String getServicioEstacionalSabado() {
		return servicioEstacionalSabado;
	}
	public void setServicioEstacionalSabado(String servicioEstacionalSabado) {
		this.servicioEstacionalSabado = servicioEstacionalSabado;
	}
	public String getServicioEstacionalDomingo() {
		return servicioEstacionalDomingo;
	}
	public void setServicioEstacionalDomingo(String servicioEstacionalDomingo) {
		this.servicioEstacionalDomingo = servicioEstacionalDomingo;
	}
	public Date getFechaEstacionalInicio() {
		return fechaEstacionalInicio;
	}
	public void setFechaEstacionalInicio(Date fechaEstacionalInicio) {
		this.fechaEstacionalInicio = fechaEstacionalInicio;
	}
	public Date getFechaEstacionalFin() {
		return fechaEstacionalFin;
	}
	public void setFechaEstacionalFin(Date fechaEstacionalFin) {
		this.fechaEstacionalFin = fechaEstacionalFin;
	}
	public Date getFechaEstacionalInicioCentro() {
		return fechaEstacionalInicioCentro;
	}
	public void setFechaEstacionalInicioCentro(Date fechaEstacionalInicioCentro) {
		this.fechaEstacionalInicioCentro = fechaEstacionalInicioCentro;
	}
	public Date getFechaEstacionalFinCentro() {
		return fechaEstacionalFinCentro;
	}
	public void setFechaEstacionalFinCentro(Date fechaEstacionalFinCentro) {
		this.fechaEstacionalFinCentro = fechaEstacionalFinCentro;
	}
	public String getNoLoQuiero() {
		return noLoQuiero;
	}
	public void setNoLoQuiero(String noLoQuiero) {
		this.noLoQuiero = noLoQuiero;
	}

	public String getNoLoQuieroCentro() {
		return noLoQuieroCentro;
	}

	public void setNoLoQuieroCentro(String noLoQuieroCentro) {
		this.noLoQuieroCentro = noLoQuieroCentro;
	}

	public Long getPlazoMaxDuracionTotal() {
		return plazoMaxDuracionTotal;
	}

	public void setPlazoMaxDuracionTotal(Long plazoMaxDuracionTotal) {
		this.plazoMaxDuracionTotal = plazoMaxDuracionTotal;
	}

	public Long getPlazoMaxAdelantarAtrasar() {
		return plazoMaxAdelantarAtrasar;
	}

	public void setPlazoMaxAdelantarAtrasar(Long plazoMaxAdelantarAtrasar) {
		this.plazoMaxAdelantarAtrasar = plazoMaxAdelantarAtrasar;
	}
}
