package es.eroski.misumi.model;

import java.io.Serializable;

import es.eroski.misumi.model.ui.Page;

public class UltimosPedidos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<PedidoBasicInfo> ultimosPedidos;
    private String mensajeSinPedidos;
	
    public UltimosPedidos() {
		super();
	}

	public UltimosPedidos(Page<PedidoBasicInfo> ultimosPedidos,
			String mensajeSinPedidos) {
		super();
		this.ultimosPedidos = ultimosPedidos;
		this.mensajeSinPedidos = mensajeSinPedidos;
	}

	public Page<PedidoBasicInfo> getUltimosPedidos() {
		return this.ultimosPedidos;
	}

	public void setUltimosPedidos(Page<PedidoBasicInfo> ultimosPedidos) {
		this.ultimosPedidos = ultimosPedidos;
	}

	public String getMensajeSinPedidos() {
		return this.mensajeSinPedidos;
	}

	public void setMensajeSinPedidos(String mensajeSinPedidos) {
		this.mensajeSinPedidos = mensajeSinPedidos;
	}

}