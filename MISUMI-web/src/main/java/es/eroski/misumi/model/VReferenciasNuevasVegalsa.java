package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VReferenciasNuevasVegalsa implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codMapa;
	private Date primeraFechaReposicion;
	private String catalogoCen;
	private String tipoAprov;
	private Double uc;
	private Long diasMaximos;
	private Long cantidadMaxima;
	
	
	public VReferenciasNuevasVegalsa() {
		super();
	}
	
	public VReferenciasNuevasVegalsa( Long codMapa, Date primeraFechaReposicion, String catalogoCen
									, String tipoAprov, Double uc) {
		super();
		this.codMapa = codMapa;
		this.primeraFechaReposicion = primeraFechaReposicion;
		this.catalogoCen = catalogoCen;
		this.tipoAprov = tipoAprov;
		this.uc = uc;
	}

	public Long getCodMapa() {
		return codMapa;
	}

	public void setCodMapa(Long codMapa) {
		this.codMapa = codMapa;
	}

	public Date getPrimeraFechaReposicion() {
		return primeraFechaReposicion;
	}

	public void setPrimeraFechaReposicion(Date primeraFechaReposicion) {
		this.primeraFechaReposicion = primeraFechaReposicion;
	}

	public String getCatalogoCen() {
		return catalogoCen;
	}

	public void setCatalogoCen(String catalogoCen) {
		this.catalogoCen = catalogoCen;
	}

	public String getTipoAprov() {
		return tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public Double getUc() {
		return uc;
	}

	public void setUc(Double uc) {
		this.uc = uc;
	}

	public Long getDiasMaximos() {
		return diasMaximos;
	}

	public void setDiasMaximos(Long diasMaximos) {
		this.diasMaximos = diasMaximos;
	}

	public Long getCantidadMaxima() {
		return cantidadMaxima;
	}

	public void setCantidadMaxima(Long cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}

}