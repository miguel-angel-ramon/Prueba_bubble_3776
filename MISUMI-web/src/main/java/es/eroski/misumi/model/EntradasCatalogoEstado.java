package es.eroski.misumi.model;

import java.util.List;

public class EntradasCatalogoEstado {

	private List<EntradaEstado> lstEntradaEstado;
	private String descError;
	private Long codError;
	
	public EntradasCatalogoEstado() {
		// TODO Auto-generated constructor stub
	}

	public EntradasCatalogoEstado(List<EntradaEstado> lstEntradaEstado, String descError, Long codError) {
		super();
		this.lstEntradaEstado = lstEntradaEstado;
		this.descError = descError;
		this.codError = codError;
	}

	public List<EntradaEstado> getLstEntradaEstado() {
		return lstEntradaEstado;
	}

	public void setLstEntradaEstado(List<EntradaEstado> lstEntradaEstado) {
		this.lstEntradaEstado = lstEntradaEstado;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}
}
