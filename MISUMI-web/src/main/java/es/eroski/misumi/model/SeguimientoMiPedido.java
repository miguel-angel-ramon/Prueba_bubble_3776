package es.eroski.misumi.model;

import java.io.Serializable;

public class SeguimientoMiPedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private String fechaPedidoDDMMYYYY;
	private String fechaPedidoPantalla;
    private Long codArea;
    private String descArea;
    private Long codSeccion;
    private String descSeccion;
    private Long codCategoria;
    private String descCategoria;
    private String nivel;
    private Long codArt;
    private String mapa;
    private String descrMapa;
    
    public SeguimientoMiPedido() {
		super();
	}
    
	public SeguimientoMiPedido(Long codCentro, String fechaPedidoDDMMYYYY, String fechaPedidoPantalla, 
			Long codArea, String descArea, Long codSeccion, String descSeccion,
			Long codCategoria, String descCategoria, String nivel, Long codArt) {
		super();
		this.codCentro = codCentro;
		this.fechaPedidoDDMMYYYY = fechaPedidoDDMMYYYY;
		this.fechaPedidoPantalla = fechaPedidoPantalla;
		this.codArea = codArea;
		this.descArea = descArea;
		this.codSeccion = codSeccion;
		this.descSeccion = descSeccion;
		this.codCategoria = codCategoria;
		this.descCategoria = descCategoria;
		this.nivel = nivel;
		this.codArt = codArt;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getFechaPedidoDDMMYYYY() {
		return this.fechaPedidoDDMMYYYY;
	}

	public void setFechaPedidoDDMMYYYY(String fechaPedidoDDMMYYYY) {
		this.fechaPedidoDDMMYYYY = fechaPedidoDDMMYYYY;
	}

	public String getFechaPedidoPantalla() {
		return this.fechaPedidoPantalla;
	}

	public void setFechaPedidoPantalla(String fechaPedidoPantalla) {
		this.fechaPedidoPantalla = fechaPedidoPantalla;
	}

	public Long getCodArea() {
		return this.codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public String getDescArea() {
		return this.descArea;
	}

	public void setDescArea(String descArea) {
		this.descArea = descArea;
	}

	public Long getCodSeccion() {
		return this.codSeccion;
	}

	public void setCodSeccion(Long codSeccion) {
		this.codSeccion = codSeccion;
	}

	public String getDescSeccion() {
		return this.descSeccion;
	}

	public void setDescSeccion(String descSeccion) {
		this.descSeccion = descSeccion;
	}

	public Long getCodCategoria() {
		return this.codCategoria;
	}

	public void setCodCategoria(Long codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getDescCategoria() {
		return this.descCategoria;
	}

	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}

	public String getNivel() {
		return this.nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	public String getDescrMapa() {
		return descrMapa;
	}

	public void setDescrMapa(String descrMapa) {
		this.descrMapa = descrMapa;
	}
}