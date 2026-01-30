package es.eroski.misumi.control;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionCmbSeccion;
import es.eroski.misumi.model.ReposicionGuardar;
import es.eroski.misumi.model.ReposicionLinea;
import es.eroski.misumi.model.ReposicionSeccion;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaModType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.ListadoReposicionService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
public class pdaP92ListadoRepoAntController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP92ListadoRepoAntController.class);

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private FotosReferenciaService fotosReferenciaService;

	@Autowired
	private ListadoReposicionService listadoReposicionService;

	@Autowired
	private StockTiendaService correccionStockService;

	@RequestMapping(value = "/pdaP92ListadoRepoAnt",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab,ModelMap model,
			@RequestParam(value = "pagina", required = false, defaultValue = "1") Long pagina,
			@RequestParam(value = "pgSubList", required = false, defaultValue = "1") Long pageSubList,
			@RequestParam(value = "max", required = false, defaultValue = "4") Long maxSubList,
			@RequestParam(value = "botPag", required = false, defaultValue = "") String botPag,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) {

		String resultado = "pda_p92_listRepoAnt";
		try {
			//Obtenemos el area de la sesión.
			String area = (String) session.getAttribute("area");

			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();
			String codMac = user.getMac();

			//Obtenemos el articulo de la cabercera para obtener el listado de reposicines que corresponda.

			//Tratamos la referencia 
			Long codArticulo = null;
			if (pdaDatosCab != null && pdaDatosCab.getCodArtCab() != null && !"".equals(pdaDatosCab.getCodArtCab().trim())){
				try{
					//Obtención de la referencia a filtrar
					PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
					String codigoError = pdaArticulo.getCodigoError();
					if (codigoError == null || codigoError.equals("0")){
						codArticulo = new Long(pdaArticulo.getCodArt());
					}else{
						Locale locale = LocaleContextHolder.getLocale();

						redirectAttributes.addAttribute("origen", "pdaP92ListadoRepoAnt.do");
						redirectAttributes.addAttribute("error", this.messageSource.getMessage("pda_p92_eanErroneo", null, locale));
						redirectAttributes.addAttribute("paginaActual", pagina);
						redirectAttributes.addAttribute("pgSubList", pageSubList);

						return "redirect:pdaP94Error.do";	
					}
				}catch(Exception e){ //Si hay error en carga de referencia se devuelve la lista vacía
				}
			}

			//Inicializamos el objeto reposicion para la busqueda
			Reposicion reposicionfiltro = new Reposicion();
			ReposicionLinea reposicionLinea = new ReposicionLinea();
			reposicionLinea.setCodArticulo(codArticulo); //Añadimos la referecia que se ha metido  en la caja de referencia y de la cual se quiere obtener el listadp de reposicion
			reposicionfiltro.setCodLoc(codCentro);
			reposicionfiltro.setCodMac(codMac);	
			//Obtenemos de sesion el tipo de listado
			Long tipoListado = (Long) session.getAttribute("tipoListado");
			reposicionfiltro.setTipoListado(tipoListado);
			reposicionfiltro.setArea(area);
			reposicionfiltro.setSeccion(pdaDatosCab.getSeccion());
			//	reposicion.setPosicion(0L);//Obtenemos el primer elemento.

			List<ReposicionLinea> reposicionLineas = new ArrayList<ReposicionLinea>();
			reposicionLineas.add(reposicionLinea);

			reposicionfiltro.setReposicionLineas(reposicionLineas);
			
			//Obtenemos la reposición
			Reposicion reposicion = listadoReposicionService.obtenerReposicion(reposicionfiltro);

			//Se guardan en sesión
			//model.addAttribute("secciones", this.loadComboSecciones(reposicionfiltro));					
			LinkedHashMap<String, String> secciones = (LinkedHashMap<String, String>) session.getAttribute("secciones");
			if(secciones == null){
				session.setAttribute("secciones", this.loadComboSecciones(reposicionfiltro));
			}
			
			//Cargado de la temporal de reposiciones T_MIS_LISTADO_REPO_TEMP

			if ((reposicion != null) && (reposicion.getCodError() == 0))    {
				//1. Borrar la temporal de las lineas de la reposicion. Cada vez que se consulta una nueva referencia borramos los datos de la temporal, Filtrando por codMac.
				this.listadoReposicionService.eliminarTempListadoRepo(codMac);


				//2. Insertar las lineas de la reposicion en la tabla temporal T_MIS_LISTADO_REPO_TEMP para la nueva referencia consultada. Si es textil puede que tenga mas de de una linea, una por cada talla/color del modelo proveedor.
				//Si no es textil tendra una sola linea

				//this.listadoReposicionService.insertarTempListadoRepo(reposicion.getReposicionLineas(), reposicion.getModeloProveedor(), reposicion.getDescrColor()); //los dos ultimos parametros son a nivel de Reposicion pero los insertamos tambien en la tabla temporal
				this.listadoReposicionService.insertarTempListadoRepo(reposicion); //los dos ultimos parametros son a nivel de Reposicion pero los insertamos tambien en la tabla temporal

				//3. Obtener la lista con las lineas de la reposicion paginada 
				if (botPag.equals("prevPag")) {
					pageSubList = pageSubList -1;	
				} else if (botPag.equals("nextPag")){
					pageSubList = pageSubList +1;		
				}

				int maximoPantalla = Integer.parseInt(maxSubList.toString()) + 1;;
				if(maximoPantalla == reposicion.getReposicionLineas().size()){
					maxSubList = new Long(maximoPantalla);
				}
				Pagination pagination= new Pagination(maxSubList, pageSubList); 		
				List<ReposicionLinea> reposicionLineasPaginada =  this.listadoReposicionService.findTempListadoRepo(reposicion, pagination);	

				//Añadimos la sublista paginada a la Reposicion
				reposicion.setReposicionLineas(reposicionLineasPaginada);
				session.setAttribute("reposicionLineasPaginada", reposicionLineasPaginada);

				//Control de paginacion de la sublista
				Long totalReg=this.listadoReposicionService.countTempListadoRepo(codMac);  

				Page<ReposicionLinea> pageSubListaReposicion = null;
				PaginationManager<ReposicionLinea> paginationManager = new PaginationManagerImpl<ReposicionLinea>();
				pageSubListaReposicion = paginationManager.paginate(new Page<ReposicionLinea>(), reposicionLineasPaginada, maxSubList.intValue(), totalReg.intValue(), pageSubList.intValue());	


				model.addAttribute("pdaDatosCab", pdaDatosCab);

				//La añadimos al modelo la reposición.
				model.addAttribute("reposicion", reposicion);
				//La añadimos al modelo la sublista de tallas.
				model.addAttribute("pageSubListaReposicion", pageSubListaReposicion);

			}else{
				//Se guardan en sesión
				//model.addAttribute("secciones", null);
				session.setAttribute("secciones", null);
				
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("msgError", reposicion.getDescError());
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultado;
	}

	@RequestMapping(value = "/pdaP92ListadoRepoAnt",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value = "guardadoStockOk", required = false) String guardadoStockOk,
			@RequestParam(value = "seccion", required = false) String seccion,
			@RequestParam(value = "operativaCero", required = false, defaultValue = "N") String operativaCero,
			@RequestParam(value = "pagina", required = false, defaultValue = "1") Long pagina,
			@RequestParam(value = "pgSubList", required = false, defaultValue = "1") Long pageSubList,
			@RequestParam(value = "max", required = false, defaultValue = "4") Long maxSubList,
			@RequestParam(value = "botPag", required = false, defaultValue = "") String botPag,
			@RequestParam(value = "sigPosVacia", required = false, defaultValue = "") String sigPosVacia,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) {
		// Validar objeto
		String resultado = "pda_p92_listRepoAnt";
		try {
			if(("N").equals(operativaCero)){

				//Puede que el area sea NO_TEXTIL de repo1 y que venga de ahí, por lo que no 
				//queremos que sea textil en caso de venir al finalizar repo1.

				//Obtenemos el area de la sesión.
				String area = (String) session.getAttribute("area");
				if(area == null){
					session.setAttribute("area", Constantes.VENTA_REPO_AREA_TEXTIL);
					area = (String) session.getAttribute("area");
				}

				//Obtener reposicion
				User user = (User) session.getAttribute("user");
				Long codCentro = user.getCentro().getCodCentro();

				String codMac = user.getMac();

				//Inicializamos el objeto reposicion para la busqueda
				Reposicion reposicionfiltro = new Reposicion();
				ReposicionLinea reposicionLinea = new ReposicionLinea();
				reposicionfiltro.setCodLoc(codCentro);
				reposicionfiltro.setCodMac(codMac);	

				//Obtenemos de sesion el tipo de listado
				Long tipoListado = (Long) session.getAttribute("tipoListado");
				if (tipoListado == null) {
					tipoListado = 2L;
					session.setAttribute("tipoListado", tipoListado);
				}
				reposicionfiltro.setTipoListado(tipoListado);
				reposicionfiltro.setPosicion(pagina);
				//Obtenemos el area de la sesión.
				//String area = (String) session.getAttribute("area");
				reposicionfiltro.setArea(area);
				reposicionfiltro.setSeccion(seccion);

				if (sigPosVacia.equals("S")) { //Mostrar la siguente posicion vacia
					reposicionfiltro.setFlgSigPosVacia(sigPosVacia);
				} else {
					reposicionfiltro.setFlgSigPosVacia(null);
				}

				List<ReposicionLinea> reposicionLineas = new ArrayList<ReposicionLinea>();
				reposicionLineas.add(reposicionLinea);

				reposicionfiltro.setReposicionLineas(reposicionLineas);

				//Obtenemos la reposición
				Reposicion reposicion = listadoReposicionService.obtenerReposicion(reposicionfiltro);

				//Obtenemos la lista de secciones de reposicion
				
				
				//Se guardan en sesión
				//model.addAttribute("secciones", this.loadComboSecciones(reposicionfiltro));					
				LinkedHashMap<String, String> secciones = (LinkedHashMap<String, String>) session.getAttribute("secciones");
				if(secciones == null){
					session.setAttribute("secciones", this.loadComboSecciones(reposicionfiltro));
				}
				
				//Cargado de la temporal de reposiciones T_MIS_LISTADO_REPO_TEMP

				if ((reposicion != null) && (reposicion.getCodError() == 0))    {
					//1. Borrar la temporal de las lineas de la reposicion. Cada vez que se consulta una nueva referencia borramos los datos de la temporal, Filtrando por codMac.
					this.listadoReposicionService.eliminarTempListadoRepo(codMac);


					//2. Insertar las lineas de la reposicion en la tabla temporal T_MIS_LISTADO_REPO_TEMP para la nueva referencia consultada. Si es textil puede que tenga mas de de una linea, una por cada talla/color del modelo proveedor.
					//Si no es textil tendra una sola linea

					//this.listadoReposicionService.insertarTempListadoRepo(reposicion.getReposicionLineas(), reposicion.getModeloProveedor(), reposicion.getDescrColor()); //los dos ultimos parametros son a nivel de Reposicion pero los insertamos tambien en la tabla temporal
					this.listadoReposicionService.insertarTempListadoRepo(reposicion); //los dos ultimos parametros son a nivel de Reposicion pero los insertamos tambien en la tabla temporal

					//3. Obtener la lista con las lineas de la reposicion paginada 
					if (botPag.equals("prevPag")) {
						pageSubList = pageSubList -1;	
					} else if (botPag.equals("nextPag")){
						pageSubList = pageSubList +1;		
					}

					int maximoPantalla = Integer.parseInt(maxSubList.toString()) + 1;
					if(maximoPantalla == reposicion.getReposicionLineas().size()){
						maxSubList = new Long(maximoPantalla);
					}
					Pagination pagination= new Pagination(maxSubList, pageSubList); 		
					List<ReposicionLinea> reposicionLineasPaginada =  this.listadoReposicionService.findTempListadoRepo(reposicion, pagination);	

					//Añadimos la sublista paginada a la Reposicion
					reposicion.setReposicionLineas(reposicionLineasPaginada);
					session.setAttribute("reposicionLineasPaginada", reposicionLineasPaginada);

					//Control de paginacion de la sublista
					Long totalReg=this.listadoReposicionService.countTempListadoRepo(codMac);  

					Page<ReposicionLinea> pageSubListaReposicion = null;
					PaginationManager<ReposicionLinea> paginationManager = new PaginationManagerImpl<ReposicionLinea>();
					pageSubListaReposicion = paginationManager.paginate(new Page<ReposicionLinea>(), reposicionLineasPaginada, maxSubList.intValue(), totalReg.intValue(), pageSubList.intValue());	

					PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
					pdaDatosCab.setSeccion(seccion);
					
					//Para mostrar el link verde o azul de stock si ha tenido cambio.
					model.addAttribute("guardadoStockOk", guardadoStockOk);
					
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					
					//La añadimos al modelo la reposición.
					model.addAttribute("reposicion", reposicion);
					
					//La añadimos al modelo la sublista de tallas.
					model.addAttribute("pageSubListaReposicion", pageSubListaReposicion);
				}else{
					model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
					
					//Se guardan en sesión
					//model.addAttribute("secciones", null);	
					session.setAttribute("secciones", null);
					
					model.addAttribute("msgError", reposicion.getDescError());
				}
			}else{
				//Borramos todos los datos de la anterior para comenzar desde cero.
				pdaP92ListadoRepoAntBorrar(null, null, null, null, response, session, redirectAttributes);	
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}

	//Sirve para cargar los valores del combobox proveedor ordenados
	private  LinkedHashMap<String, String> loadComboSecciones(
			Reposicion reposicionFiltro) throws Exception{

		List<ReposicionSeccion> list = null;

		//Inicializamos la lista.
		list = new ArrayList<ReposicionSeccion>();

		ReposicionCmbSeccion reposicionCmbSeccion = listadoReposicionService.obtenerSecciones(reposicionFiltro);
		if(new Long(0).equals(reposicionCmbSeccion.getCodError())){
			list.addAll(reposicionCmbSeccion.getRepoSeccionLst());	
		}

		//Tratamiento para pasar la lista de secciones a pantalla
		LinkedHashMap<String, String> secciones = new LinkedHashMap<String, String>();
		//Opción para mostrado de todas las secciones
		secciones.put("", "");
		if (list != null && list.size() > 0){
			for (ReposicionSeccion seccion : list) {
				secciones.put(seccion.getSeccion(),seccion.getDescripcion());
			}
		}
		return secciones;
	}

	//Sirve para guardar los valores de los repos.
	@RequestMapping(value = "/pdaP92ListadoRepoAntGuardarStock", method = RequestMethod.POST)
	public @ResponseBody ModificarStockResponseType pdaP91ListadoRepoGuardarStock(
			@RequestParam(value = "stock", required = true) Double stock,
			@RequestParam(value = "codArt", required = true) Long codArt,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Obtener reposicion
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();


		ModificarStockRequestType modificarStockRequest = new ModificarStockRequestType();
		modificarStockRequest.setCodigoCentro(BigInteger.valueOf(codCentro));

		ReferenciaModType ref = new ReferenciaModType();
		ref.setCodigoReferencia(BigInteger.valueOf(codArt));
		ref.setStock(BigDecimal.valueOf(stock));

		List<ReferenciaModType> referencias = new ArrayList<ReferenciaModType>();
		referencias.add(ref);

		modificarStockRequest.setListaReferencias(referencias.toArray(new ReferenciaModType[referencias.size()]));
		ModificarStockResponseType modificarStockResponseType = this.correccionStockService.modificarStock(modificarStockRequest,session);

		return modificarStockResponseType;
	}

	//Sirve para guardar los valores de los repos.
	@RequestMapping(value = "/pdaP92ListadoRepoAntGuardar", method = RequestMethod.POST)
	public @ResponseBody ReposicionGuardar pdaP92ListadoRepoAntGuardar(
			@RequestBody ReposicionGuardar reposicionGuardar,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Obtener reposicion
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();


		String area = (String) session.getAttribute("area");

		String codMac = user.getMac();

		reposicionGuardar.setCodLoc(codCentro);
		reposicionGuardar.setCodMac(codMac);
		//Obtenemos de sesion el tipo de listado
		Long tipoListado = (Long) session.getAttribute("tipoListado");
		reposicionGuardar.setTipoListado(tipoListado);
		reposicionGuardar.setArea(area);

		ReposicionGuardar repoVuelta = listadoReposicionService.guardarReposicion(reposicionGuardar);
		reposicionGuardar.setCodError(repoVuelta.getCodError());
		reposicionGuardar.setDescError(repoVuelta.getDescError());

		return reposicionGuardar;		
	}

	//Sirve para borrar una pagina
	@RequestMapping(value = "/pdaP92ListadoRepoAntBorrar", method = RequestMethod.POST)
	public String pdaP92ListadoRepoAntBorrar(
			@RequestParam(value = "pagina", required = false, defaultValue = "0") Long pagina,
			@RequestParam(value = "modeloProveedor", required = false) String modeloProveedor,
			@RequestParam(value = "descrColor", required = false) String descrColor,
			@RequestParam(value = "codArt", required = false) Long codArt,
			HttpServletResponse response,
			HttpSession session,RedirectAttributes redirectAttributes) throws Exception{

		//Obtener reposicion
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String codMac = user.getMac();

		//Obtenemos el area de la sesión.
		String area = (String) session.getAttribute("area");

		Reposicion repoBorrar = new Reposicion();
		repoBorrar.setCodLoc(codCentro);
		repoBorrar.setCodMac(codMac);
		//Obtenemos de sesion el tipo de listado
		Long tipoListado = (Long) session.getAttribute("tipoListado");
		repoBorrar.setTipoListado(tipoListado);
		repoBorrar.setModeloProveedor(modeloProveedor);
		repoBorrar.setDescrColor(descrColor);
		repoBorrar.setCodArt(codArt);
		repoBorrar.setArea(area);

		Reposicion repoResult = listadoReposicionService.borrarReposicion(repoBorrar);

		if ((repoResult != null) && (repoResult.getCodError() == 0)) {

			redirectAttributes.addAttribute("pagina", pagina);

			return "redirect:pdaP92ListadoRepoAnt.do";
		}else{
			redirectAttributes.addAttribute("origen", "pdaP92ListadoRepoAnt.do");
			redirectAttributes.addAttribute("error", repoResult.getDescError());
			redirectAttributes.addAttribute("paginaActual", pagina);

			return "redirect:pdaP94Error.do";			
		}
	}

	//Sirve para guardar los valores de los repos.
	@RequestMapping(value = "/pdaP92ListadoRepoAntRevSust", method = RequestMethod.POST)
	public @ResponseBody ReposicionGuardar pdaP92ListadoRepoAntRevSust(
			@RequestBody ReposicionGuardar repoRevSust,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Obtener reposicion
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();

		String area = (String) session.getAttribute("area");

		String codMac = user.getMac();

		repoRevSust.setCodLoc(codCentro);
		repoRevSust.setCodMac(codMac);
		//Obtenemos de sesion el tipo de listado
		Long tipoListado = (Long) session.getAttribute("tipoListado");
		repoRevSust.setTipoListado(tipoListado);
		repoRevSust.setArea(area);

		ReposicionGuardar repoVuelta = listadoReposicionService.guardarReposicion(repoRevSust);
		repoRevSust.setCodError(repoVuelta.getCodError());
		repoRevSust.setDescError(repoVuelta.getDescError());

		return repoRevSust;		
	}

	//Sirve para redirigir al stock
	@RequestMapping(value = "/pdaP92ListadoRepoAntStock", method = RequestMethod.POST)
	public String pdaP91ListadoRepoStock(
			@RequestParam(value = "seccionStk", required = true) String seccionStk,
			@RequestParam(value = "paginaStk", required = true) Long paginaStk,
			@RequestParam(value = "codArtStk", required = true) Long codArtStk,
			@RequestParam(value = "paginaTallaStk", required = true) Long paginaTallaStk,
			HttpServletResponse response,
			HttpSession session,RedirectAttributes redirectAttributes) throws Exception{

		ReferenciasCentro referenciasCentro = new ReferenciasCentro();
		referenciasCentro.setCodArt(codArtStk);

		//Obtener reposicion
		User user = (User) session.getAttribute("user");

		ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
		stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
		stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
		List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
		listaReferencias.add(BigInteger.valueOf(codArtStk));

		stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
		try{
			ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);

			if (stockTiendaResponse.getCodigoRespuesta().equals("OK")){
				//Comprobamos si la referencia es una referencia madre
				if (stockTiendaResponse != null && stockTiendaResponse.getTipoMensaje() != null && 
						stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
					//En este caso hacemos una consulta basica para obtener el stock
					stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA);
				}
				if (null != stockTiendaResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
					session.setAttribute("consultaStock", stockTiendaResponse);
				}else{
					session.removeAttribute("consultaStock");
				}
			}
		}catch (Exception e) {
			session.removeAttribute("consultaStock");
		}

		//Redirigimos a la página de stock pdaP28CorreccionStockInicio.do
		session.setAttribute("seccionStk", seccionStk);
		session.setAttribute("paginaStk", paginaStk);
		session.setAttribute("paginaTallaStk", paginaTallaStk);
		redirectAttributes.addAttribute("codArt", codArtStk);
		redirectAttributes.addAttribute("origen", "REPO2");

		return "redirect:pdaP28CorreccionStockInicio.do";
	}

	//Función que vale para volver de corrección de stocks a la línea de devolución
	@RequestMapping(value = "/pdaP92ListadoRepoAntVuelta",method = RequestMethod.GET)
	public String pdaP92ListadoRepoAntVuelta(
			@Valid final String guardadoStockOk,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {

		//Obtenemos la página de la sesión
		Long pagina = (Long) session.getAttribute("paginaStk");
		//Obtenemos la página de la talla la sesión
		Long paginaTalla = (Long) session.getAttribute("paginaTallaStk");

		String seccion = (String) session.getAttribute("seccionStk");

		//Redireccionamos para obtener la página donde estábamos.
		redirectAttributes.addAttribute("guardadoStockOk", guardadoStockOk);
		redirectAttributes.addAttribute("pagina", pagina);
		redirectAttributes.addAttribute("pgSubList", paginaTalla);
		redirectAttributes.addAttribute("seccion", seccion);
		
		return "redirect:pdaP92ListadoRepoAnt.do";

	}

	@RequestMapping(value = "/pdaGetImageP92", method = RequestMethod.GET)
	public void doGet(@RequestParam(value = "codArticulo", required = true) Long codArticulo,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		User user = (User) session.getAttribute("user");
		try {
			Utilidades.cargarImagenPistola(codArticulo, response, "gif", null, 60F, 235F, 282F);
		} catch (Exception e) {
			logger.error("###############################");
			logger.error(StackTraceManager.getStackTrace(e));	
			logger.error("centro: " + user.getCentro().getCodCentro());
			logger.error("codArticulo: " + codArticulo);
			logger.error("###############################");
		}
	}
}

