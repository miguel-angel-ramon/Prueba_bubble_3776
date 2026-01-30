package es.eroski.misumi.model.pda.ayudaFacing;

import java.io.Serializable;

public class RefAyudaFacing  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long codArticulo;
    private String descArticulo;
    private String rotacion;
    private Double facingExcedente;
    private Long orden;

	public RefAyudaFacing(Long codArticulo, String descArticulo, String rotacion, Double facingExcedente, Long orden) {
		super();
		this.codArticulo = codArticulo;
		this.descArticulo = descArticulo;
		this.rotacion = rotacion;
		this.facingExcedente = facingExcedente;
		this.orden = orden;
	}
	public Long getCodArticulo() {
		return codArticulo;
	}
	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	public String getDescArticulo() {
		return descArticulo;
	}
	public void setDescArticulo(String descArticulo) {
		this.descArticulo = descArticulo;
	}
	public String getRotacion() {
		return rotacion;
	}
	public void setRotacion(String rotacion) {
		this.rotacion = rotacion;
	}
	public Double getFacingExcedente() {
		return facingExcedente;
	}
	public void setFacingExcedente(Double facingExcedente) {
		this.facingExcedente = facingExcedente;
	}
	public Long getOrden() {
		return orden;
	}
	public void setOrden(Long orden) {
		this.orden = orden;
	}
    
}
