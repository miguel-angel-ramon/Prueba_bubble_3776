package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class PedidosAdCentral implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long codSecuencia;
	private Long codCentro;
	private Long codArt;
	private String descripcion;
	private Long anoOferta;
	private Long numOferta;
	private Date fechaInicio;
	private Date fechaFin;
	private Double cant1;
	private Double cant2;
	private Double cant3;
	private Double cantMin;
	private Double cantMax;
	private String flgModificar;
	private String flgEnvioPbl;
	private String flgError;
	private String finalizado;
	private Date fechaGen;
	private Long identificador;
	private String flgValidado;
	private String tipoPed;
	private String aBM;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	private Long lastUpdateLogin;
	private Long tecle;
	private Long tcn;
	private Long codError;
	private String descError;
	private Date fechaHasta;
	private Double cant1_centro;
	private Double cant2_centro;
	private Double cant3_centro;
	private Double cant4_centro;
	private Double cant5_centro;
	private String noGestionaPbl;
	private Date fecha2;
	private Date fecha3;
	private Date fecha4;
	private Date fecha5;
	
	public PedidosAdCentral() {
		super();
	}

	public PedidosAdCentral(Long codSecuencia, Long codCentro, Long codArt,
			String descripcion, Long anoOferta, Long numOferta,
			Date fechaInicio, Date fechaFin, Double cant1, Double cant2,
			Double cant3, Double cantMin, Double cantMax, String flgModificar,
			String flgEnvioPbl, String flgError, String finalizado,
			Date fechaGen, Long identificador, String flgValidado,
			String tipoPed, String aBM, Long createdBy, Date creationDate,
			Long lastUpdateBy, Date lastUpdateDate,
			Long lastUpdateLogin, Long tecle, Long tcn, Date fechaHasta,
			Double cant1_centro, Double cant2_centro, Double cant3_centro, Double cant4_centro, Double cant5_centro,
			String noGestionaPbl,Date fecha2,Date fecha3,Date fecha4,Date fecha5) {
		super();
		this.codSecuencia = codSecuencia;
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.descripcion = descripcion;
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.cant1 = cant1;
		this.cant2 = cant2;
		this.cant3 = cant3;
		this.cantMin = cantMin;
		this.cantMax = cantMax;
		this.flgModificar = flgModificar;
		this.flgEnvioPbl = flgEnvioPbl;
		this.flgError = flgError;
		this.finalizado = finalizado;
		this.fechaGen = fechaGen;
		this.identificador = identificador;
		this.flgValidado = flgValidado;
		this.tipoPed = tipoPed;
		this.aBM = aBM;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdateBy = lastUpdateBy;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdateLogin = lastUpdateLogin;
		this.tecle = tecle;
		this.tcn = tcn;
		this.fechaHasta = fechaHasta;
		this.cant1_centro = cant1_centro;
		this.cant2_centro = cant2_centro;
		this.cant3_centro = cant3_centro;
		this.cant4_centro = cant4_centro;
		this.cant5_centro = cant5_centro;
		this.noGestionaPbl = noGestionaPbl;
		this.fecha2 = fecha2;
		this.fecha3 = fecha3;
		this.fecha4 = fecha4;
		this.fecha5 = fecha5;
	}

	public Long getCodSecuencia() {
		return this.codSecuencia;
	}

	public void setCodSecuencia(Long codSecuencia) {
		this.codSecuencia = codSecuencia;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getAnoOferta() {
		return this.anoOferta;
	}

	public void setAnoOferta(Long anoOferta) {
		this.anoOferta = anoOferta;
	}

	public Long getNumOferta() {
		return this.numOferta;
	}

	public void setNumOferta(Long numOferta) {
		this.numOferta = numOferta;
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

	public Double getCant1() {
		return this.cant1;
	}

	public void setCant1(Double cant1) {
		this.cant1 = cant1;
	}

	public Double getCant2() {
		return this.cant2;
	}

	public void setCant2(Double cant2) {
		this.cant2 = cant2;
	}

	public Double getCant3() {
		return this.cant3;
	}

	public void setCant3(Double cant3) {
		this.cant3 = cant3;
	}

	public Double getCantMin() {
		return this.cantMin;
	}

	public void setCantMin(Double cantMin) {
		this.cantMin = cantMin;
	}

	public Double getCantMax() {
		return this.cantMax;
	}

	public void setCantMax(Double cantMax) {
		this.cantMax = cantMax;
	}

	public String getFlgModificar() {
		return this.flgModificar;
	}

	public void setFlgModificar(String flgModificar) {
		this.flgModificar = flgModificar;
	}

	public String getFlgEnvioPbl() {
		return this.flgEnvioPbl;
	}

	public void setFlgEnvioPbl(String flgEnvioPbl) {
		this.flgEnvioPbl = flgEnvioPbl;
	}

	public String getFlgError() {
		return this.flgError;
	}

	public void setFlgError(String flgError) {
		this.flgError = flgError;
	}

	public String getFinalizado() {
		return this.finalizado;
	}

	public void setFinalizado(String finalizado) {
		this.finalizado = finalizado;
	}

	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public Long getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getFlgValidado() {
		return this.flgValidado;
	}

	public void setFlgValidado(String flgValidado) {
		this.flgValidado = flgValidado;
	}

	public String getTipoPed() {
		return this.tipoPed;
	}

	public void setTipoPed(String tipoPed) {
		this.tipoPed = tipoPed;
	}

	public String getaBM() {
		return this.aBM;
	}

	public void setaBM(String aBM) {
		this.aBM = aBM;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdateLogin() {
		return this.lastUpdateLogin;
	}

	public void setLastUpdateLogin(Long lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Long getTecle() {
		return this.tecle;
	}

	public void setTecle(Long tecle) {
		this.tecle = tecle;
	}

	public Long getTcn() {
		return this.tcn;
	}

	public void setTcn(Long tcn) {
		this.tcn = tcn;
	}

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}
	
	public Date getFechaHasta() {
		return this.fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	public Double getCant1_centro() {
		return this.cant1_centro;
	}

	public void setCant1_centro(Double cant1_centro) {
		this.cant1_centro = cant1_centro;
	}
	
	public Double getCant2_centro() {
		return this.cant2_centro;
	}

	public void setCant2_centro(Double cant2_centro) {
		this.cant2_centro = cant2_centro;
	}

	public Double getCant3_centro() {
		return this.cant3_centro;
	}

	public void setCant3_centro(Double cant3_centro) {
		this.cant3_centro = cant3_centro;
	}

	public Double getCant4_centro() {
		return this.cant4_centro;
	}

	public void setCant4_centro(Double cant4_centro) {
		this.cant4_centro = cant4_centro;
	}

	public Double getCant5_centro() {
		return this.cant5_centro;
	}

	public void setCant5_centro(Double cant5_centro) {
		this.cant5_centro = cant5_centro;
	}
	
	public String getNoGestionaPbl() {
		return noGestionaPbl;
	}

	public void setNoGestionaPbl(String noGestionaPbl) {
		this.noGestionaPbl = noGestionaPbl;
	}
	
	public Date getFecha2() {
		return this.fecha2;
	}

	public void setFecha2(Date fecha2) {
		this.fecha2 = fecha2;
	}
	
	public Date getFecha3() {
		return this.fecha3;
	}

	public void setFecha3(Date fecha3) {
		this.fecha3 = fecha3;
	}
	
	public Date getFecha4() {
		return this.fecha4;
	}

	public void setFecha4(Date fecha4) {
		this.fecha4 = fecha4;
	}
	
	public Date getFecha5() {
		return this.fecha5;
	}

	public void setFecha5(Date fecha5) {
		this.fecha5 = fecha5;
	}
}
