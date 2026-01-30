package es.eroski.misumi.control;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.StockPlataforma;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaSeleccionStock;
import es.eroski.misumi.model.pda.PdaStock;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaModType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.StockPlataformaService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP31CorreccionStockPCNController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP31CorreccionStockPCNController.class);
	
	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private StockTiendaService correccionStockService;
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private StockPlataformaService stockPlataformaService;
	
	public PdaStock pdaP31Paginar(int posicion, String botPag, HttpSession session) {
		
		PdaStock pdaStockResultado = new PdaStock();
		
		try{
			//Paginamos a la página que corresponda.
			int posicionBusqueda= 1;
			if (botPag != null && botPag.equals("botonAnt")){
				//Obtenemos el elemento de la posición anterior.
				posicionBusqueda = posicion-1;
			}else if (botPag != null && botPag.equals("botonSig")){
				posicionBusqueda = posicion+1;
			}
				
			pdaStockResultado = obtenerReferenciasPorPosicion(session,posicionBusqueda);
			pdaStockResultado.setPosicionGrupoArticulos(posicionBusqueda);
		
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return pdaStockResultado;
	}
	
	public PdaStock validacionesStock(int posicion, PdaStock pdaStockActual, PdaStock pdaStock,
			HttpSession session) {
		
		Locale locale = LocaleContextHolder.getLocale();
		List<PdaArticulo> listaArticulos = pdaStockActual.getListaArticulos();
		PdaArticulo pdaArticuloGuardado = null; 
		boolean existeError = false;
		
		try{
			int indiceListaArticulos = 1;
			//Tratamiento de errores por cada fila
			if (pdaStockActual.getTipoMensaje().equals(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA)) { //Es textil
				indiceListaArticulos = (posicion - 1)*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL;
			} else {
				indiceListaArticulos = (posicion - 1)*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
			}
			
			for (PdaArticulo pdaArticulo : pdaStock.getListaArticulosPagina()) {
				pdaArticuloGuardado = listaArticulos.get(indiceListaArticulos);
				
				if (!pdaArticulo.getStock().equals(pdaArticuloGuardado.getStock())){
					
					//Comprobamos el formato.
					try{
						//Aceptamos que venga separado tanto por comas como por puntos.
						Double stockDouble = Double.parseDouble(pdaArticulo.getStock().replace(',', '.'));

						
						if (Double.compare(stockDouble, 0.0) < 0){
							pdaArticuloGuardado.setDescripcionError(this.messageSource.getMessage(
									"pda_p31_correccionStockPCN.formatoStock", null, locale));
							existeError = true;
						} else {
							pdaArticuloGuardado.setStock(pdaArticulo.getStock());
							pdaArticuloGuardado.setDescripcionError("");
						}
					}catch(Exception e) {
						pdaArticuloGuardado.setDescripcionError(this.messageSource.getMessage(
								"pda_p31_correccionStockPCN.formatoStock", null, locale));
						existeError = true;
					}
				}else{
					//Si no se ha modificado ningún campo y había algún error, lo quitamos.
					pdaArticuloGuardado.setDescripcionError("");
				}
				
				//Incremento del índice de la lista de artículos
				indiceListaArticulos++;
			}
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		if (existeError){
			pdaStockActual.setDescripcionError(this.messageSource.getMessage(
					"pda_p31_correccionStockPCN.formatoStock", null, locale));
		}else{
			pdaStockActual.setDescripcionError("");	
		}
		
		return pdaStockActual;
	}
	
	@RequestMapping(value = "/pdaP31CorrStockPCN", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid final PdaStock pdaStock,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		PdaStock pdaStockResultado = new PdaStock();
		PdaStock pdaStockActual = new PdaStock();
		int posicion = 0;
		String resultado = "pda_p31_correccionStockPCN";
		String botPag = "";
		Locale locale = LocaleContextHolder.getLocale();
		
		String origenPantalla = request.getParameter("origenPantalla");
		String selectProv = request.getParameter("selProv");

		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");

		try{
			if (request.getParameter("posicion") != null){
				posicion = Integer.parseInt(request.getParameter("posicion"));
			}
			
			//Obtención del objeto de stock actual
			pdaStockActual = obtenerReferenciasPorPosicion(session,posicion);
			
			if (request.getParameter("actionAnt") != null || request.getParameter("actionSig") != null || 
				request.getParameter("actionSave") != null){
				
				pdaStockActual = this.validacionesStock(posicion, pdaStockActual, pdaStock, session);
				
				//Si se ha producido un error con la validación lo mostramos.
				if (pdaStockActual.getDescripcionError() != null && !pdaStockActual.getDescripcionError().equals("")){
					//Si solo existe una referencia y es de electro o bazar, obtenemos su stock plataforma 
					obtenerStockPlataformaPorRef(pdaStockActual,user);
					
					model.addAttribute("pdaStock", pdaStockActual);
					logger.info("PDA Error Validacion Corrección stock");
					return "pda_p31_correccionStockPCN";
				}
			}
			
			//Controlamos si se ha pulsado algún botón de paginación.
			if (request.getParameter("actionAnt") != null || request.getParameter("actionSig") != null){
				
				if (request.getParameter("actionAnt") != null){
					botPag = "botonAnt";
				}else if (request.getParameter("actionSig") != null){
					botPag = "botonSig";
				}
				
				pdaStockResultado = this.pdaP31Paginar(posicion, botPag, session);
				resultado = "pda_p31_correccionStockPCN";
				
				//Si solo existe una referencia y es de electro o bazar, obtenemos su stock plataforma 
				obtenerStockPlataformaPorRef(pdaStockActual,user);
				
				model.addAttribute("pdaStock", pdaStockResultado);
				
				return resultado;
			}
			
			if (request.getParameter("actionSave") != null){
				
				pdaStockActual = this.guardarRegistros(pdaStockActual, session);
				
				if (pdaStockActual.getCodigoError().equals(Constantes.STOCK_TIENDA_RESULTADO_KO)){
					if (pdaStockActual.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
						//Recálculo de las madres para refrescar stocks
						ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
						stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
						stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
						List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
						listaReferencias.add(BigInteger.valueOf(pdaStockActual.getCodArtOrig()));
						stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
						
						
						ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
						paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
						ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
						
						if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
							logger.error("###########################################################################################################");
							logger.error("############################## CONTROLADOR: pdaP31CorreccionStockPCNController(1)	 ########################");
							logger.error("###########################################################################################################");
						}
						
						ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest, session);
						if (null != stockTiendaResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
							//Refresco los stock en sesión
							session.setAttribute("consultaStock", stockTiendaResponse);
						}
					}
					//Si solo existe una referencia y es de electro o bazar, obtenemos su stock plataforma 
					obtenerStockPlataformaPorRef(pdaStockActual,user);
					
					model.addAttribute("pdaStock", pdaStockActual);
					resultado = "pda_p31_correccionStockPCN";
				}else{//OK o Warning
					if (pdaStockActual.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
						//Recálculo de las madres para refrescar stocks
						ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
						stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
						stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
						List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
						listaReferencias.add(BigInteger.valueOf(pdaStockActual.getCodArtOrig()));
						stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
						
						
						ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
						paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
						ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
						
						if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
							logger.error("###########################################################################################################");
							logger.error("############################## CONTROLADOR: pdaP31CorreccionStockPCNController(2)	 ########################");
							logger.error("###########################################################################################################");
						}
						
						ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
						List<ReferenciaType> listaMadres = Arrays.asList(stockTiendaResponse.getListaReferencias());
						PagedListHolder<ReferenciaType> listHolder = new PagedListHolder<ReferenciaType>(listaMadres);
						listHolder.setPageSize(Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS);
						session.setAttribute("listaRelaciones", listHolder);
						PdaSeleccionStock pdaSeleccionStock = new PdaSeleccionStock();
						pdaSeleccionStock.setCodArtOrig(pdaStockActual.getCodArtOrig());
						pdaSeleccionStock.setOrigen(pdaStockActual.getOrigen());
						pdaSeleccionStock.setMMC(pdaStockActual.getMMC());
						String mensajeError = null;
						if (null != stockTiendaResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
							//Refresco los stock en sesión
							session.setAttribute("consultaStock", stockTiendaResponse);
						}
						if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
							mensajeError = stockTiendaResponse.getDescripcionRespuesta();
						}else if (stockTiendaResponse.getListaReferencias().length > 0){	
							boolean hasWarning = false;
							for (ReferenciaType referencia : stockTiendaResponse.getListaReferencias()){
								if (referencia.getCodigoError().equals(BigInteger.valueOf(new Long(2))) && !hasWarning){
									mensajeError = referencia.getMensajeError();
									hasWarning = true;
								}
									
							}	
						}
						if(mensajeError != null && !mensajeError.equals("")){
							model.addAttribute("mensajeError", mensajeError);
						}
						model.addAttribute("mensajeGuardado", this.messageSource.getMessage(
								"pda_p31_correccionStockPCN.guardadoCorrecto", null, locale));
						model.addAttribute("pdaSeleccionStock", pdaSeleccionStock);
						model.addAttribute("listaRelacion", listHolder);
						resultado = "pda_p29_correccionStockSeleccion";				
					}else{
						redirectAttributes.addAttribute("codArt", pdaStockActual.getCodArtOrig());
						redirectAttributes.addAttribute("guardadoStockOk", "S");
						if (pdaStockActual.getOrigen().equals("DR")){
							resultado = "redirect:pdaP12DatosReferencia.do?origenPantalla="+origenPantalla+"&guardadoOK=true";
						}else if(pdaStockActual.getOrigen().equals("SP")){
							resultado = "redirect:pdaP13SegPedidos.do";
						// Prehuecos
						}else if(pdaStockActual.getOrigen().equals("PH")){
							redirectAttributes.addAttribute("procede", "pdaP115PrehuecosLineal");
							resultado = "redirect:pdaP115PrehuecosLineal.do";
						// Prehuecos - Seg. Pedidos
						}else if(pdaStockActual.getOrigen().equals("PHStock")){
							redirectAttributes.addAttribute("procede", "pdaP115PrehuecosLineal");
							resultado = "redirect:pdaP13SegPedidos.do";
						// Devolución Fin de Campaña.
						}else if(pdaStockActual.getOrigen().equals("DVFN")){
							resultado = "redirect:pdaP62StockLinkVuelta.do?flgBienGuardado=S&origenPantalla="+origenPantalla+"&selectProv="+selectProv;
						// Devolución Orden de Retirada.
						}else if(pdaStockActual.getOrigen().equals("DVOR")){
							resultado = "redirect:pdaP63StockLinkVuelta.do?flgBienGuardado=S&origenPantalla="+origenPantalla+"&selectProv="+selectProv;
						}else if(pdaStockActual.getOrigen().equals("REPO1")){
							resultado = "redirect:pdaP91ListadoRepoVuelta.do";
						}else if(pdaStockActual.getOrigen().equals("REPO2")){
							resultado = "redirect:pdaP92ListadoRepoAntVuelta.do";
						//Captura Restos
						}else if(pdaStockActual.getOrigen().equals(Constantes.MENU_PDA_CAPTURA_RESTOS)){
							resultado = "redirect:pdaP98CapturaRestos.do";
						// Restos almacen
						}else if(pdaStockActual.getOrigen().equals(Constantes.MENU_PDA_SACADA_RESTOS)){
							resultado = "redirect:pdaP99SacadaRestos.do";
						}else{
							resultado = "redirect:pdaP15MovStocks.do";
						}
					}
				}
				return resultado;
			}
			
			if (request.getParameter("actionVolverConsulta") != null){
				redirectAttributes.addAttribute("codArt", pdaStockActual.getCodArtOrig());
				if (pdaStockActual.getOrigen().equals("DR")){
					resultado = "redirect:pdaP12DatosReferencia.do?origenPantalla="+origenPantalla;
				} else if(pdaStockActual.getOrigen().equals("SP")){
					resultado = "redirect:pdaP13SegPedidos.do";
				// Prehuecos
				}else if(pdaStockActual.getOrigen().equals("PH")){
					redirectAttributes.addAttribute("procede", "pdaP115PrehuecosLineal");
					resultado = "redirect:pdaP115PrehuecosLineal.do";
				// Prehuecos - Seg. Pedidos
				}else if(pdaStockActual.getOrigen().equals("PHStock")){
					redirectAttributes.addAttribute("procede", "pdaP115PrehuecosLineal");
					resultado = "redirect:pdaP13SegPedidos.do";
				}else if(pdaStockActual.getOrigen().equals("DVFN")){
					resultado = "redirect:pdaP62StockLinkVuelta.do?origenPantalla="+origenPantalla+"&selectProv="+selectProv;
				}else if(pdaStockActual.getOrigen().equals("DVOR")){
					resultado = "redirect:pdaP63StockLinkVuelta.do?origenPantalla="+origenPantalla+"&selectProv="+selectProv;
				}else if(pdaStockActual.getOrigen().equals("REPO1")){
					resultado = "redirect:pdaP91ListadoRepoVuelta.do";
				}else if(pdaStockActual.getOrigen().equals("REPO2")){
					resultado = "redirect:pdaP92ListadoRepoAntVuelta.do";
				}else if(pdaStockActual.getOrigen().equals(Constantes.MENU_PDA_CAPTURA_RESTOS)){
					resultado = "redirect:pdaP98CapturaRestos.do";
				}else if(pdaStockActual.getOrigen().equals(Constantes.MENU_PDA_SACADA_RESTOS)){
					resultado = "redirect:pdaP99SacadaRestos.do";
				}else{
					resultado = "redirect:pdaP15MovStocks.do";
				}
			}

			if (request.getParameter("actionPrev") != null){
				ConsultarStockResponseType stockTiendaResponse = (ConsultarStockResponseType) session.getAttribute("consultaStock");
				List<ReferenciaType> listaMadres = Arrays.asList(stockTiendaResponse.getListaReferencias());
				PagedListHolder<ReferenciaType> listHolder = new PagedListHolder<ReferenciaType>(listaMadres);
				
				if (pdaStockActual.getTipoMensaje().equals(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA)){
					listHolder.setPageSize(Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL);
				}else {
					listHolder.setPageSize(Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS);
				}
				session.setAttribute("listaRelaciones", listHolder);
				PdaSeleccionStock pdaSeleccionStock = new PdaSeleccionStock();
				pdaSeleccionStock.setCodArtOrig(pdaStockActual.getCodArtOrig());
				pdaSeleccionStock.setOrigen(pdaStockActual.getOrigen());
				pdaSeleccionStock.setMMC(pdaStockActual.getMMC());
				if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
					String mensajeError = stockTiendaResponse.getDescripcionRespuesta();
					model.addAttribute("mensajeError", mensajeError);
				}
				model.addAttribute("pdaSeleccionStock", pdaSeleccionStock);
				model.addAttribute("listaRelacion", listHolder);
				resultado = "pda_p29_correccionStockSeleccion";				
			}
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return resultado;
	}
	
	private PdaStock obtenerReferenciasPorPosicion(HttpSession session, int posicion) throws Exception{
		
		
		PdaStock pdaStock = new PdaStock();
		ArrayList<PdaArticulo> listaArticulos =new ArrayList<PdaArticulo>();
		ArrayList<PdaArticulo> listaArticulosPagina =new ArrayList<PdaArticulo>();
		int indiceArticuloInicialPagina = 0;
		int indiceArticuloFinalPagina = 0;
		int totalArticulos = 0;

		if (session.getAttribute("pdaStock") != null && session.getAttribute("pdaStock") != null)
		{
			pdaStock = (PdaStock) session.getAttribute("pdaStock");
			listaArticulos = pdaStock.getListaArticulos();
			
			if (pdaStock.getTipoMensaje().equals(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA)) { //Es textil
				//Cálculo de los artículos de la página 
				indiceArticuloInicialPagina = (posicion - 1)*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL;
				totalArticulos = listaArticulos.size();
				if (totalArticulos >= (posicion*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL)){
					indiceArticuloFinalPagina = (posicion*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL);
				}else{
					indiceArticuloFinalPagina = totalArticulos;
				}
				listaArticulosPagina = new ArrayList<PdaArticulo>(listaArticulos.subList(indiceArticuloInicialPagina, indiceArticuloFinalPagina));
			} else {
				//Cálculo de los artículos de la página 
				indiceArticuloInicialPagina = (posicion - 1)*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
				totalArticulos = listaArticulos.size();
				if (totalArticulos >= (posicion*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS)){
					indiceArticuloFinalPagina = (posicion*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS);
				}else{
					indiceArticuloFinalPagina = totalArticulos;
				}
				listaArticulosPagina = new ArrayList<PdaArticulo>(listaArticulos.subList(indiceArticuloInicialPagina, indiceArticuloFinalPagina));
			}
			//Refrescamos el total del objeto.
			pdaStock.setListaArticulosPagina(listaArticulosPagina);
		}
		
		return pdaStock;
	}
	
	private PdaStock guardarRegistros(PdaStock pdaStockActual, HttpSession session) throws Exception {
		
		ModificarStockRequestType modificarStockRequest = new ModificarStockRequestType();
		User user = (User) session.getAttribute("user");
		double valorStock = 0;
		modificarStockRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
		
		//Carga de referencias cambiadas
		List<ReferenciaModType> referencias = new ArrayList<ReferenciaModType>();
		for (PdaArticulo pdaArticulo : pdaStockActual.getListaArticulos()) {
			valorStock = Double.parseDouble(pdaArticulo.getStock().replace(',', '.'));
			//Se guardan los datos cuyo stock<>0 o tipo=U - Unitaria, C- sompra o S Estándar o Vacío
			if (((pdaArticulo.getTipo() == null || pdaArticulo.getTipo().trim().equals("") || 
					pdaArticulo.getTipo().equals(Constantes.STOCK_TIENDA_TIPO_UNITARIA) ||
					pdaArticulo.getTipo().equals(Constantes.STOCK_TIENDA_TIPO_COMPRA) ||
					pdaArticulo.getTipo().equals(Constantes.STOCK_TIENDA_TIPO_ESTANDAR)
				 ) 
				|| valorStock != 0) && valorStock >= 0){
				ReferenciaModType referenciaModType = new ReferenciaModType();
				referenciaModType.setCodigoReferencia(new BigInteger(pdaArticulo.getCodArt().toString()));
				//referenciaModType.setBandejas(new BigInteger("1"));
				//Redondeo a 2 decimales del stock
				//Peticion 55001. Corrección errores del LOG.
				BigDecimal bdStock = Utilidades.convertirStringABigDecimal(pdaArticulo.getStock());
			    BigDecimal roundedStock = bdStock.setScale(2, BigDecimal.ROUND_HALF_UP);
				referenciaModType.setStock(roundedStock);
				referencias.add(referenciaModType);
			}
		}
		
		ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
		paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
		ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
		
		if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
			logger.error("###########################################################################################################");
			logger.error("############################## CONTROLADOR: pdaP31CorreccionStockPCNController ############################");
			logger.error("###########################################################################################################");
		}
		
		if (referencias != null && referencias.size() > 0){
			modificarStockRequest.setListaReferencias(referencias.toArray(new ReferenciaModType[referencias.size()]));
			ModificarStockResponseType modificarStockResponseType = this.correccionStockService.modificarStock(modificarStockRequest,session);
			if (modificarStockResponseType != null){
				pdaStockActual.setCodigoError(modificarStockResponseType.getCodigoRespuesta());
				pdaStockActual.setDescripcionError(modificarStockResponseType.getDescripcionRespuesta());
			}
			else{
				pdaStockActual.setCodigoError(Constantes.STOCK_TIENDA_RESULTADO_KO);
				pdaStockActual.setDescripcionError("Error");
			}
		}else{
			pdaStockActual.setCodigoError(Constantes.STOCK_TIENDA_RESULTADO_OK);
			pdaStockActual.setDescripcionError("");
		}

		return pdaStockActual;
	}
	
	private void obtenerStockPlataformaPorRef(PdaStock pdaStockResultado,User user) throws Exception{	
		//Si solo existe una referencia y es de electro o bazar, se pinta el stock plataforma.
		if(pdaStockResultado.getListaArticulosPagina().size() == 1){
			
			PdaArticulo pdaArticulo = pdaStockResultado.getListaArticulosPagina().get(0);
			VDatosDiarioArt vDatosDiarioArt = obtenerDatosDiarioArt(pdaArticulo);

			if(vDatosDiarioArt != null && vDatosDiarioArt.getGrupo1().longValue() == new Long(Constantes.AREA_BAZAR).longValue() || vDatosDiarioArt.getGrupo1().longValue() == new Long(Constantes.AREA_ELECTRO).longValue()) {
				Long codArt = pdaArticulo.getCodArt() != null ? pdaArticulo.getCodArt() : (pdaArticulo.getCodArtOrig() != null ? pdaArticulo.getCodArtOrig() : null);
				
				if(codArt != null){
					Double stockPlataforma = this.obtenerStockPlataforma(codArt, user.getCentro().getCodCentro());
					pdaArticulo.setStockPlataforma(stockPlataforma);
				}
			}
		}
	}
	
	public VDatosDiarioArt obtenerDatosDiarioArt(PdaArticulo pdaArticulo) throws Exception{
		VDatosDiarioArt vDatosDiarioArtRes = null;
		VDatosDiarioArt vDatosDiarioArt = null;

		Long codArt = pdaArticulo.getCodArt() != null ? pdaArticulo.getCodArt() : (pdaArticulo.getCodArtOrig() != null ? pdaArticulo.getCodArtOrig() : null);
		if(codArt != null){
			vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(codArt);
			vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
		}

		return vDatosDiarioArtRes;
	}
	
	private Double obtenerStockPlataforma(Long codArt, Long codCentro) throws Exception {
		Double result = null;
		try{
			StockPlataforma sp = new StockPlataforma();
			sp.setCodCentro(codCentro);
			sp.setCodArt(codArt);


			StockPlataforma spSalida = this.stockPlataformaService.find(sp);
			if (spSalida!=null && !spSalida.equals("") && spSalida.getStock()!=null && !spSalida.getStock().equals("")){

				result = spSalida.getStock().doubleValue();
			}
		} catch (Exception e) {
			result = new Double(-9999);
		}
		return result;
	}
}