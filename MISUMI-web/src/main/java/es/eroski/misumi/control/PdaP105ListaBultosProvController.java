package es.eroski.misumi.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.annotation.Resource;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.BultoCantidad;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionEmail;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.Proveedor;
import es.eroski.misumi.model.TDevolucionBulto;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaSeleccionProveedor;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.DevolucionLineaBultoCantidadService;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
public class PdaP105ListaBultosProvController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(PdaP105ListaBultosProvController.class);

	@Autowired
	private DevolucionService devolucionesService;

	@Autowired
	private FotosReferenciaService fotosReferenciaService;

	@Autowired
	private DevolucionLineaBultoCantidadService devLineaBultoCantService;

	@Resource
	private MessageSource messageSource;

	@RequestMapping(value = "/pdaP105ListaBultosProv",method = RequestMethod.GET)
	public String showForm(ModelMap model
						  , @RequestParam(value="devolucion") String devolucionId
//						  , @RequestParam(value="proveedor") String proveedorId
						  , @RequestParam(value="tipoDevolucion") String tipoDevolucion
						  , @RequestParam(value="selectProv") String descProveedor
//						  , @RequestParam(value="mostrarFinDev") String mostrarFinDev
						  , HttpSession session, HttpServletRequest request
						  , HttpServletResponse response) throws Exception {
		
		String resultado = "pda_p105_listaBultosProv";
		String codProveedor = null;
		String descProv = "";

		boolean redireccionadoAP105 = false;
		
//		List<TDevolucionLinea> listTDevolucionLinea = null;
//
//		//Paginación de devolución linea
//		Page<TDevolucionLinea> pagDevolucionLineas = null;	        
//		int primerElementoAMostrar = 0;
//		int paginaPrimerElementoAMostrar = (primerElementoAMostrar / Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA) + 1;
//		int indiceInicioPaginaPrimerElementoAMostrar = (paginaPrimerElementoAMostrar-1) * Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA;
//		int indiceFinPaginaPrimerElementoAMostrar = indiceInicioPaginaPrimerElementoAMostrar + Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA;

		// Llamada para conseguir las devoluciones. 
		Devolucion devolucion = new Devolucion();
		devolucion.setDevolucion(new Long(devolucionId));

		//Obtención de la devolución de la lista de devoluciones
		@SuppressWarnings("unchecked")
		List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
		devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
		devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);
		
//		devolucion.setTipoDevolucion(tipoDevolucion);

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

						// Insertar en la tabla temporal T_DEVOLUCIONES
						// A partir de la lista obtenida tenemos que insertar en la
						// tabla temporal los registros obtenidos,
						// borrando previamente los posibles registros almacenados.
						this.eliminarTablaSesion(session.getId());

						//Se inserta en una tabla temporal cada línea de devolución. Se devuelve una lista de TDevolucionLinea tratada
						//a partir de la lista obtenida en el PLSQL. Así evitamos tirar de la tabla temporal al ya tener los datos.
						this.insertarTablaSesionLineaDevolucion(listDevolucionLinea, session.getId(),devolucionSeleccion.getDevolucion());																       		

						//Obtenemos la lista ordenada de la tabla temporal para mostrar en el grid
//						listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion,null,null,null,null);						
//						listTDevolucionLinea = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLinea);
//						PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();
//
//						//El número de registros será el de la lista obtenida.
//						int records = listDevolucionLinea.size();
//
//						if(records < Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA){
//							pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listTDevolucionLinea,Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA, records, 1);
//						}else{
//							//Buscamos el primer elemento de la lista que tenga stockDevuelto null o bulto null
//							primerElementoAMostrar = this.devolucionesService.firstLineaDevolucionStockOrBultoNull(null, listTDevolucionLinea);
//							paginaPrimerElementoAMostrar = (primerElementoAMostrar / Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA) + 1;
//							indiceInicioPaginaPrimerElementoAMostrar = (paginaPrimerElementoAMostrar-1) * Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA;
//							indiceFinPaginaPrimerElementoAMostrar = indiceInicioPaginaPrimerElementoAMostrar + Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA;
//							pagDevolucionLineas = paginationManager.paginate(new Page<TDevolucionLinea>(), listTDevolucionLinea.subList(indiceInicioPaginaPrimerElementoAMostrar, indiceFinPaginaPrimerElementoAMostrar), Constantes.DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA, records, paginaPrimerElementoAMostrar);
//						}
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

//		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());

		if (descProveedor != null && descProveedor != "" && descProveedor.contains("-")){
			
			codProveedor = descProveedor.substring(0, descProveedor.indexOf("-")); // Código del proveedor.
			descProv = descProveedor.substring(descProveedor.indexOf("-")+1); // Descripción del proveedor.

			Proveedor nuevoProveedor = devLineaBultoCantService.cargarListaBultos(session.getId(), devolucionId.toString(), new Proveedor(codProveedor, descProv), codProveedor, null);

			if (nuevoProveedor.getListaBultos().isEmpty()){
				model.addAttribute("mensajeError", Constantes.PROV_SIN_BULTOS);					
			}

			//Inicialmente se pagina para mostrar los elementos de la primera página.
			Page<BultoCantidad> pagBultos = this.paginarListaBultos(session, nuevoProveedor.getListaBultos(), "1", "2", "firstBulto");
			
			model.addAttribute("pagBultos", pagBultos);
			
			model.addAttribute("existeBulto", false);
			if (pagBultos != null){
				model.addAttribute("existeBulto", true);
			}

			// si se recuperan bultos --> Invocar a la paginación
			if (nuevoProveedor.getListaBultos() != null && !nuevoProveedor.getListaBultos().isEmpty()){
				redireccionadoAP105 = true;
			}
			
//			model.addAttribute("proveedor", codProveedor);
			
			model.addAttribute("descProveedor", descProveedor.subSequence(0, descProveedor.length()<15?descProveedor.length():15));
//			model.addAttribute("mostrarFinDev", mostrarFinDev);
			model.addAttribute("mostrarFinDev", devolucionesService.esProveedorSinFinalizar(session.getId(), devolucion.getDevolucion(), null, Constantes.PDA));		
			model.addAttribute("actionP61Volver","pdaP105ListaBultosProv.do");
			model.addAttribute("origenPantalla", "pdaP104");
		}else{
			model.addAttribute("actionP61Volver","pdaP104ListaProveedores.do");
			model.addAttribute("origenPantalla", "pdaP104");
		}

		PdaSeleccionProveedor pdaSeleccionProveedor = new PdaSeleccionProveedor();
		model.addAttribute("pdaSeleccionProveedor", pdaSeleccionProveedor);

//		TDevolucionLinea tDevolucionLineaActual = new TDevolucionLinea();

//		int lineasCompletadas = this.devolucionesService.findLineasDevolucionCompletadas(listTDevolucionLinea);

		model.addAttribute("existeBulto", redireccionadoAP105);

		//Insertamos la lista de bultos por proveedor
//		tDevolucionLineaActual.setBultoPorProveedorLst(listaBultoPorProveedorFiltrado);

//		model.addAttribute("pagDevolucionLineas", pagDevolucionLineas);
		model.addAttribute("devolucionCabecera", devolucion);
		model.addAttribute("proveedorAnterior", "");
		
//		model.addAttribute("devolucionLinea", tDevolucionLineaActual);
//		model.addAttribute("completados", lineasCompletadas);
//		model.addAttribute("referenciaFiltroRellenaYCantidadEnter", "");

		return resultado;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/pdaP105ListaBultosProv",method = RequestMethod.POST)
	public String processForm(@Valid final PdaSeleccionProveedor pdaSeleccionBultosProv
							 , ModelMap model
							 , HttpSession session, HttpServletRequest request
							 , RedirectAttributes redirectAttributes
							 , HttpServletResponse response) throws Exception {

		List<TDevolucionLinea> listTDevolucionLinea = null;
		String resultado = "pda_p105_listaBultosProv";
		String accion = request.getParameter("accion");

		User user = (User) session.getAttribute("user");

		Long page = new Long(1);
		
//		DevolucionCatalogoEstado devol = new DevolucionCatalogoEstado();
//		devol = (DevolucionCatalogoEstado) session.getAttribute("devCatalogoEstado");

		if(request.getParameter("paginaActual") != "" && request.getParameter("paginaActual") != null){
			page = new Long((request.getParameter("paginaActual"))); //Recuperamos la página actual de pantalla.
		}

		//Obtenemos la página de antes de paginar, para más tarde buscar la línea de devolución antes de paginar y en el caso de haber cambios en su bulto o cantidad actualizar la tabla temporal.
		Long paginaAntesDePaginar = new Long(page);
		
		//Obtención de la devolución actual
		Devolucion devolucion = new Devolucion();
		Long devolucionId = new Long(request.getParameter("devolucion"));
		String codProveedor = null;
		String descProveedor = "";
		String descProv = "";

		if (devolucionId == null){
			devolucionId = pdaSeleccionBultosProv.getDevolucion();
		}
		
		devolucion.setDevolucion(devolucionId);
		
		//Obtención de la devolución de la lista de devoluciones
		@SuppressWarnings("unchecked")
		List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
		devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
		devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);

		try{

			if (Constantes.DEVOLUCIONES_PDA_ACCION_FIN_CAMPANA.equals(accion) || Constantes.DEVOLUCIONES_PDA_ACCION_ORDEN_RETIRADA.equals(accion)){
				
				//Obtención de la devolución de la lista de devoluciones
				model.addAttribute("devolucion", devolucion);
				model.addAttribute("actionP61Volver", "pdaP105ListaBultosProv.do");
				model.addAttribute("origenPantalla", "pdaP105");
				model.addAttribute("selectProv", request.getParameter("selProv"));

				if (Constantes.DEVOLUCIONES_PDA_ACCION_FIN_CAMPANA.equals(accion)){
					
					model.addAttribute("tieneFoto", false);
					
					//Si existe el permiso de las fotos, mirar si la devolución a mostrar tiene una foto asignada, como la paginación será de 1 en 1, sabemos que la lista
					//tDevolucionLineaActualLst tendrá solo un elemento, por lo que tenemos que buscar la foto de ese elemento.
					if (null != user.getCentro() && "S".equals(user.getCentro().getControlOpciones())
							&& !user.getCentro().getOpcHabil().isEmpty()
							&& user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_FOTO_27) != -1) {
						//Comprobar si tiene Foto
						FotosReferencia fotosReferencia = new FotosReferencia();
	
						//Como la lista a mostrar tiene solo un artículo, obtenemos el valor del elemento 0.
						fotosReferencia.setCodReferencia(devolucion.getCodArticulo());
	
						//Miramos si existe la foto y en caso afirmativo, buscamos su path.
						if (fotosReferenciaService.checkImage(fotosReferencia)){
							model.addAttribute("tieneFoto",true);
						}
					}
				}
				
				return "pda_p61_realizarDevolucionCabecera";

			} else if (Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR.equals(accion)){

				//Obtenemos la lista ordenada de la tabla temporal para mostrar en el grid
				listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion,null,null,null,null);						
				listTDevolucionLinea = this.devLineaBultoCantService.cargarBultoCantidadLinea(session.getId(),listTDevolucionLinea);

				// MISUMI-114 ampliacion
				// Validar la devolución por su coste maximo
				// si el coste maximo esta definido, y la suma del coste de devolucion supera el 101% del maximo no dejamos guardar
				if(devolucion.getCosteMaximo()!=null && devolucion.getCosteMaximo() > 0L){
					Double sumaEuros = this.getCalcularSumaEuros(listTDevolucionLinea);
					Double cotaMaxima = devolucion.getCosteMaximo() * 1.01;
					if(sumaEuros.compareTo(cotaMaxima) > 0){
						String err = "ERR_COSTE";
						model.addAttribute("actionP67Volver","pdaP62RealizarDevolucionFinCampania.do");
	
						model.addAttribute("codErrorActualizar",err);
						resultado = "pda_p67_errorDevolucionLinea";
					}
				}
				
				// Redirige a la pantalla de pregunta de si se quiere o no finalizar la devolución
				// o a la que completa el dato de RMA
				boolean requiereRma = Constantes.TIPO_RMA_TIENDA.equals(devolucion.getTipoRMA())
						&& (devolucion.getCodRMA()==null || devolucion.getCodRMA().isEmpty());
	
				model.addAttribute("devolucion", devolucion);
				model.addAttribute("paginaActual", paginaAntesDePaginar.intValue());
				model.addAttribute("referenciaFiltro", (request.getParameter("referenciaFiltro") != null ? request.getParameter("referenciaFiltro") : ""));
				model.addAttribute("proveedorFiltro", (request.getParameter("proveedorFiltro") != null ? request.getParameter("proveedorFiltro") : ""));
	
				// Si hay que informar el RMA, para ello vamos a una pantalla, sino a confirmar con pregunta de finalización.
				if(requiereRma) {
					model.addAttribute("actionP80","pdaP63RealizarDevolucionOrdenDeRetirada.do");
					return "pda_p80_pedirRmaDevolucion";
				}else{
					String existenStockDevueltosConCero = devolucionesService.existeStockDevueltoConCeroTablaSesionLineaDevolucion(session.getId(),devolucion);
					
					model.addAttribute("existenStockDevueltosConCero",existenStockDevueltosConCero);
					model.addAttribute("actionP68","pdaP104ListaProveedores.do");
					model.addAttribute("origenPantalla", "pdaP104");
					return "pda_p68_msgFinalizarSiNo";
				}

			} else if (Constantes.DEVOLUCIONES_PDA_ACCION_FINALIZAR_POPUP.equals(accion)){
				
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
					//Si da otro tipo de error, va a una pantalla que permite ver el error.
					} else {
						//Si hay error después de actualizar con ceros las cantidades vacías y bultos vacíos, actualizamos la tabla temporal con los ceros para que los datos sean los reales.
						if((Constantes.CON_DATOS_BULTO).equals(existenStockDevueltosConCero)){
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

			} else if (Constantes.DEVOLUCIONES_PDA_ACCION_CARGAR_BULTOS.equals(accion)){
				
				descProveedor = request.getParameter("descProveedor");

				if (descProveedor.contains("-")){
					codProveedor = descProveedor.substring(0, descProveedor.indexOf("-")); // Código del proveedor.
					descProv = descProveedor.substring(descProveedor.indexOf("-")+1); // Descripción del proveedor.
				}
				
				Proveedor nuevoProveedor = devLineaBultoCantService.cargarListaBultos(session.getId(), devolucionId.toString(), new Proveedor(codProveedor,descProv), codProveedor, null);

				if (nuevoProveedor.getListaBultos().isEmpty()){
					model.addAttribute("mensajeError", Constantes.PROV_SIN_BULTOS);					
				}
				
				//Inicialmente se pagina para mostrar los elementos de la primera página.
				Page<BultoCantidad> pagBultos = this.paginarListaBultos(session, nuevoProveedor.getListaBultos(), "1", "2", "firstBulto");
				
				model.addAttribute("pagBultos", pagBultos);

				model.addAttribute("existeBulto", false);
				if (pagBultos != null){
					model.addAttribute("existeBulto", true);
				}

				return resultado;
				
			// Con cualquiera de las dos opciones que se elija, se muestra la ventana de comprobación SI/NO. 
			}else if (Constantes.ABRIR_BULTO.equals(accion) || Constantes.BORRAR_BULTO.equals(accion)){

				if (Constantes.ABRIR_BULTO.equals(accion)){
					model.addAttribute("accionBulto","AbrirBulto");
				}else{
					model.addAttribute("accionBulto","BorrarBulto");
				}
				model.addAttribute("actionP105","pdaP105ListaBultosProv.do");
				model.addAttribute("devolucion", request.getParameter("devolucion"));
				model.addAttribute("bulto", request.getParameter("bulto"));
				model.addAttribute("origenPantalla", "pdaP105");
				model.addAttribute("selectProv", request.getParameter("selProv"));
				
				return "pda_p105_msgAccionSiNo";
				
			}else if (Constantes.ACCION_ABRIR_BULTO.equals(accion) || Constantes.ACCION_BORRAR_BULTO.equals(accion)){

				Long numBulto = new Long(request.getParameter("bulto"));
				BultoCantidad bulto = new BultoCantidad();
				bulto.setBulto(numBulto);

				descProveedor = request.getParameter("selProv");
				
				if (descProveedor.contains("-")){
					codProveedor = descProveedor.substring(0, descProveedor.indexOf("-")); // Código del proveedor.
					descProv = descProveedor.substring(descProveedor.indexOf("-")+1); // Descripción del proveedor.
				}

				//Cuando se pulsa finalizar sale un mensaje informando al usario de si está seguro de si desea finalizar la devolución. Si pulsa si, ejecuta lo siguiente.
				if(request.getParameter("accion_si") != null){					

					String accionAbrirBorrar = Constantes.BORRAR_BULTO;

					if (Constantes.ACCION_ABRIR_BULTO.equals(accion)){
						accionAbrirBorrar = Constantes.ABRIR_BULTO;
					}
					
					try{
						bulto = devLineaBultoCantService.procedimientoActualizarEstadoBultoPorProveedor( accionAbrirBorrar, devolucionId
								 , new Long(codProveedor), null /*provrTrabajo*/, numBulto);

						// Si la operación en SIA se ha realizado correctamente, actuaremos sobre las tablas intermedias según corresponda.
						if (bulto.getCodError() != null && bulto.getCodError() == 0){
//							TDevolucionLinea nuevoRegistroTDevLinea = new TDevolucionLinea();
//							TDevolucionBulto nuevoRegistroTDevBulto = new TDevolucionBulto();
//							int idxNuevaTDevLinea = 0;
//							int idxNuevaTDevBulto = 0;
//							
//							nuevoRegistroTDevLinea.setCodArticulo(devolucion.getCodArticulo());
//							nuevoRegistroTDevBulto.setCodArticulo(devolucion.getCodArticulo());
//							idxNuevaTDevLinea=devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).gettDevLineasLst().indexOf(nuevoRegistroTDevLinea);
//							idxNuevaTDevBulto=devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).gettDevLineasLst().get(idxNuevaTDevLinea).getListTDevolucionLinea().indexOf(nuevoRegistroTDevBulto);

							if (Constantes.ACCION_ABRIR_BULTO.equals(accion)){
								// Actualizar el estado en la tabla T_MIS_DEVOLUCIONES_BULTO.
								devLineaBultoCantService.actualizarEstadoBultoPorProveedor(session.getId(), Constantes.ESTADO_ABIERTO, devolucionId, new Long(codProveedor), null, numBulto);

								// Actualiza la lista de Bultos en Sesion.
//								devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).gettDevLineasLst().get(idxNuevaTDevLinea).getListTDevolucionLinea().get(idxNuevaTDevBulto).setEstadoCerrado(Constantes.ESTADO_ABIERTO);
							}else{
								// Borrar el bulto en la tabla T_MIS_DEVOLUCIONES_BULTO
								devLineaBultoCantService.deleteBultoPorProvDev(session.getId(), devolucionId.toString(), codProveedor, null, request.getParameter("bulto"));
								// BORRO el bulto de la lista de Bultos en Sesion.
//								devol.getListDevolucionEstado().get(0).getListDevolucion().get(0).gettDevLineasLst().get(idxNuevaTDevLinea).getListTDevolucionLinea().remove(idxNuevaTDevBulto);
							}
						}

						model.addAttribute("mensajeError", bulto.getDescError()); // Devuelvo a pantalla el mensaje de ERROR de SIA.

					}catch(SQLException sqlEx){
						logger.error("Error llamada a SIA: "+StackTraceManager.getStackTrace(sqlEx));
						throw sqlEx;
					}
					
				}
				
				Proveedor nuevoProveedor = devLineaBultoCantService.cargarListaBultos(session.getId(), devolucionId.toString(), new Proveedor(codProveedor,descProv), codProveedor, null);

				if (nuevoProveedor.getListaBultos().isEmpty()){
					model.addAttribute("mensajeError", Constantes.PROV_SIN_BULTOS);					
				}
				
				//Inicialmente se pagina para mostrar los elementos de la primera página.
				Page<BultoCantidad> pagBultos = this.paginarListaBultos(session, nuevoProveedor.getListaBultos(), "1", "2", "firstBulto");
				
				model.addAttribute("pagBultos", pagBultos);

				if (pagBultos != null){
					model.addAttribute("existeBulto", true);
				}else{
					model.addAttribute("existeBulto", false);
				}

				model.addAttribute("descProveedor", descProveedor.subSequence(0, descProveedor.length()<15?descProveedor.length():15));
				model.addAttribute("devolucionCabecera", devolucion);
				model.addAttribute("origenPantalla", "pdaP104");
				model.addAttribute("selectProv", request.getParameter("selProv"));

				return resultado;
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		descProveedor = request.getParameter("descProveedor");
		
		if (descProveedor == null || descProveedor == ""){
			descProveedor = request.getParameter("selProv");
		}

		if (descProveedor.contains("-")){
			codProveedor = descProveedor.substring(0, descProveedor.indexOf("-")); // Código del proveedor.
			descProv = descProveedor.substring(descProveedor.indexOf("-")+1); // Descripción del proveedor.
		}
		
		Proveedor nuevoProveedor = devLineaBultoCantService.cargarListaBultos(session.getId(), devolucionId.toString(), new Proveedor(codProveedor,descProv), codProveedor, null);

		//Inicialmente se pagina para mostrar los elementos de la primera página.
		Page<BultoCantidad> pagBultos = this.paginarListaBultos(session, nuevoProveedor.getListaBultos(), "1", "2", "firstBulto");

		model.addAttribute("pagBultos", pagBultos);

		if (pagBultos != null){
			model.addAttribute("existeBulto", true);
		}else{
			model.addAttribute("existeBulto", false);
		}

		model.addAttribute("descProveedor", descProveedor.subSequence(0, descProveedor.length()<15?descProveedor.length():15));
		model.addAttribute("devolucionCabecera", devolucion);
		model.addAttribute("mostrarFinDev", devolucionesService.esProveedorSinFinalizar(session.getId(), devolucion.getDevolucion(), null, Constantes.PDA));		
		model.addAttribute("origenPantalla", "pdaP104");

//		session.setAttribute("devCatalogoEstado", devol);

		return resultado;
	}

	@RequestMapping(value = "/pdaP105Paginar", method = RequestMethod.GET)
	public String pdaP105Paginar(ModelMap model
								, HttpSession session
//								, @Valid final String codArtCab
								, @Valid final String pgBulto
								, @Valid final String pgTotBulto
								, @Valid final String botPag
								, @Valid final String devolucion
								, @Valid final String descProveedor
								, @Valid final String tituloDevol
								, @Valid final String mostrarFinDev
								) {

		String resultado = "pda_p105_listaBultosProv";

		Long devolucionId = new Long(devolucion);
		String codProveedor = null;
		String descProv = "";
		
		try{
			//Si se redirecciona a P104, significa que ha encontrado Bultos, por lo que hay que enseñarlos.
			boolean redireccionadoAP104 = true;

			if (descProveedor.contains("-")){
				codProveedor = descProveedor.substring(0, descProveedor.indexOf("-")); // Código del proveedor.
				descProv = descProveedor.substring(descProveedor.indexOf("-")+1); // Descripción del proveedor.
			}
			
			Proveedor nuevoProveedor = devLineaBultoCantService.cargarListaBultos(session.getId(), devolucionId.toString(), new Proveedor(codProveedor,descProv), codProveedor, null);

			//Inicialmente se pagina para mostrar los elementos de la primera página.
			Page<BultoCantidad> pagBultos = this.paginarListaBultos(session, nuevoProveedor.getListaBultos(), pgBulto, pgTotBulto, botPag);
			
			model.addAttribute("pagBultos", pagBultos);

			if (pagBultos != null){
				model.addAttribute("existeBulto", redireccionadoAP104);
			}else{
				model.addAttribute("existeBulto", false);
			}
			
			PdaSeleccionProveedor pdaSeleccionProveedor = new PdaSeleccionProveedor();
			model.addAttribute("pdaSeleccionProveedor", pdaSeleccionProveedor);

			// Llamada para conseguir las devoluciones. 
			Devolucion devol = new Devolucion();
			if (devolucion != null && devolucion != ""){
				devol.setDevolucion(new Long(devolucion));
			}
			
			devol.setTitulo1(tituloDevol);

			model.addAttribute("descProveedor", descProveedor.subSequence(0, descProveedor.length()<15?descProveedor.length():15));
			model.addAttribute("devolucionCabecera", devol);
			model.addAttribute("origenPantalla", "pdaP104");
//			model.addAttribute("mostrarFinDev", mostrarFinDev);
			model.addAttribute("mostrarFinDev", devolucionesService.esProveedorSinFinalizar(session.getId(), new Long(devolucion), null, Constantes.PDA));		
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultado;
	}
	
	//Elimina la tabla temporal histórica de todos los usuarios si ha pasado más de un día desde
	//la creación de los registros
	private void eliminarTablaSesionHistorico(){		
		try {
			this.devolucionesService.deleteHistorico();
			this.devLineaBultoCantService.deleteHistorico();
		} catch (Exception e) {
			logger.error("eliminarTablaSesionHistorico="+e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Elimina los datos de sesión de la tabla temporal T_MIS_DEVOLUCIONES_BULTO de ese usuario
	 * @param idSesion
	 */
	private void eliminarTablaSesion(String idSesion){		
		TDevolucionLinea registro = new TDevolucionLinea();		
		registro.setIdSesion(idSesion);

		try {
			this.devolucionesService.delete(registro);
			this.devLineaBultoCantService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
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
				this.devLineaBultoCantService.insertAll(listTDevolucionLineaBultoCantidad);
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

	private Page<BultoCantidad> paginarListaBultos(HttpSession session, List<BultoCantidad> listaBultos, String pgProv, String pgTotProv, String botPag) throws Exception{

		Page<BultoCantidad> result = null;
		List<BultoCantidad> nuevaListaBultos = listaBultos;
		int records = 0;
		int page = 1;

		if (listaBultos!= null && listaBultos.size()>0) {

			records = listaBultos.size(); // Tamaño de la lista de proveedores.
			page = Integer.parseInt(pgProv);
			int pageTot = Integer.parseInt(pgTotProv);

			if (botPag.equals("firstBulto")){
				page = 1;
			}else if (botPag.equals("prevBulto")){
				page--;
			}else if (botPag.equals("nextBulto")){
				page++;
			}else if (botPag.equals("lastBulto")){
				page = pageTot;
			}

			if (listaBultos.size()>Constantes.PROVEEDORES_PDA_PAGS_MAX){
				nuevaListaBultos = this.obtenerSubListaBulto(listaBultos, page, pageTot, records);
			}
		}

		if (nuevaListaBultos != null && nuevaListaBultos.size()>0) {
			PaginationManager<BultoCantidad> paginationManager = new PaginationManagerImpl<BultoCantidad>();
			result = paginationManager.paginate(new Page<BultoCantidad>(), nuevaListaBultos, Constantes.PROVEEDORES_PDA_PAGS_MAX, records, page);	
		} else {
			return new Page<BultoCantidad>();
		}

		return result;
	}
	
	private List<BultoCantidad> obtenerSubListaBulto(List<BultoCantidad> lista, int pag, int pagTot, int records) throws Exception{

		List<BultoCantidad> result = null;

		int inicio = Constantes.PROVEEDORES_PDA_PAGS_MAX*(pag-1);

		int fin = 0;	
		if (pag == pagTot){
			fin = records;
		}else{
			fin = Constantes.PROVEEDORES_PDA_PAGS_MAX*pag;

			//Si el registro final calculado es mayor que el número de registros se corrige para hacerlo coincidir con el 
			//registro final
			if (fin>records){
				fin = records;
			}
		}

		result = lista.subList(inicio, fin);

		return result;
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

}