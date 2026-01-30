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

import es.eroski.misumi.model.InventarioRotativo;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaSeleccionStock;
import es.eroski.misumi.model.pda.PdaStock;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaModType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.InventarioRotativoService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP45InvLibCorreccionStockPCNController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP45InvLibCorreccionStockPCNController.class);
	
	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private StockTiendaService correccionStockService;
	
	@Autowired
	private InventarioRotativoService inventarioRotativoService;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	
	
	public PdaStock pdaP45Paginar(int posicion, String botPag, HttpSession session, PdaStock pdaStock) {
		
		PdaStock pdaStockResultado = new PdaStock();
		
		try 
		{
			//Paginamos a la página que corresponda.
			int posicionBusqueda= 1;
			if (botPag != null && botPag.equals("botonAnt"))
			{
				//Obtenemos el elemento de la posición anterior.
				posicionBusqueda = posicion-1;
			}
			else if (botPag != null && botPag.equals("botonSig"))
			{
				posicionBusqueda = posicion+1;
			}
				
			pdaStockResultado = this.obtenerReferenciasPorPosicion(session,posicionBusqueda);
			pdaStockResultado.setPosicionGrupoArticulos(posicionBusqueda);
			pdaStockResultado.setNoGuardar(pdaStock.getNoGuardar());
			pdaStockResultado.setSeccion(pdaStock.getSeccion());
		
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
		
		try 
		{
			//Tratamiento de errores por cada fila
			int indiceListaArticulos = (posicion - 1)*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
			for (PdaArticulo pdaArticulo : pdaStock.getListaArticulosPagina()) {
				pdaArticuloGuardado = listaArticulos.get(indiceListaArticulos);
				
				if (!pdaArticulo.getStock().equals(pdaArticuloGuardado.getStock()))
				{
					
					//Comprobamos el formato.
					try{
						//Aceptamos que venga separado tanto por comas como por puntos.
						Double stockDouble = Double.parseDouble(pdaArticulo.getStock().replace(',', '.'));

						
						if (Double.compare(stockDouble, 0.0) < 0){
							pdaArticuloGuardado.setDescripcionError(this.messageSource.getMessage(
									"pda_p45_correccionStockPCN.formatoStock", null, locale));
							existeError = true;
						} else {
							pdaArticuloGuardado.setStock(pdaArticulo.getStock());
							pdaArticuloGuardado.setDescripcionError("");
							pdaArticuloGuardado.setOrigenInventario(pdaStockActual.getOrigenInventario());
						}
					}
					catch(Exception e) {
						pdaArticuloGuardado.setDescripcionError(this.messageSource.getMessage(
								"pda_p45_correccionStockPCN.formatoStock", null, locale));
						existeError = true;
					}
					
					//Si se ha modificado algún dato de la referencia entonces
					//hay que quitar el aviso de la referencia
					//y si la referencia forma parte de una madre hay que quitar el aviso de la madre y sus hijas
					//y si la madre forma parte de una unitaria hay que quitar el aviso de la unitaria y sus madres e hijas
					User user = (User)session.getAttribute("user");
		    		InventarioRotativo inventarioRotativo1 = new InventarioRotativo();
		    		inventarioRotativo1.setCodCentro(user.getCentro().getCodCentro());
		    		inventarioRotativo1.setCodMac(user.getMac());
		    		inventarioRotativo1.setCodArticulo(pdaStockActual.getCodArtOrig());
		    		inventarioRotativo1.setCodArticuloRela(pdaArticuloGuardado.getCodArt());
		    		this.inventarioRotativoService.updateAvisoPdaAll("", inventarioRotativo1);
				}
				else
				{
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
					"pda_p45_correccionStockPCN.formatoStock", null, locale));
		}else{
			pdaStockActual.setDescripcionError("");	
		}
		
		return pdaStockActual;
	}
	
	@RequestMapping(value = "/pdaP45InvLibCorreccionStockPCN", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid final PdaStock pdaStock,
			HttpServletRequest request,
			HttpServletResponse response) {
		

		PdaStock pdaStockResultado = new PdaStock();
		PdaStock pdaStockActual = new PdaStock();
		int posicion = 0;
		String resultado = "pda_p45_invLibCorreccionStockPCN";
		String botPag = "";
		Locale locale = LocaleContextHolder.getLocale();
		
		try 
		{
			if (request.getParameter("posicion") != null)
			{
				posicion = Integer.parseInt(request.getParameter("posicion"));
			}
			
			//Obtención del objeto de stock actual
			pdaStockActual = this.obtenerReferenciasPorPosicion(session,posicion);
			
			if (request.getParameter("actionAnt") != null || request.getParameter("actionSig") != null || 
				request.getParameter("actionSave") != null)
			{
				
				pdaStockActual = this.validacionesStock(posicion, pdaStockActual, pdaStock, session);
				
				//Si se ha producido un error con la validación lo mostramos.
				if (pdaStockActual.getDescripcionError() != null && !pdaStockActual.getDescripcionError().equals(""))
				{
					model.addAttribute("pdaStock", pdaStockActual);
					logger.info("PDA Error Validacion Corrección stock");
					return "pda_p45_invLibCorreccionStockPCN";
				}
			}
			
			//Controlamos si se ha pulsado algún botón de paginación.
			if (request.getParameter("actionAnt") != null || request.getParameter("actionSig") != null)
			{
				if (request.getParameter("actionAnt") != null)
				{
					botPag = "botonAnt";
				}
				else if (request.getParameter("actionSig") != null)
				{
					botPag = "botonSig";
				}
				
				pdaStockResultado = this.pdaP45Paginar(posicion, botPag, session, pdaStock);
				resultado = "pda_p45_invLibCorreccionStockPCN";
				
				model.addAttribute("pdaStock", pdaStockResultado);
				
				return resultado;
			}
			
			if (request.getParameter("actionSave") != null){
				
				pdaStockActual = this.guardarRegistros(pdaDatosCab, pdaStockActual, session);
				
				if (pdaStockActual.getCodigoError().equals(Constantes.STOCK_TIENDA_RESULTADO_KO)){
					pdaStockActual.setDescripcionError(this.messageSource.getMessage("pda_p45_correccionStockPCN.guardadoError", null, locale));
					model.addAttribute("pdaStock", pdaStockActual);
					resultado = "pda_p45_invLibCorreccionStockPCN";
				}else{//OK
					if (pdaStockActual.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
						//Obtenemos de sesión la información de usuario.
						User user = (User)session.getAttribute("user");
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
							logger.error("############################## CONTROLADOR: pdaP45InvLibCorreccionStockPCNController	 ####################");
							logger.error("###########################################################################################################");
						} 
						
						ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest, session);
						List<ReferenciaType> listaMadres = Arrays.asList(stockTiendaResponse.getListaReferencias());
						PagedListHolder<ReferenciaType> listHolder = new PagedListHolder<ReferenciaType>(listaMadres);
						listHolder.setPageSize(Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS);
						session.setAttribute("listaRelaciones", listHolder);
						PdaSeleccionStock pdaSeleccionStock = new PdaSeleccionStock();
						pdaSeleccionStock.setCodArtOrig(pdaStockActual.getCodArtOrig());
						pdaSeleccionStock.setOrigen(pdaStockActual.getOrigen());
						pdaSeleccionStock.setOrigenInventario(pdaStockActual.getOrigenInventario());
						pdaSeleccionStock.setMMC(pdaStockActual.getMMC());
						pdaSeleccionStock.setNoGuardar(pdaStockActual.getNoGuardar());
						pdaSeleccionStock.setSeccion(pdaStockActual.getSeccion());
						String mensajeError = null;
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
								"pda_p45_correccionStockPCN.guardadoCorrecto", null, locale));
						model.addAttribute("pdaSeleccionStock", pdaSeleccionStock);
						model.addAttribute("listaRelacion", listHolder);
						resultado = "pda_p43_invLibCorreccionStockSeleccion";				
					}else{
						redirectAttributes.addAttribute("codArtAnterior", "anterior");
						redirectAttributes.addAttribute("noGuardar", pdaStock.getNoGuardar());
						redirectAttributes.addAttribute("seccion", pdaStock.getSeccion());
						resultado = "redirect:pdaP42InventarioLibre.do";
					}
				}
				return resultado;
			}
			
			if (request.getParameter("actionVolverConsulta") != null){
				redirectAttributes.addAttribute("codArtAnterior", "anterior");
				redirectAttributes.addAttribute("noGuardar", pdaStock.getNoGuardar());
				redirectAttributes.addAttribute("seccion", pdaStock.getSeccion());
				resultado = "redirect:pdaP42InventarioLibre.do";
			}

			if (request.getParameter("actionPrev") != null){
				ConsultarStockResponseType stockTiendaResponse = (ConsultarStockResponseType) session.getAttribute("consultaStock");
				List<ReferenciaType> listaMadres = Arrays.asList(stockTiendaResponse.getListaReferencias());
				PagedListHolder<ReferenciaType> listHolder = new PagedListHolder<ReferenciaType>(listaMadres);
				listHolder.setPageSize(Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS);
				session.setAttribute("listaRelaciones", listHolder);
				PdaSeleccionStock pdaSeleccionStock = new PdaSeleccionStock();
				pdaSeleccionStock.setCodArtOrig(pdaStockActual.getCodArtOrig());
				pdaSeleccionStock.setOrigen(pdaStockActual.getOrigen());
				pdaSeleccionStock.setMMC(pdaStockActual.getMMC());
				pdaSeleccionStock.setOrigenInventario(pdaStockActual.getOrigenInventario());
				pdaSeleccionStock.setNoGuardar(pdaStockActual.getNoGuardar());
				pdaSeleccionStock.setSeccion(pdaStockActual.getSeccion());
				if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
					String mensajeError = stockTiendaResponse.getDescripcionRespuesta();
					model.addAttribute("mensajeError", mensajeError);
				}
				model.addAttribute("pdaSeleccionStock", pdaSeleccionStock);
				model.addAttribute("listaRelacion", listHolder);
				resultado = "pda_p43_invLibCorreccionStockSeleccion";				
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
			//Cálculo de los artículos de la página 
			indiceArticuloInicialPagina = (posicion - 1)*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
			totalArticulos = listaArticulos.size();
			if (totalArticulos >= (posicion*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS)){
				indiceArticuloFinalPagina = (posicion*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS);
			}else{
				indiceArticuloFinalPagina = totalArticulos;
			}
			listaArticulosPagina = new ArrayList<PdaArticulo>(listaArticulos.subList(indiceArticuloInicialPagina, indiceArticuloFinalPagina));
					
			//Refrescamos el total del objeto.
			pdaStock.setListaArticulosPagina(listaArticulosPagina);
		}
		
		return pdaStock;
	}
	
	private PdaStock guardarRegistros(PdaDatosCabecera pdaDatosCabecera, PdaStock pdaStockActual, HttpSession session) throws Exception {
		
		try{
			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();
			String mac = user.getMac();
			Long codArtOrigen;
			Boolean referenciaUnitariaGuardada = false;

			//Buscamos el código artículo origen.
			codArtOrigen = pdaStockActual.getCodArtOrig();
			if (pdaStockActual.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
				//Si es una referencia con unitarias entonces
				//buscamos la referencia unitaria entre la lista de articulos
				//para asignarla como código artículo origen
				for (PdaArticulo pdaArticulo : pdaStockActual.getListaArticulos()) {
					if (Constantes.STOCK_TIENDA_TIPO_COMPRA.equals(pdaArticulo.getTipo())){
						codArtOrigen = pdaArticulo.getCodArt();
						break;
					}
				}
			}
			
			double valorStock = 0;
			
			//Carga de referencias cambiadas
			List<ReferenciaModType> referencias = new ArrayList<ReferenciaModType>();
			for (PdaArticulo pdaArticulo : pdaStockActual.getListaArticulos()) {
				valorStock = Double.parseDouble(pdaArticulo.getStock().replace(',', '.'));
				//Se guardan los datos cuyo stock<>0 o tipo=U - Unitaria, C- compra o S Estándar o Vacío
				/*
				if ((pdaArticulo.getTipo() == null || pdaArticulo.getTipo().trim().equals("") || 
						pdaArticulo.getTipo().equals(Constantes.STOCK_TIENDA_TIPO_UNITARIA) ||
						pdaArticulo.getTipo().equals(Constantes.STOCK_TIENDA_TIPO_COMPRA) ||
						pdaArticulo.getTipo().equals(Constantes.STOCK_TIENDA_TIPO_ESTANDAR)
					 ) 
					|| valorStock != 0){
					*/
					
					InventarioRotativo inventarioRotativo = new InventarioRotativo();

					if (pdaStockActual.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
						if (codArtOrigen.equals(pdaArticulo.getCodArt())){
							inventarioRotativo.setCodArticulo(pdaStockActual.getCodArtOrig());
							inventarioRotativo.setCodArticuloRela(pdaArticulo.getCodArt());
						}
						else{
							inventarioRotativo.setCodArticulo(codArtOrigen);
							inventarioRotativo.setCodArticuloRela(pdaArticulo.getCodArt());
						}
					}
					else{
						inventarioRotativo.setCodArticulo(codArtOrigen);
						inventarioRotativo.setCodArticuloRela(pdaArticulo.getCodArt());
					}

					inventarioRotativo.setCodCentro(codCentro);
					if (null != pdaStockActual.getOrigenGISAE() && pdaStockActual.getOrigenGISAE().equals("SI")){
						inventarioRotativo.setCodMac("GISAE");
						
					} else {
						inventarioRotativo.setCodMac(mac);
					}
					inventarioRotativo.setOrigenInventario(pdaStockActual.getOrigenInventario());
					if (Boolean.TRUE.toString().equals(pdaStockActual.getNoGuardar())){
						inventarioRotativo.setFlgNoGuardar(Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_SI);
					}
					else{
						inventarioRotativo.setFlgNoGuardar(Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_NO);
					}
					
					//Redondeo a 2 decimales del stock
					//Peticion 55001. Corrección errores del LOG.
					BigDecimal bdStock = Utilidades.convertirStringABigDecimal(pdaArticulo.getStock());
				    BigDecimal roundedStock = bdStock.setScale(2, BigDecimal.ROUND_HALF_UP);
		
					if (Constantes.INVENTARIO_LIBRE_CAMARA_ALMACEN.equals(inventarioRotativo.getOrigenInventario())){
						inventarioRotativo.setCamaraStock(roundedStock.doubleValue());
					}else{
						inventarioRotativo.setSalaStock(roundedStock.doubleValue());
					}
		
					//Cálculo de proporción compra y venta
					RelacionArticulo relacionArticuloRela = new RelacionArticulo();
					relacionArticuloRela.setCodCentro(inventarioRotativo.getCodCentro());
					relacionArticuloRela.setCodArt(inventarioRotativo.getCodArticulo());
					relacionArticuloRela.setCodArtRela(inventarioRotativo.getCodArticuloRela());
					RelacionArticulo relacionArticuloActual = this.relacionArticuloService.findOneProporciones(relacionArticuloRela);
					if(relacionArticuloActual != null){
						inventarioRotativo.setProporRefCompra(relacionArticuloActual.getProporRefCompra());
						inventarioRotativo.setProporRefVenta(relacionArticuloActual.getProporRefVenta());
					}else{
						inventarioRotativo.setProporRefCompra(new Long(1));
						inventarioRotativo.setProporRefVenta(new Long(1));
					}
					
					//Obtención de area y sección
					VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
					vDatosDiarioArt.setCodArt(pdaArticulo.getCodArt());
					vDatosDiarioArt = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
					
					if (vDatosDiarioArt == null && codArtOrigen != null){
						//Si la referencia no esta en VDatosDiarioArt obtengo el area y sección de la referencia unitaria, si lo tuviera
						vDatosDiarioArt = new VDatosDiarioArt();
						vDatosDiarioArt.setCodArt(codArtOrigen);
						vDatosDiarioArt = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
					}
					if (vDatosDiarioArt != null){
						inventarioRotativo.setCodArea(vDatosDiarioArt.getGrupo1());
						inventarioRotativo.setCodSeccion(vDatosDiarioArt.getGrupo2());
					}

					inventarioRotativo.setFlgStockPrincipal(Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_STOCK);
		        	inventarioRotativo.setFlgUnica(Constantes.INVENTARIO_LIBRE_UNICA_NO);

					this.inventarioRotativoService.insertUpdate(inventarioRotativo);
				/*}*/
			}
			
			pdaStockActual.setCodigoError(Constantes.STOCK_TIENDA_RESULTADO_OK);
			pdaStockActual.setDescripcionError("");
			
		}catch (Exception e) {
			pdaStockActual.setCodigoError(Constantes.STOCK_TIENDA_RESULTADO_KO);
			pdaStockActual.setDescripcionError("");
		}

		return pdaStockActual;
	}
}