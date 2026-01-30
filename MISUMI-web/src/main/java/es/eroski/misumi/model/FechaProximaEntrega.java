package es.eroski.misumi.model;

import java.util.Date;

public class FechaProximaEntrega {
	private Date fechaTransmision;
	private Date fechaTransporte;
	private Date fechaVenta;
	
	public FechaProximaEntrega(Date fechaTransmision, Date fechaTransporte, Date fechaVenta) {
		super();
		this.fechaTransmision = fechaTransmision;
		this.fechaTransporte = fechaTransporte;
		this.fechaVenta = fechaVenta;
	}
	
	public Date getFechaTransmision() {
		return fechaTransmision;
	}
	public void setFechaTransmision(Date fechaTransmision) {
		this.fechaTransmision = fechaTransmision;
	}
	public Date getFechaTransporte() {
		return fechaTransporte;
	}
	public void setFechaTransporte(Date fechaTransporte) {
		this.fechaTransporte = fechaTransporte;
	}
	public Date getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
}
