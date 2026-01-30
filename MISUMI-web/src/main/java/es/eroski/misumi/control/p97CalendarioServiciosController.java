package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Date;
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
import es.eroski.misumi.model.CalendarioDia;
import es.eroski.misumi.model.CalendarioDiaCambioServicio;
import es.eroski.misumi.model.CalendarioProcesosDiarios;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.TCalendarioDiaCambioServicio;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.CalendarioService;

@Controller
@RequestMapping("/calendario/popup/servicios")
public class p97CalendarioServiciosController {

	private static Logger logger = Logger.getLogger(p97CalendarioServiciosController.class);

	@Autowired
	private CalendarioService calendarioService;

	@Resource
	private MessageSource messageSource;

	//Sirve para cargar los valores del combobox ordenados
	@RequestMapping(value = "/consultarProcesosDiarios", method = RequestMethod.POST)
	public  @ResponseBody CalendarioProcesosDiarios consultarProcesosDiarios(
			@RequestBody CalendarioDia calendarioDia,
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "tipoEjercicio", required = false, defaultValue = "N") String tipoEjercicio,
			@RequestParam(value = "flgCambioManualServicioNulo", required = false, defaultValue = "") String flgCambioManualServicioNulo,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Borramos los servicios de una sesión.
		//this.calendarioService.deleteServicios(session.getId());

		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Objeto de respuesta.
		CalendarioProcesosDiarios calendarioProcesosDiariosRespuesta;

		//Objeto que guarda la información de los servicios diarios.
		List<TCalendarioDiaCambioServicio> listTCalendarioDiaCambioServicio = null;

		//Si no es una paginación o reordenación
		if(("").equals(recarga)){			
			CalendarioProcesosDiarios calTotTodo = calendarioService.consultarProcesosDiarios(codCentro,codigoEjercicio,calendarioDia.getFechaCalendario(),tipoEjercicio);

			//Se inserta en una tabla temporal cada día del calendario
			this.insertarTablaSesionServiciosDiasDelCalendario(calTotTodo, session.getId(),codCentro,calendarioDia,flgCambioManualServicioNulo,tipoEjercicio,codigoEjercicio);	

			//Creamos la lista de días para hacer la consulta de los servicios del calendario.
			//En ete caso, solo queremos obtener los servicios de un día por lo que la lista de días
			//tendrá solo un elemento.
			List<TCalendarioDia> tCalendarioDiaLst = new ArrayList<TCalendarioDia>();

			//Creamos el día.
			TCalendarioDia tCalendarioDia = new TCalendarioDia();
			tCalendarioDia.setFechaCalendario(calendarioDia.getFechaCalendario());
			tCalendarioDia.setCodCentro(codCentro);
			tCalendarioDia.setIdSesion(session.getId());
			tCalendarioDia.setFlgServiciosBuscados("S");
			tCalendarioDia.setTipoEjercicio(tipoEjercicio);
			tCalendarioDia.setEjercicio(codigoEjercicio);

			//Insertamos el día en la lista
			tCalendarioDiaLst.add(tCalendarioDia);

			//Obtenemos la lista de los servicios del día.En este caso, la lista solo tiene un elemento (un día y hay que mirar sus servicios)
			List<TCalendarioDia> listTCalendarioDiaYServicios = this.calendarioService.findServiciosCalendario(tCalendarioDiaLst,null);

			//Si existen los servicios se insertan en el objeto de respuesta. Además se indica que los servicios.
			if(listTCalendarioDiaYServicios.get(0).gettCalendarioDiaCambioServicioLst() != null && listTCalendarioDiaYServicios.get(0).gettCalendarioDiaCambioServicioLst().size() > 0){
				listTCalendarioDiaCambioServicio = listTCalendarioDiaYServicios.get(0).gettCalendarioDiaCambioServicioLst();

				//Actualizamos la tabla temporal del día para indicar que el campo FLG_SERVICIOS_BUSCADOS es "S". Así sabemos
				//que no hay que volver a consultar el PLSQL para conseguir los servicios de un día.					
				this.calendarioService.updateDiaCalendarioTemporalFlgServicio(tCalendarioDiaLst);
			}
			//Obtenemos la lista de los servicios, como hay triggers, es mejor obtener los servicios de nuevo.
			List<TCalendarioDia> listTCalendarioDiaYServiciosVuelta = this.calendarioService.findServiciosCalendario(tCalendarioDiaLst,null);
			calendarioProcesosDiariosRespuesta =  new CalendarioProcesosDiarios(calTotTodo.getpCodError(), calTotTodo.getpDescError(), null, listTCalendarioDiaYServiciosVuelta.get(0).gettCalendarioDiaCambioServicioLst());
		}else{
			//Creamos la lista de días para hacer la consulta de los servicios del calendario.
			//En ete caso, solo queremos obtener los servicios de un día por lo que la lista de días
			//tendrá solo un elemento.
			List<TCalendarioDia> tCalendarioDiaLst = new ArrayList<TCalendarioDia>();

			//Creamos el día.
			TCalendarioDia tCalendarioDia = new TCalendarioDia();
			tCalendarioDia.setFechaCalendario(calendarioDia.getFechaCalendario());
			tCalendarioDia.setCodCentro(codCentro);
			tCalendarioDia.setIdSesion(session.getId());
			tCalendarioDia.setTipoEjercicio(tipoEjercicio);
			tCalendarioDia.setEjercicio(codigoEjercicio);

			//Este flag es para el update más tarde. Se indicará que los servicios ya se han buscado para ese día.
			tCalendarioDia.setFlgServiciosBuscados("S");

			//Insertamos el día en la lista
			tCalendarioDiaLst.add(tCalendarioDia);

			//Obtenemos la lista de los servicios del día.En este caso, la lista solo tiene un elemento (un día y hay que mirar sus servicios)
			List<TCalendarioDia> listTCalendarioDiaYServicios = this.calendarioService.findServiciosCalendario(tCalendarioDiaLst,null);

			//Si existen los servicios se insertan en el objeto de respuesta. Además se indica que los servicios.
			if(listTCalendarioDiaYServicios.get(0).gettCalendarioDiaCambioServicioLst() != null && listTCalendarioDiaYServicios.get(0).gettCalendarioDiaCambioServicioLst().size() > 0){
				//Insertamos los servicios en la lista.
				listTCalendarioDiaCambioServicio = listTCalendarioDiaYServicios.get(0).gettCalendarioDiaCambioServicioLst();

				//Actualizamos la tabla temporal del día para indicar que el campo FLG_SERVICIOS_BUSCADOS es "S". Así sabemos
				//que no hay que volver a consultar el PLSQL para conseguir los servicios de un día.					
				this.calendarioService.updateDiaCalendarioTemporalFlgServicio(tCalendarioDiaLst);

				//Si flgCambioManualServicioNulo es SQ significa que no hay que tener en cuenta la info que viene del procedimiento al pulsar el camión y que los
				//cambios manuales tienen que ser nulos si no es un festivo. Si lo es, tiene que ser N.
				if(("SQ").equals(flgCambioManualServicioNulo)){
					boolean quitarCamion = true;
					for(TCalendarioDiaCambioServicio servicio:listTCalendarioDiaCambioServicio){
						//Si es festivo, queremos que todos los servicios estén en N, por lo que al abrir el popup,
						//los servicios estarán deshabilitados. Si no cambiamos nada, al cerrar el popup, aparecería el 
						//camión, porque cambioManual es S, pero no queremos que salga si no se ha hecho cambio en servicio
						//por lo que lo ponemos a N por si acaso. En el caso de poner un servicio con el check, se actualiza el camión
						//correctamente, por lo que se vería tanto en todos como en el propio servicio. Si no cambiamos nada, como al
						//cerrar se repinta el calendario, cogería los cambios del cambioManual N y dibujaría bien todo.
						if(calendarioDia.getFestivo() != null && !calendarioDia.getFestivo().equals("X")){
							//Si no hay cambios en plataforma, se pone en N.  Si hay cambio en plataforma,
							//se mantiene el valor del servicio
							if(!(servicio.geteCambioPlataforma() != null)){
								servicio.setCambioManual("N");
							}

							if(("S").equals(servicio.geteCambioPlataforma())){
								quitarCamion = false;
							}							
						}else{
							//Si no es festivo, si ecambio plataforma no tiene valor, se pone "", para que herede.
							//Si ecambioplataforma tiene valor, se deja lo que estaba.
							
							//Si es festivo plataforma no queremos resetear.
							if(!(servicio.geteCambioPlataforma() != null) && (!("FESTIVO PLATAFORMA").equals(servicio.geteObservaConfirmaPlataforma()))){
								servicio.setCambioManual("");
							}
						}
					}
					//Si no hay ningún cambio de plataforma en S en caso de ser festivo,
					//se quita el camión.
					if(quitarCamion && (calendarioDia.getFestivo() != null && !calendarioDia.getFestivo().equals("X"))){
						tCalendarioDia.setCambioManual("N");												
						this.calendarioService.updateDiaCalendarioTemporalCambioManual(tCalendarioDiaLst,9L);
					}

					//Indicamos que los servicios se han modificado, porque han pasado de tener su valor a heredar el valor 
					//al resetearse.
					this.calendarioService.updateDiaCalendarioTemporal(tCalendarioDiaLst,null,10L);
					this.calendarioService.updateDiaCalendarioServicios(listTCalendarioDiaYServicios,null,null);
				}
			}
			//Obtenemos la lista de los servicios, como hay triggers, es mejor obtener los servicios de nuevo.
			List<TCalendarioDia> listTCalendarioDiaYServiciosVuelta = this.calendarioService.findServiciosCalendario(tCalendarioDiaLst,null);
			calendarioProcesosDiariosRespuesta =  new CalendarioProcesosDiarios(0L, "", null, listTCalendarioDiaYServiciosVuelta.get(0).gettCalendarioDiaCambioServicioLst());
		}
		return calendarioProcesosDiariosRespuesta;
	}


	//Sirve para actualizar la tabla temporal de los servicios para un día en concreto.
	@RequestMapping(value = "/updateServicioDiaCalendarioTemporal", method = RequestMethod.POST)
	public  @ResponseBody TCalendarioDia updateServicioDiaCalendarioTemporal(
			@RequestBody CalendarioDia calendarioDia,
			@RequestParam(value = "tipoEjercicio", required = true) String tipoEjercicio,
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Creamos una lista de días
		List<TCalendarioDia> tCalDiaLst = new ArrayList<TCalendarioDia>();
		for(TCalendarioDiaCambioServicio tCalDiaCambServ : calendarioDia.getCalendarioProcesosDiarios().getListadoServiciosCentroTemporal()){

			//Creamos el objeto a actualizar.
			TCalendarioDiaCambioServicio tCalendarioDiaCambioServicio = new TCalendarioDiaCambioServicio();
			tCalendarioDiaCambioServicio.setCodCentro(codCentro);
			tCalendarioDiaCambioServicio.setIdSesion(session.getId());

			//Sabemos que es un código de servicio temporal, porque los habituales no se pueden modificar.
			tCalendarioDiaCambioServicio.setCodigoServicio(tCalDiaCambServ.getCodigoServicio());
			tCalendarioDiaCambioServicio.setCambioManual(tCalDiaCambServ.getCambioManual());

			if ((tipoEjercicio.equals("E") && (tCalDiaCambServ.getCambioManual() != null && (tCalDiaCambServ.getCambioManual().equals("N")|| tCalDiaCambServ.getCambioManual().equals("S"))))) {
				tCalendarioDiaCambioServicio.seteCambioPlataforma("");
			}


			tCalendarioDiaCambioServicio.setFechaCalendario(calendarioDia.getFechaCalendario());
			tCalendarioDiaCambioServicio.setEjercicio(codigoEjercicio);
			tCalendarioDiaCambioServicio.setTipoEjercicio(tipoEjercicio);

			//Creamos una lista de servicios
			List<TCalendarioDiaCambioServicio> tCalServList = new ArrayList<TCalendarioDiaCambioServicio>();
			tCalServList.add(tCalendarioDiaCambioServicio);

			//Creamos un día y le insertamos la lista de servicios
			TCalendarioDia tCalendarioDia = new TCalendarioDia();
			tCalendarioDia.setIdSesion(session.getId());
			tCalendarioDia.setCodCentro(codCentro);
			tCalendarioDia.setFechaCalendario(calendarioDia.getFechaCalendario());
			tCalendarioDia.setCambioManual(tCalDiaCambServ.getCambioManual());
			tCalendarioDia.setEjercicio(codigoEjercicio);
			tCalendarioDia.setTipoEjercicio(tipoEjercicio);

			if ((tipoEjercicio.equals("E") && (tCalDiaCambServ.getCambioManual() != null && (tCalDiaCambServ.getCambioManual().equals("N") || tCalDiaCambServ.getCambioManual().equals("S"))))) {
				tCalendarioDia.setECambioPlataforma("");
			}

			tCalendarioDia.setCodServicio(tCalDiaCambServ.getCodigoServicio());

			tCalendarioDia.settCalendarioDiaCambioServicioLst(tCalServList);

			//Añadimos el día a la lista de días.
			tCalDiaLst.add(tCalendarioDia);
		}

		//Al cambiar un servicio, queremos que en TODOS se quite el SQ y además se le ponga un 10. Por lo que
		//inicializamos un día correspondiente a TODOS para que lo modifique.
		TCalendarioDia tCalendarioDiaTodos = new TCalendarioDia();
		tCalendarioDiaTodos.setIdSesion(session.getId());
		tCalendarioDiaTodos.setCodCentro(codCentro);
		tCalendarioDiaTodos.setFechaCalendario(calendarioDia.getFechaCalendario());
		tCalendarioDiaTodos.setEjercicio(codigoEjercicio);
		tCalendarioDiaTodos.setTipoEjercicio(tipoEjercicio);

		tCalDiaLst.add(tCalendarioDiaTodos);



		//Actualizamos el día como si estuviera modificado con un 10 (aunque el que está modificado es su servicio) y su servicio (este se actualizará de verdad).
		//Así, a la hora de guardar, obtenemos los días modificados y a partir de ahí conseguimos sus servicios. Como puede que el día no se haya modificado pero
		//sus servicios si, ponemos que el día está modificado y como para el PLSQL necesitamos tanto el día como sus servicios, matamos dos pájaros de un tiro.
		//Como ahora tenemos una fiula por cada servicio y día, se está actualziando el cambio manual de ese servicio y se le está metiendo un 10 a esa fila. A la 
		//hora de guardar el calendario, los 10s los tendrán las filas de los servicios, por lo que habrá que buscar las fechas con 10 de los servicios. Como se puede
		//chequear o deschequear más de un elemento, puede que varias filas con la misma fecha tengan 10, por lo que habrá que hacer un distinct y de ahí obtener la fila de TODOS.
		calendarioService.updateDiaCalendarioTemporalYServiciosTemporal(tCalDiaLst, null, 10L);

		//Al actualizar los checkboxes, hay que mirar continuamente si todos los servicios son N, para en el caso de salir del popup, desaparezca el camión del todos.
		//O en el caso de no serlo, que aparezca.
		TCalendarioDia tCalDiaServiciosSN = new TCalendarioDia();
		tCalDiaServiciosSN.setIdSesion(session.getId());
		tCalDiaServiciosSN.setFechaCalendario(calendarioDia.getFechaCalendario());
		tCalDiaServiciosSN.setCodCentro(codCentro);
		tCalDiaServiciosSN.setEjercicio(codigoEjercicio);
		tCalDiaServiciosSN.setTipoEjercicio(tipoEjercicio);

		String cambioManualTodos = calendarioService.checkValueOfCambioManualTodos(tCalDiaServiciosSN);

		//Si no hay ningún servicio seleccionado, tenemos que quitar el camión. Por lo que si no existe ningun servicio se introduce en cambio manual de todos un N y si no un S.
		tCalDiaServiciosSN.setCambioManual(cambioManualTodos);

		if ((tipoEjercicio.equals("E") && (cambioManualTodos != null && cambioManualTodos.equals("N")))) {
			tCalDiaServiciosSN.setECambioPlataforma("");
		}
		//Realizamos update del calendario temporal para indicar que el cambio manual de todos se ha transformado en S o en N.
		calendarioService.updateDiaCalendarioTemporalServiciosQuitados(tCalDiaServiciosSN, 9L);



		return tCalDiaServiciosSN;
	}

	/*//Sirve para guardar los valores de las líneas de devolución modificadas
	@RequestMapping(value = "/guardarCalendario", method = RequestMethod.POST)
	public  @ResponseBody CalendarioActualizado guardarCalendario(
			@RequestBody CalendarioDia calendarioDia,
			@RequestParam(value = "tipoEjercicio", required = false, defaultValue = "N") String tipoEjercicio,
			@RequestParam(value = "codigoEjercicio", required = false) Long codigoEjercicio,	
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Creamos el puntero del calendarioActualizado.
		CalendarioActualizado calActualizado = null;

		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Una vez tenemos la tabla temporal actualizada, obtenemos los días modificados.
		List<TCalendarioDia> listTCalendarioDia = this.calendarioService.findDiasCalendario(session.getId(), codCentro, null, null, new Long(10),calendarioDia.getFechaCalendario(),null,tipoEjercicio,codigoEjercicio);

		//Llamamos al PLSQL para actualizar los días del calendario.
		if(listTCalendarioDia != null & listTCalendarioDia.size() > 0){
			//Quitamos los servicios que no han sido modificados. (se modificaron, pero se volvieron a dejar como estaban.)
			this.calendarioService.quitFakeModifiedServices(session.getId(),codCentro,10L);

			//Obtener la lista de servicios de los días del calendario
			List<TCalendarioDia> listTCalendarioDiaYServicios = this.calendarioService.findServiciosCalendario(listTCalendarioDia,10L);

			//Obtenemos el día que estamos mirando sus servicios
			TCalendarioDia tCalDia = listTCalendarioDiaYServicios.get(0);

			//Si existen servicios modificados, se actualiza el calendario, si no se muestra error.
			if(tCalDia.gettCalendarioDiaCambioServicioLst() != null && tCalDia.gettCalendarioDiaCambioServicioLst().size() > 0){
				//Actualizar PLSQL.
				calActualizado = this.calendarioService.guardarCalendario(codCentro,tipoEjercicio,listTCalendarioDiaYServicios);

				//Si todo se ha guardado correctamente, actualizamos la tabla temporal y ponemos 8 (que significa guardado) en los elementos correctamente guardados.
				if(new Long("0").equals(calActualizado.getCodError())){
					//Indicamos que los días y servicios por día se han guardado correctamente en la tabla temporal con un 8 en estado_dia.
					this.calendarioService.updateDiaCalendarioTemporalYServiciosTemporal(listTCalendarioDiaYServicios, null, 8L);				
				}
			}else{
				Locale locale = LocaleContextHolder.getLocale();
				calActualizado = new CalendarioActualizado(new Long(1),this.messageSource.getMessage("p97_noExistenDatos.error", null,locale));
			}
		}else{
			Locale locale = LocaleContextHolder.getLocale();
			calActualizado = new CalendarioActualizado(new Long(1),this.messageSource.getMessage("p97_noExistenDatos.error", null,locale));
		}
		return calActualizado;
	}*/

	//Método que sirve para guardar los servicios de cada día del calendario en un registro de una tabla temporal llamada T_SERVICIOS_DIAS_CALENDARIO 
	//Esa tabla además de contener como columnas los atributos del objeto CalendarioDiaCambioServicio, contiene el id de la sesión
	//del usuario, la fecha de creación. Con la columna del id de sesión es posible que cada
	//usuario tenga sus datos en la tabla temporal y así no ver los de los demás, además se utilizará para borrar los
	//datos de la tabla temporal de su sesión cuando sea necesario. El campo de la fecha de creación sirve para eliminar los
	//registros que lleven guardados X tiempo y sean considerados como basura en la tabla temporal. 
	private void insertarTablaSesionServiciosDiasDelCalendario(CalendarioProcesosDiarios calProcDiarios, String idSesion,Long codCentro, CalendarioDia calendarioDia, String flgCambioManualServicioNulo,String tipoEjercicio, Long codigoEjercicio ){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TCalendarioDiaCambioServicio> listTCalendarioDiaCambioServicio = new ArrayList<TCalendarioDiaCambioServicio>();

		//Definimos el puntero del registro a guardar en la bd
		TCalendarioDiaCambioServicio nuevoRegistro = null;

		boolean quitarCamion = true;
		for (CalendarioDiaCambioServicio servicios:calProcDiarios.getListadoServiciosCentro()){
			nuevoRegistro = new TCalendarioDiaCambioServicio();

			nuevoRegistro.setCodCentro(codCentro);
			nuevoRegistro.setCreationDate(new Date());
			nuevoRegistro.setIdSesion(idSesion);
			nuevoRegistro.setFechaCalendario(calendarioDia.getFechaCalendario());

			nuevoRegistro.setCodigoServicio(servicios.getCodigoServicio());
			nuevoRegistro.setDenominacionServicio(servicios.getDenominacionServicio());
			nuevoRegistro.setCambioEstacional(servicios.getCambioEstacional());

			nuevoRegistro.seteCambioPlataforma(servicios.geteCambioPlataforma());
			nuevoRegistro.seteObservaConfirmaPlataforma(servicios.geteObservacionesConfirmacionPlataforma());
			nuevoRegistro.setTipoEjercicio(tipoEjercicio);
			nuevoRegistro.setEjercicio(codigoEjercicio);

			//Si flgCambioManualServicioNulo es SQ significa que no hay que tener en cuenta la info que viene del procedimiento al pulsar el camión y que los
			//cambios manuales tienen que ser nulos. Además hay que poner que el cambio manual es nulo en los días de cada servicio.
			/*if(("SQ").equals(flgCambioManualServicioNulo)){
				nuevoRegistro.setCambioManual(null);
			}else{
			}*/

			if(("SQ").equals(flgCambioManualServicioNulo)){
				//Si es festivo, queremos que todos los servicios estén en N, por lo que al abrir el popup,
				//los servicios estarán deshabilitados. Si no cambiamos nada, al cerrar el popup, aparecería el 
				//camión, porque cambioManual es S, pero no queremos que salga si no se ha hecho cambio en servicio
				//por lo que lo ponemos a N por si acaso. En el caso de poner un servicio con el check, se actualiza el camión
				//correctamente, por lo que se vería tanto en todos como en el propio servicio. Si no cambiamos nada, como al
				//cerrar se repinta el calendario, cogería los cambios del cambioManual N y dibujaría bien todo.
				if(calendarioDia.getFestivo() != null && !calendarioDia.getFestivo().equals("X")){
					//Si no hay cambios en plataforma, se pone en N.  Si hay cambio en plataforma,
					//se mantiene el valor del servicio
					if(!(servicios.geteCambioPlataforma() != null)){
						nuevoRegistro.setCambioManual("N");
						//nuevoRegistro.setCambioManualOriginal("N");
					}else{
						nuevoRegistro.setCambioManual(servicios.getCambioManual());
						//nuevoRegistro.setCambioManualOriginal(servicios.getCambioManual());
					}

					if(("S").equals(servicios.geteCambioPlataforma())){
						quitarCamion = false;
					}							
				}else{
					//Si no es festivo, si ecambio plataforma no tiene valor, se pone "", para que herede.
					//Si ecambioplataforma tiene valor, se pone lo que viene en el procedimiento.
					
					//Si es festivo plataforma no queremos resetear.
					if(!(nuevoRegistro.geteCambioPlataforma() != null) && (!("FESTIVO PLATAFORMA").equals(nuevoRegistro.geteObservaConfirmaPlataforma()))){
						nuevoRegistro.setCambioManual("");
						nuevoRegistro.setCambioManualOriginal(servicios.getCambioManual());
					}else{
						nuevoRegistro.setCambioManualOriginal(servicios.getCambioManual());
						nuevoRegistro.setCambioManual(servicios.getCambioManual());
					}
				}
			}else{
				nuevoRegistro.setCambioManualOriginal(servicios.getCambioManual());
				nuevoRegistro.setCambioManual(servicios.getCambioManual());
			}

			nuevoRegistro.setServicioHabitual(servicios.getServicioHabitual());
			nuevoRegistro.setPuedeSolicitarServicio(servicios.getPuedesolicitarServicio());
			listTCalendarioDiaCambioServicio.add(nuevoRegistro);
		}
		
		try {
			
			//Si no hay ningún cambio de plataforma en S en caso de ser festivo,
			//se quita el camión.
			if(("SQ").equals(flgCambioManualServicioNulo) && quitarCamion && (calendarioDia.getFestivo() != null && !calendarioDia.getFestivo().equals("X"))){
				//Creamos la lista de días para hacer la consulta de los servicios del calendario.
				//En ete caso, solo queremos obtener los servicios de un día por lo que la lista de días
				//tendrá solo un elemento.
				List<TCalendarioDia> tCalendarioDiaLst = new ArrayList<TCalendarioDia>();
				
				//Creamos el día.
				TCalendarioDia tCalendarioDia = new TCalendarioDia();
				tCalendarioDia.setFechaCalendario(calendarioDia.getFechaCalendario());
				tCalendarioDia.setCodCentro(codCentro);
				tCalendarioDia.setIdSesion(idSesion);
				tCalendarioDia.setTipoEjercicio(tipoEjercicio);
				tCalendarioDia.setEjercicio(codigoEjercicio);

				//Este flag es para el update más tarde. Se indicará que los servicios ya se han buscado para ese día.
				tCalendarioDia.setFlgServiciosBuscados("S");

				//Insertamos el día en la lista
				tCalendarioDiaLst.add(tCalendarioDia);
				
				tCalendarioDia.setCambioManual("N");												
				this.calendarioService.updateDiaCalendarioTemporalCambioManual(tCalendarioDiaLst,9L);
			}
			
			//Insertamos en la temporal.
			this.calendarioService.insertAllServiciosDiasCalendario(listTCalendarioDiaCambioServicio);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}	
	}
}
