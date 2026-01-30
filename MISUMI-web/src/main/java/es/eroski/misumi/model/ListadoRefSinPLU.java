package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class ListadoRefSinPLU implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<ReferenciasSinPLU> listadoSeleccionados;
	
	public ListadoRefSinPLU() {
	    super();
	}

	public ListadoRefSinPLU(List<ReferenciasSinPLU> listadoSeleccionados) {
		super();
		this.listadoSeleccionados = listadoSeleccionados;
	}

	public List<ReferenciasSinPLU> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(
			List<ReferenciasSinPLU> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}
	
}