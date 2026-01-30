package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VConfirmacionEnviado implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long codPlat;
	private Long codPedPlat;
	private Long codPedAprovCentral;
	private Date fechaTrans;
	private Date fechaPed;
	private Date fechaExp;
	private Long codArt;
	private Float cajEnv;
	private Float uniServ;
	private String tipoPed;
	private String flgEnviadoPbl;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String descripArt;
	private Float cajaNormal;
	private Float cajaEmpuje;
	private Float cajaImpl;
	private Float cajaIntertienda;

	public Float getCajaIntertienda() {
		return cajaIntertienda;
	}

	public void setCajaIntertienda(Float cajaIntertienda) {
		this.cajaIntertienda = cajaIntertienda;
	}

	public VConfirmacionEnviado() {
	    super();
	}

	public VConfirmacionEnviado(Long codCentro, Long codPlat, Long codPedPlat,
			Long codPedAprovCentral, Date fechaTrans, Date fechaPed, Date fechaExp,
			Long codArt, Float cajEnv, Float uniServ, String tipoPed,
			String flgEnviadoPbl, Long grupo1, Long grupo2, Long grupo3,
			Long grupo4, Long grupo5, String descripArt, Float cajaNormal,
			Float cajaEmpuje, Float cajaImpl, Float cajaIntertienda) {
		super();
		this.codCentro = codCentro;
		this.codPlat = codPlat;
		this.codPedPlat = codPedPlat;
		this.codPedAprovCentral = codPedAprovCentral;
		this.fechaTrans = fechaTrans;
		this.fechaPed = fechaPed;
		this.fechaExp = fechaExp;
		this.codArt = codArt;
		this.cajEnv = cajEnv;
		this.uniServ = uniServ;
		this.tipoPed = tipoPed;
		this.flgEnviadoPbl = flgEnviadoPbl;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripArt = descripArt;
		this.cajaNormal = cajaNormal;
		this.cajaEmpuje = cajaEmpuje;
		this.cajaImpl = cajaImpl;
		this.cajaIntertienda = cajaIntertienda;
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

	public Date getFechaExp() {
		return this.fechaExp;
	}

	public void setFechaExp(Date fechaExp) {
		this.fechaExp = fechaExp;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public Float getCajEnv() {
		return this.cajEnv;
	}

	public void setCajEnv(Float cajEnv) {
		this.cajEnv = cajEnv;
	}

	public Float getUniServ() {
		return this.uniServ;
	}

	public void setUniServ(Float uniServ) {
		this.uniServ = uniServ;
	}

	public String getTipoPed() {
		return this.tipoPed;
	}

	public void setTipoPed(String tipoPed) {
		this.tipoPed = tipoPed;
	}

	public String getFlgEnviadoPbl() {
		return this.flgEnviadoPbl;
	}

	public void setFlgEnviadoPbl(String flgEnviadoPbl) {
		this.flgEnviadoPbl = flgEnviadoPbl;
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

	public Float getCajaNormal() {
		return this.cajaNormal;
	}

	public void setCajaNormal(Float cajaNormal) {
		this.cajaNormal = cajaNormal;
	}

	public Float getCajaEmpuje() {
		return this.cajaEmpuje;
	}

	public void setCajaEmpuje(Float cajaEmpuje) {
		this.cajaEmpuje = cajaEmpuje;
	}

	public Float getCajaImpl() {
		return this.cajaImpl;
	}

	public void setCajaImpl(Float cajaImpl) {
		this.cajaImpl = cajaImpl;
	}
}