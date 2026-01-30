package es.eroski.misumi.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;

public class VArtCentroAlta implements Serializable{

	private static final long serialVersionUID = 1L;

	//private Long codCentro;
	private Centro centro;
	private Long codArticulo;
	private String descriptionArt;
	private String nivel;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String descripcion;
	private String pedir;
	private String marcaMaestroCentro;
	private String catalogo;
	private String tpGama;
	private Double stock;
	private String tipoAprov;
	private Double uniCajaServ;
	private Long capacidadMax;
	private Double stockMinComer;
	private Long capacidadCab;
	private Double stockCab;
	private Double stockFinMinS;
	private Long codArtRela;
	private String descripRela;
	private String multipli;
	private Long tipoOferta;
	private String numeroOferta;
	private Long cc;
	private String area;
	private String seccion;
	private String categoria;
	private String subcategoria;
	private Double ufp;
	private String gamaDiscontinua;
	private String marca;
	private Double lmin;
	private Double lsf;
	private Double coberturaSfm;
	private Double ventaMedia;
	private Double ventaAnticipada;
	private String diasStock;
	private String vidaUtil;
	private Long capaciMa1;
	private Double facingMa1;
	private Long capaciMa2;
	private Double facingMa2;
	private Double imc; //Imagen Comercial 
	//Tipo Rotacion. Pet Misumi-137
	private String tipoRotacion;
	//Campos para textil

	private String anioColeccion;
	private String lote;
	private String modeloProveedor;
	private String modelo;
	private String pedible;
	
	private String nivelLote;
	private String codColor;
	private String color;
	private String talla;
	private String temporada;
	private String numOrden;
	private String orden; //(TEMPORADA (2 dígitos) + blanco + AÑO + blanco + COMERCIAL (todo menos área) + blanco + NUMERO ORDEN (3 dígitos). 
	
	private Long codArticuloLote;
	
	private String id;  //id para enlazar con las referencias de un lote.
	
	private String loteSN;
	private String campoOrdenacionExcel;
	
	//Campo para control de referencia
	private String tipoArtFfppUnitaria;

	private String accion;
	
	private Long alto;
	private Long ancho;
	
	private String facingCero;
	private String tipoImplantacion;
	
	public VArtCentroAlta() {
	    super();
	}

	public VArtCentroAlta(Centro centro, Long codArticulo,
			String descriptionArt,String nivel, Long grupo1, Long grupo2, Long grupo3,
			Long grupo4, Long grupo5, String descripcion, String pedir,
			String marcaMaestroCentro, String catalogo, String facingCero, String tpGama, Double stock,
			String tipoAprov, Double uniCajaServ, Long capacidadMax,
			Double stockMinComer, Long capacidadCab, Double stockCab,
			Double stockFinMinS, Long codArtRela,
			String descripRela, String multipli, Long tipoOferta,
			String numeroOferta, Long cc,String area,
			String seccion, String categoria, String subcategoria,
			Double ufp, String gamaDiscontinua, String marca,
			Double lmin, Double lsf, Double coberturaSfm,
			Double ventaMedia, Double ventaAnticipada, String diasStock, String vidaUtil,
			Long capaciMa1, Double facingMa1, Long capaciMa2, Double facingMa2,
			Double imc, String pedible, String accion, String tipoImplantacion) {
		
		super();
		this.centro = centro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.nivel=nivel;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripcion = descripcion;
		this.pedir = pedir;
		this.marcaMaestroCentro = marcaMaestroCentro;
		this.catalogo = catalogo;
		this.facingCero = facingCero;
		this.tpGama = tpGama;
		this.stock = stock;
		this.tipoAprov = tipoAprov;
		this.uniCajaServ = uniCajaServ;
		this.capacidadMax = capacidadMax;
		this.stockMinComer = stockMinComer;
		this.capacidadCab = capacidadCab;
		this.stockCab = stockCab;
		this.stockFinMinS = stockFinMinS;
		this.codArtRela = codArtRela;
		this.descripRela = descripRela;
		this.multipli = multipli;
		this.tipoOferta = tipoOferta;
		this.numeroOferta = numeroOferta;
		this.cc = cc;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.ufp = ufp;
		this.gamaDiscontinua = gamaDiscontinua;
		this.marca = marca;
		this.lmin = lmin;
		this.lsf = lsf;
		this.coberturaSfm = coberturaSfm;
		this.ventaMedia = ventaMedia;
		this.ventaAnticipada = ventaAnticipada;
		this.diasStock = diasStock;
		this.vidaUtil = vidaUtil;
		this.capaciMa1 = capaciMa1;
		this.facingMa1 = facingMa1;
		this.capaciMa2 = capaciMa2;
		this.facingMa2 = facingMa2;
		this.imc = imc;
		this.pedible = pedible;
		this.accion = accion;
		this.tipoImplantacion = tipoImplantacion;
	}

	public VArtCentroAlta(Centro centro, Long codArticulo,
			String descriptionArt,String nivel, Long grupo1, Long grupo2, Long grupo3,
			Long grupo4, Long grupo5, String descripcion, String pedir,
			String marcaMaestroCentro, String catalogo, String tpGama, Double stock,
			String tipoAprov, Double uniCajaServ, Long capacidadMax,
			Double stockMinComer, Long capacidadCab, Double stockCab,
			Double stockFinMinS, Long codArtRela,
			String descripRela, String multipli, Long tipoOferta,
			String numeroOferta, Long cc,String area,
			String seccion, String categoria, String subcategoria,
			Double ufp, String gamaDiscontinua, String marca,
			Double lmin, Double lsf, Double coberturaSfm,
			Double ventaMedia, Double ventaAnticipada, String diasStock, String vidaUtil,
			Long capaciMa1, Double facingMa1, Long capaciMa2, Double facingMa2, String pedible, 
			String nivelLote, String codColor,  String color, String talla, String temporada,
			String numOrden, Double imc, String modeloProveedor, String anioColeccion, String accion,
			String tipoImplantacion) {
		super();
		this.centro = centro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.nivel=nivel;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripcion = descripcion;
		this.pedir = pedir;
		this.marcaMaestroCentro = marcaMaestroCentro;
		this.catalogo = catalogo;
		this.tpGama = tpGama;
		this.stock = stock;
		this.tipoAprov = tipoAprov;
		this.uniCajaServ = uniCajaServ;
		this.capacidadMax = capacidadMax;
		this.stockMinComer = stockMinComer;
		this.capacidadCab = capacidadCab;
		this.stockCab = stockCab;
		this.stockFinMinS = stockFinMinS;
		this.codArtRela = codArtRela;
		this.descripRela = descripRela;
		this.multipli = multipli;
		this.tipoOferta = tipoOferta;
		this.numeroOferta = numeroOferta;
		this.cc = cc;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.ufp = ufp;
		this.gamaDiscontinua = gamaDiscontinua;
		this.marca = marca;
		this.lmin = lmin;
		this.lsf = lsf;
		this.coberturaSfm = coberturaSfm;
		this.ventaMedia = ventaMedia;
		this.ventaAnticipada = ventaAnticipada;
		this.diasStock = diasStock;
		this.vidaUtil = vidaUtil;
		this.capaciMa1 = capaciMa1;
		this.facingMa1 = facingMa1;
		this.capaciMa2 = capaciMa2;
		this.facingMa2 = facingMa2;
		this.pedible = pedible;
		this.nivelLote = nivelLote;
		this.codColor = codColor;
		this.color = color;
		this.talla = talla;
		this.temporada = temporada;
		this.numOrden = numOrden;
		this.imc = imc;
		this.modeloProveedor = modeloProveedor;
		this.anioColeccion = anioColeccion;
		this.accion = accion;
		this.tipoImplantacion = tipoImplantacion;
	}
	
	public VArtCentroAlta(Long codArticuloLote, Centro centro, Long codArticulo,
			String descriptionArt,String nivel, Long grupo1, Long grupo2, Long grupo3,
			Long grupo4, Long grupo5, String descripcion, String pedir,
			String marcaMaestroCentro, String catalogo, String tpGama, Double stock,
			String tipoAprov, Double uniCajaServ, Long capacidadMax,
			Double stockMinComer, Long capacidadCab, Double stockCab,
			Double stockFinMinS, Long codArtRela,
			String descripRela, String multipli, Long tipoOferta,
			String numeroOferta, Long cc,String area,
			String seccion, String categoria, String subcategoria,
			Double ufp, String gamaDiscontinua, String marca,
			Double lmin, Double lsf, Double coberturaSfm,
			Double ventaMedia, Double ventaAnticipada, String diasStock, String vidaUtil,
			Long capaciMa1, Double facingMa1, Long capaciMa2, Double facingMa2, String pedible, 
			String nivelLote, String codColor,  String color, String talla, String temporada,
			String numOrden, String modeloProveedor, Double imc, String anioColeccion,
			String tipoImplantacion
			) {
		super();
		this.codArticuloLote = codArticuloLote;
		this.centro = centro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.nivel=nivel;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripcion = descripcion;
		this.pedir = pedir;
		this.marcaMaestroCentro = marcaMaestroCentro;
		this.catalogo = catalogo;
		this.tpGama = tpGama;
		this.stock = stock;
		this.tipoAprov = tipoAprov;
		this.uniCajaServ = uniCajaServ;
		this.capacidadMax = capacidadMax;
		this.stockMinComer = stockMinComer;
		this.capacidadCab = capacidadCab;
		this.stockCab = stockCab;
		this.stockFinMinS = stockFinMinS;
		this.codArtRela = codArtRela;
		this.descripRela = descripRela;
		this.multipli = multipli;
		this.tipoOferta = tipoOferta;
		this.numeroOferta = numeroOferta;
		this.cc = cc;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.ufp = ufp;
		this.gamaDiscontinua = gamaDiscontinua;
		this.marca = marca;
		this.lmin = lmin;
		this.lsf = lsf;
		this.coberturaSfm = coberturaSfm;
		this.ventaMedia = ventaMedia;
		this.ventaAnticipada = ventaAnticipada;
		this.diasStock = diasStock;
		this.vidaUtil = vidaUtil;
		this.capaciMa1 = capaciMa1;
		this.facingMa1 = facingMa1;
		this.capaciMa2 = capaciMa2;
		this.facingMa2 = facingMa2;
		this.pedible = pedible;
		this.nivelLote = nivelLote;
		this.codColor = codColor;
		this.color = color;
		this.talla = talla;
		this.temporada = temporada;
		this.numOrden = numOrden;
		this.modeloProveedor = modeloProveedor;
		this.imc = imc;
		this.anioColeccion = anioColeccion;
		this.tipoImplantacion = tipoImplantacion;
	}
	
	public String getTipoRotacion() {
		return tipoRotacion;
	}

	public void setTipoRotacion(String tipoRotacion) {
		this.tipoRotacion = tipoRotacion;
	}

	public void setNumOrden(String numOrden) {
		this.numOrden = numOrden;
	}

	public Centro getCentro() {
		return this.centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getDescriptionArt() {
		return this.descriptionArt;
	}

	public void setDescriptionArt(String descriptionArt) {
		this.descriptionArt = descriptionArt;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPedir() {
		return this.pedir;
	}

	public void setPedir(String pedir) {
		this.pedir = pedir;
	}

	public String getMarcaMaestroCentro() {
		return this.marcaMaestroCentro;
	}

	public void setMarcaMaestroCentro(String marcaMaestroCentro) {
		this.marcaMaestroCentro = marcaMaestroCentro;
	}

	public String getTpGama() {
		return this.tpGama;
	}

	public void setTpGama(String tpGama) {
		this.tpGama = tpGama;
	}

	public Double getStock() {
		return this.stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public String getTipoAprov() {
		return this.tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public Double getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(Double uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}

	public Long getCapacidadMax() {
		return this.capacidadMax;
	}

	public void setCapacidadMax(Long capacidadMax) {
		this.capacidadMax = capacidadMax;
	}

	public Double getStockMinComer() {
		return this.stockMinComer;
	}

	public void setStockMinComer(Double stockMinComer) {
		this.stockMinComer = stockMinComer;
	}

	public Long getCapacidadCab() {
		return this.capacidadCab;
	}

	public void setCapacidadCab(Long capacidadCab) {
		this.capacidadCab = capacidadCab;
	}

	public Double getStockCab() {
		return this.stockCab;
	}

	public void setStockCab(Double stockCab) {
		this.stockCab = stockCab;
	}

	public Double getStockFinMinS() {
		return this.stockFinMinS;
	}

	public void setStockFinMinS(Double stockFinMinS) {
		this.stockFinMinS = stockFinMinS;
	}

	public Long getCodArtRela() {
		return this.codArtRela;
	}

	public void setCodArtRela(Long codArtRela) {
		this.codArtRela = codArtRela;
	}

	public String getDescripRela() {
		return this.descripRela;
	}

	public void setDescripRela(String descripRela) {
		this.descripRela = descripRela;
	}

	public String getMultipli() {
		return this.multipli;
	}

	public void setMultipli(String multipli) {
		this.multipli = multipli;
	}

	public Long getTipoOferta() {
		return this.tipoOferta;
	}

	public void setTipoOferta(Long tipoOferta) {
		this.tipoOferta = tipoOferta;
	}

	public String getNumeroOferta() {
		return this.numeroOferta;
	}

	public void setNumeroOferta(String numeroOferta) {
		this.numeroOferta = numeroOferta;
	}

	public Long getCc() {
		return this.cc;
	}

	public void setCc(Long cc) {
		this.cc = cc;
	}

	public String getNivel() {
		return this.nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSeccion() {
		return this.seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSubcategoria() {
		return this.subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}
	
	public Double getUfp() {
		return this.ufp;
	}

	public void setUfp(Double ufp) {
		this.ufp = ufp;
	}
	
	public String getGamaDiscontinua() {
		return this.gamaDiscontinua;
	}

	public void setGamaDiscontinua(String gamaDiscontinua) {
		this.gamaDiscontinua = gamaDiscontinua;
	}
	
	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public Double getLmin() {
		return this.lmin;
	}

	public void setLmin(Double lmin) {
		this.lmin = lmin;
	}
	
	public Double getLsf() {
		return this.lsf;
	}

	public void setLsf(Double lsf) {
		this.lsf = lsf;
	}
	
	public Double getCoberturaSfm() {
		return this.coberturaSfm;
	}

	public void setCoberturaSfm(Double coberturaSfm) {
		this.coberturaSfm = coberturaSfm;
	}
	
	public Double getVentaMedia() {
		return this.ventaMedia;
	}

	public void setVentaMedia(Double ventaMedia) {
		this.ventaMedia = ventaMedia;
	}
	
	public Double getVentaAnticipada() {
		return this.ventaAnticipada;
	}

	public void setVentaAnticipada(Double ventaAnticipada) {
		this.ventaAnticipada = ventaAnticipada;
	}
	
	public String getDiasStock() {
		return this.diasStock;
	}

	public void setDiasStock(String diasStock) {
		this.diasStock = diasStock;
	}
	
	public String getVidaUtil() {
		return this.vidaUtil;
	}

	public void setVidaUtil(String vidaUtil) {
		this.vidaUtil = vidaUtil;
	}

	public Long getCapaciMa1() {
		return this.capaciMa1;
	}

	public void setCapaciMa1(Long capaciMa1) {
		this.capaciMa1 = capaciMa1;
	}

	public Double getFacingMa1() {
		return this.facingMa1;
	}

	public void setFacingMa1(Double facingMa1) {
		this.facingMa1 = facingMa1;
	}

	public Long getCapaciMa2() {
		return this.capaciMa2;
	}

	public void setCapaciMa2(Long capaciMa2) {
		this.capaciMa2 = capaciMa2;
	}

	public Double getFacingMa2() {
		return this.facingMa2;
	}

	public void setFacingMa2(Double facingMa2) {
		this.facingMa2 = facingMa2;
	}

	public Double getImc() {
		return imc;
	}

	public void setImc(Double imc) {
		this.imc = imc;
	}

	public String getTemporada() {
		return this.temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public String getAnioColeccion() {
		return this.anioColeccion;
	}

	public void setAnioColeccion(String anioColeccion) {
		this.anioColeccion = anioColeccion;
	}

	public String getTalla() {
		return this.talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLote() {
		return this.lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getModeloProveedor() {
		return this.modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}
	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public String getPedible() {
		return pedible;
	}

	public void setPedible(String pedible) {
		this.pedible = pedible;
	}
	
	public String getNivelLote() {
		return nivelLote;
	}

	public void setNivelLote(String nivelLote) {
		this.nivelLote = nivelLote;
	}

	public String getCodColor() {
		return codColor;
	}

	public void setCodColor(String codColor) {
		this.codColor = codColor;
	}
	
	public String getNumOrden() {
		return numOrden;
	}

	public void setNumOrdenr(String numOrden) {
		this.numOrden = numOrden;
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	public Long getCodArticuloLote() {
		return this.codArticuloLote;
	}

	public void setCodArticuloLote(Long codArticuloLote) {
		this.codArticuloLote = codArticuloLote;
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getLoteSN() {
		return this.loteSN;
	}

	public void setLoteSN(String loteSN) {
		this.loteSN = loteSN;
	}
	
	public String getCampoOrdenacionExcel() {
		return this.campoOrdenacionExcel;
	}

	public void setCampoOrdenacionExcel(String campoOrdenacionExcel) {
		this.campoOrdenacionExcel = campoOrdenacionExcel;
	}
	
	public String getTipoArtFfppUnitaria() {
		return this.tipoArtFfppUnitaria;
	}

	public void setTipoArtFfppUnitaria(String tipoArtFfppUnitaria) {
		this.tipoArtFfppUnitaria = tipoArtFfppUnitaria;
	}
	
	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Long getAlto() {
		return alto;
	}

	public void setAlto(Long alto) {
		this.alto = alto;
	}

	public Long getAncho() {
		return ancho;
	}

	public void setAncho(Long ancho) {
		this.ancho = ancho;
	}
	
	public String getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}
	
	public String getTipoImplantacion() {
		return tipoImplantacion;
	}

	public void setTipoImplantacion(String tipoImplantacion) {
		this.tipoImplantacion = tipoImplantacion;
	}

	public String getFacingCero() {
		return facingCero;
	}

	public void setFacingCero(String facingCero) {
		this.facingCero = facingCero;
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof VArtCentroAlta)){
			return false;
		}
		VArtCentroAlta vArtCentroAlta = (VArtCentroAlta) obj;
		return new EqualsBuilder().append(this.codArticulo, vArtCentroAlta.codArticulo).isEquals();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VArtCentroAlta [centro=");
		builder.append(centro);
		builder.append(", codArticulo=");
		builder.append(codArticulo);
		builder.append(", descriptionArt=");
		builder.append(descriptionArt);
		builder.append(", nivel=");
		builder.append(nivel);
		builder.append(", grupo1=");
		builder.append(grupo1);
		builder.append(", grupo2=");
		builder.append(grupo2);
		builder.append(", grupo3=");
		builder.append(grupo3);
		builder.append(", grupo4=");
		builder.append(grupo4);
		builder.append(", grupo5=");
		builder.append(grupo5);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append(", pedir=");
		builder.append(pedir);
		builder.append(", marcaMaestroCentro=");
		builder.append(marcaMaestroCentro);
		builder.append(", catalogo=");
		builder.append(catalogo);
		builder.append(", tpGama=");
		builder.append(tpGama);
		builder.append(", stock=");
		builder.append(stock);
		builder.append(", tipoAprov=");
		builder.append(tipoAprov);
		builder.append(", uniCajaServ=");
		builder.append(uniCajaServ);
		builder.append(", capacidadMax=");
		builder.append(capacidadMax);
		builder.append(", stockMinComer=");
		builder.append(stockMinComer);
		builder.append(", capacidadCab=");
		builder.append(capacidadCab);
		builder.append(", stockCab=");
		builder.append(stockCab);
		builder.append(", stockFinMinS=");
		builder.append(stockFinMinS);
		builder.append(", codArtRela=");
		builder.append(codArtRela);
		builder.append(", descripRela=");
		builder.append(descripRela);
		builder.append(", multipli=");
		builder.append(multipli);
		builder.append(", tipoOferta=");
		builder.append(tipoOferta);
		builder.append(", numeroOferta=");
		builder.append(numeroOferta);
		builder.append(", cc=");
		builder.append(cc);
		builder.append(", area=");
		builder.append(area);
		builder.append(", seccion=");
		builder.append(seccion);
		builder.append(", categoria=");
		builder.append(categoria);
		builder.append(", subcategoria=");
		builder.append(subcategoria);
		builder.append(", ufp=");
		builder.append(ufp);
		builder.append(", gamaDiscontinua=");
		builder.append(gamaDiscontinua);
		builder.append(", marca=");
		builder.append(marca);
		builder.append(", lmin=");
		builder.append(lmin);
		builder.append(", lsf=");
		builder.append(lsf);
		builder.append(", coberturaSfm=");
		builder.append(coberturaSfm);
		builder.append(", ventaMedia=");
		builder.append(ventaMedia);
		builder.append(", ventaAnticipada=");
		builder.append(ventaAnticipada);
		builder.append(", diasStock=");
		builder.append(diasStock);
		builder.append(", vidaUtil=");
		builder.append(vidaUtil);
		builder.append(", capaciMa1=");
		builder.append(capaciMa1);
		builder.append(", facingMa1=");
		builder.append(facingMa1);
		builder.append(", capaciMa2=");
		builder.append(capaciMa2);
		builder.append(", facingMa2=");
		builder.append(facingMa2);
		builder.append(", imc=");
		builder.append(imc);
		builder.append(", tipoRotacion=");
		builder.append(tipoRotacion);
		builder.append(", anioColeccion=");
		builder.append(anioColeccion);
		builder.append(", lote=");
		builder.append(lote);
		builder.append(", modeloProveedor=");
		builder.append(modeloProveedor);
		builder.append(", modelo=");
		builder.append(modelo);
		builder.append(", pedible=");
		builder.append(pedible);
		builder.append(", nivelLote=");
		builder.append(nivelLote);
		builder.append(", codColor=");
		builder.append(codColor);
		builder.append(", color=");
		builder.append(color);
		builder.append(", talla=");
		builder.append(talla);
		builder.append(", temporada=");
		builder.append(temporada);
		builder.append(", numOrden=");
		builder.append(numOrden);
		builder.append(", orden=");
		builder.append(orden);
		builder.append(", codArticuloLote=");
		builder.append(codArticuloLote);
		builder.append(", id=");
		builder.append(id);
		builder.append(", loteSN=");
		builder.append(loteSN);
		builder.append(", campoOrdenacionExcel=");
		builder.append(campoOrdenacionExcel);
		builder.append(", tipoArtFfppUnitaria=");
		builder.append(tipoArtFfppUnitaria);
		builder.append(", accion=");
		builder.append(accion);
		builder.append(", facingCero=");
		builder.append(facingCero);
		builder.append(", tipoImplantacion=");
		builder.append(tipoImplantacion);
		builder.append("]");
		
		return builder.toString();
	}

}