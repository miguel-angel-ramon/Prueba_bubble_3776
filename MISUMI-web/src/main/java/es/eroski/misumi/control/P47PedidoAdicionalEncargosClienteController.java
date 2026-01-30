package es.eroski.misumi.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import es.eroski.misumi.model.CamposSeleccionadosEC;
import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.PedidoAdicionalEC;
import es.eroski.misumi.model.PedidoAdicionalECPagina;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.ValidarReferenciaEncargo;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.DiasServicioService;
import es.eroski.misumi.service.iface.EncargosClientePlataformaService;
import es.eroski.misumi.service.iface.GestionEncargosService;
import es.eroski.misumi.service.iface.PedidoAdicionalECService;
import es.eroski.misumi.service.iface.TEncargosClteService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/pedidoAdicionalEncargosCliente")
public class P47PedidoAdicionalEncargosClienteController {
	
	private static Logger logger = Logger.getLogger(P47PedidoAdicionalEncargosClienteController.class);
	
	private PaginationManager<PedidoAdicionalEC> paginationManagerEC = new PaginationManagerImpl<PedidoAdicionalEC>();
	
	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private TEncargosClteService tEncargosClteService;
	
	@Autowired
	private PedidoAdicionalECService pedidoAdicionalECService;
	
	@Autowired
	private GestionEncargosService gestionEncargosService;

	@Autowired
	private EncargosClientePlataformaService encargosClientePlataformaService;

	@Autowired
	private DiasServicioService diasServicioService;
	
	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@RequestMapping(value = "/loadDataGridEC", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalECPagina loadDataGridEC(
			@RequestBody PedidoAdicionalEC pedidoAdicionalEC,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "articulo", required = false) String codArticulo,
			@RequestParam(value = "localizador", required = false) String localizador,
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
				List<PedidoAdicionalEC> list = null;
				List<CamposSeleccionadosEC> listaSeleccionados = new ArrayList<CamposSeleccionadosEC>();
				
				list = this.obtenerTablaSesionEC(session.getId(), pedidoAdicionalEC.getCodLoc(),null,null);
				
				//La primera vez que cargamos tenemos que crear la lista con las referencias inicialmente no seleccionadas.
				CamposSeleccionadosEC campoSelecc = new CamposSeleccionadosEC();
				if (list != null) {
					//Nos recorremos la lista.
					PedidoAdicionalEC campo = new PedidoAdicionalEC();
					for (int i =0;i<list.size();i++)
					{
						campo = (PedidoAdicionalEC)list.get(i);
						
						//Además creamos la lista con las referencias inicialmente no seleccionadas.
						campoSelecc = new CamposSeleccionadosEC();

						campoSelecc.setCodArticulo(campo.getCodArtFormlog());
						campoSelecc.setLocalizador(campo.getLocalizador());
						campoSelecc.setSeleccionado("N");
						campoSelecc.setModificable(campo.getFlgModificable());
						listaSeleccionados.add(i, campoSelecc);
					}
				}

				//Volvemos a obtener la lista guardada en la tabla temporal.
				
				//Montaje de lista paginada
				PedidoAdicionalECPagina pedidoAdicionalECPagina = new PedidoAdicionalECPagina();
				Page<PedidoAdicionalEC> listaPedidoAdicionalECPaginada = null;
				
				if (list != null) {
					int records = list.size();
					listaPedidoAdicionalECPaginada = this.paginationManagerEC.paginate(new Page<PedidoAdicionalEC>(), list,
							max.intValue(), records, page.intValue());	
					pedidoAdicionalECPagina.setDatos(listaPedidoAdicionalECPaginada);
					pedidoAdicionalECPagina.setListadoSeleccionados(listaSeleccionados);
					pedidoAdicionalECPagina.setCodError(new Long(0));
					
				} else {
					pedidoAdicionalECPagina.setDatos(new Page<PedidoAdicionalEC>());
				}
				return pedidoAdicionalECPagina;

			}
			else
			{
				return loadDataGridECRecarga(pedidoAdicionalEC, codArticulo, localizador, page, max, index, sortOrder, response, session);
			}
	}
	
	@RequestMapping(value = "/loadDataGridECRecarga", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalECPagina loadDataGridECRecarga(
			@RequestBody PedidoAdicionalEC pedidoAdicionalEC,
			@RequestParam(value = "codArticulo", required = false) String codArticulo,
			@RequestParam(value = "localizador", required = false) String localizador,
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
		
	        List<PedidoAdicionalEC> listaGuardada = null;
	        List<PedidoAdicionalEC> listaRecarga = new ArrayList<PedidoAdicionalEC>();
	        int records = 0;
	        Long contadorEncargosClte =new Long(0);
			try {
				Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
				Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
				listaGuardada = this.obtenerTablaSesionEC(session.getId(), pedidoAdicionalEC.getCodLoc(), null, null);
				
				//Si me llega codArticulo, significa que lo tenemos que eliminar de la tabla temporal de sesión.
				if ((codArticulo != null)&&(!codArticulo.equals("")))
				{
					//Eliminamos de la tabla el registro
					this.eliminarEncargoTablaSesion(session.getId(), pedidoAdicionalEC.getCodLoc(), Long.parseLong(localizador));
					
					//Volvemos a obtener la lista
					listaGuardada = this.obtenerTablaSesionEC(session.getId(), pedidoAdicionalEC.getCodLoc(),null,null);
					
					//Actualizamos el contador de montaje de sesión
					contadorEncargosClte = (Long)session.getAttribute("contadorEncargosCliente");
					contadorEncargosClte = contadorEncargosClte - 1;
					session.setAttribute("contadorEncargosClte", contadorEncargosClte);
				}
				
				//Se permite ordenar por cualquier campo
				if (index!=null && !index.equals("null"))
				{
					listaGuardada = this.obtenerTablaSesionEC(session.getId(), pedidoAdicionalEC.getCodLoc(),index, sortOrder);
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
			
			Page<PedidoAdicionalEC> listaPedidoAdicionaECPaginada = null;
			PedidoAdicionalECPagina pedidoAdicionalECPagina = new PedidoAdicionalECPagina();
			
			if (listaRecarga != null) {
				listaPedidoAdicionaECPaginada = this.paginationManagerEC.paginate(new Page<PedidoAdicionalEC>(), listaRecarga,
						max.intValue(), records, page.intValue());	
				pedidoAdicionalECPagina.setDatos(listaPedidoAdicionaECPaginada);
				pedidoAdicionalECPagina.setListadoSeleccionados(pedidoAdicionalEC.getListadoSeleccionados());
				pedidoAdicionalECPagina.setContadorEncargosCliente(contadorEncargosClte);
				pedidoAdicionalECPagina.setCodError(new Long(0));
				
			} else {
				pedidoAdicionalECPagina.setDatos(new Page<PedidoAdicionalEC>());
			}
			
			return pedidoAdicionalECPagina;
	}
	
	@RequestMapping(value = "/loadDataGridECDetalle", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalECPagina loadDataGridECDetalle(
			@RequestBody PedidoAdicionalEC pedidoAdicionalEC,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
			List<PedidoAdicionalEC> list = null;
			//Se obtiene la lista de seleccionados actual para completarla con la de la subtabla
			List<CamposSeleccionadosEC> listaSeleccionados = pedidoAdicionalEC.getListadoSeleccionados();
			if (listaSeleccionados == null){
				listaSeleccionados = new ArrayList<CamposSeleccionadosEC>();
			}
			
			list = this.obtenerTablaSesionECDetalle(session.getId(), pedidoAdicionalEC.getCodigoPedidoInterno());
			
			//La primera vez que cargamos tenemos que crear la lista con las referencias inicialmente no seleccionadas.
			//Si ya existía se mantiene con los datos de pantalla
			CamposSeleccionadosEC campoSelecc = new CamposSeleccionadosEC();
			if (list != null) {
				//Nos recorremos la lista.
				PedidoAdicionalEC campo = new PedidoAdicionalEC();
				
				for (int i =0;i<list.size();i++)
				{
					campo = (PedidoAdicionalEC)list.get(i);
					CamposSeleccionadosEC campoSelActual = new CamposSeleccionadosEC();
					campoSelActual.setLocalizador(campo.getLocalizador());
					
					if (!listaSeleccionados.contains(campoSelActual))
					{

						//Además creamos la lista con las referencias inicialmente no seleccionadas.
						campoSelecc = new CamposSeleccionadosEC();

						campoSelecc.setCodArticulo(campo.getCodArtFormlog());
						campoSelecc.setLocalizador(campo.getLocalizador());
						campoSelecc.setSeleccionado("N");
						campoSelecc.setModificable(campo.getFlgModificable());
						listaSeleccionados.add(i, campoSelecc);
					}
				}
			}

			//Montaje de lista paginada
			PedidoAdicionalECPagina pedidoAdicionalECPagina = new PedidoAdicionalECPagina();
			Page<PedidoAdicionalEC> listaPedidoAdicionalECPaginada = null;
			
			if (list != null && list.size() > 0) {
				int records = list.size();
				listaPedidoAdicionalECPaginada = this.paginationManagerEC.paginate(new Page<PedidoAdicionalEC>(), list,
						list.size(), records, list.size());	
				pedidoAdicionalECPagina.setDatos(listaPedidoAdicionalECPaginada);
				pedidoAdicionalECPagina.setListadoSeleccionados(listaSeleccionados);
				pedidoAdicionalECPagina.setCodError(new Long(0));
				
			} else {
				pedidoAdicionalECPagina.setDatos(new Page<PedidoAdicionalEC>());
			}
			return pedidoAdicionalECPagina;
	}

	@RequestMapping(value = "/openModifyDataGridEC", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalEC openModifyDataGridEC(
			@RequestBody PedidoAdicionalEC pedidoAdicionalEC,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
			
			Locale locale = LocaleContextHolder.getLocale();
			PedidoAdicionalEC resultado = new PedidoAdicionalEC();
			
			try {
				//Volvemos a obtener la lista
				resultado = this.pedidoAdicionalECService.obtenerTablaSesionECRegistro(session.getId(), pedidoAdicionalEC.getLocalizador());
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				resultado.setCodigoError(new Long("1"));
				resultado.setDescripcionError(this.messageSource.getMessage("p47_pedidoAdicionalEC.errorBuscarPedidosEC",null, locale));
				return resultado;
			}
			
			if (resultado == null)
			{
				//No se ha encontrado la referencia, tenemos que devolver un resultado de que no existe la referencia.
				resultado = new PedidoAdicionalEC();
				resultado.setCodigoError(new Long("2"));
				resultado.setIdSession(pedidoAdicionalEC.getIdSession());
				resultado.setCodLoc(pedidoAdicionalEC.getCodLoc());
				return resultado;
			}
			resultado.setCodigoError(new Long("0"));
			
			//Obtención de datos para el calendario
			DiasServicio diasServicio = new DiasServicio();
			diasServicio.setCodCentro(resultado.getCodLoc());
			diasServicio.setCodArt(resultado.getCodArtFormlog());
			
			User user = (User) session.getAttribute("user");
			if ((utilidadesCapraboService.esCentroCaprabo(resultado.getCodLoc(), user.getCode())) && !(resultado.getFaltaRef().equals("S"))) {//Es un centro Caprabo . si es una referencia generica no hay que traducirla.
				resultado.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(resultado.getCodArtFormlog()));
				resultado.setCodArtEroski(utilidadesCapraboService.obtenerCodigoEroski(resultado.getCodLoc(), resultado.getCodArtFormlog())); //Necesitamos calcularlo para los calendarios cuando es Caprabo
				diasServicio.setCodArt(resultado.getCodArtEroski()); 
				
			} else {
				resultado.setDescriptionArtGrid(resultado.getDenomArticulo());
				resultado.setCodArtEroski(resultado.getCodArtFormlog());
				diasServicio.setCodArt(resultado.getCodArtFormlog()); //Es el de Eroski
			}
			
			
			diasServicio.setClasePedido(Constantes.TIPO_PEDIDO_ENCARGO_CLIENTE);
			this.diasServicioService.cargarDiasServicio(diasServicio, session.getId(), null, session);
			
			ValidarReferenciaEncargo validarReferenciaEncargo = new ValidarReferenciaEncargo();
			validarReferenciaEncargo.setCodCentro(resultado.getCodLoc());
			validarReferenciaEncargo.setCodReferencia(resultado.getCodArtFormlog());
			validarReferenciaEncargo.setGenerico(resultado.getFaltaRef()!=null?resultado.getFaltaRef():"N");
			ValidarReferenciaEncargo referenciaEncargoRes = this.encargosClientePlataformaService.validarReferencia(validarReferenciaEncargo, user.getCode());
			if (!referenciaEncargoRes.getCodError().equals(0)){
				resultado.setBloqueoEncargoCliente("S");
				resultado.setErrorValidar(referenciaEncargoRes.getDescError());
			} else {
				resultado.setBloqueoEncargoCliente("N");
				resultado.setFechasVenta(referenciaEncargoRes.getFechasVenta());
				resultado.setPrimeraFechaEntrega(referenciaEncargoRes.getFechaEntrega());
				resultado.setFlgEspec(referenciaEncargoRes.getFlgEspec());
			}
			
			
			
			return resultado;
	}
	
	@RequestMapping(value = "/removeDataGridEC", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalECPagina removeDataGridEC(
			@RequestBody PedidoAdicionalEC pedidoAdicionalEC,
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
		
	        List<PedidoAdicionalEC> listaGuardada = null;
	        List<CamposSeleccionadosEC> listaCamposSeleccionados = null;
	        List<CamposSeleccionadosEC> listaSeleccionados = new ArrayList<CamposSeleccionadosEC>();
	        
	        Long contadorEncargosCliente;
	    	User user = (User) session.getAttribute("user");

	        //Reseteamos todos los posibles errores previos.
			this.resetearErroresCliente(session.getId(), pedidoAdicionalEC.getCodLoc());
	        
			//En primer lugar nos recorremos la lista de registros seleccionados para montar la lista con los registros a eliminar.
			listaCamposSeleccionados = pedidoAdicionalEC.getListadoSeleccionados();
			int regBorrados = 0;
			List<TEncargosClte> listaEncargosBorrables = new ArrayList<TEncargosClte>();
			
			for (int i=0;i<listaCamposSeleccionados.size();i++){
				
				//Obtenemos el registro
				CamposSeleccionadosEC campo = listaCamposSeleccionados.get(i);
				
				if ("S".equals(campo.getSeleccionado())){
					TEncargosClte encargo = new TEncargosClte();
					encargo.setLocalizador(campo.getLocalizador());
					encargo.setReferencia(campo.getCodArticulo());
					encargo.setCentro(user.getCentro().getCodCentro());
					encargo.setIddsesion(session.getId());
				
					listaEncargosBorrables.add(encargo);
				}
			}
			
			if (listaEncargosBorrables != null && listaEncargosBorrables.size()>0){
				EncargosClienteLista encargosLista = this.gestionEncargosService.borrarEncargos(listaEncargosBorrables);
				if (encargosLista != null && encargosLista.getDatos() != null){
					for (int i=0; i<encargosLista.getDatos().size(); i++){	
						
						TEncargosClte encargoABorrar = new TEncargosClte();
						encargoABorrar.setLocalizador(encargosLista.getDatos().get(i).getLocalizador());
						encargoABorrar.setReferencia(encargosLista.getDatos().get(i).getCodArticulo());
						encargoABorrar.setCentro(user.getCentro().getCodCentro());
						encargoABorrar.setIddsesion(session.getId());
						
						if (null!= encargosLista && encargosLista.getEstado().equals(new Long(0)) && 
								encargosLista.getDatos().get(i).getCodError().equals(new Long(0))) {
							
								this.tEncargosClteService.deleteEncargo(encargoABorrar);
								
								//Actualizamos el contador del registro borrado.
								regBorrados++;
						} else {
							String descError;
							if (null != encargosLista.getDescEstado()){
								descError = encargosLista.getDescEstado();
							} else {
								descError = encargosLista.getDatos().get(i).getDescError();
							}
							encargoABorrar.setCodigoError(new Long(Constantes.BORRADO_ERRONEO_PANTALLA));
							encargoABorrar.setDescripcionError(descError);
							this.tEncargosClteService.updateErrorEncargo(encargoABorrar);
						}
					}
				}
			}
			
			//Volvemos a obtener la lista
			listaGuardada = this.obtenerTablaSesionEC(session.getId(), pedidoAdicionalEC.getCodLoc(),null,null);
			
			//Actualizamos el contador de encargos de sesión
			contadorEncargosCliente = (Long)session.getAttribute("contadorEncargosCliente");
			contadorEncargosCliente = contadorEncargosCliente - regBorrados;
			session.setAttribute("contadorEncargosCliente", contadorEncargosCliente);
			
			//Creamos la lista de seleccionados con la nueva lista con los registros sin seleccionar.
			CamposSeleccionadosEC campoSelecc = new CamposSeleccionadosEC();
			if (listaGuardada != null) {
				//Nos recorremos la lista.
				PedidoAdicionalEC campo = new PedidoAdicionalEC();
				for (int i =0;i<listaGuardada.size();i++)
				{
					campo = (PedidoAdicionalEC)listaGuardada.get(i);
					
					//Además creamos la lista con las referencias inicialmente no selaccionadas.
					campoSelecc = new CamposSeleccionadosEC();
					campoSelecc.setCodArticulo(campo.getCodArtFormlog());
					campoSelecc.setLocalizador(campo.getLocalizador());
					campoSelecc.setSeleccionado("N");
					listaSeleccionados.add(i, campoSelecc);
				}
			}
			
			//Montaje de lista paginada
			PedidoAdicionalECPagina pedidoAdicionalECPagina = new PedidoAdicionalECPagina();
			Page<PedidoAdicionalEC> listaPedidoAdicionalECPaginada = null;
			
			if (listaGuardada != null) {
				int records = listaGuardada.size();
				listaPedidoAdicionalECPaginada = this.paginationManagerEC.paginate(new Page<PedidoAdicionalEC>(), listaGuardada,
						max.intValue(), records, page.intValue());	
				pedidoAdicionalECPagina.setDatos(listaPedidoAdicionalECPaginada);
				pedidoAdicionalECPagina.setListadoSeleccionados(listaSeleccionados);
				pedidoAdicionalECPagina.setContadorEncargosCliente(contadorEncargosCliente);
				pedidoAdicionalECPagina.setCodError(new Long(0));
				
			} else {
				pedidoAdicionalECPagina.setDatos(new Page<PedidoAdicionalEC>());
			}
			return pedidoAdicionalECPagina;
	}

	private void eliminarEncargoTablaSesion(String idSesion, Long codCentro, Long localizador){
		
		TEncargosClte registro = new TEncargosClte();
		
		registro.setIddsesion(idSesion);
		registro.setCentro(codCentro);
		registro.setLocalizador(localizador);
		
		try {
			this.tEncargosClteService.deleteEncargo(registro);
		} catch (Exception e) {
			logger.error("eliminarArticuloTablaSesion="+e.toString());
			e.printStackTrace();
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
					
					nuevoRegistro.setCodArticuloGrid((registro.getCodArticuloGrid() != null && !("".equals(registro.getCodArticuloGrid().toString())))?new Long(registro.getCodArticuloGrid().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDescriptionArtGrid() != null && !("".equals(registro.getDescriptionArtGrid())))?registro.getDescriptionArtGrid():null);
					
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
					nuevoRegistro.setNivel(registro.getNivel());
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

	private List<PedidoAdicionalEC> obtenerTablaSesionECDetalle(String idSesion, BigDecimal codPedidoInterno){
	
		List<PedidoAdicionalEC> listaPedidoAdicionalEC = new ArrayList<PedidoAdicionalEC>();
		TEncargosClte registro = new TEncargosClte();
		
		registro.setIddsesion(idSesion);
		registro.setCodigoPedidoInterno(codPedidoInterno);
		PedidoAdicionalEC nuevoRegistro = new PedidoAdicionalEC();
		
		try {
			List<TEncargosClte> listaTEncargosClte = this.tEncargosClteService.findAllDetalle(registro);
			
			if (listaTEncargosClte != null && listaTEncargosClte.size()>0)
			{
				for (int i =0;i<listaTEncargosClte.size();i++)
				{
					nuevoRegistro = new PedidoAdicionalEC();
					
					registro = (TEncargosClte)listaTEncargosClte.get(i);
					
					nuevoRegistro.setId((registro.getCodigoPedidoInterno() != null && !("".equals(registro.getCodigoPedidoInterno().toString())))?"n2_"+registro.getLocalizador().toString():null);
					
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
					nuevoRegistro.setNivel(registro.getNivel());
					nuevoRegistro.setDescripcionGestadic(registro.getDescripcionGestadic());
					nuevoRegistro.setEstadoGestadic(registro.getEstadoGestadic());
					nuevoRegistro.setTxtDetalleGestadic(registro.getTxtDetalleGestadic());
					nuevoRegistro.setTxtSituacionGestadic(registro.getTxtSituacionGestadic());
	
					listaPedidoAdicionalEC.add(nuevoRegistro);
				}
			}
		} catch (Exception e) {
			logger.error("obtenerTablaSesionECDetalle="+e.toString());
			e.printStackTrace();
		}
			
		return listaPedidoAdicionalEC;
	}

	private void resetearErroresCliente(String idSesion, Long codCentro){
	
		TEncargosClte tEncargosClte = new TEncargosClte();
		tEncargosClte.setIddsesion(idSesion);
		tEncargosClte.setCentro(codCentro);
		
		try {
			this.tEncargosClteService.updateErrores(tEncargosClte);
		} catch (Exception e) {
			logger.error("resetearErrores="+e.toString());
			e.printStackTrace();
		}
	}

}