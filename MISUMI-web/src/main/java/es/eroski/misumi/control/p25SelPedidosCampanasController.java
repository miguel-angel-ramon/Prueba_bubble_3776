package es.eroski.misumi.control;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/selPedidosCampanas")
public class p25SelPedidosCampanasController {
	
	//private static Logger logger = Logger.getLogger(p25SelPedidosCampanasController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
		
		return "p25_selPedidosCampanas";
	}
}