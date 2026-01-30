package es.eroski.misumi.control;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionCatalogoDescripcion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.util.Constantes;

@Controller
@RequestMapping("/devoluciones")
public class p82DevolucionesController {

	@Autowired
	private DevolucionService devolucionesService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false, defaultValue = "0") Long center,
		Map<String, String> model, 
		@RequestParam(required = false, defaultValue = "") String origenPantalla,
		HttpServletResponse response, HttpSession session) {
		/*try{
			List<ExclusionVentas> lstExclusionVentas  = loadWSData(center,session.getId());
		}catch(Exception e){
			logger.error(StackTraceManager.getStackTrace(e));
		}*/

		model.put("origenPantalla", origenPantalla);
		model.put("permisoDevolucionesPCDuplicar", Constantes.PERMISO_DEVOLUCIONES_PC_DUPLICAR);
		model.put("permisoDevolucionesPCEliminar", Constantes.PERMISO_DEVOLUCIONES_PC_ELIMINAR);
		model.put("permisoPCDevolucionesProcedimiento", Constantes.PC_27_DEVOLUCIONES_PROCEDIMIENTO);
		// comprobar si el centro tiene todas las referencias revisadas.
//		model.put("esCentroRevisado", devolucionesService.hayRefsPdtes(session.getId()));

		return "p82_devoluciones";
	}
	
	//Éste método devuelve un código de error, una descripción y una lista 
	//La lista tendrá 4 elementos que representan a los 4 estados de las devoluciones: 1,2,3 y 4
	//Cada elemento de la lista podrá tener X devoluciones asociadas y cada devolución Z líneas
	//que equivalen a cada línea de la tabla que tiene relacionada.
	@RequestMapping(value="/loadDenominacionesDevoluciones",method = RequestMethod.POST)
	public @ResponseBody DevolucionCatalogoDescripcion loadDenominacionesDevoluciones(@RequestBody Devolucion devolucion,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		DevolucionCatalogoDescripcion descripciones = devolucionesService.cargarDenominacionesDevoluciones(devolucion); 
			
		return descripciones;
	}
	
	@RequestMapping(value="/loadEstadoDevoluciones",method = RequestMethod.POST)
	public @ResponseBody DevolucionCatalogoEstado loadEstadoDevoluciones(@RequestBody Devolucion devolucion,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		DevolucionCatalogoEstado descripciones = devolucionesService.cargarEstadoDevoluciones(devolucion); 
			
		return descripciones;
	}
	
	@RequestMapping(value="/loadTotalDevoluciones",method = RequestMethod.POST)
	public @ResponseBody DevolucionCatalogoEstado loadTotalDevoluciones(@RequestBody Devolucion devolucion,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		DevolucionCatalogoEstado descripciones = devolucionesService.cargarAllDevoluciones(devolucion); 
			
		return descripciones;
	}
}
 