package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.InformeListadoPescaForm;
import es.eroski.misumi.model.User;

@Controller
@RequestMapping("/p36ListadoPescaMostrador")
public class p36ListadoPescaMostrador {

	@RequestMapping(value = "/getPdf", method = RequestMethod.POST)
	public String getViewAsPdf(
			@ModelAttribute InformeListadoPescaForm informePescaMostradorForm,			
			ModelMap model,
			HttpSession session) {

		List<String> lista = new ArrayList<String>();
		
		// put stuff in your model
		User userSession = (User)session.getAttribute("user");
		if (informePescaMostradorForm != null && informePescaMostradorForm.getListaPescaMostrador() != null) {
			lista = informePescaMostradorForm.getListaPescaMostrador();
		}

	    model.addAttribute("listaPescaMostrador",lista);
	    model.addAttribute("flgHabitual",informePescaMostradorForm.getFlgHabitual());
	    
	    return "pdfInformePescaView";
	}
	
}