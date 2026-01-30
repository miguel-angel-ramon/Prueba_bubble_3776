package es.eroski.misumi.model;

import java.io.Serializable;

public class VReferenciasPedirSIADetall implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long codArt; 
	private Long codN1Ec;
	private Long codN2Ec;
	private Long codN3Ec;
	private Long codN4Ec;
	private Long codN5Ec;
	
	public VReferenciasPedirSIADetall() {
		super();
	}

	public VReferenciasPedirSIADetall(Long codCentro, Long codArt, Long codN1Ec, Long codN2Ec, Long codN3Ec, Long codN4Ec,
			Long codN5Ec) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.codN1Ec = codN1Ec;
		this.codN2Ec = codN2Ec;
		this.codN3Ec = codN3Ec;
		this.codN4Ec = codN4Ec;
		this.codN5Ec = codN5Ec;
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

	public Long getCodN1Ec() {
		return this.codN1Ec;
	}

	public void setCodN1Ec(Long codN1Ec) {
		this.codN1Ec = codN1Ec;
	}

	public Long getCodN2Ec() {
		return this.codN2Ec;
	}

	public void setCodN2Ec(Long codN2Ec) {
		this.codN2Ec = codN2Ec;
	}

	public Long getCodN3Ec() {
		return this.codN3Ec;
	}

	public void setCodN3Ec(Long codN3Ec) {
		this.codN3Ec = codN3Ec;
	}

	public Long getCodN4Ec() {
		return this.codN4Ec;
	}

	public void setCodN4Ec(Long codN4Ec) {
		this.codN4Ec = codN4Ec;
	}

	public Long getCodN5Ec() {
		return this.codN5Ec;
	}

	public void setCodN5Ec(Long codN5Ec) {
		this.codN5Ec = codN5Ec;
	}
}