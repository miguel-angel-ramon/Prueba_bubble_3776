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

import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.util.StackTraceManager;

@Controller
@RequestMapping("/nuevoExclusionVentas")
public class p74NuevoExclusionVentasController {
	private static Logger logger = Logger.getLogger(p73ExclusionVentasController.class);

	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	
	@RequestMapping(value="/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerRef> getAreaData(
			@RequestBody VAgruComerRef vAgruCommerRef,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			return this.vAgruComerRefService.findAll(vAgruCommerRef, null);
		} catch (Exception e) {
		    logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		     throw e;
		}
	}
}