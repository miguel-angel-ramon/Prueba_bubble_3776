package es.eroski.misumi.model;

import java.io.InputStream;
import java.io.Serializable;

public class FotosReferencia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codReferencia;
	private InputStream foto;
	
	public Long getCodReferencia() {
		return codReferencia;
	}
	public void setCodReferencia(Long codReferencia) {
		this.codReferencia = codReferencia;
	}
	public InputStream getFoto() {
		return foto;
	}
	public void setFoto(InputStream foto) {
		this.foto = foto;
	}

}
