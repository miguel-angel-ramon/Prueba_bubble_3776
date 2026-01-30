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
import es.eroski.misumi.model.pda.PdaDatosPopupFechaActivacion;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class pdaP19FechaActivacionPopupCapraboController extends pdaConsultasController {

	private static Logger logger = Logger.getLogger(pdaP19FechaActivacionPopupCapraboController.class);

	@Resource 
	private MessageSource messageSource;
	

	@RequestMapping(value = "/pdaP19fechaActivacionPopupCaprabo", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final Long codArtRel,
			@Valid final String fechaActivacion) {
		
		
		try 
		{
			
			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));
			
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();

			//Obtenemos la descripción del artículo.
			String descArt = "";
			ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			
			referenciasCentro.setCodArtCaprabo(codArt);
			referenciasCentro.setCodCentro(codCentro);
			TMisMcgCaprabo tMisMcgCapraboResult = obtenerMcgCaprabo(referenciasCentro);
			descArt = tMisMcgCapraboResult.getDescripArt();
			pdaDatosCab.setDescArtCab(descArt);
			model.addAttribute("pdaDatosCab", pdaDatosCab);

			PdaDatosPopupFechaActivacion pdaDatosPopupFechaActivacion = new PdaDatosPopupFechaActivacion();
			pdaDatosPopupFechaActivacion.setCodArt(codArt);
			pdaDatosPopupFechaActivacion.setCodArtRel(codArtRel);
			pdaDatosPopupFechaActivacion.setFechaActivacion(fechaActivacion);
			
			model.addAttribute("pdaDatosPopupFechaActivacion", pdaDatosPopupFechaActivacion);
		
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return "pda_p19_fechaActivacionPopupCaprabo";
	}
	
	
}