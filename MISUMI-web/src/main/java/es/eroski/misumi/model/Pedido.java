package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Articulo articulo;
    private List<PedidoBasicInfo> basicInfo;
 
    public Pedido() {
		super();
	}

	public Pedido(Articulo articulo , List<PedidoBasicInfo> basicInfo) {
		super();
	    this.articulo=articulo;
	    this.basicInfo=basicInfo;
	}

	public Articulo getArticulo() {
		return this.articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public List<PedidoBasicInfo> getBasicInfo() {
		return this.basicInfo;
	}

	public void setBasicInfo(List<PedidoBasicInfo> basicInfo) {
		this.basicInfo = basicInfo;
	}
}