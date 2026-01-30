package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PedidoAdicionalAyuda1 implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cOferta; // anoOferta-numOferta
	private String cPeriodo;// fechaIni-fechaFin
	private String tipoOfertaString;// String variable dependiendo del tipo de oferta
	private Double cPvp; // formateo (/100) hecho en la vista

	private String fechaIniPeriodo;// fechaIni Periodo
	private String fechaFinPeriodo;// fechaFin Periodo

	private List<DiaVentasUltimasOfertas> listaDiaVentasUltimasOfertas; 

	// ventas en los tres d�as iniciales
	private Float cVD1;
	private Float cVD2;
	private Float cVD3;

	// estos datos realmente no har�a falta guardar. Los guardamos por si acaso
	private Date d1;
	private Date d2;
	private Date d3;

	private Float totalVentas;

	public PedidoAdicionalAyuda1() {
		super();
	}

	public PedidoAdicionalAyuda1(String cOferta, String cPeriodo, Double cPvp,
			List<DiaVentasUltimasOfertas> listaDiaVentasUltimasOfertas) {
		super();

		this.cOferta = cOferta;
		this.cPeriodo = cPeriodo;
		this.cPvp = cPvp;
		this.listaDiaVentasUltimasOfertas = listaDiaVentasUltimasOfertas;
	}

	public PedidoAdicionalAyuda1(String cOferta, String cPeriodo, Double cPvp, String fechaIniPeriodo, String fechaFinPeriodo) {
		super();
		this.cOferta = cOferta;
		this.cPeriodo = cPeriodo;
		this.cPvp = cPvp;
		this.fechaIniPeriodo = fechaIniPeriodo;
		this.fechaFinPeriodo = fechaFinPeriodo;
	}

	public PedidoAdicionalAyuda1(String cOferta, String cPeriodo, Double cPvp,
			Float cVD1, Float cVD2, Float cVD3) {
		super();

		this.cOferta = cOferta;
		this.cPeriodo = cPeriodo;
		this.cPvp = cPvp;
		this.cVD1 = cVD1;
		this.cVD2 = cVD2;
		this.cVD3 = cVD3;
	}

	public String getcOferta() {
		return this.cOferta;
	}

	public void setcOferta(String cOferta) {
		this.cOferta = cOferta;
	}

	public String getTipoOfertaString() {
		return this.tipoOfertaString;
	}

	public void setTipoOfertaString(String tipoOfertaString) {
		this.tipoOfertaString = tipoOfertaString;
	}
	
	public String getcPeriodo() {
		return this.cPeriodo;
	}

	public void setcPeriodo(String cPeriodo) {
		this.cPeriodo = cPeriodo;
	}

	public String getFechaIniPeriodo() {
		return this.fechaIniPeriodo;
	}

	public void setFechaIniPeriodo(String fechaIniPeriodo) {
		this.fechaIniPeriodo = fechaIniPeriodo;
	}

	public String getFechaFinPeriodo() {
		return this.fechaFinPeriodo;
	}

	public void setFechaFinPeriodo(String fechaFinPeriodo) {
		this.fechaFinPeriodo = fechaFinPeriodo;
	}

	public Double getcPvp() {
		return this.cPvp;
	}

	public void setcPvp(Double cPvp) {
		this.cPvp = cPvp;
	}

	public Float getcVD1() {
		return this.cVD1;
	}

	public void setcVD1(Float cVD1) {
		this.cVD1 = cVD1;
	}

	public Float getcVD2() {
		return this.cVD2;
	}

	public void setcVD2(Float cVD2) {
		this.cVD2 = cVD2;
	}

	public Float getcVD3() {
		return this.cVD3;
	}

	public void setcVD3(Float cVD3) {
		this.cVD3 = cVD3;
	}

	public Date getD1() {
		return this.d1;
	}

	public void setD1(Date d1) {
		this.d1 = d1;
	}

	public Date getD2() {
		return this.d2;
	}

	public void setD2(Date d2) {
		this.d2 = d2;
	}

	public Date getD3() {
		return this.d3;
	}

	public void setD3(Date d3) {
		this.d3 = d3;
	}

	public Float getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(Float totalVentas) {
		this.totalVentas = totalVentas;
	}

	public List<DiaVentasUltimasOfertas> getListaDiaVentasUltimasOfertas() {
		return listaDiaVentasUltimasOfertas;
	}

	public void setListaDiaVentasUltimasOfertas(List<DiaVentasUltimasOfertas> listaDiaVentasUltimasOfertas) {
		this.listaDiaVentasUltimasOfertas = listaDiaVentasUltimasOfertas;
	}
	
}
