package es.eroski.misumi.model.pda;

import java.io.Serializable;
import java.math.BigInteger;

public class PdaArticulo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long codArt;
	private Long codArtPromo;
	private String descArt;
	private String tipo;
	private BigInteger unidades;
	private String kgs;
	private String bandejas;
	private String stock;
	private String precio;
	private String precioKg;
	private int posicion;
	/** si es Porcion consumidor y de peso variable sera "S" */
	private String pesoVariable;
	
	private String codigoError;
	private String descripcionError;
	private String tipoMensaje;
	private Long codArtOrig;
	private String MMC;
	private String origen;
	
	//Textil
	private String estructura;
	private String temporada;
	private Long numOrden;
	private String modeloProveedor;
	private String descrTalla;
	private String descrColor;
	private String denominacion;

	private String origenInventario;
	private String origenGISAE;

	private Double stockPlataforma;
	
	private Boolean variosBultos;
	
	public PdaArticulo() {
		super();
	}

	//Constructor para textil
	public PdaArticulo(Long codArt, String temporada, 
				String estructura,Long numOrden,
				String modeloProveedor, String descrTalla,String descrColor, String denominacion) {
			super();
			this.codArt = codArt;
			this.temporada = temporada;
			this.estructura = estructura;
			this.numOrden = numOrden;
			this.modeloProveedor = modeloProveedor;
			this.descrTalla = descrTalla;
			this.descrColor = descrColor;
			this.denominacion = denominacion;

			
			
	}
	
	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getDescArt() {
		return this.descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}

	public String getDescripcionCompleta() {
		return this.codArt + "-" + this.descArt;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigInteger getUnidades() {
		return this.unidades;
	}

	public void setUnidades(BigInteger unidades) {
		this.unidades = unidades;
	}

	public String getKgs() {
		return this.kgs;
	}

	public void setKgs(String kgs) {
		this.kgs = kgs;
	}
	
	public String getBandejas() {
		return this.bandejas;
	}

	public void setBandejas(String bandejas) {
		this.bandejas = bandejas;
	}

	public String getStock() {
		return this.stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getPrecio() {
		return this.precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getPrecioKg() {
		return this.precioKg;
	}

	public void setPrecioKg(String precioKg) {
		this.precioKg = precioKg;
	}

	public int getPosicion() {
		return this.posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	
	public String getCodigoError() {
		return this.codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getDescripcionError() {
		return this.descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	public String getTipoMensaje() {
		return this.tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public Long getCodArtOrig() {
		return this.codArtOrig;
	}

	public void setCodArtOrig(Long codArtOrig) {
		this.codArtOrig = codArtOrig;
	}

	public String getMMC() {
		return this.MMC;
	}

	public void setMMC(String mMC) {
		MMC = mMC;
	}

	public String getOrigen() {
		return this.origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getOrigenInventario() {
		return this.origenInventario;
	}

	public void setOrigenInventario(String origenInventario) {
		this.origenInventario = origenInventario;
	}
	
	public String getEstructura() {
		return this.estructura;
	}

	public void setEstructura(String estructura) {
		this.estructura = estructura;
	}
	
	public String getTemporada() {
		return this.temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}
	
	public Long getNumOrden() {
		return this.numOrden;
	}

	public void setNumOrden(Long numOrden) {
		this.numOrden = numOrden;
	}
	
	public String getModeloProveedor() {
		return this.modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}
	
	public String getDescrTalla() {
		return this.descrTalla;
	}

	public void setDescrTalla(String descrTalla) {
		this.descrTalla = descrTalla;
	}
	
	public String getDescrColor() {
		return this.descrColor;
	}

	public void setDescrColor(String descrColor) {
		this.descrColor = descrColor;
	}
	
	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getOrigenGISAE() {
		return origenGISAE;
	}

	public void setOrigenGISAE(String origenGISAE) {
		this.origenGISAE = origenGISAE;
	}

	public String getPesoVariable() {
		return pesoVariable;
	}

	public void setPesoVariable(String pesoVariable) {
		this.pesoVariable = pesoVariable;
	}

	public 	Double getStockPlataforma() {
		return stockPlataforma;
	}

	public void setStockPlataforma(Double stockPlataforma) {
		this.stockPlataforma = stockPlataforma;
	}

	public Long getCodArtPromo() {
		return codArtPromo;
	}

	public void setCodArtPromo(Long codArtPromo) {
		this.codArtPromo = codArtPromo;
	}

	public Boolean getVariosBultos() {
		return variosBultos;
	}

	public void setVariosBultos(Boolean variosBultos) {
		this.variosBultos = variosBultos;
	}	

}
