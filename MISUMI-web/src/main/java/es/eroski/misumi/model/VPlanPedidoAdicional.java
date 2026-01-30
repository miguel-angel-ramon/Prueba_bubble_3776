package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VPlanPedidoAdicional implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long codArt; 
	private String descripArt;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String agrupacion;
	private Double uniCajaServ;
	private Date fechaInicio;
	private Date fechaFin;
	private Double impInicial;
	private Double impFinal;
	private Long perfil;
	private String excluir;
	private String tipoAprovisionamiento;
	private String anoOferta;
	private Long codOferta;
	private String esOferta;
	private String MAC;
	private String descPeriodo;
	private String espacioPromo;
	private Date fechaGen;
	
	public VPlanPedidoAdicional() {
	    super();
	}

	public VPlanPedidoAdicional(Long codCentro, Long codArt, String descripArt, Long grupo1,
			Long grupo2, Long grupo3, Long grupo4, Long grupo5,
			String agrupacion, Double uniCajaServ, Date fechaInicio,
			Date fechaFin, Double impInicial, Double impFinal,
			Long perfil, String excluir, String tipoAprovisionamiento,
			String anoOferta, Long codOferta,String descPeriodo,String espacioPromo, Date fechaGen) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.descripArt = descripArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.agrupacion = agrupacion;
		this.uniCajaServ = uniCajaServ;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.impInicial = impInicial;
		this.impFinal = impFinal;
		this.perfil = perfil;
		this.excluir = excluir;
		this.tipoAprovisionamiento = tipoAprovisionamiento;
		this.anoOferta = anoOferta;
		this.codOferta = codOferta;
		this.descPeriodo = descPeriodo;
		this.espacioPromo = espacioPromo;
		this.fechaGen = fechaGen;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	
	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getDescripArt() {
		return this.descripArt;
	}

	public void setDescripArt(String descripArt) {
		this.descripArt = descripArt;
	}

	public Long getGrupo1() {
		return this.grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public Long getGrupo2() {
		return this.grupo2;
	}

	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}

	public Long getGrupo3() {
		return this.grupo3;
	}

	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}

	public Long getGrupo4() {
		return this.grupo4;
	}

	public void setGrupo4(Long grupo4) {
		this.grupo4 = grupo4;
	}

	public Long getGrupo5() {
		return this.grupo5;
	}

	public void setGrupo5(Long grupo5) {
		this.grupo5 = grupo5;
	}
	
	public String getAgrupacion() {
		return this.agrupacion;
	}

	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
	}
	
	public Double getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(Double uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public Double getImpInicial() {
		return this.impInicial;
	}

	public void setImpInicial(Double impInicial) {
		this.impInicial = impInicial;
	}

	public Double getImpFinal() {
		return this.impFinal;
	}

	public void setImpFinal(Double impFinal) {
		this.impFinal = impFinal;
	}

	public Long getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}

	public String getExcluir() {
		return this.excluir;
	}

	public void setExcluir(String excluir) {
		this.excluir = excluir;
	}

	public String getTipoAprovisionamiento() {
		return this.tipoAprovisionamiento;
	}

	public void setTipoAprovisionamiento(String tipoAprovisionamiento) {
		this.tipoAprovisionamiento = tipoAprovisionamiento;
	}
	
	public String getAnoOferta() {
		return this.anoOferta;
	}

	public void setAnoOferta(String anoOferta) {
		this.anoOferta = anoOferta;
	}

	public Long getCodOferta() {
		return this.codOferta;
	}

	public void setCodOferta(Long codOferta) {
		this.codOferta = codOferta;
	}

	public String getEsOferta() {
		return this.esOferta;
	}

	public void setEsOferta(String esOferta) {
		this.esOferta = esOferta;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}
	
	public String getDescPeriodo() {
		return this.descPeriodo;
	}

	public void setDescPeriodo(String descPeriodo) {
		this.descPeriodo = descPeriodo;
	}
	
	public String getEspacioPromo() {
		return this.espacioPromo;
	}

	public void setEspacioPromo(String espacioPromo) {
		this.espacioPromo = espacioPromo;
	}
	
	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
}