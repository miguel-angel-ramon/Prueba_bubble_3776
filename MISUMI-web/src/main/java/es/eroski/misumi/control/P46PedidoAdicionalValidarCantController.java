package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;

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

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoAdicionalVC;
import es.eroski.misumi.model.PedidoAdicionalVCPagina;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VOfertaPa;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.PedidoAdicionalVCService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.UserService;
import es.eroski.misumi.service.iface.VDatosEspecificosTextilService;
import es.eroski.misumi.service.iface.VOfertaPaService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/pedidoAdicionalValidarCant")
public class P46PedidoAdicionalValidarCantController {
	
	private PaginationManager<PedidoAdicionalVC> paginationManagerVC = new PaginationManagerImpl<PedidoAdicionalVC>();
	
	private static Logger logger = Logger.getLogger(P46PedidoAdicionalValidarCantController.class);
	
	@Autowired
	private PedidoAdicionalVCService pedidoAdicionalVCService;
	
	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;
	
	@Autowired
	private VOfertaPaService vOfertaPaService;
	
	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;
	
	@Autowired
	private VDatosEspecificosTextilService datosTextil;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/loadDataGridVC", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalVCPagina loadDataGridVC(
			@RequestBody PedidoAdicionalVC pedidoAdicionalVC,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "initialValue", required = false, defaultValue = "N") String initialValue,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		try {
			
			//this.pedidoAdicionalVCService.modifyAll(new TPedidoAdicional());
			
		        Pagination pagination= new Pagination(max,page);
		        if (index!=null && !"null".equals(index)){
		            pagination.setSort(index);
		        }
		        if (sortOrder!=null){
		            pagination.setAscDsc(sortOrder);
		        }

		        List<PedidoAdicionalVC> list = null;
				if ("S".equals(recarga) && !pedidoAdicionalVC.getListadoSeleccionados().isEmpty()){
					this.tPedidoAdicionalService.updatePedidosValidar(pedidoAdicionalVC.getListadoSeleccionados(), session.getId());
				}	
				
				TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
				tPedidoAdicional.setIdSesion(session.getId());
				//tPedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_EMPUJE));
				tPedidoAdicional.setListaFiltroClasePedido(pedidoAdicionalVC.getListaFiltroClasePedido());
				tPedidoAdicional.setCodCentro(pedidoAdicionalVC.getCodCentro());
				tPedidoAdicional.setPantalla(Constantes.PANTALLA_LISTADOS);
				tPedidoAdicional.setMAC(pedidoAdicionalVC.getMca());
				tPedidoAdicional.setDescOferta(pedidoAdicionalVC.getDescOferta());
				String defaultDescription = null;
				if ("S".equals(initialValue)){
					defaultDescription = this.tPedidoAdicionalService.findSelectedComboValidarVC(tPedidoAdicional);
						if (null != defaultDescription){
							String[] valores = defaultDescription.split("\\*\\*");
							tPedidoAdicional.setDescOferta(valores[0]);
						} 
				}
				list = this.obtenerTablaSesionVC(tPedidoAdicional, pagination);
				
				//Montaje de lista paginada
				PedidoAdicionalVCPagina pedidoAdicionalVCPagina = new PedidoAdicionalVCPagina();
				if ("S".equals(initialValue) && null != defaultDescription){
					pedidoAdicionalVCPagina.setDefaultDescription(defaultDescription);
				}
				Page<PedidoAdicionalVC> listaPedidoAdicionalVCPaginada = null;
				
				if (list != null) {
					int records = this.tPedidoAdicionalService.findAllCount(tPedidoAdicional).intValue();
					listaPedidoAdicionalVCPaginada = this.paginationManagerVC.paginate(new Page<PedidoAdicionalVC>(), list,
							max.intValue(), records, page.intValue());	
					pedidoAdicionalVCPagina.setDatos(listaPedidoAdicionalVCPaginada);
					if ("N".equals(recarga)){
						pedidoAdicionalVCPagina.setListadoComboDescripciones(this.tPedidoAdicionalService.findComboValidarVC(tPedidoAdicional));
					}
					
				} else {
					pedidoAdicionalVCPagina.setDatos(new Page<PedidoAdicionalVC>());
				}
				return pedidoAdicionalVCPagina;
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}
	
	@RequestMapping(value = "/saveDataGridVC", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalVCPagina saveDataGridVC(
			@RequestBody PedidoAdicionalVC pedidoAdicionalVC,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "articulo", required = false) String codArticulo,
			@RequestParam(value = "identificador", required = false) String identificador,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		try {
			

			        Pagination pagination= new Pagination(max,page);
			        if (index!=null){
			            pagination.setSort(index);
			        }
			        if (sortOrder!=null){
			            pagination.setAscDsc(sortOrder);
			        }
				List<PedidoAdicionalVC> list = null;
				if (!pedidoAdicionalVC.getListadoSeleccionados().isEmpty()){
					this.tPedidoAdicionalService.updatePedidosValidar(pedidoAdicionalVC.getListadoSeleccionados(), session.getId());
				}	
				
				TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
				tPedidoAdicional.setIdSesion(session.getId());
				//tPedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_EMPUJE));
				tPedidoAdicional.setListaFiltroClasePedido(pedidoAdicionalVC.getListaFiltroClasePedido());
				tPedidoAdicional.setCodCentro(pedidoAdicionalVC.getCodCentro());
				tPedidoAdicional.setPantalla(Constantes.PANTALLA_LISTADOS);
				tPedidoAdicional.setMAC(pedidoAdicionalVC.getMca());
				tPedidoAdicional.setDescOferta(pedidoAdicionalVC.getDescOferta());
				this.pedidoAdicionalVCService.modifyAll(tPedidoAdicional,session);
				
				Long pedidosErroneos = this.tPedidoAdicionalService.findAllCount(tPedidoAdicional);
				String defaultDescription = null;
				if (pedidosErroneos.equals(new Long(0))){
					//Obtener descripción por defecto en Validar Cantidades Extra
					defaultDescription = this.tPedidoAdicionalService.findSelectedComboValidarVC(tPedidoAdicional);
					if (null != defaultDescription){
						String[] valores = defaultDescription.split("\\*\\*");
						tPedidoAdicional.setDescOferta(valores[0]);
						
					} else {
						tPedidoAdicional.setDescOferta(null);
					}
					
				}
				list = this.obtenerTablaSesionVC(tPedidoAdicional, pagination);
				
				
				//Montaje de lista paginada
				PedidoAdicionalVCPagina pedidoAdicionalVCPagina = new PedidoAdicionalVCPagina();
				if (null != defaultDescription){
					pedidoAdicionalVCPagina.setDefaultDescription(defaultDescription);
				}
				Page<PedidoAdicionalVC> listaPedidoAdicionalVCPaginada = null;
				
				if (list != null) {
					Long records = this.tPedidoAdicionalService.findAllCount(tPedidoAdicional);
					listaPedidoAdicionalVCPaginada = this.paginationManagerVC.paginate(new Page<PedidoAdicionalVC>(), list,
							max.intValue(), records.intValue(), page.intValue());	
					pedidoAdicionalVCPagina.setDatos(listaPedidoAdicionalVCPaginada);
					pedidoAdicionalVCPagina.setListadoComboDescripciones(this.tPedidoAdicionalService.findComboValidarVC(tPedidoAdicional));
				} else {
					pedidoAdicionalVCPagina.setDatos(new Page<PedidoAdicionalVC>());
				}
				tPedidoAdicional.setDescOferta(null);
				Long totalRegistros = this.tPedidoAdicionalService.findAllCount(tPedidoAdicional);
				pedidoAdicionalVCPagina.setContadorValidarCantExtra(totalRegistros);
				pedidoAdicionalVCPagina.setContadorErroneos(pedidosErroneos);
				
				//Actualización de recordatorio de validar cantidades extra
				User userSession = (User)session.getAttribute("user");
				Centro centro = userSession.getCentro();
				List<String> listaAvisos = centro.getListaAvisosCentro();
				if (listaAvisos == null){
					listaAvisos = new ArrayList<String>();
				}
				if (this.userService.mostrarAvisoValidarCantidadesExtra(pedidoAdicionalVC.getCodCentro(),session)){
					if (!listaAvisos.contains(Constantes.TABLON_ANUNCIOS_VALIDAR_CANTIDADES_EXTRA)){
						listaAvisos.add(Constantes.TABLON_ANUNCIOS_VALIDAR_CANTIDADES_EXTRA);
					}
				}else{
					if (listaAvisos.contains(Constantes.TABLON_ANUNCIOS_VALIDAR_CANTIDADES_EXTRA)){
						listaAvisos.remove(Constantes.TABLON_ANUNCIOS_VALIDAR_CANTIDADES_EXTRA);
					}
				}
				userSession.setCentro(centro);
				session.setAttribute("user", userSession);
				
				return pedidoAdicionalVCPagina;
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}
	
	@RequestMapping(value="/cargaParametrosAyuda", method = RequestMethod.POST)
	public @ResponseBody VOfertaPa getParametrosAyuda(
			@RequestParam(value = "oferta", required = true)String oferta,
			@RequestParam(value = "codArt", required = true)Long codArt,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			User user = (User) session.getAttribute("user");
			Long codArtRelacionado = codArt;
			
			//Obtención de la referencia unitaria
			VRelacionArticulo relacionArticuloRes = null;
			VRelacionArticulo relacionArticulo = new VRelacionArticulo();
			relacionArticulo.setCodArtRela(codArt);
			relacionArticulo.setCodCentro(user.getCentro().getCodCentro());
			relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);
			if (null != relacionArticuloRes){
				codArtRelacionado = relacionArticuloRes.getCodArt();
			}
			
			//Obtención de los datos de la oferta
			VOfertaPa vOfertaPa = new VOfertaPa();
			
			vOfertaPa.setCodCentro(user.getCentro().getCodCentro());
			String[] codOferta = oferta.split("-");
			VOfertaPa ofertaAux = new VOfertaPa();
			if (oferta != null && !oferta.equals("") && !oferta.equals("null")){
				vOfertaPa.setAnoOferta(Long.valueOf(codOferta[0]));
				vOfertaPa.setNumOferta(Long.valueOf(codOferta[1]));
				vOfertaPa.setCodArt(codArtRelacionado);
				ofertaAux =  this.vOfertaPaService.findOneVigente(vOfertaPa);
			}

			if (ofertaAux == null){
				ofertaAux = new VOfertaPa();
			}
			
			ofertaAux.setCodArt(codArtRelacionado);
			

			return ofertaAux;
		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}	

	private List<PedidoAdicionalVC> obtenerTablaSesionVC(TPedidoAdicional tPedidoAdicional, Pagination pagination){

		List<PedidoAdicionalVC> listaPedidoAdicionalVC = new ArrayList<PedidoAdicionalVC>();

		
		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalService.findAllPaginate(tPedidoAdicional, pagination);

				for (TPedidoAdicional registro : listaTPedidoAdicional)
				{
					PedidoAdicionalVC nuevoRegistro = new PedidoAdicionalVC();
					
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
					nuevoRegistro.setCantMax(registro.getCantMax());
					nuevoRegistro.setCantMin(registro.getCantMin());
					nuevoRegistro.setDescOferta(registro.getDescOferta());
					nuevoRegistro.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
					nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
					nuevoRegistro.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
					nuevoRegistro.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
					nuevoRegistro.setTratamiento(registro.getTratamiento());
					nuevoRegistro.setFechaHasta((registro.getFechaHasta() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
					nuevoRegistro.setEstado(registro.getEstado());
					nuevoRegistro.setNoGestionaPbl((registro.getNoGestionaPbl() != null && !("".equals(registro.getNoGestionaPbl())))?registro.getNoGestionaPbl():null);
					nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
					
					String agr = nuevoRegistro.getAgrupacion();
					String area = agr.substring(1, 2);
					
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
					
					listaPedidoAdicionalVC.add(nuevoRegistro);
				}
		} catch (Exception e) {
			logger.error("obtenerTablaSesionVC="+e.toString());
			e.printStackTrace();
		}
			
		return listaPedidoAdicionalVC;
	}
	
}