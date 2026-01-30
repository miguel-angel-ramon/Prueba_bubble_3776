package es.eroski.misumi.model;

import java.util.List;

public class GestionEurosRefs {
	private Long codArticulo;
	private Long codNecesidad;
	private Double unidsCaja;
	private Double precioCostoArticulo;
	private Double precioCostoArtLinealFinal;
	private Long unidPropuestasFlModif;
	private Long diferencia;
	private String refCumple;    
	private String avisos;     

	public GestionEurosRefs(Long codArticulo,Long codNecesidad, Double unidsCaja, Double precioCostoArticulo,
			Double precioCostoArtLinealFinal, Long unidPropuestasFlModif, Long diferencia,String refCumple, String avisos) {
		super();
		this.codArticulo = codArticulo;
		this.codNecesidad = codNecesidad;
		this.unidsCaja = unidsCaja;
		this.precioCostoArticulo = precioCostoArticulo;
		this.precioCostoArtLinealFinal = precioCostoArtLinealFinal;
		this.unidPropuestasFlModif = unidPropuestasFlModif;
		this.diferencia = diferencia;
		this.refCumple = refCumple;
		this.avisos = avisos;
	}

	public GestionEurosRefs() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	
	public Long getCodNecesidad() {
		return codNecesidad;
	}

	public void setCodNecesidad(Long codNecesidad) {
		this.codNecesidad = codNecesidad;
	}

	public Double getUnidsCaja() {
		return unidsCaja;
	}

	public void setUnidsCaja(Double unidsCaja) {
		this.unidsCaja = unidsCaja;
	}

	public Double getPrecioCostoArticulo() {
		return precioCostoArticulo;
	}

	public void setPrecioCostoArticulo(Double precioCostoArticulo) {
		this.precioCostoArticulo = precioCostoArticulo;
	}

	public Double getPrecioCostoArtLinealFinal() {
		return precioCostoArtLinealFinal;
	}

	public void setPrecioCostoArtLinealFinal(Double precioCostoArtLinealFinal) {
		this.precioCostoArtLinealFinal = precioCostoArtLinealFinal;
	}

	public Long getUnidPropuestasFlModif() {
		return unidPropuestasFlModif;
	}

	public void setUnidPropuestasFlModif(Long unidPropuestasFlModif) {
		this.unidPropuestasFlModif = unidPropuestasFlModif;
	}

	public Long getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(Long diferencia) {
		this.diferencia = diferencia;
	}
	
	public String getRefCumple() {
		return refCumple;
	}

	public void setRefCumple(String refCumple) {
		this.refCumple = refCumple;
	}

	public String getAvisos() {
		return avisos;
	}

	public void setAvisos(String avisos) {
		this.avisos = avisos;
	}
}
