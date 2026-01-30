package es.eroski.misumi.control;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaCatalogo;
import es.eroski.misumi.model.EntradasCatalogoEstado;
import es.eroski.misumi.service.iface.EntradasDescentralizadasService;

@Controller
@RequestMapping("/entradasDescentralizadas")
public class p101EntradasDescentralizadasController {
	private static Logger logger = Logger.getLogger(p101EntradasDescentralizadasController.class);

	@Autowired
	private EntradasDescentralizadasService entradasDescentralizadasService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false, defaultValue = "0") Long center,
			Map<String, String> model, 
			@RequestParam(required = false, defaultValue = "") String origenPantalla,
			HttpServletResponse response, HttpSession session) {
		model.put("origenPantalla", origenPantalla);
		return "p101_entradasDescentralizadas";
	}
	
	@RequestMapping(value="/loadDenominacionesEntradasDescentralizadas",method = RequestMethod.GET)
	public @ResponseBody EntradaCatalogo loadDenominacionesEntradasDescentralizadas(
			@RequestParam(value = "codLoc", required = true) Long codLoc,
			@RequestParam(value = "flgHistorico", required = true) String flgHistorico,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		EntradaCatalogo entradaCombo = entradasDescentralizadasService.cargarDenominacionesEntradasDescentralizadas(codLoc,flgHistorico); 
			
		return entradaCombo;
	}
	
	@RequestMapping(value="/loadEstadoEntradasDescentralizadas",method = RequestMethod.POST)
	public @ResponseBody EntradasCatalogoEstado loadEstadoEntradas(
			@RequestBody Entrada entrada,
			@RequestParam(value = "codArticulo", required = false) Long codArticulo,
			@RequestParam(value = "flgHistorico", required = true) String flgHistorico,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		EntradasCatalogoEstado entradasCatalogoEstado = entradasDescentralizadasService.loadEstadoEntradasDescentralizadas(entrada,codArticulo,flgHistorico); 
			
		return entradasCatalogoEstado;
	}
}
