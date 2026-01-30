package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;



public class ListaDetallePedidoExcel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List <ObjetoExcel> listaAnterior;
	private List <EstadoCantidadExcel> listaEstadoCantidad;
	private List <ObjetoExcel> listaPosterior;
	private List <MensajeErrorExcel> listaMensajeError;
	
	
	public ListaDetallePedidoExcel() {
		super();

	}
	
	public ListaDetallePedidoExcel(List <ObjetoExcel> listaAnterior, List <EstadoCantidadExcel> listaEstadoCantidad, List <ObjetoExcel> listaPosterior) {
		super();
		this.listaAnterior = listaAnterior;
		this.listaEstadoCantidad = listaEstadoCantidad;
		this.listaPosterior = listaPosterior;
		
	}
	public List<ObjetoExcel> getListaAnterior() {
		return this.listaAnterior;
	}
	public void setListaAnterior(List<ObjetoExcel> listaAnterior) {
		this.listaAnterior = listaAnterior;
	}
	public List<EstadoCantidadExcel> getListaEstadoCantidad() {
		return this.listaEstadoCantidad;
	}
	public void setListaEstadoCantidad(List<EstadoCantidadExcel> listaEstadoCantidad) {
		this.listaEstadoCantidad = listaEstadoCantidad;
	}
	public List<ObjetoExcel> getListaPosterior() {
		return this.listaPosterior;
	}
	public void setListaPosterior(List<ObjetoExcel> listaPosterior) {
		this.listaPosterior = listaPosterior;
	}
	public List<MensajeErrorExcel> getListaMensajeError() {
		return this.listaMensajeError;
	}
	public void setListaMensajeError(List<MensajeErrorExcel> listaMensajeError) {
		this.listaMensajeError = listaMensajeError;
	}

}