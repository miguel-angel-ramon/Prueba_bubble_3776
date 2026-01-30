package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.CalendarioActualizado;
import es.eroski.misumi.model.CalendarioCambioEstacional;
import es.eroski.misumi.model.CalendarioDia;
import es.eroski.misumi.model.CalendarioTotal;
import es.eroski.misumi.model.CalendarioValidacionTratado;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.CalendarioService;

@Controller
@RequestMapping("/calendario/popup/serviciosTemporales")
public class p98CalendarioServiciosTemporalesController {

	private static Logger logger = Logger.getLogger(p98CalendarioServiciosTemporalesController.class);

	@Autowired
	private CalendarioService calendarioService;

	@Resource
	private MessageSource messageSource;

	//Sirve para cargar los valores del combobox ordenados
	@RequestMapping(value = "/consultarCambiosEstacionales", method = RequestMethod.POST)
	public  @ResponseBody CalendarioCambioEstacional consultarCambiosEstacionales(
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Objeto de respuesta.
		CalendarioCambioEstacional calendarioCambioEstacionalRespuesta = calendarioService.consultarCambiosEstacionales(codCentro,codigoEjercicio);

		return calendarioCambioEstacionalRespuesta;
	}

	//Sirve para cargar los valores del combobox ordenados
	@RequestMapping(value = "/guardarCambioEstacional", method = RequestMethod.POST)
	public  @ResponseBody CalendarioActualizado guardarCambioEstacional(
			@RequestBody CalendarioCambioEstacional calendarioCambioEstacional,
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Objeto de respuesta.
		CalendarioActualizado calendarioActualizado = calendarioService.guardarCambioEstacional(codCentro,codigoEjercicio,calendarioCambioEstacional);

		return calendarioActualizado;
	}

	//Sirve para cargar los valores del combobox ordenados
	@RequestMapping(value = "/actualizarCambiosManualesCalendario", method = RequestMethod.POST)
	public  @ResponseBody CalendarioTotal actualizarCambiosManualesCalendario(
			@RequestBody CalendarioCambioEstacional calendarioCambioEstacional,
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,
			@RequestParam(value="codigoServicio", required=false, defaultValue = "") Long codigoServicio,
			@RequestParam(value = "tipoEjercicio", required = false, defaultValue = "N") String tipoEjercicio,
			@RequestParam(value = "mesAnio", required = false) String mesAnio,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Objeto de respuesta.
		CalendarioTotal calendarioTotalRespuesta;

		//Objeto que guarda los dias del mes a mostrar.
		List<TCalendarioDia> listTCalendarioDiasMes = null;

		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Consultamos el calendario por cada servicio buscado y actualizamos el cambio estacional de su temporal.
		for(Long clave:calendarioCambioEstacional.getListadoValidacionesTratadas().keySet()){
			CalendarioValidacionTratado calValTrat = calendarioCambioEstacional.getListadoValidacionesTratadas().get(clave);

			if(calValTrat.getExisteCambio()){
				CalendarioTotal calTotTodo = calendarioService.consultarCalendario(codCentro,codigoEjercicio,calValTrat.getCodigoServicio(),tipoEjercicio);

				if(calTotTodo != null && calTotTodo.getListadoFecha() != null && calTotTodo.getListadoFecha().size() > 0){
					//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que actualice la tabla temporal.
					List<TCalendarioDia> listTCalendarioDia = new ArrayList<TCalendarioDia>();

					//Definimos el puntero del registro a actualizar la bd
					TCalendarioDia nuevoRegistro = null;

					for (CalendarioDia calDia:calTotTodo.getListadoFecha()){
						//Se inicializa el objeto. Cada objeto de este tipo actualizará un registro de la tabla temporal
						nuevoRegistro = new TCalendarioDia();

						//Se rellena el objeto
						nuevoRegistro.setIdSesion(session.getId());

						nuevoRegistro.setFechaCalendario(calDia.getFechaCalendario());
						nuevoRegistro.setCambioEstacional(calDia.getCambioEstacional() != null ? calDia.getCambioEstacional():"");
						nuevoRegistro.setCodCentro(codCentro);
						nuevoRegistro.setCodServicio(calValTrat.getCodigoServicio());
						nuevoRegistro.setEjercicio(codigoEjercicio);
						nuevoRegistro.setTipoEjercicio(tipoEjercicio);
						
						listTCalendarioDia.add(nuevoRegistro);
					}

					//Actualizamos la tabla temporal pero no decimos que se han modificado los registros porque la BD de SIA ya tiene 
					//esos datos actualizados y es innecesario.
					this.calendarioService.updateDiaCalendarioTemporaCambiosEstacionales(listTCalendarioDia,calValTrat.getCodigoServicio());
				}else{
					//Si hay error devolvemos el error.
					Locale locale = LocaleContextHolder.getLocale();
					return calendarioTotalRespuesta =  new CalendarioTotal(new Long(1),this.messageSource.getMessage("p96_noExistenDatos.error", null,locale), null, null, null, listTCalendarioDiasMes);
				}
			}
		}
		//Al cambiar los cambios estacionales, también estamos cambiando el del calendario global. Por lo que obtenemos del procedimiento los datos del total y actualizamos la temporal.
		CalendarioTotal calTotTodo = calendarioService.consultarCalendario(codCentro,codigoEjercicio,null,tipoEjercicio);

		if(calTotTodo != null && calTotTodo.getListadoFecha() != null && calTotTodo.getListadoFecha().size() > 0){
			//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que actualice la tabla temporal.
			List<TCalendarioDia> listTCalendarioDia = new ArrayList<TCalendarioDia>();

			//Definimos el puntero del registro a actualizar la bd
			TCalendarioDia nuevoRegistro = null;

			for (CalendarioDia calDia:calTotTodo.getListadoFecha()){
				//Se inicializa el objeto. Cada objeto de este tipo actualizará un registro de la tabla temporal
				nuevoRegistro = new TCalendarioDia();

				//Se rellena el objeto
				nuevoRegistro.setIdSesion(session.getId());

				nuevoRegistro.setFechaCalendario(calDia.getFechaCalendario());
				nuevoRegistro.setCambioEstacional(calDia.getCambioEstacional() != null ? calDia.getCambioEstacional():"");
				nuevoRegistro.setCodCentro(codCentro);
				nuevoRegistro.setEjercicio(codigoEjercicio);
				nuevoRegistro.setTipoEjercicio(tipoEjercicio);
				
				listTCalendarioDia.add(nuevoRegistro);
			}

			//Actualizamos la tabla temporal pero no decimos que se han modificado los registros porque la BD de SIA ya tiene 
			//esos datos actualizados y es innecesario.
			this.calendarioService.updateDiaCalendarioTemporaCambiosEstacionales(listTCalendarioDia,null);
		}else{
			//Si hay error devolvemos el error.
			Locale locale = LocaleContextHolder.getLocale();
			return calendarioTotalRespuesta =  new CalendarioTotal(new Long(1),this.messageSource.getMessage("p96_noExistenDatos.error", null,locale), null, null, null, listTCalendarioDiasMes);
		}
		
		//Obtenemos la lista de días del mes. Ya que ha cambiado con los días verdes
		listTCalendarioDiasMes = this.calendarioService.findDiasCalendario(session.getId(), codCentro, mesAnio, null,null,null,codigoServicio,tipoEjercicio,codigoEjercicio);

		//Devuelve un calendario total
		calendarioTotalRespuesta =  new CalendarioTotal(0L, "", null, null, null, listTCalendarioDiasMes);

		return calendarioTotalRespuesta;		
	}
}
