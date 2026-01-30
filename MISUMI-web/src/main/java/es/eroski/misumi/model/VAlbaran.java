package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class VAlbaran implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long numAlbaran;
	private Long numExpedicion;
	private String estado;
	private Date fechaPrevisEnt;
	private Date fechaConfirmado;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private String fechaConfirmadoFormat;
	private Date fechaAlbaran;
	
	public VAlbaran() {
		super();
	}

	public VAlbaran(Long codCentro, Long numAlbaran, Long numExpedicion,
			String estado, Date fechaPrevisEnt, Date fechaConfirmado,
			Long grupo1, Long grupo2, Long grupo3, String fechaConfirmadoFormat,
			Date fechaAlbaran) {
		super();
		this.codCentro = codCentro;
		this.numAlbaran = numAlbaran;
		this.numExpedicion = numExpedicion;
		this.estado = estado;
		this.fechaPrevisEnt = fechaPrevisEnt;
		this.fechaConfirmado = fechaConfirmado;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.fechaConfirmadoFormat = fechaConfirmadoFormat;
		this.fechaAlbaran = fechaAlbaran;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getNumAlbaran() {
		return this.numAlbaran;
	}

	public void setNumAlbaran(Long numAlbaran) {
		this.numAlbaran = numAlbaran;
	}

	public Long getNumExpedicion() {
		return this.numExpedicion;
	}

	public void setNumExpedicion(Long numExpedicion) {
		this.numExpedicion = numExpedicion;
	}
	
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaPrevisEnt() {
		return this.fechaPrevisEnt;
	}

	public void setFechaPrevisEnt(Date fechaPrevisEnt) {
		this.fechaPrevisEnt = fechaPrevisEnt;
	}
	
	public Date getFechaConfirmado() {
		return this.fechaConfirmado;
	}

	public void setFechaConfirmado(Date fechaConfirmado) {
		this.fechaConfirmado = fechaConfirmado;
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

	public String getFechaConfirmadoFormat() {
		return fechaConfirmadoFormat;
	}

	public void setFechaConfirmadoFormat(String fechaConfirmadoFormat) {
		this.fechaConfirmadoFormat = fechaConfirmadoFormat;
	}

	public Date getFechaAlbaran() {
		return fechaAlbaran;
	}

	public void setFechaAlbaran(Date fechaAlbaran) {
		this.fechaAlbaran = fechaAlbaran;
	}
}