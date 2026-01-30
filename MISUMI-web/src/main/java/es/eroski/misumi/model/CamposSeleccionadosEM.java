package es.eroski.misumi.model;

import java.io.Serializable;

public class CamposSeleccionadosEM implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

    private int indice;
    private Long codArticulo;
    private Long identificador;
	private String seleccionado;

    public CamposSeleccionadosEM() {
		super();
	}

	public CamposSeleccionadosEM(	int indice, 
			Long codArticulo, Long identificador,
			String seleccionado) {
		super();
		
		this.indice = indice;
		this.codArticulo = codArticulo;
		this.identificador = identificador;
		this.seleccionado = seleccionado;
	}

	public int getIndice() {
		return this.indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	
	public Long getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getSeleccionado() {
		return this.seleccionado;
	}

	public void setSeleccionado(String seleccionado) {
		this.seleccionado = seleccionado;
	}
	
}