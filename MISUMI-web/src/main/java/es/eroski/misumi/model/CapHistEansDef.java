package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class CapHistEansDef implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codArticulo;
	private Long codArticuloCap;
	private Long codEan;
	private Date fecCreacion;
	private Date fecFin;
	private Date fecInicio;
	private String hora;
	private String usuario;
	
	public CapHistEansDef() {
		super();
	}

	public CapHistEansDef(Long codArticulo, Long codArticuloCap, Long codEan, Date fecCreacion, Date fecFin,
			Date fecInicio, String hora, String usuario) {
		super();
		this.codArticulo = codArticulo;
		this.codArticuloCap = codArticuloCap;
		this.codEan = codEan;
		this.fecCreacion = fecCreacion;
		this.fecFin = fecFin;
		this.fecInicio = fecInicio;
		this.hora = hora;
		this.usuario = usuario;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getCodArticuloCap() {
		return this.codArticuloCap;
	}

	public void setCodArticuloCap(Long codArticuloCap) {
		this.codArticuloCap = codArticuloCap;
	}

	public Long getCodEan() {
		return this.codEan;
	}

	public void setCodEan(Long codEan) {
		this.codEan = codEan;
	}

	public Date getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Date getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public String getHora() {
		return this.hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}