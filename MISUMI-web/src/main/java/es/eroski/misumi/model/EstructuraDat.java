package es.eroski.misumi.model;

import java.io.Serializable;

/**
 * Resultados de valores para combo
 * @author bihealga
 *
 */
public class EstructuraDat implements Serializable {
	private static final long serialVersionUID = -4970058365692951974L;

	private Long codigo;
	private String denominacion;
	
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
}
