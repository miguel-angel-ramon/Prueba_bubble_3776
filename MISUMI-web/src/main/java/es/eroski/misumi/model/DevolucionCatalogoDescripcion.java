package es.eroski.misumi.model;

import java.util.List;

//Clase que devuelve una lista de descripciones de las devoluciones
public class DevolucionCatalogoDescripcion {
	
	public Long pCodError;
	public String pDescError;
	public List<DevolucionDescripcion> listCatalogoDescripcion;
			
	public DevolucionCatalogoDescripcion(Long pCodError, String pDescError, List<DevolucionDescripcion> listCatalogoDescripcion) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.listCatalogoDescripcion = listCatalogoDescripcion;
	}
	
	public DevolucionCatalogoDescripcion() {
		super();
		// TODO Auto-generated constructor stub
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
	public List<DevolucionDescripcion> getListCatalogoDescripcion() {
		return listCatalogoDescripcion;
	}
	public void setListCatalogoDescripcion(List<DevolucionDescripcion> listCatalogoDescripcion) {
		this.listCatalogoDescripcion = listCatalogoDescripcion;
	}		
}
