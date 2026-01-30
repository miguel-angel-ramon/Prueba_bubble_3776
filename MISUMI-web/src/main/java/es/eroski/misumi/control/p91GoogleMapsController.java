package es.eroski.misumi.control;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.CentroLocalizacionService;
import es.eroski.misumi.service.iface.VCentrosUsuariosService;
import es.eroski.misumi.util.Constantes;

@Controller
@RequestMapping("/googleMaps")
public class p91GoogleMapsController {

	@Autowired
	private CentroLocalizacionService centroLocalizacionService;

	@Autowired
	private VCentrosUsuariosService vCentrosUsuariosService;
	
	@RequestMapping(value="/obtenerDireccionGoogle",method = RequestMethod.POST)
	public @ResponseBody String obtenerDireccionGoogle(
			HttpSession session,HttpServletResponse response) throws Exception {

		User user = (User)session.getAttribute("user");

		String direccionGoogle = this.centroLocalizacionService.obtenerDireccionGoogle(user.getCentro().getCodCentro());
		return direccionGoogle;
	}

	@RequestMapping(value = "/guardarDireccionGoogle", method = RequestMethod.POST)
	public void guardarDireccionGoogle(
			String direccionGoogle,
			Map<String, String> model, 
			HttpServletResponse response, HttpSession session) throws Exception {

		if(direccionGoogle !=null){		
			User user = (User)session.getAttribute("user");
			
			Centro centroProvincia = vCentrosUsuariosService.findAll(user.getCentro(), user.getCode()).get(0);
			if(user.getCentro().esCentroCaprabo()){
				direccionGoogle = Constantes.CENTRO_CAPRABO + direccionGoogle;
			}else if((Constantes.CENTRO_BALEARES).equals(centroProvincia.getProvincia())){
				direccionGoogle = Constantes.CENTRO_MERCAT + direccionGoogle;
			}else{
				direccionGoogle = Constantes.CENTRO_EROSKI + direccionGoogle;
			}
			user.getCentro().getDireccionCentro().setDireccion(direccionGoogle);
			this.centroLocalizacionService.guardarDireccionGoogle(user.getCentro().getCodCentro(),direccionGoogle);
		}
	}
}
