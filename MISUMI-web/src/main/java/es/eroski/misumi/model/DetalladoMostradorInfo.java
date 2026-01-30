/**
 * 
 */
package es.eroski.misumi.model;

import java.sql.Date;

/**
 * @author BICUGUAL
 *
 */
public class DetalladoMostradorInfo {

	private String fechaEntrega;
	private Boolean avisoVisperaFestivo;
	private String fechaPedido;
	
	public DetalladoMostradorInfo() {
		super();
	}
	
	public DetalladoMostradorInfo(String fechaEntrega, Boolean avisoVisperaFestivo, String fechaPedido) {
		super();
		this.fechaEntrega = fechaEntrega;
		this.avisoVisperaFestivo = avisoVisperaFestivo;
		this.fechaPedido = fechaPedido;
	}

	public Boolean getAvisoVisperaFestivo() {
		return avisoVisperaFestivo;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public String getFechaPedido() {
		return fechaPedido;
	}

	public void setAvisoVisperaFestivo(Boolean avisoVisperaFestivo) {
		this.avisoVisperaFestivo = avisoVisperaFestivo;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
}
