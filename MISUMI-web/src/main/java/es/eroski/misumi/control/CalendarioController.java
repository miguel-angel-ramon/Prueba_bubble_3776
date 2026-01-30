package es.eroski.misumi.control;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.service.iface.DiasServicioService;

@Controller
@RequestMapping("/calendario")
public class CalendarioController {
	
	private static Logger logger = Logger.getLogger(CalendarioController.class);
	
	@Autowired
	private DiasServicioService diasServicioService;
	
	@RequestMapping(value = "/cargarDiasServicio", method = RequestMethod.POST)
	public  @ResponseBody DiasServicio cargarDiasServicio(
			@RequestBody DiasServicio diasServicio,
			HttpServletResponse response,
			HttpSession session
			) throws Exception{

		DiasServicio diasServicioRes = null;	
		try {
			diasServicioRes = this.diasServicioService.cargarDiasServicio(diasServicio, session.getId(), null, session);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	    return diasServicioRes;
	}

	@RequestMapping(value = "/obtenerDiasServicio", method = RequestMethod.POST)
	public  @ResponseBody List obtenerDiasServicio(
			@RequestBody DiasServicio diasServicio, 
			HttpServletResponse response,
			HttpSession session
			) throws Exception{
		
			return this.diasServicioService.obtenerDiasServicio(diasServicio, session.getId());
	}
}