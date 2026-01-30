package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VConfirmacionNoServido implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long codPlat;
	private Long codPedPlat;
	private Long codPedAprovCentral;
	private Date fechaTrans;
	private Date fechaPed;
	private Date fechaPrevisEnt;
	private Long codArt;
	private Float cajaNsr;
	private Float uniNoServ;
	private String flgEnviadoPbl;
	private Date fechaNsr;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String descripArt;
	private Float uniCajaServ;
	private Long motivo;
	
	public VConfirmacionNoServido() {
		super();
	}

	public VConfirmacionNoServido(Long codCentro, Long codPlat,
			Long codPedPlat, Long codPedAprovCentral, Date fechaTrans,
			Date fechaPed, Date fechaPrevisEnt, Long codArt, Float cajaNsr,
			Float uniNoServ, String flgEnviadoPbl, Date fechaNsr, Long grupo1,
			Long grupo2, Long grupo3, Long grupo4, Long grupo5,
			String descripArt, Float uniCajaServ, Long motivo) {
		super();
		this.codCentro = codCentro;
		this.codPlat = codPlat;
		this.codPedPlat = codPedPlat;
		this.codPedAprovCentral = codPedAprovCentral;
		this.fechaTrans = fechaTrans;
		this.fechaPed = fechaPed;
		this.fechaPrevisEnt = fechaPrevisEnt;
		this.codArt = codArt;
		this.cajaNsr = cajaNsr;
		this.uniNoServ = uniNoServ;
		this.flgEnviadoPbl = flgEnviadoPbl;
		this.fechaNsr = fechaNsr;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripArt = descripArt;
		this.uniCajaServ = uniCajaServ;
		this.motivo = motivo;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodPlat() {
		return this.codPlat;
	}

	public void setCodPlat(Long codPlat) {
		this.codPlat = codPlat;
	}

	public Long getCodPedPlat() {
		return this.codPedPlat;
	}

	public void setCodPedPlat(Long codPedPlat) {
		this.codPedPlat = codPedPlat;
	}

	public Long getCodPedAprovCentral() {
		return this.codPedAprovCentral;
	}

	public void setCodPedAprovCentral(Long codPedAprovCentral) {
		this.codPedAprovCentral = codPedAprovCentral;
	}

	public Date getFechaTrans() {
		return this.fechaTrans;
	}

	public void setFechaTrans(Date fechaTrans) {
		this.fechaTrans = fechaTrans;
	}

	public Date getFechaPed() {
		return this.fechaPed;
	}

	public void setFechaPed(Date fechaPed) {
		this.fechaPed = fechaPed;
	}

	public Date getFechaPrevisEnt() {
		return this.fechaPrevisEnt;
	}

	public void setFechaPrevisEnt(Date fechaPrevisEnt) {
		this.fechaPrevisEnt = fechaPrevisEnt;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public Float getCajaNsr() {
		return this.cajaNsr;
	}

	public void setCajaNsr(Float cajaNsr) {
		this.cajaNsr = cajaNsr;
	}
	
	public Float getUniNoServ() {
		return this.uniNoServ;
	}

	public void setUniNoServ(Float uniNoServ) {
		this.uniNoServ = uniNoServ;
	}

	public String getFlgEnviadoPbl() {
		return this.flgEnviadoPbl;
	}

	public void setFlgEnviadoPbl(String flgEnviadoPbl) {
		this.flgEnviadoPbl = flgEnviadoPbl;
	}

	public Date getFechaNsr() {
		return this.fechaNsr;
	}

	public void setFechaNsr(Date fechaNsr) {
		this.fechaNsr = fechaNsr;
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

	public String getDescripArt() {
		return this.descripArt;
	}

	public void setDescripArt(String descripArt) {
		this.descripArt = descripArt;
	}

	public Float getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(Float uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}
	
	public Long getMotivo() {
		return this.motivo;
	}

	public void setMotivo(Long motivo) {
		this.motivo = motivo;
	}
}