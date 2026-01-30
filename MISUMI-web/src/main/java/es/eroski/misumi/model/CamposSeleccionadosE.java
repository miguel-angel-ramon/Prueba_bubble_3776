package es.eroski.misumi.model;

import java.io.Serializable;

public class CamposSeleccionadosE implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

    private int indice;
    private Long codArticulo;
    private Long identificador;
	private String seleccionado;
	private Long identificadorSIA;

    public CamposSeleccionadosE() {
		super();
	}

	public CamposSeleccionadosE(	int indice, 
			Long codArticulo, Long identificador, String seleccionado, Long identificadorSIA) {
		super();
		
		this.indice = indice;
		this.codArticulo = codArticulo;
		this.identificador = identificador;
		this.seleccionado = seleccionado;
		this.identificadorSIA = identificadorSIA;
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
	
	public Long getIdentificadorSIA() {
		return this.identificadorSIA;
	}

	public void setIdentificadorSIA(Long identificadorSIA) {
		this.identificadorSIA = identificadorSIA;
	}
}