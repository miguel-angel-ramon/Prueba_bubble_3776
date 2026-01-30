package es.eroski.misumi.model;

import java.io.Serializable;

import es.eroski.misumi.model.ui.Page;

public class NuevoPedidoOfertaPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<NuevoPedidoOferta> datos;
    private Long estado;
	private String descEstado;
	private String flgTipoListado;
	
	//Control de registros erróneos al guardar
	private Long totalErroneos;
	
	public NuevoPedidoOfertaPagina() {
		super();
	}
	
	public NuevoPedidoOfertaPagina(Page<NuevoPedidoOferta> datos, Long estado,
			String descEstado, String flgTipoListado) {
		super();
		this.datos = datos;
		this.estado = estado;
		this.descEstado = descEstado;
		this.flgTipoListado = flgTipoListado;
	}
	
	public Page<NuevoPedidoOferta> getDatos() {
		return this.datos;
	}
	
	public void setDatos(Page<NuevoPedidoOferta> datos) {
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
	
	public String getFlgTipoListado() {
		return this.flgTipoListado;
	}
	
	public void setFlgTipoListado(String flgTipoListado) {
		this.flgTipoListado = flgTipoListado;
	}
	
	public Long getTotalErroneos() {
		return this.totalErroneos;
	}
	
	public void setTotalErroneos(Long totalErroneos) {
		this.totalErroneos = totalErroneos;
	}

}