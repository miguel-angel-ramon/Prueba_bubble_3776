package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class EstadoConvergencia implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArticulo;
	private Long codLoc; 

	
	public EstadoConvergencia() {
		super();
	}

	public EstadoConvergencia(Long codArticulo, Long codLoc) {
		super();
		this.codArticulo = codArticulo;
		this.codLoc = codLoc;
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

}