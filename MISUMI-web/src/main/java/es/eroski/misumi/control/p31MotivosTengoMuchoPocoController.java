package es.eroski.misumi.control;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.service.iface.MotivoTengoMuchoPocoService;


@Controller
@RequestMapping("/motivosTengoMuchoPoco")
public class p31MotivosTengoMuchoPocoController {
	
	private static Logger logger = Logger.getLogger(p31MotivosTengoMuchoPocoController.class);

	@Autowired
	private MotivoTengoMuchoPocoService motivoTengoMuchoPocoService;

	@RequestMapping(value="/loadMotivosTengoMuchoPoco", method = RequestMethod.POST)
	public @ResponseBody MotivoTengoMuchoPocoLista loadMotivosBloqueo(
			@RequestBody MotivoTengoMuchoPoco motivoTengoMuchoPoco,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		MotivoTengoMuchoPocoLista motivoTengoMuchoPocoLista = null;
		
		//Búsqueda de motivos
		try {
			//Carga de la sesion en el objeto de motivos
			motivoTengoMuchoPoco.setIdSesion(session.getId()+ "_MOT_FFPP");
			motivoTengoMuchoPocoLista = this.motivoTengoMuchoPocoService.consultaMotivosTengoMuchoPoco(motivoTengoMuchoPoco);
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		
		return motivoTengoMuchoPocoLista;
	}
}