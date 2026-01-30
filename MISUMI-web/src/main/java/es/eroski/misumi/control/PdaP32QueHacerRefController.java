package es.eroski.misumi.control;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.QueHacerRef;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.QueHacerRefService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class PdaP32QueHacerRefController extends pdaConsultasController{

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private QueHacerRefService queHacerRefService;

	@Autowired
	private StockTiendaService stockTiendaService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	private static Logger logger = Logger.getLogger(PdaP32QueHacerRefController.class);

	@RequestMapping(value = "/pdaP32QueHacerRef",method = RequestMethod.GET)
	public String showForm(ModelMap model,@Valid final Long codArt,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "pda_p32_queHacerRef";
		try {

			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			model.addAttribute("pdaDatosCab", pdaDatosCab);

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	@RequestMapping(value = "/pdaP32QueHacerRef",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "pda_p32_queHacerRef";
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		User user = (User) session.getAttribute("user");
		try {

			if (pdaDatosCab.getCodArtCab() != null && !pdaDatosCab.getCodArtCab().equals(""))
			{	
				// es num empezando por #? 
//				boolean isNumH = pdaDatosCab.getCodArtCab().startsWith("#") && StringUtils.isNumeric(pdaDatosCab.getCodArtCab().substring(1));
				String codigoArticuloBuscador = pdaDatosCab.getCodArtCab();
				if (pdaDatosCab.getCodArtCab().startsWith("#")) {
					codigoArticuloBuscador = pdaDatosCab.getCodArtCab().substring(1);
				}
				//Si pdaDatosCab es número
				if (StringUtils.isNumeric(codigoArticuloBuscador)){
					//Si el centro es de tipo caprabo
					if(user.getCentro().getEsCentroCaprabo()){
						//Guardamos el código de caprabo o el EAN en el campo caprabo.
						//En el caso de que sea un EAN lo que guarda este campo, luego se hará
						//la conversión del código de eroski obtenido en la función
						//obtenerReferenciaTratada a código de caprabo. Si es caprabo, luego
						//se utilizará este campo directamente.
						pdaDatosCab.setCodArtCaprabo(pdaDatosCab.getCodArtCab());
	
						//Si el campo que llega al controlador no es un ean, será un campo caprabo. Para hacer que el código 
						//de más abajo funcione necesitaremos su referencia eroski. Este paso no es necesario con los EAN, pues
						//la función obtenerReferenciaTratada transforma los EAN en códigos de artículo eroski.
						if(!pdaDatosCab.getCodArtCab().startsWith(Constantes.REF_PISTOLA)){
							//Obtengo el código Eroski para realizar toda la opertiva del controlador
							Long codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(),new Long(pdaDatosCab.getCodArtCab().toString()));
							pdaDatosCab.setCodArtCab(codArtEroski.toString());	
						}
					}
	
					//Llamamos al método que nos devuelve la referencia, con los controles, 
					//por si se trata de balanza, etiqueta propia de Eroski, de la tabla EANS o código de referencia normal.
					PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
					String codigoError = pdaArticulo.getCodigoError();


					if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN)) {
						pdaError.setDescError(this.messageSource.getMessage(
								"pda_p12_datosReferencia.noExisteReferencia", null, locale));
						model.addAttribute("pdaError", pdaError);
						model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
						logger.info("PDA Referencia erronea");
						resultado =  "pda_p03_queHacerRef_showMessage";
					} else {										
						pdaDatosCab.setCodArtCab(pdaArticulo.getCodArt().toString());


						//VDatosDiarioArt vDatosDiarioArt =  obtenerDiarioArt(new Long(pdaDatosCab.getCodArtCab()));

						//if (null == vDatosDiarioArt)
						//{
						//	pdaError.setDescError(this.messageSource.getMessage(
						//			"pda_p12_datosReferencia.noExisteReferencia", null, locale));
						//	model.addAttribute("pdaError", pdaError);
						//	model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
						//	logger.info("PDA Referencia erronea");
						//	resultado =  "pda_p03_queHacerRef_showMessage";
						//} else {

						QueHacerRef pdaQueHacerRef = new QueHacerRef();
						pdaQueHacerRef.setCodLoc(user.getCentro().getCodCentro());
						pdaQueHacerRef.setCodArtFormlog(new Long(pdaDatosCab.getCodArtCab()));

						pdaQueHacerRef = this.queHacerRefService.obtenerAccionRef(pdaQueHacerRef);



						if (null == pdaQueHacerRef || null==pdaQueHacerRef.getEstado() || !(new Long(0)).equals(pdaQueHacerRef.getEstado())){
							//Mensaje específico de referencia inexistente
							if (new Long(4).equals(pdaQueHacerRef.getEstado()))
								pdaError.setDescError(this.messageSource.getMessage("pda_p32_queHacerRef.errorReferenciaNoValida", null, locale));
							else
								pdaError.setDescError(this.messageSource.getMessage("pda_p32_queHacerRef.errorObtencionDatos", null, locale));
							model.addAttribute("pdaError", pdaError);

							//Variable para guardar el código de artículo caprabo o eroski dependiendo del tipo de centro.
							Long codArtPantalla;

							//Si es un centro Caprabo, obtenemos la descripción de Caprabo y volvemos a poner el codArticulo de pantalla
							if(user.getCentro().getEsCentroCaprabo()){ 
								//Si el código que ha llegado es de tipo EAN, se busca el código caprabo a partir del código eroski obtenido en 
								//la función obtenerReferenciaTratada. Si es de tipo caprabo, se muestra el campo tal cual ha llegado al controlador.
								if(pdaDatosCab.getCodArtCaprabo().startsWith(Constantes.REF_PISTOLA)){
									codArtPantalla = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), Long.parseLong(pdaDatosCab.getCodArtCab()));
								}else{									
									codArtPantalla = Long.parseLong(pdaDatosCab.getCodArtCaprabo());
								}
								pdaDatosCab.setDescArtCab(utilidadesCapraboService.obtenerDescArtCaprabo(new Long(pdaDatosCab.getCodArtCaprabo().toString())));
							}else{
								codArtPantalla = Long.parseLong(pdaDatosCab.getCodArtCab());
							}

							pdaDatosCab.setCodArtCab(codArtPantalla.toString());

							model.addAttribute("pdaDatosCab", pdaDatosCab);
							resultado =  "pda_p03_queHacerRef_showMessage";
						} else {

							//Obtenemos la denominacion
							pdaDatosCab.setDescArtCab(pdaQueHacerRef.getDenomInformeRef());

							//Obtención del stock actual
							ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
							stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
							stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
							List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
							listaReferencias.add(BigInteger.valueOf(new Long(pdaDatosCab.getCodArtCab())));
							stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
							try{


								ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
								paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
								ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

								if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
									logger.error("###########################################################################################################");
									logger.error("############################## CONTROLADOR: PdaP32QueHacerRefController	 ############################");
									logger.error("###########################################################################################################");
								}

								ConsultarStockResponseType stockTiendaResponse = this.stockTiendaService.consultaStock(stockTiendaRequest, session);
								if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
									pdaQueHacerRef.setStockActual(Utilidades.convertirDoubleAString(stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue(),"###0.00"));
								} else {
									pdaQueHacerRef.setStockActual(Utilidades.convertirDoubleAString(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue(),"###0.00"));
								}

							}catch (Exception e) {
								pdaQueHacerRef.setStockActual(this.messageSource.getMessage("pda_p32_queHacerRef.errorStock", null, locale));
							}

							//Variable para guardar el código de artículo caprabo o eroski dependiendo del tipo de centro.
							Long codArtPantalla;

							//Si es un centro Caprabo, obtenemos la descripción de Caprabo y volvemos a poner el codArticulo de pantalla
							if(user.getCentro().getEsCentroCaprabo()){ 
								//Si el código que ha llegado es de tipo EAN, se busca el código caprabo a partir del código eroski obtenido en 
								//la función obtenerReferenciaTratada. Si es de tipo caprabo, se muestra el campo tal cual ha llegado al controlador.
								if(pdaDatosCab.getCodArtCaprabo().startsWith(Constantes.REF_PISTOLA)){
									codArtPantalla = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), Long.parseLong(pdaDatosCab.getCodArtCab()));
								}else{									
									codArtPantalla = Long.parseLong(pdaDatosCab.getCodArtCaprabo());
								}
								pdaDatosCab.setDescArtCab(utilidadesCapraboService.obtenerDescArtCaprabo(new Long(pdaDatosCab.getCodArtCaprabo().toString())));
							}else{
								codArtPantalla = Long.parseLong(pdaDatosCab.getCodArtCab());
							}							
							pdaDatosCab.setCodArtCab(codArtPantalla.toString());

							model.addAttribute("pdaDatosCab", pdaDatosCab);
							model.addAttribute("pdaQueHacerRef", pdaQueHacerRef);
						}
						//}
					}
				}
				else {//La referencia no tiene un valor numérico
					pdaError.setDescError(this.messageSource.getMessage(
							"pda_p32_queHacerRef.errorReferenciaNoNumerica", null, locale));
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					model.addAttribute("pdaError", pdaError);
					logger.info("PDA Referencia no numérica");
					resultado =  "pda_p03_queHacerRef_showMessage";
				}
			}else{
				//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
				pdaError.setDescError(this.messageSource.getMessage(
						"pda_p32_queHacerRef.referenciaVacia", null, locale));
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaError", pdaError);
				logger.info("PDA Referencia no introducida");
				resultado =  "pda_p03_queHacerRef_showMessage";
			}

		} catch (Exception e) {
			pdaError.setDescError(this.messageSource.getMessage("pda_p32_queHacerRef.errorObtencionDatos", null, locale));
			model.addAttribute("pdaError", pdaError);
			resultado =  "pda_p03_queHacerRef_showMessage";
			logger.error(StackTraceManager.getStackTrace(e));
			return resultado;
		} 
		return resultado;
	}
}