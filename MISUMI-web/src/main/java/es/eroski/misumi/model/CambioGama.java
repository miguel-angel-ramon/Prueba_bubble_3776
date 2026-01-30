package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class CambioGama implements Serializable {
	
	/**
	 * Atributos
	 */
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codArt;
	private String tipo;
	private Date fechaGen;
	private Long numVeces;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	
	/**
	 * Constructor
	 */
    public CambioGama() {
		super();
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArt() {
		return codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFechaGen() {
		return fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public Long getNumVeces() {
		return numVeces;
	}

	public void setNumVeces(Long numVeces) {
		this.numVeces = numVeces;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}  
	
}