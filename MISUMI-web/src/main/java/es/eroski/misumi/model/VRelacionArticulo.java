package es.eroski.misumi.model;

import java.io.Serializable;

public class VRelacionArticulo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private Long codArtRela;
	private String descripArt;
	private Float uniCajaServ;
	private String formatoProductivoActivo;
	public VRelacionArticulo() {
	    super();
	}

	public VRelacionArticulo(Long codCentro, Long codArt, Long codArtRela,
			String descripArt, Float uniCajaServ, String formatoProductivoActivo) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.codArtRela = codArtRela;
		this.descripArt = descripArt;
		this.uniCajaServ = uniCajaServ;
		this.formatoProductivoActivo=formatoProductivoActivo;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public Long getCodArtRela() {
		return this.codArtRela;
	}

	public void setCodArtRela(Long codArtRela) {
		this.codArtRela = codArtRela;
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

	public String getFormatoProductivoActivo() {
		return formatoProductivoActivo;
	}

	public void setFormatoProductivoActivo(String formatoProductivoActivo) {
		this.formatoProductivoActivo = formatoProductivoActivo;
	}
	
}