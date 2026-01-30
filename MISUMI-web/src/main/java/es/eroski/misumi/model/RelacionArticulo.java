package es.eroski.misumi.model;

import java.io.Serializable;

public class RelacionArticulo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long codArt;
	private Long codArtRela;
	private Long proporRefCompra;
	private Long proporRefVenta;
	
	public RelacionArticulo() {
	    super();
	}

	public RelacionArticulo(Long codCentro, Long codArt, Long codArtRela,
			Long proporRefCompra, Long proporRefVenta) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.codArtRela = codArtRela;
		this.proporRefCompra = proporRefCompra;
		this.proporRefVenta = proporRefVenta;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public Long getCodArtRela() {
		return this.codArtRela;
	}

	public void setCodArtRela(Long codArtRela) {
		this.codArtRela = codArtRela;
	}

	public Long getProporRefCompra() {
		return this.proporRefCompra;
	}

	public void setProporRefCompra(Long proporRefCompra) {
		this.proporRefCompra = proporRefCompra;
	}

	public Long getProporRefVenta() {
		return this.proporRefVenta;
	}

	public void setProporRefVenta(Long proporRefVenta) {
		this.proporRefVenta = proporRefVenta;
	}
}