package es.eroski.misumi.control;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/selPedidosMostrador")
public class p70SelPedidosMostradorController {

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
		
		return "p70_selPedidosMostrador";
	}
}