package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class SfmCapacidadFacing implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<VArtSfm> datos;
    private Long estado;
	private String descEstado;
	private String flgCapacidad;
	private String flgFacing;
	
	public SfmCapacidadFacing() {
		super();
	}

	public SfmCapacidadFacing(List<VArtSfm> datos, Long estado, String descEstado) {
		super();
		this.datos = datos;
		this.estado = estado;
		this.descEstado = descEstado;
	}
	
	public List<VArtSfm> getDatos() {
		return this.datos;
	}
	
	public void setDatos(List<VArtSfm> datos) {
		this.datos = datos;
	}
	
	public Long getEstado() {
		return this.estado;
	}
	
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	public String getDescEstado() {
		return this.descEstado;
	}
	
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}
	
	public String getFlgCapacidad() {
		return this.flgCapacidad;
	}

	public void setFlgCapacidad(String flgCapacidad) {
		this.flgCapacidad = flgCapacidad;
	}
	
	public String getFlgFacing() {
		return this.flgFacing;
	}

	public void setFlgFacing(String flgFacing) {
		this.flgFacing = flgFacing;
	}
}