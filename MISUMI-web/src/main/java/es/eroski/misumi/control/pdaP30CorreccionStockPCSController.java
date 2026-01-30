package es.eroski.misumi.control;


import java.math.BigDecimal;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaModType;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP30CorreccionStockPCSController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP30CorreccionStockPCSController.class);
	private static final int MINIMO_BANDEJAS_MODIFICADAS_MANUALMENTE = 6; // salvo el 0
	
	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private StockTiendaService correccionStockService;
	
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	private PdaArticulo recargarValoresPantalla(PdaArticulo pdaArticulo, HttpServletRequest request) {
		String msgErrorKey = null;
		
		//Comprobamos el formato.
		// validar bandeja
		try{
			pdaArticulo.setUnidades(new BigInteger(request.getParameter("pda_p30_fld_bandejas_pantalla")));
		}catch(Exception e) {
			msgErrorKey = "pda_p30_correccionStockPCS.formatoBandeja";
		}
		
		// validar stock
		if (msgErrorKey==null){
			try{
				//Aceptamos que venga separado tanto por comas como por puntos.
				String stockPantallaPunto = request.getParameter("pda_p30_fld_stock_pantalla").replace(',', '.');
				Double.parseDouble(stockPantallaPunto);
				pdaArticulo.setStock(stockPantallaPunto);
			}catch(Exception e) {
				msgErrorKey = "pda_p30_correccionStockPCS.formatoStock";
			}
		}
		
		// control de errores
		if(msgErrorKey!=null){
			Locale locale = LocaleContextHolder.getLocale();
			String message = this.messageSource.getMessage(msgErrorKey, null, locale);
			pdaArticulo.setDescripcionError(message);
		}else{
			pdaArticulo.setDescripcionError("");
		}

		return pdaArticulo;
	}

	private PdaArticulo validacionesBandejas(int posicion, PdaArticulo pdaArticuloActual, PdaArticulo pdaArticulo,
			HttpSession session) {
		if(StringUtils.isEmpty(pdaArticuloActual.getDescripcionError())) {
			String msgErrorKey = null;
			
			try 
			{
				
				// si se ha modificado el campo
				boolean valorHaCambiado = pdaArticuloActual.getUnidades()== null && pdaArticulo.getUnidades()!=null 
						|| pdaArticuloActual.getUnidades().compareTo(pdaArticulo.getUnidades()) != 0;
				
				// validamos si ha cambiado
				if (valorHaCambiado) {
					boolean pesoVariable = "S".equals(pdaArticuloActual.getPesoVariable());
					int unidadesInt = pdaArticulo.getUnidades()==null ? 0 : pdaArticulo.getUnidades().intValue();
					if(pesoVariable && unidadesInt!=0 && unidadesInt < MINIMO_BANDEJAS_MODIFICADAS_MANUALMENTE){
						// fallo de validacion por haber editado a mano un valor demasiado pequenyo
						msgErrorKey = "pda_p30_correccionStockPCS.minimoEditableBandeja";
					}
				}
				
			}catch (Exception e) {
				logger.error(StackTraceManager.getStackTrace(e));
				msgErrorKey = "pda_p30_correccionStockPCS.formatoBandeja";
			}
			
			if (msgErrorKey!=null){
				Locale locale = LocaleContextHolder.getLocale();
				String message = this.messageSource.getMessage(msgErrorKey, null, locale);
				pdaArticuloActual.setDescripcionError(message);
			}else{
				// esta bien y lo asignamos
				pdaArticuloActual.setUnidades(pdaArticulo.getUnidades());
			}
		}
		
		return pdaArticuloActual;
	}

	private PdaArticulo validacionesStock(int posicion, PdaArticulo pdaArticuloActual, PdaArticulo pdaArticulo,
			HttpSession session) {
		if(StringUtils.isEmpty(pdaArticuloActual.getDescripcionError())) {
			String msgErrorKey = null;
			try 
			{

				// miramos si ha cambiado
				String stockArticuloPunto = pdaArticulo.getStock()==null?null:pdaArticulo.getStock().replace(',', '.');
				if (!pdaArticuloActual.getStock().replace(',', '.').equals(stockArticuloPunto))
				{

					//Comprobamos el formato.
					try{
						//Aceptamos que venga separado tanto por comas como por puntos.
						Double stockDouble = Double.parseDouble(stockArticuloPunto);

						if (Double.compare(stockDouble, 0.0) < 0){
							msgErrorKey = "pda_p30_correccionStockPCS.formatoStock";
						}
						
						// si ha cambiado algo en el stock y las bandejas son menores del minimo esta mal
						// porque en ese caso no deberia ser editable
						boolean pesoVariable = "S".equals(pdaArticuloActual.getPesoVariable());
						int unidadesInt = pdaArticulo.getUnidades()==null ? 0 : pdaArticulo.getUnidades().intValue();
						if(pesoVariable && unidadesInt!=0
								&& unidadesInt < MINIMO_BANDEJAS_MODIFICADAS_MANUALMENTE){
							msgErrorKey = "pda_p30_correccionStockPCS.minimoEditableBandeja";
						}

					}
					catch(Exception e) {
						msgErrorKey = "pda_p30_correccionStockPCS.formatoStock";
					}
				}


			}catch (Exception e) {
				logger.error(StackTraceManager.getStackTrace(e));
				msgErrorKey = "pda_p30_correccionStockPCS.formatoStock";
			}
			
			if (msgErrorKey!=null){
				Locale locale = LocaleContextHolder.getLocale();
				String message = this.messageSource.getMessage(msgErrorKey, null, locale);
				pdaArticuloActual.setDescripcionError(message);
			}else{
				// todo bien
				pdaArticuloActual.setStock(pdaArticulo.getStock());
			}

		}
		return pdaArticuloActual;
	}
	
	@RequestMapping(value = "/pdaP30CorrStockPCS", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid final PdaArticulo pdaArticulo,
			HttpServletRequest request,
			HttpServletResponse response) {
		

		PdaArticulo pdaArticuloActual = new PdaArticulo();
		int posicion = 0;
		String resultado = "pda_p30_correccionStockPCS";
		Locale locale = LocaleContextHolder.getLocale();
		String strCodArt = "";
		String origenPantalla = request.getParameter("origenPantalla");
		String selectProv = request.getParameter("selProv");
		String procede = request.getParameter("procede");

//	    String queryString = request.getQueryString();
	    
//		boolean entroSesion = false;
		
		if (origenPantalla == null || "".equals(origenPantalla) || " ".equals(origenPantalla)
			|| "null".equals(origenPantalla)){
			
			origenPantalla = (String)session.getAttribute("origenPantalla");
			selectProv = (String)session.getAttribute("selectProv");
//			entroSesion = true;
		}

		try{
			//Obtención del objeto de articulo actual
			pdaArticuloActual = obtenerReferencia(session);
			
			//Control de referencia de cabecera
			if ((request.getParameter("actionIntroRef") != null || pdaDatosCab.getCodArtCab() != null) && null == request.getParameter("actionPrev")){
				
				model.addAttribute("procede", "pdaP115PrehuecosLineal");
				
				//Controlamos que me llega la referencia
				if (pdaDatosCab.getCodArtCab() != null && !pdaDatosCab.getCodArtCab().equals("")){
					if (pdaDatosCab.getCodArtCab().startsWith(Constantes.REF_PISTOLA)){
						//Quitamos el primer dígito
						strCodArt = pdaDatosCab.getCodArtCab();
						strCodArt = strCodArt.substring(1,strCodArt.length());
						if (strCodArt.startsWith(Constantes.REF_BALANZA) || strCodArt.startsWith(Constantes.REF_BALANZA_ESPECIAL)){
							//Llamamos al método que nos devuelve la referencia, con los controles, 
							//por si se trata de balanza, etiqueta propia de Eroski, de la tabla EANS o código de referencia normal.
							PdaArticulo pdaArticuloCabecera = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
							Long codArtTratada = pdaArticuloCabecera.getCodArt();
							String codigoError = pdaArticuloCabecera.getCodigoError();	
							if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN)){
								pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
										"pda_p30_correccionStockPCS.noExisteReferencia", null, locale));
								model.addAttribute("pdaArticulo", pdaArticuloActual);
								model.addAttribute("pdaDatosCab", pdaDatosCab);
								logger.info("PDA Referencia erronea");
								return "pda_p30_correccionStockPCS";
							}else if (!codArtTratada.equals(pdaArticuloActual.getCodArtOrig())){
								pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
										"pda_p30_correccionStockPCS.errorReferenciaNoValida", null, locale));
								model.addAttribute("pdaArticulo", pdaArticuloActual);
								model.addAttribute("pdaDatosCab", pdaDatosCab);
								logger.info("PDA Referencia no introducida");
								return "pda_p30_correccionStockPCS";
							}
							
							//Añadir bandejas y stock al guardado
							
							if (strCodArt.startsWith(Constantes.REF_BALANZA)){
								//Hay que calcular el peso. Me viene sólo el precio
								double precioCabecera = new Double(pdaArticuloCabecera.getPrecio().replace(',', '.'));
								double precioWS = new Double(pdaArticuloActual.getPrecio().replace(',', '.'));
								double kgsCabecera = precioCabecera/precioWS;
								pdaArticuloCabecera.setKgs(Utilidades.convertirDoubleAString(kgsCabecera,"###0.00"));
							}
							
							//Se obtienen los valores de pantalla para actualizar el artículo
							PdaArticulo pdaArticuloPantalla = recargarValoresPantalla(pdaArticulo, request);
							pdaArticuloActual.setDescripcionError(pdaArticuloPantalla.getDescripcionError());
							
							pdaArticuloActual = this.validacionesBandejas(posicion, pdaArticuloActual, pdaArticuloPantalla, session);
							pdaArticuloActual = this.validacionesStock(posicion, pdaArticuloActual, pdaArticuloPantalla, session);

							
							//Si se ha producido un error con las validaciones lo mostramos.
							if (pdaArticuloActual.getDescripcionError() != null && !pdaArticuloActual.getDescripcionError().isEmpty()){
								model.addAttribute("pdaArticulo", pdaArticuloActual);
								model.addAttribute("pdaDatosCab", pdaDatosCab);
								logger.info("PDA Error Validacion Corrección stock");
								return "pda_p30_correccionStockPCS";
							}

							//Se suma 1 a las bandejas y se actualiza el stock
							//Se guarda el último peso para los cálculos en pantalla
							pdaArticuloActual.setUnidades(pdaArticuloActual.getUnidades().add(new BigInteger("1")));
							double stockActual = new Double(pdaArticuloActual.getStock().replace(',', '.'));
							double stockCabecera = new Double(pdaArticuloCabecera.getKgs().replace(',', '.'));
							double stockSuma = stockActual + stockCabecera;
							pdaArticuloActual.setStock(Utilidades.convertirDoubleAString(stockSuma,"###0.00").replace(',', '.'));
							pdaArticuloActual.setKgs(Utilidades.convertirDoubleAString(stockCabecera,"###0.00").replace(',', '.'));
							
							model.addAttribute("pdaArticulo", pdaArticuloActual);
							model.addAttribute("pdaDatosCab", pdaDatosCab);
							resultado = "pda_p30_correccionStockPCS";
						
						}else{
							pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
									"pda_p30_correccionStockPCS.errorReferenciaNoValida", null, locale));
							model.addAttribute("pdaArticulo", pdaArticuloActual);
							model.addAttribute("pdaDatosCab", pdaDatosCab);
							logger.info("PDA Referencia no introducida");
							return "pda_p30_correccionStockPCS";
						}
					}else{
						pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
								"pda_p30_correccionStockPCS.errorReferenciaNoValida", null, locale));
						model.addAttribute("pdaArticulo", pdaArticuloActual);
						model.addAttribute("pdaDatosCab", pdaDatosCab);
						logger.info("PDA Referencia no introducida");
						return "pda_p30_correccionStockPCS";
					}
				}else{
					//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
					pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
							"pda_p30_correccionStockPCS.referenciaVacia", null, locale));
					model.addAttribute("pdaArticulo", pdaArticuloActual);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia no introducida");
					return "pda_p30_correccionStockPCS";
				}
			}
		
			if (request.getParameter("actionSave") != null){
				pdaArticuloActual.setDescripcionError("");
				pdaArticuloActual = this.validacionesBandejas(posicion, pdaArticuloActual, pdaArticulo, session);
				pdaArticuloActual = this.validacionesStock(posicion, pdaArticuloActual, pdaArticulo, session);
				
				//Si se ha producido un error con la validación lo mostramos.
				if (pdaArticuloActual.getDescripcionError() != null && !pdaArticuloActual.getDescripcionError().equals("")){
					model.addAttribute("pdaArticulo", pdaArticuloActual);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Error Validacion Corrección stock");
					return "pda_p30_correccionStockPCS";
				}
				
				pdaArticuloActual = this.guardarRegistro(pdaArticuloActual, session);
				
				if (pdaArticuloActual.getCodigoError().equals(Constantes.STOCK_TIENDA_RESULTADO_KO)){
					model.addAttribute("pdaArticulo", pdaArticuloActual);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					resultado = "pda_p30_correccionStockPCS";
				}else{//OK o Warning
					redirectAttributes.addAttribute("codArt", pdaArticuloActual.getCodArtOrig());
					redirectAttributes.addAttribute("guardadoStockOk", "S");
					if (pdaArticuloActual.getOrigen().equals("DR")){
						resultado = "redirect:pdaP12DatosReferencia.do?origenPantalla="+origenPantalla+"&guardadoOK=true";
					}else if(pdaArticuloActual.getOrigen().equals("SP")){
						resultado = "redirect:pdaP13SegPedidos.do";
					}else if(pdaArticuloActual.getOrigen().equals("DVFN")){
						resultado = "redirect:pdaP62StockLinkVuelta.do?flgBienGuardado=S&origenPantalla="+origenPantalla+"&selProv="+selectProv;
					}else if(pdaArticuloActual.getOrigen().equals("DVOR")){
						resultado = "redirect:pdaP63StockLinkVuelta.do?flgBienGuardado=S&origenPantalla="+origenPantalla+"&selProv="+selectProv;
					}else if(pdaArticuloActual.getOrigen().equals("REPO1")){
						resultado = "redirect:pdaP91ListadoRepoVuelta.do";
					}else if(pdaArticuloActual.getOrigen().equals("REPO2")){
						resultado = "redirect:pdaP92ListadoRepoAntVuelta.do";
					}else if(pdaArticuloActual.getOrigen().equals("CR")){
						resultado = "redirect:pdaP98CapturaRestos.do";
					}else if (Constantes.MENU_PDA_SACADA_RESTOS.equals(pdaArticuloActual.getOrigen())){
						resultado = "redirect:pdaP99SacadaRestos.do";
					}else if (null != procede && procede.equalsIgnoreCase("pdaP115PrehuecosLineal")){
						resultado = "redirect:pdaP115PrehuecosLineal.do?codArt="+pdaArticuloActual.getCodArtOrig()+"&procede=pdaP115PrehuecosLineal";
					}else{
						resultado = "redirect:pdaP15MovStocks.do";
					}
				}
				return resultado;
			}
			
			if (request.getParameter("actionPrev") != null){
				redirectAttributes.addAttribute("codArt", pdaArticuloActual.getCodArtOrig());
				if (pdaArticuloActual.getOrigen().equals("DR")){
					resultado = "redirect:pdaP12DatosReferencia.do?origenPantalla="+origenPantalla;
				}else if(pdaArticuloActual.getOrigen().equals("SP")){
					resultado = "redirect:pdaP13SegPedidos.do";
				}else if(pdaArticuloActual.getOrigen().equals("DVFN")){
					resultado = "redirect:pdaP62StockLinkVuelta.do?&origenPantalla="+origenPantalla+"&selProv="+selectProv;
				}else if(pdaArticuloActual.getOrigen().equals("DVOR")){
					resultado = "redirect:pdaP63StockLinkVuelta.do?&origenPantalla="+origenPantalla+"&selProv="+selectProv;
				}else if(pdaArticuloActual.getOrigen().equals("REPO1")){
					resultado = "redirect:pdaP91ListadoRepoVuelta.do";
				}else if(pdaArticuloActual.getOrigen().equals("REPO2")){
					resultado = "redirect:pdaP92ListadoRepoAntVuelta.do";
				}else if(pdaArticuloActual.getOrigen().equals(Constantes.MENU_PDA_CAPTURA_RESTOS)){
					if (null != request.getParameter("actionSave")){
						redirectAttributes.addAttribute("guardadoStockOk", "S");
					}
					resultado = "redirect:pdaP98CapturaRestos.do";
				}else if (pdaArticuloActual.getOrigen().equals(Constantes.MENU_PDA_SACADA_RESTOS)){
					if (null != request.getParameter("actionSave")){
						redirectAttributes.addAttribute("guardadoStockOk", "S");
					}
					resultado = "redirect:pdaP99SacadaRestos.do";
				}else{
					resultado = "redirect:pdaP15MovStocks.do";
				}
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		try{			
			session.setAttribute(origenPantalla,"");
			session.setAttribute(selectProv,"");
		}catch (Exception e){
		}

		return resultado;
	}
	
	private PdaArticulo obtenerReferencia(HttpSession session) throws Exception{

		PdaArticulo pdaArticulo = new PdaArticulo();

		if (session.getAttribute("pdaArticuloStock") != null && session.getAttribute("pdaArticuloStock") != null){
			pdaArticulo = (PdaArticulo) session.getAttribute("pdaArticuloStock");
		}

		return pdaArticulo;
	}
	
	private PdaArticulo guardarRegistro(PdaArticulo pdaArticuloActual, HttpSession session) throws Exception {
		
		ModificarStockRequestType modificarStockRequest = new ModificarStockRequestType();
		User user = (User) session.getAttribute("user");
		modificarStockRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
		
		//Carga de referencias cambiadas
		List<ReferenciaModType> referencias = new ArrayList<ReferenciaModType>();
		if (pdaArticuloActual != null){
			ReferenciaModType referenciaModType = new ReferenciaModType();
			referenciaModType.setCodigoReferencia(new BigInteger(pdaArticuloActual.getCodArt().toString()));
			//Peticion 55001. Corrección errores del LOG.
			referenciaModType.setBandejas(pdaArticuloActual.getUnidades() != null ? pdaArticuloActual.getUnidades() : new BigInteger("0"));
			//Redondeo a 2 decimales del stock
			//Peticion 55001. Corrección errores del LOG.
			BigDecimal bdStock = Utilidades.convertirStringABigDecimal(pdaArticuloActual.getStock());
		    BigDecimal roundedStock = bdStock.setScale(2, BigDecimal.ROUND_HALF_UP);
			referenciaModType.setStock(roundedStock);
			referencias.add(referenciaModType);
		}
		
	
		ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
		paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
		ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
		
		if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
			logger.error("###########################################################################################################");
			logger.error("############################## CONTROLADOR: pdaP30CorreccionStockPCSController ############################");
			logger.error("###########################################################################################################");
		}
		
		if (referencias != null && referencias.size() > 0){
			modificarStockRequest.setListaReferencias(referencias.toArray(new ReferenciaModType[referencias.size()]));
			ModificarStockResponseType modificarStockResponseType = this.correccionStockService.modificarStock(modificarStockRequest, session);
			pdaArticuloActual.setCodigoError(modificarStockResponseType.getCodigoRespuesta());
			pdaArticuloActual.setDescripcionError(modificarStockResponseType.getDescripcionRespuesta());
		}else{
			pdaArticuloActual.setCodigoError(Constantes.STOCK_TIENDA_RESULTADO_OK);
			pdaArticuloActual.setDescripcionError("");
		}

		return pdaArticuloActual;
	}
}