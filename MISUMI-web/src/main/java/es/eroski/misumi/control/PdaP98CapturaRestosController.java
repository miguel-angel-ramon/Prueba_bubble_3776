/**
 * 
 */
package es.eroski.misumi.control;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaCapturaRestosForm;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.service.iface.CapturaRestosService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;

/**
 * @author BICUGUAL
 *
 */
@Controller
@RequestMapping("/pdaP98CapturaRestos")
public class PdaP98CapturaRestosController extends pdaConsultasController {
	
	private static Logger logger = Logger.getLogger(PdaP98CapturaRestosController.class);

	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	CapturaRestosService capturaRestosService;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	@Autowired
	CapturaRestosService datosRefController;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "pagina", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "codArt", required = false) Long codArt,
			@RequestParam(value = "flgCapacidadIncorrecta", required = false) String flgCapacidadIncorrecta,
			//viaja cuando vuelve de guardarStock
			@RequestParam(value  = "guardadoStockOk", required = false, defaultValue="N") String guardadoStockOk,
			@RequestParam(value  = "guardadoSfm", required = false) String guardadoSfm,
			@RequestParam(value  = "guardadoImc", required = false) String guardadoImcOk,
			HttpSession session,
			ModelMap model) {
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		
		PdaCapturaRestosForm pageData=null;
		
		try {
			pageData = capturaRestosService.getPageData(codCentro, page, codArt, session, guardadoStockOk, guardadoImcOk, guardadoSfm);
			
			model.addAttribute("pageData", pageData);
			model.addAttribute("guardadoStockOk", guardadoStockOk);
			model.addAttribute("guardadoSfm", guardadoSfm);
			model.addAttribute("guardadoImc", guardadoImcOk);
			
		} catch (Exception e) {
			PdaError pdaError = new PdaError();
			pdaError.setDescError(this.messageSource.getMessage(
					"pda_p12_datosReferencia.noExisteReferencia", null, LocaleContextHolder.getLocale()));

			logger.info("PDA Referencia erronea");
			
			model.addAttribute("pdaError", pdaError);			
		}
		
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());

		return "pda_p98_captura_restos";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result, ModelMap model,
			HttpSession session, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {

		User user = (User) session.getAttribute("user");
		
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
		
		try {
			//Si se insertan datos
			if (pdaDatosCab.getCodArtCab() != null && !pdaDatosCab.getCodArtCab().equals("")){

				PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
				redirectAttributes.addAttribute("codArt", pdaArticulo.getCodArt());
				
				String codigoError = pdaArticulo.getCodigoError();
				
				//Comprueba que la referencia exista despues de obtenerReferecuaTratada
				if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN)){
					throw new Exception(this.messageSource.getMessage("pda_p12_datosReferencia.noExisteReferencia", null,
							LocaleContextHolder.getLocale()));
				}
				
				//Comprueba que la referencia exista
				if (null == vDatosDiarioArtService.findOne(new VDatosDiarioArt(new Long(pdaArticulo.getCodArt()), null))){				
					throw new Exception(this.messageSource.getMessage("pda_p12_datosReferencia.noExisteReferencia", null,
							LocaleContextHolder.getLocale()));
				}
				
				//Comprueba que la referencia exista en VSurtidoTienda (NO MIRAMOS VEGALSA)
				if (null == capturaRestosService.getSurtidoTienda(new Long(pdaArticulo.getCodArt()), user.getCentro(), null)){				
					throw new Exception(this.messageSource.getMessage("pda_p12_datosReferencia.noExisteReferencia", null,
							LocaleContextHolder.getLocale()));
				}
				
			}
			//Si no se ha insertado referencia
			else {
				logger.info("PDA Referencia no introducida");

				//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
				throw new Exception(this.messageSource.getMessage("pda_p12_datosReferencia.referenciaVacia", null,
						LocaleContextHolder.getLocale()));
			}
			
			
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			model.addAttribute("pdaError", new PdaError(null, e.getMessage()));

			return "pda_p98_captura_restos";
		}
		
		return "redirect:pdaP98CapturaRestos.do";
	}

	
	@RequestMapping(value="/capacidadIncorrecta", method = RequestMethod.POST)
	public String updateCapacidadIncorrecta(
			@RequestParam(value = "codArticulo", required = false) Long codArt,
			@RequestParam(value = "flgCapacidadIncorrecta", required = false) String flgCapacidadIncorrecta,
			 RedirectAttributes redirectAttributes, HttpSession session,
			ModelMap model) {
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		
		try {
			logger.info("Cambio FLAG");
			capturaRestosService.updateFlgCapacidadIncorrecta(codCentro, codArt, flgCapacidadIncorrecta);
			
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			model.addAttribute("pdaError", new PdaError(null, e.getMessage()));
			
			return "pda_p98_captura_restos";	
		}
		
		redirectAttributes.addAttribute("codArt", codArt);

		return "redirect:../pdaP98CapturaRestos.do";
	}
	
}
