/**
 * Controller PRINCIPAL de PACKING LIST.
 * Primero, VALIDAR que el CENTRO es CORRECTO.
 * Segundo, distribuir el flujo dependiendo del resultado de la validación.
 *          Si OK, 
 */
package es.eroski.misumi.control.packingList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PdaP111GestionErrorController{

//	private static Logger logger = Logger.getLogger(pdaP106SelPackingListController.class);

	private final String resultadoKO = "/pda/packingList/pda_p111_errorPackingList";

	@RequestMapping(value = "/pdaP111GestionError",method = RequestMethod.GET)
	public String showForm(ModelMap model
						  ,HttpSession session, HttpServletRequest request
						  ,HttpServletResponse response
						  ,@RequestParam("mensajeError") String mensajeError
						  ,@RequestParam("controlVolver") String controlVolver
						  ) {

		model.addAttribute("mensajeError",mensajeError);
		model.addAttribute("controlVolver",controlVolver);
		return resultadoKO;
	}
}