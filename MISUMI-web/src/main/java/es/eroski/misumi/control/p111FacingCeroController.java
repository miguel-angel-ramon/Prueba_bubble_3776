package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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

import es.eroski.misumi.dao.iface.VAgruComerRefDao;
import es.eroski.misumi.model.AreaFacingCero;
import es.eroski.misumi.model.CamposModificadosSFM;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.SfmCapacidadFacingPagina;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.AreaFacingCeroService;
import es.eroski.misumi.service.iface.MapClavesSfmService;
import es.eroski.misumi.service.iface.VAgruComerParamFacingCeroService;
import es.eroski.misumi.service.iface.VAgruComerParamSfmcapService;
import es.eroski.misumi.service.iface.VArtFacingCeroService;
import es.eroski.misumi.service.iface.VArtSfmService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/facingCero")
public class p111FacingCeroController {

	private static Logger logger = Logger.getLogger(p111FacingCeroController.class);
	private PaginationManager<VArtSfm> paginationManager = new PaginationManagerImpl<VArtSfm>();

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private MapClavesSfmService mapClavesSfmService;

	@Autowired
	AreaFacingCeroService areaFacingCeroService;
	
	@Autowired
	private VArtFacingCeroService vArtFacingCeroService;

	@Autowired
	private VArtSfmService vArtSfmService;
	
	@Autowired
	private VAgruComerParamFacingCeroService vAgruComerParamFacingCeroService;

	@Autowired
	private ExcelManager excelManager;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
		logger.info("showForm - p111_facingCero");
		return "p111_facingCero";
	}

	//Se utiliza para cargar los combos de AGRUPACION COMERCIAL
	@RequestMapping(value="/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerParamSfmcap> getAreaData(@RequestBody VAgruComerParamSfmcap vAgruCommerParamFacingCero
																, HttpSession session, HttpServletResponse response) throws Exception {
		try {
			AreaFacingCero areasFacingCero = this.areaFacingCeroService.getAreasFacingCero(vAgruCommerParamFacingCero.getCodCentro());
			
			return this.vAgruComerParamFacingCeroService.findAllAreas(areasFacingCero, null);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody SfmCapacidadFacingPagina loadDataGrid(
			@RequestBody VArtSfm vArtFacingCero,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		
		Locale locale = LocaleContextHolder.getLocale();
		
		// Si NO es RECARGA.
		if (Constantes.NO_ES_RECARGA.equals(recarga)){
			SfmCapacidadFacingPagina facingCeroPagina = new SfmCapacidadFacingPagina();
			SfmCapacidadFacingPagina facingCeroPaginaDos = new SfmCapacidadFacingPagina();
			
			SfmCapacidadFacing facingCero = null;
			List<VArtSfm> listaFacingCero = null;

			try {
				//logger.info("Searching VArtSfm");
				
				facingCero = this.vArtFacingCeroService.consultaFacingCero(vArtFacingCero.getCodLoc(), vArtFacingCero.getCodN1());
				
				if (facingCero != null && facingCero.getDatos() != null){
					listaFacingCero = facingCero.getDatos();
				}

				//Ordenamos la lista
				listaFacingCero = this.vArtFacingCeroService.ordenarLista(listaFacingCero, index, sortOrder);
				if (facingCero != null){
					facingCero.setDatos(listaFacingCero);
				}

				session.setAttribute("listaFacingCero", facingCero);

				//Además la primera vez que cargamos tenemos que crear un hashmap que relacione clave con índice de la lista.
				//HashMap<String, Integer> mapClaves = new HashMap<String, Integer>();
				if (listaFacingCero != null && listaFacingCero.size()>0) {

					//Formateamos el campo Lsf segun fecha
					listaFacingCero = this.vArtSfmService.formatearLsf(listaFacingCero);

					//Creamos el hashMap
					this.mapClavesSfmService.updateMapClavesInSession(listaFacingCero);
				}
				
				//Además la primera vez que cargamos tenemos que crear un Hashmap que relacione clave con índice de la lista.
				//HashMap<String, Integer> mapClaves = new HashMap<String, Integer>();
//				if (listaFacingCero != null && listaFacingCero.size()>0) {
//
//					//Formateamos el campo Lsf segun fecha
//					listaFacingCero = this.vArtSfmService.formatearLsf(listaFacingCero);
//
//					//Creamos el hashMap
//					this.mapClavesSfmService.updateMapClavesInSession(listaFacingCero);
//				}
//
//				//La primera vez se dejará credo el HashMap que se utilizará para guardar la relación entre referencia lote y las referencias hijas.
//				//session.removeAttribute("hashMapRelLoteHijas");
//				HashMap<String, SfmCapacidadFacing> mapRelLoteHijas = new HashMap<String, SfmCapacidadFacing>();
//				session.setAttribute("hashMapRelLoteHijas", mapRelLoteHijas);

			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}

			/*
			 * Antes de paginar vamos a calcular el FLG_NSR de todos los registros.
			 * Calculamos todos los registros porque es un campo ordenable.
			 */
//			sfmCapacidadFacing = this.vArtSfmService.calcularCampoNSR(sfmCapacidadFacing);

			/*
			 * Si se está filtrando por referencia calculamos el campo pedir.
			 */
//			sfmCapacidadFacingPagina.setPedir(this.vArtSfmService.calcularPedir(vArtSfm));

			/*
			 * Creamos una sublista paginada sólo con los registros que se muestran en el grid.
			 */
			facingCeroPaginaDos = this.vArtFacingCeroService.crearListaPaginada(facingCero, listaFacingCero, page, max, index, sortOrder);
			facingCeroPagina.setDatos(facingCeroPaginaDos.getDatos());
			facingCeroPagina.setDescEstado(facingCeroPaginaDos.getDescEstado());
			facingCeroPagina.setEstado(facingCeroPaginaDos.getEstado());
			facingCeroPagina.setFlgCapacidad(facingCeroPaginaDos.getFlgCapacidad());
			facingCeroPagina.setFlgFacing(facingCeroPaginaDos.getFlgFacing());

			/*
			 * Una vez que hemos paginado vamos a calcular el FLG_MUCHO_POCO para los registros de la primera página, 
			 * es decir, aquellos que se muestran en el grid de la pantalla.
			 * Esto lo podemos hacer porque no es un campo ordenable, es un link que se muestra en el campo stock.
			 */
//			sfmCapacidadFacingPagina = this.vArtSfmService.calcularCampoMuchoPoco(sfmCapacidadFacingPagina, vDatosDiarioArtRes.getCodFpMadre(), session.getId());

			//	sfmCapacidadFacingPagina.getDatos().getRows().get(22).getLsf()

			return facingCeroPagina;
			
		// Si es RECARGA
		}else{
			return loadDataGridRecarga(vArtFacingCero, page, max, index, sortOrder, response, session);
		}
	}

	@RequestMapping(value = "/loadDataGridRecarga", method = RequestMethod.POST)
	public  @ResponseBody SfmCapacidadFacingPagina loadDataGridRecarga(
			@RequestBody VArtSfm vArtFacingCero,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		SfmCapacidadFacing sfmCapacidadGuardada = null;
		SfmCapacidadFacingPagina facingCeroCapacidadFacingPagina = new SfmCapacidadFacingPagina();
		List<VArtSfm> listaGuardada = null;
		List<CamposModificadosSFM> listaCamposModificar = null;
		
		try {
			sfmCapacidadGuardada = (SfmCapacidadFacing) session.getAttribute("listaFacingCero");
			if (sfmCapacidadGuardada != null){
				listaGuardada = sfmCapacidadGuardada.getDatos();
			}

			//Tenemos que recorrer la lista que recibimos con los registros a actualizar.
			listaCamposModificar = (List<CamposModificadosSFM>) vArtFacingCero.getListadoModificados();

			if (listaCamposModificar != null) {
				//Nos recorremos la lista.
				CamposModificadosSFM campo = new CamposModificadosSFM();
				VArtSfm registro = new VArtSfm();
				int indice = 0;
				for (int i =0;i<listaCamposModificar.size();i++){
					campo = (CamposModificadosSFM)listaCamposModificar.get(i);

					indice = campo.getIndice();

					//Obtenemos el registro de la lista guardada en sesion.
					registro = (VArtSfm) listaGuardada.get(indice);

					//Actualizamos los valores que me llegan.
//					registro.setCapacidad(campo.getCapacidad());
//					registro.setSfm(campo.getSfm());
//					registro.setCoberturaSfm(campo.getCoberturaSfm());
					registro.setCodError(campo.getCodError());
					registro.setFacingCentro(campo.getFacingCentro());

					Long codArtCapraboOEroski = registro.getCodArticulo();
					registro.setCodArtCaprabo(codArtCapraboOEroski);

					//Actualizamos la lista con el registro.
					listaGuardada.set(indice, registro);
				}

				//Actualizamos la lista en sesión.
				sfmCapacidadGuardada.setDatos(listaGuardada);
				session.setAttribute("listaFacingCero", sfmCapacidadGuardada);
			}

			//Sólo tendremos que ordenar cuando recarguemos al pulsar sobre la columna de iconos,
			//al paginar no debe ordenar.
			if (null != index && !index.equals("")){
				//Ordenamos la lista
				listaGuardada = this.vArtFacingCeroService.ordenarLista(listaGuardada, index, sortOrder);

				//Actualizamos el mapa de claves en sesión
				this.mapClavesSfmService.updateMapClavesInSession(listaGuardada);

				//Actualizamos la lista en sesión.
				sfmCapacidadGuardada.setDatos(listaGuardada);
				session.setAttribute("listaFacingCero", sfmCapacidadGuardada);
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		/*
		 * Creamos una sublista paginada sólo con los registros que se muestran en el grid.
		 */
		facingCeroCapacidadFacingPagina = this.vArtFacingCeroService.crearListaPaginada(sfmCapacidadGuardada, listaGuardada, page, max, index, sortOrder);

		/*
		 * Una vez que hemos paginado vamos a calcular el FLG_MUCHO_POCO para los registros de la primera página, 
		 * es decir, aquellos que se muestran en el grid de la pantalla.
		 */
//		sfmCapacidadFacingPagina = this.vArtSfmService.calcularCampoMuchoPoco(sfmCapacidadFacingPagina, vDatosDiarioArtRes.getCodFpMadre(), session.getId());

		return facingCeroCapacidadFacingPagina;
	}

	@RequestMapping(value = "/saveDataGrid", method = RequestMethod.POST)
	public  @ResponseBody SfmCapacidadFacingPagina saveDataGrid(
			@RequestBody VArtSfm vArtSfm,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		
		Pagination pagination = new Pagination(max,page);
		
		if (index!=null){
			pagination.setSort(index);
		}
		
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}

		SfmCapacidadFacing sfmCapacidadGuardada = null;
		SfmCapacidadFacingPagina sfmCapacidadFacingPagina = new SfmCapacidadFacingPagina();
		List<VArtSfm> listaModificadosActualizacion = new ArrayList<VArtSfm>();
		List<VArtSfm> listaRecarga = new ArrayList<VArtSfm>();
		List<VArtSfm> listaGuardada = null;
		List<CamposModificadosSFM> listaCamposModificar = null;

		int records = 0;
		try {
			Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
			Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;

			sfmCapacidadGuardada = (SfmCapacidadFacing) session.getAttribute("listaFacingCero");
			if (sfmCapacidadGuardada != null){
				listaGuardada = sfmCapacidadGuardada.getDatos();
			}

			User userSession = (User)session.getAttribute("user");

		/* ******************************************************************************** */
			
			//Tenemos que recorrer la lista que recibimos con los registros a actualizar.
			listaCamposModificar = (List<CamposModificadosSFM>) vArtSfm.getListadoModificados();

			if (listaCamposModificar != null) {
				
				//Nos recorremos la lista.
				CamposModificadosSFM campo = new CamposModificadosSFM();
				VArtSfm registro = new VArtSfm();
				
				int indice = 0;
				
				for (int i =0;i<listaCamposModificar.size();i++){
					
					campo = (CamposModificadosSFM)listaCamposModificar.get(i);

					indice = campo.getIndice();

					//Obtenemos el registro de la lista guardada en sesión.
					registro = (VArtSfm) listaGuardada.get(indice);

					//Actualizamos los valores que me llegan.
//					registro.setCapacidad(campo.getCapacidad());
//					registro.setSfm(campo.getSfm());
//					registro.setCoberturaSfm(campo.getCoberturaSfm());
					registro.setFacingCentro(campo.getFacingCentro());
					registro.setCodError(campo.getCodError());

					//Si el centro es de CAPRABO, el codigoArticulo
					//del objeto registro (VArtSfm) llega relleno con
					//el valor que debería tener codigoArticuloCaprabo.
					//En ese caso habrá que insertar el valor de codigoArticulo
					//en codigoArticuloCaprabo. 

					//Si el centro es eroski el codigoArticulo llega correctamente
					//relleno con el codigoArticulo de eroski y codigoArticuloCaprabo 
					//guardará la referencia de eroski también. 

					//Como codigoArticuloCaprabo es nulo en los dos casos hay que actualizarlos.

					//Esto se debe a que la función crearEstructuraActualizacionSfmCap
					//de VArtSfmDaoImpl utiliza el atributo codigoArticuloCaprabo
					//para crear el objeto del plsql. Al actualizar el fac,sfm o capacidad
					//si es centro es caprabo el objeto utiliza referencias caprabo y si
					//el centro es eroski las referencias eroski.

					//En este caso en el caso de ser un centro caprabo codigoArticulo y codigoArticuloCaprabo
					//guardan el codigo de articulo de Caprabo y en caso de ser centro eroski guardan el codigo
					//de artículo eroski las dos. Se podría haber utilizado en la función crearEstructuraActualizacionSfmCap
					//directamente codigoArticulo, pero como es una función compartida con pdap21 y como en ese caso 
					//codigoArticulo y codigoArticuloCaprabo  utilizan dos referencias distintas en el caso de ser centro
					//caprabo y nos interesa la de caprabo, ha sido necesario utilizar ese atributo. En el caso de eroski
					//codigoArticulo y codigoArticuloCaprabo siempre tienen el mismo valor, por lo cual funciona bien.

					//Lo lógico hubiera sido que codigoArticulo y codigoArticuloCaprabo fueran distintos en este caso también,
					//en caprabo, pero como el grid enseña lo que existe en codigoArticulo y en este caso es correcto (guarda el
					//articulo de caprabo), se ha decidido no tocarlo por si se rompe algo, aunque realmente debería tener el codigo
					//de eroski.

					Long codArtCapraboOEroski = registro.getCodArticulo();
					registro.setCodArtCaprabo(codArtCapraboOEroski);

					//Actualizamos la lista con el registro.
					listaGuardada.set(indice, registro);
				}
			}

			if (listaGuardada != null) {
				//Nos recorremos la lista actualizada para obtener los registros modificados
				VArtSfm registroGuardado = new VArtSfm();

				for (int i=0;i<listaGuardada.size();i++){
					registroGuardado = (VArtSfm)listaGuardada.get(i);
					if (registroGuardado != null && registroGuardado.getCodError() != null && registroGuardado.getCodError().equals(Constantes.SFMCAP_CODIGO_MENSAJE_MODIFICADO)){
						listaModificadosActualizacion.add(registroGuardado);
					}
				}
			}

			//Obtenemos el usuario para enviarlo a la actualización
			String usuario = "";
			//User userSession = (User)session.getAttribute("user");
			if (userSession.getCode() != null && !userSession.getCode().equals("")){
				usuario = userSession.getCode();
			}

			//Actualización de los datos
			if (listaModificadosActualizacion != null){
				
				//Obtención del mapa de claves para la actualización
				HashMap<String, Integer> mapClaves = this.mapClavesSfmService.getMapClavesFromSession();

				SfmCapacidadFacing sfmCapacidadFacingActualizacion = null;
				
				/* ******************************************************************************* */
						// Actualiza los cambios del FACING de las referencias modificadas.
				/* ******************************************************************************* */
				sfmCapacidadFacingActualizacion = this.vArtSfmService.actualizacionFac(listaModificadosActualizacion, usuario);

				//Recarga de la lista actualizada en la lista guardada
				if (sfmCapacidadFacingActualizacion != null && sfmCapacidadFacingActualizacion.getDatos() != null
					&& sfmCapacidadFacingActualizacion.getDatos().size() > 0){
					List<VArtSfm> listaDatosActualizados = sfmCapacidadFacingActualizacion.getDatos();
					
					for (int i=0; i<listaDatosActualizados.size(); i++){
						VArtSfm varSfmActualizado = listaDatosActualizados.get(i);
						String claveRegistroActualizado = obtenerClaveRegistro(	varSfmActualizado.getCodN1()
																			  , varSfmActualizado.getCodN2()
																			  , varSfmActualizado.getCodN3()
																			  , varSfmActualizado.getCodN4()
																			  , varSfmActualizado.getCodN5()
																			  , varSfmActualizado.getCodArticulo()
																			  );
						int indiceRegistroGuardado = ((Integer)mapClaves.get(claveRegistroActualizado)).intValue();

						VArtSfm registroGuardado = (VArtSfm) listaGuardada.get(indiceRegistroGuardado);

						//Actualizamos los valores que me llegan.
						registroGuardado.setFacingCentro(varSfmActualizado.getFacingCentro());
						registroGuardado.setFacingCentroOrig(varSfmActualizado.getFacingCentroOrig());
						registroGuardado.setFacingPrevio(varSfmActualizado.getFacingPrevio());
						registroGuardado.setCapacidad(varSfmActualizado.getCapacidad());
						registroGuardado.setCapacidadOrig(varSfmActualizado.getCapacidadOrig());
						registroGuardado.setLmin(varSfmActualizado.getLmin());

						registroGuardado.setSfm(varSfmActualizado.getSfm());
						registroGuardado.setSfmOrig(varSfmActualizado.getSfmOrig());
						registroGuardado.setCoberturaSfm(varSfmActualizado.getCoberturaSfm());
						registroGuardado.setCoberturaSfmOrig(varSfmActualizado.getCoberturaSfmOrig());
						
						if (varSfmActualizado.getCodError() == null || varSfmActualizado.getCodError().equals(Constantes.SFMCAP_CODIGO_GUARDADO_CORRECTO)){
							registroGuardado.setCodError(Constantes.SFMCAP_CODIGO_MENSAJE_GUARDADO);
						}else{
							registroGuardado.setCodError(varSfmActualizado.getCodError());
							registroGuardado.setDescError(varSfmActualizado.getDescError());
						}
						listaGuardada.set(indiceRegistroGuardado, registroGuardado);
						
					}
				}
				else{
					if (sfmCapacidadFacingActualizacion != null && sfmCapacidadFacingActualizacion.getEstado() != null && sfmCapacidadFacingActualizacion.getEstado().longValue() != 0){
						sfmCapacidadFacingPagina.setEstado(sfmCapacidadFacingActualizacion.getEstado());
						sfmCapacidadFacingPagina.setDescEstado(sfmCapacidadFacingActualizacion.getDescEstado());
					}
				}
			}


			/**
			 * P-50987
			 * Sumatorio de facing para la gestion de huecos libres
			 * @author BICUGUAL 
			 */
			Long sumatorio=new Long(0);
			for (VArtSfm item:listaGuardada){
				sumatorio+=item.getFacingCentro()!=null ? item.getFacingCentro(): 0;
			}
			sfmCapacidadFacingPagina.setSumatorio(sumatorio);		
			List<VArtSfm> listaGuardadaSalida = new ArrayList<VArtSfm>();
			for (int i=0;i<listaGuardada.size();i++){
				if(listaGuardada.get(i).getCodError() == null || !listaGuardada.get(i).getCodError().equals(Constantes.SFMCAP_CODIGO_MENSAJE_GUARDADO)){
					listaGuardadaSalida.add(listaGuardada.get(i));
				}
			}
			this.mapClavesSfmService.updateMapClavesInSession(listaGuardadaSalida);
			//Actualizamos la lista en sesión.
			sfmCapacidadGuardada.setDatos(listaGuardadaSalida);
			session.setAttribute("listaFacingCero", sfmCapacidadGuardada);

			if (listaGuardadaSalida != null){ 
				records = listaGuardadaSalida.size();
				if (elemInicio <= records){
					if (elemFinal > records){
						elemFinal = new Long(records);
					}
					listaRecarga = (listaGuardadaSalida).subList(Integer.parseInt(elemInicio.toString()), Integer.parseInt(elemFinal.toString()));
				}
			}


		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<VArtSfm> listaSfmPaginada = null;

		if (listaRecarga != null) {
			listaSfmPaginada = this.paginationManager.paginate(new Page<VArtSfm>(), listaRecarga,
					max.intValue(), records, page.intValue());	
			sfmCapacidadFacingPagina.setDatos(listaSfmPaginada);
			if (sfmCapacidadFacingPagina.getEstado() == null){
				sfmCapacidadFacingPagina.setEstado(sfmCapacidadGuardada.getEstado());
			}
			if (sfmCapacidadFacingPagina.getDescEstado() == null){
				sfmCapacidadFacingPagina.setDescEstado(sfmCapacidadGuardada.getDescEstado());
			}
			sfmCapacidadFacingPagina.setFlgCapacidad(sfmCapacidadGuardada.getFlgCapacidad());
			sfmCapacidadFacingPagina.setFlgFacing(sfmCapacidadGuardada.getFlgFacing());

		} else {
			sfmCapacidadFacingPagina.setDatos(new Page<VArtSfm>());
			if (sfmCapacidadFacingPagina.getEstado() == null){
				sfmCapacidadFacingPagina.setEstado(new Long(0));
			}
			if (sfmCapacidadFacingPagina.getDescEstado() == null){
				sfmCapacidadFacingPagina.setDescEstado("");
			}
		}

		return sfmCapacidadFacingPagina;
	}

	@RequestMapping(value = "/finRevisionGrid", method = RequestMethod.POST)
	public  @ResponseBody SfmCapacidadFacingPagina finRevisionGrid(@RequestBody ArrayList <Long> referenciasSeleccionadas,
//			@RequestBody VArtSfm vArtSfm,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		
		Pagination pagination = new Pagination(max,page);
		
		if (index!=null){
			pagination.setSort(index);
		}
		
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}

		List<VArtSfm> listaRefsValidadas = new ArrayList<VArtSfm>();
		List<VArtSfm> listaRefsSeleccionadas = new ArrayList<VArtSfm>(referenciasSeleccionadas.size());
		SfmCapacidadFacing sfmCapacidadFacingActualizacion = null;

		SfmCapacidadFacing sfmCapacidadGuardada = (SfmCapacidadFacing) session.getAttribute("listaFacingCero");
		if (sfmCapacidadGuardada != null){
			listaRefsValidadas = sfmCapacidadGuardada.getDatos();
			for(int j=0;j<referenciasSeleccionadas.size();j++){
				Long referencia=Long.valueOf(String.valueOf(referenciasSeleccionadas.get(j)));
				for(int i=0;i<listaRefsValidadas.size();i++){
					
					if(referencia.equals(listaRefsValidadas.get(i).getCodArticulo())){
						listaRefsSeleccionadas.add(listaRefsValidadas.get(i));
					}
				}
			}
			
		}
		
		// Actualiza los cambios del FACING de las referencias modificadas.
		sfmCapacidadFacingActualizacion = this.vArtFacingCeroService.grabarRefValidadas(listaRefsSeleccionadas);

		return null;
	}
	
	
	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody
	void exportGrid(
			@RequestParam(required = false) String[] model,
			@RequestParam(required = false) Integer[] widths,
			@RequestParam(required = false) Long centro,
			@RequestParam(required = false) Long grupo1,
			@RequestParam(required = false) String descArea,
			HttpServletResponse response, HttpSession session) throws Exception{
		
		try {
			User user = (User) session.getAttribute("user");

			VArtSfm vArtSfm = new VArtSfm();
			vArtSfm.setCodLoc(centro);
			if (null!=grupo1){
				vArtSfm.setCodN1(Long.toString(grupo1));
			}

			//Busqueda
			SfmCapacidadFacing facingCero = null;
			List<VArtSfm> listaFacingCero = null;
			
			facingCero = this.vArtFacingCeroService.consultaFacingCero(centro, Long.toString(grupo1));
			
			if (facingCero != null && facingCero.getDatos() != null){
				listaFacingCero = facingCero.getDatos();
				
				if (listaFacingCero != null && listaFacingCero.size()>0) {
					//Formateamos el campo Lsf segun fecha
					listaFacingCero = Utilidades.formatearLsfExport(listaFacingCero);
				}
			}

			logger.info("Exporting EXCEL Facing Cero:"+model.toString());

			this.excelManager.exportFacingCero( listaFacingCero, model, widths, this.messageSource, user.getCentro()
											  , grupo1, descArea, response);

		}catch(Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}

	/**
	 * Prepara la clave del registro.
	 * @param codN1
	 * @param codN2
	 * @param codN3
	 * @param codN4
	 * @param codN5
	 * @param codArticulo
	 * @return
	 */
	private String obtenerClaveRegistro(String codN1, String codN2, String codN3, String codN4, String codN5, Long codArticulo){

		StringBuffer claveRegistroBuff = new StringBuffer();

		claveRegistroBuff.append((codN1 != null && !"".equals(codN1))? Integer.parseInt(codN1)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN2 != null && !"".equals(codN2))? Integer.parseInt(codN2)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN3 != null && !"".equals(codN3))? Integer.parseInt(codN3)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN4 != null && !"".equals(codN4))? Integer.parseInt(codN4)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN5 != null && !"".equals(codN5))? Integer.parseInt(codN5)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codArticulo!=null)?codArticulo:"");

		return claveRegistroBuff.toString();
	}

}
