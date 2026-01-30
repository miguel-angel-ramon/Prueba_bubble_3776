/**
 * 
 */
package es.eroski.misumi.model.pda;

import es.eroski.misumi.model.ImagenComercial;

/**
 * @author BICUGUAL
 *
 */
public class PdaCapturaRestosForm {

	//Atributos de T_MIS_CAPTURA_RESTOS
	PdaCapturaResto capturaResto;

	//Valores que no se recogen desde la tabla T_MIS_CAPTURA_RESTOS
	private String tieneFoto;
	
	private PdaCapturaRestosStock stockWs;
	private String mmc;
	private String calculoCC;
	private PdaCapturaRestosOferta oferta;
	private Long totalPages;

	public PdaCapturaResto getCapturaResto() {
		return capturaResto;
	}

	public void setCapturaResto(PdaCapturaResto capturaResto) {
		this.capturaResto = capturaResto;
	}

	public Long getCurrentPage(){
		return capturaResto != null ? capturaResto.getRowNum() : null;  
	}

	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	public String getTieneFoto() {
		return tieneFoto;
	}

	public void setTieneFoto(String tieneFoto) {
		this.tieneFoto = tieneFoto;
	}

	public String getMmc() {
		return mmc;
	}

	public void setMmc(String mmc) {
		this.mmc = mmc;
	}

	public String getCalculoCC() {
		return calculoCC;
	}

	public void setCalculoCC(String calculoCC) {
		this.calculoCC = calculoCC;
	}

	public PdaCapturaRestosStock getStockWs() {
		return stockWs;
	}

	public void setStockWs(PdaCapturaRestosStock stockWs) {
		this.stockWs = stockWs;
	}

	public PdaCapturaRestosOferta getOferta() {
		return oferta;
	}

	public void setOferta(PdaCapturaRestosOferta oferta) {
		this.oferta = oferta;
	}
	
}
