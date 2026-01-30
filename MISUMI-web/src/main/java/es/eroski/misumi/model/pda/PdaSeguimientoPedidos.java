package es.eroski.misumi.model.pda;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.ValoresStock;

public class PdaSeguimientoPedidos implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private String descArt;
	private String stockActual;
	private String pedidoActivo;
	private String mostrarFFPP;
	private Long codArtRel;
	private Long cantHoy;
	private Long cantFutura;
	private List<PdaUltimosEnvios> listaEnvios;
	private String esError;
	private String stockActivo;
	private String MMC;
	
	//Para el tratamiento Movimiento Continuo de Gama (Sustituida por o Sustituta de)
	private String mostrarSustARef;
	private String mostrarSustPorRef;
	private Long sustituidaPor; 
	private Long sustitutaDe; 
	private String implantacion;
	private Date fechaActivacion;
	private Date fechaGen;
	private String strFechaGen;
	private boolean mostrarFechaGen; //Petición 55001
	private String flgColorImplantacion;
	private String diasStock;
	
	//Valores del stock
	private ValoresStock valoresStock;

	//Deposito Brita
	private String flgDepositoBrita;
	
	//Referencia por catálogo
	private String flgPorCatalogo;
	
	//Pet. 54867
	private String tipoRotacion;

	private String motivoCaprabo;
	private TMisMcgCaprabo tMisMcgCaprabo;
	
	
	
	private String pedir;
	
	private String calculoCC;
	
	//Franquicidados - Foto
	private String tieneFoto;
	
	//MISUMI-171
	private String esUSS;
	
	//Vegalsa
	private boolean tratamientoVegalsa;
	
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
	public List<PdaUltimosEnvios> getListaEnvios() {
		return this.listaEnvios;
	}
	public void setListaEnvios(List<PdaUltimosEnvios> listaEnvios) {
		this.listaEnvios = listaEnvios;
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
	public ValoresStock getValoresStock() {
		return valoresStock;
	}
	public void setValoresStock(ValoresStock valoresStock) {
		this.valoresStock = valoresStock;
	}
	public String getMostrarSustARef() {
		return mostrarSustARef;
	}
	public void setMostrarSustARef(String mostrarSustARef) {
		this.mostrarSustARef = mostrarSustARef;
	}
	public String getMostrarSustPorRef() {
		return mostrarSustPorRef;
	}
	public void setMostrarSustPorRef(String mostrarSustPorRef) {
		this.mostrarSustPorRef = mostrarSustPorRef;
	}
	public Long getSustituidaPor() {
		return sustituidaPor;
	}
	public void setSustituidaPor(Long sustituidaPor) {
		this.sustituidaPor = sustituidaPor;
	}
	public Long getSustitutaDe() {
		return sustitutaDe;
	}
	public void setSustitutaDe(Long sustitutaDe) {
		this.sustitutaDe = sustitutaDe;
	}
	public String getImplantacion() {
		return implantacion;
	}
	public void setImplantacion(String implantacion) {
		this.implantacion = implantacion;
	}
	public Date getFechaActivacion() {
		return fechaActivacion;
	}
	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	public Date getFechaGen() {
		return fechaGen;
	}
	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
	public String getStrFechaGen() {
		return strFechaGen;
	}
	public void setStrFechaGen(String strFechaGen) {
		this.strFechaGen = strFechaGen;
	}
	public boolean isMostrarFechaGen() {
		return mostrarFechaGen;
	}
	public void setMostrarFechaGen(boolean mostrarFechaGen) {
		this.mostrarFechaGen = mostrarFechaGen;
	}
	public String getFlgColorImplantacion() {
		return flgColorImplantacion;
	}
	public void setFlgColorImplantacion(String flgColorImplantacion) {
		this.flgColorImplantacion = flgColorImplantacion;
	}
	public String getDiasStock() {
		return diasStock;
	}
	public void setDiasStock(String diasStock) {
		this.diasStock = diasStock;
	}
	public String getFlgDepositoBrita() {
		return this.flgDepositoBrita;
	}
	public void setFlgDepositoBrita(String flgDepositoBrita) {
		this.flgDepositoBrita = flgDepositoBrita;
	}
	public String getFlgPorCatalogo() {
		return this.flgPorCatalogo;
	}
	public void setFlgPorCatalogo(String flgPorCatalogo) {
		this.flgPorCatalogo = flgPorCatalogo;
	}
	
	public String getTipoRotacion() {
		return tipoRotacion;
	}
	public void setTipoRotacion(String tipoRotacion) {
		this.tipoRotacion = tipoRotacion;
	}
	
	public String getMotivoCaprabo() {
		return motivoCaprabo;
	}
	public void setMotivoCaprabo(String motivoCaprabo) {
		this.motivoCaprabo = motivoCaprabo;
	}
	public TMisMcgCaprabo gettMisMcgCaprabo() {
		return tMisMcgCaprabo;
	}
	public void settMisMcgCaprabo(TMisMcgCaprabo tMisMcgCaprabo) {
		this.tMisMcgCaprabo = tMisMcgCaprabo;
	}
	public String getPedir() {
		return pedir;
	}
	public void setPedir(String pedir) {
		this.pedir = pedir;
	}
	public String getCalculoCC() {
		return calculoCC;
	}
	public void setCalculoCC(String calculoCC) {
		this.calculoCC = calculoCC;
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
	public boolean isTratamientoVegalsa() {
		return tratamientoVegalsa;
	}
	public void setTratamientoVegalsa(boolean tratamientoVegalsa) {
		this.tratamientoVegalsa = tratamientoVegalsa;
	}	
}