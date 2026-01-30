package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InformeHuecos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codSeccion;
	private String descSeccion;
	private Long codPasillo;
	private String descPasillo;
	private Long codArticulo;
	private String descArticulo;
	private String mmc;
	private String catalogo;
	private Integer tipoIncidencia;
	private String tipoFormatted;
	private Date fechaUltimaVenta;
	private String oferta;
	private Long cc;
	private Double ventaMedia;
	private String tipoAprov;
	private Double stockTeorico;
	private List<Integer> causasInactividad;
	private String causasFormatted;
	
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	public Long getCodSeccion() {
		return codSeccion;
	}
	public void setCodSeccion(Long codSeccion) {
		this.codSeccion = codSeccion;
	}
	public String getDescSeccion() {
		return descSeccion;
	}
	public void setDescSeccion(String descSeccion) {
		this.descSeccion = descSeccion;
	}
	public Long getCodPasillo() {
		return codPasillo;
	}
	public void setCodPasillo(Long codPasillo) {
		this.codPasillo = codPasillo;
	}
	public String getDescPasillo() {
		return descPasillo;
	}
	public void setDescPasillo(String descPasillo) {
		this.descPasillo = descPasillo;
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
	public String getMmc() {
		return mmc;
	}
	public void setMmc(String mmc) {
		this.mmc = mmc;
	}
	public String getCatalogo() {
		return catalogo;
	}
	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}
	public Integer getTipoIncidencia() {
		return tipoIncidencia;
	}
	public void setTipoIncidencia(Integer tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}
	public String getTipoFormatted() {
		return tipoFormatted;
	}
	public void setTipoFormatted() {

		switch (this.tipoIncidencia) {
		case 1:
			 this.tipoFormatted = "*";
			break;
		case 2:
			this.tipoFormatted = "**";
			break;
		case 3:
			this.tipoFormatted = "***";
			break;
		

		default:
			break;
		}
	}
	public Date getFechaUltimaVenta() {
		return fechaUltimaVenta;
	}
	public void setFechaUltimaVenta(Date fechaUltimaVenta) {
		this.fechaUltimaVenta = fechaUltimaVenta;
	}
	public String getOferta() {
		return oferta;
	}
	public void setOferta(String oferta) {
		this.oferta = oferta;
	}
	public Long getCc() {
		return cc;
	}
	public void setCc(Long cc) {
		this.cc = cc;
	}
	public Double getVentaMedia() {
		return ventaMedia;
	}
	public void setVentaMedia(Double ventaMedia) {
		this.ventaMedia = ventaMedia;
	}
	public String getTipoAprov() {
		return tipoAprov;
	}
	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}
	public Double getStockTeorico() {
		return stockTeorico;
	}
	public void setStockTeorico(Double stockTeorico) {
		this.stockTeorico = stockTeorico;
	}
	public List<Integer> getCausasInactividad() {
		return causasInactividad;
	}
	public void setCausasInactividad(List<Integer> causasInactividad) {
		this.causasInactividad = causasInactividad;
	}
	
	public void setCausasFormatted(){
		if (this.causasInactividad.contains(new Integer("-1"))){
			this.causasFormatted =  "#ERROR";
		} else {
			this.causasFormatted = StringUtils.join(this.causasInactividad.iterator(),",");
		}
	}
	
	public String getCausasFormatted(){
		return this.causasFormatted;
	}
	
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(this.codCentro).append(this.codArticulo).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof InformeHuecos)){
			return false;
		}
		InformeHuecos npr = (InformeHuecos) obj;
		return new EqualsBuilder().append(this.codCentro, npr.codCentro).append(this.codArticulo, npr.codArticulo).isEquals();
	}

}
