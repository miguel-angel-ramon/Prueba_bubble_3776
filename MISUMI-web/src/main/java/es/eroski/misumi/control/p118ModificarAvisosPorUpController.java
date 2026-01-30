package es.eroski.misumi.control;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.AvisosSiec;
import es.eroski.misumi.service.iface.AvisosSiecService;

@Controller
@RequestMapping("/gestionAvisosSiec/popup")
public class p118ModificarAvisosPorUpController {
	@Autowired
	private AvisosSiecService avisosSiecService;

	//Es la recarga del grid, al paginar o reordenar el grid
	@RequestMapping(value = "/loadAvisosSiec", method = RequestMethod.GET)
	public  @ResponseBody AvisosSiec loadDataGrid(
			@RequestParam(value = "codAviso", required = true) String codAviso,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		try{
			return avisosSiecService.findAvisoSiec(codAviso);
		}catch(Exception e){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}
}
