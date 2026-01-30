package es.eroski.misumi.control;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.Articulo;
import es.eroski.misumi.model.CapraboMotivoNoPedible;
import es.eroski.misumi.model.CapraboMotivoNoPedibleArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleCentroArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleMotivo;
import es.eroski.misumi.model.CcRefCentro;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.CentroAutoservicio;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.HistoricoUnidadesVenta;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.Pedido;
import es.eroski.misumi.model.PedidoBasicInfo;
import es.eroski.misumi.model.PedidoPda;
import es.eroski.misumi.model.PendientesRecibir;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.model.RefEnDepositoBrita;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VDatosDiarioCap;
import es.eroski.misumi.model.VFacingX;
import es.eroski.misumi.model.VMapaAprov;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.ValoresStock;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaResponseType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ReferenciaResponseType;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosPopupImpresora;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.model.pda.PdaSeguimientoPedidos;
import es.eroski.misumi.model.pda.PdaUltimosEnvios;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ventasTiendaWS.ReferenciaRespuetaType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType;
import es.eroski.misumi.service.iface.CapraboMotivosNoPedibleService;
import es.eroski.misumi.service.iface.CcRefCentroService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.HistoricoUnidadesVentaService;
import es.eroski.misumi.service.iface.ImprimirEtiquetaService;
import es.eroski.misumi.service.iface.MotivoTengoMuchoPocoService;
import es.eroski.misumi.service.iface.NumeroEtiquetaImpresoraService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.ParamCentrosVpService;
import es.eroski.misumi.service.iface.PedidoPdaService;
import es.eroski.misumi.service.iface.PedidosCentroService;
import es.eroski.misumi.service.iface.PendientesRecibirService;
import es.eroski.misumi.service.iface.PlanogramaCentroService;
import es.eroski.misumi.service.iface.RefAsociadasService;
import es.eroski.misumi.service.iface.RefEnDepositoBritaService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.USService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VAgruComerParamSfmcapService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VFacingXService;
import es.eroski.misumi.service.iface.VMapaAprovService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VRotacionRefService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.service.iface.VentasTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP13SegPedidosController extends pdaConsultasController {

	private static Logger logger = Logger.getLogger(pdaP13SegPedidosController.class);

	private final String PREHUECOS_LINEAL = "/pda/prehuecos/pda_p115_preHuecosLineal";

	@Value( "${tipoAprovisionamiento.descentralizado}" )
	private String tipoAprovisionamientoDescentralizado;
	
	@Autowired
	private PendientesRecibirService pendientesRecibirService;
	
	@Autowired
	private StockTiendaService correccionStockService;
	
	@Autowired
	private PedidosCentroService pedidosCentroService;
	
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private RefAsociadasService refAsociadasService;

	@Autowired
	private VentasTiendaService ventasTiendaService;

	@Autowired
	private HistoricoUnidadesVentaService historicoUnidadesVentaService;

	@Autowired
	private VAgruComerParamSfmcapService vAgruComerParamSfmcapService;

	@Autowired
	private ParamCentrosVpService paramCentrosVpService;

	@Autowired
	private VMapaAprovService vMapaAprovService;

	@Autowired
	private MotivoTengoMuchoPocoService motivoTengoMuchoPocoService;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@Autowired
	private PlanogramaCentroService planogramaCentroService;

	@Autowired
	private ImprimirEtiquetaService imprimirEtiquetaService;

	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;

	@Autowired
	private RefEnDepositoBritaService refEnDepositoBritaService;

	@Autowired
	private CcRefCentroService ccRefCentroService;

	@Autowired
	private VRotacionRefService vRotacionRefService;

	@Autowired
	private VFacingXService vFacingXService;

	@Autowired
	private NumeroEtiquetaImpresoraService numeroEtiquetaImpresoraService;

	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;

	@Resource
	private MessageSource messageSource;
	
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	
	
	//Petición 55700
	@Autowired
	private PedidoPdaService pedidoPdaService; 
	
	@Autowired
	private FotosReferenciaService fotosReferenciaService; 

	@Autowired
	private USService ussService;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;
	
	@RequestMapping(value = "/pdaP13FFPPActivo", method = RequestMethod.GET)
	public String ffppActivo(ModelMap model,
							@Valid final Long codArt,
							@Valid final Long codArtRel,
							@Valid final String mostrarFFPP,
							@RequestParam(value="procede", required=false, defaultValue = "") String procede,
							HttpSession session,
							HttpServletResponse response) {

		PdaSeguimientoPedidos pdaSegPedidos = new PdaSeguimientoPedidos();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		String resultado = "pda_p13_segPedidos";
		PedidoPda pedidoPda = new PedidoPda();
		PdaError pdaError = new PdaError();

		try {
			// Obtenemos de sesión la información de usuario.
			User user = (User) session.getAttribute("user");
			Locale locale = LocaleContextHolder.getLocale();

			// Controlamos que me llega la referencia
			if (codArtRel != null && !codArtRel.equals("")) {
				pdaSegPedidos = obtenerResultado(codArtRel, user.getCentro().getCodCentro(), mostrarFFPP, codArt,session);

				// Si se ha producido un error con la referencia lo mostramos.
				if (pdaSegPedidos.getEsError() != null && !pdaSegPedidos.getEsError().equals("")) {
					pdaError.setDescError(pdaSegPedidos.getEsError());
					model.addAttribute("pdaError", pdaError);
					// model.addAttribute("pdaSegPedidos", pdaSegPedidos);
					pdaDatosCab.setCodArtCab(String.valueOf(pdaSegPedidos.getCodArt()));
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}

				pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(pdaSegPedidos.getCodArt(),user.getCentro().getCodCentro(),user.getMac()));
				pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),user.getMac()));
				if(codArt != null){
					pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(pdaSegPedidos.getCodArt().toString(),user.getCentro().getCodCentro(),user.getMac()));
				}else{
					pdaDatosCab.setImpresoraActiva("NO");
				}
				
				//La descripción será la del centro eroski y el código artículo caprabo será el de eroski
				pdaDatosCab.setDescArtCab(pdaSegPedidos.getDescArt());
				pdaDatosCab.setCodArtCaprabo(String.valueOf(pdaSegPedidos.getCodArt()));
				
				pedidoPda = pedidoPdaService.lanzarEncargoPda3(session, pdaSegPedidos.getCodArt().toString(),pdaDatosCab.getCodArtCaprabo(), pdaDatosCab.getDescArtCab(),null,response);
			
				//Miramos si existe la foto. La búsqueda se hace con código eroski.
				FotosReferencia fotosReferencia = new FotosReferencia();
				fotosReferencia.setCodReferencia(pdaSegPedidos.getCodArt());
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					pdaSegPedidos.setTieneFoto("S");
				} else {
					pdaSegPedidos.setTieneFoto("N");
				}	
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		model.addAttribute("pdaSegPedidos", pdaSegPedidos);
		pdaDatosCab.setCodArtCab(String.valueOf(pdaSegPedidos.getCodArt()));
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("procede",procede);

		session.setAttribute("pedidoPda", pedidoPda);

		return resultado;
	}

	@RequestMapping(value = "/pdaP13SegPedidos", method = RequestMethod.GET)
	public String showForm(ModelMap model,
							@Valid final Long codArt,
							@Valid final String guardadoStockOk,
							final Long codArtRel, final String mostrarFFPP,
			@RequestParam(value = "origenGISAE", required = false, defaultValue = "NO") String origenGISAE,
			@RequestParam(value = "impresoraWS", required = false, defaultValue = "NO") String impresoraWS,
			@RequestParam(value="etiqueta", required=false, defaultValue = "false") boolean etiqueta,
			@RequestParam(value="greenImpr", required=false, defaultValue = "NO") String greenImpr,
			@RequestParam(value="origen", required=false, defaultValue = "") String origen,
			@RequestParam(value="procede", required=false, defaultValue = "") String procede,
			@RequestParam(value="sufijoPrehueco", required=false) String sufijoPrehueco,
			HttpSession session, HttpServletRequest request,HttpServletResponse response) {

		// Obtenemos de sesión la información de usuario.
		User user = (User) session.getAttribute("user");
		String resultado = "pda_p13_segPedidos";
		PdaSeguimientoPedidos pdaSegPedidos = new PdaSeguimientoPedidos();
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		PedidoPda pedidoPda= new PedidoPda();

		// Si se pasa el sufijo con valor, por ejemplo "_PREHUECO", se concatena a la mac para acceder a las tablas de BBDD. (T_MIS_IMPRESION_ETIQUETAS)
		String mac = (sufijoPrehueco!=null && !StringUtils.isEmpty(sufijoPrehueco)?user.getMac()+sufijoPrehueco:user.getMac());

		if (origenGISAE.equals("SI")) {
			pdaDatosCab.setOrigenGISAE(origenGISAE);
		}
		
		model.addAttribute("origenGISAE", origenGISAE);

		try {

			//Peticion 53662. Etiquetas número de veces.
			if (etiqueta){
				//Obtenemos el número de etiqueta del codArt
				Integer numeroEtiqueta = numeroEtiquetaImpresoraService.getNumEtiqueta(codArt,user.getCentro().getCodCentro(),mac);
				//Incrementamos el número de etiqueta del codArt
				numeroEtiquetaImpresoraService.incNumEtiqueta(codArt.toString(), numeroEtiqueta.intValue(),user.getCentro().getCodCentro(),mac);
			}
			pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(codArt,user.getCentro().getCodCentro(),mac));
			pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),mac));
			if(codArt != null){
				pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(codArt.toString(),user.getCentro().getCodCentro(),mac));
			}else{
				pdaDatosCab.setImpresoraActiva("NO");
			}
			
			if (impresoraWS.equals("SI")){

				// LLamar al WS de imprimir etiquetas
				ImprimirEtiquetaRequestType imprimirEtiquetarequest = imprimirEtiquetaService.createImprimirEtiquetaRequestType();

				try {
					ImprimirEtiquetaResponseType imprimirEtiquetaResponse = imprimirEtiquetaService.imprimirEtiquetaWS(imprimirEtiquetarequest);

					if (imprimirEtiquetaService.isOkImprimirEtiquetaResponse(imprimirEtiquetaResponse)) {  // El codigo de respuesta del WS ha sido OK.

						for (int i = 0; i < imprimirEtiquetaResponse.getReferencias().length; i++){ //Hacemos un bucle porque devuelve una lista pero en
							//este caso siempre devuelve una sola referencia
							ReferenciaResponseType refResponse = imprimirEtiquetaResponse.getReferencias()[i];
							BigInteger codigoErrorRef = refResponse.getCodigoError();
							String mensajeErrorRef = refResponse.getMensajeError();

							if (codigoErrorRef.equals(BigInteger.valueOf(0))) {
								//El WS ha devuelto OK para la referencia consultada. 
								//Ponemos el icono de la impresora a verde.
								numeroEtiquetaImpresoraService.siNumEtiquetaInSessionEnviados();
								pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getNumEtiquetaInSessionEnviados());
								model.addAttribute("codigoArticulo", codArt);
								resultado = "pda_p03_enviarMapNumEti";
							} else {
								// El WS no ha devuelto OK para la referecnia
								// consultada. Abrimos una ventana con el
								// mensaje de
								// error del WS.
								resultado = "pda_p18_impresoraPopup";

								PdaDatosPopupImpresora pdaDatosPopupImpresora = new PdaDatosPopupImpresora();
								pdaDatosPopupImpresora.setCodArt(codArt);
								pdaDatosPopupImpresora.setProcede("datosRef");
								pdaDatosPopupImpresora.setMensajeErrorWS(mensajeErrorRef);

								model.addAttribute("pdaDatosPopupImpresora", pdaDatosPopupImpresora);
							}

						}
					}else{
						//El WS no ha devuelto OK. Abrimos una ventana con el mensaje de error del WS.
						resultado = "pda_p18_impresoraPopup";

						PdaDatosPopupImpresora pdaDatosPopupImpresora = new PdaDatosPopupImpresora();
						pdaDatosPopupImpresora.setCodArt(codArt);
						pdaDatosPopupImpresora.setProcede("datosRef");
						if (imprimirEtiquetaResponse != null && StringUtils.isNotBlank(imprimirEtiquetaResponse.getDescripcionRespuesta())){
							pdaDatosPopupImpresora.setMensajeErrorWS(imprimirEtiquetaResponse.getDescripcionRespuesta());
						}else{
							pdaDatosPopupImpresora.setMensajeErrorWS(messageSource.getMessage("pda_p12_datosReferencia.errorWSImpresion", null, LocaleContextHolder.getLocale()));
						}

						model.addAttribute("pdaDatosPopupImpresora", pdaDatosPopupImpresora);
					}

				} catch (Exception e) {
					resultado = "pda_p18_impresoraPopup";

					PdaDatosPopupImpresora pdaDatosPopupImpresora = new PdaDatosPopupImpresora();
					pdaDatosPopupImpresora.setCodArt(codArt);
					pdaDatosPopupImpresora.setProcede("datosRef");
					pdaDatosPopupImpresora.setMensajeErrorWS(messageSource.getMessage(
							"pda_p12_datosReferencia.errorWSImpresion", null, LocaleContextHolder.getLocale()));

					model.addAttribute("pdaDatosPopupImpresora", pdaDatosPopupImpresora);
				}

			} else {
				pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getNumEtiquetaInSessionEnviados());
			}


			// Control de guardado de stock
			model.addAttribute("guardadoStockOk", guardadoStockOk);

			// Controlamos que me llega la referencia
			if (codArt != null) {
				pdaSegPedidos = obtenerResultado(codArt, user.getCentro().getCodCentro(), mostrarFFPP, codArtRel,session);

				// Si se ha producido un error con la referencia lo mostramos.
				if (pdaSegPedidos.getEsError() != null && !pdaSegPedidos.getEsError().equals("")) {
					pdaError.setDescError(pdaSegPedidos.getEsError());
					model.addAttribute("pdaError", pdaError);
					// model.addAttribute("pdaSegPedidos", pdaSegPedidos);
					pdaDatosCab.setCodArtCab(String.valueOf(pdaSegPedidos.getCodArt()));
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}

				//else if(!user.getCentro().isEsCentroCapraboEspecial() && !user.getCentro().getEsCentroCaprabo()){
					//La descripción será la del centro eroski y el código artículo caprabo será el de eroski
					pdaDatosCab.setDescArtCab(pdaSegPedidos.getDescArt());
					pdaDatosCab.setCodArtCaprabo(String.valueOf(pdaSegPedidos.getCodArt()));
				//}

				// Si NO se accede desde Prehuecos.
				if (procede.isEmpty() || procede != "pdaP115PrehuecosLineal.do"){
					//Se calcula si se tiene que enseñar el link de lanzar encargo. En el caso de tener que enseñarlo
					//al pincharlo aparecerá un formulario a rellenar.			
					pedidoPda = pedidoPdaService.lanzarEncargoPda3(session, codArt.toString(),pdaDatosCab.getCodArtCaprabo(), pdaDatosCab.getDescArtCab(),null,response);							
				}
			
				//Miramos si existe la foto. La búsqueda se hace con código eroski.
				FotosReferencia fotosReferencia = new FotosReferencia();
				fotosReferencia.setCodReferencia(pdaSegPedidos.getCodArt());
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					pdaSegPedidos.setTieneFoto("S");
				} else {
					pdaSegPedidos.setTieneFoto("N");
				}
			} else {
				// Si no me llega la referencia mostramos el mensaje de que no
				// ha introducido ninguna referencia.
				pdaError.setDescError(
						this.messageSource.getMessage("pda_p12_datosReferencia.referenciaVacia", null, locale));
				// model.addAttribute("pdaSegPedidos", pdaSegPedidos);
				// pdaDatosCab.setCodArtCab(pdaSegPedidos.getCodArt());
				// pdaDatosCab.setDescArtCab(pdaSegPedidos.getDescArt());
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaError", pdaError);
				logger.info("PDA Referencia no introducida");
				return "pda_p03_showMessage";
			}

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		model.addAttribute("pdaSegPedidos", pdaSegPedidos);
		pdaDatosCab.setCodArtCab(String.valueOf(pdaSegPedidos.getCodArt()));
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		//model.addAttribute("pedidoPda", pedidoPda);
		
		//Guardamos el estado de la impresora verde fija si es 'SI'.
		model.addAttribute("greenImpr", greenImpr);
		model.addAttribute("procede",procede);

		if (Constantes.MENU_PDA_PREHUECOS.equals(origen)){
			model.addAttribute("origen", Constantes.MENU_PDA_PREHUECOS);
		}else{
			model.addAttribute("origen", Constantes.MENU_PDA_PREHUECOSPED);
		}

		session.setAttribute("pedidoPda", pedidoPda);
		
		return resultado;
	}

	private PdaSeguimientoPedidos obtenerResultado(Long codArt, Long codCentro, String mostradoFFPP, Long codArtRel,
			HttpSession session) throws Exception {

		Locale locale = LocaleContextHolder.getLocale();

		PdaSeguimientoPedidos pdaSegPedidos = new PdaSeguimientoPedidos();
		pdaSegPedidos.setCodArt(codArt);
		pdaSegPedidos.setCodArtRel(codArtRel);

		VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);

		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");

		ReferenciasCentro referenciasCentro = new ReferenciasCentro();
		referenciasCentro.setCodArt(codArt);
		
		//Para buscar en t_mismcg_caprabo es necesaria la referencia del artículo caprabizada. Como en 
		//esta función siempre entran en modo Eroski, es necesario caprabizar la referencia.
		boolean isCaprabo = false;
		
		referenciasCentro.setCodCentro(codCentro);
		referenciasCentro.setDiarioArt(obtenerDiarioArt(referenciasCentro));

		boolean isCapraboEspecial = false;
		
		// Miramos si tiene tratamiento Vegalsa (MISUMI-357)
		boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), codArt);
		referenciasCentro.setEsTratamientoVegalsa(tratamientoVegalsa);
		pdaSegPedidos.setTratamientoVegalsa(tratamientoVegalsa);
		
		VSurtidoTienda surtidoTienda = obtenerSurtidoTienda(referenciasCentro,isCaprabo,isCapraboEspecial);

		// Controlamos si se ha introducido una referencia que no existe.
		if (vDatosDiarioArtResul == null) {
			pdaSegPedidos.setEsError(
					this.messageSource.getMessage("pda_p12_datosReferencia.noExisteReferencia", null, locale));
			return pdaSegPedidos;
		}

		// Obtenemos la descripción del artículo.
		if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null) {
			pdaSegPedidos.setDescArt(vDatosDiarioArtResul.getDescripArt());
		}

		// Obtener MMC
		if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro() != null
				&& !"".equals(surtidoTienda.getMarcaMaestroCentro())) {
			pdaSegPedidos.setMMC(surtidoTienda.getMarcaMaestroCentro());
		} else {
			pdaSegPedidos.setMMC("N");
		}
		// Control para mostrar pedido activo o no.
		pdaSegPedidos.setPedidoActivo(obtenerPedidoActivo(surtidoTienda,isCaprabo));
		
		//Obtenemos pedir
		if (surtidoTienda != null && surtidoTienda.getPedir()!= null && !"".equals(surtidoTienda.getPedir())){
			pdaSegPedidos.setPedir(surtidoTienda.getPedir());
		}else{
			pdaSegPedidos.setPedir("N");
		}

		// Controlamos si tiene un artículo relacionado
		VRelacionArticulo vRelacionArticulo = obtenerRelacionArticulo(referenciasCentro);

		if (mostradoFFPP != null && mostradoFFPP.equals("S")) {
			// En este caso venimos de una referencia.
			pdaSegPedidos.setMostrarFFPP("N");
		} else if (mostradoFFPP != null && mostradoFFPP.equals("N")) {
			// En este caso vamos al artículo relacionado de una referencia.
			pdaSegPedidos.setMostrarFFPP("S");
		} else {
			// Controlamos si tiene un artículo unitario
			if (vRelacionArticulo != null && vRelacionArticulo.getCodArtRela() != null){
				if (tratamientoVegalsa){
					if (vRelacionArticulo.getFormatoProductivoActivo() != null && vRelacionArticulo.getFormatoProductivoActivo().equals("S")){
						pdaSegPedidos.setMostrarFFPP("S");
						pdaSegPedidos.setCodArtRel(vRelacionArticulo.getCodArtRela());
					}
				}else{
					pdaSegPedidos.setMostrarFFPP("S");
					pdaSegPedidos.setCodArtRel(vRelacionArticulo.getCodArtRela());
				}
			}else{ 
				//Comprobación de artículo unitario
				VRelacionArticulo relacionArticuloUnitario = obtenerRelacionArticuloUnitario(referenciasCentro);
				if (relacionArticuloUnitario != null && relacionArticuloUnitario.getCodArt() != null){
					if (tratamientoVegalsa){
						if (relacionArticuloUnitario.getFormatoProductivoActivo() != null && relacionArticuloUnitario.getFormatoProductivoActivo().equals("S")){
							pdaSegPedidos.setMostrarFFPP("N");
							pdaSegPedidos.setCodArtRel(relacionArticuloUnitario.getCodArt());
						}
					}else{
						pdaSegPedidos.setMostrarFFPP("N");
						pdaSegPedidos.setCodArtRel(relacionArticuloUnitario.getCodArt());
					}
				}
			}
		}

		// Obtenemos ultimos pedidos
		Centro centroArticuloPedido = new Centro();
		centroArticuloPedido.setCodCentro(codCentro);
		Articulo articuloPedido = new Articulo(centroArticuloPedido, codArt);

		Pedido pedido = this.pedidosCentroService.findAllSegPedidosPda(new Pedido(articuloPedido, null),
				vRelacionArticulo);
		if (pedido != null && pedido.getBasicInfo() != null && !pedido.getBasicInfo().isEmpty()) {
			List<PedidoBasicInfo> listaSeguimientosPedido = pedido.getBasicInfo();
			List<PdaUltimosEnvios> listaEnvios = new ArrayList<PdaUltimosEnvios>();
			int tamanoListaEnvios = listaSeguimientosPedido.size();

			if (tamanoListaEnvios > Constantes.FILAS_ULTIMOS_ENVIOS_PDA) {
				listaSeguimientosPedido = listaSeguimientosPedido.subList(0, Constantes.FILAS_ULTIMOS_ENVIOS_PDA);
			}
			
			for (PedidoBasicInfo misSeguimientos : listaSeguimientosPedido) {
				PdaUltimosEnvios ultimosEnvios = new PdaUltimosEnvios();
				String fecha = Utilidades.formatearFecha(Utilidades.formatearFecha(misSeguimientos.getFechaPed()),
						locale);
				ultimosEnvios.setFechaEnvio(fecha);
				
				if (misSeguimientos.getConfirmadas().equals("S") || misSeguimientos.getEmpuje().equals("S")
					|| misSeguimientos.getImpCab().equals("S")) {
					ultimosEnvios.setArtPed(new Long(1));
				}else if (misSeguimientos.getConfirmadas().equals("X") || misSeguimientos.getEmpuje().equals("X")
						|| misSeguimientos.getImpCab().equals("X")){
					ultimosEnvios.setArtPed(new Long(2));
				}else{
					ultimosEnvios.setArtPed(new Long(0));
				}
				
				if (misSeguimientos.getNsr().equals("S")) {
					ultimosEnvios.setArtNsr(new Long(1));
				}else{
					ultimosEnvios.setArtNsr(new Long(0));
				}
				
				if (ultimosEnvios.getArtPed() > 0) {
					StringBuffer tipoPedido = new StringBuffer();
					if (misSeguimientos.getConfirmadas().matches("X|S")) {
						tipoPedido.append(this.messageSource.getMessage("pda_p13_segPedidos.envioNormal", null, locale));
					}
					if (misSeguimientos.getImpCab().matches("X|S")) {
						if (!tipoPedido.toString().isEmpty()) {
							tipoPedido.append(",");
						}
						tipoPedido.append(this.messageSource.getMessage("pda_p13_segPedidos.implantacion", null, locale));
					}
					if (misSeguimientos.getEmpuje().matches("X|S")) {
						if (!tipoPedido.toString().isEmpty()) {
							tipoPedido.append(",");
						}
						tipoPedido.append(this.messageSource.getMessage("pda_p13_segPedidos.empuje", null, locale));
					}
					ultimosEnvios.setTipoPedido(tipoPedido.toString());
				}
				listaEnvios.add(ultimosEnvios);
			}
			pdaSegPedidos.setListaEnvios(listaEnvios);
		}

		// Obtener referencias promocionales
		RelacionArticulo relacionArticulo = new RelacionArticulo();
		relacionArticulo.setCodArt(codArt);
		relacionArticulo.setCodCentro(codCentro);
		List<Long> referencias = this.relacionArticuloService.findAll(relacionArticulo);

		// Obtenemos Pendientes 1/2
		PendientesRecibir pr = new PendientesRecibir();
		pr.setCodArt(codArt);
		pr.setCodCentro(codCentro);
		if (!referencias.isEmpty()) {
			pr.setReferencias(referencias);
		}
		pr = this.pendientesRecibirService.find(pr, null);
		pdaSegPedidos.setCantHoy(pr.getCantHoy());
		pdaSegPedidos.setCantFutura(pr.getCantFutura());

		ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
		stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
		List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
		listaReferencias.add(BigInteger.valueOf(codArt));

		// Si la referencia consultada es TEXTIL, se deben obtener todas las
		// referencia que compartan el mismo código de proveedor.
		// DESCOMENTARLO UNA VEZ ESTE HECHO EL NUEVO SERVICIO DE TEXTIL.
		// CORRESPONDE A LA PETICION 47885 (LA PARTE DE PISTOLA)
		// SE ACTIVA SOLO PARA LOS CENTROS: 5042-SEGOVIA, 0358-ABADIÑO,
		// 0175-IRUÑA, 5326-INFANTE, 410 EIBAR, 164 GERNIKA
		// Si la referencia consultada es TEXTIL, se deben obtener todas las
		// referencia que compartan el mismo código de proveedor.
		 // Es un artículo de
			// textil

		stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA); 
		
		// En este caso es una consulta basica, al WS se le pasara una lista de referencias
		List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(codArt);

		for (Long articulo : referenciasMismoModeloProveedor) {
			String strArticulo = articulo + "";
			if (!strArticulo.equals(codArt + "")) { // El articulo
					// consultado por el
					// usuario (el de la
					// cabecera) ya esta
					// metido en la lista
				listaReferencias.add(BigInteger.valueOf(articulo));
			}
		}

		stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
		try {
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
			
			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: pdaP13SegPedidosController (obtenerResultado)	 #################");
				logger.error("###########################################################################################################");
			}
			ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
			
			//Si la referencia no es madre y además no es textil, se indica que se calculará el stock mediante el WS de stock con el valor CC
			//al pinchar el link del stock.
			if (vDatosDiarioArtResul.getGrupo1() != 3 && (stockTiendaResponse == null || stockTiendaResponse.getTipoMensaje() == null || 						
					!stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE))){
				pdaSegPedidos.setCalculoCC("S");
			}else{
				pdaSegPedidos.setCalculoCC("N");
			}
			
			//Miramos que al menos una referencia sea correcta.
			boolean alMenosUnaReferenciaCorrecta = false;
			for (ReferenciaType referencia:stockTiendaResponse.getListaReferencias()){
				if (!(new BigInteger("1")).equals(referencia.getCodigoError())){
					alMenosUnaReferenciaCorrecta = true;
				}
			}
			
			if (null != stockTiendaResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())&& alMenosUnaReferenciaCorrecta) {
				session.setAttribute("consultaStock", stockTiendaResponse);
				pdaSegPedidos.setStockActivo("S");
			} else {
				session.removeAttribute("consultaStock");
				pdaSegPedidos.setStockActivo("N");
			}
			if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal()
					.equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)) {
				pdaSegPedidos.setStockActual(Utilidades.convertirDoubleAString(
						stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue(), "###0.#"));
			} else {
				pdaSegPedidos.setStockActual(Utilidades.convertirDoubleAString(
						stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue(), "###0.#"));
			}

			// Petición 48890. Calculo de los dias de stock. Los dias de stock
			// se calcularán como stock actual / venta media

			// Cargamos los datos de venta media.
			ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();

			referenciasCentroIC.setCodArt(codArt);
			referenciasCentroIC.setCodCentro(codCentro);

			HistoricoVentaMedia historicoVentaMedia = obtenerHistoricoVentaMedia(referenciasCentroIC);

			Float ventaMedia = new Float(0);
			if (historicoVentaMedia != null) {
				historicoVentaMedia.recalcularVentasMedia();
				ventaMedia = historicoVentaMedia.getMedia();
			}

			if ((ventaMedia != null && ventaMedia != 0.0) && (pdaSegPedidos.getStockActivo() != null)) {
				double resultado = new Double(
						stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue() / ventaMedia);
				pdaSegPedidos.setDiasStock("->" + Utilidades.convertirDoubleAString(resultado, "###0.#"));
			} else {
				pdaSegPedidos.setDiasStock("");
			}

		} catch (Exception e) {
			session.removeAttribute("consultaStock");
			pdaSegPedidos.setStockActual("Error");
			pdaSegPedidos.setStockActivo("N");
		}

		if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones())
				&& !user.getCentro().getOpcHabil().isEmpty()
				&& user.getCentro().getOpcHabil().toUpperCase().indexOf("PDAMUCHOPOCO") != -1) {
			RelacionArticulo relacionArticuloRela = new RelacionArticulo();
			relacionArticuloRela.setCodArtRela(referenciasCentro.getCodArt());
			relacionArticuloRela.setCodCentro(referenciasCentro.getCodCentro());
			List<Long> referenciasRela = this.relacionArticuloService.findAll(relacionArticuloRela);

			if ((pdaSegPedidos.getMostrarFFPP() == null || !pdaSegPedidos.getMostrarFFPP().equals("N"))
					&& !Constantes.ERROR_TENGO_MUCHO_POCO_PISTOLA.equals(pdaSegPedidos.getStockActual())
					&& referenciasRela.isEmpty()) {
				pdaSegPedidos.setValoresStock(this.obtenerValoresStock(referenciasCentro, pdaSegPedidos, session));
			}
		}

		// Petición 48890. Movimimiento continuo de gama. Se llama al ws de
		// PlanogramaCentro.
		ReferenciasCentro vReferenciasCentro = new ReferenciasCentro();
		vReferenciasCentro.setCodCentro(codCentro);
		vReferenciasCentro.setCodArt(codArt);

		//Obtener flag de deposito brita
		pdaSegPedidos.setFlgDepositoBrita(obtenerFlgDepositoBrita(vReferenciasCentro));

		//Obtener flag de referencia por catálogo
		pdaSegPedidos.setFlgPorCatalogo(obtenerFlgPorCatalogo(vReferenciasCentro));

		//Obtener el tipo de rotacion
		pdaSegPedidos.setTipoRotacion(this.obtenerTipoRotacion(pdaSegPedidos, codCentro));

		//Obtener el USS. Sólo para area 4 y 5
		pdaSegPedidos.setEsUSS(this.obtenerEsUSS(vDatosDiarioArtResul));
		
		ConsultaPlanogramaPorReferenciaResponseType result = this.planogramaCentroService
					.findPlanogramasCentroWS(vReferenciasCentro,session);

		if (result != null) {
			if (result.getCodigoRespuesta().equals("0")) {
				
				if (result.getPlanogramaReferencia(0).getSustituidaPor() != null) {
					pdaSegPedidos.setSustituidaPor(result.getPlanogramaReferencia(0).getSustituidaPor().longValue());
				}
				if (result.getPlanogramaReferencia(0).getSustitutaDe() != null) {
					pdaSegPedidos.setSustitutaDe(result.getPlanogramaReferencia(0).getSustitutaDe().longValue());
				}
				if (result.getPlanogramaReferencia(0).getImplantancion() != null) {
					pdaSegPedidos.setImplantacion(result.getPlanogramaReferencia(0).getImplantancion());
				}
				if (result.getPlanogramaReferencia(0).getFechaActivacion() != null) {
					pdaSegPedidos.setFechaActivacion(result.getPlanogramaReferencia(0).getFechaActivacion().getTime());
					// Obtenemos la fecha generacion de surtido tienda. Fecha en
					// la que la referencia pasa de PEDIR N a PEDIR S.
					Date FechaGenSurtidoTienda = vSurtidoTiendaService
								.obtenerFechaGeneracionSurtidoTienda(surtidoTienda);
					if (FechaGenSurtidoTienda != null) {

						pdaSegPedidos.setFechaGen(FechaGenSurtidoTienda);
						Format formatterDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy", locale);
						Integer intDiaSemana = Utilidades.getNumeroDiaSemana(FechaGenSurtidoTienda, locale);
						StringBuffer sb = new StringBuffer();
						if (intDiaSemana != null && 1 <= intDiaSemana.intValue() && intDiaSemana.intValue() <= 7) {
							StringBuffer sb1 = new StringBuffer("calendario.").append(intDiaSemana.intValue());
							String letra = messageSource.getMessage(sb1.toString(), null, locale);
							sb.append(letra);
							sb.append(" ");
						}
						sb.append(formatterDDMMYYYY.format(FechaGenSurtidoTienda));
						pdaSegPedidos.setStrFechaGen(sb.toString());

						//Begin: Petición 55001.
						//Comprobamos si la fecha de generación se debe mostrar o no

						//Fecha de generación de surtido tienda
						Calendar calFechaGenSurtidoTienda = Calendar.getInstance();
						calFechaGenSurtidoTienda .setTime(FechaGenSurtidoTienda);

						//Obtenemos la fecha actual
						Calendar calFechaActual = Calendar.getInstance();

						//Obtenemos  la diferencia entre las dos fechas 
						long millisDay = 24*60*60*1000;
						long diffDays = Math.round((calFechaActual.getTimeInMillis() - calFechaGenSurtidoTienda.getTimeInMillis())/(millisDay));

						if (diffDays <= 30) {//Si la diferencia es menor a 30 días se mostrará en pantalla
							pdaSegPedidos.setMostrarFechaGen(true);
						}else{
							pdaSegPedidos.setMostrarFechaGen(false);
						}
						//End: Petición 55001.
					}
				}

					// Comprobamos si el WS devuelve el campo SustituidaPor o
					// SustitutaDe o ninguno de los dos campos.
				if (pdaSegPedidos.getSustituidaPor() != null && pdaSegPedidos.getSustituidaPor() != 0) {
					pdaSegPedidos.setMostrarSustPorRef("S");
					pdaSegPedidos.setMostrarSustARef("N");
				} else if (pdaSegPedidos.getSustitutaDe() != null && pdaSegPedidos.getSustitutaDe() != 0) {
					pdaSegPedidos.setMostrarSustARef("S");
					pdaSegPedidos.setMostrarSustPorRef("N");
				} else {
					pdaSegPedidos.setMostrarSustARef("N");
					pdaSegPedidos.setMostrarSustPorRef("N");
				}

				// Miramos si el WS devuelve el campo implantación. Si devuleve
				// implantación habra que comprobar si este se pinta en rojo o
				// verde
				// en la pestaña Imagen comercial.
				if ((pdaSegPedidos.getImplantacion() != null) && !(pdaSegPedidos.getImplantacion().trim().equals(""))) {

					// comprobamos si el WS devuelve el campo Fecha Generacion.
					if (pdaSegPedidos.getFechaGen() != null) {
						pdaSegPedidos.setFlgColorImplantacion("VERDE");
					} else {
						// comprobamos si el STOCK es mayor a 0 o si es 0 pero
						// no ha pasado mas de un mes desde que se ha puesto a
						// cero. En ese caso lo pintamos a rojo
						referenciasCentro.setSurtidoTienda(surtidoTienda);
						if (vSurtidoTiendaService.comprobarStockMayorACero(referenciasCentro.getSurtidoTienda()) > 0) {
							pdaSegPedidos.setFlgColorImplantacion("ROJO");
						}
					}
				}
			}
		}

		return pdaSegPedidos;
	}

	private String obtenerTipoRotacion(PdaSeguimientoPedidos pdaSegPedidos, Long codCentro) throws Exception{

		String tipoRotacion = "";

		//Carga de parámetros de búsqueda
		VRotacionRef vRotacionRef = new VRotacionRef();
		vRotacionRef.setCodCentro(codCentro);
		vRotacionRef.setCodArt(pdaSegPedidos.getCodArt());

		VRotacionRef vRotacionRefRes = this.vRotacionRefService.findOne(vRotacionRef);
		if (vRotacionRefRes != null){
			tipoRotacion = vRotacionRefRes.getTipoRotTotal();		
		}

		return tipoRotacion;
	}
	
	private String obtenerEsUSS(VDatosDiarioArt vDatosDiarioArtResul) throws Exception{
		String esUSS = null;
		if(new Long(Constantes.AREA_BAZAR).equals(vDatosDiarioArtResul.getGrupo1()) || new Long(Constantes.AREA_ELECTRO).equals(vDatosDiarioArtResul.getGrupo1())){
			 esUSS = ussService.esUSS(vDatosDiarioArtResul.getCodArt(),vDatosDiarioArtResul.getGrupo1());
		}

		return esUSS;
	}

	private ValoresStock obtenerValoresStock(ReferenciasCentro referenciasCentro,
			PdaSeguimientoPedidos pdaSeguimientoPedidos, HttpSession session) throws Exception {

		ValoresStock valoresStock = new ValoresStock();
		// Calculo Ventas Medias
		ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();
		referenciasCentroIC.setCodArt(referenciasCentro.getCodArt());
		referenciasCentroIC.setCodCentro(referenciasCentro.getCodCentro());

		HistoricoVentaMedia historicoVentaMedia = this.obtenerHistoricoVentaMedia(referenciasCentroIC);

		Float ventaMedia = new Float(0);
		if (historicoVentaMedia != null) {
			historicoVentaMedia.recalcularVentasMedia();
			ventaMedia = historicoVentaMedia.getMedia();
		}

		// Calculo Venta Hoy
		Double ventaHoy = new Double(0);
		try {
			VentasTiendaRequestType ventasTiendaRequest = new VentasTiendaRequestType();
			ventasTiendaRequest.setCodigoCentro(BigInteger.valueOf(referenciasCentro.getCodCentro()));
			Calendar cal = Calendar.getInstance();
			TimeZone tz = TimeZone.getTimeZone("UTC");
			cal.setTimeZone(tz);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			ventasTiendaRequest.setFechaDesde(cal);
			ventasTiendaRequest.setFechaHasta(cal);

			// Obtención de lista de referencias relacionadas
			RefAsociadas refAsociadas = new RefAsociadas();
			refAsociadas.setCodArticulo(referenciasCentro.getCodArt());
			List<RefAsociadas> listaRefereciasAsociadas = this.refAsociadasService.findAll(refAsociadas);

			BigInteger[] listaReferencias = { BigInteger.valueOf(referenciasCentro.getCodArt()) };
			Hashtable<Long, Long> hshRefAsociadasCantidad = new Hashtable<Long, Long>();
			if (listaRefereciasAsociadas != null && listaRefereciasAsociadas.size() > 0) {
				listaReferencias = new BigInteger[listaRefereciasAsociadas.size()];
				int indiceArray = 0;

				for (RefAsociadas refAsociada : listaRefereciasAsociadas) {
					listaReferencias[indiceArray] = new BigInteger(refAsociada.getCodArticuloHijo().toString());
					hshRefAsociadasCantidad.put(refAsociada.getCodArticuloHijo(), refAsociada.getCantidad());
					indiceArray++;
				}
			} else {
				hshRefAsociadasCantidad.put(referenciasCentro.getCodArt(), new Long(1));
			}

			ventasTiendaRequest.setListaReferencias(listaReferencias);
			VentasTiendaResponseType ventasTiendaResponse = this.ventasTiendaService
					.consultaVentas(ventasTiendaRequest,session);
			if (ventasTiendaResponse.getListaReferencias().length > 0) {
				BigDecimal ventaHoyBigDecTotal = new BigDecimal(0);
				for (ReferenciaRespuetaType referencia : ventasTiendaResponse.getListaReferencias()) {
					// Si da error en la consulta de la referencia se ignora
					// salvo si se trata de la referencia padre
					if (new BigInteger("0").equals(referencia.getCodigoError())
							|| referenciasCentro.getCodArt().equals(referencia.getCodigoReferencia())) {
						BigDecimal cantidadBigDec = new BigDecimal(
								hshRefAsociadasCantidad.get(new Long(referencia.getCodigoReferencia().toString())));
						BigDecimal ventaHoyBigDec = (referencia.getTotalVentaAnticipada() != null
								? referencia.getTotalVentaAnticipada() : new BigDecimal("0"))
								.add(referencia.getTotalVentaCompetencia() != null
								? referencia.getTotalVentaCompetencia() : new BigDecimal("0"))
								.add(referencia.getTotalVentaOferta() != null ? referencia.getTotalVentaOferta()
										: new BigDecimal("0"))
								.add(referencia.getTotalVentaTarifa() != null ? referencia.getTotalVentaTarifa()
										: new BigDecimal("0"));
						ventaHoyBigDec = ventaHoyBigDec.multiply(cantidadBigDec);

						ventaHoyBigDecTotal = ventaHoyBigDecTotal.add(ventaHoyBigDec);
					}
				}
				ventaHoy = ventaHoyBigDecTotal.doubleValue();
			}
		} catch (Exception e) {
			valoresStock.setFlgErrorWSVentasTienda(1);
			return valoresStock;
		}
		// Calculo Dia Mayor Venta
		HistoricoUnidadesVenta historicoUnidadesVenta = new HistoricoUnidadesVenta();
		historicoUnidadesVenta.setCodArticulo(referenciasCentro.getCodArt());
		historicoUnidadesVenta.setCodLoc(referenciasCentro.getCodCentro());
		Double ventasMax = new Double(0);

		Double ventasAux = this.historicoUnidadesVentaService.findDayMostSales(historicoUnidadesVenta);

		if (null != ventasAux) {
			ventasMax = ventasAux;
		}

		// Calculo Stock Actual

		try {
			ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(referenciasCentro.getCodCentro()));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA);
			BigInteger[] listaRef = { BigInteger.valueOf(referenciasCentro.getCodArt()) };
			requestType.setListaCodigosReferencia(listaRef);
			
			User user = (User) session.getAttribute("user");
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
			
			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: pdaP13SegPedidosController (obtenerValoresStock)	 ############");
				logger.error("###########################################################################################################");
			}
			
			ConsultarStockResponseType responseType = this.correccionStockService.consultaStock(requestType,session);
			if (responseType.getCodigoRespuesta().equals("OK")) {
				for (ReferenciaType referencia : responseType.getListaReferencias()) {
					if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(referenciasCentro.getCodArt()))) {
						if (referencia.getCodigoError().equals(new BigInteger("0"))) {
							if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)) {
								valoresStock.setStock(referencia.getBandejas().doubleValue());
							} else {
								valoresStock.setStock(referencia.getStock().doubleValue());
							}
						} else {
							valoresStock.setFlgErrorWSVentasTienda(1);
							return valoresStock;
						}
					}
				}
			} else {
				valoresStock.setFlgErrorWSVentasTienda(1);
				return valoresStock;
			}
		} catch (Exception e) {
			valoresStock.setFlgErrorWSVentasTienda(1);
			return valoresStock;
		}

		// Control Si SFM
		VDatosDiarioArt vDatosDiarioArtRes = this.obtenerDatosDiarioArt(referenciasCentro);
		if (null != vDatosDiarioArtRes) {
			VAgruComerParamSfmcap vAgruComerParamSfmcap = new VAgruComerParamSfmcap();
			vAgruComerParamSfmcap.setCodCentro(referenciasCentro.getCodCentro());
			vAgruComerParamSfmcap.setGrupo1(vDatosDiarioArtRes.getGrupo1());
			vAgruComerParamSfmcap.setGrupo2(vDatosDiarioArtRes.getGrupo2());
			vAgruComerParamSfmcap.setGrupo3(vDatosDiarioArtRes.getGrupo3());
			vAgruComerParamSfmcap.setGrupo4(vDatosDiarioArtRes.getGrupo4());
			vAgruComerParamSfmcap.setGrupo5(vDatosDiarioArtRes.getGrupo5());
			List<VAgruComerParamSfmcap> listaEstructuras = this.vAgruComerParamSfmcapService
					.findAll(vAgruComerParamSfmcap, null);
			VAgruComerParamSfmcap estructuraArticulo = null;
			if (!listaEstructuras.isEmpty()) {
				estructuraArticulo = listaEstructuras.get(0);
			}
			Double stockBajo;
			Double sobreStockInferior;
			Double sobreStockSuperior;

			boolean esSfm = (null != estructuraArticulo && estructuraArticulo.getFlgStockFinal().equals("S"));
			boolean esFacing = (null != estructuraArticulo && estructuraArticulo.getFlgFacing().equals("S"));
			boolean esCap = (null != estructuraArticulo && estructuraArticulo.getFlgCapacidad().equals("S"));
			boolean esFacingCapOtros = false;

			Long facing = new Long(0);
			Float sfm = new Float(0);
			StockFinalMinimo stockFinalMinimo = this.obtenerStockFinalMinimo(referenciasCentroIC);

			// Compruebo si es Facing, Sfm o Capacidad, por este orden
			// y calculo el correspondiente facing o el sfm para usarlo en el
			// calculo de los stockBajo, el sobreStockInferior y el
			// sobreStockSuperior.
			if (esFacing) {
				// Es Facing
				esFacingCapOtros = true;
				facing = (null != stockFinalMinimo.getFacingCentroSIA()) ? stockFinalMinimo.getFacingCentroSIA()
						: new Long(0);
			} else if (esSfm) {
				// Es Sfm
				esFacingCapOtros = false;
				sfm = (null != stockFinalMinimo.getCantidadManualSIA()) ? stockFinalMinimo.getCantidadManualSIA()
						: new Float(0);
			} else if (esCap) {
				// Es Capacidad
				esFacingCapOtros = true;
				Float capacidad = (null != stockFinalMinimo.getCapacidadSIA()) ? stockFinalMinimo.getCapacidadSIA()
						: new Float(0);
				CentroAutoservicio centroAutoservicio = new CentroAutoservicio();
				centroAutoservicio.setCodCentro(referenciasCentroIC.getCodCentro());
				List<CentroAutoservicio> listaCentroAutoservicio = this.paramCentrosVpService
						.findCentroAutoServicioAll(centroAutoservicio);
				facing = new Long(1);
				if (!listaCentroAutoservicio.isEmpty()) {
					CentroAutoservicio centroAutoServicio = listaCentroAutoservicio.get(0);

					if (centroAutoServicio != null && centroAutoServicio.getPorcentajeCapacidad() != null) {
						Float porcentajeCapacidad = centroAutoServicio.getPorcentajeCapacidad();
						if (porcentajeCapacidad != null && capacidad * porcentajeCapacidad > 1) {
							facing = new Double(capacidad * porcentajeCapacidad).longValue();
						}
					}

				}
			} else {
				// Es otros
				esFacingCapOtros = true;
				PlanogramaVigente planogramaVigente = this.obtenerPlanogramaVigente(referenciasCentroIC);
				if (null != planogramaVigente) {
					facing = planogramaVigente.getStockMinComerLineal().longValue();
				}
			}

			// Calculo el stockBajo, el sobreStockInferior y el
			// sobreStockSuperior
			// con el facing o el sfm calculado en el if anterior.
			if (esFacingCapOtros) {
				// Es Facing o Capacidad o Otros
				VMapaAprov vMapaAprov = new VMapaAprov();
				vMapaAprov.setCodArt(referenciasCentro.getCodArt());
				vMapaAprov.setCodCentro(referenciasCentro.getCodCentro());

				vMapaAprov = this.vMapaAprovService.findOne(vMapaAprov);
				Integer tipoServicio = 5;
				if (null == vMapaAprov) {
					vMapaAprov = new VMapaAprov();
					vMapaAprov.setCodCentro(referenciasCentro.getCodCentro());
					vMapaAprov.setCodN1(vDatosDiarioArtRes.getEstlogN1().toString());
					vMapaAprov.setCodN2(vDatosDiarioArtRes.getEstlogN2().toString());
					vMapaAprov.setCodN3(vDatosDiarioArtRes.getEstlogN3().toString());
					vMapaAprov = this.vMapaAprovService.findOne(vMapaAprov);
				}
				if (null != vMapaAprov) {
					Integer contador = 0;
					Method[] methods = vMapaAprov.getClass().getDeclaredMethods();
					for (Method method : methods) {
						if (method.getName().startsWith("getPed")) {
							Long pedidos = (Long) method.invoke(vMapaAprov);
							if (pedidos > 0) {
								contador++;
							}
						}
					}
					if (contador >= 5) {
						tipoServicio = 3;
					} else {
						tipoServicio = 5;
					}
				}

				Long multiplicador = new Long(1);

				//Comprobación de si una referencia es FFPP 
				User user = (User) session.getAttribute("user");
				boolean tratamientoVegalsaAux = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
				referenciasCentro = this.vRelacionArticuloService.obtenerFfppActivaOUnitaria(referenciasCentro, tratamientoVegalsaAux);

				//Si es un FFPP no tiene sentido el multiplicador
				if (!referenciasCentro.isTieneUnitaria()){
					//Peticion 54867
					VFacingX vFacingXRes = new VFacingX();
					VFacingX vFacingX = new VFacingX();
					vFacingX.setCodArticulo(vDatosDiarioArtRes.getCodFpMadre()); 
					vFacingX.setCodCentro(referenciasCentroIC.getCodCentro());
					vFacingXRes = this.vFacingXService.findOne(vFacingX);

					if (vFacingXRes != null && vFacingXRes.getFacingX()!=null){
						multiplicador = vFacingXRes.getFacingX();
					} 
				}

				if (facing * multiplicador > ventaMedia) {
					stockBajo = ((facing * multiplicador) - ventaHoy);
				} else {
					stockBajo = (ventaMedia - ventaHoy);
				}

				sobreStockInferior = (ventasMax * tipoServicio) - ventaHoy;
				sobreStockSuperior = ((ventasMax * tipoServicio) - ventaHoy) * 2;

			} else {
				// Es Sfm
				Long vidaUtil = stockFinalMinimo.getVidaUtil();
				if (null == vidaUtil || vidaUtil.equals(new Long(0))) {
					vidaUtil = vDatosDiarioArtRes.getVidaUtil();
					if (vidaUtil.equals(new Long(0))) {
						vidaUtil = new Long(1);
					}
				}

				if (sfm > ventaMedia) {
					stockBajo = (double) (sfm - ventaHoy);
				} else {
					stockBajo = (double) (ventaMedia - ventaHoy);
				}
				sobreStockInferior = (ventasMax * vidaUtil) - ventaHoy;
				sobreStockSuperior = ((ventasMax * vidaUtil) - ventaHoy) * 2;
			}

			valoresStock.setStockBajo(stockBajo);
			valoresStock.setSobreStockInferior(sobreStockInferior);
			valoresStock.setSobreStockSuperior(sobreStockSuperior);
			valoresStock.setFlgErrorWSVentasTienda(0);
		}

		// Comprobación de si hay que mostrar el link de información de
		// motivos
		// cuando el stock es alto o bajo
		// Restringido de momento a 2 usuarios
		valoresStock.setMostrarMotivosStock("N"); // Inicialización a no
		// mostrar
		// el enlace a motivos de
		// stock

		User user = (User) session.getAttribute("user");
		String centroUsuario = "";
		if (user.getCentro() != null && user.getCentro().getCodCentro() != null) {
			centroUsuario = user.getCentro().getCodCentro().toString();
		}
		// if ("202".equals(centroUsuario) || "288".equals(centroUsuario) ||
		// "673".equals(centroUsuario) || "691".equals(centroUsuario) ||
		// "i1251".equals(user.getCode()) || "I1251".equals(user.getCode()) ||
		// "s305060".equals(user.getCode()) || "S305060".equals(user.getCode()))
		// {

		if (valoresStock.getStockBajo() <= valoresStock.getSobreStockInferior()
				&& valoresStock.getSobreStockInferior() <= valoresStock.getSobreStockSuperior()
				&& valoresStock.getStockBajo() >= 0) {
			if (valoresStock.getStock() < valoresStock.getStockBajo()
					|| valoresStock.getStock() > valoresStock.getSobreStockInferior()) {
				MotivoTengoMuchoPoco motivoTengoMuchoPoco = new MotivoTengoMuchoPoco();
				motivoTengoMuchoPoco.setCodArticulo(referenciasCentro.getCodArt());
				motivoTengoMuchoPoco.setCodCentro(referenciasCentro.getCodCentro());

				String idSesionConsultaMotivos = session.getId() + "_MOT_FFPP";
				List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();

				TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
				tPedidoAdicional.setIdSesion(idSesionConsultaMotivos);
				tPedidoAdicional.setCodCentro(referenciasCentro.getCodCentro());
				tPedidoAdicional.setCodArticulo(referenciasCentro.getCodArt());
				listaTPedidoAdicional.add(tPedidoAdicional);

				if (valoresStock.getStock() > valoresStock.getSobreStockInferior()) {
					motivoTengoMuchoPoco.setTipo(Constantes.TENGO_MUCHO_POCO_MOTIVO_MUCHO);
					// Si es tengo mucho hay que cargar los datos de la
					// referencia y de su formato productivo si existe para la
					// consulta de motivos
					if (pdaSeguimientoPedidos.getMostrarFFPP() != null
							&& pdaSeguimientoPedidos.getMostrarFFPP().equals("S")) {
						// Tratamiento pedido Adicional de ffpp
						tPedidoAdicional = new TPedidoAdicional();
						tPedidoAdicional.setIdSesion(idSesionConsultaMotivos);
						tPedidoAdicional.setCodCentro(referenciasCentro.getCodCentro());
						tPedidoAdicional.setCodArticulo(pdaSeguimientoPedidos.getCodArtRel());
						listaTPedidoAdicional.add(tPedidoAdicional);
					}
				} else {
					motivoTengoMuchoPoco.setTipo(Constantes.TENGO_MUCHO_POCO_MOTIVO_POCO);
				}

				this.tPedidoAdicionalService.obtenerPedidosAdicionales(listaTPedidoAdicional,
						referenciasCentro.getCodCentro(), idSesionConsultaMotivos,session);

				motivoTengoMuchoPoco.setIdSesion(idSesionConsultaMotivos);
				motivoTengoMuchoPoco.setStockBajo(valoresStock.getStockBajo());
				motivoTengoMuchoPoco.setStockAlto(valoresStock.getSobreStockInferior());
				motivoTengoMuchoPoco.setStock(valoresStock.getStock());

				MotivoTengoMuchoPocoLista motivoTengoMuchoPocoLista = null;
				motivoTengoMuchoPocoLista = this.motivoTengoMuchoPocoService
						.consultaMotivosTengoMuchoPoco(motivoTengoMuchoPoco);

				if (motivoTengoMuchoPocoLista != null && motivoTengoMuchoPocoLista.getEstado() != null
						&& (new Long(0)).equals(motivoTengoMuchoPocoLista.getEstado())) {
					if ("S".equals(motivoTengoMuchoPocoLista.getMapaActivo())
							&& "S".equals(motivoTengoMuchoPocoLista.getFlgFGen())) {
						valoresStock.setMostrarMotivosStock("S");
					}
				}
			}
		}

		// }

		return valoresStock;
	}

	public VDatosDiarioArt obtenerDatosDiarioArt(ReferenciasCentro referenciasCentro) throws Exception {
		VDatosDiarioArt vDatosDiarioArtRes = null;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();

		vDatosDiarioArt.setCodArt(referenciasCentro.getCodArt());
		vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

		return vDatosDiarioArtRes;
	}

	private Long obtenerCc(ReferenciasCentro referenciasCentro) throws Exception{
		//Preparar parámetros para búsqueda de cc
		CcRefCentro ccRefCentro = new CcRefCentro(referenciasCentro.getCodArt(), 
				referenciasCentro.getCodCentro(), 
				new Date());

		return ccRefCentroService.consultaCc(ccRefCentro);		
	}

	private String obtenerFlgDepositoBrita(ReferenciasCentro referenciasCentro) throws Exception{

		RefEnDepositoBrita refEnDepositoBrita = new RefEnDepositoBrita();
		refEnDepositoBrita.setCodArt(referenciasCentro.getCodArt());
		refEnDepositoBrita.setCodCentro(referenciasCentro.getCodCentro());
		
		return (this.refEnDepositoBritaService.enDepositoBrita(refEnDepositoBrita)?"S":"N");
	}

	private String obtenerFlgPorCatalogo(ReferenciasCentro referenciasCentro) throws Exception{

		String esRefPorCatalogo = "N";

		//Obtención de estructura comercial a partir de la referencia
		VDatosDiarioArt vDatosDiarioArt = obtenerDiarioArt(referenciasCentro.getCodArt());

		//Obtención del CC
		Long cc = obtenerCc(referenciasCentro);

		if (vDatosDiarioArt != null && vDatosDiarioArt.getGrupo1()!=null && cc != null &&
				Constantes.REFERENCIA_POR_CATALOGO_AREA.equals(vDatosDiarioArt.getGrupo1().toString()) && 
				Constantes.REFERENCIA_POR_CATALOGO_CC.equals(cc.toString())){

			esRefPorCatalogo = "S";
		}

		return esRefPorCatalogo;
	}
}