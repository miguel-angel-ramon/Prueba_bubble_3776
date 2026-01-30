package es.eroski.misumi.control;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.RegionService;
import es.eroski.misumi.service.iface.VCentrosUsuariosService;


@Controller
@RequestMapping("/multiCentro")
public class p90MultiCentroController {
	

	@Autowired
	private RegionService regionService;

	@Autowired
	private VCentrosUsuariosService vCentrosUsuariosService;

	
	//Obtiene todos los centros de una Region
	@RequestMapping(value="/getCentrosRegion", method = RequestMethod.POST)
	public @ResponseBody List<Centro> loadCentrosRegion/*loadReferencia*/(
			@RequestBody Long codRegion,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		try {
			
			Centro centro = new Centro(); 
			List<Centro> listaZonas = new ArrayList<Centro>();

			centro.setCodRegion(codRegion);
			
			User user = (User) session.getAttribute("user");
			listaZonas = this.vCentrosUsuariosService.findAll(centro, user.getCode());	
			
		
			return listaZonas;
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		
	}
	
	
	//Obtiene todos los centros de una Zona
	@RequestMapping(value="/getCentrosZona", method = RequestMethod.POST)
	public @ResponseBody List<Centro> loadCentrosZona(
			@RequestBody Long codZona,
			HttpSession session, HttpServletResponse response) throws Exception {
		
			try {
			
				Centro centro = new Centro(); 
				List<Centro> listaZonas = new ArrayList<Centro>();
	
				centro.setCodZona(codZona);
				
				User user = (User) session.getAttribute("user");
				listaZonas = this.vCentrosUsuariosService.findAll(centro, user.getCode());	
			
				return listaZonas;
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
		
	}
}