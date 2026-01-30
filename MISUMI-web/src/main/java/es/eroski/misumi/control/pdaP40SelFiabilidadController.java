package es.eroski.misumi.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class pdaP40SelFiabilidadController{

//	private static Logger logger = Logger.getLogger(pdaP40SelFiabilidadController.class);

	@RequestMapping(value = "/pdaP40SelFiabilidad",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		return "pda_p40_selFiabilidad";
	}
}