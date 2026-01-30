/**
 * 
 */
package es.eroski.misumi.model;


/**
 * Objeto de maniobra para capturar la informacion a exportar en Excel
 * @author BICUGUAL
 *
 */
public class DetalladoMostradorExcel {
	
	private String centro;
	private String seccion;
	private String categoria;
	private String subcategoria;
	private String segmento;
	private String soloVenta;
	private String gamaLocal;
	private String diaEspejo;
	private String iniCosto;
	private String finCosto;
	private String iniPvp;
	private String finPvp;
	private String iniCajas;
	private String finCajas;
	private String ventasEspejo;
	private String fecEntrega;
	private String fecPedido;
	
	public String getCentro() {
		return centro;
	}
	public void setCentro(String centro) {
		this.centro = centro;
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
	public String getDiaEspejo() {
		return diaEspejo;
	}
	public void setDiaEspejo(String diaEspejo) {
		this.diaEspejo = diaEspejo;
	}
	public String getIniCosto() {
		return iniCosto;
	}
	public void setIniCosto(String iniCosto) {
		this.iniCosto = iniCosto;
	}
	public String getFinCosto() {
		return finCosto;
	}
	public void setFinCosto(String finCosto) {
		this.finCosto = finCosto;
	}
	public String getIniPvp() {
		return iniPvp;
	}
	public void setIniPvp(String iniPvp) {
		this.iniPvp = iniPvp;
	}
	public String getFinPvp() {
		return finPvp;
	}
	public void setFinPvp(String finPvp) {
		this.finPvp = finPvp;
	}
	public String getIniCajas() {
		return iniCajas;
	}
	public void setIniCajas(String iniCajas) {
		this.iniCajas = iniCajas;
	}
	public String getFinCajas() {
		return finCajas;
	}
	public void setFinCajas(String finCajas) {
		this.finCajas = finCajas;
	}
	public String getVentasEspejo() {
		return ventasEspejo;
	}
	public void setVentasEspejo(String ventasEspejo) {
		this.ventasEspejo = ventasEspejo;
	}
	public String getFecEntrega() {
		return fecEntrega;
	}
	public void setFecEntrega(String fecEntrega) {
		this.fecEntrega = fecEntrega;
	}
	public String getFecPedido() {
		return fecPedido;
	}
	public void setFecPedido(String fecPedido) {
		this.fecPedido = fecPedido;
	}
}
