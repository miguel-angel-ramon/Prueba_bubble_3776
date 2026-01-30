package es.eroski.misumi.control;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosPopupFechaActivacion;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class pdaP19FechaActivacionPopupController extends pdaConsultasController {

	private static Logger logger = Logger.getLogger(pdaP19FechaActivacionPopupController.class);

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
	
	@Resource 
	private MessageSource messageSource;


	@RequestMapping(value = "/pdaP19fechaActivacionPopup", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final Long codArtRel,
			@Valid final String mostrarFFPP,
			@Valid final String origenGISAE,
			@Valid final String fechaActivacion,
			@Valid final String origen) {
		try 
		{
			User user = (User) session.getAttribute("user");
			
			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));

			//Gestionar si el centro es caprabo o no y obtener la descripción 
			//del artículo según el tipo de centro.
			Long codArtCaprabo = codArt;
			String descArt = "";

			if(!user.getCentro().esCentroCaprabo()){
				//Obtenemos la descripción del artículo.
				VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);
				if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null)
				{
					descArt =vDatosDiarioArtResul.getDescripArt();
				}
			}else{
				codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), codArt);
				descArt = utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo);
			}
			
			pdaDatosCab.setDescArtCab(descArt);
			pdaDatosCab.setCodArtCaprabo(codArtCaprabo.toString());
			model.addAttribute("pdaDatosCab", pdaDatosCab);

			PdaDatosPopupFechaActivacion pdaDatosPopupFechaActivacion = new PdaDatosPopupFechaActivacion();
			pdaDatosPopupFechaActivacion.setCodArt(codArt);
			pdaDatosPopupFechaActivacion.setCodArtRel(codArtRel);
			pdaDatosPopupFechaActivacion.setMostrarFFPP(mostrarFFPP);
			pdaDatosPopupFechaActivacion.setOrigenGISAE(origenGISAE);
			pdaDatosPopupFechaActivacion.setFechaActivacion(fechaActivacion);
			pdaDatosPopupFechaActivacion.setOrigen(origen);

			model.addAttribute("pdaDatosPopupFechaActivacion", pdaDatosPopupFechaActivacion);
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return "pda_p19_fechaActivacionPopup";
	}


}