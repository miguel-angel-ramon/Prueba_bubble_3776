package es.eroski.misumi.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionDatosTalla;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.ListadoReposicionService;

@Controller
public class pdaP95DescripcionDetalleController {

	@Autowired
	private ListadoReposicionService listadoReposicionService;

	@RequestMapping(value = "/pdaP95DescripcionDetalle",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="descripcion") String descripcion,
			@RequestParam(value="paginaActual") String paginaActual,
			@RequestParam(value="origen", required=true) String origen,
			@RequestParam(value="pgSubList") String pgSubList,
			@RequestParam(value="codArt", required=false) Long codArt,
			@RequestParam(value="seccion", required=false) String seccion,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception  {		

		String resultado = "pda_p95_descripcionDetalle";

		//Si codigo artículo es distinto de nulo, venimos de los links de
		//las tallas de las prendas. Si es nulo, venimos del link del color.
		if(codArt != null){
			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();
			String codMac = user.getMac();

			Reposicion reposicion = new Reposicion();
			reposicion.setCodArt(codArt);
			reposicion.setCodMac(codMac);
			reposicion.setCodLoc(codCentro);

			ReposicionDatosTalla reposicionDatosTalla = listadoReposicionService.obtenerDatosAdicionalesTalla(reposicion);
			model.addAttribute("reposicionDatosTalla",reposicionDatosTalla);
		}

		model.addAttribute("descripcion", descripcion);
		model.addAttribute("paginaActual", paginaActual);
		model.addAttribute("origen", origen);
		model.addAttribute("pgSubList", pgSubList);
		model.addAttribute("seccion", seccion);

		return resultado;
	}
}
