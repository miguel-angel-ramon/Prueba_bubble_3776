package es.eroski.misumi.model;

import java.io.Serializable;

public class Motivo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mapaHoy;
    private String pedir;
    private String tipoMensaje;
	private TextoMotivo textoMotivo;
	private String motivoWebservice; //Indica con S que el motivo proviene del webservice
	private String origenPantalla;
	private Long codArt; 
	private Long codCentro; 
	private Long codArtSustituidaPorEroski;
	
	public Motivo() {
		super();
	}

	public Motivo( String pedir,TextoMotivo textoMotivo,
			String motivoWebservice, String tipoMensaje) {
		super();
		
		this.pedir = pedir;
		this.textoMotivo = textoMotivo;
		this.motivoWebservice = motivoWebservice;
		this.tipoMensaje=tipoMensaje;
	}
	public Motivo( String pedir,TextoMotivo textoMotivo,
			String motivoWebservice) {
		super();
		
		this.pedir = pedir;
		this.textoMotivo = textoMotivo;
		this.motivoWebservice = motivoWebservice;
	}

	public String getMapaHoy() {
		return this.mapaHoy;
	}

	public void setMapaHoy(String mapaHoy) {
		this.mapaHoy = mapaHoy;
	}

	public String getPedir() {
		return this.pedir;
	}

	public void setPedir(String pedir) {
		this.pedir = pedir;
	}

	public TextoMotivo getTextoMotivo() {
		return this.textoMotivo;
	}

	public void setTextoMotivo(TextoMotivo textoMotivo) {
		this.textoMotivo = textoMotivo;
	}

	public String getMotivoWebservice() {
		return this.motivoWebservice;
	}

	public void setMotivoWebservice(String motivoWebservice) {
		this.motivoWebservice = motivoWebservice;
	}

	public String getTipoMensaje() {
		return this.tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getOrigenPantalla() {
		return origenPantalla;
	}

	public void setOrigenPantalla(String origenPantalla) {
		this.origenPantalla = origenPantalla;
	}
	
	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	
	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArtSustituidaPorEroski() {
		return codArtSustituidaPorEroski;
	}

	public void setCodArtSustituidaPorEroski(Long codArtSustituidaPorEroski) {
		this.codArtSustituidaPorEroski = codArtSustituidaPorEroski;
	}
}
