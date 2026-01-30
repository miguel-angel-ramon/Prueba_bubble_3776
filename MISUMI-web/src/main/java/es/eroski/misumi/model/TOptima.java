package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TOptima implements Serializable, Comparable<TOptima>{
	
	private static final long serialVersionUID = 1L;
	
	private String codMac;
	private Long codCentro;
	private Long area;
	private Long codArt;
	private String descripcion;
	private Boolean nsr;
	private Boolean enviado;
	private Date fechaGen;
	private Date creationDate;
	private Long codError;
	private String descError;

	public String getCodMac() {
		return codMac;
	}
	public void setCodMac(String codMac) {
		this.codMac = codMac;
	}
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public Long getArea() {
		return area;
	}
	public void setArea(Long area) {
		this.area = area;
	}
	public Long getCodArt() {
		return codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Boolean isNsr() {
		return nsr;
	}
	public void setNsr(Boolean nsr) {
		this.nsr = nsr;
	}
	public Boolean isEnviado() {
		return enviado;
	}
	public void setEnviado(Boolean enviado) {
		this.enviado = enviado;
	}
	public Date getFechaGen() {
		return fechaGen;
	}
	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	
	public String getDescCompleta(){
		return this.codArt + " - " + this.descripcion;
	}
	
	public int hashCode() {
		
		return new HashCodeBuilder(17, 31).append(this.codArt).append(this.codMac).append(this.codCentro).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof TOptima)){
			return false;
		}
		TOptima dp = (TOptima) obj;
		return new EqualsBuilder().append(this.codArt, dp.codArt).append(this.codMac, dp.codMac).append(this.codCentro,dp.codCentro).isEquals();
	}
	@Override
	public int compareTo(TOptima o) {
		Integer h = this.hashCode();
		Integer h1 = o.hashCode();
		return h.compareTo(h1);
	}
	
	

}
