package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DevolucionLineaBulto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long devolucion;
	private Long codArt;
	private List<BultoCantidad> listaBultoCantidad = new ArrayList<BultoCantidad>();
	private Long codError;
	private String hayRefsPdtes;
	    
	public DevolucionLineaBulto() {
	    super();
	}

	public DevolucionLineaBulto(Long devolucion, Long codArt, List<BultoCantidad> listaBultoCantidad) {
		super();
		this.devolucion=devolucion;
		this.codArt=codArt;
		this.listaBultoCantidad = listaBultoCantidad;
	}

	public Long getDevolucion() {
		return devolucion;
	}

	public void setDevolucion(Long devolucion) {
		this.devolucion = devolucion;
	}

	public Long getCodArt() {
		return codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public List<BultoCantidad> getListaBultoCantidad() {
		return listaBultoCantidad;
	}

	public void setListaBultoCantidad(List<BultoCantidad> listaBultoCantidad) {
		this.listaBultoCantidad = listaBultoCantidad;
	}

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getHayRefsPdtes() {
		return hayRefsPdtes;
	}

	public void setHayRefsPdtes(String hayRefsPdtes) {
		this.hayRefsPdtes = hayRefsPdtes;
	}

}