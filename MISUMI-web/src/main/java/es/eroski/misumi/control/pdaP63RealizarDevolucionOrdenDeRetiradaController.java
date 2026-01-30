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
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.ParamCentrosOpc;
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
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
public class pdaP63RealizarDevolucionOrdenDeRetiradaController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP63RealizarDevolucionOrdenDeRetiradaController.class);

	@Autowired
	private DevolucionService devolucionesService;
	
	@Autowired
	private DevolucionLineaBultoCantidadService devolucionLineaBultoCantidadService;

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
	

	@RequestMapping(value = "/pdaP63RealizarDevolucionOrdenDeRetirada",method = RequestMethod.GET)
	public String showForm(ModelMap model,
							@RequestParam(value="devolucion") String devolucionId,
							@RequestParam(value="origenPantalla", required=false, defaultValue = "") String origenPantalla,
							@RequestParam(value="selectProv", required=false, defaultValue = "") String selectProv,
							@RequestParam(value="bultoSeleccionado", required=false, defaultValue = "0") int bultoSeleccionado,
							HttpSession session, HttpServletRequest request,
							HttpServletResponse response) throws Exception{

		String resultado = "pda_p63_realizarDevolucionOrdenDeRetirada";

		List<TDevolucionLinea> listTDevolucionLinea = null;
		
		String proveedor = null;
		
		if (selectProv != null && !selectProv.toString().equals("")){
			proveedor = selectProv.substring(0, selectProv.indexOf("-"));
		}

		//Paginación de devolución linea
		Page<TDevolucionLinea> pagDevolucionLineas = null;	        
		int primerElementoAMostrar = 0;
		int paginaPrimerElementoAMostrar = (primerElementoAMostrar / Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA) + 1;
		int indiceInicioPaginaPrimerElementoAMostrar = (paginaPrimerElementoAMostrar-1) * Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA;
		int indiceFinPaginaPrimerElementoAMostrar = indiceInicioPaginaPrimerElementoAMostrar + Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA;

		// Llamada para conseguir las devoluciones. 
		Devolucion devolucion = new Devolucion();
		devolucion.setDevolucion(new Long(devolucionId));

		//Obtención de la devolución de la lista de devoluciones
		@SuppressWarnings("unchecked")
		List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
		devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
		devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);

		//Obtiene una devolución con sus líneas de devolución
		DevolucionCatalogoEstado devol = devolucionesService.cargarAllDevoluciones(devolucion); 

		//Se mira que existe la lista de líneas de devolución
		if(devol != null){
			if(devol.getListDevolucionEstado() != null && devol.getListDevolucionEstado().size() > 0){
				if(devol.getListDevolucionEstado().get(0).getListDevolucion() != null && devol.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
					Devolucion devolucionSeleccion = devol.getListDevolucionEstado().get(0).getListDevolucion().get(0);
					if(devolucionSeleccion.getDevLineas() != null && devolucionSeleccion.getDevLineas().size() > 0){																							

						//Se obtiene la lista de líneas de devolución
						List<DevolucionLinea> listDevolucionLinea = (List<DevolucionLinea>) devolucionSeleccion.getDevLineas();

						// Cuando hacemos la primera búsqueda eliminamos siempre los
						// registros guardados anteriormente. Se eliminarán todos los
						//registros de todas las sesiones que lleven más de un día en
						//la tabla.
						this.eliminarTablaSesionHistorico();

						// Insertar tabla temporal T_DEVOLUCIONES
						// A partir de la lista obtenida tenemos que insertar en la
						// tabla temporal los registros obtenidos,
						// borrando previamente los posibles registros almacenados.
						this.eliminarTablaSesion(session.getId());

						//Se inserta en una tabla temporal cada línea de devolución. Se devuelve una lista de TDevolucionLinea tratada
						//a partir de la lista obtenida en el PLSQL. Así evitamos tirar de la tabla temporal al ya tener los datos.
						this.insertarTablaSesionLineaDevolucion(listDevolucionLinea, session.getId(),devolucionSeleccion.getDevolucion());																       		

						// Carga del valor del bulto seleccionado en la pistola.
						devolucion.setBultoSeleccionado(bultoSeleccionado);

						//Obtenemos la lista ordenada de la tabla tmeporal para mostrar en el grid
						listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion,null,null,proveedor,null);						
						listTDevolucionLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLinea);
						PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

						//El número de registros será el de la lista obtenida.
						int records = listTDevolucionLinea.size();

						if(listDevolucionLinea.size() < Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA){
							pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listTDevolucionLinea, Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA, records, 1);
						}else{
							//Buscamos el primer elemento de la lista que tenga stockDevuelto null o bulto null
							primerElementoAMostrar = this.devolucionesService.firstLineaDevolucionStockOrBultoNull(null, listTDevolucionLinea);
							paginaPrimerElementoAMostrar = (primerElementoAMostrar / Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA) + 1;
							indiceInicioPaginaPrimerElementoAMostrar = (paginaPrimerElementoAMostrar-1) * Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA;
							indiceFinPaginaPrimerElementoAMostrar = indiceInicioPaginaPrimerElementoAMostrar + Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA;
							pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listTDevolucionLinea.subList(indiceInicioPaginaPrimerElementoAMostrar, indiceFinPaginaPrimerElementoAMostrar), Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA, records, paginaPrimerElementoAMostrar);
						}
					}else{
						resultado = "pda_p66_noHayDevolucionesError";
						model.addAttribute("origenErrorDevolucion","pda_p63_realizarDevolucionOrdenDeRetirada");
					}
				}else{
					resultado = "pda_p66_noHayDevolucionesError";
					model.addAttribute("origenErrorDevolucion","pda_p63_realizarDevolucionOrdenDeRetirada");
				}
			}else{
				resultado = "pda_p66_noHayDevolucionesError";
				model.addAttribute("origenErrorDevolucion","pda_p63_realizarDevolucionOrdenDeRetirada");
			}
		}else{
			resultado = "pda_p66_noHayDevolucionesError";
			model.addAttribute("origenErrorDevolucion","pda_p63_realizarDevolucionOrdenDeRetirada");
		}

		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());

		//Obtenemos el combo de proveedores
		LinkedHashMap<String,String> comboBox = this.loadComboProveedor(devolucion, response, session);

		//Obtención de combo de proveedores
		model.addAttribute("proveedores", comboBox);

		//Obtenemos las claves de los combos y les insetamos asteriscos.
		List<String> listaBultoPorProveedorTodos = new ArrayList<String>(comboBox.keySet());
		List<String> listaBultoPorProveedorFiltrado = new ArrayList<String>();

		Locale locale = LocaleContextHolder.getLocale();
		String refPermanentes = this.messageSource.getMessage("pda_p62_refPermanentes", null,locale);

		for(String clave:listaBultoPorProveedorTodos){
			if(clave != "" && clave !=refPermanentes){
				clave = clave + "*";
				listaBultoPorProveedorFiltrado.add(clave);
			}
		}

		TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();

		//booleano que indica si mostrar link para caducidad
		boolean linkCaducidad = false;
		//booleano que indica si mostrar link para lote
		boolean linkLote = false;

		//Obtención de la devolución actual
		if(pagDevolucionLineas != null && pagDevolucionLineas.getRows() != null && pagDevolucionLineas.getRows().size() > 0){
			tDevolucionLineaActual = pagDevolucionLineas.getRows().get(0);

			//Para comparar en el jsp los códigos de error
			tDevolucionLineaActual.setCodErrorStr(tDevolucionLineaActual.getCodError().toString());

			//Control que calcula si se va a enseñar un link o no para mostrar la información de la caducidad o no al ser demasiado largo. En principio, si hay más de una
			//coma, significa que hay más de 2 fechas al separarse cada fecha con comas. Si hay más de una coma, se mostrará el link.
			if(tDevolucionLineaActual.getnCaducidad() != null){
				if(StringUtils.countMatches(tDevolucionLineaActual.getnCaducidad(), ",") > 1){
					linkCaducidad = true;
				}
			}
			//Control que calcula si se va a enseñar un link o no para mostrar la información de la lote o no al ser demasiado largo. En principio, si hay más de 25 dígitos,
			//se mostrará un link.
			if(tDevolucionLineaActual.getnLote() != null){
				if(tDevolucionLineaActual.getnLote().length() > 25){
					linkLote = true;
				}
			}
		}

		int lineasCompletadas = this.devolucionesService.findLineasDevolucionCompletadas(listTDevolucionLinea);

		//Insertamos la lista de bultos por proveedor
		tDevolucionLineaActual.setBultoPorProveedorLst(listaBultoPorProveedorFiltrado);
		Map<String, Boolean> linkTextil = this.mostrarLinkTextil(tDevolucionLineaActual);

		//links caducidad y lote
		model.addAttribute("linkCaducidad", linkCaducidad);
		model.addAttribute("linkLote", linkLote);
		model.addAttribute("linkTextil", linkTextil);

		model.addAttribute("pagDevolucionLineas", pagDevolucionLineas);
		model.addAttribute("devolucionCabecera", devolucion);
		model.addAttribute("proveedorAnterior", "");
		

		model.addAttribute("devolucionLinea", tDevolucionLineaActual);
		model.addAttribute("completados", lineasCompletadas);
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
			listaBultos=this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(),tDevolucionLineaActual);
			devolucionFinalizada=this.devolucionesService.esProveedorSinFinalizar(session.getId(),tDevolucionLineaActual.getDevolucion(),null, Constantes.PDA);
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
	private void insertarTablaSesionLineaDevolucion(List<DevolucionLinea> listDevolucionLinea, String idSesion, Long devolucion){

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
				logger.error("insertar Tabla Lista Bulto Cantidad= "+e.toString());
				e.printStackTrace();
			}	
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
			nuevoRegistro.setFlgPesoVariable((devolLinea.getFlgPesoVariable() != null && !("".equals(devolLinea.getFlgPesoVariable())))?devolLinea.getFlgPesoVariable():null); // MISUMI-295
			nuevoRegistro.setStockDevueltoBandejas(devolLinea.getStockDevueltoBandejas());
			nuevoRegistro.setStockDevueltoBandejasOrig(devolLinea.getStockDevueltoBandejas());
			nuevoRegistro.setDescrTalla(devolLinea.getDescrTalla());
			nuevoRegistro.setDescrColor(devolLinea.getDescrColor());
			nuevoRegistro.setModelo(devolLinea.getModelo());
			nuevoRegistro.setModeloProveedor(devolLinea.getModeloProveedor());
			nuevoRegistro.setArea(devolLinea.getArea());
			nuevoRegistro.setCosteUnitario(devolLinea.getCosteUnitario());

			//El objeto se inseta en una lista
			listTDevolucionLinea.add(nuevoRegistro);
		}

		try {
			this.devolucionesService.insertAll(listTDevolucionLinea);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}	
	}

	//Elimina la tabla temporal histórica de todos los usuarios si ha pasado más de un día desde
	//la creación de los registros
	private void eliminarTablaSesionHistorico(){		
		try {
			this.devolucionesService.deleteHistorico();
			this.devolucionLineaBultoCantidadService.deleteHistorico();
		} catch (Exception e) {
			logger.error("eliminarTablaSesionHistorico="+e.toString());
			e.printStackTrace();
		}
	}

	//Elimina los datos de sesión de la tabla temporal de ese usuario 
	private void eliminarTablaSesion(String idSesion){		
		TDevolucionLinea registro = new TDevolucionLinea();		
		registro.setIdSesion(idSesion);

		try {
			this.devolucionesService.delete(registro);
			this.devolucionLineaBultoCantidadService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

	//Sirve para cargar los valores del combobox proveedor ordenados
	private  LinkedHashMap<String, String> loadComboProveedor(
			Devolucion devolucion,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		List<OptionSelectBean> list = null;
		try {
			list = this.devolucionesService.obtenerProveedoresLineasDevolucion(session.getId(),devolucion);
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

	@RequestMapping(value = "/pdaP63RealizarDevolucionOrdenDeRetirada",method = RequestMethod.POST)
	public String processForm(@Valid final TDevolucionLinea devolucionLinea,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{

		String resultado = "pda_p63_realizarDevolucionOrdenDeRetirada";
		final String accion = request.getParameter("accion");	
		String origenPantalla = request.getParameter("origenPantalla");
		String selectProv = request.getParameter("selProv");

		//Obtención de datos del filtro
		PdaDatosCabecera pdaDatosCabecera = new PdaDatosCabecera();
		pdaDatosCabecera.setCodArtCab((request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
		pdaDatosCabecera.setProveedor((request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));

		String proveedor = pdaDatosCabecera.getProveedor();
		
		if (origenPantalla != null
			&& (origenPantalla.toString().equals("pdaP104") || origenPantalla.toString().equals("pdaP105"))){
			if (selectProv != null && !selectProv.toString().equals("")){
				proveedor = selectProv.substring(0, selectProv.indexOf("-"));
			}
		}

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

		User user = (User) session.getAttribute("user");

		//Paginación de devolución linea
		Page<TDevolucionLinea> pagDevolucionLineas = null;
		Long page = new Long(1);
		if(request.getParameter("paginaActual") != "" && request.getParameter("paginaActual") != null){
			page = new Long((request.getParameter("paginaActual"))); //Recuperamos la página actual de pantalla.
		}
		Long max = new Long(Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA);

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

		List<TDevolucionLinea> list = null;
		Devolucion devolucion = new Devolucion();

		//Mirar si se ha seleccionado el combo de ref permanentes. En caso de orden de retirada no existirá esa opcion en principio, por lo que se deja en null.
		String flagRefPermanentes = null;

		try {
			devolucion.setDevolucion(new Long(devolucionId));

			//Obtención de la devolución de la lista de devoluciones
			@SuppressWarnings("unchecked")
			List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
			devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
			devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);

			//Como esta lista se va a usar para detectar si hay devoluciones editadas, y al filtrar se cambia de proveedor, es necesario saber que proveedor había antes de realizar el cambio para así obtener la lista correcpondiente
			//y comparar con la lista original los cambios realizados. En caso de paginar y demás funciones, el proveedor será el mismo.
			String proveedorAnt = request.getParameter("proveedorAnterior");

			if (origenPantalla != null
				&& (origenPantalla.toString().equals("pdaP104") || origenPantalla.toString().equals("pdaP105"))){
				if (selectProv != null && !selectProv.toString().equals("")){
					proveedorAnt = selectProv.substring(0, selectProv.indexOf("-"));
				}
			}
			
			//Se busca la lista de líneas de devolución antes de paginar, filtrar, ver cabecera, ver más información, etc.
			List<TDevolucionLinea> listTDevolucionLineaAnt = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null,null, proveedorAnt, flagRefPermanentes);
			listTDevolucionLineaAnt = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLineaAnt);
			//Se mira que se haya modificado algún elemento. Este flag se utilizará para entrar en la función de actualización plsql si ha habido algún cambio.
			boolean elementosGuardados = false;

			//Mirar si ha habido cambios en la línea de devolución si se pagina únicamente.
			if(listTDevolucionLineaAnt.size() > 0 && 
					(Constantes.DEVOLUCIONES_PDA_ACCION_PRIMERA_PAGINA.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_ANTERIOR_PAGINA.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_SIGUIENTE_PAGINA.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_ULTIMA_PAGINA.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_GUARDAR.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_SIGUIENTE_VACIO.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_FILTRAR.equals(accion) 
							|| Constantes.DEVOLUCIONES_PDA_ACCION_VERCABECERA.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_STOCKLINK.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_STOCKDEVUELTOLINK.equals(accion))){
				//Cada vez que paginemos miraremos si se ha actualizado el bulto o la cantidad y en ese caso, se actualizará la tabla temporal.

				//Buscamos la línea de devolución antes de paginar 
				TDevolucionLinea devolucionLineaAntesDePaginar = listTDevolucionLineaAnt.get(paginaAntesDePaginar.intValue() - 1);

				//Cremos un booleano que indique si se ha modificado la línea de devolución
				boolean isLineaModificada = false;

				//Creamos un objeto que guarde los cambios de las líneas de devolución
				DevolucionLineaModificada devolucionLineaActualizar = new DevolucionLineaModificada();
				devolucionLineaActualizar.setEstadoCerrado(devolucionLinea.getEstadoCerrado());
				devolucionLineaActualizar.setBultoEstadoCerrado(devolucionLinea.getBultoEstadoCerrado());
				
				if(devolucionLinea.getVariosBultos()==null || !devolucionLinea.getVariosBultos()){
					//Controlamos que se haya cambiado el bulto de la línea de devolución
					if(devolucionLineaAntesDePaginar.getBulto() == null){
						if(devolucionLinea.getBulto() != null){
	
							//Obtener el bulto nuevo
							devolucionLineaActualizar.setBulto(devolucionLinea.getBulto());
	
							//Se ha modificado la línea de devolución
							isLineaModificada = true;
						}
					}else{
						if(!devolucionLineaAntesDePaginar.getBulto().equals(devolucionLinea.getBulto())){
							//Se ha modificado la línea de devolución
							isLineaModificada = true;
						}
						//Obtener el bulto nuevo
						devolucionLineaActualizar.setBulto(devolucionLinea.getBulto());
					}
	
					//Controlamos que se haya cambiado la cantidad del estado de la devolución, stockDevuelto
					if(devolucionLineaAntesDePaginar.getStockDevuelto() == null){
						if(devolucionLinea.getStockDevuelto() != null){
	
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
				}
				//Si la línea de devolución se ha modificado, actualizar la tabla temporal
				if (isLineaModificada){
					//Creamos dos nuevos objetos, para actualizar la devolución linea
					Devolucion devolucionActualizar = new Devolucion();

					//Creamos una lista para insertar la línea de devolución modificada
					List<DevolucionLineaModificada> devLineaLstModificada = new ArrayList<DevolucionLineaModificada>();

					//Insertamos en la línea el artículo al que corresponde esa línea y el estado modificado como código 9
					devolucionLineaActualizar.setCodError(new Long(9));
					devolucionLineaActualizar.setCodArticulo(devolucionLineaAntesDePaginar.getCodArticulo());				

					//Insertamos la línea de devolución modificada
					devLineaLstModificada.add(devolucionLineaActualizar);

					devolucionActualizar.setDevolucion(devolucion.getDevolucion());	
					devolucionActualizar.setDevLineasModificadas(devLineaLstModificada);

					this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizar,false);
					this.devolucionLineaBultoCantidadService.deleteLineasTablaBultoCantidad(session.getId(),devolucion.getDevolucion(),devolucionLineaAntesDePaginar.getCodArticulo());
					
					List<TDevolucionBulto> listTDevolucionLineaBultoCantidad = new ArrayList<TDevolucionBulto>();
					//Se inicializa el objeto. Cada objeto de este tipo será un registro de la tabla temporal
					if(devolucionLineaActualizar.getBulto() != null && devolucionLineaActualizar.getBulto() != null){
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
					}
					
					try {
						this.devolucionLineaBultoCantidadService.insertAll(listTDevolucionLineaBultoCantidad);
					} catch (Exception e) {
						logger.error("insertar Tabla Lista Bulto Cantidad= "+e.toString());
						e.printStackTrace();
					}	
					//Si se ha modificado algún elemento se actualizará con plsql
					elementosGuardados = true;
				}
			}
			
			//Refrescamos la lista de devoluciones con las modificaciones correspondientes y con el nuevo filtro
			list = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null, null, proveedor, flagRefPermanentes);			
			list = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(), list);
			//Si no hay error al guardar. Ejecutamos las acciones de la devolución. Si no, se muestra el error de guardado.
			//Y se ignora la accion (Guardar, VerCabecera,MasInfo,Finalizar) que se estaba realizando. 
			boolean errorAlGuardar = false;

			//Se guardan los cambios realizados en todos los casos.
			if(elementosGuardados &&
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
							|| Constantes.DEVOLUCIONES_PDA_ACCION_STOCKLINK.equals(accion)
							|| Constantes.DEVOLUCIONES_PDA_ACCION_STOCKDEVUELTOLINK.equals(accion))){

				//Si se ha pulsado el botón actualizar
				DevolucionCatalogoEstado devolucionCatalogoEstadoActualizada = null;

				//Una vez tenemos la tabla temporal actualizada, obtenemos los elementos modificados.
				List<DevolucionLinea> listTDevolucionesModificadas = this.devolucionesService.findLineasDevolucionEditadas(session.getId(),new Long("9"));		
				listTDevolucionesModificadas=this.rellenarEstructuraLineaBulto(listTDevolucionesModificadas, devolucionLinea);
				//Si hay líneas de devolución editadas, actualizamos esas líneas mediante el PLSQL, actualizamos la temporal con los códigos de error correspondientes
				//y obtenemos las líneas del grid actualizadas de la tabla temporal actualizada. Si no hay modificaciones, obtenemos la lista tal cual se encuentra
				//en la tabla temporal.
				if(listTDevolucionesModificadas != null & listTDevolucionesModificadas.size() > 0){
					//Se insertan las líneas de devolución modificadas únicamente en la lista de líneas de devolución de la lista.
					devolucion.setDevLineas(listTDevolucionesModificadas);

					//Actualizamos la devolución y obtenemos el resultado con las líneas de devolucion y sus códigos de error
					devolucionCatalogoEstadoActualizada = this.devolucionesService.actualizarDevolucion(devolucion);

					if(devolucionCatalogoEstadoActualizada != null && ((new Long("0")).equals(devolucionCatalogoEstadoActualizada.getpCodError()) || devolucionCatalogoEstadoActualizada.getpCodError() == null)){
						if(devolucionCatalogoEstadoActualizada.getListDevolucionEstado() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().size() > 0){
							if(devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
								//Obtenemos la devolucion actualizada
								Devolucion devolucionActualizada = devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().get(0);

								if(new Long("0").equals(devolucionActualizada.getCodError()) || devolucionActualizada.getCodError() == null){
									//Por cada línea de la devolución si el codError es 9, significa que se ha actualizado bien, al no haber cambio en el código de error insertado en el PLSQL. En ese caso insertamos a las líneas el código de error 8 
									//para indicar que han pasado de modificadas a guardadas. En caso contrario, se inserta el código de error obtenido. Estos cambios, habrá que guardarlos en la tabla temporal para así tener actualizados los estados de
									//cada línea y dibujar bien el grid, por lo cual utilizaremos el método updateTablaSesionLineaDevolucion. 

									DevolucionLineaModificada devLineaModificada = null;
									List<DevolucionLineaModificada> devLineaModificadaActualizada = new ArrayList<DevolucionLineaModificada>();
									Long codError = null;
									for(DevolucionLinea devLinea:devolucionActualizada.getDevLineas()){		
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
									model.addAttribute("actionP67Volver","pdaP63RealizarDevolucionOrdenDeRetirada.do");

									errorAlGuardar = true;
									model.addAttribute("codErrorActualizar",err);
									resultado = "pda_p67_errorDevolucionLinea";
								}
							}else{
								String err = "ERR_ACT";
								model.addAttribute("actionP67Volver","pdaP63RealizarDevolucionOrdenDeRetirada.do");

								errorAlGuardar = true;
								model.addAttribute("codErrorActualizar",err);
								resultado = "pda_p67_errorDevolucionLinea";
							}
						}else{
							String err = "ERR_ACT";
							model.addAttribute("actionP67Volver","pdaP63RealizarDevolucionOrdenDeRetirada.do");

							errorAlGuardar = true;
							model.addAttribute("codErrorActualizar",err);
							resultado = "pda_p67_errorDevolucionLinea";
						}
					}else{
						String err = "ERR_ACT";
						model.addAttribute("actionP67Volver","pdaP63RealizarDevolucionOrdenDeRetirada.do");

						errorAlGuardar = true;
						model.addAttribute("codErrorActualizar",err);
						resultado = "pda_p67_errorDevolucionLinea";
					}
				}

				//Se obtienen las devoluciones con sus estados actualizados
				list = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null, codArtFiltro, proveedor, flagRefPermanentes);			
				list = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),list);
			}
			
			//Insertamos la lista de los bultos por proveedor en la devolución para que
			//al ocurrir un error y volver atrás, guarde los valores de los bultos por proveedor
			devolucion.setBultoPorProveedorLst(devolucionLinea.getBultoPorProveedorLst());

			if (Constantes.DEVOLUCIONES_PDA_ACCION_ULTIMA_PAGINA.equals(accion) &&  !errorAlGuardar){
				page = new Long(list.size());
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
					model.addAttribute("actionP80","pdaP63RealizarDevolucionOrdenDeRetirada.do");
					if (origenPantalla != null && origenPantalla != ""){
						model.addAttribute("origenPantalla", origenPantalla);
						model.addAttribute("selectProv", selectProv);
					}
					return "pda_p80_pedirRmaDevolucion";
				}else{

					String existenStockDevueltosConCero = devolucionesService.existeStockDevueltoConCeroTablaSesionLineaDevolucion(session.getId(),devolucion);
					
					model.addAttribute("existenStockDevueltosConCero",existenStockDevueltosConCero);
					model.addAttribute("actionP68","pdaP63RealizarDevolucionOrdenDeRetirada.do");
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
					
					if(msgErrorKey!=null){
						// volvemos a pedir el RMA con mensaje de error y restauramos la situacion anterior
						devolucion.setCodRMA(codRmaLastValue);
						model.addAttribute("actionP80","pdaP63RealizarDevolucionOrdenDeRetirada.do");
						model.addAttribute("msgErrorKey", msgErrorKey);
						return "pda_p80_pedirRmaDevolucion";
					}else{
						// navegamos a la de finalizar para confirmar
						String existenStockDevueltosConCero = devolucionesService.existeStockDevueltoConCeroTablaSesionLineaDevolucion(session.getId(),devolucion);
						model.addAttribute("existenStockDevueltosConCero",existenStockDevueltosConCero);
						model.addAttribute("actionP68","pdaP63RealizarDevolucionOrdenDeRetirada.do");
						return "pda_p68_msgFinalizarSiNo";
					}
				}
			}else if (Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR_POPUP.equals(accion) && !errorAlGuardar){
				//Cuando se pulsa finalizar sale un mensaje informando al usario de si está seguro de si desea finalizar la devolución. Si pulsa si, ejecuta lo siguiente.
				if(request.getParameter("finalizar_si") != null){					
					//Obtenemos el flgRellenarHuecos. En este caso se llama existenStockDevueltosConCeros conseguido en la opción Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR
					String existenStockDevueltosConCero = request.getParameter("existenStockDevueltosConCero");
					
					//Se finaliza la devolución
					DevolucionEmail devolucionEmail = devolucionesService.finalizarDevolucion(devolucion,existenStockDevueltosConCero,"PDA");
					//Si todo va correctamente va a la pantalla de finalizada correctamente
					if (devolucionEmail != null && Constantes.DEVOLUCIONES_PDA_CODIGO_FINALIZAR_CORRECTO.equals(devolucionEmail.getCodError())){
						model.addAttribute("devolucion", devolucion);
						model.addAttribute("actionP64Volver","pdaP63RealizarDevolucionOrdenDeRetirada.do");

						return "pda_p64_realizarDevolucionSatisfactoria";
					}
					//Si da otro tipo de error, va a una pantalla que permite ver el error.
					else{
						//Si hay error después de actualizar con ceros las cantidades vacías y bultos vacíos, actualizamos la tabla temporal con los ceros para que los datos sean los reales.
						if(("S").equals(existenStockDevueltosConCero)){
							devolucionesService.updateConCeroTablaSesionLineaDevolucion(session.getId(),devolucion);
						}
						model.addAttribute("devolucion", devolucion);
						model.addAttribute("msgError", devolucionEmail.getMsgError());
						model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
						model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
						model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));
						model.addAttribute("actionP65Volver","pdaP63RealizarDevolucionOrdenDeRetirada.do");

						return "pda_p65_realizarDevolucionErronea";
					}	
				}
			}else if (Constantes.DEVOLUCIONES_PDA_ACCION_SIGUIENTE_VACIO.equals(accion) && !errorAlGuardar){
				//Buscamos el primer elemento de la lista que tenga stockDevuelto null o bulto null a partir de la linea de devolución en la que estamos.
				int primerElementoAMostrar = this.devolucionesService.firstLineaDevolucionStockOrBultoNull((int)(long)paginaAntesDePaginar, list);
				page = (long) (primerElementoAMostrar + 1);
			}else if((Constantes.DEVOLUCIONES_PDA_ACCION_VERCABECERA.equals(accion) && !errorAlGuardar) || Constantes.DEVOLUCIONES_PDA_ACCION_VERCABECERADESDEERROROK.equals(accion)){
				
				//Obtención de la devolución de la lista de devoluciones
				model.addAttribute("devolucion", devolucion);
				model.addAttribute("actionP61Volver","pdaP63RealizarDevolucionOrdenDeRetirada.do");
				model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
				model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
				model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));
				model.addAttribute("origenPantalla", request.getParameter("origenPantalla"));
				model.addAttribute("selectProv", selectProv);
				
				return "pda_p61_realizarDevolucionCabecera";
				
			}else if((Constantes.DEVOLUCIONES_PDA_ACCION_MASINFO.equals(accion) && !errorAlGuardar)){								
				//MASINFO ocurre en orden de retirada, y en Fin de campanya,
				//cuando hay informacion de textil que no entre bien, muchas fechas de caducidad o muchos lotes. 
				// Corresponde a un link y al pincharlo, se muestran las fechas de caducidad y los lotes en
				//un jsp. Por eso se han creado las dos listas siguientes.
				TDevolucionLinea tDevLineaDetalle = list.get(paginaAntesDePaginar.intValue()-1);
				
				//Creamos una lista de fechas
				if(tDevLineaDetalle.getnCaducidad() != null){
					String[] fechas = tDevLineaDetalle.getnCaducidad().split(",");
					List<String> caducidadConcatLst = new ArrayList<String>();
					for(int i=0;i<fechas.length;i=i+2){
						String fechaConcat1 = fechas[i].trim();
						String fechaConcat2  = "";
						if(i+1<fechas.length){
							fechaConcat2 = ", " + fechas[i+1].trim();
						}

						String fechaConcat = fechaConcat1 + fechaConcat2;
						caducidadConcatLst.add(fechaConcat);
					}
					model.addAttribute("caducidadConcatLst",caducidadConcatLst);
				}

				//Creamos una lista de lotes
				if(tDevLineaDetalle.getnLote() != null){
					String[] lotes = tDevLineaDetalle.getnLote().split(",");
					List<String> loteConcatLst = new ArrayList<String>();
					for(int i=0;i<lotes.length;i=i+3){
						String loteConcat1 = lotes[i].trim();
						String loteConcat2 = "";
						String loteConcat3 = "";

						if(i+1<lotes.length){
							loteConcat2 = ", " + lotes[i+1].trim();
						}
						if(i+2<lotes.length){
							loteConcat3 = ", " + lotes[i+2].trim();
						}

						String loteConcat = loteConcat1 + loteConcat2 +loteConcat3;
						loteConcatLst.add(loteConcat);
					}
					model.addAttribute("loteConcatLst",loteConcatLst);
				}

				// si es de textil
				if(tDevLineaDetalle.getArea() !=null 
						&& tDevLineaDetalle.getArea().longValue() == 3L){
					model.addAttribute("conTextil", true);
				}
				
				model.addAttribute("devolucionLinea", tDevLineaDetalle);				
				model.addAttribute("actionP61Volver","pdaP63RealizarDevolucionOrdenDeRetirada.do");
				model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
				model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
				model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));

				return "pda_p69_ordenDeRetiradaDetalle";
				
			}else if((Constantes.DEVOLUCIONES_PDA_ACCION_STOCKLINK.equals(accion) && !errorAlGuardar)){
				
				VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(devolucionLinea.getCodArticulo());
				
				ReferenciasCentro referenciasCentro = new ReferenciasCentro();
				referenciasCentro.setCodArt(devolucionLinea.getCodArticulo());
				
				referenciasCentro.setCodCentro(user.getCentro().getCodCentro());
				referenciasCentro.setDiarioArt(vDatosDiarioArtResul);
				
				//Es necesario saber 
				VSurtidoTienda surtidoTienda = obtenerSurtidoTienda(referenciasCentro,false,false);
				
				if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro()!= null && !"".equals(surtidoTienda.getMarcaMaestroCentro())){
					model.addAttribute("MMC", surtidoTienda.getMarcaMaestroCentro());
				}else{
					model.addAttribute("MMC","N");
				}
				
				String estructuraComercial = request.getParameter("estructuraComercial");
				
				//Actualizamos la variable consultaStock para que se muestren los datos de stock de esta devolución
				ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
				stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
				stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
				List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
				listaReferencias.add(BigInteger.valueOf(devolucionLinea.getCodArticulo()));

				// DESCOMENTARLO UNA VEZ ESTE HECHO EL NUEVO SERVICIO DE TEXTIL. CORRESPONDE A LA PETICION 47885 (LA PARTE DE PISTOLA)
				// SE ACTIVA SOLO PARA LOS CENTROS: 5042-SEGOVIA, 0358-ABADIÃO, 0175-IRUÃA, 5326-INFANTE, 410 EIBAR, 164 GERNIKA
				//Si la referencia consultada es TEXTIL, se deben obtener todas las referencia que compartan el mismo cÃ³digo de proveedor.
				if (estructuraComercial.startsWith("03")) {  //Es un artÃ­culo de textil

					stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA); //En este caso es una consulta basica, al 
					//WS se le pasara una lista de referencias
					List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(devolucionLinea.getCodArticulo());

					for (Long articulo : referenciasMismoModeloProveedor) {
						String strArticulo = articulo + "";
						if (!strArticulo.equals(devolucionLinea.getCodArticulo() + "")) { //El articulo consultado por el usuario  (el de la cabecera) ya esta metido en la lista
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
						logger.error("########## CONTROLADOR: pdaP63RealizarDevolucionOrdenDeRetiradaController (processForm  1)	 ####");
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
				session.setAttribute("estructuraComercial", estructuraComercial);

				session.setAttribute("paginaActual", paginaAntesDePaginar.intValue());
				session.setAttribute("referenciaFiltro", request.getParameter("referenciaFiltro"));
				session.setAttribute("proveedorFiltro", request.getParameter("proveedorFiltro"));
				session.setAttribute("devolucion", devolucion);
				
				//Redirigimos a la página de stock pdaP28CorreccionStockInicio.do
				redirectAttributes.addAttribute("codArt", devolucionLinea.getCodArticulo());
				redirectAttributes.addAttribute("origen", "DVOR");
				if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro()!= null && !"".equals(surtidoTienda.getMarcaMaestroCentro())){
					redirectAttributes.addAttribute("mmc", surtidoTienda.getMarcaMaestroCentro());
				}else{
					redirectAttributes.addAttribute("mmc", "N");
				}

//				redirectAttributes.addFlashAttribute("origenPantalla", origenPantalla);
//				redirectAttributes.addFlashAttribute("selectProv", selectProv);

				redirectAttributes.addAttribute("origenPantalla", origenPantalla);
				redirectAttributes.addAttribute("selectProv", selectProv);
				
				return "redirect:pdaP28CorreccionStockInicio.do";
				
			} else if((Constantes.DEVOLUCIONES_PDA_ACCION_STOCKDEVUELTOLINK.equals(accion) && !errorAlGuardar)){

				Long.parseLong(request.getParameter("codArticulo"));
				String estructuraComercial = request.getParameter("estructuraComercial");
				
				String stockDevueltoBandejasStr = request.getParameter("stockDevueltoBandejas"); //Hidden
				Double stockDevueltoBandejas = null;
				if(stockDevueltoBandejasStr != null){
					stockDevueltoBandejas = Double.parseDouble(request.getParameter("stockDevueltoBandejas"));
				}else{
					stockDevueltoBandejas = 0.0;
				}
				
				String stockDevueltoStr = request.getParameter("stockDevuelto");
				Double stockDevuelto = null;
				if ((stockDevueltoStr != null) && !(stockDevueltoStr.equals(""))){
					stockDevuelto = Double.parseDouble(request.getParameter("stockDevuelto"));
				}else{
					stockDevuelto = 0.0;
				}							

				//Para saber si los campos de bandejas y kgs serán editables o no.
				boolean fechaDeDevolucionPasada = devolucion.isFechaDeDevolucionPasada();
				
				String descripcion = list.get(0).getDenominacion();
				Long codArt = devolucionLinea.getCodArticulo();								
				String descCodArt = codArt.toString() + "-" + descripcion;
				
				redirectAttributes.addAttribute("codArt", devolucionLinea.getCodArticulo());
				redirectAttributes.addAttribute("descCodArt", descCodArt);
				redirectAttributes.addAttribute("stockDevuelto", stockDevuelto); //campo kgs del pop-up
				redirectAttributes.addAttribute("stockDevueltoBandejas", stockDevueltoBandejas);	 //Es el campo hidden que en el pop-up se pinta en el campo Bandejas
				redirectAttributes.addAttribute("fechaDevolucionPasada", fechaDeDevolucionPasada);
				redirectAttributes.addAttribute("variosBultos", devolucionLinea.getVariosBultos());
				redirectAttributes.addAttribute("origen", "DVOR");
				redirectAttributes.addAttribute("origenPantalla", origenPantalla);
				redirectAttributes.addAttribute("selectProv", selectProv);
				session.setAttribute("estructuraComercial", estructuraComercial);
				session.setAttribute("paginaActual", paginaAntesDePaginar.intValue());
				session.setAttribute("referenciaFiltro", request.getParameter("referenciaFiltro"));
				session.setAttribute("proveedorFiltro", request.getParameter("proveedorFiltro"));
				session.setAttribute("devolucion", devolucion);
				
				return "redirect:pdaP73BandejasKgsInicio.do";
				
			}else if(Constantes.DEVOLUCIONES_PDA_ACCION_CERRAR_BULTO.equals(accion) ){
				
				this.devolucionLineaBultoCantidadService.deleteLineasTablaBultoCantidad(session.getId(), devolucionLinea.getDevolucion(), devolucionLinea.getCodArticulo());
				this.insertarTablaSesionLineaDevolucion(session.getId(), devolucionLinea.getDevolucion(), devolucionLinea.getCodArticulo(), devolucionLinea.getStockDevuelto(), devolucionLinea.getBulto(), devolucionLinea.getEstadoCerrado());
				this.actualizarDatosTablaSesionLineaBulto(devolucionLinea.getDevolucion(), devolucionLinea.getCodArticulo(),session.getId());
				this.devolucionLineaBultoCantidadService.actualizarEstadoBultoPorProveedor(session.getId(),devolucionLinea.getEstadoCerrado(),devolucionLinea.getDevolucion(),Long.valueOf(proveedorAnt),null,devolucionLinea.getBulto());
				this.devolucionLineaBultoCantidadService.procedimientoActualizarEstadoBultoPorProveedor(Constantes.CERRAR_BULTO,devolucionLinea.getDevolucion(),Long.valueOf(proveedorAnt),null,devolucionLinea.getBulto());
				List<DevolucionLinea> listTDevolucionesModificadas = this.devolucionesService.findLineasDevolucionEditadas(session.getId(),new Long("9"));		
				listTDevolucionesModificadas=this.rellenarEstructuraLineaBulto(listTDevolucionesModificadas, devolucionLinea.getStockDevuelto(), devolucionLinea.getBulto(), devolucionLinea.getEstadoCerrado());
				//Si hay líneas de devolución editadas, actualizamos esas líneas mediante el PLSQL, actualizamos la temporal con los códigos de error correspondientes
				//y obtenemos las líneas del grid actualizadas de la tabla temporal actualizada. Si no hay modificaciones, obtenemos la lista tal cual se encuentra
				//en la tabla temporal.
				//Se insertan las líneas de devolución modificadas únicamente en la lista de líneas de devolución de la lista.
				devolucion.setDevLineas(listTDevolucionesModificadas);
				DevolucionCatalogoEstado devolucionCatalogoEstadoActualizada = null;
				//Actualizamos la devolución y obtenemos el resultado con las líneas de devolucion y sus códigos de error
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
				list = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null, null, proveedor, flagRefPermanentes);			
				list = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(), list);
			}
			
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

		//Se mira si existe una línea de devolución con ese filtro. Si no existe, muestra una mantalla de error.
		List<TDevolucionLinea> existeLineaDevolucionFiltroLst = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null, codArtFiltro, proveedor, flagRefPermanentes);
		if (existeLineaDevolucionFiltroLst != null && existeLineaDevolucionFiltroLst.size() > 0) {		

			if(codArtFiltro != null){				
				TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
				devolucionLineaComparator.setCodArticulo(codArtFiltro);

				page = new Long(list.indexOf(devolucionLineaComparator))+1;		
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

			pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), list.subList(desdeSubList,hastaSubList),max.intValue(), records, page.intValue());
			
		}else{
			
			//Si no se encuentra una referencia sin haber seleccionado proveedor
			model.addAttribute("codErrP71","0");
			if(pdaDatosCabecera.getProveedor() != null){
				if(("").equals(pdaDatosCabecera.getProveedor())){
					model.addAttribute("codErrP71","0");
				}else{
					if(pdaDatosCabecera.getCodArtCab() != null){
						//Si no existe el articulo buscado para un proveedor
						model.addAttribute("codErrP71","2");
					}
				}
			}
			
			model.addAttribute("actionP71","pdaP63RealizarDevolucionOrdenDeRetirada.do");
			model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
			resultado = "pda_p71_noExisteRefDevLin";
		}

		int lineasCompletadas = this.devolucionesService.findLineasDevolucionCompletadas(list);

		///////////////////////

		model.addAttribute("pdaDatosCab", pdaDatosCabecera);

		//Obtención de combo de proveedores
		model.addAttribute("proveedores", this.loadComboProveedor(devolucion, response, session));

		TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();

		//booleano que indica si mostrar link para caducidad
		boolean linkCaducidad = false;
		//booleano que indica si mostrar link para lote
		boolean linkLote = false;

		//Obtención de la devolución actual
		if(pagDevolucionLineas != null && pagDevolucionLineas.getRows() != null && pagDevolucionLineas.getRows().size() > 0){
			tDevolucionLineaActual = pagDevolucionLineas.getRows().get(0);

			//Para comparar en el jsp los códigos de error
			tDevolucionLineaActual.setCodErrorStr(tDevolucionLineaActual.getCodError().toString());

			//Control que calcula si se va a enseñar un link o no para mostrar la información de la caducidad o no al ser demasiado largo. En principio, si hay más de una
			//coma, significa que hay más de 2 fechas al separarse cada fecha con comas. Si hay más de una coma, se mostrará el link.
			if(tDevolucionLineaActual.getnCaducidad() != null){
				if(StringUtils.countMatches(tDevolucionLineaActual.getnCaducidad(), ",") > 1){
					linkCaducidad = true;
				}			
			}
			//Control que calcula si se va a enseñar un link o no para mostrar la información de la lote o no al ser demasiado largo. En principio, si hay más de 25 dígitos,
			//se mostrará un link.
			if(tDevolucionLineaActual.getnLote() != null){
				if(tDevolucionLineaActual.getnLote().length() > 25){
					linkLote = true;
				}
			}
		}else{
			tDevolucionLineaActual = new TDevolucionLinea();
			tDevolucionLineaActual.setDevolucion(new Long(devolucionId));
		}

		tDevolucionLineaActual.setDenomProveedor(pdaDatosCabecera.getProveedor());		

		//Insertamos la lista de bultos por proveedor
		tDevolucionLineaActual.setBultoPorProveedorLst(devolucionLinea.getBultoPorProveedorLst());
		Map<String, Boolean> linkTextil = this.mostrarLinkTextil(tDevolucionLineaActual);

		//links caducidad y lote
		model.addAttribute("linkCaducidad", linkCaducidad);
		model.addAttribute("linkLote", linkLote);
		model.addAttribute("linkTextil", linkTextil);
		session.setAttribute("paginaActual", paginaAntesDePaginar.intValue());
		model.addAttribute("pagDevolucionLineas", pagDevolucionLineas);
		model.addAttribute("devolucionCabecera", devolucion);
		model.addAttribute("devolucionLinea", tDevolucionLineaActual);

		model.addAttribute("referenciaFiltro", pdaDatosCabecera.getCodArtCab());;
		model.addAttribute("proveedorFiltro", pdaDatosCabecera.getProveedor());
		model.addAttribute("completados", lineasCompletadas);

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
		if(centroParametrizado){
			listaBultos=this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(),tDevolucionLineaActual);
			devolucionFinalizada=this.devolucionesService.esProveedorSinFinalizar(session.getId(), tDevolucionLineaActual.getDevolucion(), null, Constantes.PDA);
		}
		model.addAttribute("listaBultos", listaBultos);
		model.addAttribute("centroParametrizado", centroParametrizado);
		model.addAttribute("devolucionFinalizada", devolucionFinalizada);
		return resultado;
	}

	//Función que vale para volver de corrección de stocks a la línea de devolución
	@RequestMapping(value = "/pdaP63StockLinkVuelta",method = RequestMethod.GET)
	public String pdaP63StockLinkVuelta(
			@Valid final Long codArt,
			@RequestParam(value = "flgBienGuardado", required = false, defaultValue = "N") String flgBienGuardado,
			@RequestParam(value = "flgOrigenBandejasKgs", required = false, defaultValue = "N") String flgOrigenBandejasKgs,
			@RequestParam(value = "origenPantalla", required = false, defaultValue = "") String origenPantalla,
			@RequestParam(value = "selectProv", required = false, defaultValue = "") String selectProv,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (selectProv == null || "".equals(selectProv) || " ".equals(selectProv)){
			origenPantalla = request.getParameter("origenPantalla");
			selectProv = request.getParameter("selProv");
		}

		//Redireccionaremos a la línea de devolución que nos encontrabamos
		String resultado = "pda_p63_realizarDevolucionOrdenDeRetirada";
		User user = (User) session.getAttribute("user");

		List<TDevolucionLinea> listTDevLinea = null;
		Devolucion devolucion = new Devolucion();

		List<TDevolucionLinea> listaAMostrar = null;
		TDevolucionLinea devolABuscar = null;

		int indice = 0;
		TDevolucionLinea devolucionStk = null;
		Double stockGuardado = null;

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
		// SE ACTIVA SOLO PARA LOS CENTROS: 5042-SEGOVIA, 0358-ABADIÑO, 0175-IRUÑA, 5326-INFANTE, 410 EIBAR, 164 GERNIKA
		//Si la referencia consultada es TEXTIL, se deben obtener todas las referencia que compartan el mismo código de proveedor.
		if (estructuraComercial.startsWith("03")) {  //Es un artÃ­culo de textil

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
				logger.error("########## CONTROLADOR: pdaP63RealizarDevolucionOrdenDeRetiradaController (pdaP63StockLinkVuelta  1)	 ####");
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
						logger.error("########## CONTROLADOR: pdaP63RealizarDevolucionOrdenDeRetiradaController (pdaP63StockLinkVuelta  2)	 ####");
						logger.error("###########################################################################################################");
					}
					basicoStockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
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
		//Obtención de datos del filtro
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

		//Paginación de devolución linea
		Page<TDevolucionLinea> pagDevolucionLineas = null;
		
		Long page = new Long(1);
		if(session.getAttribute("paginaActual") != "" && session.getAttribute("paginaActual") != null){
			page = new Long((session.getAttribute("paginaActual").toString())); //Recuperamos la página actual de pantalla.
		}
		Long numeroElementosPorPagina = new Long(Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA);

		//Obtención de la devolución actual
		Devolucion devol = (Devolucion)session.getAttribute("devolucion");
		String devolucionId = devol.getDevolucion().toString();

		//Mirar si se ha seleccionado el combo de ref permanentes. En caso de orden de retirada no existirá esa opcion en principio, por lo que se deja en null.
		String flagRefPermanentes = null;

		devolucion.setDevolucion(new Long(devolucionId));

		//Obtención de la devolución de la lista de devoluciones
		@SuppressWarnings("unchecked")
		List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
		devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
		devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);

		String proveedor = pdaDatosCabecera.getProveedor();

		if (origenPantalla != null
			&& (origenPantalla.toString().equals("pdaP104") || origenPantalla.toString().equals("pdaP105"))){
			if (selectProv != null && !selectProv.toString().equals("") && !selectProv.toString().equals(" ")){
				proveedor = selectProv.substring(0, selectProv.indexOf("-"));
			}
		}
		
		//Refrescamos la lista de devoluciones con las modificaciones correspondientes y con el nuevo filtro
		listTDevLinea = this.devolucionesService.findLineasDevolucion(session.getId(), devolucion, null, null, proveedor, flagRefPermanentes);			
		listTDevLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevLinea);
		if (codArtFiltro != null){				
			TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
			devolucionLineaComparator.setCodArticulo(codArtFiltro);

			page = new Long((listTDevLinea.indexOf(devolucionLineaComparator)/numeroElementosPorPagina)+1);		
		}
		int records = listTDevLinea.size();
		int desdeSubList = ((page.intValue()-1)*numeroElementosPorPagina.intValue());
		int hastaSubList = ((page.intValue())*numeroElementosPorPagina.intValue());

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
		listaAMostrar = new ArrayList<TDevolucionLinea>(listTDevLinea.subList(desdeSubList,hastaSubList));

		//Obtenemos la devolución de la lista en la que hemos pinchado en el link stock
		devolABuscar = new TDevolucionLinea();
		devolABuscar.setCodArticulo(codArt);

		indice = listaAMostrar.indexOf(devolABuscar);
		if (indice != -1){
			devolucionStk = listaAMostrar.get(indice);
		}

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
		
		if (devolucionStk != null && !devolucionStk.getStockActual().equals(stockGuardado)){ 
			//Creamos una lista para insertar la línea de devolución modificada
			List<DevolucionLineaModificada> devLineaLstModificada = new ArrayList<DevolucionLineaModificada>();

			//Creamos un objeto que guarde los cambios de las líneas de devolución
			DevolucionLineaModificada devolucionLineaActualizar = new DevolucionLineaModificada();

			//Insertamos en la línea el artículo al que corresponde esa línea, el estado modificado como código 9 y el nuevo stockActual
			devolucionLineaActualizar.setCodError(new Long(9));
			devolucionLineaActualizar.setCodArticulo(codArt);	
			devolucionLineaActualizar.setStockActual(stockGuardado);
			devolucionLineaActualizar.setStockDevuelto(devolucionStk.getStockDevuelto());
			devolucionLineaActualizar.setBulto(devolucionStk.getBulto());

			//Insertamos la línea de devolución modificada
			devLineaLstModificada.add(devolucionLineaActualizar);

			//Creamos dos nuevos objetos, para actualizar la devolución linea
			Devolucion devolucionActualizar = new Devolucion();
			devolucionActualizar.setDevolucion(devolucion.getDevolucion());	
			devolucionActualizar.setDevLineasModificadas(devLineaLstModificada);

			this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizar,false);

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
		}
		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();
		pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listaAMostrar, numeroElementosPorPagina.intValue(), records, page.intValue());	


		int lineasCompletadas = this.devolucionesService.findLineasDevolucionCompletadas(listTDevLinea);

		///////////////////////

		model.addAttribute("pdaDatosCab", pdaDatosCabecera);

		//Obtención de combo de proveedores
		model.addAttribute("proveedores", this.loadComboProveedor(devolucion, response, session));

		TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();

		//booleano que indica si mostrar link para caducidad
		boolean linkCaducidad = false;
		//booleano que indica si mostrar link para lote
		boolean linkLote = false;

		//Obtención de la devolución actual
		if(pagDevolucionLineas != null && pagDevolucionLineas.getRows() != null && pagDevolucionLineas.getRows().size() > 0){
			tDevolucionLineaActual = pagDevolucionLineas.getRows().get(0);

			//Para comparar en el jsp los códigos de error
			tDevolucionLineaActual.setCodErrorStr(tDevolucionLineaActual.getCodError().toString());

			//Control que calcula si se va a enseñar un link o no para mostrar la información de la caducidad o no al ser demasiado largo. En principio, si hay más de una
			//coma, significa que hay más de 2 fechas al separarse cada fecha con comas. Si hay más de una coma, se mostrará el link.
			if(tDevolucionLineaActual.getnCaducidad() != null){
				if(StringUtils.countMatches(tDevolucionLineaActual.getnCaducidad(), ",") > 1){
					linkCaducidad = true;
				}			
			}
			//Control que calcula si se va a enseñar un link o no para mostrar la información de la lote o no al ser demasiado largo. En principio, si hay más de 25 dígitos,
			//se mostrará un link.
			if(tDevolucionLineaActual.getnLote() != null){
				if(tDevolucionLineaActual.getnLote().length() > 25){
					linkLote = true;
				}
			}
		}else{
			tDevolucionLineaActual = new TDevolucionLinea();
			tDevolucionLineaActual.setDevolucion(new Long(devolucionId));
		}

		tDevolucionLineaActual.setDenomProveedor(pdaDatosCabecera.getProveedor());		

		//Insertamos la lista de bultos por proveedor
		tDevolucionLineaActual.setBultoPorProveedorLst(devol.getBultoPorProveedorLst());

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

		Map<String, Boolean> linkTextil = this.mostrarLinkTextil(tDevolucionLineaActual);
		
		//links caducidad y lote
		model.addAttribute("linkCaducidad", linkCaducidad);
		model.addAttribute("linkLote", linkLote);
		model.addAttribute("linkTextil", linkTextil);

		model.addAttribute("pagDevolucionLineas", pagDevolucionLineas);
		model.addAttribute("devolucionCabecera", devolucion);
		model.addAttribute("devolucionLinea", tDevolucionLineaActual);

		model.addAttribute("referenciaFiltro", pdaDatosCabecera.getCodArtCab());;
		model.addAttribute("proveedorFiltro", pdaDatosCabecera.getProveedor());
		model.addAttribute("completados", lineasCompletadas);

		//Se guarda el proveedor nada más devolver las líneas, para que a la hora de mirar si se han editado líneas, obtenga las líneas con el proveedor original y no el cambiado.
		//Y de este modo la lista antes de cambiar de proveedor coincida con la editada.
		model.addAttribute("proveedorAnterior", pdaDatosCabecera.getProveedor());

		String referenciaFiltroRellenaYCantidadEnter = (request.getParameter("referenciaFiltroRellenaYCantidadEnter") != null ? request.getParameter("referenciaFiltroRellenaYCantidadEnter") : "");
		model.addAttribute("referenciaFiltroRellenaYCantidadEnter", referenciaFiltroRellenaYCantidadEnter);

		//Ponemos si el link se ha guardado bien o no.
		model.addAttribute("flgBienGuardado", flgBienGuardado);

		model.addAttribute("origenPantalla", origenPantalla);
		model.addAttribute("selectProv", selectProv);

		String listaBultos="";
		User usuario= (User)session.getAttribute("user");
		boolean centroParametrizado = this.devolucionLineaBultoCantidadService.isCentroParametrizado(usuario.getCentro().getCodCentro(), Constantes.DEVOLUCIONES_PDA_PERMISO_27);
		String devolucionFinalizada = "";
		if(centroParametrizado){
			listaBultos=this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(),tDevolucionLineaActual);
			devolucionFinalizada=this.devolucionesService.esProveedorSinFinalizar(session.getId(),tDevolucionLineaActual.getDevolucion(),null, Constantes.PDA);
		}
		model.addAttribute("listaBultos", listaBultos);
		model.addAttribute("centroParametrizado", centroParametrizado);
		model.addAttribute("devolucionFinalizada", devolucionFinalizada);
		return resultado;
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
	
	@RequestMapping(value = "/pdaP63VariosBultosLinkVuelta",method = RequestMethod.GET)
	public String pdaP63VariosBultosLinkVuelta(
			@Valid final Long codArt,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		String resultado = "pda_p63_realizarDevolucionOrdenDeRetirada";

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
		list = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),list);

		if(codArt != null){				
			TDevolucionLinea devolucionLineaComparator = new TDevolucionLinea();
			devolucionLineaComparator.setCodArticulo(codArt);

			page = new Long(list.indexOf(devolucionLineaComparator))+1;		
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

		//Obtenemos las claves de los combos y les insetamos asteriscos.
		List<String>  listaBultoPorProveedorTodos= new ArrayList<String>(comboBox.keySet());
		List<String> listaBultoPorProveedorFiltrado = new ArrayList<String>();

		Locale locale = LocaleContextHolder.getLocale();
		String refPermanentes = this.messageSource.getMessage("pda_p62_refPermanentes", null,locale);

		for(String clave:listaBultoPorProveedorTodos){
			if(clave != "" && clave !=refPermanentes){
				clave = clave + "*";
				listaBultoPorProveedorFiltrado.add(clave);
			}
		}

		TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();

		//booleano que indica si mostrar link para caducidad
		boolean linkCaducidad = false;
		//booleano que indica si mostrar link para lote
		boolean linkLote = false;

		//Obtención de la devolución actual
		if(pagDevolucionLineas != null && pagDevolucionLineas.getRows() != null && pagDevolucionLineas.getRows().size() > 0){
			tDevolucionLineaActual = pagDevolucionLineas.getRows().get(0);

			//Para comparar en el jsp los códigos de error
			tDevolucionLineaActual.setCodErrorStr(tDevolucionLineaActual.getCodError().toString());

			//Control que calcula si se va a enseñar un link o no para mostrar la información de la caducidad o no al ser demasiado largo. En principio, si hay más de una
			//coma, significa que hay más de 2 fechas al separarse cada fecha con comas. Si hay más de una coma, se mostrará el link.
			if(tDevolucionLineaActual.getnCaducidad() != null){
				if(StringUtils.countMatches(tDevolucionLineaActual.getnCaducidad(), ",") > 1){
					linkCaducidad = true;
				}
			}
			//Control que calcula si se va a enseñar un link o no para mostrar la información de la lote o no al ser demasiado largo. En principio, si hay más de 25 dígitos,
			//se mostrará un link.
			if(tDevolucionLineaActual.getnLote() != null){
				if(tDevolucionLineaActual.getnLote().length() > 25){
					linkLote = true;
				}
			}
		}

		int lineasCompletadas = this.devolucionesService.findLineasDevolucionCompletadas(list);

		//Insertamos la lista de bultos por proveedor
		tDevolucionLineaActual.setBultoPorProveedorLst(listaBultoPorProveedorFiltrado);
		Map<String, Boolean> linkTextil = this.mostrarLinkTextil(tDevolucionLineaActual);

		//links caducidad y lote
		model.addAttribute("linkCaducidad", linkCaducidad);
		model.addAttribute("linkLote", linkLote);
		model.addAttribute("linkTextil", linkTextil);

		model.addAttribute("pagDevolucionLineas", pagDevolucionLineas);
		model.addAttribute("devolucionCabecera", devolucion);
		model.addAttribute("proveedorAnterior", "");
		

		model.addAttribute("devolucionLinea", tDevolucionLineaActual);
		model.addAttribute("completados", lineasCompletadas);
		model.addAttribute("referenciaFiltroRellenaYCantidadEnter", "");

		model.addAttribute("origenPantalla", origenPantalla);
		model.addAttribute("selectProv", selectProv);
		String listaBultos="";
		User usuario= (User)session.getAttribute("user");
		boolean centroParametrizado = this.devolucionLineaBultoCantidadService.isCentroParametrizado(usuario.getCentro().getCodCentro(), Constantes.DEVOLUCIONES_PDA_PERMISO_27);
		String devolucionFinalizada = "";
		if(centroParametrizado){
			listaBultos=this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(),tDevolucionLineaActual);
			devolucionFinalizada=this.devolucionesService.esProveedorSinFinalizar(session.getId(),tDevolucionLineaActual.getDevolucion(),null, Constantes.PDA);
		}
		model.addAttribute("listaBultos", listaBultos);
		model.addAttribute("centroParametrizado", centroParametrizado);
		model.addAttribute("devolucionFinalizada", devolucionFinalizada);
		return resultado;
	}
	
	private List<DevolucionLinea> rellenarEstructuraLineaBulto(List<DevolucionLinea> listTDevolucionesModificadas,TDevolucionLinea devolucionLinea){
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
			if(stockDevueltoPantalla!=null && bultoPantalla!=null){
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
