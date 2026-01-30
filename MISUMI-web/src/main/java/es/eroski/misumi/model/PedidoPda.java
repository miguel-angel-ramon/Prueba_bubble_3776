package es.eroski.misumi.model;

import java.io.Serializable;

public class PedidoPda implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private boolean mostrasLinkLanzarEncargo;
	private PedidoAdicionalE pedidoAdicionalE;
	private PedidoAdicionalCompleto pedidoAdicionalCompleto;
	
	//MISUMI-278
	private String msgGamaLibre;
	
	public PedidoPda() {
		super();
	}
	public PedidoPda(boolean mostrasLinkLanzarEncargo, PedidoAdicionalE pedidoAdicionalE) {
		super();
		this.mostrasLinkLanzarEncargo = mostrasLinkLanzarEncargo;
		this.pedidoAdicionalE = pedidoAdicionalE;
	}	
	public PedidoPda(boolean mostrasLinkLanzarEncargo, PedidoAdicionalCompleto pedidoAdicionalCompleto) {
		super();
		this.mostrasLinkLanzarEncargo = mostrasLinkLanzarEncargo;
		this.pedidoAdicionalCompleto = pedidoAdicionalCompleto;
	}
	
	public boolean isMostrasLinkLanzarEncargo() {
		return mostrasLinkLanzarEncargo;
	}
	public void setMostrasLinkLanzarEncargo(boolean mostrasLinkLanzarEncargo) {
		this.mostrasLinkLanzarEncargo = mostrasLinkLanzarEncargo;
	}
	public PedidoAdicionalE getPedidoAdicionalE() {
		return pedidoAdicionalE;
	}
	public void setPedidoAdicionalE(PedidoAdicionalE pedidoAdicionalE) {
		this.pedidoAdicionalE = pedidoAdicionalE;
	}
	public PedidoAdicionalCompleto getPedidoAdicionalCompleto() {
		return pedidoAdicionalCompleto;
	}
	public void setPedidoAdicionalCompleto(PedidoAdicionalCompleto pedidoAdicionalCompleto) {
		this.pedidoAdicionalCompleto = pedidoAdicionalCompleto;
	}	

	public String getMsgGamaLibre() {
		return msgGamaLibre;
	}



	public void setMsgGamaLibre(String msgGamaLibre) {
		this.msgGamaLibre = msgGamaLibre;
	}	
}
