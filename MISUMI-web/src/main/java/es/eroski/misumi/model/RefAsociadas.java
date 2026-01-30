package es.eroski.misumi.model;

import java.io.Serializable;

public class RefAsociadas implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArticulo;
	private Long codArticuloHijo;
	private Long cantidad;
	private Long codNivelEstrArtEc1;
	private Long codNivelEstrArtEc2;
	private Long codNivelEstrArtEc3;
	private Long codNivelEstrArtEc4;
	private Long codNivelEstrArtEc5;
	
	public RefAsociadas() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RefAsociadas(Long codArticulo, Long codArticuloHijo, Long cantidad,
			Long codNivelEstrArtEc1, Long codNivelEstrArtEc2,
			Long codNivelEstrArtEc3, Long codNivelEstrArtEc4,
			Long codNivelEstrArtEc5) {
		super();
		this.codArticulo = codArticulo;
		this.codArticuloHijo = codArticuloHijo;
		this.cantidad = cantidad;
		this.codNivelEstrArtEc1 = codNivelEstrArtEc1;
		this.codNivelEstrArtEc2 = codNivelEstrArtEc2;
		this.codNivelEstrArtEc3 = codNivelEstrArtEc3;
		this.codNivelEstrArtEc4 = codNivelEstrArtEc4;
		this.codNivelEstrArtEc5 = codNivelEstrArtEc5;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getCodArticuloHijo() {
		return this.codArticuloHijo;
	}

	public void setCodArticuloHijo(Long codArticuloHijo) {
		this.codArticuloHijo = codArticuloHijo;
	}

	public Long getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getCodNivelEstrArtEc1() {
		return this.codNivelEstrArtEc1;
	}

	public void setCodNivelEstrArtEc1(Long codNivelEstrArtEc1) {
		this.codNivelEstrArtEc1 = codNivelEstrArtEc1;
	}

	public Long getCodNivelEstrArtEc2() {
		return this.codNivelEstrArtEc2;
	}

	public void setCodNivelEstrArtEc2(Long codNivelEstrArtEc2) {
		this.codNivelEstrArtEc2 = codNivelEstrArtEc2;
	}

	public Long getCodNivelEstrArtEc3() {
		return this.codNivelEstrArtEc3;
	}

	public void setCodNivelEstrArtEc3(Long codNivelEstrArtEc3) {
		this.codNivelEstrArtEc3 = codNivelEstrArtEc3;
	}

	public Long getCodNivelEstrArtEc4() {
		return this.codNivelEstrArtEc4;
	}

	public void setCodNivelEstrArtEc4(Long codNivelEstrArtEc4) {
		this.codNivelEstrArtEc4 = codNivelEstrArtEc4;
	}

	public Long getCodNivelEstrArtEc5() {
		return this.codNivelEstrArtEc5;
	}

	public void setCodNivelEstrArtEc5(Long codNivelEstrArtEc5) {
		this.codNivelEstrArtEc5 = codNivelEstrArtEc5;
	}
}