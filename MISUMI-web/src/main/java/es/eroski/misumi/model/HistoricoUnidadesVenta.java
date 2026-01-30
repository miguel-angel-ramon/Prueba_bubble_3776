package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class HistoricoUnidadesVenta implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc;
	private Date fechaVenta;
	private Long codArticulo;
	private Float unidVentaTarifa;
	private Float unidVentaCompetencia;
	private Float unidVentaOferta;
	private Float unidVentaAnticipada;
	private Float unidVentaTotal;
	private Long unidVentaTarifaBd;
	private Long unidVentaCompetenciaBd;
	private Long unidVentaOfertaBd;
	private Long unidVentaAnticipadaBd;
	private Long unidVentaTotalBd;
	private String porcionCen;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private Long lastUpdateLogin;
	private Long tecle;
	private Long tcn;
	
	//Campos calculados
	private Float totalVentas;
	private Float totalVentasLastDays;
	private Long records;

	//Campos p17
	private String fechaVentaDDMMYYYY;
	
	public HistoricoUnidadesVenta() {
		super();
	}

	public HistoricoUnidadesVenta(Long codLoc, Date fechaVenta,
			Long codArticulo, Float unidVentaTarifa,
			Float unidVentaCompetencia, Float unidVentaOferta,
			Float unidVentaAnticipada, Float unidVentaTotal,
			Long unidVentaTarifaBd, Long unidVentaCompetenciaBd,
			Long unidVentaOfertaBd, Long unidVentaAnticipadaBd,
			Long unidVentaTotalBd, String porcionCen, Long createdBy,
			Date creationDate, Long lastUpdatedBy, Date lastUpdateDate,
			Long lastUpdateLogin, Long tecle, Long tcn, Float totalVentas,
			Float totalVentasLastDays, Long records) {
		super();
		this.codLoc = codLoc;
		this.fechaVenta = fechaVenta;
		this.codArticulo = codArticulo;
		this.unidVentaTarifa = unidVentaTarifa;
		this.unidVentaCompetencia = unidVentaCompetencia;
		this.unidVentaOferta = unidVentaOferta;
		this.unidVentaAnticipada = unidVentaAnticipada;
		this.unidVentaTotal = unidVentaTotal;
		this.unidVentaTarifaBd = unidVentaTarifaBd;
		this.unidVentaCompetenciaBd = unidVentaCompetenciaBd;
		this.unidVentaOfertaBd = unidVentaOfertaBd;
		this.unidVentaAnticipadaBd = unidVentaAnticipadaBd;
		this.unidVentaTotalBd = unidVentaTotalBd;
		this.porcionCen = porcionCen;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdateLogin = lastUpdateLogin;
		this.tecle = tecle;
		this.tcn = tcn;
		this.totalVentas = totalVentas;
		this.totalVentasLastDays = totalVentasLastDays;
		this.records = records;
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public Date getFechaVenta() {
		return this.fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Float getUnidVentaTarifa() {
		return this.unidVentaTarifa;
	}

	public void setUnidVentaTarifa(Float unidVentaTarifa) {
		this.unidVentaTarifa = unidVentaTarifa;
	}

	public Float getUnidVentaCompetencia() {
		return this.unidVentaCompetencia;
	}

	public void setUnidVentaCompetencia(Float unidVentaCompetencia) {
		this.unidVentaCompetencia = unidVentaCompetencia;
	}

	public Float getUnidVentaOferta() {
		return this.unidVentaOferta;
	}

	public void setUnidVentaOferta(Float unidVentaOferta) {
		this.unidVentaOferta = unidVentaOferta;
	}

	public Float getUnidVentaAnticipada() {
		return this.unidVentaAnticipada;
	}

	public void setUnidVentaAnticipada(Float unidVentaAnticipada) {
		this.unidVentaAnticipada = unidVentaAnticipada;
	}

	public Float getUnidVentaTotal() {
		return this.unidVentaTotal;
	}

	public void setUnidVentaTotal(Float unidVentaTotal) {
		this.unidVentaTotal = unidVentaTotal;
	}

	public Long getUnidVentaTarifaBd() {
		return this.unidVentaTarifaBd;
	}

	public void setUnidVentaTarifaBd(Long unidVentaTarifaBd) {
		this.unidVentaTarifaBd = unidVentaTarifaBd;
	}

	public Long getUnidVentaCompetenciaBd() {
		return this.unidVentaCompetenciaBd;
	}

	public void setUnidVentaCompetenciaBd(Long unidVentaCompetenciaBd) {
		this.unidVentaCompetenciaBd = unidVentaCompetenciaBd;
	}

	public Long getUnidVentaOfertaBd() {
		return this.unidVentaOfertaBd;
	}

	public void setUnidVentaOfertaBd(Long unidVentaOfertaBd) {
		this.unidVentaOfertaBd = unidVentaOfertaBd;
	}

	public Long getUnidVentaAnticipadaBd() {
		return this.unidVentaAnticipadaBd;
	}

	public void setUnidVentaAnticipadaBd(Long unidVentaAnticipadaBd) {
		this.unidVentaAnticipadaBd = unidVentaAnticipadaBd;
	}

	public Long getUnidVentaTotalBd() {
		return this.unidVentaTotalBd;
	}

	public void setUnidVentaTotalBd(Long unidVentaTotalBd) {
		this.unidVentaTotalBd = unidVentaTotalBd;
	}

	public String getPorcionCen() {
		return this.porcionCen;
	}

	public void setPorcionCen(String porcionCen) {
		this.porcionCen = porcionCen;
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

	public Long getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
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
	
	public Float getTotalVentas() {
		return this.totalVentas;
	}

	public void setTotalVentas(Float totalVentas) {
		this.totalVentas = totalVentas;
	}
	
	public Float getTotalVentasLastDays() {
		return this.totalVentasLastDays;
	}

	public void setTotalVentasLastDays(Float totalVentasLastDays) {
		this.totalVentasLastDays = totalVentasLastDays;
	}
	
	public Long getRecords() {
		return this.records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public String getFechaVentaDDMMYYYY() {
		return fechaVentaDDMMYYYY;
	}

	public void setFechaVentaDDMMYYYY(String fechaVentaDDMMYYYY) {
		this.fechaVentaDDMMYYYY = fechaVentaDDMMYYYY;
	}
}