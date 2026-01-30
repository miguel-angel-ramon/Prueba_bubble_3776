package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class PLU implements Serializable{

	private static final long serialVersionUID = 1L;

	private int codCentro;
	private int	grupoBalanza;
	private int numMaxPLU;
	private String usuarioCreacion;
	private String usuarioModificacion;
	
	public PLU() {
		super();
	}

	public PLU(int codCentro, int grupoBalanza, int numMaxPLU, String usuarioCreacion, String usuarioModificacion) {
		super();
		this.codCentro = codCentro;
		this.grupoBalanza = grupoBalanza;
		this.numMaxPLU = numMaxPLU;
		this.usuarioCreacion = usuarioCreacion;
		this.usuarioModificacion = usuarioModificacion;
	}

	public int getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(int codCentro) {
		this.codCentro = codCentro;
	}

	public int getGrupoBalanza() {
		return grupoBalanza;
	}

	public void setGrupoBalanza(int grupoBalanza) {
		this.grupoBalanza = grupoBalanza;
	}

	public int getNumMaxPLU() {
		return numMaxPLU;
	}

	public void setNumMaxPLU(int numMaxPLU) {
		this.numMaxPLU = numMaxPLU;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	@Override
	public String toString() {
		return "PLU [codCentro=" + codCentro + ", grupoBalanza=" + grupoBalanza + ", numMaxPLU=" + numMaxPLU
				+ ", usuarioCreacion=" + usuarioCreacion + ", usuarioModificacion=" + usuarioModificacion + "]";
	}
		
}
