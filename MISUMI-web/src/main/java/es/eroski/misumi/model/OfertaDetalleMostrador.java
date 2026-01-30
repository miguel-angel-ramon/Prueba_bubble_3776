/**
 * 
 */
package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author BICUGUAL
 *
 */
public class OfertaDetalleMostrador implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String denomOferta;
	private String anioOferta;
	private String numOferta;
	private Date fechaInicio;
	private Date fechaFin;
	private Long referencia;
	private String denominacion;
	private String mecanica;
	private String pvpTarifa;
	private String pvpOferta;
	
	public OfertaDetalleMostrador() {
		super();	
	}
	
	public String getNumOferta() {
		return numOferta;
	}
	public String getAnioOferta() {
		return anioOferta;
	}

	public void setAnioOferta(String anioOferta) {
		this.anioOferta = anioOferta;
	}

	public String getDenomOferta() {
		return denomOferta;
	}

	public void setDenomOferta(String denomOferta) {
		this.denomOferta = denomOferta;
	}

	public void setNumOferta(String numOfertas) {
		this.numOferta = numOfertas;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Long getReferencia() {
		return referencia;
	}
	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public String getMecanica() {
		return mecanica;
	}
	public void setMecanica(String mecanica) {
		this.mecanica = mecanica;
	}
	public String getPvpTarifa() {
		return pvpTarifa;
	}
	public void setPvpTarifa(String pvpTarifa) {
		this.pvpTarifa = pvpTarifa;
	}
	public String getPvpOferta() {
		return pvpOferta;
	}
	public void setPvpOferta(String pvpOferta) {
		this.pvpOferta = pvpOferta;
	}
}
