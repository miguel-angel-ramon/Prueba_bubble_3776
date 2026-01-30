package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.eroski.misumi.model.Agrupacion;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetalladoContadores;
import es.eroski.misumi.model.DetalladoRedondeo;
import es.eroski.misumi.model.DetalladoSIA;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoLista;
import es.eroski.misumi.model.DetallePedidoModificados;
import es.eroski.misumi.model.DetallePedidoPagina;
import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.GestionEuros;
import es.eroski.misumi.model.GestionEurosRefs;
import es.eroski.misumi.model.GestionEurosSIA;
import es.eroski.misumi.model.ListadoRefSinPLU;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.PropuestaDetalladoVegalsa;
import es.eroski.misumi.model.ReferenciasSinPLU;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.VReferenciasPedirSIADetall;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.ValidateReference;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.AlarmasPLUService;
import es.eroski.misumi.service.iface.DetalladoPedidoService;
import es.eroski.misumi.service.iface.EncargosReservasService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.VBloqueoEncargosPiladasService;
import es.eroski.misumi.service.iface.VReferenciasPedirSIADetallService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.DocumentManager;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/detalladoPedido")
@SessionAttributes("loadDetalladoPedido")
public class p70DetalladoPedidoController {
	// UUID.randomUUID();
	private static Integer totalGuardado;
	private static Integer totalError;
	private static Integer totalErrorWS;
	private static Integer totalErrorProc;
	private static Long totalFlgPropuesta;



	private static Logger logger = Logger.getLogger(p70DetalladoPedidoController.class);
	private PaginationManager<DetallePedido> paginationManager = new PaginationManagerImpl<DetallePedido>();
	@Resource
	private MessageSource messageSource;
	@Autowired
	private ExcelManager excelManager;
	@Autowired
	private DetalladoPedidoService detalladoPedidoService;
	@Autowired
	private VSurtidoTiendaService surtidoTiendaService;
	@Autowired
	private VBloqueoEncargosPiladasService vBloqueoEncargoPiladasService;
	@Autowired
	private VReferenciasPedirSIADetallService vReferenciasPedirSIADetallService;
	@Autowired
	private EncargosReservasService encargosReservasService;
	@Autowired
	private DocumentManager documentManager;
	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
	@Autowired
	private AlarmasPLUService alarmasPLUService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false, defaultValue = "0") Long center,
			Model model,
			HttpServletResponse response,
			HttpServletRequest request,
			HttpSession session) {
		try{		
logger.debug("############### ---> showForm ############### Sesion: "+session.getId());

			Boolean cargado = (Boolean)request.getSession().getAttribute("loadDetalladoPedido");
			if (cargado==null || !cargado){
				logger.debug("############### ---> loadDetalladoPedido ############### Sesion: "+session.getId()+" cargado: "+(cargado==null?"null":cargado?"true":"false"));
				request.getSession().setAttribute("loadDetalladoPedido", true);
				cargado = true;

//				model.addAttribute("loadDetalladoPedido", true);
				
				//Comprobar si se trata de un centro Vegalsa
				Boolean isCentroVegalsa = detalladoPedidoService.isCentroVegalsa(center);			
				model.addAttribute("isCentroVegalsa", isCentroVegalsa);

				logger.debug("############### ---> loadDetalladoPedido-2 ############### Sesion: "+session.getId()+" cargado: "+(cargado==null?"null":cargado?"true":"false"));
				int lengthDetallePedido = detalladoPedidoService.loadDetalladoPedido(center, isCentroVegalsa, session);
				model.addAttribute("lengthDetallePedido", lengthDetallePedido);
				
				Boolean hasAnyRecordFromSIA = true;
				if (isCentroVegalsa && lengthDetallePedido > 0){
					//cargar los mapas
					List<OptionSelectBean> lstMapas = detalladoPedidoService.getLstMapasByCenterAndIdSession(center, session.getId());
					model.addAttribute("lstMapas", lstMapas);
					
					//Comprobar si TODOS los registros del detallado tienen como unico origen Vegalsa
					hasAnyRecordFromSIA = detalladoPedidoService.hasAnyRecordFromSIA(center, session.getId());
				}
				
				model.addAttribute("hasAnyRecordFromSIA", hasAnyRecordFromSIA);
				request.getSession().setAttribute("loadDetalladoPedido", false);
				cargado = false;
//				model.addAttribute("loadDetalladoPedido", false);

				logger.debug("############### <--- loadDetalladoPedido ############### Sesion: "+session.getId()+" cargado: "+(cargado==null?"null":cargado?"true":"false"));
				
			}else{
				logger.debug("############### ---> loadDetalladoPedido-ELSE ############### Sesion: "+session.getId()+" cargado: "+(cargado==null?"null":cargado?"true":"false"));
			}

logger.debug("############### <--- showForm ###############");
			
		}catch(Exception e){
			logger.error("###############Error al cargar detallado de pedido para el centro " + center);
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return "p70_detalladoPedido";
	}

//	public  List<DetallePedido> loaData(Long center,
//			String sessionId,  HttpSession session) throws Exception{
//
//		List<DetallePedido> list = new ArrayList<DetallePedido>();
//
//		try {
//			list = detalladoPedidoService.findDetallePedido(new Centro(center, null, null, null, null, null, null, null, null, null,null), sessionId, session);//getListFromWs();
//
//		} catch (Exception e) {
//			//logger.error(StackTraceManager.getStackTrace(e));
//			throw e;
//		}
//		return list;
//
//	}
	@RequestMapping(value = "/clearSessionCenter", method = RequestMethod.GET)
	public  void clearSessionCenter(HttpServletResponse response,
			HttpSession session) throws Exception{

		User usuario= (User)session.getAttribute("user");
		usuario.setCentro(null);
		session.setAttribute("user",usuario);
	}

	@RequestMapping(value = "/getPLUsPendientes", method = RequestMethod.POST)
	public  @ResponseBody List<ReferenciasSinPLU> getPLUsPendientes(
			@RequestParam(value = "seccion", required = false) Long seccionId, 
			HttpServletResponse response,
			HttpSession session) throws Exception{
		String idSesion=session.getId();
		User usuario= (User)session.getAttribute("user");
		Long codCentro=usuario.getCentro().getCodCentro();
		List<ReferenciasSinPLU> listadoReferenciasSinPlu = new ArrayList<ReferenciasSinPLU>();
		listadoReferenciasSinPlu=this.detalladoPedidoService.loadReferenciasSinPLU(codCentro, idSesion, seccionId);
		return listadoReferenciasSinPlu;
		
	}
	
	@RequestMapping(value = "/guardarPLUsPendientes", method = RequestMethod.POST)
	public  @ResponseBody List<ReferenciasSinPLU> guardarPLUsPendientes(@RequestBody ListadoRefSinPLU listaReferenciasSinPLUPantalla,
			HttpSession session) throws Exception{
		User usuario = (User) session.getAttribute("user");
		String nombreUsuario = usuario.getCode();
		List<ReferenciasSinPLU> listaReferenciasSinPlu=this.detalladoPedidoService.saveReferenciasSinPLU(listaReferenciasSinPLUPantalla.getListadoSeleccionados(),nombreUsuario);
		return listaReferenciasSinPlu;
		
	}
	
	@RequestMapping(value="/loadContadoresDetallado", method = RequestMethod.POST)
	public @ResponseBody DetalladoContadores loadContadoresDetallado(
			@RequestBody DetallePedido detallePedido,
			HttpSession session, HttpServletResponse response) throws Exception {

		DetalladoContadores resultado = new DetalladoContadores();

		Locale locale = LocaleContextHolder.getLocale();
		try {	


			User user = (User) session.getAttribute("user");

			//¡¡¡¡¡¡¡¡ACLARAR COMO ESTA HECHO EL TRATAMIENTO DE CAPRABO EN EL DETALLADO!!!!!!!!

			//	if (utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro()) && (detallePedido.getCodArticulo()!=null && !(detallePedido.getCodArticulo().equals("")))){
			//Es un centro caprabo y se esta realizando una busqueda por referencia. Hacemos la conversion de codigo de articulo a codigo de articulo Eroski.	
			//		detallePedido.setCodArticulo(utilidadesCapraboService.obtenerCodigoEroski(detallePedido.getCodCentro(), detallePedido.getCodArticulo()));
			//	}

			//Se calculan los ditintos contadores
			resultado = this.detalladoPedidoService.loadContadoresDetallado(detallePedido, session, response);


			// Mantenemos en sesión el Contador de Encargos
			session.setAttribute("contadorsinOferta",resultado.getContadorSinOferta());

			// Mantenemos en sesión el Contador de Montaje
			session.setAttribute("contadorOferta",resultado.getContadorOferta());



			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
			resultado.setCodError(new Long(1));
			resultado.setDescError(this.messageSource.getMessage("p70_detalladoPedido.errorWSContarDetallado",null, locale));
			return resultado;
		}
	}


	@RequestMapping(value = "/loadDataGridTotal", method = RequestMethod.POST)
	public  @ResponseBody DetallePedidoPagina loadDataGrid(
			@RequestBody DetallePedido detallePedido,
			@RequestParam(value = "saveChanges", required = false, defaultValue = "N") String saveChanges,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session
			) throws Exception{
		String sessionId =  session.getId();
		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		if (saveChanges.equals("N")){
			//borrar los cambios
			detalladoPedidoService.resetSessionData(sessionId);
			detallePedido.setListaModificados(null);
		}else if (detallePedido!=null && detallePedido.getListaModificados()!=null && detallePedido.getListaModificados().size()>0){
			//actualizar estado en bbdd

			detalladoPedidoService.updateGridState(detallePedido, session);
		}

		List<DetallePedido> lstDetallePedido =this.detalladoPedidoService.findSessionInfo(detallePedido,sessionId, pagination, null);


		List<DetallePedido> lstNewDetallePedido = getChangesList(lstDetallePedido,detallePedido);

		Page<DetallePedido> pageDetallePedido = new Page<DetallePedido>();
		DetallePedidoPagina result = new DetallePedidoPagina();

		if (lstNewDetallePedido != null) {
			Long totalReg=detalladoPedidoService.countSessionInfo(detallePedido, sessionId, null);
			pageDetallePedido = this.paginationManager.paginate(new Page<DetallePedido>(), lstNewDetallePedido,
					max.intValue(), totalReg.intValue(), page.intValue());	

			//Miramos si en la selección hecha sobre el T_DETALLADO PEDIDO EXISTEN REGISTROS DE NOALI (SIA)
			detallePedido.setFlgSIA("S"); 
			Long countRegistrosNoali =this.detalladoPedidoService.countSessionInfo(detallePedido,sessionId, null);

			if(totalReg.longValue() == countRegistrosNoali.longValue()){
				result.setEsTodoSIA("S");
			}


			result.setDatos(pageDetallePedido); 
		} 




		return result;

	}


	@RequestMapping(value = "/loadDataGridSinOferta", method = RequestMethod.POST)
	public  @ResponseBody DetallePedidoPagina loadDataGridSinOferta(
			@RequestBody DetallePedido detallePedido,
			@RequestParam(value = "saveChanges", required = false, defaultValue = "N") String saveChanges,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "filtrosTabla", required = false) String filtrosTabla,
			HttpServletResponse response,
			HttpSession session
			) throws Exception{
		String sessionId =  session.getId();
		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		if (saveChanges.equals("N")){
			//borrar los cambios
			detalladoPedidoService.resetSessionData(sessionId);
			detallePedido.setListaModificados(null);
		}else if (detallePedido!=null && detallePedido.getListaModificados()!=null && detallePedido.getListaModificados().size()>0){
			//actualizar estado en bbdd

			detalladoPedidoService.updateGridState(detallePedido, session);
		}

		List<DetallePedido> lstDetallePedido = this.findSessionInfoSinOferta(detallePedido,sessionId, pagination, filtrosTabla);


		List<DetallePedido> lstNewDetallePedido = getChangesList(lstDetallePedido,detallePedido);

		Page<DetallePedido> pageDetallePedido = new Page<DetallePedido>();
		DetallePedidoPagina result = new DetallePedidoPagina();

		if (lstNewDetallePedido != null) {
			Long totalReg=this.countSessionInfoSinOferta(detallePedido, sessionId, filtrosTabla);
			pageDetallePedido = this.paginationManager.paginate( new Page<DetallePedido>(), lstNewDetallePedido
															   , max.intValue(), totalReg.intValue(), page.intValue()
															   );	

			//Miramos si en la selección hecha sobre el T_DETALLADO PEDIDO EXISTEN REGISTROS DE NOALI (SIA)
			//detallePedido.setFlgSIA("S"); 
			//Long countRegistrosNoali = this.detalladoPedidoService.countSessionInfo(detallePedido,sessionId);

			//if(totalReg.longValue() == countRegistrosNoali.longValue()){
			//result.setEsTodoSIA("S");
			//}

			//GESTION DE EUROS
			Double sumaEurosIniciales = this.detalladoPedidoService.sumarEurosIniciales(detallePedido,sessionId);
			Double sumaEurosFinales = this.detalladoPedidoService.sumarEurosFinales(detallePedido,sessionId);

			Double sumaCajasIniciales = this.detalladoPedidoService.sumarCajasIniciales(detallePedido,sessionId);
			Double sumaCajasFinales = this.detalladoPedidoService.sumarCajasFinales(detallePedido,sessionId);

			result.setEurosIniciales(sumaEurosIniciales);
			result.setEurosFinales(sumaEurosFinales); //Limitamos los decimales a 1
			result.setCajasIniciales(sumaCajasIniciales);
			result.setCajasFinales(sumaCajasFinales);

			result.setDatos(pageDetallePedido);
		}

		//Actualizamos los estadoGrid 9 a 2. El estado 9 solo vale para controlar que se ha modificado una referencia mas de una vez sin antes guardar.
		Long estadoGrid = new Long(9);
		Long nuevoEstadoGrid = new Long(2);
		detalladoPedidoService.updateOnlyGridState(detallePedido, session, estadoGrid, nuevoEstadoGrid);
		//Actualizamos los estadoGrid 8 a 5. El estado 8 solo vale para controlar que se ha modificado una referencia mas de una vez sin antes guardar y ademas tiene redondeo.
		estadoGrid = new Long(8);
		nuevoEstadoGrid = new Long(5);
		detalladoPedidoService.updateOnlyGridState(detallePedido, session, estadoGrid, nuevoEstadoGrid);

		//Miramos si mostramos el empuje.
		String mostrarEmpuje = detalladoPedidoService.mostrarEmpuje(session.getId(), detallePedido);
		result.setMostrarEmpuje(mostrarEmpuje);
		return result;

	}

	@RequestMapping(value = "/loadDataGridOferta", method = RequestMethod.POST)
	public  @ResponseBody DetallePedidoPagina loadDataGridOferta(
			@RequestBody DetallePedido detallePedido,
			@RequestParam(value = "saveChanges", required = false, defaultValue = "N") String saveChanges,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "filtrosTabla", required = false) String filtrosTabla,
			HttpServletResponse response,
			HttpSession session
			) throws Exception{
		String sessionId =  session.getId();
		Pagination pagination= new Pagination(max,page);
 		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		if (saveChanges.equals("N")){
			//borrar los cambios
			detalladoPedidoService.resetSessionData(sessionId);
			detallePedido.setListaModificados(null);
		}else if (detallePedido!=null && detallePedido.getListaModificados()!=null && detallePedido.getListaModificados().size()>0){
			//actualizar estado en bbdd

			detalladoPedidoService.updateGridState(detallePedido, session);
		}


		List<DetallePedido> lstDetallePedido = this.findSessionInfoOferta(detallePedido,sessionId, pagination, filtrosTabla);

 		List<DetallePedido> lstNewDetallePedido = getChangesList(lstDetallePedido,detallePedido);

		Page<DetallePedido> pageDetallePedido = new Page<DetallePedido>();
		DetallePedidoPagina result = new DetallePedidoPagina();

		if (lstNewDetallePedido != null) {
			Long totalReg=this.countSessionInfoOferta(detallePedido, sessionId, filtrosTabla);
			pageDetallePedido = this.paginationManager.paginate(new Page<DetallePedido>(), lstNewDetallePedido,
					max.intValue(), totalReg.intValue(), page.intValue());	

			//Miramos si en la selección hecha sobre el T_DETALLADO PEDIDO EXISTEN REGISTROS DE NOALI (SIA)
			//detallePedido.setFlgSIA("S"); 
			//Long countRegistrosNoali =this.detalladoPedidoService.countSessionInfo(detallePedido,sessionId);

			//if(totalReg.longValue() == countRegistrosNoali.longValue()){
			//result.setEsTodoSIA("S");
			//}

			//GESTION DE EUROS
			Double sumaEurosIniciales = this.detalladoPedidoService.sumarEurosIniciales(detallePedido,sessionId);
			Double sumaEurosFinales = this.detalladoPedidoService.sumarEurosFinales(detallePedido,sessionId);

			Double sumaCajasIniciales = this.detalladoPedidoService.sumarCajasIniciales(detallePedido,sessionId);
			Double sumaCajasFinales = this.detalladoPedidoService.sumarCajasFinales(detallePedido,sessionId);

			result.setEurosIniciales(sumaEurosIniciales);
			result.setEurosFinales(sumaEurosFinales); //Limitamos los decimales a 1
			result.setCajasIniciales(sumaCajasIniciales);
			result.setCajasFinales(sumaCajasFinales);

			result.setDatos(pageDetallePedido);


		} 


		return result;

	}




	private List<DetallePedido> findSessionInfoSinOferta (DetallePedido detallePedido,String sesionID, Pagination pagination, String filtrosTabla) {

		List<DetallePedido> lstDetallePedido = null;
		try {

			//Filtramos los pedidos que NO tengan oferta
			detallePedido.setFlgOferta("N");

			lstDetallePedido = this.detalladoPedidoService.findSessionInfo(detallePedido,sesionID, pagination,filtrosTabla);
		} catch (Exception e) {
			logger.error("findSessionInfoSinOferta="+e.toString());
			e.printStackTrace();
		}
		return lstDetallePedido;
	}

	private Long countSessionInfoSinOferta (DetallePedido detallePedido,String sesionID, String filtrosTabla) {

		Long result = new Long(0);
		try {

			//Filtramos los pedidos que NO tengan oferta
			detallePedido.setFlgOferta("N");

			result =  this.detalladoPedidoService.countSessionInfo(detallePedido,sesionID, filtrosTabla);
		} catch (Exception e) {
			logger.error("countSessionInfoSinOferta="+e.toString());
			e.printStackTrace();
		}

		return result;
	}

	private List<DetallePedido> findSessionInfoOferta (DetallePedido detallePedido,String sesionID, Pagination pagination, String filtrosTabla) {

		List<DetallePedido> lstDetallePedido = null;
		try {

			//Filtramos los pedidos que SI tengan oferta
			detallePedido.setFlgOferta("S");

			lstDetallePedido = this.detalladoPedidoService.findSessionInfo(detallePedido,sesionID, pagination, filtrosTabla);
		} catch (Exception e) {
			logger.error("findSessionInfoOferta="+e.toString());
			e.printStackTrace();
		}
		return lstDetallePedido;
	}


	private Long countSessionInfoOferta (DetallePedido detallePedido,String sesionID, String filtrosTabla) {

		Long result = new Long(0);
		try {

			//Filtramos los pedidos que NO tengan oferta
			detallePedido.setFlgOferta("S");

			result =  this.detalladoPedidoService.countSessionInfo(detallePedido,sesionID, filtrosTabla);
		} catch (Exception e) {
			logger.error("countSessionInfoOferta="+e.toString());
			e.printStackTrace();
		}

		return result;
	}






	@RequestMapping(value = "/calculoCronometro", method = RequestMethod.POST)
	public  @ResponseBody Cronometro calculoCronometro(
			@RequestBody DetallePedido detallePedido,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		return this.detalladoPedidoService.calculoCronometroYNumeroHorasLimite(detallePedido,session.getId());
	}

	@RequestMapping(value = "/loadDataSubGridTextil", method = RequestMethod.POST)
	public  @ResponseBody Page<DetallePedido> loadDataSubGridTextil(
			@RequestBody DetallePedido detallePedido,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{


		List<DetallePedido> list = null;

		list = this.detalladoPedidoService.findAllTextilN2ByLote(detallePedido);


		Page<DetallePedido> result = null;

		if ((list != null) &&  (list.size()> 0)) {

			int records = list.size();


			result = this.paginationManager.paginate(new Page<DetallePedido>(), list,
					list.size(), records, list.size());	

		} else {
			return new Page<DetallePedido>();
		}

		return result;
	}


	@RequestMapping(value = "/loadCantidades", method = RequestMethod.POST)
	public  @ResponseBody Long loadCantidades(
			@RequestBody DetallePedido detallePedido,
			HttpServletResponse response,
			HttpSession session
			) throws Exception{

		return detalladoPedidoService.sumBoxes(detallePedido, session.getId());

	}

	@RequestMapping(value = "/calcularRedondeo", method = RequestMethod.POST)
	public  @ResponseBody Long calcularRedondeo(
			@RequestParam(required = false, defaultValue = "") Long codCentro,
			@RequestParam(required = false, defaultValue = "") Long codArticulo,
			@RequestParam(required = false, defaultValue = "") Long cantidad,
			@RequestParam(required = false, defaultValue = "") Double unidadesCaja,
			@RequestParam(required = false, defaultValue = "") String tipoUFP,
			HttpServletResponse response,
			HttpSession session
			) throws Exception{

		//Redondeo de la cantidad
		DetalladoSIA detalladoSIA = new DetalladoSIA();
		detalladoSIA.setCodLoc(codCentro);
		
		User user = (User) session.getAttribute("user");
		
		if (utilidadesCapraboService.esCentroCaprabo(codCentro, user.getCode())){
			Long codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(codCentro, codArticulo);
			detalladoSIA.setCodArticulo(codArtEroski);
		} else {
			detalladoSIA.setCodArticulo(codArticulo);
		}

		/*
			if((Constantes.TIPO_UFP).equals(tipoUFP)){
				if(cantidad > unidadesCaja){
					if(new Long(0).equals((cantidad % unidadesCaja))){
						detalladoSIA.setUnidPropuestasFlModif(Math.round(Math.ceil(new Double(cantidad))));
					}else{				
						//Así si se hace P.ej 5/2 devuelve 2 y no 2.5
						Double division = Math.floor(cantidad / unidadesCaja);
						detalladoSIA.setUnidPropuestasFlModif(Math.round(new Double((unidadesCaja * (division)) + unidadesCaja)));
					}
				}else{
					detalladoSIA.setUnidPropuestasFlModif(Math.round(Math.ceil(new Double(cantidad))));
				}
			}else{				
				detalladoSIA.setUnidPropuestasFlModif(Math.round(Math.ceil(new Double(cantidad) * unidadesCaja)));
			}
		 */

//		detalladoSIA.setUnidPropuestasFlModif(Math.round(Math.ceil(new Double(cantidad) * unidadesCaja)));
//		detalladoSIA.setUnidPropuestasFlModif(Math.round(Math.floor(new Double(cantidad) * unidadesCaja)));
		detalladoSIA.setUnidPropuestasFlModif(Math.round(new Double(cantidad) * unidadesCaja*100.0)/100.0);
		DetalladoRedondeo detalladoRedondeo = (DetalladoRedondeo) detalladoPedidoService.redondeoDetallado(detalladoSIA);

		/*
			if (detalladoRedondeo != null) {
				if (detalladoRedondeo.getCodError() == 1) {
					//Si no es UFP, como para redondear se ha multiplicado por unidades caja ahora hay que dividir.
					if(!(Constantes.TIPO_UFP).equals(tipoUFP)){
						Double division = Double.parseDouble(detalladoRedondeo.getUnidPropuestasFlModif().toString()) / unidadesCaja;
						cantidad =  division.longValue();
					}else{
						cantidad =  detalladoRedondeo.getUnidPropuestasFlModif();
					}
				} 
			}
		 */
		if (detalladoRedondeo != null) {
			if (detalladoRedondeo.getCodError() == 1) {
				cantidad =  detalladoRedondeo.getUnidPropuestasFlModif().longValue();
			}
		}

		return cantidad;
	}

	@RequestMapping(value = "/actualizarPrecioCostoFinal", method = RequestMethod.POST)
	public  @ResponseBody Double actualizarPrecioCostoFinal(
			@RequestParam(required = false, defaultValue = "") Long codCentro,
			@RequestParam(required = false, defaultValue = "") Long codArticulo,
			@RequestParam(required = false, defaultValue = "") Long cantidad,
			@RequestParam(required = false, defaultValue = "") Double precioCostoArticulo,
			@RequestParam(required = false, defaultValue = "") Double unidadesCaja,
			HttpServletResponse response,
			HttpSession session
			) throws Exception{


		Double precioCostoFinal = null;
		//Redondeo de la cantidad
		DetallePedido detallePedido = new DetallePedido();
		detallePedido.setCodCentro(codCentro);

		User user = (User) session.getAttribute("user");
		
		if (utilidadesCapraboService.esCentroCaprabo(codCentro, user.getCode())){
			Long codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(codCentro, codArticulo);
			detallePedido.setCodArticulo(codArtEroski);
		} else {
			detallePedido.setCodArticulo(codArticulo);
		}

		precioCostoFinal = cantidad * precioCostoArticulo * unidadesCaja;
		detallePedido.setCantidad(cantidad); //Actualizamos tambien la cantidad en el T_DETALLADO_PEDIDO
		detallePedido.setPrecioCostoFinal((double)Math.round(precioCostoFinal * 10d) / 10d); //Limitamos los decimales a 1
		DetallePedido detallePedidoOut = (DetallePedido) detalladoPedidoService.actualizarPrecioCostoFinal(detallePedido, session.getId());


		if (detallePedidoOut != null) {
			precioCostoFinal =  detallePedidoOut.getPrecioCostoFinal();
		}

		return precioCostoFinal;
	}

	@RequestMapping(value = "/insertNewRecord", method = RequestMethod.POST)
	public  @ResponseBody Long insertNewRecord(
			@RequestBody DetallePedido detPedido, 
			HttpServletResponse response,
			HttpSession session
			) throws Exception{

		try {
			Long codArticulo = null;

			if ((detPedido.getFlgSIA() != null) && (detPedido.getFlgSIA().equals("S"))) {

				DetallePedidoLista listaDetallePedido = (DetallePedidoLista) detalladoPedidoService.referenciaNuevaSIA(detPedido.getCodArticuloEroski(), detPedido.getCodCentro());
				DetallePedido detallePedido = null;
				if (listaDetallePedido!=null ){

					if (listaDetallePedido.getDatos() != null && listaDetallePedido.getDatos().size()>0)
					{
						detallePedido = listaDetallePedido.getDatos().get(0);

						detPedido.setEstadoPedido("P");
						detPedido.setTipoAprovisionamiento(detallePedido.getTipoAprovisionamiento());
						detPedido.setUnidadesCaja(detallePedido.getUnidadesCaja());
						detPedido.setUfp(detallePedido.getUfp().doubleValue());
						detPedido.setTipo(detallePedido.getTipo());

						//GESTION DE EUROS
						detPedido.setPrecioCostoArticulo(detallePedido.getPrecioCostoArticulo());
						detPedido.setPrecioCostoFinal(detallePedido.getPrecioCostoFinal());
						detPedido.setPrecioCostoInicial(detallePedido.getPrecioCostoInicial());
						detPedido.setRotacion(detallePedido.getRotacion());
						detPedido.setDenomArea(detallePedido.getDenomArea());
						detPedido.setDenomSeccion(detallePedido.getDenomSeccion());
						detPedido.setDenomCategoria(detallePedido.getDenomCategoria());
						detPedido.setDenomSegmento(detallePedido.getDenomSegmento());
						detPedido.setDenomSubcategoria(detallePedido.getDenomSubcategoria());


					}
				}

			} else {
				VSurtidoTienda surtidoTienda = new VSurtidoTienda();
				surtidoTienda.setCodCentro(detPedido.getCodCentro());
				surtidoTienda.setCodArt(detPedido.getCodArticuloEroski()); //Calculamos con el código de referencia de eroski
				surtidoTienda = surtidoTiendaService.findOne(surtidoTienda);
				detPedido.setEstadoPedido("P");
				detPedido.setTipoAprovisionamiento(surtidoTienda.getTipoAprov());
				detPedido.setUnidadesCaja(surtidoTienda.getUniCajaServ());
				detPedido.setUfp(surtidoTienda.getUfp().doubleValue());

				Calendar cal = Calendar.getInstance();
				cal.setTime(surtidoTienda.getFechaGen());
				cal.add(Calendar.DAY_OF_MONTH, 3);
				if (surtidoTienda.getMarcaMaestroCentro().equals("S") && cal.getTime().after(new Date())){
					String MMC = detalladoPedidoService.comprobarReferenciaNueva(detPedido.getCodCentro(), detPedido.getCodArticuloEroski(), surtidoTienda.getFechaGen()); //Calculamos con el código de referencia de eroski
					if (null != MMC && MMC.equalsIgnoreCase("N")){
						detPedido.setTipo("N");
					}
				}
			}

			if ( (detPedido.getGrupo2().intValue() == Constantes.SECCION_CARNICERIA || 
					(detPedido.getGrupo2().intValue() == Constantes.SECCION_CHARCUTERIA && detPedido.getGrupo3() == Constantes.CATEGORIA_QUESOS_RECIEN_CORTADOS) ||
					(detPedido.getGrupo2().intValue() == Constantes.SECCION_PESCADERIA && detPedido.getGrupo3() == Constantes.CATEGORIA_ESPECIALIDADES)) && 
					!detPedido.getUfp().equals(new Double(0))){
				detPedido.setTipoUFP("U");
			}

			if ((detPedido.getTipoUFP() != null) && (detPedido.getTipoUFP().equals("U"))) { 
				if ((detPedido.getUfp()!=null) && (detPedido.getUnidadesCaja() != null)) {
					Double dexenx = Math.ceil(detPedido.getUfp()/detPedido.getUnidadesCaja());
					detPedido.setDexenx(dexenx); //Para pintarlo en el Grid
					//Así si se hace P.ej 5/2 devuelve 2 y no 2.5
					Double dexenxEntero = Math.ceil(detPedido.getUfp()/detPedido.getUnidadesCaja());
					detPedido.setDexenxEntero(dexenxEntero); //Para calcular los multiplos
				}
			}

			DetallePedido detallado=detalladoPedidoService.add(detPedido, session.getId());
			if (detallado!=null){

				if (detallado.getCodErrorRedondeo() != null && detallado.getCodErrorRedondeo() != 0 && detallado.getCodErrorRedondeo() != 1) {
					return new Long(2); //Error en el redondeo de la cantidad
				} else {
					return new Long(0);
				}
			}else{
				//buscar en los previamente insertados
				return new Long(1);
			}

		} catch (Exception e) {
			logger.error("#####################################################");
			logger.error( "####### REFERENCIA TRATADA: " + detPedido.getCodArticulo());
			logger.error("#####################################################");
			throw e;
		}
	}

	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody
	void exportGrid(
			@RequestParam(required = false) String[] headers,
			@RequestParam(required = false) String[] model,
			@RequestParam(required = false) Integer[] widths,
			@RequestParam(required = false) Long grupo2,
			@RequestParam(required = false) Long grupo3,
			@RequestParam(required = false) Long codigoArticulo,
			@RequestParam(required = false) String flgIncluirPropPed,
			@RequestParam(required = false) String flgOferta,
			@RequestParam(required = false) String index,
			@RequestParam(required = false) String sortOrder,
			@RequestParam(required = false) Integer codMapa,

			HttpServletResponse response, HttpSession session) throws Exception{
		
		try {
			Pagination pagination= new Pagination();
			if (index!=null){
				pagination.setSort(index);
			}
			if (sortOrder!=null){
				pagination.setAscDsc(sortOrder);
			} else {
				pagination.setAscDsc("ASC");
			}

			DetallePedido detallePedido= new DetallePedido();
			detallePedido.setCodArticulo(codigoArticulo);
			detallePedido.setGrupo2(grupo2);
			detallePedido.setGrupo3(grupo3);
			detallePedido.setFlgIncluirPropPed(flgIncluirPropPed);
			detallePedido.setFlgOferta(flgOferta);
			detallePedido.setCodMapa(codMapa);

			//List<DetallePedido> list =detalladoPedidoService.findSessionInfo(detallePedido,session.getId(), null);
			List<GenericExcelVO> list =detalladoPedidoService.findSessionInfoExcel(detallePedido,model,session.getId(), pagination);

			this.excelManager.exportDetallePedido(list, headers, model,widths, this.messageSource, response);

		}catch(Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}
	private Map<Long,DetallePedido> transformList(List<DetallePedido> listDetallePedido){
		Map<Long,DetallePedido> map = new HashMap<Long,DetallePedido>();
		for (DetallePedido i : listDetallePedido) map.put(i.getCodArticulo(),i);
		return map;
	}

	@RequestMapping(value="/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerRef> getAreaData(
			@RequestBody VAgruComerRef vAgruCommerRef,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			User usuario= (User)session.getAttribute("user");
			Long centro = usuario.getCentro().getCodCentro();
			List<VAgruComerRef> listaAux = this.detalladoPedidoService.findFilter(vAgruCommerRef,session.getId());
			List<VAgruComerRef> lista = new ArrayList<VAgruComerRef>();

			for (VAgruComerRef vAgruComerRef : listaAux) {

				Long countFlgPropuesta = detalladoPedidoService.countFlgPropuesta(session.getId(), centro, vAgruComerRef.getGrupo2(), vAgruComerRef.getGrupo3());

				if (countFlgPropuesta > 0) {
					vAgruComerRef.setFlgPropuesta("X");
				}

				lista.add(vAgruComerRef);
			}

			return lista;

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	private  List<DetallePedido> getChangesList(List<DetallePedido> listDetallePedidoParametro,DetallePedido detPedido){
		if(detPedido!=null && detPedido.getListaModificados()!=null && detPedido.getListaModificados().size()>0){
			Map<Long,DetallePedidoModificados> map=new HashMap<Long,DetallePedidoModificados>();
			for (DetallePedidoModificados i : detPedido.getListaModificados()){
				map.put(i.getCodArticulo(),i);
			}
			return getMergedList(listDetallePedidoParametro,map);

		}else{
			return listDetallePedidoParametro;
		}

	}
	private List<DetallePedido> getMergedList(List<DetallePedido> listDetallePedidoParametro,Map<Long,DetallePedidoModificados> modificados){
		List<DetallePedido> auxiliar= new ArrayList<DetallePedido>();
		
		if(listDetallePedidoParametro!=null &&  listDetallePedidoParametro.size()>0){
			for (DetallePedido i : listDetallePedidoParametro){
				DetallePedido record= i;

				DetallePedidoModificados detalleOrig = modificados.get(i.getCodArticulo());
				if (detalleOrig!=null){
					if ((record.getEstadoGrid() != null) && !(record.getEstadoGrid().equals("9")) && !(record.getEstadoGrid().equals("8"))) { //Se ha modificado el valor de una referencia modificada anteriormente y todavia sin guardar. 
						record.setEstadoGrid(detalleOrig.getEstadoGrid());
						record.setCantidad(detalleOrig.getCantidad());
					} else {
						record.setEstadoGrid("2"); // El estado 9 pasa a ser un 2, para que en el grid aparezca como modificado
					}
					record.setCantidadOriginal(detalleOrig.getCantidadOriginal());
					record.setCantidadUltValida(detalleOrig.getCantidadUltValida());
					record.setResultadoWS(detalleOrig.getResultadoWS());
					record.setCodMapa(detalleOrig.getCodMapa()!=null?detalleOrig.getCodMapa().intValue():null);
				}
				auxiliar.add(record);

			}
		}
		return auxiliar;
	}

	@RequestMapping(value = "/loadAgrupacion", method = RequestMethod.GET)
	public  @ResponseBody Agrupacion loadAgrupacion(
			@RequestParam(value = "codArticulo", required = false) Long codArticulo,
			HttpServletResponse response,
			HttpSession session
			) throws Exception{

		return detalladoPedidoService.findAll(new Agrupacion(codArticulo,null,null,null,null,null)); 


	}
	@RequestMapping(value="/validateReference", method = RequestMethod.GET)
	public @ResponseBody ValidateReference loadValidarReferencia(
			@RequestParam Long codArt,
			@RequestParam Long centerId,

			HttpSession session, HttpServletResponse response) throws Exception{
		Locale locale = LocaleContextHolder.getLocale();

		ValidateReference resultado = new ValidateReference();

		try {
			User user = (User) session.getAttribute("user");
			
			//Inicializamos el modelo resultado
			resultado.setEsCaprabo(false);
			resultado.setCodArtCaprabo(null);
			resultado.setCodArtEroski(codArt);
			//Miramos si la referencia es de caprabo
			if (utilidadesCapraboService.esCentroCaprabo(centerId, user.getCode())){
				Long codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(centerId, codArt);
				if (codArtEroski != null){
					//Si el centro es de caprabo y existe el código de eroski para ese código de caprabo 
					//sustituimos el código de artículo de caprabo por el código de eroski que le corresponde.
					resultado.setEsCaprabo(true);
					resultado.setCodArtCaprabo(codArt);
					resultado.setCodArtEroski(codArtEroski);
					//Y ahora sustituimos el codArt de la request para trabajar con el de eroski
					codArt = codArtEroski;
				}
				else{
					resultado.setValido( messageSource.getMessage("p70_detallePedido.caprabo.error.referenciaNoExiste", null, locale) );
					return resultado;
				}
			}

			VBloqueoEncargosPiladas vBloqueo = new VBloqueoEncargosPiladas();
			vBloqueo.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_DETALLADO);
			vBloqueo.setModo(Constantes.BLOQUEOS_MODO_FECHA_TRANSMISION);
			vBloqueo.setCodArticulo(codArt);
			vBloqueo.setCodCentro(centerId);
			vBloqueo.setFechaControl(new Date());
			VBloqueoEncargosPiladas vBloqueRes = this.vBloqueoEncargoPiladasService.getBloqueoFecha(vBloqueo);
			if (null != vBloqueRes){
				String fechaIni = Utilidades.obtenerFechaFormateada(vBloqueRes.getFecIni());
				String fechaFin = Utilidades.obtenerFechaFormateada(vBloqueRes.getFecFin());
				List<Object> args = new ArrayList<Object>();
				args.add(fechaIni);
				args.add(fechaFin);
				resultado.setValido( messageSource.getMessage("p70_detallePedido.referenciaBloqueadaTransmision", args.toArray(), locale) );
				return resultado;
			} else {


				//Comprobación de si es una referencia NO_ALI tratada por SIA
				VReferenciasPedirSIADetall vReferenciasPedirSIADetall = new VReferenciasPedirSIADetall();
				vReferenciasPedirSIADetall.setCodCentro(centerId);
				vReferenciasPedirSIADetall.setCodArt(codArt);
				boolean esReferenciaPedirSIA = this.vReferenciasPedirSIADetallService.tratarReferenciaSIA(vReferenciasPedirSIADetall); 


				//Se valida el articulo llamando al procedimiento de SIA
				resultado.setValido(this.validarArticuloSIA(codArt,centerId,session,response));
				if (resultado.getValido().equals("")) {
					//Si es una referencia de SIA, y no ha dado error la validación, en vez de devolver una cadena vacia
					//devolvemos una "S" para indicar que es una rreferencia de valida de SIA (NOALI)
					resultado.setValido("S");
					return resultado;
				} else {
					return resultado;
				}					
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			resultado.setValido("");
			return resultado;
		}

	}

	@RequestMapping(value="/calcularGestionEuros", method = RequestMethod.POST)
	public  @ResponseBody GestionEurosSIA calcularGestionEuros(
			@RequestBody DetallePedido detallePedido,			
			HttpSession session, HttpServletResponse response) throws Exception{	

		List<GestionEurosRefs> lstGestionEurosRefs = null;
		GestionEurosSIA gestionEurosSIA = null;
		try {

			//Reseteamos el campo Aviso y Diferencia antes del Calcular
			detalladoPedidoService.updatePrevioCalcular(detallePedido, session.getId());

			//Obtenemos las referencias del grid a modificar
			lstGestionEurosRefs = this.detalladoPedidoService.findSessionInfoGestionEurosRefs(detallePedido, session.getId(), null);

			if(lstGestionEurosRefs != null && lstGestionEurosRefs.size() > 0){
				//Calculamos la gestion de euros
				GestionEuros gestionEuros = new GestionEuros(detallePedido.getCodCentro(), detallePedido.getRespetarIMC(), detallePedido.getPrecioCostoFinal(), detallePedido.getPrecioCostoFinalFinal(), lstGestionEurosRefs);
				gestionEurosSIA = detalladoPedidoService.gestionEuros(gestionEuros);

				if(gestionEurosSIA.getGestionEuros() != null){
					detalladoPedidoService.updateGridStateGestionEurosRefs(gestionEurosSIA.getGestionEuros(), session.getId());
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gestionEurosSIA;
	}

	@RequestMapping(value="/propuestaInicial", method = RequestMethod.POST)
	public @ResponseBody DetallePedido propuestaInicial(
			@RequestBody DetallePedido detallePedido,			
			HttpSession session, HttpServletResponse response) throws Exception{	




		try {
			//realizamos el update para volver las cajas y el precio costos inicial a la propuesta inicial
			this.detalladoPedidoService.updatePropuestaInicial(detallePedido, session.getId());
			detallePedido.setCodError(0);


		} catch (Exception e) {
			e.printStackTrace();
		}
		return detallePedido;
	}


	private  String validarArticuloSIA(Long codArt, Long centerId, HttpSession session, HttpServletResponse response){


		Locale locale = LocaleContextHolder.getLocale();

		try {

			EncargoReserva encargoReserva = new EncargoReserva();
			encargoReserva.setCodArtFormlog(codArt);
			encargoReserva.setCodLoc(centerId);

			EncargoReserva resultProc = new EncargoReserva();

			//Llamamos al DAO que llama al procedimiento para obtener el resultado.
			resultProc = this.encargosReservasService.validarArticulo(encargoReserva);


			if (resultProc.getCodError() == 0) {

				if(resultProc.getBloqueoDetallado()) {

					return messageSource.getMessage("p70_detallePedido.referenciaBloqueada", null, locale);
				} else {
					VBloqueoEncargosPiladas vBloqueo = new VBloqueoEncargosPiladas();
					vBloqueo = this.detalladoPedidoService.getNextDayDetalladoPedido(codArt, centerId);

					if (null != vBloqueo && null != vBloqueo.getFecIni() && null != vBloqueo.getFecFin()){
						String fechaIni = Utilidades.obtenerFechaFormateada(vBloqueo.getFecIni());
						String fechaFin = Utilidades.obtenerFechaFormateada(vBloqueo.getFecFin());
						List<Object> args = new ArrayList<Object>();
						args.add(fechaIni);
						args.add(fechaFin);
						return messageSource.getMessage("p70_detallePedido.referenciaBloqueadaTransmision", args.toArray(), locale);
					} else {
						return "";
					}
				}

			} else {
				return resultProc.getDescError();
			}



		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			return "";
		}
	}


	@RequestMapping(value="/validateAgrupacion", method = RequestMethod.GET)
	public @ResponseBody String loadValidarAgrupacion(
			@RequestParam Long grupo1,
			@RequestParam Long grupo2,
			@RequestParam Long grupo3,
			@RequestParam Long grupo4,
			@RequestParam Long grupo5,
			HttpSession session, HttpServletResponse response) throws Exception{

		Locale locale = LocaleContextHolder.getLocale();
		try {
			DetallePedido detallePedido= new DetallePedido();
			detallePedido.setGrupo1(grupo1);
			detallePedido.setGrupo2(grupo2);
			detallePedido.setGrupo3(grupo3);
			detallePedido.setGrupo4(grupo4);
			detallePedido.setGrupo5(grupo5);

			List<DetallePedido> list =detalladoPedidoService.findSessionInfo(detallePedido,session.getId(), null, null);

			if (list == null || list.size() == 0){ //No existe agrupación en el listado
				return messageSource.getMessage("p70_detallePedido.agrupacionNoPermitida", null, locale);
			}
			return null;
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

	}

	@RequestMapping(value = "/saveData", method = RequestMethod.POST)
	public  @ResponseBody DetallePedidoPagina saveData(
			@RequestBody DetallePedido detallePedido,
			@RequestParam(value = "center", required = false) Long center, 
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session
			) throws Exception{
		Pagination pagination= new Pagination(max,page);

		// pagination.setSort("agrupacion");

		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}

		if (index!=null){
			pagination.setSort(index);
		}
		String sessionId =  session.getId();
		
		List<DetallePedido> lstDetallePedido =new ArrayList<DetallePedido>();
		Integer total = 0;
		Integer error = 0;

		if (detallePedido!=null && detallePedido.getListaModificados()!=null && detallePedido.getListaModificados().size()>0){
			
			// Recuperar registros de T_DETALLADO_PEDIDO. Si hay registos --> INSERT en T_MIS_DETALLADO_VEGALSA_MODIF. Si existe registro, UPDATE.
			List<DetallePedidoModificados> lstDetallePedidoModif = detalladoPedidoService.gestionDatosVegalsaModif(detallePedido.getListaModificados(), session);
			detallePedido.setListaModificados(lstDetallePedidoModif);

//			detalladoPedidoService.updateDatosVegalsa(lstDetallePedidoModif, session.getId());

			for (DetallePedidoModificados dpm : lstDetallePedidoModif){
				if (dpm.getEstadoGrid().equals("-1")){
					error++;
				}
			}
			total = lstDetallePedidoModif.size();
		}

		if(detallePedido.getTotalGuardar() != null){
			int guardadoAcumulado = detallePedido.getTotalGuardar();
			detallePedido.setTotalGuardar(guardadoAcumulado + total);
		}else{
			detallePedido.setTotalGuardar(total);
		}

		if(detallePedido.getTotalError() != null){
			int errorAcumulado = detallePedido.getTotalError();
			detallePedido.setTotalError(errorAcumulado + error);
		}else{
			detallePedido.setTotalError(error);
		}

		// Si el filtro NO es Mapa
		if (!detallePedido.isFiltroMapa()){
			detalladoPedidoService.saveData(detallePedido,sessionId,new Centro(center,null,null,null,null,null,null,null,null,null,null), session);
		}
		lstDetallePedido = detalladoPedidoService.findSessionInfo(detallePedido,sessionId,pagination, null);

		totalGuardado = detallePedido.getTotalGuardar();
		totalError = detallePedido.getTotalError();
		totalErrorWS = detallePedido.getErrorModificarWS();
		totalErrorProc = detallePedido.getErrorModificarProc();

		Page<DetallePedido> pageDetallePedido = new Page<DetallePedido>();
		DetallePedidoPagina result = new DetallePedidoPagina();

		if (lstDetallePedido != null) {
			Long totalReg=detalladoPedidoService.countSessionInfo(detallePedido, sessionId, null);
			pageDetallePedido = this.paginationManager.paginate( new Page<DetallePedido>(), lstDetallePedido
															   , max.intValue(), totalReg.intValue(), page.intValue()
															   );	

			//Miramos si en la selección hecha sobre el T_DETALLADO PEDIDO EXISTEN REGISTROS DE NOALI (SIA)
			detallePedido.setFlgSIA("S"); 
			Long countRegistrosNoali =this.detalladoPedidoService.countSessionInfo(detallePedido,sessionId, null);

			if(totalReg.longValue() == countRegistrosNoali.longValue()){
				result.setEsTodoSIA("S");
			}

			result.setDatos(pageDetallePedido);

		} 
		return result;

	}

	@RequestMapping(value = "/getCounter", method = RequestMethod.POST)
	public  @ResponseBody Map<String,Integer> getCounter(
			HttpServletResponse response,
			HttpSession session
			) throws Exception{
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("totalGuardado", (totalGuardado != null ? totalGuardado : 0) );
		map.put("totalError", (totalError != null ? totalError : 0) );
		map.put("totalErrorWS", (totalErrorWS != null ? totalErrorWS : 0) );
		map.put("totalErrorProc", (totalErrorProc != null ? totalErrorProc : 0) );

		return map;

	}

	@RequestMapping(value = "/countModificados", method = RequestMethod.GET)
	public  @ResponseBody int countModificados(
			HttpServletResponse response,
			HttpSession session
			) throws Exception{
		
		int countModificados = detalladoPedidoService.countModificados(session.getId());

		return countModificados;

	}
	
	@RequestMapping(value = "/getTotalFlgPropuesta", method = RequestMethod.POST)
	public  @ResponseBody Map<String,Long> getTotalFlgPropuesta(
			HttpServletResponse response,
			HttpSession session
			) throws Exception{

		Map<String, Long> map = new HashMap<String, Long>();

		map.put("totalFlgPropuesta", (totalFlgPropuesta != null ? totalFlgPropuesta : 0) );


		return map;

	}

	@RequestMapping(value = "/downloadHorariosPlataforma", method = RequestMethod.GET)
	public @ResponseBody
	void downloadHorariosPaltaforma(
			HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {
			this.documentManager.downloadHorarios(this.messageSource, request, response);

		}catch(Exception e) { 

			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}
	
	@RequestMapping(value = "/resumenPropuestaVegalsa", method = RequestMethod.GET)
	public @ResponseBody
	List<PropuestaDetalladoVegalsa> resumenPropuestaVegalsa(
			@RequestParam(value = "codMapa", required = true) Long codMapa, 
			HttpServletRequest request,
			HttpServletResponse response, 
			HttpSession session) throws Exception{
		try {
			final User user = (User) session.getAttribute("user");
			final Long codCentro = user.getCentro().getCodCentro();
			return detalladoPedidoService.resumenPropuestaVegalsa(codCentro, codMapa);
		}catch(Exception e) { 
			logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}
	
}