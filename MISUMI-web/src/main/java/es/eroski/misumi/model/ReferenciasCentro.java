package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class ReferenciasCentro implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codArt; 
	private Long codCentro; 
	
	private VDatosDiarioArt diarioArt; 
	private VSurtidoTienda surtidoTienda;
	private OfertaVigente oferta;
	private VSurtidoTienda variablesPedido;
	private VRelacionArticulo articuloRelacionado;
	private ValoresStock valoresStock;
	private TMisMcgCaprabo tMisMcgCaprabo;
	private OfertaPVP ofertaPVP;
	
	private Long codArtCaprabo;
	private String descArtCaprabo; 
	private Long codArtEroski;

	private String respuestaWSStock;

	private PedidoAdicionalContadores pedidoAdicionalContadores;
	
	//Para el tratamiento de articulos relacionados (ffpp activo o unitaria)
	private boolean tieneFfppActivo;
	private boolean tieneUnitaria;
	private Long codArtRelacionado;
	
	//Para el tratamiento Movimiento Continuo de Gama (Sustituida por o Sustituta de)
	private Long sustituidaPor; 
	private Long sustitutaDe; 
	private boolean mostrarLinkRefNuevaVieja;
	private String implantacion;
	private Date fechaActivacion;
	private String flgColorImplantacion;
	private boolean mostrarImplantacion;
	private Date fechaGen;
	private String strFechaGen;
	private boolean mostrarFechaGen;
	
	

	//Para el tratamiento del campo cc
	private Long cc;
	
	private String origenPantalla;
	
	
	//Accion Franquiciados
	private String accion;
	
	//Franquicidados - Foto
	private String tieneFoto;
	
	private String nextDayPedido;
	

	//Deposito Brita
	private String flgDepositoBrita;
	
	//Referencia por catálogo
	private String flgPorCatalogo;
	
	//Campos para textil
	private String temporada;
	private String anioColeccion;
	private String talla;
	private String color;
	private String lote;
	private String modeloProveedor;
	private String numOrden;
	private String pedible;
	private String motivoCaprabo;
	
	//Campo para control de Caprabo
	private boolean esCentroCaprabo;
	private boolean esCentroCapraboEspecial;
	private boolean esCentroCapraboNuevo;
	
	//Campos para detallado
	private Double stockTienda;
	private Double pendienteTienda;
	private Double pendienteTiendaManana;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String descGrupo3; //Descripción del 3. nivel de la estructura
	private Double ufp;
	private Double dexenx; //Contendra la division entre UFP y U/C en el caso de que tipo_UFP se U. (Se decide que el campo contenga tambien la parte entera lo mismmo que DEXENXENTERO)
	private Double dexenxEntero; //Contendra la division entre UFP y U/C en el caso de que tipo_UFP se U. SOLO LA PARTE ENTERA
	private String tipoUFP;
	
	private String flgOferta;

	//Obtenemos el stock plataforma MISUMI-205
	private Double stockPlataforma;
	
	private Long codError;
	private String descError;
	
	private String estadoPedido;
	
	//Campo para control de Vegalsa
	private boolean esTratamientoVegalsa;
	
	private String mapaReferencia;
	
	//MISUMI-428
	private String MAC;
	private String espacio;
	private String cantidadMA;
	private String fechaInicioMA;
	private String fechaFinMA;
	
	//Campo para controlar si existe en la tabla de gama el registro
	private boolean existeGama;
	
	private String bloqueo;
	
	private String msgUNIDADESCAJAS; // Contiene el texto que se mostrará en la pestaña "Imagen Comercial".
	
	public ReferenciasCentro() {
	    super();
	    this.valoresStock = new ValoresStock();
	   
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	
	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	
	public VDatosDiarioArt getDiarioArt() {
		return this.diarioArt;
	}

	public void setDiarioArt(VDatosDiarioArt diarioArt) {
		this.diarioArt = diarioArt;
	}

	public VSurtidoTienda getSurtidoTienda() {
		return this.surtidoTienda;
	}

	public void setSurtidoTienda(VSurtidoTienda surtidoTienda) {
		this.surtidoTienda = surtidoTienda;
	}

	public OfertaVigente getOferta() {
		return this.oferta;
	}

	public void setOferta(OfertaVigente oferta) {
		this.oferta = oferta;
	}

	public VSurtidoTienda getVariablesPedido() {
		return this.variablesPedido;
	}

	public void setVariablesPedido(VSurtidoTienda variablesPedido) {
		this.variablesPedido = variablesPedido;
	}

	public VRelacionArticulo getArticuloRelacionado() {
		return this.articuloRelacionado;
	}

	public void setArticuloRelacionado(VRelacionArticulo articuloRelacionado) {
		this.articuloRelacionado = articuloRelacionado;
	}

	public ValoresStock getValoresStock() {
		return valoresStock;
	}

	public void setValoresStock(ValoresStock valoresStock) {
		this.valoresStock = valoresStock;
	}
	
	public TMisMcgCaprabo gettMisMcgCaprabo() {
		return tMisMcgCaprabo;
	}

	public void settMisMcgCaprabo(TMisMcgCaprabo tMisMcgCaprabo) {
		this.tMisMcgCaprabo = tMisMcgCaprabo;
	}
	
	public OfertaPVP getOfertaPVP() {
		return ofertaPVP;
	}

	public void setOfertaPVP(OfertaPVP ofertaPVP) {
		this.ofertaPVP = ofertaPVP;
	}
	
	public Long getCodArtCaprabo() {
		return codArtCaprabo;
	}

	public void setCodArtCaprabo(Long codArtCaprabo) {
		this.codArtCaprabo = codArtCaprabo;
	}

	public Long getCodArtEroski() {
		return codArtEroski;
	}

	public void setCodArtEroski(Long codArtEroski) {
		this.codArtEroski = codArtEroski;
	}

	public PedidoAdicionalContadores getPedidoAdicionalContadores() {
		return pedidoAdicionalContadores;
	}

	public void setPedidoAdicionalContadores(
			PedidoAdicionalContadores pedidoAdicionalContadores) {
		this.pedidoAdicionalContadores = pedidoAdicionalContadores;
	}

	public boolean isTieneFfppActivo() {
		return this.tieneFfppActivo;
	}

	public void setTieneFfppActivo(boolean tieneFfppActivo) {
		this.tieneFfppActivo = tieneFfppActivo;
	}

	public boolean isTieneUnitaria() {
		return this.tieneUnitaria;
	}

	public void setTieneUnitaria(boolean tieneUnitaria) {
		this.tieneUnitaria = tieneUnitaria;
	}

	public Long getCodArtRelacionado() {
		return this.codArtRelacionado;
	}

	public void setCodArtRelacionado(Long codArtRelacionado) {
		this.codArtRelacionado = codArtRelacionado;
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
	
	public boolean isMostrarLinkRefNuevaVieja() {
		return mostrarLinkRefNuevaVieja;
	}

	public void setMostrarLinkRefNuevaVieja(boolean mostrarLinkRefNuevaVieja) {
		this.mostrarLinkRefNuevaVieja = mostrarLinkRefNuevaVieja;
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
	
	public boolean isMostrarImplantacion() {
		return mostrarImplantacion;
	}

	public void setMostrarImplantacion(boolean mostrarImplantacion) {
		this.mostrarImplantacion = mostrarImplantacion;
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

	public Long getCc() {
		return this.cc;
	}

	public void setCc(Long cc) {
		this.cc = cc;
	}

	public String getOrigenPantalla() {
		return origenPantalla;
	}

	public void setOrigenPantalla(String origenPantalla) {
		this.origenPantalla = origenPantalla;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getTieneFoto() {
		return tieneFoto;
	}

	public void setTieneFoto(String tieneFoto) {
		this.tieneFoto = tieneFoto;
	}

	public String getNextDayPedido() {
		return nextDayPedido;
	}

	public void setNextDayPedido(String nextDayPedido) {
		this.nextDayPedido = nextDayPedido;
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

	public String getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(String numOrden) {
		this.numOrden = numOrden;
	}

	public String getPedible() {
		return pedible;
	}

	public void setPedible(String pedible) {
		this.pedible = pedible;
	}

	public String getDescArtCaprabo() {
		return descArtCaprabo;
	}

	public void setDescArtCaprabo(String descArtCaprabo) {
		this.descArtCaprabo = descArtCaprabo;
	}

	public String getMotivoCaprabo() {
		return motivoCaprabo;
	}

	public void setMotivoCaprabo(String motivoCaprabo) {
		this.motivoCaprabo = motivoCaprabo;
	}

	public boolean getEsCentroCaprabo() {
		return this.esCentroCaprabo;
	}

	public void setEsCentroCaprabo(boolean esCentroCaprabo) {
		this.esCentroCaprabo = esCentroCaprabo;
	}
	

	public boolean getEsCentroCapraboEspecial() {
		return this.esCentroCapraboEspecial;
	}

	public void setEsCentroCapraboEspecial(boolean esCentroCapraboEspecial) {
		this.esCentroCapraboEspecial = esCentroCapraboEspecial;
	}
	

	public boolean getEsCentroCapraboNuevo() {
		return this.esCentroCapraboNuevo;
	}

	public void setEsCentroCapraboNuevo(boolean esCentroCapraboNuevo) {
		this.esCentroCapraboNuevo = esCentroCapraboNuevo;
	}

	public Double getStockTienda() {
		return this.stockTienda;
	}

	public void setStockTienda(Double stockTienda) {
		this.stockTienda = stockTienda;
	}

	public Double getPendienteTienda() {
		return this.pendienteTienda;
	}

	public void setPendienteTienda(Double pendienteTienda) {
		this.pendienteTienda = pendienteTienda;
	}

	public Double getPendienteTiendaManana() {
		return this.pendienteTiendaManana;
	}

	public void setPendienteTiendaManana(Double pendienteTiendaManana) {
		this.pendienteTiendaManana = pendienteTiendaManana;
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
	
	public String getDescGrupo3() {
		return descGrupo3;
	}

	public void setDescGrupo3(String descGrupo3) {
		this.descGrupo3 = descGrupo3;
	}

	public Double getDexenx() {
		return dexenx;
	}

	public void setDexenx(Double dexenx) {
		this.dexenx = dexenx;
	}

	public Double getDexenxEntero() {
		return dexenxEntero;
	}

	public void setDexenxEntero(Double dexenxEntero) {
		this.dexenxEntero = dexenxEntero;
	}

	public String getTipoUFP() {
		return tipoUFP;
	}

	public void setTipoUFP(String tipoUFP) {
		this.tipoUFP = tipoUFP;
	}

	public String getFlgOferta() {
		return flgOferta;
	}

	public void setFlgOferta(String flgOferta) {
		this.flgOferta = flgOferta;
	}
	
	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public String getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public Double getStockPlataforma() {
		return stockPlataforma;
	}

	public void setStockPlataforma(Double stockPlataforma) {
		this.stockPlataforma = stockPlataforma;
	}

	public boolean isEsTratamientoVegalsa() {
		return esTratamientoVegalsa;
	}

	public void setEsTratamientoVegalsa(boolean esTratamientoVegalsa) {
		this.esTratamientoVegalsa = esTratamientoVegalsa;
	}

	public String getMapaReferencia() {
		return mapaReferencia;
	}

	public void setMapaReferencia(String mapaReferencia) {
		this.mapaReferencia = mapaReferencia;
	}

	public String getRespuestaWSStock() {
		return respuestaWSStock;
	}

	public void setRespuestaWSStock(String respuestaWSStock) {
		this.respuestaWSStock = respuestaWSStock;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getEspacio() {
		return espacio;
	}

	public void setEspacio(String espacio) {
		this.espacio = espacio;
	}

	public String getCantidadMA() {
		return cantidadMA;
	}

	public void setCantidadMA(String cantidadMA) {
		this.cantidadMA = cantidadMA;
	}

	public String getFechaInicioMA() {
		return fechaInicioMA;
	}

	public void setFechaInicioMA(String fechaInicioMA) {
		this.fechaInicioMA = fechaInicioMA;
	}

	public String getFechaFinMA() {
		return fechaFinMA;
	}

	public void setFechaFinMA(String fechaFinMA) {
		this.fechaFinMA = fechaFinMA;
	}

	public boolean isExisteGama() {
		return existeGama;
	}

	public void setExisteGama(boolean existeGama) {
		this.existeGama = existeGama;
	}

	public String getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(String bloqueo) {
		this.bloqueo = bloqueo;
	}

	public String getMsgUNIDADESCAJAS() {
		return msgUNIDADESCAJAS;
	}

	public void setMsgUNIDADESCAJAS(String msgUNIDADESCAJAS) {
		this.msgUNIDADESCAJAS = msgUNIDADESCAJAS;
	}

}