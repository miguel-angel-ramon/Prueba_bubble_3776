package es.eroski.misumi.model.pda;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ValoresStock;

public class PdaMovStocks implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private String descArt;
	private String stockActual;
	private String pedidoActivo;
	private String mostrarFFPP;
	private Long codArtRel;
	private Long cantHoy;
	private Long cantFutura;
	private List<PdaUltimosMovStocks> listaMovStocks;
	private String esError;
	private String stockActivo;
	private String MMC;
	

	private String page; //Current page of the query
	private String total; //Total pages for the query
	private String records; //Total number of records for the query
	
	
	
	//Valores del stock
	private ValoresStock valoresStock;
	
	
	//Pet. 54867
	private String tipoRotacion;

	//Franquicidados - Foto
	private String tieneFoto;
	
	//MISUMI-171
	private String esUSS;
	
	// Para PRUEBAS. Se añade una nueva propiedad.
	private VDatosDiarioArt datosDiarioArt;
		
	public Long getCodArt() {
		return this.codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public String getDescArt() {
		return this.descArt;
	}
	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}
	public String getStockActual() {
		return this.stockActual;
	}
	public void setStockActual(String stockActual) {
		this.stockActual = stockActual;
	}
	public String getPedidoActivo() {
		return this.pedidoActivo;
	}
	public void setPedidoActivo(String pedidoActivo) {
		this.pedidoActivo = pedidoActivo;
	}
	public String getMostrarFFPP() {
		return this.mostrarFFPP;
	}
	public void setMostrarFFPP(String mostrarFFPP) {
		this.mostrarFFPP = mostrarFFPP;
	}
	public Long getCodArtRel() {
		return this.codArtRel;
	}
	public void setCodArtRel(Long codArtRel) {
		this.codArtRel = codArtRel;
	}
	public Long getCantHoy() {
		return this.cantHoy;
	}
	public void setCantHoy(Long cantHoy) {
		this.cantHoy = cantHoy;
	}
	public Long getCantFutura() {
		return this.cantFutura;
	}
	public void setCantFutura(Long cantFutura) {
		this.cantFutura = cantFutura;
	}
	public List<PdaUltimosMovStocks> getListaMovStocks() {
		return this.listaMovStocks;
	}
	public void setListaMovStocks(List<PdaUltimosMovStocks> listaMovStocks) {
		this.listaMovStocks = listaMovStocks;
	}
	public String getEsError() {
		return this.esError;
	}
	public void setEsError(String esError) {
		this.esError = esError;
	}
	public String getStockActivo() {
		return stockActivo;
	}
	public void setStockActivo(String stockActivo) {
		this.stockActivo = stockActivo;
	}
	public String getMMC() {
		return MMC;
	}
	public void setMMC(String mMC) {
		this.MMC = mMC;
	}
	public String getPage() {
		return this.page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getTotal() {
		return this.total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getRecords() {
		return this.records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	
	public ValoresStock getValoresStock() {
		return valoresStock;
	}
	public void setValoresStock(ValoresStock valoresStock) {
		this.valoresStock = valoresStock;
	}
	
	public String getTipoRotacion() {
		return tipoRotacion;
	}
	public void setTipoRotacion(String tipoRotacion) {
		this.tipoRotacion = tipoRotacion;
	}
	public String getTieneFoto() {
		return tieneFoto;
	}
	public void setTieneFoto(String tieneFoto) {
		this.tieneFoto = tieneFoto;
	}
	public String getEsUSS() {
		return esUSS;
	}
	public void setEsUSS(String esUSS) {
		this.esUSS = esUSS;
	}
	public VDatosDiarioArt getDatosDiarioArt() {
		return datosDiarioArt;
	}
	public void setDatosDiarioArt(VDatosDiarioArt datosDiarioArt) {
		this.datosDiarioArt = datosDiarioArt;
	}	
}