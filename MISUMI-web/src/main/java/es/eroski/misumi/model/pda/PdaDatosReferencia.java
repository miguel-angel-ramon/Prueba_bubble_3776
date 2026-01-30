package es.eroski.misumi.model.pda;

import java.io.Serializable;
import java.util.Date;

import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VPlanograma;
import es.eroski.misumi.model.ValoresStock;

public class PdaDatosReferencia implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt;
	private Long codArtPromo;
	private String descArt;
	private String stockActual;
	private String pedidoActivo;
	private String mostrarFFPP;
	private Long codArtRel;
	private String uniCajaServ;
	private String tipoAprov;
	private String flgGamaDiscont;
	private String ufp;
	private String numOferta;
	private String tarifa = "0";
	private String competencia = "0";
	private String oferta = "0";
	private String anticipada = "0";
	private String flgSfmCapacidad;
	private String flgFacing;
	private String flgFacingCapacidad;
	private String sfm;
	private String capacidadLineal;
	private String facingLineal;
	private String capacidad1;
	private String facing1;
	private String capacidad2;
	private String facing2;
	private String esAutoServicio;
	private String esError;
	private String parametrizadoSfmCap;
	private String pedir;
	private String parametrizadoFacing;
	private String facingCentro;
	private String stockActivo;
	private String MMC;
	private Long cantHoy;
	private Long cantFutura;
	
	//Para el tratamiento Movimiento Continuo de Gama (Sustituida por o Sustituta de)
	private String mostrarSustARef;
	private String mostrarSustPorRef;
	private Long sustituidaPor; 
	private Long sustitutaDe; 
	private String implantacion;
	private Date fechaActivacion;
	private String flgColorImplantacion;
	private Date fechaGen;
	private String strFechaGen;
	private boolean mostrarFechaGen;
	private String diasStock;
	private Float ventaMedia;
	private boolean existeVentaMedia;
	
	
	//Peticion 52467 Incluir Facing SIA
	private Long facingCentroSIA;
	
	//Pet. 53005
	private Integer multiplicadorFac;
	private Long imc;
	private String flgCapacidad;
	private String flgFacingX;
	
	//Pet. 54867
	private String tipoRotacion;

	//Pet.55590
	private TMisMcgCaprabo tMisMcgCaprabo;
	
	private String motivoCaprabo;
	
	private String calculoCC;
	
	private VPlanograma  vPlanogramaTipoP;
	
	//Franquicidados - Foto
	private String tieneFoto;
	
	//MISUMI-171
	private String esUSS;
	
	//MISUMI-124
	private ImagenComercial imagenComercial;

	//MISUMI-428
	private String cantidadMac;
	private String cantidadMa;
	
	//MISUMI-648
	private String incrementoPrevisionVenta;
	private String stockMinimoEstatico;

	//Cantidades calculadas de SIA
	private String cantidaSfmSIA;
	private String cantidadFacLinealSIA;
	private String cantidadCapLinealSIA;
	private String mostrarCantidadSfmSIA;
	private String mostrarCantidadCapSIA;
	private String flgOfertaVigente;
	
	//Valores del stock
	private ValoresStock valoresStock;
	
	//Deposito Brita
	private String flgDepositoBrita;
	
	//Referencia por catálogo
	private String flgPorCatalogo;
	
	//Vegalsa
	private boolean tratamientoVegalsa;
	
	private String descSubcategoria;
	
// Para PRUEBAS. Se añade una nueva propiedad.
	private VDatosDiarioArt datosDiarioArt;
	
	// Indica si el centro tiene la parametrización 145_AYUDA_UBICAR_ALTAS.
	private String parametrizadoAyudaFacing;
	
	private String msgUNIDADESCAJAS;
	private String msgUNIDADESCAJASFacing;
	
	public PdaDatosReferencia() {
	    super();
	}

	public PdaDatosReferencia(Long codArt, String descArt, String stockActual, String pedidoActivo, String mostrarFFPP, Long codArtRel,
			String uniCajaServ, String tipoAprov,	String flgGamaDiscont, String ufp, String numOferta, String tarifa, 
			String competencia, String oferta, String anticipada,String flgSfmCapacidad, String flgFacing, String sfm, String capacidadLineal, String facingLineal,
			String capacidad1, String facing1, String capacidad2, String facing2, String esAutoServicio, String pedir, String esError, boolean tratamientoVegalsa) {
	    super();
	    this.codArt=codArt;
	    this.descArt=descArt;
	    this.stockActual=stockActual;
	    this.pedidoActivo=pedidoActivo;
	    this.mostrarFFPP=mostrarFFPP;
	    this.codArtRel=codArtRel;
	    this.uniCajaServ=uniCajaServ;
	    this.tipoAprov = tipoAprov;
	    this.flgGamaDiscont = flgGamaDiscont;
	    this.ufp = ufp;
	    this.numOferta = numOferta;
	    this.tarifa = tarifa;
	    this.competencia = competencia;
	    this.oferta = oferta;
	    this.anticipada = anticipada;
	    this.flgSfmCapacidad = flgSfmCapacidad;
	    this.flgFacing = flgFacing;
	    this.sfm = sfm;
	    this.capacidadLineal = capacidadLineal;
	    this.facingLineal = facingLineal;
	    this.capacidad1 = capacidad1;
	    this.facing1 = facing1;
	    this.capacidad2 = capacidad2;
	    this.facing2 = facing2;
	    this.esAutoServicio = esAutoServicio;
	    this.pedir = pedir;
	    this.esError = esError;
	    this.tratamientoVegalsa = tratamientoVegalsa;
	    
	}

	public Long getCantHoy() {
		return cantHoy;
	}

	public void setCantHoy(Long cantHoy) {
		this.cantHoy = cantHoy;
	}

	public Long getCantFutura() {
		return cantFutura;
	}

	public void setCantFutura(Long cantFutura) {
		this.cantFutura = cantFutura;
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
	
	public String getStockActual() {
		return this.stockActual;
	}

	public void setStockActual(String stockActual) {
		this.stockActual = stockActual;
	}
	
	public String getPedidoActivo() {
		return this.pedidoActivo;
	}

	public void setPedidoActivo(String pedidoActivo) {
		this.pedidoActivo = pedidoActivo;
	}
	
	public String getMostrarFFPP() {
		return this.mostrarFFPP;
	}

	public void setMostrarFFPP(String mostrarFFPP) {
		this.mostrarFFPP = mostrarFFPP;
	}
	
	public Long getCodArtRel() {
		return this.codArtRel;
	}

	public void setCodArtRel(Long codArtRel) {
		this.codArtRel = codArtRel;
	}
	
	public String getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(String uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}
	
	public String getTipoAprov() {
		return this.tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}
	
	public String getFlgGamaDiscont() {
		return this.flgGamaDiscont;
	}

	public void setFlgGamaDiscont(String flgGamaDiscont) {
		this.flgGamaDiscont = flgGamaDiscont;
	}
	
	public String getUfp() {
		return this.ufp;
	}

	public void setUfp(String ufp) {
		this.ufp = ufp;
	}
	
	public String getNumOferta() {
		return this.numOferta;
	}

	public void setNumOferta(String numOferta) {
		this.numOferta = numOferta;
	}
	
	public String getTarifa() {
		return this.tarifa;
	}

	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	
	public String getCompetencia() {
		return this.competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}
	
	public String getOferta() {
		return this.oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}
	
	public String getAnticipada() {
		return this.anticipada;
	}

	public void setAnticipada(String anticipada) {
		this.anticipada = anticipada;
	}
	
	public String getFlgSfmCapacidad() {
		return this.flgSfmCapacidad;
	}

	public void setFlgSfmCapacidad(String flgSfmCapacidad) {
		this.flgSfmCapacidad = flgSfmCapacidad;
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

	public String getSfm() {
		return this.sfm;
	}

	public void setSfm(String sfm) {
		this.sfm = sfm;
	}
	
	public String getCapacidadLineal() {
		return this.capacidadLineal;
	}

	public void setCapacidadLineal(String capacidadLineal) {
		this.capacidadLineal = capacidadLineal;
	}
	
	public String getFacingLineal() {
		return this.facingLineal;
	}

	public void setFacingLineal(String facingLineal) {
		this.facingLineal = facingLineal;
	}
	
	public String getCapacidad1() {
		return this.capacidad1;
	}

	public void setCapacidad1(String capacidad1) {
		this.capacidad1 = capacidad1;
	}
	
	public String getFacing1() {
		return this.facing1;
	}

	public void setFacing1(String facing1) {
		this.facing1 = facing1;
	}
	
	public String getCapacidad2() {
		return this.capacidad2;
	}

	public void setCapacidad2(String capacidad2) {
		this.capacidad2 = capacidad2;
	}
	
	public String getFacing2() {
		return this.facing2;
	}

	public void setFacing2(String facing2) {
		this.facing2 = facing2;
	}
	
	public String getEsAutoServicio() {
		return this.esAutoServicio;
	}

	public void setEsAutoServicio(String esAutoServicio) {
		this.esAutoServicio = esAutoServicio;
	}
	
	public String getEsError() {
		return this.esError;
	}

	public void setEsError(String esError) {
		this.esError = esError;
	}

	public String getCantidaSfmSIA() {
		return this.cantidaSfmSIA;
	}

	public void setCantidaSfmSIA(String cantidaSfmSIA) {
		this.cantidaSfmSIA = cantidaSfmSIA;
	}

	public String getCantidadFacLinealSIA() {
		return this.cantidadFacLinealSIA;
	}

	public void setCantidadFacLinealSIA(String cantidadFacLinealSIA) {
		this.cantidadFacLinealSIA = cantidadFacLinealSIA;
	}

	public String getCantidadCapLinealSIA() {
		return this.cantidadCapLinealSIA;
	}

	public void setCantidadCapLinealSIA(String cantidadCapLinealSIA) {
		this.cantidadCapLinealSIA = cantidadCapLinealSIA;
	}

	public String getMostrarCantidadSfmSIA() {
		return this.mostrarCantidadSfmSIA;
	}

	public void setMostrarCantidadSfmSIA(String mostrarCantidadSfmSIA) {
		this.mostrarCantidadSfmSIA = mostrarCantidadSfmSIA;
	}

	public String getMostrarCantidadCapSIA() {
		return this.mostrarCantidadCapSIA;
	}

	public void setMostrarCantidadCapSIA(String mostrarCantidadCapSIA) {
		this.mostrarCantidadCapSIA = mostrarCantidadCapSIA;
	}

	public String getParametrizadoSfmCap() {
		return parametrizadoSfmCap;
	}

	public void setParametrizadoSfmCap(String parametrizadoSfmCap) {
		this.parametrizadoSfmCap = parametrizadoSfmCap;
	}

	public String getPedir() {
		return this.pedir;
	}

	public void setPedir(String pedir) {
		this.pedir = pedir;
	}
	
	public String getParametrizadoFacing() {
		return parametrizadoFacing;
	}

	public void setParametrizadoFacing(String parametrizadoFacing) {
		this.parametrizadoFacing = parametrizadoFacing;
	}
	
	public String getFacingCentro() {
		return this.facingCentro;
	}

	public void setFacingCentro(String facingCentro) {
		this.facingCentro = facingCentro;
	}

	public String getStockActivo() {
		return stockActivo;
	}

	public void setStockActivo(String stockActivo) {
		this.stockActivo = stockActivo;
	}

	public String getMMC() {
		return MMC;
	}

	public void setMMC(String mMC) {
		MMC = mMC;
	}

	public String getFlgOfertaVigente() {
		return this.flgOfertaVigente;
	}

	public void setFlgOfertaVigente(String flgOfertaVigente) {
		this.flgOfertaVigente = flgOfertaVigente;
	}

	public ValoresStock getValoresStock() {
		return valoresStock;
	}

	public void setValoresStock(ValoresStock valoresStock) {
		this.valoresStock = valoresStock;
	}
	
	public String getMostrarSustARef() {
		return this.mostrarSustARef;
	}

	public void setMostrarSustARef(String mostrarSustARef) {
		this.mostrarSustARef = mostrarSustARef;
	}
	
	public String getMostrarSustPorRef() {
		return this.mostrarSustPorRef;
	}

	public void setMostrarSustPorRef(String mostrarSustPorRef) {
		this.mostrarSustPorRef = mostrarSustPorRef;
	}
	
	public Long getSustituidaPor() {
		return this.sustituidaPor;
	}

	public void setSustituidaPor(Long sustituidaPor) {
		this.sustituidaPor = sustituidaPor;
	}
	
	public Long getSustitutaDe() {
		return this.sustitutaDe;
	}

	public void setSustitutaDe(Long sustitutaDe) {
		this.sustitutaDe = sustitutaDe;
	}
	
	public String getImplantacion() {
		return this.implantacion;
	}

	public void setImplantacion(String implantacion) {
		this.implantacion = implantacion;
	}
	
	public Date getFechaActivacion() {
		return this.fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	
	public String getFlgColorImplantacion() {
		return this.flgColorImplantacion;
	}

	public void setFlgColorImplantacion(String flgColorImplantacion) {
		this.flgColorImplantacion = flgColorImplantacion;
	}
	
	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}
	
	public String getStrFechaGen() {
		return strFechaGen;
	}

	public void setStrFechaGen(String strFechaGen) {
		this.strFechaGen = strFechaGen;
	}

	public boolean isMostrarFechaGen() {
		return mostrarFechaGen;
	}

	public void setMostrarFechaGen(boolean mostrarFechaGen) {
		this.mostrarFechaGen = mostrarFechaGen;
	}

	public String getDiasStock() {
		return diasStock;
	}
	public void setDiasStock(String diasStock) {
		this.diasStock = diasStock;
	}
	public Float getVentaMedia() {
		return ventaMedia;
	}
	public void setVentaMedia(Float ventaMedia) {
		this.ventaMedia = ventaMedia;
	}
	public boolean getExisteVentaMedia() {
		return existeVentaMedia;
	}
	public void setExisteVentaMedia(boolean existeVentaMedia) {
		this.existeVentaMedia = existeVentaMedia;
	}

	public Long getFacingCentroSIA() {
		return facingCentroSIA;
	}

	public void setFacingCentroSIA(Long facingCentroSIA) {
		this.facingCentroSIA = facingCentroSIA;
	}
	public String getFlgDepositoBrita() {
		return this.flgDepositoBrita;
	}

	public void setFlgDepositoBrita(String flgDepositoBrita) {
		this.flgDepositoBrita = flgDepositoBrita;
	}

	public String getFlgPorCatalogo() {
		return this.flgPorCatalogo;
	}

	public void setFlgPorCatalogo(String flgPorCatalogo) {
		this.flgPorCatalogo = flgPorCatalogo;
	}
	
	public Integer getMultiplicadorFac() {
		return multiplicadorFac;
	}

	public void setMultiplicadorFac(Integer multiplicadorFac) {
		this.multiplicadorFac = multiplicadorFac;
	}
	
	public Long getImc() {
		return imc;
	}

	public void setImc(Long imc) {
		this.imc = imc;
	}

	public String getFlgCapacidad() {
		return flgCapacidad;
	}

	public void setFlgCapacidad(String flgCapacidad) {
		this.flgCapacidad = flgCapacidad;
	}

	public String getFlgFacingX() {
		return flgFacingX;
	}

	public void setFlgFacingX(String flgFacingX) {
		this.flgFacingX = flgFacingX;
	}
	
	public String getTipoRotacion() {
		return tipoRotacion;
	}

	public void setTipoRotacion(String tipoRotacion) {
		this.tipoRotacion = tipoRotacion;
	}

	public TMisMcgCaprabo gettMisMcgCaprabo() {
		return tMisMcgCaprabo;
	}

	public void settMisMcgCaprabo(TMisMcgCaprabo tMisMcgCaprabo) {
		this.tMisMcgCaprabo = tMisMcgCaprabo;
	}

	public String getMotivoCaprabo() {
		return motivoCaprabo;
	}

	public void setMotivoCaprabo(String motivoCaprabo) {
		this.motivoCaprabo = motivoCaprabo;
	}

	public String getCalculoCC() {
		return calculoCC;
	}

	public void setCalculoCC(String calculoCC) {
		this.calculoCC = calculoCC;
	}

	public VPlanograma getvPlanogramaTipoP() {
		return vPlanogramaTipoP;
	}

	public void setvPlanogramaTipoP(VPlanograma vPlanogramaTipoP) {
		this.vPlanogramaTipoP = vPlanogramaTipoP;
	}

	public String getTieneFoto() {
		return tieneFoto;
	}

	public void setTieneFoto(String tieneFoto) {
		this.tieneFoto = tieneFoto;
	}

	public String getEsUSS() {
		return esUSS;
	}

	public void setEsUSS(String esUSS) {
		this.esUSS = esUSS;
	}

	public ImagenComercial getImagenComercial() {
		return imagenComercial;
	}

	public void setImagenComercial(ImagenComercial imagenComercial) {
		this.imagenComercial = imagenComercial;
	}

	public Long getCodArtPromo() {
		return codArtPromo;
	}

	public void setCodArtPromo(Long codArtPromo) {
		this.codArtPromo = codArtPromo;
	}

	public boolean isTratamientoVegalsa() {
		return tratamientoVegalsa;
	}

	public void setTratamientoVegalsa(boolean tratamientoVegalsa) {
		this.tratamientoVegalsa = tratamientoVegalsa;
	}

	public String getCantidadMac() {
		return cantidadMac;
	}

	public void setCantidadMac(String cantidadMac) {
		this.cantidadMac = cantidadMac;
	}

	public String getCantidadMa() {
		return cantidadMa;
	}

	public void setCantidadMa(String cantidadMa) {
		this.cantidadMa = cantidadMa;
	}
	
	public String getIncrementoPrevisionVenta() {
		return incrementoPrevisionVenta;
	}

	public void setIncrementoPrevisionVenta(String incrementoPrevisionVenta) {
		this.incrementoPrevisionVenta = incrementoPrevisionVenta;
	}

	public String getStockMinimoEstatico() {
		return stockMinimoEstatico;
	}

	public void setStockMinimoEstatico(String stockMinimoEstatico) {
		this.stockMinimoEstatico = stockMinimoEstatico;
	}

	public VDatosDiarioArt getDatosDiarioArt() {
		return datosDiarioArt;
	}

	public void setDatosDiarioArt(VDatosDiarioArt vDatosDiarioArt) {
		this.datosDiarioArt = vDatosDiarioArt;
	}

	public String getParametrizadoAyudaFacing() {
		return parametrizadoAyudaFacing;
	}

	public void setParametrizadoAyudaFacing(String parametrizadoAyudaFacing) {
		this.parametrizadoAyudaFacing = parametrizadoAyudaFacing;
	}

	public String getDescSubcategoria() {
		return descSubcategoria;
	}

	public void setDescSubcategoria(String descSubcategoria) {
		this.descSubcategoria = descSubcategoria;
	}

	public String getMsgUNIDADESCAJAS() {
		return msgUNIDADESCAJAS;
	}

	public void setMsgUNIDADESCAJAS(String msgUNIDADESCAJAS) {
		this.msgUNIDADESCAJAS = msgUNIDADESCAJAS;
	}

	public String getMsgUNIDADESCAJASFacing() {
		return msgUNIDADESCAJASFacing;
	}

	public void setMsgUNIDADESCAJASFacing(String msgUNIDADESCAJASFacing) {
		this.msgUNIDADESCAJASFacing = msgUNIDADESCAJASFacing;
	}

}