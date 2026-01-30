/**
 * 
 */
package es.eroski.misumi.model.pda;

import es.eroski.misumi.model.ImagenComercial;

/**
 * @author BICUGUAL
 *
 */
public class PdaCapturaResto {

	private Long rowNum;
	//Atributos tabla T_MIS_CAPTURA_RESTOS
	private Long codCentro;
	private Long codArt;
	private String flgModifStock;
	private Double stockAntes;
	private Double stockDespues;
	private String flgModifAlto;
	private String flgModifAncho;
	private String flgModifFacing;
	private Long facingAntes;
	private Long facingDespues;
	private Long capacidadAntes;
	private Long capacidadDespues;
	private String flgCapacidadIncorreta;
	private String flgFondoBalda;
	private String flgMuyAltaRotacion;
	private String rotacion;
	private Double unidadesCaja;
	private String tipoAprov;
	private String empuje;
	private Long codPlat;
	private Double coeficienteReposicion;
	private Integer facingExcedenteMaximo;
	private Integer metodoCalculoIMC;
	private Long tipoReferencia;
	private Boolean modificarLastUpdateDate;
	
	
	private Long facingAltoAntes;
	private Long facingAnchoAntes;
	private Long facingAltoDespues;
	private Long facingAnchoDespues;
	
	private String descripArt;
	
	// Valor para identificar si la referencia es de Vegalsa
	private Boolean tratamientoVegalsa;
	// Imagen comercial de la referencia
	ImagenComercial imagenComercial;
	// Color del link de Implatanción
	String flgColorImplantacion;
	// Flag para comprobar si el pedido está activo (S/N)
	String pedidoActivo;
	// Capacidad1 y Facing1
	String capacidad1;
	String facing1;
	
	// Implantacion
	String implantacion;
	
	// Pedir
	String pedir;
	
	// Numero a mostrar en capacidad facing 1
	Integer capacidadFacing;
	
	public Long getRowNum() {
		return rowNum;
	}
	
	public void setRowNum(Long rowNum) {
		this.rowNum = rowNum;
	}
	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	
	public Long getCodArt() {
		return codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getFlgModifStock() {
		return flgModifStock;
	}

	public void setFlgModifStock(String flgModifStock) {
		this.flgModifStock = flgModifStock;
	}

	public Double getStockAntes() {
		return stockAntes;
	}

	public void setStockAntes(Double stockAntes) {
		this.stockAntes = stockAntes;
	}

	public Double getStockDespues() {
		return stockDespues;
	}

	public void setStockDespues(Double stockDespues) {
		this.stockDespues = stockDespues;
	}

	public String getFlgModifAlto() {
		return flgModifAlto;
	}

	public void setFlgModifAlto(String flgModifAlto) {
		this.flgModifAlto = flgModifAlto;
	}

	public String getFlgModifAncho() {
		return flgModifAncho;
	}

	public void setFlgModifAncho(String flgModifAncho) {
		this.flgModifAncho = flgModifAncho;
	}

	public String getFlgModifFacing() {
		return flgModifFacing;
	}

	public void setFlgModifFacing(String flgModifFacing) {
		this.flgModifFacing = flgModifFacing;
	}

	public Long getFacingAntes() {
		return facingAntes;
	}

	public void setFacingAntes(Long facingAntes) {
		this.facingAntes = facingAntes;
	}

	public Long getFacingDespues() {
		return facingDespues;
	}

	public void setFacingDespues(Long facingDespues) {
		this.facingDespues = facingDespues;
	}

	public Long getCapacidadAntes() {
		return capacidadAntes;
	}

	public void setCapacidadAntes(Long capacidadAntes) {
		this.capacidadAntes = capacidadAntes;
	}

	public Long getCapacidadDespues() {
		return capacidadDespues;
	}

	public void setCapacidadDespues(Long capacidadDespues) {
		this.capacidadDespues = capacidadDespues;
	}

	public String getFlgCapacidadIncorreta() {
		return flgCapacidadIncorreta;
	}

	public void setFlgCapacidadIncorreta(String flgCapacidadIncorreta) {
		this.flgCapacidadIncorreta = flgCapacidadIncorreta;
	}

	public String getFlgFondoBalda() {
		return flgFondoBalda;
	}

	public void setFlgFondoBalda(String flgFondoBalda) {
		this.flgFondoBalda = flgFondoBalda;
	}

	public String getFlgMuyAltaRotacion() {
		return flgMuyAltaRotacion;
	}

	public void setFlgMuyAltaRotacion(String flgMuyAltaRotacion) {
		this.flgMuyAltaRotacion = flgMuyAltaRotacion;
	}

	public String getRotacion() {
		return rotacion;
	}

	public void setRotacion(String rotacion) {
		this.rotacion = rotacion;
	}

	public Double getUnidadesCaja() {
		return unidadesCaja;
	}

	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}

	public String getTipoAprov() {
		return tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public String getEmpuje() {
		return empuje;
	}

	public void setEmpuje(String empuje) {
		this.empuje = empuje;
	}

	public Long getCodPlat() {
		return codPlat;
	}

	public void setCodPlat(Long codPlat) {
		this.codPlat = codPlat;
	}

	public Double getCoeficienteReposicion() {
		return coeficienteReposicion;
	}

	public void setCoeficienteReposicion(Double coeficienteReposicion) {
		this.coeficienteReposicion = coeficienteReposicion;
	}

	public Integer getFacingExcedenteMaximo() {
		return facingExcedenteMaximo;
	}

	public void setFacingExcedenteMaximo(Integer facingExcedenteMaximo) {
		this.facingExcedenteMaximo = facingExcedenteMaximo;
	}

	public Integer getMetodoCalculoIMC() {
		return metodoCalculoIMC;
	}

	public void setMetodoCalculoIMC(Integer metodoCalculoIMC) {
		this.metodoCalculoIMC = metodoCalculoIMC;
	}

	public Long getTipoReferencia() {
		return tipoReferencia;
	}

	public void setTipoReferencia(Long tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

	public String getDescripArt() {
		return descripArt;
	}

	public void setDescripArt(String descripArt) {
		this.descripArt = descripArt;
	}

	public Boolean getModificarLastUpdateDate() {
		return modificarLastUpdateDate;
	}

	public void setModificarLastUpdateDate(Boolean modificarLastUpdateDate) {
		this.modificarLastUpdateDate = modificarLastUpdateDate;
	}

	public Boolean getTratamientoVegalsa() {
		return tratamientoVegalsa;
	}

	public void setTratamientoVegalsa(Boolean tratamientoVegalsa) {
		this.tratamientoVegalsa = tratamientoVegalsa;
	}

	public ImagenComercial getImagenComercial() {
		return imagenComercial;
	}

	public void setImagenComercial(ImagenComercial imagenComercial) {
		this.imagenComercial = imagenComercial;
	}

	public String getFlgColorImplantacion() {
		return flgColorImplantacion;
	}

	public void setFlgColorImplantacion(String flgColorImplantacion) {
		this.flgColorImplantacion = flgColorImplantacion;
	}

	public String getPedidoActivo() {
		return pedidoActivo;
	}

	public void setPedidoActivo(String pedidoActivo) {
		this.pedidoActivo = pedidoActivo;
	}

	public String getCapacidad1() {
		return capacidad1;
	}

	public void setCapacidad1(String capacidad1) {
		this.capacidad1 = capacidad1;
	}

	public String getFacing1() {
		return facing1;
	}

	public void setFacing1(String facing1) {
		this.facing1 = facing1;
	}

	public String getImplantacion() {
		return implantacion;
	}

	public void setImplantacion(String implantacion) {
		this.implantacion = implantacion;
	}

	public String getPedir() {
		return pedir;
	}

	public void setPedir(String pedir) {
		this.pedir = pedir;
	}

	public Integer getCapacidadFacing() {
		return capacidadFacing;
	}

	public void setCapacidadFacing(Integer capacidadFacing) {
		this.capacidadFacing = capacidadFacing;
	}

	public Long getFacingAltoAntes() {
		return facingAltoAntes;
	}

	public void setFacingAltoAntes(Long facingAltoAntes) {
		this.facingAltoAntes = facingAltoAntes;
	}

	public Long getFacingAnchoAntes() {
		return facingAnchoAntes;
	}

	public void setFacingAnchoAntes(Long facingAnchoAntes) {
		this.facingAnchoAntes = facingAnchoAntes;
	}

	public Long getFacingAltoDespues() {
		return facingAltoDespues;
	}

	public void setFacingAltoDespues(Long facingAltoDespues) {
		this.facingAltoDespues = facingAltoDespues;
	}

	
	public Long getFacingAnchoDespues() {
		return facingAnchoDespues;
	}

	public void setFacingAnchoDespues(Long facingAnchoDespues) {
		this.facingAnchoDespues = facingAnchoDespues;
	}
	
}
