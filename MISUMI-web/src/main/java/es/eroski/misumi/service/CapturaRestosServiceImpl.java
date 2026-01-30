package es.eroski.misumi.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CapturaRestoDao;
import es.eroski.misumi.dao.iface.FotosReferenciaDao;
import es.eroski.misumi.dao.iface.ImagenComercialDao;
import es.eroski.misumi.dao.iface.StockTiendaDao;
import es.eroski.misumi.dao.iface.VDatosDiarioArtDao;
import es.eroski.misumi.dao.iface.VRotacionRefDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaCapturaResto;
import es.eroski.misumi.model.pda.PdaCapturaRestosForm;
import es.eroski.misumi.model.pda.PdaCapturaRestosOferta;
import es.eroski.misumi.model.pda.PdaCapturaRestosStock;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.CapturaRestosService;
import es.eroski.misumi.service.iface.KosmosService;
import es.eroski.misumi.service.iface.OfertaVigenteService;
import es.eroski.misumi.service.iface.PlanogramaCentroService;
import es.eroski.misumi.service.iface.PlanogramaVigenteService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Service
public class CapturaRestosServiceImpl implements CapturaRestosService {

	private static Logger logger = Logger.getLogger(CapturaRestosServiceImpl.class);

	@Autowired
	private CapturaRestoDao capturaRestoDao;
	
	@Autowired
	private FotosReferenciaDao fotosReferenciaDao;
	
	@Autowired
	private VRotacionRefDao vRotacionRefDao;
	
	@Autowired
	private StockTiendaDao correccionStockDao;
	
	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;
	
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;
	
	@Autowired
	private PlanogramaVigenteService planogramaVigenteService;
	
	@Autowired
	private PlanogramaCentroService planogramaCentroService;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	@Autowired
	private VDatosDiarioArtDao vDatosDiarioArtDao;
	
	@Autowired
	private KosmosService kosmosService;
	
	@Autowired
	private ImagenComercialDao imagenComercialDao;
	
	@Autowired
	private OfertaVigenteService ofertaVigenteService;

	
	@Override
	public PdaCapturaRestosForm getPageData(final Long codCentro, final Long page, Long codArt, final HttpSession session, final String guardadoStockOk, String guardadoImcOk, String guardadoSfm) throws Exception {

		PdaCapturaRestosForm form = new PdaCapturaRestosForm();
		PdaCapturaResto capturaResto = null;
		
		//Busqueda por codArt
		if (null != codArt ){
			
			//Si no existe, se inserta
			if (! this.existsCapturaResto(codCentro, codArt)){
				this.insertCapturaRestos(codCentro, codArt, session);
			}
			
			capturaResto = this.getCapturaRestoByCodArt(codCentro, codArt);
		} 
		// Busqueda por pagina
		else {
			capturaResto = this.getCapturaRestosByPage(codCentro, page);
		}

		//Si existe registro cargamos los datos de la pagina
		if (null!=capturaResto && null!= capturaResto.getCodArt()){
			
			VDatosDiarioArt vDatosDiarioArt = getDatosDiarioArt(capturaResto.getCodArt());
			
			/*--Recuperar datos que no se obtienen la la tabla T_MIS_CAPTURA_RESTOS --*/ 
			//tieneFoto
			form.setTieneFoto(this.getTieneFoto(capturaResto.getCodArt()));
			
			//STOCK, MSG ALBARAN Y CALCULO CC
			PdaCapturaRestosStock stockWs=this.getValoresStock(capturaResto.getCodArt(), codCentro, vDatosDiarioArt.getGrupo1(), session);
			form.setStockWs(stockWs);
			
			if ("S".equalsIgnoreCase(guardadoStockOk)){
				capturaResto.setFlgModifStock("S");
				capturaResto.setStockDespues(stockWs.getStock());
				capturaResto.setModificarLastUpdateDate(Boolean.TRUE);
			} else {
				if ("N".equalsIgnoreCase(capturaResto.getFlgModifStock())){
					capturaResto.setStockAntes(stockWs.getStock());
				}
			}			
	
			// Datos para la imagen comercial
			final ImagenComercial imc = imagenComercialDao.consultaImc(codCentro, capturaResto.getCodArt());
			capturaResto.setImagenComercial(imc);
			
			//IMAGEN COMERCIAL
			if ("S".equalsIgnoreCase(guardadoImcOk) || (imc.getMetodo().compareTo(Integer.valueOf(2))!=0 && "SI".equalsIgnoreCase(guardadoSfm))){
				capturaResto.setFlgModifFacing("S");
				capturaResto.setFacingDespues(imc.getFacing());
				capturaResto.setCapacidadDespues(imc.getCapacidad());
				capturaResto.setFacingAltoDespues(imc.getFacingAlto());
				capturaResto.setFacingAnchoDespues(imc.getFacingAncho());
				capturaResto.setModificarLastUpdateDate(Boolean.TRUE);
			} else {
				if ("N".equalsIgnoreCase(capturaResto.getFlgModifFacing())){
					capturaResto.setFacingAntes(imc.getFacing());
					capturaResto.setCapacidadAntes(imc.getCapacidad());
					capturaResto.setFacingAltoAntes(imc.getFacingAlto());
					capturaResto.setFacingAnchoAntes(imc.getFacingAncho());
				}
			} 
			
			
			//Oferta
			form.setOferta(this.getOferta(codCentro, capturaResto.getCodArt()));
			
				
			// Tratamiento Vegalsa
			final User user = (User)session.getAttribute("user");
			final boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), capturaResto.getCodArt());
			capturaResto.setTratamientoVegalsa(tratamientoVegalsa);
			
			// Pedido Activo
			final VSurtidoTienda vSurtidoTienda = getSurtidoTienda(capturaResto.getCodArt(), user.getCentro(), tratamientoVegalsa);
			final String pedidoActivo = getPedidoActivo(vSurtidoTienda);
			capturaResto.setPedidoActivo(pedidoActivo);
			
			// MMC
			form.setMmc(this.getMMC(vSurtidoTienda));
			
			// Capacidad 1 y Facing 1
			final PlanogramaVigente planogramaVigente = getPlanogramaVigente(capturaResto.getCodArt(), codCentro);
			final String capacidad1 = null == planogramaVigente ? null : getCapacidad1(planogramaVigente); 
			final String facing1 =  null == planogramaVigente ? null : getFacing1(planogramaVigente); 
			capturaResto.setCapacidad1(capacidad1);
			capturaResto.setFacing1(facing1);
			
			// Pedir
			final String pedir = getPedir(vSurtidoTienda);
			capturaResto.setPedir(pedir);
			
			// Implantacion
			final ConsultaPlanogramaPorReferenciaResponseType consultaPlanogramaWSResponse = getPlanogramaWS(capturaResto.getCodArt(),codCentro,session);
			final String implantacion = getImplantacion(consultaPlanogramaWSResponse);

			capturaResto.setImplantacion(implantacion);
			// Color del enlace
			final Date fechaGen = getFechaGen(vSurtidoTienda);
			final String flgColorImplantacion = getFlgColorImplantacion(implantacion, fechaGen, vSurtidoTienda);
			capturaResto.setFlgColorImplantacion(flgColorImplantacion);
			
			// Capacidad 1
			final Long capacidad = imc.getCapacidad();
			final Long facing = imc.getFacing();
			Integer capacidadFacing = 0;

			if (capacidad!=null && facing!=null && !facing.equals(0L)){
				capacidadFacing = (int) Math.ceil(capacidad / facing);
			}
				
			capturaResto.setCapacidadFacing(capacidadFacing);
				
			
			// Finalmente, metemos el numero total de registros en el formulario
			Long totalRegs = capturaRestoDao.countCapturasRestos(codCentro, null);
			form.setTotalPages(totalRegs);
		}
		//Si no existe registro cargamos mensaje de error
		else{
			form.setTotalPages(0L);
		}
		
		//Modificacion de la referencia en la tabla T_MIS_CAPTURA_RESTOS
		capturaRestoDao.updateCapturaResto(capturaResto);
		
		// Guardamos el objeto capturaResto en el formulario con todos los datos ya introducidos
		form.setCapturaResto(capturaResto);		
		
		return form;
	}

	private VDatosDiarioArt getDatosDiarioArt(Long codArt) throws Exception {

		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		vDatosDiarioArt.setCodArt(codArt);
		return this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
	}

	private String getPedir(VSurtidoTienda vSurtidoTienda) {
		String output = null;
		if (vSurtidoTienda!=null){
			final String pedir = vSurtidoTienda.getPedir();
			if (pedir!=null && pedir!="")
				output = pedir;
			else
				output = "N";
		}
		
		return output;
	}

	private String getFlgColorImplantacion(String implantacion, Date fechaGen, VSurtidoTienda vSurtidoTienda) throws Exception {
		String output = null;
		if (implantacion!=null  && !implantacion.trim().equals("")) {
			if (fechaGen != null) { 
				output = "VERDE";		
			} else {
				if (vSurtidoTiendaService.comprobarStockMayorACero(vSurtidoTienda) > 0 ){
					output = "ROJO";
				}
			}			
		}
		return output;
	}

	private Date getFechaGen(VSurtidoTienda vSurtidoTienda) throws Exception {
		return vSurtidoTiendaService.obtenerFechaGeneracionSurtidoTienda(vSurtidoTienda);
	}

	private String getImplantacion(ConsultaPlanogramaPorReferenciaResponseType input) {
		String output = null;
		
		if (input!=null && input.getCodigoRespuesta().equals("0")){
			output = input.getPlanogramaReferencia(0).getImplantancion();
		}
		
		return output;
	}

	private ConsultaPlanogramaPorReferenciaResponseType getPlanogramaWS(final Long codArt, final Long codCentro, final HttpSession session) throws Exception {
		ReferenciasCentro vReferenciasCentro = new ReferenciasCentro();
		vReferenciasCentro.setCodCentro(codCentro);
		vReferenciasCentro.setCodArt(codArt);

		return this.planogramaCentroService.findPlanogramasCentroWS(vReferenciasCentro,session);

	}

	private String getCapacidad1(PlanogramaVigente planogramaVigente) {
		String output = null;
		Double capacidad = null;
		if (planogramaVigente.getFechaGenMontaje1()!= null && planogramaVigente.getCapacidadMontaje1()!= null){
			capacidad = planogramaVigente.getCapacidadMontaje1().doubleValue();			
		}
		else 
			if (planogramaVigente.getFechaGenCabecera()!= null && planogramaVigente.getCapacidadMaxCabecera()!= null){
				capacidad = planogramaVigente.getCapacidadMaxCabecera().doubleValue();
			}
		
		if (capacidad != null){
			output = Utilidades.convertirDoubleAString(capacidad,"###0");			
		}
		return output;
	}
	
	private String getFacing1(PlanogramaVigente planogramaVigente) {
		String output = null;
		Double facing = null;
		if (planogramaVigente.getFechaGenMontaje1()!= null && planogramaVigente.getFacingMontaje1()!= null){
			facing = planogramaVigente.getFacingMontaje1().doubleValue();			
		}
		else 
			if (planogramaVigente.getFechaGenCabecera()!= null && planogramaVigente.getStockMinComerCabecera()!= null){
				facing = planogramaVigente.getStockMinComerCabecera().doubleValue();
			}
		
		if (facing != null){
			output = Utilidades.convertirDoubleAString(facing,"###0");			
		}
		return output;
	}
	
	private PlanogramaVigente getPlanogramaVigente(Long codArt, Long codCentro) throws Exception {

		final PlanogramaVigente input = new PlanogramaVigente();
		input.setCodArt(codArt);
		input.setCodCentro(codCentro);
		return this.planogramaVigenteService.findOne(input);
		
	}

	private String getPedidoActivo(VSurtidoTienda vSurtidoTienda) throws Exception {

		final String pedir = vSurtidoTienda.getPedir();
		final String mapaHoy = vSurtidoTienda.getMapaHoy();
		final Long numPedidosOtroDia = vSurtidoTienda.getNumeroPedidosOtroDia();
		
		String output = "N";
		if (pedir != null  && pedir.equals("S")){
			if (mapaHoy != null && mapaHoy.equals("N")){
					if (numPedidosOtroDia== 0){
						//No hay pedido ningún día
						output = "N";
					}else{
						//Hoy no hay pedido pero si algún otro día
						output = "S";
					}
			}else{
				output = "S";
			}
		}

		return output;
	}

	/**
	 * Comprueba en el tabla CATI_CARGA_FOTOS_REF si existe algun 
	 * registro para el codigo de articulo provisto
	 * @param codArt
	 * @return S existe algun registro. N en caso contrario
	 * @throws Exception
	 */
	private String getTieneFoto(Long codArt) throws Exception{
		//Miramos si existe la foto. La búsqueda se hace con código eroski.
		FotosReferencia fotosReferencia = new FotosReferencia();
		fotosReferencia.setCodReferencia(codArt);
		
		Long counter = fotosReferenciaDao.checkImage(fotosReferencia);  
		
		return null != counter && counter.longValue() > 0 ? "S" : "N";
		
	}
	
	/**
	 * Obtiene el tipo de rotacion
	 * @param codArt
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
	private String getTipoRotacion(Long codArt, Long codCentro) throws Exception{

		String tipoRotacion = "";

		//Carga de parámetros de búsqueda
		VRotacionRef vRotacionRef = new VRotacionRef();
		vRotacionRef.setCodCentro(codCentro);
		vRotacionRef.setCodArt(codArt);

		VRotacionRef vRotacionRefRes = this.vRotacionRefDao.findOne(vRotacionRef);
		if (vRotacionRefRes != null){
			tipoRotacion = vRotacionRefRes.getTipoRotTotal();		
		}

		return tipoRotacion;
	}

	/**
	 * Invoca 2 veces al WS de STOCK con diferentes parametros.
	 * La primera para obtener el valor del stock (tipoMensaje=CB).
	 * La segunda para comprobar si hay errores y debemos de mostrar mensaje ALBARAN SIN ACT (tipoMensaje="CC")
	 * Devuelve objeto con el resultado.
	 * @param codArt
	 * @param codCentro
	 * @param area Area de la estructura comercial a la que pertenece la referencia. Utilizado para ver si se trata de un referencia del area textil=3
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private PdaCapturaRestosStock getValoresStock(Long codArt, Long codCentro, Long area, final HttpSession session) throws Exception{
						
		PdaCapturaRestosStock output = new PdaCapturaRestosStock();
		
		ConsultarStockResponseType stockResponse = getConsultaStock(codArt, codCentro, Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA, area, session);

		//Miramos que al menos una referencia sea correcta.
		boolean alMenosUnaReferenciaCorrecta = false;
		
		//Obtener stock
		if (null != stockResponse && stockResponse.getCodigoRespuesta().equals("OK")){
				
			for (ReferenciaType referencia : stockResponse.getListaReferencias()){
				
				// Para referencias TEXTIL insertamos el session la consultaStock porque se utiliza en la pantalla de edicion (PdaP28StockInicioController)
				if (null!=area && new Long(3).compareTo(area)==0){
					//Miramos que al menos una referencia sea correcta.
					if(!(new BigInteger("1")).equals(referencia.getCodigoError())){
						alMenosUnaReferenciaCorrecta = true;
					}
				}
				
				
				//Para el codArt enviado
				if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(codArt))){
					//Si no hay codigo de error
					if (referencia.getCodigoError().equals(new BigInteger("0"))){
						//seteo valores
						if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
							output.setStock(referencia.getBandejas().doubleValue());
						} else {
							output.setStock(referencia.getStock().doubleValue());
						}
						
						//seteo el calculo CC
						if(new Long(3).compareTo(area)!=0 && (stockResponse == null || stockResponse.getTipoMensaje() == null || 						
								!stockResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE))){
							output.setCalculoCC("S");
						}else{
							output.setCalculoCC("N");
						}
					} 
				}
				
			}//for
			
		}
		
		
		// Si se trata de una referecia TEXTIL y existe al menos una ref
		// correcta insertamos en session la consulta para que los datos del WS
		// sean consumidos en la pantalla de edicion de
		// STOCK(PdaP28StockInicioController)
		if (null != stockResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockResponse.getCodigoRespuesta())
				&& alMenosUnaReferenciaCorrecta) {
			session.setAttribute("consultaStock", stockResponse);
		} else {
			session.removeAttribute("consultaStock");
		}
		
		//Comprobar mensaje error albaran
		ConsultarStockResponseType msgAlbaranResponse = getConsultaStock(codArt, codCentro, Constantes.STOCK_TIENDA_CONSULTA_CORRECION, area, session);
		
		if (null != msgAlbaranResponse && msgAlbaranResponse.getCodigoRespuesta().equals("OK")){
			for (ReferenciaType referencia : msgAlbaranResponse.getListaReferencias()) {
				// Para el codArt enviado
				if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(codArt))) {
					
					/*PARA PRUEBAS DEL TEXTO ALBARAN (solo hay datos en Prod)*/
//					referencia.setCodigoError(new BigInteger("2"));
//					referencia.setMensajeError("ALBARAN X SIN ACT.");
					
					// Si hay codigo de error 2
					if (referencia.getCodigoError().equals(new BigInteger("2"))) {
						// seteo valores
						output.setMsgError(referencia.getMensajeError());
					}
				}

			}			
		}
		
		return output;
		
	}
	
	/**
	 * Consulta el stock en el WS e inserta en session la consulta si el area del articulo es textil (3).
	 * 
	 * @param codArt
	 * @param codCentro
	 * @param stockTiendaConsultaBasicaPistola
	 * @param area
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private ConsultarStockResponseType getConsultaStock(Long codArt, Long codCentro,
			String stockTiendaConsultaBasicaPistola, Long area, HttpSession session) throws Exception {
		
		ConsultarStockRequestType stockRequest = new ConsultarStockRequestType();
		stockRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
		stockRequest.setTipoMensaje(stockTiendaConsultaBasicaPistola);
		List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
		listaReferencias.add(BigInteger.valueOf(codArt));

		//Para referencias textil consultamos el WS con todas las referencias del proveedor
		if (Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA.equals(stockTiendaConsultaBasicaPistola) && null!=area && new Long(3).compareTo(area)==0){
			List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(codArt);
			
			for (Long articulo : referenciasMismoModeloProveedor) {
				if (!listaReferencias.contains(BigInteger.valueOf(articulo))) {
					listaReferencias.add(BigInteger.valueOf(articulo));
				}
			}			
		}

		stockRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
		
		//Consulta al WS
		ConsultarStockResponseType stockResponse = this.correccionStockDao.consultaStock(stockRequest, session);
		
		return stockResponse;
	}

	private String getCalculoCC(final Long codArt, final Long codCentro, final VDatosDiarioArt vDatosDiarioArt, final HttpSession session) throws Exception{
		
		String output = null;
		ConsultarStockResponseType stockTiendaResponse = getConsultaStock(codArt, codCentro, Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA, null, session);

		if(vDatosDiarioArt.getGrupo1() != 3 && (stockTiendaResponse == null || stockTiendaResponse.getTipoMensaje() == null || 						
				!stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE))){
			output = "S";
		}else{
			output = "N";
		}
		return output;
	}
	
	private String getMMC(VSurtidoTienda input){
		String output = null;
		if (input != null && input.getMarcaMaestroCentro()!= null && !"".equals(input.getMarcaMaestroCentro())){
			output = input.getMarcaMaestroCentro();
		}else{
			output = "N";
		}
		return output;
	}
	
	/**
	 * Obtiene los datos de la tabla V_SURTIDO_TIENDA para recoger los parametros
	 * de unidades caja y tipo aprovisionamiento de la captura restos.
	 * 
	 * @param codArt
	 * @param centro
	 * @param isTratamientoVegalsa
	 * @return null si no encuentra ningun registro
	 * @throws Exception
	 */
	public VSurtidoTienda getSurtidoTienda (Long codArt, Centro centro, Boolean isTratamientoVegalsa) throws Exception{
		try {
			
			if (null==isTratamientoVegalsa) {
				isTratamientoVegalsa=utilidadesVegalsaService.esTratamientoVegalsa(centro, codArt);
			}
			
			if (null != isTratamientoVegalsa && isTratamientoVegalsa){
				return this.vSurtidoTiendaService.findOneVegalsa(new VSurtidoTienda(centro.getCodCentro(), codArt));
			} else {
				return this.vSurtidoTiendaService.findOne(new VSurtidoTienda(centro.getCodCentro(), codArt));
			}
		} catch (EmptyResultDataAccessException e){
			return null;
		}
		
	}
	
	/**
	 * Obtiene los datos pertenecientes a la oferta
	 * @param codCentro
	 * @param codArt
	 * @return
	 * @throws Exception
	 */
	private PdaCapturaRestosOferta getOferta(Long codCentro, Long codArt) throws Exception{
		
		//Obtenemos la ofertaPVP
		OfertaPVP ofertaPVP = null;					
		ofertaPVP = new OfertaPVP();
		ofertaPVP.setCodArticulo(codArt);
		ofertaPVP.setCentro(codCentro);
		ofertaPVP.setFecha(new Date());
		ofertaPVP = this.kosmosService.obtenerDatosPVP(ofertaPVP);

		if (null!=ofertaPVP && ofertaPVP.getTarifa()==null){
			ofertaPVP=this.ofertaVigenteService.recuperarAnnoOferta(ofertaPVP);
		}
		
		
		//Si existe oferta, buscar gondola para comprobar si mostrar Cabecera X
		if (null != ofertaPVP && null != ofertaPVP.getCodOferta()){
			Boolean mostrarCabeceraX = capturaRestoDao.countGondolaG(codCentro, codArt, ofertaPVP.getAnnoOferta(), ofertaPVP.getCodOferta()) > 0;
			return new PdaCapturaRestosOferta(ofertaPVP.getCodOferta(), ofertaPVP.getAnnoOferta(), mostrarCabeceraX);
		}

		return null;
	}
	
	
	private PdaCapturaResto getCapturaRestoByCodArt(Long codCentro, Long codArt){
		return capturaRestoDao.getCapturaRestosByCodArt(codCentro, codArt);
	}
	
	private PdaCapturaResto getCapturaRestosByPage(Long codCentro, Long page){
		return capturaRestoDao.getCapturaRestosByPage(codCentro, page);
	}
	
	private Boolean existsCapturaResto(Long codCentro, Long codArt){
		return capturaRestoDao.countCapturasRestos(codCentro, codArt) > 0 ? Boolean.TRUE : Boolean.FALSE;
	}	
	
	/**
	 * Inserta un registro en la tabla T_MIS_CAPTURA_RESTOS obteniendo los datos de 
	 * distintos origenes.
	 * 
	 * @param codCentro
	 * @param codArt
	 * @throws Exception
	 */
	private void insertCapturaRestos(final Long codCentro, final Long codArt, final HttpSession session) throws Exception{
		User user = (User)session.getAttribute("user");
		
		//Obtener valores para el nuevo capturaResto
		PdaCapturaResto capturaResto = new PdaCapturaResto();
		capturaResto.setCodCentro(codCentro);
		capturaResto.setCodArt(codArt);
		
		//Stock--> Los valores de STOCK se obtienen el la consulta y se realiza una update posterior para guardarlos
		capturaResto.setFlgModifStock("N");

		//Obtener tipoRotacion
		capturaResto.setRotacion(this.getTipoRotacion(codArt, codCentro));

		List<Long> vRelacionArticuloLista = this.vDatosDiarioArtDao.findRefMismaRefMadre(codCentro, codArt);
		//empuje
		capturaResto.setEmpuje(this.capturaRestoDao.getEmpuje(codCentro, codArt, vRelacionArticuloLista));
		
		//codPlat
		capturaResto.setCodPlat(this.capturaRestoDao.getCodPlataforma(codCentro, codArt, vRelacionArticuloLista));
		
		//metodo calculo imc
		ImagenComercial imc = imagenComercialDao.consultaImc(codCentro, codArt);
		//tipo referencia
		capturaResto.setTipoReferencia(imc.getTipoReferencia());
		capturaResto.setMetodoCalculoIMC(imc.getMetodo());
		capturaResto.setFlgModifFacing("N");
		

		//Obtener U/C y tipoAprov
		VSurtidoTienda vSurtidoTienda = this.getSurtidoTienda(codArt, user.getCentro(), null);
		capturaResto.setUnidadesCaja(vSurtidoTienda.getUniCajaServ());
		capturaResto.setTipoAprov(vSurtidoTienda.getTipoAprov());
		
		capturaRestoDao.insertCapturaResto(capturaResto);
	}

	@Override
	public void updateFlgCapacidadIncorrecta(Long codCentro, Long codArt, String flgCapacidadIncorrecta)
			throws Exception {
		capturaRestoDao.updateFlagCapacidadIncorrecta(codCentro, codArt, flgCapacidadIncorrecta);
		
	}

}
