package es.eroski.misumi.model;

import java.io.Serializable;

import es.eroski.misumi.model.ui.Page;

public class SfmCapacidadFacingPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<VArtSfm> datos;
    private Long estado;
	private String descEstado;
	private String flgCapacidad;
	private String flgFacing;
	private String pedir;
	private Long sumatorio;
	
	private VAgruComerParamSfmcap estructuraArticulo;
	
	public SfmCapacidadFacingPagina() {
		super();
	}

	public SfmCapacidadFacingPagina(Page<VArtSfm> datos, Long estado,
			String descEstado) {
		super();
		this.datos = datos;
		this.estado = estado;
		this.descEstado = descEstado;
	}

	public Page<VArtSfm> getDatos() {
		return this.datos;
	}

	public void setDatos(Page<VArtSfm> datos) {
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
	
	public String getPedir() {
		return this.pedir;
	}

	public void setPedir(String pedir) {
		this.pedir = pedir;
	}

	public Long getSumatorio() {
		return sumatorio;
	}

	public void setSumatorio(Long sumatorio) {
		this.sumatorio = sumatorio;
	}

	public VAgruComerParamSfmcap getEstructuraArticulo() {
		return estructuraArticulo;
	}

	public void setEstructuraArticulo(VAgruComerParamSfmcap estructuraArticulo) {
		this.estructuraArticulo = estructuraArticulo;
	}

}