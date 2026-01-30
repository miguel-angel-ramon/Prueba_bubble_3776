package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaDatosCabecera implements Serializable{

	private static final long serialVersionUID = 1L;

	private String codArtCab;
	private String codArtCaprabo;
	private String descArtCab;
	private String origenConsulta;
	private String descArtCabConCodigo; //Utilizado para formatear la descripción de pantalla
	
	//Sección utilizada para filtro en pantallas
	private String seccion;
	
	//Proveedor utilizado para filtro en pantallas
	private String proveedor;

	//Control por si viene de Inventario Propuesto
	private String origenGISAE;
	
	//Control para el icono impresora de la cabecera, si es igual a SI, aparecera en verde si no en gris
	private String impresoraActiva;

	private Integer numEti; //Utilizado para imprimir etiqueta
	private boolean existeMapNumEti = false; //Utilizado para imprimir etiqueta

	/**
	 * Empleado en las cabeceras de las pantalla de PackingList.
	 */
	private String matricula;

	private String fechaHasta;
	
	private String fechaDesde;
	 
	private Boolean soyPalet;
	 
	private String urlPalet;

	public PdaDatosCabecera() {
	    super();
	}

	public PdaDatosCabecera(String codArtCab, String descArtCab) {
	    super();
	    this.codArtCab=codArtCab;
	    this.descArtCab=descArtCab;
	}
	
	public String getCodArtCab() {
		return this.codArtCab;
	}

	public void setCodArtCab(String codArtCab) {
		this.codArtCab = codArtCab;
	}
	
	public String getCodArtCaprabo() {
		return codArtCaprabo;
	}

	public void setCodArtCaprabo(String codArtCaprabo) {
		this.codArtCaprabo = codArtCaprabo;
	}

	public String getDescArtCab() {
		return this.descArtCab;
	}

	public void setDescArtCab(String descArtCab) {
		this.descArtCab = descArtCab;
	}

	public String getOrigenConsulta() {
		return this.origenConsulta;
	}

	public void setOrigenConsulta(String origenConsulta) {
		this.origenConsulta = origenConsulta;
	}
	
	public String getDescArtCabConCodigo() {
		StringBuffer descConCodigo = new StringBuffer();
		
		if (this.codArtCab != null){
			descConCodigo.append(this.codArtCab);
		}
		if (this.descArtCab != null){
			descConCodigo.append("-");
			descConCodigo.append(this.descArtCab);
		}
		return descConCodigo.toString();
	}
	
	public String getDescArtCabConCodigoGenerico() {
		StringBuffer descConCodigo = new StringBuffer();
		
		if (this.codArtCaprabo != null){
			descConCodigo.append(this.codArtCaprabo);
		}
		if (this.descArtCab != null){
			descConCodigo.append("-");
			descConCodigo.append(this.descArtCab);
		}
		return descConCodigo.toString();
	}

	public void setDescArtCabConCodigo(String descArtCabConCodigo) {
		this.descArtCabConCodigo = descArtCabConCodigo;
	}

	public String getSeccion() {
		return this.seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getOrigenGISAE() {
		return origenGISAE;
	}

	public void setOrigenGISAE(String origenGISAE) {
		this.origenGISAE = origenGISAE;
	}
	
	public String getImpresoraActiva() {
		return impresoraActiva;
	}

	public void setImpresoraActiva(String impresoraActiva) {
		this.impresoraActiva = impresoraActiva;
	}

	public Integer getNumEti() {
		return numEti;
	}

	public void setNumEti(Integer numEti) {
		this.numEti = numEti;
	}

	public boolean isExisteMapNumEti() {
		return existeMapNumEti;
	}

	public void setExisteMapNumEti(boolean existeMapNumEti) {
		this.existeMapNumEti = existeMapNumEti;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Boolean getSoyPalet() {
		return soyPalet;
	}

	public void setSoyPalet(Boolean soyPalet) {
		this.soyPalet = soyPalet;
	}

	public String getUrlPalet() {
		return urlPalet;
	}

	public void setUrlPalet(String urlPalet) {
		this.urlPalet = urlPalet;
	}
}