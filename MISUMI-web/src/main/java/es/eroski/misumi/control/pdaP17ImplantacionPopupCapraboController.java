package es.eroski.misumi.control;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosPopupImplantacion;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class pdaP17ImplantacionPopupCapraboController extends pdaConsultasController {

	private static Logger logger = Logger.getLogger(pdaP17ImplantacionPopupCapraboController.class);

	@Resource 
	private MessageSource messageSource;
	

	@RequestMapping(value = "/pdaP17implantacionPopupCaprabo", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final String implantacion,
			@Valid final String flgColorImplantacion) {
		
		
		try 
		{
			
			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));
			
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();
			
			//Obtenemos la descripción del artículo.
			ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			
			referenciasCentro.setCodArtCaprabo(codArt);
			referenciasCentro.setCodCentro(codCentro);
			TMisMcgCaprabo tMisMcgCapraboResult = obtenerMcgCaprabo(referenciasCentro);
			
			pdaDatosCab.setDescArtCab(tMisMcgCapraboResult.getDescripArt());
			model.addAttribute("pdaDatosCab", pdaDatosCab);

			PdaDatosPopupImplantacion pdaDatosPopupImplantacion = new PdaDatosPopupImplantacion();
			pdaDatosPopupImplantacion.setCodArt(codArt);
			pdaDatosPopupImplantacion.setImplantacion(implantacion);
			pdaDatosPopupImplantacion.setFlgColorImplantacion(flgColorImplantacion);
			
			model.addAttribute("pdaDatosPopupImplantacion", pdaDatosPopupImplantacion);
		
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return "pda_p17_implantacionPopupCaprabo";
	}
	
	
}