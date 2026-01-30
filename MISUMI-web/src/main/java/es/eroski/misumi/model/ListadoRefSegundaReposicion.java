package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class ListadoRefSegundaReposicion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long codCentro;
	private Long grupo1;
	private Long grupo2;
	private int mes;
	private List<Long> listadoSeleccionados;
	
	public ListadoRefSegundaReposicion() {
	    super();
	}

	public ListadoRefSegundaReposicion(Long codCentro, 	Long grupo1, Long grupo2, List<Long> listadoSeleccionados, int mes) {
		super();
		this.codCentro = codCentro;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.listadoSeleccionados = listadoSeleccionados;
		this.mes = mes;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getGrupo1() {
		return this.grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public Long getGrupo2() {
		return this.grupo2;
	}

	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public List<Long> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(
			List<Long> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}

}