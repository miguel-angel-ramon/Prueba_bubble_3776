package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class InformeListadoPescaForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<String> listaPescaMostrador;
	private String flgHabitual;
	
	public List<String> getListaPescaMostrador() {
		return listaPescaMostrador;
	}

	public void setListaPescaMostrador(List<String> listaPescaMostrador) {
		this.listaPescaMostrador = listaPescaMostrador;
	}

	public String getFlgHabitual() {
		return flgHabitual;
	}

	public void setFlgHabitual(String flgHabitual) {
		this.flgHabitual = flgHabitual;
	}
}
