package es.eroski.misumi.control;

import java.util.List;

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
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.util.Constantes;

@Controller
public class pdaP61RealizarDevolucionCabeceraController {

	private static Logger logger = Logger.getLogger(pdaP61RealizarDevolucionCabeceraController.class);

	@RequestMapping(value = "/pdaP61RealizarDevolucionCabecera",method = RequestMethod.GET)
	public String realizarDevolucionLinkDetalle(ModelMap model,
			@RequestParam(value="devolucion", required=false, defaultValue = "") String devolucionId,
			@RequestParam(value="origenPantalla", required=false, defaultValue = "") String origenPantalla,
			@RequestParam(value="msgError", required=false, defaultValue = "") String msgError,
			@RequestParam(value="referenciaFiltro", required=false, defaultValue = "") String referenciaFiltro,
			@RequestParam(value="proveedorFiltro", required=false, defaultValue = "") String proveedorFiltro,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		// Llamada para conseguir las devoluciones. 
		Devolucion devolucion = new Devolucion();
		devolucion.setDevolucion(new Long(devolucionId));
		
		//Obtención de la devolución de la lista de devoluciones
		List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
		devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));

		//Obtener los bultos de los proveedores
		List<String> devolucionConBultoPorProveedor = ((TDevolucionLinea) session.getAttribute("devolucionConBultoPorProveedor")).getBultoPorProveedorLst();
		devolucion.setBultoPorProveedorLst(devolucionConBultoPorProveedor);
		
		model.addAttribute("devolucion",devolucion);
		model.addAttribute("origenPantalla",origenPantalla);
		model.addAttribute("msgError",msgError);
		model.addAttribute("referenciaFiltro",referenciaFiltro);
		model.addAttribute("proveedorFiltro",proveedorFiltro);
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());

		if(Constantes.DEVOLUCIONES_ORIGEN_PANTALLA_ORDEN_RETIRADA.equals(origenPantalla)) {
			model.addAttribute("actionP61Volver","pdaP63RealizarDevolucionOrdenDeRetirada.do");
		}else{
			model.addAttribute("actionP61Volver","pdaP62RealizarDevolucionFinCampania.do");
		}

		return "pda_p61_realizarDevolucionCabecera";
	}
}
