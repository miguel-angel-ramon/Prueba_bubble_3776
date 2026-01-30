package es.eroski.misumi.model;

import java.io.Serializable;

public class CamposSeleccionadosMO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

    private int indice;
    private Long codArticulo;
    private Long identificador;
	private String seleccionado;
	private String esPlanograma;
	private Long clasePedido;
	private String noGestionaPbl;
	private Long identificadorSIA;
	private Long identificadorVegalsa;

    public CamposSeleccionadosMO() {
		super();
	}

	public CamposSeleccionadosMO(	int indice, 
			Long codArticulo, Long identificador,
			String seleccionado, String esPlanograma,
			Long clasePedido, String noGestionaPbl,
			Long identificadorSIA) {
		super();
		
		this.indice = indice;
		this.codArticulo = codArticulo;
		this.identificador = identificador;
		this.seleccionado = seleccionado;
		this.esPlanograma = esPlanograma;
		this.clasePedido = clasePedido;
		this.noGestionaPbl = noGestionaPbl;
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
	
	public String getEsPlanograma() {
		return this.esPlanograma;
	}

	public void setEsPlanograma(String esPlanograma) {
		this.esPlanograma = esPlanograma;
	}

	public Long getClasePedido() {
		return clasePedido;
	}

	public void setClasePedido(Long clasePedido) {
		this.clasePedido = clasePedido;
	}

	public String getNoGestionaPbl() {
		return this.noGestionaPbl;
	}

	public void setNoGestionaPbl(String noGestionaPbl) {
		this.noGestionaPbl = noGestionaPbl;
	}
	
	public Long getIdentificadorSIA() {
		return this.identificadorSIA;
	}

	public void setIdentificadorSIA(Long identificadorSIA) {
		this.identificadorSIA = identificadorSIA;
	}

	public Long getIdentificadorVegalsa() {
		return identificadorVegalsa;
	}

	public void setIdentificadorVegalsa(Long identificadorVegalsa) {
		this.identificadorVegalsa = identificadorVegalsa;
	}

}