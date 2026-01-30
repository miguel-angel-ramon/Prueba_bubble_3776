package es.eroski.misumi.control.prehuecos;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.control.pdaConsultasController;
import es.eroski.misumi.model.CcRefCentro;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.HistoricoUnidadesVenta;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.model.MontajeVegalsa;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.PedidoPda;
import es.eroski.misumi.model.PendientesRecibir;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.PlataformaAprovisionamientoMercancia;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.model.RefEnDepositoBrita;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VMapaAprov;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.ValoresStock;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaTypeResponse;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaResponseType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ReferenciaResponseType;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosPopupImpresora;
import es.eroski.misumi.model.pda.PdaDatosReferencia;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.model.pda.prehuecos.PrehuecosLineal;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ventasTiendaWS.ReferenciaRespuetaType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType;
import es.eroski.misumi.service.iface.CcRefCentroService;
import es.eroski.misumi.service.iface.FacingVegalsaService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.HistoricoUnidadesVentaService;
import es.eroski.misumi.service.iface.ImagenComercialService;
import es.eroski.misumi.service.iface.ImprimirEtiquetaService;
import es.eroski.misumi.service.iface.KosmosService;
import es.eroski.misumi.service.iface.MotivoTengoMuchoPocoService;
import es.eroski.misumi.service.iface.NumeroEtiquetaImpresoraService;
import es.eroski.misumi.service.iface.OfertaVigenteService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.PedidoPdaService;
import es.eroski.misumi.service.iface.PendientesRecibirService;
import es.eroski.misumi.service.iface.PlanogramaCentroService;
import es.eroski.misumi.service.iface.PlanogramaKosmosService;
import es.eroski.misumi.service.iface.PlataformaOrigenSuministroService;
import es.eroski.misumi.service.iface.RefAsociadasService;
import es.eroski.misumi.service.iface.RefEnDepositoBritaService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockFinalMinimoService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.USService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VMapaAprovService;
import es.eroski.misumi.service.iface.VRotacionRefService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.service.iface.VentasTiendaService;
import es.eroski.misumi.service.prehuecos.iface.PrehuecosLinealService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class PdaP115PrehuecosLinealController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(PdaP115PrehuecosLinealController.class);

	private final String PREHUECOS = "/pda/prehuecos/pda_p114_preHuecos";
	private final String PREHUECOS_SEL_OPERATIVA = "/pda/prehuecos/pda_p115_preHuecosSelOperativa";
	private final String PREHUECOS_LINEAL = "/pda/prehuecos/pda_p115_preHuecosLineal";
	private final String SUFIJO_PREHUECO = "_PREHUECO";
	private final String MENSAJE_ERROR = "/pda/prehuecos/pda_p115_showMessage";

	@Value( "${tipoAprovisionamiento.descentralizado}" )
	private String tipoAprovisionamientoDescentralizado;

	@Autowired
	private StockFinalMinimoService stockFinalMinimoService;

	@Autowired
	private StockTiendaService correccionStockService;

	@Autowired
	private RefAsociadasService refAsociadasService;

	@Autowired
	private VentasTiendaService ventasTiendaService;

	@Autowired
	private HistoricoUnidadesVentaService historicoUnidadesVentaService;

	@Autowired
	private VMapaAprovService vMapaAprovService;

	@Autowired
	private MotivoTengoMuchoPocoService motivoTengoMuchoPocoService;

	/* inicio  cambios 48332 */
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	/* fin cambios 48332 */

	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@Autowired
	private PendientesRecibirService pendientesRecibirService;

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
	private NumeroEtiquetaImpresoraService numeroEtiquetaImpresoraService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@Autowired
	private PlataformaOrigenSuministroService plataformaOrigenSuministroService;

	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	@Resource
	private MessageSource messageSource;

	@Autowired
	private FotosReferenciaService fotosReferenciaService; 

	@Autowired
	private USService ussService;

	@Autowired
	private PlanogramaKosmosService planogramaKosmosService;

	@Autowired
	private ImagenComercialService imagenComercialService;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;

	@Autowired
	private FacingVegalsaService facingVegalsaService;

	@Autowired
	private PrehuecosLinealService prehuecosLinealService;
	

	@RequestMapping(value = "/pdaP115PrehuecosLineal", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@Valid final Long codArt,
			@Valid final String guardadoStockOk,
			@Valid final String guardadoSfm,
			@Valid final String guardadoImc,
			@Valid final String centroParametrizado,
			@Valid final String procede,
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE,
			@RequestParam(value="impresoraWS", required=false, defaultValue = "NO") String impresoraWS,
			@RequestParam(value="etiqueta", required=false, defaultValue = "false") boolean etiqueta,
			@RequestParam(value="greenLink", required=false, defaultValue = "false") boolean greenLink,
			@RequestParam(value="greenImpr", required=false, defaultValue = "NO") String greenImpr,
			@RequestParam(value="cleanAll", required=false, defaultValue = "N") String cleanAll,
			@RequestParam(value="prehuecoSeleccionado", required=false, defaultValue = "N") String prehuecoSeleccionado,
			final Long codArtRel,
			final String mostrarFFPP,HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");

		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		PdaDatosReferencia pdaDatosRefResultado = new PdaDatosReferencia();
		String resultado = PREHUECOS_LINEAL;
		PdaError pdaError = new PdaError();
		String macPrehueco = user.getMac()+SUFIJO_PREHUECO;

		Long stockLineal = null;
		Long estadoRef = -1L;
		Long numPrehuecosSinValidar = 0L;
		PrehuecosLineal prehuecosLineal = new PrehuecosLineal();
		PrehuecosLineal prehuecosLinealAux = new PrehuecosLineal();
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();
		
		if (origenGISAE.equals("SI")){
			pdaDatosCab.setOrigenGISAE(origenGISAE);
		}
		model.addAttribute("origenGISAE", origenGISAE);

		try{
			Boolean existeMapNumEtiq = numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(codCentro, macPrehueco);

			if (existeMapNumEtiq && cleanAll.equals("N")){
				if(procede==null){
					resultado = PREHUECOS_SEL_OPERATIVA;
				}
			}else{
				model.addAttribute("procede", procede);
				resultado = PREHUECOS_LINEAL;
			}

			if (cleanAll.equals("Y")){
				numeroEtiquetaImpresoraService.removeMapNumEtiquetaInBBDD(codCentro,macPrehueco);
			}
			Integer numEtiqueta = numeroEtiquetaImpresoraService.getNumEtiqueta(codArt,codCentro,macPrehueco);

			//Peticion 53662. Etiquetas número de veces.
			// Incrementa el número de etiquetas a imprimir para la referencia dada.
			// Si (etiqueta = true o el centro está parametrizado y no tiene ninguna etiqueta)
			if (etiqueta || (Constantes.SI.equals(centroParametrizado) && new Integer(0).equals(numEtiqueta))){
				//Incrementamos el número de etiqueta del codArt
				numEtiqueta = numeroEtiquetaImpresoraService.incNumEtiqueta(codArt.toString(), numEtiqueta.intValue(), codCentro, macPrehueco);
			}

			pdaDatosCab.setNumEti(numEtiqueta);
			pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(codCentro, macPrehueco));
			if (codArt != null){
				pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(codArt.toString(),codCentro,macPrehueco));
			}else{
				pdaDatosCab.setImpresoraActiva("NO");
			}

			if (impresoraWS.equals(Constantes.SI)){

				user.setMacPrehueco(macPrehueco);
				
				//LLamar al WS de imprimir etiquetas
				ImprimirEtiquetaRequestType imprimirEtiquetarequest = imprimirEtiquetaService.createImprimirEtiquetaRequestType();

				try{
					ImprimirEtiquetaResponseType imprimirEtiquetaResponse = imprimirEtiquetaService.imprimirEtiquetaWS(imprimirEtiquetarequest);

					if (imprimirEtiquetaService.isOkImprimirEtiquetaResponse(imprimirEtiquetaResponse)) {  // El codigo de respuesta del WS ha sido OK.

						for (int i = 0; i < imprimirEtiquetaResponse.getReferencias().length; i++){ //Hacemos un bucle porque devuelve una lista pero en
																									//este caso siempre devuelve una sola referencia
							ReferenciaResponseType refResponse = imprimirEtiquetaResponse.getReferencias()[i];
							BigInteger codigoErrorRef = refResponse.getCodigoError();
							String mensajeErrorRef = refResponse.getMensajeError();

							//El WS ha devuelto OK para la referencia consultada. 
							//Ponemos el icono de la impresora a verde.
							if (codigoErrorRef.equals(BigInteger.valueOf(0))) { 
								numeroEtiquetaImpresoraService.siNumEtiquetaInSessionEnviados();
								pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getNumEtiquetaInSessionEnviados());
								model.addAttribute("codigoArticulo", codArt);
								model.addAttribute("esPrehueco", "SI");
								resultado = "pda_p03_enviarMapNumEti";
							} else {
								//El WS no ha devuelto OK para la referencia consultada. Abrimos una ventana con el mensaje de error del WS.
								resultado = "pda_p18_impresoraPopup";

								PdaDatosPopupImpresora pdaDatosPopupImpresora = new PdaDatosPopupImpresora();
								pdaDatosPopupImpresora.setCodArt(codArt);
								pdaDatosPopupImpresora.setProcede("datosRef");
								pdaDatosPopupImpresora.setMensajeErrorWS(mensajeErrorRef);

								model.addAttribute("pdaDatosPopupImpresora", pdaDatosPopupImpresora);
							}
						}
					} else {
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
				user.setMacPrehueco(null);
			}else{
				pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getNumEtiquetaInSessionEnviados());
			}

			//Control de guardado de stock
			model.addAttribute("guardadoStockOk", guardadoStockOk);
			model.addAttribute("guardadoSfm", guardadoSfm);
			model.addAttribute("guardadoImc", guardadoImc);

			//En el caso de que sea centro Eroski se ejecutará lo siguiente, si es centro caprabo y encuentra la referencia eroski
			//relacionada con la referencia caprabo también, si no, no ejecutará lo siguiente.
			if (codArt != null && !codArt.equals("")){

				//En este caso venimos del popup y tenemos que cargar los datos de la referencia recibida.
				pdaDatosRefResultado = obtenerResultado(codArt,codCentro,mostrarFFPP, codArtRel, session);

				//Mirar si el resultado ha devuelto error
				if (pdaDatosRefResultado.getEsError() != null && !pdaDatosRefResultado.getEsError().equals("")){
					pdaError.setDescError(pdaDatosRefResultado.getEsError());
					model.addAttribute("pdaError", pdaError);
					model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}

				//Miramos si existe la foto. La búsqueda se hace con código eroski.
				FotosReferencia fotosReferencia = new FotosReferencia();
				fotosReferencia.setCodReferencia(codArt);
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					pdaDatosRefResultado.setTieneFoto("S");
				} else {
					pdaDatosRefResultado.setTieneFoto("N");
				}

				//La descripción será la del centro eroski y el código artículo caprabo será el de eroski
				pdaDatosCab.setDescArtCab(pdaDatosRefResultado.getDescArt());
				pdaDatosCab.setCodArtCaprabo(String.valueOf(pdaDatosRefResultado.getCodArt()));		

				model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
				pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosRefResultado.getCodArt()));
				model.addAttribute("pdaDatosCab", pdaDatosCab);

			}else{
				model.addAttribute("pdaDatosCab", pdaDatosCab);
			}

			/* ****************************************************************************************** */
			/* Carga INICIAL de valores de PREHUECOS LINEAL. */
			/* ****************************************************************************************** */
			prehuecosLineal.setCodCentro(codCentro);
			prehuecosLineal.setMac(mac);
			prehuecosLineal.setCodArticulo(codArt);

			if (codArt != null){
				
				// Obtener el valor del Stock Lineal de Referencias que NO estén VALIDADAS o si están VALIDADAS, que haya sido HOY.
				// Obtener el estado de la referencia consultada. Los posibles valores son,
				//
				// 0 - Sin Revisar.
				// 1 - Pendiente.
				// 2 - Revisado.
				//
				// Si no existen datos en la tabla T_MIS_PREHUECOS_LINEAL 	
				prehuecosLinealAux = prehuecosLinealService.getStockLinealEstadoRef(prehuecosLineal);
				estadoRef = prehuecosLinealAux.getEstadoRef();
				stockLineal = prehuecosLinealAux.getStockLineal();
					
				// Obtener el número de referencias SIN VALIDAR para indicarlo en el portapapeles.
				numPrehuecosSinValidar = prehuecosLinealService.getPrehuecosLineal(prehuecosLineal);
			}
			/* ****************************************************************************************** */

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
		pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosRefResultado.getCodArt()));
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("greenLink", greenLink);

		//Guardamos el estado de la impresora verde fija si es 'SI'.
		model.addAttribute("greenImpr", greenImpr);
		model.addAttribute("stockLineal", stockLineal);
		model.addAttribute("estadoRef", estadoRef);
		model.addAttribute("numPrehuecosSinValidar", numPrehuecosSinValidar);

		model.addAttribute("prehuecoSeleccionado", prehuecoSeleccionado);

		return resultado;
	}

	@RequestMapping(value = "/pdaP115PrehuecosSelOperativa", method = RequestMethod.GET)
	public String showForm(ModelMap model, @Valid final String operativa,
			@Valid final Long codArt,
			@Valid final String guardadoStockOk,
			@Valid final String guardadoSfm,
			@Valid final String guardadoImc,
			@Valid final String centroParametrizado,
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE,
			@RequestParam(value="impresoraWS", required=false, defaultValue = "NO") String impresoraWS,
			@RequestParam(value="etiqueta", required=false, defaultValue = "false") boolean etiqueta,
			@RequestParam(value="greenLink", required=false, defaultValue = "false") boolean greenLink,
			@RequestParam(value="greenImpr", required=false, defaultValue = "NO") String greenImpr,
			@RequestParam(value="cleanAll", required=false, defaultValue = "N") String cleanAll,
			@RequestParam(value="prehuecoSeleccionado", required=false, defaultValue = "N") String prehuecoSeleccionado,
			final Long codArtRel,
			final String mostrarFFPP,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = PREHUECOS_LINEAL;
		
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		PdaDatosReferencia pdaDatosRef = new PdaDatosReferencia();

		try {

			if (origenGISAE.equals("SI")){
				pdaDatosCab.setOrigenGISAE(origenGISAE);
			}
			model.addAttribute("origenGISAE", origenGISAE);

			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();
			String mac = user.getMac();
			String macPrehueco = mac+SUFIJO_PREHUECO;

			//Tratamiento de secciones
			PrehuecosLineal prehuecosLineal = new PrehuecosLineal();
			prehuecosLineal.setCodCentro(codCentro);
			prehuecosLineal.setMac(mac);

			// Si tengo que volver a la anterior pantalla de PREHUECOS.
			if (operativa != null){
					
				if (Constantes.VOLVER.equalsIgnoreCase(operativa)) {
					resultado = PREHUECOS;
				}else{

					if (Constantes.EMPEZAR.equalsIgnoreCase(operativa)) {
						numeroEtiquetaImpresoraService.removeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),macPrehueco);
					}
					
					model.addAttribute("prehuecoSeleccionado", prehuecoSeleccionado);
					resultado = PREHUECOS_LINEAL;
				}
			}

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("pdaDatosRef", pdaDatosRef);

		return resultado;
	}	

	@RequestMapping(value = "/pdaP115MostrarMensajeError", method = RequestMethod.GET)
	public String mostrarMensajeError(ModelMap model) {
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();

		pdaError.setDescError(this.messageSource.getMessage("pda_p114_preHuecos.noPrehuecosSinValidar", null, locale));
		model.addAttribute("pdaError", pdaError);
		model.addAttribute("pdaDatosCab", pdaDatosCab);

		return MENSAJE_ERROR;
	}	

	@RequestMapping(value = "/pdaP115PrehuecosLineal",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab,
							@Valid final String pdaOrigen,
							@RequestParam(value="prehuecoSeleccionado", required=false, defaultValue = "N") String prehuecoSeleccionado,
							BindingResult result,
							RedirectAttributes redirectAttributes,
							ModelMap model,
							HttpSession session,
							HttpServletRequest request,
							HttpServletResponse response) {
		
		PdaDatosReferencia pdaDatosRefResultado = new PdaDatosReferencia();	
		PrehuecosLineal prehuecosLinealAux = new PrehuecosLineal();
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();

		Long stockLineal = null;
		Long estadoRef = -1L;
		Long numPrehuecosSinValidar = 0L;

		try{
			
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");	

			//Controlamos que me llega la referencia de pantalla. Esa referencia puede ser un EAN, un código caprabo o un código de eroski
			if (pdaDatosCab.getCodArtCab() != null && !pdaDatosCab.getCodArtCab().equals("")){

				PrehuecosLineal prehuecosLineal = new PrehuecosLineal();
				Long codCentro = user.getCentro().getCodCentro();
				String mac = user.getMac();
				String macPrehueco = mac+SUFIJO_PREHUECO;

				//Llamamos al método que nos devuelve la referencia después de haber superado los controles, 
				//si se trata de balanza, etiqueta propia de Eroski, de la tabla EANS o código de referencia normal.
				PdaArticulo pdaArticulo = super.obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());

				String codigoError = pdaArticulo.getCodigoError();

				if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN)){
					pdaError.setDescError(this.messageSource.getMessage(
							"pda_p12_datosReferencia.noExisteReferencia", null, locale));
					model.addAttribute("pdaError", pdaError);
					model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}

				//La función "obtenerReferenciaTratada" devuelve un código de artículo transformado a partir del código que haya
				//introducido el usuario en la pantalla. El código introducido puede ser de tipo EAN, caprabo o Eroski. Un código
				//de tipo EAN es un código que empieza por "#" que corresponde a un código de barras. Como los mismos productos de
				//distintos centros Eroski o caprabo usan el mismo código de barras, los códigos EAN serán iguales tanto para caprabo
				//como para Eroski.En el caso de introducir un código que no sea de tipo EAN (eroski o caprabo), o lo que es lo mismo,
				//un código que no tiene un "#" delante, la función devuelve el código de caprabo o eroski tal cual se ha introducido 
				//en la función. Si se introduce un EAN devuelve el código de EROSKI correspondiente a ese EAN. En la aplicación queremos
				//trabajar con  códigos de Eroski, por lo que si lo introducido en pantalla es un EAN o un código de eroski la función devolverá
				//justo el código que necesitamos. En el caso de introducir un código caprabo, al devolver el mismo código caprabo,
				//necesitaremos realizar la transformación correspondiente a código Eroski.Aunque sepamos que el centro es de tipo
				//caprabo, hay dos posibilidades, que lo introducido sea un EAN y baste con la función obtenerReferenciaTratada, pero
				//sea necesario obtener el código de caprabo, o que lo introducido sea un código caprabo, y necesitemos el código de
				//eroski para trabajar. En el caso de ser un EAN, si llega hasta este punto significa que ha obtenido el código de eroski,
				//porque si no hubiera devuelto un error. El EAN introducido y el código eroski serán distintos en el caso de haber 
				//introducido un EAN y en el caso de haber introducido un código caprabo iguales. De este modo sabremos que transformación hacer.

				//En principio el código de artículo será el que devuelva la función. Más tarde se tratará si es de caprabo
				Long codArtEroski = pdaArticulo.getCodArt();

				//Se calcula aunque sea un centro de eroski. En el caso de ser un centro de eroski codArtEroski = codArtCaprabo.
				//En caso contrario, codArtCaprabo != codArtEroski. Como en la descripción de la pda sale codArt + descripción
				//utilizamos el codArtCaprabo para devolver el código de artículo. En el caso de ser centro Eroski devolverá la
				//referencia de eroski y en el caso de ser caprabo la de caprabo
				Long codArtCaprabo = pdaArticulo.getCodArt();

				//Es posible que al introducir un código caprabo, por fallo de la bd, no tenga un código eroski relacionado
				//por eso hay que asegurarse de tratar el posible error.
				if (codArtEroski != null){

					//Insertar referencia CAPRABO, para que se pueda ver en la descripción de la pistola. En el caso de ser
					//centro de eroski, la referencia caprabo tendrá el valor de la referencia eroski y también se verá bien.
					pdaDatosCab.setCodArtCaprabo(codArtCaprabo.toString());				

					//Insertar la referencia de eroski
					pdaDatosCab.setCodArtCab(codArtEroski.toString());

					pdaDatosRefResultado = this.obtenerResultado(new Long(pdaDatosCab.getCodArtCab()),user.getCentro().getCodCentro(),"", null, session);

					//Mirar si el resultado ha devuelto error
					if (pdaDatosRefResultado.getEsError() != null && !pdaDatosRefResultado.getEsError().equals("")){
						pdaError.setDescError(pdaDatosRefResultado.getEsError());
						model.addAttribute("pdaError", pdaError);
						model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
						model.addAttribute("pdaDatosCab", pdaDatosCab);
						logger.info("PDA Referencia erronea");
						return MENSAJE_ERROR;
					}				

					//Si cambiamos de referencia, se indica que no se guarda el estado de impresora verde
					numeroEtiquetaImpresoraService.noNumEtiquetaInSessionEnviados();
					
					//Si el centro es caprabo especial, se obtienen los motivos de AC KO caja roja. Si existen motivos,
					//en pda_p12_datosReferencia se mostrará la caja con un link
					if (user.getCentro().isEsCentroCapraboEspecial()){										
						codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), codArtEroski);

						//La descripción será la del centro eroski
						VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArtEroski);

						//Insertar la descripción de eroski
						pdaDatosCab.setDescArtCab(vDatosDiarioArtResul.getDescripArt());	

						//Obtenemos la plataforma de aprovisionamiento de las mercancias.
						PlataformaAprovisionamientoMercancia plataformaAprovisionamientoMercancia = plataformaOrigenSuministroService.obtOss(vDatosDiarioArtResul.getCodFpMadre(), user.getCentro().getCodCentro(), new Date(), null);					
						if (plataformaAprovisionamientoMercancia.getCodLocOri() != null){
							if (new Long(Constantes.PLATAFORMA_2418).equals(plataformaAprovisionamientoMercancia.getCodLocOri())){
								//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri() + "-" + Constantes.PLATAFORMA_GRUPAJE);
								plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri() + "-" + this.messageSource.getMessage("pda_p12_datosReferencia.plataformaGrupaje", null, locale));							
							}else if (new Long(Constantes.PLATAFORMA_2415).equals(plataformaAprovisionamientoMercancia.getCodLocOri())){
								//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri() + "-" + Constantes.PLATAFORMA_BAJAROTACION);
								plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri() + "-" + this.messageSource.getMessage("pda_p12_datosReferencia.bajaRotacion", null, locale));
							}else if (new Long(Constantes.PLATAFORMA_7951).equals(plataformaAprovisionamientoMercancia.getCodLocOri()) || new Long(Constantes.PLATAFORMA_7956).equals(plataformaAprovisionamientoMercancia.getCodLocOri())){
								//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(Constantes.PLATAFORMA_ABRERACENTRAL);
								plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(this.messageSource.getMessage("pda_p12_datosReferencia.plataformaCentral", null, locale));
							}else if (new Long(Constantes.PLATAFORMA_7952).equals(plataformaAprovisionamientoMercancia.getCodLocOri()) || new Long(Constantes.PLATAFORMA_7957).equals(plataformaAprovisionamientoMercancia.getCodLocOri())){
								//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(Constantes.PLATAFORMA_CIENCIASCENTRAL);
								plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(this.messageSource.getMessage("pda_p12_datosReferencia.plataformaCienciasCentral", null, locale));
							}else{
								//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri()+ "-" + Constantes.PLATAFORMA_CENTRAL);
								plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri()+ "-" + this.messageSource.getMessage("pda_p12_datosReferencia.central", null, locale));
							}
						}
						model.put("plataformaAprovisionamientoMercancia", plataformaAprovisionamientoMercancia);

					}
					//Insertar la descripción de eroski
					pdaDatosCab.setDescArtCab(pdaDatosRefResultado.getDescArt());					

					//Peticion 53662. Etiquetas número de veces.
					pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(new Long(pdaDatosCab.getCodArtCab()),user.getCentro().getCodCentro(),macPrehueco));
					pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),macPrehueco));
					if(pdaDatosCab != null && pdaDatosCab.getCodArtCab()!=null){
						pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(pdaDatosCab.getCodArtCab(),user.getCentro().getCodCentro(),macPrehueco));				
					}else{
						pdaDatosCab.setImpresoraActiva("NO");
					}
					
					//Se calcula si se tiene que enseñar el link de lanzar encargo y en el caso de existir el encargo para mostrarlo
					//en la pantalla
//					pedidoPda = pedidoPdaService.lanzarEncargoPda3(session, pdaDatosCab.getCodArtCab(), pdaDatosCab.getCodArtCaprabo(), pdaDatosCab.getDescArtCab(), pdaDatosRefResultado.getDatosDiarioArt(), response);				

					//Miramos si existe la foto. La búsqueda se hace con código eroski.
					FotosReferencia fotosReferencia = new FotosReferencia();
					fotosReferencia.setCodReferencia(codArtEroski);
					if (fotosReferenciaService.checkImage(fotosReferencia)){
						pdaDatosRefResultado.setTieneFoto("S");
					}else{
						pdaDatosRefResultado.setTieneFoto("N");
					}

				}else{
					pdaError.setDescError(this.messageSource.getMessage("pda_p12_datosReferencia.noExistenDatos", null, locale));
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
					model.addAttribute("pdaError", pdaError);
					model.addAttribute("stockLineal", stockLineal);
					logger.info("PDA. NO EXISTE referencia EROSKI para referencia CAPRABO.");
					return "pda_p03_showMessage";	
				}	

			/* ****************************************************************************************** */
								/* Carga INICIAL de valores de PREHUECOS LINEAL. */
			/* ****************************************************************************************** */
				prehuecosLineal.setCodCentro(codCentro);
				prehuecosLineal.setMac(mac);
				prehuecosLineal.setCodArticulo(codArtEroski);

				if (codArtEroski!=null){
					// Obtener el valor del Stock Lineal de Referencias que NO estén VALIDADAS o si están VALIDADAS, que haya sido HOY.
					// Obtener el estado de la referencia consultada. Los posibles valores son,
					//
					// 0 - Sin Revisar.
					// 1 - Pendiente.
					// 2 - Revisado.
					//
					// Si no existen datos en la tabla T_MIS_PREHUECOS_LINEAL 
					prehuecosLinealAux = prehuecosLinealService.getStockLinealEstadoRef(prehuecosLineal);
					stockLineal = prehuecosLinealAux.getStockLineal();
					estadoRef = prehuecosLinealAux.getEstadoRef();
					
					// Obtener el número de referencias SIN VALIDAR para indicarlo en el portapapeles.
					numPrehuecosSinValidar = prehuecosLinealService.getPrehuecosLineal(prehuecosLineal);
				}
			/* ****************************************************************************************** */

				//Insertamos el código promocional.
				pdaDatosRefResultado.setCodArtPromo(pdaArticulo.getCodArtPromo());

			//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
			}else{
				pdaError.setDescError(this.messageSource.getMessage(
						"pda_p12_datosReferencia.referenciaVacia", null, locale));		
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
				model.addAttribute("pdaError", pdaError);
				model.addAttribute("stockLineal", stockLineal);
				model.addAttribute("estadoRef", estadoRef);
				logger.info("PDA Referencia no introducida.");
				return MENSAJE_ERROR;
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
		model.addAttribute("stockLineal", stockLineal);
		model.addAttribute("estadoRef", estadoRef);
		model.addAttribute("numPrehuecosSinValidar", numPrehuecosSinValidar);
		model.addAttribute("procede", "pdaP115PrehuecosLineal");
		
		model.addAttribute("prehuecoSeleccionado", prehuecoSeleccionado);
		
//		session.setAttribute("pedidoPda", pedidoPda);

		if (!pdaOrigen.isEmpty()){
			if (pdaOrigen.equalsIgnoreCase("PHPed")){
			    redirectAttributes.addAttribute("codArt", pdaDatosCab.getCodArtCab());
			    redirectAttributes.addAttribute("procede", "pdaP115PrehuecosLineal");
			    redirectAttributes.addAttribute("sufijoPrehueco", "_PREHUECO");
			    return "redirect:pdaP13SegPedidos.do";
			}else if (pdaOrigen.equalsIgnoreCase("PHStock")){
			    redirectAttributes.addAttribute("codArt", pdaDatosCab.getCodArtCab());
			    redirectAttributes.addAttribute("procede", "pdaP115PrehuecosLineal");
			    redirectAttributes.addAttribute("sufijoPrehueco", "_PREHUECO");
			    return "redirect:pdaP15MovStocks.do";
			}
		}
		
		return PREHUECOS_LINEAL;

	}

	@RequestMapping(value = "/pdaP115PrehuecosDesdeStockAlmacen", method = RequestMethod.GET)
	public String processForm(@RequestParam(value = "codArt", required = false) String codArt,
			@Valid final String origenPantalla,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		PdaDatosReferencia pdaDatosRefResultado = new PdaDatosReferencia();	
		PrehuecosLineal prehuecosLinealAux = new PrehuecosLineal();
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		Long stockLineal = null;
		Long numPrehuecosSinValidar = 0L;
		
		try{
			
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");	
			pdaDatosCab.setCodArtCab(codArt);

			PrehuecosLineal prehuecosLineal = new PrehuecosLineal();
			Long codCentro = user.getCentro().getCodCentro();
			String mac = user.getMac();
			String macPrehueco = mac+SUFIJO_PREHUECO;

			//Llamamos al método que nos devuelve la referencia después de haber superado los controles, 
			//si se trata de balanza, etiqueta propia de Eroski, de la tabla EANS o código de referencia normal.
			PdaArticulo pdaArticulo = super.obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());

			String codigoError = pdaArticulo.getCodigoError();

			if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN)){
				pdaError.setDescError(this.messageSource.getMessage(
						"pda_p12_datosReferencia.noExisteReferencia", null, locale));
				model.addAttribute("pdaError", pdaError);
				model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				logger.info("PDA Referencia erronea");
				return "pda_p03_showMessage";
			}

			//La función "obtenerReferenciaTratada" devuelve un código de artículo transformado a partir del código que haya
			//introducido el usuario en la pantalla. El código introducido puede ser de tipo EAN, caprabo o Eroski. Un código
			//de tipo EAN es un código que empieza por "#" que corresponde a un código de barras. Como los mismos productos de
			//distintos centros Eroski o caprabo usan el mismo código de barras, los códigos EAN serán iguales tanto para caprabo
			//como para Eroski.En el caso de introducir un código que no sea de tipo EAN (eroski o caprabo), o lo que es lo mismo,
			//un código que no tiene un "#" delante, la función devuelve el código de caprabo o eroski tal cual se ha introducido 
			//en la función. Si se introduce un EAN devuelve el código de EROSKI correspondiente a ese EAN. En la aplicación queremos
			//trabajar con  códigos de Eroski, por lo que si lo introducido en pantalla es un EAN o un código de eroski la función devolverá
			//justo el código que necesitamos. En el caso de introducir un código caprabo, al devolver el mismo código caprabo,
			//necesitaremos realizar la transformación correspondiente a código Eroski.Aunque sepamos que el centro es de tipo
			//caprabo, hay dos posibilidades, que lo introducido sea un EAN y baste con la función obtenerReferenciaTratada, pero
			//sea necesario obtener el código de caprabo, o que lo introducido sea un código caprabo, y necesitemos el código de
			//eroski para trabajar. En el caso de ser un EAN, si llega hasta este punto significa que ha obtenido el código de eroski,
			//porque si no hubiera devuelto un error. El EAN introducido y el código eroski serán distintos en el caso de haber 
			//introducido un EAN y en el caso de haber introducido un código caprabo iguales. De este modo sabremos que transformación hacer.

			//En principio el código de artículo será el que devuelva la función. Más tarde se tratará si es de caprabo
			Long codArtEroski = pdaArticulo.getCodArt();

			//Se calcula aunque sea un centro de eroski. En el caso de ser un centro de eroski codArtEroski = codArtCaprabo.
			//En caso contrario, codArtCaprabo != codArtEroski. Como en la descripción de la pda sale codArt + descripción
			//utilizamos el codArtCaprabo para devolver el código de artículo. En el caso de ser centro Eroski devolverá la
			//referencia de eroski y en el caso de ser caprabo la de caprabo
			Long codArtCaprabo = pdaArticulo.getCodArt();

			//Es posible que al introducir un código caprabo, por fallo de la bd, no tenga un código eroski relacionado
			//por eso hay que asegurarse de tratar el posible error.
			if (codArtEroski != null){

				//Insertar referencia CAPRABO, para que se pueda ver en la descripción de la pistola. En el caso de ser
				//centro de eroski, la referencia caprabo tendrá el valor de la referencia eroski y también se verá bien.
				pdaDatosCab.setCodArtCaprabo(codArtCaprabo.toString());				

				//Insertar la referencia de eroski
				pdaDatosCab.setCodArtCab(codArtEroski.toString());

				pdaDatosRefResultado = this.obtenerResultado(new Long(pdaDatosCab.getCodArtCab()),user.getCentro().getCodCentro(),"", null, session);

				//Mirar si el resultado ha devuelto error
				if (pdaDatosRefResultado.getEsError() != null && !pdaDatosRefResultado.getEsError().equals("")){
					pdaError.setDescError(pdaDatosRefResultado.getEsError());
					model.addAttribute("pdaError", pdaError);
					model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}				

				//Si cambiamos de referencia, se indica que no se guarda el estado de impresora verde
				numeroEtiquetaImpresoraService.noNumEtiquetaInSessionEnviados();
				
				//Si el centro es caprabo especial, se obtienen los motivos de AC KO caja roja. Si existen motivos,
				//en pda_p12_datosReferencia se mostrará la caja con un link
				if (user.getCentro().isEsCentroCapraboEspecial()){										
					codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), codArtEroski);

					//La descripción será la del centro eroski
					VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArtEroski);

					//Insertar la descripción de eroski
					pdaDatosCab.setDescArtCab(vDatosDiarioArtResul.getDescripArt());	

					//Obtenemos la plataforma de aprovisionamiento de las mercancias.
					PlataformaAprovisionamientoMercancia plataformaAprovisionamientoMercancia = plataformaOrigenSuministroService.obtOss(vDatosDiarioArtResul.getCodFpMadre(), user.getCentro().getCodCentro(), new Date(), null);					
					if (plataformaAprovisionamientoMercancia.getCodLocOri() != null){
						if (new Long(Constantes.PLATAFORMA_2418).equals(plataformaAprovisionamientoMercancia.getCodLocOri())){
							//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri() + "-" + Constantes.PLATAFORMA_GRUPAJE);
							plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri() + "-" + this.messageSource.getMessage("pda_p12_datosReferencia.plataformaGrupaje", null, locale));							
						}else if (new Long(Constantes.PLATAFORMA_2415).equals(plataformaAprovisionamientoMercancia.getCodLocOri())){
							//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri() + "-" + Constantes.PLATAFORMA_BAJAROTACION);
							plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri() + "-" + this.messageSource.getMessage("pda_p12_datosReferencia.bajaRotacion", null, locale));
						}else if (new Long(Constantes.PLATAFORMA_7951).equals(plataformaAprovisionamientoMercancia.getCodLocOri()) || new Long(Constantes.PLATAFORMA_7956).equals(plataformaAprovisionamientoMercancia.getCodLocOri())){
							//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(Constantes.PLATAFORMA_ABRERACENTRAL);
							plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(this.messageSource.getMessage("pda_p12_datosReferencia.plataformaCentral", null, locale));
						}else if (new Long(Constantes.PLATAFORMA_7952).equals(plataformaAprovisionamientoMercancia.getCodLocOri()) || new Long(Constantes.PLATAFORMA_7957).equals(plataformaAprovisionamientoMercancia.getCodLocOri())){
							//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(Constantes.PLATAFORMA_CIENCIASCENTRAL);
							plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(this.messageSource.getMessage("pda_p12_datosReferencia.plataformaCienciasCentral", null, locale));
						}else{
							//plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri()+ "-" + Constantes.PLATAFORMA_CENTRAL);
							plataformaAprovisionamientoMercancia.setPlataformaCapraboOrion(plataformaAprovisionamientoMercancia.getCodLocOri()+ "-" + this.messageSource.getMessage("pda_p12_datosReferencia.central", null, locale));
						}
					}
					model.put("plataformaAprovisionamientoMercancia", plataformaAprovisionamientoMercancia);

				}
				//Insertar la descripción de eroski
				pdaDatosCab.setDescArtCab(pdaDatosRefResultado.getDescArt());					

				//Peticion 53662. Etiquetas número de veces.
				pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(new Long(pdaDatosCab.getCodArtCab()),user.getCentro().getCodCentro(),macPrehueco));
				pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),macPrehueco));
				if(pdaDatosCab != null && pdaDatosCab.getCodArtCab()!=null){
					pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(pdaDatosCab.getCodArtCab(),user.getCentro().getCodCentro(),macPrehueco));				
				}else{
					pdaDatosCab.setImpresoraActiva("NO");
				}
				
				//Se calcula si se tiene que enseñar el link de lanzar encargo y en el caso de existir el encargo para mostrarlo
				//en la pantalla
//				pedidoPda = pedidoPdaService.lanzarEncargoPda3(session, pdaDatosCab.getCodArtCab(), pdaDatosCab.getCodArtCaprabo(), pdaDatosCab.getDescArtCab(), pdaDatosRefResultado.getDatosDiarioArt(), response);				

				//Miramos si existe la foto. La búsqueda se hace con código eroski.
				FotosReferencia fotosReferencia = new FotosReferencia();
				fotosReferencia.setCodReferencia(codArtEroski);
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					pdaDatosRefResultado.setTieneFoto("S");
				}else{
					pdaDatosRefResultado.setTieneFoto("N");
				}

			}else{
				pdaError.setDescError(this.messageSource.getMessage("pda_p12_datosReferencia.noExistenDatos", null, locale));
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
				model.addAttribute("pdaError", pdaError);
				model.addAttribute("stockLineal", stockLineal);
				logger.info("PDA NO EXISTE REFERENCIA EROSKI PARA REFERENCIA CAPRABO");
				return "pda_p03_showMessage";	
			}	
			
		/* ****************************************************************************************** */
							/* Carga INICIAL de valores de PREHUECOS LINEAL. */
		/* ****************************************************************************************** */
			prehuecosLineal.setCodCentro(codCentro);
			prehuecosLineal.setMac(mac);
			prehuecosLineal.setCodArticulo(codArtEroski);

			if (codArtEroski!=null){
				// Obtener el valor del Stock Lineal de Referencias que NO estén VALIDADAS o si están VALIDADAS que haya sido HOY.
				prehuecosLinealAux = prehuecosLinealService.getStockLinealEstadoRef(prehuecosLineal);
				stockLineal = prehuecosLinealAux.getStockLineal();
				
				// Obtener el número de referencias SIN VALIDAR para indicarlo en el portapapeles.
				numPrehuecosSinValidar = prehuecosLinealService.getPrehuecosLineal(prehuecosLineal);
			}
		/* ****************************************************************************************** */

			//Insertamos el código promocional.
			pdaDatosRefResultado.setCodArtPromo(pdaArticulo.getCodArtPromo());

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
		model.addAttribute("stockLineal", stockLineal);
		model.addAttribute("numPrehuecosSinValidar", numPrehuecosSinValidar);
//		session.setAttribute("pedidoPda", pedidoPda);

		return PREHUECOS_LINEAL;
	}

	//Borrar los Prehuecos.
	@RequestMapping(value = "/pdaP115BorrarPrehuecos", method = RequestMethod.POST)
	public @ResponseBody PrehuecosLineal pdaP115BorrarPrehuecos( @RequestParam(value = "codArt", required = true) Long codArt
									  , ModelMap model
									  , HttpServletResponse response
									  ,	HttpSession session) throws Exception{

		PrehuecosLineal prehuecosLineal = new PrehuecosLineal();
		PrehuecosLineal prehuecosLinealAux = new PrehuecosLineal();
		Boolean hayCambios = Boolean.FALSE;
		Long estadoRef = -1L;
		Long numPrehuecosSinValidar = 0L;

		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");	
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		prehuecosLineal.setCodCentro(codCentro);
		prehuecosLineal.setMac(mac);
		prehuecosLineal.setCodArticulo(codArt);
		
		hayCambios = prehuecosLinealService.deletePrehuecos(prehuecosLineal)>0;
		
		// SÓLO ACTUALIZAR el valor de las referencias pendiente de validar
		// del portapapeles cuando se ha borrado al menos un registro.
		//
		// Si NO se ha BORRADO ningún registro, no es necesario actualizar el valor.
		if (hayCambios){
			// Obtener el estado de la referencia consultada. Los posibles valores son,
			//
			// 0 - Sin Revisar.
			// 1 - Pendiente.
			// 2 - Revisado.
			//
			// Si no existen datos en la tabla T_MIS_PREHUECOS_LINEAL 
			prehuecosLinealAux = prehuecosLinealService.getStockLinealEstadoRef(prehuecosLineal);
			estadoRef = prehuecosLinealAux.getEstadoRef();
			
			numPrehuecosSinValidar = prehuecosLinealService.getPrehuecosLineal(prehuecosLineal);
		}
		
		prehuecosLineal.setNumPrehuecos(numPrehuecosSinValidar);
		prehuecosLineal.setEstadoRef(estadoRef);

//		Map<String, Long> result = new HashMap<String, Long>();
//		result.put("numPrehuecos", numPrehuecosSinValidar);
//		result.put("estadoRef", estadoRef);
		
		return prehuecosLineal;
	}

	//Alta Prehuecos.
	@RequestMapping(value = "/pdaP115AltaPrehuecos", method = RequestMethod.POST)
	public @ResponseBody PrehuecosLineal pdaP115AltaPrehuecos( @RequestParam(value = "codArt", required = true) Long codArt
									, @RequestParam(value = "stockLineal", required = true) Long stockLineal
									
									/***
									 * @author BICUGUAL MISUMI-664
									 * Logger para tracear problema en prehuecos 
									 */
									, HttpServletRequest request 
									, @RequestParam(value = "origenPantalla") String origenPantalla
									, @RequestParam(value = "procede") String procede
									, @RequestParam(value = "pathName") String pathName
									, @RequestParam(value = "pdaOrigen") String pdaOrigen
									/******/
									
									, ModelMap model
									, HttpServletResponse response
									, HttpSession session) throws Exception{

		PrehuecosLineal prehuecosLineal = new PrehuecosLineal();
		PrehuecosLineal prehuecosLinealAux = new PrehuecosLineal();

		Long estadoRef = -1L;
		Long numPrehuecosSinValidar = 0L;
		Boolean hayCambios = Boolean.FALSE;

		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");	
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		prehuecosLineal.setCodCentro(codCentro);
		prehuecosLineal.setMac(mac);
		prehuecosLineal.setCodArticulo(codArt);
		prehuecosLineal.setStockLineal(stockLineal);
		
		if (codArt!=null){
			numPrehuecosSinValidar = prehuecosLinealService.getPrehuecosSinValidar(prehuecosLineal);
			
			/***
			 * @author BICUGUAL MISUMI-664
			 * Logger para tracear problema en prehuecos 
			 */
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			paramCentrosOpc.setOpcHabil(Constantes.PERMISO_127_PDA_PREHUECOS);

			if (this.paramCentrosOpcService.findAllCont(paramCentrosOpc).longValue() == 0) {
				
				logger.error("INICIO TRACEO PREHUECO-LINEAL -----------------------------------");
				logger.error("URL del request: " + request.getRequestURL().toString());
				logger.error("URL de la pantalla: " + pathName);
				logger.error("PdaOrigen: " + pdaOrigen);
				logger.error("OrigenPantalla: " + origenPantalla);
				logger.error("Procede: " + procede);
				logger.error("CodCentro: " + user.getCentro().getCodCentro());
				logger.error("MAC: " + user.getMac());
				logger.error((numPrehuecosSinValidar > 0)? "Update >>>>" : "Insert >>>>");
				logger.error("codArticulo: " + codArt);
				logger.error("stockLineal: " + stockLineal);
				logger.error("FIN TRACEO PREHUECO-LINEAL -----------------------------------");
				
			}

			
			if (numPrehuecosSinValidar > 0){
				hayCambios = prehuecosLinealService.updatePrehuecos(prehuecosLineal)>0;
			}else{
				hayCambios = prehuecosLinealService.insertPrehuecos(prehuecosLineal)>0;
			}
			
			if (hayCambios){
				// Obtener el estado de la referencia consultada. Los posibles valores son,
				//
				// 0 - Sin Revisar.
				// 1 - Pendiente.
				// 2 - Revisado.
				//
				// Si no existen datos en la tabla T_MIS_PREHUECOS_LINEAL
				prehuecosLinealAux = prehuecosLinealService.getStockLinealEstadoRef(prehuecosLineal);
				estadoRef = prehuecosLinealAux.getEstadoRef();
	
				numPrehuecosSinValidar = prehuecosLinealService.getPrehuecosLineal(prehuecosLineal);
			}
			
			prehuecosLineal.setNumPrehuecos(numPrehuecosSinValidar);
			prehuecosLineal.setEstadoRef(estadoRef);
		}

		return prehuecosLineal;
	}

	private PdaDatosReferencia obtenerResultado(Long codArt, Long codCentro, String mostradoFFPP, Long codaArtRel, HttpSession session) throws Exception{
		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosReferencia pdaDatosRef = new PdaDatosReferencia();
		pdaDatosRef.setCodArt(codArt);
		pdaDatosRef.setCodArtRel(codaArtRel);

		VDatosDiarioArt vDatosDiarioArtResul = this.obtenerDiarioArt(codArt);
		pdaDatosRef.setDatosDiarioArt(vDatosDiarioArtResul);

		//Saber si el centro es de caprabo para obtener los mapas o no
		User user = (User)session.getAttribute("user");

		ReferenciasCentro referenciasCentro = new ReferenciasCentro();
		referenciasCentro.setCodArt(codArt);

		//Para buscar en t_mismcg_caprabo es necesaria la referencia del artículo caprabizada. Como en 
		//esta función siempre entran en modo Eroski, es necesario caprabizar la referencia.
		boolean isCaprabo = false;
		

		referenciasCentro.setCodCentro(codCentro);
		referenciasCentro.setDiarioArt(vDatosDiarioArtResul);

		boolean isCapraboEspecial = false;
		
		// Miramos si tiene tratamiento Vegalsa
		boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), codArt);
		referenciasCentro.setEsTratamientoVegalsa(tratamientoVegalsa);
		pdaDatosRef.setTratamientoVegalsa(tratamientoVegalsa);

		//Es necesario saber 
		VSurtidoTienda surtidoTienda = this.obtenerSurtidoTienda(referenciasCentro,isCaprabo,isCapraboEspecial);

		//Controlamos si se ha introducido una referencia que no existe.
		if (vDatosDiarioArtResul == null){
			pdaDatosRef.setEsError(this.messageSource.getMessage(
					"pda_p12_datosReferencia.noExisteReferencia", null, locale));
			return pdaDatosRef;
		}

		//Obtenemos la descripción del artículo.
		if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null){
			pdaDatosRef.setDescArt(vDatosDiarioArtResul.getDescripArt());
		}

		//Obtenemos U/C
		if (surtidoTienda != null && surtidoTienda.getUniCajaServ()!= null){
			pdaDatosRef.setUniCajaServ(Utilidades.convertirDoubleAString(surtidoTienda.getUniCajaServ().doubleValue(),"###0.00"));
		}

		/* inicio  cambios 48332 */
		//Obtener referencias promocionales
		RelacionArticulo relacionArticulo1 = new RelacionArticulo();
		relacionArticulo1.setCodArt(codArt);
		relacionArticulo1.setCodCentro(codCentro);
		List<Long> referencias = this.relacionArticuloService.findAll(relacionArticulo1);

		//Obtenemos Pendientes 1/2
		PendientesRecibir pr = new PendientesRecibir();
		pr.setCodArt(codArt);
		pr.setCodCentro(codCentro);
		if (!referencias.isEmpty()){
			pr.setReferencias(referencias);
		}
		pr = this.pendientesRecibirService.find(pr, null);
		pdaDatosRef.setCantHoy(pr.getCantHoy());
		pdaDatosRef.setCantFutura(pr.getCantFutura());

		if (null != session.getAttribute("consultaStock")){
			pdaDatosRef.setStockActivo("S");
		}else{
			pdaDatosRef.setStockActivo("N");
		}

		/* fin  cambios 48332 */

		//Obtenemos el tipo de aprovisionamiento.
		pdaDatosRef.setTipoAprov(obtenerTipoAprov(surtidoTienda));

		//Control para mostrar pedido activo o no. Si es N se enseñará la caja AC KO roja
		//con link en el caso de ser una referencia de centro eroski. Si es un centro caprabo,
		//habrá que ver que exista algún motivo. Gracias a este atributo se consigue montar el
		//AC KO o AC OK de pda_p12_datosReferencia.jsp
		pdaDatosRef.setPedidoActivo(obtenerPedidoActivo(surtidoTienda,isCaprabo));

		//Obtenemos pedir
		if (surtidoTienda != null && surtidoTienda.getPedir()!= null && !"".equals(surtidoTienda.getPedir())){
			pdaDatosRef.setPedir(surtidoTienda.getPedir());
		}else{
			if(user.getCentro().esCentroCaprabo() || user.getCentro().esCentroCapraboEspecial()){
				pdaDatosRef.setPedir(" ");
			}else{
				pdaDatosRef.setPedir("N");
			}
		}

		//Obtener MMC
		if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro()!= null && !"".equals(surtidoTienda.getMarcaMaestroCentro())){
			pdaDatosRef.setMMC(surtidoTienda.getMarcaMaestroCentro());
		}else{
			pdaDatosRef.setMMC("N");
		}

		if (mostradoFFPP != null && mostradoFFPP.equals("S")){
			//En este caso venimos de una referencia.
			pdaDatosRef.setMostrarFFPP("N");
		}else if (mostradoFFPP != null && mostradoFFPP.equals("N")){
			//En este caso vamos al artÃ­culo relacionado de una referencia.
			pdaDatosRef.setMostrarFFPP("S");
		}else{
			VRelacionArticulo relacionArticulo = obtenerRelacionArticulo(referenciasCentro);
			if (relacionArticulo != null && relacionArticulo.getCodArtRela() != null){
				if (tratamientoVegalsa){
					if(relacionArticulo.getFormatoProductivoActivo() !=null && relacionArticulo.getFormatoProductivoActivo().equals("S")){
						pdaDatosRef.setMostrarFFPP("S");
						pdaDatosRef.setCodArtRel(relacionArticulo.getCodArtRela());
					}
				}else{
					pdaDatosRef.setMostrarFFPP("S");
					pdaDatosRef.setCodArtRel(relacionArticulo.getCodArtRela());
				}
			}else{ 
				//Comprobación de artículo unitario
				VRelacionArticulo relacionArticuloUnitario = obtenerRelacionArticuloUnitario(referenciasCentro);
				if (relacionArticuloUnitario != null && relacionArticuloUnitario.getCodArt() != null){
					if(tratamientoVegalsa){
						if(relacionArticuloUnitario.getFormatoProductivoActivo() !=null && relacionArticuloUnitario.getFormatoProductivoActivo().equals("S")){
							pdaDatosRef.setMostrarFFPP("N");
							pdaDatosRef.setCodArtRel(relacionArticuloUnitario.getCodArt());
						}
					}else{
						pdaDatosRef.setMostrarFFPP("N");
						pdaDatosRef.setCodArtRel(relacionArticuloUnitario.getCodArt());
					}
				}
			}
		}

		//Cargamos los datos de las variables de pedido
		pdaDatosRef = cargarVariablesPedido(referenciasCentro,pdaDatosRef);

		//Cargamos los datos de la oferta
		pdaDatosRef = cargarOferta(referenciasCentro,pdaDatosRef);

		//Cargamos los datos de venta media.
		ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();

		referenciasCentroIC.setCodArt(codArt);
		referenciasCentroIC.setCodCentro(codCentro);

		HistoricoVentaMedia historicoVentaMedia = obtenerHistoricoVentaMedia(referenciasCentroIC);
		referenciasCentroIC.setHistoricoVentaMedia(historicoVentaMedia);

		pdaDatosRef = cargarDatosVentaMedia(historicoVentaMedia,pdaDatosRef);


		//Añadimos la imagen comercial
		ImagenComercial imagenComercial = imagenComercialService.consultaImc(referenciasCentro.getCodCentro(), referenciasCentro.getCodArt());

		if (imagenComercial.getMetodo() != null){
			imagenComercial.setTratamientoVegalsa(tratamientoVegalsa?1:0);
			pdaDatosRef.setImagenComercial(imagenComercial);

			boolean esSfm = (Constantes.IMC_SFM == imagenComercial.getMetodo());
			if (!esSfm){
				//Se trata de Capacidad.
				pdaDatosRef = cargarDatosCapacidad(referenciasCentroIC,pdaDatosRef,codCentro,session);
			}
		}

		if (tratamientoVegalsa){
			
			BigInteger[] arrayReferencias = new BigInteger[10];
			ConsultaFacingVegalsaRequestType facingVegalsaRequest = new ConsultaFacingVegalsaRequestType();

			arrayReferencias[0] = BigInteger.valueOf(referenciasCentro.getCodArt());
			facingVegalsaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
			facingVegalsaRequest.setTipo(Constantes.CONSULTAR_FACING);
			facingVegalsaRequest.setListaReferencias(arrayReferencias);

			try{
				ConsultaFacingVegalsaResponseType facingVegalsaResponse = facingVegalsaService.consultarFacing(facingVegalsaRequest, session);
	
				// Si la consulta al WS ha ido bien
				if (facingVegalsaResponse.getCodigoRespuesta().equals("OK")){
	
					for (ReferenciaTypeResponse referencia : facingVegalsaResponse.getReferencias()){
	
						// Si Referencia OK
						if (new BigInteger("0").equals(referencia.getCodigoError())){
							
							imagenComercial.setCentro(user.getCentro().getCodCentro());
							imagenComercial.setReferencia(referenciasCentro.getCodArt());
							imagenComercial.setCapacidad(referencia.getCapacidad().longValue());
							imagenComercial.setFacing(referencia.getFacing().longValue());
							imagenComercial.setMultiplicador(referencia.getFondo().intValue());
							imagenComercial.setCodError(referencia.getCodigoError().intValue());
							imagenComercial.setDescripcionError(referencia.getMensajeError());
							
							// Registramos si el centro tiene permiso de modificación del Facing.
							// En el caso de ser un centro VEGALSA, no debería tener permiso de moficación.
							boolean facingModificable = facingVegalsaService.isFacingModificable(user.getCentro().getCodCentro());
							imagenComercial.setFacingModificable(facingModificable?1:0);
							imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_OK);
							
						} else {
							imagenComercial.setCodError(referencia.getCodigoError().intValue());
							imagenComercial.setDescripcionError(referencia.getMensajeError());
							imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
						}
					}
				} else {
					imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
				}
	
				imagenComercial.setTratamientoVegalsa(tratamientoVegalsa?1:0);
				pdaDatosRef.setImagenComercial(imagenComercial);
				
			}catch(Exception e){
				imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
				pdaDatosRef.setImagenComercial(imagenComercial);
			}
		}
		
		ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
		stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
		List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
		listaReferencias.add(BigInteger.valueOf(codArt));

		// DESCOMENTARLO UNA VEZ ESTE HECHO EL NUEVO SERVICIO DE TEXTIL. CORRESPONDE A LA PETICION 47885 (LA PARTE DE PISTOLA)
		// SE ACTIVA SOLO PARA LOS CENTROS: 5042-SEGOVIA, 0358-ABADIÑO, 0175-IRUÑA, 5326-INFANTE, 410 EIBAR, 164 GERNIKA
		//Si la referencia consultada es TEXTIL, se deben obtener todas las referencia que compartan el mismo código de proveedor.
		stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA); //En este caso es una consulta basica, al 

		//WS se le pasara una lista de referencias
		List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(codArt);


		for (Long articulo : referenciasMismoModeloProveedor) {
			String strArticulo = articulo + "";
			if (!strArticulo.equals(codArt + "")) { //El articulo consultado por el usuario  (el de la cabecera) ya esta metido en la lista
				listaReferencias.add(BigInteger.valueOf(articulo));
			}
		}

		stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
		try{
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: pdaP12DatosReferenciaController(1)	 ############################");
				logger.error("###########################################################################################################");
			}

			ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);

			if (stockTiendaResponse.getCodigoRespuesta().equals("OK")){//Pet. 53787
				//Si la referencia no es madre y además no es textil, se indica que se calculará el stock mediante el WS de stock con el valor CC
				//al pinchar el link del stock.
				if(vDatosDiarioArtResul.getGrupo1() != 3 && (stockTiendaResponse == null || stockTiendaResponse.getTipoMensaje() == null || 						
						!stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE))){
					pdaDatosRef.setCalculoCC("S");
				}else{
					pdaDatosRef.setCalculoCC("N");
				}

				//Miramos que al menos una referencia sea correcta.
				boolean alMenosUnaReferenciaCorrecta = false;
				for(ReferenciaType referencia:stockTiendaResponse.getListaReferencias()){
					if(!(new BigInteger("1")).equals(referencia.getCodigoError())){
						alMenosUnaReferenciaCorrecta = true;
					}
				}

				if (null != stockTiendaResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta()) && alMenosUnaReferenciaCorrecta){					
					session.setAttribute("consultaStock", stockTiendaResponse);
					pdaDatosRef.setStockActivo("S");
				}else{
					session.removeAttribute("consultaStock");
					pdaDatosRef.setStockActivo("N");
				}

				if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
					pdaDatosRef.setStockActual(Utilidades.convertirDoubleAString(stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue(),"###0.#"));
				} else {
					pdaDatosRef.setStockActual(Utilidades.convertirDoubleAString(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue(),"###0.#"));				
				}

				//Petición 48890. Calculo de los dias de stock. Los dias de stock  se calcularÃ¡n como stock actual / venta media

				Float ventaMedia = new Float(0);
				if (historicoVentaMedia != null){
					historicoVentaMedia.recalcularVentasMedia();
					ventaMedia = historicoVentaMedia.getMedia();
				}

				if ((ventaMedia != null && ventaMedia != 0.0) && (pdaDatosRef.getStockActivo()!= null)){
					double resultado = 0;
					resultado = new Double(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue() / ventaMedia); 
					pdaDatosRef.setDiasStock("->" + Utilidades.convertirDoubleAString(resultado,"###0.#"));
					pdaDatosRef.setVentaMedia(ventaMedia);
					pdaDatosRef.setExisteVentaMedia(true);
				} else {
					pdaDatosRef.setExisteVentaMedia(false);
					pdaDatosRef.setDiasStock("");
				}
			} else {
				pdaDatosRef.setStockActual("Error");
				pdaDatosRef.setStockActivo("N");
			}	

		}catch (Exception e) {
			session.removeAttribute("consultaStock");
			pdaDatosRef.setStockActual("Error");
			pdaDatosRef.setStockActivo("N");
		}	

		if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones()) 
				&& !user.getCentro().getOpcHabil().isEmpty() && user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_CONTROL_STOCK_24) != -1){
			RelacionArticulo relacionArticuloRela = new RelacionArticulo();
			relacionArticuloRela.setCodArtRela(referenciasCentro.getCodArt());
			relacionArticuloRela.setCodCentro(referenciasCentro.getCodCentro());
			List<Long> referenciasRela = this.relacionArticuloService.findAll(relacionArticuloRela);

			if ((pdaDatosRef.getMostrarFFPP() == null || !pdaDatosRef.getMostrarFFPP().equals("N")) && !Constantes.ERROR_TENGO_MUCHO_POCO_PISTOLA.equals(pdaDatosRef.getStockActual()) && referenciasRela.isEmpty()){
				pdaDatosRef.setValoresStock(this.obtenerValoresStock(referenciasCentro, pdaDatosRef, historicoVentaMedia, vDatosDiarioArtResul, session));
			}
		}

		//Petición 48890. Movimimiento continuo de gama. Se llama al ws de PlanogramaCentro.
		ReferenciasCentro vReferenciasCentro = new ReferenciasCentro();
		vReferenciasCentro.setCodCentro(codCentro);
		vReferenciasCentro.setCodArt(codArt);

		//Obtener flag de deposito brita
		pdaDatosRef.setFlgDepositoBrita(this.obtenerFlgDepositoBrita(vReferenciasCentro));

		//Obtener flag de referencia por catálogo
		pdaDatosRef.setFlgPorCatalogo(this.obtenerFlgPorCatalogo(vReferenciasCentro, vDatosDiarioArtResul));

		//Obtener el tipo de rotacion
		pdaDatosRef.setTipoRotacion(this.obtenerTipoRotacion(pdaDatosRef, codCentro));

		//Obtener el USS. Sólo para area 4 y 5
		pdaDatosRef.setEsUSS(this.obtenerEsUSS(vDatosDiarioArtResul));

		ConsultaPlanogramaPorReferenciaResponseType result = this.planogramaCentroService.findPlanogramasCentroWS(vReferenciasCentro,session);

		if (result!=null){
			
			if (result.getCodigoRespuesta().equals("0")){
				if (result.getPlanogramaReferencia(0).getSustituidaPor() != null) { pdaDatosRef.setSustituidaPor(result.getPlanogramaReferencia(0).getSustituidaPor().longValue()); }
				if (result.getPlanogramaReferencia(0).getSustitutaDe() != null) { pdaDatosRef.setSustitutaDe(result.getPlanogramaReferencia(0).getSustitutaDe().longValue()); }
				if (result.getPlanogramaReferencia(0).getImplantancion() != null) { pdaDatosRef.setImplantacion(result.getPlanogramaReferencia(0).getImplantancion()); }
				if (result.getPlanogramaReferencia(0).getFechaActivacion() != null) { 
					pdaDatosRef.setFechaActivacion(result.getPlanogramaReferencia(0).getFechaActivacion().getTime()); 
					//Obtenemos la fecha generacion de surtido tienda. Fecha en la que la referencia pasa de PEDIR N a PEDIR S.
					Date FechaGenSurtidoTienda = vSurtidoTiendaService.obtenerFechaGeneracionSurtidoTienda(surtidoTienda);
					if (FechaGenSurtidoTienda != null) {

						pdaDatosRef.setFechaGen(FechaGenSurtidoTienda); 
						Format formatterDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy", locale);
						Integer intDiaSemana = Utilidades.getNumeroDiaSemana(FechaGenSurtidoTienda, locale);
						StringBuffer sb = new StringBuffer();
						if (intDiaSemana != null && 1 <= intDiaSemana.intValue() && intDiaSemana.intValue() <= 7){
							StringBuffer sb1 = new StringBuffer("calendario.").append(intDiaSemana.intValue());
							String letra = messageSource.getMessage(sb1.toString(), null, locale);
							sb.append(letra);
							sb.append(" ");
						}
						sb.append(formatterDDMMYYYY.format(FechaGenSurtidoTienda));
						pdaDatosRef.setStrFechaGen(sb.toString()); 

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
							pdaDatosRef.setMostrarFechaGen(true);
						}
						else{
							pdaDatosRef.setMostrarFechaGen(false);
						}
						//End: Petición 55001.
					}
				}

				//Comprobamos si el WS devuelve el campo SustituidaPor o SustitutaDe o ninguno de los dos campos.
				if (pdaDatosRef.getSustituidaPor() != null && pdaDatosRef.getSustituidaPor() != 0) {
					pdaDatosRef.setMostrarSustPorRef("S");
					pdaDatosRef.setMostrarSustARef("N");
				} else if (pdaDatosRef.getSustitutaDe() != null && pdaDatosRef.getSustitutaDe() != 0) {
					pdaDatosRef.setMostrarSustARef("S");
					pdaDatosRef.setMostrarSustPorRef("N");
				} else {
					pdaDatosRef.setMostrarSustARef("N");
					pdaDatosRef.setMostrarSustPorRef("N");
				}

				//Miramos si el WS devuelve el campo implantaciÃ³n. Si devuleve implantaciÃ³n habra que comprobar si este se pinta en rojo o verde 
				//en la pestaÃ±a Imagen comercial. 
				if ((pdaDatosRef.getImplantacion() != null)  && !(pdaDatosRef.getImplantacion().trim().equals(""))) {

					//comprobamos si existe Fecha Generacion. 
					if (pdaDatosRef.getFechaGen() != null) { 

						pdaDatosRef.setFlgColorImplantacion("VERDE");		

					} else {

						//comprobamos si el STOCK es mayor a 0 o si es 0 pero no ha pasado mas de un mes desde que se ha puesto a cero. En ese caso lo pintamos a rojo

						referenciasCentro.setSurtidoTienda(surtidoTienda);
						if (vSurtidoTiendaService.comprobarStockMayorACero(referenciasCentro.getSurtidoTienda()) > 0 ){
							pdaDatosRef.setFlgColorImplantacion("ROJO");
						}
					}			
				}
			}
		} 

		PlanogramaVigente planogramaVigente = obtenerPlanogramaVigente(referenciasCentroIC);

		//Si no existe el planogramaVigente o la capacidad maxima lineal es nula y el stockminimocomercial es nulo, se consulta el planograma de kosmos.
		if(planogramaVigente == null || (planogramaVigente.getCapacidadMaxLineal() == null && planogramaVigente.getStockMinComerLineal() == null)){
			planogramaVigente = this.obtenerPlanogramaKosmos(referenciasCentroIC);
		}

		if(planogramaVigente!=null){
			planogramaVigente = rellenarDatosVegalsa(planogramaVigente, session);
			if(planogramaVigente!=null && planogramaVigente.getOfertaProm()!=null &&  planogramaVigente.getEspacioProm()!=null){
				int cantidad=0;
				if(planogramaVigente.getFechaGenMontaje1()!=null){
					cantidad=planogramaVigente.getCapacidadMontaje1().intValue();
				}else if(planogramaVigente.getFechaGenCabecera()!=null){
					cantidad=planogramaVigente.getCapacidadMaxCabecera().intValue();
				}
				pdaDatosRef.setCantidadMac(String.valueOf(cantidad));
			}
		}
		MontajeVegalsa montajeVegalsa=tPedidoAdicionalService.getPedidosVegalsa(referenciasCentroIC.getCodCentro(), referenciasCentroIC.getCodArt());
		if(montajeVegalsa!=null){
			pdaDatosRef.setCantidadMa(montajeVegalsa.getCantidad().toString());
		}

		return pdaDatosRef;
	}
	
	private PlanogramaVigente obtenerPlanogramaKosmos(
			ReferenciasCentroIC referenciasCentroIC) throws Exception {

		PlanogramaVigente planogramaVigenteRes;
		PlanogramaVigente planogramaVigente = new PlanogramaVigente();
		planogramaVigente.setCodArt(referenciasCentroIC.getCodArt());
		planogramaVigente.setCodCentro(referenciasCentroIC.getCodCentro());

		planogramaVigenteRes = this.planogramaKosmosService.findOne(planogramaVigente);

		return planogramaVigenteRes;
	}

	private ValoresStock obtenerValoresStock(ReferenciasCentro referenciasCentro, PdaDatosReferencia pdaDatosReferencia
											, HistoricoVentaMedia historicoVentaMedia
											, VDatosDiarioArt vDatosDiarioArtRes, HttpSession session) throws Exception{

		ValoresStock valoresStock = new ValoresStock();
		//Calculo Ventas Medias
		ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();
		referenciasCentroIC.setCodArt(referenciasCentro.getCodArt());
		referenciasCentroIC.setCodCentro(referenciasCentro.getCodCentro());
		
		Float ventaMedia = new Float(0);
		if (historicoVentaMedia != null){
			historicoVentaMedia.recalcularVentasMedia();
			ventaMedia = historicoVentaMedia.getMedia();
		}
		
		//Calculo Venta Hoy
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
			
			//ObtenciÃ³n de lista de referencias relacionadas
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
		
			ventasTiendaRequest.setListaReferencias(listaReferencias);
			VentasTiendaResponseType ventasTiendaResponse = this.ventasTiendaService.consultaVentas(ventasTiendaRequest,session);
	
			if (ventasTiendaResponse.getListaReferencias().length > 0){
				BigDecimal ventaHoyBigDecTotal = new BigDecimal(0);
				
				for (ReferenciaRespuetaType referencia : ventasTiendaResponse.getListaReferencias()) {
					//Si da error en la consulta de la referencia se ignora salvo si se trata de la referencia padre
					if (new BigInteger("0").equals(referencia.getCodigoError()) || referenciasCentro.getCodArt().equals(referencia.getCodigoReferencia())){
						BigDecimal cantidadBigDec = new BigDecimal(hshRefAsociadasCantidad.get(new Long(referencia.getCodigoReferencia().toString())));
						BigDecimal ventaHoyBigDec = (referencia.getTotalVentaAnticipada()!=null?referencia.getTotalVentaAnticipada():new BigDecimal("0"))
						.add(referencia.getTotalVentaCompetencia()!=null?referencia.getTotalVentaCompetencia():new BigDecimal("0"))
						.add(referencia.getTotalVentaOferta()!=null?referencia.getTotalVentaOferta():new BigDecimal("0"))
						.add(referencia.getTotalVentaTarifa()!=null?referencia.getTotalVentaTarifa():new BigDecimal("0"));
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
		
		//Calculo Dia Mayor Venta
		HistoricoUnidadesVenta historicoUnidadesVenta = new HistoricoUnidadesVenta();
		historicoUnidadesVenta.setCodArticulo(referenciasCentro.getCodArt());
		historicoUnidadesVenta.setCodLoc(referenciasCentro.getCodCentro());
		Double ventasMax = new Double(0);
		
		Double ventasAux = this.historicoUnidadesVentaService.findDayMostSales(historicoUnidadesVenta);
		
		if (null != ventasAux){
			ventasMax = ventasAux;
		}
		
		//Calculo Stock Actual
		if (null != session.getAttribute("consultaStock")){
			ConsultarStockResponseType responseType = (ConsultarStockResponseType) session.getAttribute("consultaStock");
			
			if (responseType.getCodigoRespuesta().equals("OK")){
				for (ReferenciaType referencia : responseType.getListaReferencias()){
					if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(referenciasCentro.getCodArt()))){
						if (referencia.getCodigoError().equals(new BigInteger("0"))){
							if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
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
		} else {
			valoresStock.setFlgErrorWSVentasTienda(1);
			return valoresStock;
		}
	
		//Calculo del Velocímetro.
		ImagenComercial imagenComercial = pdaDatosReferencia.getImagenComercial();
		
		if (imagenComercial != null && imagenComercial.getTratamientoVegalsa().equals(0)){
			//Para PRUEBAS.
			//VDatosDiarioArt vDatosDiarioArtRes = this.obtenerDatosDiarioArt(referenciasCentro);
	
			if (null != vDatosDiarioArtRes && imagenComercial.getMetodo() != null){
				Double stockBajo;
				Double sobreStockInferior;
				Double sobreStockSuperior;
				StockFinalMinimo stockFinalMinimo = this.stockFinalMinimoService.obtenerStockFinalMinimo(referenciasCentroIC);
			
				//Calculo el stockBajo, el sobreStockInferior y el sobreStockSuperior
				boolean esSfm = (Constantes.IMC_SFM == imagenComercial.getMetodo());
				if (!esSfm){
					//Es Facing o Capacidad o Otros
					VMapaAprov vMapaAprov = new VMapaAprov();
					vMapaAprov.setCodArt(referenciasCentro.getCodArt());
					vMapaAprov.setCodCentro(referenciasCentro.getCodCentro());
					
					vMapaAprov = this.vMapaAprovService.findOne(vMapaAprov);
					Integer tipoServicio = 5;
					
					if (null == vMapaAprov){
						vMapaAprov = new VMapaAprov();
						vMapaAprov.setCodCentro(referenciasCentro.getCodCentro());
						vMapaAprov.setCodN1(vDatosDiarioArtRes.getEstlogN1().toString());
						vMapaAprov.setCodN2(vDatosDiarioArtRes.getEstlogN2().toString());
						vMapaAprov.setCodN3(vDatosDiarioArtRes.getEstlogN3().toString());
						vMapaAprov = this.vMapaAprovService.findOne(vMapaAprov);
					}
					
					if (null != vMapaAprov){
						Integer contador = 0;
						Method[] methods = vMapaAprov.getClass().getDeclaredMethods();
						
						for (Method method : methods) {
							
							if (method.getName().startsWith("getPed")) {
								Long pedidos = (Long) method.invoke(vMapaAprov);
								if (pedidos > 0){
									contador++;
								}
							}
						}
						
						if (contador >= 5){
							tipoServicio = 3;
						} else {
							tipoServicio = 5;
						}
					}
			
					//Calculamos el stock bajo para los que no son SFM -> MAX(IMC, VM) – VENTA HOY.
					Long imc = imagenComercial.getImc();
					stockBajo = imc > ventaMedia ? (imc - ventaHoy) : (ventaMedia - ventaHoy);
					
					//Calculamos el stock superior e inferior del velocímetro
					sobreStockInferior = (ventasMax * tipoServicio) - ventaHoy;
					sobreStockSuperior = ((ventasMax * tipoServicio) - ventaHoy) * 2;
					
				}else{
					
					//Es Sfm
					Long vidaUtil = stockFinalMinimo.getVidaUtil();
					if (null == vidaUtil || vidaUtil.equals(new Long(0))){
						vidaUtil = vDatosDiarioArtRes.getVidaUtil();
						if (vidaUtil.equals(new Long(0))){
							vidaUtil = new Long(1);
						}
					}
			
					//Calculamos el stock bajo para los que no son SFM -> MAX(SFM, VM) – VENTA HOY.
					Long sfm = imagenComercial.getSfm();
					stockBajo = sfm > ventaMedia ? (sfm - ventaHoy) : (ventaMedia - ventaHoy);
					
					//Calculamos el stock superior e inferior del velocímetro
					sobreStockInferior = (ventasMax * vidaUtil) - ventaHoy;
					sobreStockSuperior = ((ventasMax * vidaUtil) - ventaHoy) * 2;
				}
			
				valoresStock.setStockBajo(stockBajo);
				valoresStock.setSobreStockInferior(sobreStockInferior);
				valoresStock.setSobreStockSuperior(sobreStockSuperior);
				valoresStock.setFlgErrorWSVentasTienda(0);
			}
		}else{
			valoresStock.setStockBajo(new Double(0));
			valoresStock.setSobreStockInferior(new Double(0));
			valoresStock.setSobreStockSuperior(new Double(0));
			valoresStock.setFlgErrorWSVentasTienda(0);
		}
		
		//Comprobación de si hay que mostrar el link de información de motivos cuando el stock es alto o bajo
		//Restringido de momento a 2 usuarios
		valoresStock.setMostrarMotivosStock("N"); //InicializaciÃ³n a no mostrar el enlace a motivos de stock	
		
		if (valoresStock.getStockBajo() <= valoresStock.getSobreStockInferior()
			&& valoresStock.getSobreStockInferior() <= valoresStock.getSobreStockSuperior() && valoresStock.getStockBajo() >= 0){
			
			if (valoresStock.getStock() < valoresStock.getStockBajo() || valoresStock.getStock() > valoresStock.getSobreStockInferior()){
				MotivoTengoMuchoPoco motivoTengoMuchoPoco = new MotivoTengoMuchoPoco();
				motivoTengoMuchoPoco.setCodArticulo(referenciasCentro.getCodArt());
				motivoTengoMuchoPoco.setCodCentro(referenciasCentro.getCodCentro());
				
				String idSesionConsultaMotivos = session.getId()+ "_MOT_FFPP";
				List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();
				
				TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
				tPedidoAdicional.setIdSesion(idSesionConsultaMotivos);
				tPedidoAdicional.setCodCentro(referenciasCentro.getCodCentro());
				tPedidoAdicional.setCodArticulo(referenciasCentro.getCodArt());
				listaTPedidoAdicional.add(tPedidoAdicional);
				
				if (valoresStock.getStock() > valoresStock.getSobreStockInferior()){
					motivoTengoMuchoPoco.setTipo(Constantes.TENGO_MUCHO_POCO_MOTIVO_MUCHO);

					//Si es tengo mucho hay que cargar los datos de la referencia y de su formato productivo si existe para la consulta de motivos
					if (pdaDatosReferencia.getMostrarFFPP()!=null && pdaDatosReferencia.getMostrarFFPP().equals("S")){
						//Tratamiento pedido Adicional de ffpp							
						tPedidoAdicional = new TPedidoAdicional();
						tPedidoAdicional.setIdSesion(idSesionConsultaMotivos);
						tPedidoAdicional.setCodCentro(referenciasCentro.getCodCentro());
						tPedidoAdicional.setCodArticulo(pdaDatosReferencia.getCodArtRel());
						listaTPedidoAdicional.add(tPedidoAdicional);
					}
				} else {
					motivoTengoMuchoPoco.setTipo(Constantes.TENGO_MUCHO_POCO_MOTIVO_POCO);
				}
		
				this.tPedidoAdicionalService.obtenerPedidosAdicionales(listaTPedidoAdicional, referenciasCentro.getCodCentro(), idSesionConsultaMotivos,session);
		
				motivoTengoMuchoPoco.setIdSesion(idSesionConsultaMotivos);
				motivoTengoMuchoPoco.setStockBajo(valoresStock.getStockBajo());
				motivoTengoMuchoPoco.setStockAlto(valoresStock.getSobreStockInferior());
				motivoTengoMuchoPoco.setStock(valoresStock.getStock());
				
				MotivoTengoMuchoPocoLista motivoTengoMuchoPocoLista = null;
				motivoTengoMuchoPocoLista = this.motivoTengoMuchoPocoService.consultaMotivosTengoMuchoPoco(motivoTengoMuchoPoco);
		
				if (motivoTengoMuchoPocoLista != null && motivoTengoMuchoPocoLista.getEstado() != null && (new Long(0)).equals(motivoTengoMuchoPocoLista.getEstado())){
					
					if ("S".equals(motivoTengoMuchoPocoLista.getMapaActivo()) && "S".equals(motivoTengoMuchoPocoLista.getFlgFGen())){
						valoresStock.setMostrarMotivosStock("S");
					}
				}
			}
		}
		
		return valoresStock;
	}

	private String obtenerFlgDepositoBrita(ReferenciasCentro referenciasCentro) throws Exception{

		RefEnDepositoBrita refEnDepositoBrita = new RefEnDepositoBrita();
		refEnDepositoBrita.setCodArt(referenciasCentro.getCodArt());
		refEnDepositoBrita.setCodCentro(referenciasCentro.getCodCentro());

		return (this.refEnDepositoBritaService.enDepositoBrita(refEnDepositoBrita)?"S":"N");
	}

	private Long obtenerCc(ReferenciasCentro referenciasCentro) throws Exception{
		//Preparar parámetros para búsqueda de cc
		CcRefCentro ccRefCentro = new CcRefCentro(referenciasCentro.getCodArt(), 
				referenciasCentro.getCodCentro(), 
				new Date());

		return ccRefCentroService.consultaCc(ccRefCentro);		
	}

	private String obtenerFlgPorCatalogo(ReferenciasCentro referenciasCentro, VDatosDiarioArt vDatosDiarioArt) throws Exception{

		String esRefPorCatalogo = "N";

		//Obtención de estructura comercial a partir de la referencia
// Para PRUEBAS.
//		VDatosDiarioArt vDatosDiarioArt = obtenerDiarioArt(referenciasCentro.getCodArt());

		//Obtención del CC
		Long cc = this.obtenerCc(referenciasCentro);

		if (vDatosDiarioArt != null && vDatosDiarioArt.getGrupo1()!=null && cc != null &&
				Constantes.REFERENCIA_POR_CATALOGO_AREA.equals(vDatosDiarioArt.getGrupo1().toString()) && 
				Constantes.REFERENCIA_POR_CATALOGO_CC.equals(cc.toString())){

			esRefPorCatalogo = "S";
		}

		return esRefPorCatalogo;
	}

	private String obtenerTipoRotacion(PdaDatosReferencia pdaDatosRef, Long codCentro) throws Exception{

		String tipoRotacion = "";

		//Carga de parámetros de búsqueda
		VRotacionRef vRotacionRef = new VRotacionRef();
		vRotacionRef.setCodCentro(codCentro);
		vRotacionRef.setCodArt(pdaDatosRef.getCodArt());

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

}
