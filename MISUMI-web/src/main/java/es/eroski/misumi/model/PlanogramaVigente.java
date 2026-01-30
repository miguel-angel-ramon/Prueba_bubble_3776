package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class PlanogramaVigente implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private Float stockMinComerLineal;
	private Long capacidadMaxLineal;
	private Float stockMinComerCabecera;
	private Long capacidadMaxCabecera;
	private String anoOfertaPilada;
	private Long numOferta;
	private Date fechaIni;
	private Date fechaFin;
	private Date fechaGenLineal;
	private Date fechaGenCabecera;
	private String simuladoLineal;
	private Float capacidadMontaje1;
	private Float facingMontaje1;
	private String flgCabeceraMontaje1;
	private String flgOfertaMontaje1;
	private String flgCampanaMontaje1;
	private Date fechaGenMontaje1;
	private Float capacidadMontaje2;
	private Float facingMontaje2;
	private String flgCabeceraMontaje2;
	private String flgOfertaMontaje2;
	private String flgCampanaMontaje2;
	private Date fechaGenMontaje2;
	private Date creationDate;
	private Date updateDate;
	private String recalculado;
	private Float stockMinComerLinealRecal;
	private Long capacidadMaxLinealRecal;
	private Integer multiplicadorFac;
	private Long imc;
	private Boolean esFacingX;
	private String ofertaProm;
	private String espacioProm;
	
	//Campos calculados
	private String fechaGenLinealPantalla;
	
	//MISUMI-47. Si cualquier flg de V_AGRU_COMER_PARAM_SFMCAP es S, será true
	private Boolean esPlanogramaCalculado;
	
	//Guarda el rol del usuario.
	private String esTecnico;
	
	//Capacidad y facing originales.
	private Long capacidadMaxLinealOrig;
	private Float stockMinComerLinealOrig;
	
	//Mensaje de error al actualizar planogramaVigente
	private String msgError;
	
	//MISUMI-100. Si no tira de KOSMOS, estos datos se obtienen de V_PLANOGRAMA
	public String tipoPlano;
	public Long facingAlto;
	public Long facingAncho;
	public String esCajaExp;
	
	private Long incPrevisionVenta;
	private Long smEstatico;
	
	public PlanogramaVigente() {
		super();
	}

	public PlanogramaVigente(Long codCentro, Long codArt,
			Float stockMinComerLineal, Long capacidadMaxLineal,
			Float stockMinComerCabecera, Long capacidadMaxCabecera,
			 Date fechaGenLineal,
			String anoOfertaPilada, Long numOferta, Date fechaIni,
			Date fechaFin, Date fechaGenCabecera,
			String simuladoLineal,Float capacidadMontaje1, 
			Float facingMontaje1, String flgCabeceraMontaje1, 
			String flgOfertaMontaje1, String flgCampanaMontaje1,
			Date fechaGenMontajel, Float capacidadMontaje2, 
			Float facingMontaje2, String flgCabeceraMontaje2, 
			String flgOfertaMontaje2, String flgCampanaMontaje2,
			Date fechaGenMontaje2, Date creationDate,
			Date updateDate) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.stockMinComerLineal = stockMinComerLineal;
		this.capacidadMaxLineal = capacidadMaxLineal;
		this.stockMinComerCabecera = stockMinComerCabecera;
		this.capacidadMaxCabecera = capacidadMaxCabecera;
		this.anoOfertaPilada = anoOfertaPilada;
		this.numOferta = numOferta;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.fechaGenLineal = fechaGenLineal;
		this.fechaGenCabecera = fechaGenCabecera;
		this.simuladoLineal = simuladoLineal;
		this.capacidadMontaje1 = capacidadMontaje1;
		this.facingMontaje1 = facingMontaje1;
		this.flgCabeceraMontaje1 = flgCabeceraMontaje1;
		this.flgOfertaMontaje1 = flgOfertaMontaje1;
		this.flgCampanaMontaje1 = flgCampanaMontaje1;
		this.fechaGenMontaje1 = fechaGenMontajel;
		this.capacidadMontaje2 = capacidadMontaje2;
		this.facingMontaje2 = facingMontaje2;
		this.flgCabeceraMontaje2 = flgCabeceraMontaje2;
		this.flgOfertaMontaje2 = flgOfertaMontaje2;
		this.flgCampanaMontaje2 = flgCampanaMontaje2;
		this.fechaGenMontaje2 = fechaGenMontaje2;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
	}

	public PlanogramaVigente(Long codCentro, Long codArt,
			Float stockMinComerLineal, Long capacidadMaxLineal,
			Float stockMinComerCabecera, Long capacidadMaxCabecera,
			 Date fechaGenLineal,
			String anoOfertaPilada, Long numOferta, Date fechaIni,
			Date fechaFin, Date fechaGenCabecera,
			String simuladoLineal,Float capacidadMontaje1, 
			Float facingMontaje1, String flgCabeceraMontaje1, 
			String flgOfertaMontaje1, String flgCampanaMontaje1,
			Date fechaGenMontajel, Float capacidadMontaje2, 
			Float facingMontaje2, String flgCabeceraMontaje2, 
			String flgOfertaMontaje2, String flgCampanaMontaje2,
			Date fechaGenMontaje2, Date creationDate,
			Date updateDate, String tipoPlano, Long facingAlto, Long facingAncho, String esCajaExp) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.stockMinComerLineal = stockMinComerLineal;
		this.capacidadMaxLineal = capacidadMaxLineal;
		this.stockMinComerCabecera = stockMinComerCabecera;
		this.capacidadMaxCabecera = capacidadMaxCabecera;
		this.anoOfertaPilada = anoOfertaPilada;
		this.numOferta = numOferta;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.fechaGenLineal = fechaGenLineal;
		this.fechaGenCabecera = fechaGenCabecera;
		this.simuladoLineal = simuladoLineal;
		this.capacidadMontaje1 = capacidadMontaje1;
		this.facingMontaje1 = facingMontaje1;
		this.flgCabeceraMontaje1 = flgCabeceraMontaje1;
		this.flgOfertaMontaje1 = flgOfertaMontaje1;
		this.flgCampanaMontaje1 = flgCampanaMontaje1;
		this.fechaGenMontaje1 = fechaGenMontajel;
		this.capacidadMontaje2 = capacidadMontaje2;
		this.facingMontaje2 = facingMontaje2;
		this.flgCabeceraMontaje2 = flgCabeceraMontaje2;
		this.flgOfertaMontaje2 = flgOfertaMontaje2;
		this.flgCampanaMontaje2 = flgCampanaMontaje2;
		this.fechaGenMontaje2 = fechaGenMontaje2;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.tipoPlano = tipoPlano;
		this.facingAlto = facingAlto;
		this.facingAncho = facingAncho;
		this.esCajaExp = esCajaExp;
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

	public Float getStockMinComerLineal() {
		return stockMinComerLineal;
	}

	public void setStockMinComerLineal(Float stockMinComerLineal) {
		this.stockMinComerLineal = stockMinComerLineal;
	}

	public Long getCapacidadMaxLineal() {
		return capacidadMaxLineal;
	}

	public void setCapacidadMaxLineal(Long capacidadMaxLineal) {
		this.capacidadMaxLineal = capacidadMaxLineal;
	}

	public Float getStockMinComerCabecera() {
		return stockMinComerCabecera;
	}

	public void setStockMinComerCabecera(Float stockMinComerCabecera) {
		this.stockMinComerCabecera = stockMinComerCabecera;
	}

	public Long getCapacidadMaxCabecera() {
		return capacidadMaxCabecera;
	}

	public void setCapacidadMaxCabecera(Long capacidadMaxCabecera) {
		this.capacidadMaxCabecera = capacidadMaxCabecera;
	}

	public String getAnoOfertaPilada() {
		return anoOfertaPilada;
	}

	public void setAnoOfertaPilada(String anoOfertaPilada) {
		this.anoOfertaPilada = anoOfertaPilada;
	}

	public Long getNumOferta() {
		return numOferta;
	}

	public void setNumOferta(Long numOferta) {
		this.numOferta = numOferta;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaGenLineal() {
		return fechaGenLineal;
	}

	public void setFechaGenLineal(Date fechaGenLineal) {
		this.fechaGenLineal = fechaGenLineal;
	}

	public Date getFechaGenCabecera() {
		return fechaGenCabecera;
	}

	public void setFechaGenCabecera(Date fechaGenCabecera) {
		this.fechaGenCabecera = fechaGenCabecera;
	}

	public String getSimuladoLineal() {
		return simuladoLineal;
	}

	public void setSimuladoLineal(String simuladoLineal) {
		this.simuladoLineal = simuladoLineal;
	}

	public Float getCapacidadMontaje1() {
		return capacidadMontaje1;
	}

	public void setCapacidadMontaje1(Float capacidadMontaje1) {
		this.capacidadMontaje1 = capacidadMontaje1;
	}

	public Float getFacingMontaje1() {
		return facingMontaje1;
	}

	public void setFacingMontaje1(Float facingMontaje1) {
		this.facingMontaje1 = facingMontaje1;
	}

	public String getFlgCabeceraMontaje1() {
		return flgCabeceraMontaje1;
	}

	public void setFlgCabeceraMontaje1(String flgCabeceraMontaje1) {
		this.flgCabeceraMontaje1 = flgCabeceraMontaje1;
	}

	public String getFlgOfertaMontaje1() {
		return flgOfertaMontaje1;
	}

	public void setFlgOfertaMontaje1(String flgOfertaMontaje1) {
		this.flgOfertaMontaje1 = flgOfertaMontaje1;
	}

	public String getFlgCampanaMontaje1() {
		return flgCampanaMontaje1;
	}

	public void setFlgCampanaMontaje1(String flgCampanaMontaje1) {
		this.flgCampanaMontaje1 = flgCampanaMontaje1;
	}

	public Date getFechaGenMontaje1() {
		return fechaGenMontaje1;
	}

	public void setFechaGenMontaje1(Date fechaGenMontaje1) {
		this.fechaGenMontaje1 = fechaGenMontaje1;
	}

	public Float getCapacidadMontaje2() {
		return capacidadMontaje2;
	}

	public void setCapacidadMontaje2(Float capacidadMontaje2) {
		this.capacidadMontaje2 = capacidadMontaje2;
	}

	public Float getFacingMontaje2() {
		return facingMontaje2;
	}

	public void setFacingMontaje2(Float facingMontaje2) {
		this.facingMontaje2 = facingMontaje2;
	}

	public String getFlgCabeceraMontaje2() {
		return flgCabeceraMontaje2;
	}

	public void setFlgCabeceraMontaje2(String flgCabeceraMontaje2) {
		this.flgCabeceraMontaje2 = flgCabeceraMontaje2;
	}

	public String getFlgOfertaMontaje2() {
		return flgOfertaMontaje2;
	}

	public void setFlgOfertaMontaje2(String flgOfertaMontaje2) {
		this.flgOfertaMontaje2 = flgOfertaMontaje2;
	}

	public String getFlgCampanaMontaje2() {
		return flgCampanaMontaje2;
	}

	public void setFlgCampanaMontaje2(String flgCampanaMontaje2) {
		this.flgCampanaMontaje2 = flgCampanaMontaje2;
	}

	public Date getFechaGenMontaje2() {
		return fechaGenMontaje2;
	}

	public void setFechaGenMontaje2(Date fechaGenMontaje2) {
		this.fechaGenMontaje2 = fechaGenMontaje2;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRecalculado() {
		return recalculado;
	}

	public void setRecalculado(String recalculado) {
		this.recalculado = recalculado;
	}

	public Float getStockMinComerLinealRecal() {
		return stockMinComerLinealRecal;
	}

	public void setStockMinComerLinealRecal(Float stockMinComerLinealRecal) {
		this.stockMinComerLinealRecal = stockMinComerLinealRecal;
	}

	public Long getCapacidadMaxLinealRecal() {
		return capacidadMaxLinealRecal;
	}

	public void setCapacidadMaxLinealRecal(Long capacidadMaxLinealRecal) {
		this.capacidadMaxLinealRecal = capacidadMaxLinealRecal;
	}

	public String getFechaGenLinealPantalla() {
		return fechaGenLinealPantalla;
	}

	public void setFechaGenLinealPantalla(String fechaGenLinealPantalla) {
		this.fechaGenLinealPantalla = fechaGenLinealPantalla;
	}

	public Integer getMultiplicadorFac() {
		return this.multiplicadorFac;
	}

	public void setMultiplicadorFac(Integer multiplicadorFac) {
		this.multiplicadorFac = multiplicadorFac;
	}

	public Long getImc() {
		return this.imc;
	}

	public void setImc(Long imc) {
		this.imc = imc;
	}

	public Boolean getEsFacingX() {
		return this.esFacingX;
	}

	public void setEsFacingX(Boolean esFacingX) {
		this.esFacingX = esFacingX;
	}

	public Boolean getEsPlanogramaCalculado() {
		return esPlanogramaCalculado;
	}

	public void setEsPlanogramaCalculado(Boolean esPlanogramaCalculado) {
		this.esPlanogramaCalculado = esPlanogramaCalculado;
	}

	public String getEsTecnico() {
		return esTecnico;
	}

	public void setEsTecnico(String esTecnico) {
		this.esTecnico = esTecnico;
	}

	public Long getCapacidadMaxLinealOrig() {
		return capacidadMaxLinealOrig;
	}

	public void setCapacidadMaxLinealOrig(Long capacidadMaxLinealOrig) {
		this.capacidadMaxLinealOrig = capacidadMaxLinealOrig;
	}

	public Float getStockMinComerLinealOrig() {
		return stockMinComerLinealOrig;
	}

	public void setStockMinComerLinealOrig(Float stockMinComerLinealOrig) {
		this.stockMinComerLinealOrig = stockMinComerLinealOrig;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	public String getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(String tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

	public Long getFacingAlto() {
		return facingAlto;
	}

	public void setFacingAlto(Long facingAlto) {
		this.facingAlto = facingAlto;
	}

	public Long getFacingAncho() {
		return facingAncho;
	}

	public void setFacingAncho(Long facingAncho) {
		this.facingAncho = facingAncho;
	}

	public String getEsCajaExp() {
		return esCajaExp;
	}

	public void setEsCajaExp(String esCajaExp) {
		this.esCajaExp = esCajaExp;
	}

	public String getOfertaProm() {
		return ofertaProm;
	}

	public void setOfertaProm(String ofertaProm) {
		this.ofertaProm = ofertaProm;
	}

	public String getEspacioProm() {
		return espacioProm;
	}

	public void setEspacioProm(String espacioProm) {
		this.espacioProm = espacioProm;
	}

	public Long getIncPrevisionVenta() {
		return incPrevisionVenta;
	}

	public void setIncPrevisionVenta(Long incPrevisionVenta) {
		this.incPrevisionVenta = incPrevisionVenta;
	}

	public Long getSmEstatico() {
		return smEstatico;
	}

	public void setSmEstatico(Long smEstatico) {
		this.smEstatico = smEstatico;
	}

}