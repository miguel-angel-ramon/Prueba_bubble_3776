package es.eroski.misumi.model;

import java.io.Serializable;

import es.eroski.misumi.model.ui.Page;

public class MotivosBloqueos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<VBloqueoEncargosPiladas> motivos;
    private Page<TPedidoAdicional> pedidos;
	
    public MotivosBloqueos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MotivosBloqueos(Page<VBloqueoEncargosPiladas> motivos,
			Page<TPedidoAdicional> pedidos) {
		super();
		this.motivos = motivos;
		this.pedidos = pedidos;
	}

	public Page<VBloqueoEncargosPiladas> getMotivos() {
		return this.motivos;
	}

	public void setMotivos(Page<VBloqueoEncargosPiladas> motivos) {
		this.motivos = motivos;
	}

	public Page<TPedidoAdicional> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(Page<TPedidoAdicional> pedidos) {
		this.pedidos = pedidos;
	}
}