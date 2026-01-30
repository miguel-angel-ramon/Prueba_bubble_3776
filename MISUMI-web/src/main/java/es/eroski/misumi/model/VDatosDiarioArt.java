package es.eroski.misumi.model;

import java.io.Serializable;

public class VDatosDiarioArt implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt; 
	private Long codArtGrid;
	private String descripArt;
	private String descripArtGrid;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private Long codifiComerSup;
	private Long codifiComerHip;
	private Long estlogN1;
	private Long estlogN2;
	private Long estlogN3;
	private String atrib1;
	private String atrib2;
	private Long tipoMarca;
	private Long codMarca;
	private Long codAnt;
	private Long codFpMadre;
	private Long balanza;
	private String tipoCompraVenta;
	private Long vidaUtil;
	private String formato;
	
	public VDatosDiarioArt() {
	    super();
	}

	public VDatosDiarioArt(Long codArt, String descripArt, Long grupo1,
			Long grupo2, Long grupo3, Long grupo4, Long grupo5,
			Long codifiComerSup, Long codifiComerHip, Long estlogN1,
			Long estlogN2, Long estlogN3, String atrib1, String atrib2,
			Long tipoMarca, Long codMarca, Long codAnt, Long codFpMadre,
			Long balanza, String tipoCompraVenta, Long vidaUtil, String formato) {
		super();
		this.codArt = codArt;
		this.descripArt = descripArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.codifiComerSup = codifiComerSup;
		this.codifiComerHip = codifiComerHip;
		this.estlogN1 = estlogN1;
		this.estlogN2 = estlogN2;
		this.estlogN3 = estlogN3;
		this.atrib1 = atrib1;
		this.atrib2 = atrib2;
		this.tipoMarca = tipoMarca;
		this.codMarca = codMarca;
		this.codAnt = codAnt;
		this.codFpMadre = codFpMadre;
		this.balanza = balanza;
		this.tipoCompraVenta = tipoCompraVenta;
		this.vidaUtil = vidaUtil;
		this.setFormato(formato);
	}

	public VDatosDiarioArt(Long codArt, String descripArt) {
		super();
		this.codArt = codArt;
		this.descripArt = descripArt;
	}



	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	
	public Long getCodArtGrid() {
		return codArtGrid;
	}

	public void setCodArtGrid(Long codArtGrid) {
		this.codArtGrid = codArtGrid;
	}
	public String getDescripArt() {
		return this.descripArt;
	}

	public void setDescripArt(String descripArt) {
		this.descripArt = descripArt;
	}

	public String getDescripArtGrid() {
		return descripArtGrid;
	}

	public void setDescripArtGrid(String descripArtGrid) {
		this.descripArtGrid = descripArtGrid;
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

	public Long getCodifiComerSup() {
		return this.codifiComerSup;
	}

	public void setCodifiComerSup(Long codifiComerSup) {
		this.codifiComerSup = codifiComerSup;
	}

	public Long getCodifiComerHip() {
		return this.codifiComerHip;
	}

	public void setCodifiComerHip(Long codifiComerHip) {
		this.codifiComerHip = codifiComerHip;
	}

	public Long getEstlogN1() {
		return this.estlogN1;
	}

	public void setEstlogN1(Long estlogN1) {
		this.estlogN1 = estlogN1;
	}

	public Long getEstlogN2() {
		return this.estlogN2;
	}

	public void setEstlogN2(Long estlogN2) {
		this.estlogN2 = estlogN2;
	}

	public Long getEstlogN3() {
		return this.estlogN3;
	}

	public void setEstlogN3(Long estlogN3) {
		this.estlogN3 = estlogN3;
	}

	public String getAtrib1() {
		return this.atrib1;
	}

	public void setAtrib1(String atrib1) {
		this.atrib1 = atrib1;
	}

	public String getAtrib2() {
		return this.atrib2;
	}

	public void setAtrib2(String atrib2) {
		this.atrib2 = atrib2;
	}

	public Long getTipoMarca() {
		return this.tipoMarca;
	}

	public void setTipoMarca(Long tipoMarca) {
		this.tipoMarca = tipoMarca;
	}

	public Long getCodMarca() {
		return this.codMarca;
	}

	public void setCodMarca(Long codMarca) {
		this.codMarca = codMarca;
	}

	public Long getCodAnt() {
		return this.codAnt;
	}

	public void setCodAnt(Long codAnt) {
		this.codAnt = codAnt;
	}

	public Long getCodFpMadre() {
		return this.codFpMadre;
	}

	public void setCodFpMadre(Long codFpMadre) {
		this.codFpMadre = codFpMadre;
	}
	
	public Long getBalanza() {
		return this.balanza;
	}

	public void setBalanza(Long balanza) {
		this.balanza = balanza;
	}

	public String getTipoCompraVenta() {
		return this.tipoCompraVenta;
	}

	public void setTipoCompraVenta(String tipoCompraVenta) {
		this.tipoCompraVenta = tipoCompraVenta;
	}

	public Long getVidaUtil() {
		return vidaUtil;
	}

	public void setVidaUtil(Long vidaUtil) {
		this.vidaUtil = vidaUtil;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}
}