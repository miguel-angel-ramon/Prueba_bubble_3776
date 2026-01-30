package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class DetalleMostradorLista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<DetalleMostrador> datos;
    private Long codError;
	private String descError;
	
	public DetalleMostradorLista() {
		super();
	}

	public DetalleMostradorLista(List<DetalleMostrador> datos, Long codError, String descError) {
		super();
		this.datos = datos;
		this.codError = codError;
		this.descError = descError;
	}
	
	public List<DetalleMostrador> getDatos() {
		return this.datos;
	}
	
	public void setDatos(List<DetalleMostrador> datos) {
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