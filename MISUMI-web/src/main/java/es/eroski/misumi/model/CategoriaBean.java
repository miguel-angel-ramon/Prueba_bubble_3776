package es.eroski.misumi.model;

public class CategoriaBean {

	public Long codArea;
	public Long codSeccion;
	public Long codCategoria;

	public String descripcionCat;

	public CategoriaBean() {
		super();
	}
	
	public CategoriaBean(Long codArea, Long codSeccion, Long codCategoria, String descripcionCat) {
		super();
		this.codArea = codArea;
		this.codSeccion = codSeccion;
		this.codCategoria = codCategoria;
		this.descripcionCat = descripcionCat;
	}

	public Long getCodArea() {
		return codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public Long getCodSeccion() {
		return codSeccion;
	}

	public void setCodSeccion(Long codSeccion) {
		this.codSeccion = codSeccion;
	}

	public Long getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(Long codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getDescripcionCat() {
		return descripcionCat;
	}

	public void setDescripcionCat(String descripcionCat) {
		this.descripcionCat = descripcionCat;
	}
}
