package es.eroski.misumi.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.MontajeVegalsa;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP101MontajeAdicionalMAController {
	
	private static Logger logger = Logger.getLogger(pdaP101MontajeAdicionalMAController.class);
	
	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;
	
	@RequestMapping(value = "/pdaP101MontajeAdicionalMA",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="codArt") Long codArt,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		User user = (User) session.getAttribute("user");
		String resultado = "pda_p101_montajeAdicionalMA";
		model.addAttribute("codArt", codArt);
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
		try {
			ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();
			referenciasCentroIC.setCodCentro(user.getCentro().getCodCentro());
			referenciasCentroIC.setCodArt(codArt);
			MontajeVegalsa montajeVegalsa=tPedidoAdicionalService.getPedidosVegalsa(referenciasCentroIC.getCodCentro(), referenciasCentroIC.getCodArt());
			if(montajeVegalsa!=null){
				model.addAttribute("capacidad", montajeVegalsa.getCantidad());
				model.addAttribute("oferta", montajeVegalsa.getOferta());
				model.addAttribute("fechaInicio", Utilidades.formatearFecha_dd_MM_yyyy(montajeVegalsa.getFechaInicio()));
				model.addAttribute("fechaFin", Utilidades.formatearFecha_dd_MM_yyyy(montajeVegalsa.getFechaFin()));
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return resultado;
		
		
	}
	
}
