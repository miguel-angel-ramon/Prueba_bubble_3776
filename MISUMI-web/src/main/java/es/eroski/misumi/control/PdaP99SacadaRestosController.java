/**
 * 
 */
package es.eroski.misumi.control;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.model.pda.PdaSacadaRestosForm;
import es.eroski.misumi.service.iface.SacadaRestosService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;

/**
 * @author BICUGUAL
 *
 */
@Controller
public class PdaP99SacadaRestosController extends pdaConsultasController {
	
	private static Logger logger = Logger.getLogger(PdaP99SacadaRestosController.class);

	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	SacadaRestosService sacadaRestosService;
	
	@Autowired
	SacadaRestosService datosRefController;
	
	@RequestMapping(value = "/pdaP99SacadaRestos", method = RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "codArt", required = false) Long codArt,
			@Valid final String guardadoStockOk,
			@Valid final String guardadoSfm,
			@Valid final String guardadoImc,
			HttpSession session,
			ModelMap model) {
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		PdaError pdaError = new PdaError();
		PdaSacadaRestosForm pageData=new PdaSacadaRestosForm();
		Locale locale = LocaleContextHolder.getLocale();
		try {
			if(codArt!=null){
				pageData = sacadaRestosService.getPageData(codCentro, codArt, session, guardadoStockOk);
				if(pageData==null || pageData.getTotalPages()==0){
					pdaError.setDescError(this.messageSource.getMessage(
							"pda_p12_datosReferencia.noExisteReferencia", null, locale));
					model.addAttribute("pdaError", pdaError);
				}
			}
			model.addAttribute("pageData", pageData);
			model.addAttribute("guardadoStockOk", guardadoStockOk);
			model.addAttribute("guardadoSfm", guardadoSfm);
			model.addAttribute("guardadoImc", guardadoImc);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
		

		return "pda_p99_sacada_restos";
	}

	@RequestMapping(value = "/pdaP99SacadaRestos", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result, ModelMap model,
			HttpSession session, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
			redirectAttributes.addAttribute("codArt", pdaArticulo.getCodArt());
			//return "pda_p98_captura_restos";
			
			String codigoError = pdaArticulo.getCodigoError();

			if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN)){
				PdaError pdaError = new PdaError();
				pdaError.setDescError(this.messageSource.getMessage(
						"pda_p12_datosReferencia.noExisteReferencia", null, LocaleContextHolder.getLocale()));

				logger.info("PDA Referencia erronea");
				
				model.addAttribute("pdaError", pdaError);
				//model.addAttribute("pdaArticulo", new PdaArticulo());
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				
				return "pda_p99_sacada_restos";
			}
			
			
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		//return "pda_p98_captura_restos";
		return "redirect:pdaP99SacadaRestos.do";
	}
	
	@RequestMapping(value = "/pdaP99FFPPActivo", method = RequestMethod.GET)
	public String ffppActivo(@RequestParam(value = "codArt", required = false) Long codArt, 
			RedirectAttributes redirectAttributes) {
				redirectAttributes.addAttribute("codArt",codArt);
				return "redirect:pdaP99SacadaRestos.do";
	
	}

}
