/**
 * Controller PRINCIPAL de PACKING LIST.
 * Primero, VALIDAR que el CENTRO es CORRECTO.
 * Segundo, distribuir el flujo dependiendo del resultado de la validación.
 *          Si OK, 
 */
package es.eroski.misumi.control.packingList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.packingList.RdoApiPackingKO;
import es.eroski.misumi.model.pda.packingList.RdoValidarCecoOK;
import es.eroski.misumi.model.pda.packingList.RdoValidarCecoWrapper;
import es.eroski.misumi.service.packingList.iface.PaletService;
import es.eroski.misumi.util.Constantes;

@Controller
public class PdaP106PackingListController{

//	private static Logger logger = Logger.getLogger(pdaP106SelPackingListController.class);

//	@Autowired
//	PaletService paletService;

	@Autowired
//	private RestTemplateService restTemplateService;
	private PaletService paletService;

	private final String resultadoOK = "/pda/packingList/pda_p106_packingList";

	@RequestMapping(value = "/pdaP106PackingList",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		RdoValidarCecoOK rdoValidarCecoOK;
		RdoApiPackingKO rdoValidarCecoKO;
		String mensajeError;

		// Validación del CECO y obtención del TOKEN.
//		RdoValidarCecoWrapper rdoValidarCecoWrapper = restTemplateService.validarCeco(codCentro.toString());

		RdoValidarCecoWrapper rdoValidarCecoWrapper = paletService.validarCeco(codCentro.toString());
		rdoValidarCecoOK = rdoValidarCecoWrapper.getRdoValidarCecoOK();
		
		if (Constantes.PACKING_LIST_OK.equalsIgnoreCase(rdoValidarCecoOK.getTipoRespuesta())){
			session.setAttribute(Constantes.X_APP_KEY, rdoValidarCecoOK.getToken());
			return resultadoOK;
		}else{
//			model.addAttribute("mensajeError", rdoValidarCeco.getResultado());
//			return resultadoKO;
			
			rdoValidarCecoKO = rdoValidarCecoWrapper.getRdoValidarCecoKO();
			mensajeError = (rdoValidarCecoKO.getResultado()==null?"":rdoValidarCecoKO.getResultado());
			return "redirect:pdaP111GestionError.do?mensajeError="+mensajeError
					+"&controlVolver=pdawelcome.do";
		}
	}
	
	@RequestMapping(value = "/pdaP106PackingList",method = RequestMethod.POST)
	public String processForm(ModelMap model
							 ,HttpSession session
							 ,HttpServletRequest request
							 ,HttpServletResponse response
							 ) {
		return "pda_p11_welcome";
	}

}