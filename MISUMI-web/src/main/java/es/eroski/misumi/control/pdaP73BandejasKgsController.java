package es.eroski.misumi.control;

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

import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP73BandejasKgsController {
	
	
	private static Logger logger = Logger.getLogger(pdaP30CorreccionStockPCSController.class);
	
	@Resource 
	private MessageSource messageSource;
	


	
	
	@RequestMapping(value = "/pdaP73BandejasKgsInicio",method = RequestMethod.GET)
	public String showForm(ModelMap model, 
			@Valid final Long codArt,
			@Valid final String descCodArt,
			@Valid final String stockDevuelto,
			@Valid final String stockDevueltoBandejas,
			@Valid final boolean fechaDevolucionPasada,
			@Valid final boolean variosBultos,
			@Valid final String origen, 
			@Valid final Double cantMaximaLin,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "pda_p73_bandejasKgs";
		String origenPantalla = request.getParameter("origenPantalla");
		String selectProv = request.getParameter("selectProv");
		
		model.addAttribute("pdaArticulo", new PdaArticulo());
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
		
		model.addAttribute("codArt", codArt);
		model.addAttribute("descCodArt", descCodArt);
		model.addAttribute("stockDevuelto", stockDevuelto);
		model.addAttribute("stockDevueltoBandejas", stockDevueltoBandejas);
		model.addAttribute("fechaDevolucionPasada", fechaDevolucionPasada);
		model.addAttribute("origen", origen);
		model.addAttribute("cantMaximaLin", cantMaximaLin);
		model.addAttribute("variosBultos", variosBultos);
		model.addAttribute("origenPantalla", origenPantalla);
		model.addAttribute("selectProv", selectProv);
		PdaArticulo articulo = 	new PdaArticulo();
		
		articulo.setCodArt(codArt);
		articulo.setDescArt(descCodArt);
		articulo.setOrigen(origen);
		articulo.setKgs(stockDevuelto);
		articulo.setBandejas(stockDevueltoBandejas);
		articulo.setVariosBultos(variosBultos);
		model.addAttribute("pdaArticulo", articulo);
		session.setAttribute("pdaArticuloStock", articulo);
			
		
		return resultado;
	}
	
	@RequestMapping(value = "/pdaP73BandejasKgsInicio",method = RequestMethod.POST)
	public String processForm(ModelMap model, 
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "pda_p73_bandejasKgs";
		model.addAttribute("pdaArticulo", new PdaArticulo());
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
		return resultado;
	}
	
	@RequestMapping(value = "/pdaP73BandejasKgs", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid final PdaArticulo pdaArticulo,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String origenPantalla = request.getParameter("origenPantalla");
		String selectProv = request.getParameter("selProv");
		
		PdaArticulo pdaArticuloActual = new PdaArticulo();
		int posicion = 0;
		String resultado = "pda_p73_bandejasKgs";
		Locale locale = LocaleContextHolder.getLocale();
		String strCodArt = "";
		
		try{
			
			//Obtención del objeto de articulo actual
			pdaArticuloActual = obtenerReferencia(session);
			
			if (request.getParameter("actionSave") != null){
				
				pdaArticuloActual = this.validacionesBandejas(posicion, pdaArticuloActual, pdaArticulo, session);
				pdaArticuloActual = this.validacionesKgs(posicion, pdaArticuloActual, pdaArticulo, session);
				
				//Si se ha producido un error con la validación lo mostramos.
				if (pdaArticuloActual.getDescripcionError() != null && !pdaArticuloActual.getDescripcionError().equals("")){
					logger.info("PDA Error Validacion Corrección stock");
					return "pda_p30_correccionStockPCS";
				}

				redirectAttributes.addAttribute("codArt", pdaArticuloActual.getCodArt());
				//redirectAttributes.addAttribute("guardadoStockOk", "S");
				if (pdaArticuloActual.getOrigen().equals("DR")){
					resultado = "redirect:pdaP12DatosReferencia.do";
				} else if(pdaArticuloActual.getOrigen().equals("SP")){
					resultado = "redirect:pdaP13SegPedidos.do";
				} else if(pdaArticuloActual.getOrigen().equals("DVFN")){
					resultado = "redirect:pdaP62StockLinkVuelta.do?flgBienGuardado=S&flgOrigenBandejasKgs=S&origenPantalla="+origenPantalla+"&selectProv="+selectProv;
				} else if(pdaArticuloActual.getOrigen().equals("DVOR")){
					resultado = "redirect:pdaP63StockLinkVuelta.do?flgBienGuardado=S&flgOrigenBandejasKgs=S&origenPantalla="+origenPantalla+"&selectProv="+selectProv;
				} else{
					resultado = "redirect:pdaP15MovStocks.do";
				}
				
				session.setAttribute("pdaArticulo", pdaArticuloActual);
				session.setAttribute("pdaDatosCab", pdaDatosCab);
			}
			
			if (request.getParameter("actionPrev") != null){
				redirectAttributes.addAttribute("codArt", pdaArticuloActual.getCodArt());
				if (pdaArticuloActual.getOrigen().equals("DR")){
					resultado = "redirect:pdaP12DatosReferencia.do";
				} else if(pdaArticuloActual.getOrigen().equals("SP")){
					resultado = "redirect:pdaP13SegPedidos.do";
				} else if(pdaArticuloActual.getOrigen().equals("DVFN")){
					resultado = "redirect:pdaP62StockLinkVuelta.do?origenPantalla="+origenPantalla+"&selectProv="+selectProv;
				} else if(pdaArticuloActual.getOrigen().equals("DVOR")){
					resultado = "redirect:pdaP63StockLinkVuelta.do?origenPantalla="+origenPantalla+"&selectProv="+selectProv;
				} else{
					resultado = "redirect:pdaP15MovStocks.do";
				}
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
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
	
	
	public PdaArticulo validacionesBandejas(int posicion, PdaArticulo pdaArticuloActual, PdaArticulo pdaArticulo,
			HttpSession session) {
		
		Locale locale = LocaleContextHolder.getLocale();
		boolean existeError = false;
		
		try{
			if (pdaArticuloActual.getBandejas() != pdaArticulo.getBandejas()){
				pdaArticuloActual.setBandejas(pdaArticulo.getBandejas());
				pdaArticuloActual.setDescripcionError("");
			}else{
				//Si no se ha modificado ningún campo y había algún error, lo quitamos.
				pdaArticuloActual.setDescripcionError("");
			}
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		if (existeError){
			pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
					"pda_p73_bandejasKgs.formatoBandeja", null, locale));
		}else{
			pdaArticuloActual.setDescripcionError("");	
		}
		
		return pdaArticuloActual;
	}

	public PdaArticulo validacionesKgs(int posicion, PdaArticulo pdaArticuloActual, PdaArticulo pdaArticulo,
			HttpSession session) {
		
		Locale locale = LocaleContextHolder.getLocale();
		boolean existeError = false;
		
		try{
			
			if (!pdaArticuloActual.getKgs().equals(pdaArticulo.getKgs())){
				
				//Comprobamos el formato.
				try{
					//Aceptamos que venga separado tanto por comas como por puntos.
					Double kgsDouble = Double.parseDouble(pdaArticulo.getKgs().replace(',', '.'));

					if (Double.compare(kgsDouble, 0.0) < 0){
						pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
								"pda_p73_bandejasKgs.formatoStock", null, locale));
						existeError = true;
					} else {
						pdaArticuloActual.setKgs(pdaArticulo.getKgs());
						pdaArticuloActual.setDescripcionError("");
					}
					
				}catch(Exception e) {
					pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
							"pda_p73_bandejasKgs.formatoKgs", null, locale));
					existeError = true;
				}
				
			}else{
				//Si no se ha modificado ningún campo y había algún error, lo quitamos.
				pdaArticuloActual.setDescripcionError("");
			}
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		if (existeError){
			pdaArticuloActual.setDescripcionError(this.messageSource.getMessage(
					"pda_p73_bandejasKgs.formatoKgs", null, locale));
		}else{
			pdaArticuloActual.setDescripcionError("");	
		}
		
		return pdaArticuloActual;
	}
}