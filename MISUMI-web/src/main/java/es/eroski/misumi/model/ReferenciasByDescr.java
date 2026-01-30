/**
 * 
 */
package es.eroski.misumi.model;

/**
 * Modelo para registros de referencias encontradas en busquedas por descripcion
 * P49634
 * @author BICUGUAL
 */
public class ReferenciasByDescr {

	private Long codArticulo;
	private Long grupo1;
	private Long nivel_lote;
	private String descripcion;
	private String activa;
	private String catalogo;
	private Double unidadesCaja;
	
	//Textil
	private String color;
	private String talla;
	private String modelo_proveedor;
	
	private Long nivel_mod_prov;
	
	private String tieneFoto;
	
	//Página de acceso a la consulta, sirve para controlar desde el js desde que pagina se ha solicitado la consulta.
	private String paginaConsulta;
	
	private String numOrden;
	public ReferenciasByDescr() {
		super();
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
	}

	public String getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	public Double getUnidadesCaja() {
		return unidadesCaja;
	}

	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}
	
	public Long getGrupo1() {
		return grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public Long getNivel_lote() {
		return nivel_lote;
	}

	public void setNivel_lote(Long nivel_lote) {
		this.nivel_lote = nivel_lote;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getModelo_proveedor() {
		return modelo_proveedor;
	}

	public void setModelo_proveedor(String modelo_proveedor) {
		this.modelo_proveedor = modelo_proveedor;
	}	
	
	public Long getNivel_mod_prov() {
		return nivel_mod_prov;
	}

	public void setNivel_mod_prov(Long nivel_mod_prov) {
		this.nivel_mod_prov = nivel_mod_prov;
	}

	public String getPaginaConsulta() {
		return paginaConsulta;
	}

	public void setPaginaConsulta(String paginaConsulta) {
		this.paginaConsulta = paginaConsulta;
	}

	public String getTieneFoto() {
		return tieneFoto;
	}

	public void setTieneFoto(String tieneFoto) {
		this.tieneFoto = tieneFoto;
	}

	public String getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(String numOrden) {
		this.numOrden = numOrden;
	}			
}
