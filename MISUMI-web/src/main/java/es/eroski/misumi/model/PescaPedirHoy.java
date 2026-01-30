package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class PescaPedirHoy implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private List<VPescaMostrador> listaPesca;
	private Long codError;
	private String descError;

	public PescaPedirHoy() {
		super();
	}

	public PescaPedirHoy(Long codCentro, List<VPescaMostrador> listaPesca, Long codError, String descError) {
		super();
		this.codCentro = codCentro;
		this.listaPesca = listaPesca;
		this.codError = codError;
		this.descError = descError;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public List<VPescaMostrador> getListaPesca() {
		return listaPesca;
	}

	public void setListaPesca(List<VPescaMostrador> listaPesca) {
		this.listaPesca = listaPesca;
	}

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

}