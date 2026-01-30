package es.eroski.misumi.model;

/**
 * Representación en java del tipo de dato APR_R_IMC_REG.
 * @author bihealga
 */
public class ImagenComercial {
	private Long centro;
	private Long referencia;
	private Long facing;
	private Long capacidad;
	private Long facingAlto;
	private Long facingAncho;
	private Long tipoReferencia;
	private String tipoPlano;
	private Integer multiplicador;
	private Long imc;
	private String avisoCambio;
	private Integer metodo;
	private Long sfm;
	private Long diasDeVenta;
	private Integer codError;
	private String descripcionError;
	private String flgPistola;
	private String usuario;
	
	private Integer tratamientoVegalsa;
	private Integer facingModificable;
	private Integer centroParametrizado;

	//Campo para control de error del WS de Facing Vegalsa.
	private int flgErrorWSFacingVegalsa;

	//Campo para control de error del WS de actualizar Facing Alto x Ancho. 
	private int flgErrorWSFacingAltoAncho;
	
	private String procede;
	
	public ImagenComercial() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImagenComercial( Long centro, Long referencia, Long facing, Long capacidad
						  , Long facingAlto, Long facingAncho, Long tipoReferencia
						  , String tipoPlano, Integer multiplicador, Long imc, String avisoCambio, Integer metodo
						  , Long sfm, Long diasDeVenta, Integer codError, String descripcionError
						  , String flgPistola, String usuario
						  , Integer tratamientoVegalsa, Integer facingModificable, Integer centroParametrizado
						  , int flgErrorWSFacingVegalsa, int flgErrorWSFacingAltoAncho
						  ){
		super();
		this.centro = centro;
		this.referencia = referencia;
		this.facing = facing;
		this.capacidad = capacidad;
		this.facingAlto = facingAlto;
		this.facingAncho = facingAncho;
		this.tipoReferencia = tipoReferencia;
		this.tipoPlano = tipoPlano;
		this.multiplicador = multiplicador;
		this.imc = imc;
		this.avisoCambio = avisoCambio;
		this.metodo = metodo;
		this.sfm = sfm;
		this.diasDeVenta = diasDeVenta;
		this.codError = codError;
		this.descripcionError = descripcionError;
		this.flgPistola = flgPistola;
		this.usuario = usuario;
		
		this.tratamientoVegalsa = tratamientoVegalsa;
		this.facingModificable = facingModificable;
		this.centroParametrizado = centroParametrizado;
		this.flgErrorWSFacingVegalsa = flgErrorWSFacingVegalsa;
		this.flgErrorWSFacingAltoAncho = flgErrorWSFacingAltoAncho;
	}
	
	public Long getCentro() {
		return centro;
	}
	public void setCentro(Long centro) {
		this.centro = centro;
	}
	public Long getReferencia() {
		return referencia;
	}
	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}
	public Long getFacing() {
		return facing;
	}
	public void setFacing(Long facing) {
		this.facing = facing;
	}
	public Long getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}
	public Long getFacingAlto() {
		return facingAlto;
	}
	public void setFacingAlto(Long facingAlto) {
		this.facingAlto = facingAlto;
	}
	public Long getFacingAncho() {
		return facingAncho;
	}
	public void setFacingAncho(Long facingAncho) {
		this.facingAncho = facingAncho;
	}
	public Long getTipoReferencia() {
		return tipoReferencia;
	}
	public void setTipoReferencia(Long tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}
	public String getTipoPlano() {
		return tipoPlano;
	}
	public void setTipoPlano(String tipoPlano) {
		this.tipoPlano = tipoPlano;
	}
	public Integer getMultiplicador() {
		return multiplicador;
	}
	public void setMultiplicador(Integer multiplicador) {
		this.multiplicador = multiplicador;
	}
	public Long getImc() {
		return imc;
	}
	public void setImc(Long imc) {
		this.imc = imc;
	}
	public String getAvisoCambio() {
		return avisoCambio;
	}
	public void setAvisoCambio(String avisoCambio) {
		this.avisoCambio = avisoCambio;
	}
	public Integer getMetodo() {
		return metodo;
	}
	public void setMetodo(Integer metodo) {
		this.metodo = metodo;
	}
	public Long getSfm() {
		return sfm;
	}
	public void setSfm(Long sfm) {
		this.sfm = sfm;
	}
	public Long getDiasDeVenta() {
		return diasDeVenta;
	}
	public void setDiasDeVenta(Long diasDeVenta) {
		this.diasDeVenta = diasDeVenta;
	}
	public Integer getCodError() {
		return codError;
	}
	public void setCodError(Integer codError) {
		this.codError = codError;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	public String getFlgPistola() {
		return flgPistola;
	}

	public void setFlgPistola(String flgPistola) {
		this.flgPistola = flgPistola;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getTratamientoVegalsa() {
		return tratamientoVegalsa;
	}

	public void setTratamientoVegalsa(Integer tratamientoVegalsa) {
		this.tratamientoVegalsa = tratamientoVegalsa;
	}

	public Integer getFacingModificable() {
		return facingModificable;
	}

	public void setFacingModificable(Integer facingModificable) {
		this.facingModificable = facingModificable;
	}

	public Integer getCentroParametrizado() {
		return centroParametrizado;
	}

	public void setCentroParametrizado(Integer centroParametrizado) {
		this.centroParametrizado = centroParametrizado;
	}

	public int getFlgErrorWSFacingVegalsa() {
		return flgErrorWSFacingVegalsa;
	}

	public void setFlgErrorWSFacingVegalsa(int flgErrorWSFacingVegalsa) {
		this.flgErrorWSFacingVegalsa = flgErrorWSFacingVegalsa;
	}

	public int getFlgErrorWSFacingAltoAncho() {
		return flgErrorWSFacingAltoAncho;
	}

	public void setFlgErrorWSFacingAltoAncho(int flgErrorWSFacingAltoAncho) {
		this.flgErrorWSFacingAltoAncho = flgErrorWSFacingAltoAncho;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImagenComercial [");
		if (centro != null) {
			builder.append("centro=");
			builder.append(centro);
			builder.append(", ");
		}
		if (referencia != null) {
			builder.append("referencia=");
			builder.append(referencia);
			builder.append(", ");
		}
		if (facing != null) {
			builder.append("facing=");
			builder.append(facing);
			builder.append(", ");
		}
		if (capacidad != null) {
			builder.append("capacidad=");
			builder.append(capacidad);
			builder.append(", ");
		}
		if (facingAlto != null) {
			builder.append("facingAlto=");
			builder.append(facingAlto);
			builder.append(", ");
		}
		if (facingAncho != null) {
			builder.append("facingAncho=");
			builder.append(facingAncho);
			builder.append(", ");
		}
		if (tipoReferencia != null) {
			builder.append("tipoReferencia=");
			builder.append(tipoReferencia);
			builder.append(", ");
		}
		if (tipoPlano != null) {
			builder.append("tipoPlano=");
			builder.append(tipoPlano);
			builder.append(", ");
		}
		if (multiplicador != null) {
			builder.append("multiplicador=");
			builder.append(multiplicador);
			builder.append(", ");
		}
		if (imc != null) {
			builder.append("imc=");
			builder.append(imc);
			builder.append(", ");
		}
		if (avisoCambio != null) {
			builder.append("avisoCambio=");
			builder.append(avisoCambio);
			builder.append(", ");
		}
		if (metodo != null) {
			builder.append("metodo=");
			builder.append(metodo);
			builder.append(", ");
		}
		if (sfm != null) {
			builder.append("sfm=");
			builder.append(sfm);
			builder.append(", ");
		}
		if (diasDeVenta != null) {
			builder.append("diasDeVenta=");
			builder.append(diasDeVenta);
			builder.append(", ");
		}
		if (codError != null) {
			builder.append("codError=");
			builder.append(codError);
			builder.append(", ");
		}
		if (descripcionError != null) {
			builder.append("descripcionError=");
			builder.append(descripcionError);
			builder.append(", ");
		}
		if (flgPistola != null) {
			builder.append("flgPistola=");
			builder.append(flgPistola);
			builder.append(", ");
		}
		if (usuario != null) {
			builder.append("usuario=");
			builder.append(usuario);
			builder.append(", ");
		}
		if (tratamientoVegalsa != null) {
			builder.append("tratamientoVegalsa=");
			builder.append(tratamientoVegalsa);
			builder.append(", ");
		}
		if (facingModificable != null) {
			builder.append("facingModificable=");
			builder.append(facingModificable);
			builder.append(", ");
		}
		if (centroParametrizado != null) {
			builder.append("centroParametrizado=");
			builder.append(centroParametrizado);
			builder.append(", ");
		}
		builder.append("flgErrorWSFacingVegalsa=");
		builder.append(flgErrorWSFacingVegalsa);
		builder.append(", ");
		builder.append("flgErrorWSFacingAltoAncho=");
		builder.append(flgErrorWSFacingAltoAncho);
		builder.append("]");
		return builder.toString();
	}

	public String getProcede() {
		return procede;
	}

	public void setProcede(String procede) {
		this.procede = procede;
	}
}
