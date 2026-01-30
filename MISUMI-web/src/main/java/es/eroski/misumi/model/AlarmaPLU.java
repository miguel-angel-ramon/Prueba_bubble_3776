package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class AlarmaPLU implements Serializable {
	
	/**
	 * Atributos
	 */
	private static final long serialVersionUID = 1L;
	
	private Date fechaGen;
	private Long codArticulo;
	private Long codCentro;
	private Long grupo1;
	private String descGrupo1;
	private Long grupo2;
	private String descGrupo2;
	private Long grupo3;
	private String descGrupo3;
	private Long grupo4;
	private Long grupo5;
	private String denomSegmento;
	private String denominacion;
	private String formato;
	private Double tipoFormato;
	private String marca;
	private Long balanza;
	private Long grupoBalanza;
	private String descGrupoBalanza;
	private Long plu;
	private Long plu_1;
	private Long plu_2;
	private Long plu_3;
	
	private Long pluAnt;
	private Long diasCaducidad;
	private String mmc;
	private String eb;
	private Date fechaUltimaVenta;
	private Double stock;
	private Double stockBandejas;
	private String albaranes;
	private String compraVenta;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private String flgEnviadoGISAE;
	private Long estadoGrid;
	private Long imprimirEtiquetas;
	private Long codError;
	private String msgError;
	private String denominacionBalanza;
	private String flgVariable;
	
	private Long pluOriginal;
	private Long pluOriginal_1;
	private Long pluOriginal_2;
	private Long pluOriginal_3;
	
	private Long cajasPendientesRecibir;
	
	private Boolean stockModificado;
	
	private String lstOtrasAgrupacionesBalanza;
	
	/**
	 * Constructor
	 */
    public AlarmaPLU() {
		super();
	}  

	public Date getFechaGen() {
		return fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	/**
	 * Getters and Setters
	 */

	public Long getCodArticulo() {
		return codArticulo;
	}



	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}



	public Long getCodCentro() {
		return codCentro;
	}



	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}



	public Long getGrupo1() {
		return grupo1;
	}



	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}



	public Long getGrupo2() {
		return grupo2;
	}



	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}



	public Long getGrupo3() {
		return grupo3;
	}



	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}



	public Long getGrupo4() {
		return grupo4;
	}



	public void setGrupo4(Long grupo4) {
		this.grupo4 = grupo4;
	}



	public Long getGrupo5() {
		return grupo5;
	}



	public void setGrupo5(Long grupo5) {
		this.grupo5 = grupo5;
	}



	public String getDenominacion() {
		return denominacion;
	}



	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}



	public String getFormato() {
		return formato;
	}



	public void setFormato(String formato) {
		this.formato = formato;
	}



	public Double getTipoFormato() {
		return tipoFormato;
	}



	public void setTipoFormato(Double tipoFormato) {
		this.tipoFormato = tipoFormato;
	}



	public String getMarca() {
		return marca;
	}



	public void setMarca(String marca) {
		this.marca = marca;
	}



	public Long getBalanza() {
		return balanza;
	}



	public void setBalanza(Long balanza) {
		this.balanza = balanza;
	}



	public Long getGrupoBalanza() {
		return grupoBalanza;
	}



	public void setGrupoBalanza(Long grupoBalanza) {
		this.grupoBalanza = grupoBalanza;
	}



	public Long getPlu() {
		return plu;
	}



	public void setPlu(Long plu) {
		this.plu = plu;
	}



	public Long getPluAnt() {
		return pluAnt;
	}



	public void setPluAnt(Long pluAnt) {
		this.pluAnt = pluAnt;
	}



	public Long getDiasCaducidad() {
		return diasCaducidad;
	}



	public void setDiasCaducidad(Long diasCaducidad) {
		this.diasCaducidad = diasCaducidad;
	}



	public String getMmc() {
		return mmc;
	}



	public void setMmc(String mmc) {
		this.mmc = mmc;
	}



	public String getEb() {
		return eb;
	}



	public void setEb(String eb) {
		this.eb = eb;
	}



	public Date getFechaUltimaVenta() {
		return fechaUltimaVenta;
	}



	public void setFechaUltimaVenta(Date fechaUltimaVenta) {
		this.fechaUltimaVenta = fechaUltimaVenta;
	}



	public Double getStock() {
		return stock;
	}



	public void setStock(Double stock) {
		this.stock = stock;
	}



	public Double getStockBandejas() {
		return stockBandejas;
	}



	public void setStockBandejas(Double stockBandejas) {
		this.stockBandejas = stockBandejas;
	}



	public String getAlbaranes() {
		return albaranes;
	}



	public void setAlbaranes(String albaranes) {
		this.albaranes = albaranes;
	}



	public String getCompraVenta() {
		return compraVenta;
	}



	public void setCompraVenta(String compraVenta) {
		this.compraVenta = compraVenta;
	}



	public Date getFechaCreacion() {
		return fechaCreacion;
	}



	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}



	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}



	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}



	public String getFlgEnviadoGISAE() {
		return flgEnviadoGISAE;
	}



	public void setFlgEnviadoGISAE(String flgEnviadoGISAE) {
		this.flgEnviadoGISAE = flgEnviadoGISAE;
	}



	public Long getEstadoGrid() {
		return estadoGrid;
	}



	public void setEstadoGrid(Long estadoGrid) {
		this.estadoGrid = estadoGrid;
	}



	public Long getImprimirEtiquetas() {
		return imprimirEtiquetas;
	}



	public void setImprimirEtiquetas(Long imprimirEtiquetas) {
		this.imprimirEtiquetas = imprimirEtiquetas;
	}



	public Long getCodError() {
		return codError;
	}



	public void setCodError(Long codError) {
		this.codError = codError;
	}



	public String getMsgError() {
		return msgError;
	}



	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	public String getDenomSegmento() {
		return denomSegmento;
	}

	public void setDenomSegmento(String denomSegmento) {
		this.denomSegmento = denomSegmento;
	}

	public String getDenominacionBalanza() {
		return denominacionBalanza;
	}

	public void setDenominacionBalanza(String denominacionBalanza) {
		this.denominacionBalanza = denominacionBalanza;
	}

	public String getFlgVariable() {
		return flgVariable;
	}

	public void setFlgVariable(String flgVariable) {
		this.flgVariable = flgVariable;
	}

	public String getDescGrupo1() {
		return descGrupo1;
	}

	public void setDescGrupo1(String descGrupo1) {
		this.descGrupo1 = descGrupo1;
	}

	public String getDescGrupo2() {
		return descGrupo2;
	}

	public void setDescGrupo2(String descGrupo2) {
		this.descGrupo2 = descGrupo2;
	}

	public String getDescGrupo3() {
		return descGrupo3;
	}

	public void setDescGrupo3(String descGrupo3) {
		this.descGrupo3 = descGrupo3;
	}

	public String getDescGrupoBalanza() {
		return descGrupoBalanza;
	}

	public void setDescGrupoBalanza(String descGrupoBalanza) {
		this.descGrupoBalanza = descGrupoBalanza;
	}

	public String getAgrupacion() {
		final String grupo1 = String.valueOf(getGrupo1());
		final String grupo2 = String.valueOf(getGrupo2());
		final String grupo3 = String.valueOf(getGrupo3());
		final String grupo4 = String.valueOf(getGrupo4());
		final String grupo5 = String.valueOf(getGrupo5());
		final String output = grupo1 + "-" + grupo2 + "-" + grupo3 + "-" + grupo4 + "-" + grupo5;
		return output;
	}

	public Long getPluOriginal() {
		return pluOriginal;
	}

	public void setPluOriginal(Long pluOriginal) {
		this.pluOriginal = pluOriginal;
	}

	public Long getCajasPendientesRecibir() {
		return cajasPendientesRecibir;
	}

	public void setCajasPendientesRecibir(Long cajasPendientesRecibir) {
		this.cajasPendientesRecibir = cajasPendientesRecibir;
	}

	@Override
	public String toString() {
		return "AlarmaPLU [fechaGen=" + fechaGen + ", codArticulo=" + codArticulo + ", codCentro=" + codCentro
				+ ", grupo1=" + grupo1 + ", descGrupo1=" + descGrupo1 + ", grupo2=" + grupo2 + ", descGrupo2="
				+ descGrupo2 + ", grupo3=" + grupo3 + ", descGrupo3=" + descGrupo3 + ", grupo4=" + grupo4 + ", grupo5="
				+ grupo5 + ", denomSegmento=" + denomSegmento + ", denominacion=" + denominacion + ", formato="
				+ formato + ", tipoFormato=" + tipoFormato + ", marca=" + marca + ", balanza=" + balanza
				+ ", grupoBalanza=" + grupoBalanza + ", descGrupoBalanza=" + descGrupoBalanza + ", plu=" + plu
				+ ", pluAnt=" + pluAnt + ", diasCaducidad=" + diasCaducidad + ", mmc=" + mmc + ", eb=" + eb
				+ ", fechaUltimaVenta=" + fechaUltimaVenta + ", stock=" + stock + ", stockBandejas=" + stockBandejas
				+ ", albaranes=" + albaranes + ", compraVenta=" + compraVenta + ", fechaCreacion=" + fechaCreacion
				+ ", fechaActualizacion=" + fechaActualizacion + ", flgEnviadoGISAE=" + flgEnviadoGISAE
				+ ", estadoGrid=" + estadoGrid + ", imprimirEtiquetas=" + imprimirEtiquetas + ", codError=" + codError
				+ ", msgError=" + msgError + ", denominacionBalanza=" + denominacionBalanza + ", flgVariable="
				+ flgVariable + ", pluOriginal=" + pluOriginal + ", cajasPendientesRecibir=" + cajasPendientesRecibir
				+ "]";
	}

	public Long getPlu_1() {
		return plu_1;
	}

	public void setPlu_1(Long plu_1) {
		this.plu_1 = plu_1;
	}

	public Long getPlu_2() {
		return plu_2;
	}

	public void setPlu_2(Long plu_2) {
		this.plu_2 = plu_2;
	}

	public Long getPlu_3() {
		return plu_3;
	}

	public void setPlu_3(Long plu_3) {
		this.plu_3 = plu_3;
	}

	public Long getPluOriginal_1() {
		return pluOriginal_1;
	}

	public void setPluOriginal_1(Long pluOriginal_1) {
		this.pluOriginal_1 = pluOriginal_1;
	}

	public Long getPluOriginal_2() {
		return pluOriginal_2;
	}

	public void setPluOriginal_2(Long pluOriginal_2) {
		this.pluOriginal_2 = pluOriginal_2;
	}

	public Long getPluOriginal_3() {
		return pluOriginal_3;
	}

	public void setPluOriginal_3(Long pluOriginal_3) {
		this.pluOriginal_3 = pluOriginal_3;
	}

	public Boolean getStockModificado() {
		return stockModificado;
	}

	public void setStockModificado(Boolean stockModificado) {
		this.stockModificado = stockModificado;
	}

	public String getLstOtrasAgrupacionesBalanza() {
		return lstOtrasAgrupacionesBalanza;
	}

	public void setLstOtrasAgrupacionesBalanza(String lstOtrasAgrupacionesBalanza) {
		this.lstOtrasAgrupacionesBalanza = lstOtrasAgrupacionesBalanza;
	}
	
}