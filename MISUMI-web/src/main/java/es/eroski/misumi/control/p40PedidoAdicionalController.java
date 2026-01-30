package es.eroski.misumi.control;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.CamposSeleccionadosE;
import es.eroski.misumi.model.CamposSeleccionadosM;
import es.eroski.misumi.model.CamposSeleccionadosMO;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.PedidoAdicionalAyuda1;
import es.eroski.misumi.model.PedidoAdicionalAyuda2;
import es.eroski.misumi.model.PedidoAdicionalContadores;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoAdicionalEC;
import es.eroski.misumi.model.PedidoAdicionalEPagina;
import es.eroski.misumi.model.PedidoAdicionalM;
import es.eroski.misumi.model.PedidoAdicionalMO;
import es.eroski.misumi.model.PedidoAdicionalMOPagina;
import es.eroski.misumi.model.PedidoAdicionalMPagina;
import es.eroski.misumi.model.PedidoAdicionalNuevo;
import es.eroski.misumi.model.PedidoHTNoPbl;
import es.eroski.misumi.model.PedidoHTNoPblLista;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VAgruComerRefPedidos;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.VOfertaPa;
import es.eroski.misumi.model.VOfertaPaAyuda;
import es.eroski.misumi.model.VPlanPedidoAdicional;
import es.eroski.misumi.model.VReferenciasNuevasVegalsa;
import es.eroski.misumi.model.VReferenciasPedirSIA;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.HistoricoUnidadesVentaService;
import es.eroski.misumi.service.iface.HistoricoVentaMediaService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.PedidoAdicionalECService;
import es.eroski.misumi.service.iface.PedidoAdicionalEService;
import es.eroski.misumi.service.iface.PedidoAdicionalMOService;
import es.eroski.misumi.service.iface.PedidoAdicionalMService;
import es.eroski.misumi.service.iface.PedidoAdicionalNuevoService;
import es.eroski.misumi.service.iface.PedidoAdicionalService;
import es.eroski.misumi.service.iface.PedidoHTNoPblService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.TEncargosClteService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VAgruComerRefPedidosService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VBloqueoEncargosPiladasService;
import es.eroski.misumi.service.iface.VDatosEspecificosTextilService;
import es.eroski.misumi.service.iface.VOfertaPaAyudaService;
import es.eroski.misumi.service.iface.VOfertaPaService;
import es.eroski.misumi.service.iface.VPlanPedidoAdicionalService;
import es.eroski.misumi.service.iface.VReferenciasPedirSIAService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/pedidoAdicional")
public class p40PedidoAdicionalController {

	private static Logger logger = Logger.getLogger(p40PedidoAdicionalController.class);

	private PaginationManager<PedidoAdicionalE> paginationManagerE = new PaginationManagerImpl<PedidoAdicionalE>();
	private PaginationManager<PedidoAdicionalM> paginationManagerM = new PaginationManagerImpl<PedidoAdicionalM>();
	private PaginationManager<PedidoAdicionalMO> paginationManagerMO = new PaginationManagerImpl<PedidoAdicionalMO>();


	@Resource 
	private MessageSource messageSource;

	@Autowired
	private HistoricoVentaMediaService historicoVentaMediaService;
	@Autowired
	private HistoricoUnidadesVentaService historicoUnidadesVentaService;

	@Autowired
	private VOfertaPaAyudaService vOfertaPaAyudaService;

	@Autowired
	private PedidoAdicionalService pedidoAdicionalService;

	@Autowired
	private PedidoAdicionalEService pedidoAdicionalEService;

	@Autowired
	private PedidoAdicionalMService pedidoAdicionalMService;

	@Autowired
	private PedidoAdicionalMOService pedidoAdicionalMOService;

	@Autowired
	private PedidoAdicionalECService pedidoAdicionalECService;

	@Autowired
	private VAgruComerRefPedidosService vAgruComerRefPedidosService;

	@Autowired
	private VPlanPedidoAdicionalService vPlanPedidoAdicionalService;

	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@Autowired
	private TEncargosClteService tEncargosClteService;

	@Autowired
	private VOfertaPaService vOfertaPaService;

	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;

	@Autowired
	private VDatosEspecificosTextilService datosTextil;

	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;

	@Autowired
	private ExcelManager excelManager;

	@Autowired
	private StockTiendaService stockTiendaService;

	@Autowired
	private VAgruComerRefService vAgruComerRefService;

	@Autowired
	private VBloqueoEncargosPiladasService vBloqueoEncargoPiladasService;

	@Autowired
	private PedidoHTNoPblService pedidoHTNoPblService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	@Autowired
	private VReferenciasPedirSIAService vReferenciasPedirSIAService;

	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;
	
	@Autowired
	private PedidoAdicionalNuevoService pedidoAdicionalNuevoService;

	
	@Value( "${adminRole}" )
	private String adminRole;
	
	@Value( "${tecnicRole}" )
	private String tecnicRole;

	public String area=null;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model,
			@RequestParam(required = false, defaultValue = "") String flagCancelarNuevo,
			@RequestParam(required = false, defaultValue = "") String pestanaOrigen,
			@RequestParam(required = false, defaultValue = "") String codArea,
			@RequestParam(required = false, defaultValue = "") String codSeccion,
			@RequestParam(required = false, defaultValue = "") String codCategoria,
			@RequestParam(required = false, defaultValue = "") String referencia,
			@RequestParam(required = false, defaultValue = "") String mac,
			@RequestParam(required = false, defaultValue = "") String flgReferenciaCentro,
			@RequestParam(required = false, defaultValue = "") String descPeriodo,
			@RequestParam(required = false, defaultValue = "") String pantallaOrigen,
			HttpServletResponse response,
			HttpSession session) {

		//Información necesaria para cuando venimos del botón cancelar de Pedido Nuevo.
		model.put("flagCancelarNuevo", flagCancelarNuevo);
		model.put("pestanaOrigen", pestanaOrigen);
		model.put("codArea", codArea);
		model.put("codSeccion", codSeccion);
		model.put("codCategoria", codCategoria);
		model.put("referencia", referencia);
		model.put("mac", mac);
		model.put("flgReferenciaCentro", flgReferenciaCentro);
		model.put("descPeriodo", descPeriodo);
		model.put("pantallaOrigen", pantallaOrigen);
		
		return "p40_pedidoAdicional";
	}

	@RequestMapping(value = "/loadDataGridE", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalEPagina loadDataGrid(
			@RequestBody PedidoAdicionalE pedidoAdicionalE,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "articulo", required = false) String codArticulo,
			@RequestParam(value = "identificador", required = false) String identificador,
			@RequestParam(value = "identificadorSIA", required = false) String identificadorSIA,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

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
			List<PedidoAdicionalE> list = null;
			List<CamposSeleccionadosE> listaSeleccionados = new ArrayList<CamposSeleccionadosE>();
			String esTecnico = "N";


			list = this.obtenerTablaSesionE(session.getId(), pedidoAdicionalE.getCodCentro(), pedidoAdicionalE.getMca(),null,null);

			//Además la primera vez que cargamos tenemos que crear la lista con las referencias inicialmente no selaccionadas.
			CamposSeleccionadosE campoSelecc = new CamposSeleccionadosE();
			if (list != null) {
				//Nos recorremos la lista.
				PedidoAdicionalE campo = new PedidoAdicionalE();
				for (int i =0;i<list.size();i++)
				{
					campo = (PedidoAdicionalE)list.get(i);

					//Además creamos la lista con las referencias inicialmente no selaccionadas.
					campoSelecc = new CamposSeleccionadosE();
					campoSelecc.setCodArticulo(campo.getCodArticulo());
					campoSelecc.setIdentificador(campo.getIdentificador());
					campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
					campoSelecc.setSeleccionado("N");
					listaSeleccionados.add(i, campoSelecc);
				}
			}

			//Volvemos a obtener la lista guardada en la tabla temporal.


			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

			//Montaje de lista paginada
			PedidoAdicionalEPagina pedidoAdicionalEPagina = new PedidoAdicionalEPagina();
			Page<PedidoAdicionalE> listaPedidoAdicionalEPaginada = null;

			if (list != null) {
				int records = list.size();
				listaPedidoAdicionalEPaginada = this.paginationManagerE.paginate(new Page<PedidoAdicionalE>(), list,
						max.intValue(), records, page.intValue());	
				pedidoAdicionalEPagina.setDatos(listaPedidoAdicionalEPaginada);
				pedidoAdicionalEPagina.setListadoSeleccionados(listaSeleccionados);
				pedidoAdicionalEPagina.setEsTecnico(esTecnico);
				pedidoAdicionalEPagina.setCodError(new Long(0));

			} else {
				pedidoAdicionalEPagina.setDatos(new Page<PedidoAdicionalE>());
			}
			return pedidoAdicionalEPagina;

		}
		else
		{
			return loadDataGridERecarga(pedidoAdicionalE, codArticulo, identificador, identificadorSIA, page, max, index, sortOrder, response, session);
		}
	}

	@RequestMapping(value = "/loadDataGridERecarga", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalEPagina loadDataGridERecarga(
			@RequestBody PedidoAdicionalE pedidoAdicionalE,
			@RequestParam(value = "codArticulo", required = false) String codArticulo,
			@RequestParam(value = "identificador", required = false) String identificador,
			@RequestParam(value = "identificadorSIA", required = false) String identificadorSIA,
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

		List<PedidoAdicionalE> listaGuardada = null;
		List<PedidoAdicionalE> listaRecarga = new ArrayList<PedidoAdicionalE>();
		String esTecnico = "N";
		int records = 0;
		Long contadorEncargos=new Long(0);
		try {
			Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
			Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
			listaGuardada = this.obtenerTablaSesionE(session.getId(), pedidoAdicionalE.getCodCentro(), pedidoAdicionalE.getMca(),null,null);

			//Si me llega codArticulo, significa que lo tenemos que eliminar de la tabla temporal de sesión.
			if ((codArticulo != null)&&(!codArticulo.equals("")))
			{
				//Eliminamos de la tabla el registro
				this.eliminarArticuloTablaSesion(session.getId(), pedidoAdicionalE.getClasePedido(), pedidoAdicionalE.getCodCentro(), Long.parseLong(codArticulo),(identificador!=null && !"".equals(identificador) && !"null".equals(identificador)?Long.parseLong(identificador):null),(identificadorSIA!=null && !"".equals(identificadorSIA) && !"null".equals(identificadorSIA)?Long.parseLong(identificadorSIA):null));

				//Volvemos a obtener la lista
				listaGuardada = this.obtenerTablaSesionE(session.getId(), pedidoAdicionalE.getCodCentro(),pedidoAdicionalE.getMca(),null,null);

				//Actualizamos el contador de montaje de sesión
				contadorEncargos = (Long)session.getAttribute("contadorEncargos");
				contadorEncargos = contadorEncargos - 1;
				session.setAttribute("contadorEncargos", contadorEncargos);
			}

			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionE(session.getId(), pedidoAdicionalE.getCodCentro(),pedidoAdicionalE.getMca(),index, sortOrder);
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

			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<PedidoAdicionalE> listaPedidoAdicionalEPaginada = null;
		PedidoAdicionalEPagina pedidoAdicionalEPagina = new PedidoAdicionalEPagina();

		if (listaRecarga != null) {
			listaPedidoAdicionalEPaginada = this.paginationManagerE.paginate(new Page<PedidoAdicionalE>(), listaRecarga,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalEPagina.setDatos(listaPedidoAdicionalEPaginada);
			pedidoAdicionalEPagina.setListadoSeleccionados(pedidoAdicionalE.getListadoSeleccionados());
			pedidoAdicionalEPagina.setEsTecnico(esTecnico);
			pedidoAdicionalEPagina.setContadorEncargos(contadorEncargos);
			pedidoAdicionalEPagina.setCodError(new Long(0));

		} else {
			pedidoAdicionalEPagina.setDatos(new Page<PedidoAdicionalE>());
		}

		return pedidoAdicionalEPagina;
	}

	@RequestMapping(value = "/openModifyDataGridE", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalE openModifyDataGridE(
			@RequestBody PedidoAdicionalE pedidoAdicionalE,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{


		User user = (User) session.getAttribute("user");

		//Tenemos que obtener a partir de la referencia recibida como parámetro de entrada,
		//toda la información relativa a esa referencia.
		List<PedidoAdicionalE> list = null;
		List<PedidoAdicionalE> listRespuesta = null;
		List<PedidoAdicionalE> listAux = new ArrayList<PedidoAdicionalE>();
		Locale locale = LocaleContextHolder.getLocale();
		PedidoAdicionalE resultado = new PedidoAdicionalE();

		try {

			pedidoAdicionalE.setEsCaprabo(utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode())); //Miramos si es un centro CAPRABO
			list = this.pedidoAdicionalEService.findAll(pedidoAdicionalE, session);

		} catch (Exception e) {
			//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
			resultado.setCodError(new String("1"));
			resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSBuscarPedidosE",null, locale));
			return resultado;
		}

		if (list != null && list.size()>0)
		{
			//Obtenemos el resultado.
			//resultado = list.get(0);

			//Miramos si la lista tiene mas de un pedido, si es asi,tenemos que quedarnos con el pedido que corresponde al 
			//seleccionado en el grid (IdentificadorSIA ó Identificador). 

			if (list.size()>1) {

				if ((pedidoAdicionalE.getIdentificadorSIA() != null) && (pedidoAdicionalE.getIdentificadorSIA() > 0)) {

					//Recorremos la lista para quedarnos con el que coincide con IdentificadorSIA
					for (int i =0;i<list.size();i++) {
						if (list.get(i).getIdentificadorSIA().toString().equals(pedidoAdicionalE.getIdentificadorSIA().toString())) {
							//Solo nos quedamo con el pedido que nos interesa
							listAux.add(0,list.get(i));		
						}
					}

				} else {
					if ((pedidoAdicionalE.getIdentificador() != null) && (pedidoAdicionalE.getIdentificador() > 0)) {

						//Recorremos la lista para quedarnos con el que coincide con Identificador
						for (int i =0;i<list.size();i++) {
							if (list.get(i).getIdentificador().toString().equals(pedidoAdicionalE.getIdentificador().toString())) {
								//Solo nos quedamo con el pedido que nos interesa
								listAux.add(0,list.get(i));		
							}
						}
					}
				}

				resultado = listAux.get(0);

			} else {

				resultado = list.get(0);
				listAux = list;
			}

			//Si es una referencia MODIFICABLE hay que validar.
			if (listAux != null && listAux.size()>0) {

				if (listAux.get(0).getModificable().equals("S")) {

					//Tenemos que validar el pedido para obtener la fecha mímima a poner.
					try {	
						if (listAux != null && listAux.size()>0)
						{
							listRespuesta = this.pedidoAdicionalEService.validateAll(listAux,session);
						} 

					} catch (Exception e) {
						//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
						resultado.setCodError(new String("1"));
						resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosE",null, locale));
						return resultado;
					}

					if (listRespuesta != null && listRespuesta.size()>0)
					{
						if (listRespuesta.get(0).getCodigoRespuesta().equals("1"))
						{
							//Se ha producido un error al validar a nivel del pedido
							resultado.setCodError(new String("1"));
							resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosE",null, locale));
							return resultado;
						}
						else
						{
							//Añadimos al resultado la fecha mínima.
							resultado.setFechaMinima(listRespuesta.get(0).getFecEntrega());
						}
					}
				}
			}
		}
		else
		{
			//No se ha encontrado la referencia, tenemos que devolver un resultado de que no existe la referencia.
			resultado.setClasePedido(pedidoAdicionalE.getClasePedido());
			resultado.setCodArticulo(pedidoAdicionalE.getCodArticulo());
			resultado.setIdentificador(pedidoAdicionalE.getIdentificador());
			resultado.setIdentificadorSIA(pedidoAdicionalE.getIdentificadorSIA());
			resultado.setCodError(new String("2"));
			return resultado;
		}

		//Calculo el stock actual
		try {
			ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
			BigInteger[] listaRef = {BigInteger.valueOf(pedidoAdicionalE.getCodArticulo())}; 
			requestType.setListaCodigosReferencia(listaRef);


			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: p40PedidoAdicionalController (openModifyDataGridE)	 #######");
				logger.error("###########################################################################################################");
			}
			ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
			if (responseType.getCodigoRespuesta().equals("OK")){
				for (ReferenciaType referencia : responseType.getListaReferencias()){
					if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(pedidoAdicionalE.getCodArticulo()))){
						if (referencia.getCodigoError().equals(new BigInteger("0"))){
							if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								resultado.setStock(referencia.getBandejas().doubleValue());
							} else {
								resultado.setStock(referencia.getStock().doubleValue());
							}
						} else {
							resultado.setStock(new Double(-9999));
						}
					}
				}
			} else {
				resultado.setStock(new Double(-9999));
			}
		} catch (Exception e) {
			resultado.setStock(new Double(-9999));
		}


		resultado.setCodError(new String("0"));

		//Comprobación de bloqueos
		this.comprobacionBloqueos(resultado, session);

		//Miramos si hay que mostrar los combos de excluir y cajas.
		//Solo se mostrarán si la referencia no es de tipo horizonte. 
		//Como los centros caprabo, son todos horizonte, nunca se mostrarán.
		//Comprobación de si es una referencia NO_ALI tratada por SIA
		VReferenciasPedirSIA vReferenciasPedirSIA = new VReferenciasPedirSIA();
		vReferenciasPedirSIA.setCodCentro(pedidoAdicionalE.getCodCentro());
		vReferenciasPedirSIA.setCodArt(pedidoAdicionalE.getCodArticulo());
		vReferenciasPedirSIA.setTipoPedido(new Integer(Constantes.CLASE_PEDIDO_ENCARGO));

		VReferenciasPedirSIA vReferenciasPedirSIARes = this.vReferenciasPedirSIAService.findOne(vReferenciasPedirSIA); 

		//Miramos si tenemos que mostrar o no cajas y excluir.
		if(vReferenciasPedirSIARes != null && Constantes.V_REFERENCIA_PEDIR_SIA_TIPO_HORIZONTE.equals(vReferenciasPedirSIARes.getTipo())){
			resultado.setShowExcluirAndCajas(false);			
		}
		else{
			resultado.setShowExcluirAndCajas(true);			
		}

		return resultado;
	}

	@RequestMapping(value = "/loadDataGridM", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMPagina loadDataGridM(
			@RequestBody PedidoAdicionalM pedidoAdicionalM,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "articulo", required = false) String codArticulo,
			@RequestParam(value = "identificador", required = false) String identificador,
			@RequestParam(value = "identificadorSIA", required = false) String identificadorSIA,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

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
			List<PedidoAdicionalM> list = null;
			List<CamposSeleccionadosM> listaSeleccionados = new ArrayList<CamposSeleccionadosM>();
			String esTecnico = "N";

			if (pedidoAdicionalM.getDescPeriodo() == null || pedidoAdicionalM.getDescPeriodo().equals("null")) {
				pedidoAdicionalM.setDescPeriodo(null);
			}						

			if (pedidoAdicionalM.getEspacioPromo() == null ||pedidoAdicionalM.getEspacioPromo().equals("null")) {
				pedidoAdicionalM.setEspacioPromo(null);
			}

			list = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),null,null,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());

			//La primera vez que cargamos tenemos que crear la lista con las referencias inicialmente no seleccionadas.
			CamposSeleccionadosM campoSelecc = new CamposSeleccionadosM();
			if (list != null) {
				//Nos recorremos la lista.
				PedidoAdicionalM campo = new PedidoAdicionalM();
				for (int i =0;i<list.size();i++)
				{
					campo = (PedidoAdicionalM)list.get(i);

					//Además creamos la lista con las referencias inicialmente no seleccionadas.
					campoSelecc = new CamposSeleccionadosM();
					campoSelecc.setCodArticulo(campo.getCodArticulo());
					campoSelecc.setIdentificador(campo.getIdentificador());
					campoSelecc.setEsPlanograma(campo.getEsPlanograma());
					campoSelecc.setSeleccionado("N");
					campoSelecc.setBorrable(campo.getBorrable());
					campoSelecc.setClasePedido(campo.getClasePedido());
					listaSeleccionados.add(i, campoSelecc);
					campoSelecc.setNoGestionaPbl(campo.getNoGestionaPbl());
					campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
					campoSelecc.setIdentificadorVegalsa(campo.getIdentificadorVegalsa());
				}
			}				

			//Volvemos a obtener la lista guardada en la tabla temporal.


			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

			//Montaje de lista paginada
			PedidoAdicionalMPagina pedidoAdicionalMPagina = new PedidoAdicionalMPagina();
			Page<PedidoAdicionalM> listaPedidoAdicionalMPaginada = null;

			if (list != null) {
				TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
				tPedidoAdicional.setIdSesion(session.getId());
				tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)));
				tPedidoAdicional.setCodCentro(pedidoAdicionalM.getCodCentro());
				tPedidoAdicional.setPantalla(Constantes.PANTALLA_LISTADOS);
				tPedidoAdicional.setMAC(pedidoAdicionalM.getMca());
				tPedidoAdicional.setDescPeriodo(pedidoAdicionalM.getDescPeriodo());
				tPedidoAdicional.setEspacioPromo(pedidoAdicionalM.getEspacioPromo());

				int records = list.size();
				listaPedidoAdicionalMPaginada = this.paginationManagerM.paginate(new Page<PedidoAdicionalM>(), list,
						max.intValue(), records, page.intValue());	
				pedidoAdicionalMPagina.setDatos(listaPedidoAdicionalMPaginada);
				pedidoAdicionalMPagina.setListadoComboOfertaPeriodo(this.tPedidoAdicionalService.findComboOfertaPeriodoMO(tPedidoAdicional));
				pedidoAdicionalMPagina.setListadoComboEspacioPromo(this.tPedidoAdicionalService.findComboEspacioPromoMO(tPedidoAdicional));
				pedidoAdicionalMPagina.setListadoSeleccionados(listaSeleccionados);
				pedidoAdicionalMPagina.setEsTecnico(esTecnico);
				pedidoAdicionalMPagina.setCodError(new Long(0));

			} else {
				pedidoAdicionalMPagina.setDatos(new Page<PedidoAdicionalM>());
			}
			return pedidoAdicionalMPagina;

		}
		else
		{
			return loadDataGridMRecarga(pedidoAdicionalM, codArticulo, identificador, identificadorSIA, page, max, index, sortOrder, response, session);
		}
	}



	private PedidoAdicionalM devuelvePedidoPlanograma(VPlanPedidoAdicional rowPlanograma, boolean esCentroCaprabo)  throws Exception{

		PedidoAdicionalM pedidoAdicionalM = new PedidoAdicionalM();

		if (rowPlanograma != null)
		{
			pedidoAdicionalM = new PedidoAdicionalM();

			pedidoAdicionalM.setCodCentro(rowPlanograma.getCodCentro());
			pedidoAdicionalM.setCodArticulo(rowPlanograma.getCodArt());
			pedidoAdicionalM.setDescriptionArt(rowPlanograma.getDescripArt());
			if (esCentroCaprabo) {
				pedidoAdicionalM.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(pedidoAdicionalM.getCodCentro(), pedidoAdicionalM.getCodArticulo()));
				pedidoAdicionalM.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(pedidoAdicionalM.getCodArticuloGrid()));
			} else {
				pedidoAdicionalM.setCodArticuloGrid(rowPlanograma.getCodArt());
				pedidoAdicionalM.setDescriptionArtGrid(rowPlanograma.getDescripArt());
			}

			pedidoAdicionalM.setGrupo1(rowPlanograma.getGrupo1());
			pedidoAdicionalM.setGrupo2(rowPlanograma.getGrupo2());
			pedidoAdicionalM.setGrupo3(rowPlanograma.getGrupo3());
			pedidoAdicionalM.setAgrupacion(rowPlanograma.getAgrupacion());
			pedidoAdicionalM.setUniCajaServ((rowPlanograma.getUniCajaServ() != null && !("".equals(rowPlanograma.getUniCajaServ().toString())))?rowPlanograma.getUniCajaServ():null);
			pedidoAdicionalM.setFechaInicio((rowPlanograma.getFechaInicio() != null)?Utilidades.formatearFecha(rowPlanograma.getFechaInicio()):null);
			pedidoAdicionalM.setFechaFin((rowPlanograma.getFechaFin() != null)?Utilidades.formatearFecha(rowPlanograma.getFechaFin()):null);
			pedidoAdicionalM.setCapMax((rowPlanograma.getImpInicial() != null && !("".equals(rowPlanograma.getImpInicial().toString())))?rowPlanograma.getImpInicial():null);
			pedidoAdicionalM.setCapMin((rowPlanograma.getImpFinal() != null && !("".equals(rowPlanograma.getImpFinal().toString())))?rowPlanograma.getImpFinal():null);
			pedidoAdicionalM.setPerfil(rowPlanograma.getPerfil());
			pedidoAdicionalM.setExcluir((rowPlanograma.getExcluir() != null && !("".equals(rowPlanograma.getExcluir().toString())))?(("N".equals(rowPlanograma.getExcluir().toString())?false:true)):null);
			pedidoAdicionalM.setEsPlanograma("S");
			pedidoAdicionalM.setNoGestionaPbl("N");
			pedidoAdicionalM.setTipoAprovisionamiento(rowPlanograma.getTipoAprovisionamiento());

			//Las planogramadas no son ni modificables ni borrables.
			pedidoAdicionalM.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
			pedidoAdicionalM.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
		}

		return pedidoAdicionalM;

	}

	private PedidoAdicionalM devuelvePedidoHTNoPbl(PedidoHTNoPbl rowPedidoHTNoPbl, boolean esCentroCaprabo)  throws Exception{

		PedidoAdicionalM pedidoAdicionalM = new PedidoAdicionalM();

		if (rowPedidoHTNoPbl != null)
		{
			pedidoAdicionalM = new PedidoAdicionalM();

			pedidoAdicionalM.setCodCentro(rowPedidoHTNoPbl.getCodCentro());
			pedidoAdicionalM.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE));
			pedidoAdicionalM.setCodArticulo(rowPedidoHTNoPbl.getCodArticulo());
			pedidoAdicionalM.setDescriptionArt(rowPedidoHTNoPbl.getDescriptionArt());

			if (esCentroCaprabo) {
				pedidoAdicionalM.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(pedidoAdicionalM.getCodCentro(), pedidoAdicionalM.getCodArticulo()));
				pedidoAdicionalM.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(pedidoAdicionalM.getCodArticuloGrid()));
			} else {
				pedidoAdicionalM.setCodArticuloGrid(rowPedidoHTNoPbl.getCodArticulo());
				pedidoAdicionalM.setDescriptionArtGrid(rowPedidoHTNoPbl.getDescriptionArt());
			}

			pedidoAdicionalM.setGrupo1(rowPedidoHTNoPbl.getGrupo1());
			pedidoAdicionalM.setGrupo2(rowPedidoHTNoPbl.getGrupo2());
			pedidoAdicionalM.setGrupo3(rowPedidoHTNoPbl.getGrupo3());
			pedidoAdicionalM.setAgrupacion(rowPedidoHTNoPbl.getAgrupacion());
			pedidoAdicionalM.setUniCajaServ((rowPedidoHTNoPbl.getUniCajaServ() != null && !("".equals(rowPedidoHTNoPbl.getUniCajaServ().toString())))?rowPedidoHTNoPbl.getUniCajaServ():null);
			pedidoAdicionalM.setFechaInicio((rowPedidoHTNoPbl.getFechaInicio() != null)?rowPedidoHTNoPbl.getFechaInicio():null);
			pedidoAdicionalM.setFechaFin((rowPedidoHTNoPbl.getFechaFin() != null)?rowPedidoHTNoPbl.getFechaFin():null);
			pedidoAdicionalM.setFechaMinima((rowPedidoHTNoPbl.getFechaInicio() != null)?rowPedidoHTNoPbl.getFechaInicio():null);
			pedidoAdicionalM.setCantMax((rowPedidoHTNoPbl.getCantMax() != null && !("".equals(rowPedidoHTNoPbl.getCantMax().toString())))?rowPedidoHTNoPbl.getCantMax():null);
			pedidoAdicionalM.setCantMin((rowPedidoHTNoPbl.getCantMin() != null && !("".equals(rowPedidoHTNoPbl.getCantMin().toString())))?rowPedidoHTNoPbl.getCantMin():null);
			pedidoAdicionalM.setPerfil(new Long(1));//Pet. 58538
			pedidoAdicionalM.setExcluir(true);//En la pantalla no aparecerá pero tendrá "S" por defecto
			pedidoAdicionalM.setEsPlanograma("N");
			pedidoAdicionalM.setNoGestionaPbl("S");
			pedidoAdicionalM.setIdentificador(rowPedidoHTNoPbl.getIdentificador());
			pedidoAdicionalM.setTipoAprovisionamiento(rowPedidoHTNoPbl.getTipoAprovisionamiento());
			if (rowPedidoHTNoPbl.getAnoOferta()!=null && !"".equals(rowPedidoHTNoPbl.getAnoOferta()) && rowPedidoHTNoPbl.getNumOferta()!=null && !"".equals(rowPedidoHTNoPbl.getNumOferta())){
				pedidoAdicionalM.setOferta(rowPedidoHTNoPbl.getAnoOferta()+"-"+rowPedidoHTNoPbl.getNumOferta());
			}
			pedidoAdicionalM.setTipoPedido((rowPedidoHTNoPbl.getTipoPedido() != null && !("".equals(rowPedidoHTNoPbl.getTipoPedido())))?rowPedidoHTNoPbl.getTipoPedido():null);
			pedidoAdicionalM.setFechaHasta((rowPedidoHTNoPbl.getFechaHasta() != null && !("".equals(rowPedidoHTNoPbl.getFechaHasta())))?rowPedidoHTNoPbl.getFechaHasta():null);
			pedidoAdicionalM.setDescOferta((rowPedidoHTNoPbl.getDescOferta() != null && !("".equals(rowPedidoHTNoPbl.getDescOferta())))?rowPedidoHTNoPbl.getDescOferta():null);
			pedidoAdicionalM.setFecha2((rowPedidoHTNoPbl.getFecha2() != null && !("".equals(rowPedidoHTNoPbl.getFecha2())))?rowPedidoHTNoPbl.getFecha2():null);
			pedidoAdicionalM.setFecha3((rowPedidoHTNoPbl.getFecha3() != null && !("".equals(rowPedidoHTNoPbl.getFecha3())))?rowPedidoHTNoPbl.getFecha3():null);
			pedidoAdicionalM.setFecha4((rowPedidoHTNoPbl.getFecha4() != null && !("".equals(rowPedidoHTNoPbl.getFecha4())))?rowPedidoHTNoPbl.getFecha4():null);
			pedidoAdicionalM.setFecha5((rowPedidoHTNoPbl.getFecha5() != null && !("".equals(rowPedidoHTNoPbl.getFecha5())))?rowPedidoHTNoPbl.getFecha5():null);
			pedidoAdicionalM.setCantidad1((rowPedidoHTNoPbl.getCantidad1() != null )? rowPedidoHTNoPbl.getCantidad1():null);
			pedidoAdicionalM.setCantidad2((rowPedidoHTNoPbl.getCantidad2() != null )? rowPedidoHTNoPbl.getCantidad2():null);
			pedidoAdicionalM.setCantidad3((rowPedidoHTNoPbl.getCantidad3() != null )? rowPedidoHTNoPbl.getCantidad3():null);
			pedidoAdicionalM.setCantidad4((rowPedidoHTNoPbl.getCantidad4() != null )? rowPedidoHTNoPbl.getCantidad4():null);
			pedidoAdicionalM.setCantidad5((rowPedidoHTNoPbl.getCantidad5() != null )? rowPedidoHTNoPbl.getCantidad5():null);

			//Las planogramadas no son ni modificables ni borrables.
			pedidoAdicionalM.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
			pedidoAdicionalM.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);

			//Cálculo de cantidades modificables. La fecha de la cantidad debe ser >= hoy + 1 día
			StringBuffer modificableIndivBuff = new StringBuffer();

			Calendar calendarDiaControl = Calendar.getInstance();
			calendarDiaControl.setTime(new Date()); 
			calendarDiaControl.set(Calendar.HOUR_OF_DAY, 0);
			calendarDiaControl.set(Calendar.MINUTE, 0);
			calendarDiaControl.set(Calendar.SECOND, 0);
			calendarDiaControl.set(Calendar.MILLISECOND, 0);
			Date diaControl = calendarDiaControl.getTime(); 

			if (rowPedidoHTNoPbl.getFechaInicio() != null && !"".equals(rowPedidoHTNoPbl.getFechaInicio())){
				if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFechaInicio()).after(diaControl)){
					modificableIndivBuff.append("S");
				}else{
					modificableIndivBuff.append("N");
				}
				if (rowPedidoHTNoPbl.getFecha2() != null && !"".equals(rowPedidoHTNoPbl.getFecha2())){
					if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFecha2()).after(diaControl)){
						modificableIndivBuff.append("S");
					}else{
						modificableIndivBuff.append("N");
					}
					if (rowPedidoHTNoPbl.getFecha3() != null && !"".equals(rowPedidoHTNoPbl.getFecha3())){
						if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFecha3()).after(diaControl)){
							modificableIndivBuff.append("S");
						}else{
							modificableIndivBuff.append("N");
						}
						if (rowPedidoHTNoPbl.getFecha4() != null && !"".equals(rowPedidoHTNoPbl.getFecha4())){
							if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFecha4()).after(diaControl)){
								modificableIndivBuff.append("S");
							}else{
								modificableIndivBuff.append("N");
							}
							if (rowPedidoHTNoPbl.getFecha5() != null && !"".equals(rowPedidoHTNoPbl.getFecha5())){
								if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFecha5()).after(diaControl)){
									modificableIndivBuff.append("S");
								}else{
									modificableIndivBuff.append("N");
								}
							}
						}
					}
				}
			}
			pedidoAdicionalM.setModificableIndiv(modificableIndivBuff.toString());
		}

		return pedidoAdicionalM;

	}

	private PedidoAdicionalMO devuelvePedidoHTNoPblOferta(PedidoHTNoPbl rowPedidoHTNoPbl,boolean esCentroCaprabo)  throws Exception{

		PedidoAdicionalMO pedidoAdicionalMO = new PedidoAdicionalMO();

		if (rowPedidoHTNoPbl != null)
		{
			pedidoAdicionalMO = new PedidoAdicionalMO();

			pedidoAdicionalMO.setCodCentro(rowPedidoHTNoPbl.getCodCentro());
			pedidoAdicionalMO.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
			pedidoAdicionalMO.setCodArticulo(rowPedidoHTNoPbl.getCodArticulo());
			pedidoAdicionalMO.setDescriptionArt(rowPedidoHTNoPbl.getDescriptionArt());

			if (esCentroCaprabo) {
				pedidoAdicionalMO.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(pedidoAdicionalMO.getCodCentro(), pedidoAdicionalMO.getCodArticulo()));
				pedidoAdicionalMO.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(pedidoAdicionalMO.getCodArticuloGrid()));
			} else {
				pedidoAdicionalMO.setCodArticuloGrid(rowPedidoHTNoPbl.getCodArticulo());
				pedidoAdicionalMO.setDescriptionArtGrid(rowPedidoHTNoPbl.getDescriptionArt());
			}

			pedidoAdicionalMO.setGrupo1(rowPedidoHTNoPbl.getGrupo1());
			pedidoAdicionalMO.setGrupo2(rowPedidoHTNoPbl.getGrupo2());
			pedidoAdicionalMO.setGrupo3(rowPedidoHTNoPbl.getGrupo3());
			pedidoAdicionalMO.setAgrupacion(rowPedidoHTNoPbl.getAgrupacion());
			pedidoAdicionalMO.setUniCajaServ((rowPedidoHTNoPbl.getUniCajaServ() != null && !("".equals(rowPedidoHTNoPbl.getUniCajaServ().toString())))?rowPedidoHTNoPbl.getUniCajaServ():null);
			pedidoAdicionalMO.setFechaInicio((rowPedidoHTNoPbl.getFechaInicio() != null)?rowPedidoHTNoPbl.getFechaInicio():null);
			pedidoAdicionalMO.setFechaFin((rowPedidoHTNoPbl.getFechaFin() != null)?rowPedidoHTNoPbl.getFechaFin():null);
			pedidoAdicionalMO.setCantMax((rowPedidoHTNoPbl.getCantMax() != null && !("".equals(rowPedidoHTNoPbl.getCantMax().toString())))?rowPedidoHTNoPbl.getCantMax():null);
			pedidoAdicionalMO.setCantMin((rowPedidoHTNoPbl.getCantMin() != null && !("".equals(rowPedidoHTNoPbl.getCantMin().toString())))?rowPedidoHTNoPbl.getCantMin():null);
			pedidoAdicionalMO.setPerfil(new Long(1));//Pet. 58538
			pedidoAdicionalMO.setExcluir(true);//En la pantalla no aparecerá pero tendrá "S" por defecto
			pedidoAdicionalMO.setEsPlanograma("N");
			pedidoAdicionalMO.setNoGestionaPbl("S");
			pedidoAdicionalMO.setIdentificador(rowPedidoHTNoPbl.getIdentificador());
			pedidoAdicionalMO.setTipoAprovisionamiento(rowPedidoHTNoPbl.getTipoAprovisionamiento());
			if (rowPedidoHTNoPbl.getAnoOferta()!=null && !"".equals(rowPedidoHTNoPbl.getAnoOferta()) && rowPedidoHTNoPbl.getNumOferta()!=null && !"".equals(rowPedidoHTNoPbl.getNumOferta())){
				pedidoAdicionalMO.setOferta(rowPedidoHTNoPbl.getAnoOferta()+"-"+rowPedidoHTNoPbl.getNumOferta());
			}
			pedidoAdicionalMO.setTipoPedido((rowPedidoHTNoPbl.getTipoPedido() != null && !("".equals(rowPedidoHTNoPbl.getTipoPedido())))?rowPedidoHTNoPbl.getTipoPedido():null);
			pedidoAdicionalMO.setFechaHasta((rowPedidoHTNoPbl.getFechaHasta() != null && !("".equals(rowPedidoHTNoPbl.getFechaHasta())))?rowPedidoHTNoPbl.getFechaHasta():null);
			pedidoAdicionalMO.setDescOferta((rowPedidoHTNoPbl.getDescOferta() != null && !("".equals(rowPedidoHTNoPbl.getDescOferta())))?rowPedidoHTNoPbl.getDescOferta():null);
			pedidoAdicionalMO.setFecha2((rowPedidoHTNoPbl.getFecha2() != null && !("".equals(rowPedidoHTNoPbl.getFecha2())))?rowPedidoHTNoPbl.getFecha2():null);
			pedidoAdicionalMO.setFecha3((rowPedidoHTNoPbl.getFecha3() != null && !("".equals(rowPedidoHTNoPbl.getFecha3())))?rowPedidoHTNoPbl.getFecha3():null);
			pedidoAdicionalMO.setFecha4((rowPedidoHTNoPbl.getFecha4() != null && !("".equals(rowPedidoHTNoPbl.getFecha4())))?rowPedidoHTNoPbl.getFecha4():null);
			pedidoAdicionalMO.setFecha5((rowPedidoHTNoPbl.getFecha5() != null && !("".equals(rowPedidoHTNoPbl.getFecha5())))?rowPedidoHTNoPbl.getFecha5():null);
			pedidoAdicionalMO.setCantidad1((rowPedidoHTNoPbl.getCantidad1() != null )? rowPedidoHTNoPbl.getCantidad1():null);
			pedidoAdicionalMO.setCantidad2((rowPedidoHTNoPbl.getCantidad2() != null )? rowPedidoHTNoPbl.getCantidad2():null);
			pedidoAdicionalMO.setCantidad3((rowPedidoHTNoPbl.getCantidad3() != null )? rowPedidoHTNoPbl.getCantidad3():null);
			pedidoAdicionalMO.setCantidad4((rowPedidoHTNoPbl.getCantidad4() != null )? rowPedidoHTNoPbl.getCantidad4():null);
			pedidoAdicionalMO.setCantidad5((rowPedidoHTNoPbl.getCantidad5() != null )? rowPedidoHTNoPbl.getCantidad5():null);

			//Las planogramadas no son ni modificables ni borrables.
			pedidoAdicionalMO.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
			pedidoAdicionalMO.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);

			//Cálculo de cantidades modificables. La fecha de la cantidad debe ser >= hoy + 1 día
			StringBuffer modificableIndivBuff = new StringBuffer();

			Calendar calendarDiaControl = Calendar.getInstance();
			calendarDiaControl.setTime(new Date()); 
			calendarDiaControl.set(Calendar.HOUR_OF_DAY, 0);
			calendarDiaControl.set(Calendar.MINUTE, 0);
			calendarDiaControl.set(Calendar.SECOND, 0);
			calendarDiaControl.set(Calendar.MILLISECOND, 0);
			Date diaControl = calendarDiaControl.getTime(); 

			if (rowPedidoHTNoPbl.getFechaInicio() != null && !"".equals(rowPedidoHTNoPbl.getFechaInicio())){
				if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFechaInicio()).after(diaControl)){
					modificableIndivBuff.append("S");
				}else{
					modificableIndivBuff.append("N");
				}
				if (rowPedidoHTNoPbl.getFecha2() != null && !"".equals(rowPedidoHTNoPbl.getFecha2())){
					if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFecha2()).after(diaControl)){
						modificableIndivBuff.append("S");
					}else{
						modificableIndivBuff.append("N");
					}
					if (rowPedidoHTNoPbl.getFecha3() != null && !"".equals(rowPedidoHTNoPbl.getFecha3())){
						if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFecha3()).after(diaControl)){
							modificableIndivBuff.append("S");
						}else{
							modificableIndivBuff.append("N");
						}
						if (rowPedidoHTNoPbl.getFecha4() != null && !"".equals(rowPedidoHTNoPbl.getFecha4())){
							if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFecha4()).after(diaControl)){
								modificableIndivBuff.append("S");
							}else{
								modificableIndivBuff.append("N");
							}
							if (rowPedidoHTNoPbl.getFecha5() != null && !"".equals(rowPedidoHTNoPbl.getFecha5())){
								if (Utilidades.convertirStringAFecha(rowPedidoHTNoPbl.getFecha5()).after(diaControl)){
									modificableIndivBuff.append("S");
								}else{
									modificableIndivBuff.append("N");
								}
							}
						}
					}
				}
			}
			pedidoAdicionalMO.setModificableIndiv(modificableIndivBuff.toString());
		}

		return pedidoAdicionalMO;
	}

	@RequestMapping(value = "/loadDataGridMRecarga", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMPagina loadDataGridMRecarga(
			@RequestBody PedidoAdicionalM pedidoAdicionalM,
			@RequestParam(value = "codArticulo", required = false) String codArticulo,
			@RequestParam(value = "identificador", required = false) String identificador,
			@RequestParam(value = "identificadorSIA", required = false) String identificadorSIA,
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

		List<PedidoAdicionalM> listaGuardada = null;
		List<PedidoAdicionalM> listaRecarga = new ArrayList<PedidoAdicionalM>();
		String esTecnico = "N";
		int records = 0;
		Long contadorMontaje =new Long(0);
		try {
			Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
			Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;

			if (pedidoAdicionalM.getDescPeriodo() == null || pedidoAdicionalM.getDescPeriodo().equals("null")) {
				pedidoAdicionalM.setDescPeriodo(null);
			}						

			if (pedidoAdicionalM.getEspacioPromo() == null ||pedidoAdicionalM.getEspacioPromo().equals("null")) {
				pedidoAdicionalM.setEspacioPromo(null);
			}

			listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),null,null,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());

			//Si me llega codArticulo, significa que lo tenemos que eliminar de la tabla temporal de sesión.
			if ((codArticulo != null)&&(!codArticulo.equals("")))
			{
				//Eliminamos de la tabla el registro
				this.eliminarArticuloTablaSesion(session.getId(), pedidoAdicionalM.getClasePedido(), pedidoAdicionalM.getCodCentro(), Long.parseLong(codArticulo),(identificador!=null && !"".equals(identificador) && !"null".equals(identificador)?Long.parseLong(identificador):null), (identificadorSIA!=null && !"".equals(identificadorSIA) && !"null".equals(identificadorSIA)?Long.parseLong(identificadorSIA):null));

				//Volvemos a obtener la lista
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),null,null,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());

				//Actualizamos el contador de montaje de sesión
				contadorMontaje = (Long)session.getAttribute("contadorMontaje");
				contadorMontaje = contadorMontaje - 1;
				session.setAttribute("contadorMontaje", contadorMontaje);
			}

			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),index, sortOrder,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());
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

			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<PedidoAdicionalM> listaPedidoAdicionalMPaginada = null;
		PedidoAdicionalMPagina pedidoAdicionalMPagina = new PedidoAdicionalMPagina();

		if (listaRecarga != null) {

			listaPedidoAdicionalMPaginada = this.paginationManagerM.paginate(new Page<PedidoAdicionalM>(), listaRecarga,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalMPagina.setDatos(listaPedidoAdicionalMPaginada);
			pedidoAdicionalMPagina.setListadoSeleccionados(pedidoAdicionalM.getListadoSeleccionados());
			pedidoAdicionalMPagina.setContadorMontaje(contadorMontaje);
			pedidoAdicionalMPagina.setEsTecnico(esTecnico);
			pedidoAdicionalMPagina.setCodError(new Long(0));

		} else {
			pedidoAdicionalMPagina.setDatos(new Page<PedidoAdicionalM>());
		}

		return pedidoAdicionalMPagina;
	}

	@RequestMapping(value = "/openModifyDataGridM", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalM openModifyDataGridM(
			@RequestBody PedidoAdicionalM pedidoAdicionalM,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User user = (User) session.getAttribute("user");

		//Tenemos que obtener a partir de la referencia recibida como parámetro de entrada,
		//toda la información relativa a esa referencia.
		List<PedidoAdicionalM> list = null;
		List<PedidoAdicionalM> listRespuesta = null;
		List<PedidoAdicionalM> listAux = new ArrayList<PedidoAdicionalM>();
		Locale locale = LocaleContextHolder.getLocale();
		PedidoAdicionalM resultado = new PedidoAdicionalM();


		pedidoAdicionalM.setEsCaprabo(utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode())); //Miramos si es un centro CAPRABO


		//Antes de nada tenemos que saber si se trata de una planogramada o no gestionada por Pbl o no para ir a buscarla al WS o a la vista.
		if (pedidoAdicionalM.getEsPlanograma() != null && pedidoAdicionalM.getEsPlanograma().equals("S") && pedidoAdicionalM.getIdentificadorSIA()==null){
			//En este caso sí lo es y tenemos que ir a la vista
			VPlanPedidoAdicional vPlanPedidoAdicional = new VPlanPedidoAdicional();
			vPlanPedidoAdicional.setCodCentro(pedidoAdicionalM.getCodCentro());
			vPlanPedidoAdicional.setCodArt(pedidoAdicionalM.getCodArticulo());
			vPlanPedidoAdicional.setFechaInicio(Utilidades.convertirStringAFecha(pedidoAdicionalM.getFechaInicio()));
			vPlanPedidoAdicional.setEsOferta(Constantes.NO_OFERTA);
			List<VPlanPedidoAdicional> listPlanogramas = null;
			listPlanogramas = this.vPlanPedidoAdicionalService.findAll(vPlanPedidoAdicional);

			if (listPlanogramas != null && listPlanogramas.size()>0)
			{
				resultado = this.devuelvePedidoPlanograma(listPlanogramas.get(0),pedidoAdicionalM.getEsCaprabo());
			}
		}else if (pedidoAdicionalM.getNoGestionaPbl() != null && pedidoAdicionalM.getNoGestionaPbl().equals("S") && pedidoAdicionalM.getIdentificadorSIA()==null){
			PedidoHTNoPbl pedidoHTNoPblMontAd = new PedidoHTNoPbl();
			pedidoHTNoPblMontAd.setCodCentro(pedidoAdicionalM.getCodCentro());
			pedidoHTNoPblMontAd.setClasePedido(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL));
			pedidoHTNoPblMontAd.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_SI);
			pedidoHTNoPblMontAd.setCodArticulo(pedidoAdicionalM.getCodArticulo());
			pedidoHTNoPblMontAd.setIdentificador(pedidoAdicionalM.getIdentificador());
			pedidoHTNoPblMontAd.setFechaInicio(pedidoAdicionalM.getFechaInicio());
			PedidoHTNoPblLista listaNoGestionadosPblMontAd = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblMontAd);

			if (listaNoGestionadosPblMontAd != null && listaNoGestionadosPblMontAd.getDatos() != null && listaNoGestionadosPblMontAd.getDatos().size()>0){
				resultado = this.devuelvePedidoHTNoPbl(listaNoGestionadosPblMontAd.getDatos().get(0),pedidoAdicionalM.getEsCaprabo());
			}
			
		}else{
			//En este caso no lo es y tenemos que ir al WS o a buscar a SIA los encargos reservas
			try {

				list = this.pedidoAdicionalMService.findAll(pedidoAdicionalM, session);
				final Boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), pedidoAdicionalM.getCodArticulo());
				if (tratamientoVegalsa){
					List<PedidoAdicionalM> resultadoVegalsa = this.pedidoAdicionalMService.findAllVegalsa(pedidoAdicionalM, session);
					
					List<PedidoAdicionalM> listaVegalsa = new ArrayList<PedidoAdicionalM>();
					
					// Inicio MISUMI-409
					for (PedidoAdicionalM rdoVegalsa:resultadoVegalsa){
						PedidoAdicionalM rdoNuevo = rdoVegalsa;
						VReferenciasNuevasVegalsa referenciasNuevasVegalsa = pedidoAdicionalNuevoService.getDatosValidacionVegalsa(new PedidoAdicionalNuevo(rdoVegalsa.getCodCentro(), rdoVegalsa.getCodArticulo()));
						if (referenciasNuevasVegalsa != null){
							rdoNuevo.setDiasMaximos(referenciasNuevasVegalsa.getDiasMaximos());
							rdoNuevo.setCantidadMaxima(referenciasNuevasVegalsa.getCantidadMaxima());
						}
						listaVegalsa.add(rdoNuevo);
					}
					
					if (listaVegalsa != null && !listaVegalsa.isEmpty() && listaVegalsa.get(0).getCodArticulo() != null){
						list.addAll(listaVegalsa);
					}else{
						list.addAll(resultadoVegalsa);
					}
					// FIN MISUMI-409
					
				}
				
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				resultado.setCodError(new String("1"));
				resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSBuscarPedidosM",null, locale));
				e.printStackTrace();
				return resultado;
			}

			if (list != null && list.size()>0){

				//Miramos si la lista tiene mas de un pedido, si es asi,tenemos que quedarnos con el pedido que corresponde al 
				//seleccionado en el grid (IdentificadorSIA ó Identificador). 

				if (list.size()>1) {

					if ((pedidoAdicionalM.getIdentificadorSIA() != null) && (pedidoAdicionalM.getIdentificadorSIA() > 0)) {

						//Recorremos la lista para quedarnos con el que coincide con IdentificadorSIA
						for (int i =0;i<list.size();i++) {
							if (list.get(i).getIdentificadorSIA().toString().equals(pedidoAdicionalM.getIdentificadorSIA().toString())) {
								//Solo nos quedamo con el pedido que nos interesa
								listAux.add(0,list.get(i));		
							}
						}

					} else {
						if ((pedidoAdicionalM.getIdentificador() != null) && (pedidoAdicionalM.getIdentificador() > 0)) {

							//Recorremos la lista para quedarnos con el que coincide con Identificador
							for (int i =0;i<list.size();i++) {
								if (list.get(i).getIdentificador().toString().equals(pedidoAdicionalM.getIdentificador().toString())) {
									//Solo nos quedamo con el pedido que nos interesa
									listAux.add(0,list.get(i));		
								}
							}
						}
					}

					resultado = listAux.get(0);

				} else {

					resultado = list.get(0);
					listAux = list;
				}


				//Si es una referencia MODIFICABLE O BORRABLE  hay que validar.
				if (listAux != null && listAux.size()>0) {

					if ((listAux.get(0).getModificableIndiv() != null && listAux.get(0).getModificableIndiv().indexOf("S")>=0) || (listAux.get(0).getBorrable().equals("S"))){
						PedidoAdicionalM pedido = listAux.get(0);
						// MISUMI-352
						if (pedido.getIdentificadorVegalsa()!=null){
							String fechaMinima = obtenerFechaMinimaVegalsa(pedido.getCodCentro(), pedido.getCodArticulo());
							if (fechaMinima!=null){
								resultado.setCodError(new String("0"));
								resultado.setFechaMinima(fechaMinima);
								resultado.setBloqueoPilada(pedido.getBloqueoPilada());
								resultado.setFechaInicio(pedido.getFechaInicio());
								resultado.setFechaFin(pedido.getFechaFin());
							}else{
								resultado.setCodError(new String("1"));
								resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosM",null, locale));
								return resultado;
							}
							
						}else{
							//Tenemos que validar el pedido para obtener la fecha mímima a poner.
							try {	
								if (listAux != null && listAux.size()>0)
								{
									listRespuesta = this.pedidoAdicionalMService.validateAll(listAux,session);
								}
							} catch (Exception e) {
								//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
								resultado.setCodError(new String("1"));
								resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosM",null, locale));
								return resultado;
							}

							if (listRespuesta != null && listRespuesta.size()>0)
							{
								if (listRespuesta.get(0).getCodigoRespuesta().equals("1"))
								{
									//Se ha producido un error al validar a nivel del pedido
									resultado.setCodError(new String("1"));
									resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosM",null, locale));
									return resultado;
								}
								else
								{
									//Añadimos al resultado la fecha mínima.
									resultado.setFechaMinima(listRespuesta.get(0).getFechaInicio());
									//Añadimos el bloqueo de pilada
									resultado.setBloqueoPilada(listRespuesta.get(0).getBloqueoPilada());
								}
							}
						}
						
					}
				}
			}
			else
			{
				//No se ha encontrado la referencia, tenemos que devolver un resultado de que no existe la referencia.
				resultado.setCodError(new String("2"));
				resultado.setClasePedido(pedidoAdicionalM.getClasePedido());
				resultado.setCodArticulo(pedidoAdicionalM.getCodArticulo());
				resultado.setIdentificador(pedidoAdicionalM.getIdentificador());
				resultado.setIdentificadorSIA(pedidoAdicionalM.getIdentificadorSIA());
				return resultado;
			}
		}
		resultado.setCodError(new String("0"));

		//Obtener referencia unitaria
		VRelacionArticulo relacionArticuloRes = null;
		VRelacionArticulo relacionArticulo = new VRelacionArticulo();

		relacionArticulo.setCodArtRela(resultado.getCodArticulo());
		relacionArticulo.setCodCentro(resultado.getCodCentro());
		relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);

		if (null != relacionArticuloRes){
			resultado.setReferenciaUnitaria(relacionArticuloRes.getCodArt());
		} else {
			resultado.setReferenciaUnitaria(resultado.getCodArticulo());
		}

		//Control de referencia nueva
		HistoricoVentaMedia historicoVentaMedia = new HistoricoVentaMedia();
		historicoVentaMedia.setCodLoc(resultado.getCodCentro());
		historicoVentaMedia.setCodArticulo(resultado.getReferenciaUnitaria());

		//HistoricoVentaMedia historicoVentaMediaAux = this.historicoVentaMediaService.findOne(historicoVentaMedia);
		//if (null == historicoVentaMediaAux ) {
		//resultado.setReferenciaNueva(true);
		//} else { 
		//historicoVentaMediaAux.recalcularVentasMedia();
		//if (historicoVentaMediaAux.getMedia().equals(new Float(0.0))){
		//resultado.setReferenciaNueva(true);
		//} else {
		resultado.setReferenciaNueva(false);
		//}
		//}

		//Calculo el stock actual
		try {
			ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
			BigInteger[] listaRef = {BigInteger.valueOf(pedidoAdicionalM.getCodArticulo())}; 
			requestType.setListaCodigosReferencia(listaRef);


			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: p40PedidoAdicionalController (openModifyDataGridM)	 #######");
				logger.error("###########################################################################################################");
			}
			ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
			if (responseType.getCodigoRespuesta().equals("OK")){
				for (ReferenciaType referencia : responseType.getListaReferencias()){
					if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(pedidoAdicionalM.getCodArticulo()))){
						if (referencia.getCodigoError().equals(new BigInteger("0"))){
							if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								resultado.setStock(referencia.getBandejas().doubleValue());
							} else {
								resultado.setStock(referencia.getStock().doubleValue());
							}
						} else {
							resultado.setStock(new Double(-9999));
						}
					}
				}
			} else {
				resultado.setStock(new Double(-9999));
			}
		} catch (Exception e) {
			resultado.setStock(new Double(-9999));
		}

		//Cálculo de fecha fin fijada por referencia nueva
		/*if (resultado.getReferenciaNueva()){
				Integer numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA;
				if (resultado.getBloqueoPilada()){
					numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA_BLOQUEO_PILADA;
				}
				VFestivoCentro vFestivoCentro = new VFestivoCentro();
				vFestivoCentro.setCodCentro(resultado.getCodCentro());
				vFestivoCentro.setFechaInicio(Utilidades.convertirStringAFecha(resultado.getFechaInicio()));
				resultado.setFechaFin(this.vFestivoCentroService.getFechaFinNuevaReferencia(vFestivoCentro, numDias));
			}*/

		//Comprobación de bloqueos
		this.comprobacionBloqueos(resultado, session);

		//Miramos si hay que mostrar los combos de excluir y cajas.
		//Solo se mostrarán si la referencia no es de tipo horizonte. 
		//Como los centros caprabo, son todos horizonte, nunca se mostrarán

		//Comprobación de si es una referencia NO_ALI tratada por SIA
		VReferenciasPedirSIA vReferenciasPedirSIA = new VReferenciasPedirSIA();
		vReferenciasPedirSIA.setCodCentro(pedidoAdicionalM.getCodCentro());
		vReferenciasPedirSIA.setCodArt(pedidoAdicionalM.getCodArticulo());
		vReferenciasPedirSIA.setTipoPedido(new Integer(Constantes.CLASE_PEDIDO_MONTAJE));

		VReferenciasPedirSIA vReferenciasPedirSIARes = this.vReferenciasPedirSIAService.findOne(vReferenciasPedirSIA); 

		//Miramos si tenemos que mostrar o no cajas y excluir.
		if(vReferenciasPedirSIARes != null && Constantes.V_REFERENCIA_PEDIR_SIA_TIPO_HORIZONTE.equals(vReferenciasPedirSIARes.getTipo())){
			resultado.setShowExcluirAndCajas(false);			
		}
		else{
			resultado.setShowExcluirAndCajas(true);			
		}


		return resultado;
	}

	private String obtenerFechaMinimaVegalsa(Long codCentro, Long codArticulo) throws Exception {
		return tPedidoAdicionalService.getPrimeraFechaVentaDisponible(codCentro, codArticulo);
	}

	@RequestMapping(value = "/loadDataGridMO", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMOPagina loadDataGridMO(
			@RequestBody PedidoAdicionalMO pedidoAdicionalMO,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "articulo", required = false) String codArticulo,
			@RequestParam(value = "identificador", required = false) String identificador,
			@RequestParam(value = "identificadorSIA", required = false) String identificadorSIA,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

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
			List<PedidoAdicionalMO> list = null;
			List<CamposSeleccionadosMO> listaSeleccionados = new ArrayList<CamposSeleccionadosMO>();
			String esTecnico = "N";

			if (pedidoAdicionalMO.getDescPeriodo() == null || pedidoAdicionalMO.getDescPeriodo().equals("null")) {
				pedidoAdicionalMO.setDescPeriodo(null);
			}
			String defaultDescPeriodo = null;
			defaultDescPeriodo = pedidoAdicionalMO.getDescPeriodo();


			if (pedidoAdicionalMO.getEspacioPromo() == null ||pedidoAdicionalMO.getEspacioPromo().equals("null")) {
				pedidoAdicionalMO.setEspacioPromo(null);
			}
			String defaultEspacioPromo = null;
			defaultEspacioPromo = pedidoAdicionalMO.getEspacioPromo();

			list = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),null,null);
			//La primera vez que cargamos tenemos que crear la lista con las referencias inicialmente no seleccionadas.
			CamposSeleccionadosMO campoSelecc = new CamposSeleccionadosMO();
			if (list != null) {
				//Nos recorremos la lista.
				PedidoAdicionalMO campo = new PedidoAdicionalMO();
				for (int i =0;i<list.size();i++)
				{
					campo = (PedidoAdicionalMO)list.get(i);

					//Además creamos la lista con las referencias inicialmente no seleccionadas.
					campoSelecc = new CamposSeleccionadosMO();
					campoSelecc.setCodArticulo(campo.getCodArticulo());
					campoSelecc.setIdentificador(campo.getIdentificador());
					campoSelecc.setSeleccionado("N");
					campoSelecc.setEsPlanograma(campo.getEsPlanograma());
					campoSelecc.setNoGestionaPbl(campo.getNoGestionaPbl());
					campoSelecc.setClasePedido(campo.getClasePedido());
					campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
					campoSelecc.setIdentificadorVegalsa(campo.getIdentificadorVegalsa());
					listaSeleccionados.add(i, campoSelecc);
				}
			}


			TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
			tPedidoAdicional.setIdSesion(session.getId());
			tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA)));
			tPedidoAdicional.setCodCentro(pedidoAdicionalMO.getCodCentro());
			tPedidoAdicional.setPantalla(Constantes.PANTALLA_LISTADOS);
			tPedidoAdicional.setMAC(pedidoAdicionalMO.getMca());
			tPedidoAdicional.setDescPeriodo(pedidoAdicionalMO.getDescPeriodo());
			tPedidoAdicional.setEspacioPromo(pedidoAdicionalMO.getEspacioPromo());


			//Volvemos a obtener la lista guardada en la tabla temporal.


			//Obtenemos de sesión el perfil del usuario. 
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

			//Montaje de lista paginada
			PedidoAdicionalMOPagina pedidoAdicionalMOPagina = new PedidoAdicionalMOPagina();
			Page<PedidoAdicionalMO> listaPedidoAdicionalMOPaginada = null;

			pedidoAdicionalMOPagina.setDefaultDescPeriodo(defaultDescPeriodo);
			pedidoAdicionalMOPagina.setDefaultEspacioPromo(defaultEspacioPromo);

			if (list != null) {
				int records = list.size();
				listaPedidoAdicionalMOPaginada = this.paginationManagerMO.paginate(new Page<PedidoAdicionalMO>(), list,
						max.intValue(), records, page.intValue());	
				pedidoAdicionalMOPagina.setDatos(listaPedidoAdicionalMOPaginada);
				pedidoAdicionalMOPagina.setListadoComboOfertaPeriodo(this.tPedidoAdicionalService.findComboOfertaPeriodoMO(tPedidoAdicional));
				pedidoAdicionalMOPagina.setListadoComboEspacioPromo(this.tPedidoAdicionalService.findComboEspacioPromoMO(tPedidoAdicional));
				pedidoAdicionalMOPagina.setListadoSeleccionados(listaSeleccionados);
				pedidoAdicionalMOPagina.setEsTecnico(esTecnico);
				pedidoAdicionalMOPagina.setCodError(new Long(0));

			} else {
				pedidoAdicionalMOPagina.setDatos(new Page<PedidoAdicionalMO>());
			}
			return pedidoAdicionalMOPagina;

		}
		else
		{
			if (pedidoAdicionalMO.getDescPeriodo() == null || pedidoAdicionalMO.getDescPeriodo().equals("null")) {
				pedidoAdicionalMO.setDescPeriodo(null);
			}
			if (pedidoAdicionalMO.getEspacioPromo() == null ||pedidoAdicionalMO.getEspacioPromo().equals("null")) {
				pedidoAdicionalMO.setEspacioPromo(null);
			}
			return loadDataGridMORecarga(pedidoAdicionalMO, codArticulo, identificador, identificadorSIA, page, max, index, sortOrder, response, session);
		}
	}


	private PedidoAdicionalMO devuelvePedidoPlanogramaOferta(VPlanPedidoAdicional rowPlanograma, boolean esCentroCaprabo)  throws Exception{

		PedidoAdicionalMO pedidoAdicionalMO = new PedidoAdicionalMO();

		if (rowPlanograma != null)
		{
			pedidoAdicionalMO = new PedidoAdicionalMO();

			pedidoAdicionalMO.setCodCentro(rowPlanograma.getCodCentro());
			pedidoAdicionalMO.setCodArticulo(rowPlanograma.getCodArt());
			pedidoAdicionalMO.setDescriptionArt(rowPlanograma.getDescripArt());
			if (esCentroCaprabo) {
				pedidoAdicionalMO.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(pedidoAdicionalMO.getCodCentro(), pedidoAdicionalMO.getCodArticulo()));
				pedidoAdicionalMO.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(pedidoAdicionalMO.getCodArticuloGrid()));
			} else {
				pedidoAdicionalMO.setCodArticuloGrid(rowPlanograma.getCodArt());
				pedidoAdicionalMO.setDescriptionArtGrid(rowPlanograma.getDescripArt());
			}
			pedidoAdicionalMO.setGrupo1(rowPlanograma.getGrupo1());
			pedidoAdicionalMO.setGrupo2(rowPlanograma.getGrupo2());
			pedidoAdicionalMO.setGrupo3(rowPlanograma.getGrupo3());
			pedidoAdicionalMO.setAgrupacion(rowPlanograma.getAgrupacion());
			pedidoAdicionalMO.setOferta(rowPlanograma.getAnoOferta()+"-"+((rowPlanograma.getCodOferta() != null && !("".equals(rowPlanograma.getCodOferta().toString())))?rowPlanograma.getCodOferta().toString():null));
			pedidoAdicionalMO.setUniCajaServ((rowPlanograma.getUniCajaServ() != null && !("".equals(rowPlanograma.getUniCajaServ().toString())))?rowPlanograma.getUniCajaServ():null);
			pedidoAdicionalMO.setFechaInicio((rowPlanograma.getFechaInicio() != null)?Utilidades.formatearFecha(rowPlanograma.getFechaInicio()):null);
			pedidoAdicionalMO.setFechaFin((rowPlanograma.getFechaFin() != null)?Utilidades.formatearFecha(rowPlanograma.getFechaFin()):null);
			pedidoAdicionalMO.setCapMax((rowPlanograma.getImpInicial() != null && !("".equals(rowPlanograma.getImpInicial().toString())))?rowPlanograma.getImpInicial():null);
			pedidoAdicionalMO.setCapMin((rowPlanograma.getImpFinal() != null && !("".equals(rowPlanograma.getImpFinal().toString())))?rowPlanograma.getImpFinal():null);
			pedidoAdicionalMO.setPerfil(rowPlanograma.getPerfil());
			pedidoAdicionalMO.setEsPlanograma("S");
			pedidoAdicionalMO.setNoGestionaPbl("N");
			pedidoAdicionalMO.setTipoAprovisionamiento(rowPlanograma.getTipoAprovisionamiento());

			//Las planogramadas no son ni modificables ni borrables.
			pedidoAdicionalMO.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
			pedidoAdicionalMO.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
		}

		return pedidoAdicionalMO;

	}

	@RequestMapping(value = "/loadDataGridMORecarga", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMOPagina loadDataGridMORecarga(
			@RequestBody PedidoAdicionalMO pedidoAdicionalMO,
			@RequestParam(value = "codArticulo", required = false) String codArticulo,
			@RequestParam(value = "identificador", required = false) String identificador,
			@RequestParam(value = "identificadorSIA", required = false) String identificadorSIA,
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

		List<PedidoAdicionalMO> listaGuardada = null;
		List<PedidoAdicionalMO> listaRecarga = new ArrayList<PedidoAdicionalMO>();
		String esTecnico = "N";
		int records = 0;
		Long contadorMontajeOferta = new Long(0);
		try {
			Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
			Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
			listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),null,null);

			//Si me llega codArticulo, significa que lo tenemos que eliminar de la tabla temporal de sesión.
			if ((codArticulo != null)&&(!codArticulo.equals("")))
			{
				//Eliminamos de la tabla el registro
				this.eliminarArticuloTablaSesion(session.getId(), pedidoAdicionalMO.getClasePedido(), pedidoAdicionalMO.getCodCentro(), Long.parseLong(codArticulo),(identificador!=null && !"".equals(identificador) && !"null".equals(identificador)?Long.parseLong(identificador):null),(identificadorSIA!=null && !"".equals(identificadorSIA) && !"null".equals(identificadorSIA)?Long.parseLong(identificadorSIA):null));

				//Volvemos a obtener la lista
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),null,null);

				//Actualizamos el contador de montaje de sesión
				contadorMontajeOferta = (Long)session.getAttribute("contadorMontajeOferta");
				contadorMontajeOferta = contadorMontajeOferta - 1;
				session.setAttribute("contadorMontajeOferta", contadorMontajeOferta);

			}

			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),index, sortOrder);
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

			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<PedidoAdicionalMO> listaPedidoAdicionalMOPaginada = null;
		PedidoAdicionalMOPagina pedidoAdicionalMOPagina = new PedidoAdicionalMOPagina();

		if (listaRecarga != null) {
			listaPedidoAdicionalMOPaginada = this.paginationManagerMO.paginate(new Page<PedidoAdicionalMO>(), listaRecarga,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalMOPagina.setDatos(listaPedidoAdicionalMOPaginada);
			pedidoAdicionalMOPagina.setListadoSeleccionados(pedidoAdicionalMO.getListadoSeleccionados());
			pedidoAdicionalMOPagina.setEsTecnico(esTecnico);
			pedidoAdicionalMOPagina.setContadorMontajeOferta(contadorMontajeOferta);
			pedidoAdicionalMOPagina.setCodError(new Long(0));

		} else {
			pedidoAdicionalMOPagina.setDatos(new Page<PedidoAdicionalMO>());
		}

		return pedidoAdicionalMOPagina; 
	}

	@RequestMapping(value = "/openModifyDataGridMO", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMO openModifyDataGridMO(
			@RequestBody PedidoAdicionalMO pedidoAdicionalMO,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{


		User user = (User) session.getAttribute("user");

		//Tenemos que obtener a partir de la referencia recibida como parámetro de entrada,
		//toda la información relativa a esa referencia.
		List<PedidoAdicionalMO> list = null;
		List<PedidoAdicionalMO> listRespuesta = null;
		List<PedidoAdicionalMO> listAux = new ArrayList<PedidoAdicionalMO>();
		Locale locale = LocaleContextHolder.getLocale();
		PedidoAdicionalMO resultado = new PedidoAdicionalMO();

		pedidoAdicionalMO.setEsCaprabo(utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode())); //Miramos si es un centro CAPRABO


		//Antes de nada tenemos que saber si se trata de una planogramada o no gestionada por PBL o no para ir a buscarla al WS o a la vista.
		if (pedidoAdicionalMO.getEsPlanograma() != null && pedidoAdicionalMO.getEsPlanograma().equals("S") && pedidoAdicionalMO.getIdentificadorSIA()==null)
		{
			//En este caso sí lo es y tenemos que ir a la vista
			VPlanPedidoAdicional vPlanPedidoAdicional = new VPlanPedidoAdicional();
			vPlanPedidoAdicional.setCodCentro(pedidoAdicionalMO.getCodCentro());
			vPlanPedidoAdicional.setCodArt(pedidoAdicionalMO.getCodArticulo());
			vPlanPedidoAdicional.setFechaInicio(Utilidades.convertirStringAFecha(pedidoAdicionalMO.getFechaInicio()));
			vPlanPedidoAdicional.setEsOferta(Constantes.SI_OFERTA);
			List<VPlanPedidoAdicional> listPlanogramas = null;
			listPlanogramas = this.vPlanPedidoAdicionalService.findAll(vPlanPedidoAdicional);

			if (listPlanogramas != null && listPlanogramas.size()>0)
			{
				resultado = this.devuelvePedidoPlanogramaOferta(listPlanogramas.get(0), pedidoAdicionalMO.getEsCaprabo());
			}
		}
		else if (pedidoAdicionalMO.getNoGestionaPbl() != null && pedidoAdicionalMO.getNoGestionaPbl().equals("S") && pedidoAdicionalMO.getIdentificadorSIA()==null){
			PedidoHTNoPbl pedidoHTNoPblMontAdOf = new PedidoHTNoPbl();
			pedidoHTNoPblMontAdOf.setCodCentro(pedidoAdicionalMO.getCodCentro());
			pedidoHTNoPblMontAdOf.setClasePedido(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA));
			pedidoHTNoPblMontAdOf.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_SI);
			pedidoHTNoPblMontAdOf.setCodArticulo(pedidoAdicionalMO.getCodArticulo());
			pedidoHTNoPblMontAdOf.setIdentificador(pedidoAdicionalMO.getIdentificador());
			PedidoHTNoPblLista listaNoGestionadosPblMontAdOf = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblMontAdOf);

			if (listaNoGestionadosPblMontAdOf != null && listaNoGestionadosPblMontAdOf.getDatos() != null && listaNoGestionadosPblMontAdOf.getDatos().size()>0)
			{
				resultado = this.devuelvePedidoHTNoPblOferta(listaNoGestionadosPblMontAdOf.getDatos().get(0),pedidoAdicionalMO.getEsCaprabo());
			}
		}
		else
		{
			//En este caso no lo es y tenemos que ir al WS o a buscar a SIA los encargos reservas
			try {
				list = this.pedidoAdicionalMOService.findAll(pedidoAdicionalMO, session);
				final Boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), pedidoAdicionalMO.getCodArticulo());
				if (tratamientoVegalsa){
					List<PedidoAdicionalMO> resultadoVegalsa = this.pedidoAdicionalMOService.findAllVegalsa(pedidoAdicionalMO, session);
					List<PedidoAdicionalMO> listaVegalsa = new ArrayList<PedidoAdicionalMO>();
					for (PedidoAdicionalMO rdoVegalsa:resultadoVegalsa){
						PedidoAdicionalMO rdoNuevo = rdoVegalsa;
						VReferenciasNuevasVegalsa referenciasNuevasVegalsa = pedidoAdicionalNuevoService.getDatosValidacionVegalsa(new PedidoAdicionalNuevo(rdoVegalsa.getCodCentro(), rdoVegalsa.getCodArticulo()));
						if (referenciasNuevasVegalsa != null){
							rdoNuevo.setDiasMaximos(referenciasNuevasVegalsa.getDiasMaximos());
							rdoNuevo.setCantidadMaxima(referenciasNuevasVegalsa.getCantidadMaxima());
						}
						listaVegalsa.add(rdoNuevo);
					}
					
					if (listaVegalsa != null && !listaVegalsa.isEmpty() && listaVegalsa.get(0).getCodArticulo() != null){
						list.addAll(listaVegalsa);
					}else{
						list.addAll(resultadoVegalsa);
					}
					//list.addAll(resultadoVegalsa);
				}
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				resultado.setCodError(new String("1"));
				resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSBuscarPedidosMO",null, locale));
				return resultado;
			}

			if (list != null && list.size()>0)
			{
				//Obtenemos el resultado.
				//resultado = list.get(0);

				//Miramos si la lista tiene mas de un pedido, si es asi,tenemos que quedarnos con el pedido que corresponde al 
				//seleccionado en el grid (IdentificadorSIA ó Identificador). 

				if (list.size()>1) {

					if ((pedidoAdicionalMO.getIdentificadorSIA() != null) && (pedidoAdicionalMO.getIdentificadorSIA() > 0)) {

						//Recorremos la lista para quedarnos con el que coincide con IdentificadorSIA
						for (int i =0;i<list.size();i++) {
							if (list.get(i).getIdentificadorSIA().toString().equals(pedidoAdicionalMO.getIdentificadorSIA().toString())) {
								//Solo nos quedamo con el pedido que nos interesa
								listAux.add(0,list.get(i));		
							}
						}

					} else {
						if ((pedidoAdicionalMO.getIdentificador() != null) && (pedidoAdicionalMO.getIdentificador() > 0)) {

							//Recorremos la lista para quedarnos con el que coincide con Identificador
							for (int i =0;i<list.size();i++) {
								if (list.get(i).getIdentificador().toString().equals(pedidoAdicionalMO.getIdentificador().toString())) {
									//Solo nos quedamo con el pedido que nos interesa
									listAux.add(0,list.get(i));		
								}
							}
						}
					}

					resultado = listAux.get(0);

				} else {

					resultado = list.get(0);
					listAux = list;
				}


				//Si es una referencia MODIFICABLE O BORRABLE  hay que validar.
				if (listAux != null && listAux.size()>0) {

					if ((listAux.get(0).getModificableIndiv() != null && listAux.get(0).getModificableIndiv().indexOf("S")>=0) || (listAux.get(0).getBorrable().equals("S"))){
						PedidoAdicionalMO pedido = listAux.get(0);
						// MISUMI-352
						if (pedido.getIdentificadorVegalsa()!=null){
							String fechaMinima = obtenerFechaMinimaVegalsa(pedido.getCodCentro(), pedido.getCodArticulo());
							if (fechaMinima!=null){
								resultado.setCodError(new String("0"));
								resultado.setFechaMinima(fechaMinima);
								resultado.setBloqueoPilada(pedido.getBloqueoPilada());
								resultado.setFechaInicio(pedido.getFechaInicio());
								resultado.setFechaFin(pedido.getFechaFin());
							}else{
								resultado.setCodError(new String("1"));
								resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosMO",null, locale));
							}
							
							return resultado;
							
						}else{
							//Tenemos que validar el pedido para obtener la fecha mímima a poner.
							try {	
								if (listAux != null && listAux.size()>0)
								{
									listRespuesta = this.pedidoAdicionalMOService.validateAll(listAux,session);
								}
								} catch (Exception e) {
								//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
									resultado.setCodError(new String("1"));
									resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosMO",null, locale));
									return resultado;
								}

							if (listRespuesta != null && listRespuesta.size()>0)
							{
								if (listRespuesta.get(0).getCodigoRespuesta().equals("1"))
								{
									//Se ha producido un error al validar a nivel del pedido
									resultado.setCodError(new String("1"));
									resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosMO",null, locale));
									return resultado;
								}
								else
								{
									//Añadimos al resultado la fecha mínima.
									resultado.setFechaMinima(listRespuesta.get(0).getFechaInicio());
									resultado.setBloqueoPilada(listRespuesta.get(0).getBloqueoPilada());
								}
							}
						
						}
					}
				}

			}
			else
			{
				//No se ha encontrado la referencia, tenemos que devolver un resultado de que no existe la referencia.
				resultado.setCodError(new String("2"));
				resultado.setClasePedido(pedidoAdicionalMO.getClasePedido());
				resultado.setCodArticulo(pedidoAdicionalMO.getCodArticulo());
				resultado.setIdentificador(pedidoAdicionalMO.getIdentificador());
				resultado.setIdentificadorSIA(pedidoAdicionalMO.getIdentificadorSIA());
				return resultado;
			}
		}
		if (resultado.getOferta() != null){
			VOfertaPa vOferta = new VOfertaPa();
			vOferta.setCodCentro(resultado.getCodCentro());
			String[] oferta = resultado.getOferta().trim().split("-");

			if (oferta.length == 2) { //La oferta tiene que estar compuesta por oferta 
				//y año oferta, si  no es asi no se considera que no tiene oferta

				vOferta.setAnoOferta(new Long(oferta[0]));
				vOferta.setNumOferta(new Long(oferta[1]));
				vOferta.setCodArt(resultado.getCodArticulo());
				VOfertaPa vOfertaAux = this.vOfertaPaService.findOneVigente(vOferta);

				if (null == vOfertaAux){
					VOfertaPaAyuda  vOfertaPaAyuda = new VOfertaPaAyuda();
					vOfertaPaAyuda.setCodCentro(vOferta.getCodCentro());
					vOfertaPaAyuda.setAnoOferta(vOferta.getAnoOferta());
					vOfertaPaAyuda.setNumOferta(vOferta.getNumOferta());
					vOfertaPaAyuda.setCodArt(vOferta.getCodArt());
					List<VOfertaPaAyuda> listVOfertaAyuda = null;
					if (null == vOfertaPaAyuda.getCodArt()){
						listVOfertaAyuda = new ArrayList<VOfertaPaAyuda>();
					} else {
						listVOfertaAyuda = this.vOfertaPaAyudaService.findCountNOVigentes(vOfertaPaAyuda, 1);
					}
					if (!listVOfertaAyuda.isEmpty()){
						VOfertaPaAyuda vOfertaPaAyudaAux = listVOfertaAyuda.get(0);
						resultado.setTipoOferta(vOfertaPaAyudaAux.getTipoOferta());
					} else {
						resultado.setTipoOferta(new Long(0));
					}

				} else {
					resultado.setTipoOferta(vOfertaAux.getTipoOferta());
				}
			}

		}

		//Calculo el stock actual
		try {
			ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
			BigInteger[] listaRef = {BigInteger.valueOf(pedidoAdicionalMO.getCodArticulo())}; 
			requestType.setListaCodigosReferencia(listaRef);


			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: p40PedidoAdicionalController (openModifyDataGridMO)	 #######");
				logger.error("###########################################################################################################");
			}
			ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
			if (responseType.getCodigoRespuesta().equals("OK")){
				for (ReferenciaType referencia : responseType.getListaReferencias()){
					if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(pedidoAdicionalMO.getCodArticulo()))){
						if (referencia.getCodigoError().equals(new BigInteger("0"))){
							if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								resultado.setStock(referencia.getBandejas().doubleValue());
							} else {
								resultado.setStock(referencia.getStock().doubleValue());
							}
						} else {
							resultado.setStock(new Double(-9999));
						}
					}
				}
			} else {
				resultado.setStock(new Double(-9999));
			}
		} catch (Exception e) {
			resultado.setStock(new Double(-9999));
		}

		resultado.setCodError(new String("0"));

		//Comprobación de bloqueos
		this.comprobacionBloqueos(resultado, session);

		return resultado;
	}

	@RequestMapping(value = "/removeDataGridE", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalEPagina removeDataGridE(
			@RequestBody PedidoAdicionalE pedidoAdicionalE,
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

		List<PedidoAdicionalE> listaGuardada = null;
		List<CamposSeleccionadosE> listaCamposSeleccionados = null;
		List<PedidoAdicionalE> listaPedidoAdicionalE = new ArrayList<PedidoAdicionalE>();
		List<PedidoAdicionalE> listaPedidoAdicionalERespuesta = new ArrayList<PedidoAdicionalE>();
		List<CamposSeleccionadosE> listaSeleccionados = new ArrayList<CamposSeleccionadosE>();

		String esTecnico = "N";
		Long contadorEncargos;

		Locale locale = LocaleContextHolder.getLocale();

		//Reseteamos todos los posibles errores previos.
		this.resetearErrores(session.getId(), pedidoAdicionalE.getListaFiltroClasePedido(), pedidoAdicionalE.getCodCentro());

		//En primer lugar nos recorremos la lista de registros seleccionados para montar la lista con los registros a eliminar.
		listaCamposSeleccionados = pedidoAdicionalE.getListadoSeleccionados();
		listaGuardada = this.obtenerTablaSesionE(session.getId(), pedidoAdicionalE.getCodCentro(),pedidoAdicionalE.getMca(),null,null);

		for (int i=0;i<listaCamposSeleccionados.size();i++){

			//Obtenemos el registro
			PedidoAdicionalE registroGuardado = pedidoAdicionalService.obtenerArticuloTablaSesionE(session.getId(),pedidoAdicionalE.getClasePedido(), pedidoAdicionalE.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA());

			if (listaCamposSeleccionados.get(i).getSeleccionado() != null && listaCamposSeleccionados.get(i).getSeleccionado().equals("S"))
			{
				listaPedidoAdicionalE.add(registroGuardado);
			}
		}
		try {	
			listaPedidoAdicionalERespuesta = this.pedidoAdicionalEService.removeAll(listaPedidoAdicionalE, session);
		} catch (Exception e) {
			//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
			PedidoAdicionalEPagina pedidoAdicionalEPagina = new PedidoAdicionalEPagina();
			pedidoAdicionalEPagina.setDatos(new Page<PedidoAdicionalE>());
			pedidoAdicionalEPagina.setCodError(new Long(1));
			pedidoAdicionalEPagina.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSBorrarPedidosE",null, locale));
			return pedidoAdicionalEPagina;
		}

		//Con la lista que nos devuelve el WS, nos tenemos que recorrer la lista guardada en sesión, quitando los 
		//eliminados correctamente y avisando en los que no se ha podido borrar.
		int regBorrados = 0;

		for (int j=0;j<listaPedidoAdicionalERespuesta.size();j++){

			if (listaPedidoAdicionalERespuesta.get(j).getCodArticulo() != null)
			{
				if (listaPedidoAdicionalERespuesta.get(j).getCodigoRespuesta() != null && 
						Integer.parseInt(listaPedidoAdicionalERespuesta.get(j).getCodigoRespuesta()) == 0 )
				{
					//Tenemos que eliminar el registro de la tabla temporal de sesión
					this.eliminarArticuloTablaSesion(session.getId(), listaPedidoAdicionalERespuesta.get(j).getClasePedido(), pedidoAdicionalE.getCodCentro(), listaPedidoAdicionalERespuesta.get(j).getCodArticulo(),listaPedidoAdicionalERespuesta.get(j).getIdentificador(),listaPedidoAdicionalERespuesta.get(j).getIdentificadorSIA());

					//Actualizamos el contador del registro borrado.
					regBorrados++;
				}
				else 
				{
					//Tenemos que añadir el código de error
					this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalERespuesta.get(j).getClasePedido(), pedidoAdicionalE.getCodCentro(), listaPedidoAdicionalERespuesta.get(j).getCodArticulo(),listaPedidoAdicionalERespuesta.get(j).getIdentificador(),listaPedidoAdicionalERespuesta.get(j).getIdentificadorSIA(), Constantes.BORRADO_ERRONEO_PANTALLA,listaPedidoAdicionalERespuesta.get(j).getDescripcionRespuesta());
				}
			}
		}

		//Volvemos a obtener la lista
		listaGuardada = this.obtenerTablaSesionE(session.getId(), pedidoAdicionalE.getCodCentro(),pedidoAdicionalE.getMca(),null,null);

		//Actualizamos el contador de encargos de sesión
		contadorEncargos = (Long)session.getAttribute("contadorEncargos");
		contadorEncargos = contadorEncargos - regBorrados;
		session.setAttribute("contadorEncargos", contadorEncargos);

		//Creamos la lista de seleccionados con la nueva lista con los registros sin seleccionar.
		CamposSeleccionadosE campoSelecc = new CamposSeleccionadosE();
		if (listaGuardada != null) {
			//Nos recorremos la lista.
			PedidoAdicionalE campo = new PedidoAdicionalE();
			for (int i =0;i<listaGuardada.size();i++)
			{
				campo = (PedidoAdicionalE)listaGuardada.get(i);

				//Además creamos la lista con las referencias inicialmente no seleccionadas.
				campoSelecc = new CamposSeleccionadosE();
				campoSelecc.setIndice(i);
				campoSelecc.setCodArticulo(campo.getCodArticulo());
				campoSelecc.setIdentificador(campo.getIdentificador());
				campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
				campoSelecc.setSeleccionado("N");
				listaSeleccionados.add(i, campoSelecc);
			}
		}

		//Obtenemos de sesión el perfil del usuario.
		User user = (User) session.getAttribute("user");
		if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
		{
			//En este caso se trata de un perfil técnico
			esTecnico = "S";
		}

		//Montaje de lista paginada
		PedidoAdicionalEPagina pedidoAdicionalEPagina = new PedidoAdicionalEPagina();
		Page<PedidoAdicionalE> listaPedidoAdicionalEPaginada = null;

		if (listaGuardada != null) {
			int records = listaGuardada.size();
			listaPedidoAdicionalEPaginada = this.paginationManagerE.paginate(new Page<PedidoAdicionalE>(), listaGuardada,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalEPagina.setDatos(listaPedidoAdicionalEPaginada);
			pedidoAdicionalEPagina.setListadoSeleccionados(listaSeleccionados);
			pedidoAdicionalEPagina.setEsTecnico(esTecnico);
			pedidoAdicionalEPagina.setContadorEncargos(contadorEncargos);
			pedidoAdicionalEPagina.setCodError(new Long(0));

		} else {
			pedidoAdicionalEPagina.setDatos(new Page<PedidoAdicionalE>());
		}
		return pedidoAdicionalEPagina;
	}

	@RequestMapping(value = "/removeDataGridM", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMPagina removeDataGridM(
			@RequestBody PedidoAdicionalM pedidoAdicionalM,
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

		List<PedidoAdicionalM> listaGuardada = null;
		List<CamposSeleccionadosM> listaCamposSeleccionados = null;
		List<PedidoAdicionalM> listaPedidoAdicionalM_Borrar = new ArrayList<PedidoAdicionalM>();
		List<PedidoAdicionalM> listaPedidoAdicionalM_BorrarMod = new ArrayList<PedidoAdicionalM>();
		List<PedidoAdicionalM> listaPedidoAdicionalM_BorrarBloq = new ArrayList<PedidoAdicionalM>();
		List<PedidoAdicionalM> listaPedidoAdicionalMRespuesta_Borrables = new ArrayList<PedidoAdicionalM>();
		List<PedidoAdicionalM> listaPedidoAdicionalMRespuesta_Borrables_Mod = new ArrayList<PedidoAdicionalM>();
		List<CamposSeleccionadosM> listaSeleccionados = new ArrayList<CamposSeleccionadosM>();

		String esTecnico = "N";
		Long contadorMontaje;

		Locale locale = LocaleContextHolder.getLocale();

		try {
			int regBorrados = 0;

			//Reseteamos todos los posibles errores previos.
			this.resetearErrores(session.getId(), pedidoAdicionalM.getListaFiltroClasePedido(), pedidoAdicionalM.getCodCentro());

			//En primer lugar nos recorremos la lista de registros seleccionados.
			listaCamposSeleccionados = pedidoAdicionalM.getListadoSeleccionados();

			//Tenemos que obtener las listas para el borrado atómico(listaPedidoAdicionalM_Borrar), y para 
			//el modificado de cantidades(listaPedidoAdicionalM_BorrarMod)
			for (int i=0;i<listaCamposSeleccionados.size();i++){

				if (listaCamposSeleccionados.get(i).getSeleccionado() != null && listaCamposSeleccionados.get(i).getSeleccionado().equals("S"))
				{
					if (listaCamposSeleccionados.get(i).getIdentificadorVegalsa()!=null){
						tPedidoAdicionalService.deletePedidosVegalsa(listaCamposSeleccionados.get(i).getIdentificadorVegalsa());
						regBorrados++;
					}else{
						//Obtenemos el registro
						PedidoAdicionalM registroGuardado = pedidoAdicionalService.obtenerArticuloTablaSesionM(session.getId(),listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA(),null);

						//Con el registro comprobamos el tipo de borrado que tenemos que realizar para 
						//añadirlo a la lista correspondiente.
						if (registroGuardado.getBorrable() != null && !registroGuardado.getBorrable().equals(""))
						{
							if (registroGuardado.getBorrable().equals(Constantes.PEDIDO_BORRABLE))
							{
								listaPedidoAdicionalM_Borrar.add(registroGuardado);
							}
							else if (registroGuardado.getBorrable().equals(Constantes.PEDIDO_BORRABLE_MODIF))
							{
								//Clonamos el objeto para evitar modificar la lista de sesión
								PedidoAdicionalM registroMod = registroGuardado.clone();

								//Ponemos las cantidades a cero, en función de cada línea si es S o N.
								char[] arrayModifIndiv = registroMod.getModificableIndiv().toCharArray();

								for (int x=0;x<arrayModifIndiv.length;x++){
									if (arrayModifIndiv[x] == 'S')
									{
										if (x == 0)
										{
											registroMod.setCantidad1(new Double(0));
										}
										else if (x == 1)
										{
											registroMod.setCantidad2(new Double(0));
										}
										else if (x == 2)
										{
											registroMod.setCantidad3(new Double(0));
										}
									}
								}
								listaPedidoAdicionalM_BorrarMod.add(registroMod);
							}else if(registroGuardado.getBorrable().equals(Constantes.PEDIDO_NO_BORRABLE)){
								listaPedidoAdicionalM_BorrarBloq.add(registroGuardado);
							}
						}
					}
					
					
				}
			}

			try {	
				//Hacemos el borrado atómico de los seleccionados y borrables.
				listaPedidoAdicionalMRespuesta_Borrables = this.pedidoAdicionalMService.removeAll(listaPedidoAdicionalM_Borrar, session);
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				PedidoAdicionalMPagina pedidoAdicionalMPagina = new PedidoAdicionalMPagina();
				pedidoAdicionalMPagina.setDatos(new Page<PedidoAdicionalM>());
				pedidoAdicionalMPagina.setCodError(new Long(1));
				pedidoAdicionalMPagina.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSBorrarPedidosM",null, locale));
				return pedidoAdicionalMPagina;
			}

			try {	
				//Hacemos el modificado de las cantidades de los seleccionados y borrables modificables.
				listaPedidoAdicionalMRespuesta_Borrables_Mod = this.pedidoAdicionalMService.modifyAll(listaPedidoAdicionalM_BorrarMod,Constantes.BORRADO_LOGICO, session);
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				PedidoAdicionalMPagina pedidoAdicionalMPagina = new PedidoAdicionalMPagina();
				pedidoAdicionalMPagina.setDatos(new Page<PedidoAdicionalM>());
				pedidoAdicionalMPagina.setCodError(new Long(1));
				pedidoAdicionalMPagina.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSModificarPedidosM",null, locale));
				return pedidoAdicionalMPagina;
			}

			//Con las listas que nos devuelve el WS, nos tenemos que recorrer la lista guardada en sesión, quitando los 
			//eliminados correctamente y avisando en los que no se ha podido borrar y/o modificar.

			for (int j=0;j<listaPedidoAdicionalMRespuesta_Borrables.size();j++){

				if (listaPedidoAdicionalMRespuesta_Borrables.get(j).getCodArticulo() != null)
				{
					if (listaPedidoAdicionalMRespuesta_Borrables.get(j).getCodigoRespuesta() != null && 
							Integer.parseInt(listaPedidoAdicionalMRespuesta_Borrables.get(j).getCodigoRespuesta()) == 0 )
					{
						//Tenemos que eliminar el registro de la tabla temporal de sesión
						this.eliminarArticuloTablaSesion(session.getId(), listaPedidoAdicionalMRespuesta_Borrables.get(j).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaPedidoAdicionalMRespuesta_Borrables.get(j).getCodArticulo(),listaPedidoAdicionalMRespuesta_Borrables.get(j).getIdentificador(),listaPedidoAdicionalMRespuesta_Borrables.get(j).getIdentificadorSIA());

						//Actualizamos el contador del registro borrado.
						regBorrados++;
					}
					else 
					{
						//Tenemos que añadir el código de error
						this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalMRespuesta_Borrables.get(j).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaPedidoAdicionalMRespuesta_Borrables.get(j).getCodArticulo(),listaPedidoAdicionalMRespuesta_Borrables.get(j).getIdentificador(),listaPedidoAdicionalMRespuesta_Borrables.get(j).getIdentificadorSIA(),Constantes.BORRADO_ERRONEO_PANTALLA,listaPedidoAdicionalMRespuesta_Borrables.get(j).getDescripcionRespuesta());
					}
				}
			}

			//Hacemos lo mismo con la lista que nos devuelve de modificados.
			for (int j=0;j<listaPedidoAdicionalMRespuesta_Borrables_Mod.size();j++){

				if (listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getCodArticulo() != null)
				{
					if (listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getCodigoRespuesta() != null && 
							Integer.parseInt(listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getCodigoRespuesta()) == 0 )
					{
						//Tenemos que eliminar el registro de la tabla temporal de sesión
						this.eliminarArticuloTablaSesion(session.getId(), listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getCodArticulo(),listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getIdentificador(),listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getIdentificadorSIA());

						//Actualizamos el contador del registro borrado.
						regBorrados++;
					}
					else
					{
						//Tenemos que añadir el código de error
						this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getCodArticulo(),listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getIdentificador(),listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getIdentificadorSIA(),Constantes.MODIFICADO_ERRONEO_PANTALLA,listaPedidoAdicionalMRespuesta_Borrables_Mod.get(j).getDescripcionRespuesta());
					}
				}
			}

			//Controlamos los pedidos no borrables para informar por pantalla.
			for (int j=0;j<listaPedidoAdicionalM_BorrarBloq.size();j++){

				if (listaPedidoAdicionalM_BorrarBloq.get(j).getCodArticulo() != null)
				{
					//Tenemos que añadir el código de error
					this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalM_BorrarBloq.get(j).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaPedidoAdicionalM_BorrarBloq.get(j).getCodArticulo(),listaPedidoAdicionalM_BorrarBloq.get(j).getIdentificador(),listaPedidoAdicionalM_BorrarBloq.get(j).getIdentificadorSIA(),Constantes.BORRADO_ERRONEO_BLOQUEADO_PANTALLA,listaPedidoAdicionalM_BorrarBloq.get(j).getDescripcionRespuesta());
				}
			}


			//Actualizamos el contador de montaje de sesión
			contadorMontaje = (Long)session.getAttribute("contadorMontaje");
			contadorMontaje = contadorMontaje - regBorrados;
			session.setAttribute("contadorMontaje", contadorMontaje);

			//Volvemos a obtener la lista
			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),index, sortOrder,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());
			}
			else
			{
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),null,null,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());
			}

			//Creamos la lista de seleccionados con la nueva lista con los registros sin seleccionar.
			CamposSeleccionadosM campoSelecc = new CamposSeleccionadosM();
			if (listaGuardada != null) {
				//Nos recorremos la lista.
				PedidoAdicionalM campo = new PedidoAdicionalM();
				for (int i =0;i<listaGuardada.size();i++)
				{
					campo = (PedidoAdicionalM)listaGuardada.get(i);

					//Además creamos la lista con las referencias inicialmente no selaccionadas.
					campoSelecc = new CamposSeleccionadosM();
					campoSelecc.setIndice(i);
					campoSelecc.setCodArticulo(campo.getCodArticulo());
					campoSelecc.setIdentificador(campo.getIdentificador());
					campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
					campoSelecc.setIdentificadorVegalsa(campo.getIdentificadorVegalsa());
					campoSelecc.setSeleccionado("N");
					campoSelecc.setClasePedido(campo.getClasePedido());
					listaSeleccionados.add(i, campoSelecc);
				}
			}
			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		//Montaje de lista paginada
		PedidoAdicionalMPagina pedidoAdicionalMPagina = new PedidoAdicionalMPagina();
		Page<PedidoAdicionalM> listaPedidoAdicionalMPaginada = null;

		if (listaGuardada != null) {
			int records = listaGuardada.size();
			listaPedidoAdicionalMPaginada = this.paginationManagerM.paginate(new Page<PedidoAdicionalM>(), listaGuardada,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalMPagina.setDatos(listaPedidoAdicionalMPaginada);
			pedidoAdicionalMPagina.setListadoSeleccionados(listaSeleccionados);
			pedidoAdicionalMPagina.setEsTecnico(esTecnico);
			pedidoAdicionalMPagina.setContadorMontaje(contadorMontaje);
			pedidoAdicionalMPagina.setCodError(new Long(0));

		} else {
			pedidoAdicionalMPagina.setDatos(new Page<PedidoAdicionalM>());
		}
		return pedidoAdicionalMPagina;
	}

	@RequestMapping(value = "/removeDataGridMO", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMOPagina removeDataGridMO(
			@RequestBody PedidoAdicionalMO pedidoAdicionalMO,
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

		List<PedidoAdicionalMO> listaGuardada = null;
		List<CamposSeleccionadosMO> listaCamposSeleccionados = null;
		List<PedidoAdicionalMO> listaPedidoAdicionalMO_Borrar = new ArrayList<PedidoAdicionalMO>();
		List<PedidoAdicionalMO> listaPedidoAdicionalMO_BorrarMod = new ArrayList<PedidoAdicionalMO>();
		List<PedidoAdicionalMO> listaPedidoAdicionalMORespuesta_Borrables = new ArrayList<PedidoAdicionalMO>();
		List<PedidoAdicionalMO> listaPedidoAdicionalMORespuesta_Borrables_Mod = new ArrayList<PedidoAdicionalMO>();
		List<PedidoAdicionalMO> listaPedidoAdicionalMO_BorrarBloq = new ArrayList<PedidoAdicionalMO>();
		List<CamposSeleccionadosMO> listaSeleccionados = new ArrayList<CamposSeleccionadosMO>();

		String esTecnico = "N";
		Long contadorMontajeOferta;

		Locale locale = LocaleContextHolder.getLocale();

		try {
			int regBorrados = 0;
			
			//Reseteamos todos los posibles errores previos.
			this.resetearErrores(session.getId(), pedidoAdicionalMO.getListaFiltroClasePedido(), pedidoAdicionalMO.getCodCentro());


			//En primer lugar nos recorremos la lista de registros seleccionados.
			listaCamposSeleccionados = pedidoAdicionalMO.getListadoSeleccionados();

			//Tenemos que obtener las listas para el borrado atómico(listaPedidoAdicionalM_Borrar), y para 
			//el modificado de cantidades(listaPedidoAdicionalM_BorrarMod)
			for (int i=0;i<listaCamposSeleccionados.size();i++){

				if (listaCamposSeleccionados.get(i).getSeleccionado() != null && listaCamposSeleccionados.get(i).getSeleccionado().equals("S"))
				{
					if (listaCamposSeleccionados.get(i).getIdentificadorVegalsa()!=null){
						tPedidoAdicionalService.deletePedidosVegalsa(listaCamposSeleccionados.get(i).getIdentificadorVegalsa());
						regBorrados++;
					}else{
						//Obtenemos el registro
						PedidoAdicionalMO registroGuardado = pedidoAdicionalService.obtenerArticuloTablaSesionMO(session.getId(),listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA(),null);

						//Con el registro comprobamos el tipo de borrado que tenemos que realizar para 
						//añadirlo a la lista correspondiente.
						if (registroGuardado.getBorrable() != null && !registroGuardado.getBorrable().equals(""))
						{
							if (registroGuardado.getBorrable().equals(Constantes.PEDIDO_BORRABLE))
							{
								listaPedidoAdicionalMO_Borrar.add(registroGuardado);
							}
							else if (registroGuardado.getBorrable().equals(Constantes.PEDIDO_BORRABLE_MODIF))
							{
								//Clonamos el objeto para evitar modificar la lista de sesión
								PedidoAdicionalMO registroMod = registroGuardado.clone();

								//Ponemos las cantidades a cero, en función de cada línea si es S o P.
								char[] arrayModifIndiv = registroMod.getModificableIndiv().toCharArray();

								for (int x=0;x<arrayModifIndiv.length;x++){
									if (arrayModifIndiv[x] == 'S')
									{
										if (x == 0)
										{
											registroMod.setCantidad1(new Double(0));
										}
										else if (x == 1)
										{
											registroMod.setCantidad2(new Double(0));
										}
										else if (x == 2)
										{
											registroMod.setCantidad3(new Double(0));
										}
									}
								}

								listaPedidoAdicionalMO_BorrarMod.add(registroMod);
							}else if(registroGuardado.getBorrable().equals(Constantes.PEDIDO_NO_BORRABLE)){
								listaPedidoAdicionalMO_BorrarBloq.add(registroGuardado);
							}
						}
					}
					
				}
			}

			try {	
				//Hacemos el borrado atómico de los seleccionados y borrables.
				listaPedidoAdicionalMORespuesta_Borrables.addAll(this.pedidoAdicionalMOService.removeAll(listaPedidoAdicionalMO_Borrar,session));
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				PedidoAdicionalMOPagina pedidoAdicionalMOPagina = new PedidoAdicionalMOPagina();
				pedidoAdicionalMOPagina.setDatos(new Page<PedidoAdicionalMO>());
				pedidoAdicionalMOPagina.setCodError(new Long(1));
				pedidoAdicionalMOPagina.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSBorrarPedidosMO",null, locale));
				return pedidoAdicionalMOPagina;
			}

			try {	
				//Hacemos el modificado de las cantidades de los seleccionados y borrables modificables.
				listaPedidoAdicionalMORespuesta_Borrables_Mod = this.pedidoAdicionalMOService.modifyAll(listaPedidoAdicionalMO_BorrarMod,Constantes.BORRADO_LOGICO, session);
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				PedidoAdicionalMOPagina pedidoAdicionalMOPagina = new PedidoAdicionalMOPagina();
				pedidoAdicionalMOPagina.setDatos(new Page<PedidoAdicionalMO>());
				pedidoAdicionalMOPagina.setCodError(new Long(1));
				pedidoAdicionalMOPagina.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSModificarPedidosMO",null, locale));
				return pedidoAdicionalMOPagina;
			}

			//Con las listas que nos devuelve el WS, nos tenemos que recorrer la lista guardada en sesión, quitando los 
			//eliminados correctamente y avisando en los que no se ha podido borrar y/o modificar.

			for (int j=0;j<listaPedidoAdicionalMORespuesta_Borrables.size();j++){

				if (listaPedidoAdicionalMORespuesta_Borrables.get(j).getCodArticulo() != null)
				{

					if (listaPedidoAdicionalMORespuesta_Borrables.get(j).getCodigoRespuesta() != null && 
							Integer.parseInt(listaPedidoAdicionalMORespuesta_Borrables.get(j).getCodigoRespuesta()) == 0 )
					{
						//Tenemos que eliminar el registro de la tabla temporal de sesión
						this.eliminarArticuloTablaSesion(session.getId(), listaPedidoAdicionalMORespuesta_Borrables.get(j).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaPedidoAdicionalMORespuesta_Borrables.get(j).getCodArticulo(),listaPedidoAdicionalMORespuesta_Borrables.get(j).getIdentificador(),listaPedidoAdicionalMORespuesta_Borrables.get(j).getIdentificadorSIA());

						//Actualizamos el contador del registro borrado.
						regBorrados++;
					}
					else
					{
						//Tenemos que añadir el código de error
						this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalMORespuesta_Borrables.get(j).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaPedidoAdicionalMORespuesta_Borrables.get(j).getCodArticulo(),listaPedidoAdicionalMORespuesta_Borrables.get(j).getIdentificador(),listaPedidoAdicionalMORespuesta_Borrables.get(j).getIdentificadorSIA(),Constantes.BORRADO_ERRONEO_PANTALLA,listaPedidoAdicionalMORespuesta_Borrables.get(j).getDescripcionRespuesta());
					}
				}
			}

			//Hacemos lo mismo con la lista que nos devuelve de modificados.
			for (int j=0;j<listaPedidoAdicionalMORespuesta_Borrables_Mod.size();j++){

				if (listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getCodArticulo() != null)
				{
					if (listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getCodigoRespuesta() != null && 
							Integer.parseInt(listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getCodigoRespuesta()) == 0 )
					{
						//Tenemos que eliminar el registro de la tabla temporal de sesión
						this.eliminarArticuloTablaSesion(session.getId(), listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getCodArticulo(),listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getIdentificador(),listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getIdentificadorSIA());

						//Actualizamos el contador del registro borrado.
						regBorrados++;

					}
					else
					{
						//Tenemos que añadir el código de error
						this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getCodArticulo(),listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getIdentificador(),listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getIdentificadorSIA(),Constantes.MODIFICADO_ERRONEO_PANTALLA,listaPedidoAdicionalMORespuesta_Borrables_Mod.get(j).getDescripcionRespuesta());
					}
				}
			}

			//Controlamos los pedidos no borrables para informar por pantalla.
			for (int j=0;j<listaPedidoAdicionalMO_BorrarBloq.size();j++){

				if (listaPedidoAdicionalMO_BorrarBloq.get(j).getCodArticulo() != null)
				{
					//Tenemos que añadir el código de error
					this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalMO_BorrarBloq.get(j).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaPedidoAdicionalMO_BorrarBloq.get(j).getCodArticulo(),listaPedidoAdicionalMO_BorrarBloq.get(j).getIdentificador(),listaPedidoAdicionalMO_BorrarBloq.get(j).getIdentificadorSIA(),Constantes.BORRADO_ERRONEO_BLOQUEADO_PANTALLA,listaPedidoAdicionalMO_BorrarBloq.get(j).getDescripcionRespuesta());
				}
			}


			//Actualizamos el contador de montaje en oferta de sesión
			contadorMontajeOferta = (Long)session.getAttribute("contadorMontajeOferta");
			contadorMontajeOferta = contadorMontajeOferta - regBorrados;
			session.setAttribute("contadorMontajeOferta", contadorMontajeOferta);

			//Volvemos a obtener la lista
			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),index, sortOrder);
			}
			else
			{
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),null,null);
			}
			//Creamos la lista de seleccionados con la nueva lista con los registros sin seleccionar.
			CamposSeleccionadosMO campoSelecc = new CamposSeleccionadosMO();
			if (listaGuardada != null) {
				//Nos recorremos la lista.
				PedidoAdicionalMO campo = new PedidoAdicionalMO();
				for (int i =0;i<listaGuardada.size();i++)
				{
					campo = (PedidoAdicionalMO)listaGuardada.get(i);

					//Además creamos la lista con las referencias inicialmente no selaccionadas.
					campoSelecc = new CamposSeleccionadosMO();
					campoSelecc.setIndice(i);
					campoSelecc.setCodArticulo(campo.getCodArticulo());
					campoSelecc.setIdentificador(campo.getIdentificador());
					campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
					campoSelecc.setIdentificadorVegalsa(campo.getIdentificadorVegalsa());
					campoSelecc.setSeleccionado("N");
					campoSelecc.setClasePedido(campo.getClasePedido());
					listaSeleccionados.add(i, campoSelecc);
				}
			}
			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		//Montaje de lista paginada
		PedidoAdicionalMOPagina pedidoAdicionalMOPagina = new PedidoAdicionalMOPagina();
		Page<PedidoAdicionalMO> listaPedidoAdicionalMOPaginada = null;

		if (listaGuardada != null) {
			int records = listaGuardada.size();
			listaPedidoAdicionalMOPaginada = this.paginationManagerMO.paginate(new Page<PedidoAdicionalMO>(), listaGuardada,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalMOPagina.setDatos(listaPedidoAdicionalMOPaginada);
			pedidoAdicionalMOPagina.setListadoSeleccionados(listaSeleccionados);
			pedidoAdicionalMOPagina.setEsTecnico(esTecnico);
			pedidoAdicionalMOPagina.setContadorMontajeOferta(contadorMontajeOferta);
			pedidoAdicionalMOPagina.setCodError(new Long(0));

		} else {
			pedidoAdicionalMOPagina.setDatos(new Page<PedidoAdicionalMO>());
		}
		return pedidoAdicionalMOPagina;
	}

	@RequestMapping(value = "/testModifyDataGridM", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMPagina testModifyDataGridM(
			@RequestBody PedidoAdicionalM pedidoAdicionalM,
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

		List<PedidoAdicionalM> listaGuardada = null;
		List<CamposSeleccionadosM> listaCamposSeleccionados = null;
		List<PedidoAdicionalM> listaPedidoAdicionalM_Mod = new ArrayList<PedidoAdicionalM>();
		List<PedidoAdicionalM> listaPedidoAdicionalMRespuesta_Mod = new ArrayList<PedidoAdicionalM>();
		List<CamposSeleccionadosM> listaSeleccionados = new ArrayList<CamposSeleccionadosM>();

		String esTecnico = "N";
		String esModificable = "N";
		Date maxFecInicio = null;
		String fechaFin = "";

		Locale locale = LocaleContextHolder.getLocale();

		try {
			//En primer lugar nos recorremos la lista de registros seleccionados.
			listaCamposSeleccionados = pedidoAdicionalM.getListadoSeleccionados();

			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),index, sortOrder,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());
			}
			else
			{
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),null,null,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());
			}

			//Antes de empezar tenemos que limpiar los posibles errores mostrados anteriormente.
			this.resetearErroresTesteo(session.getId(), pedidoAdicionalM.getListaFiltroClasePedido(), pedidoAdicionalM.getCodCentro());

			//Reseteamos todos los posibles errores previos.
			this.resetearErrores(session.getId(), pedidoAdicionalM.getListaFiltroClasePedido(), pedidoAdicionalM.getCodCentro());

			//Tenemos que comprobar que todos los seleccionados tienen la misma fecha fin.
			boolean fechasFinOk = true;
			boolean pedidosModif = true;
			boolean valFechasOk = true;
			boolean bloqueosOk = true;
			for (int i=0;i<listaCamposSeleccionados.size();i++){

				if (listaCamposSeleccionados.get(i).getSeleccionado() != null && listaCamposSeleccionados.get(i).getSeleccionado().equals("S"))
				{
					//Obtenemos el registro
					PedidoAdicionalM registroGuardado = pedidoAdicionalService.obtenerArticuloTablaSesionM(session.getId(), listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA(), listaCamposSeleccionados.get(i).getIdentificadorVegalsa());

					if (fechaFin.equals(""))
					{
						fechaFin = registroGuardado.getFechaFin();
					}
					else if (!fechaFin.equals(registroGuardado.getFechaFin()))
					{
						//En este caso el registro seleccionado tiene fecha fin distinta,
						//con lo que lo tenemos que deseleccionar y avisar del error al usuario.
						listaCamposSeleccionados.get(i).setSeleccionado("N");

						//Tenemos que añadir el código de que se ha modificado erroneamente el registro
						this.errorArticuloTablaSesion(session.getId(), listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA(),Constantes.MODIFICADO_FEC_ERRONEA_PANTALLA,"");

						//Ponemos el indicador de fecha a false.
						fechasFinOk = false;
					}
					if (fechasFinOk)
					{
						//Con el registro comprobamos si es modificable, en cuyo caso obtenemos la fecha de inicio de la pilada
						if (registroGuardado.getModificableIndiv() != null && !registroGuardado.getModificableIndiv().equals("") &&
								registroGuardado.getModificableIndiv().indexOf(Constantes.PEDIDO_MODIFICABLE_SI)>=0)
						{
							//Añadimos el registro a la lista para posteriormente llamar al WS de validación.
							listaPedidoAdicionalM_Mod.add(registroGuardado);

							//En este caso tenemos que guadar la fecha mayor
							if (Constantes.TIPO_PEDIDO_ENCARGO.equals(registroGuardado.getTipoPedido())){
								//Se trata de un fresco puro
								if (registroGuardado.getFechaPilada() != null && !registroGuardado.getFechaPilada().equals(""))
								{
									//Comprobación de bloqueo de pilada
									if ((maxFecInicio == null)||(Utilidades.convertirStringAFecha(registroGuardado.getFechaPilada()).after(maxFecInicio)))
									{
										maxFecInicio = Utilidades.convertirStringAFecha(registroGuardado.getFechaPilada());
									}
								}
							}
							else
							{
								//Se trata de alimentación
								if ((maxFecInicio == null)||(Utilidades.convertirStringAFecha(registroGuardado.getFechaInicio()).after(maxFecInicio)))
								{
									maxFecInicio = Utilidades.convertirStringAFecha(registroGuardado.getFechaInicio());
								}
							}
						}
						else
						{
							//Avisamos al usuario de que el registro no es modificable.
							this.errorArticuloTablaSesion(session.getId(), listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA(),Constantes.MODIFICADO_NO_MODIFICABLE,"");

							//Deseleccionamos el registro
							listaCamposSeleccionados.get(i).setSeleccionado("N");

							//Por lo menos hay un pedido no modificable.
							pedidosModif = false;
						}
					}
				}
			}
			if (fechasFinOk && pedidosModif && bloqueosOk)
			{
				try {	
					//Ahora tenemos que validar la lista de pedidos a modificar, si falla alguna validación tampoco se permitirá la
					//modificación.
					listaPedidoAdicionalMRespuesta_Mod = this.pedidoAdicionalMService.validateAll(listaPedidoAdicionalM_Mod,session);
				} catch (Exception e) {
					//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
					PedidoAdicionalMPagina pedidoAdicionalMPagina = new PedidoAdicionalMPagina();
					pedidoAdicionalMPagina.setDatos(new Page<PedidoAdicionalM>());
					pedidoAdicionalMPagina.setCodError(new Long(1));
					pedidoAdicionalMPagina.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosM",null, locale));
					return pedidoAdicionalMPagina;
				}

				if (listaPedidoAdicionalMRespuesta_Mod != null && listaPedidoAdicionalMRespuesta_Mod.size()>0)
				{
					for (int i=0;i<listaPedidoAdicionalMRespuesta_Mod.size();i++){

						if (listaPedidoAdicionalMRespuesta_Mod.get(i).getCodigoRespuesta() != null && 
								Integer.parseInt(listaPedidoAdicionalMRespuesta_Mod.get(i).getCodigoRespuesta()) == 0 )
						{
							//Si la validación de algún pedido ha fallado no es necesario seguir obteniendo fechas.
							if (valFechasOk)
							{
								//Obtenemos la fecha de inicio y si es mayor que la que teníamos, la guardamos.
								if ((maxFecInicio == null)||(Utilidades.convertirStringAFecha(listaPedidoAdicionalMRespuesta_Mod.get(i).getFechaInicio()).after(maxFecInicio)))
								{
									maxFecInicio = Utilidades.convertirStringAFecha(listaPedidoAdicionalMRespuesta_Mod.get(i).getFechaInicio());
								}
							}
						}
						else
						{
							//En este caso se ha producido un error en el WS y tendremos que avisar al usuario.
							//Tenemos que añadir el código de que se ha producido un error al validar el pedido e intentar obtener la fecha de inicio.
							this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalMRespuesta_Mod.get(i).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaPedidoAdicionalMRespuesta_Mod.get(i).getCodArticulo(),listaPedidoAdicionalMRespuesta_Mod.get(i).getIdentificador(),listaPedidoAdicionalMRespuesta_Mod.get(i).getIdentificadorSIA(),Constantes.MODIFICADO_FEC_INICIO_ERROR,listaPedidoAdicionalMRespuesta_Mod.get(i).getDescripcionRespuesta());

							//Deseleccionamos el registro
							for (int j=0;i<listaCamposSeleccionados.size();j++){
								if (listaCamposSeleccionados.get(j).getSeleccionado() != null && 
										listaCamposSeleccionados.get(j).getSeleccionado().equals("S") &&
										listaCamposSeleccionados.get(j).getClasePedido() == listaPedidoAdicionalMRespuesta_Mod.get(i).getClasePedido() &&
										listaCamposSeleccionados.get(j).getCodArticulo() == listaPedidoAdicionalMRespuesta_Mod.get(i).getCodArticulo())
								{
									listaCamposSeleccionados.get(j).setSeleccionado("N");
								}
							}

							valFechasOk = false;
						}
					}
				}

				if (valFechasOk)
				{
					//En este caso se cumple que todos los pedidos seleccionados tienen la misma fecha fin y son modificables,
					//seguimos con la modificación.
					esModificable = "S";

					//Además tenemos que limpiar los posibles errores mostrados anteriormente.
					this.resetearErroresTesteo(session.getId(), pedidoAdicionalM.getListaFiltroClasePedido(), pedidoAdicionalM.getCodCentro());
				}
			}
			listaSeleccionados = listaCamposSeleccionados;

			//Volvemos a obtener la lista guardada en la tabla temporal.
			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),index, sortOrder,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());
			}
			else
			{
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),null,null,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());
			}

			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		//Montaje de lista paginada
		PedidoAdicionalMPagina pedidoAdicionalMPagina = new PedidoAdicionalMPagina();
		Page<PedidoAdicionalM> listaPedidoAdicionalMPaginada = null;

		if (listaGuardada != null) {
			int records = listaGuardada.size();
			listaPedidoAdicionalMPaginada = this.paginationManagerM.paginate(new Page<PedidoAdicionalM>(), listaGuardada,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalMPagina.setDatos(listaPedidoAdicionalMPaginada);
			pedidoAdicionalMPagina.setListadoSeleccionados(listaSeleccionados);
			pedidoAdicionalMPagina.setEsTecnico(esTecnico);
			pedidoAdicionalMPagina.setEsModificable(esModificable);
			pedidoAdicionalMPagina.setCodError(new Long(0));
			//Se rellena la fecha fin de la selección
			pedidoAdicionalMPagina.setFechaFin(fechaFin);
			if (maxFecInicio != null)
			{
				pedidoAdicionalMPagina.setFechaMinima(Utilidades.formatearFecha(maxFecInicio));
			}

		} else {
			pedidoAdicionalMPagina.setDatos(new Page<PedidoAdicionalM>());
		}
		return pedidoAdicionalMPagina;
	}	

	@RequestMapping(value = "/modifyDataGridM", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMPagina modifyDataGridM(
			@RequestBody PedidoAdicionalM pedidoAdicionalM,
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

		List<PedidoAdicionalM> listaGuardada = null;
		List<CamposSeleccionadosM> listaCamposSeleccionados = null;
		List<PedidoAdicionalM> listaPedidoAdicionalM_Mod = new ArrayList<PedidoAdicionalM>();
		List<PedidoAdicionalM> listaPedidoAdicionalMRespuesta_Mod = new ArrayList<PedidoAdicionalM>();
		List<CamposSeleccionadosM> listaSeleccionados = new ArrayList<CamposSeleccionadosM>();

		String esTecnico = "N";

		Locale locale = LocaleContextHolder.getLocale();

		try {
			//Antes de empezar tenemos que limpiar los posibles guardados mostrados anteriormente.
			this.resetearGuardados(session.getId(), pedidoAdicionalM.getListaFiltroClasePedido(), pedidoAdicionalM.getCodCentro());

			//En primer lugar nos recorremos la lista de registros seleccionados.
			listaCamposSeleccionados = pedidoAdicionalM.getListadoSeleccionados();


			//Tenemos que obtener la lista para la modificación múltiple de la fecha fin
			for (int i=0;i<listaCamposSeleccionados.size();i++){

				if (listaCamposSeleccionados.get(i).getSeleccionado() != null && listaCamposSeleccionados.get(i).getSeleccionado().equals("S"))
				{
					//Obtenemos el registro
					PedidoAdicionalM registroGuardado = pedidoAdicionalService.obtenerArticuloTablaSesionM(session.getId(), listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(), listaCamposSeleccionados.get(i).getIdentificador(), listaCamposSeleccionados.get(i).getIdentificadorSIA(), listaCamposSeleccionados.get(i).getIdentificadorVegalsa());

					//Clonamos el objeto para evitar modificar la lista de sesión
					PedidoAdicionalM registroMod = registroGuardado.clone();

					//Actualizamos la fecha fin.
					if (pedidoAdicionalM.getFechaFin()!= null && !pedidoAdicionalM.getFechaFin().equals(""))
					{
						//registroMod.setFechaPilada(registroMod.getFechaFin());
						registroMod.setFechaFin(pedidoAdicionalM.getFechaFin().replaceAll("/", ""));

					}

					//Añadimos el registro a la lista a enviar al WS.
					listaPedidoAdicionalM_Mod.add(registroMod);
				}
			}

			try {	
				//Hacemos el modificado de la fecha fin de los pedidos seleccionados
				listaPedidoAdicionalMRespuesta_Mod = this.pedidoAdicionalMService.modifyAll(listaPedidoAdicionalM_Mod,Constantes.MODIFICADO, session);
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				PedidoAdicionalMPagina pedidoAdicionalMPagina = new PedidoAdicionalMPagina();
				pedidoAdicionalMPagina.setDatos(new Page<PedidoAdicionalM>());
				pedidoAdicionalMPagina.setCodError(new Long(1));
				pedidoAdicionalMPagina.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSModificarPedidosM",null, locale));
				return pedidoAdicionalMPagina;
			}


			//Con las listas que nos devuelve el WS, nos tenemos que recorrer la lista guardada en sesión, 
			//avisando en los que no se ha podido modificar.

			for (int j=0;j<listaPedidoAdicionalMRespuesta_Mod.size();j++){

				if (listaPedidoAdicionalMRespuesta_Mod.get(j).getCodArticulo() != null)
				{
					if (listaPedidoAdicionalMRespuesta_Mod.get(j).getCodigoRespuesta() != null && 
							Integer.parseInt(listaPedidoAdicionalMRespuesta_Mod.get(j).getCodigoRespuesta()) == 0 )
					{
						//En este caso hemos actualizado la fecha fin, lo hacemos en el objeto de sesión también.							
						this.modificarArticuloFechaFinTablaSesion(session.getId(), listaPedidoAdicionalMRespuesta_Mod.get(j).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaPedidoAdicionalMRespuesta_Mod.get(j).getCodArticulo(),listaPedidoAdicionalMRespuesta_Mod.get(j).getIdentificador(),listaPedidoAdicionalMRespuesta_Mod.get(j).getIdentificadorSIA(),Constantes.MODIFICADO_CORRECTO_PANTALLA, listaPedidoAdicionalMRespuesta_Mod.get(j).getFechaFin());

					}
					else
					{
						//Tenemos que añadir el código de que se ha modificado erroneamente el registro
						this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalMRespuesta_Mod.get(j).getClasePedido(), pedidoAdicionalM.getCodCentro(), listaPedidoAdicionalMRespuesta_Mod.get(j).getCodArticulo(),listaPedidoAdicionalMRespuesta_Mod.get(j).getIdentificador(),listaPedidoAdicionalMRespuesta_Mod.get(j).getIdentificadorSIA(),Constantes.MODIFICADO_ERRONEO_PANTALLA,listaPedidoAdicionalMRespuesta_Mod.get(j).getDescripcionRespuesta());
					}
				}
			}

			//Volvemos a obtener la lista
			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),index, sortOrder,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());
			}
			else
			{
				listaGuardada = this.obtenerTablaSesionM(session.getId(), pedidoAdicionalM.getCodCentro(),pedidoAdicionalM.getMca(),null,null,pedidoAdicionalM.getDescPeriodo(),pedidoAdicionalM.getEspacioPromo());
			}

			//Una vez realizadas las modificaciones creamos la lista de seleccionados con los registros sin seleccionar.
			CamposSeleccionadosM campoSelecc = new CamposSeleccionadosM();
			if (listaGuardada != null) {
				//Nos recorremos la lista.
				PedidoAdicionalM campo = new PedidoAdicionalM();
				for (int i =0;i<listaGuardada.size();i++)
				{
					campo = (PedidoAdicionalM)listaGuardada.get(i);

					//Además creamos la lista con las referencias inicialmente no seleccionadas.
					campoSelecc = new CamposSeleccionadosM();
					campoSelecc.setIndice(i);
					campoSelecc.setCodArticulo(campo.getCodArticulo());
					campoSelecc.setIdentificador(campo.getIdentificador());
					campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
					campoSelecc.setIdentificadorVegalsa(campo.getIdentificadorVegalsa());
					campoSelecc.setSeleccionado("N");
					campoSelecc.setClasePedido(campo.getClasePedido());
					listaSeleccionados.add(i, campoSelecc);
				}
			}

			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		//Montaje de lista paginada
		PedidoAdicionalMPagina pedidoAdicionalMPagina = new PedidoAdicionalMPagina();
		Page<PedidoAdicionalM> listaPedidoAdicionalMPaginada = null;

		if (listaGuardada != null) {
			int records = listaGuardada.size();
			listaPedidoAdicionalMPaginada = this.paginationManagerM.paginate(new Page<PedidoAdicionalM>(), listaGuardada,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalMPagina.setDatos(listaPedidoAdicionalMPaginada);
			pedidoAdicionalMPagina.setListadoSeleccionados(listaSeleccionados);
			pedidoAdicionalMPagina.setEsTecnico(esTecnico);
			pedidoAdicionalMPagina.setCodError(new Long(0));

		} else {
			pedidoAdicionalMPagina.setDatos(new Page<PedidoAdicionalM>());
		}
		return pedidoAdicionalMPagina;
	}	


	@RequestMapping(value = "/testModifyDataGridMO", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMOPagina testModifyDataGridMO(
			@RequestBody PedidoAdicionalMO pedidoAdicionalMO,
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

		List<PedidoAdicionalMO> listaGuardada = null;
		List<CamposSeleccionadosMO> listaCamposSeleccionados = null;
		List<PedidoAdicionalMO> listaPedidoAdicionalMO_Mod = new ArrayList<PedidoAdicionalMO>();
		List<PedidoAdicionalMO> listaPedidoAdicionalMORespuesta_Mod = new ArrayList<PedidoAdicionalMO>();
		List<CamposSeleccionadosMO> listaSeleccionados = new ArrayList<CamposSeleccionadosMO>();

		String esTecnico = "N";
		String esModificable = "N";
		Date maxFecInicio = null;
		String fechaFin = "";

		Locale locale = LocaleContextHolder.getLocale();

		try {
			//En primer lugar nos recorremos la lista de registros seleccionados.
			listaCamposSeleccionados = pedidoAdicionalMO.getListadoSeleccionados();
			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),index, sortOrder);
			}
			else
			{
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),null,null);
			}


			//Antes de empezar tenemos que limpiar los posibles errores mostrados anteriormente.
			this.resetearErroresTesteo(session.getId(), pedidoAdicionalMO.getListaFiltroClasePedido(), pedidoAdicionalMO.getCodCentro());

			//Reseteamos todos los posibles errores previos.
			this.resetearErrores(session.getId(), pedidoAdicionalMO.getListaFiltroClasePedido(), pedidoAdicionalMO.getCodCentro());

			//Tenemos que comprobar que todos los seleccionados tienen la misma fecha fin.
			boolean fechasFinOk = true;
			boolean pedidosModif = true;
			boolean valFechasOk = true;
			for (int i=0;i<listaCamposSeleccionados.size();i++){

				if (listaCamposSeleccionados.get(i).getSeleccionado() != null && listaCamposSeleccionados.get(i).getSeleccionado().equals("S"))
				{
					//Obtenemos el registro
					PedidoAdicionalMO registroGuardado = pedidoAdicionalService.obtenerArticuloTablaSesionMO(session.getId(), listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA(),listaCamposSeleccionados.get(i).getIdentificadorVegalsa());

					if (fechaFin.equals(""))
					{
						fechaFin = registroGuardado.getFechaFin();
					}
					else if (!fechaFin.equals(registroGuardado.getFechaFin()))
					{
						//En este caso el registro seleccionado tiene fecha fin distinta,
						//con lo que lo tenemos que deseleccionar y avisar del error al usuario.
						listaCamposSeleccionados.get(i).setSeleccionado("N");

						//Tenemos que añadir el código de que se ha modificado erroneamente el registro
						this.errorArticuloTablaSesion(session.getId(),  listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA(),Constantes.MODIFICADO_FEC_ERRONEA_PANTALLA,"");

						//Ponemos el indicador de fecha a false.
						fechasFinOk = false;
					}
					if (fechasFinOk)
					{
						//Con el registro comprobamos si es modificable, en cuyo caso obtenemos la fecha de inicio de la pilada
						if (registroGuardado.getModificableIndiv() != null && !registroGuardado.getModificableIndiv().equals("") &&
								registroGuardado.getModificableIndiv().indexOf(Constantes.PEDIDO_MODIFICABLE_SI)>=0)
						{
							//Añadimos el registro a la lista para posteriormente llamar al WS de validación.
							listaPedidoAdicionalMO_Mod.add(registroGuardado);

							//En este caso tenemos que guardar la fecha mayor
							if (Constantes.TIPO_PEDIDO_ENCARGO.equals(registroGuardado.getTipoPedido())){
								//Se trata de un fresco puro
								if (registroGuardado.getFechaPilada() != null && !registroGuardado.getFechaPilada().equals(""))
								{
									//Comprobación de bloqueo de pilada
									if ((maxFecInicio == null)||(Utilidades.convertirStringAFecha(registroGuardado.getFechaPilada()).after(maxFecInicio)))
									{
										maxFecInicio = Utilidades.convertirStringAFecha(registroGuardado.getFechaPilada());
									}
								}
							}
							else
							{
								//Se trata de alimentación
								if ((maxFecInicio == null)||(Utilidades.convertirStringAFecha(registroGuardado.getFechaInicio()).after(maxFecInicio)))
								{
									maxFecInicio = Utilidades.convertirStringAFecha(registroGuardado.getFechaInicio());
								}
							}
						}
						else
						{
							//Avisamos al usuario de que el registro no es modificable.
							this.errorArticuloTablaSesion(session.getId(),  listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA(),Constantes.MODIFICADO_NO_MODIFICABLE,"");

							//Deseleccionamos el registro
							listaCamposSeleccionados.get(i).setSeleccionado("N");

							//Por lo menos hay un pedido no modificable.
							pedidosModif = false;
						}
					}
				}
			}
			if (fechasFinOk &&pedidosModif)
			{
				try {	
					//Ahora tenemos que validar la lista de pedidos a modificar, si falla alguna validación tampoco se permitirá la
					//modificación.
					listaPedidoAdicionalMORespuesta_Mod = this.pedidoAdicionalMOService.validateAll(listaPedidoAdicionalMO_Mod, session);
				} catch (Exception e) {
					//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
					PedidoAdicionalMOPagina pedidoAdicionalMOPagina = new PedidoAdicionalMOPagina();
					pedidoAdicionalMOPagina.setDatos(new Page<PedidoAdicionalMO>());
					pedidoAdicionalMOPagina.setCodError(new Long(1));
					pedidoAdicionalMOPagina.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSValidarPedidosMO",null, locale));
					return pedidoAdicionalMOPagina;
				}

				if (listaPedidoAdicionalMORespuesta_Mod != null && listaPedidoAdicionalMORespuesta_Mod.size()>0)
				{
					for (int i=0;i<listaPedidoAdicionalMORespuesta_Mod.size();i++){

						if (listaPedidoAdicionalMORespuesta_Mod.get(i).getCodigoRespuesta() != null && 
								Integer.parseInt(listaPedidoAdicionalMORespuesta_Mod.get(i).getCodigoRespuesta()) == 0 )
						{
							//Si la validación de algún pedido ha fallado no es necesario seguir obteniendo fechas.
							if (valFechasOk)
							{
								//Obtenemos la fecha de inicio y si es mayor que la que teníamos, la guardamos.
								if ((maxFecInicio == null)||(Utilidades.convertirStringAFecha(listaPedidoAdicionalMORespuesta_Mod.get(i).getFechaInicio()).after(maxFecInicio)))
								{
									maxFecInicio = Utilidades.convertirStringAFecha(listaPedidoAdicionalMORespuesta_Mod.get(i).getFechaInicio());
								}
							}
						}
						else
						{
							//En este caso se ha producido un error en el WS y tendremos que avisar al usuario.
							//Tenemos que añadir el código de que se ha producido un error al validar el pedido e intentar obtener la fecha de inicio.
							this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalMORespuesta_Mod.get(i).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaPedidoAdicionalMORespuesta_Mod.get(i).getCodArticulo(),listaPedidoAdicionalMORespuesta_Mod.get(i).getIdentificador(),listaPedidoAdicionalMORespuesta_Mod.get(i).getIdentificadorSIA(),Constantes.MODIFICADO_FEC_INICIO_ERROR,listaPedidoAdicionalMORespuesta_Mod.get(i).getDescripcionRespuesta());

							//Deseleccionamos el registro
							for (int j=0;i<listaCamposSeleccionados.size();j++){
								if (listaCamposSeleccionados.get(j).getSeleccionado() != null && 
										listaCamposSeleccionados.get(j).getSeleccionado().equals("S") &&
										listaCamposSeleccionados.get(j).getClasePedido() == listaPedidoAdicionalMORespuesta_Mod.get(i).getClasePedido() &&
										listaCamposSeleccionados.get(j).getCodArticulo() == listaPedidoAdicionalMORespuesta_Mod.get(i).getCodArticulo())
								{
									listaCamposSeleccionados.get(j).setSeleccionado("N");
								}
							}

							valFechasOk = false;
						}
					}
				}

				if (valFechasOk)
				{
					//En este caso se cumple que todos los pedidos seleccionados tienen la misma fecha fin y son modificables,
					//seguimos con la modificación.
					esModificable = "S";

					//Además tenemos que limpiar los posibles errores mostrados anteriormente.
					this.resetearErroresTesteo(session.getId(), pedidoAdicionalMO.getListaFiltroClasePedido(), pedidoAdicionalMO.getCodCentro());
				}
			}
			listaSeleccionados = listaCamposSeleccionados;

			//Volvemos a obtener la lista guardada en la tabla temporal.
			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),index, sortOrder);
			}
			else
			{
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),null,null);
			}

			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		//Montaje de lista paginada
		PedidoAdicionalMOPagina pedidoAdicionalMOPagina = new PedidoAdicionalMOPagina();
		Page<PedidoAdicionalMO> listaPedidoAdicionalMOPaginada = null;

		if (listaGuardada != null) {
			int records = listaGuardada.size();
			listaPedidoAdicionalMOPaginada = this.paginationManagerMO.paginate(new Page<PedidoAdicionalMO>(), listaGuardada,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalMOPagina.setDatos(listaPedidoAdicionalMOPaginada);
			pedidoAdicionalMOPagina.setListadoSeleccionados(listaSeleccionados);
			pedidoAdicionalMOPagina.setEsTecnico(esTecnico);
			pedidoAdicionalMOPagina.setEsModificable(esModificable);
			pedidoAdicionalMOPagina.setCodError(new Long(0));
			//Se rellena la fecha fin de la selección
			pedidoAdicionalMOPagina.setFechaFin(fechaFin);
			if (maxFecInicio != null)
			{
				pedidoAdicionalMOPagina.setFechaMinima(Utilidades.formatearFecha(maxFecInicio));
			}

		} else {
			pedidoAdicionalMOPagina.setDatos(new Page<PedidoAdicionalMO>());
		}
		return pedidoAdicionalMOPagina;
	}	

	@RequestMapping(value = "/modifyDataGridMO", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalMOPagina modifyDataGridMO(
			@RequestBody PedidoAdicionalMO pedidoAdicionalMO,
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

		List<PedidoAdicionalMO> listaGuardada = null;
		List<CamposSeleccionadosMO> listaCamposSeleccionados = null;
		List<PedidoAdicionalMO> listaPedidoAdicionalMO_Mod = new ArrayList<PedidoAdicionalMO>();
		List<PedidoAdicionalMO> listaPedidoAdicionalMORespuesta_Mod = new ArrayList<PedidoAdicionalMO>();
		List<CamposSeleccionadosMO> listaSeleccionados = new ArrayList<CamposSeleccionadosMO>();

		String esTecnico = "N";

		Locale locale = LocaleContextHolder.getLocale();

		try {
			//Antes de empezar tenemos que limpiar los posibles guardados mostrados anteriormente.
			this.resetearGuardados(session.getId(), pedidoAdicionalMO.getListaFiltroClasePedido(), pedidoAdicionalMO.getCodCentro());

			//En primer lugar nos recorremos la lista de registros seleccionados.
			listaCamposSeleccionados = pedidoAdicionalMO.getListadoSeleccionados();

			//Tenemos que obtener la lista para la modificación múltiple de la fecha fin
			for (int i=0;i<listaCamposSeleccionados.size();i++){

				if (listaCamposSeleccionados.get(i).getSeleccionado() != null && listaCamposSeleccionados.get(i).getSeleccionado().equals("S"))
				{
					//Obtenemos el registro
					PedidoAdicionalMO registroGuardado = pedidoAdicionalService.obtenerArticuloTablaSesionMO(session.getId(), listaCamposSeleccionados.get(i).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaCamposSeleccionados.get(i).getCodArticulo(),listaCamposSeleccionados.get(i).getIdentificador(),listaCamposSeleccionados.get(i).getIdentificadorSIA(),listaCamposSeleccionados.get(i).getIdentificadorVegalsa());

					//Clonamos el objeto para evitar modificar la lista de sesión
					PedidoAdicionalMO registroMod = registroGuardado.clone();

					//Actualizamos la fecha fin.
					if (pedidoAdicionalMO.getFechaFin()!= null && !pedidoAdicionalMO.getFechaFin().equals(""))
					{
						//registroMod.setFechaPilada(registroMod.getFechaFin());
						registroMod.setFechaFin(pedidoAdicionalMO.getFechaFin().replaceAll("/", ""));
					}

					//Añadimos el registro a la lista a enviar al WS.
					listaPedidoAdicionalMO_Mod.add(registroMod);
				}
			}

			try {	
				//Hacemos el modificado de la fecha fin de los pedidos seleccionados
				listaPedidoAdicionalMORespuesta_Mod = this.pedidoAdicionalMOService.modifyAll(listaPedidoAdicionalMO_Mod,Constantes.MODIFICADO, session);
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				PedidoAdicionalMOPagina pedidoAdicionalMOPagina = new PedidoAdicionalMOPagina();
				pedidoAdicionalMOPagina.setDatos(new Page<PedidoAdicionalMO>());
				pedidoAdicionalMOPagina.setCodError(new Long(1));
				pedidoAdicionalMOPagina.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSModificarPedidosMO",null, locale));
				return pedidoAdicionalMOPagina;
			}


			//Con las listas que nos devuelve el WS, nos tenemos que recorrer la lista guardada en sesión, 
			//avisando en los que no se ha podido modificar.

			for (int j=0;j<listaPedidoAdicionalMORespuesta_Mod.size();j++){

				if (listaPedidoAdicionalMORespuesta_Mod.get(j).getCodArticulo() != null)
				{
					if (listaPedidoAdicionalMORespuesta_Mod.get(j).getCodigoRespuesta() != null && 
							Integer.parseInt(listaPedidoAdicionalMORespuesta_Mod.get(j).getCodigoRespuesta()) == 0 )
					{
						//En este caso hemos actualizado la fecha fin, lo hacemos en el objeto de sesión también.
						this.modificarArticuloFechaFinTablaSesion(session.getId(), listaPedidoAdicionalMORespuesta_Mod.get(j).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaPedidoAdicionalMORespuesta_Mod.get(j).getCodArticulo(),listaPedidoAdicionalMORespuesta_Mod.get(j).getIdentificador(),listaPedidoAdicionalMORespuesta_Mod.get(j).getIdentificadorSIA(),Constantes.MODIFICADO_CORRECTO_PANTALLA, listaPedidoAdicionalMORespuesta_Mod.get(j).getFechaFin());
					}
					else
					{
						//Tenemos que añadir el código de que se ha modificado erroneamente el registro
						this.errorArticuloTablaSesion(session.getId(), listaPedidoAdicionalMORespuesta_Mod.get(j).getClasePedido(), pedidoAdicionalMO.getCodCentro(), listaPedidoAdicionalMORespuesta_Mod.get(j).getCodArticulo(),listaPedidoAdicionalMORespuesta_Mod.get(j).getIdentificador(),listaPedidoAdicionalMORespuesta_Mod.get(j).getIdentificadorSIA(),Constantes.MODIFICADO_ERRONEO_PANTALLA,listaPedidoAdicionalMORespuesta_Mod.get(j).getDescripcionRespuesta());
					}
				}
			}

			//Volvemos a obtener la lista
			//Se permite ordenar por cualquier campo
			if (index!=null && !index.equals("null"))
			{
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),index, sortOrder);
			}
			else
			{
				listaGuardada = this.obtenerTablaSesionMO(session.getId(), pedidoAdicionalMO.getCodCentro(),pedidoAdicionalMO.getMca(),pedidoAdicionalMO.getDescPeriodo(),pedidoAdicionalMO.getEspacioPromo(),null,null);
			}

			//Una vez realizadas las modificaciones creamos la lista de seleccionados con los registros sin seleccionar.
			CamposSeleccionadosMO campoSelecc = new CamposSeleccionadosMO();
			if (listaGuardada != null) {
				//Nos recorremos la lista.
				PedidoAdicionalMO campo = new PedidoAdicionalMO();
				for (int i =0;i<listaGuardada.size();i++)
				{
					campo = (PedidoAdicionalMO)listaGuardada.get(i);

					//Además creamos la lista con las referencias inicialmente no seleccionadas.
					campoSelecc = new CamposSeleccionadosMO();
					campoSelecc.setIndice(i);
					campoSelecc.setCodArticulo(campo.getCodArticulo());
					campoSelecc.setIdentificador(campo.getIdentificador());
					campoSelecc.setIdentificadorSIA(campo.getIdentificadorSIA());
					campoSelecc.setSeleccionado("N");
					campoSelecc.setClasePedido(campo.getClasePedido());
					listaSeleccionados.add(i, campoSelecc);
				}
			}

			//Obtenemos de sesión el perfil del usuario.
			User user = (User) session.getAttribute("user");
			if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole))
			{
				//En este caso se trata de un perfil técnico
				esTecnico = "S";
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		//Montaje de lista paginada
		PedidoAdicionalMOPagina pedidoAdicionalMOPagina = new PedidoAdicionalMOPagina();
		Page<PedidoAdicionalMO> listaPedidoAdicionalMOPaginada = null;

		if (listaGuardada != null) {
			int records = listaGuardada.size();
			listaPedidoAdicionalMOPaginada = this.paginationManagerMO.paginate(new Page<PedidoAdicionalMO>(), listaGuardada,
					max.intValue(), records, page.intValue());	
			pedidoAdicionalMOPagina.setDatos(listaPedidoAdicionalMOPaginada);
			pedidoAdicionalMOPagina.setListadoSeleccionados(listaSeleccionados);
			pedidoAdicionalMOPagina.setEsTecnico(esTecnico);
			pedidoAdicionalMOPagina.setCodError(new Long(0));

		} else {
			pedidoAdicionalMOPagina.setDatos(new Page<PedidoAdicionalMO>());
		}
		return pedidoAdicionalMOPagina;
	}	

	@RequestMapping(value="/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerRefPedidos> getAreaData(
			@RequestBody VAgruComerRefPedidos vAgruComerRefPedidos,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			return this.vAgruComerRefPedidosService.findAll(vAgruComerRefPedidos, null);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value="/loadContadores", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalContadores getContadores(
			@RequestBody PedidoAdicionalE pedidoAdicionalE,
			HttpSession session, HttpServletResponse response) throws Exception {

		PedidoAdicionalContadores resultado = new PedidoAdicionalContadores();

		Locale locale = LocaleContextHolder.getLocale();
		try {	


			User user = (User) session.getAttribute("user");

			pedidoAdicionalE.setCodArticuloGrid(pedidoAdicionalE.getCodArticulo());
			if (utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode()) && (pedidoAdicionalE.getCodArticulo()!=null && !(pedidoAdicionalE.getCodArticulo().equals("")))){
				//Es un centro caprabo y se esta realizando una busqueda por referencia. Hacemos la conversion de codigo de articulo a codigo de articulo Eroski.	
				pedidoAdicionalE.setCodArticulo(utilidadesCapraboService.obtenerCodigoEroski(pedidoAdicionalE.getCodCentro(), pedidoAdicionalE.getCodArticulo()));
			}

			//Se calculan los ditintos contadores
			resultado = this.pedidoAdicionalService.loadContadores(pedidoAdicionalE, session, response);

			if (esCentroVegalsa(session)){
				PedidoAdicionalContadores resultadoVegalsa = this.pedidoAdicionalService.loadContadoresVegalsa(pedidoAdicionalE);
				resultado = sumarContadores(resultado, resultadoVegalsa);
				this.pedidoAdicionalService.insertarPedidosVegalsa(pedidoAdicionalE, session);
			}

			// Mantenemos en sesión el Contador de Encargos
			session.setAttribute("contadorEncargos",resultado.getContadorEncargos());

			// Mantenemos en sesión el Contador de Montaje
			session.setAttribute("contadorMontaje",resultado.getContadorMontaje());

			// Mantenemos en sesión el Contador de Montaje en Oferta
			session.setAttribute("contadorMontajeOferta",resultado.getContadorMontajeOferta());

			// Mantenemos en sesión el Contador de Validar Cantidades Extra
			session.setAttribute("contadorValidarCont",resultado.getContadorValidarCantExtra());

			// Mantenemos en sesión el Contador de Encargos cliente
			session.setAttribute("contadorEncargosCliente", resultado.getContadorEncargosCliente());

			//Obtener descripción por defecto en Validar Cantidades Extra

			TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
			tPedidoAdicional.setIdSesion(session.getId());
			tPedidoAdicional.setCodCentro(pedidoAdicionalE.getCodCentro());

			resultado.setDefaultDescription(this.tPedidoAdicionalService.findSelectedComboValidarVC(tPedidoAdicional));
			resultado.setCodError(new Long(0));

			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
			resultado.setCodError(new Long(1));
			resultado.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSContarPedidosE",null, locale));
			return resultado;
		}
	}

	private PedidoAdicionalContadores sumarContadores(PedidoAdicionalContadores resultado,
			PedidoAdicionalContadores resultadoVegalsa) {
		
		final PedidoAdicionalContadores output = new PedidoAdicionalContadores();
		output.setCodError(resultado.getCodError());
		output.setDescError(resultado.getDescError());
		output.setDefaultDescription(resultado.getDefaultDescription());
		
		final Long contadorEncargos = resultado.getContadorEncargos() + (resultadoVegalsa.getContadorEncargos()!=null?resultadoVegalsa.getContadorEncargos():0L);
		final Long contadorMontaje = resultado.getContadorMontaje() + (resultadoVegalsa.getContadorMontaje()!=null?resultadoVegalsa.getContadorMontaje():0L);
		final Long contadorMontajeOferta = resultado.getContadorMontajeOferta() + (resultadoVegalsa.getContadorMontajeOferta()!=null?resultadoVegalsa.getContadorMontajeOferta():0L);
		final Long contadorValidarCont = resultado.getContadorValidarCantExtra() + (resultadoVegalsa.getContadorValidarCantExtra()!=null?resultadoVegalsa.getContadorValidarCantExtra():0L);
		final Long contadorEncargosCliente = resultado.getContadorEncargosCliente() + (resultadoVegalsa.getContadorEncargosCliente()!=null?resultadoVegalsa.getContadorEncargosCliente():0L);
		
		
		output.setContadorEmpuje(0L);
		output.setContadorMAC(0L);
		output.setContadorEncargos(contadorEncargos);
		output.setContadorMontaje(contadorMontaje);
		output.setContadorMontajeOferta(contadorMontajeOferta);
		output.setContadorValidarCantExtra(contadorValidarCont);
		output.setContadorEncargosCliente(contadorEncargosCliente);
		
		return output;
	}

	private boolean esCentroVegalsa(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return user.getCentro().getCodSoc().equals(new Long(13));
	}

	@RequestMapping(value="/loadAyuda2", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalAyuda2 loadAyuda2(
			@RequestBody PedidoAdicionalAyuda2 vPedidoAdicionalAyuda2,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			PedidoAdicionalAyuda2 pedidoAdicionalAyuda2 = vPedidoAdicionalAyuda2;	

			VRelacionArticulo relacionArticuloRes = null;
			VRelacionArticulo relacionArticulo = new VRelacionArticulo();

			relacionArticulo.setCodArtRela(pedidoAdicionalAyuda2.getCodArt());
			relacionArticulo.setCodCentro(pedidoAdicionalAyuda2.getCodCentro());
			relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);

			if (null != relacionArticuloRes){
				pedidoAdicionalAyuda2.setCodArt(relacionArticuloRes.getCodArt());
			}

			pedidoAdicionalAyuda2.setHistoricoVentaMedia(obtenerHistoricoVentaMedia(pedidoAdicionalAyuda2));

			return pedidoAdicionalAyuda2;
		} catch (Exception e) {
			//		    logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	private HistoricoVentaMedia obtenerHistoricoVentaMedia(
			PedidoAdicionalAyuda2 pedidoAdicionalAyuda2) throws Exception {

		HistoricoVentaMedia historicoVentaMediaRes;
		HistoricoVentaMedia historicoVentaMedia = new HistoricoVentaMedia();
		historicoVentaMedia.setCodArticulo(pedidoAdicionalAyuda2.getCodArt());
		historicoVentaMedia.setCodLoc(pedidoAdicionalAyuda2.getCodCentro());

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


	@RequestMapping(value="/loadAyuda1", method = RequestMethod.POST)
	public @ResponseBody Page<PedidoAdicionalAyuda1> loadAyuda1(
			@RequestBody VOfertaPaAyuda vVOfertaPaAyuda, 
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "4") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpSession session, HttpServletResponse response) throws Exception {

		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		} 
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		List<VOfertaPaAyuda> list = null;
		List<PedidoAdicionalAyuda1> listPay1 = null;
		try {
			//La búsqueda de la yuda va con la referencia unitaria
			VRelacionArticulo relacionArticuloRes = null;
			VRelacionArticulo relacionArticulo = new VRelacionArticulo();

			relacionArticulo.setCodArtRela(vVOfertaPaAyuda.getCodArt());
			relacionArticulo.setCodCentro(vVOfertaPaAyuda.getCodCentro());
			relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);

			if (null != relacionArticuloRes){
				vVOfertaPaAyuda.setCodArt(relacionArticuloRes.getCodArt());
			}
			if (null == vVOfertaPaAyuda.getCodArt()){
				list = new ArrayList<VOfertaPaAyuda>();
			} else {
				list = this.vOfertaPaAyudaService.findCountNOVigentes(vVOfertaPaAyuda, 4);
			}
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<PedidoAdicionalAyuda1> result = null;	        

		if (list != null) {
			listPay1 = new ArrayList<PedidoAdicionalAyuda1>();
			int records = list.size() ;
			for(VOfertaPaAyuda pAy:list){

				PedidoAdicionalAyuda1 aux=new PedidoAdicionalAyuda1();
				aux.setcOferta(pAy.getAnoOferta()+"-"+pAy.getNumOferta());
				aux.setcPeriodo(formatearF(pAy.getFechaIni())+" "+formatearF(pAy.getFechaFin()));
				aux.setFechaIniPeriodo(Utilidades.formatearFecha(pAy.getFechaIni()));
				aux.setFechaFinPeriodo(Utilidades.formatearFecha(pAy.getFechaFin()));
				aux.setcPvp(pAy.getPrecio());
				//Se obtienen las cantidades de los 3 siguientes días al inicio de la oferta
				ObtenerCantidadesDia(pAy, aux);
				listPay1.add(aux);
			}
			PaginationManager<PedidoAdicionalAyuda1> paginationManager = new PaginationManagerImpl<PedidoAdicionalAyuda1>();
			result = paginationManager.paginate(new Page<PedidoAdicionalAyuda1>(), listPay1, max.intValue(), records, page.intValue());	

		} else {
			return new Page<PedidoAdicionalAyuda1>();
		}
		return result;

	}

	private void ObtenerCantidadesDia(VOfertaPaAyuda pAy, PedidoAdicionalAyuda1 pa) {
		List<Map<String,Object>> listaFechasVentasMedias = null;
		Float total = new Float(0);
		try {
			//Busco en BBDD la lista de fechas con sus ventas medias
			listaFechasVentasMedias = this.historicoUnidadesVentaService.findDateListMediaSalesViejo(
					pAy.getCodCentro().toString(), 
					pAy.getCodArt().toString(), 
					Utilidades.formatearFecha(pAy.getFechaIni()), 
					Utilidades.formatearFecha(pAy.getFechaFin()));

			//Día 1
			if (listaFechasVentasMedias != null && listaFechasVentasMedias.size() > 0) {
				Map<String,Object> m = listaFechasVentasMedias.get(0);
				pa.setD1(Utilidades.convertirStringAFecha((String) m.get("FECHA_VENTA")));
				pa.setcVD1( ((BigDecimal) m.get("TOTAL_VENTAS")).floatValue() );
			}
			//Día 2
			if (listaFechasVentasMedias != null && listaFechasVentasMedias.size() > 1) {
				Map<String,Object> m = listaFechasVentasMedias.get(1);
				pa.setD2(Utilidades.convertirStringAFecha((String) m.get("FECHA_VENTA")));
				pa.setcVD2( ((BigDecimal) m.get("TOTAL_VENTAS")).floatValue() );
			}
			//Día 3
			if (listaFechasVentasMedias != null && listaFechasVentasMedias.size() > 2) {
				Map<String,Object> m = listaFechasVentasMedias.get(2);
				pa.setD3(Utilidades.convertirStringAFecha((String) m.get("FECHA_VENTA")));
				pa.setcVD3( ((BigDecimal) m.get("TOTAL_VENTAS")).floatValue() );
			}
			//Total Ventas
			if (listaFechasVentasMedias != null && listaFechasVentasMedias.size() > 0) {
				for (Map<String,Object> m : listaFechasVentasMedias){
					total +=  ((BigDecimal) m.get("TOTAL_VENTAS")).floatValue() ;
				} 
			}
			pa.setTotalVentas(total);
		} catch (Exception e) {
			logger.error("ObtenerCantidadesDia="+e.toString());
			e.printStackTrace();
		}

	}

	private String formatearF(Date d){
		Locale locale = LocaleContextHolder.getLocale();
		return Utilidades.formatearFecha(Utilidades.formatearFecha(d), locale);

	}

	private void modificarArticuloFechaFinTablaSesion(String idSesion, Long clasePedido, Long codCentro, Long codArticulo,Long identificador,Long identificadorSIA,String codError, String fechaFin){

		TPedidoAdicional registro = new TPedidoAdicional();


		registro.setIdSesion(idSesion);
		registro.setClasePedido(clasePedido);
		registro.setCodCentro(codCentro);
		registro.setCodArticulo(codArticulo);
		registro.setIdentificador(identificador);
		registro.setIdentificadorSIA(identificadorSIA);
		registro.setCodError(codError);
		registro.setFechaFin(fechaFin);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);

		try {
			this.tPedidoAdicionalService.updateModifFechaFinArticulo(registro);
		} catch (Exception e) {
			logger.error("modificarArticuloFechaFinTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}


	private void resetearErroresTesteo(String idSesion, List<Long> listaFiltroClasePedido, Long codCentro){

		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setListaFiltroClasePedido(listaFiltroClasePedido);
		registro.setCodCentro(codCentro);
		registro.setCodError(Constantes.MODIFICADO_FEC_ERRONEA_PANTALLA+"-"+Constantes.MODIFICADO_NO_MODIFICABLE+"-"+Constantes.MODIFICADO_FEC_INICIO_ERROR);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);

		try {
			this.tPedidoAdicionalService.updateErroresTesteo(registro);
		} catch (Exception e) {
			logger.error("resetearErroresTesteo="+e.toString());
			e.printStackTrace();
		}
	}

	private void resetearErrores(String idSesion, List<Long> listaFiltroClasePedido, Long codCentro){

		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setListaFiltroClasePedido(listaFiltroClasePedido);
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);

		try {
			this.tPedidoAdicionalService.updateErrores(registro);
		} catch (Exception e) {
			logger.error("resetearErrores="+e.toString());
			e.printStackTrace();
		}
	}

	private void resetearGuardados(String idSesion, List<Long> listaFiltroClasePedido, Long codCentro){

		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setListaFiltroClasePedido(listaFiltroClasePedido);
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);

		try {
			this.tPedidoAdicionalService.resetearGuardados(registro);
		} catch (Exception e) {
			logger.error("resetearGuardados="+e.toString());
			e.printStackTrace();
		}
	}

	private void errorArticuloTablaSesion(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA, String codError, String descError){

		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setClasePedido(clasePedido);
		registro.setCodCentro(codCentro);
		registro.setCodArticulo(codArticulo);
		registro.setIdentificador(identificador);
		registro.setIdentificadorSIA(identificadorSIA);
		registro.setCodError(codError);
		registro.setDescError(descError);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);

		try {
			this.tPedidoAdicionalService.updateErrorArticulo(registro);
		} catch (Exception e) {
			logger.error("errorArticuloTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

	private void eliminarArticuloTablaSesion(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA){

		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setClasePedido(clasePedido);
		registro.setCodCentro(codCentro);
		registro.setCodArticulo(codArticulo);
		registro.setIdentificador(identificador);
		registro.setIdentificadorSIA(identificadorSIA);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);

		try {
			this.tPedidoAdicionalService.deleteArticulo(registro);
		} catch (Exception e) {
			logger.error("eliminarArticuloTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}



	private List<PedidoAdicionalE> obtenerTablaSesionE(String idSesion, Long codCentro, String MAC, String orderBy, String sortOrder){

		List<PedidoAdicionalE> listaPedidoAdicionalE = new ArrayList<PedidoAdicionalE>();
		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_ENCARGO)));
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		registro.setMAC(MAC);
		if (orderBy != null && !orderBy.equals(""))
		{
			registro.setOrderBy(orderBy);
			registro.setSortOrder(sortOrder);
		}

		PedidoAdicionalE nuevoRegistro = new PedidoAdicionalE();

		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalService.findAll(registro);

			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				for (int i =0;i<listaTPedidoAdicional.size();i++)
				{
					nuevoRegistro = new PedidoAdicionalE();

					registro = (TPedidoAdicional)listaTPedidoAdicional.get(i);

					nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
					nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
					nuevoRegistro.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					nuevoRegistro.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);	
					nuevoRegistro.setCodArticuloGrid((registro.getCodArticuloGrid() != null && !("".equals(registro.getCodArticuloGrid().toString())))?new Long(registro.getCodArticuloGrid().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDescriptionArtGrid() != null && !("".equals(registro.getDescriptionArtGrid())))?registro.getDescriptionArtGrid():null);
					nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
					nuevoRegistro.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
					nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
					nuevoRegistro.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
					nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
					nuevoRegistro.setUnidadesPedidas((registro.getCajasPedidas() != null && !("".equals(registro.getCajasPedidas().toString())))?new Double(registro.getCajasPedidas().toString()):null);
					nuevoRegistro.setFecEntrega((registro.getFecEntrega() != null && !("".equals(registro.getFecEntrega())))?registro.getFecEntrega():null);
					nuevoRegistro.setCajas((registro.getCajas() != null && !("".equals(registro.getCajas().toString())))?(("N".equals(registro.getCajas().toString())?false:true)):null);
					nuevoRegistro.setExcluir((registro.getExcluir() != null && !("".equals(registro.getExcluir().toString())))?(("N".equals(registro.getExcluir().toString())?false:true)):null);
					nuevoRegistro.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
					nuevoRegistro.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
					nuevoRegistro.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null);
					nuevoRegistro.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
					nuevoRegistro.setTratamiento(registro.getTratamiento());
					nuevoRegistro.setFechaHasta((registro.getFecEntrega() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
					nuevoRegistro.setEstado(registro.getEstado());
					nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);

					String agr = nuevoRegistro.getAgrupacion();
					String area = null;
					if (agr != null) {
						area = agr.substring(1, 2);
					}

					//TEXTIL
					if(area!=null){
						if("3".equals(area)){


							PedidoAdicionalE pedidoAdicional = new PedidoAdicionalE();
							pedidoAdicional.setCodArticulo(nuevoRegistro.getCodArticulo());		
							pedidoAdicional = datosTextil.findAll(pedidoAdicional);

							nuevoRegistro.setColor(pedidoAdicional.getColor());
							nuevoRegistro.setTalla(pedidoAdicional.getTalla());
							nuevoRegistro.setModeloProveedor(pedidoAdicional.getModeloProveedor());
						}
					}

					//Recogemos valores de V_SURTIDO_TIENDA
					VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
					vSurtidoTienda.setCodArt(registro.getCodArticulo());
					vSurtidoTienda.setCodCentro(registro.getCodCentro());
					vSurtidoTienda = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

					if (null != vSurtidoTienda){
						logger.debug("**********************************************");
						logger.debug("****************ENCARGOS E**********************");
						logger.debug("**********************************************");
						logger.debug("CATALOGO: "+vSurtidoTienda.getCatalogo());
						logger.debug("PEDIR: "+vSurtidoTienda.getPedir());
						logger.debug("**********************************************");
						logger.debug("**********************************************");
						logger.debug("**********************************************");

						//Montaje + Frescos Con Pilada
						/*if ("B".equals(vSurtidoTienda.getCatalogo()) && "N".equals(vSurtidoTienda.getPedir())){
						nuevoRegistro.setEstado("NOACT");
					}else{
						//Encargos o Montajes solo con encargos
						if("B".equals(vSurtidoTienda.getCatalogo()) && "".equals(registro.getFechaPilada())){
							nuevoRegistro.setEstado("NOACT");
						}
					}*/
					}

					listaPedidoAdicionalE.add(nuevoRegistro);
				}
			}
		} catch (Exception e) {
			logger.error("obtenerTablaSesionE="+e.toString());
			e.printStackTrace();
		}

		return listaPedidoAdicionalE;
	}

	private List<PedidoAdicionalM> obtenerTablaSesionM(String idSesion, Long codCentro, String MAC, String orderBy, String sortOrder, String descPeriodo, String espacioPromo){

		List<PedidoAdicionalM> listaPedidoAdicionalM = new ArrayList<PedidoAdicionalM>();
		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)));
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		registro.setMAC(MAC);
		registro.setDescPeriodo(descPeriodo);
		registro.setEspacioPromo(espacioPromo);
		if (orderBy != null && !orderBy.equals(""))
		{
			registro.setOrderBy(orderBy);
			registro.setSortOrder(sortOrder);
		}

		PedidoAdicionalM nuevoRegistro = new PedidoAdicionalM();

		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalService.findAll(registro);

			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				for (int i =0;i<listaTPedidoAdicional.size();i++)
				{
					nuevoRegistro = new PedidoAdicionalM();

					registro = (TPedidoAdicional)listaTPedidoAdicional.get(i);

					nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
					nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
					nuevoRegistro.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					nuevoRegistro.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
					nuevoRegistro.setCodArticuloGrid((registro.getCodArticuloGrid() != null && !("".equals(registro.getCodArticuloGrid().toString())))?new Long(registro.getCodArticuloGrid().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDescriptionArtGrid() != null && !("".equals(registro.getDescriptionArtGrid())))?registro.getDescriptionArtGrid():null);
					nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
					nuevoRegistro.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
					nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
					nuevoRegistro.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
					nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
					nuevoRegistro.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
					nuevoRegistro.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
					nuevoRegistro.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
					nuevoRegistro.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
					nuevoRegistro.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
					nuevoRegistro.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
					nuevoRegistro.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
					nuevoRegistro.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
					nuevoRegistro.setFecha4((registro.getFecha4() != null && !("".equals(registro.getFecha4())))?registro.getFecha4():null);
					nuevoRegistro.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
					nuevoRegistro.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
					nuevoRegistro.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
					nuevoRegistro.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
					nuevoRegistro.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
					nuevoRegistro.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
					nuevoRegistro.setExcluir((registro.getExcluir() != null && !("".equals(registro.getExcluir().toString())))?(("N".equals(registro.getExcluir().toString())?false:true)):null);
					nuevoRegistro.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null);
					nuevoRegistro.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
					nuevoRegistro.setEsPlanograma((registro.getEsPlanograma() != null && !("".equals(registro.getEsPlanograma())))?registro.getEsPlanograma():null);
					nuevoRegistro.setNoGestionaPbl((registro.getNoGestionaPbl() != null && !("".equals(registro.getNoGestionaPbl())))?registro.getNoGestionaPbl():null);
					nuevoRegistro.setCantMax(registro.getCantMax());
					nuevoRegistro.setCantMin(registro.getCantMin());
					if (Constantes.PEDIDO_ADICIONAL_MAC.equals(MAC)){
						nuevoRegistro.setDescOferta(registro.getDescOferta() != null ? registro.getDescOferta() : (registro.getDescPeriodo() != null ? registro.getDescPeriodo() : registro.getOferta()));
					}
					else{
						nuevoRegistro.setDescOferta(registro.getDescOferta());
					}
					nuevoRegistro.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
					nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
					nuevoRegistro.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
					nuevoRegistro.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
					nuevoRegistro.setTratamiento(registro.getTratamiento());
					nuevoRegistro.setFechaHasta((registro.getFecEntrega() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
					nuevoRegistro.setEstado(registro.getEstado());
					nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
					nuevoRegistro.setIdentificadorVegalsa((registro.getIdentificadorVegalsa() != null && !("".equals(registro.getIdentificadorVegalsa().toString())))?new Long(registro.getIdentificadorVegalsa().toString()):null);

					nuevoRegistro.setDescPeriodo(registro.getDescPeriodo());
					nuevoRegistro.setEspacioPromo(registro.getEspacioPromo());

					String agr = nuevoRegistro.getAgrupacion();

					String area = null;
					if (agr != null) {
						area = agr.substring(1, 2);
					}

					//TEXTIL
					if(area!=null){
						if("3".equals(area)){


							PedidoAdicionalE pedidoAdicional = new PedidoAdicionalE();
							pedidoAdicional.setCodArticulo(nuevoRegistro.getCodArticulo());		
							pedidoAdicional = datosTextil.findAll(pedidoAdicional);

							nuevoRegistro.setColor(pedidoAdicional.getColor());
							nuevoRegistro.setTalla(pedidoAdicional.getTalla());
							nuevoRegistro.setModeloProveedor(pedidoAdicional.getModeloProveedor());
						}
					}

					//Recogemos valores de V_SURTIDO_TIENDA
					VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
					vSurtidoTienda.setCodArt(registro.getCodArticulo());
					vSurtidoTienda.setCodCentro(registro.getCodCentro());
					vSurtidoTienda = this.vSurtidoTiendaService.findOne(vSurtidoTienda);
					if (null != vSurtidoTienda){
						logger.debug("**********************************************");
						logger.debug("****************ENCARGOS M **********************");
						logger.debug("**********************************************");
						logger.debug("CATALOGO: "+vSurtidoTienda.getCatalogo());
						logger.debug("PEDIR: "+vSurtidoTienda.getPedir());
						logger.debug("**********************************************");
						logger.debug("**********************************************");
						logger.debug("**********************************************");

						//Montaje + Frescos Con Pilada
						/*if ("B".equals(vSurtidoTienda.getCatalogo()) && "N".equals(vSurtidoTienda.getPedir())){
						nuevoRegistro.setEstado("NOACT");
					}else{
						//Encargos o Montajes solo con encargos
						if("B".equals(vSurtidoTienda.getCatalogo()) && "".equals(registro.getFechaPilada())){
							nuevoRegistro.setEstado("NOACT");
						}
					}*/
					}

					listaPedidoAdicionalM.add(nuevoRegistro);
				}
			}
		} catch (Exception e) {
			logger.error("obtenerTablaSesionM="+e.toString());
			e.printStackTrace();
		}

		return listaPedidoAdicionalM;
	}

	private List<PedidoAdicionalMO> obtenerTablaSesionMO(String idSesion, Long codCentro, String MAC, String descPeriodo, String espacioPromo, String orderBy, String sortOrder){

		List<PedidoAdicionalMO> listaPedidoAdicionalMO = new ArrayList<PedidoAdicionalMO>();
		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA)));
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		registro.setMAC(MAC);
		registro.setDescPeriodo(descPeriodo);
		registro.setEspacioPromo(espacioPromo);
		if (orderBy != null && !orderBy.equals(""))
		{
			registro.setOrderBy(orderBy);
			registro.setSortOrder(sortOrder);
		}

		PedidoAdicionalMO nuevoRegistro = new PedidoAdicionalMO();

		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalService.findAll(registro);

			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				for (int i =0;i<listaTPedidoAdicional.size();i++)
				{
					nuevoRegistro = new PedidoAdicionalMO();

					registro = (TPedidoAdicional)listaTPedidoAdicional.get(i);

					nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
					nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
					nuevoRegistro.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					nuevoRegistro.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
					nuevoRegistro.setCodArticuloGrid((registro.getCodArticuloGrid() != null && !("".equals(registro.getCodArticuloGrid().toString())))?new Long(registro.getCodArticuloGrid().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDescriptionArtGrid() != null && !("".equals(registro.getDescriptionArtGrid())))?registro.getDescriptionArtGrid():null);
					nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
					nuevoRegistro.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
					nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
					nuevoRegistro.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
					nuevoRegistro.setOferta((registro.getOferta() != null && !("".equals(registro.getOferta())))?registro.getOferta():null);
					nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
					nuevoRegistro.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
					nuevoRegistro.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
					nuevoRegistro.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
					nuevoRegistro.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
					nuevoRegistro.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
					nuevoRegistro.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
					nuevoRegistro.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
					nuevoRegistro.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
					nuevoRegistro.setFecha4((registro.getFecha4() != null && !("".equals(registro.getFecha4())))?registro.getFecha4():null);
					nuevoRegistro.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
					nuevoRegistro.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
					nuevoRegistro.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
					nuevoRegistro.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
					nuevoRegistro.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
					nuevoRegistro.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
					nuevoRegistro.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null);
					nuevoRegistro.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
					nuevoRegistro.setEsPlanograma((registro.getEsPlanograma() != null && !("".equals(registro.getEsPlanograma())))?registro.getEsPlanograma():null);
					nuevoRegistro.setNoGestionaPbl((registro.getNoGestionaPbl() != null && !("".equals(registro.getNoGestionaPbl())))?registro.getNoGestionaPbl():null);
					nuevoRegistro.setCantMax(registro.getCantMax());
					nuevoRegistro.setCantMin(registro.getCantMin());
					if (Constantes.PEDIDO_ADICIONAL_MAC.equals(MAC)){
						nuevoRegistro.setDescOferta(registro.getDescOferta() != null ? registro.getDescOferta() : (registro.getDescPeriodo() != null ? registro.getDescPeriodo() : registro.getOferta()));
					}
					else{
						nuevoRegistro.setDescOferta(registro.getDescOferta());
					}
					nuevoRegistro.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
					nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
					nuevoRegistro.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
					nuevoRegistro.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
					nuevoRegistro.setTratamiento(registro.getTratamiento());
					nuevoRegistro.setFechaHasta((registro.getFecEntrega() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
					nuevoRegistro.setEstado(registro.getEstado());
					nuevoRegistro.setDescPeriodo(registro.getDescPeriodo());
					nuevoRegistro.setEspacioPromo(registro.getEspacioPromo());
					nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
					nuevoRegistro.setIdentificadorVegalsa((registro.getIdentificadorVegalsa() != null && !("".equals(registro.getIdentificadorVegalsa().toString())))?new Long(registro.getIdentificadorVegalsa().toString()):null);

					String agr = nuevoRegistro.getAgrupacion();

					String area = null;
					if (agr != null) {
						area = agr.substring(1, 2);
					}

					//TEXTIL
					if(area!=null){
						if("3".equals(area)){

							PedidoAdicionalE pedidoAdicional = new PedidoAdicionalE();
							pedidoAdicional.setCodArticulo(nuevoRegistro.getCodArticulo());		
							pedidoAdicional = datosTextil.findAll(pedidoAdicional);

							nuevoRegistro.setColor(pedidoAdicional.getColor());
							nuevoRegistro.setTalla(pedidoAdicional.getTalla());

							nuevoRegistro.setModeloProveedor(pedidoAdicional.getModeloProveedor());

						}
					}

					//Recogemos valores de V_SURTIDO_TIENDA
					VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
					vSurtidoTienda.setCodArt(registro.getCodArticulo());
					vSurtidoTienda.setCodCentro(registro.getCodCentro());
					vSurtidoTienda = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

					if (null != vSurtidoTienda){
						logger.debug("**********************************************");
						logger.debug("****************ENCARGOS MO**********************");
						logger.debug("**********************************************");
						logger.debug("CATALOGO: "+vSurtidoTienda.getCatalogo());
						logger.debug("PEDIR: "+vSurtidoTienda.getPedir());
						logger.debug("**********************************************");
						logger.debug("**********************************************");
						logger.debug("**********************************************");

						//Montaje + Frescos Con Pilada
						/*if ("B".equals(vSurtidoTienda.getCatalogo()) && "N".equals(vSurtidoTienda.getPedir())){
					nuevoRegistro.setEstado(Constantes.PEDIDO_ESTADO_NO_ACTIVA);
				}else{
					//Encargos o Montajes solo con encargos
					if("B".equals(vSurtidoTienda.getCatalogo()) && "".equals(registro.getFechaPilada())){
						nuevoRegistro.setEstado(Constantes.PEDIDO_ESTADO_NO_ACTIVA);
					}
				}*/
					}

					listaPedidoAdicionalMO.add(nuevoRegistro);
				}
			}
		} catch (Exception e) {
			logger.error("obtenerTablaSesionMO="+e.toString());
			e.printStackTrace();
		}

		return listaPedidoAdicionalMO;
	}

	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody void exportGrid(
			@RequestParam(required = false) String[] headers,
			@RequestParam(required = false) String[] model,
			@RequestParam(required = false) Integer[] widths,
			@RequestParam(required = false) Long clasePedidoAdicional,
			@RequestParam(required = false) String seccion,
			@RequestParam(required = false) Long grupo3,
			@RequestParam(required = false) Long codigoArticulo,
			@RequestParam(required = false) Boolean mac,
			@RequestParam(required = false) String descripcion,
			@RequestParam(required = false) String descPeriodo,
			@RequestParam(required = false) String espacioPromo,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {
			List<GenericExcelVO> list = new ArrayList<GenericExcelVO>();
			Long grupo1 = null;
			Long grupo2 = null;
			VAgruComerRef vAgruComerGrupoSection = null;
			VAgruComerRef vAgruComerGrupoCategory = null;
			User user = (User) session.getAttribute("user");

			TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
			tPedidoAdicional.setIdSesion(session.getId());
			tPedidoAdicional.setCodCentro(user.getCentro().getCodCentro());
			tPedidoAdicional.setPantalla(Constantes.PANTALLA_LISTADOS);

			tPedidoAdicional.setMAC(mac?"S":"N");
			if(clasePedidoAdicional.equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE)) ||
					clasePedidoAdicional.equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL)) ||
					clasePedidoAdicional.equals(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4))){
				if (clasePedidoAdicional.equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE))){
					tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)));
				} else if (clasePedidoAdicional.equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL))){
					tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA)));
				} else {
					tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4), new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_5)));
				}
			} else {
				tPedidoAdicional.setClasePedido(clasePedidoAdicional);
			}

			if (clasePedidoAdicional.equals(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4)) && null != descripcion){
				tPedidoAdicional.setDescOferta(descripcion);
			}

			if (clasePedidoAdicional.equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL))){
				if (descPeriodo != null && !descPeriodo.equals("")){
					tPedidoAdicional.setDescPeriodo(descPeriodo);
				}
				if (espacioPromo != null && !espacioPromo.equals("")){
					tPedidoAdicional.setEspacioPromo(espacioPromo);
				}
			}

			if(seccion!=null)
			{
				tPedidoAdicional.setSeccion(seccion.substring(0,1));
			}
			if (!clasePedidoAdicional.equals(new Long(Constantes.CLASE_PEDIDO_ENCARGO_CLIENTE))){
				list = this.tPedidoAdicionalService.findAllExcel(tPedidoAdicional,model);

				if (null != seccion){
					String[] grupos = seccion.split("\\*");
					grupo1 = new Long(grupos[0]);
					grupo2 = new Long(grupos[1]);
					vAgruComerGrupoSection = this.vAgruComerRefService.findAll(new VAgruComerRef("I2",grupo1,grupo2,null,null,null,null), null).get(0);
				}
				if (grupo3!=null){
					vAgruComerGrupoCategory =this.vAgruComerRefService.findAll(new VAgruComerRef("I3",grupo1,grupo2,grupo3,null,null,null), null).get(0);
				}	

				this.excelManager.exportPedidoAdicional(list, headers, model, widths,this.messageSource, user.getCentro(),vAgruComerGrupoSection, vAgruComerGrupoCategory,
						mac, codigoArticulo, clasePedidoAdicional, descripcion, descPeriodo, espacioPromo, response);
			} else {
				TEncargosClte tEncargosClte = new TEncargosClte();
				tEncargosClte.setCentro(tPedidoAdicional.getCodCentro());
				tEncargosClte.setIddsesion(tPedidoAdicional.getIdSesion());
				List<TEncargosClte> listaEncargosClte = this.tEncargosClteService.findAllExcel(tEncargosClte);

				this.excelManager.exportEncargosCliente(listaEncargosClte, user.getCentro(),vAgruComerGrupoSection, vAgruComerGrupoCategory,
						mac, codigoArticulo, response);
			}
		} catch (Exception e) {

			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	private void comprobacionBloqueos(Object pedidoComprobacion, HttpSession session) throws Exception{
		int numeroRegistrosBloqueo = 0;
		int numeroRegistrosBloqueoLeyenda = 0;

		Class<? extends Object> clase = pedidoComprobacion.getClass();
		Method getterCodArticulo = clase.getMethod("getCodArticulo");
		Long codArticulo = (Long)getterCodArticulo.invoke(pedidoComprobacion, new Object[0]);
		Method getterClasePedido = clase.getMethod("getClasePedido");
		Long clasePedido = (Long)getterClasePedido.invoke(pedidoComprobacion, new Object[0]);
		String tipoPedido = Constantes.TIPO_PEDIDO_ENCARGO;

		if (pedidoComprobacion instanceof PedidoAdicionalM || pedidoComprobacion instanceof PedidoAdicionalMO){
			Method getterTipoPedido = clase.getMethod("getTipoPedido");
			tipoPedido = (String)getterTipoPedido.invoke(pedidoComprobacion, new Object[0]);
		}

		if (tipoPedido != null){
			User user = (User) session.getAttribute("user");
			VBloqueoEncargosPiladas vBloqueoEncargosPiladas = new VBloqueoEncargosPiladas();
			vBloqueoEncargosPiladas.setCodCentro(user.getCentro().getCodCentro());
			vBloqueoEncargosPiladas.setCodArticulo(codArticulo);

			VBloqueoEncargosPiladas vBloqueoEncargosPiladasLeyenda = new VBloqueoEncargosPiladas();
			vBloqueoEncargosPiladasLeyenda.setCodCentro(user.getCentro().getCodCentro());
			vBloqueoEncargosPiladasLeyenda.setCodArticulo(codArticulo);

			if (pedidoComprobacion instanceof PedidoAdicionalM || pedidoComprobacion instanceof PedidoAdicionalMO){
				Method getterFechaInicio = clase.getMethod("getFechaInicio");
				String fechaInicio = (String)getterFechaInicio.invoke(pedidoComprobacion, new Object[0]);
				Method getterFecha2 = clase.getMethod("getFecha2");
				String fecha2 = (String)getterFecha2.invoke(pedidoComprobacion, new Object[0]);
				Method getterFecha3 = clase.getMethod("getFecha3");
				String fecha3 = (String)getterFecha3.invoke(pedidoComprobacion, new Object[0]);
				Method getterFecha4 = clase.getMethod("getFecha4");
				String fecha4 = (String)getterFecha4.invoke(pedidoComprobacion, new Object[0]);
				Method getterFecha5 = clase.getMethod("getFecha5");
				String fecha5 = (String)getterFecha5.invoke(pedidoComprobacion, new Object[0]);
				Method getterFechaPilada = clase.getMethod("getFechaPilada");
				String fechaPilada = (String)getterFechaPilada.invoke(pedidoComprobacion, new Object[0]);
				Method getterFechaFin = clase.getMethod("getFechaFin");
				String fechaFin = (String)getterFechaFin.invoke(pedidoComprobacion, new Object[0]);

				vBloqueoEncargosPiladas.setFecIniDDMMYYYY((fechaInicio!=null?fechaInicio:""));
				vBloqueoEncargosPiladas.setFecha2DDMMYYYY((fecha2!=null?fecha2:""));
				vBloqueoEncargosPiladas.setFecha3DDMMYYYY((fecha3!=null?fecha3:""));
				vBloqueoEncargosPiladas.setFecha4DDMMYYYY((fecha4!=null?fecha4:""));
				vBloqueoEncargosPiladas.setFecha5DDMMYYYY((fecha5!=null?fecha5:""));
				vBloqueoEncargosPiladas.setFechaInPilDDMMYYYY((fechaPilada!=null?fechaPilada:""));
				vBloqueoEncargosPiladas.setFecFinDDMMYYYY((fechaFin!=null?fechaFin:""));
			}else{
				Method getterFecEntrega = clase.getMethod("getFecEntrega");
				String fecEntrega = (String)getterFecEntrega.invoke(pedidoComprobacion, new Object[0]);

				vBloqueoEncargosPiladas.setFecIniDDMMYYYY((fecEntrega!=null?fecEntrega:""));
				vBloqueoEncargosPiladas.setFecha2DDMMYYYY("");
				vBloqueoEncargosPiladas.setFecha3DDMMYYYY("");
				vBloqueoEncargosPiladas.setFecha4DDMMYYYY("");
				vBloqueoEncargosPiladas.setFecha5DDMMYYYY("");
				vBloqueoEncargosPiladas.setFechaInPilDDMMYYYY("");
				vBloqueoEncargosPiladas.setFecFinDDMMYYYY((fecEntrega!=null?fecEntrega:""));
			}

			Class[] paramString = new Class[1];	
			paramString[0] = String.class;

			Method setterFechaBloqueoEncargo = clase.getDeclaredMethod("setFechaBloqueoEncargo", paramString);
			Method setterFechaBloqueoEncargoPilada = null;

			if (pedidoComprobacion instanceof PedidoAdicionalM || pedidoComprobacion instanceof PedidoAdicionalMO){
				setterFechaBloqueoEncargoPilada = clase.getDeclaredMethod("setFechaBloqueoEncargoPilada", paramString);
			}


			//Para el control de mostrar las leyendas de bloqueo
			Method setterMostrarLeyendaBloqueo = null;

			if (pedidoComprobacion instanceof PedidoAdicionalM || pedidoComprobacion instanceof PedidoAdicionalMO || pedidoComprobacion instanceof PedidoAdicionalE){
				setterMostrarLeyendaBloqueo = clase.getDeclaredMethod("setMostrarLeyendaBloqueo", paramString);
			}

			if ((new Long(Constantes.CLASE_PEDIDO_ENCARGO)).equals(clasePedido)){
				vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
				numeroRegistrosBloqueo = this.vBloqueoEncargoPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
				if (numeroRegistrosBloqueo > 0){
					setterFechaBloqueoEncargo.invoke(pedidoComprobacion, new String("S"));
				}else{
					setterFechaBloqueoEncargo.invoke(pedidoComprobacion, new String("N"));
				}
				if (pedidoComprobacion instanceof PedidoAdicionalM || pedidoComprobacion instanceof PedidoAdicionalMO){
					setterFechaBloqueoEncargoPilada.invoke(pedidoComprobacion, new String("N"));
				}

				//Para el control de mostrar las leyendas de bloqueo (Solo se mostraran las leyendas cuando un articulo este o ha estado blqueado, independientemente de las fechas)
				vBloqueoEncargosPiladasLeyenda.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
				numeroRegistrosBloqueoLeyenda = this.vBloqueoEncargoPiladasService.registrosRefBloqueadaCont(vBloqueoEncargosPiladasLeyenda).intValue();

				if (numeroRegistrosBloqueoLeyenda > 0){
					setterMostrarLeyendaBloqueo.invoke(pedidoComprobacion, new String("S"));
				}else{
					setterMostrarLeyendaBloqueo.invoke(pedidoComprobacion, new String("N"));
				}


			}else{ 
				if (Constantes.TIPO_PEDIDO_ENCARGO.equals(tipoPedido)){
					//Se guarda la fecha de fin para restaurarla tras la búsqueda
					String fechaFinMontaje = vBloqueoEncargosPiladas.getFecFinDDMMYYYY(); 
					//Obtener la última fecha de encargo
					String fechaFinEncargoDateDDMMYYYY = null;
					if(vBloqueoEncargosPiladas.getFecha5DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha5DDMMYYYY())){
						fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha5DDMMYYYY(); 
					}else if(vBloqueoEncargosPiladas.getFecha4DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha4DDMMYYYY())){
						fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha4DDMMYYYY();
					}else if(vBloqueoEncargosPiladas.getFecha3DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha3DDMMYYYY())){
						fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha3DDMMYYYY();
					}else if(vBloqueoEncargosPiladas.getFecha2DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha2DDMMYYYY())){
						fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha2DDMMYYYY();
					}
					//Bloqueos de encargo
					vBloqueoEncargosPiladas.setFecFinDDMMYYYY(fechaFinEncargoDateDDMMYYYY);
					vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
					numeroRegistrosBloqueo = this.vBloqueoEncargoPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
					if (numeroRegistrosBloqueo > 0){
						setterFechaBloqueoEncargo.invoke(pedidoComprobacion, new String("S"));
					}else{
						setterFechaBloqueoEncargo.invoke(pedidoComprobacion, new String("N"));
					}
					vBloqueoEncargosPiladas.setFecFinDDMMYYYY(fechaFinMontaje);

					//Para el control de mostrar las leyendas de bloqueo (Solo se mostraran las leyendas cuando un articulo este o ha estado blqueado, independientemente de las fechas)
					vBloqueoEncargosPiladasLeyenda.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
					numeroRegistrosBloqueoLeyenda = this.vBloqueoEncargoPiladasService.registrosRefBloqueadaCont(vBloqueoEncargosPiladasLeyenda).intValue();
				}

				//Bloqueos de montaje
				vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_MONTAJE);
				numeroRegistrosBloqueo = this.vBloqueoEncargoPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
				if (numeroRegistrosBloqueo > 0){
					setterFechaBloqueoEncargoPilada.invoke(pedidoComprobacion, new String("S"));
				}else{
					setterFechaBloqueoEncargoPilada.invoke(pedidoComprobacion, new String("N"));
				}


				if (numeroRegistrosBloqueoLeyenda == 0) {
					//Para el control de mostrar las leyendas de bloqueo (Solo se mostraran las leyendas cuando un articulo este o ha estado blqueado, independientemente de las fechas)
					vBloqueoEncargosPiladasLeyenda.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_MONTAJE);
					numeroRegistrosBloqueoLeyenda = this.vBloqueoEncargoPiladasService.registrosRefBloqueadaCont(vBloqueoEncargosPiladasLeyenda).intValue();
				}

				if (numeroRegistrosBloqueoLeyenda > 0){
					setterMostrarLeyendaBloqueo.invoke(pedidoComprobacion, new String("S"));
				}else{
					setterMostrarLeyendaBloqueo.invoke(pedidoComprobacion, new String("N"));
				}


			}
		}
	}



	private List<PedidoAdicionalEC> obtenerTablaSesionEC(String idSesion, Long codCentro, String orderBy, String sortOrder){

		List<PedidoAdicionalEC> listaPedidoAdicionalEC = new ArrayList<PedidoAdicionalEC>();
		TEncargosClte registro = new TEncargosClte();

		registro.setIddsesion(idSesion);
		registro.setCentro(codCentro);
		if (orderBy != null && !orderBy.equals(""))
		{
			registro.setOrderBy(orderBy);
			registro.setSortOrder(sortOrder);
		}

		PedidoAdicionalEC nuevoRegistro = new PedidoAdicionalEC();

		try {
			List<TEncargosClte> listaTEncargosClte = this.tEncargosClteService.findAll(registro);

			if (listaTEncargosClte != null && listaTEncargosClte.size()>0)
			{
				for (int i =0;i<listaTEncargosClte.size();i++)
				{
					nuevoRegistro = new PedidoAdicionalEC();

					registro = (TEncargosClte)listaTEncargosClte.get(i);

					//Id para relacionar grid y subgrid. Se relaciona por codigoPedidoInterno
					//Si es sólo de nivel 1 se distingue por el localizador y si no por el codigoPedidoInterno
					if (Constantes.ENCARGOS_CLIENTE_PRIMER_NIVEL.equals(registro.getNivel())){
						nuevoRegistro.setId((registro.getLocalizador() != null && !("".equals(registro.getLocalizador().toString())))?"n1_"+registro.getLocalizador().toString():null);
					}else{
						nuevoRegistro.setId((registro.getCodigoPedidoInterno() != null && !("".equals(registro.getCodigoPedidoInterno().toString())))?"n12_"+registro.getCodigoPedidoInterno().toString():null);
					}

					nuevoRegistro.setLocalizador(registro.getLocalizador() != null && !("".equals(registro.getLocalizador().toString()))?new Long(registro.getLocalizador().toString()):null);
					nuevoRegistro.setCodLoc((registro.getCentro() != null && !("".equals(registro.getCentro().toString())))?new Long(registro.getCentro().toString()):null);
					nuevoRegistro.setArea(registro.getArea());		
					nuevoRegistro.setSeccion(registro.getSeccion());		
					nuevoRegistro.setCategoria(registro.getCategoria());		
					nuevoRegistro.setSubcategoria(registro.getSubcategoria());		
					nuevoRegistro.setSegmento(registro.getSegmento());		
					nuevoRegistro.setCodArtFormlog((registro.getReferencia() != null && !("".equals(registro.getReferencia().toString())))?new Long(registro.getReferencia().toString()):null);		
					nuevoRegistro.setCodArtFormlogMisumi((registro.getReferencia() != null && !("".equals(registro.getReferencia().toString())))?new Long(registro.getReferencia().toString()):null);
					nuevoRegistro.setDenomArticulo(registro.getDescripcion());		
					nuevoRegistro.setUnidServ(registro.getUnidadescaja() != null && !("".equals(registro.getUnidadescaja().toString()))?new Double(registro.getUnidadescaja().toString()):null);		
					nuevoRegistro.setContactoCentro(registro.getContactoCentro());		
					nuevoRegistro.setTelefonoCentro(registro.getTelefonoCentro());		
					nuevoRegistro.setNombreCliente(registro.getNombreCliente());		
					nuevoRegistro.setApellidoCliente(registro.getApellidoCliente());		
					nuevoRegistro.setTelefonoCliente(registro.getTelefonoCliente());		
					nuevoRegistro.setFechaHoraEncargo(registro.getFechaHoraEncargo());		
					nuevoRegistro.setTipoEncargo(registro.getTipoEncargo());		
					nuevoRegistro.setFechaVenta(registro.getFechaVenta());		
					nuevoRegistro.setFechaVentaModificada(registro.getFechaVentaModificada());		
					nuevoRegistro.setFechaInferior(registro.getFechaInferior());		
					nuevoRegistro.setEspecificacion(registro.getEspecificacion());		
					nuevoRegistro.setPesoDesde((registro.getPesoDesde() != null && !("".equals(registro.getPesoDesde().toString())))?new Double(registro.getPesoDesde().toString()):null);		
					nuevoRegistro.setPesoHasta((registro.getPesoHasta() != null && !("".equals(registro.getPesoHasta().toString())))?new Double(registro.getPesoHasta().toString()):null);		
					nuevoRegistro.setConfirmarEspecificaciones(registro.getConfirmarEspecificaciones());		
					nuevoRegistro.setFaltaRef(registro.getFaltaRef());		
					nuevoRegistro.setCambioRef(registro.getCambioRef());		
					nuevoRegistro.setConfirmarPrecio(registro.getConfirmarPrecio());
					nuevoRegistro.setCantEncargo((registro.getCantEncargo() != null && !("".equals(registro.getCantEncargo().toString())))?new Double(registro.getCantEncargo().toString()):null);
					nuevoRegistro.setCantFinalCompra((registro.getCantFinalCompra() != null && !("".equals(registro.getCantFinalCompra().toString())))?new Double(registro.getCantFinalCompra().toString()):null);
					nuevoRegistro.setCantServido((registro.getCantServido() != null && !("".equals(registro.getCantServido().toString())))?new Double(registro.getCantServido().toString()):null);
					nuevoRegistro.setCantNoServido((registro.getCantNoServido() != null && !("".equals(registro.getCantNoServido().toString())))?new Double(registro.getCantNoServido().toString()):null);
					nuevoRegistro.setEstado(registro.getEstado());
					nuevoRegistro.setObservacionesMisumi(registro.getObservacionesMisumi());
					nuevoRegistro.setCodigoPedidoInterno((registro.getCodigoPedidoInterno() != null && !("".equals(registro.getCodigoPedidoInterno().toString())))?new BigDecimal(registro.getCodigoPedidoInterno().toString()):null);
					nuevoRegistro.setFlgModificable(registro.getFlgModificable());
					nuevoRegistro.setTipoAprov(registro.getCodTpAprov());
					nuevoRegistro.setVitrina(registro.getVitrina());
					nuevoRegistro.setRelCompraVenta(registro.getRelCompraVenta());
					nuevoRegistro.setCodigoError((registro.getCodigoError() != null && !("".equals(registro.getCodigoError().toString())))?new Long(registro.getCodigoError().toString()):null);
					nuevoRegistro.setDescripcionError(registro.getDescripcionError());
					nuevoRegistro.setDescripcionGestadic(registro.getDescripcionGestadic());
					nuevoRegistro.setEstadoGestadic(registro.getEstadoGestadic());
					nuevoRegistro.setTxtDetalleGestadic(registro.getTxtDetalleGestadic());
					nuevoRegistro.setTxtSituacionGestadic(registro.getTxtSituacionGestadic());

					listaPedidoAdicionalEC.add(nuevoRegistro);
				}
			}
		} catch (Exception e) {
			logger.error("obtenerTablaSesionEC="+e.toString());
			e.printStackTrace();
		}

		return listaPedidoAdicionalEC;
	}
}