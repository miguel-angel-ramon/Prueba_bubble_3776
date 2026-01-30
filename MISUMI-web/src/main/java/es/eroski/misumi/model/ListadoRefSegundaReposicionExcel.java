package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class ListadoRefSegundaReposicionExcel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long codCentro;
	private String descCentro;
	private Long codgrupo1;
	private String descgrupo1;
	private Long codgrupo2;
	private String descgrupo2;
	private int mes;
	private String descMes;
	private List<Long> listadoSeleccionados;
	private String listadoSeleccionadosText;
	
	public ListadoRefSegundaReposicionExcel() {
	    super();
	}

	public ListadoRefSegundaReposicionExcel(Long codCentro, String descCentro, Long codgrupo1, String descgrupo1, Long codgrupo2, String descgrupo2, 
			int mes , List<Long> listadoSeleccionados, String listadoSeleccionadosText, String descMes) {
		super();
		this.codCentro = codCentro;
		this.codgrupo1 = codgrupo1;
		this.descgrupo1 = descgrupo1;
		this.codgrupo2 = codgrupo2;
		this.descgrupo2 = descgrupo2;
		this.listadoSeleccionados = listadoSeleccionados;
		this.listadoSeleccionadosText = listadoSeleccionadosText;
		this.mes=mes;
		this.descMes=descMes;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getDescCentro() {
		return descCentro;
	}

	public void setDescCentro(String descCentro) {
		this.descCentro = descCentro;
	}

	public Long getCodgrupo1() {
		return codgrupo1;
	}

	public void setCodgrupo1(Long codgrupo1) {
		this.codgrupo1 = codgrupo1;
	}

	public String getDescgrupo1() {
		return descgrupo1;
	}

	public void setDescgrupo1(String descgrupo1) {
		this.descgrupo1 = descgrupo1;
	}

	public Long getCodgrupo2() {
		return codgrupo2;
	}

	public void setCodgrupo2(Long codgrupo2) {
		this.codgrupo2 = codgrupo2;
	}

	public String getDescgrupo2() {
		return descgrupo2;
	}

	public void setDescgrupo2(String descgrupo2) {
		this.descgrupo2 = descgrupo2;
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

	public String getListadoSeleccionadosText() {
		return listadoSeleccionadosText;
	}

	public void setListadoSeleccionadosText(String listadoSeleccionadosText) {
		this.listadoSeleccionadosText = listadoSeleccionadosText;
	}

	public String getDescMes() {
		return descMes;
	}

	public void setDescMes(String descMes) {
		this.descMes = descMes;
	}
	
}