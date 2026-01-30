package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CestasNavidad implements Serializable {
	
	/**
	 * Atributos
	 */
	private static final long serialVersionUID = 1L;
	
	private Long codArtLote;
	private String descrLoteMisumi;
	private String descrProducto;
	private String imagen1;
	private String imagen2;
	private String imagen3;
	private Long stock;

	//campos para Caprabo
	private Long codArtLoteCpb;
	private String descripcionLoteCpb;

	//Campos para visualización de datos en pantalla dependiendo del centro Eroski o Caprabo
	private Long codArtLotePantalla;
	private String descripcionLotePantalla;

	//MISUMI-266
	private Date fecIni;
	private Date fecFin;
	private String flgEroski;
	private String flgCaprabo;
	private Long orden;
	private Long estado;
	private String borrar;
	
	private List<CestasNavidadArticulo> lstBorrados;
	private List<CestasNavidadArticulo> lstModificados;
	private List<CestasNavidadArticulo> lstNuevos;
	
	private List<CestasNavidadArticulo> lstArticulos;
	private Date lastUpdate;

	// MISUMI-284
	private boolean tieneTexto;

	/**
	 * Constructor
	 */
    public CestasNavidad() {
		super();
	}
    
    public CestasNavidad(Long codArtLote, String descrLoteMisumi,
			String descrProducto, String imagen1, String imagen2, String imagen3,
			Long codArtLoteCpb, String descripcionLoteCpb, 
			Long codArtLotePantalla, String descripcionLotePantalla) {
		super();
		this.codArtLote = codArtLote;
		this.descrLoteMisumi = descrLoteMisumi;
		this.descrProducto = descrProducto;
		this.imagen1 = imagen1;
		this.imagen2 = imagen2;
		this.imagen3 = imagen3;
		this.codArtLoteCpb = codArtLoteCpb;
		this.descripcionLoteCpb = descripcionLoteCpb;
		this.codArtLotePantalla = codArtLotePantalla;
		this.descripcionLotePantalla = descripcionLotePantalla;
	}


	public CestasNavidad(Long codArtLote, String descrLoteMisumi, String imagen1, String imagen2,
			String imagen3, Long codArtLoteCpb, String descripcionLoteCpb, 
			Date fecIni, Date fecFin, String flgEroski, String flgCaprabo, Long orden,
			Long estado, String borrar, Boolean tieneTexto) {
		super();
		this.codArtLote = codArtLote;
		this.descrLoteMisumi = descrLoteMisumi;
		this.imagen1 = imagen1;
		this.imagen2 = imagen2;
		this.imagen3 = imagen3;
		this.codArtLoteCpb = codArtLoteCpb;
		this.descripcionLoteCpb = descripcionLoteCpb;
		this.fecIni = fecIni;
		this.fecFin = fecFin;
		this.flgEroski = flgEroski;
		this.flgCaprabo = flgCaprabo;
		this.orden = orden;
		this.estado = estado;
		this.borrar = borrar;
		this.tieneTexto = tieneTexto;
	}

	public CestasNavidad(Long codArtLote, String descrLoteMisumi, String descrProducto, String imagen1, String imagen2,
			String imagen3, Long stock, Long codArtLoteCpb, String descripcionLoteCpb, Long codArtLotePantalla,
			String descripcionLotePantalla, Date fecIni, Date fecFin, String flgEroski, String flgCaprabo, Long orden,
			Long estado, String borrar, List<CestasNavidadArticulo> lstBorrados,
			List<CestasNavidadArticulo> lstModificados, List<CestasNavidadArticulo> lstNuevos) {
		super();
		this.codArtLote = codArtLote;
		this.descrLoteMisumi = descrLoteMisumi;
		this.descrProducto = descrProducto;
		this.imagen1 = imagen1;
		this.imagen2 = imagen2;
		this.imagen3 = imagen3;
		this.stock = stock;
		this.codArtLoteCpb = codArtLoteCpb;
		this.descripcionLoteCpb = descripcionLoteCpb;
		this.codArtLotePantalla = codArtLotePantalla;
		this.descripcionLotePantalla = descripcionLotePantalla;
		this.fecIni = fecIni;
		this.fecFin = fecFin;
		this.flgEroski = flgEroski;
		this.flgCaprabo = flgCaprabo;
		this.orden = orden;
		this.estado = estado;
		this.borrar = borrar;
		this.lstBorrados = lstBorrados;
		this.lstModificados = lstModificados;
		this.lstNuevos = lstNuevos;
	}

	/**
	 * Getters and Setters
	 */
	
	public Long getCodArtLote() {
		return this.codArtLote;
	}

	public void setCodArtLote(Long codArtLote) {
		this.codArtLote = codArtLote;
	}

	public String getDescrLoteMisumi() {
		return this.descrLoteMisumi;
	}

	public void setDescrLoteMisumi(String descrLoteMisumi) {
		this.descrLoteMisumi = descrLoteMisumi;
	}

	public String getDescrProducto() {
		return this.descrProducto;
	}

	public void setDescrProducto(String descrProducto) {
		this.descrProducto = descrProducto;
	}


	public String getImagen1() {
		return this.imagen1;
	}


	public void setImagen1(String imagen1) {
		this.imagen1 = imagen1;
	}


	public String getImagen2() {
		return this.imagen2;
	}


	public void setImagen2(String imagen2) {
		this.imagen2 = imagen2;
	}


	public String getImagen3() {
		return this.imagen3;
	}


	public void setImagen3(String imagen3) {
		this.imagen3 = imagen3;
	}

	public Long getStock() {
		return this.stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getCodArtLoteCpb() {
		return this.codArtLoteCpb;
	}

	public void setCodArtLoteCpb(Long codArtLoteCpb) {
		this.codArtLoteCpb = codArtLoteCpb;
	}

	public String getDescripcionLoteCpb() {
		return this.descripcionLoteCpb;
	}

	public void setDescripcionLoteCpb(String descripcionLoteCpb) {
		this.descripcionLoteCpb = descripcionLoteCpb;
	}

	public Long getCodArtLotePantalla() {
		return this.codArtLotePantalla;
	}

	public void setCodArtLotePantalla(Long codArtLotePantalla) {
		this.codArtLotePantalla = codArtLotePantalla;
	}

	public String getDescripcionLotePantalla() {
		return this.descripcionLotePantalla;
	}

	public void setDescripcionLotePantalla(String descripcionLotePantalla) {
		this.descripcionLotePantalla = descripcionLotePantalla;
	}

	public Date getFecIni() {
		return fecIni;
	}

	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public String getFlgEroski() {
		return flgEroski;
	}

	public void setFlgEroski(String flgEroski) {
		this.flgEroski = flgEroski;
	}

	public String getFlgCaprabo() {
		return flgCaprabo;
	}

	public void setFlgCaprabo(String flgCaprabo) {
		this.flgCaprabo = flgCaprabo;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public String getBorrar() {
		return borrar;
	}

	public void setBorrar(String borrar) {
		this.borrar = borrar;
	}

	public List<CestasNavidadArticulo> getLstBorrados() {
		return lstBorrados;
	}

	public void setLstBorrados(List<CestasNavidadArticulo> lstBorrados) {
		this.lstBorrados = lstBorrados;
	}

	public List<CestasNavidadArticulo> getLstModificados() {
		return lstModificados;
	}

	public void setLstModificados(List<CestasNavidadArticulo> lstModificados) {
		this.lstModificados = lstModificados;
	}

	public List<CestasNavidadArticulo> getLstNuevos() {
		return lstNuevos;
	}

	public void setLstNuevos(List<CestasNavidadArticulo> lstNuevos) {
		this.lstNuevos = lstNuevos;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<CestasNavidadArticulo> getLstArticulos() {
		return lstArticulos;
	}

	public void setLstArticulos(List<CestasNavidadArticulo> lstArticulos) {
		this.lstArticulos = lstArticulos;
	}

	public boolean isTieneTexto() {
		return tieneTexto;
	}

	public void setTieneTexto(boolean tieneTexto) {
		this.tieneTexto = tieneTexto;
	}
}