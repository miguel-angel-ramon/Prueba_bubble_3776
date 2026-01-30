package es.eroski.misumi.control;

import javax.servlet.http.HttpSession;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.NumeroEtiquetaImpresoraService;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class pdaP03MapNumEtiController {

	private static Logger logger = Logger.getLogger(pdaP03MapNumEtiController.class);

	@Autowired
	private NumeroEtiquetaImpresoraService numeroEtiquetaImpresoraService;
	
	@RequestMapping(value = "/pdaP03ExisteMapNumEti", method = RequestMethod.GET)
	public String showForm(ModelMap model,
						   @RequestParam(value="sufijoPrehueco", required=false) String sufijoPrehueco) {
		
		model.addAttribute("sufijoPrehueco", sufijoPrehueco);
		
		return "pda_p03_existeMapNumEti";
	}

	@RequestMapping(value = "/pdaP03IncNumeroEtiqueta", method = RequestMethod.GET)
	@ResponseBody
	public String incrementarNumeroEtiqueta(
			@RequestParam(value="codArt", required=true) String codArt,
			@RequestParam(value="numEti", required=true) int numeroEtiqueta,
			HttpSession session) {
		//Recuperamos de sesión el map con las referencias y su respectivo
		//número de etiqueta, incrementamos el número de etiqueta recibido en la request,
		//actualizamos el map, volvemos a guardar el map en sesión y 
		//devolvemos el número de etiqueta incrementado en 1 
		
		int resultado = 0;
		User user = (User)session.getAttribute("user");
		try{
			if (codArt != null && !codArt.equals("")){	
				//Incrementamos el número de etiqueta recibido en la request y lo enviamos como respuesta
				resultado = numeroEtiquetaImpresoraService.incNumEtiqueta(codArt, numeroEtiqueta,user.getCentro().getCodCentro(),user.getMac());
			}
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return String.valueOf(resultado);
	}
	
	@RequestMapping(value = "/pdaP03ResetNumeroEtiqueta", method = RequestMethod.GET)
	public String resetNumeroEtiqueta(
			@RequestParam(value="paginaDeVuelta", required=false) String paginaDeVuelta,
			@RequestParam(value="codArt", required=false) String codArt,
			@RequestParam(value="sufijoPrehueco", required=false) String sufijoPrehueco,
			RedirectAttributes redirectAttributes,HttpSession session) {
		
		//Borramos de la sesión el map con todos los números de etiquetas
		//y volvemos a la página de inicio pdawelcome o a la de pdaP12DatosReferencia
		User user = (User)session.getAttribute("user");
		
		// Si se pasa el sufijo con valor, por ejemplo "_PREHUECO", se concatena a la mac para acceder a las tablas de BBDD. (T_MIS_IMPRESION_ETIQUETAS)
		String mac = (sufijoPrehueco!=null && !StringUtils.isEmpty(sufijoPrehueco)?user.getMac()+sufijoPrehueco:user.getMac());
		
		try{
			numeroEtiquetaImpresoraService.removeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),mac);
			numeroEtiquetaImpresoraService.removeNumEtiquetaInSessionEnviados();
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		if (paginaDeVuelta != null && (paginaDeVuelta.equals("pdaP12DatosReferencia") || paginaDeVuelta.equals("pdaP115PrehuecosLineal"))){
			redirectAttributes.addAttribute("codArt", codArt);
			redirectAttributes.addAttribute("greenImpr", "SI");
			
			if (sufijoPrehueco!=null && !StringUtils.isEmpty(sufijoPrehueco)){
				return "redirect:pdaP115PrehuecosLineal.do";
			}else{
				return "redirect:pdaP12DatosReferencia.do";
			}
			
		}else{
			return "redirect:pdawelcome.do";
		}
	}
	
}