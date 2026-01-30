package es.eroski.misumi.model.pda;

import java.io.Serializable;

import es.eroski.misumi.model.ImagenComercial;

public class PdaDatosImc implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private String descArt;
	private String descArtConCodigo; //Utilizado para formatear la descripción de pantalla
	private String tipoReferencia;
	private String capacidad;
	private String multiplicador;
	private String facing;
	private String imc;
	private String facAncho;
	private String facAlto;
	private String uc;
	private String guardadoImc;
	private boolean tratamientoVegalsa;
	
	
	private String error;
	private String descError;
	private String flgPistola;
	private String usuario;
	private String procede;
	
	public PdaDatosImc() {
	    super();
	}

	public PdaDatosImc( Long codArt, String descArt, String tipoReferencia, String capacidad
					  , String multiplicador, String facing, String imc, String facAncho
					  , String facAlto, String uc
					  , String flgPistola, String usuario
					  ) {
		super();
		this.codArt = codArt;
		this.descArt = descArt;
		this.tipoReferencia = tipoReferencia;
		this.capacidad = capacidad;
		this.multiplicador = multiplicador;
		this.facing = facing;
		this.imc = imc;
		this.facAncho = facAncho;
		this.facAlto = facAlto;
		this.uc = uc;
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

	public String getDescArtConCodigo() {
		return codArt  + "-" + descArt;
	}

	public String getTipoReferencia() {
		return tipoReferencia;
	}

	public void setTipoReferencia(String tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getMultiplicador() {
		return multiplicador;
	}

	public void setMultiplicador(String multiplicador) {
		this.multiplicador = multiplicador;
	}

	public String getFacing() {
		return facing;
	}

	public void setFacing(String facing) {
		this.facing = facing;
	}

	public String getImc() {
		return imc;
	}

	public void setImc(String imc) {
		this.imc = imc;
	}

	public String getFacAncho() {
		return facAncho;
	}

	public void setFacAncho(String facAncho) {
		this.facAncho = facAncho;
	}

	public String getFacAlto() {
		return facAlto;
	}

	public void setFacAlto(String facAlto) {
		this.facAlto = facAlto;
	}
	
	public String getUc() {
		return uc;
	}

	public void setUc(String uc) {
		this.uc = uc;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public boolean getTratamientoVegalsa() {
		return tratamientoVegalsa;
	}

	public void setTratamientoVegalsa(boolean tratamientoVegalsa) {
		this.tratamientoVegalsa = tratamientoVegalsa;
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

	public void setDescArtConCodigo(String descArtConCodigo) {
		this.descArtConCodigo = descArtConCodigo;
	}
	public String getProcede() {
		return procede;
	}

	public void setProcede(String procede) {
		this.procede = procede;
	}
	public String getGuardadoImc() {
		return guardadoImc;
	}

	public void setGuardadoImc(String guardadoImc) {
		this.guardadoImc = guardadoImc;
	}

	public ImagenComercial parseImc(){
		return new ImagenComercial( null, this.codArt, new Long(this.facing)
								  , (this.capacidad != null ? new Long(this.capacidad):null)
								  , (this.facAlto != null ? new Long(this.facAlto):null)
								  , (this.facAncho != null ? new Long(this.facAncho):null)
								  , (this.tipoReferencia != null ? new Long(this.tipoReferencia):null)
								  , null,(this.multiplicador != null ? new Integer(this.multiplicador):null)
								  , (this.imc != null ? new Long(this.imc):null)
								  , null // avisoCambio
								  , new Integer("1") // método
								  , null // sfm
								  , null // diasDeVenta
								  , null // codError
								  , null // descripcionError
								  , this.getFlgPistola(), this.getUsuario()
								  , null, null, null
								  , 0 // flgErrorWSFacingVegalsa
								  , 0 // flgErrorWSFacingAltoAncho
								  );
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PdaDatosImc [codArt=");
		builder.append(codArt);
		builder.append(", descArt=");
		builder.append(descArt);
		builder.append(", descArtConCodigo=");
		builder.append(descArtConCodigo);
		builder.append(", tipoReferencia=");
		builder.append(tipoReferencia);
		builder.append(", capacidad=");
		builder.append(capacidad);
		builder.append(", multiplicador=");
		builder.append(multiplicador);
		builder.append(", facing=");
		builder.append(facing);
		builder.append(", imc=");
		builder.append(imc);
		builder.append(", facAncho=");
		builder.append(facAncho);
		builder.append(", facAlto=");
		builder.append(facAlto);
		builder.append(", uc=");
		builder.append(uc);
		builder.append(", guardadoImc=");
		builder.append(guardadoImc);
		builder.append(", tratamientoVegalsa=");
		builder.append(tratamientoVegalsa);
		builder.append(", error=");
		builder.append(error);
		builder.append(", descError=");
		builder.append(descError);
		builder.append(", flgPistola=");
		builder.append(flgPistola);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append(", procede=");
		builder.append(procede);
		builder.append("]");
		return builder.toString();
	}

}