/**
 * 
 */
package es.eroski.misumi.model;

/**
 * Bean de maniobra para cargar los filtros de consulta para las busquedas de referencias por descripcion. 
 * P49634
 * @author BICUGUAL
 */
public class QueryReferenciasByDescr {

	private Long codCentro;
	private String codArticulo;
	private String descripcion;
	private Long codArea;
	private Long codSeccion;
	private Long codCategoria;
	private Boolean altaCatalogo;
	private Boolean activo;
	private Boolean modeloProveedor; 
	private Boolean ean;
	private Boolean alfaNumerico;
	private String talla;
	private String color;
	
	//Página de acceso a la consulta, sirve para controlar desde el js desde que pagina se ha solicitado la consulta.
	private String paginaConsulta;
	
	private String flgNivelModProv;

	private String flgCategoria;
	
	private Long codCentroRelacionado;
	
	private String numOrden;
	public QueryReferenciasByDescr() {
		super();
	}

	public QueryReferenciasByDescr(Long codCentro, String descripcion,
			Long codSeccion, Long categoria) {
		super();
		this.codCentro = codCentro;
		this.descripcion = descripcion;
		this.codSeccion = codSeccion;
		this.codCategoria = codCategoria;
	}

	public Long getCodCentro() {
		return codCentro;
	}


	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Long getCodArea() {
		return codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public Long getCodSeccion() {
		return codSeccion;
	}


	public void setCodSeccion(Long codSeccion) {
		this.codSeccion = codSeccion;
	}
	
	public Long getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(Long codCategoria) {
		this.codCategoria = codCategoria;
	}

	public Boolean getAltaCatalogo() {
		return altaCatalogo;
	}

	public void setAltaCatalogo(Boolean altaCatalogo) {
		this.altaCatalogo = altaCatalogo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(Boolean modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}

	public String getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(String codArticulo) {
		this.codArticulo = codArticulo;
	}


	public Boolean getAlfaNumerico() {
		return alfaNumerico;
	}

	public void setAlfaNumerico(Boolean alfaNumerico) {
		this.alfaNumerico = alfaNumerico;
	}

	public Boolean getEan() {
		return ean;
	}

	public void setEan(Boolean ean) {
		this.ean = ean;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPaginaConsulta() {
		return paginaConsulta;
	}

	public void setPaginaConsulta(String paginaConsulta) {
		this.paginaConsulta = paginaConsulta;
	}

	public String getFlgNivelModProv() {
		return flgNivelModProv;
	}

	public void setFlgNivelModProv(String flgNivelModProv) {
		this.flgNivelModProv = flgNivelModProv;
	}

	public String getFlgCategoria() {
		return flgCategoria;
	}

	public void setFlgCategoria(String flgCategoria) {
		this.flgCategoria = flgCategoria;
	}

	public Long getCodCentroRelacionado() {
		return codCentroRelacionado;
	}

	public void setCodCentroRelacionado(Long codCentroRelacionado) {
		this.codCentroRelacionado = codCentroRelacionado;
	}

	public String getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(String numOrden) {
		this.numOrden = numOrden;
	}
}
