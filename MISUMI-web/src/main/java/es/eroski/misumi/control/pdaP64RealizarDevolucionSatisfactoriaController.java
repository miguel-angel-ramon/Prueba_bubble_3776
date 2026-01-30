package es.eroski.misumi.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.Devolucion;

@Controller
public class pdaP64RealizarDevolucionSatisfactoriaController {

	private static Logger logger = Logger.getLogger(pdaP64RealizarDevolucionSatisfactoriaController.class);

	@RequestMapping(value = "/pdaP64RealizarDevolucionSatisfactoria",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="imprimir", required=false, defaultValue = "S") String imprimir, //indica si hay que imprimir informe PS
			@RequestParam(value="devolucion") String idDevolucion,
			@RequestParam(value="titulo") String titulo,
			@RequestParam(value="referenciaFiltro", required=false, defaultValue = "") String referenciaFiltro,
			@RequestParam(value="proveedorFiltro", required=false, defaultValue = "") String proveedorFiltro,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		
		Devolucion devolucion = new Devolucion();
		devolucion.setDevolucion(new Long(idDevolucion));
		devolucion.setTitulo1(titulo);
		
		model.addAttribute("devolucion", devolucion);
		model.addAttribute("referenciaFiltro",referenciaFiltro);
		model.addAttribute("proveedorFiltro",proveedorFiltro);
		
		String resultado = "pda_p64_realizarDevolucionSatisfactoria";
		
		return resultado;
	}
}
