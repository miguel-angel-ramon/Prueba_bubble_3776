package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

//Contiene la información de una Devolución y una lista que contiene la información de las líneas de la tabla 
//relacionada con esa devolución
public class Proveedor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String descripcion;
	private String finalizado;
//	private String mostrarFinDev;
	private List<BultoCantidad> listaBultos;

	public Proveedor(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(String finalizado) {
		this.finalizado = finalizado;
	}

	public List<BultoCantidad> getListaBultos() {
		return listaBultos;
	}

	public void setListaBultos(List<BultoCantidad> listaBultos) {
		this.listaBultos = listaBultos;
	}

//	public String getMostrarFinDev() {
//		return mostrarFinDev;
//	}
//
//	public void setMostrarFinDev(String mostrarFinDev) {
//		this.mostrarFinDev = mostrarFinDev;
//	}

}