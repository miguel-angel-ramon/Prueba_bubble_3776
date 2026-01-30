package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class DetallePedidoLista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<DetallePedido> datos;
    private Long codError;
	private String descError;
	
	public DetallePedidoLista() {
		super();
	}

	public DetallePedidoLista(List<DetallePedido> datos, Long codError, String descError) {
		super();
		this.datos = datos;
		this.codError = codError;
		this.descError = descError;
	}
	
	public List<DetallePedido> getDatos() {
		return this.datos;
	}
	
	public void setDatos(List<DetallePedido> datos) {
		this.datos = datos;
	}
	
	public Long getCodError() {
		return this.codError;
	}
	
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	
	public String getDescError() {
		return this.descError;
	}
	
	public void setDescError(String descError) {
		this.descError = descError;
	}
}