package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.ui.Page;

public class PedidoAdicionalVCPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<PedidoAdicionalVC> datos;
    private List<String> listadoComboDescripciones;
    private Long contadorValidarCantExtra;
    private Long contadorErroneos;
    
   	//Campo para el valor por defecto combo Validar Cantidad Extra
   	private String defaultDescription;
	
	public PedidoAdicionalVCPagina() {
		super();
	}

	public PedidoAdicionalVCPagina(Page<PedidoAdicionalVC> datos,
			List<String> listadoComboDescripciones,
			Long contadorValidarCantExtra) {
		super();
		this.datos = datos;
		this.listadoComboDescripciones = listadoComboDescripciones;
		this.contadorValidarCantExtra = contadorValidarCantExtra;
	}

	public Page<PedidoAdicionalVC> getDatos() {
		return datos;
	}

	public void setDatos(Page<PedidoAdicionalVC> datos) {
		this.datos = datos;
	}

	public List<String> getListadoComboDescripciones() {
		return listadoComboDescripciones;
	}

	public void setListadoComboDescripciones(List<String> listadoComboDescripciones) {
		this.listadoComboDescripciones = listadoComboDescripciones;
	}

	public Long getContadorValidarCantExtra() {
		return contadorValidarCantExtra;
	}

	public void setContadorValidarCantExtra(Long contadorValidarCantExtra) {
		this.contadorValidarCantExtra = contadorValidarCantExtra;
	}

	public Long getContadorErroneos() {
		return contadorErroneos;
	}

	public void setContadorErroneos(Long contadorErroneos) {
		this.contadorErroneos = contadorErroneos;
	}

	public String getDefaultDescription() {
		return defaultDescription;
	}

	public void setDefaultDescription(String defaultDescription) {
		this.defaultDescription = defaultDescription;
	}

	


}