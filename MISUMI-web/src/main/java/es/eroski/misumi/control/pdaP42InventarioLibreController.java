package es.eroski.misumi.control;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.InventarioRotativo;
import es.eroski.misumi.model.InventarioRotativoGisae;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosGuardadosInvLib;
import es.eroski.misumi.model.pda.PdaDatosInventarioLibre;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.model.pda.PdaSeleccionStock;
import es.eroski.misumi.model.pda.PdaStock;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaModType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.InventarioRotativoGisaeService;
import es.eroski.misumi.service.iface.InventarioRotativoService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VRotacionRefService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP42InventarioLibreController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP42InventarioLibreController.class);
	
	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private InventarioRotativoService inventarioRotativoService;
	
	@Autowired
	private StockTiendaService correccionStockService;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	@Autowired
	private InventarioRotativoGisaeService inventarioRotativoGisaeService;

	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private VRotacionRefService vRotacionRefService;
	
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	@RequestMapping(value = "/pdaP42InventarioLibre", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@Valid final Long codArt,@Valid final String origen,
			@RequestParam(value="codArtAnterior", required=false) String codArtAnterior, 
			@RequestParam(value="origenInventario", required=false) String origenInventario,
			@RequestParam(value="mmc", required=false) String mmc,
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE,
			@RequestParam(value="noGuardar", required=false, defaultValue = "") String noGuardar,
			@RequestParam(value="seccion", required=false, defaultValue = "-1") String seccion,
			HttpSession session,HttpServletRequest request, RedirectAttributes redirectAttributes) {

		Locale locale = LocaleContextHolder.getLocale();
		
		PdaDatosInventarioLibre pdaDatosInventarioLibreResultado = new PdaDatosInventarioLibre();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		String resultado = "pda_p42_inventarioLibre";
		
		try{
			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();
			
			if (seccion != null){
				pdaDatosCab.setSeccion(seccion);
			}
			
			if (codArtAnterior != null && codArtAnterior.equals("anterior")){
				//En este caso venimos de la ventana de error.
				//Tendremos que cargar la última visualizada por el usuario.
				pdaDatosInventarioLibreResultado = this.obtenerUltimaReferencia(session, request);
				origenGISAE = pdaDatosInventarioLibreResultado.getOrigenGISAE();
			}else if (codArt != null){
				//En este caso venimos de los link de cantidades.
				//Primero actualizamos el flgNoGuardar antes de ir a la pantalla de modificación de cantidades
				if (Boolean.TRUE.toString().equals(noGuardar) || Boolean.FALSE.toString().equals(noGuardar)){
					String flgNoGuardar = Boolean.TRUE.toString().equals(noGuardar) ? Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_SI : Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_NO;
					this.updateFlgNoGuardarPda(flgNoGuardar, codArt, codCentro, user.getMac());
				}
				resultado = this.cargaInvLibCorreccionStock(model, codArt, codCentro, origenInventario, origenGISAE, noGuardar, pdaDatosCab, mmc, session, request);
			}else{
				boolean isGISAE = "SI".equals(origenGISAE);
				Long codSeccion = null;
				if (isGISAE){
					if (null != pdaDatosInventarioLibreResultado.getCodSeccion()){
						codSeccion = pdaDatosInventarioLibreResultado.getCodSeccion();
					}
				} 
				this.obtenerReferenciasSesion(session, codSeccion ,isGISAE);
				List<PdaDatosInventarioLibre> lista = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
				if (null != lista && !lista.isEmpty()){
					pdaDatosInventarioLibreResultado = (PdaDatosInventarioLibre) lista.get(0);
					
					//Si no es referencia única y no tiene varias unitarias hay que refrescar los datos de pantalla con la suma de cantidades
					if (!Constantes.INVENTARIO_LIBRE_UNICA_SI.equals(pdaDatosInventarioLibreResultado.getFlgUnica()) && 
							!Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibreResultado.getFlgVariasUnitarias())){
						this.cargarDatosSumaTablaTemporal(pdaDatosInventarioLibreResultado);
					}

					//Obtenemos el stock actual.
					ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
					stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(pdaDatosInventarioLibreResultado.getCodCentro()));
					stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
					List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
					listaReferencias.add(BigInteger.valueOf(pdaDatosInventarioLibreResultado.getCodArticulo()));
					stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
					boolean errorConsultaStock = false;
					
					try{
						ConsultarStockResponseType stockTiendaResponse = null;
						if (stockTiendaRequest.equals(request.getAttribute("stockTiendaRequestCopia"))){
							stockTiendaResponse = (ConsultarStockResponseType) request.getAttribute("stockTiendaResponseCopia");
						}else{
							ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
							paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
							ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
							
							if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
								logger.error("###########################################################################################################");
								logger.error("####################### CONTROLADOR: pdaP42InventarioLibreController  ########");
								logger.error("###########################################################################################################");
							}
							
							stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
							request.setAttribute("stockTiendaRequestCopia", stockTiendaRequest);
							request.setAttribute("stockTiendaResponseCopia", stockTiendaResponse);
						}
						
						if (null == stockTiendaResponse || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
							pdaDatosInventarioLibreResultado.setStockActual(new Double(0));
							errorConsultaStock = true;
						}else{
							if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								pdaDatosInventarioLibreResultado.setStockActual(stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue());
							} else {
								pdaDatosInventarioLibreResultado.setStockActual(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue());
							}
						}				
					}catch (Exception e) {
						errorConsultaStock = true;
						pdaDatosInventarioLibreResultado.setStockActual(new Double(0));
					}	

					//Redondeo a 2 decimales del stock
					//Peticion 55001. Corrección errores del LOG.
					BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibreResultado.getSalaStock());
				    BigDecimal roundedSalaStock = bdSalaStock.setScale(2, BigDecimal.ROUND_HALF_UP);

					//Redondeo a 2 decimales del stock
					//Peticion 55001. Corrección errores del LOG.
					BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibreResultado.getCamaraStock());
					BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);

					BigDecimal roundedTotalStock = roundedCamaraStock.add(roundedSalaStock);
					pdaDatosInventarioLibreResultado.setTotalStock(Utilidades.convertirDoubleAString(roundedTotalStock.doubleValue(),"###0.00").replace('.', ','));

					if (Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS.equals(pdaDatosInventarioLibreResultado.getFlgStockPrincipal())){
						Long lCamaraBandeja = new Long(pdaDatosInventarioLibreResultado.getCamaraBandeja()!=null?pdaDatosInventarioLibreResultado.getCamaraBandeja():"0");
						Long lSalaBandeja = new Long(pdaDatosInventarioLibreResultado.getSalaBandeja()!=null?pdaDatosInventarioLibreResultado.getSalaBandeja():"0");
						
						//Cálculo de campos calculados
						Long totalBandejas = lCamaraBandeja + lSalaBandeja; 
						pdaDatosInventarioLibreResultado.setTotalBandeja(totalBandejas.toString());
						Double diferencia = totalBandejas - pdaDatosInventarioLibreResultado.getStockActual();
						if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibreResultado.getFlgVariasUnitarias())){
							//Si tiene referencias unitarias hay que mostrar diferencia 0
							diferencia = new Double(0);
						}
						pdaDatosInventarioLibreResultado.setDiferencia(diferencia);
					}else{
						Double diferencia = roundedTotalStock.doubleValue() - pdaDatosInventarioLibreResultado.getStockActual();
						if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibreResultado.getFlgVariasUnitarias())){
							//Si tiene referencias unitarias hay que mostrar diferencia 0
							diferencia = new Double(0);
						}
						pdaDatosInventarioLibreResultado.setDiferencia(diferencia);
					}

					//Formateo de cantidades de bandejas
					pdaDatosInventarioLibreResultado.setSalaBandeja(pdaDatosInventarioLibreResultado.getSalaBandeja()!=null?pdaDatosInventarioLibreResultado.getSalaBandeja():"0");
					pdaDatosInventarioLibreResultado.setCamaraBandeja(pdaDatosInventarioLibreResultado.getCamaraBandeja()!=null?pdaDatosInventarioLibreResultado.getCamaraBandeja():"0");

					//Formateo de cantidades de stock
					pdaDatosInventarioLibreResultado.setSalaStock(Utilidades.convertirDoubleAString(roundedSalaStock.doubleValue(),"###0.00").replace('.', ','));
					pdaDatosInventarioLibreResultado.setCamaraStock(Utilidades.convertirDoubleAString(roundedCamaraStock.doubleValue(),"###0.00").replace('.', ','));

					//Carga de descripciones de stock actual y diferencia
					if (errorConsultaStock){
						pdaDatosInventarioLibreResultado.setDescStockActual(this.messageSource.getMessage(
								"pda_p42_inventarioLibre.stockActualError", null, locale));
						pdaDatosInventarioLibreResultado.setDescDiferencia(this.messageSource.getMessage(
								"pda_p42_inventarioLibre.diferenciaError", null, locale));
					}else{
						pdaDatosInventarioLibreResultado.setDescStockActual(this.messageSource.getMessage(
								"pda_p42_inventarioLibre.stockActual", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibreResultado.getStockActual(),"###0.00").replace('.', ',')}, locale));
						if (pdaDatosInventarioLibreResultado.getDiferencia() > 0) {
							pdaDatosInventarioLibreResultado.setDescDiferencia(this.messageSource.getMessage(
								"pda_p42_inventarioLibre.diferencia", new Object[]{"+" + Utilidades.convertirDoubleAString(pdaDatosInventarioLibreResultado.getDiferencia(),"###0.00").replace('.', ',')}, locale));
						} else {
							pdaDatosInventarioLibreResultado.setDescDiferencia(this.messageSource.getMessage(
									"pda_p42_inventarioLibre.diferencia", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibreResultado.getDiferencia(),"###0.00").replace('.', ',')}, locale));						
						}					
					}

					//Refrescamos el total del objeto.
					pdaDatosInventarioLibreResultado.setTotal(lista.size());
					
					if (!"SI".equals(origenGISAE)){
						//Obtener el tipo de rotacion
						pdaDatosInventarioLibreResultado.setTipoRotacion(this.obtenerTipoRotacion(pdaDatosInventarioLibreResultado));
					}
				}
				
			}
			
			if ("SI".equals(origenGISAE)){
				pdaDatosInventarioLibreResultado.setOrigenGISAE("SI");
				if (null != pdaDatosInventarioLibreResultado.getCodSeccion()){
					//pdaDatosCab.setSeccion(pdaDatosInventarioLibreResultado.getCodSeccion().toString());
				}
			}
			
			//Comprobamos el checkbox de noGuardar para setearlo
			if (Boolean.TRUE.toString().equals(noGuardar)){
				pdaDatosInventarioLibreResultado.setChkNoGuardar(true);
			}else{
				pdaDatosInventarioLibreResultado.setChkNoGuardar(false);
			}
			
			this.refrescarSecciones(model, pdaDatosInventarioLibreResultado, session);
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("pdaDatosInvLib", pdaDatosInventarioLibreResultado);
		
		return resultado;
	}
	
	public PdaDatosInventarioLibre pdaP42Paginar(int posicion, String botPag,
			HttpServletRequest request,
			HttpSession session) {
		
		PdaDatosInventarioLibre pdaDatosInLibResultado = new PdaDatosInventarioLibre();
		
		try{
			//Paginamos a la página que corresponda.
			int posicionBusqueda= 1;
			if (botPag != null && botPag.equals("botonAnt")){
				//Obtenemos el elemento de la posición anterior.
				posicionBusqueda = posicion-1;
			}else if (botPag != null && botPag.equals("botonSig")){
				posicionBusqueda = posicion+1;
			}
				
			pdaDatosInLibResultado = this.obtenerReferenciaPorPosicion(session,posicionBusqueda,request);

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		
		return pdaDatosInLibResultado;
	}
	
	public PdaDatosInventarioLibre validaciones(int posicion, PdaDatosInventarioLibre pdaDatosInventarioLibre, PdaDatosInventarioLibre pdaDatosInventarioLibreActual,
			HttpSession session) {
		
		Locale locale = LocaleContextHolder.getLocale();
		List<PdaDatosInventarioLibre> listaInvLib =new ArrayList<PdaDatosInventarioLibre>();
		boolean hayCamposModificados = false;
		
		try 
		{
			if (pdaDatosInventarioLibreActual != null && Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS.equals(pdaDatosInventarioLibreActual.getFlgStockPrincipal())){
				//Peticion 55001. Corrección errores del LOG.
				if ( pdaDatosInventarioLibre != null && pdaDatosInventarioLibreActual != null &&
						((pdaDatosInventarioLibre.getCamaraBandeja() != null && !pdaDatosInventarioLibre.getCamaraBandeja().equals(pdaDatosInventarioLibreActual.getCamaraBandeja())) || 
						(pdaDatosInventarioLibre.getSalaBandeja() != null && !pdaDatosInventarioLibre.getSalaBandeja().equals(pdaDatosInventarioLibreActual.getSalaBandeja()))) )
				{
					hayCamposModificados = true;
					
					//Comprobamos el formato.
					try{
						//Aceptamos que venga separado tanto por comas como por puntos.
						Long camaraBandejaLong = Long.parseLong(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");
						Long salaBandejaLong = Long.parseLong(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
						
						pdaDatosInventarioLibreActual.setCamaraBandeja(camaraBandejaLong.toString());
						pdaDatosInventarioLibreActual.setSalaBandeja(salaBandejaLong.toString());
						
						//Tenemos que guardar los cambios en sesión.
						if (session.getAttribute("listaInvLib") != null)
						{
							listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
							listaInvLib.set(posicion-1, pdaDatosInventarioLibreActual);
							session.setAttribute("listaInvLib", listaInvLib);
						}
						
						//Si va bien ponemos a vacío el error.
						pdaDatosInventarioLibreActual.setAviso("");
						pdaDatosInventarioLibreActual.setError("");
					}
					catch(Exception e) {
						pdaDatosInventarioLibreActual.setError(this.messageSource.getMessage(
								"pda_p42_inventarioLibre.formatoBandeja", null, locale));
						return pdaDatosInventarioLibreActual;
					}
				}
			}
			
			//Peticion 55001. Corrección errores del LOG.
			if ( pdaDatosInventarioLibre != null && pdaDatosInventarioLibreActual != null &&
					((pdaDatosInventarioLibre.getCamaraStock() != null && !pdaDatosInventarioLibre.getCamaraStock().equals(pdaDatosInventarioLibreActual.getCamaraStock())) ||
					(pdaDatosInventarioLibre.getSalaStock() != null && !pdaDatosInventarioLibre.getSalaStock().equals(pdaDatosInventarioLibreActual.getSalaStock()))) )
			{
				hayCamposModificados = true;
				
				try{
					//Aceptamos que venga separado tanto por comas como por puntos.
					Double camaraStockDouble = Double.parseDouble(pdaDatosInventarioLibre.getCamaraStock()!=null?pdaDatosInventarioLibre.getCamaraStock().replace(',', '.'):"0");
					Double salaStockDouble = Double.parseDouble(pdaDatosInventarioLibre.getSalaStock()!=null?pdaDatosInventarioLibre.getSalaStock().replace(',', '.'):"0");
					
					pdaDatosInventarioLibreActual.setCamaraStock(Utilidades.convertirDoubleAString(camaraStockDouble.doubleValue(),"###0.00").replace('.', ','));
					pdaDatosInventarioLibreActual.setSalaStock(Utilidades.convertirDoubleAString(salaStockDouble.doubleValue(),"###0.00").replace('.', ','));
					
					//Tenemos que guardar los cambios en sesión.
					if (session.getAttribute("listaInvLib") != null)
					{
						listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
						listaInvLib.set(posicion-1, pdaDatosInventarioLibreActual);
						session.setAttribute("listaInvLib", listaInvLib);
					}
					
					//Si va bien ponemos a vacío el aviso y el error.
					pdaDatosInventarioLibreActual.setAviso("");
					pdaDatosInventarioLibreActual.setError("");
		    		//Quitamos el aviso de base de datos
		    		InventarioRotativo inventarioRotativo = new InventarioRotativo();
		    		inventarioRotativo.setCodCentro(pdaDatosInventarioLibreActual.getCodCentro());
		    		inventarioRotativo.setCodArticulo(pdaDatosInventarioLibreActual.getCodArticulo());
		    		inventarioRotativo.setCodMac(pdaDatosInventarioLibreActual.getCodMac());
		    		this.inventarioRotativoService.updateAvisoPda("", inventarioRotativo);
				}
				catch(Exception e) {
					pdaDatosInventarioLibreActual.setError(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.formatoStock", null, locale));
					return pdaDatosInventarioLibreActual;
				}

			}
			
			if (!hayCamposModificados)
			{
				//Si no se ha modificado ningún campo y había algún error, lo quitamos.
				if (pdaDatosInventarioLibreActual.getError() != null && !"".equals(pdaDatosInventarioLibreActual.getError())){
					//pdaDatosInventarioLibreActual.setAviso("");
					pdaDatosInventarioLibreActual.setError("");

					//Tenemos que guardar los cambios en sesión.
					if (session.getAttribute("listaInvLib") != null)
					{
						listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
						listaInvLib.set(posicion-1, pdaDatosInventarioLibreActual);
						session.setAttribute("listaInvLib", listaInvLib);
					}
				}
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return pdaDatosInventarioLibreActual;
	}

	@RequestMapping(value = "/pdaP42InventarioLibre", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid final PdaDatosInventarioLibre pdaDatosInventarioLibre,
			HttpServletRequest request,
			HttpServletResponse response) {
		

		PdaDatosInventarioLibre pdaDatosInventarioLibreResultado = new PdaDatosInventarioLibre();
		PdaDatosInventarioLibre pdaDatosInventarioLibreActual = new PdaDatosInventarioLibre();
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		int posicion = 0;
		String botPag = "";
		
		try 
		{
			if (request.getParameter("posicion") != null)
			{
				posicion = Integer.parseInt(request.getParameter("posicion"));
			}
			
			if (request.getParameter("actionLogin") != null || request.getParameter("actionVolver") != null ||
				request.getParameter("actionSeccion") != null || request.getParameter("actionNuevo") != null ||
				request.getParameter("actionAnt") != null || request.getParameter("actionSig") != null || 
				request.getParameter("actionGrabarTodo") != null || request.getParameter("actionGrabar") != null || 
				request.getParameter("actionLimpiarTodo") != null || request.getParameter("actionLimpiar") != null)
			{
				//Se ha pulsado algún botón tendremos que realizar las validaciones.
				pdaDatosInventarioLibreActual = this.obtenerReferenciaPorPosicion(session,posicion,request);
				//Se actualiza el campo de no guardar
				pdaDatosInventarioLibreActual.setFlgNoGuardar(pdaDatosInventarioLibre.getFlgNoGuardar());
				//Se actualiza el campo de si viene de inventario programado
				pdaDatosInventarioLibreActual.setOrigenGISAE(pdaDatosInventarioLibre.getOrigenGISAE());
				
				//Se realizan las validaciones si es modificable en esta pantalla
				if (Constantes.INVENTARIO_LIBRE_UNICA_SI.equals(pdaDatosInventarioLibreActual.getFlgUnica())){
					pdaDatosInventarioLibreActual = this.validaciones(posicion, pdaDatosInventarioLibre, pdaDatosInventarioLibreActual, session);

					//Si se ha producido un error con la validación lo mostramos.
					if (pdaDatosInventarioLibreActual.getError() != null && !pdaDatosInventarioLibreActual.getError().equals(""))
					{
						pdaError.setDescError(pdaDatosInventarioLibreActual.getError());
						model.addAttribute("pdaError", pdaError);
						model.addAttribute("pdaDatosInvLib", pdaDatosInventarioLibreActual);
						model.addAttribute("pdaDatosCab", pdaDatosCab);
						
						logger.info("PDA Error Validacion Inventario Libre");
						
						return "pda_p42_inventarioLibre";
					}else{
						//Se guardan los datos en la tabla temporal
						pdaDatosInventarioLibreActual = this.guardarRegistroTemporal(pdaDatosInventarioLibreActual, session, request);
						BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getSalaStock()).setScale(2, BigDecimal.ROUND_HALF_UP);
						BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getCamaraStock()).setScale(2, BigDecimal.ROUND_HALF_UP);
						pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(bdSalaStock.doubleValue(),"###0.00"));
						pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(bdCamaraStock.doubleValue(),"###0.00"));
					}
					this.refrescarSecciones(model, pdaDatosInventarioLibreActual, session);
				}else{
					if (null != pdaDatosInventarioLibreActual.getCodArticulo()){
						InventarioRotativo inventarioRotativoModif = new InventarioRotativo();
				    	inventarioRotativoModif.setCodArticulo(pdaDatosInventarioLibreActual.getCodArticulo());
				    	inventarioRotativoModif.setCodArticuloRela(pdaDatosInventarioLibreActual.getCodArticuloRela());
				    	inventarioRotativoModif.setCodCentro(pdaDatosInventarioLibreActual.getCodCentro());
				    	inventarioRotativoModif.setCodMac(pdaDatosInventarioLibreActual.getCodMac());
	
				    	inventarioRotativoModif = this.inventarioRotativoService.findOne(inventarioRotativoModif);
				    	if (inventarioRotativoModif != null){
					    	inventarioRotativoModif.setFlgNoGuardar(pdaDatosInventarioLibreActual.getFlgNoGuardar());
				        	this.inventarioRotativoService.insertUpdate(inventarioRotativoModif);		
				    	}
					}
				}
			}
			
			if (request.getParameter("actionVolver") != null && "S".equals(request.getParameter("actionVolver")))
			{
				return "pda_p40_selFiabilidad";
			}
			
			if (request.getParameter("actionSeccion") != null && "S".equals(request.getParameter("actionSeccion")))
			{
				if (request.getParameter("pda_p42_fld_seccion") != null && !request.getParameter("pda_p42_fld_seccion").equals("")){
					
					boolean isGISAE = false;
					Long codSeccion = null;
					if (null != pdaDatosInventarioLibre.getOrigenGISAE() && pdaDatosInventarioLibre.getOrigenGISAE().equals("SI")){
						isGISAE = true;	
					}
					
					if (request.getParameter("pda_p42_fld_seccion") != null) {
						codSeccion = new Long(request.getParameter("pda_p42_fld_seccion"));
					}
					
					this.obtenerReferenciasSesion(session,codSeccion ,isGISAE);
					
					List<PdaDatosInventarioLibre> listaInvLib = new ArrayList<PdaDatosInventarioLibre>();
					if (null !=	session.getAttribute("listaInvLib")){
						listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
					}
					
					if (!listaInvLib.isEmpty()) {

						HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
						Integer posicionMap = 1;
						for (PdaDatosInventarioLibre invLib : listaInvLib){
							if (null != pdaDatosInventarioLibre.getOrigenGISAE() && pdaDatosInventarioLibre.getOrigenGISAE().equals("SI")){
								//this.guardarRegistroTemporal(invLib, session, request);
							}
							mapClavesInvLib.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
							invLib.setPosicion(posicionMap);
							posicionMap++;
						}
						session.setAttribute("hashMapClavesInvLib", mapClavesInvLib);
						PdaDatosInventarioLibre pdaDatosInvLibResultado = this.obtenerReferenciaPorPosicion(session, 1, request);
						PdaDatosCabecera pdaDatosCabResultado = new PdaDatosCabecera();
						pdaDatosCabResultado.setCodArtCab(String.valueOf(pdaDatosInvLibResultado.getCodArticulo()));
						pdaDatosCabResultado.setSeccion(request.getParameter("pda_p42_fld_seccion"));
						model.addAttribute("pdaDatosCab", pdaDatosCabResultado);
						model.addAttribute("pdaDatosInvLib", pdaDatosInvLibResultado);
						
						this.refrescarSecciones(model, pdaDatosInvLibResultado, session);
						
					} else {
						session.removeAttribute("hashMapClavesInvLib");
						session.removeAttribute("listaInvLib");
						PdaDatosCabecera pdaDatosCabResultado = new PdaDatosCabecera();
						pdaDatosCabResultado.setSeccion(request.getParameter("pda_p42_fld_seccion"));
						if (null != pdaDatosInventarioLibre.getOrigenGISAE() && pdaDatosInventarioLibre.getOrigenGISAE().equals("SI")){
							model.addAttribute("pdaDatosInvLib", pdaDatosInventarioLibre);
						}
						
						model.addAttribute("pdaDatosCab", pdaDatosCabResultado);
					}
					this.refrescarSecciones(model, pdaDatosInventarioLibreActual, session);
				}
				return "pda_p42_inventarioLibre";
			}

			//Controlamos si se ha pusado algún botón de paginación.
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
				
				pdaDatosInventarioLibreResultado = this.pdaP42Paginar(posicion, botPag, request, session);
				if(null != pdaDatosInventarioLibreActual.getOrigenGISAE() && pdaDatosInventarioLibreActual.getOrigenGISAE().equals("SI")){
					pdaDatosCab.setSeccion(pdaDatosInventarioLibreResultado.getCodSeccion().toString());
				}
				else{
					if(null != request.getParameter("pda_p42_fld_seccion") && !request.getParameter("pda_p42_fld_seccion").equals("")){
						pdaDatosCab.setSeccion(request.getParameter("pda_p42_fld_seccion"));
					}
				}
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaDatosInvLib", pdaDatosInventarioLibreResultado);
				
				this.refrescarSecciones(model, pdaDatosInventarioLibreActual, session);
				
				return "pda_p42_inventarioLibre";
			}
			
			if (request.getParameter("actionLimpiarTodo") != null){
				//Si hay una sección seleccionada sólo hay que borrar las referencias de esa sección
				//si no, se borran todas las referencias
				Long codSeccion = null;
				if (request.getParameter("pda_p42_fld_seccion") != null && !request.getParameter("pda_p42_fld_seccion").equals("")){
					codSeccion = new Long(request.getParameter("pda_p42_fld_seccion"));
				}
				this.borrarReferenciasSesion(session, codSeccion);
				
				//Si hay una sección seleccionada y hemos borrado las referencias de esa sección
				//puede que haya referencias de otras secciones que habrá que mostrar en pantalla
				PdaDatosCabecera pdaDatosCabModif = new PdaDatosCabecera();
				PdaDatosInventarioLibre pdaDatosInvLibResultado = new PdaDatosInventarioLibre();
				if (codSeccion != null){
					boolean isGISAE = false;
					if (null != pdaDatosInventarioLibreResultado.getOrigenGISAE() && pdaDatosInventarioLibreResultado.getOrigenGISAE().equals("SI")){
						isGISAE = true;	
					}
					//Obtenemos todas las referencias de session
					codSeccion = null;
					this.obtenerReferenciasSesion(session,codSeccion ,isGISAE);
					List<PdaDatosInventarioLibre> listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
					
					//Obtenemos las claves [codArticulo, posicion]
					HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
					Integer posicionMap = 1;
					for (PdaDatosInventarioLibre invLib : listaInvLib){
						mapClavesInvLib.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
						invLib.setPosicion(posicionMap);
						posicionMap++;
					}
					Integer posicionIni = posicion;
					if (posicionIni > listaInvLib.size()){
						posicionIni = listaInvLib.size();
					}
					session.setAttribute("hashMapClavesInvLib", mapClavesInvLib);
					
					//Obtenemos la primera referencia de la lista
					pdaDatosInvLibResultado = this.obtenerReferenciaPorPosicion(session, posicionIni, request);
					
					//Establecemos la cabecera
					//El código de sección de la cabecera lo ponemos a null
					//por que ya no hay referencias de esa sección y mostramos
					//todas las demás referencias
					pdaDatosCabModif.setCodArtCab(String.valueOf(pdaDatosInvLibResultado.getCodArticulo()));
				}
				else{
					session.removeAttribute("hashMapClavesInvLib");
					session.removeAttribute("listaInvLib");
				}
				model.addAttribute("pdaDatosCab", pdaDatosCabModif);
				model.addAttribute("pdaDatosInvLib", pdaDatosInvLibResultado);
				
				this.refrescarSecciones(model, pdaDatosInventarioLibreActual, session);
				
				return "pda_p42_inventarioLibre";
			}
			
			if (request.getParameter("actionLimpiar") != null){
				pdaDatosInventarioLibreResultado = this.obtenerReferenciaPorPosicion(session,posicion, request);
				boolean isGISAE = false;
				if (null != pdaDatosInventarioLibreResultado.getOrigenGISAE() && pdaDatosInventarioLibreResultado.getOrigenGISAE().equals("SI")){
					isGISAE = true;	
				}
				if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibreResultado.getFlgVariasUnitarias())){
					this.borrarReferenciaUnitariaSesion(session, pdaDatosInventarioLibreResultado.getCodArticulo());
				}
				else{
					this.borrarReferenciaSesion(session, pdaDatosInventarioLibreResultado.getCodArticulo());
				}
				//Comento estas dos líneas por la peticion 51923, 
				//error en pistola cuando queda una referencia en inventario libre
				//y se pulsa sobre borrar.
				//model.addAttribute("pdaDatosCab", pdaDatosCab);
				//model.addAttribute("pdaDatosInvLib", pdaDatosInventarioLibreResultado);
				
				List<PdaDatosInventarioLibre> listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
				if (null != listaInvLib){
					listaInvLib.remove(posicion - 1);
					
					//Ahora vamos a calcular los atributos "pdaDatosCab" y "pdaDatosInvLib"
					//que vamos a pasar al model
					PdaDatosCabecera pdaDatosCabResultado = new PdaDatosCabecera();
					PdaDatosInventarioLibre pdaDatosInvLibResultado = new PdaDatosInventarioLibre();
					if (!listaInvLib.isEmpty()) {
						HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
						Integer posicionMap = 1;
						for (PdaDatosInventarioLibre invLib : listaInvLib){
							mapClavesInvLib.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
							invLib.setPosicion(posicionMap);
							posicionMap++;
						}
						Integer posicionIni = posicion;
						if (posicionIni > listaInvLib.size()){
							posicionIni = listaInvLib.size();
						}
						session.setAttribute("hashMapClavesInvLib", mapClavesInvLib);
						pdaDatosInvLibResultado = this.obtenerReferenciaPorPosicion(session, posicionIni, request);
						pdaDatosCabResultado.setCodArtCab(String.valueOf(pdaDatosInvLibResultado.getCodArticulo()));
						if(null != request.getParameter("pda_p42_fld_seccion") && !request.getParameter("pda_p42_fld_seccion").equals("")){
							pdaDatosCabResultado.setSeccion(request.getParameter("pda_p42_fld_seccion"));
						}
					} else {
						Long codSeccion = null;
						if (request.getParameter("pda_p42_fld_seccion") != null && !request.getParameter("pda_p42_fld_seccion").equals("")){
							codSeccion = new Long(request.getParameter("pda_p42_fld_seccion"));
						}
						if (codSeccion != null){
							//Obtenemos todas las referencias de session
							codSeccion = null;
							this.obtenerReferenciasSesion(session,codSeccion ,isGISAE);
							listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
							
							//Obtenemos las claves [codArticulo, posicion]
							HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
							Integer posicionMap = 1;
							for (PdaDatosInventarioLibre invLib : listaInvLib){
								mapClavesInvLib.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
								invLib.setPosicion(posicionMap);
								posicionMap++;
							}
							Integer posicionIni = posicion;
							if (posicionIni > listaInvLib.size()){
								posicionIni = listaInvLib.size();
							}
							session.setAttribute("hashMapClavesInvLib", mapClavesInvLib);
							
							//Obtenemos la primera referencia de la lista
							pdaDatosInvLibResultado = this.obtenerReferenciaPorPosicion(session, posicionIni, request);
							
							//Establecemos la cabecera
							//El código de sección de la cabecera lo ponemos a null
							//por que ya no hay referencias de esa sección y mostramos
							//todas las demás referencias
							pdaDatosCabResultado.setCodArtCab(String.valueOf(pdaDatosInvLibResultado.getCodArticulo()));
						}
						else{
							session.removeAttribute("hashMapClavesInvLib");
							session.removeAttribute("listaInvLib");
						}
					}
	
					this.refrescarSecciones(model, pdaDatosInventarioLibreActual, session);
					
					model.addAttribute("pdaDatosCab", pdaDatosCabResultado);
					model.addAttribute("pdaDatosInvLib", pdaDatosInvLibResultado);
					
					return "pda_p42_inventarioLibre";
				} else {
					return "pda_p03_sesionCaducada";
				}
			}

			if (request.getParameter("actionGrabarTodo") != null){
				Long codSeccion = null;
				if (request.getParameter("pda_p42_fld_seccion") != null && !request.getParameter("pda_p42_fld_seccion").equals("")){
					codSeccion = new Long(request.getParameter("pda_p42_fld_seccion"));
					pdaDatosCab.setSeccion(codSeccion.toString());
				}
				PdaDatosGuardadosInvLib datosGuardados = this.guardarRegistrosRelacionados(pdaDatosInventarioLibreActual, session, codSeccion);
				pdaDatosCab.setOrigenGISAE(pdaDatosInventarioLibreActual.getOrigenGISAE());
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaDatosSave", datosGuardados);
				return "pda_p46_guardarInvLib";
			}
			
			if (request.getParameter("actionGrabar") != null){
				PdaDatosGuardadosInvLib datosGuardados = this.guardarRegistroUnico(pdaDatosInventarioLibreActual, session);
				pdaDatosCab.setOrigenGISAE(pdaDatosInventarioLibreActual.getOrigenGISAE());
				if(null != request.getParameter("pda_p42_fld_seccion") && !request.getParameter("pda_p42_fld_seccion").equals("")){
					pdaDatosCab.setSeccion(request.getParameter("pda_p42_fld_seccion"));
				}
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaDatosSave", datosGuardados);
				//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
				session.setAttribute("ultimaRefInvLib", String.valueOf(pdaDatosInventarioLibreActual.getCodArticulo()));
				return "pda_p46_guardarInvLib";
			}

			if (request.getParameter("actionNuevo") != null && "S".equals(request.getParameter("actionNuevo")))
			{
				//Controlamos que me llega la referencia
				if (request.getParameter("pda_p42_fld_codArtCab") != null && !request.getParameter("pda_p42_fld_codArtCab").equals(""))
				{
					//Si existe sección, entonces buscamos todas las referencias, absolutamente todas
					Long codSeccion = null;
					if (request.getParameter("pda_p42_fld_seccion") != null && !request.getParameter("pda_p42_fld_seccion").equals("")){
						codSeccion = new Long(request.getParameter("pda_p42_fld_seccion"));
					}
					if (codSeccion != null){
						boolean isGISAE = false;
						if (null != pdaDatosInventarioLibreResultado.getOrigenGISAE() && pdaDatosInventarioLibreResultado.getOrigenGISAE().equals("SI")){
							isGISAE = true;	
						}
						List<PdaDatosInventarioLibre> listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
						//Obtenemos todas las referencias de session
						codSeccion = null;
						this.obtenerReferenciasSesion(session,codSeccion ,isGISAE);
						listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
						
						//Obtenemos las claves [codArticulo, posicion]
						HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
						Integer posicionMap = 1;
						for (PdaDatosInventarioLibre invLib : listaInvLib){
							mapClavesInvLib.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
							invLib.setPosicion(posicionMap);
							posicionMap++;
						}
						Integer posicionIni = posicion;
						if (posicionIni > listaInvLib.size()){
							posicionIni = listaInvLib.size();
						}
						session.setAttribute("hashMapClavesInvLib", mapClavesInvLib);
					}
					
					pdaDatosCab.setCodArtCab(request.getParameter("pda_p42_fld_codArtCab"));
					
					//Llamamos al método que nos devuelve la referencia, con los controles, 
					//por si se trata de balanza, etiqueta propia de Eroski, de la tabla EANS o código de referencia normal.
					PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
					String codigoError = pdaArticulo.getCodigoError();
					
					if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN))
					{
						pdaError.setDescError(this.messageSource.getMessage(
								"pda_p12_datosReferencia.noExisteReferencia", null, locale));
						model.addAttribute("pdaError", pdaError);
						model.addAttribute("pdaDatosInvLib", pdaDatosInventarioLibre);
						model.addAttribute("pdaDatosCab",  new PdaDatosCabecera());
						logger.info("PDA Referencia erronea");
						this.refrescarSecciones(model, pdaDatosInventarioLibreActual, session);
						return "pda_p03_invLib_showMessage";
					}
					
					pdaDatosCab.setCodArtCab(pdaArticulo.getCodArt().toString());
					pdaDatosInventarioLibreResultado = obtenerResultado(new Long(pdaDatosCab.getCodArtCab()),session, request);
					
					if (pdaDatosInventarioLibreResultado.getError() != null && !pdaDatosInventarioLibreResultado.getError().equals(""))
					{
						pdaError.setDescError(pdaDatosInventarioLibreResultado.getError());
						model.addAttribute("pdaError", pdaError);
						model.addAttribute("pdaDatosInvLib", pdaDatosInventarioLibreResultado);
						model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
						logger.info("PDA Referencia erronea");
						this.refrescarSecciones(model, pdaDatosInventarioLibreActual, session);
						return "pda_p03_invLib_showMessage";
					}
					
					if (null == pdaDatosInventarioLibreResultado.getOrigenGISAE() || !"SI".equals(pdaDatosInventarioLibreResultado.getOrigenGISAE())){
						//Obtener el tipo de rotacion
						pdaDatosInventarioLibreResultado.setTipoRotacion(this.obtenerTipoRotacion(pdaDatosInventarioLibreResultado));
					}
					//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
					session.setAttribute("ultimaRefInvLib", String.valueOf(pdaDatosInventarioLibreResultado.getCodArticulo()));
				}
				else
				{
					//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
					pdaError.setDescError(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.referenciaVacia", null, locale));
					model.addAttribute("pdaDatosInvLib", pdaDatosInventarioLibre);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					model.addAttribute("pdaError", pdaError);
					logger.info("PDA Referencia no introducida");
					this.refrescarSecciones(model, pdaDatosInventarioLibreActual, session);
					return "pda_p03_invLib_showMessage";
				}
			}
			
			this.refrescarSecciones(model, pdaDatosInventarioLibreActual, session);
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		pdaDatosInventarioLibreResultado.setOrigenGISAE(pdaDatosInventarioLibre.getOrigenGISAE());
		model.addAttribute("pdaDatosInvLib", pdaDatosInventarioLibreResultado);
		pdaDatosCab.setDescArtCab(pdaDatosInventarioLibreResultado.getDescArt());
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
		
		return "pda_p42_inventarioLibre";
	}
	
	private PdaDatosInventarioLibre obtenerUltimaReferencia(HttpSession session, HttpServletRequest request) throws Exception{
		
		Locale locale = LocaleContextHolder.getLocale();
		
		PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();
		List<PdaDatosInventarioLibre> listaInvLib =new ArrayList<PdaDatosInventarioLibre>();
		HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
		int indiceRegistroGuardado = 0;
		String codArt = "";

		if (session.getAttribute("hashMapClavesInvLib") != null && session.getAttribute("hashMapClavesInvLib") != null)
		{
			mapClavesInvLib = (HashMap<String, Integer>) session.getAttribute("hashMapClavesInvLib");
			
			listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
			
			//Obtenemos la referencia a buscar.
			if (session.getAttribute("ultimaRefInvLib") != null)
			{
				codArt = (String) session.getAttribute("ultimaRefInvLib");
				
				if (mapClavesInvLib.get(codArt) != null){ //Si la referencia se ha eliminado se mostrará la primera referencia
					indiceRegistroGuardado = ((Integer)mapClavesInvLib.get(codArt)).intValue()-1;
				}
				pdaDatosInventarioLibre = (PdaDatosInventarioLibre) listaInvLib.get(indiceRegistroGuardado);
				
				//Si no es referencia única y no tiene varias unitarias hay que refrescar los datos de pantalla con la suma de cantidades
				if (!Constantes.INVENTARIO_LIBRE_UNICA_SI.equals(pdaDatosInventarioLibre.getFlgUnica()) && 
						!Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
					this.cargarDatosSumaTablaTemporal(pdaDatosInventarioLibre);
				}
				
				//Obtenemos el stock actual.
				ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
				stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(pdaDatosInventarioLibre.getCodCentro()));
				stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
				List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
				listaReferencias.add(BigInteger.valueOf(pdaDatosInventarioLibre.getCodArticulo()));
				stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
				boolean errorConsultaStock = false;
				try{
					ConsultarStockResponseType stockTiendaResponse = null;
					if (stockTiendaRequest.equals(request.getAttribute("stockTiendaRequestCopia"))){
						stockTiendaResponse = (ConsultarStockResponseType) request.getAttribute("stockTiendaResponseCopia");
					}
					else{
						
						User user = (User) session.getAttribute("user");
						ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
						paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
						ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
						
						if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
							logger.error("###########################################################################################################");
							logger.error("####################### CONTROLADOR: pdaP42InventarioLibreController (obtenerUltimaReferencia  1)	 ########");
							logger.error("###########################################################################################################");
						}
						
						stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
						request.setAttribute("stockTiendaRequestCopia", stockTiendaRequest);
						request.setAttribute("stockTiendaResponseCopia", stockTiendaResponse);
					}
					if (null == stockTiendaResponse || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
						pdaDatosInventarioLibre.setStockActual(new Double(0));
						errorConsultaStock = true;
					}else{
						if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
							stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
							ConsultarStockResponseType stockTiendaResponseBasica = null;
							
							User user = (User) session.getAttribute("user");
							ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
							paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
							ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
							
							if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
								logger.error("###########################################################################################################");
								logger.error("####################### CONTROLADOR: pdaP42InventarioLibreController (obtenerUltimaReferencia  2)	 ########");
								logger.error("###########################################################################################################");
							}
							
							stockTiendaResponseBasica = this.correccionStockService.consultaStock(stockTiendaRequest, session);
							if (null == stockTiendaResponseBasica || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponseBasica.getCodigoRespuesta())){
								pdaDatosInventarioLibre.setStockActual(new Double(0));
								errorConsultaStock = true;
							}else{
								if (stockTiendaResponseBasica.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
									pdaDatosInventarioLibre.setStockActual(stockTiendaResponseBasica.getListaReferencias()[0].getBandejas().doubleValue());
								} else {
									pdaDatosInventarioLibre.setStockActual(stockTiendaResponseBasica.getListaReferencias()[0].getStock().doubleValue());
								}
							}
						}
						else{
							if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								pdaDatosInventarioLibre.setStockActual(stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue());
							} else {
								pdaDatosInventarioLibre.setStockActual(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue());
							}
						}
					}				
				}catch (Exception e) {
					errorConsultaStock = true;
					pdaDatosInventarioLibre.setStockActual(new Double(0));
				}	

				//Redondeo a 2 decimales del stock
				//Peticion 55001. Corrección errores del LOG.
				BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getSalaStock());
			    BigDecimal roundedSalaStock = bdSalaStock.setScale(2, BigDecimal.ROUND_HALF_UP);

				//Redondeo a 2 decimales del stock
				//Peticion 55001. Corrección errores del LOG.
				BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getCamaraStock());
				BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);

				BigDecimal roundedTotalStock = roundedCamaraStock.add(roundedSalaStock);
				pdaDatosInventarioLibre.setTotalStock(Utilidades.convertirDoubleAString(roundedTotalStock.doubleValue(),"###0.00").replace(',', '.'));

				if (Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS.equals(pdaDatosInventarioLibre.getFlgStockPrincipal())){
					Long lCamaraBandeja = new Long(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");
					Long lSalaBandeja = new Long(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
					
					//Cálculo de campos calculados
					Long totalBandejas = lCamaraBandeja + lSalaBandeja; 
					pdaDatosInventarioLibre.setTotalBandeja(totalBandejas.toString());
					Double diferencia = totalBandejas - pdaDatosInventarioLibre.getStockActual();
					if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
						//Si tiene referencias unitarias hay que mostrar diferencia 0
						diferencia = new Double(0);
					}
					pdaDatosInventarioLibre.setDiferencia(diferencia);
				}else{
					Double diferencia = roundedTotalStock.doubleValue() - pdaDatosInventarioLibre.getStockActual();
					if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
						//Si tiene referencias unitarias hay que mostrar diferencia 0
						diferencia = new Double(0);
					}
					pdaDatosInventarioLibre.setDiferencia(diferencia);
				}
				
				//Formateo de cantidades de bandejas
				pdaDatosInventarioLibre.setSalaBandeja(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
				pdaDatosInventarioLibre.setCamaraBandeja(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");

				//Formateo de cantidades de stock
				pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(roundedSalaStock.doubleValue(),"###0.00").replace(',', '.'));
				pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(roundedCamaraStock.doubleValue(),"###0.00").replace(',', '.'));

				//Carga de descripciones de stock actual y diferencia
				if (errorConsultaStock){
					pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.stockActualError", null, locale));
					pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.diferenciaError", null, locale));
				}else{
					pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.stockActual", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getStockActual(),"###0.00").replace('.', ',')}, locale));
					if (pdaDatosInventarioLibre.getDiferencia() > 0) {
						pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.diferencia", new Object[]{"+" + Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
				
					} else {
						pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
								"pda_p42_inventarioLibre.diferencia", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
					
					}
				}
				
				//Hay que cargar los avisos por si acaso ha habido modificaciones y se han borrado de base de datos
	    		InventarioRotativo inventarioRotativo = new InventarioRotativo();
	    		inventarioRotativo.setCodCentro(pdaDatosInventarioLibre.getCodCentro());
	    		inventarioRotativo.setCodArticulo(pdaDatosInventarioLibre.getCodArticulo());
	    		inventarioRotativo.setCodArticuloRela(pdaDatosInventarioLibre.getCodArticuloRela());
	    		inventarioRotativo.setCodMac(pdaDatosInventarioLibre.getCodMac());
	    		InventarioRotativo existeInvRot = this.inventarioRotativoService.findOne(inventarioRotativo);
	    		if (existeInvRot != null){
		    		pdaDatosInventarioLibre.setAviso(existeInvRot.getAviso());
	    		}
	    		
	    		if (!"SI".equals(pdaDatosInventarioLibre.getOrigenGISAE())){
					//Obtener el tipo de rotacion
					pdaDatosInventarioLibre.setTipoRotacion(this.obtenerTipoRotacion(pdaDatosInventarioLibre));
				}	    		
			}
		}
		
		return pdaDatosInventarioLibre;
	}
	
	private PdaDatosInventarioLibre obtenerReferenciaPorPosicion(HttpSession session, int posicion, HttpServletRequest request) throws Exception{
		
		Locale locale = LocaleContextHolder.getLocale();
		
		PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();
		List<PdaDatosInventarioLibre> listaInvLib =new ArrayList<PdaDatosInventarioLibre>();
		HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();

		if (session.getAttribute("hashMapClavesInvLib") != null && session.getAttribute("listaInvLib") != null)
		{
			mapClavesInvLib = (HashMap<String, Integer>) session.getAttribute("hashMapClavesInvLib");
			
			listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");

			if (posicion > listaInvLib.size()){
				posicion = listaInvLib.size();
			} else if (posicion < 1){
				posicion = 1;
			}
			
			pdaDatosInventarioLibre = (PdaDatosInventarioLibre) listaInvLib.get(posicion-1);
			
			//Si no es referencia única y no tiene varias unitarias hay que refrescar los datos de pantalla con la suma de cantidades
			if (!Constantes.INVENTARIO_LIBRE_UNICA_SI.equals(pdaDatosInventarioLibre.getFlgUnica()) && 
					!Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
				this.cargarDatosSumaTablaTemporal(pdaDatosInventarioLibre);
			}
			
			//Obtenemos el stock actual.
			ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
			stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(pdaDatosInventarioLibre.getCodCentro()));
			stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
			List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
			listaReferencias.add(BigInteger.valueOf(pdaDatosInventarioLibre.getCodArticulo()));
			stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
			boolean errorConsultaStock = false;
			try{
				ConsultarStockResponseType stockTiendaResponse = null;
				if (stockTiendaRequest.equals(request.getAttribute("stockTiendaRequestCopia"))){
					stockTiendaResponse = (ConsultarStockResponseType) request.getAttribute("stockTiendaResponseCopia");
				}
				else{
					
					User user = (User) session.getAttribute("user");
					ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
					paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
					ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
					
					if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
						logger.error("###########################################################################################################");
						logger.error("####################### CONTROLADOR: pdaP42InventarioLibreController (obtenerReferenciaPorPosicion 1)	 ########");
						logger.error("###########################################################################################################");
					}
					
					stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
					request.setAttribute("stockTiendaRequestCopia", stockTiendaRequest);
					request.setAttribute("stockTiendaResponseCopia", stockTiendaResponse);
				}
				if (null == stockTiendaResponse || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
					pdaDatosInventarioLibre.setStockActual(new Double(0));
					errorConsultaStock = true;
				}else{
					if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
						stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
						ConsultarStockResponseType stockTiendaResponseBasica = null;
						
						User user = (User) session.getAttribute("user");
						ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
						paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
						ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
						
						if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
							logger.error("###########################################################################################################");
							logger.error("####################### CONTROLADOR: pdaP42InventarioLibreController (obtenerReferenciaPorPosicion 2)	 ####");
							logger.error("###########################################################################################################");
						}
						
						stockTiendaResponseBasica = this.correccionStockService.consultaStock(stockTiendaRequest, session);
						if (null == stockTiendaResponseBasica || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponseBasica.getCodigoRespuesta())){
							pdaDatosInventarioLibre.setStockActual(new Double(0));
							errorConsultaStock = true;
						}else{
							if (stockTiendaResponseBasica.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								pdaDatosInventarioLibre.setStockActual(stockTiendaResponseBasica.getListaReferencias()[0].getBandejas().doubleValue());
							} else {
								pdaDatosInventarioLibre.setStockActual(stockTiendaResponseBasica.getListaReferencias()[0].getStock().doubleValue());
							}
						}
					}
					else{
						if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
							ReferenciaType referencia = stockTiendaResponse.getListaReferencias()[0];
							pdaDatosInventarioLibre.setStockActual(referencia.getBandejas().doubleValue());
							pdaDatosInventarioLibre.setKgs(Utilidades.convertirDoubleAString((referencia.getStock() != null ? referencia.getStock().floatValue() : 0)/((referencia.getBandejas()!=null && referencia.getBandejas().floatValue() != 0)?referencia.getBandejas().floatValue():1),"###0.000"));
						} else {
							pdaDatosInventarioLibre.setStockActual(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue());
						}
					}
				}		
				
				if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
					pdaDatosInventarioLibre.setFlgVariasUnitarias(Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI);
				}else{
					pdaDatosInventarioLibre.setFlgVariasUnitarias(Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_NO);
				}
			}catch (Exception e) {
				errorConsultaStock = true;
				pdaDatosInventarioLibre.setStockActual(new Double(0));
			}	

			//Redondeo a 2 decimales del stock
			//Peticion 55001. Corrección errores del LOG.
			BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getSalaStock());
		    BigDecimal roundedSalaStock = bdSalaStock.setScale(2, BigDecimal.ROUND_HALF_UP);

			//Redondeo a 2 decimales del stock
			//Peticion 55001. Corrección errores del LOG.
			BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getCamaraStock());
			BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);

			BigDecimal roundedTotalStock = roundedCamaraStock.add(roundedSalaStock);
			pdaDatosInventarioLibre.setTotalStock(Utilidades.convertirDoubleAString(roundedTotalStock.doubleValue(),"###0.00").replace(',', '.'));

			if (Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS.equals(pdaDatosInventarioLibre.getFlgStockPrincipal())){
				Long lCamaraBandeja = new Long(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");
				Long lSalaBandeja = new Long(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
				
				//Cálculo de campos calculados
				Long totalBandejas = lCamaraBandeja + lSalaBandeja; 
				pdaDatosInventarioLibre.setTotalBandeja(totalBandejas.toString());
				Double diferencia = totalBandejas - pdaDatosInventarioLibre.getStockActual();
				if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
					//Si tiene referencias unitarias hay que mostrar diferencia 0
					diferencia = new Double(0);
				}
				pdaDatosInventarioLibre.setDiferencia(diferencia);
			}else{
				Double diferencia = roundedTotalStock.doubleValue() - pdaDatosInventarioLibre.getStockActual();
				if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
					//Si tiene referencias unitarias hay que mostrar diferencia 0
					diferencia = new Double(0);
				}
				pdaDatosInventarioLibre.setDiferencia(diferencia);
			}
			
			//Formateo de cantidades de bandejas
			pdaDatosInventarioLibre.setSalaBandeja(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
			pdaDatosInventarioLibre.setCamaraBandeja(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");

			//Formateo de cantidades de stock
			pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(roundedSalaStock.doubleValue(),"###0.00").replace(",", "."));
			pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(roundedCamaraStock.doubleValue(),"###0.00").replace(",", "."));

			//Carga de descripciones de stock actual y diferencia
			if (errorConsultaStock){
				pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
						"pda_p42_inventarioLibre.stockActualError", null, locale));
				pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
						"pda_p42_inventarioLibre.diferenciaError", null, locale));
			}else{
				pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
						"pda_p42_inventarioLibre.stockActual", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getStockActual(),"###0.00").replace('.', ',')}, locale));
				if (pdaDatosInventarioLibre.getDiferencia() > 0) {
					pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
						"pda_p42_inventarioLibre.diferencia", new Object[]{"+" + Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
			
				} else {
					pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.diferencia", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
				
				}
			}

			//Refrescamos el total del objeto.
			pdaDatosInventarioLibre.setTotal(mapClavesInvLib.size());
			
			if (null == pdaDatosInventarioLibre.getOrigenGISAE() || !"SI".equals(pdaDatosInventarioLibre.getOrigenGISAE())){
				//Obtener el tipo de rotacion
				pdaDatosInventarioLibre.setTipoRotacion(this.obtenerTipoRotacion(pdaDatosInventarioLibre));
			}

			//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
			session.setAttribute("ultimaRefInvLib", String.valueOf(pdaDatosInventarioLibre.getCodArticulo()));
		}
		
		return pdaDatosInventarioLibre;
	}
	
	private PdaDatosInventarioLibre obtenerReferenciaActualConsulta(HttpSession session) throws Exception{
		
		PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();

		pdaDatosInventarioLibre = (PdaDatosInventarioLibre) session.getAttribute("invLibReferenciaConsulta");
		
		return pdaDatosInventarioLibre;
	}

	private PdaDatosInventarioLibre obtenerResultado(Long codArt, HttpSession session, HttpServletRequest request) throws Exception{
		
		
		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();
		pdaDatosInventarioLibre.setCodArticulo(codArt);
		boolean mostrarError = false;
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();
		
		List<PdaDatosInventarioLibre> listaInvLib =new ArrayList<PdaDatosInventarioLibre>();
		
		if (session.getAttribute("listaInvLib") != null)
		{
			listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
		}
		
		HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
		 
		if (session.getAttribute("hashMapClavesInvLib") != null)
		{
			mapClavesInvLib = (HashMap<String, Integer>) session.getAttribute("hashMapClavesInvLib");
		}
		
		VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);
		
		if (null != vDatosDiarioArtResul){
			//Obtener MMC
			ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			referenciasCentro.setCodArt(codArt);
			referenciasCentro.setCodCentro(codCentro);
			referenciasCentro.setDiarioArt(vDatosDiarioArtResul);
			VSurtidoTienda surtidoTienda = obtenerSurtidoTienda(referenciasCentro,false,false);
			if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro()!= null && !"".equals(surtidoTienda.getMarcaMaestroCentro()))
			{
				pdaDatosInventarioLibre.setMmc(surtidoTienda.getMarcaMaestroCentro());
			}else{
				pdaDatosInventarioLibre.setMmc("N");
			}
			
			//Obtenemos la descripción del artículo.
			if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null)
			{
				pdaDatosInventarioLibre.setDescArt(vDatosDiarioArtResul.getDescripArt());
			}
			pdaDatosInventarioLibre.setCodCentro(codCentro);
			pdaDatosInventarioLibre.setCodMac(mac);
			pdaDatosInventarioLibre.setCodArea(vDatosDiarioArtResul.getGrupo1());
			pdaDatosInventarioLibre.setCodSeccion(vDatosDiarioArtResul.getGrupo2());
			pdaDatosInventarioLibre.setCodArticulo(codArt);
			pdaDatosInventarioLibre.setCodArticuloRela(codArt);
			
			//Obtenemos la información a partir de la referencia.
			ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
			stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
			stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
			List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
			listaReferencias.add(BigInteger.valueOf(codArt));
			stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
			try{
				ConsultarStockResponseType stockTiendaResponse = null;
				if (stockTiendaRequest.equals(request.getAttribute("stockTiendaRequestCopia"))){
					stockTiendaResponse = (ConsultarStockResponseType) request.getAttribute("stockTiendaResponseCopia");
				}
				else{
					
					
					ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
					paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
					ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
					
					if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
						logger.error("###########################################################################################################");
						logger.error("####################### CONTROLADOR: pdaP42InventarioLibreController (obtenerResultado )	 ########");
						logger.error("###########################################################################################################");
					}
					
					stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
					request.setAttribute("stockTiendaRequestCopia", stockTiendaRequest);
					request.setAttribute("stockTiendaResponseCopia", stockTiendaResponse);
				}
				if (null != stockTiendaResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
					session.setAttribute("consultaStock", stockTiendaResponse);
				}else{
					session.removeAttribute("consultaStock");
					pdaDatosInventarioLibre.setError(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.errorCorreccionStockNoPermitida", null, locale));
					mostrarError = true;
					
				}
				if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
					pdaDatosInventarioLibre.setFlgVariasUnitarias(Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI);
				}else{
					pdaDatosInventarioLibre.setFlgVariasUnitarias(Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_NO);
				}
				if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
					ReferenciaType referencia = stockTiendaResponse.getListaReferencias()[0];
					pdaDatosInventarioLibre.setStockActual(referencia.getBandejas().doubleValue());
					pdaDatosInventarioLibre.setFlgStockPrincipal(Constantes.STOCK_PRINCIPAL_BANDEJAS);
					pdaDatosInventarioLibre.setKgs(Utilidades.convertirDoubleAString((referencia.getStock() != null ? referencia.getStock().floatValue() : 0)/((referencia.getBandejas()!=null && referencia.getBandejas().floatValue() != 0)?referencia.getBandejas().floatValue():1),"###0.000"));
				} else {
					pdaDatosInventarioLibre.setStockActual(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue());
					pdaDatosInventarioLibre.setFlgStockPrincipal(Constantes.STOCK_PRINCIPAL_STOCK);
				}
				
				if (stockTiendaResponse.getListaReferencias()!=null && stockTiendaResponse.getListaReferencias().length > 1){
					pdaDatosInventarioLibre.setFlgUnica(Constantes.INVENTARIO_LIBRE_UNICA_NO);
				}else{
					pdaDatosInventarioLibre.setFlgUnica(Constantes.INVENTARIO_LIBRE_UNICA_SI);
				}
			}catch (Exception e) {
				session.removeAttribute("consultaStock");
				pdaDatosInventarioLibre.setError(this.messageSource.getMessage(
						"pda_p42_inventarioLibre.errorCorreccionStockError", null, locale));
				mostrarError = true;
			}		

			if (!mostrarError){
				//Comprobamos si ya teníamos guardada la referencia en sesión
				if (mapClavesInvLib.get(String.valueOf(codArt)) != null )
				{
					//Obtener la posición que ocupa y mostrarlo en pantalla
					pdaDatosInventarioLibre = obtenerReferenciaPorPosicion(session, mapClavesInvLib.get(String.valueOf(codArt)), request);					
				}
				else
				{
					//Guardamos en sesión el objeto de la referencia introducida.
					listaInvLib.add(pdaDatosInventarioLibre);
					session.setAttribute("listaInvLib", listaInvLib);
					
					//Además debemos guardar en el hashmap la referencia con la posición.
					mapClavesInvLib.put(String.valueOf(codArt), listaInvLib.size());
					session.setAttribute("hashMapClavesInvLib", mapClavesInvLib);
					
					//Guardamos la posición y el total de elementos en el objeto.
					pdaDatosInventarioLibre.setPosicion(mapClavesInvLib.size());
					pdaDatosInventarioLibre.setTotal(mapClavesInvLib.size());
					
					//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
					session.setAttribute("ultimaRef", String.valueOf(pdaDatosInventarioLibre.getCodArticulo()));

					
					//Obtener los campos calculados y guardado en tabla temporal
					//Como es nuevo pasamos los valores a 0
					pdaDatosInventarioLibre.setCamaraBandeja("0");
					pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(new Double("0").doubleValue(),"###0.00").replace(',', '.'));
					pdaDatosInventarioLibre.setSalaBandeja("0");
					pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(new Double("0").doubleValue(),"###0.00").replace(',', '.'));
					
					//Actualizamos las cantidades de camara y sala por si acaso ya estaba referenciada por otro articulo
					pdaDatosInventarioLibre = this.inventarioRotativoService.actualizarCantidadesRef(pdaDatosInventarioLibre);
					
					pdaDatosInventarioLibre = this.guardarRegistroTemporal(pdaDatosInventarioLibre, session, request);
					
					//Actualizamos las cantidades de camara y sala por si acaso ya estaba referenciada por otro articulo
					pdaDatosInventarioLibre = this.inventarioRotativoService.actualizarCantidadesRefConRelaciones(pdaDatosInventarioLibre);
					
					//Comprobamos si hay algún dato en la tabla temporal, por ejemplo,
					//introducido como una referencia unitaria de una madre
//					PdaDatosInventarioLibre pdaDatosInventarioLibreSumaRefConRelaciones = this.inventarioRotativoService.findSumaCantidadesRefConRelaciones(pdaDatosInventarioLibre);
//					
//					if (pdaDatosInventarioLibreSumaRefConRelaciones != null && pdaDatosInventarioLibreSumaRefConRelaciones.getCamaraBandeja() != null){
//						pdaDatosInventarioLibre.setCamaraBandeja(pdaDatosInventarioLibreSumaRefConRelaciones.getCamaraBandeja());
//					}
//					else{
//						pdaDatosInventarioLibre.setCamaraBandeja("0");
//					}
//					if (pdaDatosInventarioLibreSumaRefConRelaciones != null && pdaDatosInventarioLibreSumaRefConRelaciones.getCamaraStock() != null){
//						pdaDatosInventarioLibre.setCamaraStock(pdaDatosInventarioLibreSumaRefConRelaciones.getCamaraStock().replace('.', ','));
//					}
//					else{
//						pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(new Double("0").doubleValue(),"###0.00").replace('.', ','));
//					}
//					if (pdaDatosInventarioLibreSumaRefConRelaciones != null && pdaDatosInventarioLibreSumaRefConRelaciones.getSalaBandeja() != null){
//						pdaDatosInventarioLibre.setSalaBandeja(pdaDatosInventarioLibreSumaRefConRelaciones.getSalaBandeja());
//					}
//					else{
//						pdaDatosInventarioLibre.setSalaBandeja("0");
//					}
//					if (pdaDatosInventarioLibreSumaRefConRelaciones != null && pdaDatosInventarioLibreSumaRefConRelaciones.getSalaStock() != null){
//						pdaDatosInventarioLibre.setSalaStock(pdaDatosInventarioLibreSumaRefConRelaciones.getSalaStock().replace('.', ','));
//						Double diferencia = new Double(pdaDatosInventarioLibre.getSalaStock()) - pdaDatosInventarioLibre.getStockActual();
//						pdaDatosInventarioLibre.setDiferencia(diferencia);
//
//					}
//					else{
//						pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(new Double("0").doubleValue(),"###0.00").replace('.', ','));
//					}
				}
			}
		}
		else
		{
			pdaDatosInventarioLibre.setError(this.messageSource.getMessage(
					"pda_p42_inventarioLibre.errorReferenciaNoValida", null, locale));
		}
		
		return pdaDatosInventarioLibre;
	}
	
	public String cargaInvLibCorreccionStock(ModelMap model, final Long codArt, final Long codCentro, 
			final String origenInventario, String origenGISAE, String noGuardar, PdaDatosCabecera pdaDatosCab, final String mmc, HttpSession session, HttpServletRequest request){
		
		String resultado = "";
		int indiceArticuloInicialPagina = 0;
		int indiceArticuloFinalPagina = 0;
		int totalArticulos = 0;

		try {
			
			//Tratamiento de entrada por link de cantidades
			ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
			stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
			stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
			List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
			listaReferencias.add(BigInteger.valueOf(codArt));
			stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
			ConsultarStockResponseType stockTiendaResponse = null;
			try{
				if (stockTiendaRequest.equals(request.getAttribute("stockTiendaRequestCopia"))){
					stockTiendaResponse = (ConsultarStockResponseType) request.getAttribute("stockTiendaResponseCopia");
				}
				else{
					
					User user = (User) session.getAttribute("user");
					ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
					paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
					ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
					
					if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
						logger.error("###########################################################################################################");
						logger.error("####################### CONTROLADOR: pdaP42InventarioLibreController (cargaInvLibCorreccionStock)	 ########");
						logger.error("###########################################################################################################");
					}
					
					stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
					request.setAttribute("stockTiendaRequestCopia", stockTiendaRequest);
					request.setAttribute("stockTiendaResponseCopia", stockTiendaResponse);
				}
				if (null != stockTiendaResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
					session.setAttribute("consultaStock", stockTiendaResponse);
				}else{
					session.removeAttribute("consultaStock");
				}
			}catch (Exception e) {
				session.removeAttribute("consultaStock");
			}	

			if(stockTiendaResponse!=null){
				if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
					
					List<ReferenciaType> listaMadres = Arrays.asList(stockTiendaResponse.getListaReferencias());
					PagedListHolder<ReferenciaType> listHolder = new PagedListHolder<ReferenciaType>(listaMadres);
					listHolder.setPageSize(Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS);
					session.setAttribute("listaRelaciones", listHolder);
					PdaSeleccionStock pdaSeleccionStock = new PdaSeleccionStock();
					pdaSeleccionStock.setCodArtOrig(codArt);
					pdaSeleccionStock.setOrigenInventario(origenInventario);
					pdaSeleccionStock.setMMC(mmc);
					pdaSeleccionStock.setOrigenGISAE(origenGISAE);
					pdaSeleccionStock.setNoGuardar(noGuardar);
					pdaSeleccionStock.setSeccion(pdaDatosCab.getSeccion());
					if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
						String mensajeError = stockTiendaResponse.getDescripcionRespuesta();
						model.addAttribute("mensajeError", mensajeError);
					} else if (stockTiendaResponse.getListaReferencias().length > 0){	
						boolean hasWarning = false;
						for (ReferenciaType referencia : stockTiendaResponse.getListaReferencias()){
							if (referencia.getCodigoError().equals(BigInteger.valueOf(new Long(2))) && !hasWarning){
								String mensajeError = referencia.getMensajeError();
								model.addAttribute("mensajeError", mensajeError);
								hasWarning = true;
							}
								
						}	
					}
					model.addAttribute("pdaSeleccionStock", pdaSeleccionStock);
					model.addAttribute("listaRelacion", listHolder);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					resultado = "pda_p43_invLibCorreccionStockSeleccion";
				} else if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_PORCION_CONSUMIDOR)){
					PdaArticulo pdaArticulo = new PdaArticulo();
					pdaArticulo.setCodArtOrig(codArt);
					pdaArticulo.setOrigenInventario(origenInventario);
					pdaArticulo.setMMC(mmc);
					pdaArticulo.setOrigenGISAE(origenGISAE);
					session.setAttribute("pdaArticuloStock", pdaArticulo);
					
					ReferenciaType referencia = stockTiendaResponse.getListaReferencias()[0];
					pdaArticulo.setCodArt(referencia.getCodigoReferencia().longValue());
					pdaArticulo.setDescArt(referencia.getDescripcion());
					pdaArticulo.setPrecio(Utilidades.convertirDoubleAString(referencia.getPVP().floatValue(),"###0.000"));
					pdaArticulo.setTipo(referencia.getTipoReferencia());
					pdaArticulo.setKgs(Utilidades.convertirDoubleAString((referencia.getStock() != null ? referencia.getStock().floatValue() : 0)/((referencia.getBandejas()!=null && referencia.getBandejas().floatValue() != 0)?referencia.getBandejas().floatValue():1),"###0.000"));
					if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
						pdaArticulo.setDescripcionError(stockTiendaResponse.getDescripcionRespuesta());
					} else if (stockTiendaResponse.getListaReferencias().length > 0){	
							boolean hasWarning = false;
							for (ReferenciaType ref : stockTiendaResponse.getListaReferencias()){
								if (ref.getCodigoError().equals(BigInteger.valueOf(new Long(2))) && !hasWarning){
									pdaArticulo.setDescripcionError(ref.getMensajeError());
									hasWarning = true;
								}
									
							}	
					}
					model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
					
					//Cargar datos de unidades y stock desde la tabla temporal
					this.cargarDatosTablaTemporal(pdaArticulo, origenInventario, origenGISAE, session);
	
					model.addAttribute("pdaArticulo", pdaArticulo);
					session.setAttribute("pdaArticuloStock", pdaArticulo);
	
					resultado = "pda_p44_invLibCorreccionStockPCS";
				} else if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_CONSULTA_CORRECION)){
					PdaStock stock = new PdaStock();
					stock.setCodArtOrig(codArt);
					stock.setOrigenInventario(origenInventario);
					stock.setMMC(mmc);
					stock.setOrigenGISAE(origenGISAE);
					stock.setNoGuardar(noGuardar);
					stock.setSeccion(pdaDatosCab.getSeccion());
					if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
						stock.setDescripcionError(stockTiendaResponse.getDescripcionRespuesta());
					} else if (stockTiendaResponse.getListaReferencias().length > 0){	
							boolean hasWarning = false;
							for (ReferenciaType referencia : stockTiendaResponse.getListaReferencias()){
								if (referencia.getCodigoError().equals(BigInteger.valueOf(new Long(2))) && !hasWarning){
									stock.setDescripcionError(referencia.getMensajeError());
									hasWarning = true;
								}
							}	
					}
					stock.setListaArticulos(this.obtenerListado(stockTiendaResponse.getListaReferencias()));
					
					//Cálculo de los artículos de la página 
					indiceArticuloInicialPagina = 0;
					totalArticulos = stock.getListaArticulos().size();
					if (totalArticulos >= Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS){
						indiceArticuloFinalPagina = Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
					}else{
						indiceArticuloFinalPagina = totalArticulos;
					}
							
					stock.setListaArticulosPagina(new ArrayList<PdaArticulo>(stock.getListaArticulos().subList(indiceArticuloInicialPagina, indiceArticuloFinalPagina)));
	
					//Carga de stocks de la temporal
					this.cargarDatosListaTablaTemporal(stock, origenInventario, origenGISAE, session);
					
					stock.setTotalArticulos(stock.getListaArticulos().size());
					stock.setPosicionGrupoArticulos(1);
					//Cálculo de total de páginas
					int numeroPaginas = stock.getListaArticulos().size()/Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
					if ((numeroPaginas*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS)<stock.getListaArticulos().size()){
						numeroPaginas++;
					}
					stock.setTotalPaginas(numeroPaginas);
					stock.setTipoMensaje(stockTiendaResponse.getTipoMensaje());
					model.addAttribute("pdaStock", stock);
					session.setAttribute("pdaStock", stock);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					resultado = "pda_p45_invLibCorreccionStockPCN";
				}
		}
			

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	private ArrayList<PdaArticulo> obtenerListado(ReferenciaType[] listaReferencias) throws Exception{
	
		ArrayList<PdaArticulo> listaArticulos = new ArrayList<PdaArticulo>();
		for (ReferenciaType referencia : listaReferencias){
			PdaArticulo articulo = new PdaArticulo();
			articulo.setCodArt(referencia.getCodigoReferencia().longValue());
			articulo.setDescArt(referencia.getDescripcion());
			if (null != referencia.getPVP()){
				articulo.setPrecio(Utilidades.convertirDoubleAString(referencia.getPVP().floatValue(),"###0.000"));
			}
			if (null != referencia.getBandejas()){
				articulo.setUnidades(referencia.getBandejas());
			}
			articulo.setStock(Utilidades.convertirDoubleAString(referencia.getStock().floatValue(),"###0.00").replace(",", "."));
			articulo.setTipo(referencia.getTipoReferencia());
			articulo.setKgs(Utilidades.convertirDoubleAString(new Float(0),"###0.000"));
			listaArticulos.add(articulo);
		}
		return listaArticulos;
	}

	private PdaDatosGuardadosInvLib guardarRegistroUnico(PdaDatosInventarioLibre pdaDatosInventarioLibre, HttpSession session) throws Exception {
		
		PdaDatosInventarioLibre pdaInventarioLibreBusqueda = new PdaDatosInventarioLibre();
		pdaInventarioLibreBusqueda.setCodCentro(pdaDatosInventarioLibre.getCodCentro());
		pdaInventarioLibreBusqueda.setCodArticulo(pdaDatosInventarioLibre.getCodArticulo());
		pdaInventarioLibreBusqueda.setCodMac(pdaDatosInventarioLibre.getCodMac());
		pdaInventarioLibreBusqueda.setOrigenGISAE(pdaDatosInventarioLibre.getOrigenGISAE());
		if (null != pdaInventarioLibreBusqueda.getOrigenGISAE() && pdaInventarioLibreBusqueda.getOrigenGISAE().equals("SI")){
			pdaInventarioLibreBusqueda.setCreationDate(new Date());
			pdaInventarioLibreBusqueda.setCodSeccion(pdaDatosInventarioLibre.getCodSeccion());
		}
		return this.guardarRegistros(pdaInventarioLibreBusqueda, session);
	}

	private PdaDatosGuardadosInvLib guardarRegistrosRelacionados(PdaDatosInventarioLibre pdaDatosInventarioLibre, HttpSession session, Long codSeccion) throws Exception {
		
		PdaDatosInventarioLibre pdaInventarioLibreBusqueda = new PdaDatosInventarioLibre();
		pdaInventarioLibreBusqueda.setCodCentro(pdaDatosInventarioLibre.getCodCentro());
		pdaInventarioLibreBusqueda.setCodMac(pdaDatosInventarioLibre.getCodMac());
		pdaInventarioLibreBusqueda.setOrigenGISAE(pdaDatosInventarioLibre.getOrigenGISAE());
		if (null != pdaInventarioLibreBusqueda.getOrigenGISAE() && pdaInventarioLibreBusqueda.getOrigenGISAE().equals("SI")){
			pdaInventarioLibreBusqueda.setCreationDate(new Date());
			pdaInventarioLibreBusqueda.setCodSeccion(pdaDatosInventarioLibre.getCodSeccion());
		}
		else{
			pdaInventarioLibreBusqueda.setCodSeccion(codSeccion);
		}

		return this.guardarRegistros(pdaInventarioLibreBusqueda, session);
	}
	
	private PdaDatosGuardadosInvLib guardarRegistros(PdaDatosInventarioLibre pdaDatosInventarioLibre, HttpSession session) throws Exception {
		
		PdaDatosGuardadosInvLib datosGuardados = new PdaDatosGuardadosInvLib();
		ModificarStockRequestType modificarStockRequest = new ModificarStockRequestType();
		//En listaArticulosTratados vamos a ir añadiendo los articulos
		//que vamos tratando para no volver a tratarlos cuando
		//esten dentro de articulos con varias unitarias
		List<Long> listaArticulosTratados = new ArrayList<Long>();
		List<PdaDatosInventarioLibre> listaInvLib  = new ArrayList<PdaDatosInventarioLibre>();
		if (null != pdaDatosInventarioLibre.getOrigenGISAE() && pdaDatosInventarioLibre.getOrigenGISAE().equals("SI")){
			listaInvLib = this.inventarioRotativoService.findAllPdaGISAE(pdaDatosInventarioLibre);
		} else {
			listaInvLib = this.inventarioRotativoService.findAllPda(pdaDatosInventarioLibre, true);
		}
		int totalReferencias = 0;
		int totalOk = 0;
		int totalError = 0;
		int totalAvisos = 0;
		
		User user = (User) session.getAttribute("user");
		ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
		paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
		ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
		
		if (listaInvLib!=null && listaInvLib.size()>0){
			for (PdaDatosInventarioLibre invLib : listaInvLib){
				
				List<ReferenciaModType> referencias = new ArrayList<ReferenciaModType>();
				List<PdaDatosInventarioLibre> listaInvLibRelacionadas = new ArrayList<PdaDatosInventarioLibre>();
				List<PdaDatosInventarioLibre> listaInvLibUnitarias = new ArrayList<PdaDatosInventarioLibre>();
				
				if (invLib != null && !Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_SI.equals(invLib.getFlgNoGuardar())){
					
					totalReferencias++;
					
					if (Constantes.INVENTARIO_LIBRE_UNICA_SI.equals(invLib.getFlgUnica())){
						if (!listaArticulosTratados.contains(invLib.getCodArticulo())){
							//Redondeo a 2 decimales del stock
							//Peticion 55001. Corrección errores del LOG.
							BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(invLib.getSalaStock());
						    BigDecimal roundedSalaStock = bdSalaStock.setScale(2, BigDecimal.ROUND_HALF_UP);

							//Redondeo a 2 decimales del stock
							//Peticion 55001. Corrección errores del LOG.
							BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(invLib.getCamaraStock());
							BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);

							BigDecimal roundedTotalStock = roundedCamaraStock.add(roundedSalaStock);

							Long lCamaraBandeja = new Long(invLib.getCamaraBandeja()!=null?invLib.getCamaraBandeja():"0");
							Long lSalaBandeja = new Long(invLib.getSalaBandeja()!=null?invLib.getSalaBandeja():"0");
							
							//Cálculo de campos calculados
							Long totalBandejas = lCamaraBandeja + lSalaBandeja; 

							ReferenciaModType referenciaModType = new ReferenciaModType();
							referenciaModType.setCodigoReferencia(new BigInteger(invLib.getCodArticulo().toString()));
							referenciaModType.setBandejas(new BigInteger(totalBandejas+""));
							referenciaModType.setStock(roundedTotalStock);
							referencias.add(referenciaModType);
							listaArticulosTratados.add(invLib.getCodArticulo());
						}
						else{
							//Ya esta tratado
							//Se borra el registro
							InventarioRotativo inventarioRotativo = new InventarioRotativo();
							inventarioRotativo.setCodCentro(invLib.getCodCentro());
							inventarioRotativo.setCodArticulo(invLib.getCodArticulo());
							inventarioRotativo.setCodArticuloRela(invLib.getCodArticuloRela());
							inventarioRotativo.setCodMac(invLib.getCodMac());
							this.inventarioRotativoService.delete(inventarioRotativo);
							totalOk++;
						}
					}else if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(invLib.getFlgVariasUnitarias())){
						
						int totalUniOk = 0;
						int totalUniError = 0;
						int totalUniAvisos = 0;
						
						//Buscamos las referencias unitarias
						
						PdaDatosInventarioLibre pdaDatosInventarioLibreUni = new PdaDatosInventarioLibre();
						pdaDatosInventarioLibreUni.setCodCentro(invLib.getCodCentro());
						pdaDatosInventarioLibreUni.setCodArticulo(invLib.getCodArticulo());
						pdaDatosInventarioLibreUni.setCodMac(invLib.getCodMac());

						listaInvLibUnitarias = this.inventarioRotativoService.findAllPda(pdaDatosInventarioLibreUni, false);
						for (PdaDatosInventarioLibre invLibUni : listaInvLibUnitarias){
							//Aqui tratamos cada una de las referencias unitarias
							//salvo la propia referencia que contiene las unitarias
							if (!listaArticulosTratados.contains(invLibUni.getCodArticuloRela())){ 
								if (!invLibUni.getCodArticulo().equals(invLibUni.getCodArticuloRela())){ 
									//Añado la referencia unitaria
									
									//Redondeo a 2 decimales del stock
									//Peticion 55001. Corrección errores del LOG.
									BigDecimal bdSalaStockUni = Utilidades.convertirStringABigDecimal(invLibUni.getSalaStock());
								    BigDecimal roundedSalaStockUni = bdSalaStockUni.setScale(2, BigDecimal.ROUND_HALF_UP);

									//Redondeo a 2 decimales del stock
									//Peticion 55001. Corrección errores del LOG.
									BigDecimal bdCamaraStockUni = Utilidades.convertirStringABigDecimal(invLibUni.getCamaraStock());
									BigDecimal roundedCamaraStockUni = bdCamaraStockUni.setScale(2, BigDecimal.ROUND_HALF_UP);

									BigDecimal roundedTotalStockUni = roundedCamaraStockUni.add(roundedSalaStockUni);

									Long lCamaraBandejaUni = new Long(invLibUni.getCamaraBandeja()!=null?invLibUni.getCamaraBandeja():"0");
									Long lSalaBandejaUni = new Long(invLibUni.getSalaBandeja()!=null?invLibUni.getSalaBandeja():"0");
									
									//Cálculo de campos calculados
									Long totalBandejasUni = lCamaraBandejaUni + lSalaBandejaUni; 

									ReferenciaModType referenciaModTypeUni = new ReferenciaModType();
									referenciaModTypeUni.setCodigoReferencia(new BigInteger(invLibUni.getCodArticuloRela().toString()));
									referenciaModTypeUni.setBandejas(new BigInteger(totalBandejasUni+""));
									referenciaModTypeUni.setStock(roundedTotalStockUni);
									referencias.add(referenciaModTypeUni);
									
									PdaDatosInventarioLibre pdaDatosInventarioLibreRel = new PdaDatosInventarioLibre();
									pdaDatosInventarioLibreRel.setCodCentro(invLibUni.getCodCentro());
									pdaDatosInventarioLibreRel.setCodArticulo(invLibUni.getCodArticuloRela());
									pdaDatosInventarioLibreRel.setCodMac(invLibUni.getCodMac());

									//Añado las referencias hijas de la unitaria
									
									listaInvLibRelacionadas = this.inventarioRotativoService.findAllPda(pdaDatosInventarioLibreRel, false);
									for (PdaDatosInventarioLibre invLibRel : listaInvLibRelacionadas){
										
										if (!invLibRel.getCodArticulo().equals(invLibRel.getCodArticuloRela())){ //Puede que la referencia este en la lista principal
											//Redondeo a 2 decimales del stock
											//Peticion 55001. Corrección errores del LOG.
											BigDecimal bdSalaStockRel = Utilidades.convertirStringABigDecimal(invLibRel.getSalaStock());
										    BigDecimal roundedSalaStockRel = bdSalaStockRel.setScale(2, BigDecimal.ROUND_HALF_UP);

											//Redondeo a 2 decimales del stock
											//Peticion 55001. Corrección errores del LOG.
											BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(invLibRel.getCamaraStock());
											BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);

											BigDecimal roundedTotalStockRel = roundedCamaraStock.add(roundedSalaStockRel);

											Long lCamaraBandejaRel = new Long(invLibRel.getCamaraBandeja()!=null?invLibRel.getCamaraBandeja():"0");
											Long lSalaBandejaRel = new Long(invLibRel.getSalaBandeja()!=null?invLibRel.getSalaBandeja():"0");
											
											//Cálculo de campos calculados
											Long totalBandejas = lCamaraBandejaRel + lSalaBandejaRel; 

											ReferenciaModType referenciaModTypeRel = new ReferenciaModType();
											referenciaModTypeRel.setCodigoReferencia(new BigInteger(invLibRel.getCodArticuloRela().toString()));
											referenciaModTypeRel.setBandejas(new BigInteger(totalBandejas+""));
											referenciaModTypeRel.setStock(roundedTotalStockRel);
											referencias.add(referenciaModTypeRel);
										}
									}
									listaArticulosTratados.add(invLibUni.getCodArticuloRela());
								}
								
							}
							else{
								//Ya esta tratado
								//Se borra el registro
								InventarioRotativo inventarioRotativo = new InventarioRotativo();
								inventarioRotativo.setCodCentro(invLibUni.getCodCentro());
								inventarioRotativo.setCodArticulo(invLibUni.getCodArticulo());
								inventarioRotativo.setCodArticuloRela(invLibUni.getCodArticuloRela());
								inventarioRotativo.setCodMac(invLibUni.getCodMac());
								this.inventarioRotativoService.delete(inventarioRotativo);
								totalUniOk++;
							}
							
							
							
							
							if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
								logger.error("###########################################################################################################");
								logger.error("############################## CONTROLADOR: pdaP42InventarioLibreontroller (1)");
								logger.error("############################## Origen GISAE:" + pdaDatosInventarioLibre.getOrigenGISAE());
								logger.error("############################## FlgNoGuardar:" + invLib.getFlgNoGuardar());
								logger.error("############################## FlgUnica:" + invLib.getFlgUnica());
								logger.error("############################## FlgVariasUnitarias:" + invLib.getFlgVariasUnitarias());
								logger.error("###########################################################################################################");
							}
							
							//Enviamos las referencias al servicio de modificarStock
							if (referencias != null && referencias.size() > 0){
								modificarStockRequest.setCodigoCentro(BigInteger.valueOf(pdaDatosInventarioLibre.getCodCentro()));
								modificarStockRequest.setListaReferencias(referencias.toArray(new ReferenciaModType[referencias.size()]));
								ModificarStockResponseType modificarStockResponseType = this.correccionStockService.modificarStock(modificarStockRequest,session);
							
								if(modificarStockResponseType!=null){
									
									if (Constantes.STOCK_TIENDA_RESULTADO_KO.equals(modificarStockResponseType.getCodigoRespuesta())){
										invLib.setError(modificarStockResponseType.getDescripcionRespuesta());
										invLib.setAviso("");
										totalUniError++;
									}else if (Constantes.STOCK_TIENDA_RESULTADO_WARN.equals(modificarStockResponseType.getCodigoRespuesta())){
										invLib.setAviso(modificarStockResponseType.getDescripcionRespuesta());
										invLib.setError("");
										totalUniAvisos++;
									}else{
										invLib.setAviso("");
										invLib.setError("");
										totalUniOk++;
									}
								
									if (Constantes.STOCK_TIENDA_RESULTADO_OK.equals(modificarStockResponseType.getCodigoRespuesta())){
										//Borramos la referencia unitaria
										this.borrarReferenciaRelaciones(session, invLibUni.getCodArticuloRela());
										if (null != pdaDatosInventarioLibre.getOrigenGISAE() && pdaDatosInventarioLibre.getOrigenGISAE().equals("SI")){
											InventarioRotativoGisae inventarioRotativoGisae = new InventarioRotativoGisae();
											inventarioRotativoGisae.setCodArticulo(invLib.getCodArticulo());
											inventarioRotativoGisae.setCodCentro(invLib.getCodCentro());
											inventarioRotativoGisae.setFechaGen(new Date());
											inventarioRotativoGisae.setTratado(true);
											this.inventarioRotativoGisaeService.updateInventarioATratado(inventarioRotativoGisae);
										}
									}else{//Error o warning informar en pantalla
										InventarioRotativo inventarioRotativo = new InventarioRotativo();
										inventarioRotativo.setCodCentro(invLib.getCodCentro());
										inventarioRotativo.setCodArticulo(invLib.getCodArticulo());
										inventarioRotativo.setCodArticuloRela(invLib.getCodArticuloRela());
										inventarioRotativo.setCodMac(invLib.getCodMac());
										if (Constantes.STOCK_TIENDA_RESULTADO_WARN.equals(modificarStockResponseType.getCodigoRespuesta())){
											inventarioRotativo.setAviso(modificarStockResponseType.getDescripcionRespuesta());
											inventarioRotativo.setError("");
										}else{
											inventarioRotativo.setAviso("");
											inventarioRotativo.setError(modificarStockResponseType.getDescripcionRespuesta());
										}
										this.inventarioRotativoService.updateErrorAvisoArticulo(inventarioRotativo);
										//Actualizamos también el aviso y error de la unitaria
										InventarioRotativo inventarioRotativoUni = new InventarioRotativo();
										inventarioRotativoUni.setCodCentro(invLibUni.getCodCentro());
										inventarioRotativoUni.setCodArticulo(invLibUni.getCodArticulo());
										inventarioRotativoUni.setCodArticuloRela(invLibUni.getCodArticuloRela());
										inventarioRotativoUni.setCodMac(invLibUni.getCodMac());
										if (Constantes.STOCK_TIENDA_RESULTADO_WARN.equals(modificarStockResponseType.getCodigoRespuesta())){
											inventarioRotativoUni.setAviso(modificarStockResponseType.getDescripcionRespuesta());
											inventarioRotativoUni.setError("");
										}else{
											inventarioRotativoUni.setAviso("");
											inventarioRotativoUni.setError(modificarStockResponseType.getDescripcionRespuesta());
										}
										this.inventarioRotativoService.updateErrorAvisoArticulo(inventarioRotativoUni);
										//Actualizamos también el aviso y error de las hijas de la unitaria, si existen
										for (PdaDatosInventarioLibre invLibRel : listaInvLibRelacionadas){
											InventarioRotativo inventarioRotativoRel = new InventarioRotativo();
											inventarioRotativoRel.setCodCentro(invLibRel.getCodCentro());
											inventarioRotativoRel.setCodArticulo(invLibRel.getCodArticulo());
											inventarioRotativoRel.setCodArticuloRela(invLibRel.getCodArticuloRela());
											inventarioRotativoRel.setCodMac(invLibRel.getCodMac());
											if (Constantes.STOCK_TIENDA_RESULTADO_WARN.equals(modificarStockResponseType.getCodigoRespuesta())){
												inventarioRotativoRel.setAviso(modificarStockResponseType.getDescripcionRespuesta());
												inventarioRotativoRel.setError("");
											}else{
												inventarioRotativoRel.setAviso("");
												inventarioRotativoRel.setError(modificarStockResponseType.getDescripcionRespuesta());
											}
											this.inventarioRotativoService.updateErrorAvisoArticulo(inventarioRotativoRel);
										}
									}
								}
							}
							referencias.clear();
							
						}
						if (totalUniError > 0){
							totalError++;
						}
						else if (totalUniAvisos > 0){
							totalAvisos++;
						}
						else if (totalUniOk > 0){
							totalOk++;
							//Si todas las unitarias se han grabado OK
							//se borra el registro Madre de las unitarias
							InventarioRotativo inventarioRotativo = new InventarioRotativo();
							inventarioRotativo.setCodCentro(invLib.getCodCentro());
							inventarioRotativo.setCodArticulo(invLib.getCodArticulo());
							inventarioRotativo.setCodArticuloRela(invLib.getCodArticuloRela());
							inventarioRotativo.setCodMac(invLib.getCodMac());
							this.inventarioRotativoService.delete(inventarioRotativo);
						}
						
					}else {
						if (!listaArticulosTratados.contains(invLib.getCodArticulo())){
							PdaDatosInventarioLibre pdaDatosInventarioLibreRel = new PdaDatosInventarioLibre();
							pdaDatosInventarioLibreRel.setCodCentro(invLib.getCodCentro());
							pdaDatosInventarioLibreRel.setCodArticulo(invLib.getCodArticulo());
							pdaDatosInventarioLibreRel.setCodMac(invLib.getCodMac());

							listaInvLibRelacionadas = this.inventarioRotativoService.findAllPda(pdaDatosInventarioLibreRel, false);
							for (PdaDatosInventarioLibre invLibRel : listaInvLibRelacionadas){
								
								//Redondeo a 2 decimales del stock
								//Peticion 55001. Corrección errores del LOG.
								BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(invLibRel.getSalaStock());
							    BigDecimal roundedSalaStock = bdSalaStock.setScale(2, BigDecimal.ROUND_HALF_UP);

								//Redondeo a 2 decimales del stock
								//Peticion 55001. Corrección errores del LOG.
								BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(invLibRel.getCamaraStock());
								BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);

								BigDecimal roundedTotalStock = roundedCamaraStock.add(roundedSalaStock);

								Long lCamaraBandeja = new Long(invLibRel.getCamaraBandeja()!=null?invLibRel.getCamaraBandeja():"0");
								Long lSalaBandeja = new Long(invLibRel.getSalaBandeja()!=null?invLibRel.getSalaBandeja():"0");
								
								//Cálculo de campos calculados
								Long totalBandejas = lCamaraBandeja + lSalaBandeja; 

								ReferenciaModType referenciaModType = new ReferenciaModType();
								referenciaModType.setCodigoReferencia(new BigInteger(invLibRel.getCodArticuloRela().toString()));
								referenciaModType.setBandejas(new BigInteger(totalBandejas+""));
								referenciaModType.setStock(roundedTotalStock);
								referencias.add(referenciaModType);
							}
							listaArticulosTratados.add(invLib.getCodArticulo());
						}else{
							//Ya esta tratado
							//Se borra el registro
							InventarioRotativo inventarioRotativo = new InventarioRotativo();
							inventarioRotativo.setCodCentro(invLib.getCodCentro());
							inventarioRotativo.setCodArticulo(invLib.getCodArticulo());
							inventarioRotativo.setCodArticuloRela(invLib.getCodArticuloRela());
							inventarioRotativo.setCodMac(invLib.getCodMac());
							this.inventarioRotativoService.delete(inventarioRotativo);
							totalOk++;
						}
					}
				

					if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
						logger.error("###########################################################################################################");
						logger.error("############################## CONTROLADOR: pdaP42InventarioLibreontroller (2)");
						logger.error("############################## Origen GISAE:" + pdaDatosInventarioLibre.getOrigenGISAE());
						logger.error("############################## FlgNoGuardar:" + invLib.getFlgNoGuardar());
						logger.error("############################## FlgUnica:" + invLib.getFlgUnica());
						logger.error("############################## FlgVariasUnitarias:" + invLib.getFlgVariasUnitarias());
						logger.error("###########################################################################################################");
					}
				
					if (referencias != null && referencias.size() > 0){
						modificarStockRequest.setCodigoCentro(BigInteger.valueOf(pdaDatosInventarioLibre.getCodCentro()));
						modificarStockRequest.setListaReferencias(referencias.toArray(new ReferenciaModType[referencias.size()]));
						ModificarStockResponseType modificarStockResponseType = this.correccionStockService.modificarStock(modificarStockRequest,session);
					
						if(modificarStockResponseType!=null){
							
							if (Constantes.STOCK_TIENDA_RESULTADO_KO.equals(modificarStockResponseType.getCodigoRespuesta())){
								invLib.setError(modificarStockResponseType.getDescripcionRespuesta());
								invLib.setAviso("");
								totalError++;
							}else if (Constantes.STOCK_TIENDA_RESULTADO_WARN.equals(modificarStockResponseType.getCodigoRespuesta())){
								invLib.setAviso(modificarStockResponseType.getDescripcionRespuesta());
								invLib.setError("");
								totalAvisos++;
							}else{
								invLib.setAviso("");
								invLib.setError("");
								totalOk++;
							}
						
							if (Constantes.STOCK_TIENDA_RESULTADO_OK.equals(modificarStockResponseType.getCodigoRespuesta())){
								//Se borra el registro
								InventarioRotativo inventarioRotativo = new InventarioRotativo();
								inventarioRotativo.setCodCentro(invLib.getCodCentro());
								inventarioRotativo.setCodArticulo(invLib.getCodArticulo());
								inventarioRotativo.setCodMac(invLib.getCodMac());
								this.inventarioRotativoService.delete(inventarioRotativo);
								if (null != pdaDatosInventarioLibre.getOrigenGISAE() && pdaDatosInventarioLibre.getOrigenGISAE().equals("SI")){
									InventarioRotativoGisae inventarioRotativoGisae = new InventarioRotativoGisae();
									inventarioRotativoGisae.setCodArticulo(invLib.getCodArticulo());
									inventarioRotativoGisae.setCodCentro(invLib.getCodCentro());
									inventarioRotativoGisae.setFechaGen(new Date());
									inventarioRotativoGisae.setTratado(true);
									this.inventarioRotativoGisaeService.updateInventarioATratado(inventarioRotativoGisae);
								}
							}else{//Error o warning informar en pantalla
								InventarioRotativo inventarioRotativo = new InventarioRotativo();
								inventarioRotativo.setCodCentro(invLib.getCodCentro());
								inventarioRotativo.setCodArticulo(invLib.getCodArticulo());
								inventarioRotativo.setCodArticuloRela(invLib.getCodArticuloRela());
								inventarioRotativo.setCodMac(invLib.getCodMac());
								if (Constantes.STOCK_TIENDA_RESULTADO_WARN.equals(modificarStockResponseType.getCodigoRespuesta())){
									inventarioRotativo.setAviso(modificarStockResponseType.getDescripcionRespuesta());
									inventarioRotativo.setError("");
								}else{
									inventarioRotativo.setAviso("");
									inventarioRotativo.setError(modificarStockResponseType.getDescripcionRespuesta());
								}
								this.inventarioRotativoService.updateErrorAvisoArticulo(inventarioRotativo);
								//Actualizamos también las relacionadas, si existen
								for (PdaDatosInventarioLibre invLibRel : listaInvLibRelacionadas){
									InventarioRotativo inventarioRotativoRel = new InventarioRotativo();
									inventarioRotativoRel.setCodCentro(invLibRel.getCodCentro());
									inventarioRotativoRel.setCodArticulo(invLibRel.getCodArticulo());
									inventarioRotativoRel.setCodArticuloRela(invLibRel.getCodArticuloRela());
									inventarioRotativoRel.setCodMac(invLibRel.getCodMac());
									if (Constantes.STOCK_TIENDA_RESULTADO_WARN.equals(modificarStockResponseType.getCodigoRespuesta())){
										inventarioRotativoRel.setAviso(modificarStockResponseType.getDescripcionRespuesta());
										inventarioRotativoRel.setError("");
									}else{
										inventarioRotativoRel.setAviso("");
										inventarioRotativoRel.setError(modificarStockResponseType.getDescripcionRespuesta());
									}
									this.inventarioRotativoService.updateErrorAvisoArticulo(inventarioRotativoRel);
								}
							}
						}
					}
				}
			}
			
			datosGuardados.setTotalGuardadosAviso(totalAvisos);
			datosGuardados.setTotalGuardadosError(totalError);
			datosGuardados.setTotalGuardadosOk(totalOk);
			datosGuardados.setTotalRegistros(totalReferencias);
	
		}
		
		return datosGuardados;
	}
	
	private void borrarReferenciaSesion(HttpSession session, Long codArticulo) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		InventarioRotativo inventarioRotativo = new InventarioRotativo();
		inventarioRotativo.setCodCentro(codCentro);
		inventarioRotativo.setCodMac(mac);
		inventarioRotativo.setCodArticulo(codArticulo);
		inventarioRotativo.setCodArticuloRela(codArticulo);
		
		this.inventarioRotativoService.delete(inventarioRotativo);
		
		InventarioRotativo invRotUni = new InventarioRotativo();
		invRotUni.setCodCentro(codCentro);
		invRotUni.setCodMac(mac);
		invRotUni.setCodArticuloRela(codArticulo);
		
		InventarioRotativo existeInvRotUni = this.inventarioRotativoService.findOne(invRotUni);
		if (existeInvRotUni == null){
			//Significa que no forma parte como unitaria 
			//de ninguna referencia madre y se puede borrar todo.
			this.inventarioRotativoService.deleteRefSinRelaciones(inventarioRotativo);
		}
		
	}

	private void borrarReferenciaUnitariaSesion(HttpSession session, Long codArticulo) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		PdaDatosInventarioLibre pdaDatosInvLib = new PdaDatosInventarioLibre();
		pdaDatosInvLib.setCodCentro(codCentro);
		pdaDatosInvLib.setCodArticulo(codArticulo);
		pdaDatosInvLib.setCodMac(mac);

		List<PdaDatosInventarioLibre> listaInvLib  = new ArrayList<PdaDatosInventarioLibre>();
		listaInvLib = this.inventarioRotativoService.findAllPda(pdaDatosInvLib, false);
		for (PdaDatosInventarioLibre pdaDatosInventarioLibre : listaInvLib){
			InventarioRotativo inventarioRotativo = new InventarioRotativo();
			inventarioRotativo.setCodCentro(codCentro);
			inventarioRotativo.setCodMac(mac);
			inventarioRotativo.setCodArticulo(pdaDatosInventarioLibre.getCodArticuloRela());
			inventarioRotativo.setCodArticuloRela(pdaDatosInventarioLibre.getCodArticuloRela());

			InventarioRotativo existeInvRot = this.inventarioRotativoService.findOne(inventarioRotativo);
			if (existeInvRot == null){
				//Significa que no está introducida en la lista de referencias,
				//sólo forma parte como unitaria de la referencia madre y se puede borrar.
				this.inventarioRotativoService.deleteRefSinRelaciones(inventarioRotativo);
			}
			else{
				//Borramos la sólo la parte correspondiente a la 
				//referencia madre (codArticulo) + la unitaria (codArticuloRela)
				InventarioRotativo invRot = new InventarioRotativo();
				invRot.setCodCentro(codCentro);
				invRot.setCodMac(mac);
				invRot.setCodArticulo(codArticulo);
				invRot.setCodArticuloRela(pdaDatosInventarioLibre.getCodArticuloRela());

				this.inventarioRotativoService.delete(invRot);
			}
		}
		
		//Borramos la ref. madre de las unitarias
		InventarioRotativo inventarioRotativo = new InventarioRotativo();
		inventarioRotativo.setCodCentro(codCentro);
		inventarioRotativo.setCodMac(mac);
		inventarioRotativo.setCodArticulo(codArticulo);
		inventarioRotativo.setCodArticuloRela(codArticulo);

		this.inventarioRotativoService.delete(inventarioRotativo);
	}

	private void borrarReferenciaRelaciones(HttpSession session, Long codArticulo) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		InventarioRotativo inventarioRotativo = new InventarioRotativo();
		inventarioRotativo.setCodCentro(codCentro);
		inventarioRotativo.setCodMac(mac);
		inventarioRotativo.setCodArticulo(codArticulo);
		
		this.inventarioRotativoService.deleteRefSinRelaciones(inventarioRotativo);
	}

	private void borrarReferenciasSesion(HttpSession session, Long codSeccion) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		InventarioRotativo inventarioRotativo = new InventarioRotativo();
		inventarioRotativo.setCodCentro(codCentro);
		inventarioRotativo.setCodMac(mac);
		inventarioRotativo.setCodSeccion(codSeccion);
		
		this.inventarioRotativoService.delete(inventarioRotativo);
	}
	
	private PdaDatosInventarioLibre guardarRegistroTemporal(PdaDatosInventarioLibre pdaDatosInventarioLibre, HttpSession session, HttpServletRequest request) throws Exception {
		
		Locale locale = LocaleContextHolder.getLocale();
		try{
			if (pdaDatosInventarioLibre != null){
				User user = (User) session.getAttribute("user");
				Long codCentro = user.getCentro().getCodCentro();
				String mac = user.getMac();
				
				InventarioRotativo inventarioRotativo = new InventarioRotativo();
				inventarioRotativo.setCodArticulo(pdaDatosInventarioLibre.getCodArticulo());
				inventarioRotativo.setCodArticuloRela(pdaDatosInventarioLibre.getCodArticulo());
				inventarioRotativo.setCodCentro(codCentro);
				if (null != pdaDatosInventarioLibre.getOrigenGISAE() && pdaDatosInventarioLibre.getOrigenGISAE().equals("SI")){
					inventarioRotativo.setCodMac(pdaDatosInventarioLibre.getCodMac());
				} else {
					inventarioRotativo.setCodMac(mac);
				}
				
				
				//Obtenemos el stock actual.
				ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
				stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(pdaDatosInventarioLibre.getCodCentro()));
				stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
				List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
				listaReferencias.add(BigInteger.valueOf(pdaDatosInventarioLibre.getCodArticulo()));
				stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
				boolean errorConsultaStock = false;
				try{
					ConsultarStockResponseType stockTiendaResponse = null;
					if (stockTiendaRequest.equals(request.getAttribute("stockTiendaRequestCopia"))){
						stockTiendaResponse = (ConsultarStockResponseType) request.getAttribute("stockTiendaResponseCopia");
					}
					else{
						
						
						ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
						paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
						ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
						
						if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
							logger.error("###########################################################################################################");
							logger.error("####################### CONTROLADOR: pdaP42InventarioLibreController (guardarRegistroTemporal 1)	 ########");
							logger.error("###########################################################################################################");
						}
						stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
						request.setAttribute("stockTiendaRequestCopia", stockTiendaRequest);
						request.setAttribute("stockTiendaResponseCopia", stockTiendaResponse);
					}
					if (null == stockTiendaResponse || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
						pdaDatosInventarioLibre.setStockActual(new Double(0));
						errorConsultaStock = true;
					}else{
						if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
							stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
							ConsultarStockResponseType stockTiendaResponseBasica = null;
							
							
							ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
							paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
							ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
							
							if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
								logger.error("###########################################################################################################");
								logger.error("####################### CONTROLADOR: pdaP42InventarioLibreController (guardarRegistroTemporal 2)	 ########");
								logger.error("###########################################################################################################");
							}
							stockTiendaResponseBasica = this.correccionStockService.consultaStock(stockTiendaRequest,session);
							if (null == stockTiendaResponseBasica || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponseBasica.getCodigoRespuesta())){
								pdaDatosInventarioLibre.setStockActual(new Double(0));
								errorConsultaStock = true;
							}else{
								if (stockTiendaResponseBasica.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
									pdaDatosInventarioLibre.setStockActual(stockTiendaResponseBasica.getListaReferencias()[0].getBandejas().doubleValue());
									pdaDatosInventarioLibre.setFlgStockPrincipal(Constantes.STOCK_PRINCIPAL_BANDEJAS);
								} else {
									pdaDatosInventarioLibre.setStockActual(stockTiendaResponseBasica.getListaReferencias()[0].getStock().doubleValue());
									pdaDatosInventarioLibre.setFlgStockPrincipal(Constantes.STOCK_PRINCIPAL_STOCK);
								}
							}
						}
						else{
							if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								pdaDatosInventarioLibre.setStockActual(stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue());
								pdaDatosInventarioLibre.setFlgStockPrincipal(Constantes.STOCK_PRINCIPAL_BANDEJAS);
							} else {
								pdaDatosInventarioLibre.setStockActual(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue());
								pdaDatosInventarioLibre.setFlgStockPrincipal(Constantes.STOCK_PRINCIPAL_STOCK);
							}
						}
						if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
							pdaDatosInventarioLibre.setFlgVariasUnitarias(Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI);
						}else{
							pdaDatosInventarioLibre.setFlgVariasUnitarias(Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_NO);
						}
						if (stockTiendaResponse.getListaReferencias()!=null && stockTiendaResponse.getListaReferencias().length > 1){
							pdaDatosInventarioLibre.setFlgUnica(Constantes.INVENTARIO_LIBRE_UNICA_NO);
						}else{
							pdaDatosInventarioLibre.setFlgUnica(Constantes.INVENTARIO_LIBRE_UNICA_SI);
						}
					}				
				}catch (Exception e) {
					errorConsultaStock = true;
					pdaDatosInventarioLibre.setStockActual(new Double(0));
				}	

				//Redondeo a 2 decimales del stock
				//Peticion 55001. Corrección errores del LOG.
				BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getSalaStock());
			    BigDecimal roundedSalaStock = bdSalaStock.setScale(2, BigDecimal.ROUND_HALF_UP);
				inventarioRotativo.setSalaStock(roundedSalaStock.doubleValue());

				//Redondeo a 2 decimales del stock
				//Peticion 55001. Corrección errores del LOG.
				BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getCamaraStock());
				BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);
				inventarioRotativo.setCamaraStock(roundedCamaraStock.doubleValue());

				inventarioRotativo.setFlgNoGuardar(pdaDatosInventarioLibre.getFlgNoGuardar());
				inventarioRotativo.setFlgStockPrincipal(pdaDatosInventarioLibre.getFlgStockPrincipal());
				inventarioRotativo.setFlgUnica(pdaDatosInventarioLibre.getFlgUnica());
				inventarioRotativo.setFlgVariasUnitarias(pdaDatosInventarioLibre.getFlgVariasUnitarias());
				
				BigDecimal roundedTotalStock = roundedCamaraStock.add(roundedSalaStock);
				pdaDatosInventarioLibre.setTotalStock(Utilidades.convertirDoubleAString(roundedTotalStock.doubleValue(),"###0.00").replace('.', ','));

				if (Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS.equals(pdaDatosInventarioLibre.getFlgStockPrincipal())){
					Long lCamaraBandeja = new Long(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");
					Long lSalaBandeja = new Long(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
					inventarioRotativo.setCamaraBandeja(lCamaraBandeja);
					inventarioRotativo.setSalaBandeja(lSalaBandeja);
					
					//Cálculo de campos calculados
					Long totalBandejas = lCamaraBandeja + lSalaBandeja; 
					pdaDatosInventarioLibre.setTotalBandeja(totalBandejas.toString());
					Double diferencia = totalBandejas - pdaDatosInventarioLibre.getStockActual();
					if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
						//Si tiene referencias unitarias hay que mostrar diferencia 0
						diferencia = new Double(0);
					}
					pdaDatosInventarioLibre.setDiferencia(diferencia);
				}else{
					Double diferencia = roundedTotalStock.doubleValue() - pdaDatosInventarioLibre.getStockActual();
					if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
						//Si tiene referencias unitarias hay que mostrar diferencia 0
						diferencia = new Double(0);
					}
					pdaDatosInventarioLibre.setDiferencia(diferencia);
				}
				
				//Formateo de cantidades de bandejas
				pdaDatosInventarioLibre.setSalaBandeja(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
				pdaDatosInventarioLibre.setCamaraBandeja(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");

				//Formateo de cantidades de stock
				pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(roundedSalaStock.doubleValue(),"###0.00").replace(',', '.'));
				pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(roundedCamaraStock.doubleValue(),"###0.00").replace(',', '.'));

				//Carga de descripciones de stock actual y diferencia
				if (errorConsultaStock){
					pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.stockActualError", null, locale));
					pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.diferenciaError", null, locale));
				}else{
					pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.stockActual", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getStockActual(),"###0.00").replace('.', ',')}, locale));
					if (pdaDatosInventarioLibre.getDiferencia() > 0) {
						pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.diferencia", new Object[]{"+" + Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
				
					} else {
						pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
								"pda_p42_inventarioLibre.diferencia", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
					
					}
					
				
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
				vDatosDiarioArt.setCodArt(pdaDatosInventarioLibre.getCodArticulo());
				vDatosDiarioArt = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

				inventarioRotativo.setCodArea(vDatosDiarioArt.getGrupo1());
				inventarioRotativo.setCodSeccion(vDatosDiarioArt.getGrupo2());

				this.inventarioRotativoService.insertUpdate(inventarioRotativo);
			}
			if (pdaDatosInventarioLibre.getError() != null && !"".equals(pdaDatosInventarioLibre.getError())){
				pdaDatosInventarioLibre.setAviso("");
				pdaDatosInventarioLibre.setError("");
			}
		}catch (Exception e) {
			pdaDatosInventarioLibre.setAviso("");
			pdaDatosInventarioLibre.setError(this.messageSource.getMessage(
					"pda_p42_inventarioLibre.errorGuardadoTemporal", null, locale));
		}
		
		return pdaDatosInventarioLibre;
	}
	
	private void refrescarSecciones(ModelMap model, PdaDatosInventarioLibre pdaDatosInventarioLibreActual, HttpSession session) throws Exception {
		
		LinkedHashMap<Long, String> secciones = new LinkedHashMap<Long, String>();
		
		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");
		
		//Obtener secciones
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();
	
		//Tratamiento de secciones
		if(null != pdaDatosInventarioLibreActual.getOrigenGISAE() && pdaDatosInventarioLibreActual.getOrigenGISAE().equals("SI")){
			InventarioRotativoGisae inventarioRotativo = new InventarioRotativoGisae();
			inventarioRotativo.setCodCentro(codCentro);
			inventarioRotativo.setFechaGen(new Date());
			secciones = this.inventarioRotativoGisaeService.findAllSeccion(inventarioRotativo);
		} else {
			PdaDatosInventarioLibre pdaDatosInventarioLibreSec = new PdaDatosInventarioLibre();
			pdaDatosInventarioLibreSec.setCodCentro(codCentro);
			pdaDatosInventarioLibreSec.setCodMac(mac);
			pdaDatosInventarioLibreSec.setCodArticulo(pdaDatosInventarioLibreActual.getCodArticulo());
			
			secciones = this.inventarioRotativoService.findSeccionesPda(pdaDatosInventarioLibreSec);
		}
		model.addAttribute("secciones", secciones);
	}
	
	private PdaDatosInventarioLibre obtenerReferenciasSesion(HttpSession session, Long codSeccion, boolean isGISAE) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();
		pdaDatosInventarioLibre.setCodCentro(codCentro);
		if (!isGISAE){
			pdaDatosInventarioLibre.setCodMac(mac);
			pdaDatosInventarioLibre.setCodSeccion(codSeccion);
		} else {
			pdaDatosInventarioLibre.setCodMac("GISAE");
			pdaDatosInventarioLibre.setCodSeccion(codSeccion);
		}
		
		List<PdaDatosInventarioLibre> listaDatosInventarioLibre = null;
		
		if (isGISAE){
			listaDatosInventarioLibre = this.inventarioRotativoService.findAllPdaGISAE(pdaDatosInventarioLibre);
		} else {
			listaDatosInventarioLibre = this.inventarioRotativoService.findAllPda(pdaDatosInventarioLibre, true);
		}
		
		HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();

		if (listaDatosInventarioLibre == null || listaDatosInventarioLibre.isEmpty()){
			session.removeAttribute("listaInvLib");
			session.removeAttribute("hashMapClavesInvLib");
		}else{
			session.setAttribute("listaInvLib", listaDatosInventarioLibre);
			HashMap<String,Integer> mapAux = new HashMap<String,Integer>();
			Integer posicionMap = 1;
			if (listaDatosInventarioLibre != null) {
				Iterator<PdaDatosInventarioLibre> it  = listaDatosInventarioLibre.iterator();
				while(it.hasNext() && !listaDatosInventarioLibre.isEmpty() ){
					PdaDatosInventarioLibre invLib = it.next();
					invLib.setPosicion(posicionMap);
					mapAux.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
					posicionMap++;
				}
			}
			session.setAttribute("hashMapClavesInvLib", mapAux);
		}
		
		if (session.getAttribute("hashMapClavesInvLib") != null && session.getAttribute("listaInvLib") != null)
		{
			mapClavesInvLib = (HashMap<String, Integer>) session.getAttribute("hashMapClavesInvLib");
			
			listaDatosInventarioLibre = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
			
			pdaDatosInventarioLibre = (PdaDatosInventarioLibre) listaDatosInventarioLibre.get(0);
			
			//Refrescamos el total del objeto.
			pdaDatosInventarioLibre.setTotal(mapClavesInvLib.size());
			
			//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
			session.setAttribute("ultimaRefInvLib", String.valueOf(pdaDatosInventarioLibre.getCodArticulo()));
		}
		
		return pdaDatosInventarioLibre;
	}
	
	private void cargarDatosTablaTemporal(PdaArticulo pdaArticulo, String origenInventario, String origenGISAE, HttpSession session) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		InventarioRotativo inventarioRotativo = new InventarioRotativo();
		inventarioRotativo.setCodArticulo(pdaArticulo.getCodArtOrig());
		inventarioRotativo.setCodArticuloRela(pdaArticulo.getCodArt());
		inventarioRotativo.setCodCentro(codCentro);
		if ("SI".equals(origenGISAE)){
			inventarioRotativo.setCodMac("GISAE");
			
		} else {
			inventarioRotativo.setCodMac(mac);
		}
		
		inventarioRotativo = this.inventarioRotativoService.findOne(inventarioRotativo);

		if (inventarioRotativo != null){
			if (Constantes.INVENTARIO_LIBRE_CAMARA_ALMACEN.equals(origenInventario)){
				pdaArticulo.setUnidades(inventarioRotativo.getCamaraBandeja()!=null?BigInteger.valueOf(inventarioRotativo.getCamaraBandeja()):new BigInteger("0"));
				pdaArticulo.setStock(Utilidades.convertirDoubleAString(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"),"###0.00"));
			}else if (Constantes.INVENTARIO_LIBRE_SALA_VENTA.equals(origenInventario)){    			
				pdaArticulo.setUnidades(inventarioRotativo.getSalaBandeja()!=null?BigInteger.valueOf(inventarioRotativo.getSalaBandeja()):new BigInteger("0"));
				pdaArticulo.setStock(Utilidades.convertirDoubleAString(inventarioRotativo.getSalaStock()!=null?inventarioRotativo.getSalaStock():new Double("0"),"###0.00"));
			}else{
				pdaArticulo.setUnidades(new BigInteger("0"));
				pdaArticulo.setStock(Utilidades.convertirDoubleAString(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"),"###0.00"));		
			}
		}else{
			pdaArticulo.setUnidades(new BigInteger("0"));
			pdaArticulo.setStock(Utilidades.convertirDoubleAString(new Double("0"),"###0.00"));		
		}
	}
	
	private void cargarDatosListaTablaTemporal(PdaStock pdaStock, String origenInventario, String origenGISAE, HttpSession session) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		if (pdaStock != null && pdaStock.getListaArticulos()!=null){
			for (PdaArticulo pdaArticulo : pdaStock.getListaArticulos()){
				InventarioRotativo inventarioRotativo = new InventarioRotativo();
				inventarioRotativo.setCodArticulo(pdaStock.getCodArtOrig());
				inventarioRotativo.setCodArticuloRela(pdaArticulo.getCodArt());
				inventarioRotativo.setCodCentro(codCentro);
				if ("SI".equals(origenGISAE)){
					inventarioRotativo.setCodMac("GISAE");
					
				} else {
					inventarioRotativo.setCodMac(mac);
				}

				inventarioRotativo = this.inventarioRotativoService.findOne(inventarioRotativo);
				
				if (inventarioRotativo != null){
					if (Constantes.INVENTARIO_LIBRE_CAMARA_ALMACEN.equals(origenInventario)){
						pdaArticulo.setUnidades(inventarioRotativo.getCamaraBandeja()!=null?BigInteger.valueOf(inventarioRotativo.getCamaraBandeja()):new BigInteger("0"));
						pdaArticulo.setStock(Utilidades.convertirDoubleAString(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"),"###0.00").replace(",", "."));
					}else if (Constantes.INVENTARIO_LIBRE_SALA_VENTA.equals(origenInventario)){    			
						pdaArticulo.setUnidades(inventarioRotativo.getSalaBandeja()!=null?BigInteger.valueOf(inventarioRotativo.getSalaBandeja()):new BigInteger("0"));
						pdaArticulo.setStock(Utilidades.convertirDoubleAString(inventarioRotativo.getSalaStock()!=null?inventarioRotativo.getSalaStock():new Double("0"),"###0.00").replace(",", "."));
					}else{
						pdaArticulo.setUnidades(new BigInteger("0"));
						pdaArticulo.setStock(Utilidades.convertirDoubleAString(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"),"###0.00").replace(",", "."));		
					}
				}else{
					pdaArticulo.setUnidades(new BigInteger("0"));
					pdaArticulo.setStock(Utilidades.convertirDoubleAString(new Double("0"),"###0.00").replace(",", "."));	
				}
			}	
		}
	}
	
	private void cargarDatosSumaTablaTemporal(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
		
		PdaDatosInventarioLibre pdaDatosInventarioLibreSuma = this.inventarioRotativoService.findSumaCantidades(pdaDatosInventarioLibre);

		if (pdaDatosInventarioLibreSuma != null){
			Long lCamaraBandeja = new Long(pdaDatosInventarioLibreSuma.getCamaraBandeja()!=null?pdaDatosInventarioLibreSuma.getCamaraBandeja():"0");
			Long lSalaBandeja = new Long(pdaDatosInventarioLibreSuma.getSalaBandeja()!=null?pdaDatosInventarioLibreSuma.getSalaBandeja():"0");
			
			//Redondeo a 2 decimales del stock
			//Peticion 55001. Corrección errores del LOG.
			BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibreSuma.getCamaraStock());
		    BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);

			//Redondeo a 2 decimales del stock
			//Peticion 55001. Corrección errores del LOG.
			BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibreSuma.getSalaStock());
		    BigDecimal roundedSalaStock = bdSalaStock.setScale(2, BigDecimal.ROUND_HALF_UP);

		    String lCamaraStock = Utilidades.convertirDoubleAString(roundedCamaraStock.doubleValue(),"###0.00").replace('.', ',');
		    String lSalaStock = Utilidades.convertirDoubleAString(roundedSalaStock.doubleValue(),"###0.00").replace('.', ',');
		    
//		    if (pdaDatosInventarioLibre.getAviso() != null && !"".equals(pdaDatosInventarioLibre.getAviso())){
//		    	if (!pdaDatosInventarioLibre.getCamaraBandeja().equals(lCamaraBandeja.toString()) ||
//		    			!pdaDatosInventarioLibre.getSalaBandeja().equals(lSalaBandeja.toString()) ||
//		    			!pdaDatosInventarioLibre.getCamaraStock().equals(lCamaraStock) ||
//		    			!pdaDatosInventarioLibre.getSalaStock().equals(lSalaStock)){
//		    		//Si se ha modificado algún dato quitamos el aviso
//		    		InventarioRotativo inventarioRotativo = new InventarioRotativo();
//		    		inventarioRotativo.setCodCentro(pdaDatosInventarioLibre.getCodCentro());
//		    		inventarioRotativo.setCodArticulo(pdaDatosInventarioLibre.getCodArticulo());
//		    		inventarioRotativo.setCodMac(pdaDatosInventarioLibre.getCodMac());
//		    		this.inventarioRotativoService.updateAvisoPda("", inventarioRotativo);
//		    		pdaDatosInventarioLibre.setAviso("");
//		    	}
//		    }
		    pdaDatosInventarioLibre.setCamaraBandeja(lCamaraBandeja.toString());
		    pdaDatosInventarioLibre.setSalaBandeja(lSalaBandeja.toString());
		    pdaDatosInventarioLibre.setCamaraStock(lCamaraStock);
		    pdaDatosInventarioLibre.setSalaStock(lSalaStock);
		}
	}

	private void updateFlgNoGuardarPda(String flgNoGuardar, Long codArt, Long codCentro, String mac) throws Exception{
		
		if (Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_SI.equals(flgNoGuardar) || Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_NO.equals(flgNoGuardar)){
			if (codArt != null && codCentro != null && mac != null){
				if (!"".equals(codArt) && !"".equals(codCentro) && !"".equals(mac)){
					InventarioRotativo inventarioRotativo = new InventarioRotativo();
					inventarioRotativo.setCodCentro(codCentro);
					inventarioRotativo.setCodArticulo(codArt);
					inventarioRotativo.setCodArticuloRela(codArt);
					inventarioRotativo.setCodMac(mac);
					inventarioRotativo.setFlgNoGuardar(Boolean.TRUE.toString().equals(flgNoGuardar) ? Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_SI : Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_NO);
					
					this.inventarioRotativoService.updateFlgNoGuardarPda(flgNoGuardar, inventarioRotativo);
				}
			}
		}
	}
	
	private String obtenerTipoRotacion(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
		
		String tipoRotacion = "";
		
		//Carga de parámetros de búsqueda
		VRotacionRef vRotacionRef = new VRotacionRef();
		vRotacionRef.setCodCentro(pdaDatosInventarioLibre.getCodCentro());
		vRotacionRef.setCodArt(pdaDatosInventarioLibre.getCodArticulo());
		//Es necesario filtrar por grupo1 y grupo2 por temas de rendimiento en la búsquda
		//vRotacionRef.setGrupo1(pdaDatosInventarioLibre.getCodArea());
		//vRotacionRef.setGrupo2(pdaDatosInventarioLibre.getCodSeccion());
		
		VRotacionRef vRotacionRefRes = this.vRotacionRefService.findOne(vRotacionRef);
    	if (vRotacionRefRes != null){
	    	tipoRotacion = vRotacionRefRes.getTipoRotTotal();		
    	}
		
		return tipoRotacion;
	}

}