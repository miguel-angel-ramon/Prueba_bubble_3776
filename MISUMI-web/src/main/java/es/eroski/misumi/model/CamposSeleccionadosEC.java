package es.eroski.misumi.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CamposSeleccionadosEC implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codArticulo;
    private Long localizador;
	private String seleccionado;
	private String modificable;

    public CamposSeleccionadosEC() {
		super();
	}

	public CamposSeleccionadosEC(Long codArticulo, Long localizador,
			String seleccionado, String modificable) {
		super();
		
		this.codArticulo = codArticulo;
		this.localizador = localizador;
		this.seleccionado = seleccionado;
		this.modificable = modificable;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	
	public Long getLocalizador() {
		return this.localizador;
	}

	public void setLocalizador(Long localizador) {
		this.localizador = localizador;
	}

	public String getSeleccionado() {
		return this.seleccionado;
	}

	public void setSeleccionado(String seleccionado) {
		this.seleccionado = seleccionado;
	}

	public String getModificable() {
		return this.modificable;
	}

	public void setModificable(String modificable) {
		this.modificable = modificable;
	}
	
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(this.localizador).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof CamposSeleccionadosEC)){
			return false;
		}
		CamposSeleccionadosEC cs = (CamposSeleccionadosEC) obj;
		return new EqualsBuilder().append(this.localizador, cs.localizador).isEquals();
	}
}