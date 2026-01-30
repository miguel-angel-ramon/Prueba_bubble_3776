package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.CalendarioDiaWarning;
import es.eroski.misumi.model.CalendarioWarnings;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.CalendarioService;
import es.eroski.misumi.util.Utilidades;

@Controller
@RequestMapping("/calendario/popup/warnings")
public class p99CalendarioWarningsController {

	private static Logger logger = Logger.getLogger(p99CalendarioWarningsController.class);

	@Autowired
	private CalendarioService calendarioService;
	
	//Sirve para cargar los warnings del centro
	@RequestMapping(value = "/consultarWarnings", method = RequestMethod.POST)
	public  @ResponseBody CalendarioWarnings consultarWarnings(
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User usuario= (User)session.getAttribute("user");
		Long codCentro = usuario.getCentro().getCodCentro();

		//Obtenemos los warnings
		List<CalendarioDiaWarning> listaWarnings = new ArrayList<CalendarioDiaWarning>();//Pasamos la lista de warnings vacía para indicar que es una consulta
		CalendarioWarnings calWarnings = calendarioService.consultaModificacionWarnings(codCentro, listaWarnings);

		return calWarnings;
	}
	

	//Sirve para eliminar los warnings del centro
	@RequestMapping(value = "/eliminarWarning", method = RequestMethod.POST)
	public  @ResponseBody CalendarioWarnings eliminarWarning(
			@RequestParam(value = "fecha", required = true) String fechaAfectada,	
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,			
			@RequestParam(value = "tipoEjercicio", required = true) String tipoEjercicio,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User usuario= (User)session.getAttribute("user");
		Long codCentro = usuario.getCentro().getCodCentro();

		//Obtenemos los warnings
		List<CalendarioDiaWarning> listaWarnings = new ArrayList<CalendarioDiaWarning>();//Pasamos la lista con el elemento a eliminar
		CalendarioDiaWarning calendarioDiaWarning = new CalendarioDiaWarning();
		calendarioDiaWarning.setFechaAfectada(Utilidades.convertirStringAFecha(fechaAfectada));
		listaWarnings.add(calendarioDiaWarning);
		
		CalendarioWarnings calWarnings = calendarioService.consultaModificacionWarnings(codCentro, listaWarnings);
		
		//Si no hay error, actualizamos el dato de la tabla temporal y le quitamos el flag que hace que se muestre el aviso parpadeando (ponemos eaprobadoCambio a 'S' para ese día)
		if(new Long("0").equals(calWarnings.getCodError())){	
			TCalendarioDia tCalendarioDia = new TCalendarioDia();
			//Actualizamos la tabla temporal.
			tCalendarioDia.setIdSesion(session.getId().toString());
			tCalendarioDia.setCodCentro(codCentro);
			tCalendarioDia.setFechaCalendario(Utilidades.convertirStringAFecha(fechaAfectada));
			tCalendarioDia.setEjercicio(codigoEjercicio);
			tCalendarioDia.setTipoEjercicio(tipoEjercicio);
			
			//Ponemos el flag en S para que no muestre la alerta parpadeante de ahora en adelante
			tCalendarioDia.setEAprobadoCambio("S");
			
			List<TCalendarioDia> tCalendarioDiaLst = new ArrayList<TCalendarioDia>();
			tCalendarioDiaLst.add(tCalendarioDia);

			//Actualizamos el día
			this.calendarioService.updateDiaCalendarioTemporal(tCalendarioDiaLst,null);
		}

		return calWarnings;
	}

}