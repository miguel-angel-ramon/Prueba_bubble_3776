package es.eroski.misumi.control;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.Articulo;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.CentroAutoservicio;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionActualizada;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.DevolucionLineaModificada;
import es.eroski.misumi.model.DevolucionPlataforma;
import es.eroski.misumi.model.DevolucionTipos;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.HistoricoUnidadesVenta;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VFacingX;
import es.eroski.misumi.model.VMapaAprov;
import es.eroski.misumi.model.ValoresStock;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.model.ventasTiendaWS.ReferenciaRespuetaType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.HistoricoUnidadesVentaService;
import es.eroski.misumi.service.iface.HistoricoVentaMediaService;
import es.eroski.misumi.service.iface.MotivoTengoMuchoPocoService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.ParamCentrosVpService;
import es.eroski.misumi.service.iface.PlanogramaVigenteService;
import es.eroski.misumi.service.iface.RefAsociadasService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockFinalMinimoService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VAgruComerParamSfmcapService;
import es.eroski.misumi.service.iface.VCentrosPlataformasService;
import es.eroski.misumi.service.iface.VCentrosUsuariosService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VFacingXService;
import es.eroski.misumi.service.iface.VMapaAprovService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VentasTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/devoluciones/popupCrearDevolucion")
public class p86PopUpCrearDevolucionController {

	private static Logger logger = Logger.getLogger(p86PopUpCrearDevolucionController.class);

	@Autowired
	private VCentrosUsuariosService vCentrosUsuariosService;

	@Autowired
	private VFacingXService vFacingXService;

	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private MotivoTengoMuchoPocoService motivoTengoMuchoPocoService;

	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;

	@Autowired
	private HistoricoVentaMediaService historicoVentaMediaService;

	@Autowired
	private VMapaAprovService vMapaAprovService;

	@Autowired
	private RelacionArticuloService relacionArticuloService;

	@Autowired
	private VentasTiendaService ventasTiendaService;

	@Autowired
	private RefAsociadasService refAsociadasService;

	@Autowired
	private StockTiendaService stockTiendaService;

	@Autowired
	private HistoricoUnidadesVentaService historicoUnidadesVentaService;

	@Autowired
	private VAgruComerParamSfmcapService vAgruComerParamSfmcapService;

	@Autowired
	private StockFinalMinimoService stockFinalMinimoService;

	@Autowired
	private ParamCentrosVpService paramCentrosVpService;

	@Autowired
	private PlanogramaVigenteService planogramaVigenteService;

	@Autowired
	private DevolucionService devolucionesService;

	@Autowired
	private FotosReferenciaService fotosReferenciaService;

	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;

	@RequestMapping(value="/cargaComboCrearDevolucion",method = RequestMethod.POST)
	public @ResponseBody Centro cargaComboCrearDevolucion(@RequestBody Centro centro,
			HttpSession session,HttpServletResponse response) throws Exception {

		User user = (User) session.getAttribute("user");
		List<Centro> centroNegocioLst = vCentrosUsuariosService.findAll(centro,user.getCode());
		Centro centroNegocio = null;
		if(centroNegocioLst != null && centroNegocioLst.size() > 0){
			centroNegocio = centroNegocioLst.get(0);
		}
		return centroNegocio;
	}

	@RequestMapping(value="/obtenerDatosCombo",method = RequestMethod.POST)
	public @ResponseBody DevolucionTipos obtenerDatosCombo(@RequestBody Centro centro,
			HttpSession session,HttpServletResponse response) throws Exception {

		return devolucionesService.obtenerDatosCombo(centro);
	}

	@RequestMapping(value="/cargarStockActualYDenom",method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentro cargarStockActualYDenom(@RequestBody ReferenciasCentro referenciasCentro,
			HttpSession session,HttpServletResponse response) throws Exception {

		referenciasCentro.setDiarioArt(obtenerDiarioArt(referenciasCentro));
		referenciasCentro.setValoresStock(this.obtenerValoresStock(referenciasCentro, session));

		return referenciasCentro;			
	}

	@RequestMapping(value = "/addRowToDataGrid", method = RequestMethod.POST)
	public @ResponseBody Page<TDevolucionLinea> addRowToDataGrid(
			@RequestBody DevolucionLinea devolucionLinea,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Insertamos la fila creada en la tabla temporal. Los códigos de devolucion para las creadas serán 0.
		this.insertarTablaSesionLineaDevolucion(devolucionLinea, session.getId(),new Long("0"));

		//Obtenemos la tabla.
		Devolucion dev = new Devolucion();
		dev.setDevolucion(new Long("0"));
		return loadDataGridRecarga(dev,new Long("1"),new Long("10"),null,null,response,session);							

	}

	//Es la recarga del grid, al paginar o reordenar el grid
	@RequestMapping(value = "/loadDataGridRecarga", method = RequestMethod.POST)
	public  @ResponseBody Page<TDevolucionLinea> loadDataGridRecarga(
			@RequestBody Devolucion devolucion,
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
		List<TDevolucionLinea> list = null;

		try {

			//Si hay cambios actualizar tabla temporal
			if(devolucion.getDevLineasModificadas() != null && devolucion.getDevLineasModificadas().size() > 0){
				this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucion,false);
			}

			//Control del provvedor para ver si se elige REFERENCIAS PERMANENTES
			if (Constantes.PROVEEDOR_REF_PERMANENTES.equals(devolucion.getProveedor())){
				devolucion.setProveedor(null);
				devolucion.setFlagRefPermanentes("S");
			}

			list = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion, pagination,null,devolucion.getProveedor(),devolucion.getFlagRefPermanentes());
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<TDevolucionLinea> result = null;

		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

		if (list != null && list.size() > 0) {
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

			//Si vamos a la 1 página, el primer elemento guardará un 1 en caso de ser vacío y al clicarlo. En caso
			//contrario, guardará el bulto actual del elemento de la página anterior.
			if(page.intValue() == 1){
				list.get(desdeSubList).setPrimerElementoBulto(new Long(1));
				list.get(desdeSubList).setPrimerElementoStockDevuelto(new Double(1.00));
			}else{
				list.get(desdeSubList).setPrimerElementoBulto(list.get(desdeSubList-1).getBulto());
				list.get(desdeSubList).setPrimerElementoStockDevuelto(list.get(desdeSubList-1).getStockDevuelto());
			}

			List<TDevolucionLinea> listaAMostrar = list.subList(desdeSubList,hastaSubList);
			refTieneFoto(listaAMostrar);

			result = paginationManager.paginate(new Page<TDevolucionLinea>(), listaAMostrar,max.intValue(), records, page.intValue());	
		} else {
			return new Page<TDevolucionLinea>();
		}
		return result;
	}

	//Es la recarga del grid, al paginar o reordenar el grid
	@RequestMapping(value = "/checkIfRefExistsInGrid", method = RequestMethod.POST)
	public  @ResponseBody String loadDataGridRecarga(
			@RequestBody DevolucionLinea devolucionLinea,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		String existeRefEnDevol = null;

		//Buscamos la línea de devolución. Si existe devuelve true y si no false.
		existeRefEnDevol = this.devolucionesService.existeReferenciaEnDevolucion(session.getId(), devolucionLinea);

		return existeRefEnDevol;
	}

	//Se eliminan los datos del grid según sesión o fecha de caducidad.
	@RequestMapping(value = "/eliminarTablaSesionGrid", method = RequestMethod.POST)
	public  @ResponseBody boolean eliminarTablaSesionGrid(
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Eliminar historico del grid de distintas sesiones.
		boolean eliminarHistoricoSesiones = this.eliminarTablaSesionHistorico();

		//Eliminar datos del grid relacionados con la sesión.
		boolean eliminarHistoricos = this.eliminarTablaSesion(session.getId());

		return (eliminarHistoricoSesiones && eliminarHistoricos);
	}

	//Sirve para guardar los valores de las líneas de devolución modificadas
	@RequestMapping(value = "/saveDataGrid", method = RequestMethod.POST)
	public  @ResponseBody DevolucionActualizada loadComboProveedor(
			@RequestBody Devolucion devolucion,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		Pagination pagination= new Pagination(max,page);
		int countGuardados = 0;
		int countError = 0;

		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}

		List<TDevolucionLinea> list = null;
		DevolucionCatalogoEstado devolucionCatalogoEstadoActualizada = null;

		//Si hay cambios, actualizar tabla temporal y poner los registros como modificados.Número 9.
		if(devolucion.getDevLineasModificadas() != null && devolucion.getDevLineasModificadas().size() > 0){
			this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucion,false);
		}
		//Una vez tenemos la tabla temporal actualizada, obtenemos todos los elementos. Todos van a ser elementos a guardar, ya que es una devolución nueva.
		List<TDevolucionLinea> listTLineasDevolucion = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion,null,null,null,null);							

		//Obtenemos las líneas de devolución creadas de la tabla temporal
		if(listTLineasDevolucion != null & listTLineasDevolucion.size() > 0){
			//Se insertan las líneas de devolución creadas en la lista de líneas de devolución de la lista.
			devolucion.settDevLineasLst(listTLineasDevolucion);

			//Ponemos un nulo en el campo devolución. Habrá un 0 para buscar en la temporal las líneas creadas,
			//pero para actualizar nos interesa un null.
			devolucion.setDevolucion(null);

			//Actualizamos la devolución y obtenemos el resultado con las líneas de devolucion y sus códigos de error
			devolucionCatalogoEstadoActualizada = this.devolucionesService.altaDevolucionCreadaPorCentro(devolucion);

			//Si no actualiza bien
			if(devolucionCatalogoEstadoActualizada !=  null){
				if(devolucionCatalogoEstadoActualizada.getListDevolucionEstado() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().size() > 0){
					//Obtenemos la devolucion actualizada
					Devolucion devolucionActualizada = devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().get(0);

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
							countGuardados ++;
						}else{
							codError = devLinea.getCodError();
							countError ++;
						}
						devLineaModificada = new DevolucionLineaModificada(devLinea.getBulto(), devLinea.getStockDevuelto(), devLinea.getStockActual(), codError, devLinea.getCodArticulo());
						devLineaModificadaActualizada.add(devLineaModificada);
					}
					devolucionActualizada.setDevLineasModificadas(devLineaModificadaActualizada);

					//Más tarde se limpian los estados. De esta forma se evita que al actualizar dos veces se mantengan los errores y disquetes de los estados enteriores y solo se visualicen
					//los nuevos.		
					this.devolucionesService.resetDevolEstados(session.getId());			

					//Actualizamos los códigos de error de las devoluciones. Además también actualizamos los datos de BULTO_ORI y STOCKDEVUELTO_ORI de la tabla temporal
					this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizada,true);
				}
			}
		}

		//Control del provvedor para ver si se elige REFERENCIAS PERMANENTES
		if (Constantes.PROVEEDOR_REF_PERMANENTES.equals(devolucion.getProveedor())){
			devolucion.setProveedor(null);
			devolucion.setFlagRefPermanentes("S");
		}

		list = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion, pagination,null,devolucion.getProveedor(),devolucion.getFlagRefPermanentes());

		Page<TDevolucionLinea> result = null;

		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

		if (list != null && list.size() > 0) {
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
			List<TDevolucionLinea> listaAMostrar = list.subList(desdeSubList,hastaSubList);

			//Obtenemos el flag de si la referencia tiene foto o no.
			refTieneFoto(listaAMostrar);
			result = paginationManager.paginate(new Page<TDevolucionLinea>(), listaAMostrar,max.intValue(), records, page.intValue());	
		} else {
			return new DevolucionActualizada(new Page<TDevolucionLinea>(),countGuardados,countError,devolucionCatalogoEstadoActualizada.getpDescError(),devolucionCatalogoEstadoActualizada.getpCodError());
		}
		DevolucionActualizada devActualizada = new DevolucionActualizada(result,countGuardados,countError,devolucionCatalogoEstadoActualizada.getpDescError(),devolucionCatalogoEstadoActualizada.getpCodError());
		return devActualizada;
	}

	//Sirve para guardar los valores de las líneas de devolución modificadas
	@RequestMapping(value = "/deleteDataGridRows", method = RequestMethod.POST)
	public  @ResponseBody Page<TDevolucionLinea> deleteDataGridRows(
			@RequestBody Devolucion devolucionAEliminar,
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

		List<TDevolucionLinea> list = null;

		//Si hay elementos a eliminar, eliminarlos de la tabla temporal
		if(devolucionAEliminar.gettDevLineasLst() != null && devolucionAEliminar.gettDevLineasLst().size() > 0){
			this.devolucionesService.deleteLineasDevolucion(session.getId(),devolucionAEliminar);
		}
		list = this.devolucionesService.findLineasDevolucion(session.getId(),devolucionAEliminar, pagination,null,devolucionAEliminar.getProveedor(),devolucionAEliminar.getFlagRefPermanentes());

		Page<TDevolucionLinea> result = null;
		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

		if (list != null && list.size() > 0) {
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

			//Si vamos a la 1 página, el primer elemento guardará un 1 en caso de ser vacío y al clicarlo. En caso
			//contrario, guardará el bulto actual del elemento de la página anterior.
			if(page.intValue() == 1){
				list.get(desdeSubList).setPrimerElementoBulto(new Long(1));
				list.get(desdeSubList).setPrimerElementoStockDevuelto(new Double(1.00));
			}else{
				list.get(desdeSubList).setPrimerElementoBulto(list.get(desdeSubList-1).getBulto());
				list.get(desdeSubList).setPrimerElementoStockDevuelto(list.get(desdeSubList-1).getStockDevuelto());
			}

			List<TDevolucionLinea> listaAMostrar = list.subList(desdeSubList,hastaSubList);
			refTieneFoto(listaAMostrar);

			result = paginationManager.paginate(new Page<TDevolucionLinea>(), listaAMostrar,max.intValue(), records, page.intValue());	
		} else {
			return new Page<TDevolucionLinea>();
		}
		return result;
	}

	//Sirve para buscar las plataformas de una referencia.
	@RequestMapping(value = "/obtenerPlataformasDevolucionCreadaPorCentro", method = RequestMethod.POST)
	public  @ResponseBody DevolucionPlataforma obtenerPlataformasDevolucionCreadaPorCentro(
			@RequestBody DevolucionPlataforma devolucionPlataforma,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		return devolucionesService.obtenerPlataformasDevolucionCreadaPorCentro(devolucionPlataforma); 
	}

	//Sirve para buscar las plataformas de una referencia.
	@RequestMapping(value = "/obtenerCantidadADevolver", method = RequestMethod.POST)
	public  @ResponseBody List<DevolucionLinea> obtenerCantidadADevolver(
			@RequestBody  Devolucion devolucion,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		return devolucionesService.obtenerCantidadADevolver(devolucion); 
	}
	/************************ OBTENER STOCK Y DENOMINACION ************************/
	private VDatosDiarioArt obtenerDiarioArt(Long codArt) throws Exception{
		VDatosDiarioArt vDatosDiarioArtRes;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		vDatosDiarioArt.setCodArt(codArt);
		vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

		return vDatosDiarioArtRes;
	}

	private VDatosDiarioArt obtenerDiarioArt(ReferenciasCentro referenciasCentro) throws Exception{
		return this.obtenerDiarioArt(referenciasCentro.getCodArt());
	}

	private ValoresStock obtenerValoresStock(ReferenciasCentro referenciasCentro, HttpSession session) throws Exception{

		ValoresStock valoresStock = new ValoresStock();
		//Calculo Ventas Medias
		ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();
		referenciasCentroIC.setCodArt(referenciasCentro.getCodArt());
		referenciasCentroIC.setCodCentro(referenciasCentro.getCodCentro());

		HistoricoVentaMedia historicoVentaMedia = this.obtenerHistoricoVentaMedia(referenciasCentroIC);

		Float ventaMedia = new Float(0);
		if (historicoVentaMedia != null){
			historicoVentaMedia.recalcularVentasMedia();
			ventaMedia = historicoVentaMedia.getMedia();
		}

		//Calculo Venta Hoy
		Double ventaHoy = new Double(0);
		List<Long> referenciaLoteTextil = null;
		List<BigInteger> listaReferenciasHijasTextil = null;
		try {
			VentasTiendaRequestType ventasTiendaRequest = new VentasTiendaRequestType();
			ventasTiendaRequest.setCodigoCentro(BigInteger.valueOf(referenciasCentro.getCodCentro()));
			Calendar cal = Calendar.getInstance();
			TimeZone tz = TimeZone.getTimeZone("UTC");
			cal.setTimeZone(tz);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			ventasTiendaRequest.setFechaDesde(cal);
			ventasTiendaRequest.setFechaHasta(cal);

			//Obtención de lista de referencias relacionadas (REF_ASOCIADA)
			RefAsociadas refAsociadas = new RefAsociadas();
			refAsociadas.setCodArticulo(referenciasCentro.getCodArt());
			List<RefAsociadas> listaRefereciasAsociadas = this.refAsociadasService.findAll(refAsociadas);

			BigInteger[] listaReferencias = { BigInteger.valueOf(referenciasCentro.getCodArt()) };
			Hashtable<Long, Long> hshRefAsociadasCantidad = new Hashtable<Long, Long>();
			if (listaRefereciasAsociadas != null && listaRefereciasAsociadas.size()>0){
				listaReferencias = new BigInteger[listaRefereciasAsociadas.size()];
				int indiceArray = 0;

				for (RefAsociadas refAsociada : listaRefereciasAsociadas) {
					listaReferencias[indiceArray] = new BigInteger(refAsociada.getCodArticuloHijo().toString());
					hshRefAsociadasCantidad.put(refAsociada.getCodArticuloHijo(), refAsociada.getCantidad());
					indiceArray++;
				}
			}else{
				hshRefAsociadasCantidad.put(referenciasCentro.getCodArt(), new Long(1));
			}



			//Petición 54293

			//Se consulta la vista V_REFERENCIAS_LOTE_TEXTIL para comprobar si la referencia es una referencia lote.
			referenciaLoteTextil = this.relacionArticuloService.esReferenciaLote(referenciasCentro.getCodArt());	

			if ((referenciaLoteTextil != null) && (referenciaLoteTextil.size() > 0)) {

				//Si ademas es una referencias de textil y referencias lote, debemos obtener las referencias
				//hijas de V_REFERENCIAS_LOTE_TEXTIL. La lista de referencias hija obtenida se junta con la obtenida
				//de REF_ASOCIADAS.	

				listaReferenciasHijasTextil = this.relacionArticuloService.obtenerHijasLoteBI(referenciasCentro.getCodArt());	

				if (listaReferenciasHijasTextil != null && listaReferenciasHijasTextil.size()>0){    

					//Juntamos el array "listaReferencias" y la lista "listaReferenciasHijasTextil" en un nuevo array de BigInteger.
					BigInteger[] listaReferenciasTextil =  new BigInteger[listaReferencias.length + listaReferenciasHijasTextil.size()];

					//Primero volcamos la "listaReferencias"
					for (int i = 0; i < listaReferencias.length; i++) {
						listaReferenciasTextil[i] = listaReferencias[i];
					} 

					//Seguido volcamos la "listaReferenciasHijasTextil"
					int indiceArray = listaReferencias.length;		
					for (BigInteger referencia : listaReferenciasHijasTextil) {
						listaReferenciasTextil[indiceArray] = referencia;
						indiceArray++;
					}	

					for (BigInteger refHijaLote : listaReferenciasHijasTextil) {
						//hshRefAsociadasCantidad.put(refAsociada.getCodArticuloHijo(), refAsociada.getCantidad());
						hshRefAsociadasCantidad.put(new Long(refHijaLote.longValue()), new Long(1)); //PREGUNTAR A MARIA, EN EL CASO DE TEXTIL DE DONDE SACAMOS LA CANTIDAD???														   //¿¿¿ES 1???	
					}	

					//Es una referencia lote de textil, se pasara al WS la referencias obtenida en REF_ASOCIADAS + las obtenidas en V_REFERENCIAS_LOTE_TEXTIL
					ventasTiendaRequest.setListaReferencias(listaReferenciasTextil);
				}

			} else {

				//No es una referencia lote de textil, solo se pasara al WS la referencias obtenida en REF_ASOCIADAS

				ventasTiendaRequest.setListaReferencias(listaReferencias);
			}


			VentasTiendaResponseType ventasTiendaResponse = this.ventasTiendaService.consultaVentas(ventasTiendaRequest,session);
			if (ventasTiendaResponse.getListaReferencias().length > 0){
				BigDecimal ventaHoyBigDecTotal = new BigDecimal(0);
				for (ReferenciaRespuetaType referencia : ventasTiendaResponse.getListaReferencias()) {
					//Si da error en la consulta de la referencia se ignora salvo si se trata de la referencia padre
					if (new BigInteger("0").equals(referencia.getCodigoError()) || referenciasCentro.getCodArt().equals(referencia.getCodigoReferencia())){
						BigDecimal cantidadBigDec = new BigDecimal(hshRefAsociadasCantidad.get(new Long(referencia.getCodigoReferencia().toString())));
						BigDecimal ventaHoyBigDec = (referencia.getTotalVentaAnticipada()!=null?referencia.getTotalVentaAnticipada():new BigDecimal("0"))
								.add(referencia.getTotalVentaCompetencia()!=null?referencia.getTotalVentaCompetencia():new BigDecimal("0"))
								.add(referencia.getTotalVentaOferta()!=null?referencia.getTotalVentaOferta():new BigDecimal("0"))
								.add(referencia.getTotalVentaTarifa()!=null?referencia.getTotalVentaTarifa():new BigDecimal("0"));
						ventaHoyBigDec = ventaHoyBigDec.multiply(cantidadBigDec);

						ventaHoyBigDecTotal = ventaHoyBigDecTotal.add(ventaHoyBigDec);
					}
				}
				ventaHoy = ventaHoyBigDecTotal.doubleValue();
			}
		} catch (Exception e) {
			valoresStock.setFlgErrorWSVentasTienda(1);
			return valoresStock;
		}
		//Calculo Dia Mayor Venta
		HistoricoUnidadesVenta historicoUnidadesVenta = new HistoricoUnidadesVenta();
		historicoUnidadesVenta.setCodArticulo(referenciasCentro.getCodArt());
		historicoUnidadesVenta.setCodLoc(referenciasCentro.getCodCentro());
		Double ventasMax = new Double(0);

		Double ventasAux = this.historicoUnidadesVentaService.findDayMostSales(historicoUnidadesVenta);

		if (null != ventasAux){
			ventasMax = ventasAux;
		}

		//Calculo Stock Actual
		Articulo articulo = new Articulo();
		Centro centro = new Centro();
		centro.setCodCentro(referenciasCentro.getCodCentro());
		articulo.setCentro(centro);
		articulo.setCodArt(referenciasCentro.getCodArt());

		try {
			ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(referenciasCentro.getCodCentro()));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);


			//Petición 54293

			//Se consulta la vista V_REFERENCIAS_LOTE_TEXTIL para comprobar si la referencia es una referencia lote. (Consultada previamemte)

			if ((referenciaLoteTextil != null) && (referenciaLoteTextil.size() > 0)) {

				//Seguido volcamos la "listaReferenciasHijasTextil"
				int indiceArray = 0;	
				BigInteger[] listaRef = new BigInteger[listaReferenciasHijasTextil.size()];
				for (BigInteger referencia : listaReferenciasHijasTextil) {
					listaRef[indiceArray] = referencia;
					indiceArray++;
				}	
				requestType.setListaCodigosReferencia(listaRef);

			} else {
				BigInteger[] listaRef = {BigInteger.valueOf(referenciasCentro.getCodArt())}; 
				requestType.setListaCodigosReferencia(listaRef);
			}

			User user = (User) session.getAttribute("user");
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: p86PopUpCrearDevolucionController (obtenerValoresStock)	 ####");
				logger.error("###########################################################################################################");
			}

			ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
			if (responseType.getCodigoRespuesta().equals("OK")){

				for (ReferenciaType referencia : responseType.getListaReferencias()){
					//if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(referenciasCentro.getCodArt()))){
					if (referencia.getCodigoError().equals(new BigInteger("0"))){
						valoresStock.setStockPrincipal(referencia.getStockPrincipal());
						if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
							valoresStock.setStock(referencia.getBandejas().doubleValue());

						} else {
							if (valoresStock.getStock()==null) {
								valoresStock.setStock(referencia.getStock().doubleValue());
							} else {
								valoresStock.setStock(valoresStock.getStock().doubleValue() + referencia.getStock().doubleValue());
							}

						}
					} else {
						valoresStock.setFlgErrorWSVentasTienda(1);
						return valoresStock;
					}
					//}
				}
			} else {
				valoresStock.setFlgErrorWSVentasTienda(1);
				return valoresStock;
			}
		} catch (Exception e) {
			valoresStock.setFlgErrorWSVentasTienda(1);
			return valoresStock;
		}

		//Control Si SFM
		VDatosDiarioArt vDatosDiarioArtRes = this.obtenerDatosDiarioArt(referenciasCentro);
		if (null != vDatosDiarioArtRes){
			VAgruComerParamSfmcap vAgruComerParamSfmcap = new VAgruComerParamSfmcap();
			vAgruComerParamSfmcap.setCodCentro(referenciasCentro.getCodCentro());
			vAgruComerParamSfmcap.setGrupo1(vDatosDiarioArtRes.getGrupo1());
			vAgruComerParamSfmcap.setGrupo2(vDatosDiarioArtRes.getGrupo2());
			vAgruComerParamSfmcap.setGrupo3(vDatosDiarioArtRes.getGrupo3());
			vAgruComerParamSfmcap.setGrupo4(vDatosDiarioArtRes.getGrupo4());
			vAgruComerParamSfmcap.setGrupo5(vDatosDiarioArtRes.getGrupo5());
			List<VAgruComerParamSfmcap> listaEstructuras = this.vAgruComerParamSfmcapService.findAll(vAgruComerParamSfmcap, null);
			VAgruComerParamSfmcap estructuraArticulo = null;
			if (!listaEstructuras.isEmpty()){
				estructuraArticulo = listaEstructuras.get(0);
			}
			Double stockBajo;
			Double sobreStockInferior;
			Double sobreStockSuperior;

			boolean esSfm = ( null != estructuraArticulo && estructuraArticulo.getFlgStockFinal().equals("S") );
			boolean esFacing = ( null != estructuraArticulo && estructuraArticulo.getFlgFacing().equals("S") );
			boolean esCap = ( null != estructuraArticulo && estructuraArticulo.getFlgCapacidad().equals("S") );
			boolean esFacingCapOtros = false;
			boolean esFacingX = ( null != estructuraArticulo && 
					("S".equals(estructuraArticulo.getFlgFacingCapacidad()) || "S".equals(estructuraArticulo.getFlgCapacidad()) || 
							("N".equals(estructuraArticulo.getFlgStockFinal()) && "N".equals(estructuraArticulo.getFlgFacing()))));

			Long facing = new Long(0);
			Float sfm = new Float(0);
			//Pet. 53005		
			referenciasCentroIC.setEsFacingX(esFacingX);

			StockFinalMinimo stockFinalMinimo = this.stockFinalMinimoService.obtenerStockFinalMinimo(referenciasCentroIC);

			//Compruebo si es Facing, Sfm o Capacidad, por este orden
			//y calculo el correspondiente facing o el sfm para usarlo en el
			//calculo de los stockBajo, el sobreStockInferior y el sobreStockSuperior.
			if (esFacing) {
				//Es Facing
				esFacingCapOtros = true;
				facing = (null != stockFinalMinimo.getFacingCentroSIA())?stockFinalMinimo.getFacingCentroSIA():new Long(0);
			} else if (esSfm) {
				//Es Sfm
				esFacingCapOtros = false;
				sfm = (null != stockFinalMinimo.getCantidadManualSIA())?stockFinalMinimo.getCantidadManualSIA(): new Float(0);
			} else if (esCap){
				//Es Capacidad
				esFacingCapOtros = true;
				Float capacidad = (null != stockFinalMinimo.getCapacidadSIA())?stockFinalMinimo.getCapacidadSIA():new Float(0);
				CentroAutoservicio centroAutoservicio = new CentroAutoservicio();
				centroAutoservicio.setCodCentro(referenciasCentroIC.getCodCentro());
				List<CentroAutoservicio> listaCentroAutoservicio = this.paramCentrosVpService.findCentroAutoServicioAll(centroAutoservicio);
				facing = new Long(1);
				if (!listaCentroAutoservicio.isEmpty())
				{
					CentroAutoservicio centroAutoServicio = listaCentroAutoservicio.get(0);

					if (centroAutoServicio != null && centroAutoServicio.getPorcentajeCapacidad() != null)
					{
						Float porcentajeCapacidad = centroAutoServicio.getPorcentajeCapacidad();
						if (porcentajeCapacidad != null && capacidad*porcentajeCapacidad >1)
						{
							facing = new  Double(capacidad*porcentajeCapacidad).longValue();
						}
					}

				}
			} else {
				//Es otros
				esFacingCapOtros = true;
				PlanogramaVigente planogramaVigente = this.obtenerPlanogramaVigente(referenciasCentroIC);
				if (null != planogramaVigente){
					facing = planogramaVigente.getStockMinComerLineal().longValue();
				}
			}

			//Calculo el stockBajo, el sobreStockInferior y el sobreStockSuperior
			//con el facing o el sfm calculado en el if anterior.
			if (esFacingCapOtros){
				//Es Facing o Capacidad o Otros
				VMapaAprov vMapaAprov = new VMapaAprov();
				vMapaAprov.setCodArt(referenciasCentro.getCodArt());
				vMapaAprov.setCodCentro(referenciasCentro.getCodCentro());

				vMapaAprov = this.vMapaAprovService.findOne(vMapaAprov);
				Integer tipoServicio = 5;
				if (null == vMapaAprov){
					vMapaAprov = new VMapaAprov();
					vMapaAprov.setCodCentro(referenciasCentro.getCodCentro());
					vMapaAprov.setCodN1(vDatosDiarioArtRes.getEstlogN1().toString());
					vMapaAprov.setCodN2(vDatosDiarioArtRes.getEstlogN2().toString());
					vMapaAprov.setCodN3(vDatosDiarioArtRes.getEstlogN3().toString());
					vMapaAprov = this.vMapaAprovService.findOne(vMapaAprov);
				}
				if (null != vMapaAprov){
					Integer contador = 0;
					Method[] methods = vMapaAprov.getClass().getDeclaredMethods();
					for (Method method : methods) {
						if (method.getName().startsWith("getPed")) {
							Long pedidos = (Long) method.invoke(vMapaAprov);
							if (pedidos > 0){
								contador++;
							}
						}
					}
					if (contador >= 5){
						tipoServicio = 3;
					} else {
						tipoServicio = 5;
					}
				}

				Long multiplicador = new Long(1);

				//Comprobación de si una referencia es FFPP 
				User user = (User) session.getAttribute("user");
				boolean tratamientoVegalsaAux = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
				referenciasCentro = this.vRelacionArticuloService.obtenerFfppActivaOUnitaria(referenciasCentro, tratamientoVegalsaAux);

				//Si es un FFPP no tiene sentido el multiplicador
				if (!referenciasCentro.isTieneUnitaria()){
					//Peticion 54867
					VFacingX vFacingXRes = new VFacingX();
					VFacingX vFacingX = new VFacingX();
					vFacingX.setCodArticulo(vDatosDiarioArtRes.getCodFpMadre()); 
					vFacingX.setCodCentro(referenciasCentroIC.getCodCentro());
					vFacingXRes = this.vFacingXService.findOne(vFacingX);

					if (vFacingXRes != null && vFacingXRes.getFacingX()!=null){
						multiplicador = vFacingXRes.getFacingX();
					} 
				}
				if (facing * multiplicador > ventaMedia){
					stockBajo = ((facing * multiplicador) - ventaHoy);
				} else {
					stockBajo = (ventaMedia - ventaHoy);
				}

				sobreStockInferior = (ventasMax * tipoServicio) - ventaHoy;
				sobreStockSuperior = ((ventasMax * tipoServicio) - ventaHoy) * 2;

			}
			else{
				//Es Sfm
				Long vidaUtil = stockFinalMinimo.getVidaUtil();
				if (null == vidaUtil || vidaUtil.equals(new Long(0))){
					vidaUtil = vDatosDiarioArtRes.getVidaUtil();
					if (vidaUtil.equals(new Long(0))){
						vidaUtil = new Long(1);
					}
				}

				if (sfm > ventaMedia){
					stockBajo = (double) (sfm - ventaHoy);
				} else {
					stockBajo = (double) (ventaMedia - ventaHoy);
				}
				sobreStockInferior = (ventasMax * vidaUtil) - ventaHoy;
				sobreStockSuperior = ((ventasMax * vidaUtil) - ventaHoy) * 2;
			}

			valoresStock.setStockBajo(stockBajo);
			valoresStock.setSobreStockInferior(sobreStockInferior);
			valoresStock.setSobreStockSuperior(sobreStockSuperior);
			valoresStock.setFlgErrorWSVentasTienda(0);
		}

		//Comprobación de si hay que mostrar el link de información de motivos cuando el stock es alto o bajo
		//Restringido de momento a 2 usuarios
		valoresStock.setMostrarMotivosStock("N"); //Inicialización a no mostrar el enlace a motivos de stock	

		User user = (User) session.getAttribute("user");
		String centroUsuario = "";
		if (user.getCentro() != null && user.getCentro().getCodCentro()!=null){
			centroUsuario = user.getCentro().getCodCentro().toString();
		}
		//if ("202".equals(centroUsuario) || "288".equals(centroUsuario) || "673".equals(centroUsuario) || "691".equals(centroUsuario) || "i1251".equals(user.getCode()) || "I1251".equals(user.getCode()) || "s305060".equals(user.getCode()) || "S305060".equals(user.getCode())) {

		if (((valoresStock.getStockBajo() != null) & (valoresStock.getSobreStockInferior()!= null)) & (valoresStock.getSobreStockSuperior()!= null)){

			if (valoresStock.getStockBajo() <= valoresStock.getSobreStockInferior() && valoresStock.getSobreStockInferior() <= valoresStock.getSobreStockSuperior() && valoresStock.getStockBajo() >= 0){
				if(valoresStock.getStock() < valoresStock.getStockBajo() || valoresStock.getStock() > valoresStock.getSobreStockInferior()){
					MotivoTengoMuchoPoco motivoTengoMuchoPoco = new MotivoTengoMuchoPoco();
					motivoTengoMuchoPoco.setCodArticulo(referenciasCentro.getCodArt());
					motivoTengoMuchoPoco.setCodCentro(referenciasCentro.getCodCentro());

					String idSesionConsultaMotivos = session.getId()+ "_MOT_FFPP";
					List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();

					TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
					tPedidoAdicional.setIdSesion(idSesionConsultaMotivos);
					tPedidoAdicional.setCodCentro(referenciasCentro.getCodCentro());
					tPedidoAdicional.setCodArticulo(referenciasCentro.getCodArt());
					listaTPedidoAdicional.add(tPedidoAdicional);

					if(valoresStock.getStock() > valoresStock.getSobreStockInferior()){
						motivoTengoMuchoPoco.setTipo(Constantes.TENGO_MUCHO_POCO_MOTIVO_MUCHO);
						//Si es tengo mucho hay que cargar los datos de la referencia y de su formato productivo si existe para la consulta de motivos
						if (referenciasCentro.isTieneFfppActivo()){
							//Tratamiento pedido Adicional de ffpp							
							tPedidoAdicional = new TPedidoAdicional();
							tPedidoAdicional.setIdSesion(idSesionConsultaMotivos);
							tPedidoAdicional.setCodCentro(referenciasCentro.getCodCentro());
							tPedidoAdicional.setCodArticulo(referenciasCentro.getCodArtRelacionado());
							listaTPedidoAdicional.add(tPedidoAdicional);
						}
					} else {
						motivoTengoMuchoPoco.setTipo(Constantes.TENGO_MUCHO_POCO_MOTIVO_POCO);
					}

					this.tPedidoAdicionalService.obtenerPedidosAdicionales(listaTPedidoAdicional, referenciasCentro.getCodCentro(), idSesionConsultaMotivos,session);

					motivoTengoMuchoPoco.setIdSesion(idSesionConsultaMotivos);
					motivoTengoMuchoPoco.setStockBajo(valoresStock.getStockBajo());
					motivoTengoMuchoPoco.setStockAlto(valoresStock.getSobreStockInferior());
					motivoTengoMuchoPoco.setStock(valoresStock.getStock());

					MotivoTengoMuchoPocoLista motivoTengoMuchoPocoLista = null;
					motivoTengoMuchoPocoLista = this.motivoTengoMuchoPocoService.consultaMotivosTengoMuchoPoco(motivoTengoMuchoPoco);

					if (motivoTengoMuchoPocoLista != null && motivoTengoMuchoPocoLista.getEstado() != null && (new Long(0)).equals(motivoTengoMuchoPocoLista.getEstado())){
						if ("S".equals(motivoTengoMuchoPocoLista.getMapaActivo()) && "S".equals(motivoTengoMuchoPocoLista.getFlgFGen())){
							valoresStock.setMostrarMotivosStock("S");
						}
					}
				}
			}
		}
		//}	    	

		//Petición 48890. Calculo de los dias de stock. Los dias de stock  se calcularán como stock actual / venta media

		if ((ventaMedia != null && ventaMedia != 0.0) && (valoresStock.getStock()!= null)){
			double resultado = new Double(valoresStock.getStock() / ventaMedia); 
			valoresStock.setDiasStock(resultado);
			valoresStock.setVentaMedia(ventaMedia);
			valoresStock.setExisteVentaMedia(true);
		} else {
			valoresStock.setExisteVentaMedia(false);
		}	    	    		    		
		return valoresStock;
	}

	private HistoricoVentaMedia obtenerHistoricoVentaMedia(
			ReferenciasCentroIC referenciasCentroIC) throws Exception {

		HistoricoVentaMedia historicoVentaMediaRes;
		HistoricoVentaMedia historicoVentaMedia = new HistoricoVentaMedia();
		historicoVentaMedia.setCodArticulo(referenciasCentroIC.getCodArt());
		historicoVentaMedia.setCodLoc(referenciasCentroIC.getCodCentro());

		//Filtro de fecha con el día de ayer
		Calendar fechaVenta = Calendar.getInstance();
		fechaVenta.add(Calendar.DAY_OF_YEAR, -1);
		historicoVentaMedia.setFechaVentaMedia(fechaVenta.getTime());

		historicoVentaMediaRes = this.historicoVentaMediaService.findOneAcumuladoRef(historicoVentaMedia);

		if (historicoVentaMediaRes != null){
			historicoVentaMediaRes.recalcularVentasMedia();
		}

		return historicoVentaMediaRes;
	}

	public VDatosDiarioArt obtenerDatosDiarioArt(ReferenciasCentro referenciasCentro) throws Exception{
		VDatosDiarioArt vDatosDiarioArtRes = null;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();

		vDatosDiarioArt.setCodArt(referenciasCentro.getCodArt());
		vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

		return vDatosDiarioArtRes;
	}

	private PlanogramaVigente obtenerPlanogramaVigente(
			ReferenciasCentroIC referenciasCentroIC) throws Exception {

		PlanogramaVigente planogramaVigenteRes;
		PlanogramaVigente planogramaVigente = new PlanogramaVigente();
		planogramaVigente.setCodArt(referenciasCentroIC.getCodArt());
		planogramaVigente.setCodCentro(referenciasCentroIC.getCodCentro());

		planogramaVigenteRes = this.planogramaVigenteService.findOne(planogramaVigente);

		return planogramaVigenteRes;
	}

	/************************ METODOS TABLA TEMPORAL ************************/

	//Método que sirve para guardar la línea de devolución en un registro de una tabla temporal llamada T_DEVOLUCIONES
	//Esa tabla además de contener como columnas los atributos del objeto DevolucionLinea, contiene el id de la sesión
	//del usuario, la fecha de creación y un código de devolución. Con la columna del id de sesión es posible que cada
	//usuario tenga sus datos en la tabla temporal y así no ver los de los demás, además se utilizará para borrar los
	//datos de la tabla temporal de su sesión cuando sea necesario. El campo de la fecha de creación sirve para eliminar los
	//registros que lleven guardados X tiempo y sean considerados como basura en la tabla temporal. El campo de devolución
	//se utilizará para saber cada línea de devolución a que devolución pertenece.
	private void insertarTablaSesionLineaDevolucion(DevolucionLinea devolucionLinea, String idSesion, Long devolucion){

		//Aunque la devolución se inserte una a una, creamos una lista donde insertaremos la devolución, ya que el método insertAll solo acepta listas.
		List<TDevolucionLinea> listTDevolucionLinea = new ArrayList<TDevolucionLinea>();

		//Creamos la devolución con los datos necesarios para que se inserte en la tabla temporal.
		TDevolucionLinea addTDevolucionLinea = new TDevolucionLinea();

		//Se rellena el objeto
		addTDevolucionLinea.setIdSesion(idSesion);
		addTDevolucionLinea.setCreationDate(new Date());
		addTDevolucionLinea.setDevolucion(devolucion);
		addTDevolucionLinea.setCodArticulo(devolucionLinea.getCodArticulo());
		addTDevolucionLinea.setDenominacion((devolucionLinea.getDenominacion() != null && !("".equals(devolucionLinea.getDenominacion())))?devolucionLinea.getDenominacion().toString():null);			
		addTDevolucionLinea.setMarca((devolucionLinea.getMarca() != null && !("".equals(devolucionLinea.getMarca())))?devolucionLinea.getMarca():null);
		addTDevolucionLinea.setSeccion((devolucionLinea.getSeccion() != null && !("".equals(devolucionLinea.getSeccion())))?devolucionLinea.getSeccion():null);
		addTDevolucionLinea.setProvrGen(devolucionLinea.getProvrGen());
		addTDevolucionLinea.setProvrTrabajo(devolucionLinea.getProvrTrabajo());
		addTDevolucionLinea.setDenomProveedor((devolucionLinea.getDenomProveedor() != null && !("".equals(devolucionLinea.getDenomProveedor())))?devolucionLinea.getDenomProveedor():null);			
		addTDevolucionLinea.setFamilia((devolucionLinea.getFamilia() != null && !("".equals(devolucionLinea.getFamilia())))?devolucionLinea.getFamilia():null);
		addTDevolucionLinea.setFormatoDevuelto((devolucionLinea.getFormatoDevuelto() != null && !("".equals(devolucionLinea.getFormatoDevuelto())))?devolucionLinea.getFormatoDevuelto():null);
		addTDevolucionLinea.setFormato(devolucionLinea.getFormato());
		addTDevolucionLinea.setTipoFormato((devolucionLinea.getTipoFormato() != null && !("".equals(devolucionLinea.getTipoFormato())))?devolucionLinea.getTipoFormato():null);
		addTDevolucionLinea.setPasillo((devolucionLinea.getPasillo() != null && !("".equals(devolucionLinea.getPasillo())))?devolucionLinea.getPasillo():null);
		addTDevolucionLinea.setEstructuraComercial((devolucionLinea.getEstructuraComercial() != null && !("".equals(devolucionLinea.getEstructuraComercial())))?devolucionLinea.getEstructuraComercial():null);
		addTDevolucionLinea.setUc(devolucionLinea.getUc());
		addTDevolucionLinea.setStockActual(devolucionLinea.getStockActual());
		addTDevolucionLinea.setStockTienda(devolucionLinea.getStockTienda());
		addTDevolucionLinea.setStockDevolver(devolucionLinea.getStockDevolver());
		addTDevolucionLinea.setStockDevuelto(devolucionLinea.getStockDevuelto());
		addTDevolucionLinea.setStockDevueltoOrig(devolucionLinea.getStockDevuelto());
		addTDevolucionLinea.setCantAbonada(devolucionLinea.getCantAbonada());
		addTDevolucionLinea.setFlgContinuidad((devolucionLinea.getFlgContinuidad() != null && !("".equals(devolucionLinea.getFlgContinuidad())))?devolucionLinea.getFlgContinuidad():null);
		addTDevolucionLinea.setLote((devolucionLinea.getLote() != null && !("".equals(devolucionLinea.getLote())))?devolucionLinea.getLote():null);
		addTDevolucionLinea.setnLote((devolucionLinea.getnLote() != null && !("".equals(devolucionLinea.getnLote())))?devolucionLinea.getnLote():null);
		addTDevolucionLinea.setCaducidad((devolucionLinea.getCaducidad() != null && !("".equals(devolucionLinea.getCaducidad())))?devolucionLinea.getCaducidad():null);
		addTDevolucionLinea.setnCaducidad((devolucionLinea.getnCaducidad() != null && !("".equals(devolucionLinea.getnCaducidad())))?devolucionLinea.getnCaducidad():null);
		addTDevolucionLinea.setDescAbonoError((devolucionLinea.getDescAbonoError() != null && !("".equals(devolucionLinea.getDescAbonoError())))?devolucionLinea.getDescAbonoError():null);
		addTDevolucionLinea.setBulto(devolucionLinea.getBulto());
		addTDevolucionLinea.setBultoOrig(devolucionLinea.getBulto());
		addTDevolucionLinea.setUbicacion((devolucionLinea.getUbicacion() != null && !("".equals(devolucionLinea.getUbicacion())))?devolucionLinea.getUbicacion():null);
		addTDevolucionLinea.setTipoReferencia((devolucionLinea.getTipoReferencia() != null && !("".equals(devolucionLinea.getTipoReferencia())))?devolucionLinea.getTipoReferencia():null);
		addTDevolucionLinea.setEstadoLin(devolucionLinea.getEstadoLin());
		addTDevolucionLinea.setCodError(devolucionLinea.getCodError());
		addTDevolucionLinea.setDescError((devolucionLinea.getDescError() != null && !("".equals(devolucionLinea.getDescError())))?devolucionLinea.getDescError():null);
		addTDevolucionLinea.setFlgBandejas((devolucionLinea.getFlgBandejas() != null && !("".equals(devolucionLinea.getFlgBandejas())))?devolucionLinea.getFlgBandejas():null);
		addTDevolucionLinea.setStockDevueltoBandejas(devolucionLinea.getStockDevueltoBandejas());
		addTDevolucionLinea.setCodTpCa(devolucionLinea.getCodTpCa());
		addTDevolucionLinea.setCantidadMaximaPermitida(devolucionLinea.getCantidadMaximaPermitida());
		addTDevolucionLinea.setDescrTalla(devolucionLinea.getDescrTalla());
		addTDevolucionLinea.setDescrColor(devolucionLinea.getDescrColor());
		addTDevolucionLinea.setModelo(devolucionLinea.getModelo());
		addTDevolucionLinea.setModeloProveedor(devolucionLinea.getModeloProveedor());
		addTDevolucionLinea.setArea(devolucionLinea.getArea());
		addTDevolucionLinea.setCosteUnitario(devolucionLinea.getCosteUnitario());

		listTDevolucionLinea.add(addTDevolucionLinea);
		try {
			this.devolucionesService.insertAll(listTDevolucionLinea);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}	
	}


	/************************ METODOS BUSQUEDA FOTOL ************************/

	private void refTieneFoto(List<TDevolucionLinea> listTDevolucionLinea) throws Exception{
		for(TDevolucionLinea devLin:listTDevolucionLinea){
			//Comprobar si tiene Foto
			FotosReferencia fotosReferencia = new FotosReferencia();
			fotosReferencia.setCodReferencia(devLin.getCodArticulo());
			if (fotosReferenciaService.checkImage(fotosReferencia)){
				devLin.setFlgFoto("S");
			} else {
				devLin.setFlgFoto("N");
			}
		}
	}

	/************************ METODOS ELIMINAR SESION ************************/

	//Elimina la tabla temporal histórica de todos los usuarios si ha pasado más de un día desde
	//la creación de los registros
	private boolean eliminarTablaSesionHistorico(){		
		boolean eliminarHistorico = false;
		try {
			this.devolucionesService.deleteHistorico();
			eliminarHistorico = true;
		} catch (Exception e) {
			logger.error("eliminarTablaSesionHistorico="+e.toString());
			e.printStackTrace();
		}
		return eliminarHistorico;
	}

	//Elimina los datos de sesión de la tabla temporal de ese usuario 
	private boolean eliminarTablaSesion(String idSesion){		
		boolean eliminarHistorico = false;

		TDevolucionLinea registro = new TDevolucionLinea();		
		registro.setIdSesion(idSesion);

		try {
			this.devolucionesService.delete(registro);
			eliminarHistorico = true;
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
		return eliminarHistorico;
	}

}
