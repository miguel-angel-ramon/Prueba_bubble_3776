package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

/**
 * Estructura para los combos Area, Seccion, Capacidad, Subcapacidad
 *  de Sfm Fac Cap.
 * @author bihealga
 *
 */
public class EstructuraCom implements Serializable {
	private static final long serialVersionUID = 2611008156199423855L;

	/** Se espera uno de estos valores SFM, FAC, CAP */
	private String tipoEstructura;
	private List<EstructuraDat> datos;
	
	public String getTipoEstructura() {
		return tipoEstructura;
	}
	public void setTipoEstructura(String tipoEstructura) {
		this.tipoEstructura = tipoEstructura;
	}
	public List<EstructuraDat> getDatos() {
		return datos;
	}
	public void setDatos(List<EstructuraDat> datos) {
		this.datos = datos;
	}
}
