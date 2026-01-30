package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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
import es.eroski.misumi.model.CalendarioDescripcionServicio;
import es.eroski.misumi.model.CalendarioDescripcionServicios;
import es.eroski.misumi.model.CalendarioDia;
import es.eroski.misumi.model.CalendarioDiaCambioServicio;
import es.eroski.misumi.model.CalendarioEjercicios;
import es.eroski.misumi.model.CalendarioProcesosDiarios;
import es.eroski.misumi.model.CalendarioTotal;
import es.eroski.misumi.model.CalendarioValidado;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.TCalendarioDiaCambioServicio;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.CalendarioService;

@Controller
@RequestMapping("/calendario")
public class p96CalendarioController {

	private static Logger logger = Logger.getLogger(p96CalendarioController.class);

	@Resource
	private MessageSource messageSource;

	@Autowired
	private CalendarioService calendarioService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false, defaultValue = "0") Long center,
			Map<String, String> model, 
			@RequestParam(required = false, defaultValue = "") String origenPantalla,
			HttpServletResponse response, HttpSession session) {

		model.put("origenPantalla", origenPantalla);
		
		return "p96_calendario";
	}

	//Sirve para cargar los valores del combobox ordenados
	@RequestMapping(value = "/consultarServiciosCombo", method = RequestMethod.POST)
	public  @ResponseBody CalendarioDescripcionServicios consultarServiciosCombo(
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,			
			@RequestParam(value = "tipoEjercicio", required = false, defaultValue = "N") String tipoEjercicio,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User usuario= (User)session.getAttribute("user");
		Long codCentro = usuario.getCentro().getCodCentro();

		return calendarioService.consultarServiciosCombo(codCentro,codigoEjercicio,tipoEjercicio);
	}

	//Sirve para cargar los valores del combobox ordenados
	@RequestMapping(value = "/consultarAnioCalendarioCombo", method = RequestMethod.POST)
	public  @ResponseBody CalendarioEjercicios consultarAnioCalendarioCombo(
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User usuario= (User)session.getAttribute("user");
		Long codCentro = usuario.getCentro().getCodCentro();

		//Obtenemos los ejercicios
		CalendarioEjercicios calEjer = calendarioService.consultarAnioCalendarioCombo(codCentro);

		//Obtenemos el rol. tecnicRole=1 centerRole=2 consultaRole=3 adminRole= 4
		calEjer.setRol(usuario.getPerfil().toString());

		return calEjer;
	}

	//Sirve para cargar los valores del combobox ordenados
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/consultarCalendario", method = RequestMethod.POST)
	public  @ResponseBody CalendarioTotal consultarCalendario(
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,
			@RequestParam(value = "codigoServicio", required = true) Long codigoServicio,
			@RequestBody  CalendarioDescripcionServicios calendarioDescripcionServicios,
			@RequestParam(value = "tipoEjercicio", required = false, defaultValue = "N") String tipoEjercicio,
			@RequestParam(value = "eliminarTemporal", required = false, defaultValue = "") String eliminarTemporal,
			@RequestParam(value = "mesAnio", required = false) String mesAnio,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Consultamos los días del calendario.

		User usuario= (User)session.getAttribute("user");

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Objeto de respuesta.
		CalendarioTotal calendarioTotalRespuesta;

		//Objeto que guarda el mes.
		List<TCalendarioDia> listTCalendarioDia = null;

		//Si no es una paginación o reordenación
		if(("N").equals(recarga)){			
			// Cuando hacemos la primera búsqueda eliminamos siempre los
			// registros guardados anteriormente. Se eliminarán todos los
			//registros de todas las sesiones que lleven más de un día en
			//la tabla.
			this.eliminarTablaSesionHistorico();

			// Insertar tabla temporal T_DIAS_CALENDARIO
			// A partir de la lista obtenida tenemos que insertar en la
			// tabla temporal los registros obtenidos,
			// borrando previamente los posibles registros almacenados. 
			// Si este metodo se lanza desde el combo de año, no debe boorarse la temporal, ya podria haber cambios del año peviamente consultado todavia sin guardar.

			if (!(eliminarTemporal.equals("N"))) {
				this.eliminarTablaSesion(session.getId());
			}


			//Se ha decidido que nada más cargar la pantalla, se cargue la tabla temporal con todos los días del año por cada
			//servicio. Por lo que un mismo día aparecerá N veces, una por cada servicio.
			CalendarioTotal calTotTodo = new CalendarioTotal();


			//Solo vamos a consultar el procedimiento y e insertar en la tabla temporal si es un año que no se ha consultado previamente. Podrriamos tener cambios sin guardar para
			//ese año y si consultamos e insertamos otra vez, borrariamos dichos cambios

			//Primero consultamos la tabla temporal T_DIAS_CALENDARIO para saber si ese ejercicio y tipo de ejerrcicio ya ha sido consultado previamente
			boolean ejericioConsultado = (boolean) calendarioService.ejercicioConsultado(session.getId(),codCentro, tipoEjercicio, codigoEjercicio);

			//Le estamos pasando la lista de servicios, pero también queremos que busque los totales. Como los totales se buscan pasando codigo de servicio en null, 
			//lo metemos aquí a mano.
			calendarioDescripcionServicios.getListCatalogoDescripcion().add(new CalendarioDescripcionServicio("",null));
			for(CalendarioDescripcionServicio calDescServ:calendarioDescripcionServicios.getListCatalogoDescripcion()){
				calTotTodo = calendarioService.consultarCalendario(codCentro,codigoEjercicio,calDescServ.getLocalizador(),tipoEjercicio);

				//El procedimiento no devuelve el tipoEjericicio ni el Ejercicio (el año) pero lo tenemos, se lo asignamos para insertarlo en T_DIAS_CALENDARIO para cada uno de los dias.
				calTotTodo.setTipoEjercicio(tipoEjercicio);
				calTotTodo.setEjercicio(codigoEjercicio);

				if (!(ejericioConsultado)) { //solo se inserta en la tabla temporal si no ha sido consultado e insertado previamente
					//Si va bien, se itnroduce en la tabla temporal. Si no, devolvemos error.
					if(new Long("0").equals(calTotTodo.getpCodError())){
						//Si el calendario tiene dias.
						if(calTotTodo.getListadoFecha() != null && calTotTodo.getListadoFecha().size() > 0){
							//Se inserta en una tabla temporal cada día del calendario
							this.insertarTablaSesionDiasDelCalendario(calTotTodo, session.getId(),codCentro,calDescServ.getLocalizador());	
						}
					}else{
						return calTotTodo;
					}
				}
			}


			//Obtiene la lista de meses y años del calendario actual.
			List<String> mesAnioLst = calendarioService.findMesAnioCalendario(session.getId(),codCentro, tipoEjercicio, codigoEjercicio);

			if(mesAnioLst != null && mesAnioLst.size()>0){

				Date fechaHoy = new Date();
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				cal.setTime(fechaHoy);
				int anio = cal.get(Calendar.YEAR);
				int mes = cal.get(Calendar.MONTH) + 1;
				
				//Si es Ejecucion y el año actual correponde con el año seleccionado en el combo, nos situamos en mes que corresponde.
				//Los años en eroski duran desde enero del año actual hasta febrero del siguiente, por lo que también hay que tener en cuenta que el codigo de ejercicio puede ser Por ejemplo 2019 y la fecha actual 2020. 
				//En ese caso también nos interesa colocarnos en el mes actual.
				
				//Por eso, si el código de ejercicio y el año actual coinciden, si el mes no es enero (de febrero a diciembre) se calcula el mes actual.
				//Si el código de ejercicio y el año actual no coinciden (Estamos en codigoEjercicio 2019 pero es Enero de 2020), también se calcula el mes
				//actual.
				
				//Si estamos en codigoEjercicio 2020 y hoy es enero de 2020, no nos interesa el mes actual. Nos interesa febrero, ya que el calendario empiezam en
				//febrero.
				Long anioSiguiente = codigoEjercicio + 1;
				if ((tipoEjercicio.equals("E")) && (codigoEjercicio.equals(new Long(anio)) && mes != 1) || (anioSiguiente.equals(new Long(anio)) && mes == 1)) { 
					if (mes < 10) {
						mesAnio = anio + "-0" + mes;
					} else {
						mesAnio =  anio + "-" + mes;
					}

				} else { //Sino en el primer mes (Febrero)
					mesAnio = mesAnioLst.get(0);
				}

				//Obtenemos la lista de días del mes.
				listTCalendarioDia = this.calendarioService.findDiasCalendario(session.getId(), codCentro, mesAnio, null,null,null,codigoServicio, tipoEjercicio, codigoEjercicio);
				
				if ((listTCalendarioDia == null) ||  (listTCalendarioDia.size() <= 0)) {//Si no hay datos nos posicionamos en febrero y obtenemos los datos para febrero
					mesAnio = mesAnioLst.get(0);
					listTCalendarioDia = this.calendarioService.findDiasCalendario(session.getId(), codCentro, mesAnio, null,null,null,codigoServicio, tipoEjercicio, codigoEjercicio);
				}
			}

			//Si hemos llegado aquí significa que no ha habido ningún error en la carga de la tabla temporal.
			calendarioTotalRespuesta =  new CalendarioTotal(0L, "", calTotTodo.getValidarCalendario(), calTotTodo.getNumeroDiasAdelanteAtrasVerde(), null, listTCalendarioDia,mesAnioLst);
		}else{
			//Obtenemos la lista de días del mes.
			listTCalendarioDia = this.calendarioService.findDiasCalendario(session.getId(), codCentro, mesAnio, null,null,null,codigoServicio, tipoEjercicio, codigoEjercicio);

			calendarioTotalRespuesta =  new CalendarioTotal(0L, "", null, null, null, listTCalendarioDia);
		}
		return calendarioTotalRespuesta;
	}

	//Sirve para actualizar la tabla temporal. Si flgDiaVerde es S, devuelve las filas de la temporal también.
	@RequestMapping(value = "/updateDiaCalendarioTemporal", method = RequestMethod.POST)
	public  @ResponseBody CalendarioTotal updateDiaCalendarioTemporal(
			@RequestBody TCalendarioDia tCalendarioDia,			
			@RequestParam(value = "flgDiaVerde", required = false, defaultValue = "N") String flgDiaVerde,
			@RequestParam(value = "numeroDiasAdelanteAtrasVerdeP96", required = false, defaultValue = "") Long numeroDiasAdelanteAtrasVerdeP96,
			@RequestParam(value = "mesAnio", required = false) String mesAnio,
			@RequestParam(value="codigoServicio", required=false, defaultValue = " ") Long codigoServicio,
			@RequestParam(value = "codigoEjercicio", required = false) Long codigoEjercicio,	
			@RequestParam(value = "tipoEjercicio", required = false, defaultValue = "N") String tipoEjercicio,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		List<TCalendarioDia> listTCalendarioDialistTCalendarioDia = null;

		//Una vez actualizados los datos temporales, si ha habido cambio en el día verde, se reactualiza la tabla temporal
		//con los cambios de días verdes de todo el calendario y se obtienen los datos temporales para volver a pintarlos
		//en pantalla.

		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Actualizamos la tabla temporal.
		tCalendarioDia.setIdSesion(session.getId().toString());
		tCalendarioDia.setCodCentro(codCentro);
		tCalendarioDia.setCodServicio(codigoServicio);
		tCalendarioDia.setEjercicio(codigoEjercicio);
		tCalendarioDia.setTipoEjercicio(tipoEjercicio);

		List<TCalendarioDia> tCalendarioDiaLst = new ArrayList<TCalendarioDia>();
		tCalendarioDiaLst.add(tCalendarioDia);

		//Actualizamos el día. Se hacen dos actualizaciones distintas, una por los datos que afectan a todos los estados (abierto/cerrado, festivo/no festivo)
		//y otra por los cambios manuales. Anteriormente, se hacia todo en un update, porque o llegaba un cambio de cambio manual que podía afectar a uno/varios
		//servicios o llegaba un cambio para todos los servicios. Ahora, por ejemplo, puede que llegue un cambio de cambio manual para un solo servicio y un cerrado
		//a la vez. El cambioManual querrá afectar a un servicio específico y el cerrado a todos, por lo que se han separado los updates.
		this.calendarioService.updateDiaCalendarioTemporal(tCalendarioDiaLst,9L);
		this.calendarioService.updateDiaCalendarioTemporalCambioManual(tCalendarioDiaLst,9L);

		//Si se ha cerrado el centro, queremos eliminar los servicios buscados de ese día para volver a buscarlos en el procedimiento.
		if(("S").equals(tCalendarioDia.getCerrado())){
			//Creamos un día y le insertamos la lista de servicios
			TCalendarioDia tCalendarioDiaFlgServBuscados = new TCalendarioDia();
			tCalendarioDiaFlgServBuscados.setIdSesion(session.getId());
			tCalendarioDiaFlgServBuscados.setCodCentro(codCentro);
			tCalendarioDiaFlgServBuscados.setFechaCalendario(tCalendarioDia.getFechaCalendario());
			
			//Indicamos que no se han buscado los servicios.
			tCalendarioDiaFlgServBuscados.setFlgServiciosBuscados("");
			
			tCalendarioDiaFlgServBuscados.setTipoEjercicio(tipoEjercicio);
			tCalendarioDiaFlgServBuscados.setEjercicio(codigoEjercicio);
			
			//Creamos una lista de días
			List<TCalendarioDia> tCalDiaLst = new ArrayList<TCalendarioDia>();
			tCalDiaLst.add(tCalendarioDiaFlgServBuscados);
			
			//Borramos los servicios de ese día
			this.calendarioService.deleteServiciosDia(tCalendarioDia);
			
			//Indicamos que los servicios de ese día no se han buscado para que vuelva a tirar de procedimiento.
			this.calendarioService.updateDiaCalendarioTemporalFlgServicio(tCalDiaLst);
		}
		
		//Si el código del servicio es diferente de nulo y se ha cambiado un cambio manual (se ha puesto o quitado el camión), 
		//significa que tenemos que quitar de la tabla temporal de los servicios ese cambio manual
		//para que a la hora de abrir el popup, salga sin ese servicio. Puede que todavía no se hayan
		//cargado los servicios de ese día, por lo que habría que cargarlos en la temporal, actualizar el cambio
		//e indicar que flgCambioServicios es S para indicar que la tabla ya existe. Si ya etán cargados, simplemente hay que actualizar la temporal.
		if(codigoServicio != null && (("S").equals(tCalendarioDia.getCambioManual()) || ("N").equals(tCalendarioDia.getCambioManual()))){
			//Si los servicios del día ya están buscados, actualiza el servicio con el valor del cambio manual.
			//Si no están buscados, buscalos y cambia el valor.
			if(("S").equals(tCalendarioDia.getFlgServiciosBuscados())){
				//Actualizamos con el valor que se ha dado al servicio la tabla temporal de servicios
				//Creamos el objeto a actualizar.
				TCalendarioDiaCambioServicio tCalendarioDiaCambioServicio = new TCalendarioDiaCambioServicio();
				tCalendarioDiaCambioServicio.setCodCentro(codCentro);
				tCalendarioDiaCambioServicio.setIdSesion(session.getId());

				//Sabemos que es un código de servicio temporal, porque los habituales no se pueden modificar.
				tCalendarioDiaCambioServicio.setCodigoServicio(codigoServicio);
				tCalendarioDiaCambioServicio.setCambioManual(tCalendarioDia.getCambioManual());
				tCalendarioDiaCambioServicio.setFechaCalendario(tCalendarioDia.getFechaCalendario());

				tCalendarioDiaCambioServicio.setEjercicio(codigoEjercicio);
				tCalendarioDiaCambioServicio.setTipoEjercicio(tipoEjercicio);
				
				//Creamos una lista de servicios
				List<TCalendarioDiaCambioServicio> tCalServList = new ArrayList<TCalendarioDiaCambioServicio>();
				tCalServList.add(tCalendarioDiaCambioServicio);

				//Creamos un día y le insertamos la lista de servicios
				TCalendarioDia tCalendarioDiaServ = new TCalendarioDia();
				tCalendarioDiaServ.setIdSesion(session.getId());
				tCalendarioDiaServ.setCodCentro(codCentro);
				tCalendarioDiaServ.setFechaCalendario(tCalendarioDia.getFechaCalendario());
				tCalendarioDiaServ.setEjercicio(codigoEjercicio);
				tCalendarioDiaServ.setTipoEjercicio(tipoEjercicio);
				
				tCalendarioDiaServ.settCalendarioDiaCambioServicioLst(tCalServList);

				//Creamos una lista de días
				List<TCalendarioDia> tCalDiaLst = new ArrayList<TCalendarioDia>();
				tCalDiaLst.add(tCalendarioDiaServ);

				//Actualizamos el día como si estuviera modificado con un 10 (aunque el que está modificado es su servicio) y su servicio (este se actualizará de verdad).
				//Así, a la hora de guardar, obtenemos los días modificados y a partir de ahí conseguimos sus servicios. Como puede que el día no se haya modificado pero
				//sus servicios si, ponemos que el día está modificado y como para el PLSQL necesitamos tanto el día como sus servicios, matamos dos pájaros de un tiro.
				calendarioService.updateDiaCalendarioTemporalYServiciosTemporal(tCalDiaLst, null, 10L);

				//Al actualizar los checkboxes, hay que mirar continuamente si todos los servicios son N, para en el caso de salir del popup, desaparezca el camión del todos.
				//O en el caso de no serlo, que aparezca.
				TCalendarioDia tCalDiaServiciosSN = new TCalendarioDia();
				tCalDiaServiciosSN.setIdSesion(session.getId());
				tCalDiaServiciosSN.setFechaCalendario(tCalendarioDia.getFechaCalendario());
				tCalDiaServiciosSN.setCodCentro(codCentro);
				tCalDiaServiciosSN.setEjercicio(codigoEjercicio);
				tCalDiaServiciosSN.setTipoEjercicio(tipoEjercicio);

				String cambioManualTodos = calendarioService.checkValueOfCambioManualTodos(tCalDiaServiciosSN);

				//Si no hay ningún servicio seleccionado, tenemos que quitar el camión. Por lo que si no existe ningun servicio se introduce en cambio manual de todos un N y si no un S.
				tCalDiaServiciosSN.setCambioManual(cambioManualTodos);

				//Realizamos update del calendario temporal para indicar que el cambio manual de todos se ha transformado en S o en N.
				calendarioService.updateDiaCalendarioTemporalServiciosQuitados(tCalDiaServiciosSN, 9L);
			}else{
				CalendarioProcesosDiarios calTotTodo = calendarioService.consultarProcesosDiarios(codCentro,codigoEjercicio,tCalendarioDia.getFechaCalendario(),tipoEjercicio);

				//Se inserta en una tabla temporal cada día del calendario
				this.insertarTablaSesionServiciosDiasDelCalendario(calTotTodo, session.getId(),codCentro,tCalendarioDia.getFechaCalendario(), tipoEjercicio,codigoEjercicio);	

				//Actualizamos con el valor que se ha dado al servicio la tabla temporal de servicios
				//Creamos el objeto a actualizar.
				TCalendarioDiaCambioServicio tCalendarioDiaCambioServicio = new TCalendarioDiaCambioServicio();
				tCalendarioDiaCambioServicio.setCodCentro(codCentro);
				tCalendarioDiaCambioServicio.setIdSesion(session.getId());

				//Si estamos abriendo el centro, se está enviando cerrado = N y cambioManual a N.Si estamos en un servicio, queremos que ponga el cambio manual a N en todos los servicios, no solo el del servicio en el que 
				//estamos, por lo que el codigoServicio lo pasamos a null. Al abrir el centro, el flgServiciosBuscados es "" por lo que entra en esta parte del código y solo hay que realizar esta modificación aquí.
				if(("N").equals(tCalendarioDia.getCerrado())){
					tCalendarioDiaCambioServicio.setCodigoServicio(null);
				}else{
					//Sabemos que es un código de servicio temporal, porque los habituales no se pueden modificar.
					tCalendarioDiaCambioServicio.setCodigoServicio(codigoServicio);
				}
				
				tCalendarioDiaCambioServicio.setCambioManual(tCalendarioDia.getCambioManual());
				tCalendarioDiaCambioServicio.setFechaCalendario(tCalendarioDia.getFechaCalendario());

				tCalendarioDiaCambioServicio.setTipoEjercicio(tipoEjercicio);
				tCalendarioDiaCambioServicio.setEjercicio(codigoEjercicio);
				
				//Creamos una lista de servicios
				List<TCalendarioDiaCambioServicio> tCalServList = new ArrayList<TCalendarioDiaCambioServicio>();
				tCalServList.add(tCalendarioDiaCambioServicio);

				//Creamos un día y le insertamos la lista de servicios
				TCalendarioDia tCalendarioDiaServ = new TCalendarioDia();
				tCalendarioDiaServ.setIdSesion(session.getId());
				tCalendarioDiaServ.setCodCentro(codCentro);
				tCalendarioDiaServ.setFechaCalendario(tCalendarioDia.getFechaCalendario());
				tCalendarioDiaServ.setFlgServiciosBuscados("S");
				tCalendarioDiaServ.settCalendarioDiaCambioServicioLst(tCalServList);
				tCalendarioDiaServ.setTipoEjercicio(tipoEjercicio);
				tCalendarioDiaServ.setEjercicio(codigoEjercicio);
				
				//Creamos una lista de días
				List<TCalendarioDia> tCalDiaLst = new ArrayList<TCalendarioDia>();
				tCalDiaLst.add(tCalendarioDiaServ);

				//Actualizamos el día como si estuviera modificado con un 10 (aunque el que está modificado es su servicio) y su servicio (este se actualizará de verdad).
				//Así, a la hora de guardar, obtenemos los días modificados y a partir de ahí conseguimos sus servicios. Como puede que el día no se haya modificado pero
				//sus servicios si, ponemos que el día está modificado y como para el PLSQL necesitamos tanto el día como sus servicios, matamos dos pájaros de un tiro.
				calendarioService.updateDiaCalendarioTemporalYServiciosTemporal(tCalDiaLst, null, 10L);

				//Actualizamos la tabla temporal del día para indicar que el campo FLG_SERVICIOS_BUSCADOS es "S". Así sabemos
				//que no hay que volver a consultar el PLSQL para conseguir los servicios de un día.					
				this.calendarioService.updateDiaCalendarioTemporalFlgServicio(tCalDiaLst);

				//Al actualizar los checkboxes, hay que mirar continuamente si todos los servicios son N, para en el caso de salir del popup, desaparezca el camión del todos.
				//O en el caso de no serlo, que aparezca.
				TCalendarioDia tCalDiaServiciosSN = new TCalendarioDia();
				tCalDiaServiciosSN.setIdSesion(session.getId());
				tCalDiaServiciosSN.setFechaCalendario(tCalendarioDia.getFechaCalendario());
				tCalDiaServiciosSN.setCodCentro(codCentro);
				tCalDiaServiciosSN.setEjercicio(codigoEjercicio);
				tCalDiaServiciosSN.setTipoEjercicio(tipoEjercicio);
				
				String cambioManualTodos = calendarioService.checkValueOfCambioManualTodos(tCalDiaServiciosSN);

				//Si no hay ningún servicio seleccionado, tenemos que quitar el camión. Por lo que si no existe ningun servicio se introduce en cambio manual de todos un N y si no un S.
				tCalDiaServiciosSN.setCambioManual(cambioManualTodos);

				//Realizamos update del calendario temporal para indicar que el cambio manual de TODOS se ha transformado en S o en N.
				calendarioService.updateDiaCalendarioTemporalServiciosQuitados(tCalDiaServiciosSN, 9L);
			}
		}//Si el servicio es TODOS y quitamos el camión, queremos que la tabla temporal de T_SERVICIOS_DIAS se vuelva N también.
		//Así si insertamos algún servicio desde el calendario, al abrir el popup, todos serán N excepto el que pongamos desde el
		//calendario.
		else if(codigoServicio == null && ("N").equals(tCalendarioDia.getCambioManual())){
			//Si los servicios del día ya están buscados, actualiza el servicio con el valor del cambio manual.
			//Si no están buscados, buscalos y cambia el valor.
			if(("S").equals(tCalendarioDia.getFlgServiciosBuscados())){
				//Actualizamos con el valor que se ha dado al servicio la tabla temporal de servicios
				//Creamos el objeto a actualizar.
				TCalendarioDiaCambioServicio tCalendarioDiaCambioServicio = new TCalendarioDiaCambioServicio();
				tCalendarioDiaCambioServicio.setCodCentro(codCentro);
				tCalendarioDiaCambioServicio.setIdSesion(session.getId());

				//Sabemos que es un código de servicio temporal, porque los habituales no se pueden modificar.
				tCalendarioDiaCambioServicio.setCodigoServicio(codigoServicio);
				tCalendarioDiaCambioServicio.setCambioManual(tCalendarioDia.getCambioManual());
				tCalendarioDiaCambioServicio.setFechaCalendario(tCalendarioDia.getFechaCalendario());

				tCalendarioDiaCambioServicio.setEjercicio(codigoEjercicio);
				tCalendarioDiaCambioServicio.setTipoEjercicio(tipoEjercicio);
				
				//Creamos una lista de servicios
				List<TCalendarioDiaCambioServicio> tCalServList = new ArrayList<TCalendarioDiaCambioServicio>();
				tCalServList.add(tCalendarioDiaCambioServicio);

				//Creamos un día y le insertamos la lista de servicios
				TCalendarioDia tCalendarioDiaServ = new TCalendarioDia();
				tCalendarioDiaServ.setIdSesion(session.getId());
				tCalendarioDiaServ.setCodCentro(codCentro);
				tCalendarioDiaServ.setFechaCalendario(tCalendarioDia.getFechaCalendario());

				tCalendarioDiaServ.settCalendarioDiaCambioServicioLst(tCalServList);

				tCalendarioDiaServ.setEjercicio(codigoEjercicio);
				tCalendarioDiaServ.setTipoEjercicio(tipoEjercicio);
				
				//Creamos una lista de días
				List<TCalendarioDia> tCalDiaLst = new ArrayList<TCalendarioDia>();
				tCalDiaLst.add(tCalendarioDiaServ);

				//Actualizamos el día como si estuviera modificado con un 10 (aunque el que está modificado es su servicio) y su servicio (este se actualizará de verdad).
				//Así, a la hora de guardar, obtenemos los días modificados y a partir de ahí conseguimos sus servicios. Como puede que el día no se haya modificado pero
				//sus servicios si, ponemos que el día está modificado y como para el PLSQL necesitamos tanto el día como sus servicios, matamos dos pájaros de un tiro.
				calendarioService.updateDiaCalendarioTemporalYServiciosTemporal(tCalDiaLst, null, 10L);

				//Al actualizar los checkboxes, hay que mirar continuamente si todos los servicios son N, para en el caso de salir del popup, desaparezca el camión del todos.
				//O en el caso de no serlo, que aparezca.
				TCalendarioDia tCalDiaServiciosSN = new TCalendarioDia();
				tCalDiaServiciosSN.setIdSesion(session.getId());
				tCalDiaServiciosSN.setFechaCalendario(tCalendarioDia.getFechaCalendario());
				tCalDiaServiciosSN.setCodCentro(codCentro);
				tCalDiaServiciosSN.setEjercicio(codigoEjercicio);
				tCalDiaServiciosSN.setTipoEjercicio(tipoEjercicio);
				
				String cambioManualTodos = calendarioService.checkValueOfCambioManualTodos(tCalDiaServiciosSN);
				
				//Si no hay ningún servicio seleccionado, tenemos que quitar el camión. Por lo que si no existe ningun servicio se introduce en cambio manual de todos un N y si no un S.
				tCalDiaServiciosSN.setCambioManual(cambioManualTodos);

				//Realizamos update del calendario temporal para indicar que el cambio manual de todos se ha transformado en S o en N.
				calendarioService.updateDiaCalendarioTemporalServiciosQuitados(tCalDiaServiciosSN, 9L);
			}else{
				CalendarioProcesosDiarios calTotTodo = calendarioService.consultarProcesosDiarios(codCentro,codigoEjercicio,tCalendarioDia.getFechaCalendario(),tipoEjercicio);

				//Se inserta en una tabla temporal cada día del calendario. t_dias_calendario y t_servicios_dias_calendario obtienen el mismo valor aquí, RESETEANDO LO QUE HABÍA
				this.insertarTablaSesionServiciosDiasDelCalendario(calTotTodo, session.getId(),codCentro,tCalendarioDia.getFechaCalendario(),tipoEjercicio,codigoEjercicio);	

				//Actualizamos con el valor que se ha dado al servicio la tabla temporal de servicios
				//Creamos el objeto a actualizar.
				TCalendarioDiaCambioServicio tCalendarioDiaCambioServicio = new TCalendarioDiaCambioServicio();
				tCalendarioDiaCambioServicio.setCodCentro(codCentro);
				tCalendarioDiaCambioServicio.setIdSesion(session.getId());

				//Sabemos que es un código de servicio temporal, porque los habituales no se pueden modificar.
				tCalendarioDiaCambioServicio.setCodigoServicio(codigoServicio);
				tCalendarioDiaCambioServicio.setCambioManual(tCalendarioDia.getCambioManual());
				tCalendarioDiaCambioServicio.setFechaCalendario(tCalendarioDia.getFechaCalendario());
				tCalendarioDiaCambioServicio.setEjercicio(codigoEjercicio);
				tCalendarioDiaCambioServicio.setTipoEjercicio(tipoEjercicio);
				
				//Creamos una lista de servicios
				List<TCalendarioDiaCambioServicio> tCalServList = new ArrayList<TCalendarioDiaCambioServicio>();
				tCalServList.add(tCalendarioDiaCambioServicio);

				//Creamos un día y le insertamos la lista de servicios
				TCalendarioDia tCalendarioDiaServ = new TCalendarioDia();
				tCalendarioDiaServ.setIdSesion(session.getId());
				tCalendarioDiaServ.setCodCentro(codCentro);
				tCalendarioDiaServ.setFechaCalendario(tCalendarioDia.getFechaCalendario());
				tCalendarioDiaServ.setFlgServiciosBuscados("S");
				tCalendarioDiaServ.settCalendarioDiaCambioServicioLst(tCalServList);
				tCalendarioDiaServ.setEjercicio(codigoEjercicio);
				tCalendarioDiaServ.setTipoEjercicio(tipoEjercicio);
				
				//Creamos una lista de días
				List<TCalendarioDia> tCalDiaLst = new ArrayList<TCalendarioDia>();
				tCalDiaLst.add(tCalendarioDiaServ);

				//Actualizamos el día como si estuviera modificado con un 10 (aunque el que está modificado es su servicio) y su servicio (este se actualizará de verdad).
				//Así, a la hora de guardar, obtenemos los días modificados y a partir de ahí conseguimos sus servicios. Como puede que el día no se haya modificado pero
				//sus servicios si, ponemos que el día está modificado y como para el PLSQL necesitamos tanto el día como sus servicios, matamos dos pájaros de un tiro.
				
				//Cuando actualizamos T_SERVICIOS_DIAS_CALENDARIO hay un trigger que pone el mismo valor en T_DIAS_CALENDARIO
				calendarioService.updateDiaCalendarioTemporalYServiciosTemporal(tCalDiaLst, null, 10L);
				
				//Actualizamos la tabla temporal del día para indicar que el campo FLG_SERVICIOS_BUSCADOS es "S". Así sabemos
				//que no hay que volver a consultar el PLSQL para conseguir los servicios de un día.					
				this.calendarioService.updateDiaCalendarioTemporalFlgServicio(tCalDiaLst);

				//Al actualizar los checkboxes, hay que mirar continuamente si todos los servicios son N, para en el caso de salir del popup, desaparezca el camión del todos.
				//O en el caso de no serlo, que aparezca.
				TCalendarioDia tCalDiaServiciosSN = new TCalendarioDia();
				tCalDiaServiciosSN.setIdSesion(session.getId());
				tCalDiaServiciosSN.setFechaCalendario(tCalendarioDia.getFechaCalendario());
				tCalDiaServiciosSN.setCodCentro(codCentro);
				tCalDiaServiciosSN.setEjercicio(codigoEjercicio);
				tCalDiaServiciosSN.setTipoEjercicio(tipoEjercicio);
				
				String cambioManualTodos = calendarioService.checkValueOfCambioManualTodos(tCalDiaServiciosSN);

				//Si no hay ningún servicio seleccionado, tenemos que quitar el camión. Por lo que si no existe ningun servicio se introduce en cambio manual de todos un N y si no un S.
				tCalDiaServiciosSN.setCambioManual(cambioManualTodos);

				//Realizamos update del calendario temporal para indicar que el cambio manual de TODOS se ha transformado en S o en N.
				calendarioService.updateDiaCalendarioTemporalServiciosQuitados(tCalDiaServiciosSN, 9L);
			}
		}

		//Ya hemos actualizado los datos temporales, como los días verdes tienen un tratamiento especial,
		//se aplica.
		if(("S").equals(flgDiaVerde)){
			//Actualizamos los días verdes del calendario temporal.
			this.calendarioService.updateDiasVerdesCalendarioTemporal(tCalendarioDiaLst,numeroDiasAdelanteAtrasVerdeP96,session.getId(),9L, codCentro,usuario);

			//Obtenemos la lista de días del mes. Ya que ha cambiado con los días verdes
			listTCalendarioDialistTCalendarioDia = this.calendarioService.findDiasCalendario(session.getId(), codCentro, mesAnio, null,null,null,codigoServicio, tipoEjercicio, codigoEjercicio);
		}

		CalendarioTotal calendarioTotal = new CalendarioTotal(0L,"",null, null,null,listTCalendarioDialistTCalendarioDia);
		return calendarioTotal;
	}

	//Sirve para guardar los valores de las líneas de devolución modificadas
	@RequestMapping(value = "/guardarCalendario", method = RequestMethod.POST)
	public  @ResponseBody CalendarioActualizado guardarCalendario(
			@RequestParam(value = "tipoEjercicio", required = false, defaultValue = "N") String tipoEjercicio,
			@RequestParam(value="codigoServicio", required=false, defaultValue = " ") Long codigoServicio,
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,	
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Creamos el puntero del calendarioActualizado.
		CalendarioActualizado calActualizadoEjecucion = null;
		CalendarioActualizado calActualizadoPlanificacion = null;
		CalendarioActualizado calActualizado = null;

		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Quitamos los días que no han sido modificados. (se modificaron, pero se volvieron a dejar como estaban.)
		//Si cambio_manual_servicios_nulos es nulo, significa que no se han reseteado los servicios relacionados,
		//por lo que solo miramos los festivos, cerrados, etc. Si es SQ, significa que se han reseteado sus servicios
		//pero puede que aún reseteados, se hayan quedado como estaban y que el día no tenga cambios, por loo que también
		//nos interesa quitarlos. Además, puede que un camión saliera por el servicio habitual y no por el cambio manual,
		//al poner y quitar camión, jugamos con el cambio manual, por lo que ese vampo cogerá S o N. Al guardar, puede que
		//hubiera camión y siga habiéndolo, pero ahora dependa del cambio manual y no del servicioHabitual, en esos casos
		//lo tomaremos como que no ha habido cambios tampoco.
		this.calendarioService.quitFakeModifiedDays(session.getId(),codCentro,9L);

		//Una vez tenemos la tabla temporal actualizada, obtenemos los días modificados. Obtenemos los días con el servicio en NULL, porque ese es el servicio de TODOS y 
		//el que realmente guarda el estado de todo el calendario.
		List<TCalendarioDia> listTCalendarioDia = this.calendarioService.findDiasModificadosCalendario(session.getId(), codCentro, new Long(9), null);

		//Una vez tenemos la tabla temporal actualizada, obtenemos los días con servicios modificados (con el flag 10)
		List<TCalendarioDia> listTCalendarioDiaDeServiciosModificados = this.calendarioService.findDiasModificadosCalendario(session.getId(), codCentro, null, new Long(10));

		//Inicializamos la lista que guarda los días con servicios modificados.
		List<TCalendarioDia> listTCalendarioDiaYServicios = null;

		//Llamamos al PLSQL para actualizar los días del calendario.
		if(listTCalendarioDiaDeServiciosModificados != null & listTCalendarioDiaDeServiciosModificados.size() > 0){
			//Quitamos los servicios que no han sido modificados. (se modificaron, pero se volvieron a dejar como estaban.
			this.calendarioService.quitFakeModifiedServices(session.getId(),codCentro,10L);

			//Obtener la lista de servicios de los días del calendario con servicios modificados.
			listTCalendarioDiaYServicios = this.calendarioService.findServiciosCalendario(listTCalendarioDiaDeServiciosModificados,10L);

			//Copiamos los días que tienen un 10, y que tienen servicios. Así filtramos los que no tienen
			List<TCalendarioDia> listaConServicios = new ArrayList<TCalendarioDia>();

			//Vamos añadiendo a una lista los elementos que SI tienen servicios.
			for(TCalendarioDia tCalDia:listTCalendarioDiaYServicios){
				if(tCalDia.gettCalendarioDiaCambioServicioLst() != null){
					listaConServicios.add(tCalDia);	
				}
			}


			//Juntamos las listas de servicios modificados y de días modificados. Puede que un dia haya sido modificado (9) y que además tenga servicios (10), por lo que 
			//hay que controlar no meter dos veces el mismo elemento en la lista. Si el elemento del día con servicios no existe en la lista, se introduce el elemento.

			//Esta lista guardará la posición de los elementos de los días de servicio a introducir en la lista general porque no existen en esa lista.
			List<Integer> posDiasQueNoEstanEnListTCalendarioDia  = new ArrayList<Integer>();

			//Inicializamos la posicion del elemento que 
			int indiceTDiaServicio = 0;

			//Miramos por cada día de servicios si existe un día con la misma fecha en la lista general, si existe, le metemos los servicios de ese día al día de la lista general,
			//si no existe, guardamos la posición que ocupa en la lista posDiasQueNoEstanEnListTCalendarioDia para luego coger ese elemento y guardarlo en la lista general.
			for(TCalendarioDia tCalDiaServicios:listaConServicios){
				boolean servicioNoIncluidoEnLista = true;
				for(TCalendarioDia tCalDia:listTCalendarioDia){						
					//Si existe esa fecha en la lista general, se meten sus servicios en el día de la fecha general.
					if(tCalDiaServicios.getFechaCalendario().equals(tCalDia.getFechaCalendario())){
						tCalDia.settCalendarioDiaCambioServicioLst(tCalDiaServicios.gettCalendarioDiaCambioServicioLst());

						//Se indica que este elemento no hay que meterlo entero en la lista general.
						servicioNoIncluidoEnLista = false;
						break;
					}
				}

				//Si no existe esa fecha en la lista general, se guarda la posición del día para introducirlo más tarde.
				if(servicioNoIncluidoEnLista){
					posDiasQueNoEstanEnListTCalendarioDia.add(indiceTDiaServicio);
				}
				indiceTDiaServicio ++;
			}

			//Insertamos los días de servicios que no existen en la lista general en la lista general.
			for(Integer posLista:posDiasQueNoEstanEnListTCalendarioDia){
				TCalendarioDia tDiaServ = listaConServicios.get(posLista);
				listTCalendarioDia.add(tDiaServ);
			}
		}
		//Actualizar PLSQL.
		if(listTCalendarioDia != null & listTCalendarioDia.size() > 0){

			//Separamos la lista a GUARDAR en dos listas una con tipo de Ejecucion 'E' y otra con 'P'. Se hace esto porque el procedimiento lo requiere asi.
			List<TCalendarioDia> listTCalendarioDiaEjecucion = new ArrayList<TCalendarioDia>();
			List<TCalendarioDia> listTCalendarioDiaPlanificacion = new ArrayList<TCalendarioDia>();

			for(TCalendarioDia tCalDia:listTCalendarioDia){
				if(tCalDia.getTipoEjercicio().equals("E")){
					listTCalendarioDiaEjecucion.add(tCalDia);	
				} else {
					listTCalendarioDiaPlanificacion.add(tCalDia);	
				}
			}

			//Se llama al procedimiento dos veces, una con tipo de Ejecucion 'E' y otra con 'P'
			if (listTCalendarioDiaEjecucion != null && listTCalendarioDiaEjecucion.size() > 0) {
				tipoEjercicio = "E";
				calActualizadoEjecucion = this.calendarioService.guardarCalendario(codCentro,tipoEjercicio,listTCalendarioDiaEjecucion);
			}
			if (listTCalendarioDiaPlanificacion != null && listTCalendarioDiaPlanificacion.size() > 0) {
				tipoEjercicio = "P";
				calActualizadoPlanificacion = this.calendarioService.guardarCalendario(codCentro,tipoEjercicio,listTCalendarioDiaPlanificacion);
			}

			//Si todo se ha guardado correctamente, actualizamos la tabla temporal y ponemos 8 (que significa guardado) en los elementos correctamente guardados.
			if ((calActualizadoEjecucion != null) && (new Long("0").equals(calActualizadoEjecucion.getCodError()) || new Long("1").equals(calActualizadoEjecucion.getCodError()))) {
				//Indicamos que los días y servicios por día se han guardado correctamente en la tabla temporal con un 8 en estado_dia.
				//Se actualizarán las filas correspondientes a todos y a los servicios de t_dias_calendario y a las de t_servicios_calendario.
				this.calendarioService.updateDiaCalendarioTemporalYServiciosTemporalGuardado(listTCalendarioDiaEjecucion);			
			}

			if ((calActualizadoPlanificacion != null) && (new Long("0").equals(calActualizadoPlanificacion.getCodError()) || new Long("1").equals(calActualizadoEjecucion.getCodError()))) {
				//Indicamos que los días y servicios por día se han guardado correctamente en la tabla temporal con un 8 en estado_dia.
				//Se actualizarán las filas correspondientes a todos y a los servicios de t_dias_calendario y a las de t_servicios_calendario.
				this.calendarioService.updateDiaCalendarioTemporalYServiciosTemporalGuardado(listTCalendarioDiaPlanificacion);			
			}
			Long codError = null;
			String descError = null;
			if(calActualizadoEjecucion != null){
				codError = calActualizadoEjecucion.getCodError();
				descError = calActualizadoEjecucion.getDescError();
			}
			if(calActualizadoPlanificacion != null){
				//Si existe codError, significa que hay calendario de ejecución. Si no, devolvemos lo de planificación directamente.
				//Si codError es 0 porque la ejecución se ha guardado bien, obtenemos el valor de planificación. Si es 0 genial, si es error, guardamos error. 
				//Si ejecución tenía error, mostramos su error.
				if(codError != null){
					codError = new Long("0").equals(codError) ? calActualizadoPlanificacion.getCodError() : codError;
					descError = new Long("0").equals(codError) ? calActualizadoPlanificacion.getDescError() : descError;
				}else{
					codError = calActualizadoPlanificacion.getCodError();
					descError = calActualizadoPlanificacion.getDescError();
				}
			}
			
			calActualizado = new CalendarioActualizado(codError,descError);	
		}else{
			Locale locale = LocaleContextHolder.getLocale();
			calActualizado = new CalendarioActualizado(new Long(99),this.messageSource.getMessage("p96_noExistenDatos.error", null,locale));			
		}
		return calActualizado;
	}

	//Sirve para validar el calendario
	@RequestMapping(value = "/validarCalendario", method = RequestMethod.POST)
	public  @ResponseBody CalendarioValidado validarCalendario(
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,	
			HttpServletResponse response,
			HttpSession session) throws Exception{
		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		return calendarioService.validarCalendario(codCentro, codigoEjercicio);
	}

	@RequestMapping(value = "/existenDatosSinGuardar", method = RequestMethod.GET)
	public  @ResponseBody Long  existenDatosSinGuardar(
			HttpServletResponse response,
			HttpSession session) throws Exception{
		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Quitamos los días que no han sido modificados. (se modificaron, pero se volvieron a dejar como estaban.)
		this.calendarioService.quitFakeModifiedDays(session.getId(),codCentro,9L);

		//Una vez tenemos la tabla temporal actualizada, obtenemos los días modificados. Obtenemos los días con el servicio en NULL, porque ese es el servicio de TODOS y 
		//el que realmente guarda el estado de todo el calendario.
		List<TCalendarioDia> listTCalendarioDia = this.calendarioService.findDiasModificadosCalendario(session.getId(), codCentro, new Long(9), null);

		//Una vez tenemos la tabla temporal actualizada, obtenemos los días con servicios modificados (con el flag 10)
		List<TCalendarioDia> listTCalendarioDiaDeServiciosModificados = this.calendarioService.findDiasModificadosCalendario(session.getId(), codCentro, null, new Long(10));
	
		//Inicializamos la lista que guarda los días con servicios modificados.
		List<TCalendarioDia> listTCalendarioDiaYServicios = null;

		//Llamamos al PLSQL para actualizar los días del calendario.
		if(listTCalendarioDiaDeServiciosModificados != null & listTCalendarioDiaDeServiciosModificados.size() > 0){
			//Quitamos los servicios que no han sido modificados. (se modificaron, pero se volvieron a dejar como estaban.
			this.calendarioService.quitFakeModifiedServices(session.getId(),codCentro,10L);

			//Obtener la lista de servicios de los días del calendario con servicios modificados.
			listTCalendarioDiaYServicios = this.calendarioService.findServiciosCalendario(listTCalendarioDiaDeServiciosModificados,10L);

			//Copiamos los días que tienen un 10, y que tienen servicios. Así filtramos los que no tienen
			List<TCalendarioDia> listaConServicios = new ArrayList<TCalendarioDia>();

			//Vamos añadiendo a una lista los elementos que SI tienen servicios.
			for(TCalendarioDia tCalDia:listTCalendarioDiaYServicios){
				if(tCalDia.gettCalendarioDiaCambioServicioLst() != null){
					listaConServicios.add(tCalDia);	
				}
			}


			//Juntamos las listas de servicios modificados y de días modificados. Puede que un dia haya sido modificado (9) y que además tenga servicios (10), por lo que 
			//hay que controlar no meter dos veces el mismo elemento en la lista. Si el elemento del día con servicios no existe en la lista, se introduce el elemento.

			//Esta lista guardará la posición de los elementos de los días de servicio a introducir en la lista general porque no existen en esa lista.
			List<Integer> posDiasQueNoEstanEnListTCalendarioDia  = new ArrayList<Integer>();

			//Inicializamos la posicion del elemento que 
			int indiceTDiaServicio = 0;

			//Miramos por cada día de servicios si existe un día con la misma fecha en la lista general, si existe, le metemos los servicios de ese día al día de la lista general,
			//si no existe, guardamos la posición que ocupa en la lista copiaListTCalendarioDiaYServicios para luego coger ese elemento y guardarlo en la lista general.
			for(TCalendarioDia tCalDiaServicios:listaConServicios){
				boolean servicioNoIncluidoEnLista = true;
				for(TCalendarioDia tCalDia:listTCalendarioDia){						
					//Si existe esa fecha en la lista general, se meten sus servicios en el día de la fecha general.
					if(tCalDiaServicios.getFechaCalendario().equals(tCalDia.getFechaCalendario())){
						tCalDia.settCalendarioDiaCambioServicioLst(tCalDiaServicios.gettCalendarioDiaCambioServicioLst());

						//Se indica que este elemento no hay que meterlo entero en la lista general.
						servicioNoIncluidoEnLista = false;
						break;
					}
				}

				//Si no existe esa fecha en la lista general, se guarda la posición del día para introducirlo más tarde.
				if(servicioNoIncluidoEnLista){
					posDiasQueNoEstanEnListTCalendarioDia.add(indiceTDiaServicio);
				}
				indiceTDiaServicio ++;
			}

			//Insertamos los días de servicios que no existen en la lista general en la lista general.
			for(Integer posLista:posDiasQueNoEstanEnListTCalendarioDia){
				TCalendarioDia tDiaServ = listaConServicios.get(posLista);
				listTCalendarioDia.add(tDiaServ);
			}
		}
		//Devuelve 1 si hay elementos sin guardar y 0 si no los hay.
		return (listTCalendarioDia != null & listTCalendarioDia.size() > 0)? new Long(1):new Long(0);
	}

	//Elimina la tabla temporal histórica de todos los usuarios si ha pasado más de un día desde
	//la creación de los registros
	private void eliminarTablaSesionHistorico(){		
		try {
			this.calendarioService.deleteHistorico();
		} catch (Exception e) {
			logger.error("eliminarTablaSesionHistorico="+e.toString());
			e.printStackTrace();
		}
	}

	//Elimina los datos de sesión de la tabla temporal de ese usuario 
	private void eliminarTablaSesion(String idSesion){		
		try {
			this.calendarioService.delete(idSesion);
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

	//Método que sirve para guardar cada día del calendario en un registro de una tabla temporal llamada T_DIAS_CALENDARIO
	//Esa tabla además de contener como columnas los atributos del objeto CalendarioDia, contiene el id de la sesión
	//del usuario, la fecha de creación. Con la columna del id de sesión es posible que cada
	//usuario tenga sus datos en la tabla temporal y así no ver los de los demás, además se utilizará para borrar los
	//datos de la tabla temporal de su sesión cuando sea necesario. El campo de la fecha de creación sirve para eliminar los
	//registros que lleven guardados X tiempo y sean considerados como basura en la tabla temporal. 
	private void insertarTablaSesionDiasDelCalendario(CalendarioTotal calTot, String idSesion,Long codCentro,Long codServicio){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TCalendarioDia> listTCalendarioDia = new ArrayList<TCalendarioDia>();

		//Definimos el puntero del registro a guardar en la bd
		TCalendarioDia nuevoRegistro = null;

		//Obtenemos el Tipo de Ejercicio y el Ejercicio que corresponde al calendario que estamos consultando
		String tipoEjercicio = calTot.getTipoEjercicio();
		Long ejercicio = calTot.getEjercicio();

		for (CalendarioDia calDia:calTot.getListadoFecha()){
			//Se inicializa el objeto. Cada objeto de este tipo será un registro de la tabla temporal
			nuevoRegistro = new TCalendarioDia();

			//Se rellena el objeto
			nuevoRegistro.setIdSesion(idSesion);
			nuevoRegistro.setCreationDate(new Date());

			nuevoRegistro.setFechaCalendario(calDia.getFechaCalendario());
			nuevoRegistro.setFestivo(calDia.getFestivo());
			nuevoRegistro.setPonerDiaVerde(calDia.getPonerDiaVerde());
			nuevoRegistro.setCerrado(calDia.getCerrado());
			nuevoRegistro.setServicioHabitual(calDia.getServicioHabitual());
			nuevoRegistro.setCambioManual(calDia.getCambioManual());
			nuevoRegistro.setCambioEstacional(calDia.getCambioEstacional());	
			nuevoRegistro.setECambioPlataforma(calDia.getECambioPlataforma());	
			nuevoRegistro.setESePuedeModificarServicio(calDia.getESePuedeModificarServicio());	
			nuevoRegistro.setEAprobadoCambio(calDia.getEAprobadoCambio());	
			nuevoRegistro.setCodCentro(codCentro);
			nuevoRegistro.setCodServicio(codServicio);
			nuevoRegistro.setTipoEjercicio(tipoEjercicio); // El tipo de Ejercicio es igual para todos los dias de un calendario P (Planificacion) o E (Ejecucion)	
			nuevoRegistro.setEjercicio(ejercicio); // El Ejercicio es igual para todos los dias de un calendario, es año de calendario consultado	
			nuevoRegistro.setVerdePlataforma(calDia.getVerdePlataforma());
			nuevoRegistro.setPuedeSolicitarServicio(calDia.getPuedeSolicitarServicio());
			nuevoRegistro.setNoServicio(calDia.getNoServicio());
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(nuevoRegistro.getFechaCalendario());

			//Se hace +1 porque ENERO es 0
			int month = cal.get(Calendar.MONTH) +1;
			int year = cal.get(Calendar.YEAR);

			String monthStr = month > 9 ? Integer.toString(month) : "0"+Integer.toString(month);
			nuevoRegistro.setMesAnio(year+"-"+monthStr);

			listTCalendarioDia.add(nuevoRegistro);
		}

		try {
			this.calendarioService.insertAllDiasCalendario(listTCalendarioDia);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}	
	}

	//Sirve para cargar los valores del combobox ordenados
	/*@RequestMapping(value = "/actualizarCalendarioTrasCambioDeServicio", method = RequestMethod.POST)
	public  @ResponseBody CalendarioTotal actualizarCambiosManualesCalendario(
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

		CalendarioTotal calTotTodo = calendarioService.consultarCalendario(codCentro,codigoEjercicio,codigoServicio,tipoEjercicio);

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
				nuevoRegistro.setCambioEstacional(calDia.getCambioEstacional() != null ? calDia.getCambioEstacional() : "");	
				nuevoRegistro.setCambioManual(calDia.getCambioManual() != null ? calDia.getCambioManual() : "");	
				nuevoRegistro.setServicioHabitual(calDia.getServicioHabitual() != null ? calDia.getServicioHabitual() : "");
				nuevoRegistro.setCambioManualOri(calDia.getCambioManual() != null ? calDia.getCambioManual() : "");

				nuevoRegistro.setCodCentro(codCentro);


				listTCalendarioDia.add(nuevoRegistro);
			}

			//Actualizamos la tabla temporal pero no decimos que se han modificado los registros porque la BD de SIA ya tiene 
			//esos datos actualizados y es innecesario.
			this.calendarioService.updateDiaCalendarioTemporal(listTCalendarioDia,null);
			this.calendarioService.updateDiaCalendarioTemporalCambioManual(listTCalendarioDia,null);

			//Obtenemos la lista de días del mes. Ya que ha cambiado con los días verdes
			listTCalendarioDiasMes = this.calendarioService.findDiasCalendario(session.getId(), codCentro, mesAnio, null,null,null,codigoServicio, tipoEjercicio, codigoEjercicio);

			//Devuelve un calendario total
			calendarioTotalRespuesta =  new CalendarioTotal(0L, "", null, null, null, listTCalendarioDiasMes);
		}else{
			Locale locale = LocaleContextHolder.getLocale();
			calendarioTotalRespuesta =  new CalendarioTotal(new Long(1),this.messageSource.getMessage("p96_noExistenDatos.error", null,locale), null, null, null, listTCalendarioDiasMes);
		}
		return calendarioTotalRespuesta;		
	}*/

	//Método que sirve para guardar los servicios de cada día del calendario en un registro de una tabla temporal llamada T_SERVICIOS_DIAS_CALENDARIO 
	//Esa tabla además de contener como columnas los atributos del objeto CalendarioDiaCambioServicio, contiene el id de la sesión
	//del usuario, la fecha de creación. Con la columna del id de sesión es posible que cada
	//usuario tenga sus datos en la tabla temporal y así no ver los de los demás, además se utilizará para borrar los
	//datos de la tabla temporal de su sesión cuando sea necesario. El campo de la fecha de creación sirve para eliminar los
	//registros que lleven guardados X tiempo y sean considerados como basura en la tabla temporal. 
	private void insertarTablaSesionServiciosDiasDelCalendario(CalendarioProcesosDiarios calProcDiarios, String idSesion,Long codCentro, Date fecha, String tipoEjercicio, Long codigoEjercicio){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TCalendarioDiaCambioServicio> listTCalendarioDiaCambioServicio = new ArrayList<TCalendarioDiaCambioServicio>();

		//Definimos el puntero del registro a guardar en la bd
		TCalendarioDiaCambioServicio nuevoRegistro = null;

		for (CalendarioDiaCambioServicio servicios:calProcDiarios.getListadoServiciosCentro()){
			nuevoRegistro = new TCalendarioDiaCambioServicio();

			nuevoRegistro.setCodCentro(codCentro);
			nuevoRegistro.setCreationDate(new Date());
			nuevoRegistro.setIdSesion(idSesion);
			nuevoRegistro.setFechaCalendario(fecha);

			nuevoRegistro.setCodigoServicio(servicios.getCodigoServicio());
			nuevoRegistro.setDenominacionServicio(servicios.getDenominacionServicio());
			nuevoRegistro.setCambioEstacional(servicios.getCambioEstacional());
			nuevoRegistro.setCambioManual(servicios.getCambioManual());
			nuevoRegistro.setServicioHabitual(servicios.getServicioHabitual());

			nuevoRegistro.seteCambioPlataforma(servicios.geteCambioPlataforma());
			nuevoRegistro.seteObservaConfirmaPlataforma(servicios.geteObservacionesConfirmacionPlataforma());
			nuevoRegistro.setTipoEjercicio(tipoEjercicio);
			nuevoRegistro.setEjercicio(codigoEjercicio); // El Ejercicio es igual para todos los dias de un calendario, es año de calendario consultado	
			nuevoRegistro.setPuedeSolicitarServicio(servicios.getPuedesolicitarServicio()); 
			
			listTCalendarioDiaCambioServicio.add(nuevoRegistro);
		}

		try {
			this.calendarioService.insertAllServiciosDiasCalendario(listTCalendarioDiaCambioServicio);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}	
	}

	@RequestMapping(value = "/countFestivosLocales", method = RequestMethod.GET)
	public  @ResponseBody Long  countFestivosLocales(
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,
			@RequestParam(value = "tipoEjercicio", required = true) String tipoEjercicio,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		//Consultamos los días del calendario.
		User usuario= (User)session.getAttribute("user");	

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();
		
		//Obtenemos la cantidad de festivos locales.
		return this.calendarioService.countFestivosLocales(session.getId(), codCentro, codigoEjercicio, tipoEjercicio);		
	}
	
}