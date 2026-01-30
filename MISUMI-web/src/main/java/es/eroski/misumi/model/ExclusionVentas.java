package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;

public class ExclusionVentas implements Cloneable,Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long codCentro;
	private Long codArt;
	private String descripArt;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String descripGrupo1;
	private String descripGrupo2;
	private String descripGrupo3;
	private String descripGrupo4;
	private String descripGrupo5;
	private Long identificador;
	private String fecha;
	private Long codError;
	private String descripError;
	private List<CamposSeleccionadosExclVenta> listadoSeleccionados;
	
	private Long identificadorSIA;
	
	public ExclusionVentas() {
	    super();
	}
	
	public  ExclusionVentas clone() throws CloneNotSupportedException{
		return (ExclusionVentas)super.clone();
	}

	public ExclusionVentas(Long codCentro, Long codArt, String descripArt,
			Long grupo1, Long grupo2, Long grupo3, Long grupo4,
			Long grupo5, String descripGrupo1, String descripGrupo2,
			String descripGrupo3, String descripGrupo4, String descripGrupo5,
			Long identificador, String fecha, Long codError,
			String descripError, List<CamposSeleccionadosExclVenta> listadoSeleccionados) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.descripArt = descripArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripGrupo1 = descripGrupo1;
		this.descripGrupo2 = descripGrupo2;
		this.descripGrupo3 = descripGrupo3;
		this.descripGrupo4 = descripGrupo4;
		this.descripGrupo5 = descripGrupo5;
		this.identificador = identificador;
		this.fecha = fecha;
		this.codError = codError;
		this.descripError = descripError;
		this.listadoSeleccionados = listadoSeleccionados;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getDescripArt() {
		return this.descripArt;
	}

	public void setDescripArt(String descripArt) {
		this.descripArt = descripArt;
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

	public Long getGrupo3() {
		return this.grupo3;
	}

	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}

	public Long getGrupo4() {
		return this.grupo4;
	}

	public void setGrupo4(Long grupo4) {
		this.grupo4 = grupo4;
	}

	public Long getGrupo5() {
		return this.grupo5;
	}

	public void setGrupo5(Long grupo5) {
		this.grupo5 = grupo5;
	}

	public String getDescripGrupo1() {
		return this.descripGrupo1;
	}

	public void setDescripGrupo1(String descripGrupo1) {
		this.descripGrupo1 = descripGrupo1;
	}

	public String getDescripGrupo2() {
		return this.descripGrupo2;
	}

	public void setDescripGrupo2(String descripGrupo2) {
		this.descripGrupo2 = descripGrupo2;
	}

	public String getDescripGrupo3() {
		return this.descripGrupo3;
	}

	public void setDescripGrupo3(String descripGrupo3) {
		this.descripGrupo3 = descripGrupo3;
	}

	public String getDescripGrupo4() {
		return this.descripGrupo4;
	}

	public void setDescripGrupo4(String descripGrupo4) {
		this.descripGrupo4 = descripGrupo4;
	}

	public String getDescripGrupo5() {
		return this.descripGrupo5;
	}

	public void setDescripGrupo5(String descripGrupo5) {
		this.descripGrupo5 = descripGrupo5;
	}

	public Long getIdentificador() {
		return (null != this.identificador?this.identificador:new Long("0"));
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Long getCodError() {
		return this.codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescripError() {
		return this.descripError;
	}

	public void setDescripError(String descripError) {
		this.descripError = descripError;
	}

	public List<CamposSeleccionadosExclVenta> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(
			List<CamposSeleccionadosExclVenta> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}

	public Long getIdentificadorSIA() {
		return (null != this.identificadorSIA?this.identificadorSIA:new Long("0"));
	}

	public void setIdentificadorSIA(Long identificadorSIA) {
		this.identificadorSIA = identificadorSIA;
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof ExclusionVentas)){
			return false;
		}
		ExclusionVentas exc = (ExclusionVentas) obj;
		return new EqualsBuilder()
				.append(this.codCentro, exc.codCentro)
				.append(this.codArt, exc.codArt)
				.append(this.grupo1, exc.grupo1)
				.append(this.grupo2, exc.grupo2)
				.append(this.grupo3, exc.grupo3)
				.append(this.grupo4, exc.grupo4)
				.append(this.grupo5, exc.grupo5).isEquals();
	}
}