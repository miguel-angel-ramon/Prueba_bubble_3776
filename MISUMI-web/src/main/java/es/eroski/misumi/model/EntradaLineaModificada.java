package es.eroski.misumi.model;

public class EntradaLineaModificada {
	private Long codArticulo;
	private Long numeroCajasRecepcionadas;
	private Long totalBandejasRecepcionadas;
	private Double totalUnidadesRecepcionadas;
	private Long codError;
	private String descError;
	
	public EntradaLineaModificada() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EntradaLineaModificada(Long codArticulo, Long numeroCajasRecepcionadas, Long totalBandejasRecepcionadas,
			Double totalUnidadesRecepcionadas, Long codError, String descError) {
		super();
		this.codArticulo = codArticulo;
		this.numeroCajasRecepcionadas = numeroCajasRecepcionadas;
		this.totalBandejasRecepcionadas = totalBandejasRecepcionadas;
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
	public Long getNumeroCajasRecepcionadas() {
		return numeroCajasRecepcionadas;
	}
	public void setNumeroCajasRecepcionadas(Long numeroCajasRecepcionadas) {
		this.numeroCajasRecepcionadas = numeroCajasRecepcionadas;
	}
	public Long getTotalBandejasRecepcionadas() {
		return totalBandejasRecepcionadas;
	}
	public void setTotalBandejasRecepcionadas(Long totalBandejasRecepcionadas) {
		this.totalBandejasRecepcionadas = totalBandejasRecepcionadas;
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
}
