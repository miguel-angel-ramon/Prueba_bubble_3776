package es.eroski.misumi.control;

import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pdaP22Capacidad")
public class pdaP22CapacidadController {

	private static Logger logger = Logger.getLogger(pdaP22CapacidadController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
		
		return "pda_p22_capacidad";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processForm(Map<String, String> model) {
		
		return "pda_p22_capacidad";
	}
}