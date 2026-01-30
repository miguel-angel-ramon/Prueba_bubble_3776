package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class EstadoMostrador implements Serializable {

	private static final long serialVersionUID = -2305727980637551447L;

	// Lista de los estado de las diferentes estructuras comerciales de centros
	// parametrizados con Mostrador.
	private List<EstructuraArtMostrador> lstEstadoEstructurasMostrador;

	// Codigo que nos indica el mensaje a mostrar segun estado de estructuras
	// comerciales
	private Integer codMensajeEstado;

	public List<EstructuraArtMostrador> getLstEstadoEstructurasMostrador() {
		return lstEstadoEstructurasMostrador;
	}

	public void setLstEstadoEstructurasMostrador(List<EstructuraArtMostrador> lstEstadoEstructurasMostrador) {
		this.lstEstadoEstructurasMostrador = lstEstadoEstructurasMostrador;
	}

	public Integer getCodMensajeEstado() {
		return codMensajeEstado;
	}

	public void setCodMensajeEstado(Integer codMensajeEstado) {
		this.codMensajeEstado = codMensajeEstado;
	}

}