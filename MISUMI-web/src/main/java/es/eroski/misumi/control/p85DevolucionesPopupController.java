package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;

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

import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionEmail;
import es.eroski.misumi.service.iface.DevolucionLineaBultoCantidadService;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.util.Constantes;

@Controller
@RequestMapping("/devoluciones/popup")
public class p85DevolucionesPopupController {

//	private static Logger logger = Logger.getLogger(p85DevolucionesPopupController.class);

	@Autowired
	private DevolucionService devolucionesService;

	@Autowired
	private DevolucionLineaBultoCantidadService devLineaBultoService;

	@RequestMapping(value="/loadCabeceraDevoluciones",method = RequestMethod.POST)
	public @ResponseBody DevolucionCatalogoEstado loadCabeceraDevoluciones(@RequestBody Devolucion devolucion,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		DevolucionCatalogoEstado descripciones = devolucionesService.cargarCabeceraDevoluciones(devolucion); 

//		List<Devolucion> listDevoluciones = descripciones.getListDevolucionEstado().get(0).getListDevolucion();
//		
//		// Tratamiento para incorporar el flag que comprueba si existen referencias pendientes de validar para la devolucion.
//		List<Devolucion> listNuevaDevol = new ArrayList<Devolucion>();
//		for (Devolucion dev:listDevoluciones){
//			dev.setHayRefsPdtes(devolucionesService.hayRefsPdtes(session.getId()));
//			listNuevaDevol.add(dev);
//		}
//		
//		if (listNuevaDevol != null && !listNuevaDevol.isEmpty() && listNuevaDevol.size()>0){
//			descripciones.getListDevolucionEstado().get(0).setListDevolucion(listNuevaDevol);
//		}
		
		return descripciones;
	}
	
	@RequestMapping(value="/finalizarTareaDeDevolucion",method = RequestMethod.POST)
	public @ResponseBody DevolucionEmail finalizarTareaDeDevolucion(@RequestBody Devolucion devolucion, @RequestParam(value = "flgRellenarHuecos", required = false, defaultValue = "") String flgRellenarHuecos,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		// Si está parametrizado con PC_27_DEVOLUCIONES_PROCEDIMIENTO
		if (devLineaBultoService.isCentroParametrizado(devolucion.getCentro(), Constantes.PC_27_DEVOLUCIONES_PROCEDIMIENTO)){
			String rdoPuedeFinalizar = devolucionesService.puedeFinalizar(devolucion.getCentro(), devolucion.getLocalizador());
			// comprobar si se puede finalizar.
			if (!Constantes.FLG_SIA_SI.equals(rdoPuedeFinalizar)){
				return new DevolucionEmail(null, 99L, rdoPuedeFinalizar);
			}
		}
		
		return devolucionesService.finalizarDevolucion(devolucion,flgRellenarHuecos,"PC"); 
	}
	
	@RequestMapping(value="/finalizarObtenerRellenarHuecos",method = RequestMethod.POST)
	public @ResponseBody String finalizarTareaDeDevolucion(@RequestBody Devolucion devolucion,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		return devolucionesService.devolucionConStockVacio(devolucion);
	}

	@RequestMapping(value="/duplicarDevolucion",method = RequestMethod.POST)
	public @ResponseBody Devolucion duplicarDevolucion(@RequestBody Devolucion devolucion,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		return devolucionesService.duplicarDevolucion(devolucion);
	}

	@RequestMapping(value="/eliminarDevolucion",method = RequestMethod.POST)
	public @ResponseBody Devolucion eliminarDevolucion(@RequestBody Devolucion devolucion,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		return devolucionesService.eliminarDevolucion(devolucion);
	}
}
