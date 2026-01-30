package es.eroski.misumi.model;

import java.io.Serializable;

public class ParamCentrosVp implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc;
	private String flgStock;
	private String flgCapacidad;
	private String flgPorcConsumidor;
	private String flgFacingCentro;

	public ParamCentrosVp() {
	    super();
	}

	public ParamCentrosVp(Long codLoc, String flgStock, String flgCapacidad,
			String flgPorcConsumidor, String flgFacingCentro) {
		super();
		this.codLoc = codLoc;
		this.flgStock = flgStock;
		this.flgCapacidad = flgCapacidad;
		this.flgPorcConsumidor = flgPorcConsumidor;
		this.flgFacingCentro = flgFacingCentro;
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public String getFlgStock() {
		return this.flgStock;
	}

	public void setFlgStock(String flgStock) {
		this.flgStock = flgStock;
	}

	public String getFlgCapacidad() {
		return this.flgCapacidad;
	}

	public void setFlgCapacidad(String flgCapacidad) {
		this.flgCapacidad = flgCapacidad;
	}

	public String getFlgPorcConsumidor() {
		return this.flgPorcConsumidor;
	}

	public void setFlgPorcConsumidor(String flgPorcConsumidor) {
		this.flgPorcConsumidor = flgPorcConsumidor;
	}

	public String getFlgFacingCentro() {
		return flgFacingCentro;
	}

	public void setFlgFacingCentro(String flgFacingCentro) {
		this.flgFacingCentro = flgFacingCentro;
	}

}