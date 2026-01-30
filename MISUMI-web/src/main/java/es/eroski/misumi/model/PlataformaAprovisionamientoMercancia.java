package es.eroski.misumi.model;

public class PlataformaAprovisionamientoMercancia {
	private String tpAprovSia;
	private Long codLocOri;
	private String descLocOri;
	private Long codProvrGen;
	private Long codProvrTrabajo;
	private String descProvr;
	
	private String plataformaCapraboOrion;

	public PlataformaAprovisionamientoMercancia(String tpAprovSia, Long codLocOri, String descLocOri, Long codProvrGen,
			Long codProvrTrabajo, String descProvr) {
		super();
		this.tpAprovSia = tpAprovSia;
		this.codLocOri = codLocOri;
		this.descLocOri = descLocOri;
		this.codProvrGen = codProvrGen;
		this.codProvrTrabajo = codProvrTrabajo;
		this.descProvr = descProvr;
	}

	public String getTpAprovSia() {
		return tpAprovSia;
	}

	public void setTpAprovSia(String tpAprovSia) {
		this.tpAprovSia = tpAprovSia;
	}

	public Long getCodLocOri() {
		return codLocOri;
	}

	public void setCodLocOri(Long codLocOri) {
		this.codLocOri = codLocOri;
	}

	public String getDescLocOri() {
		return descLocOri;
	}

	public void setDescLocOri(String descLocOri) {
		this.descLocOri = descLocOri;
	}

	public Long getCodProvrGen() {
		return codProvrGen;
	}

	public void setCodProvrGen(Long codProvrGen) {
		this.codProvrGen = codProvrGen;
	}

	public Long getCodProvrTrabajo() {
		return codProvrTrabajo;
	}

	public void setCodProvrTrabajo(Long codProvrTrabajo) {
		this.codProvrTrabajo = codProvrTrabajo;
	}

	public String getDescProvr() {
		return descProvr;
	}

	public void setDescProvr(String descProvr) {
		this.descProvr = descProvr;
	}

	public String getPlataformaCapraboOrion() {
		return plataformaCapraboOrion;
	}

	public void setPlataformaCapraboOrion(String plataformaCapraboOrion) {
		this.plataformaCapraboOrion = plataformaCapraboOrion;
	}
	
	
}
