package es.eroski.misumi.model;

public class EntradaLinea {
	private Long codArticulo;
	private String denomCodArticulo;
	private Long numeroCajasPedidas;
	private Long numeroCajasRecepcionadas;
	private Long uc;
	private Long totalBandejasPedidas;
	private Long totalBandejasRecepcionadas;
	private Double totalUnidadesPedidas;
	private Double totalUnidadesRecepcionadas;
	private Long codError;
	private String descError;

	public EntradaLinea() {
		// TODO Auto-generated constructor stub
	}

	public EntradaLinea(Long codArticulo, String denomCodArticulo, Long numeroCajasPedidas,
			Long numeroCajasRecepcionadas, Long uc, Long totalBandejasPedidas, Long totalBandejasRecepcionadas,
			Double totalUnidadesPedidas, Double totalUnidadesRecepcionadas) {
		this(codArticulo,denomCodArticulo,numeroCajasPedidas,numeroCajasRecepcionadas,uc,totalBandejasPedidas,
				totalBandejasRecepcionadas,totalUnidadesPedidas,totalUnidadesRecepcionadas,null,null);
	}

	public EntradaLinea(Long codArticulo, String denomCodArticulo, Long numeroCajasPedidas,
			Long numeroCajasRecepcionadas, Long uc, Long totalBandejasPedidas, Long totalBandejasRecepcionadas,
			Double totalUnidadesPedidas, Double totalUnidadesRecepcionadas, Long codError, String descError) {
		super();
		this.codArticulo = codArticulo;
		this.denomCodArticulo = denomCodArticulo;
		this.numeroCajasPedidas = numeroCajasPedidas;
		this.numeroCajasRecepcionadas = numeroCajasRecepcionadas;
		this.uc = uc;
		this.totalBandejasPedidas = totalBandejasPedidas;
		this.totalBandejasRecepcionadas = totalBandejasRecepcionadas;
		this.totalUnidadesPedidas = totalUnidadesPedidas;
		this.totalUnidadesRecepcionadas = totalUnidadesRecepcionadas;
		this.codError = codError;
		this.descError = descError;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getDenomCodArticulo() {
		return denomCodArticulo;
	}

	public void setDenomCodArticulo(String denomCodArticulo) {
		this.denomCodArticulo = denomCodArticulo;
	}

	public Long getNumeroCajasPedidas() {
		return numeroCajasPedidas;
	}

	public void setNumeroCajasPedidas(Long numeroCajasPedidas) {
		this.numeroCajasPedidas = numeroCajasPedidas;
	}

	public Long getNumeroCajasRecepcionadas() {
		return numeroCajasRecepcionadas;
	}

	public void setNumeroCajasRecepcionadas(Long numeroCajasRecepcionadas) {
		this.numeroCajasRecepcionadas = numeroCajasRecepcionadas;
	}

	public Long getUc() {
		return uc;
	}

	public void setUc(Long uc) {
		this.uc = uc;
	}

	public Long getTotalBandejasPedidas() {
		return totalBandejasPedidas;
	}

	public void setTotalBandejasPedidas(Long totalBandejasPedidas) {
		this.totalBandejasPedidas = totalBandejasPedidas;
	}

	public Long getTotalBandejasRecepcionadas() {
		return totalBandejasRecepcionadas;
	}

	public void setTotalBandejasRecepcionadas(Long totalBandejasRecepcionadas) {
		this.totalBandejasRecepcionadas = totalBandejasRecepcionadas;
	}

	public Double getTotalUnidadesPedidas() {
		return totalUnidadesPedidas;
	}

	public void setTotalUnidadesPedidas(Double totalUnidadesPedidas) {
		this.totalUnidadesPedidas = totalUnidadesPedidas;
	}

	public Double getTotalUnidadesRecepcionadas() {
		return totalUnidadesRecepcionadas;
	}

	public void setTotalUnidadesRecepcionadas(Double totalUnidadesRecepcionadas) {
		this.totalUnidadesRecepcionadas = totalUnidadesRecepcionadas;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntradaLinea [referenciaTienda=");
		builder.append(codArticulo);
		builder.append(", denomReferenciaTienda=");
		builder.append(denomCodArticulo);
		builder.append(", numeroCajasPedidas=");
		builder.append(numeroCajasPedidas);
		builder.append(", numeroCajasRecepcionadas=");
		builder.append(numeroCajasRecepcionadas);
		builder.append(", uc=");
		builder.append(uc);
		builder.append(", totalBandejasPedidas=");
		builder.append(totalBandejasPedidas);
		builder.append(", totalBandejasRecepcionadas=");
		builder.append(totalBandejasRecepcionadas);
		builder.append(", totalUnidadesPedidas=");
		builder.append(totalUnidadesPedidas);
		builder.append(", totalUnidadesRecepcionadas=");
		builder.append(totalUnidadesRecepcionadas);
		builder.append("]");
		return builder.toString();
	}


}
