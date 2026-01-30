package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.ui.Page;

public class PedidoAdicionalMPagina implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Page<PedidoAdicionalM> datos;
    private List<CamposSeleccionadosM> listadoSeleccionados;
    private List<String> listadoComboOfertaPeriodo;
    private List<String> listadoComboEspacioPromo;
    private String esTecnico = "";
    private String esModificable = "";
    private String fechaMinima = "";
    private String fechaFin = "";
    private Long contadorMontaje;
    
  //Campos para control de errores
  	private Long codError;
  	private String descError;
	
	public PedidoAdicionalMPagina() {
		super();
	}

	public PedidoAdicionalMPagina(Page<PedidoAdicionalM> datos, List<CamposSeleccionadosM> listadoSeleccionados, String esTecnico,
			String esModificable, String fechaMinima, String fechaFin, Long contadorMontaje, Long codError, String descError) {
		super();
		this.datos = datos;
		this.listadoSeleccionados = listadoSeleccionados;
		this.esTecnico = esTecnico;
		this.esModificable = esModificable;
		this.fechaMinima = fechaMinima;
		this.fechaFin = fechaFin;
		this.contadorMontaje = contadorMontaje;
		this.codError = codError;
		this.descError = descError;
	}

	public Page<PedidoAdicionalM> getDatos() {
		return this.datos;
	}

	public void setDatos(Page<PedidoAdicionalM> datos) {
		this.datos = datos;
	}

	public List<CamposSeleccionadosM> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(List<CamposSeleccionadosM> listadoSeleccionados) {
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

	public Long getContadorMontaje() {
		return this.contadorMontaje;
	}

	public void setContadorMontaje(Long contadorMontaje) {
		this.contadorMontaje = contadorMontaje;
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