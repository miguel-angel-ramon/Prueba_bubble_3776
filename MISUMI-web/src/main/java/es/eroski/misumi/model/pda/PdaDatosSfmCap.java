package es.eroski.misumi.model.pda;

import java.io.Serializable;
import java.util.ArrayList;

public class PdaDatosSfmCap implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private Long codArtCaprabo;
	private String descArt;
	private String descArtConCodigo; //Utilizado para formatear la descripción de pantalla
	private String esAutoServicio;
	private String flgCapacidad;
	private String flgFacing;
	private String flgFacingCapacidad;
	private String lmin;
	private String lsf;
	private String sfm;
	private String coberturaSfm;
	private String ventaMedia;
	private String ventaAnticipada;
	private String vidaUtil;
	private String stock;
	private String diasStock;
	private String capacidad;
	private String facing;
	private String facingPrevio;
	private String flgEstrategica;
	private int posicion;
	private int total;
	private Long flgFecsfm;
	private String icono;
	private String errorIcono;
	private String sfmOrig;
	private String coberturaSfmOrig;
	private String capacidadOrig;
	private String facingOrig;
	private String esError;
	
	//Campos para textil
	private boolean textil = false;;
	private int posicionTextil;
	private int totalTextil;
	private String codN1;
	private String codN2;
	private String codN3;
	private String codN4;
	private String codN5;
	private boolean textilPedible = true;
	private boolean lote = false;
	private String temporada;
	private String anioColeccion;
	private String talla;
	private String color;
	private String modeloProveedor;
	private String tempColNumOrden;
	private String orden;
	private ArrayList<PdaDatosSfmCap> listaHijas;
	private String facing_textil_0;
	private String facing_textil_1;
	private String facing_textil_2;
	private boolean activa;
	private boolean inactivarLote;
	
	//Peticion 52126 A�adir Campos de TipoGama y CC
	private String tipoGama;
	private Long cc;
	
	//Campos para bloqueos
	private String flgSfmFijo;
	
	//Campos Solo Imagen
	private String flgSoloImagen;
	private String multiplicadorFacing;
	private String imagenComercialMin;
	
	//Campo para comprobacion de FFPP
	private boolean esFFPP;
	
	
	public PdaDatosSfmCap() {
	    super();
	}

	public PdaDatosSfmCap(Long codArt, String descArt, String esAutoServicio, String flgCapacidad, String flgFacing, String lmin, String lsf, 
			String sfm, String coberturaSfm, String ventaMedia, String ventaAnticipada, String vidaUtil, String stock, 
			String diasStock, String capacidad, String facing, String facingPrevio, int posicion, int total, Long flgFecsfm, String icono,
			String errorIcono, String sfmOrig, String coberturaSfmOrig, String capacidadOrig, String facingOrig, String esError, 
			String codN1, String codN2, String codN3, String codN4, String codN5, boolean textilPedible, boolean lote, 
			String temporada, String anioColeccion, String talla, String color, String modeloProveedor, String tempColNumOrden, String orden) {
	    super();
	    this.codArt=codArt;
	    this.descArt=descArt;
	    this.esAutoServicio=esAutoServicio;
	    this.flgCapacidad=flgCapacidad;
	    this.flgFacing=flgFacing;
	    this.lmin=lmin;
	    this.lsf=lsf;
	    this.sfm=sfm;
	    this.coberturaSfm=coberturaSfm;
	    this.ventaMedia=ventaMedia;
	    this.ventaMedia=ventaMedia;
	    this.vidaUtil=vidaUtil;
	    this.stock=stock;
	    this.diasStock=diasStock;
	    this.capacidad=capacidad;
	    this.facing=facing;
	    this.facingPrevio=facingPrevio;
	    this.posicion=posicion;
	    this.total=total;
	    this.flgFecsfm=flgFecsfm;
	    this.icono=icono;
	    this.errorIcono=errorIcono;
	    this.sfmOrig=sfmOrig;
	    this.coberturaSfmOrig=coberturaSfmOrig;
	    this.capacidadOrig=capacidadOrig;
	    this.facingOrig=facingOrig;
	    this.esError=esError;
	    this.codN1=codN1;
	    this.codN2=codN2;
	    this.codN3=codN3;
	    this.codN4=codN4;
	    this.codN5=codN5;
	    this.textilPedible=textilPedible;
	    this.lote=lote;
	    this.temporada=temporada;
	    this.anioColeccion=anioColeccion;
	    this.talla=talla;
	    this.color=color;
	    this.modeloProveedor=modeloProveedor;
	    this.tempColNumOrden=tempColNumOrden;
	    this.orden=orden;
	    
	}
	
	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	
	public Long getCodArtCaprabo() {
		return codArtCaprabo;
	}

	public void setCodArtCaprabo(Long codArtCaprabo) {
		this.codArtCaprabo = codArtCaprabo;
	}

	public String getDescArt() {
		return this.descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}
	
	public String getDescArtConCodigo() {
		return this.codArtCaprabo + "-" + this.descArt;
	}

	public void setDescArtConCodigo(String descArtConCodigo) {
		this.descArtConCodigo = descArtConCodigo;
	}

	public String getEsAutoServicio() {
		return this.esAutoServicio;
	}

	public void setEsAutoServicio(String esAutoServicio) {
		this.esAutoServicio = esAutoServicio;
	}
	
	public String getFlgCapacidad() {
		return this.flgCapacidad;
	}

	public void setFlgCapacidad(String flgCapacidad) {
		this.flgCapacidad = flgCapacidad;
	}
	
	public String getFlgFacing() {
		return this.flgFacing;
	}

	public void setFlgFacing(String flgFacing) {
		this.flgFacing = flgFacing;
	}
	
	public String getFlgFacingCapacidad() {
		return this.flgFacingCapacidad;
	}

	public void setFlgFacingCapacidad(String flgFacingCapacidad) {
		this.flgFacingCapacidad = flgFacingCapacidad;
	}

	public String getLmin() {
		return this.lmin;
	}

	public void setLmin(String lmin) {
		this.lmin = lmin;
	}
	
	public String getLsf() {
		return this.lsf;
	}

	public void setLsf(String lsf) {
		this.lsf = lsf;
	}
	
	public String getSfm() {
		return this.sfm;
	}

	public void setSfm(String sfm) {
		this.sfm = sfm;
	}
	
	public String getCoberturaSfm() {
		return this.coberturaSfm;
	}

	public void setCoberturaSfm(String coberturaSfm) {
		this.coberturaSfm = coberturaSfm;
	}
	
	public String getVentaMedia() {
		return this.ventaMedia;
	}

	public void setVentaMedia(String ventaMedia) {
		this.ventaMedia = ventaMedia;
	}
	
	public String getVentaAnticipada() {
		return this.ventaAnticipada;
	}

	public void setVentaAnticipada(String ventaAnticipada) {
		this.ventaAnticipada = ventaAnticipada;
	}
	
	public String getVidaUtil() {
		return this.vidaUtil;
	}

	public void setVidaUtil(String vidaUtil) {
		this.vidaUtil = vidaUtil;
	}
	
	public String getStock() {
		return this.stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}
	
	public String getDiasStock() {
		return this.diasStock;
	}

	public void setDiasStock(String diasStock) {
		this.diasStock = diasStock;
	}
	
	public String getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}
	
	public String getFacing() {
		return this.facing;
	}

	public void setFacing(String facing) {
		this.facing = facing;
	}
	
	public String getFacingPrevio() {
		return this.facingPrevio;
	}

	public void setFacingPrevio(String facingPrevio) {
		this.facingPrevio = facingPrevio;
	}

	public String getFlgEstrategica() {
		return flgEstrategica;
	}

	public void setFlgEstrategica(String flgEstrategica) {
		this.flgEstrategica = flgEstrategica;
	}

	public int getPosicion() {
		return this.posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	
	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public Long getFlgFecsfm() {
		return this.flgFecsfm;
	}

	public void setFlgFecsfm(Long flgFecsfm) {
		this.flgFecsfm = flgFecsfm;
	}
	
	public String getIcono() {
		return this.icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}
	
	public String getErrorIcono() {
		return this.errorIcono;
	}

	public void setErrorIcono(String errorIcono) {
		this.errorIcono = errorIcono;
	}
	
	public String getSfmOrig() {
		return this.sfmOrig;
	}

	public void setSfmOrig(String sfmOrig) {
		this.sfmOrig = sfmOrig;
	}
	
	public String getCoberturaSfmOrig() {
		return this.coberturaSfmOrig;
	}

	public void setCoberturaSfmOrig(String coberturaSfmOrig) {
		this.coberturaSfmOrig = coberturaSfmOrig;
	}
	
	public String getCapacidadOrig() {
		return this.capacidadOrig;
	}

	public void setCapacidadOrig(String capacidadOrig) {
		this.capacidadOrig = capacidadOrig;
	}
	
	public String getFacingOrig() {
		return this.facingOrig;
	}

	public void setFacingOrig(String facingOrig) {
		this.facingOrig = facingOrig;
	}

	public String getEsError() {
		return this.esError;
	}

	public void setEsError(String esError) {
		this.esError = esError;
	}

	public int getPosicionTextil() {
		return posicionTextil;
	}

	public void setPosicionTextil(int posicionTextil) {
		this.posicionTextil = posicionTextil;
	}

	public int getTotalTextil() {
		return totalTextil;
	}

	public boolean isTextil() {
		return textil;
	}

	public void setTextil(boolean textil) {
		this.textil = textil;
	}

	public void setTotalTextil(int totalTextil) {
		this.totalTextil = totalTextil;
	}

	public String getCodN1() {
		return codN1;
	}

	public void setCodN1(String codN1) {
		this.codN1 = codN1;
	}

	public String getCodN2() {
		return codN2;
	}

	public void setCodN2(String codN2) {
		this.codN2 = codN2;
	}

	public String getCodN3() {
		return codN3;
	}

	public void setCodN3(String codN3) {
		this.codN3 = codN3;
	}

	public String getCodN4() {
		return codN4;
	}

	public void setCodN4(String codN4) {
		this.codN4 = codN4;
	}

	public String getCodN5() {
		return codN5;
	}

	public void setCodN5(String codN5) {
		this.codN5 = codN5;
	}

	public boolean isTextilPedible() {
		return textilPedible;
	}

	public void setTextilPedible(boolean textilPedible) {
		this.textilPedible = textilPedible;
	}

	public boolean isLote() {
		return lote;
	}

	public void setLote(boolean lote) {
		this.lote = lote;
	}

	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public String getAnioColeccion() {
		return anioColeccion;
	}

	public void setAnioColeccion(String anioColeccion) {
		this.anioColeccion = anioColeccion;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}

	public String getTempColNumOrden() {
		return tempColNumOrden;
	}

	public void setTempColNumOrden(String tempColNumOrden) {
		this.tempColNumOrden = tempColNumOrden;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public ArrayList<PdaDatosSfmCap> getListaHijas() {
		return listaHijas;
	}

	public void setListaHijas(ArrayList<PdaDatosSfmCap> listaHijas) {
		this.listaHijas = listaHijas;
	}

	public void addListaHijas(PdaDatosSfmCap hija) {
		if (this.listaHijas == null){
			this.listaHijas = new ArrayList<PdaDatosSfmCap>();
		}
		this.listaHijas.add(hija);
	}

	public String getFacing_textil_0() {
		return facing_textil_0;
	}

	public void setFacing_textil_0(String facing_textil_0) {
		this.facing_textil_0 = facing_textil_0;
	}

	public String getFacing_textil_1() {
		return facing_textil_1;
	}

	public void setFacing_textil_1(String facing_textil_1) {
		this.facing_textil_1 = facing_textil_1;
	}

	public String getFacing_textil_2() {
		return facing_textil_2;
	}

	public void setFacing_textil_2(String facing_textil_2) {
		this.facing_textil_2 = facing_textil_2;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public boolean isInactivarLote() {
		return inactivarLote;
	}

	public void setInactivarLote(boolean inactivarLote) {
		this.inactivarLote = inactivarLote;
	}

	public String getTipoGama() {
		return tipoGama;
	}

	public void setTipoGama(String tipoGama) {
		this.tipoGama = tipoGama;
	}

	public Long getCc() {
		return cc;
	}

	public void setCc(Long cc) {
		this.cc = cc;
	}
	
	public String getFlgSfmFijo() {
		return flgSfmFijo;
	}

	public void setFlgSfmFijo(String flgSfmFijo) {
		this.flgSfmFijo = flgSfmFijo;
	}
	
	public String getFlgSoloImagen() {
		return flgSoloImagen;
	}

	public void setFlgSoloImagen(String flgSoloImagen) {
		this.flgSoloImagen = flgSoloImagen;
	}
	
	public String getMultiplicadorFacing() {
		return this.multiplicadorFacing;
	}

	public void setMultiplicadorFacing(String multiplicadorFacing) {
		this.multiplicadorFacing = multiplicadorFacing;
	}
	
	public String getImagenComercialMin() {
		return this.imagenComercialMin;
	}

	public void setImagenComercialMin(String imagenComercialMin) {
		this.imagenComercialMin = imagenComercialMin;
	}

	public boolean isEsFFPP() {
		return this.esFFPP;
	}

	public void setEsFFPP(boolean esFFPP) {
		this.esFFPP = esFFPP;
	}
}