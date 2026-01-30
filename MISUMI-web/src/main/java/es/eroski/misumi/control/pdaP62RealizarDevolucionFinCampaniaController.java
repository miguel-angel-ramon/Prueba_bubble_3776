package es.eroski.misumi.control;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.BultoCantidad;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionEmail;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.DevolucionLineaModificada;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.RdoListasDev;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.TDevolucionBulto;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.RefAsociadasServiceImpl;
import es.eroski.misumi.service.iface.DevolucionLineaBultoCantidadService;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
public class pdaP62RealizarDevolucionFinCampaniaController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP62RealizarDevolucionFinCampaniaController.class);

	@Autowired
	private DevolucionService devolucionesService;
	
	@Autowired
	private DevolucionLineaBultoCantidadService devolucionLineaBultoCantidadService;

	@Autowired
	private FotosReferenciaService fotosReferenciaService;

	@Autowired
	private RefAsociadasServiceImpl refAsociadasServiceImpl;

	@Autowired
	private StockTiendaService correccionStockService;

	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;

	@Resource
	private MessageSource messageSource;

	@RequestMapping(value = "/pdaP62RealizarDevolucionFinCampania",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="devolucion") String devolucionId,
			@RequestParam(value="origenPantalla", required=false, defaultValue = "") String origenPantalla,
			@RequestParam(value="selectProv", required=false, defaultValue = "") String selectProv,
			@RequestParam(value="bultoSeleccionado", required=false, defaultValue = "0") int bultoSeleccionado,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LinkedHashMap<String,String> comboBox = new LinkedHashMap<String, String>();
		LinkedHashMap<String,String> listaProv = new LinkedHashMap<String, String>();
		LinkedHashMap<String,String> listaProvCargados = new LinkedHashMap<String, String>();
		List<TDevolucionLinea> listTDevolucionLinea = null;
		DevolucionCatalogoEstado devol = new DevolucionCatalogoEstado();
		String proveedor = null;

		String resultado = "pda_p62_realizarDevolucionFinDeCampania";
		User user = (User) session.getAttribute("user");

		if (selectProv != null && !selectProv.toString().equals("")){
			proveedor = selectProv.substring(0, selectProv.indexOf("-"));
		}
		
		//Paginación de devolución linea
		Page<TDevolucionLinea> pagDevolucionLineas = null;	        
		int primerElementoAMostrar = 0;
		int numeroElementosPorPagina = Constantes.DEVOLUCIONES_PDA_PAGS_FIN_CAMPANA_CON_IMAGEN;

		int paginaPrimerElementoAMostrar = 1;
		int filaPrimerElementoAMostrar = 0;
		int indiceInicioPaginaPrimerElementoAMostrar = 0;
		int indiceFinPaginaPrimerElementoAMostrar = numeroElementosPorPagina;

		// Llamada para conseguir las devoluciones. 
		Devolucion devolucion = new Devolucion();
		devolucion.setDevolucion(new Long(devolucionId));

		//Obtención de la devolución de la lista de devoluciones
		@SuppressWarnings("unchecked")
		List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
		devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
		devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);

		listaProvCargados = (LinkedHashMap<String,String>)session.getAttribute("listaProveedor");

		// Esta rama del IF, SÓLO será necesaria
		if (listaProvCargados==null || listaProvCargados.isEmpty() || (boolean)session.getAttribute("existenDevoluciones").equals("false")){
			//Obtiene una devolución con sus líneas de devolución incluidos los artículos.
			devol = devolucionesService.cargarAllDevoluciones(devolucion);
			session.setAttribute("devCatalogoEstado", devol);
		}else{
			devol = (DevolucionCatalogoEstado) session.getAttribute("devCatalogoEstado");
		}

		//Se mira que existe la lista de líneas de devolución
		if (devol != null){
			if (devol.getListDevolucionEstado() != null && devol.getListDevolucionEstado().size() > 0){
				if (devol.getListDevolucionEstado().get(0).getListDevolucion() != null && devol.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
					
					Devolucion devolucionSeleccion = devol.getListDevolucionEstado().get(0).getListDevolucion().get(0);
					
					if(devolucionSeleccion.getDevLineas() != null && devolucionSeleccion.getDevLineas().size() > 0){																							

						//Se obtiene la lista de líneas de devolución
						List<DevolucionLinea> listDevolucionLinea = (List<DevolucionLinea>) devolucionSeleccion.getDevLineas();

						// Sólo se borrarán e insertarán la primera vez que se entre a las referencias.
						if ((boolean)session.getAttribute("existenDevoluciones").equals("false")){
							// Cuando hacemos la primera búsqueda eliminamos siempre los
							// registros guardados anteriormente. Se eliminarán todos los
							//registros de todas las sesiones que lleven más de un día en
							//la tabla.
							this.eliminarTablaSesionHistorico(session);

							// Insertar tabla temporal T_DEVOLUCIONES
							// A partir de la lista obtenida tenemos que insertar en la
							// tabla temporal los registros obtenidos,
							// borrando previamente los posibles registros almacenados.
							this.eliminarTablaSesion(session);

							//Se inserta en una tabla temporal cada línea de devolución. Se devuelve una lista de TDevolucionLinea tratada
							//a partir de la lista obtenida en el PLSQL. Así evitamos tirar de la tabla temporal al ya tener los datos.
							RdoListasDev rdoListasDev = this.insertarTablaSesionLineaDevolucion(listDevolucionLinea, session.getId(),devolucionSeleccion.getDevolucion());
							
							//Obtenemos el combo de proveedores
							listaProv = this.loadComboProveedor(devolucion, response, session);
							// Eliminar los valores NULOS de la lista.
//							listaProv = Utilidades.limpiaLista(comboBox);
							
							session.setAttribute("listaProveedor", listaProv);
							session.setAttribute("existenDevoluciones", "true");
						}

						// Carga del valor del bulto seleccionado en la pistola.
						devolucion.setBultoSeleccionado(bultoSeleccionado);
						
						//Obtenemos la lista ordenada de la tabla temporal para mostrar en el grid
						listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion,null,null,proveedor,null);						
						listTDevolucionLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLinea);
						PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

						//El número de registros será el de la lista obtenida.
						int records = listTDevolucionLinea.size();

						if(listTDevolucionLinea.size() < numeroElementosPorPagina){
							pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listTDevolucionLinea,numeroElementosPorPagina, records, 1);
						}else{
							//Buscamos el primer elemento de la lista que tenga stockDevuelto null o bulto null
							primerElementoAMostrar = this.devolucionesService.firstLineaDevolucionStockOrBultoNull(null, listTDevolucionLinea);
							paginaPrimerElementoAMostrar = (primerElementoAMostrar / numeroElementosPorPagina) + 1;
							filaPrimerElementoAMostrar = primerElementoAMostrar % numeroElementosPorPagina;
							indiceInicioPaginaPrimerElementoAMostrar = (paginaPrimerElementoAMostrar-1) * numeroElementosPorPagina;
							indiceFinPaginaPrimerElementoAMostrar = indiceInicioPaginaPrimerElementoAMostrar + numeroElementosPorPagina;
							//Hay que evitar que el elemento de fin de paginación exceda del máximo de la lista
							if (indiceFinPaginaPrimerElementoAMostrar>listTDevolucionLinea.size()){
								indiceFinPaginaPrimerElementoAMostrar = listTDevolucionLinea.size();
							}
							pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listTDevolucionLinea.subList(indiceInicioPaginaPrimerElementoAMostrar, indiceFinPaginaPrimerElementoAMostrar), numeroElementosPorPagina, records, paginaPrimerElementoAMostrar);
						}
					}else{
						resultado = "pda_p66_noHayDevolucionesError";
						model.addAttribute("origenErrorDevolucion","pda_p62_realizarDevolucionFinDeCampania");
					}
				}else{
					resultado = "pda_p66_noHayDevolucionesError";
					model.addAttribute("origenErrorDevolucion","pda_p62_realizarDevolucionFinDeCampania");
				}
			}else{
				resultado = "pda_p66_noHayDevolucionesError";
				model.addAttribute("origenErrorDevolucion","pda_p62_realizarDevolucionFinDeCampania");
			}
		}else{
			resultado = "pda_p66_noHayDevolucionesError";
			model.addAttribute("origenErrorDevolucion","pda_p62_realizarDevolucionFinDeCampania");
		}

		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());

		//Obtención de combo de proveedores
		model.addAttribute("proveedores", listaProv);

		//Obtenemos las claves de los combos y les insertamos asteriscos.
		List<String> listaBultoPorProveedorTodos= new ArrayList<String>(listaProv.keySet());
		List<String> listaBultoPorProveedorFiltrado = new ArrayList<String>();

		Locale locale = LocaleContextHolder.getLocale();
		String refPermanentes = this.messageSource.getMessage("pda_p62_refPermanentes", null,locale);

		for(String clave:listaBultoPorProveedorTodos){
			if(clave != "" && clave !=refPermanentes){
				clave = clave + "*";
				listaBultoPorProveedorFiltrado.add(clave);
			}
		}

		List<TDevolucionLinea> tDevolucionLineaActualLst = new ArrayList<TDevolucionLinea>();

		//Obtención de la devolución actual
		if(pagDevolucionLineas != null && pagDevolucionLineas.getRows() != null && pagDevolucionLineas.getRows().size() > 0){
			int numeroLineasVisibles = pagDevolucionLineas.getRows().size();
			for(int i=0; i<numeroLineasVisibles; i++){
				TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();

				tDevolucionLineaActual = pagDevolucionLineas.getRows().get(i);
				
				//Obtenemos el formato devuelto
				String formatoDevuelto = tDevolucionLineaActual.getFormatoDevuelto();
				String formatoDevueltoFormat = formatoDevuelto;
				if(formatoDevuelto.equals(Constantes.DEVOLUCIONES_FORMATO1)){
					formatoDevueltoFormat = this.messageSource.getMessage("pda_p62_canOriCerrado", null, locale);
				}else if(formatoDevuelto.equals(Constantes.DEVOLUCIONES_FORMATO2)){
					formatoDevueltoFormat = this.messageSource.getMessage("pda_p62_unidadesKgs", null, locale);
				}
				
				tDevolucionLineaActual.setFormatoDevuelto(formatoDevueltoFormat);

				//Para comparar en el jsp los códigos de error
				tDevolucionLineaActual.setCodErrorStr(tDevolucionLineaActual.getCodError().toString());
				tDevolucionLineaActualLst.add(tDevolucionLineaActual);
			}
		}

		//Si existe el permiso de las fotos, mirar si la devolución a mostrar tiene una foto asignada
		// , como la paginación será de 1 en 1, sabemos que la lista tDevolucionLineaActualLst tendrá 
		// solo un elemento, por lo que tenemos que buscar la foto de ese elemento.
		if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones())
				&& !user.getCentro().getOpcHabil().isEmpty()
				&& user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_FOTO_27) != -1) {
			//Comprobar si tiene Foto
			FotosReferencia fotosReferencia = new FotosReferencia();

			if (tDevolucionLineaActualLst.size() > 0){
				//Como la lista a mostrar tiene solo un artículo, obtenemos el valor del elemento 0.
				fotosReferencia.setCodReferencia(tDevolucionLineaActualLst.get(0).getCodArticulo());

				//Miramos si existe la foto en BBDD. En caso afirmativo, buscaremos más adelante su path.
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					model.addAttribute("tieneFoto",true);
				}else{
					model.addAttribute("tieneFoto",false);
				}
			}else{
				model.addAttribute("tieneFoto",false);
			}

		}else{
			model.addAttribute("tieneFoto",false);
		}
		int lineasCompletadas = this.devolucionesService.findLineasDevolucionCompletadas(listTDevolucionLinea);

		model.addAttribute("pagDevolucionLineas", pagDevolucionLineas);
		model.addAttribute("devolucionCabecera", devolucion);

		Devolucion dev = new Devolucion();
		dev.settDevLineasLst(tDevolucionLineaActualLst);

		//Insertamos la lista con bulto por proveedor
		dev.setBultoPorProveedorLst(listaBultoPorProveedorFiltrado);
		
		
		
		// Completar la lista de devoluciones con los datos de las referencias y bultos por referencia.
		// De esta manera en la paginación, no tendremos que volver a consultar los datos de T_DEVOLUCIONES y T_DEVOLUCIONES_BULTO.
		devol = (DevolucionCatalogoEstado) session.getAttribute("devCatalogoEstado");
//		devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).settDevLineasLst(tDevolucionLineaActualLst);
		devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).settDevLineasLst(listTDevolucionLinea);
		devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).setBultoPorProveedorLst(listaBultoPorProveedorFiltrado);
		session.setAttribute("devCatalogoEstado", devol);

		
		
		Double sumaEuros = getCalcularSumaEuros(listTDevolucionLinea);
		Map<String, Boolean> linkTextil = this.mostrarLinkTextil(listTDevolucionLinea, paginaPrimerElementoAMostrar); 

		model.addAttribute("linkTextil", linkTextil);
		model.addAttribute("devolucionConLineas", dev);
		model.addAttribute("proveedorAnterior", "");
		model.addAttribute("sumaEuros", sumaEuros);
		model.addAttribute("completados", lineasCompletadas);
		model.addAttribute("primerElementoBultoFila", filaPrimerElementoAMostrar);

		model.addAttribute("referenciaFiltroRellenaYCantidadEnter", "");
		
		if (origenPantalla != null && !origenPantalla.toString().equals("")){
			model.addAttribute("origenPantalla", origenPantalla);
			model.addAttribute("selectProv", selectProv);
		}
		
		String listaBultos="";
		User usuario= (User)session.getAttribute("user");
		boolean centroParametrizado = this.devolucionLineaBultoCantidadService.isCentroParametrizado(usuario.getCentro().getCodCentro(), Constantes.DEVOLUCIONES_PDA_PERMISO_27);
		String devolucionFinalizada = "";
		if(centroParametrizado){
			listaBultos=this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(),tDevolucionLineaActualLst.get(0));
			devolucionFinalizada = this.devolucionesService.esProveedorSinFinalizar(session.getId(),tDevolucionLineaActualLst.get(0).getDevolucion(),null, Constantes.PDA);
		}
		model.addAttribute("listaBultos", listaBultos);
		model.addAttribute("centroParametrizado", centroParametrizado);
		model.addAttribute("devolucionFinalizada", devolucionFinalizada);
		
		return resultado;
	}

	@RequestMapping(value = "/pdaGetImageP62", method = RequestMethod.GET)
	public void doGet(@RequestParam(value = "codArticulo", required = true) Long codArticulo,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		User user = (User) session.getAttribute("user");
		try {
			Utilidades.cargarImagenPistola(codArticulo, response, "gif", null, 80F, 235F, 83F);
		} catch (Exception e) {
			logger.error("###############################");
			logger.error(StackTraceManager.getStackTrace(e));	
			logger.error("centro: " + user.getCentro().getCodCentro());
			logger.error("codArticulo: " + codArticulo);
			logger.error("###############################");
		}
	}

	@RequestMapping(value = "/pdaP62RealizarDevolucionFinCampania",method = RequestMethod.POST)
	public String processForm(@Valid final Devolucion devolucionConLineas,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws Exception {

		String resultado = "pda_p62_realizarDevolucionFinDeCampania";
		String accion = request.getParameter("accion");
		String origenPantalla = request.getParameter("origenPantalla");
		String selectProv = request.getParameter("selProv");
		String proveedor = null;
		String proveedorAnt = null;
		String flagRefPermanentesAnt = null;
		DevolucionCatalogoEstado devol = null; 
		List<DevolucionLinea> listTDevolucionesModificadas = new ArrayList<DevolucionLinea>();
		
		//Obtención de datos del filtro. Se obtiene el nuevo proveedor seleccionado y la nueva referencia introducida en el filtro
		PdaDatosCabecera pdaDatosCabecera = new PdaDatosCabecera();
		pdaDatosCabecera.setCodArtCab((request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
		pdaDatosCabecera.setProveedor((request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));

		//Obtención del código articulo de filtrado
		Long codArtFiltro = null;
		//Se mantiene el criterio de cabecera entre las diferentes pantallas de las devoluciones
		if (pdaDatosCabecera != null && pdaDatosCabecera.getCodArtCab() != null && !"".equals(pdaDatosCabecera.getCodArtCab().trim())){
			try{
				//Obtención de la referencia a filtrar
				PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCabecera.getCodArtCab());
				String codigoError = pdaArticulo.getCodigoError();
				if (codigoError == null || codigoError.equals("0")){
					codArtFiltro = new Long(pdaArticulo.getCodArt());
				}
			}catch(Exception e){ //Si hay error en carga de referencia se devuelve la lista vacía
			}
		}

		//Buscar la referencia madre
		if (codArtFiltro != null){
			//Obtenemos las referencias asociadas a una devolución
			//Y obtenemos la referencia madre
			RefAsociadas referenciasAsociadas = new RefAsociadas();
			referenciasAsociadas.setCodArticuloHijo(codArtFiltro);
			List<RefAsociadas> refAsociadasLst = refAsociadasServiceImpl.findAll(referenciasAsociadas);

			if(refAsociadasLst != null && refAsociadasLst.size() > 0){
				codArtFiltro = refAsociadasLst.get(0).getCodArticulo();
			}
		}

		User user = (User) session.getAttribute("user");

		//Si tiene permiso para que se vea la imagen del artículo la paginación se hará de uno en uno. Si no, la paginación será de dos en dos
		int numeroElementosPorPagina = Constantes.DEVOLUCIONES_PDA_PAGS_FIN_CAMPANA;
		if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones())
				&& !user.getCentro().getOpcHabil().isEmpty()
				&& user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_FOTO_27) != -1) {
			numeroElementosPorPagina = Constantes.DEVOLUCIONES_PDA_PAGS_FIN_CAMPANA_CON_IMAGEN;
		}else if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones())
				&& !user.getCentro().getOpcHabil().isEmpty()
				&& user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_SIN_FOTO_28) != -1) {
			numeroElementosPorPagina = Constantes.DEVOLUCIONES_PDA_PAGS_FIN_CAMPANA_CON_IMAGEN;
		}

		//Paginación de devolución linea
		Page<TDevolucionLinea> pagDevolucionLineas = null;	 
		int primerElementoAMostrar = 0;
		int filaPrimerElementoAMostrar = 0;
		Long page = new Long(1);
		if(request.getParameter("paginaActual") != "" && request.getParameter("paginaActual") != null){
			page = new Long((request.getParameter("paginaActual"))); //Recuperamos la página actual de pantalla.
		}

		//Obtención de la devolución actual
		String devolucionId = request.getParameter("devolucion");

		//Obtenemos la página de antes de paginar, para más tarde buscar la línea de devolución antes de paginar y en el caso de haber cambios en su bulto o cantidad actualizar la tabla temporal.
		Long paginaAntesDePaginar = new Long(page);

		//En caso de paginaciones se actualiza la página a mostrar
		if (Constantes.DEVOLUCIONES_PDA_ACCION_FILTRAR.equals(accion)){
			page = new Long(1);
		}else if (Constantes.DEVOLUCIONES_PDA_ACCION_PRIMERA_PAGINA.equals(accion)){
			page = new Long(1);
		}else if (Constantes.DEVOLUCIONES_PDA_ACCION_ANTERIOR_PAGINA.equals(accion)){
			page = page - 1;
		}else if (Constantes.DEVOLUCIONES_PDA_ACCION_SIGUIENTE_PAGINA.equals(accion)){
			page = page + 1;
		}else if (Constantes.DEVOLUCIONES_PDA_ACCION_ULTIMA_PAGINA.equals(accion)){
			//page = ultima;
		}

		List<TDevolucionLinea> listTDevolucionLinea = null;
		List<TDevolucionLinea> listTDevolucionLineaAnt = null;
		
		// Objeto que se va a ir completando con lo modificado en pantalla a medida que avanza el código
		// para posteriormente llevarlo a SIA.
		Devolucion devolucion = new Devolucion();

		//Mirar si se ha seleccionado el combo de ref permanentes
		String flagRefPermanentes = null;

		//Obtener el idioma
		Locale locale = LocaleContextHolder.getLocale();

		//Obtener la opción del combobox de proveedores seleccionada recientemente
		String refPermanentes = this.messageSource.getMessage("pda_p62_refPermanentes", null,locale);
		
		//Si la opcion es ref. permanentes, buscar las líneas con FLG_CONTINUIDAD en 'S'
		if((refPermanentes).equals(pdaDatosCabecera.getProveedor())){
			flagRefPermanentes = "S";
		}else{
			proveedor = pdaDatosCabecera.getProveedor();
		}

		try {
			devolucion.setDevolucion(new Long(devolucionId));

			//Obtención de la devolución de la lista de devoluciones
			@SuppressWarnings("unchecked")
			List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
			devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
			devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);

			//Como esta lista se va a usar para detectar si hay devoluciones editadas, y al filtrar se cambia de proveedor, es necesario saber que proveedor había antes de realizar el cambio para así obtener la lista correcpondiente.
			proveedorAnt = request.getParameter("proveedorAnterior");

			if((refPermanentes).equals(proveedorAnt)){
				flagRefPermanentesAnt = "S";
				proveedorAnt = null;
			}

			if (origenPantalla != null
				&& (origenPantalla.toString().equals("pdaP104") || origenPantalla.toString().equals("pdaP105"))){
				if (selectProv != null && !selectProv.toString().equals("")){
					proveedor = selectProv.substring(0, selectProv.indexOf("-"));
					proveedorAnt = proveedor;
				}
			}
			
			//Obtenemos la lista de devolución antes de cambiar de proveedor.
			// PRUEBA. Es factible eliminar estas dos sentencias ya que sólo lo usa para saber si hay datos
			//         y buscar la línea de devolución antes de paginar.
//			listTDevolucionLineaAnt = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null, null, proveedorAnt, flagRefPermanentesAnt);
//			listTDevolucionLineaAnt = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLineaAnt);
//			listTDevolucionLinea = listTDevolucionLineaAnt;
			// FIN PRUEBA.

			
			// Puedo recuperar los valores de la sesion en lugar de volver a consultar los datos.
			// No parecen necesarias las dos sentencias de consulta anteriores.
			devol = (DevolucionCatalogoEstado) session.getAttribute("devCatalogoEstado");
			listTDevolucionLineaAnt = devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).gettDevLineasLst();
			listTDevolucionLinea = listTDevolucionLineaAnt;

			
			//Se mira que se haya modificado algún elemento. Este flag se utilizará para entrar en la función de actualización plsql si ha habido algún cambio.
			boolean elementosGuardados = false;
			DevolucionLineaModificada devolucionLineaActualizar = new DevolucionLineaModificada();
			
			//Mirar si ha habido cambios en la línea de devolución si se pagina o siguiente accion o finaliza.
			if (listTDevolucionLineaAnt.size() > 0 &&
					(Constantes.DEVOLUCIONES_PDA_ACCION_PRIMERA_PAGINA.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_ANTERIOR_PAGINA.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_SIGUIENTE_PAGINA.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_ULTIMA_PAGINA.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_GUARDAR.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_SIGUIENTE_VACIO.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_FILTRAR.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_VERCABECERA.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_MASINFO.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_VERFOTO.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_STOCKLINK.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_STOCKDEVUELTOLINK.equals(accion))){
				
				//Cada vez que paginemos, mirar si se ha actualizado el bulto o la cantidad y en ese caso, se actualizará la tabla temporal.
				// Recupera todas las líneas de devolucion que hay en PANTALLA.
				List<TDevolucionLinea> listaTDevolucionLineaPantalla = devolucionConLineas.gettDevLineasLst();

				List<DevolucionLinea> listaDevLineasPantalla = devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).getDevLineas();

				int numeroLineasVisibles = listaTDevolucionLineaPantalla.size();

				for (int i=0;i<numeroLineasVisibles;i++){

					//Buscamos la línea de devolución antes de paginar.
					// Obtiene los datos de la devolución ANTES del cambio.
					TDevolucionLinea devolucionLineaAntesDePaginar = listTDevolucionLineaAnt.get(((paginaAntesDePaginar.intValue() - 1)*numeroElementosPorPagina) + i);

					//Creamos un booleano que indique si se ha modificado la línea de devolución
					boolean isLineaModificada = false;

					//Creamos un objeto que guarde los cambios de las líneas de devolución
					devolucionLineaActualizar = new DevolucionLineaModificada();

					TDevolucionLinea devolucionLinea = new TDevolucionLinea();
					devolucionLinea = listaTDevolucionLineaPantalla.get(i);	
					devolucionLineaActualizar.setEstadoCerrado(devolucionLinea.getEstadoCerrado());
					devolucionLineaActualizar.setBultoEstadoCerrado(devolucionLinea.getBultoEstadoCerrado());
					
					if(devolucionLinea.getVariosBultos()==null || !devolucionLinea.getVariosBultos()){
						//Controlamos que se haya cambiado el bulto de la línea de devolución
						if (devolucionLineaAntesDePaginar.getBulto() == null){
							if (devolucionLinea.getBulto() != null){
	
								//Obtener el bulto nuevo
								devolucionLineaActualizar.setBulto(devolucionLinea.getBulto());
	
								//Se ha modificado la línea de devolución
								isLineaModificada = true;
							}
						}else{
							if (!devolucionLineaAntesDePaginar.getBulto().equals(devolucionLinea.getBulto())){
								//Se ha modificado la línea de devolución
								isLineaModificada = true;
							}
							//Obtener el bulto nuevo
							devolucionLineaActualizar.setBulto(devolucionLinea.getBulto());
						}
	
						//Controlamos que se haya cambiado la cantidad del estado de la devolución, stockDevuelto
						if (devolucionLineaAntesDePaginar.getStockDevuelto() == null){
							if (devolucionLinea.getStockDevuelto() != null){
								//Obtener el stockDevuelto nuevo
								devolucionLineaActualizar.setStockDevuelto(devolucionLinea.getStockDevuelto());
	
								//Se ha modificado la línea de devolución
								isLineaModificada = true;
							}
						}else{
							if(!devolucionLineaAntesDePaginar.getStockDevuelto().equals(devolucionLinea.getStockDevuelto())){
								//Se ha modificado la línea de devolución
								isLineaModificada = true;
							}
	
							//Obtener el stockDevuelto nuevo
							devolucionLineaActualizar.setStockDevuelto(devolucionLinea.getStockDevuelto());
						}
						
						//Controlamos que se hayan cambiado las bandejas del estado de la devolución, stockDevueltoBandeja
						if(devolucionLineaAntesDePaginar.getStockDevueltoBandejas() == null){
							if(devolucionLinea.getStockDevueltoBandejas() != null){
								//Obtener el stockDevueltoBandejas nuevo
								devolucionLineaActualizar.setStockDevueltoBandejas(devolucionLinea.getStockDevueltoBandejas());
	
								//Se ha modificado la línea de devolución
								isLineaModificada = true;
							}
						}else{
							if(!devolucionLineaAntesDePaginar.getStockDevueltoBandejas().equals(devolucionLinea.getStockDevueltoBandejas())){
								//Se ha modificado la línea de devolución
								isLineaModificada = true;
							}
	
							//Obtener el stockDevueltoBandejas nuevo
							devolucionLineaActualizar.setStockDevueltoBandejas(devolucionLinea.getStockDevueltoBandejas());
						}
					}else{
						if(!devolucionLineaAntesDePaginar.getStockDevueltoBandejas().equals(devolucionLinea.getStockDevueltoBandejas())){
							//Se ha modificado la línea de devolución
							isLineaModificada = true;
						}

						//Obtener el stockDevueltoBandejas nuevo
						devolucionLineaActualizar.setStockDevueltoBandejas(devolucionLinea.getStockDevueltoBandejas());
					}

					//Si la línea de devolución se ha modificado, actualizar las
					// tablas temporales T_DEVOLUCION y T_MIS_DEVOLUCIONES_BULTO.
					if (isLineaModificada){
						//Creamos dos nuevos objetos, para actualizar la devolución linea
						Devolucion devolucionActualizar = new Devolucion();

						//Creamos una lista para insertar la línea de devolución modificada
						List<DevolucionLineaModificada> devLineaLstModificada = new ArrayList<DevolucionLineaModificada>();

						//Insertamos en la línea el artículo al que corresponde esa línea y el estado modificado como código 9
						devolucionLineaActualizar.setCodError(new Long(9));
						devolucionLineaActualizar.setCodArticulo(devolucionLineaAntesDePaginar.getCodArticulo());				


						TDevolucionLinea nuevoRegistroTDevLinea = new TDevolucionLinea();
						int idxNuevaDevLinMod = 0;

						nuevoRegistroTDevLinea.setCodArticulo(devolucionLineaActualizar.getCodArticulo());
						idxNuevaDevLinMod=listTDevolucionLinea.indexOf(nuevoRegistroTDevLinea);

						devolucionLineaActualizar.setStockActual(listTDevolucionLinea.get(idxNuevaDevLinMod).getStockActual());
						devolucionLineaActualizar.setStockDevolver(listTDevolucionLinea.get(idxNuevaDevLinMod).getStockDevolver());


						//Insertamos la línea de devolución modificada
						devLineaLstModificada.add(devolucionLineaActualizar);


						devolucionActualizar.setDevolucion(devolucion.getDevolucion());	
						devolucionActualizar.setDevLineasModificadas(devLineaLstModificada);


						// Actualiza la T_DEVOLUCION con los cambios.
						this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizar,false);								
						
						// Borra la línea de T_MIS_DEVOLUCIONES_BULTO para luego añadirla nuevamente con los cambios de pantalla.
						this.devolucionLineaBultoCantidadService.deleteLineasTablaBultoCantidad(session.getId(),devolucion.getDevolucion(),devolucionLineaAntesDePaginar.getCodArticulo());
						
						List<TDevolucionBulto> listTDevolucionLineaBultoCantidad = new ArrayList<TDevolucionBulto>();
						
						//Se inicializa el objeto. Cada objeto de este tipo será un registro de la tabla temporal
						if (devolucionLineaActualizar.getBulto() != null){
							TDevolucionBulto nuevoRegistroBultoCantidad = null;
							nuevoRegistroBultoCantidad = new TDevolucionBulto();
							nuevoRegistroBultoCantidad.setIdSesion(session.getId());
							nuevoRegistroBultoCantidad.setFechaGen(new Date());
							nuevoRegistroBultoCantidad.setDevolucion(devolucion.getDevolucion());
							nuevoRegistroBultoCantidad.setBulto(devolucionLineaActualizar.getBulto());
							nuevoRegistroBultoCantidad.setCodArticulo(devolucionLineaActualizar.getCodArticulo());
							nuevoRegistroBultoCantidad.setBultoOri(devolucionLineaActualizar.getBulto());
							nuevoRegistroBultoCantidad.setStock(devolucionLineaActualizar.getStockDevuelto());
							nuevoRegistroBultoCantidad.setStockOri(devolucionLineaActualizar.getStockDevuelto());
							nuevoRegistroBultoCantidad.setEstadoCerrado(devolucionLineaActualizar.getEstadoCerrado());
							nuevoRegistroBultoCantidad.setCodError(devolucionLineaActualizar.getCodError());
							nuevoRegistroBultoCantidad.setDescError("");
							nuevoRegistroBultoCantidad.setCreationDate(new Date());
							
							listTDevolucionLineaBultoCantidad.add(nuevoRegistroBultoCantidad);
							
							
							// Se actualizan los valores de los bultos en la lista "devLineas".
							DevolucionLinea nuevoRegistroDevLinea = new DevolucionLinea();
							int idxNuevaDevLinea = 0;
							
							nuevoRegistroDevLinea.setCodArticulo(devolucionLineaActualizar.getCodArticulo());
							idxNuevaDevLinea=listaDevLineasPantalla.indexOf(nuevoRegistroDevLinea);
							nuevoRegistroDevLinea=listaDevLineasPantalla.get(idxNuevaDevLinea);
							if (nuevoRegistroDevLinea.getListaBultoCantidad() == null || nuevoRegistroDevLinea.getListaBultoCantidad().isEmpty()){
								 nuevoRegistroDevLinea.getListaBultoCantidad().add(new BultoCantidad());
							}
							nuevoRegistroDevLinea.getListaBultoCantidad().get(0).setBulto(devolucionLineaActualizar.getBulto());
							nuevoRegistroDevLinea.getListaBultoCantidad().get(0).setEstadoCerrado(devolucionLineaActualizar.getEstadoCerrado());
							nuevoRegistroDevLinea.getListaBultoCantidad().get(0).setStock(devolucionLineaActualizar.getStockDevuelto());
							nuevoRegistroDevLinea.getListaBultoCantidad().get(0).setDescError("");
//							nuevoRegistroDevLinea.getListaBultoCantidad().get(0).setStockBandejas(devolucionLineaActualizar.getStockDevueltoBandejas());
							nuevoRegistroDevLinea.getListaBultoCantidad().get(0).setCodError(devolucionLineaActualizar.getCodError());

							listaDevLineasPantalla.set(idxNuevaDevLinea, nuevoRegistroDevLinea);
							devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).setDevLineas(listaDevLineasPantalla);
//							devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).gettDevLineasLst().get(0).setEstadoCerrado(devolucionLineaActualizar.getEstadoCerrado());

						}
						
						try {
							this.devolucionLineaBultoCantidadService.insertAll(listTDevolucionLineaBultoCantidad);
						} catch (Exception e) {
							logger.error("ERROR insertar Tabla Lista Bulto Cantidad= "+e.toString());
							e.printStackTrace();
						}
						
						//Si se ha modificado algún elemento se actualizará con plsql
						elementosGuardados= true;
						
						// Código trasladado de las dos líneas inferiores.
						// Sólo es necesario volver a consultar los datos si se han modificado datos de bultos.
						listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion, null, null, proveedor, flagRefPermanentes);
						listTDevolucionLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLinea);
						
					// Código trasladado de las dos líneas inferiores.
					// Sólo es necesario volver a consultar los datos si se han modificado datos de bultos.
					}else{
						if (proveedor != proveedorAnt){
							listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion, null, null, proveedor, flagRefPermanentes);
							listTDevolucionLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLinea);
							
						}
					}
				}
			}
			
// PRUEBA. Intentar hacer que no tenga que consultar los datos.
			
			//Obtenemos la lista de devoluciones con el proveedor nuevo seleccionado.
//			listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion, null, null, proveedor, flagRefPermanentes);
//			listTDevolucionLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLinea);

// FIN PRUEBA. Intentar hacer que no tenga que consultar los datos.
			
			// validaciones antes de guardar
			// estas validaciones no se hacen en el modelo y es importante su visualizacion con mensaje
			boolean errorAlValidar = false;
			
			if (elementosGuardados
					|| Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR.equals(accion)){
				// MISUMI-114 ampliacion
				// Validar la devolución por su coste maximo
				// si el coste maximo esta definido, y la suma del coste de devolucion supera el 101% del maximo no dejamos guardar
				if (devolucion.getCosteMaximo()!=null && devolucion.getCosteMaximo() > 0L){
					Double sumaEuros = this.getCalcularSumaEuros(listTDevolucionLinea);
					Double cotaMaxima = devolucion.getCosteMaximo() * 1.01;
					if(sumaEuros.compareTo(cotaMaxima) > 0){
						String err = "ERR_COSTE";
						model.addAttribute("actionP67Volver","pdaP62RealizarDevolucionFinCampania.do");
	
						errorAlValidar = true;
						model.addAttribute("codErrorActualizar",err);
						resultado = "pda_p67_errorDevolucionLinea";
					}
				}
			}

			//Si no hay error al guardar. Ejecutamos las acciones de la devolución. Si no, se muestra el error de guardado.
			//Y se ignora la accion (Guardar, VerCabecera,MasInfo,Finalizar) que se estaba realizando. 
			boolean errorAlGuardar = errorAlValidar;

			//Se guardan los cambios realizados en todos los casos.
			if (!errorAlValidar && elementosGuardados &&
					(Constantes.DEVOLUCIONES_PDA_ACCION_GUARDAR.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_PRIMERA_PAGINA.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_ANTERIOR_PAGINA.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_SIGUIENTE_PAGINA.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_ULTIMA_PAGINA.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_GUARDAR.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_SIGUIENTE_VACIO.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_FILTRAR.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_VERCABECERA.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_VERFOTO.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_STOCKLINK.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_STOCKDEVUELTOLINK.equals(accion))){

				// Actualizo los valores de "devol" para dejarlo actualizado en la variable de Sesion.
//				devolModif = devol.getListDevolucionEstado().get(0).getListDevolucion().get(0);
////				devolModif.settDevLineasLst(listTDevolucionLinea);
//				
//				for (TDevolucionLinea tDevolucionLinea : listTDevolucionLinea){
//					
//					int numLineasDev = devolModif.getDevLineas().size();
//
//					for (int i=0; i<numLineasDev; i++){
//						if (devolModif.getDevLineas().get(i).getCodArticulo().equals(tDevolucionLinea.getCodArticulo())){
//							devolModif.getDevLineas().get(i).setBulto(tDevolucionLinea.getBulto());
//							devolModif.getDevLineas().get(i).setStockDevuelto(tDevolucionLinea.getStockDevuelto());
//							devolModif.getDevLineas().get(i).setStockDevueltoBandejas(tDevolucionLinea.getStockDevueltoBandejas());
//							if (devolModif.getDevLineas().get(i).getListaBultoCantidad()!= null && !devolModif.getDevLineas().get(i).getListaBultoCantidad().isEmpty()){
//								devolModif.getDevLineas().get(i).getListaBultoCantidad().get(0).setBulto(tDevolucionLinea.getBulto());
//								devolModif.getDevLineas().get(i).getListaBultoCantidad().get(0).setStock(tDevolucionLinea.getStockDevuelto());
////								devolModif.getDevLineas().get(0).getListaBultoCantidad().get(0).setStockBandejas(tDevolucionLinea.getStockDevueltoBandejas());
//								devolModif.getDevLineas().get(i).getListaBultoCantidad().get(0).setEstadoCerrado(tDevolucionLinea.getEstadoCerrado());
//							}
//						}
//					}
//				}
//				
//				devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).setDevLineas(devolModif.getDevLineas());
				
				devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).settDevLineasLst(listTDevolucionLinea);
				session.setAttribute("devCatalogoEstado", devol);

				//Si se ha pulsado el botón actualizar
				DevolucionCatalogoEstado devolucionCatalogoEstadoActualizada = null;

				//Una vez tenemos la tabla temporal actualizada, obtenemos los elementos modificados.
				listTDevolucionesModificadas = this.devolucionesService.findLineasDevolucionEditadas(session.getId(),new Long("9"));
				
				//Si hay líneas de devolución editadas, actualizamos esas líneas mediante el PLSQL, actualizamos la temporal con los códigos de error correspondientes
				//y obtenemos las líneas del grid actualizadas de la tabla temporal actualizada. Si no hay modificaciones, obtenemos la lista tal cual se encuentra
				//en la tabla temporal.
				if (listTDevolucionesModificadas != null && !listTDevolucionesModificadas.isEmpty()){
					
					listTDevolucionesModificadas = this.rellenarEstructuraLineaBulto(listTDevolucionesModificadas, devolucionLineaActualizar);

					//Se insertan las líneas de devolución modificadas únicamente en la lista de líneas de devolución de la lista.
					devolucion.setDevLineas(listTDevolucionesModificadas);
					
					// Actualiza la devolución y obtenemos el resultado con las líneas de devolucion y sus códigos de error
					// Actualiza SIA.
					devolucionCatalogoEstadoActualizada = this.devolucionesService.actualizarDevolucion(devolucion);

					if (devolucionCatalogoEstadoActualizada != null && ((new Long("0")).equals(devolucionCatalogoEstadoActualizada.getpCodError()) || devolucionCatalogoEstadoActualizada.getpCodError() == null)){
						if (devolucionCatalogoEstadoActualizada.getListDevolucionEstado() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().size() > 0){
							if (devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
								//Obtenemos la devolucion actualizada
								Devolucion devolucionActualizada = devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().get(0);

								if (new Long("0").equals(devolucionActualizada.getCodError()) || devolucionActualizada.getCodError() == null){
									//Por cada línea de la devolución si el codError es 9, significa que se ha actualizado bien, al no haber cambio en el código de error insertado en el PLSQL. En ese caso insertamos a las líneas el código de error 8 
									//para indicar que han pasado de modificadas a guardadas. En caso contrario, se inserta el código de error obtenido. Estos cambios, habrá que guardarlos en la tabla temporal para así tener actualizados los estados de
									//cada línea y dibujar bien el grid, por lo cual utilizaremos el método updateTablaSesionLineaDevolucion. 

									DevolucionLineaModificada devLineaModificada = null;
									List<DevolucionLineaModificada> devLineaModificadaActualizada = new ArrayList<DevolucionLineaModificada>();
									Long codError = null;
									for (DevolucionLinea devLinea:devolucionActualizada.getDevLineas()){		
										//Si coinciden en código de artículo estamos hablando de la misma línea de devolución
										if ((new Long("0")).equals(devLinea.getCodError())){
											codError = new Long("8");
										}else{
											codError = devLinea.getCodError();
										}
										devLineaModificada = new DevolucionLineaModificada(devLinea.getBulto(), devLinea.getStockDevuelto(), devLinea.getStockActual(), codError, devLinea.getCodArticulo(), devLinea.getStockDevueltoBandejas());
										devLineaModificadaActualizada.add(devLineaModificada);
									}
									devolucionActualizada.setDevLineasModificadas(devLineaModificadaActualizada);

									//Más tarde se limpian los estados. De esta forma se evita que al actualizar dos veces se mantengan los errores y disquetes de los estados Anteriores y solo se visualicen
									//los nuevos.		
									this.devolucionesService.resetDevolEstados(session.getId());			

									//Actualizamos los códigos de error de las devoluciones. Además también actualizamos los datos de BULTO_ORI y STOCKDEVUELTO_ORI de la tabla temporal
									// Actualiza la tabla de UNIDIN (T_DEVOLUCIONES).
									this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizada,true);									

									//Como se quiere actualizar y finalizar en este caso, se cambia la accion a finalizarPopup.
									if (Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR_POPUP_REFERENCIAS_SIN_INFORMAR.equals(accion)){
										//FINALIZAMOS LA DEVOLUCION ESPECIAL
										accion = Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR_POPUP;
										request.setAttribute("accion", accion);
										//flgRellenarHuecosStockDevuelto = 'S'
									}									
								}else{
									String err = "ERR_ACT";
									model.addAttribute("actionP67Volver","pdaP62RealizarDevolucionFinCampania.do");

									errorAlGuardar = true;
									model.addAttribute("codErrorActualizar",err);
									resultado = "pda_p67_errorDevolucionLinea";
								}
							}else{
								String err = "ERR_ACT";
								model.addAttribute("actionP67Volver","pdaP62RealizarDevolucionFinCampania.do");

								errorAlGuardar = true;
								model.addAttribute("codErrorActualizar",err);
								resultado = "pda_p67_errorDevolucionLinea";
							}
						}else{
							String err = "ERR_ACT";
							model.addAttribute("actionP67Volver","pdaP62RealizarDevolucionFinCampania.do");

							errorAlGuardar = true;
							model.addAttribute("codErrorActualizar",err);
							resultado = "pda_p67_errorDevolucionLinea";
						}
					}else{
						String err = "ERR_ACT";
						model.addAttribute("actionP67Volver","pdaP62RealizarDevolucionFinCampania.do");

						errorAlGuardar = true;
						model.addAttribute("codErrorActualizar",err);
						resultado = "pda_p67_errorDevolucionLinea";
					}
				}
				// PRUEBA. Se comenta ya que en el anterior IF en el que se había detectado modificación
				//         ya se había consultados los datos con los cambios.
				//Se obtienen las devoluciones con sus estados actualizados
//				listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion, null, null, proveedor, flagRefPermanentes);	
//				listTDevolucionLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLinea);
				// PRUEBA
			}

			//Insertamos la lista de los bultos por proveedor en la devolución para que al ocurrir un error y volver atrás, guarde los estados
			devolucion.setBultoPorProveedorLst(devolucionConLineas.getBultoPorProveedorLst());

			if (Constantes.DEVOLUCIONES_PDA_ACCION_ULTIMA_PAGINA.equals(accion) &&  !errorAlGuardar){
				page = new Long(listTDevolucionLinea.size()/numeroElementosPorPagina);
				if (listTDevolucionLinea.size()%numeroElementosPorPagina>0){
					page++;
				}
			}else if (Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR.equals(accion) &&  !errorAlGuardar){
				//Redirige a la pantalla de pregunta de si se quiere o no finalizar la devolución
				// o a la que completa el dato de RMA
				boolean requiereRma = Constantes.TIPO_RMA_TIENDA.equals(devolucion.getTipoRMA())
						&& (devolucion.getCodRMA()==null || devolucion.getCodRMA().isEmpty());

				model.addAttribute("devolucion", devolucion);
				model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
				model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
				model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));

				// si hay que informar el RMA vamos a una pantalla para ello, sino a confirmar
				if(requiereRma) {
					model.addAttribute("actionP80","pdaP62RealizarDevolucionFinCampania.do");
					if (origenPantalla != null && origenPantalla != ""){
						model.addAttribute("origenPantalla", origenPantalla);
						model.addAttribute("selectProv", selectProv);
					}
					return "pda_p80_pedirRmaDevolucion";
				}else{
					//Redirige a la pantalla de pregunta de si se quiere o no finalizar la devolución
					String existenStockDevueltosConCero = devolucionesService.existeStockDevueltoConCeroTablaSesionLineaDevolucion(session.getId(),devolucion);

					model.addAttribute("existenStockDevueltosConCero",existenStockDevueltosConCero);
					model.addAttribute("actionP68","pdaP62RealizarDevolucionFinCampania.do");
					if (origenPantalla != null && origenPantalla != ""){
						model.addAttribute("origenPantalla", origenPantalla);
						model.addAttribute("selectProv", selectProv);
					}
					return "pda_p68_msgFinalizarSiNo";
				}
			}else if (Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR_RMA.equals(accion) && !errorAlGuardar){
				// Venimos de la pantalla intermedia que informa el RMA
				// actualizamos el valor de RMA, 
				// no lo hacemos más arriba porque arriba se presupone que se modifican lineas y esto solo modifica la cabecera
				// además asi hacemos tratamiento de error sobre la pantalla de consulta de RMA
				// Si fuera bien vamos a la pantalla de confirmar la finalizacion
				if(request.getParameter("finalizar_si") != null){
					String msgErrorKey = null;
					String codRma = request.getParameter("codRMA");
					String codRmaLastValue = devolucion.getCodRMA();

					//Mirar si ha habido cambios en la devolucion y actualizarlos
					if(codRma==null || codRma.isEmpty()){
						// es obligatorio y devolvemos error
						msgErrorKey = "pda_p80_pedirRmaDevolucion.msg";
					}else if(!codRma.equals(devolucion.getCodRMA())){
						// Indicamos el codigo de rma que ha informado el usuario
						devolucion.setCodRMA(codRma);
						DevolucionCatalogoEstado actualizada = devolucionesService.actualizarDevolucion(devolucion);
						if(actualizada==null){
							msgErrorKey = "pda_p80_pedirRmaDevolucion.error.actualizar";
						}else if(actualizada!=null && actualizada.getpCodError()!=null && actualizada.getpCodError().longValue() != 0L){
							logger.error("Error actualizando el codigo de RMA: " + actualizada.getpCodError() + " - " + actualizada.getpDescError());
							msgErrorKey = "pda_p80_pedirRmaDevolucion.error.actualizar";
						}
					}

					model.addAttribute("devolucion", devolucion);
					model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
					model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
					model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));
					
					if (msgErrorKey!=null){
						// volvemos a pedir el RMA con mensaje de error y restauramos la situacion anterior
						devolucion.setCodRMA(codRmaLastValue);
						model.addAttribute("actionP80","pdaP62RealizarDevolucionFinCampania.do");
						model.addAttribute("msgErrorKey", msgErrorKey);

						return "pda_p80_pedirRmaDevolucion";
					}else{
						// navegamos a la de finalizar para confirmar
						String existenStockDevueltosConCero = devolucionesService.existeStockDevueltoConCeroTablaSesionLineaDevolucion(session.getId(),devolucion);
						model.addAttribute("existenStockDevueltosConCero",existenStockDevueltosConCero);
						model.addAttribute("actionP68","pdaP62RealizarDevolucionFinCampania.do");

						return "pda_p68_msgFinalizarSiNo";
					}
				}
			}else if (Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR_POPUP.equals(accion) &&  !errorAlGuardar){
				if (request.getParameter("finalizar_si") != null){
					//Obtenemos el flgRellenarHuecos. En este caso se llama existenStockDevueltosConCeros conseguido en la opción Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR
					String existenStockDevueltosConCero = request.getParameter("existenStockDevueltosConCero");

					DevolucionEmail devolucionEmail = devolucionesService.finalizarDevolucion(devolucion,existenStockDevueltosConCero,"PDA");
					if (devolucionEmail != null && Constantes.DEVOLUCIONES_PDA_CODIGO_FINALIZAR_CORRECTO.equals(devolucionEmail.getCodError())){
						model.addAttribute("devolucion", devolucion);

//						devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).settDevLineasLst(listTDevolucionLinea);
//						session.setAttribute("devCatalogoEstado", devol);

						return "pda_p64_realizarDevolucionSatisfactoria";
					//Si da otro tipo de error, va a una pantalla que permite ver el error.
					}else{
						//Si hay error después de actualizar con ceros las cantidades vacías y bultos vacíos, actualizamos la tabla temporal con los ceros para que los datos sean los reales.
						if(("S").equals(existenStockDevueltosConCero)){
							devolucionesService.updateConCeroTablaSesionLineaDevolucion(session.getId(), devolucion);
						}
						model.addAttribute("devolucion", devolucion);
						model.addAttribute("msgError", devolucionEmail.getMsgError());
						model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
						model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
						model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));
						model.addAttribute("actionP65Volver","pdaP62RealizarDevolucionFinCampania.do");

//						devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).settDevLineasLst(listTDevolucionLinea);
//						session.setAttribute("devCatalogoEstado", devol);

						return "pda_p65_realizarDevolucionErronea";
					}	
				}						
			} else if (Constantes.DEVOLUCIONES_PDA_ACCION_SIGUIENTE_VACIO.equals(accion) &&  !errorAlGuardar){
				//Buscamos el primer elemento de la lista que tenga stockDevuelto null o bulto null
				//Para calcular el primer elemento pasamos la posicion inicial (page - 1) por que las páginas van de 1 a n y los elementos de 0 a n-1
				//Hay que tener en cuenta el número de elementos por página porque pueden ser más de 1 y parametrizable por BD
				primerElementoAMostrar = this.devolucionesService.firstLineaDevolucionStockOrBultoNull((int)(long)paginaAntesDePaginar, listTDevolucionLinea);
				page = (long) ((primerElementoAMostrar/numeroElementosPorPagina) + 1);
				filaPrimerElementoAMostrar = primerElementoAMostrar % numeroElementosPorPagina;
			} else if ((Constantes.DEVOLUCIONES_PDA_ACCION_VERCABECERA.equals(accion) && !errorAlGuardar) || Constantes.DEVOLUCIONES_PDA_ACCION_VERCABECERADESDEERROROK.equals(accion)){
				//Obtención de la devolución de la lista de devoluciones
				model.addAttribute("devolucion", devolucion);
				model.addAttribute("actionP61Volver","pdaP62RealizarDevolucionFinCampania.do");
				model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
				model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
				model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));
				model.addAttribute("origenPantalla", origenPantalla);
				model.addAttribute("selectProv", selectProv);

//				devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).settDevLineasLst(listTDevolucionLinea);
//				session.setAttribute("devCatalogoEstado", devol);

				return "pda_p61_realizarDevolucionCabecera";
			}else if((Constantes.DEVOLUCIONES_PDA_ACCION_VERFOTO.equals(accion) && !errorAlGuardar)){
				model.addAttribute("devolucion", devolucion);
				model.addAttribute("codArticuloFoto", request.getParameter("codArticuloFoto"));
				model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
				model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
				model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));
				model.addAttribute("origenPantalla", origenPantalla);
				model.addAttribute("selectProv", selectProv);

				return "pda_p70_fotoAmpliada";
			}else if((Constantes.DEVOLUCIONES_PDA_ACCION_STOCKLINK.equals(accion) && !errorAlGuardar)){
				//Actualizamos la variable consultaStock para que se muestren los datos de stock de esta devolución
				String fila =  request.getParameter("fila");
				Long codigoArticulo = Long.parseLong(request.getParameter("codArticulo"+fila));
				String estructuraComercial = request.getParameter("estructuraComercial"+fila);

				VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codigoArticulo);

				ReferenciasCentro referenciasCentro = new ReferenciasCentro();
				referenciasCentro.setCodArt(codigoArticulo);

				referenciasCentro.setCodCentro(user.getCentro().getCodCentro());
				referenciasCentro.setDiarioArt(vDatosDiarioArtResul);

				//Es necesario saber 
				VSurtidoTienda surtidoTienda = obtenerSurtidoTienda(referenciasCentro,false,false);

				if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro()!= null && !"".equals(surtidoTienda.getMarcaMaestroCentro())){
					model.addAttribute("MMC", surtidoTienda.getMarcaMaestroCentro());
				}else{
					model.addAttribute("MMC","N");
				}

				ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
				stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
				stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
				List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
				listaReferencias.add(BigInteger.valueOf(codigoArticulo));


				// DESCOMENTARLO UNA VEZ ESTE HECHO EL NUEVO SERVICIO DE TEXTIL. CORRESPONDE A LA PETICION 47885 (LA PARTE DE PISTOLA)
				// SE ACTIVA SOLO PARA LOS CENTROS: 5042-SEGOVIA, 0358-ABADIÃO, 0175-IRUÃA, 5326-INFANTE, 410 EIBAR, 164 GERNIKA
				//Si la referencia consultada es TEXTIL, se deben obtener todas las referencia que compartan el mismo cÃ³digo de proveedor.
				if (estructuraComercial.startsWith("03")) {  //Es un artÃ­culo de textil

					stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA); //En este caso es una consulta basica, al 
					//WS se le pasara una lista de referencias
					List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(codigoArticulo);

					for (Long articulo : referenciasMismoModeloProveedor) {
						String strArticulo = articulo + "";
						if (!strArticulo.equals(codigoArticulo + "")) { //El articulo consultado por el usuario  (el de la cabecera) ya esta metido en la lista
							listaReferencias.add(BigInteger.valueOf(articulo));
						}
					}
				}

				stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
				try{
					ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
					paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
					ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
					
					if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
						logger.error("###########################################################################################################");
						logger.error("############################## CONTROLADOR: pdaP46GuardarInvLibController (processForm)	 ####");
						logger.error("###########################################################################################################");
					} 
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

				model.addAttribute("linkStock", "S");
				model.addAttribute("fila",fila);	

				session.setAttribute("fila", fila);
				session.setAttribute("estructuraComercial", estructuraComercial);
				session.setAttribute("paginaActual", paginaAntesDePaginar.intValue());
				session.setAttribute("referenciaFiltro", request.getParameter("referenciaFiltro"));
				session.setAttribute("proveedorFiltro", request.getParameter("proveedorFiltro"));
				session.setAttribute("devolucion", devolucion);

				//Redirigimos a la página de stock pdaP28CorreccionStockInicio.do
				redirectAttributes.addAttribute("codArt", codigoArticulo);
				redirectAttributes.addAttribute("origen", "DVFN");
				if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro()!= null && !"".equals(surtidoTienda.getMarcaMaestroCentro())){
					redirectAttributes.addAttribute("mmc", surtidoTienda.getMarcaMaestroCentro());
				}else{
					redirectAttributes.addAttribute("mmc", "N");
				}
				
				redirectAttributes.addFlashAttribute("origenPantalla", origenPantalla);
				redirectAttributes.addFlashAttribute("selectProv", selectProv);
				
				return "redirect:pdaP28CorreccionStockInicio.do";
				
			}else if((Constantes.DEVOLUCIONES_PDA_ACCION_STOCKDEVUELTOLINK.equals(accion) && !errorAlGuardar)){
				
				String fila =  request.getParameter("fila");
				Long.parseLong(request.getParameter("codArticulo"+fila));
				String estructuraComercial = request.getParameter("estructuraComercial"+fila);
				
				String stockDevueltoBandejasStr = request.getParameter("stockDevueltoBandejas"+fila); //Hidden
				Double stockDevueltoBandejas = null;
				if(stockDevueltoBandejasStr != null){
					stockDevueltoBandejas = Double.parseDouble(request.getParameter("stockDevueltoBandejas"+fila));
				}else{
					stockDevueltoBandejas = 0.0;
				}
				
				String stockDevueltoStr = request.getParameter("stockDevuelto"+fila);
				Double stockDevuelto = null;
				if(StringUtils.isNotBlank(stockDevueltoStr)){
					stockDevuelto = Double.parseDouble(request.getParameter("stockDevuelto"+fila));
				}else{
					stockDevuelto = 0.0;
				}							
				
				//Calculamos el elemento a mostrar
				if(codArtFiltro != null){
					TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
					devolucionLineaComparator.setCodArticulo(codArtFiltro);

					page =  new Long((listTDevolucionLinea.indexOf(devolucionLineaComparator)/numeroElementosPorPagina)+1);
				}

				int records = listTDevolucionLinea.size();
				int desdeSubList = ((page.intValue()-1) * numeroElementosPorPagina);
				int hastaSubList = ((page.intValue())*numeroElementosPorPagina);

				//Hay que ver desde que elemento hasta que elemento hay que hacer la sublista. Para ello utilizamos
				//el elemento desdeSubList y hastaSubList. Puede darse la situación de que hastaSubList sea mayor a los
				//elementos de la lista, por ejemplo existen 6 paginaciones y se enseñan 10 elementos por paginación,
				//en la paginación 6 el elemento desdeSubList será 50 y hastaSubList 60, pero si la sublista tiene
				//53 elementos da error! Por lo cual hay que hacer un caso en el que hastaSubList sea igual al número
				//de elementos de la lista!
				if(hastaSubList > records){
					hastaSubList = records;				
				}

				//Obtenemos la devolución seleccionada
				TDevolucionLinea tDevolActual = listTDevolucionLinea.subList(desdeSubList,hastaSubList).get(Integer.parseInt(fila));
				
				//Para mostrar la descripción
				String descripcion = tDevolActual.getDenominacion();
				Long codArt = tDevolActual.getCodArticulo();								
				String descCodArt = codArt.toString() + "-" + descripcion;
				
				//Para saber si los campos de bandejas y kgs serán editables o no.
				boolean fechaDeDevolucionPasada = devolucion.isFechaDeDevolucionPasada();
				
				redirectAttributes.addAttribute("codArt", codArt);
				redirectAttributes.addAttribute("descCodArt", descCodArt);
				redirectAttributes.addAttribute("stockDevuelto", stockDevuelto); //campo kgs del pop-up
				redirectAttributes.addAttribute("stockDevueltoBandejas", stockDevueltoBandejas);	 //Es el campo hidden que en el pop-up se pinta en el campo Bandejas
				redirectAttributes.addAttribute("fechaDevolucionPasada", fechaDeDevolucionPasada);	
				redirectAttributes.addAttribute("variosBultos", tDevolActual.getVariosBultos());
				redirectAttributes.addAttribute("origen", "DVFN");
				
				//MISUMI-269
				Double cantMaximaLin = tDevolActual.getCantidadMaximaLin();
				redirectAttributes.addAttribute("cantMaximaLin", cantMaximaLin);	
				
				session.setAttribute("fila", fila);
				session.setAttribute("estructuraComercial", estructuraComercial);
				session.setAttribute("paginaActual", paginaAntesDePaginar.intValue());
				session.setAttribute("referenciaFiltro", request.getParameter("referenciaFiltro"));
				session.setAttribute("proveedorFiltro", request.getParameter("proveedorFiltro"));
				session.setAttribute("devolucion", devolucion);
				redirectAttributes.addAttribute("origenPantalla", origenPantalla);
				redirectAttributes.addAttribute("selectProv", selectProv);
				return "redirect:pdaP73BandejasKgsInicio.do";
				
			}else if ((Constantes.DEVOLUCIONES_PDA_ACCION_MASINFO.equals(accion) && !errorAlGuardar)){
				
				//MASINFO Ocurre en esta Fin de campanya u orden de retirada cuando textil tiene datos extensos
				// o en orden de retirada cuadno lotes o caducidad tienen muchas fechas
				TDevolucionLinea tDevolActual = listTDevolucionLinea.get(page.intValue()-1);

				// si hay que mostrar de textil
				if(tDevolActual.getArea() !=null 
						&& tDevolActual.getArea().longValue() == 3L){
					model.addAttribute("conTextil", true);		
				}
				
				model.addAttribute("devolucionLinea", tDevolActual);
				model.addAttribute("actionP61Volver","pdaP62RealizarDevolucionFinCampania.do");
				model.addAttribute("actionP69","pdaP62RealizarDevolucionFinCampania.do");
				model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
				model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
				model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));

				return "pda_p69_ordenDeRetiradaDetalle";
				
			}else if(Constantes.DEVOLUCIONES_PDA_ACCION_CERRAR_BULTO.equals(accion) ){

				TDevolucionLinea devolucionLinea=devolucionConLineas.gettDevLineasLst().get(0);
//				this.devolucionLineaBultoCantidadService.deleteLineasTablaBultoCantidad(session.getId(), devolucionConLineas.getDevolucion(), devolucionConLineas.getCodArticulo());
				// Se gestiona las tablas de UNIDIN (T_DEVOLUCIONES, T_MIS_DEVOLUCIONES_BULTO)
				this.insertarTablaSesionLineaDevolucion(session.getId(), devolucionConLineas.getDevolucion(), devolucionConLineas.getCodArticulo(), devolucionLinea.getStockDevuelto(), devolucionLinea.getBulto(), devolucionLinea.getEstadoCerrado());
				this.actualizarDatosTablaSesionLineaBulto(devolucionConLineas.getDevolucion(), devolucionConLineas.getCodArticulo(),session.getId());

				// Actualiza tabla de UNIDIN (T_MIS_DEVOLUCIONES_BULTO con el nuevo valor de "estadoCerrado")
				this.devolucionLineaBultoCantidadService.actualizarEstadoBultoPorProveedor(session.getId(),devolucionLinea.getEstadoCerrado(),devolucionConLineas.getDevolucion(),Long.valueOf(proveedorAnt==""?"0":proveedorAnt),null,devolucionLinea.getBulto());
				// Actualiza tablas de SIA a través del paquete.
				this.devolucionLineaBultoCantidadService.procedimientoActualizarEstadoBultoPorProveedor(Constantes.CERRAR_BULTO,devolucionConLineas.getDevolucion(),Long.valueOf(proveedorAnt),null,devolucionLinea.getBulto());
				
				TDevolucionLinea nuevoRegistroTDevLinea = new TDevolucionLinea();
				int idxNuevaTDevLinea = 0;
				
				nuevoRegistroTDevLinea.setCodArticulo(devolucionConLineas.getCodArticulo());
				idxNuevaTDevLinea=devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).gettDevLineasLst().indexOf(nuevoRegistroTDevLinea);
				// Actualiza la propiedad "estadoCerrado" de la referencia en curso.
				devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).gettDevLineasLst().get(idxNuevaTDevLinea).setEstadoCerrado(devolucionLinea.getEstadoCerrado());

				
				if (listTDevolucionesModificadas == null || listTDevolucionesModificadas.isEmpty()){
					listTDevolucionesModificadas = this.devolucionesService.findLineasDevolucionEditadas(session.getId(),new Long("9"));
				}
				
				if (listTDevolucionesModificadas != null && !listTDevolucionesModificadas.isEmpty()){
					
					listTDevolucionesModificadas = this.rellenarEstructuraLineaBulto(listTDevolucionesModificadas, devolucionLinea.getStockDevuelto(), devolucionLinea.getBulto(), devolucionLinea.getEstadoCerrado());

					//Si hay líneas de devolución editadas, actualizamos esas líneas mediante el PLSQL, actualizamos la temporal con los códigos de error correspondientes
					//y obtenemos las líneas del grid actualizadas de la tabla temporal actualizada. Si no hay modificaciones, obtenemos la lista tal cual se encuentra
					//en la tabla temporal.
					//Se insertan las líneas de devolución modificadas únicamente en la lista de líneas de devolución de la lista.
					devolucion.setDevLineas(listTDevolucionesModificadas);
					DevolucionCatalogoEstado devolucionCatalogoEstadoActualizada = null;
					//Actualizamos la devolución y obtenemos el resultado con las líneas de devolucion y sus códigos de error
					// Actualizar SIA a través del paquete.
					devolucionCatalogoEstadoActualizada = this.devolucionesService.actualizarDevolucion(devolucion);
	
					if (devolucionCatalogoEstadoActualizada != null && ((new Long("0")).equals(devolucionCatalogoEstadoActualizada.getpCodError()) || devolucionCatalogoEstadoActualizada.getpCodError() == null)){
						if (devolucionCatalogoEstadoActualizada.getListDevolucionEstado() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().size() > 0){
							if (devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
								//Obtenemos la devolucion actualizada
								Devolucion devolucionActualizada = devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().get(0);
	
								if (new Long("0").equals(devolucionActualizada.getCodError()) || devolucionActualizada.getCodError() == null){
									//Por cada línea de la devolución si el codError es 9, significa que se ha actualizado bien, al no haber cambio en el código de error insertado en el PLSQL. En ese caso insertamos a las líneas el código de error 8 
									//para indicar que han pasado de modificadas a guardadas. En caso contrario, se inserta el código de error obtenido. Estos cambios, habrá que guardarlos en la tabla temporal para así tener actualizados los estados de
									//cada línea y dibujar bien el grid, por lo cual utilizaremos el método updateTablaSesionLineaDevolucion. 
	
									DevolucionLineaModificada devLineaModificada = null;
									List<DevolucionLineaModificada> devLineaModificadaActualizada = new ArrayList<DevolucionLineaModificada>();
									Long codError = null;
									for (DevolucionLinea devLinea:devolucionActualizada.getDevLineas()){		
										//Si coinciden en código de artículo estamos hablando de la misma línea de devolución
										if((new Long("0")).equals(devLinea.getCodError())){
											codError = new Long("8");
										}else{
											codError = devLinea.getCodError();
										}
										devLineaModificada = new DevolucionLineaModificada(devLinea.getBulto(), devLinea.getStockDevuelto(), devLinea.getStockActual(), codError, devLinea.getCodArticulo(), devLinea.getStockDevueltoBandejas());
										devLineaModificadaActualizada.add(devLineaModificada);
									}
									devolucionActualizada.setDevLineasModificadas(devLineaModificadaActualizada);
	
									//Más tarde se limpian los estados. De esta forma se evita que al actualizar dos veces se mantengan los errores y disquetes de los estados Anteriores y solo se visualicen
									//los nuevos.		
									this.devolucionesService.resetDevolEstados(session.getId());			
	
									//Actualizamos los códigos de error de las devoluciones. Además también actualizamos los datos de BULTO_ORI y STOCKDEVUELTO_ORI de la tabla temporal
									this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizada,true);									
	
								}else{
									String err = "ERR_ACT";
									model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");
	
									model.addAttribute("codErrorActualizar",err);
									resultado = "pda_p67_errorDevolucionLinea";
								}
							}else{
								String err = "ERR_ACT";
								model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");
	
								model.addAttribute("codErrorActualizar",err);
								resultado = "pda_p67_errorDevolucionLinea";
							}
						}else{
							String err = "ERR_ACT";
							model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");
	
							model.addAttribute("codErrorActualizar",err);
							resultado = "pda_p67_errorDevolucionLinea";
						}
					}else{
						String err = "ERR_ACT";
						model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");
	
						model.addAttribute("codErrorActualizar",err);
						resultado = "pda_p67_errorDevolucionLinea";
					}
				
				// PRUEBA. Creo que no es necesario ya que tengo los datos consultados en el código superior.
//				listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null, null, proveedor, flagRefPermanentes);			
//				listTDevolucionLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(), listTDevolucionLinea);
				}
			}
			
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		//Insertamos el filtro del proveedor seleccionado. Se inserta aquí, porque si se selecciona el filtro REF. PERMANENTES y no se encuentran 
		//líneas de devolución rediurigiendo a pda_p71_noExisteRefDevLin, queremos insertar como filtro del proveedor el filtro proveedor anterior. 
		model.addAttribute("proveedorFiltro", pdaDatosCabecera.getProveedor());

		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

		//Se mira si existe una línea de devolución con ese filtro.
		List<TDevolucionLinea> existeLineaDevolucionFiltroLst = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null, codArtFiltro, proveedor, flagRefPermanentes);
		if (existeLineaDevolucionFiltroLst != null && existeLineaDevolucionFiltroLst.size() > 0) {		

			if(codArtFiltro != null){
				TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
				devolucionLineaComparator.setCodArticulo(codArtFiltro);

				page =  new Long((listTDevolucionLinea.indexOf(devolucionLineaComparator)/numeroElementosPorPagina)+1);
			}

			int records = listTDevolucionLinea.size();
			int desdeSubList = ((page.intValue()-1)*numeroElementosPorPagina);
			int hastaSubList = ((page.intValue())*numeroElementosPorPagina);

			//Hay que ver desde que elemento hasta que elemento hay que hacer la sublista. Para ello utilizamos
			//el elemento desdeSubList y hastaSubList. Puede darse la situación de que hastaSubList sea mayor a los
			//elementos de la lista, por ejemplo existen 6 paginaciones y se enseñan 10 elementos por paginación,
			//en la paginación 6 el elemento desdeSubList será 50 y hastaSubList 60, pero si la sublista tiene
			//53 elementos da error! Por lo cual hay que hacer un caso en el que hastaSubList sea igual al número
			//de elementos de la lista!
			if(hastaSubList > records){
				hastaSubList = records;				
			}

			if(codArtFiltro != null){	
				TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
				devolucionLineaComparator.setCodArticulo(codArtFiltro);

				//Calcula el primer elemento donde situar el cursor al haber filtrado por referencia y poder ser una paginación de 2 en 2 o de 1 en 1.
				filaPrimerElementoAMostrar = listTDevolucionLinea.subList(desdeSubList,hastaSubList).indexOf(devolucionLineaComparator);
			}

			pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listTDevolucionLinea.subList(desdeSubList,hastaSubList),numeroElementosPorPagina, records, page.intValue());	
		}else{		
			//Si no se encuentra un resultado con el combo ref permanentes, guardamos como proveedor el proveedor anterior para que se pueda volver atrás.

			//Si no se encuentra una referencia sin haber seleccionado proveedor
			model.addAttribute("codErrP71","0");
			if(pdaDatosCabecera.getProveedor() != null){
				if((refPermanentes).equals(pdaDatosCabecera.getProveedor())){
					//Si no existe ningún artículo para REF. PERMANENTES
					proveedorAnt = request.getParameter("proveedorAnterior");
					model.addAttribute("proveedorFiltro", proveedorAnt);
					model.addAttribute("codErrP71","1");
				}else if(("").equals(pdaDatosCabecera.getProveedor())){
					model.addAttribute("codErrP71","0");
				}else{
					if(pdaDatosCabecera.getCodArtCab() != null){
						//Si no existe el articulo buscado para un proveedor
						model.addAttribute("codErrP71","2");
					}
				}
			}

			model.addAttribute("actionP71","pdaP62RealizarDevolucionFinCampania.do");
			model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
			resultado = "pda_p71_noExisteRefDevLin";
		}

		int lineasCompletadas = this.devolucionesService.findLineasDevolucionCompletadas(listTDevolucionLinea);

		///////////////////////

		model.addAttribute("pdaDatosCab", pdaDatosCabecera);

		//Obtención de combo de proveedores
		model.addAttribute("proveedores", this.loadComboProveedor(devolucion, response, session));

		List<TDevolucionLinea> tDevolucionLineaActualLst = new ArrayList<TDevolucionLinea>();
		
		//Obtención de la devolución actual
		if (pagDevolucionLineas != null && pagDevolucionLineas.getRows() != null && pagDevolucionLineas.getRows().size() > 0){
			int numeroLineasVisibles = pagDevolucionLineas.getRows().size();
			for (int i= 0;i<numeroLineasVisibles;i++){
				TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();

				tDevolucionLineaActual = pagDevolucionLineas.getRows().get(i);
				
				//Obtenemos el formato devuelto
				String formatoDevuelto = tDevolucionLineaActual.getFormatoDevuelto();
				String formatoDevueltoFormat = formatoDevuelto;
				if(formatoDevuelto.equals(Constantes.DEVOLUCIONES_FORMATO1)){
					formatoDevueltoFormat = this.messageSource.getMessage("pda_p62_canOriCerrado", null, locale);
				}else if(formatoDevuelto.equals(Constantes.DEVOLUCIONES_FORMATO2)){
					formatoDevueltoFormat = this.messageSource.getMessage("pda_p62_unidadesKgs", null, locale);
				}
				
				tDevolucionLineaActual.setFormatoDevuelto(formatoDevueltoFormat);

				//Para comparar en el jsp los códigos de error
				tDevolucionLineaActual.setCodErrorStr(tDevolucionLineaActual.getCodError().toString());
				tDevolucionLineaActualLst.add(tDevolucionLineaActual);
			}
		}else{
			TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();
			tDevolucionLineaActual.setDevolucion(new Long(devolucionId));
			tDevolucionLineaActualLst.add(tDevolucionLineaActual);
		}

		//Si existe el permiso de las fotos, mirar si la devolución a mostrar tiene una foto asignada, como la paginación será de 1 en 1, sabemos que la lista
		//tDevolucionLineaActualLst tendrá solo un elemento, por lo que tenemos que buscar la foto de ese elemento.
		if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones())
				&& !user.getCentro().getOpcHabil().isEmpty()
				&& user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_FOTO_27) != -1) {
			//Comprobar si tiene Foto
			FotosReferencia fotosReferencia = new FotosReferencia();

			if (tDevolucionLineaActualLst.size() > 0){
				//Como la lista a mostrar tiene solo un artículo, obtenemos el valor del elemento 0.
				fotosReferencia.setCodReferencia(tDevolucionLineaActualLst.get(0).getCodArticulo());

				//Miramos si existe la foto y en caso afirmativo, buscamos su path.
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					model.addAttribute("tieneFoto",true);
				}else{
					model.addAttribute("tieneFoto",false);
				}
			}else{
				model.addAttribute("tieneFoto",false);
			}

		}else{
			model.addAttribute("tieneFoto",false);
		}
		
		Devolucion dev = new Devolucion();
		dev.settDevLineasLst(tDevolucionLineaActualLst);
		dev.setBultoPorProveedorLst(devolucionConLineas.getBultoPorProveedorLst());
		
		Double sumaEuros = getCalcularSumaEuros(listTDevolucionLinea);
		Map<String, Boolean> linkTextil = this.mostrarLinkTextil(listTDevolucionLinea, page); 
		
		model.addAttribute("pagDevolucionLineas", pagDevolucionLineas);
		model.addAttribute("devolucionCabecera", devolucion);

		model.addAttribute("devolucionLinea", devolucion);

		model.addAttribute("referenciaFiltro", pdaDatosCabecera.getCodArtCab());
		session.setAttribute("paginaActual", paginaAntesDePaginar.intValue());
		model.addAttribute("linkTextil", linkTextil);
		model.addAttribute("sumaEuros", sumaEuros);
		model.addAttribute("completados", lineasCompletadas);

		model.addAttribute("devolucionConLineas", dev);
		model.addAttribute("primerElementoBultoFila", filaPrimerElementoAMostrar);

		//Se guarda el proveedor nada más devolver las líneas, para que a la hora de mirar si se han editado líneas, obtenga las líneas con el proveedor original y no el cambiado.
		//Y de este modo la lista antes de cambiar de proveedor coincida con la editada.
		model.addAttribute("proveedorAnterior", pdaDatosCabecera.getProveedor());

		//Guarda el valor de la acción previa al devolver el jsp.
		model.addAttribute("accionAnterior",accion);

		String referenciaFiltroRellenaYCantidadEnter = (request.getParameter("referenciaFiltroRellenaYCantidadEnter") != null ? request.getParameter("referenciaFiltroRellenaYCantidadEnter") : "");
		model.addAttribute("referenciaFiltroRellenaYCantidadEnter", referenciaFiltroRellenaYCantidadEnter);		

		if (origenPantalla != null && origenPantalla != ""){
			model.addAttribute("origenPantalla", origenPantalla);
			model.addAttribute("selectProv", selectProv);
		}
		
		String listaBultos="";
		User usuario= (User)session.getAttribute("user");
		boolean centroParametrizado = this.devolucionLineaBultoCantidadService.isCentroParametrizado(usuario.getCentro().getCodCentro(), Constantes.DEVOLUCIONES_PDA_PERMISO_27);
		String devolucionFinalizada = "";
		if (centroParametrizado && tDevolucionLineaActualLst.size()>0){
			listaBultos=this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(), tDevolucionLineaActualLst.get(0));
			devolucionFinalizada = this.devolucionesService.esProveedorSinFinalizar(session.getId(), tDevolucionLineaActualLst.get(0).getDevolucion(), null, Constantes.PDA);
		}
		model.addAttribute("listaBultos", listaBultos);
		model.addAttribute("centroParametrizado", centroParametrizado);
		model.addAttribute("devolucionFinalizada", devolucionFinalizada);
		
		devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).settDevLineasLst(listTDevolucionLinea);
		session.setAttribute("devCatalogoEstado", devol);
		
		return resultado;
	}

	//Función que vale para volver de corrección de stocks a la línea de devolución
	@RequestMapping(value = "/pdaP62StockLinkVuelta",method = RequestMethod.GET)
	public String pdaP62StockLinkVuelta(
			@Valid final Long codArt,
			@RequestParam(value = "flgBienGuardado", required = false, defaultValue = "N") String flgBienGuardado,
			@RequestParam(value = "flgOrigenBandejasKgs", required = false, defaultValue = "N") String flgOrigenBandejasKgs,
			@RequestParam(value = "origenPantalla", required = false, defaultValue = " ") String origenPantalla,
			@RequestParam(value = "selectProv", required = false, defaultValue = " ") String selectProv,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		if (selectProv == null || "".equals(selectProv) || " ".equals(selectProv)){
			origenPantalla = request.getParameter("origenPantalla");
			selectProv = request.getParameter("selProv");
		}

		//Redireccionaremos a la línea de devolución que nos encontrabamos
		String resultado = "pda_p62_realizarDevolucionFinDeCampania";
		User user = (User) session.getAttribute("user");

		DevolucionCatalogoEstado devolTotal = (DevolucionCatalogoEstado) session.getAttribute("devCatalogoEstado");
		Devolucion devolTot = devolTotal.getListDevolucionEstado().get(0).getListDevolucion().get(0);
		Devolucion devol = (Devolucion)session.getAttribute("devolucion");
		String devolucionId = devol.getDevolucion().toString(); //Obtención de la devolución actual

		TDevolucionLinea nuevoRegistroTDevLinea = new TDevolucionLinea();
		int idxNuevaTDevLinea = 0;

		DevolucionLinea nuevoRegistroDevLinea = new DevolucionLinea();
		int idxNuevaDevLinea = 0;
		
		//Actualizamos el stock
		ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
		stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
		stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
		List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
		listaReferencias.add(BigInteger.valueOf(codArt));

		ConsultarStockResponseType basicoStockTiendaResponse = null;
		ConsultarStockResponseType stockTiendaResponse = null;
		String estructuraComercial = session.getAttribute("estructuraComercial").toString();

		// DESCOMENTARLO UNA VEZ ESTE HECHO EL NUEVO SERVICIO DE TEXTIL. CORRESPONDE A LA PETICION 47885 (LA PARTE DE PISTOLA)
		// SE ACTIVA SOLO PARA LOS CENTROS: 5042-SEGOVIA, 0358-ABADIÑO, 0175-IRUÑA, 5326-INFANTE, 410 EIBAR, 164 GERNIKA
		//Si la referencia consultada es TEXTIL, se deben obtener todas las referencia que compartan el mismo código de proveedor.
		if (estructuraComercial.startsWith("03")) {  //Es un artículo de textil

			stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA); //En este caso es una consulta basica, al 
			//WS se le pasara una lista de referencias
			List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(codArt);

			for (Long articulo : referenciasMismoModeloProveedor) {
				String strArticulo = articulo + "";
				if (!strArticulo.equals(codArt + "")) { //El articulo consultado por el usuario  (el de la cabecera) ya esta metido en la lista
					listaReferencias.add(BigInteger.valueOf(articulo));
				}
			}
		}

		stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
		try{
			
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
			
			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: pdaP46GuardarInvLibController (pdaP62StockLinkVuelta  1)	 ####");
				logger.error("###########################################################################################################");
			} 
			
			stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest, session);
			if (stockTiendaResponse.getCodigoRespuesta().equals("OK")){
				//Comprobamos si la referencia es una referencia madre
				if (stockTiendaResponse != null && stockTiendaResponse.getTipoMensaje() != null && 
						stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
					//En este caso hacemos una consulta basica para obtener el stock
					stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA);
			
					if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
						logger.error("###########################################################################################################");
						logger.error("############################## CONTROLADOR: pdaP46GuardarInvLibController (pdaP62StockLinkVuelta  2)	 ####");
						logger.error("###########################################################################################################");
					} 
					basicoStockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest, session);
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

		//Obtención de datos del filtro. Se obtiene el nuevo proveedor seleccionado y la nueva referencia introducida en el filtro
		PdaDatosCabecera pdaDatosCabecera = new PdaDatosCabecera();
		pdaDatosCabecera.setCodArtCab((session.getAttribute("referenciaFiltro") != null ? session.getAttribute("referenciaFiltro").toString() : ""));
		pdaDatosCabecera.setProveedor((session.getAttribute("proveedorFiltro") != null ? session.getAttribute("proveedorFiltro").toString() : ""));

		//Obtención del código articulo de filtrado
		Long codArtFiltro = null;
		//Se mantiene el criterio de cabecera entre las diferentes pantallas de las devoluciones
		if (pdaDatosCabecera != null && pdaDatosCabecera.getCodArtCab() != null && !"".equals(pdaDatosCabecera.getCodArtCab().trim())){
			try{
				//Obtención de la referencia a filtrar
				PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCabecera.getCodArtCab());
				String codigoError = pdaArticulo.getCodigoError();
				
				if (codigoError == null || codigoError.equals("0")){
					codArtFiltro = new Long(pdaArticulo.getCodArt());
					
					//Buscar la referencia madre
					if(codArtFiltro != null){
						//Obtenemos las referencias asociadas a una devolución
						//Y obtenemos la referencia madre
						RefAsociadas referenciasAsociadas = new RefAsociadas();
						referenciasAsociadas.setCodArticuloHijo(codArtFiltro);
						List<RefAsociadas> refAsociadasLst = refAsociadasServiceImpl.findAll(referenciasAsociadas);

						if(refAsociadasLst != null && refAsociadasLst.size() > 0){
							codArtFiltro = refAsociadasLst.get(0).getCodArticulo();
						}
					}
				}
			}catch(Exception e){ //Si hay error en carga de referencia se devuelve la lista vacía
			}
		}

		//Si tiene permiso para que se vea la imagen del artículo la paginación se hará de uno en uno. Si no, la paginación será de dos en dos
		int numeroElementosPorPagina = Constantes.DEVOLUCIONES_PDA_PAGS_FIN_CAMPANA;
		if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones())
				&& !user.getCentro().getOpcHabil().isEmpty()
				&& user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_FOTO_27) != -1) {
			numeroElementosPorPagina = Constantes.DEVOLUCIONES_PDA_PAGS_FIN_CAMPANA_CON_IMAGEN;
		}

		//Paginación de devolución linea
		Page<TDevolucionLinea> pagDevolucionLineas = null;	 

		int filaPrimerElementoAMostrar = 0;
		Long page = new Long(1);
		if(session.getAttribute("paginaActual") != "" && session.getAttribute("paginaActual") != null){
			page = new Long(session.getAttribute("paginaActual").toString()); //Recuperamos la página actual de pantalla.
		}

		List<TDevolucionLinea> listTDevolucionLinea = null;
		Devolucion devolucion = new Devolucion();
		devolucion.setDevolucion(new Long(devolucionId));

		//Obtención de la devolución de la lista de devoluciones
		@SuppressWarnings("unchecked")
		List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
		devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
		devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);

		//Obtener la opción del combobox de proveedores seleccionada recientemente
		Locale locale = LocaleContextHolder.getLocale();
		String refPermanentes = this.messageSource.getMessage("pda_p62_refPermanentes", null,locale);

		//Si la opcion es ref. permanentes, buscar las líneas con FLG_CONTINUIDAD en 'S'
		String flagRefPermanentes = null;
		String proveedor = null;
		if((refPermanentes).equals(pdaDatosCabecera.getProveedor())){
			flagRefPermanentes = "S";
		}else{
			proveedor = pdaDatosCabecera.getProveedor();
		}

		if (origenPantalla != null
			&& (origenPantalla.toString().equals("pdaP104") || origenPantalla.toString().equals("pdaP105"))){
			if (selectProv != null && !selectProv.toString().equals("") && !selectProv.toString().equals(" ")){
				proveedor = selectProv.substring(0, selectProv.indexOf("-"));
			}
		}

		//Obtenemos la lista de devoluciones con el proveedor nuevo seleccionado.
		listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion, null,null, proveedor,flagRefPermanentes);	
		listTDevolucionLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLinea);
		//Buscamos la devolución a mostrar
		if(codArtFiltro != null){
			TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
			devolucionLineaComparator.setCodArticulo(codArtFiltro);

			page =  new Long((listTDevolucionLinea.indexOf(devolucionLineaComparator)/numeroElementosPorPagina)+1);
		}

		int records = listTDevolucionLinea.size();
		int desdeSubList = ((page.intValue()-1)*numeroElementosPorPagina);
		int hastaSubList = ((page.intValue())*numeroElementosPorPagina);

		//Hay que ver desde que elemento hasta que elemento hay que hacer la sublista. Para ello utilizamos
		//el elemento desdeSubList y hastaSubList. Puede darse la situación de que hastaSubList sea mayor a los
		//elementos de la lista, por ejemplo existen 6 paginaciones y se enseñan 10 elementos por paginación,
		//en la paginación 6 el elemento desdeSubList será 50 y hastaSubList 60, pero si la sublista tiene
		//53 elementos da error! Por lo cual hay que hacer un caso en el que hastaSubList sea igual al número
		//de elementos de la lista!
		if(hastaSubList > records){
			hastaSubList = records;				
		}

		if(codArtFiltro != null){	
			TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
			devolucionLineaComparator.setCodArticulo(codArtFiltro);

			//Calcula el primer elemento donde situar el cursor al haber filtrado por referencia y poder ser una paginación de 2 en 2 o de 1 en 1.
			filaPrimerElementoAMostrar = listTDevolucionLinea.subList(desdeSubList,hastaSubList).indexOf(devolucionLineaComparator);
		}

		//Obtenemos la lista de devolucion
		List<TDevolucionLinea> listaAMostrar = new ArrayList<TDevolucionLinea>(listTDevolucionLinea.subList(desdeSubList,hastaSubList));

		//Obtenemos la devolución de la lista en la que hemos pinchado en el link stock
		TDevolucionLinea devolABuscar = new TDevolucionLinea();
		devolABuscar.setCodArticulo(codArt);

		int indice = listaAMostrar.indexOf(devolABuscar);
		
		TDevolucionLinea devolucionStk = new TDevolucionLinea();
		if (indice != -1){
			devolucionStk = listaAMostrar.get(indice);
		}

		Double stockGuardado = null;
		//Comparamos el nuevo stock Guardado con el que estaba.
		if (basicoStockTiendaResponse != null){
			if (basicoStockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
				stockGuardado = basicoStockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue();
			} else {
				stockGuardado = basicoStockTiendaResponse.getListaReferencias()[0].getStock().doubleValue();
			}
		}else{
			if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
				stockGuardado = stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue();
			} else {
				stockGuardado = stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue();				
			}
		}

		//Creamos una lista para insertar la línea de devolución modificada
		List<DevolucionLineaModificada> devLineaLstModificada = new ArrayList<DevolucionLineaModificada>();
		//Creamos un objeto que guarde los cambios de las líneas de devolución
		DevolucionLineaModificada devolucionLineaActualizar = new DevolucionLineaModificada();
		//Creamos dos nuevos objetos, para actualizar la devolución linea
		Devolucion devolucionActualizar = new Devolucion();
		
		//Primero se mira si se ha modificado el stock
		if (devolucionStk != null && devolucionStk.getStockActual() != null && !devolucionStk.getStockActual().equals(stockGuardado)){ 

			//Insertamos en la línea el artículo al que corresponde esa línea, el estado modificado como código 9 y el nuevo stockActual
			devolucionLineaActualizar.setCodError(new Long(9));
			devolucionLineaActualizar.setCodArticulo(codArt);	
			devolucionLineaActualizar.setStockActual(stockGuardado);
			devolucionLineaActualizar.setStockDevuelto(devolucionStk.getStockDevuelto());
			devolucionLineaActualizar.setBulto(devolucionStk.getBulto());

			if (basicoStockTiendaResponse != null){
				if (basicoStockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
					devolucionStk.setStockActual(stockGuardado);
				} else {
					devolucionStk.setStockActual(stockGuardado);
				}
			}
			else{
				if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
					devolucionStk.setStockActual(stockGuardado);
				} else {
					devolucionStk.setStockActual(stockGuardado);				
				}
			}
			
			//Insertamos la línea de devolución modificada
			devLineaLstModificada.add(devolucionLineaActualizar);

			devolucionActualizar.setDevolucion(devolucion.getDevolucion());	
			devolucionActualizar.setDevLineasModificadas(devLineaLstModificada);

			this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizar,false);
		}

		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();
		pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listaAMostrar,numeroElementosPorPagina, records, page.intValue());	


		int lineasCompletadas = this.devolucionesService.findLineasDevolucionCompletadas(listTDevolucionLinea);

		///////////////////////

		model.addAttribute("pdaDatosCab", pdaDatosCabecera);

		//Obtención de combo de proveedores
		model.addAttribute("proveedores", this.loadComboProveedor(devolucion, response, session));

		List<TDevolucionLinea> tDevolucionLineaActualLst = new ArrayList<TDevolucionLinea>();
		//Obtención de la devolución actual
		if(pagDevolucionLineas != null && pagDevolucionLineas.getRows() != null && pagDevolucionLineas.getRows().size() > 0){
			int numeroLineasVisibles = pagDevolucionLineas.getRows().size();
			for(int i= 0;i<numeroLineasVisibles;i++){
				TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();

				tDevolucionLineaActual = pagDevolucionLineas.getRows().get(i);

				//Obtenemos el formato devuelto
				String formatoDevuelto = tDevolucionLineaActual.getFormatoDevuelto();
				String formatoDevueltoFormat = formatoDevuelto;
				if(formatoDevuelto.equals(Constantes.DEVOLUCIONES_FORMATO1)){
					formatoDevueltoFormat = this.messageSource.getMessage("pda_p62_canOriCerrado", null, locale);
				}else if(formatoDevuelto.equals(Constantes.DEVOLUCIONES_FORMATO2)){
					formatoDevueltoFormat = this.messageSource.getMessage("pda_p62_unidadesKgs", null, locale);
				}
				
				tDevolucionLineaActual.setFormatoDevuelto(formatoDevueltoFormat);
				
				//Despues miramos si venimos del popUp de actualización Bandejas/kgs
				if (flgOrigenBandejasKgs.equals("S")) {
					
					PdaArticulo pdaArticulo = new PdaArticulo();
					pdaArticulo = (PdaArticulo) session.getAttribute("pdaArticulo"); 

					Double kgsDouble = Double.parseDouble(pdaArticulo.getKgs().replace(',', '.'));
					Long bandekasDouble = Long.parseLong(pdaArticulo.getBandejas().replace(',', '.'));
					
					tDevolucionLineaActual.setStockDevuelto(kgsDouble);//kgs
					tDevolucionLineaActual.setStockDevueltoBandejas(bandekasDouble);//Bandejas	
					tDevolucionLineaActual.setVariosBultos(pdaArticulo.getVariosBultos());
				}

				//Para comparar en el jsp los códigos de error
				tDevolucionLineaActual.setCodErrorStr(tDevolucionLineaActual.getCodError().toString());
				tDevolucionLineaActualLst.add(tDevolucionLineaActual);
			}
		}

		//Si existe el permiso de las fotos, mirar si la devolución a mostrar tiene una foto asignada, como la paginación será de 1 en 1, sabemos que la lista
		//tDevolucionLineaActualLst tendrá solo un elemento, por lo que tenemos que buscar la foto de ese elemento.
		if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones())
				&& !user.getCentro().getOpcHabil().isEmpty()
				&& user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_FOTO_27) != -1) {
			//Comprobar si tiene Foto
			FotosReferencia fotosReferencia = new FotosReferencia();

			if(tDevolucionLineaActualLst.size() > 0){
				//Como la lista a mostrar tiene solo un artículo, obtenemos el valor del elemento 0.
				fotosReferencia.setCodReferencia(tDevolucionLineaActualLst.get(0).getCodArticulo());

				//Miramos si existe la foto y en caso afirmativo, buscamos su path.
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					model.addAttribute("tieneFoto",true);
				}else{
					model.addAttribute("tieneFoto",false);
				}
			}else{
				model.addAttribute("tieneFoto",false);
			}

		}else{
			model.addAttribute("tieneFoto",false);
		}

		Devolucion dev = new Devolucion();
		dev.settDevLineasLst(tDevolucionLineaActualLst);
		dev.setBultoPorProveedorLst(devol.getBultoPorProveedorLst());

		// Se actualiza el valor de stock en la referencia en curso.
		nuevoRegistroTDevLinea.setCodArticulo(codArt);
		idxNuevaTDevLinea=devolTot.gettDevLineasLst().indexOf(nuevoRegistroTDevLinea);
		
		devolTot.gettDevLineasLst().set(idxNuevaTDevLinea, tDevolucionLineaActualLst.get(0));
		
		nuevoRegistroDevLinea.setCodArticulo(codArt);
		// Actualizar los datos de la lista DevLinea
		idxNuevaDevLinea=devolTot.getDevLineas().indexOf(nuevoRegistroDevLinea);
		devolTot.getDevLineas().get(idxNuevaDevLinea).setStockActual(tDevolucionLineaActualLst.get(0).getStockActual());
		
		// Actualizar los datos de la lista DevLinea y TDevLinea
		devolTotal.getListDevolucionEstado().get(0).getListDevolucion().set(0, devolTot);

		session.setAttribute("devCatalogoEstado", devolTotal);

		Double sumaEuros = getCalcularSumaEuros(listTDevolucionLinea);
		Map<String, Boolean> linkTextil = this.mostrarLinkTextil(listTDevolucionLinea, page); 
		
		model.addAttribute("pagDevolucionLineas", pagDevolucionLineas);
		model.addAttribute("devolucionCabecera", devolucion);

		model.addAttribute("devolucionLinea", devolucion);

		model.addAttribute("referenciaFiltro", pdaDatosCabecera.getCodArtCab());
		model.addAttribute("proveedorFiltro", pdaDatosCabecera.getProveedor());
		model.addAttribute("linkTextil", linkTextil);
		model.addAttribute("sumaEuros", sumaEuros);
		model.addAttribute("completados", lineasCompletadas);

		model.addAttribute("devolucionConLineas", dev);
		model.addAttribute("primerElementoBultoFila", filaPrimerElementoAMostrar);

		//Se guarda el proveedor nada más devolver las líneas, para que a la hora de mirar si se han editado líneas, obtenga las líneas con el proveedor original y no el cambiado.
		//Y de este modo la lista antes de cambiar de proveedor coincida con la editada.
		model.addAttribute("proveedorAnterior", pdaDatosCabecera.getProveedor());

		String referenciaFiltroRellenaYCantidadEnter = (request.getParameter("referenciaFiltroRellenaYCantidadEnter") != null ? request.getParameter("referenciaFiltroRellenaYCantidadEnter") : "");
		model.addAttribute("referenciaFiltroRellenaYCantidadEnter", referenciaFiltroRellenaYCantidadEnter);		

		//Ponemos si el link se ha guardado bien o no.
		model.addAttribute("flgBienGuardado", flgBienGuardado);

		//Se guarda la fila que se ha clicado para luego saber colorear o no el link de stocks
		model.addAttribute("fila", session.getAttribute("fila").toString());
		
		model.addAttribute("origenPantalla", origenPantalla);
		model.addAttribute("selectProv", selectProv);
		
		String listaBultos="";
		User usuario= (User)session.getAttribute("user");
		boolean centroParametrizado = this.devolucionLineaBultoCantidadService.isCentroParametrizado(usuario.getCentro().getCodCentro(), Constantes.DEVOLUCIONES_PDA_PERMISO_27);
		String devolucionFinalizada = "";
		if(centroParametrizado){
			listaBultos=this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(), tDevolucionLineaActualLst.get(0));
			devolucionFinalizada = this.devolucionesService.esProveedorSinFinalizar(session.getId(), tDevolucionLineaActualLst.get(0).getDevolucion(), null, Constantes.PDA);
		}
		model.addAttribute("listaBultos", listaBultos);
		model.addAttribute("centroParametrizado", centroParametrizado);
		model.addAttribute("devolucionFinalizada", devolucionFinalizada);
		return resultado;
	}

	//Método que sirve para guardar cada línea de devolución en un registro de una tabla temporal llamada T_DEVOLUCIONES
	//Esa tabla además de contener como columnas los atributos del objeto DevolucionLinea, contiene el id de la sesión
	//del usuario, la fecha de creación y un código de devolución. Con la columna del id de sesión es posible que cada
	//usuario tenga sus datos en la tabla temporal y así no ver los de los demás, además se utilizará para borrar los
	//datos de la tabla temporal de su sesión cuando sea necesario. El campo de la fecha de creación sirve para eliminar los
	//registros que lleven guardados X tiempo y sean considerados como basura en la tabla temporal. El campo de devolución
	//se utilizará para saber cada línea de devolución a que devolución pertenece.
	private RdoListasDev insertarTablaSesionLineaDevolucion(List<DevolucionLinea> listDevolucionLinea, String idSesion, Long devolucion){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TDevolucionLinea> listTDevolucionLinea = new ArrayList<TDevolucionLinea>();

		//Definimos el puntero del registro a guardar en la bd
		TDevolucionLinea nuevoRegistro = null;

		for (DevolucionLinea devolLinea:listDevolucionLinea){
			
			List<TDevolucionBulto> listTDevolucionLineaBultoCantidad = new ArrayList<TDevolucionBulto>();
			//Se inicializa el objeto. Cada objeto de este tipo será un registro de la tabla temporal
			TDevolucionBulto nuevoRegistroBultoCantidad = null;
			for (BultoCantidad bultoCantidad:devolLinea.getListaBultoCantidad()){
				nuevoRegistroBultoCantidad = new TDevolucionBulto();
				nuevoRegistroBultoCantidad.setIdSesion(idSesion);
				nuevoRegistroBultoCantidad.setFechaGen(new Date());
				nuevoRegistroBultoCantidad.setDevolucion(devolucion);
				nuevoRegistroBultoCantidad.setBulto(bultoCantidad.getBulto());
				nuevoRegistroBultoCantidad.setCodArticulo(devolLinea.getCodArticulo());
				nuevoRegistroBultoCantidad.setBultoOri(bultoCantidad.getBulto());
				nuevoRegistroBultoCantidad.setStock(bultoCantidad.getStock());
				nuevoRegistroBultoCantidad.setStockOri(bultoCantidad.getStock());
				nuevoRegistroBultoCantidad.setEstadoCerrado(bultoCantidad.getEstadoCerrado());
				nuevoRegistroBultoCantidad.setCodError(bultoCantidad.getCodError());
				nuevoRegistroBultoCantidad.setDescError(bultoCantidad.getDescError());
				nuevoRegistroBultoCantidad.setCreationDate(new Date());
				listTDevolucionLineaBultoCantidad.add(nuevoRegistroBultoCantidad);
			}
			try {
				this.devolucionLineaBultoCantidadService.insertAll(listTDevolucionLineaBultoCantidad);
			} catch (Exception e) {
				logger.error("Insertar tabla Lista Bulto Cantidad= "+e.toString());
				e.printStackTrace();
			}
			
			//Se inicializa el objeto. Cada objeto de este tipo será un registro de la tabla temporal
			nuevoRegistro = new TDevolucionLinea();

			//Se rellena el objeto
			nuevoRegistro.setIdSesion(idSesion);
			nuevoRegistro.setCreationDate(new Date());
			nuevoRegistro.setDevolucion(devolucion);
			nuevoRegistro.setCodArticulo(devolLinea.getCodArticulo());
			nuevoRegistro.setDenominacion((devolLinea.getDenominacion() != null && !("".equals(devolLinea.getDenominacion())))?devolLinea.getDenominacion().toString():null);			
			nuevoRegistro.setMarca((devolLinea.getMarca() != null && !("".equals(devolLinea.getMarca())))?devolLinea.getMarca():null);
			nuevoRegistro.setSeccion((devolLinea.getSeccion() != null && !("".equals(devolLinea.getSeccion())))?devolLinea.getSeccion():null);
			nuevoRegistro.setProvrGen(devolLinea.getProvrGen());
			nuevoRegistro.setProvrTrabajo(devolLinea.getProvrTrabajo());
			nuevoRegistro.setDenomProveedor((devolLinea.getDenomProveedor() != null && !("".equals(devolLinea.getDenomProveedor())))?devolLinea.getDenomProveedor():null);			
			nuevoRegistro.setFamilia((devolLinea.getFamilia() != null && !("".equals(devolLinea.getFamilia())))?devolLinea.getFamilia():null);
			nuevoRegistro.setFormatoDevuelto((devolLinea.getFormatoDevuelto() != null && !("".equals(devolLinea.getFormatoDevuelto())))?devolLinea.getFormatoDevuelto():null);
			nuevoRegistro.setFormato(devolLinea.getFormato());
			nuevoRegistro.setTipoFormato((devolLinea.getTipoFormato() != null && !("".equals(devolLinea.getTipoFormato())))?devolLinea.getTipoFormato():null);
			nuevoRegistro.setPasillo((devolLinea.getPasillo() != null && !("".equals(devolLinea.getPasillo())))?devolLinea.getPasillo():null);
			nuevoRegistro.setEstructuraComercial((devolLinea.getEstructuraComercial() != null && !("".equals(devolLinea.getEstructuraComercial())))?devolLinea.getEstructuraComercial():null);
			nuevoRegistro.setUc(devolLinea.getUc());
			nuevoRegistro.setStockActual(devolLinea.getStockActual());
			nuevoRegistro.setStockTienda(devolLinea.getStockTienda());
			nuevoRegistro.setStockDevolver(devolLinea.getStockDevolver());
			nuevoRegistro.setStockDevuelto(devolLinea.getStockDevuelto());
			nuevoRegistro.setStockDevueltoOrig(devolLinea.getStockDevuelto());
			nuevoRegistro.setCantAbonada(devolLinea.getCantAbonada());
			nuevoRegistro.setFlgContinuidad((devolLinea.getFlgContinuidad() != null && !("".equals(devolLinea.getFlgContinuidad())))?devolLinea.getFlgContinuidad():null);
			nuevoRegistro.setLote((devolLinea.getLote() != null && !("".equals(devolLinea.getLote())))?devolLinea.getLote():null);
			nuevoRegistro.setnLote((devolLinea.getnLote() != null && !("".equals(devolLinea.getnLote())))?devolLinea.getnLote():null);
			nuevoRegistro.setCaducidad((devolLinea.getCaducidad() != null && !("".equals(devolLinea.getCaducidad())))?devolLinea.getCaducidad():null);
			nuevoRegistro.setnCaducidad((devolLinea.getnCaducidad() != null && !("".equals(devolLinea.getnCaducidad())))?devolLinea.getnCaducidad():null);
			nuevoRegistro.setDescAbonoError((devolLinea.getDescAbonoError() != null && !("".equals(devolLinea.getDescAbonoError())))?devolLinea.getDescAbonoError():null);
			nuevoRegistro.setBulto(devolLinea.getBulto());
			nuevoRegistro.setBultoOrig(devolLinea.getBulto());
			nuevoRegistro.setUbicacion((devolLinea.getUbicacion() != null && !("".equals(devolLinea.getUbicacion())))?devolLinea.getUbicacion():null);
			nuevoRegistro.setTipoReferencia((devolLinea.getTipoReferencia() != null && !("".equals(devolLinea.getTipoReferencia())))?devolLinea.getTipoReferencia():null);
			nuevoRegistro.setEstadoLin(devolLinea.getEstadoLin());
			nuevoRegistro.setCodError(devolLinea.getCodError());
			nuevoRegistro.setDescError((devolLinea.getDescError() != null && !("".equals(devolLinea.getDescError())))?devolLinea.getDescError():null);
			nuevoRegistro.setFlgBandejas((devolLinea.getFlgBandejas() != null && !("".equals(devolLinea.getFlgBandejas())))?devolLinea.getFlgBandejas():null);
			nuevoRegistro.setFlgPesoVariable((devolLinea.getFlgPesoVariable() != null && !("".equals(devolLinea.getFlgPesoVariable())))?devolLinea.getFlgPesoVariable():null); // MISUMI-259
			nuevoRegistro.setStockDevueltoBandejas(devolLinea.getStockDevueltoBandejas());
			nuevoRegistro.setStockDevueltoBandejasOrig(devolLinea.getStockDevueltoBandejas());
			nuevoRegistro.setDescrTalla(devolLinea.getDescrTalla());
			nuevoRegistro.setDescrColor(devolLinea.getDescrColor());
			nuevoRegistro.setModelo(devolLinea.getModelo());
			nuevoRegistro.setModeloProveedor(devolLinea.getModeloProveedor());
			nuevoRegistro.setCosteUnitario(devolLinea.getCosteUnitario());
			nuevoRegistro.setArea(devolLinea.getArea());
			nuevoRegistro.setCantidadMaximaLin(devolLinea.getCantidadMaximaLin());
			
			//El objeto se inserta en una lista
			listTDevolucionLinea.add(nuevoRegistro);
		}

		try {
			this.devolucionesService.insertAll(listTDevolucionLinea);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}
		
		return new RdoListasDev(listTDevolucionLinea, new ArrayList<TDevolucionBulto>());
	}

	//Elimina la tabla temporal histórica de todos los usuarios si ha pasado más de un día desde
	//la creación de los registros
	private void eliminarTablaSesionHistorico(HttpSession session){		
		try {
			// Sólo se borrarán la primera vez que se entre a las referencias.
			if ((boolean)session.getAttribute("existenDevoluciones").equals("false")){
				this.devolucionesService.deleteHistorico();
			}
			this.devolucionLineaBultoCantidadService.deleteHistorico();
		} catch (Exception e) {
			logger.error("eliminarTablaSesionHistorico="+e.toString());
			e.printStackTrace();
		}
	}

	//Elimina los datos de sesión de la tabla temporal de ese usuario 
	private void eliminarTablaSesion(HttpSession session){		
		TDevolucionLinea registro = new TDevolucionLinea();
		registro.setIdSesion(session.getId());

		try {
			// Sólo se borrarán la primera vez que se entre a las referencias.
			if ((boolean)session.getAttribute("existenDevoluciones").equals("false")){
				this.devolucionesService.delete(registro);
			}
			this.devolucionLineaBultoCantidadService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

	//Sirve para cargar los valores del combobox proveedor ordenados
	private LinkedHashMap<String, String> loadComboProveedor(
			Devolucion devolucion,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		List<OptionSelectBean> list = null;
		
		try {
			//Obtener el idioma
			Locale locale = LocaleContextHolder.getLocale();

			//Inicializamos la lista.
			list = new ArrayList<OptionSelectBean>();

			//Obtener la opción del combobox
			String refPermanentes = this.messageSource.getMessage("pda_p62_refPermanentes", null,locale);

			OptionSelectBean opt = new OptionSelectBean(refPermanentes,refPermanentes);

			list.add(opt);
			list.addAll(this.devolucionesService.obtenerProveedoresLineasDevolucion(session.getId(),devolucion));
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		//Tratamiento para pasar la lista de proveedores a pantalla
		LinkedHashMap<String, String> proveedores = new LinkedHashMap<String, String>();
		//Opción para mostrado de todos los proveedores
		proveedores.put("", "");
		if (list != null && list.size() > 0){
			for (OptionSelectBean proveedor : list) {
				proveedores.put(proveedor.getCodigo(), proveedor.getDescripcion());
			}
		}
		return proveedores;
	}

	/**
	 * Sumatorio del producto del coste unitario por el stock a devolver.
	 * El resultante debiera ser el total en euros de la devolucion.
	 * @param dev
	 * @return Cero o el total en euros de la devolucion.
	 */
	private Double getCalcularSumaEuros(List<TDevolucionLinea> tDevolucionList) {
		Double costeDevolucionSuma = new Double(0L);
		
		if(tDevolucionList!=null){
			for(TDevolucionLinea it : tDevolucionList){
				Double costeUnitarioDbl = it.getCosteUnitario();
				Double stockDevueltoDbl = it.getStockDevuelto();
					
				if(costeUnitarioDbl!=null && stockDevueltoDbl!=null){
					Double multi = costeUnitarioDbl * stockDevueltoDbl;
					costeDevolucionSuma = costeDevolucionSuma + multi;
				}
			}
		}
		
		Double suma = (double)Math.round(costeDevolucionSuma * 10d) / 10d; //Limitamos los decimales a 1
		return suma;
		
	}

	/** Dice que hay que mostrar el link si se prevee que no se visualice bien en pantalla los datos de textil */
	private Map<String, Boolean> mostrarLinkTextil(List<TDevolucionLinea> tDevolucionList, Number page){
		TDevolucionLinea dev = null;
		if(tDevolucionList!=null && page!=null && tDevolucionList.size() >= page.intValue()){
			dev = tDevolucionList.get(page.intValue() -1);
		}
		return mostrarLinkTextil(dev);
	}
	/** Dice que hay que mostrar el link si se prevee que no se visualice bien en pantalla los datos de textil */
	private Map<String, Boolean> mostrarLinkTextil(TDevolucionLinea dev){
		boolean esTextil = dev!=null && dev.getArea()!=null && dev.getArea().longValue() == 3L;
		String color = esTextil ? dev.getDescrColor() : "";
		String talla = esTextil ? dev.getDescrTalla() : "";
		String mp = esTextil ? dev.getModeloProveedor() : "";
		// número de caracteres que entran en la interface de usuario
		boolean colorLargo = color.length() > 3;
		boolean tallaLarga = talla.length() > 3;
		boolean mpLargo = mp.length() > 9;
		
		Map<String, Boolean> retorno = new HashMap<String, Boolean>();
		retorno.put("talla", tallaLarga);
		retorno.put("color", colorLargo);
		retorno.put("mp", mpLargo);
		return retorno;
	}
	
	@RequestMapping(value = "/pdaP62VariosBultosLinkVuelta",method = {RequestMethod.POST, RequestMethod.GET})
	public String pdaP62VariosBultosLinkVuelta(
			@Valid final Long codArt,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		String resultado = "pda_p62_realizarDevolucionFinDeCampania";
		User user = (User) session.getAttribute("user");
		Long page = new Long(1);
		Long max = new Long(Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA);
		//Paginación de devolución linea
		Page<TDevolucionLinea> pagDevolucionLineas = null;	        

		//Obtención de la devolución actual
		Devolucion devol = (Devolucion)session.getAttribute("devolucion");
		String devolucionId = devol.getDevolucion().toString();

		String origenPantalla = request.getParameter("origenPantalla");
		String selectProv = request.getParameter("selectProv");
		
		List<TDevolucionLinea> list = null;
		Devolucion devolucion = new Devolucion();

		//Mirar si se ha seleccionado el combo de ref permanentes. En caso de orden de retirada no existirá esa opcion en principio, por lo que se deja en null.
		String flagRefPermanentes = null;
		
		String proveedor = null;

		devolucion.setDevolucion(new Long(devolucionId));

		//Obtención de la devolución de la lista de devoluciones
		@SuppressWarnings("unchecked")
		List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
		devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
		devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);


		if (origenPantalla != null
			&& (origenPantalla.toString().equals("pdaP104") || origenPantalla.toString().equals("pdaP105"))){
			if (selectProv != null && !selectProv.toString().equals("")){
				proveedor = selectProv.substring(0, selectProv.indexOf("-"));
			}
		}
		
		//Refrescamos la lista de devoluciones con las modificaciones correspondientes y con el nuevo filtro
		list = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null, null, proveedor, flagRefPermanentes);			
		list = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(), list);

		if(codArt != null){				
			TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
			devolucionLineaComparator.setCodArticulo(codArt);

			page = new Long(list.indexOf(devolucionLineaComparator))+1;		
		}else{
			Long codArticuloDevolucion = (Long)session.getAttribute("codArticuloDevolucion");
			if(codArticuloDevolucion != null){				
				TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
				devolucionLineaComparator.setCodArticulo(codArticuloDevolucion);

				page = new Long(list.indexOf(devolucionLineaComparator))+1;	
			}
		}
		int records = list.size();
		int desdeSubList = ((page.intValue()-1) * max.intValue());
		int hastaSubList = ((page.intValue())*max.intValue());

		//Hay que ver desde que elemento hasta que elemento hay que hacer la sublista. Para ello utilizamos
		//el elemento desdeSubList y hastaSubList. Puede darse la situación de que hastaSubList sea mayor a los
		//elementos de la lista, por ejemplo existen 6 paginaciones y se enseñan 10 elementos por paginación,
		//en la paginación 6 el elemento desdeSubList será 50 y hastaSubList 60, pero si la sublista tiene
		//53 elementos da error! Por lo cual hay que hacer un caso en el que hastaSubList sea igual al número
		//de elementos de la lista!
		if(hastaSubList > records){
			hastaSubList = records;				
		}

		//Obtenemos la lista de devolucion
		List<TDevolucionLinea> listaAMostrar = new ArrayList<TDevolucionLinea>(list.subList(desdeSubList,hastaSubList));

		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();
		pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listaAMostrar,max.intValue(), records, page.intValue());	
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());

		//Obtenemos el combo de proveedores
		LinkedHashMap<String,String> comboBox = this.loadComboProveedor(devolucion, response, session);

		//Obtención de combo de proveedores
		model.addAttribute("proveedores", comboBox);

		//Obtenemos las claves de los combos y les insertamos asteriscos.
		List<String> listaBultoPorProveedorTodos= new ArrayList<String>(comboBox.keySet());
		List<String> listaBultoPorProveedorFiltrado = new ArrayList<String>();

		Locale locale = LocaleContextHolder.getLocale();
		String refPermanentes = this.messageSource.getMessage("pda_p62_refPermanentes", null,locale);

		for(String clave:listaBultoPorProveedorTodos){
			if(clave != "" && clave !=refPermanentes){
				clave = clave + "*";
				listaBultoPorProveedorFiltrado.add(clave);
			}
		}

		List<TDevolucionLinea> tDevolucionLineaActualLst = new ArrayList<TDevolucionLinea>();

		//Obtención de la devolución actual
		if(pagDevolucionLineas != null && pagDevolucionLineas.getRows() != null && pagDevolucionLineas.getRows().size() > 0){
			int numeroLineasVisibles = pagDevolucionLineas.getRows().size();
			for(int i=0; i<numeroLineasVisibles; i++){
				TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();

				tDevolucionLineaActual = pagDevolucionLineas.getRows().get(i);
				
				//Obtenemos el formato devuelto
				String formatoDevuelto = tDevolucionLineaActual.getFormatoDevuelto();
				String formatoDevueltoFormat = formatoDevuelto;
				if(formatoDevuelto.equals(Constantes.DEVOLUCIONES_FORMATO1)){
					formatoDevueltoFormat = this.messageSource.getMessage("pda_p62_canOriCerrado", null, locale);
				}else if(formatoDevuelto.equals(Constantes.DEVOLUCIONES_FORMATO2)){
					formatoDevueltoFormat = this.messageSource.getMessage("pda_p62_unidadesKgs", null, locale);
				}
				
				tDevolucionLineaActual.setFormatoDevuelto(formatoDevueltoFormat);

				//Para comparar en el jsp los códigos de error
				tDevolucionLineaActual.setCodErrorStr(tDevolucionLineaActual.getCodError().toString());
				tDevolucionLineaActualLst.add(tDevolucionLineaActual);
			}
		}

		//Si existe el permiso de las fotos, mirar si la devolución a mostrar tiene una foto asignada, como la paginación será de 1 en 1, sabemos que la lista
		//tDevolucionLineaActualLst tendrá solo un elemento, por lo que tenemos que buscar la foto de ese elemento.
		if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones())
				&& !user.getCentro().getOpcHabil().isEmpty()
				&& user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_FOTO_27) != -1) {
			//Comprobar si tiene Foto
			FotosReferencia fotosReferencia = new FotosReferencia();

			if(tDevolucionLineaActualLst.size() > 0){
				//Como la lista a mostrar tiene solo un artículo, obtenemos el valor del elemento 0.
				fotosReferencia.setCodReferencia(tDevolucionLineaActualLst.get(0).getCodArticulo());

				//Miramos si existe la foto y en caso afirmativo, buscamos su path.
				if (fotosReferenciaService.checkImage(fotosReferencia)){
					model.addAttribute("tieneFoto",true);
				}else{
					model.addAttribute("tieneFoto",false);
				}
			}else{
				model.addAttribute("tieneFoto",false);
			}

		}else{
			model.addAttribute("tieneFoto",false);
		}
		
		int lineasCompletadas = this.devolucionesService.findLineasDevolucionCompletadas(list);

		model.addAttribute("pagDevolucionLineas", pagDevolucionLineas);
		model.addAttribute("devolucionCabecera", devolucion);

		Devolucion dev = new Devolucion();
		dev.settDevLineasLst(tDevolucionLineaActualLst);

		//Insertamos la lista con bulto por proveedor
		dev.setBultoPorProveedorLst(listaBultoPorProveedorFiltrado);
		
		Double sumaEuros = getCalcularSumaEuros(list);
		Map<String, Boolean> linkTextil = this.mostrarLinkTextil(list, page); 

		model.addAttribute("linkTextil", linkTextil);
		model.addAttribute("devolucionConLineas", dev);
		model.addAttribute("proveedorAnterior", "");
		model.addAttribute("sumaEuros", sumaEuros);
		model.addAttribute("completados", lineasCompletadas);
		model.addAttribute("primerElementoBultoFila", page-1);

		model.addAttribute("referenciaFiltroRellenaYCantidadEnter", "");

		model.addAttribute("origenPantalla", origenPantalla);
		model.addAttribute("selectProv", selectProv);
		
		String listaBultos="";
		User usuario= (User)session.getAttribute("user");
		boolean centroParametrizado = this.devolucionLineaBultoCantidadService.isCentroParametrizado(usuario.getCentro().getCodCentro(), Constantes.DEVOLUCIONES_PDA_PERMISO_27);
		String devolucionFinalizada = "";
		if(centroParametrizado){
			listaBultos=this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(), tDevolucionLineaActualLst.get(0));
			devolucionFinalizada = this.devolucionesService.esProveedorSinFinalizar(session.getId(), tDevolucionLineaActualLst.get(0).getDevolucion(), null, Constantes.PDA);
		}
		model.addAttribute("listaBultos", listaBultos);
		model.addAttribute("centroParametrizado", centroParametrizado);
		model.addAttribute("devolucionFinalizada", devolucionFinalizada);

		return resultado;
	}
	
	// 
	private List<DevolucionLinea> rellenarEstructuraLineaBulto(List<DevolucionLinea> listTDevolucionesModificadas,DevolucionLineaModificada devolucionLinea){
		List<BultoCantidad> listBultoCantidad=new ArrayList<BultoCantidad>();
		if(devolucionLinea.getBulto() != null && devolucionLinea.getStockDevuelto() != null){
			BultoCantidad bultoCantidad=new BultoCantidad();
			bultoCantidad.setBulto(devolucionLinea.getBulto());
			bultoCantidad.setStock(devolucionLinea.getStockDevuelto());
			bultoCantidad.setEstadoCerrado(devolucionLinea.getEstadoCerrado());
			listBultoCantidad.add(bultoCantidad);
		}
		listTDevolucionesModificadas.get(0).setListaBultoCantidad(listBultoCantidad);
		return listTDevolucionesModificadas;
	}
	
	private void insertarTablaSesionLineaDevolucion(String idSesion, Long devolucion, Long codArt, Double stockDevueltoPantalla, Long bultoPantalla, String estado){
		List<TDevolucionBulto> listTDevolucionLineaBultoCantidad = new ArrayList<TDevolucionBulto>();
		try {
			this.devolucionLineaBultoCantidadService.deleteLineasTablaBultoCantidad(idSesion, devolucion, codArt);
			if (stockDevueltoPantalla!=null && bultoPantalla!=null){
				TDevolucionBulto nuevoRegistroBultoCantidad = null;
				nuevoRegistroBultoCantidad = new TDevolucionBulto();
				nuevoRegistroBultoCantidad.setIdSesion(idSesion);
				nuevoRegistroBultoCantidad.setFechaGen(new Date());
				nuevoRegistroBultoCantidad.setDevolucion(devolucion);
				nuevoRegistroBultoCantidad.setBulto(bultoPantalla);
				nuevoRegistroBultoCantidad.setCodArticulo(codArt);
				nuevoRegistroBultoCantidad.setBultoOri(bultoPantalla);
				nuevoRegistroBultoCantidad.setStock(stockDevueltoPantalla);
				nuevoRegistroBultoCantidad.setStockOri(stockDevueltoPantalla);
				nuevoRegistroBultoCantidad.setEstadoCerrado(estado);
				nuevoRegistroBultoCantidad.setCodError(0L);
				nuevoRegistroBultoCantidad.setDescError(null);
				nuevoRegistroBultoCantidad.setCreationDate(new Date());
				listTDevolucionLineaBultoCantidad.add(nuevoRegistroBultoCantidad);
				this.devolucionLineaBultoCantidadService.insertAll(listTDevolucionLineaBultoCantidad);
			}
		} catch (Exception e) {
			logger.error("insertar Tabla Lista Bulto Cantidad= "+e.toString());
			e.printStackTrace();
		}	
	}
	
	private void actualizarDatosTablaSesionLineaBulto(Long devolucion, Long codArt, String idSesion) throws Exception{
		DevolucionLineaModificada devolucionLineaActualizar = new DevolucionLineaModificada();
		//Creamos dos nuevos objetos, para actualizar la devolución linea
		Devolucion devolucionActualizar = new Devolucion();

		//Creamos una lista para insertar la línea de devolución modificada
		List<DevolucionLineaModificada> devLineaLstModificada = new ArrayList<DevolucionLineaModificada>();

		//Insertamos en la línea el artículo al que corresponde esa línea y el estado modificado como código 9
		devolucionLineaActualizar.setCodError(new Long(9));
		devolucionLineaActualizar.setCodArticulo(codArt);

		//Insertamos la línea de devolución modificada
		devLineaLstModificada.add(devolucionLineaActualizar);

		devolucionActualizar.setDevolucion(devolucion);
		devolucionActualizar.setDevLineasModificadas(devLineaLstModificada);

		this.devolucionesService.updateTablaSesionLineaDevolucion(idSesion,devolucionActualizar,false);
	}
	
	private List<DevolucionLinea> rellenarEstructuraLineaBulto(List<DevolucionLinea> listTDevolucionesModificadas, Double stockDevueltoPantalla, Long bultoPantalla,String estadoCerradoPantalla){
		List<BultoCantidad> listBultoCantidad=new ArrayList<BultoCantidad>();
			BultoCantidad bultoCantidad=new BultoCantidad();
			bultoCantidad.setBulto(bultoPantalla);
			bultoCantidad.setStock(stockDevueltoPantalla);
			bultoCantidad.setEstadoCerrado(estadoCerradoPantalla);
			listBultoCantidad.add(bultoCantidad);
		listTDevolucionesModificadas.get(0).setListaBultoCantidad(listBultoCantidad);
		return listTDevolucionesModificadas;
	}
}
