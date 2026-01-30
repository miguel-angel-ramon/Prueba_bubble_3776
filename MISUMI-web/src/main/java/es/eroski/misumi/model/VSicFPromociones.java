package es.eroski.misumi.model;

import java.io.Serializable;

public class VSicFPromociones implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codPromocion; 
	private Long ejerPromocion;
	private Long codTpEstrLoc;
	private String fecInicio;
	private String fecFin;
	private Long codLoc;
	private Long nelCodN1;
	private Long nelCodN2;
	private Long nelCodN3;
	private Long codArticulo;
	private String neaCodN1;
	private String neaCodN2;
	private String neaCodN3;
	private String neaCodN4;
	private String neaCodN5;
	private Long codTpPromocion;
	private String flgFolleto;
	private String flgNovedad;
	private String cabGondola;
	private Double cantN;
	private Double cantM;
	private String flgCompraRegalo;
	private String codTpDescuento;
	private Double cantDescuento;
	private Double umbral;
	private String flgTv;
	private String financiacion;
	private Long codBloque;
	private String flgPortada;
	private String flgContraportada;
	private String flgDestacado;
	private Long codZonaCampana;
	private Double pvp;
	
	public VSicFPromociones() {
	    super();
	}

	public VSicFPromociones(Long codPromocion, Long ejerPromocion,
			Long codTpEstrLoc, String fecInicio, String fecFin, Long codLoc,
			Long nelCodN1, Long nelCodN2, Long nelCodN3, Long codArticulo,
			String neaCodN1, String neaCodN2, String neaCodN3, String neaCodN4,
			String neaCodN5, Long codTpPromocion, String flgFolleto,
			String flgNovedad, String cabGondola, Double cantN, Double cantM,
			String flgCompraRegalo, String codTpDescuento,
			Double cantDescuento, Double umbral, String flgTv,
			String financiacion, Long codBloque, String flgPortada,
			String flgContraportada, String flgDestacado, Long codZonaCampana,
			Double pvp) {
		super();
		this.codPromocion = codPromocion;
		this.ejerPromocion = ejerPromocion;
		this.codTpEstrLoc = codTpEstrLoc;
		this.fecInicio = fecInicio;
		this.fecFin = fecFin;
		this.codLoc = codLoc;
		this.nelCodN1 = nelCodN1;
		this.nelCodN2 = nelCodN2;
		this.nelCodN3 = nelCodN3;
		this.codArticulo = codArticulo;
		this.neaCodN1 = neaCodN1;
		this.neaCodN2 = neaCodN2;
		this.neaCodN3 = neaCodN3;
		this.neaCodN4 = neaCodN4;
		this.neaCodN5 = neaCodN5;
		this.codTpPromocion = codTpPromocion;
		this.flgFolleto = flgFolleto;
		this.flgNovedad = flgNovedad;
		this.cabGondola = cabGondola;
		this.cantN = cantN;
		this.cantM = cantM;
		this.flgCompraRegalo = flgCompraRegalo;
		this.codTpDescuento = codTpDescuento;
		this.cantDescuento = cantDescuento;
		this.umbral = umbral;
		this.flgTv = flgTv;
		this.financiacion = financiacion;
		this.codBloque = codBloque;
		this.flgPortada = flgPortada;
		this.flgContraportada = flgContraportada;
		this.flgDestacado = flgDestacado;
		this.codZonaCampana = codZonaCampana;
		this.pvp = pvp;
	}

	public VSicFPromociones(String cabGondola) {
		super();
		this.cabGondola = cabGondola;
	}

	public Long getCodPromocion() {
		return this.codPromocion;
	}

	public void setCodPromocion(Long codPromocion) {
		this.codPromocion = codPromocion;
	}

	public Long getEjerPromocion() {
		return this.ejerPromocion;
	}

	public void setEjerPromocion(Long ejerPromocion) {
		this.ejerPromocion = ejerPromocion;
	}

	public Long getCodTpEstrLoc() {
		return this.codTpEstrLoc;
	}

	public void setCodTpEstrLoc(Long codTpEstrLoc) {
		this.codTpEstrLoc = codTpEstrLoc;
	}

	public String getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(String fecInicio) {
		this.fecInicio = fecInicio;
	}

	public String getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(String fecFin) {
		this.fecFin = fecFin;
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public Long getNelCodN1() {
		return this.nelCodN1;
	}

	public void setNelCodN1(Long nelCodN1) {
		this.nelCodN1 = nelCodN1;
	}

	public Long getNelCodN2() {
		return this.nelCodN2;
	}

	public void setNelCodN2(Long nelCodN2) {
		this.nelCodN2 = nelCodN2;
	}

	public Long getNelCodN3() {
		return this.nelCodN3;
	}

	public void setNelCodN3(Long nelCodN3) {
		this.nelCodN3 = nelCodN3;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getNeaCodN1() {
		return this.neaCodN1;
	}

	public void setNeaCodN1(String neaCodN1) {
		this.neaCodN1 = neaCodN1;
	}

	public String getNeaCodN2() {
		return this.neaCodN2;
	}

	public void setNeaCodN2(String neaCodN2) {
		this.neaCodN2 = neaCodN2;
	}

	public String getNeaCodN3() {
		return this.neaCodN3;
	}

	public void setNeaCodN3(String neaCodN3) {
		this.neaCodN3 = neaCodN3;
	}

	public String getNeaCodN4() {
		return this.neaCodN4;
	}

	public void setNeaCodN4(String neaCodN4) {
		this.neaCodN4 = neaCodN4;
	}

	public String getNeaCodN5() {
		return this.neaCodN5;
	}

	public void setNeaCodN5(String neaCodN5) {
		this.neaCodN5 = neaCodN5;
	}

	public Long getCodTpPromocion() {
		return this.codTpPromocion;
	}

	public void setCodTpPromocion(Long codTpPromocion) {
		this.codTpPromocion = codTpPromocion;
	}

	public String getFlgFolleto() {
		return this.flgFolleto;
	}

	public void setFlgFolleto(String flgFolleto) {
		this.flgFolleto = flgFolleto;
	}

	public String getFlgNovedad() {
		return this.flgNovedad;
	}

	public void setFlgNovedad(String flgNovedad) {
		this.flgNovedad = flgNovedad;
	}

	public String getCabGondola() {
		return this.cabGondola;
	}

	public void setCabGondola(String cabGondola) {
		this.cabGondola = cabGondola;
	}

	public Double getCantN() {
		return this.cantN;
	}

	public void setCantN(Double cantN) {
		this.cantN = cantN;
	}

	public Double getCantM() {
		return this.cantM;
	}

	public void setCantM(Double cantM) {
		this.cantM = cantM;
	}

	public String getFlgCompraRegalo() {
		return this.flgCompraRegalo;
	}

	public void setFlgCompraRegalo(String flgCompraRegalo) {
		this.flgCompraRegalo = flgCompraRegalo;
	}

	public String getCodTpDescuento() {
		return this.codTpDescuento;
	}

	public void setCodTpDescuento(String codTpDescuento) {
		this.codTpDescuento = codTpDescuento;
	}

	public Double getCantDescuento() {
		return this.cantDescuento;
	}

	public void setCantDescuento(Double cantDescuento) {
		this.cantDescuento = cantDescuento;
	}

	public Double getUmbral() {
		return this.umbral;
	}

	public void setUmbral(Double umbral) {
		this.umbral = umbral;
	}

	public String getFlgTv() {
		return this.flgTv;
	}

	public void setFlgTv(String flgTv) {
		this.flgTv = flgTv;
	}

	public String getFinanciacion() {
		return this.financiacion;
	}

	public void setFinanciacion(String financiacion) {
		this.financiacion = financiacion;
	}

	public Long getCodBloque() {
		return this.codBloque;
	}

	public void setCodBloque(Long codBloque) {
		this.codBloque = codBloque;
	}

	public String getFlgPortada() {
		return this.flgPortada;
	}

	public void setFlgPortada(String flgPortada) {
		this.flgPortada = flgPortada;
	}

	public String getFlgContraportada() {
		return this.flgContraportada;
	}

	public void setFlgContraportada(String flgContraportada) {
		this.flgContraportada = flgContraportada;
	}

	public String getFlgDestacado() {
		return this.flgDestacado;
	}

	public void setFlgDestacado(String flgDestacado) {
		this.flgDestacado = flgDestacado;
	}

	public Long getCodZonaCampana() {
		return this.codZonaCampana;
	}

	public void setCodZonaCampana(Long codZonaCampana) {
		this.codZonaCampana = codZonaCampana;
	}

	public Double getPvp() {
		return this.pvp;
	}

	public void setPvp(Double pvp) {
		this.pvp = pvp;
	}


}