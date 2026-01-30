/**
 * 
 */
package es.eroski.misumi.model;

/**
 * POJO para la representacion de los registros de la tabla temporal T_MIS_REF_NO_GAMA_MOSTRADOR 
 * @author BICUGUAL
 *
 */
public class ReferenciaNoGamaMostrador {

	private String idSession;
	private Long codArtFormLog;
	private String denomRef;
	private Double costo;
	private Double pvpTarifa;
	private Double iva;
	private Double margen;
	private Double cc;
	private Integer numOrden;
	private String foto;
	
	private Long unidadesCaja;
	
	public ReferenciaNoGamaMostrador() {
		super();
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public Long getCodArtFormLog() {
		return codArtFormLog;
	}

	public void setCodArtFormLog(Long codArtFormLog) {
		this.codArtFormLog = codArtFormLog;
	}

	public String getDenomRef() {
		return denomRef;
	}

	public void setDenomRef(String denomRef) {
		this.denomRef = denomRef;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Double getPvpTarifa() {
		return pvpTarifa;
	}

	public void setPvpTarifa(Double pvpTarifa) {
		this.pvpTarifa = pvpTarifa;
	}

	public Double getMargen() {
		return margen;
	}

	public void setMargen(Double margen) {
		this.margen = margen;
	}

	public Double getCc() {
		return cc;
	}

	public void setCc(Double cc) {
		this.cc = cc;
	}

	public Integer getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(Integer numOrden) {
		this.numOrden = numOrden;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Long getUnidadesCaja() {
		return unidadesCaja;
	}

	public void setUnidadesCaja(Long unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}
}
