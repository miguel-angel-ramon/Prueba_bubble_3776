package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.ui.Page;

public class NuevoPedidoOfertaLista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<NuevoPedidoOferta> datos;
    private Long estado;
	private String descEstado;
	private String flgTipoListado;
	
	public NuevoPedidoOfertaLista() {
		super();
	}
	
	public NuevoPedidoOfertaLista(List<NuevoPedidoOferta> datos, Long estado,
			String descEstado, String flgTipoListado) {
		super();
		this.datos = datos;
		this.estado = estado;
		this.descEstado = descEstado;
		this.flgTipoListado = flgTipoListado;
	}
	
	public List<NuevoPedidoOferta> getDatos() {
		return this.datos;
	}
	
	public void setDatos(List<NuevoPedidoOferta> datos) {
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
	
	public void setFlgFrescoPuro(String flgTipoListado) {
		this.flgTipoListado = flgTipoListado;
	}    
}