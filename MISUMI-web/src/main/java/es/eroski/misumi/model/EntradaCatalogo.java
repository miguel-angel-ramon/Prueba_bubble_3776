package es.eroski.misumi.model;

import java.util.List;

public class EntradaCatalogo {
	private List<Entrada> lstEntrada;
	private String descError;
	private Long codError;	
	
	public EntradaCatalogo(List<Entrada> lstEntrada, String descError, Long codError) {
		super();
		this.lstEntrada = lstEntrada;
		this.descError = descError;
		this.codError = codError;
	}

	public List<Entrada> getLstEntrada() {
		return lstEntrada;
	}
	public void setLstEntrada(List<Entrada> lstEntrada) {
		this.lstEntrada = lstEntrada;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntradaCatalogo [lstEntrada=");
		builder.append(lstEntrada);
		builder.append(", descError=");
		builder.append(descError);
		builder.append(", codError=");
		builder.append(codError);
		builder.append("]");
		return builder.toString();
	}
	
	
}
