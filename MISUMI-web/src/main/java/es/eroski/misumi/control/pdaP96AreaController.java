package es.eroski.misumi.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class pdaP96AreaController {
	@RequestMapping(value = "/pdaP96AreaSel",method = RequestMethod.GET)
	public String showForm(ModelMap model, 
			HttpSession session, HttpServletRequest request,
			@RequestParam(value="tipoListado") Long tipoListado,
			HttpServletResponse response) {
		
		//Si entramos desde la opcion de menu LISTADO REPO, el tipo de listado sera 1
		session.setAttribute("tipoListado", tipoListado);
		
		String resultado = "pda_p96_areas";
		return resultado;
	}
}
