package es.eroski.misumi.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;

public class ArtGamaRapid implements Serializable {

	private static final long serialVersionUID = 1560840260156287343L;

	private Long codCentro;
	private String descCentro;
	private Long codArticulo;
	private String descriptionArt;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String area;
	private String seccion;
	private String categoria;
	private String subcategoria;
	private String segmento;
	private String marca;
	private String tpGama;
	private Double uniCajaServ;
	private Double precioCosto;
	private Double pvp;
	private Double pvpOferta;
	private Double kgUnidad;
	private String formato;

	public ArtGamaRapid() {
		super();
	}

	public ArtGamaRapid(Long codCentro, String descCentro, Long codArticulo, String descriptionArt, Long grupo1,
			Long grupo2, Long grupo3, Long grupo4, Long grupo5, String area, String seccion, String categoria,
			String subcategoria, String segmento, String marca, String tpGama, Double uniCajaServ, Double precioCosto,
			Double pvp, Double pvpOferta, Double kgUnidad, String formato) {
		super();
		this.codCentro = codCentro;
		this.descCentro = descCentro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.marca = marca;
		this.tpGama = tpGama;
		this.uniCajaServ = uniCajaServ;
		this.precioCosto = precioCosto;
		this.pvp = pvp;
		this.pvpOferta = pvpOferta;
		this.kgUnidad = kgUnidad;
		this.formato = formato;
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

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getDescriptionArt() {
		return descriptionArt;
	}

	public void setDescriptionArt(String descriptionArt) {
		this.descriptionArt = descriptionArt;
	}

	public Long getGrupo1() {
		return grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public Long getGrupo2() {
		return grupo2;
	}

	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}

	public Long getGrupo3() {
		return grupo3;
	}

	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}

	public Long getGrupo4() {
		return grupo4;
	}

	public void setGrupo4(Long grupo4) {
		this.grupo4 = grupo4;
	}

	public Long getGrupo5() {
		return grupo5;
	}

	public void setGrupo5(Long grupo5) {
		this.grupo5 = grupo5;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getTpGama() {
		return tpGama;
	}

	public void setTpGama(String tpGama) {
		this.tpGama = tpGama;
	}

	public Double getUniCajaServ() {
		return uniCajaServ;
	}

	public void setUniCajaServ(Double uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}

	public Double getPrecioCosto() {
		return precioCosto;
	}

	public void setPrecioCosto(Double precioCosto) {
		this.precioCosto = precioCosto;
	}

	public Double getPvp() {
		return pvp;
	}

	public void setPvp(Double pvp) {
		this.pvp = pvp;
	}

	public Double getPvpOferta() {
		return pvpOferta;
	}

	public void setPvpOferta(Double pvpOferta) {
		this.pvpOferta = pvpOferta;
	}

	public Double getKgUnidad() {
		return kgUnidad;
	}

	public void setKgUnidad(Double kgUnidad) {
		this.kgUnidad = kgUnidad;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ArtGamaRapid)) {
			return false;
		}
		ArtGamaRapid vArtCentroAlta = (ArtGamaRapid) obj;
		return new EqualsBuilder().append(this.codArticulo, vArtCentroAlta.codArticulo).isEquals();
	}

}