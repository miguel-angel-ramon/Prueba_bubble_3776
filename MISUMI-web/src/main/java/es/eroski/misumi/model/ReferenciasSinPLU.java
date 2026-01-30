package es.eroski.misumi.model;

public class ReferenciasSinPLU {

	private Long codCentro;
	private Long codArt;
	private String descArt;
	private Long codError;
	private String descError;
	private Long codAgrupacionBalanza;
	private String descAgrupacionBalanza;
	private Long plu;
	
	public ReferenciasSinPLU() {
		// TODO Auto-generated constructor stub
	}

	public ReferenciasSinPLU(Long codCentro, Long codArt, String descArt, Long codError, String descError, Long codAgrupacionBalanza,String descAgrupacionBalanza,
			Long plu) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.descArt = descArt;
		this.codError = codError;
		this.descError = descError;
		this.codAgrupacionBalanza = codAgrupacionBalanza;
		this.descAgrupacionBalanza = descAgrupacionBalanza;
		this.plu = plu;
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

	public String getDescArt() {
		return descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
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

	public Long getCodAgrupacionBalanza() {
		return codAgrupacionBalanza;
	}

	public void setCodAgrupacionBalanza(Long codAgrupacionBalanza) {
		this.codAgrupacionBalanza = codAgrupacionBalanza;
	}

	public String getDescAgrupacionBalanza() {
		return descAgrupacionBalanza;
	}

	public void setDescAgrupacionBalanza(String descAgrupacionBalanza) {
		this.descAgrupacionBalanza = descAgrupacionBalanza;
	}

	public Long getPlu() {
		return plu;
	}

	public void setPlu(Long plu) {
		this.plu = plu;
	}

}
