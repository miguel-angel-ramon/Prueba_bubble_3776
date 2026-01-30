package es.eroski.misumi.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class pdaP94ErrorListadoRepoController {
	@RequestMapping(value = "/pdaP94Error",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="error") String error,
			@RequestParam(value="paginaActual") String paginaActual,
			@RequestParam(value="origen", required=true) String origen,
			@RequestParam(value="pgSubList") String pgSubList,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {		
		
		String resultado = "pda_p94_errorListadoRepo";
		
		model.addAttribute("error", error);
		model.addAttribute("paginaActual", paginaActual);
		model.addAttribute("origen", origen);
		model.addAttribute("pgSubList", pgSubList);
		
		return resultado;
	}
}
