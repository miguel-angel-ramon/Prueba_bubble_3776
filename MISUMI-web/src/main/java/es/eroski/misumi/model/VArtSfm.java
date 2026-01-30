package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class VArtSfm implements Serializable, Comparable<VArtSfm>{

	private static final long serialVersionUID = 1L;

	private Long codLoc;
	private Long codArticulo;
	private Long codArtCaprabo;
	private Long nivel;
	private String denomInforme;
	private String codN1;
	private String descCodN1;
	private String codN2;
	private String descCodN2;
	private String codN3;
	private String descCodN3;
	private String codN4;
	private String descCodN4;
	private String codN5;
	private String descCodN5;
	private String marca;
	private Double lmin;
	private Double lsf;
	private Double sfm;
	private Double coberturaSfm;
	private Double ventaMedia;
	private Double ventaAnticipada;
	private Double uc;
	private Double stock;
	private Double diasStock;
	private Double capacidad;
	private Date fechaSfm;
	private Long flgFecsfm;
	private Long codError;
	private String descError;
	private String clave;
	private int indice;
	private List<CamposModificadosSFM> listadoModificados;
	private Double sfmOrig;
	private Double coberturaSfmOrig;
	private Double capacidadOrig;
	private Long vidaUtil;
	
	
	private String pedir;
	private String loteSN;
	
	private Long facingCentro;
	private Long facingPrevio;
	private String flgEstrategica;
	private Long facingCentroOrig;
	private String tipoGama;
	private String tipoAprov;
	private Long cc;
	private String tipoExpositor;
	
	//Campos para textil
	private String temporada;
	private String anioColeccion;
	private String talla;
	private String color;
	private String lote;
	private String modeloProveedor;
	private String tempColNumOrden;
	private String pedible;
	
	private String porcionConsumidor;

	
	private String fechaSfmDDMMYYYY;
	
	private Long codArticuloLote;
	private Long nivelLote;
	
	private String flgMuchoPoco;
	private String flgFoto;
	private String flgNueva;
	private String flgSfmFijo;
	private Long ccEstr;
	
	private String flgNsr;
	
	private String flgUfp;
	
	private String flgSoloImagen;
	private Double multiplicadorFacing;
	private Double imagenComercialMin;

	private String flgActualizarFacing; //Sirve para saber si hay que actualizar el valor facing de las 
										//referencias hijas con el facing de la madre a la hora de desplegar un lote en pntalla
	
	private String flgPintarTengoPoco; //Sirve para saber si hay que pintar de rojo tengo poco (sólo se pinta de rojo sin link si no tiene motivos)
	private String flgPintarLinkTengoPoco; //Sirve para saber si hay que pintar el link de tengo poco, solo se pintar si hay motivos que mostrar

	private Double stockBajo;
	
	private String tipoEstructura;


	
	
	

	public VArtSfm() {
	    super();
	}

	public VArtSfm(Long codLoc, Long codArticulo, Long nivel, String denomInforme,
			String codN1, String descCodN1, String codN2, String descCodN2,
			String codN3, String descCodN3, String codN4, String descCodN4,
			String codN5, String descCodN5, String marca, Double lmin,
			Double lsf, Double sfm, Double coberturaSfm, Double ventaMedia,
			Double ventaAnticipada, Double uc, Double stock, Double diasStock,
			Double capacidad, Date fechaSfm, Long flgFecsfm, Long codError, String descError,
			String clave, int indice, List<CamposModificadosSFM> listadoModificados,
			Double sfmOrig, Double coberturaSfmOrig, Double capacidadOrig, Long vidaUtil,
			Long facingCentro, Long facingCentroOrig, Long facingPrevio, String flgEstrategica, String tipoGama, String tipoAprov, String pedir,
			Long cc, String tipoExpositor, String temporada, String anioColeccion, String talla,
			String color, String lote, String modeloProveedor, String tempColNumOrden, String pedible, String fechaSfmDDMMYYYY, String porcionConsumidor,
			Long codArticuloLote, Long nivelLote) {
		super();
		this.codLoc = codLoc;
		this.codArticulo = codArticulo;
		this.nivel = nivel;
		this.denomInforme = denomInforme;
		this.codN1 = codN1;
		this.descCodN1 = descCodN1;
		this.codN2 = codN2;
		this.descCodN2 = descCodN2;
		this.codN3 = codN3;
		this.descCodN3 = descCodN3;
		this.codN4 = codN4;
		this.descCodN4 = descCodN4;
		this.codN5 = codN5;
		this.descCodN5 = descCodN5;
		this.marca = marca;
		this.lmin = lmin;
		this.lsf = lsf;
		this.sfm = sfm;
		this.coberturaSfm = coberturaSfm;
		this.ventaMedia = ventaMedia;
		this.ventaAnticipada = ventaAnticipada;
		this.uc = uc;
		this.stock = stock;
		this.diasStock = diasStock;
		this.capacidad = capacidad;
		this.fechaSfm = fechaSfm;
		this.flgFecsfm = flgFecsfm;
		this.codError = codError;
		this.descError = descError;
		this.clave = clave;
		this.indice = indice;
		this.listadoModificados = listadoModificados;
		this.sfmOrig = sfmOrig;
		this.coberturaSfmOrig = coberturaSfmOrig;
		this.capacidadOrig = capacidadOrig;
		this.vidaUtil = vidaUtil;
		this.facingCentro = facingCentro;
		this.facingCentroOrig = facingCentroOrig;
		this.facingPrevio = facingPrevio;
		this.flgEstrategica = flgEstrategica;
		this.tipoGama = tipoGama;
		this.tipoAprov = tipoAprov;
		this.pedir = pedir;
		this.cc = cc;
		this.tipoExpositor = tipoExpositor;
		this.temporada = temporada;
		this.anioColeccion = anioColeccion;
		this.talla = talla;
		this.color = color;
		this.lote = lote;
		this.modeloProveedor = modeloProveedor;
		this.tempColNumOrden = tempColNumOrden;
		this.pedible = pedible;
		this.fechaSfmDDMMYYYY = fechaSfmDDMMYYYY;
		this.porcionConsumidor = porcionConsumidor;
		this.codArticuloLote = codArticuloLote;
		this.nivelLote = nivelLote;
		redefineLsf();
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getNivel() {
		return this.nivel;
	}

	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}

	public String getDenomInforme() {
		return this.denomInforme;
	}

	public void setDenomInforme(String denomInforme) {
		this.denomInforme = denomInforme;
	}

	public String getCodN1() {
		return this.codN1;
	}

	public void setCodN1(String codN1) {
		this.codN1 = codN1;
	}

	public String getDescCodN1() {
		return this.descCodN1;
	}

	public void setDescCodN1(String descCodN1) {
		this.descCodN1 = descCodN1;
	}

	public String getCodN2() {
		return this.codN2;
	}

	public void setCodN2(String codN2) {
		this.codN2 = codN2;
	}

	public String getDescCodN2() {
		return this.descCodN2;
	}

	public void setDescCodN2(String descCodN2) {
		this.descCodN2 = descCodN2;
	}

	public String getCodN3() {
		return this.codN3;
	}

	public void setCodN3(String codN3) {
		this.codN3 = codN3;
	}

	public String getDescCodN3() {
		return this.descCodN3;
	}

	public void setDescCodN3(String descCodN3) {
		this.descCodN3 = descCodN3;
	}

	public String getCodN4() {
		return this.codN4;
	}

	public void setCodN4(String codN4) {
		this.codN4 = codN4;
	}

	public String getDescCodN4() {
		return this.descCodN4;
	}

	public void setDescCodN4(String descCodN4) {
		this.descCodN4 = descCodN4;
	}

	public String getCodN5() {
		return this.codN5;
	}

	public void setCodN5(String codN5) {
		this.codN5 = codN5;
	}

	public String getDescCodN5() {
		return this.descCodN5;
	}

	public void setDescCodN5(String descCodN5) {
		this.descCodN5 = descCodN5;
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

	public Double getSfm() {
		return this.sfm;
	}

	public void setSfm(Double sfm) {
		this.sfm = sfm;
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

	public Double getUc() {
		return this.uc;
	}

	public void setUc(Double uc) {
		this.uc = uc;
	}

	public Double getStock() {
		return this.stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Double getDiasStock() {
		return this.diasStock;
	}

	public void setDiasStock(Double diasStock) {
		this.diasStock = diasStock;
	}

	public Double getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Double capacidad) {
		this.capacidad = capacidad;
	}

	public Date getFechaSfm() {
		return this.fechaSfm;
	}

	public void setFechaSfm(Date fechaSfm) {
		this.fechaSfm = fechaSfm;
	}
	public Long getFlgFecsfm() {
		return this.flgFecsfm;
	}

	public void setFlgFecsfm(Long flgFecsfm) {
		this.flgFecsfm = flgFecsfm;
	}

	public Long getCodError() {
		return this.codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return this.descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}
	
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public int getIndice() {
		return this.indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public List<CamposModificadosSFM> getListadoModificados() {
		return this.listadoModificados;
	}

	public void setListadoModificados(List<CamposModificadosSFM> listadoModificados) {
		this.listadoModificados = listadoModificados;
	}
	
	public Double getSfmOrig() {
		return this.sfmOrig;
	}

	public void setSfmOrig(Double sfmOrig) {
		this.sfmOrig = sfmOrig;
	}

	public Double getCoberturaSfmOrig() {
		return this.coberturaSfmOrig;
	}

	public void setCoberturaSfmOrig(Double coberturaSfmOrig) {
		this.coberturaSfmOrig = coberturaSfmOrig;
	}
	
	public Double getCapacidadOrig() {
		return this.capacidadOrig;
	}

	public void setCapacidadOrig(Double capacidadOrig) {
		this.capacidadOrig = capacidadOrig;
	}
	
	public Long getVidaUtil() {
		return this.vidaUtil;
	}

	public void setVidaUtil(Long vidaUtil) {
		this.vidaUtil = vidaUtil;
	}

	public String getPedir() {
		return pedir;
	}

	public void setPedir(String pedir) {
		this.pedir = pedir;
	}
	
	public String getLoteSN() {
		return loteSN;
	}

	public void setLoteSN(String loteSN) {
		this.loteSN = loteSN;
	}

	public Long getFacingCentro() {
		return facingCentro;
	}

	public void setFacingCentro(Long facingCentro) {
		this.facingCentro = facingCentro;
	}

	public Long getFacingPrevio() {
		return facingPrevio;
	}

	public void setFacingPrevio(Long facingPrevio) {
		this.facingPrevio = facingPrevio;
	}

	public String getFlgEstrategica() {
		return flgEstrategica;
	}

	public void setFlgEstrategica(String flgEstrategica) {
		this.flgEstrategica = flgEstrategica;
	}

	public Long getFacingCentroOrig() {
		return facingCentroOrig;
	}

	public void setFacingCentroOrig(Long facingCentroOrig) {
		this.facingCentroOrig = facingCentroOrig;
	}

	public String getTipoGama() {
		return tipoGama;
	}

	public void setTipoGama(String tipoGama) {
		this.tipoGama = tipoGama;
	}

	public String getTipoAprov() {
		return tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public Long getCc() {
		return this.cc;
	}

	public void setCc(Long cc) {
		this.cc = cc;
	}

	public String getTipoExpositor() {
		return this.tipoExpositor;
	}

	public void setTipoExpositor(String tipoExpositor) {
		this.tipoExpositor = tipoExpositor;
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

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
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

	public String getPedible() {
		return pedible;
	}

	public void setPedible(String pedible) {
		this.pedible = pedible;
	}
	
	public String getFechaSfmDDMMYYYY() {
		return this.fechaSfmDDMMYYYY;
	}

	public void setFechaSfmDDMMYYY(String fechaSfmDDMMYYYY) {
		this.fechaSfmDDMMYYYY = fechaSfmDDMMYYYY;
	}
	
	public String getPorcionConsumidor() {
		return porcionConsumidor;
	}

	public void setPorcionConsumidor(String porcionConsumidor) {
		this.porcionConsumidor = porcionConsumidor;
	}

	public Long getCodArticuloLote() {
		return codArticuloLote;
	}

	public void setCodArticuloLote(Long codArticuloLote) {
		this.codArticuloLote = codArticuloLote;
	}

	public Long getNivelLote() {
		return nivelLote;
	}

	public void setNivelLote(Long nivelLote) {
		this.nivelLote = nivelLote;
	}
	
	
	public String getFlgMuchoPoco() {
		return flgMuchoPoco;
	}

	public void setFlgMuchoPoco(String flgMuchoPoco) {
		this.flgMuchoPoco = flgMuchoPoco;
	}

	public String getFlgFoto() {
		return flgFoto;
	}

	public void setFlgFoto(String flgFoto) {
		this.flgFoto = flgFoto;
	}

	public String getFlgNueva() {
		return flgNueva;
	}

	public void setFlgNueva(String flgNueva) {
		this.flgNueva = flgNueva;
	}

	public String getFlgSfmFijo() {
		return flgSfmFijo;
	}

	public void setFlgSfmFijo(String flgSfmFijo) {
		this.flgSfmFijo = flgSfmFijo;
	}

	public Long getCcEstr() {
		return ccEstr;
	}

	public void setCcEstr(Long ccEstr) {
		this.ccEstr = ccEstr;
	}

	public String getFlgNsr() {
		return flgNsr;
	}

	public void setFlgNsr(String flgNsr) {
		this.flgNsr = flgNsr;
	}
	
	public String getFlgUfp() {
		return flgUfp;
	}

	public void setFlgUfp(String flgUfp) {
		this.flgUfp = flgUfp;
	}
	
	public String getFlgSoloImagen() {
		return flgSoloImagen;
	}

	public void setFlgSoloImagen(String flgSoloImagen) {
		this.flgSoloImagen = flgSoloImagen;
	}
	
	public Double getMultiplicadorFacing() {
		return this.multiplicadorFacing;
	}

	public void setMultiplicadorFacing(Double multiplicadorFacing) {
		this.multiplicadorFacing = multiplicadorFacing;
	}
	
	public Double getImagenComercialMin() {
		return this.imagenComercialMin;
	}

	public void setImagenComercialMin(Double imagenComercialMin) {
		this.imagenComercialMin = imagenComercialMin;
	}
	
	
	public String getFlgActualizarFacing() {
		return flgActualizarFacing;
	}

	public void setFlgActualizarFacing(String flgActualizarFacing) {
		this.flgActualizarFacing = flgActualizarFacing;
	}
	
	public String getFlgPintarTengoPoco() {
		return flgPintarTengoPoco;
	}

	public void setFlgPintarTengoPoco(String flgPintarTengoPoco) {
		this.flgPintarTengoPoco = flgPintarTengoPoco;
	}

	public String getFlgPintarLinkTengoPoco() {
		return flgPintarLinkTengoPoco;
	}

	public void setFlgPintarLinkTengoPoco(String flgPintarLinkTengoPoco) {
		this.flgPintarLinkTengoPoco = flgPintarLinkTengoPoco;
	}
	

	public Double getStockBajo() {
		return stockBajo;
	}

	public void setStockBajo(Double stockBajo) {
		this.stockBajo = stockBajo;
	}
	
	public Long getCodArtCaprabo() {
		return codArtCaprabo;
	}

	public void setCodArtCaprabo(Long codArtCaprabo) {
		this.codArtCaprabo = codArtCaprabo;
	}

	@Override
	public int compareTo(VArtSfm o) {
		
		int test= (int) (this.sfm-o.sfm);
		System.out.println(o.getDenomInforme() +" : "+test);
		return (test);
	}

	private void redefineLsf(){
		if (this.lmin != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.fechaSfm);
			cal.add(Calendar.DAY_OF_MONTH, 20);
			if (cal.getTime().after(new Date())){
				this.lsf = new Double(0);
			}
			if (this.lsf != null){
				int retval = Double.compare(this.lsf, this.lmin);
				if (retval < 0){
					this.lsf = this.lmin;
				}
			}else{
				this.lsf = this.lmin;
			}
		}			
	}
	
	
	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof VArtSfm)){
			return false;
		}
		VArtSfm npr = (VArtSfm) obj;
		return new EqualsBuilder().append(this.codLoc, npr.codLoc).append(this.codArticulo, npr.codArticulo).isEquals();
	}
	
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(this.codLoc).append(this.codArticulo).toHashCode();
	}

	public String getTipoEstructura() {
		return tipoEstructura;
	}

	public void setTipoEstructura(String tipoEstructura) {
		this.tipoEstructura = tipoEstructura;
	}
	
}