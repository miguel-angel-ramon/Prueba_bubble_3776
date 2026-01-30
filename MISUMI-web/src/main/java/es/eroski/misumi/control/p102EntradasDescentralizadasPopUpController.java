package es.eroski.misumi.control;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaCatalogo;
import es.eroski.misumi.model.EntradaFinalizar;
import es.eroski.misumi.model.EntradasCatalogoEstado;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.EntradasDescentralizadasService;

@Controller
@RequestMapping("/entradasDescentralizadas/popup")
public class p102EntradasDescentralizadasPopUpController {
	private static Logger logger = Logger.getLogger(p102EntradasDescentralizadasPopUpController.class);

	@Autowired
	private EntradasDescentralizadasService entradasDescentralizadasService;

	@RequestMapping(value="/loadCabeceraEntradasDescentralizadas",method = RequestMethod.POST)
	public @ResponseBody EntradasCatalogoEstado loadCabeceraEntradasDescentralizadas(
			@RequestBody Entrada entrada,
			@RequestParam(value = "codArticulo", required = false) Long codArticulo,
			@RequestParam(value = "flgHistorico", required = true) String flgHistorico,
			HttpSession session,HttpServletResponse response) throws Exception {

		EntradasCatalogoEstado entradasCatalogoEstado = entradasDescentralizadasService.loadCabeceraEntradas(entrada, codArticulo, flgHistorico ); 

		return entradasCatalogoEstado;
	}

	@RequestMapping(value="/finalizarTareaDeEntrada",method = RequestMethod.POST)
	public @ResponseBody EntradaFinalizar finalizarTareaDeEntrada(@RequestBody Entrada entrada,
			HttpSession session,HttpServletResponse response) throws Exception {

		return entradasDescentralizadasService.finalizarEntrada(entrada); 
	}


	@RequestMapping(value = "/imprimirEntradaDescentralizada", method = RequestMethod.GET)
	public String getViewAsPdfOrdenRecogida( @RequestParam(value = "codLoc", required = true) Long codLoc,  
			@RequestParam(value = "codCabPedido", required = true) Long codCabPedido, 
			ModelMap model,HttpServletResponse response,
			HttpSession session) throws Exception{
		logger.info("Imprimir entrada descentralizada ->");
		Entrada entrada = new Entrada();
		entrada.setCodLoc(codLoc);
		entrada.setCodCabPedido(codCabPedido);
		EntradaCatalogo entradaCatalogo = entradasDescentralizadasService.cargarAllLineasEntrada(entrada); 
		logger.info("entradaCatalogo: "+entradaCatalogo);

		if (entradaCatalogo.getCodError() == 0) { //No ha habido error
			if (entradaCatalogo.getLstEntrada() != null && entradaCatalogo.getLstEntrada().size() > 0) {
				model.addAttribute("entrada", entradaCatalogo.getLstEntrada().get(0));
				User userSession = (User)session.getAttribute("user");
			}
		} else {
			//TODO hay un error
			logger.error("Error al pintar el pdf [" + entradaCatalogo.getCodError() + "] Error: " + entradaCatalogo.getDescError());
		}
		return "pdfInformeEntradasDescentralizadasView";

		//		Devolucion devolucion = new Devolucion();
		//		devolucion.setLocalizador(localizador);
		//
		//		devolucion.setFlagHistorico(flgHistorico);
		//
		//		User userSession = (User)session.getAttribute("user");
		//		devolucion.setCentro(userSession.getCentro().getCodCentro());
		//
		//
		//		//Obtiene una devolución con sus líneas de devolución
		//		DevolucionCatalogoEstado devolucionCatalogo = devolucionesService.cargarAllDevoluciones(devolucion); 
		//
		//		//Se mira que existe la lista de líneas de devolución
		//		if(devolucionCatalogo != null){
		//			if(devolucionCatalogo.getListDevolucionEstado() != null && devolucionCatalogo.getListDevolucionEstado().size() > 0){
		//				if(devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
		//					devolucion = devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().get(0);
		//
		//					if(devolucion.getDevLineas() != null && devolucion.getDevLineas().size() > 0){																							
		//
		//						//Se obtiene la lista de líneas de devolución
		//						List<DevolucionLinea> listDevolucionLinea = (List<DevolucionLinea>) devolucion.getDevLineas();
		//
		//						// Cuando hacemos la primera búsqueda eliminamos siempre los
		//						// registros guardados anteriormente. Se eliminarán todos los
		//						//registros de todas las sesiones que lleven más de un día en
		//						//la tabla.
		//						this.eliminarTablaSesionHistorico();
		//
		//						// Insertar tabla temporal T_DEVOLUCIONES
		//						// A partir de la lista obtenida tenemos que insertar en la
		//						// tabla temporal los registros obtenidos,
		//						// borrando previamente los posibles registros almacenados.
		//						this.eliminarTablaSesion(session.getId());
		//
		//						//Se inserta en una tabla temporal cada línea de devolución. Se devuelve una lista de TDevolucionLinea tratada
		//						//a partir de la lista obtenida en el PLSQL. Así evitamos tirar de la tabla temporal al ya tener los datos.
		//						this.insertarTablaSesionLineaDevolucion(listDevolucionLinea, session.getId(),devolucion.getDevolucion());																       		
		//
		//					}
		//
		//				}
		//			}
		//		}
		//
		//
		//
		//		if (devolucion != null){
		//			model.addAttribute("devolucion", devolucion);
		//		}
		//
		//		if (localizador != null){
		//			model.addAttribute("idSession", localizador);
		//		}
		//
		//
		//
		//		if (session.getId() != null){
		//			model.addAttribute("idSession", session.getId());
		//		}

		//		return "PdfInformeDevolucionOrdenRecogidaView";
	}
}
