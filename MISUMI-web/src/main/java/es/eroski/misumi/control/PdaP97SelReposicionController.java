/**
 * 
 */
package es.eroski.misumi.control;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author BICUGUAL
 *
 */
@Controller
@RequestMapping("/pdaP97SelReposicion")
public class PdaP97SelReposicionController {

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {

		return "pda_p97_selReposicion";
	}

}
