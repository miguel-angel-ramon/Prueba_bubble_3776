/**
 * 
 */
package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author BICUGUAL
 *
 */
public class FiltrosDetalleMostrador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codCentro;
	private String seccion;
	private String categoria;
	private String subcategoria;
	private String segmento;
	private String tipoAprov;
	private String soloVenta;
	private String gamaLocal;
	private Date diaEspejo;
	private String idSesion;
	
	public FiltrosDetalleMostrador() {
		super();
	}

	public FiltrosDetalleMostrador(Long codCentro, Long seccion, Long categoria, Long subcategoria,
			Long segmento, String tipoAprov, String soloVenta, String gamaLocal, Date diaEspejo, String idSesion) {
		super();
		this.codCentro = null != codCentro ? codCentro.toString() : null;
		this.seccion =  null != seccion ? seccion.toString() : null;
		this.categoria = null != categoria ? categoria.toString(): null;
		this.subcategoria = null != subcategoria ? subcategoria.toString() : null;
		this.segmento = null !=segmento ? segmento.toString() : null;
		this.tipoAprov = tipoAprov;
		this.soloVenta = soloVenta;
		this.gamaLocal = gamaLocal;
		this.diaEspejo = diaEspejo;
		this.idSesion = idSesion;
	}

	public String getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(String codCentro) {
		this.codCentro = codCentro;
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

	public String getTipoAprov() {
		return tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public String getSoloVenta() {
		return soloVenta;
	}

	public void setSoloVenta(String soloVenta) {
		this.soloVenta = soloVenta;
	}

	public String getGamaLocal() {
		return gamaLocal;
	}

	public void setGamaLocal(String gamaLocal) {
		this.gamaLocal = gamaLocal;
	}

	public Date getDiaEspejo() {
		return diaEspejo;
	}

	public void setDiaEspejo(Date diaEspejo) {
		this.diaEspejo = diaEspejo;
	}

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FiltrosDetalleMostrador [codCentro=");
		builder.append(codCentro);
		builder.append(", seccion=");
		builder.append(seccion);
		builder.append(", categoria=");
		builder.append(categoria);
		builder.append(", subcategoria=");
		builder.append(subcategoria);
		builder.append(", segmento=");
		builder.append(segmento);
		builder.append(", tipoAprov=");
		builder.append(tipoAprov);
		builder.append(", soloVenta=");
		builder.append(soloVenta);
		builder.append(", gamaLocal=");
		builder.append(gamaLocal);
		builder.append(", diaEspejo=");
		builder.append(diaEspejo);
		builder.append(", idSesion=");
		builder.append(idSesion);
		builder.append("]");
		return builder.toString();
	}

}
