package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

//Esta clase contiene una lista con objetos 4 objetos (uno por cada estado) que contienen una lista de devoluciones.
public class DevolucionCatalogoEstado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Long pCodError;
	public String pDescError;
	public List<DevolucionEstado> listDevolucionEstado;
	
	public DevolucionCatalogoEstado(Long pCodError, String pDescError, List<DevolucionEstado> listDevolucionEstado) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.listDevolucionEstado = listDevolucionEstado;
	}

	public DevolucionCatalogoEstado() {
		super();
	}

	public Long getpCodError() {
		return pCodError;
	}

	public void setpCodError(Long pCodError) {
		this.pCodError = pCodError;
	}

	public String getpDescError() {
		return pDescError;
	}

	public void setpDescError(String pDescError) {
		this.pDescError = pDescError;
	}

	public List<DevolucionEstado> getListDevolucionEstado() {
		return listDevolucionEstado;
	}

	public void setListDevolucionEstado(List<DevolucionEstado> listDevolucionEstado) {
		this.listDevolucionEstado = listDevolucionEstado;
	}
}
