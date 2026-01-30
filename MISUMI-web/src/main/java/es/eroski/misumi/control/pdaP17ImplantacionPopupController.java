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
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosPopupImplantacion;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class pdaP17ImplantacionPopupController extends pdaConsultasController {

	private static Logger logger = Logger.getLogger(pdaP17ImplantacionPopupController.class);
	
	@Resource 
	private MessageSource messageSource;

	@RequestMapping(value = "/pdaP17implantacionPopup", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final String procede,
			@Valid final String implantacion,
			@Valid final String flgColorImplantacion,
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE) {
		
		try{
			model.addAttribute("origenGISAE", origenGISAE);

			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));

			//Gestionar si el centro es caprabo o no y obtener la descripción 
			//del artículo según el tipo de centro.
			Long codArtCaprabo = codArt;
			String descArt = "";
			
			//Obtenemos la descripción del artículo.
			VDatosDiarioArt vDatosDiarioArtResul = super.obtenerDiarioArt(codArt);
			if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null){
				descArt =vDatosDiarioArtResul.getDescripArt();
			}
			
			pdaDatosCab.setDescArtCab(descArt);
			pdaDatosCab.setCodArtCaprabo(codArtCaprabo.toString());

			model.addAttribute("pdaDatosCab", pdaDatosCab);

			PdaDatosPopupImplantacion pdaDatosPopupImplantacion = new PdaDatosPopupImplantacion();
			pdaDatosPopupImplantacion.setCodArt(codArt);
			pdaDatosPopupImplantacion.setProcede(procede);
			pdaDatosPopupImplantacion.setImplantacion(implantacion);
			pdaDatosPopupImplantacion.setFlgColorImplantacion(flgColorImplantacion);

			model.addAttribute("pdaDatosPopupImplantacion", pdaDatosPopupImplantacion);

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return "pda_p17_implantacionPopup";
	}

}