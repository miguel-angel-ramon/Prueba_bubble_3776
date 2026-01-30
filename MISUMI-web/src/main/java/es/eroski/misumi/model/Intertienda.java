package es.eroski.misumi.model;

import java.io.Serializable;

public class Intertienda implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codReferencia;
	private Long codCentro;
	private String descCentro;
	private Long codRegion;
	private String descRegion;
	private Long codZona;
	private String descZona;
	private Long codProvincia;
	private String descProvincia;
	private Long codEnsena;
	private Long codArea;
	private Double stock;
	private Float ventaMedia;
	private boolean existeArticulo;
	private String descError;
	private Long codZonaSession;
	private String descProvinciaSession;
	private Long codRegionSession;
	private String negocio;

	public Intertienda() {
		super();
	}

	public Intertienda(Long codReferencia, Long codCentro, String descCentro,
			Long codRegion, String descRegion, Long codZona, String descZona,
			Long codProvincia, String descProvincia, Long codEnsena,
			Long codArea, Double stock, Float ventaMedia, boolean existeArticulo, 
			String descError) {
		super();
		this.codReferencia = codReferencia;
		this.codCentro = codCentro;
		this.descCentro = descCentro;
		this.codRegion = codRegion;
		this.descRegion = descRegion;
		this.codZona = codZona;
		this.descZona = descZona;
		this.codProvincia = codProvincia;
		this.descProvincia = descProvincia;
		this.codEnsena = codEnsena;
		this.codArea = codArea;
		this.stock = stock;
		this.ventaMedia = ventaMedia;
		this.existeArticulo = existeArticulo;
		this.descError = descError;
	}
	
	public Centro toCentro(){
		Centro centro = new Centro();
		centro.setCodCentro(this.codCentro);
		centro.setDescripCentro(this.descCentro);
		centro.setCodEnsena(this.codEnsena);
		centro.setCodArea(this.codArea);
		centro.setCodRegion(this.codRegion);
		centro.setCodZona(this.codZona);
		centro.setDescripZona(this.descZona);
		centro.setProvincia(this.descProvincia);
		return centro;
	}

	public Long getCodReferencia() {
		return codReferencia;
	}

	public void setCodReferencia(Long codReferencia) {
		this.codReferencia = codReferencia;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getDescCentro() {
		return descCentro;
	}

	public void setDescCentro(String descCentro) {
		this.descCentro = descCentro;
	}

	public Long getCodRegion() {
		return codRegion;
	}

	public void setCodRegion(Long codRegion) {
		this.codRegion = codRegion;
	}

	public String getDescRegion() {
		return descRegion;
	}

	public void setDescRegion(String descRegion) {
		this.descRegion = descRegion;
	}

	public Long getCodZona() {
		return codZona;
	}

	public void setCodZona(Long codZona) {
		this.codZona = codZona;
	}

	public String getDescZona() {
		return descZona;
	}

	public void setDescZona(String descZona) {
		this.descZona = descZona;
	}

	public Long getCodProvincia() {
		return codProvincia;
	}

	public void setCodProvincia(Long codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getDescProvincia() {
		return descProvincia;
	}

	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}

	public Long getCodEnsena() {
		return codEnsena;
	}

	public void setCodEnsena(Long codEnsena) {
		this.codEnsena = codEnsena;
	}

	public Long getCodArea() {
		return codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Float getVentaMedia() {
		return ventaMedia;
	}

	public void setVentaMedia(Float ventaMedia) {
		this.ventaMedia = ventaMedia;
	}

	public boolean isExisteArticulo() {
		return existeArticulo;
	}

	public void setExisteArticulo(boolean existeArticulo) {
		this.existeArticulo = existeArticulo;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public Long getCodZonaSession() {
		return codZonaSession;
	}

	public void setCodZonaSession(Long codZonaSession) {
		this.codZonaSession = codZonaSession;
	}

	public String getDescProvinciaSession() {
		return descProvinciaSession;
	}

	public void setDescProvinciaSession(String descProvinciaSession) {
		this.descProvinciaSession = descProvinciaSession;
	}

	public Long getCodRegionSession() {
		return codRegionSession;
	}

	public void setCodRegionSession(Long codRegionSession) {
		this.codRegionSession = codRegionSession;
	}

	public String getNegocio() {
		return negocio;
	}

	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}

}