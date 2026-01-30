package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ValidarReferenciaEncargo implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codReferencia;
	private Double unidadesCaja;
	private Long plataforma;
	private Date fechaEntrega;
	private List<Date> fechasVenta;
	private Integer codError;
	private String descError;
	private String generico;
	private String flgEspec;
	private String tipoReferencia;
	
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public Long getCodReferencia() {
		return codReferencia;
	}
	public void setCodReferencia(Long codReferencia) {
		this.codReferencia = codReferencia;
	}
	public Double getUnidadesCaja() {
		return unidadesCaja;
	}
	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}
	public Long getPlataforma() {
		return plataforma;
	}
	public void setPlataforma(Long plataforma) {
		this.plataforma = plataforma;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public List<Date> getFechasVenta() {
		return fechasVenta;
	}
	public void setFechasVenta(List<Date> fechasVenta) {
		this.fechasVenta = fechasVenta;
	}
	public Integer getCodError() {
		return codError;
	}
	public void setCodError(Integer codError) {
		this.codError = codError;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	public String getGenerico() {
		return generico;
	}
	public void setGenerico(String generico) {
		this.generico = generico;
	}
	public String getFlgEspec() {
		return flgEspec;
	}
	public void setFlgEspec(String flgEspec) {
		this.flgEspec = flgEspec;
	}
	public String getTipoReferencia() {
		return tipoReferencia;
	}
	public void setTipoReferencia(String tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

}
