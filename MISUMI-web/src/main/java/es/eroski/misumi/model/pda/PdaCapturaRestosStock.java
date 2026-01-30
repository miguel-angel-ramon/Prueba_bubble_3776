/**
 * 
 */
package es.eroski.misumi.model.pda;

/**
 * @author BICUGUAL
 *
 */
public class PdaCapturaRestosStock {

	private Double stock;
	private String msgError;
	private String calculoCC;
	
	public Double getStock() {
		return stock;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}
	public String getMsgError() {
		return msgError;
	}
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
	public String getCalculoCC() {
		return calculoCC;
	}
	public void setCalculoCC(String calculoCC) {
		this.calculoCC = calculoCC;
	}
	
	
	
}
