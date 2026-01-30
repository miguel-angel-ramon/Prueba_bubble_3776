package es.eroski.misumi.control;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.CestasNavidadArticulo;
import es.eroski.misumi.service.iface.CestasNavidadService;

@Controller
@RequestMapping("/parametrizacionCestas/popup")
public class p105ModificarLotePorUpController {
	@Autowired
	private CestasNavidadService cestasNavidadService;

	//Es la recarga del grid, al paginar o reordenar el grid
	@RequestMapping(value = "/loadArticulosCesta", method = RequestMethod.GET)
	public  @ResponseBody List<CestasNavidadArticulo> loadDataGrid(
			@RequestParam(value = "codArtLote", required = true) Long codArtLote,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		try{
			return cestasNavidadService.findAllCestasNavidadArticulo(codArtLote);}
		catch(Exception e){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}
}
