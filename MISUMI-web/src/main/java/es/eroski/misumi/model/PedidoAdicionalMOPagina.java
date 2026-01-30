package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.ui.Page;

public class PedidoAdicionalMOPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<PedidoAdicionalMO> datos;
    private List<CamposSeleccionadosMO> listadoSeleccionados;
    private String esTecnico = "";
    private String esModificable = "";
    private String fechaMinima = "";
    private String fechaFin = "";
    private Long contadorMontajeOferta;
    private List<String> listadoComboOfertaPeriodo;
    private List<String> listadoComboEspacioPromo;
    private String defaultDescPeriodo = "";
    private String defaultEspacioPromo = "";
    
    //Campos para control de errores
   	private Long codError;
   	private String descError;
	
	public PedidoAdicionalMOPagina() {
		super();
	}

	public PedidoAdicionalMOPagina(Page<PedidoAdicionalMO> datos, List<CamposSeleccionadosMO> listadoSeleccionados, String esTecnico,
			String esModificable, String fechaMinima, String fechaFin, Long contadorMontajeOferta, List<String> listadoComboOfertaPeriodo,
			List<String> listadoComboEspacioPromo, String defaultDescPeriodo, String defaultEspacioPromo) {
		super();
		this.datos = datos;
		this.listadoSeleccionados = listadoSeleccionados;
		this.esTecnico = esTecnico;
		this.esModificable = esModificable;
		this.fechaMinima = fechaMinima;
		this.fechaFin = fechaFin;
		this.contadorMontajeOferta = contadorMontajeOferta;
		this.codError = codError;
		this.descError = descError;
		this.listadoComboOfertaPeriodo = listadoComboOfertaPeriodo;
		this.listadoComboEspacioPromo = listadoComboEspacioPromo;
		this.defaultDescPeriodo = defaultDescPeriodo;
		this.defaultEspacioPromo = defaultEspacioPromo;
	}

	public Page<PedidoAdicionalMO> getDatos() {
		return this.datos;
	}

	public void setDatos(Page<PedidoAdicionalMO> datos) {
		this.datos = datos;
	}

	public List<CamposSeleccionadosMO> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(List<CamposSeleccionadosMO> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}
	
	public List<String> getListadoComboOfertaPeriodo() {
		return listadoComboOfertaPeriodo;
	}

	public void setListadoComboOfertaPeriodo(List<String> listadoComboOfertaPeriodo) {
		this.listadoComboOfertaPeriodo = listadoComboOfertaPeriodo;
	}
	
	public List<String> getListadoComboEspacioPromo() {
		return listadoComboEspacioPromo;
	}

	public void setListadoComboEspacioPromo(List<String> listadoComboEspacioPromo) {
		this.listadoComboEspacioPromo = listadoComboEspacioPromo;
	}
	
	public String getEsTecnico() {
		return this.esTecnico;
	}

	public void setEsTecnico(String esTecnico) {
		this.esTecnico = esTecnico;
	}
	
	public String getEsModificable() {
		return this.esModificable;
	}

	public void setEsModificable(String esModificable) {
		this.esModificable = esModificable;
	}
	
	public String getFechaMinima() {
		return this.fechaMinima;
	}

	public void setFechaMinima(String fechaMinima) {
		this.fechaMinima = fechaMinima;
	}
	
	public String getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getContadorMontajeOferta() {
		return this.contadorMontajeOferta;
	}

	public void setContadorMontajeOferta(Long contadorMontajeOferta) {
		this.contadorMontajeOferta = contadorMontajeOferta;
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
	
	public String getDefaultDescPeriodo() {
		return this.defaultDescPeriodo;
	}

	public void setDefaultDescPeriodo(String defaultDescPeriodo) {
		this.defaultDescPeriodo = defaultDescPeriodo;
	}
	
	public String getDefaultEspacioPromo() {
		return this.defaultEspacioPromo;
	}

	public void setDefaultEspacioPromo(String defaultEspacioPromo) {
		this.defaultEspacioPromo = defaultEspacioPromo;
	}

}