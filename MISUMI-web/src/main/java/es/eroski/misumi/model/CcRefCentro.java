package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class CcRefCentro implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArticulo;
	private Long codLoc; 
	private Date fecha;
	
	public CcRefCentro() {
		super();
	}

	public CcRefCentro(Long codArticulo, Long codLoc, Date fecha) {
		super();
		this.codArticulo = codArticulo;
		this.codLoc = codLoc;
		this.fecha = fecha;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}