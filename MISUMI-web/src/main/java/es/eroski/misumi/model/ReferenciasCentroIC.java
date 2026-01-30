package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReferenciasCentroIC implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt; 
	private Long codCentro; 
	private String fechaVentaDDMMYYYY;
	private String promocional;
	
	private StockFinalMinimo stockFinalMinimo; 
	private PlanogramaVigente planogramaVigente;
	private HistoricoVentaMedia historicoVentaMedia;
	private List<HistoricoVentaUltimoMes> listaHistoricoVentaUltimoMes;

	//Pet. 53005	
	private Boolean esFacingX;
	
	//Campos calculados
	private String mostrarFechaPlanograma;
	private Long flgStockFinal;
	
	private String flgFacing;
	private String flgCapacidad;
	private String flgFacingCapacidad;
	
	private Long sustituidaPor;
	private Long sustitutaDe;
	private String implantacion;
	private Date fechaActivacion;
	private Date fechaGen;
	private String descripcionPlanograma;
	private String colorImagenComercial;
	
	private Integer multiplicadorFac;
	private Long imc;
	
	private Boolean esFFPP;
	
	private VPlanograma vPlanogramaTipoP;
	
	private int tratamientoVegalsa;
	
	//MISUMI-428
	private String capacidadMontajeAdicionalCentro;
	private String fechaInicioMontajeAdicionalCentro;
	private String fechaFinMontajeAdicionalCentro;
	private String ofertaMontajeAdicionalCentro;
	
	public ReferenciasCentroIC() {
	    super();
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	
	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getFechaVentaDDMMYYYY() {
		return this.fechaVentaDDMMYYYY;
	}

	public void setFechaVentaDDMMYYYY(String fechaVentaDDMMYYYY) {
		this.fechaVentaDDMMYYYY = fechaVentaDDMMYYYY;
	}

	public StockFinalMinimo getStockFinalMinimo() {
		return this.stockFinalMinimo;
	}

	public void setStockFinalMinimo(StockFinalMinimo stockFinalMinimo) {
		this.stockFinalMinimo = stockFinalMinimo;
	}

	public PlanogramaVigente getPlanogramaVigente() {
		return this.planogramaVigente;
	}

	public void setPlanogramaVigente(PlanogramaVigente planogramaVigente) {
		this.planogramaVigente = planogramaVigente;
	}

	public HistoricoVentaMedia getHistoricoVentaMedia() {
		return this.historicoVentaMedia;
	}

	public void setHistoricoVentaMedia(HistoricoVentaMedia historicoVentaMedia) {
		this.historicoVentaMedia = historicoVentaMedia;
	}

	public List<HistoricoVentaUltimoMes> getListaHistoricoVentaUltimoMes() {
		return this.listaHistoricoVentaUltimoMes;
	}

	public void setListaHistoricoVentaUltimoMes(
			List<HistoricoVentaUltimoMes> listaHistoricoVentaUltimoMes) {
		this.listaHistoricoVentaUltimoMes = listaHistoricoVentaUltimoMes;
	}
	
	public String getMostrarFechaPlanograma() {
		return this.mostrarFechaPlanograma;
	}

	public void setMostrarFechaPlanograma(String mostrarFechaPlanograma) {
		this.mostrarFechaPlanograma = mostrarFechaPlanograma;
	}

	public Long getFlgStockFinal() {
		return this.flgStockFinal;
	}

	public void setFlgStockFinal(Long flgStockFinal) {
		this.flgStockFinal = flgStockFinal;
	}

	public String getFlgFacing() {
		return flgFacing;
	}

	public void setFlgFacing(String flgFacing) {
		this.flgFacing = flgFacing;
	}
	
	public String getFlgCapacidad() {
		return flgCapacidad;
	}

	public void setFlgCapacidad(String flgCapacidad) { 
		this.flgCapacidad = flgCapacidad;
	}
	
	public String getFlgFacingCapacidad() {
		return flgFacingCapacidad;
	}

	public void setFlgFacingCapacidad(String flgFacingCapacidad) {
		this.flgFacingCapacidad = flgFacingCapacidad;
	}
	
	

	public String getPromocional() {
		return promocional;
	}

	public void setPromocional(String promocional) {
		this.promocional = promocional;
	}
	
	public String getDescripcionPlanograma() {
		return descripcionPlanograma;
	}

	public void setDescripcionPlanograma(String descripcionPlanograma) {
		this.descripcionPlanograma = descripcionPlanograma;
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

	public String getColorImagenComercial() {
		return colorImagenComercial;
	}

	public void setColorImagenComercial(String colorImagenComercial) {
		this.colorImagenComercial = colorImagenComercial;
	}
	
	public Boolean getEsFacingX() {
		return esFacingX;
	}

	public void setEsFacingX(Boolean esFacingX) {
		this.esFacingX = esFacingX;
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

	public Boolean getEsFFPP() {
		return this.esFFPP;
	}

	public void setEsFFPP(Boolean esFFPP) {
		this.esFFPP = esFFPP;
	}

	public VPlanograma getvPlanogramaTipoP() {
		return vPlanogramaTipoP;
	}

	public void setvPlanogramaTipoP(VPlanograma vPlanogramaTipoP) {
		this.vPlanogramaTipoP = vPlanogramaTipoP;
	}

	public int isTratamientoVegalsa() {
		return tratamientoVegalsa;
	}

	public void setTratamientoVegalsa(int tratamientoVegalsa) {
		this.tratamientoVegalsa = tratamientoVegalsa;
	}

	public String getCapacidadMontajeAdicionalCentro() {
		return capacidadMontajeAdicionalCentro;
	}

	public void setCapacidadMontajeAdicionalCentro(String capacidadMontajeAdicionalCentro) {
		this.capacidadMontajeAdicionalCentro = capacidadMontajeAdicionalCentro;
	}

	public String getFechaInicioMontajeAdicionalCentro() {
		return fechaInicioMontajeAdicionalCentro;
	}

	public void setFechaInicioMontajeAdicionalCentro(String fechaInicioMontajeAdicionalCentro) {
		this.fechaInicioMontajeAdicionalCentro = fechaInicioMontajeAdicionalCentro;
	}

	public String getFechaFinMontajeAdicionalCentro() {
		return fechaFinMontajeAdicionalCentro;
	}

	public void setFechaFinMontajeAdicionalCentro(String fechaFinMontajeAdicionalCentro) {
		this.fechaFinMontajeAdicionalCentro = fechaFinMontajeAdicionalCentro;
	}

	public String getOfertaMontajeAdicionalCentro() {
		return ofertaMontajeAdicionalCentro;
	}

	public void setOfertaMontajeAdicionalCentro(String ofertaMontajeAdicionalCentro) {
		this.ofertaMontajeAdicionalCentro = ofertaMontajeAdicionalCentro;
	}

}