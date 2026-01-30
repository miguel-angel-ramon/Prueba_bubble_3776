package es.eroski.misumi.control;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import es.eroski.misumi.model.CamposSeleccionadosExclVenta;
import es.eroski.misumi.model.ExclusionVentas;
import es.eroski.misumi.model.ExclusionVentasPagina;
import es.eroski.misumi.model.TExclusionVentas;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ExclusionVentasService;
import es.eroski.misumi.service.iface.TExclusionVentasService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;



@Controller
@RequestMapping("/exclusionVentas")
public class p73ExclusionVentasController {
	private static Logger logger = Logger.getLogger(p73ExclusionVentasController.class);
	private PaginationManager<ExclusionVentas> paginationManager = new PaginationManagerImpl<ExclusionVentas>();

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private VAgruComerRefService vAgruComerRefService;

	@Autowired
	private ExclusionVentasService exclusionVentasService;

	@Autowired
	private TExclusionVentasService tExclusionVentasService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false, defaultValue = "0") Long center,
		Map<String, String> model, HttpServletResponse response, HttpSession session) {
		try{
			List<ExclusionVentas> lstExclusionVentas  = loadWSData(center,session.getId());
		}catch(Exception e){
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return "p73_exclusionVentas";
	}
	
	@RequestMapping(value="/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerRef> getAreaData(
			@RequestBody VAgruComerRef vAgruCommerRef,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			return this.vAgruComerRefService.findAll(vAgruCommerRef, null);
		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}

	@RequestMapping(value = "/clearSessionCenter", method = RequestMethod.GET)
	public  void clearSessionCenter(HttpServletResponse response,
			HttpSession session) throws Exception{
    
		User usuario= (User)session.getAttribute("user");
		usuario.setCentro(null);
		session.setAttribute("user",usuario);
	}

	private  List<ExclusionVentas> loadWSData(Long center,
			String sessionId) throws Exception{
    
		List<ExclusionVentas> list = new ArrayList<ExclusionVentas>();
		ExclusionVentas exclusionVentas = new ExclusionVentas();
		try {
			exclusionVentas.setCodCentro(center);
			list = this.exclusionVentasService.findAll(exclusionVentas);//getListFromWs();
			
			// Insertar tabla temporal T_EXCLUSION_VENTAS
			// A partir de la lista obtenida tenemos que insertar en la
			// tabla temporal los registros obtenidos,
			// borrando previamente los posibles registros almacenados.
			this.eliminarTablaSesionHistorico();
			this.eliminarTablaSesion(sessionId, center);
			if (list != null && list.size() > 0) {
				this.insertarTablaSesion(list, sessionId, center);
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			throw e;
		}
		return list;
	}	
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody ExclusionVentasPagina loadDataGrid(
			@RequestBody ExclusionVentas exclusionVentas,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		ExclusionVentasPagina exclusionVentasPagina = new ExclusionVentasPagina();
		Locale locale = LocaleContextHolder.getLocale();
		
		if ("N".equals(recarga)){
				//Si no es una recarga inicializamos la página a la primera.
				page = new Long(1);
				
		        Pagination pagination= new Pagination(max,page);
		        if (index!=null){
		            pagination.setSort(index);
		        }
		        if (sortOrder!=null){
		            pagination.setAscDsc(sortOrder);
		        }
			List<ExclusionVentas> list = null;
			List<CamposSeleccionadosExclVenta> listaSeleccionados = new ArrayList<CamposSeleccionadosExclVenta>();
			
			list = this.obtenerTablaSesion(session.getId(), exclusionVentas.getCodCentro(), null);
			
			//Si sólo tiene una linea con error y sin identificador se trata como un error general
			if (list != null && list.size() == 1){
				ExclusionVentas campo = new ExclusionVentas();
				campo = (ExclusionVentas)list.get(0);
				
				if (campo==null || campo.getIdentificador()==null || (new Long(0).equals(campo.getIdentificador()) && new Long(0).equals(campo.getIdentificadorSIA()))){
					if (campo!=null && (campo.getIdentificador()==null || (new Long(0).equals(campo.getIdentificador()) && new Long(0).equals(campo.getIdentificadorSIA())))&& campo.getCodError()!=null && !new Long(0).equals(campo.getCodError())){
						//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
						exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
						exclusionVentasPagina.setCodError(campo.getCodError());
						if (campo.getCodError()>0){//Pet. 53818
							if (campo.getDescripError()!= null && !campo.getDescripError().trim().equals("")){
								exclusionVentasPagina.setDescError(campo.getDescripError());
							}else{
								exclusionVentasPagina.setDescError(this.messageSource.getMessage("p73_exclusionVentas.errorWSObtenerExclusionVentas",null, locale));
							}
						}
					}else{
						exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
						exclusionVentasPagina.setCodError(new Long(1));
						exclusionVentasPagina.setDescError(this.messageSource.getMessage("p73_exclusionVentas.errorWSObtenerExclusionVentas",null, locale));
					}
					return exclusionVentasPagina;
				}
			}
			
			//La primera vez que cargamos tenemos que crear la lista con las referencias inicialmente no seleccionadas.
			CamposSeleccionadosExclVenta campoSelecc = new CamposSeleccionadosExclVenta();
			if (list != null) {
				//Nos recorremos la lista.
				ExclusionVentas campo = new ExclusionVentas();
				for (int i =0;i<list.size();i++)
				{
					campo = (ExclusionVentas)list.get(i);
					
					//Además creamos la lista con las referencias inicialmente no seleccionadas.
					campoSelecc = new CamposSeleccionadosExclVenta();
					campoSelecc.setCodCentro(campo.getCodCentro());
					campoSelecc.setCodArt(campo.getCodArt());
					campoSelecc.setGrupo1(campo.getGrupo1());
					campoSelecc.setGrupo2(campo.getGrupo2());
					campoSelecc.setGrupo3(campo.getGrupo3());
					campoSelecc.setGrupo4(campo.getGrupo4());
					campoSelecc.setGrupo5(campo.getGrupo5());
					campoSelecc.setIdentificador(campo.getIdentificador());
					campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
					campoSelecc.setFecha(campo.getFecha());
					campoSelecc.setSeleccionado("N");
					listaSeleccionados.add(i, campoSelecc);
				}
			}

			//Volvemos a obtener la lista guardada en la tabla temporal.
			
			//Montaje de lista paginada
			Page<ExclusionVentas> listaExclusionVentasPaginada = null;
			
			if (list != null) {
				int records = list.size();
				listaExclusionVentasPaginada = this.paginationManager.paginate(new Page<ExclusionVentas>(), list,
						max.intValue(), records, page.intValue());	
				exclusionVentasPagina.setDatos(listaExclusionVentasPaginada);
				exclusionVentasPagina.setListadoSeleccionados(listaSeleccionados);
				exclusionVentasPagina.setCodError(new Long(0));
				
			} else {
				exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
			}
			return exclusionVentasPagina;

		}
		else
		{
			return loadDataGridRecarga(exclusionVentas, page, max, index, sortOrder, response, session);
		}
	}
	
	@RequestMapping(value = "/loadDataGridRecarga", method = RequestMethod.POST)
	public  @ResponseBody ExclusionVentasPagina loadDataGridRecarga(
			@RequestBody ExclusionVentas exclusionVentas,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
	        Pagination pagination= new Pagination(max,page);
	        if (index!=null){
	            pagination.setSort(index);
	        }
	        if (sortOrder!=null){
	            pagination.setAscDsc(sortOrder);
	        }
		
	        List<ExclusionVentas> listaGuardada = null;
	        List<ExclusionVentas> listaRecarga = new ArrayList<ExclusionVentas>();
	        int records = 0;

			try {
				Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
				Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
				listaGuardada = this.obtenerTablaSesion(session.getId(), exclusionVentas.getCodCentro(), pagination);
				
				//Se permite ordenar por cualquier campo
				if (index!=null && !index.equals("null"))
				{
					listaGuardada = this.obtenerTablaSesion(session.getId(), exclusionVentas.getCodCentro(), pagination);
				}
				
				if (listaGuardada != null){ 
					records = listaGuardada.size();
					if (elemInicio <= records){
						if (elemFinal > records){
							elemFinal = new Long(records);
						}
						listaRecarga = (listaGuardada).subList(Integer.parseInt(elemInicio.toString()), Integer.parseInt(elemFinal.toString()));
					}
				}
				
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			Page<ExclusionVentas> listaExclusionVentasPaginada = null;
			ExclusionVentasPagina exclusionVentasPagina = new ExclusionVentasPagina();
			
			if (listaRecarga != null) {
				listaExclusionVentasPaginada = this.paginationManager.paginate(new Page<ExclusionVentas>(), listaRecarga,
						max.intValue(), records, page.intValue());	
				exclusionVentasPagina.setDatos(listaExclusionVentasPaginada);
				exclusionVentasPagina.setListadoSeleccionados(exclusionVentas.getListadoSeleccionados());
				exclusionVentasPagina.setCodError(new Long(0));
			} else {
				exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
			}
			
			return exclusionVentasPagina;
	}
	
	@RequestMapping(value = "/removeDataGrid", method = RequestMethod.POST)
	public  @ResponseBody ExclusionVentasPagina removeDataGrid(
			@RequestBody ExclusionVentas exclusionVentas,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
	        Pagination pagination= new Pagination(max,page);
	        if (index!=null){
	            pagination.setSort(index);
	        }
	        if (sortOrder!=null){
	            pagination.setAscDsc(sortOrder);
	        }
		
	        List<ExclusionVentas> listaGuardada = null;
	        List<CamposSeleccionadosExclVenta> listaCamposSeleccionados = null;
	        List<CamposSeleccionadosExclVenta> listaSeleccionados = new ArrayList<CamposSeleccionadosExclVenta>();
	        List<ExclusionVentas> listaExclusionVentas_Borrar = new ArrayList<ExclusionVentas>();
	        List<ExclusionVentas> listaExclusionVentasRespuesta_Borrables = new ArrayList<ExclusionVentas>();
	        
	        Locale locale = LocaleContextHolder.getLocale();
	        ExclusionVentasPagina exclusionVentasPagina = new ExclusionVentasPagina();
	        
	        try {
	        	//Reseteamos todos los posibles errores previos.
				this.resetearErrores(session.getId(), exclusionVentas.getCodCentro());
				
				//En primer lugar nos recorremos la lista de registros seleccionados.
				listaCamposSeleccionados = exclusionVentas.getListadoSeleccionados();
				listaExclusionVentas_Borrar = this.cargarRegistrosSeleccionados(session.getId(), exclusionVentas.getCodCentro(), null, listaCamposSeleccionados);
				
				try {	
					//Hacemos el borrado atómico de los seleccionados 
					listaExclusionVentasRespuesta_Borrables = this.exclusionVentasService.removeAll(listaExclusionVentas_Borrar);
				} catch (Exception e) {
					//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
					exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
					exclusionVentasPagina.setCodError(new Long(1));
					exclusionVentasPagina.setDescError(this.messageSource.getMessage("p73_exclusionVentas.errorWSBorrarExclusionVentas",null, locale));
					return exclusionVentasPagina;
				}
				
				//Se obtienen los datos actuales tras el borrado desde el WS
				List<ExclusionVentas> lstExclusionVentas  = loadWSData(exclusionVentas.getCodCentro(), session.getId());
				
				//Con la lista que nos devuelve el WS, nos tenemos que recorrer la lista guardada en sesión
				//avisando en los que no se ha podido borrar.
				for (int j=0;j<listaExclusionVentasRespuesta_Borrables.size();j++){

					if (listaExclusionVentasRespuesta_Borrables.get(j).getCodError() != null && 
							!new Long(0).equals(listaExclusionVentasRespuesta_Borrables.get(j).getCodError()))
					{
						//Tenemos que añadir el código de error
						this.errorRegistroTablaSesion(session.getId(), listaExclusionVentasRespuesta_Borrables.get(j));
					}
				}
				
				//Volvemos a obtener la lista
				listaGuardada = this.obtenerTablaSesion(session.getId(), exclusionVentas.getCodCentro(),null);

				//Si sólo tiene una linea con error y sin identificador se trata como un error general
				if (listaGuardada != null && listaGuardada.size() == 1){
					ExclusionVentas campo = new ExclusionVentas();
					campo = (ExclusionVentas)listaGuardada.get(0);
					
					if (campo==null || campo.getIdentificador()==null || (new Long(0).equals(campo.getIdentificador()) && new Long(0).equals(campo.getIdentificadorSIA()))){
						if (campo!=null && (campo.getIdentificador()==null || (new Long(0).equals(campo.getIdentificador()) && new Long(0).equals(campo.getIdentificadorSIA())))&& campo.getCodError()!=null && !new Long(0).equals(campo.getCodError())){
							//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
							exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
							exclusionVentasPagina.setCodError(campo.getCodError());
							if (campo.getCodError()>0){//Pet. 53818
								if (campo.getDescripError()!= null && !campo.getDescripError().trim().equals("")){
									exclusionVentasPagina.setDescError(campo.getDescripError());
								}else{
									exclusionVentasPagina.setDescError(this.messageSource.getMessage("p73_exclusionVentas.errorWSObtenerExclusionVentas",null, locale));
								}
							}
						}else{
							exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
							exclusionVentasPagina.setCodError(new Long(1));
							exclusionVentasPagina.setDescError(this.messageSource.getMessage("p73_exclusionVentas.errorWSObtenerExclusionVentas",null, locale));
						}
						return exclusionVentasPagina;
					}
				}

				//Creamos la lista de seleccionados con la nueva lista con los registros sin seleccionar.
				CamposSeleccionadosExclVenta campoSelecc = new CamposSeleccionadosExclVenta();
				if (listaGuardada != null) {
					//Nos recorremos la lista.
					ExclusionVentas campo = new ExclusionVentas();
					for (int i =0;i<listaGuardada.size();i++)
					{
						campo = (ExclusionVentas)listaGuardada.get(i);
						
						//Además creamos la lista con las referencias inicialmente no seleccionadas.
						campoSelecc = new CamposSeleccionadosExclVenta();
						campoSelecc.setCodCentro(campo.getCodCentro());
						campoSelecc.setCodArt(campo.getCodArt());
						campoSelecc.setGrupo1(campo.getGrupo1());
						campoSelecc.setGrupo2(campo.getGrupo2());
						campoSelecc.setGrupo3(campo.getGrupo3());
						campoSelecc.setGrupo4(campo.getGrupo4());
						campoSelecc.setGrupo5(campo.getGrupo5());
						campoSelecc.setIdentificador(campo.getIdentificador());
						campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
						campoSelecc.setFecha(campo.getFecha());
						campoSelecc.setSeleccionado("N");
						listaSeleccionados.add(i, campoSelecc);
					}
				}

			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			//Montaje de lista paginada
			Page<ExclusionVentas> listaExclusionVentasPaginada = null;
			
			if (listaGuardada != null) {
				int records = listaGuardada.size();
				listaExclusionVentasPaginada = this.paginationManager.paginate(new Page<ExclusionVentas>(), listaGuardada,
						max.intValue(), records, page.intValue());	
				exclusionVentasPagina.setDatos(listaExclusionVentasPaginada);
				exclusionVentasPagina.setListadoSeleccionados(listaSeleccionados);
				exclusionVentasPagina.setCodError(new Long(0));
				
			} else {
				exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
			}
			return exclusionVentasPagina;
	}
	
	@RequestMapping(value = "/insertDataGrid", method = RequestMethod.POST)
	public  @ResponseBody ExclusionVentasPagina insertDataGrid(
			@RequestBody ExclusionVentas exclusionVentas,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
	        Pagination pagination= new Pagination(max,page);
	        if (index!=null){
	            pagination.setSort(index);
	        }
	        if (sortOrder!=null){
	            pagination.setAscDsc(sortOrder);
	        }
		
	        List<ExclusionVentas> listaGuardada = null;
	        List<CamposSeleccionadosExclVenta> listaCamposSeleccionados = null;
	        List<CamposSeleccionadosExclVenta> listaSeleccionados = new ArrayList<CamposSeleccionadosExclVenta>();
	        List<CamposSeleccionadosExclVenta> listaSeleccionadosNuevo = new ArrayList<CamposSeleccionadosExclVenta>();
	        List<ExclusionVentas> listaExclusionVentas_Insertar = new ArrayList<ExclusionVentas>();
	        List<ExclusionVentas> listaExclusionVentasCorrectos_Insertar = new ArrayList<ExclusionVentas>();
	        List<ExclusionVentas> listaExclusionVentasRespuesta_Nuevos = new ArrayList<ExclusionVentas>();

	        Locale locale = LocaleContextHolder.getLocale();
	        ExclusionVentasPagina exclusionVentasPagina = new ExclusionVentasPagina();
	        
	        try {
				//Antes de empezar tenemos que limpiar los posibles guardados mostrados anteriormente.
				this.eliminarTablaSesion(session.getId(), exclusionVentas.getCodCentro());

				//En primer lugar nos recorremos la lista de registros seleccionados.
				listaCamposSeleccionados = exclusionVentas.getListadoSeleccionados();
				listaExclusionVentas_Insertar = this.cargarRegistrosSeleccionados(session.getId(), exclusionVentas.getCodCentro(), exclusionVentas.getFecha(), listaCamposSeleccionados);

				try {	
					//Hacemos el insertado con los registros seleccionados
					listaExclusionVentasRespuesta_Nuevos = this.exclusionVentasService.insertAll(listaExclusionVentas_Insertar);
				} catch (Exception e) {
					e.printStackTrace();
					//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
					exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
					exclusionVentasPagina.setCodError(new Long(1));
					exclusionVentasPagina.setDescError(this.messageSource.getMessage("p73_exclusionVentas.errorWSInsertarExclusionVentas",null, locale));
					return exclusionVentasPagina;
				}
				
				//Se obtienen los datos actuales tras el borrado desde el WS
				List<ExclusionVentas> lstExclusionVentas  = loadWSData(exclusionVentas.getCodCentro(), session.getId());
				
				//Con la lista que nos devuelve el WS, nos tenemos que recorrer la lista guardada en sesión
				//avisando en los que no se ha podido borrar y los guardados correctamente
				if(lstExclusionVentas!=null){
					for (int j=0;j<listaExclusionVentasRespuesta_Nuevos.size();j++){

						if (listaExclusionVentasRespuesta_Nuevos.get(j).getCodError() != null && 
								!new Long(0).equals(listaExclusionVentasRespuesta_Nuevos.get(j).getCodError()))
						{
							//Tenemos que añadir el código de error
							this.errorRegistroTablaSesion(session.getId(), listaExclusionVentasRespuesta_Nuevos.get(j));
						}else{ //Los registros guardados correctamente los marcamos con -1
							listaExclusionVentasRespuesta_Nuevos.get(j).setCodError(new Long(-1));
							this.errorRegistroTablaSesion(session.getId(), listaExclusionVentasRespuesta_Nuevos.get(j));
						}
					}
				}
				
				
				//Volvemos a obtener la lista
				listaGuardada = this.obtenerTablaSesion(session.getId(), exclusionVentas.getCodCentro(),null);
				
				//Si sólo tiene una linea con error y sin identificador se trata como un error general
				if (listaGuardada != null && listaGuardada.size() == 1){
					ExclusionVentas campo = new ExclusionVentas();
					campo = (ExclusionVentas)listaGuardada.get(0);
					
					if (campo==null || campo.getIdentificador()==null || (new Long(0).equals(campo.getIdentificador()) && new Long(0).equals(campo.getIdentificadorSIA()))){
						if (campo!=null && (campo.getIdentificador()==null || (new Long(0).equals(campo.getIdentificador()) && new Long(0).equals(campo.getIdentificadorSIA())))&& campo.getCodError()!=null && !new Long(0).equals(campo.getCodError())){
							//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
							exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
							exclusionVentasPagina.setCodError(campo.getCodError());
							if (campo.getCodError()>0){//Pet. 53818
								if (campo.getDescripError()!= null && !campo.getDescripError().trim().equals("")){
									exclusionVentasPagina.setDescError(campo.getDescripError());
								}else{
									exclusionVentasPagina.setDescError(this.messageSource.getMessage("p73_exclusionVentas.errorWSObtenerExclusionVentas",null, locale));
								}
							}
						}else{
							exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
							exclusionVentasPagina.setCodError(new Long(1));
							exclusionVentasPagina.setDescError(this.messageSource.getMessage("p73_exclusionVentas.errorWSObtenerExclusionVentas",null, locale));
						}
						return exclusionVentasPagina;
					}
				}
				
				//Una vez realizadas las modificaciones creamos la lista de seleccionados con los registros sin seleccionar.
				//Creamos la lista de seleccionados con la nueva lista con los registros sin seleccionar.
				CamposSeleccionadosExclVenta campoSelecc = new CamposSeleccionadosExclVenta();
				if (listaGuardada != null) {
					//Nos recorremos la lista.
					ExclusionVentas campo = new ExclusionVentas();
					for (int i =0;i<listaGuardada.size();i++)
					{
						campo = (ExclusionVentas)listaGuardada.get(i);
						
						//Además creamos la lista con las referencias inicialmente no seleccionadas.
						campoSelecc = new CamposSeleccionadosExclVenta();
						campoSelecc.setCodCentro(campo.getCodCentro());
						campoSelecc.setCodArt(campo.getCodArt());
						campoSelecc.setGrupo1(campo.getGrupo1());
						campoSelecc.setGrupo2(campo.getGrupo2());
						campoSelecc.setGrupo3(campo.getGrupo3());
						campoSelecc.setGrupo4(campo.getGrupo4());
						campoSelecc.setGrupo5(campo.getGrupo5());
						campoSelecc.setIdentificador(campo.getIdentificador());
						campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());//Pet. 53818
						campoSelecc.setFecha(campo.getFecha());
						campoSelecc.setSeleccionado("N");
						listaSeleccionados.add(i, campoSelecc);
					}
				}

				//Se vuelve a cargar la lista con los seleccionados para la inserción (estructura)
				//indicando los mensajes de error posibles para notificarlo en pantalla
				CamposSeleccionadosExclVenta campoSeleccNuevo = new CamposSeleccionadosExclVenta();
				if (listaExclusionVentasRespuesta_Nuevos != null) {
					//Nos recorremos la lista.
					ExclusionVentas campo = new ExclusionVentas();
					for (int i =0;i<listaExclusionVentasRespuesta_Nuevos.size();i++)
					{
						campo = (ExclusionVentas)listaExclusionVentasRespuesta_Nuevos.get(i);
						
						//Además creamos la lista con las referencias inicialmente no seleccionadas.
						campoSeleccNuevo = new CamposSeleccionadosExclVenta();
						campoSeleccNuevo.setCodCentro(campo.getCodCentro());
						campoSeleccNuevo.setCodArt(campo.getCodArt());
						campoSeleccNuevo.setGrupo1(campo.getGrupo1());
						campoSeleccNuevo.setGrupo2(campo.getGrupo2());
						campoSeleccNuevo.setGrupo3(campo.getGrupo3());
						campoSeleccNuevo.setGrupo4(campo.getGrupo4());
						campoSeleccNuevo.setGrupo5(campo.getGrupo5());
						campoSeleccNuevo.setCodError(campo.getCodError());
						campoSeleccNuevo.setDescripError(campo.getDescripError());

						listaSeleccionadosNuevo.add(i, campoSeleccNuevo);
					}
				}

			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			//Montaje de lista paginada
			Page<ExclusionVentas> listaExclusionVentasPaginada = null;
			
			if (listaGuardada != null) {
				int records = listaGuardada.size();
				listaExclusionVentasPaginada = this.paginationManager.paginate(new Page<ExclusionVentas>(), listaGuardada,
						max.intValue(), records, page.intValue());	
				exclusionVentasPagina.setDatos(listaExclusionVentasPaginada);
				exclusionVentasPagina.setListadoSeleccionados(listaSeleccionados);
				exclusionVentasPagina.setCodError(new Long(0));
				
			} else {
				exclusionVentasPagina.setDatos(new Page<ExclusionVentas>());
			}
			//Lista con las estructuras seleccionada para la inserción
			exclusionVentasPagina.setListadoSeleccionadosNuevo(listaSeleccionadosNuevo);
			
			return exclusionVentasPagina;
	}	
	
	
	private void resetearErrores(String idSesion, Long codCentro){
	
		TExclusionVentas registro = new TExclusionVentas();
		
		registro.setIdSesion(idSesion);
		registro.setCodCentro(codCentro);
		
		try {
			this.tExclusionVentasService.updateErrores(registro);
		} catch (Exception e) {
			logger.error("resetearErrores="+e.toString());
			e.printStackTrace();
		}
	}

	private void eliminarTablaSesion(String idSesion, Long codCentro){
		
		TExclusionVentas registro = new TExclusionVentas();
		
		registro.setIdSesion(idSesion);
		registro.setCodCentro(codCentro);
		
		try {
			this.tExclusionVentasService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

	private void eliminarTablaSesionHistorico(){
		
		try {
			this.tExclusionVentasService.deleteHistorico();
		} catch (Exception e) {
			logger.error("eliminarTablaSesionHistorico="+e.toString());
			e.printStackTrace();
		}
	}

	private void eliminarRegistroTablaSesion(String idSesion, ExclusionVentas exclusionVentas){
		
		TExclusionVentas registro = new TExclusionVentas();
		
		registro.setIdSesion(idSesion);
		registro.setCodCentro((exclusionVentas.getCodCentro() != null && !("".equals(exclusionVentas.getCodCentro().toString())))?new Long(exclusionVentas.getCodCentro().toString()):null);
		registro.setCodArt((exclusionVentas.getCodArt() != null && !("".equals(exclusionVentas.getCodArt().toString())))?new Long(exclusionVentas.getCodArt().toString()):null);
		registro.setDescripArt((exclusionVentas.getDescripArt() != null && !("".equals(exclusionVentas.getDescripArt())))?exclusionVentas.getDescripArt():null);
		registro.setGrupo1((exclusionVentas.getGrupo1() != null && !("".equals(exclusionVentas.getGrupo1().toString())))?new Long(exclusionVentas.getGrupo1().toString()):null);
		registro.setGrupo2((exclusionVentas.getGrupo2() != null && !("".equals(exclusionVentas.getGrupo2().toString())))?new Long(exclusionVentas.getGrupo2().toString()):null);
		registro.setGrupo3((exclusionVentas.getGrupo3() != null && !("".equals(exclusionVentas.getGrupo3().toString())))?new Long(exclusionVentas.getGrupo3().toString()):null);
		registro.setGrupo4((exclusionVentas.getGrupo4() != null && !("".equals(exclusionVentas.getGrupo4().toString())))?new Long(exclusionVentas.getGrupo4().toString()):null);
		registro.setGrupo5((exclusionVentas.getGrupo5() != null && !("".equals(exclusionVentas.getGrupo5().toString())))?new Long(exclusionVentas.getGrupo5().toString()):null);
		
		try {
			this.tExclusionVentasService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarRegistroTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

	private void errorRegistroTablaSesion(String idSesion, ExclusionVentas exclusionVentas){
		
		TExclusionVentas registro = new TExclusionVentas();
		
		registro.setIdSesion(idSesion);
		registro.setCodCentro((exclusionVentas.getCodCentro() != null && !("".equals(exclusionVentas.getCodCentro().toString())))?new Long(exclusionVentas.getCodCentro().toString()):null);
		registro.setCodArt((exclusionVentas.getCodArt() != null && !("".equals(exclusionVentas.getCodArt().toString())))?new Long(exclusionVentas.getCodArt().toString()):null);
		registro.setDescripArt((exclusionVentas.getDescripArt() != null && !("".equals(exclusionVentas.getDescripArt())))?exclusionVentas.getDescripArt():null);
		registro.setGrupo1((exclusionVentas.getGrupo1() != null && !("".equals(exclusionVentas.getGrupo1().toString())))?new Long(exclusionVentas.getGrupo1().toString()):null);
		registro.setGrupo2((exclusionVentas.getGrupo2() != null && !("".equals(exclusionVentas.getGrupo2().toString())))?new Long(exclusionVentas.getGrupo2().toString()):null);
		registro.setGrupo3((exclusionVentas.getGrupo3() != null && !("".equals(exclusionVentas.getGrupo3().toString())))?new Long(exclusionVentas.getGrupo3().toString()):null);
		registro.setGrupo4((exclusionVentas.getGrupo4() != null && !("".equals(exclusionVentas.getGrupo4().toString())))?new Long(exclusionVentas.getGrupo4().toString()):null);
		registro.setGrupo5((exclusionVentas.getGrupo5() != null && !("".equals(exclusionVentas.getGrupo5().toString())))?new Long(exclusionVentas.getGrupo5().toString()):null);
		registro.setCodError((exclusionVentas.getCodError() != null && !("".equals(exclusionVentas.getCodError().toString())))?new Long(exclusionVentas.getCodError().toString()):null);
		registro.setDescripError((exclusionVentas.getDescripError() != null && !("".equals(exclusionVentas.getDescripError())))?exclusionVentas.getDescripError():null);
		registro.setIdentificador((exclusionVentas.getIdentificador() != null && !("".equals(exclusionVentas.getIdentificador().toString())))?new Long(exclusionVentas.getIdentificador().toString()):null);
		registro.setIdentificadorSIA((exclusionVentas.getIdentificadorSIA() != null && !("".equals(exclusionVentas.getIdentificadorSIA().toString())))?new Long(exclusionVentas.getIdentificadorSIA().toString()):null);
		registro.setFecha(Utilidades.convertirStringAFecha(exclusionVentas.getFecha()));
		
		try {
			this.tExclusionVentasService.updateErrores(registro);
		} catch (Exception e) {
			logger.error("errorRegistroTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

	private void insertarTablaSesion(List<ExclusionVentas> list, String idSesion, Long codCentro){
	
		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TExclusionVentas> listaTExclusionVentas = new ArrayList<TExclusionVentas>();
		
		//Nos recorremos la lista.
		ExclusionVentas registro = new ExclusionVentas();
		TExclusionVentas nuevoRegistro = new TExclusionVentas();
		for (int i =0;i<list.size();i++)
		{
			nuevoRegistro = new TExclusionVentas();
			
			registro = (ExclusionVentas)list.get(i);
			
			nuevoRegistro.setIdSesion(idSesion);
			nuevoRegistro.setCodCentro(codCentro);
			nuevoRegistro.setCodArt((registro.getCodArt() != null && !("".equals(registro.getCodArt().toString())))?new Long(registro.getCodArt().toString()):null);
			nuevoRegistro.setDescripArt((registro.getDescripArt() != null && !("".equals(registro.getDescripArt())))?registro.getDescripArt():null);
			nuevoRegistro.setGrupo1((registro.getGrupo1() != null && !("".equals(registro.getGrupo1().toString())))?new Long(registro.getGrupo1().toString()):null);
			nuevoRegistro.setGrupo2((registro.getGrupo2() != null && !("".equals(registro.getGrupo2().toString())))?new Long(registro.getGrupo2().toString()):null);
			nuevoRegistro.setGrupo3((registro.getGrupo3() != null && !("".equals(registro.getGrupo3().toString())))?new Long(registro.getGrupo3().toString()):null);
			nuevoRegistro.setGrupo4((registro.getGrupo4() != null && !("".equals(registro.getGrupo4().toString())))?new Long(registro.getGrupo4().toString()):null);
			nuevoRegistro.setGrupo5((registro.getGrupo5() != null && !("".equals(registro.getGrupo5().toString())))?new Long(registro.getGrupo5().toString()):null);
			nuevoRegistro.setDescripGrupo1((registro.getDescripGrupo1() != null && !("".equals(registro.getDescripGrupo1())))?registro.getDescripGrupo1():null);
			nuevoRegistro.setDescripGrupo2((registro.getDescripGrupo2() != null && !("".equals(registro.getDescripGrupo2())))?registro.getDescripGrupo2():null);
			nuevoRegistro.setDescripGrupo3((registro.getDescripGrupo3() != null && !("".equals(registro.getDescripGrupo3())))?registro.getDescripGrupo3():null);
			nuevoRegistro.setDescripGrupo4((registro.getDescripGrupo4() != null && !("".equals(registro.getDescripGrupo4())))?registro.getDescripGrupo4():null);
			nuevoRegistro.setDescripGrupo5((registro.getDescripGrupo5() != null && !("".equals(registro.getDescripGrupo5())))?registro.getDescripGrupo5():null);
			nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
			nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
			nuevoRegistro.setFecha((registro.getFecha() != null && !("".equals(registro.getFecha())))?Utilidades.convertirStringAFecha(registro.getFecha()):null);
			nuevoRegistro.setFechaGen(new Date());
			nuevoRegistro.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError().toString())))?new Long(registro.getCodError().toString()):null);
			nuevoRegistro.setDescripError((registro.getDescripError() != null && !("".equals(registro.getDescripError())))?registro.getDescripError():null);

			listaTExclusionVentas.add(nuevoRegistro);
		}
		
		try {
			this.tExclusionVentasService.insertAll(listaTExclusionVentas);
		} catch (Exception e) {
			logger.error("insertarTablaSesion="+e.toString());
			e.printStackTrace();
		}
			
	}

	private List<ExclusionVentas> obtenerTablaSesion(String idSesion, Long codCentro, Pagination pagination){
	
		List<ExclusionVentas> listaExclusionVentas = new ArrayList<ExclusionVentas>();
		TExclusionVentas registro = new TExclusionVentas();
		
		registro.setIdSesion(idSesion);
		registro.setCodCentro(codCentro);
		
		ExclusionVentas nuevoRegistro = new ExclusionVentas();
		
		try {
			TExclusionVentas tExclusionVentas = new TExclusionVentas();
			tExclusionVentas.setIdSesion(idSesion);
			tExclusionVentas.setCodCentro(codCentro);
			List<TExclusionVentas> listaTExclusionVentas = this.tExclusionVentasService.findAllPaginate(tExclusionVentas, pagination);
			
			if (listaTExclusionVentas != null && listaTExclusionVentas.size()>0)
			{
				for (int i =0;i<listaTExclusionVentas.size();i++)
				{
					nuevoRegistro = new ExclusionVentas();
					
					registro = (TExclusionVentas)listaTExclusionVentas.get(i);
					
					nuevoRegistro.setCodCentro(codCentro);
					nuevoRegistro.setCodArt((registro.getCodArt() != null && !("".equals(registro.getCodArt().toString())))?new Long(registro.getCodArt().toString()):null);
					nuevoRegistro.setDescripArt((registro.getDescripArt() != null && !("".equals(registro.getDescripArt())))?registro.getDescripArt():null);
					nuevoRegistro.setGrupo1((registro.getGrupo1() != null && !("".equals(registro.getGrupo1().toString())))?new Long(registro.getGrupo1().toString()):null);
					nuevoRegistro.setGrupo2((registro.getGrupo2() != null && !("".equals(registro.getGrupo2().toString())))?new Long(registro.getGrupo2().toString()):null);
					nuevoRegistro.setGrupo3((registro.getGrupo3() != null && !("".equals(registro.getGrupo3().toString())))?new Long(registro.getGrupo3().toString()):null);
					nuevoRegistro.setGrupo4((registro.getGrupo4() != null && !("".equals(registro.getGrupo4().toString())))?new Long(registro.getGrupo4().toString()):null);
					nuevoRegistro.setGrupo5((registro.getGrupo5() != null && !("".equals(registro.getGrupo5().toString())))?new Long(registro.getGrupo5().toString()):null);
					nuevoRegistro.setDescripGrupo1((registro.getDescripGrupo1() != null && !("".equals(registro.getDescripGrupo1())))?registro.getDescripGrupo1():null);
					nuevoRegistro.setDescripGrupo2((registro.getDescripGrupo2() != null && !("".equals(registro.getDescripGrupo2())))?registro.getDescripGrupo2():null);
					nuevoRegistro.setDescripGrupo3((registro.getDescripGrupo3() != null && !("".equals(registro.getDescripGrupo3())))?registro.getDescripGrupo3():null);
					nuevoRegistro.setDescripGrupo4((registro.getDescripGrupo4() != null && !("".equals(registro.getDescripGrupo4())))?registro.getDescripGrupo4():null);
					nuevoRegistro.setDescripGrupo5((registro.getDescripGrupo5() != null && !("".equals(registro.getDescripGrupo5())))?registro.getDescripGrupo5():null);
					nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
					nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
					nuevoRegistro.setFecha((registro.getFecha() != null && !("".equals(registro.getFecha())))?Utilidades.formatearFecha(registro.getFecha()):null);
					nuevoRegistro.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError().toString())))?new Long(registro.getCodError().toString()):null);
					nuevoRegistro.setDescripError((registro.getDescripError() != null && !("".equals(registro.getDescripError())))?registro.getDescripError():null);
					
					listaExclusionVentas.add(nuevoRegistro);
				}
			}
		} catch (Exception e) {
			logger.error("obtenerTablaSesion="+e.toString());
			e.printStackTrace();
		}
			
		return listaExclusionVentas;
	}
	
	private List<ExclusionVentas> cargarRegistrosSeleccionados(String idSesion, Long codCentro, String fecha, List<CamposSeleccionadosExclVenta> listaCamposSeleccionados){
		
		List<ExclusionVentas> listaExclusionVentas = new ArrayList<ExclusionVentas>();
		ExclusionVentas nuevoRegistro = new ExclusionVentas();
		CamposSeleccionadosExclVenta registro = new CamposSeleccionadosExclVenta();
		
		try {

			if (listaCamposSeleccionados != null && listaCamposSeleccionados.size()>0)
			{
				for (int i =0;i<listaCamposSeleccionados.size();i++)
				{
					nuevoRegistro = new ExclusionVentas();
					
					registro = (CamposSeleccionadosExclVenta)listaCamposSeleccionados.get(i);
					if (registro.getSeleccionado()!=null && registro.getSeleccionado().equals("S")){
						nuevoRegistro.setCodCentro(codCentro);
						nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
						nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
						nuevoRegistro.setCodArt((registro.getCodArt() != null && !("".equals(registro.getCodArt().toString())))?new Long(registro.getCodArt().toString()):null);
						nuevoRegistro.setGrupo1((registro.getGrupo1() != null && !("".equals(registro.getGrupo1().toString())))?new Long(registro.getGrupo1().toString()):null);
						nuevoRegistro.setGrupo2((registro.getGrupo2() != null && !("".equals(registro.getGrupo2().toString())))?new Long(registro.getGrupo2().toString()):null);
						nuevoRegistro.setGrupo3((registro.getGrupo3() != null && !("".equals(registro.getGrupo3().toString())))?new Long(registro.getGrupo3().toString()):null);
						nuevoRegistro.setGrupo4((registro.getGrupo4() != null && !("".equals(registro.getGrupo4().toString())))?new Long(registro.getGrupo4().toString()):null);
						nuevoRegistro.setGrupo5((registro.getGrupo5() != null && !("".equals(registro.getGrupo5().toString())))?new Long(registro.getGrupo5().toString()):null);
						
						if (fecha != null){//Es una inserción
							nuevoRegistro.setFecha(fecha);
						}else{
							nuevoRegistro.setFecha((registro.getFecha() != null && !("".equals(registro.getFecha())))?registro.getFecha():null);
						}
						listaExclusionVentas.add(nuevoRegistro);
					}					
				}
			}
		} catch (Exception e) {
			logger.error("cargarRegistrosSeleccionados="+e.toString());
			e.printStackTrace();
		}
			
		return listaExclusionVentas;
	}
}