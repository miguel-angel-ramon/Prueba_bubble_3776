package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VBloqueoEncargosPiladas implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codBloqueo; 
	private Long codN1;
	private Long codN2;
	private Long codN3;
	private Long codCentro;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private Long codArticulo;
	private String codTpBloqueo;
	private String codTpOrigenEp;
	private Date fecIni;
	private Date fecFin;
	private Date fechaGen;
	
	//Campos de filtro
	private String fecIniDDMMYYYY;
	private String fecha2DDMMYYYY;
	private String fecha3DDMMYYYY;
	private String fecha4DDMMYYYY;
	private String fecha5DDMMYYYY;
	private String fechaInPilDDMMYYYY;
	private String fecFinDDMMYYYY;
	
	//Campos para popup de bloqueos
	private Long clasePedido;
	
	private String modo;
	private Date fechaControl;
	
	//Control Fresco/Alimentacion Bloqueos
	private Boolean esFresco;
	
	public VBloqueoEncargosPiladas() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VBloqueoEncargosPiladas(Long codBloqueo, Long codN1, Long codN2,
			Long codN3, Long codCentro, Long grupo1, Long grupo2,
			Long grupo3, Long grupo4, Long grupo5, Long codArticulo,
			String codTpBloqueo, String codTpOrigenEp, Date fecIni,
			Date fecFin, Date fechaGen, String fecIniDDMMYYYY, String fecFinDDMMYYYY) {
		super();
		this.codBloqueo = codBloqueo;
		this.codN1 = codN1;
		this.codN2 = codN2;
		this.codN3 = codN3;
		this.codCentro = codCentro;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.codArticulo = codArticulo;
		this.codTpBloqueo = codTpBloqueo;
		this.codTpOrigenEp = codTpOrigenEp;
		this.fecIni = fecIni;
		this.fecFin = fecFin;
		this.fechaGen = fechaGen;
		this.fecIniDDMMYYYY = fecIniDDMMYYYY;
		this.fecFinDDMMYYYY = fecFinDDMMYYYY;
	}

	public Long getCodBloqueo() {
		return this.codBloqueo;
	}

	public void setCodBloqueo(Long codBloqueo) {
		this.codBloqueo = codBloqueo;
	}

	public Long getCodN1() {
		return this.codN1;
	}

	public void setCodN1(Long codN1) {
		this.codN1 = codN1;
	}

	public Long getCodN2() {
		return this.codN2;
	}

	public void setCodN2(Long codN2) {
		this.codN2 = codN2;
	}

	public Long getCodN3() {
		return this.codN3;
	}

	public void setCodN3(Long codN3) {
		this.codN3 = codN3;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
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

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getCodTpBloqueo() {
		return this.codTpBloqueo;
	}

	public void setCodTpBloqueo(String codTpBloqueo) {
		this.codTpBloqueo = codTpBloqueo;
	}

	public String getCodTpOrigenEp() {
		return this.codTpOrigenEp;
	}

	public void setCodTpOrigenEp(String codTpOrigenEp) {
		this.codTpOrigenEp = codTpOrigenEp;
	}

	public Date getFecIni() {
		return this.fecIni;
	}

	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
	}

	public Date getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public String getFecIniDDMMYYYY() {
		return this.fecIniDDMMYYYY;
	}

	public void setFecIniDDMMYYYY(String fecIniDDMMYYYY) {
		this.fecIniDDMMYYYY = fecIniDDMMYYYY;
	}

	public String getFecha2DDMMYYYY() {
		return this.fecha2DDMMYYYY;
	}

	public void setFecha2DDMMYYYY(String fecha2ddmmyyyy) {
		this.fecha2DDMMYYYY = fecha2ddmmyyyy;
	}

	public String getFecha3DDMMYYYY() {
		return this.fecha3DDMMYYYY;
	}

	public void setFecha3DDMMYYYY(String fecha3ddmmyyyy) {
		this.fecha3DDMMYYYY = fecha3ddmmyyyy;
	}

	public String getFecha4DDMMYYYY() {
		return this.fecha4DDMMYYYY;
	}

	public void setFecha4DDMMYYYY(String fecha4ddmmyyyy) {
		this.fecha4DDMMYYYY = fecha4ddmmyyyy;
	}

	public String getFecha5DDMMYYYY() {
		return this.fecha5DDMMYYYY;
	}

	public void setFecha5DDMMYYYY(String fecha5ddmmyyyy) {
		this.fecha5DDMMYYYY = fecha5ddmmyyyy;
	}

	public String getFechaInPilDDMMYYYY() {
		return this.fechaInPilDDMMYYYY;
	}

	public void setFechaInPilDDMMYYYY(String fechaInPilDDMMYYYY) {
		this.fechaInPilDDMMYYYY = fechaInPilDDMMYYYY;
	}

	public String getFecFinDDMMYYYY() {
		return this.fecFinDDMMYYYY;
	}

	public void setFecFinDDMMYYYY(String fecFinDDMMYYYY) {
		this.fecFinDDMMYYYY = fecFinDDMMYYYY;
	}
	
	public Long getClasePedido() {
		return this.clasePedido;
	}

	public void setClasePedido(Long clasePedido) {
		this.clasePedido = clasePedido;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public Date getFechaControl() {
		return fechaControl;
	}

	public void setFechaControl(Date fechaControl) {
		this.fechaControl = fechaControl;
	}

	public Boolean getEsFresco() {
		return esFresco;
	}

	public void setEsFresco(Boolean esFresco) {
		this.esFresco = esFresco;
	}
}