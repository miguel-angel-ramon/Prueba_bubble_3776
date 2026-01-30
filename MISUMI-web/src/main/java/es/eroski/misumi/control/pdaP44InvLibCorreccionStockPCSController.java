package es.eroski.misumi.control;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import es.eroski.misumi.model.InventarioRotativo;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.service.iface.InventarioRotativoService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP44InvLibCorreccionStockPCSController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP44InvLibCorreccionStockPCSController.class);
	
	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private InventarioRotativoService inventarioRotativoService;
	
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	public PdaArticulo recargarValoresPantalla(PdaArticulo pdaArticulo, HttpServletRequest request) {
		
		Locale locale = LocaleContextHolder.getLocale();
		boolean existeError = false;
		
		//Comprobamos el formato.
		try{
			pdaArticulo.setUnidades(new BigInteger(request.getParameter("pda_p44_fld_bandejas_pantalla")));
			pdaArticulo.setDescripcionError("");
		}
		catch(Exception e) {
			pdaArticulo.setDescripcionError(this.messageSource.getMessage(
					"pda_p44_correccionStockPCS.formatoBandeja", null, locale));
			existeError = true;
		}
		
		if (!existeError){
			try{
				//Aceptamos que venga separado tanto por comas como por puntos.
				Double stockDouble = Double.parseDouble(request.getParameter("pda_p44_fld_stock_pantalla").replace(',', '.'));
	
				pdaArticulo.setStock(request.getParameter("pda_p44_fld_stock_pantalla").replace(',', '.'));
				pdaArticulo.setDescripcionError("");
			}
			catch(Exception e) {
				pdaArticulo.setDescripcionError(this.messageSource.getMessage(
						"pda_p44_correccionStockPCS.formatoStock", null, locale));
			}
		}

		return pdaArticulo;
	}

	public PdaArticulo validacionesBandejas(int posicion, PdaArticulo pdaArticuloActual, PdaArticulo pdaArticulo,
			HttpSession session) {
		
		Locale locale = LocaleContextHolder.getLocale();
		boolean existeError = false;
		
		try 
		{
			
			if (pdaArticuloActual.getUnidades() != pdaArticulo.getUnidades())
			{
				
				pdaArticuloActual.setUnidades(pdaArticulo.getUnidades());
				pdaArticuloActual.setDescripcionError("");
				
			}
			else
			{
				//Si no se ha modificado ningún campo y había algún error, lo quitamos.
				pdaArticuloActual.setDescripcionError("");
			}
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		if (existeError){
			pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
					"pda_p44_correccionStockPCS.formatoBandeja", null, locale));
		}else{
			pdaArticuloActual.setDescripcionError("");	
		}
		
		return pdaArticuloActual;
	}

	public PdaArticulo validacionesStock(int posicion, PdaArticulo pdaArticuloActual, PdaArticulo pdaArticulo,
			HttpSession session) {
		
		Locale locale = LocaleContextHolder.getLocale();
		boolean existeError = false;
		
		try 
		{
			
			if (!pdaArticuloActual.getStock().equals(pdaArticulo.getStock()))
			{
				
				//Comprobamos el formato.
				try{
					//Aceptamos que venga separado tanto por comas como por puntos.
					Double stockDouble = Double.parseDouble(pdaArticulo.getStock().replace(',', '.'));


					if (Double.compare(stockDouble, 0.0) < 0){
						pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
								"pda_p44_correccionStockPCS.formatoStock", null, locale));
						existeError = true;
					} else {
						pdaArticuloActual.setStock(pdaArticulo.getStock());
						pdaArticuloActual.setDescripcionError("");
					}
					
				}
				catch(Exception e) {
					pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
							"pda_p44_correccionStockPCS.formatoStock", null, locale));
					existeError = true;
				}
			}
			else
			{
				//Si no se ha modificado ningún campo y había algún error, lo quitamos.
				pdaArticuloActual.setDescripcionError("");
			}
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		if (existeError){
			pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
					"pda_p44_correccionStockPCS.formatoStock", null, locale));
		}else{
			pdaArticuloActual.setDescripcionError("");	
		}
		
		return pdaArticuloActual;
	}
	
	@RequestMapping(value = "/pdaP44InvLibCorreccionStockPCS", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid final PdaArticulo pdaArticulo,
			HttpServletRequest request,
			HttpServletResponse response) {
		

		PdaArticulo pdaArticuloActual = new PdaArticulo();
		int posicion = 0;
		String resultado = "pda_p44_correccionStockPCS";
		Locale locale = LocaleContextHolder.getLocale();
		String strCodArt = "";
		
		try 
		{
			//Obtención del objeto de articulo actual
			pdaArticuloActual = this.obtenerReferencia(session);
			
			//Control de referencia de cabecera
			if ((request.getParameter("actionIntroRef") != null || pdaDatosCab.getCodArtCab() != null) && null == request.getParameter("actionPrev")){
				//Controlamos que me llega la referencia
				if (pdaDatosCab.getCodArtCab() != null && !pdaDatosCab.getCodArtCab().equals(""))
				{
					if (pdaDatosCab.getCodArtCab().startsWith(Constantes.REF_PISTOLA))
					{
						//Quitamos el primer dígito
						strCodArt = pdaDatosCab.getCodArtCab();
						strCodArt = strCodArt.substring(1,strCodArt.length());
						if (strCodArt.startsWith(Constantes.REF_BALANZA) || strCodArt.startsWith(Constantes.REF_BALANZA_ESPECIAL)){
							//Llamamos al método que nos devuelve la referencia, con los controles, 
							//por si se trata de balanza, etiqueta propia de Eroski, de la tabla EANS o código de referencia normal.
							PdaArticulo pdaArticuloCabecera = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
							Long codArtTratada = pdaArticuloCabecera.getCodArt();
							String codigoError = pdaArticuloCabecera.getCodigoError();	
							if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN))
							{
								pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
										"pda_p44_correccionStockPCS.noExisteReferencia", null, locale));
								model.addAttribute("pdaArticulo", pdaArticuloActual);
								model.addAttribute("pdaDatosCab", pdaDatosCab);
								logger.info("PDA Referencia erronea");
								return "pda_p44_invLibCorreccionStockPCS";
							}else{
								if (!codArtTratada.equals(pdaArticuloActual.getCodArtOrig())){
									pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
											"pda_p30_correccionStockPCS.errorReferenciaNoValida", null, locale));
									model.addAttribute("pdaArticulo", pdaArticuloActual);
									model.addAttribute("pdaDatosCab", pdaDatosCab);
									logger.info("PDA Referencia no introducida");
									return "pda_p44_invLibCorreccionStockPCS";
								}
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
							//Si se ha producido un error con la validación de formato lo mostramos.
							if (pdaArticuloPantalla.getDescripcionError() != null && !pdaArticuloPantalla.getDescripcionError().equals(""))
							{
								pdaArticuloActual.setDescripcionError(pdaArticuloPantalla.getDescripcionError());
								model.addAttribute("pdaArticulo", pdaArticuloActual);
								model.addAttribute("pdaDatosCab", pdaDatosCab);
								logger.info("PDA Error Validacion Corrección stock");
								return "pda_p44_invLibCorreccionStockPCS";
							}
							
							pdaArticuloActual = this.validacionesBandejas(posicion, pdaArticuloActual, pdaArticuloPantalla, session);
							pdaArticuloActual = this.validacionesStock(posicion, pdaArticuloActual, pdaArticuloPantalla, session);
							
							//Si se ha producido un error con la validación lo mostramos.
							if (pdaArticuloActual.getDescripcionError() != null && !pdaArticuloActual.getDescripcionError().equals(""))
							{
								model.addAttribute("pdaArticulo", pdaArticuloActual);
								model.addAttribute("pdaDatosCab", pdaDatosCab);
								logger.info("PDA Error Validacion Corrección stock");
								return "pda_p44_invLibCorreccionStockPCS";
							}

							//Se suma 1 a las bandejas y se actualiza el stock
							//Se guarda el último peso para los cálculos en pantalla
							pdaArticuloActual.setUnidades(pdaArticuloActual.getUnidades().add(new BigInteger("1")));
							double stockActual = new Double(pdaArticuloActual.getStock().replace(',', '.'));
							double stockCabecera = new Double(pdaArticuloCabecera.getKgs().replace(',', '.'));
							double stockSuma = stockActual + stockCabecera;
							pdaArticuloActual.setStock(Utilidades.convertirDoubleAString(stockSuma,"###0.00"));
							pdaArticuloActual.setKgs(Utilidades.convertirDoubleAString(stockCabecera,"###0.00"));
							
							model.addAttribute("pdaArticulo", pdaArticuloActual);
							model.addAttribute("pdaDatosCab", pdaDatosCab);
							resultado = "pda_p44_invLibCorreccionStockPCS";
						
						}else{
							pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
									"pda_p30_correccionStockPCS.errorReferenciaNoValida", null, locale));
							model.addAttribute("pdaArticulo", pdaArticuloActual);
							model.addAttribute("pdaDatosCab", pdaDatosCab);
							logger.info("PDA Referencia no introducida");
							return "pda_p44_invLibCorreccionStockPCS";
						}
					}else{
						pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
								"pda_p44_correccionStockPCS.errorReferenciaNoValida", null, locale));
						model.addAttribute("pdaArticulo", pdaArticuloActual);
						model.addAttribute("pdaDatosCab", pdaDatosCab);
						logger.info("PDA Referencia no introducida");
						return "pda_p44_invLibCorreccionStockPCS";
					}
				}
				else
				{
					//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
					pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
							"pda_p44_correccionStockPCS.referenciaVacia", null, locale));
					model.addAttribute("pdaArticulo", pdaArticuloActual);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia no introducida");
					return "pda_p44_invLibCorreccionStockPCS";
				}
			}
		
			if (request.getParameter("actionSave") != null)
			{
				
				pdaArticuloActual = this.validacionesBandejas(posicion, pdaArticuloActual, pdaArticulo, session);
				pdaArticuloActual = this.validacionesStock(posicion, pdaArticuloActual, pdaArticulo, session);
				
				//Si se ha producido un error con la validación lo mostramos.
				if (pdaArticuloActual.getDescripcionError() != null && !pdaArticuloActual.getDescripcionError().equals(""))
				{
					model.addAttribute("pdaArticulo", pdaArticuloActual);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Error Validacion Corrección stock");
					return "pda_p44_invLibCorreccionStockPCS";
				}
			}
			
			if (request.getParameter("actionSave") != null){
				pdaArticuloActual = this.guardarRegistro(pdaDatosCab, pdaArticuloActual, session);
				
				if (pdaArticuloActual.getCodigoError().equals(Constantes.STOCK_TIENDA_RESULTADO_KO)){
					pdaArticuloActual.setDescripcionError(this.messageSource.getMessage("pda_p44_correccionStockPCS.guardadoError", null, locale));
					
					model.addAttribute("pdaArticulo", pdaArticuloActual);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					resultado = "pda_p44_invLibCorreccionStockPCS";
				}else{//OK
					redirectAttributes.addAttribute("codArtAnterior", "anterior");
					resultado = "redirect:pdaP42InventarioLibre.do";
				}
				return resultado;
			}
			
			if (request.getParameter("actionPrev") != null){
				redirectAttributes.addAttribute("codArtAnterior", "anterior");
				resultado = "redirect:pdaP42InventarioLibre.do";
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return resultado;
	}
	
	private PdaArticulo obtenerReferencia(HttpSession session) throws Exception{
		
		
		PdaArticulo pdaArticulo = new PdaArticulo();

		if (session.getAttribute("pdaArticuloStock") != null && session.getAttribute("pdaArticuloStock") != null)
		{
			pdaArticulo = (PdaArticulo) session.getAttribute("pdaArticuloStock");
		}
		
		return pdaArticulo;
	}
	
	private PdaArticulo guardarRegistro(PdaDatosCabecera pdaDatosCabecera, PdaArticulo pdaArticuloActual, HttpSession session) throws Exception {
		
		try{
			//Carga de referencias cambiadas
			if (pdaArticuloActual != null){
				User user = (User) session.getAttribute("user");
				Long codCentro = user.getCentro().getCodCentro();
				String mac = user.getMac();
				
				InventarioRotativo inventarioRotativo = new InventarioRotativo();
				inventarioRotativo.setCodArticulo(pdaArticuloActual.getCodArtOrig());
				inventarioRotativo.setCodArticuloRela(pdaArticuloActual.getCodArt());
				inventarioRotativo.setCodCentro(codCentro);
				if (null != pdaArticuloActual.getOrigenGISAE() && pdaArticuloActual.getOrigenGISAE().equals("SI")){
					inventarioRotativo.setCodMac("GISAE");
					
				} else {
					inventarioRotativo.setCodMac(mac);
				}
				inventarioRotativo.setOrigenInventario(pdaArticuloActual.getOrigenInventario());
				
				//Redondeo a 2 decimales del stock
				//Peticion 55001. Corrección errores del LOG.
				BigDecimal bdStock = Utilidades.convertirStringABigDecimal(pdaArticuloActual.getStock());
			    BigDecimal roundedStock = bdStock.setScale(2, BigDecimal.ROUND_HALF_UP);
	
				if (Constantes.INVENTARIO_LIBRE_CAMARA_ALMACEN.equals(inventarioRotativo.getOrigenInventario())){
					inventarioRotativo.setCamaraBandeja(new Long(pdaArticuloActual.getUnidades()+""));
					inventarioRotativo.setCamaraStock(roundedStock.doubleValue());
				}else{
					inventarioRotativo.setSalaBandeja(new Long(pdaArticuloActual.getUnidades()+""));
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
				vDatosDiarioArt.setCodArt(pdaArticuloActual.getCodArt());
				vDatosDiarioArt = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

				inventarioRotativo.setCodArea(vDatosDiarioArt.getGrupo1());
				inventarioRotativo.setCodSeccion(vDatosDiarioArt.getGrupo2());

				inventarioRotativo.setFlgNoGuardar(Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_NO);
				inventarioRotativo.setFlgStockPrincipal(Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS);
	        	inventarioRotativo.setFlgUnica(Constantes.INVENTARIO_LIBRE_UNICA_NO);

				this.inventarioRotativoService.insertUpdate(inventarioRotativo);
			}
			pdaArticuloActual.setCodigoError(Constantes.STOCK_TIENDA_RESULTADO_OK);
			pdaArticuloActual.setDescripcionError("");

		}catch (Exception e) {
			pdaArticuloActual.setCodigoError(Constantes.STOCK_TIENDA_RESULTADO_KO);
			pdaArticuloActual.setDescripcionError("");
		}
		
		return pdaArticuloActual;
	}
}