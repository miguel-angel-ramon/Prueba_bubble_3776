package es.eroski.misumi.control.prehuecos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//import es.eroski.misumi.model.PedidoPda;
//import es.eroski.misumi.model.PlanogramaVigente;
//import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosReferencia;
import es.eroski.misumi.model.pda.prehuecos.PrehuecosLineal;
import es.eroski.misumi.service.prehuecos.iface.PrehuecosLinealService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class PdaP114PrehuecosController {

	private static Logger logger = Logger.getLogger(PdaP114PrehuecosController.class);

	private final String PREHUECOS = "/pda/prehuecos/pda_p114_preHuecos";
	private final String PREHUECOS_SEL_OPERATIVA = "/pda/prehuecos/pda_p114_preHuecosSelOperativa";
	private final String PREHUECOS_LINEAL = "/pda/prehuecos/pda_p115_preHuecosLineal";

	@Autowired
	private PrehuecosLinealService prehuecosLinealService;

	@RequestMapping(value = "/pdaP114PrehuecosLineal", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		model.addAttribute("pdaDatosCab", pdaDatosCab);

		return PREHUECOS;
	}

	@RequestMapping(value = "/pdaP114PrehuecosSelOperativa", method = RequestMethod.GET)
	public String showForm(ModelMap model, 
			@Valid final String operativa,
			@Valid final Long codArt,
			@Valid final String guardadoStockOk,
			@Valid final String guardadoSfm,
			@Valid final String guardadoImc,
			@Valid final String centroParametrizado,
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE,
			@RequestParam(value="impresoraWS", required=false, defaultValue = "NO") String impresoraWS,
			@RequestParam(value="etiqueta", required=false, defaultValue = "false") boolean etiqueta,
			@RequestParam(value="greenLink", required=false, defaultValue = "false") boolean greenLink,
			@RequestParam(value="greenImpr", required=false, defaultValue = "NO") String greenImpr,
			@RequestParam(value="cleanAll", required=false, defaultValue = "N") String cleanAll,
			@RequestParam(value="sufijoPrehueco", required=false) String sufijoPrehueco,
			final Long codArtRel,
			final String mostrarFFPP,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = PREHUECOS_LINEAL;
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		PdaDatosReferencia pdaDatosRef = new PdaDatosReferencia();

		if (origenGISAE.equals("SI")){
			pdaDatosCab.setOrigenGISAE(origenGISAE);
		}
		model.addAttribute("origenGISAE", origenGISAE);
		
		try {

			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();

			// Si se pasa el sufijo con valor, por ejemplo "_PREHUECO", se concatena a la mac para acceder a las tablas de BBDD. (T_MIS_IMPRESION_ETIQUETAS)
			String mac = (sufijoPrehueco!=null && !StringUtils.isEmpty(sufijoPrehueco)?user.getMac()+sufijoPrehueco:user.getMac());

			//Tratamiento de secciones
			PrehuecosLineal prehuecosLineal = new PrehuecosLineal();
			prehuecosLineal.setCodCentro(codCentro);
			prehuecosLineal.setMac(mac);

			// Si tengo que volver a la anterior pantalla de PREHUECOS.
			if (operativa != null){
					
				if (Constantes.VOLVER.equalsIgnoreCase(operativa)) {
					resultado = PREHUECOS;
				}else{

					// Comprobación de si existen referencias pendiente de validar (estado != 2)
					// , para mostrar la opción de continuar o empezar de cero.
					// Si NO existen datos cargados en T_MIS_PREHUECOS_LINEAL, iré a la pantalla normal
					// (en la que se indica la referencia a buscar)
					if (prehuecosLinealService.getPrehuecosLineal(prehuecosLineal)>0){

						resultado = PREHUECOS_SEL_OPERATIVA;

						// Si se viene del menu en el que se selecciona "Prehueco Lineal" y existe prehuecos pendientes de validar.
						if (Constantes.SELECCIONAR.equalsIgnoreCase(operativa)){
							resultado = PREHUECOS_SEL_OPERATIVA;
						// Si "Empezar desde cero." --> BORRAR los registros de la tabla T_MIS_PREHUECOS_LINEAL.
						} else {
							if (Constantes.EMPEZAR.equalsIgnoreCase(operativa)) {
								// Borrar los registros de la tabla "T_MIS_PREHUECOS_LINEAL".
								this.prehuecosLinealService.deletePrehuecos(prehuecosLineal);
							}
							// Si "Continuar" --> Recuperar los datos almacenados en BBDD (T_MIS_PREHUECOS_LINEAL).
//							resultado = PREHUECOS_LINEAL;
							return "redirect:pdaP115PrehuecosLineal.do?cleanAll=N&prehuecoSeleccionado=S";

						}
					}

				}
			}

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("pdaDatosRef", pdaDatosRef);

		return resultado;
	}

	@RequestMapping(value = "/pdaP114PrehuecosSelOperativa",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return PREHUECOS;
	}

}
