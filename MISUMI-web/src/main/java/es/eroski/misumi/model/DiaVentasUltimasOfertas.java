package es.eroski.misumi.model;

public class DiaVentasUltimasOfertas {
	private String d;
	private String dFormat;
	private Float cVD;
	private String laboral;
	private String anticipada;
	private String oferta;

	public DiaVentasUltimasOfertas() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiaVentasUltimasOfertas(String d,String dFormat, Float cVD, String laboral,String anticipada,String oferta) {
		super();
		this.d = d;
		this.dFormat = dFormat;
		this.cVD = cVD;
		this.laboral = laboral;
		this.anticipada= anticipada;
		this.oferta= oferta;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public Float getcVD() {
		return cVD;
	}

	public void setcVD(Float cVD) {
		this.cVD = cVD;
	}

	public String getLaboral() {
		return laboral;
	}

	public void setLaboral(String laboral) {
		this.laboral = laboral;
	}

	public String getDFormat() {
		return dFormat;
	}

	public void setDFormat(String dFormat) {
		this.dFormat = dFormat;
	}

	public String getAnticipada() {
		return anticipada;
	}

	public void setAnticipada(String anticipada) {
		this.anticipada = anticipada;
	}

	public String getOferta() {
		return oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}
	
	
}
