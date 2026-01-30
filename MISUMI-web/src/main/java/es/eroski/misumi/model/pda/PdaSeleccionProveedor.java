package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaSeleccionProveedor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long devolucion; 
	private Long codProveedor;

	public PdaSeleccionProveedor() {
	    super();
	}
	
	public Long getCodProveedor() {
		return codProveedor;
	}

	public void setCodProveedor(Long codProveedor) {
		this.codProveedor = codProveedor;
	}

	public Long getDevolucion() {
		return devolucion;
	}

	public void setDevolucion(Long devolucionId) {
		this.devolucion = devolucionId;
	}

}
