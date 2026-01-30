/**
 * 
 */
package es.eroski.misumi.model.pda;

/**
 * @author BICUGUAL
 *
 */
public class PdaSacadaRestosForm {

	//Atributos de T_MIS_CAPTURA_RESTOS
	PdaSacadaResto sacadaResto;

	//Valores que no se recogen desde la tabla T_MIS_CAPTURA_RESTOS
	private String tieneFoto;
	
	private PdaSacadaRestosStock stockWs;
	private String mmc;
	private String calculoCC;
	private PdaSacadaRestosOferta oferta;
	private Long totalPages;

	public PdaSacadaResto getSacadaResto() {
		return sacadaResto;
	}

	public void setSacadaResto(PdaSacadaResto sacadaResto) {
		this.sacadaResto = sacadaResto;
	}

	public PdaSacadaRestosStock getStockWs() {
		return stockWs;
	}

	public PdaSacadaRestosOferta getOferta() {
		return oferta;
	}

	public void setStockWs(PdaSacadaRestosStock stockWs) {
		this.stockWs = stockWs;
	}

	public void setOferta(PdaSacadaRestosOferta oferta) {
		this.oferta = oferta;
	}

	public Long getCurrentPage(){
		return sacadaResto != null ? sacadaResto.getRowNum() : null;  
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

}
