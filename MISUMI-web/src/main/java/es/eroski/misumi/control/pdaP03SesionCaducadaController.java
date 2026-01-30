package es.eroski.misumi.control;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.User;

@Controller
public class pdaP03SesionCaducadaController {

	private static Logger logger = Logger.getLogger(pdaP03SesionCaducadaController.class);

	@RequestMapping(value = "/pdaSesionCaducada", method = RequestMethod.GET)
	public String showForm(HttpSession session, HttpServletResponse response) {
		return "pda_p03_sesionCaducada";
	}
	
	@RequestMapping(value = "/pdaSesionCaducada", method = RequestMethod.POST)
	public String processForm(@Valid final User user, BindingResult result,
			Map<String, User> model, HttpSession session,
			HttpServletResponse response) {
		return "pda_p03_sesionCaducada";
	}
}