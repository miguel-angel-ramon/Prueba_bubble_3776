package es.eroski.misumi.control;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.MovimientoStock;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.PedidoPda;
import es.eroski.misumi.model.PendientesRecibir;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
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
import es.eroski.misumi.model.pda.PdaMovStocks;
import es.eroski.misumi.model.pda.PdaUltimosMovStocks;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.ventasTiendaWS.ReferenciaRespuetaType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.ImprimirEtiquetaService;
import es.eroski.misumi.service.iface.MovimientosStockService;
import es.eroski.misumi.service.iface.NumeroEtiquetaImpresoraService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.PedidoPdaService;
import es.eroski.misumi.service.iface.PendientesRecibirService;
import es.eroski.misumi.service.iface.RefAsociadasService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.USService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VRotacionRefService;
import es.eroski.misumi.service.iface.VentasTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP15MovStocksController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP13SegPedidosController.class);

	@Autowired
	private MovimientosStockService movimientosStockService;
	@Autowired
	private StockTiendaService correccionStockService;
	@Autowired
	private PendientesRecibirService pendientesRecibirService;
	@Autowired
	private RelacionArticuloService relacionArticuloService;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private ImprimirEtiquetaService imprimirEtiquetaService;

	@Autowired
	private NumeroEtiquetaImpresoraService numeroEtiquetaImpresoraService;

	@Autowired
	private VRotacionRefService vRotacionRefService;

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
	
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
	
	@Autowired
	private VentasTiendaService ventasTiendaService;

	@Autowired
	private RefAsociadasService refAsociadasService;

	@Autowired
	private StockTiendaService stockTiendaService;
	
	private final String PDA_P15_MOVSTOCKS = "pda_p15_movStocks";
	
	@RequestMapping(value = "/pdaP15FFPPActivo", method = RequestMethod.GET)
	public String ffppActivo(ModelMap model,
			@Valid final Long codArt,
			@Valid final Long codArtRel,
			@Valid final String mostrarFFPP,			
			HttpSession session,
			HttpServletResponse response) {

		PdaMovStocks pdaMovStocks = new PdaMovStocks();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		String resultado = "pda_p15_movStocks";
		PedidoPda pedidoPda = new PedidoPda();
		PdaError pdaError = new PdaError();

		try {
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");

			//Controlamos que me llega la referencia
			if (codArtRel != null && !codArtRel.equals("")){
				pdaMovStocks = this.obtenerResultado(codArtRel,user.getCentro().getCodCentro(),mostrarFFPP, codArt, session);

				//Si se ha producido un error con la referencia lo mostramos.
				if (pdaMovStocks.getEsError() != null && !pdaMovStocks.getEsError().equals("")){
					pdaError.setDescError(pdaMovStocks.getEsError());
					model.addAttribute("pdaError", pdaError);
					pdaDatosCab.setCodArtCab(String.valueOf(pdaMovStocks.getCodArt()));
					pdaDatosCab.setDescArtCab(pdaMovStocks.getDescArt());
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}	

				pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(codArt,user.getCentro().getCodCentro(),user.getMac()));
				pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),user.getMac()));
				if(codArt != null){
					pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(codArt.toString(),user.getCentro().getCodCentro(),user.getMac()));
				}else{
					pdaDatosCab.setImpresoraActiva("NO");
				}
				
				//Si el centro es de tipo caprabo, calcula los motivos por los que el centro caprabo muestra AC KO
				if(user.getCentro().getEsCentroCaprabo()){ 

					//Obtengo el código caprabo y su descripción y los inserto en pdaDatosCab
					Long codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), codArt);
					String descripCaprabo = utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo);

					pdaDatosCab.setCodArtCaprabo(codArtCaprabo.toString());					
					pdaDatosCab.setDescArtCab(descripCaprabo);
				}else{
					pdaDatosCab.setCodArtCaprabo(pdaMovStocks.getCodArt().toString());					
					pdaDatosCab.setDescArtCab(pdaMovStocks.getDescArt());
				}

				pedidoPda = pedidoPdaService.lanzarEncargoPda3(session, pdaMovStocks.getCodArt().toString(),pdaDatosCab.getCodArtCaprabo(),pdaDatosCab.getDescArtCab(),pdaMovStocks.getDatosDiarioArt(),response);
		
				//Miramos si existe la foto. La búsqueda se hace con código eroski.
				FotosReferencia fotosReferencia = new FotosReferencia();
				fotosReferencia.setCodReferencia(pdaMovStocks.getCodArt());
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					pdaMovStocks.setTieneFoto("S");
				} else {
					pdaMovStocks.setTieneFoto("N");
				}	
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		model.addAttribute("pdaMovStocks", pdaMovStocks);
		pdaDatosCab.setCodArtCab(String.valueOf(pdaMovStocks.getCodArt()));
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		session.setAttribute("pedidoPda", pedidoPda);

		return resultado;
	}

	@RequestMapping(value = "/pdaP15MovStocks", method = RequestMethod.GET)
	public String showForm(ModelMap model, 
			@Valid final Long codArt, 
			@Valid final String guardadoStockOk,
			final Long codArtRel,
			final String mostrarFFPP,
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE,
			@RequestParam(value="impresoraWS", required=false, defaultValue = "NO") String impresoraWS,
			@RequestParam(value="etiqueta", required=false, defaultValue = "false") boolean etiqueta,
			@RequestParam(value="greenImpr", required=false, defaultValue = "NO") String greenImpr,
			@RequestParam(value="procede", required=false, defaultValue = "") String procede,
			@RequestParam(value="sufijoPrehueco", required=false) String sufijoPrehueco,
			HttpSession session, HttpServletRequest request,HttpServletResponse response) {

		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");

		String resultado = PDA_P15_MOVSTOCKS;
		PdaMovStocks pdaMovStocks = new PdaMovStocks();
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		PedidoPda pedidoPda = new PedidoPda();

		// Si se pasa el sufijo con valor, por ejemplo "_PREHUECO", se concatena a la mac para acceder a las tablas de BBDD. (T_MIS_IMPRESION_ETIQUETAS)
		String mac = (sufijoPrehueco!=null && !StringUtils.isEmpty(sufijoPrehueco)?user.getMac()+sufijoPrehueco:user.getMac());

		if (origenGISAE.equals("SI")){
			pdaDatosCab.setOrigenGISAE(origenGISAE);
		}
		model.addAttribute("origenGISAE", origenGISAE);

		try{

			//Peticion 53662. Etiquetas número de veces.
			if (etiqueta){
				//Obtenemos el número de etiqueta del codArt
				Integer numeroEtiqueta = numeroEtiquetaImpresoraService.getNumEtiqueta(codArt,user.getCentro().getCodCentro(),mac);
				//Incrementamos el número de etiqueta del codArt
				numeroEtiquetaImpresoraService.incNumEtiqueta(codArt.toString(), numeroEtiqueta.intValue(),user.getCentro().getCodCentro(),mac);
			}
			pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(codArt,user.getCentro().getCodCentro(),mac));
			pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),mac));
			if (codArt != null){
				pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(codArt.toString(),user.getCentro().getCodCentro(),mac));
			}else{
				pdaDatosCab.setImpresoraActiva("NO");
			}
			
			if (impresoraWS.equals("SI")){

				//LLamar al WS de imprimir etiquetas
				ImprimirEtiquetaRequestType imprimirEtiquetarequest = imprimirEtiquetaService.createImprimirEtiquetaRequestType();

				try {
					ImprimirEtiquetaResponseType imprimirEtiquetaResponse = imprimirEtiquetaService.imprimirEtiquetaWS(imprimirEtiquetarequest);

					if (imprimirEtiquetaService.isOkImprimirEtiquetaResponse(imprimirEtiquetaResponse)) {  // El codigo de respuesta del WS ha sido OK.

						//Hacemos un bucle porque devuelve una lista pero en
						//este caso siempre devuelve una sola referencia
						for (int i = 0; i < imprimirEtiquetaResponse.getReferencias().length; i++){
							ReferenciaResponseType refResponse = imprimirEtiquetaResponse.getReferencias()[i];
							BigInteger codigoErrorRef = refResponse.getCodigoError();
							String mensajeErrorRef = refResponse.getMensajeError();

							//El WS ha devuelto OK para la referencia consultada. 
							//Ponemos el icono de la impresora a verde.
							if (codigoErrorRef.equals(BigInteger.valueOf(0))) { 
								numeroEtiquetaImpresoraService.siNumEtiquetaInSessionEnviados();
								pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getNumEtiquetaInSessionEnviados());
								model.addAttribute("codigoArticulo", codArt);
								resultado = "pda_p03_enviarMapNumEti";
							//El WS no ha devuelto OK para la referecnia consultada. Abrimos una ventana con el mensaje de error del WS.
							} else {
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
					pdaDatosPopupImpresora.setMensajeErrorWS(messageSource.getMessage("pda_p12_datosReferencia.errorWSImpresion", null, LocaleContextHolder.getLocale()));

					model.addAttribute("pdaDatosPopupImpresora", pdaDatosPopupImpresora);								
				}							

			} else {
				pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getNumEtiquetaInSessionEnviados());
			}

			//Control de guardado de stock
			model.addAttribute("guardadoStockOk", guardadoStockOk);

			//Controlamos que me llega la referencia
			if (!codArt.equals("")){
				pdaMovStocks = this.obtenerResultado(codArt,user.getCentro().getCodCentro(),mostrarFFPP, codArtRel, session);

				//Si se ha producido un error con la referencia lo mostramos.
				if (pdaMovStocks.getEsError() != null && !pdaMovStocks.getEsError().equals("")){
					pdaError.setDescError(pdaMovStocks.getEsError());
					model.addAttribute("pdaError", pdaError);
					pdaDatosCab.setCodArtCab(String.valueOf(pdaMovStocks.getCodArt()));
					pdaDatosCab.setDescArtCab(pdaMovStocks.getDescArt());
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}

				//Si el centro es de tipo caprabo, calcula los motivos por los que el centro caprabo muestra AC KO
				if (user.getCentro().getEsCentroCaprabo()){ 
					//Obtengo el código caprabo y su descripción y los inserto en pdaDatosCab
					Long codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), codArt);
					String descripCaprabo = utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo);

					pdaDatosCab.setCodArtCaprabo(codArtCaprabo.toString());					
					pdaDatosCab.setDescArtCab(descripCaprabo);
				}else{
					pdaDatosCab.setCodArtCaprabo(pdaMovStocks.getCodArt().toString());					
					pdaDatosCab.setDescArtCab(pdaMovStocks.getDescArt());
				}

				// Si NO se accede desde Prehuecos.
				if (procede.isEmpty() || procede != "pdaP115PrehuecosLineal.do"){
					//Se calcula si se tiene que enseñar el link de lanzar encargo y en el caso de existir el encargo para mostrarlo
					//en la pantalla
					//pedidoPda = pedidoPdaService.lanzarEncargoPda2(user, codArt.toString(), pdaMovStocks.getDescArt());
					pedidoPda = pedidoPdaService.lanzarEncargoPda3(session, codArt.toString(),pdaDatosCab.getCodArtCaprabo(), pdaDatosCab.getDescArtCab(),pdaMovStocks.getDatosDiarioArt(),response);
				}
					
				//Miramos si existe la foto. La búsqueda se hace con código eroski.
				FotosReferencia fotosReferencia = new FotosReferencia();
				fotosReferencia.setCodReferencia(pdaMovStocks.getCodArt());
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					pdaMovStocks.setTieneFoto("S");
				} else {
					pdaMovStocks.setTieneFoto("N");
				}	
			}else{
				//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
				pdaError.setDescError(this.messageSource.getMessage("pda_p12_datosReferencia.referenciaVacia", null, locale));
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaError", pdaError);
				logger.info("PDA Referencia no introducida");
				return "pda_p03_showMessage";
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		model.addAttribute("pdaMovStocks", pdaMovStocks);

		pdaDatosCab.setCodArtCab(String.valueOf(pdaMovStocks.getCodArt()));
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		//model.addAttribute("pedidoPda", pedidoPda);
		
		//Guardamos el estado de la impresora verde fija si es 'SI'.
		model.addAttribute("greenImpr", greenImpr);
		model.addAttribute("procede", procede);

		session.setAttribute("pedidoPda", pedidoPda);
		return resultado;
	}

	@RequestMapping(value = "/pdaP15Paginar", method = RequestMethod.GET)
	public String pdaP15Paginar(ModelMap model, 
			@Valid final Long codArt, 
			@Valid final String guardadoStockOk,
			final Long codArtRel,
			final String mostrarFFPP,
			@Valid final String page,
			@Valid final String pgTot,
			@Valid final String botPag,
			@Valid final String procede,
			HttpSession session,
			HttpServletResponse response) {

		PdaMovStocks pdaMovStocks = new PdaMovStocks();
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		PedidoPda pedidoPda = new PedidoPda();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();

		try{
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");

			//Control de guardado de stock
			model.addAttribute("guardadoStockOk", guardadoStockOk);

			//Peticion 53662. Etiquetas número de veces.
			pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(codArt,user.getCentro().getCodCentro(),user.getMac()));
			pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),user.getMac()));
			if(codArt != null){
				pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(codArt.toString(),user.getCentro().getCodCentro(),user.getMac()));
			}else{
				pdaDatosCab.setImpresoraActiva("NO");				
			}
			
			//Controlamos que me llega la referencia
			if (codArt != null){
				pdaMovStocks = obtenerResultadoPaginar(codArt,user.getCentro().getCodCentro(),mostrarFFPP, codArtRel, page, pgTot, botPag, session);

				//Si se ha producido un error con la referencia lo mostramos.
				if (pdaMovStocks.getEsError() != null && !pdaMovStocks.getEsError().equals("")){
					pdaError.setDescError(pdaMovStocks.getEsError());
					model.addAttribute("pdaError", pdaError);
					pdaDatosCab.setCodArtCab(String.valueOf(pdaMovStocks.getCodArt()));
					pdaDatosCab.setDescArtCab(pdaMovStocks.getDescArt());
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}

				//Si el centro es de tipo caprabo, calcula los motivos por los que el centro caprabo muestra AC KO
				if(user.getCentro().getEsCentroCaprabo()){ 
					//Obtengo el código caprabo y su descripción y los inserto en pdaDatosCab
					Long codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), codArt);
					String descripCaprabo = utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo);

					pdaDatosCab.setCodArtCaprabo(codArtCaprabo.toString());					
					pdaDatosCab.setDescArtCab(descripCaprabo);
				}else{
					pdaDatosCab.setCodArtCaprabo(pdaMovStocks.getCodArt().toString());					
					pdaDatosCab.setDescArtCab(pdaMovStocks.getDescArt());
				}

				// Si NO se accede desde Prehuecos.
				if (procede.isEmpty() || procede != "pdaP115PrehuecosLineal.do"){
					//Se calcula si se tiene que enseñar el link de lanzar encargo y en el caso de existir el encargo para mostrarlo
					//en la pantalla
					//pedidoPda = pedidoPdaService.lanzarEncargoPda2(user, codArt.toString(), pdaMovStocks.getDescArt());
					pedidoPda = pedidoPdaService.lanzarEncargoPda3(session, codArt.toString(),pdaDatosCab.getCodArtCaprabo(), pdaDatosCab.getDescArtCab(), pdaMovStocks.getDatosDiarioArt(),response);
				}
				
			}else{
				//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
				pdaError.setDescError(this.messageSource.getMessage("pda_p12_datosReferencia.referenciaVacia", null, locale));
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaError", pdaError);
				logger.info("PDA Referencia no introducida");
				return "pda_p03_showMessage";
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		model.addAttribute("pdaMovStocks", pdaMovStocks);

		pdaDatosCab.setCodArtCab(String.valueOf(pdaMovStocks.getCodArt()));
		pdaDatosCab.setDescArtCab(pdaMovStocks.getDescArt());
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("procede", procede);

		//model.addAttribute("pedidoPda", pedidoPda);
		session.setAttribute("pedidoPda", pedidoPda);
		
		return PDA_P15_MOVSTOCKS;
	}

	private PdaMovStocks obtenerResultado(Long codArt, Long codCentro, String mostradoFFPP, Long codArtRel, HttpSession session) throws Exception{

		Locale locale = LocaleContextHolder.getLocale();

		PdaMovStocks pdaMovStocks = new PdaMovStocks();
		pdaMovStocks.setCodArt(codArt);
		pdaMovStocks.setCodArtRel(codArtRel);

		VDatosDiarioArt vDatosDiarioArtResul = this.obtenerDiarioArt(codArt);
		pdaMovStocks.setDatosDiarioArt(vDatosDiarioArtResul);
		
		ReferenciasCentro referenciasCentro = new ReferenciasCentro();

		referenciasCentro.setCodArt(codArt);
		referenciasCentro.setCodCentro(codCentro);
		referenciasCentro.setDiarioArt(obtenerDiarioArt(referenciasCentro));

		//Obtenemos de sesion la informacion de usuario.
		User user = (User)session.getAttribute("user");

		boolean isCaprabo = false;
		if(user.getCentro().esCentroCaprabo()){
			isCaprabo = true;	
		}
		
		boolean isCapraboEspecial = false;
		if(user.getCentro().esCentroCapraboEspecial()){
			isCapraboEspecial = true;
		}
				
		VSurtidoTienda surtidoTienda = obtenerSurtidoTienda(referenciasCentro,isCaprabo,isCapraboEspecial);

		//Controlamos si se ha introducido una referencia que no existe.
		if (vDatosDiarioArtResul == null){
			pdaMovStocks.setEsError(this.messageSource.getMessage(
					"pda_p12_datosReferencia.noExisteReferencia", null, locale));
			return pdaMovStocks;
		}

		//Obtenemos la descripción del artículo.
		if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null){
			pdaMovStocks.setDescArt(vDatosDiarioArtResul.getDescripArt());
		}

		//Obtener MMC
		if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro()!= null && !"".equals(surtidoTienda.getMarcaMaestroCentro())){
			pdaMovStocks.setMMC(surtidoTienda.getMarcaMaestroCentro());
		}else{
			pdaMovStocks.setMMC("N");
		}

		//Controlamos si tiene un artículo relacionado
		VRelacionArticulo vRelacionArticulo = obtenerRelacionArticulo(referenciasCentro);

		//Control para mostrar pedido activo o no.
		pdaMovStocks.setPedidoActivo(obtenerPedidoActivo(surtidoTienda,isCaprabo));

		if (mostradoFFPP != null && mostradoFFPP.equals("S")){
			//En este caso venimos de una referencia.
			pdaMovStocks.setMostrarFFPP("N");
		}else if (mostradoFFPP != null && mostradoFFPP.equals("N")){
			//En este caso vamos al artículo relacionado de una referencia.
			pdaMovStocks.setMostrarFFPP("S");
		}else{
			// Controlamos si tiene un artículo unitario
			boolean tratamientoVegalsaAux = this.utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
			if (vRelacionArticulo != null && vRelacionArticulo.getCodArtRela() != null)
			{
				if(tratamientoVegalsaAux){
					if(vRelacionArticulo.getFormatoProductivoActivo() != null && vRelacionArticulo.getFormatoProductivoActivo().equals("S")){
						pdaMovStocks.setMostrarFFPP("S");
						pdaMovStocks.setCodArtRel(vRelacionArticulo.getCodArtRela());
					}
				}else{
					pdaMovStocks.setMostrarFFPP("S");
					pdaMovStocks.setCodArtRel(vRelacionArticulo.getCodArtRela());
				}
			}else{ 
				//Comprobación de artículo unitario
				VRelacionArticulo relacionArticuloUnitario = obtenerRelacionArticuloUnitario(referenciasCentro);
				if (relacionArticuloUnitario != null && relacionArticuloUnitario.getCodArt() != null){
					if(tratamientoVegalsaAux){
						if(relacionArticuloUnitario.getFormatoProductivoActivo() != null && relacionArticuloUnitario.getFormatoProductivoActivo().equals("S")){
							pdaMovStocks.setMostrarFFPP("N");
							pdaMovStocks.setCodArtRel(relacionArticuloUnitario.getCodArt());
						}
					}else{
						pdaMovStocks.setMostrarFFPP("N");
						pdaMovStocks.setCodArtRel(relacionArticuloUnitario.getCodArt());
					}
				}
			}
			
		}

		//Obtenemos movimientos de sctock
		MovimientoStock movimientoStock = new MovimientoStock();
		movimientoStock.setCodArt(codArt);
		movimientoStock.setCodCentro(codCentro);

		//Obtener referencias promocionales
		RelacionArticulo relacionArticulo = new RelacionArticulo();
		relacionArticulo.setCodArt(codArt);
		relacionArticulo.setCodCentro(codCentro);
		List<Long> referencias = this.relacionArticuloService.findAll(relacionArticulo);
		if (!referencias.isEmpty()){
			movimientoStock.setReferencias(referencias);
		}

		//Obtenemos movimientos de Stock y realizamo tratamiento para paginación

		//Se realiza un count de la consulta de movimientos de stock (un mes de movimientos de stock para atras)

		List<PdaUltimosMovStocks> listaMovStocks = this.movimientosStockService.findAllDetailsLastDaysPda(movimientoStock);

		if (!listaMovStocks.isEmpty()) {
			//Completar el campo page de pdaMovStocks, campo que indica que pagina se va a mostrar en pantalla, la primera vez
			//sera 1, todavia no se ha paginado
			pdaMovStocks.setPage("1");

			//Completar pdaMovStocks con el numero de registros totales de la lista
			pdaMovStocks.setRecords(listaMovStocks.size() + "");

			//Calcular el numero de paginas.
			int totalPaginas = listaMovStocks.size()/Constantes.DIAS_MOVIMIENTO_PISTOLA;
			if(listaMovStocks.size()%Constantes.DIAS_MOVIMIENTO_PISTOLA != 0){
				totalPaginas++;
			}
			pdaMovStocks.setTotal(totalPaginas + "");

			//Calcular la lista paginada
			int inicioPaginacion = 0; //La primera vez, cuando todavia el usuario no ha paginado
			int finPaginacion = Constantes.DIAS_MOVIMIENTO_PISTOLA; //El numero maximo de registros que se quiere mostrar en cada pagina.
			List<PdaUltimosMovStocks> listaMovStocksPaginadaFinal=new ArrayList<PdaUltimosMovStocks>();
			List<PdaUltimosMovStocks> listaMovStocksPaginada = this.movimientosStockService.findAllDetailsLastDaysPdaPaginada(movimientoStock, inicioPaginacion, finPaginacion);
			//MISUMI-581
			if(user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_MOVIMIENTOS_STOCK_130) != -1){
				PdaUltimosMovStocks pdaUltimosMovStocksHoy=new PdaUltimosMovStocks();
				ValoresStock valoresMovimientoStock=this.obtenerValoresMovimientoStock(referenciasCentro, session);
				Double sumatorioVentas=Utilidades.getOrZero(valoresMovimientoStock.getTotalVentaTarifa())
										+Utilidades.getOrZero(valoresMovimientoStock.getTotalVentaOferta())
										+Utilidades.getOrZero(valoresMovimientoStock.getTotalVentaAnticipada())
										+Utilidades.getOrZero(valoresMovimientoStock.getTotalVentaCompetencia());
				
				if (sumatorioVentas!=0 || valoresMovimientoStock.getTotalModifAjuste()!=0 || valoresMovimientoStock.getTotalModifRegul()!=0){
					pdaUltimosMovStocksHoy.setFecha("HOY");
					pdaUltimosMovStocksHoy.setVentas(sumatorioVentas.floatValue());
					pdaUltimosMovStocksHoy.setCorr1(valoresMovimientoStock.getTotalModifAjuste().floatValue());
					pdaUltimosMovStocksHoy.setCorr2(valoresMovimientoStock.getTotalModifRegul().floatValue());
					pdaUltimosMovStocksHoy.setFila("0");
					listaMovStocksPaginadaFinal.add(pdaUltimosMovStocksHoy);
				}
				
				for(PdaUltimosMovStocks pdaUltimosMovStocksAux:listaMovStocksPaginada){
					listaMovStocksPaginadaFinal.add(pdaUltimosMovStocksAux);
				}
			}else{
				listaMovStocksPaginadaFinal=listaMovStocksPaginada;
			}
			
			//Añadir la lista paginada
			pdaMovStocks.setListaMovStocks(listaMovStocksPaginadaFinal);
		}else{
			//MISUMI-581
			if(user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_MOVIMIENTOS_STOCK_130) != -1){
				List<PdaUltimosMovStocks> listaMovStocksPaginadaFinal=new ArrayList<PdaUltimosMovStocks>();
				ValoresStock valoresMovimientoStock=this.obtenerValoresMovimientoStock(referenciasCentro, session);
				Double sumatorioVentas=valoresMovimientoStock.getTotalVentaTarifa()+valoresMovimientoStock.getTotalVentaOferta()+
						valoresMovimientoStock.getTotalVentaAnticipada()+valoresMovimientoStock.getTotalVentaCompetencia();
				if(sumatorioVentas!=0 && valoresMovimientoStock.getTotalModifAjuste()!=0 && valoresMovimientoStock.getTotalModifRegul()!=0){
					PdaUltimosMovStocks pdaUltimosMovStocksHoy=new PdaUltimosMovStocks();
					pdaUltimosMovStocksHoy.setFecha("HOY");
					pdaUltimosMovStocksHoy.setVentas(sumatorioVentas.floatValue());
					pdaUltimosMovStocksHoy.setCorr1(valoresMovimientoStock.getTotalModifAjuste().floatValue());
					pdaUltimosMovStocksHoy.setCorr2(valoresMovimientoStock.getTotalModifRegul().floatValue());
					pdaUltimosMovStocksHoy.setFila("1");
					listaMovStocksPaginadaFinal.add(pdaUltimosMovStocksHoy);
					//Añadir la lista paginada
					pdaMovStocks.setListaMovStocks(listaMovStocksPaginadaFinal);
				}
			}
		}

		//Obtenemos Pendientes 1/2
		PendientesRecibir pr = new PendientesRecibir();
		pr.setCodArt(codArt);
		pr.setCodCentro(codCentro);
		if (!referencias.isEmpty()){
			pr.setReferencias(referencias);
		}
		pr = this.pendientesRecibirService.find(pr, null);
		pdaMovStocks.setCantHoy(pr.getCantHoy());
		pdaMovStocks.setCantFutura(pr.getCantFutura());

		if (null != session.getAttribute("consultaStock")){
			pdaMovStocks.setStockActivo("S");
		}else{
			pdaMovStocks.setStockActivo("N");
		}

		/*ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
		stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
		stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
		List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
		listaReferencias.add(BigInteger.valueOf(codArt));

		//Si la referencia consultada es TEXTIL, se deben obtener todas las referencia que compartan el mismo código de proveedor.
		//Si la referencia consultada es TEXTIL, se deben obtener todas las referencia que compartan el mismo código de proveedor.
		// DESCOMENTARLO UNA VEZ ESTE HECHO EL NUEVO SERVICIO DE TEXTIL. CORRESPONDE A LA PETICION 47885 (LA PARTE DE PISTOLA)
		// SE ACTIVA SOLO PARA LOS CENTROS: 5042-SEGOVIA, 0358-ABADIÑO, 0175-IRUÑA, 5326-INFANTE, 410 EIBAR, 164 GERNIKA
		if (vDatosDiarioArtResul.getGrupo1() == 3) {  //Es un artículo de textil
			
			stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA); //En este caso es una consulta basica, al 
			//WS se le pasara una lista de referencias
			List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(codArt);
			
			for (Long articulo : referenciasMismoModeloProveedor) {
				String strArticulo = articulo + "";
				if (!strArticulo.equals(codArt + "")) { //El articulo consultado por el usuario  (el de la cabecera) ya esta metido en la lista
					listaReferencias.add(BigInteger.valueOf(articulo));
				}
			}
		}

		stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
		try{*/
			
			
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
			
			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: pdaP15MovStocksController (obtenerResultado)	      ###########");
				logger.error("###########################################################################################################");
			}
			
			/*ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
			if (null != stockTiendaResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
				session.setAttribute("consultaStock", stockTiendaResponse);
				pdaMovStocks.setStockActivo("S");
			}else{
				session.removeAttribute("consultaStock");
				pdaMovStocks.setStockActivo("N");
			}
			if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
				pdaMovStocks.setStockActual(Utilidades.convertirDoubleAString(stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue(),"###0.0"));
			} else {
				pdaMovStocks.setStockActual(Utilidades.convertirDoubleAString(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue(),"###0.0"));
			}
		}catch (Exception e) {
			session.removeAttribute("consultaStock");
			pdaMovStocks.setStockActual("Error");
			pdaMovStocks.setStockActivo("N");
		}		

		/*RelacionArticulo relacionArticuloRela = new RelacionArticulo();
		relacionArticuloRela.setCodArtRela(referenciasCentro.getCodArt());
		relacionArticuloRela.setCodCentro(referenciasCentro.getCodCentro());
		List<Long> referenciasRela = this.relacionArticuloService.findAll(relacionArticuloRela);

		if ((pdaMovStocks.getMostrarFFPP() == null || !pdaMovStocks.getMostrarFFPP().equals("N")) && !Constantes.ERROR_TENGO_MUCHO_POCO_PISTOLA.equals(pdaMovStocks.getStockActual()) && referenciasRela.isEmpty())
		{
			pdaMovStocks.setValoresStock(this.obtenerValoresStock(referenciasCentro, pdaMovStocks, session));
		}*/

		//Obtener el tipo de rotacion
		pdaMovStocks.setTipoRotacion(this.obtenerTipoRotacion(pdaMovStocks, codCentro));
		//Obtener el USS. Sólo para area 4 y 5
		pdaMovStocks.setEsUSS(this.obtenerEsUSS(vDatosDiarioArtResul));
		return pdaMovStocks;
	}

	private String obtenerTipoRotacion(PdaMovStocks pdaMovStocks, Long codCentro) throws Exception{

		String tipoRotacion = "";

		//Carga de parámetros de búsqueda
		VRotacionRef vRotacionRef = new VRotacionRef();
		vRotacionRef.setCodCentro(codCentro);
		vRotacionRef.setCodArt(pdaMovStocks.getCodArt());

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
	
	private PdaMovStocks obtenerResultadoPaginar(Long codArt, Long codCentro, String mostradoFFPP, Long codArtRel, String page, String pgTot, String botPag, HttpSession session) throws Exception{

		Locale locale = LocaleContextHolder.getLocale();

		PdaMovStocks pdaMovStocks = new PdaMovStocks();
		pdaMovStocks.setCodArt(codArt);
		pdaMovStocks.setCodArtRel(codArtRel);

		VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);

		ReferenciasCentro referenciasCentro = new ReferenciasCentro();

		referenciasCentro.setCodArt(codArt);
		referenciasCentro.setCodCentro(codCentro);
		referenciasCentro.setDiarioArt(obtenerDiarioArt(referenciasCentro));

		//Obtenemos de sesi�n la informaci�n de usuario.
		User user = (User)session.getAttribute("user");
		
		boolean isCaprabo = false;
		if(user.getCentro().esCentroCaprabo()){
			isCaprabo = true;	
		}

		boolean isCapraboEspecial = false;
		if(user.getCentro().esCentroCapraboEspecial()){
			isCapraboEspecial = true;
		}
		
		VSurtidoTienda surtidoTienda = obtenerSurtidoTienda(referenciasCentro,isCaprabo,isCapraboEspecial);

		//Controlamos si se ha introducido una referencia que no existe.
		if (vDatosDiarioArtResul == null || surtidoTienda == null)
		{
			pdaMovStocks.setEsError(this.messageSource.getMessage(
					"pda_p12_datosReferencia.noExisteReferencia", null, locale));
			return pdaMovStocks;
		}

		//Obtenemos la descripción del artículo.
		if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null)
		{
			pdaMovStocks.setDescArt(vDatosDiarioArtResul.getDescripArt());
		}

		//Obtener MMC
		if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro()!= null && !"".equals(surtidoTienda.getMarcaMaestroCentro()))
		{
			pdaMovStocks.setMMC(surtidoTienda.getMarcaMaestroCentro());
		}else{
			pdaMovStocks.setMMC("N");
		}

		//Controlamos si tiene un artículo relacionado
		VRelacionArticulo vRelacionArticulo = obtenerRelacionArticulo(referenciasCentro);

		//Control para mostrar pedido activo o no.
		pdaMovStocks.setPedidoActivo(obtenerPedidoActivo(surtidoTienda,isCaprabo));

		if (mostradoFFPP != null && mostradoFFPP.equals("S"))
		{
			//En este caso venimos de una referencia.
			pdaMovStocks.setMostrarFFPP("N");
		}
		else if (mostradoFFPP != null && mostradoFFPP.equals("N"))
		{
			//En este caso vamos al artículo relacionado de una referencia.
			pdaMovStocks.setMostrarFFPP("S");
		}
		else
		{
			//Controlamos si tiene un artículo unitario
			boolean tratamientoVegalsaAux = this.utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
			if (vRelacionArticulo != null && vRelacionArticulo.getCodArtRela() != null)
			{
				if(tratamientoVegalsaAux){
					if(vRelacionArticulo.getFormatoProductivoActivo() != null && vRelacionArticulo.getFormatoProductivoActivo().equals("S")){
						pdaMovStocks.setMostrarFFPP("S");
						pdaMovStocks.setCodArtRel(vRelacionArticulo.getCodArtRela());
					}
				}else{
					pdaMovStocks.setMostrarFFPP("S");
					pdaMovStocks.setCodArtRel(vRelacionArticulo.getCodArtRela());
				}
			}else{ 
				//Comprobación de artículo unitario
				VRelacionArticulo relacionArticuloUnitario = obtenerRelacionArticuloUnitario(referenciasCentro);
				if (relacionArticuloUnitario != null && relacionArticuloUnitario.getCodArt() != null){
					if(tratamientoVegalsaAux){
						if(relacionArticuloUnitario.getFormatoProductivoActivo() != null && relacionArticuloUnitario.getFormatoProductivoActivo().equals("S")){
							pdaMovStocks.setMostrarFFPP("N");
							pdaMovStocks.setCodArtRel(relacionArticuloUnitario.getCodArt());
						}
					}else{
						pdaMovStocks.setMostrarFFPP("N");
						pdaMovStocks.setCodArtRel(relacionArticuloUnitario.getCodArt());
					}
				}
			}
		}


		//Obtenemos movimientos de sctock
		MovimientoStock movimientoStock = new MovimientoStock();
		movimientoStock.setCodArt(codArt);
		movimientoStock.setCodCentro(codCentro);

		//Obtener referencias promocionales
		RelacionArticulo relacionArticulo = new RelacionArticulo();
		relacionArticulo.setCodArt(codArt);
		relacionArticulo.setCodCentro(codCentro);
		List<Long> referencias = this.relacionArticuloService.findAll(relacionArticulo);
		if (!referencias.isEmpty()){
			movimientoStock.setReferencias(referencias);
		}



		//Obtenemos movimientos de Stock y realizamo tratamiento para paginación

		//Se realiza un count de la consulta de movimientos de stock (un mes de movimientos de stock para atras)

		List<PdaUltimosMovStocks> listaMovStocks = this.movimientosStockService.findAllDetailsLastDaysPda(movimientoStock);

		if (!listaMovStocks.isEmpty()) {


			//Completar pdaMovStocks con el numero de registros totales de la lista
			pdaMovStocks.setRecords(listaMovStocks.size() + "");

			//Calcular el numero de paginas.
			int totalPaginas = listaMovStocks.size()/Constantes.DIAS_MOVIMIENTO_PISTOLA;
			if(listaMovStocks.size()%Constantes.DIAS_MOVIMIENTO_PISTOLA != 0){
				totalPaginas++;
			}
			pdaMovStocks.setTotal(totalPaginas + "");



			//Calculamos segun el boton pulsado, cual va a ser la pagina a la que tenemos que ir
			int pagina = Integer.parseInt(page);
			int pageTot = Integer.parseInt(pgTot);


			if (botPag.equals("first"))
			{
				pagina = 1;
				pageTot = 2;
			}
			else if (botPag.equals("prev"))
			{
				pagina = pagina -1;

			}
			else if (botPag.equals("next"))
			{
				pagina = pagina +1;

			}
			else if (botPag.equals("last"))
			{
				pagina = pageTot;
			}


			//Completar el campo page de pdaMovStocks
			pdaMovStocks.setPage(pagina + "");

			//Calculamos el rownum de primer registro y del ultimo registro a mostrar en la pagina
			int inicioPaginacion = (pagina - 1) * Constantes.DIAS_MOVIMIENTO_PISTOLA;
			int finPaginacion = pagina *Constantes.DIAS_MOVIMIENTO_PISTOLA; 

			List<PdaUltimosMovStocks> listaMovStocksPaginadaFinal=new ArrayList<PdaUltimosMovStocks>();
			List<PdaUltimosMovStocks> listaMovStocksPaginada = this.movimientosStockService.findAllDetailsLastDaysPdaPaginada(movimientoStock, inicioPaginacion, finPaginacion);
			//MISUMI-581
			if(pagina == 1 && user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_MOVIMIENTOS_STOCK_130) != -1){
				PdaUltimosMovStocks pdaUltimosMovStocksHoy=new PdaUltimosMovStocks();
				ValoresStock valoresMovimientoStock=this.obtenerValoresMovimientoStock(referenciasCentro, session);
				Double sumatorioVentas=Utilidades.getOrZero(valoresMovimientoStock.getTotalVentaTarifa())
									+Utilidades.getOrZero(valoresMovimientoStock.getTotalVentaOferta())
									+Utilidades.getOrZero(valoresMovimientoStock.getTotalVentaAnticipada())
									+Utilidades.getOrZero(valoresMovimientoStock.getTotalVentaCompetencia());
				if(sumatorioVentas!=0 || valoresMovimientoStock.getTotalModifAjuste()!=0 || valoresMovimientoStock.getTotalModifRegul()!=0){
					pdaUltimosMovStocksHoy.setFecha("HOY");
					pdaUltimosMovStocksHoy.setVentas(sumatorioVentas.floatValue());
					pdaUltimosMovStocksHoy.setCorr1(valoresMovimientoStock.getTotalModifAjuste().floatValue());
					pdaUltimosMovStocksHoy.setCorr2(valoresMovimientoStock.getTotalModifRegul().floatValue());
					pdaUltimosMovStocksHoy.setFila("0");
					listaMovStocksPaginadaFinal.add(pdaUltimosMovStocksHoy);
				}
				
				for(PdaUltimosMovStocks pdaUltimosMovStocksAux:listaMovStocksPaginada){
					listaMovStocksPaginadaFinal.add(pdaUltimosMovStocksAux);
				}
			}else{
				listaMovStocksPaginadaFinal=listaMovStocksPaginada;
			}
			//Añadir la lista paginada
			pdaMovStocks.setListaMovStocks(listaMovStocksPaginadaFinal);
		}else{
			//MISUMI-581
			if(user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_MOVIMIENTOS_STOCK_130) != -1){
				List<PdaUltimosMovStocks> listaMovStocksPaginadaFinal=new ArrayList<PdaUltimosMovStocks>();
				ValoresStock valoresMovimientoStock=this.obtenerValoresMovimientoStock(referenciasCentro, session);
				Double sumatorioVentas=valoresMovimientoStock.getTotalVentaTarifa()+valoresMovimientoStock.getTotalVentaOferta()+
						valoresMovimientoStock.getTotalVentaAnticipada()+valoresMovimientoStock.getTotalVentaCompetencia();
				if(sumatorioVentas!=0 && valoresMovimientoStock.getTotalModifAjuste()!=0 && valoresMovimientoStock.getTotalModifRegul()!=0){
					PdaUltimosMovStocks pdaUltimosMovStocksHoy=new PdaUltimosMovStocks();
					pdaUltimosMovStocksHoy.setFecha("HOY");
					pdaUltimosMovStocksHoy.setVentas(sumatorioVentas.floatValue());
					pdaUltimosMovStocksHoy.setCorr1(valoresMovimientoStock.getTotalModifAjuste().floatValue());
					pdaUltimosMovStocksHoy.setCorr2(valoresMovimientoStock.getTotalModifRegul().floatValue());
					pdaUltimosMovStocksHoy.setFila("1");
					listaMovStocksPaginadaFinal.add(pdaUltimosMovStocksHoy);
					//Añadir la lista paginada
					pdaMovStocks.setListaMovStocks(listaMovStocksPaginadaFinal);
				}
			}
		}

		//Obtenemos Pendientes 1/2
		PendientesRecibir pr = new PendientesRecibir();
		pr.setCodArt(codArt);
		pr.setCodCentro(codCentro);
		if (!referencias.isEmpty()){
			pr.setReferencias(referencias);
		}
		pr = this.pendientesRecibirService.find(pr, null);
		pdaMovStocks.setCantHoy(pr.getCantHoy());
		pdaMovStocks.setCantFutura(pr.getCantFutura());

		if (null != session.getAttribute("consultaStock")){
			pdaMovStocks.setStockActivo("S");
		}else{
			pdaMovStocks.setStockActivo("N");
		}

		ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
		stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
		stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
		List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
		listaReferencias.add(BigInteger.valueOf(codArt));
		stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));

		//Si la referencia consultada es TEXTIL, se deben obtener todas las referencia que compartan el mismo código de proveedor.
		// DESCOMENTARLO UNA VEZ ESTE HECHO EL NUEVO SERVICIO DE TEXTIL. CORRESPONDE A LA PETICION 47885 (LA PARTE DE PISTOLA)
		// SE ACTIVA SOLO PARA LOS CENTROS: 5042-SEGOVIA, 0358-ABADIÑO, 0175-IRUÑA, 5326-INFANTE, 410 EIBAR, 164 GERNIKA
		if (vDatosDiarioArtResul.getGrupo1() == 3) {  //Es un artículo de textil



			stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA); //En este caso es una consulta basica, al 
			//WS se le pasara una lista de referencias
			List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(codArt);


			for (Long articulo : referenciasMismoModeloProveedor) {
				String strArticulo = articulo + "";
				if (!strArticulo.equals(codArt + "")) { //El articulo consultado por el usuario  (el de la cabecera) ya esta metido en la lista
					listaReferencias.add(BigInteger.valueOf(articulo));
				}
			}

		}

		try{
			
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
			
			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: pdaP15MovStocksController (obtenerResultadoPaginar) ###########");
				logger.error("###########################################################################################################");
			}
			ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
			if (null != stockTiendaResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
				session.setAttribute("consultaStock", stockTiendaResponse);
				pdaMovStocks.setStockActivo("S");
			}else{
				session.removeAttribute("consultaStock");
				pdaMovStocks.setStockActivo("N");
			}
			if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
				pdaMovStocks.setStockActual(Utilidades.convertirDoubleAString(stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue(),"###0.0"));
			} else {
				pdaMovStocks.setStockActual(Utilidades.convertirDoubleAString(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue(),"###0.0"));
			}
		}catch (Exception e) {
			session.removeAttribute("consultaStock");
			pdaMovStocks.setStockActual("Error");
			pdaMovStocks.setStockActivo("N");
		}		

		/*RelacionArticulo relacionArticuloRela = new RelacionArticulo();
		relacionArticuloRela.setCodArtRela(referenciasCentro.getCodArt());
		relacionArticuloRela.setCodCentro(referenciasCentro.getCodCentro());
		List<Long> referenciasRela = this.relacionArticuloService.findAll(relacionArticuloRela);

		if ((pdaMovStocks.getMostrarFFPP() == null || !pdaMovStocks.getMostrarFFPP().equals("N")) && !Constantes.ERROR_TENGO_MUCHO_POCO_PISTOLA.equals(pdaMovStocks.getStockActual()) && referenciasRela.isEmpty())
		{
			pdaMovStocks.setValoresStock(this.obtenerValoresStock(referenciasCentro, pdaMovStocks, session));
		}*/

		return pdaMovStocks;
	}

	public VDatosDiarioArt obtenerDatosDiarioArt(ReferenciasCentro referenciasCentro) throws Exception{
		VDatosDiarioArt vDatosDiarioArtRes = null;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();

		vDatosDiarioArt.setCodArt(referenciasCentro.getCodArt());
		vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

		return vDatosDiarioArtRes;
	}
	
	private ValoresStock obtenerValoresMovimientoStock(ReferenciasCentro referenciasCentro, HttpSession session) throws Exception{

		ValoresStock valoresStock = new ValoresStock();
		//Calculo Ventas Medias
		ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();
		referenciasCentroIC.setCodArt(referenciasCentro.getCodArt());
		referenciasCentroIC.setCodCentro(referenciasCentro.getCodCentro());

		//Calculo Venta Hoy
		List<Long> referenciaLoteTextil = null;
		List<BigInteger> listaReferenciasHijasTextil = null;
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

			//Obtención de lista de referencias relacionadas (REF_ASOCIADA)
			RefAsociadas refAsociadas = new RefAsociadas();
			refAsociadas.setCodArticulo(referenciasCentro.getCodArt());
			List<RefAsociadas> listaRefereciasAsociadas = this.refAsociadasService.findAll(refAsociadas);

			BigInteger[] listaReferencias = { BigInteger.valueOf(referenciasCentro.getCodArt()) };
			Hashtable<Long, Long> hshRefAsociadasCantidad = new Hashtable<Long, Long>();
			if (listaRefereciasAsociadas != null && listaRefereciasAsociadas.size()>0){
				listaReferencias = new BigInteger[listaRefereciasAsociadas.size()];
				int indiceArray = 0;

				for (RefAsociadas refAsociada : listaRefereciasAsociadas) {
					listaReferencias[indiceArray] = new BigInteger(refAsociada.getCodArticuloHijo().toString());
					hshRefAsociadasCantidad.put(refAsociada.getCodArticuloHijo(), refAsociada.getCantidad());
					indiceArray++;
				}
			}else{
				hshRefAsociadasCantidad.put(referenciasCentro.getCodArt(), new Long(1));
			}



			//Petición 54293

			//Se consulta la vista V_REFERENCIAS_LOTE_TEXTIL para comprobar si la referencia es una referencia lote.
			referenciaLoteTextil = this.relacionArticuloService.esReferenciaLote(referenciasCentro.getCodArt());	

			if ((referenciaLoteTextil != null) && (referenciaLoteTextil.size() > 0)) {

				//Si ademas es una referencias de textil y referencias lote, debemos obtener las referencias
				//hijas de V_REFERENCIAS_LOTE_TEXTIL. La lista de referencias hija obtenida se junta con la obtenida
				//de REF_ASOCIADAS.	

				listaReferenciasHijasTextil = this.relacionArticuloService.obtenerHijasLoteBI(referenciasCentro.getCodArt());	

				if (listaReferenciasHijasTextil != null && listaReferenciasHijasTextil.size()>0){    

					//Juntamos el array "listaReferencias" y la lista "listaReferenciasHijasTextil" en un nuevo array de BigInteger.
					BigInteger[] listaReferenciasTextil =  new BigInteger[listaReferencias.length + listaReferenciasHijasTextil.size()];

					//Primero volcamos la "listaReferencias"
					for (int i = 0; i < listaReferencias.length; i++) {
						listaReferenciasTextil[i] = listaReferencias[i];
					} 

					//Seguido volcamos la "listaReferenciasHijasTextil"
					int indiceArray = listaReferencias.length;		
					for (BigInteger referencia : listaReferenciasHijasTextil) {
						listaReferenciasTextil[indiceArray] = referencia;
						indiceArray++;
					}	

					for (BigInteger refHijaLote : listaReferenciasHijasTextil) {
						//hshRefAsociadasCantidad.put(refAsociada.getCodArticuloHijo(), refAsociada.getCantidad());
						hshRefAsociadasCantidad.put(new Long(refHijaLote.longValue()), new Long(1)); //PREGUNTAR A MARIA, EN EL CASO DE TEXTIL DE DONDE SACAMOS LA CANTIDAD???														   //¿¿¿ES 1???	
					}	

					//Es una referencia lote de textil, se pasara al WS la referencias obtenida en REF_ASOCIADAS + las obtenidas en V_REFERENCIAS_LOTE_TEXTIL
					ventasTiendaRequest.setListaReferencias(listaReferenciasTextil);
				}

			} else {
				//No es una referencia lote de textil, solo se pasara al WS la referencias obtenida en REF_ASOCIADAS
				ventasTiendaRequest.setListaReferencias(listaReferencias);
			}

			VentasTiendaResponseType ventasTiendaResponse = this.ventasTiendaService.consultaVentas(ventasTiendaRequest,session);
			if (ventasTiendaResponse.getListaReferencias().length > 0){
				
				Double totalVentaTarifa = new Double(0);
				Double totalVentaOferta = new Double(0);
				Double totalVentaAnticipada = new Double(0);
				Double totalVentaCompetencia = new Double(0);
				Double totalModifAjuste = new Double(0);
				Double totalModifRegul = new Double(0);

				for (ReferenciaRespuetaType referencia : ventasTiendaResponse.getListaReferencias()) {
					//Si da error en la consulta de la referencia se ignora salvo si se trata de la referencia padre
					if (new BigInteger("0").equals(referencia.getCodigoError()) || referenciasCentro.getCodArt().equals(referencia.getCodigoReferencia())){
						valoresStock.setStockInicial(stockTiendaService.consultarStockInicial(referenciasCentro.getCodCentro(), referenciasCentro.getCodArt()));
						
						final Long cantidad = hshRefAsociadasCantidad.get(new Long(referencia.getCodigoReferencia().toString()));

						// Valores parciales de una referencia
						final Double ventaTarifa = referencia.getTotalVentaTarifa()!=null?referencia.getTotalVentaTarifa().doubleValue():new Double(0.000);
						final Double ventaOferta = referencia.getTotalVentaOferta()!=null?referencia.getTotalVentaOferta().doubleValue():new Double(0.000);
						final Double ventaAnticipada = referencia.getTotalVentaAnticipada()!=null?referencia.getTotalVentaAnticipada().doubleValue():new Double(0.000);
						final Double ventaCompetencia = referencia.getTotalVentaCompetencia()!=null?referencia.getTotalVentaCompetencia().doubleValue():new Double(0.000);
						final Double modifAjuste = referencia.getTotalSalidasAjustes()!=null?referencia.getTotalSalidasAjustes().doubleValue():new Double(0.000);
						final Double modifRegul = referencia.getTotalRegulaciones()!=null?referencia.getTotalRegulaciones().doubleValue():new Double(0.000);
						
						// Sumatorio de los valores de cada referencia por la cantidad
						totalVentaTarifa = totalVentaTarifa + (cantidad * ventaTarifa);
						totalVentaOferta = totalVentaOferta + (cantidad * ventaOferta);
						totalVentaAnticipada = totalVentaAnticipada + (cantidad * ventaAnticipada);
						totalVentaCompetencia = totalVentaCompetencia + (cantidad * ventaCompetencia);
						totalModifAjuste = totalModifAjuste + (cantidad * modifAjuste);
						totalModifRegul = totalModifRegul + (cantidad * modifRegul);
						
					}
				}

				valoresStock.setTotalVentaTarifa(totalVentaTarifa);
				valoresStock.setTotalVentaOferta(totalVentaOferta);
				valoresStock.setTotalVentaAnticipada(totalVentaAnticipada);
				valoresStock.setTotalVentaCompetencia(totalVentaCompetencia);
				valoresStock.setTotalModifAjuste(totalModifAjuste);
				valoresStock.setTotalModifRegul(totalModifRegul);

			}
		} catch (Exception e) {
			valoresStock.setFlgErrorWSVentasTienda(1);
			return valoresStock;
		}
		
		return valoresStock;
	}

}