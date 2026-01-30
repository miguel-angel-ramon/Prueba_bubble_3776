package es.eroski.misumi.control;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import es.eroski.misumi.model.CamposModificadosNO;
import es.eroski.misumi.model.NuevoPedidoOferta;
import es.eroski.misumi.model.NuevoPedidoOfertaLista;
import es.eroski.misumi.model.NuevoPedidoOfertaPagina;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerOfertaPa;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.model.VOfertaPa;
import es.eroski.misumi.model.VSicFPromociones;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.NuevoPedidoOfertaService;
import es.eroski.misumi.service.iface.PedidoAdicionalService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.VAgruComerOfertaPaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VFestivoCentroService;
import es.eroski.misumi.service.iface.VOfertaPaService;
import es.eroski.misumi.service.iface.VSicFPromocionesService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;



@Controller
@RequestMapping("/nuevoPedidoAdicionalOferta")
public class p51NuevoPedidoAdicionalOfertaController {
	private static Logger logger = Logger.getLogger(p51NuevoPedidoAdicionalOfertaController.class);
	private PaginationManager<NuevoPedidoOferta> paginationManager = new PaginationManagerImpl<NuevoPedidoOferta>();
	
	@Autowired
	private VAgruComerOfertaPaService vAgruComerOfertaPaService;

	@Autowired
	private NuevoPedidoOfertaService nuevoPedidoOfertaService;
	
	@Autowired
	private VOfertaPaService vOfertaPaService;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	@Autowired
	private VFestivoCentroService vFestivoCentroService;
	
	@Autowired
	private PedidoAdicionalService pedidoAdicionalService;
	
	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@Autowired
	private VSicFPromocionesService vSicFPromocionesService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
		return "p51_nuevoPedidoOferta";
	}

	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody NuevoPedidoOfertaPagina loadDataGrid(
			@RequestBody NuevoPedidoOferta nuevoPedidoOferta,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
			if ("".equals(index)){
				index = "codArticulo";
			}
				
			if ("N".equals(recarga)){
		        Pagination pagination= new Pagination(max,page);
		        if (index!=null){
		            pagination.setSort(index);
		        }
		        if (sortOrder!=null){
		            pagination.setAscDsc(sortOrder);
		        }
				
		        NuevoPedidoOfertaLista nuevoPedidoOfertaLista = null;
		        List<NuevoPedidoOferta> listaNuevoPedidoOferta = null;
		        List<NuevoPedidoOferta> listaNuevoOfertaPag = null;
				
				try {
					//Borrado de los datos de calendario de la tabla temporal
					TPedidoAdicional registroCalendario = new TPedidoAdicional();
					registroCalendario.setIdSesion(session.getId());
					registroCalendario.setCodCentro(nuevoPedidoOferta.getCodCentro());
					registroCalendario.setPantalla(Constantes.PANTALLA_CALENDARIO);
					this.tPedidoAdicionalService.deleteCalendario(registroCalendario);
					
					User user = (User) session.getAttribute("user");
					
					listaNuevoPedidoOferta = this.nuevoPedidoOfertaService.findAll(nuevoPedidoOferta, session.getId(), user.getCode(), session);
					
					//Obtener el tipo de listado a partir del primer elemento de la lista
					if (listaNuevoPedidoOferta!= null && listaNuevoPedidoOferta.size()>0){
						if (listaNuevoPedidoOferta.get(0) != null && listaNuevoPedidoOferta.get(0).getCodArticulo() != null){
							VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
							vDatosDiarioArt.setCodArt(listaNuevoPedidoOferta.get(0).getCodArticulo());
							VDatosDiarioArt vDatosAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
							if (vDatosAux.getGrupo1().equals(new Long(1))){
								//if (vDatosAux.getGrupo2().equals(new Long(1)) || vDatosAux.getGrupo2().equals(new Long(2)) || vDatosAux.getGrupo2().equals(new Long(7))) {
									nuevoPedidoOferta.setFlgTipoListado(Constantes.PED_ADI_TIPO_LISTADO_FRESCO_PURO);
								//} else {
									//nuevoPedidoOferta.setFlgTipoListado(Constantes.PED_ADI_TIPO_LISTADO_ALIMENTACION);
								//}
							} else {
								 nuevoPedidoOferta.setFlgTipoListado(Constantes.PED_ADI_TIPO_LISTADO_ALIMENTACION);
							}
						}
					}

					this.eliminarTablaSesion(session.getId(),new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL),nuevoPedidoOferta.getCodCentro());
					if (listaNuevoPedidoOferta != null && listaNuevoPedidoOferta.size()>0) {
						this.nuevoPedidoOfertaService.insertarTablaSesionNuevoOferta(listaNuevoPedidoOferta, session.getId(), user.getCode(), new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
					}
					
					TPedidoAdicional registro = new TPedidoAdicional();
					
					registro.setIdSesion(session.getId());
					
					
					registro.setCodCentro(user.getCentro().getCodCentro());
					registro.setPantalla(Constantes.PANTALLA_OFERTAS);
					listaNuevoOfertaPag = this.obtenerTablaSesionNuevoOferta(registro, pagination);
					if (listaNuevoOfertaPag!=null){
						nuevoPedidoOfertaLista = new NuevoPedidoOfertaLista();
						nuevoPedidoOfertaLista.setDatos(listaNuevoPedidoOferta);
						
						/*------- Recarga de días de servicio ---------------------------------------------*/
						this.nuevoPedidoOfertaService.recargaDiasServicioArticulosPagina (nuevoPedidoOferta, listaNuevoOfertaPag, session.getId());
						/*------- Fin Recarga de días de servicio -----------------------------------------*/ 
						
						//Tratamiento de listado de pedidos de pantalla
						//listaNuevoOfertaPag = this.nuevoPedidoOfertaService.validarPedidosPagina(nuevoPedidoOferta, listaNuevoOfertaPag, user, session.getId(),session);

					}
					
				} catch (Exception e) {
					e.printStackTrace();
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
				
				//Montaje de lista paginada
				NuevoPedidoOfertaPagina nuevoPedidoOfertaPagina = new NuevoPedidoOfertaPagina();
				Page<NuevoPedidoOferta> listaNuevoPedidoOfertaPaginada = null;
				
				if (listaNuevoOfertaPag != null) {
					int records = listaNuevoPedidoOferta.size();
					listaNuevoPedidoOfertaPaginada = this.paginationManager.paginate(new Page<NuevoPedidoOferta>(), listaNuevoOfertaPag,
							max.intValue(), records, page.intValue());	
					nuevoPedidoOfertaPagina.setDatos(listaNuevoPedidoOfertaPaginada);
					nuevoPedidoOfertaPagina.setEstado(nuevoPedidoOfertaLista.getEstado());
					nuevoPedidoOfertaPagina.setDescEstado(nuevoPedidoOfertaLista.getDescEstado());
					
				} else {
					nuevoPedidoOfertaPagina.setDatos(new Page<NuevoPedidoOferta>());
					nuevoPedidoOfertaPagina.setEstado(new Long(0));
					nuevoPedidoOfertaPagina.setDescEstado("");
				}
				nuevoPedidoOfertaPagina.setFlgTipoListado(nuevoPedidoOferta.getFlgTipoListado());
				
				return nuevoPedidoOfertaPagina;
			}else{
				return loadDataGridRecarga(nuevoPedidoOferta, page, max, index, sortOrder, response, session);
			}
	}
	
	@RequestMapping(value = "/loadDataGridRecarga", method = RequestMethod.POST)
	public  @ResponseBody NuevoPedidoOfertaPagina loadDataGridRecarga(
			@RequestBody NuevoPedidoOferta nuevoPedidoOferta,
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
		
	        NuevoPedidoOfertaPagina nuevoPedidoOfertaPagina = new NuevoPedidoOfertaPagina();
	        List<NuevoPedidoOferta> listaRecarga = new ArrayList<NuevoPedidoOferta>();
	        TPedidoAdicional registro = new TPedidoAdicional();
			try {
				for (CamposModificadosNO campo : nuevoPedidoOferta.getListadoModificados())
				{
					actualizarDatosModificadosNO(campo, nuevoPedidoOferta, session);
				}
				
				//Sólo tendremos que ordenar cuando recarguemos al pulsar sobre la columna de iconos,
				//al paginar no debe ordenar.
				
				
				registro.setIdSesion(session.getId());
				//registro.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
				//registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL)));
				User user = (User) session.getAttribute("user");
				
				registro.setCodCentro(user.getCentro().getCodCentro());
				registro.setPantalla(Constantes.PANTALLA_OFERTAS);
				listaRecarga = this.obtenerTablaSesionNuevoOferta(registro, pagination);
				
				if (listaRecarga!=null){
					/*------- Recarga de días de servicio ---------------------------------------------*/
					this.nuevoPedidoOfertaService.recargaDiasServicioArticulosPagina (nuevoPedidoOferta, listaRecarga, session.getId());
					/*------- Fin Recarga de días de servicio -----------------------------------------*/ 
					
					//Tratamiento de listado de pedidos de pantalla
					//listaRecarga = this.nuevoPedidoOfertaService.validarPedidosPagina(nuevoPedidoOferta, listaRecarga, user, session.getId(), session);
				}
				
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			Page<NuevoPedidoOferta> listaNuevoPedidoOfertaPaginada = null;
			
			if (listaRecarga != null) {
				int records = this.tPedidoAdicionalService.findAllCount(registro).intValue();
				listaNuevoPedidoOfertaPaginada = this.paginationManager.paginate(new Page<NuevoPedidoOferta>(), listaRecarga,
						max.intValue(), records, page.intValue());	
				nuevoPedidoOfertaPagina.setDatos(listaNuevoPedidoOfertaPaginada);
				//nuevoPedidoOfertaPagina.setEstado(nuevoPedidoOfertaListaGuardada.getEstado());
				//nuevoPedidoOfertaPagina.setDescEstado(nuevoPedidoOfertaListaGuardada.getDescEstado());
				
			} else {
				nuevoPedidoOfertaPagina.setDatos(new Page<NuevoPedidoOferta>());
				nuevoPedidoOfertaPagina.setEstado(new Long(0));
				nuevoPedidoOfertaPagina.setDescEstado("");
			}
			nuevoPedidoOfertaPagina.setFlgTipoListado(nuevoPedidoOferta.getFlgTipoListado());
			
			return nuevoPedidoOfertaPagina;
	}

	/*@RequestMapping(value = "/saveDataGrid", method = RequestMethod.POST)
	public  @ResponseBody NuevoPedidoOfertaPagina saveDataGrid(
			@RequestBody NuevoPedidoOferta nuevoPedidoOferta,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
			//Control de registros erróneos
			int totalErroneos = 0;
			
	        Pagination pagination= new Pagination(max,page);
	        if (index!=null){
	            pagination.setSort(index);
	        }
	        if (sortOrder!=null){
	            pagination.setAscDsc(sortOrder);
	        }
		
	        NuevoPedidoOfertaPagina nuevoPedidoOfertaPagina = new NuevoPedidoOfertaPagina();
	        List<NuevoPedidoOferta> listaRecarga = new ArrayList<NuevoPedidoOferta>();
	        TPedidoAdicional registro = new TPedidoAdicional();

			try {
				//Tenemos que recorrer la lista que recibimos con los registros a actualizar.
				for (CamposModificadosNO campo : nuevoPedidoOferta.getListadoModificados())
				{
					actualizarDatosModificadosNO(campo, nuevoPedidoOferta, session);
				}
				
				TPedidoAdicional regModificados = new TPedidoAdicional();
				
				regModificados.setIdSesion(session.getId());
				User user = (User) session.getAttribute("user");
				
				regModificados.setCodCentro(user.getCentro().getCodCentro());
				regModificados.setPantalla(Constantes.PANTALLA_OFERTAS);
				regModificados.setCodError("9");
				List<NuevoPedidoOferta> listaModificadosActualizacion = this.obtenerTablaSesionNuevoOferta(regModificados, null);
				List<PedidoAdicionalCompleto> list = this.obtenerListaPedidosCompleto(listaModificadosActualizacion,nuevoPedidoOferta.getFlgTipoListado());
				PedidoAdicionalResponse[] insertarResponse = this.pedidoAdicionalService.insertarPedido(list, session);
				for (PedidoAdicionalResponse pedidoAdicionalResponse : insertarResponse){
					
					
					TPedidoAdicional registroEnviado = new TPedidoAdicional();
					registroEnviado.setIdSesion(session.getId());
					registroEnviado.setCodCentro(user.getCentro().getCodCentro());
					registroEnviado.setPantalla(Constantes.PANTALLA_OFERTAS);
					registroEnviado.setCodArticulo(pedidoAdicionalResponse.getReferencia().longValue());
					
					if (Integer.parseInt(pedidoAdicionalResponse.getCodigoRespuesta()) == 0){
						//Se elimina de la lista porque ya está guardado
						this.tPedidoAdicionalService.deleteArticulo(registroEnviado);
					}else{
						totalErroneos++;
						registroEnviado.setCodError(pedidoAdicionalResponse.getCodigoRespuesta());
						registroEnviado.setDescError(pedidoAdicionalResponse.getDescripcionRespuesta());
						this.tPedidoAdicionalService.updateErrorArticulo(registroEnviado);
					}
					
					
					
				}
				registro = new TPedidoAdicional();
				registro.setIdSesion(session.getId());
				registro.setCodCentro(user.getCentro().getCodCentro());
				registro.setPantalla(Constantes.PANTALLA_OFERTAS);
				listaRecarga = this.obtenerTablaSesionNuevoOferta(registro, pagination);
				
				if (listaRecarga!=null){
					//------- Recarga de días de servicio ---------------------------------------------/
					this.nuevoPedidoOfertaService.recargaDiasServicioArticulosPagina (nuevoPedidoOferta, listaRecarga, session.getId());
					//------- Fin Recarga de días de servicio -----------------------------------------/ 
					
					//Tratamiento de listado de pedidos de pantalla
					//listaRecarga = this.nuevoPedidoOfertaService.validarPedidosPagina(nuevoPedidoOferta, listaRecarga, user, session.getId(), session);
				}

			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			Page<NuevoPedidoOferta> listaPedidoOfertaPaginada = null;
			
			if (listaRecarga != null) {
				int records = this.tPedidoAdicionalService.findAllCount(registro).intValue();
				listaPedidoOfertaPaginada = this.paginationManager.paginate(new Page<NuevoPedidoOferta>(), listaRecarga,
						max.intValue(), records, page.intValue());	
				nuevoPedidoOfertaPagina.setDatos(listaPedidoOfertaPaginada);
				
			} else {
				nuevoPedidoOfertaPagina.setDatos(new Page<NuevoPedidoOferta>());
			}
			
			//Control de mensajes de guardado
			nuevoPedidoOfertaPagina.setTotalErroneos(new Long(totalErroneos));
	          
			return nuevoPedidoOfertaPagina;
	}*/
	
	private List<PedidoAdicionalCompleto> obtenerListaPedidosCompleto(List<NuevoPedidoOferta> listaModificadosActualizacion, String strFlgTipoListado)
	{
		List<PedidoAdicionalCompleto> listaPedidoAdicionalCompleto = new ArrayList<PedidoAdicionalCompleto>();
		NuevoPedidoOferta registro = new NuevoPedidoOferta();
		
		PedidoAdicionalCompleto nuevoRegistro = new PedidoAdicionalCompleto();
		
		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
		
		try {
			
			if (listaModificadosActualizacion != null && listaModificadosActualizacion.size()>0)
			{
				for (int i =0;i<listaModificadosActualizacion.size();i++)
				{
					nuevoRegistro = new PedidoAdicionalCompleto();
					
					registro = (NuevoPedidoOferta)listaModificadosActualizacion.get(i);
					
					nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
					nuevoRegistro.setCodArt((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					nuevoRegistro.setDescArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
					nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
					nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprov() != null && !("".equals(registro.getTipoAprov())))?registro.getTipoAprov():null);
					nuevoRegistro.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?Integer.parseInt(registro.getTipoPedido()):null);
					nuevoRegistro.setUniCajas((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?registro.getUniCajaServ():null);
					nuevoRegistro.setUser((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
					nuevoRegistro.setFechaIni((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?df.parse(registro.getFechaInicio()):null);
					nuevoRegistro.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
					nuevoRegistro.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
					nuevoRegistro.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
					nuevoRegistro.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?df.parse(registro.getFecha2()):null);
					nuevoRegistro.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?df.parse(registro.getFecha3()):null);
					nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?df.parse(registro.getFechaPilada()):null);
					nuevoRegistro.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?df.parse(registro.getFechaFin()):null);
					nuevoRegistro.setCapacidadMaxima((registro.getImplInicial() != null && !("".equals(registro.getImplInicial().toString())))?registro.getImplInicial():null);
					nuevoRegistro.setImplantacionMinima((registro.getImplFinal() != null && !("".equals(registro.getImplFinal().toString())))?registro.getImplFinal():null);
					nuevoRegistro.setTipoPedido(new Integer(2));
					
					if (registro.getAnoOferta() != null && !("".equals(registro.getAnoOferta()))&&
						registro.getNumOferta() != null && !("".equals(registro.getNumOferta())))
					{
						nuevoRegistro.setOferta(registro.getAnoOferta()+"-"+registro.getNumOferta());
					}
					else
					{
						nuevoRegistro.setOferta(null);
					}
					
					if (Constantes.PED_ADI_TIPO_LISTADO_FRESCO_PURO.equals(strFlgTipoListado))
					{
						nuevoRegistro.setFrescoPuro(true);
					}
					else
					{
						nuevoRegistro.setFrescoPuro(false);
					}
					
					listaPedidoAdicionalCompleto.add(nuevoRegistro);
				}
			}
		} catch (Exception e) {
			logger.error("obtenerListaPedidosCompleto="+e.toString());
			e.printStackTrace();
		}
			
		return listaPedidoAdicionalCompleto;
		
	}

	
	@RequestMapping(value="/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerOfertaPa> getAreaData(
			@RequestBody VAgruComerOfertaPa vAgruComerRefPedidos,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			return this.vAgruComerOfertaPaService.findAll(vAgruComerRefPedidos);
		} catch (Exception e) {
		    //ogger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		     throw e;
		}
	}	
	
	@RequestMapping(value="/loadOfertas", method = RequestMethod.POST)
	public @ResponseBody List<VOfertaPa> getOfertas(
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			VOfertaPa vOfertaPa = new VOfertaPa();
			User user = (User) session.getAttribute("user");
			vOfertaPa.setCodCentro(user.getCentro().getCodCentro());
			vOfertaPa.setFechaFin(new Date());
			return this.vOfertaPaService.findAllVigentesCentro(vOfertaPa);
		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		     throw e;
		}
	}	
	
	@RequestMapping(value="/getOferta", method = RequestMethod.POST)
	public @ResponseBody VOfertaPa getOfertaDetalle(
			@RequestParam(value = "oferta", required = true)String oferta,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			VOfertaPa vOfertaPa = new VOfertaPa();
			User user = (User) session.getAttribute("user");
			vOfertaPa.setCodCentro(user.getCentro().getCodCentro());
			String[] codOferta = oferta.split("-");
			vOfertaPa.setAnoOferta(Long.valueOf(codOferta[0]));
			vOfertaPa.setNumOferta(Long.valueOf(codOferta[1]));
			VOfertaPa ofertaAux =  this.vOfertaPaService.findOneVigente(vOfertaPa);

			return ofertaAux;
		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		     throw e;
		}
	}	
	
	@RequestMapping(value="/loadDates", method = RequestMethod.POST)
	public @ResponseBody NuevoPedidoOferta loadDates(@RequestBody NuevoPedidoOferta nuevoPedidoOferta,
		 HttpSession session, HttpServletResponse response) throws Exception {
	    
		try {	
			User user = (User) session.getAttribute("user");
			if (Constantes.PED_ADI_TIPO_LISTADO_FRESCO_PURO.equals(nuevoPedidoOferta.getFlgTipoListado())){
				this.loadDatesFP(nuevoPedidoOferta, user);
			} else {
				this.loadDatesAFI(nuevoPedidoOferta, user);
			} 

		} catch (Exception e) {		  
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        throw e;
		}
		return nuevoPedidoOferta;
	}
	
	private NuevoPedidoOferta loadDatesFP(NuevoPedidoOferta nuevoPedidoOferta, User user) throws Exception {
	    
		List<Map<String,Object>> listaFechasSiguientes = null;
		VFestivoCentro vFestivoCentro = new VFestivoCentro();
		vFestivoCentro.setCodCentro(nuevoPedidoOferta.getCodCentro());
		vFestivoCentro.setFechaInicio(Utilidades.convertirStringAFecha(nuevoPedidoOferta.getFechaInicio()));
		vFestivoCentro.setFechaFin(Utilidades.convertirStringAFecha(nuevoPedidoOferta.getFechaFin()));

		listaFechasSiguientes = this.vFestivoCentroService.getNextDays(vFestivoCentro, Constantes.NUM_DIAS_INTERVALO_FRESCOS_MODIFICACION);

		if ( null != nuevoPedidoOferta.getFechaFin()){
			if (listaFechasSiguientes != null && listaFechasSiguientes.size() > 0) {//Fecha2
	    		Map<String,Object> m = listaFechasSiguientes.get(0);
	    		nuevoPedidoOferta.setFecha2((String) m.get("SIGUIENTE_DIA"));
	    		if (listaFechasSiguientes.size() > 1) {//Fecha3
		    		m = listaFechasSiguientes.get(1);
		    		nuevoPedidoOferta.setFecha3((String) m.get("SIGUIENTE_DIA"));
		    		if (listaFechasSiguientes.size() > 2) {//Fecha Pilada{
			    		m = listaFechasSiguientes.get(2);
			    		nuevoPedidoOferta.setFechaPilada((String) m.get("SIGUIENTE_DIA"));
		    		}else{
		    			nuevoPedidoOferta.setFechaPilada(null);
		    		}
	    		}else{
	    			nuevoPedidoOferta.setFecha3(null);
					nuevoPedidoOferta.setFechaPilada(null);
	    		}
	    	}else{
	    		nuevoPedidoOferta.setFecha2(null);
				nuevoPedidoOferta.setFecha3(null);
				nuevoPedidoOferta.setFechaPilada(null);
	    	}
	    }else{
    		nuevoPedidoOferta.setFecha2(null);
			nuevoPedidoOferta.setFecha3(null);
			nuevoPedidoOferta.setFechaPilada(null);
	    }
		
		this.nuevoPedidoOfertaService.comprobacionBloqueos(nuevoPedidoOferta, user, nuevoPedidoOferta.getFlgTipoListado(), false);

		return nuevoPedidoOferta;
	}
	
	private NuevoPedidoOferta loadDatesAFI(NuevoPedidoOferta nuevoPedidoOferta, User user) throws Exception {
	    
		nuevoPedidoOferta.setFechaPilada(nuevoPedidoOferta.getFechaInicio());
		this.nuevoPedidoOfertaService.comprobacionBloqueos(nuevoPedidoOferta, user, nuevoPedidoOferta.getFlgTipoListado(), false);

		return nuevoPedidoOferta;
	}
	
	/*private void setImplantacionInicial(NuevoPedidoOferta nuevoPedidoOferta) throws Exception{
		
		Date fechaIni = Utilidades.convertirStringAFecha(nuevoPedidoOferta.getFechaInicio());
		Date fechaFin = Utilidades.convertirStringAFecha(nuevoPedidoOferta.getFechaFin());
		DateTime dt = new DateTime(fechaFin).withMillisOfDay(0);
		if (dt.isBefore(fechaIni.getTime())){
			fechaFin = fechaIni;
			nuevoPedidoOferta.setFechaFin(nuevoPedidoOferta.getFechaInicio());
		}
		ImplantacionInicial implantacionInicial = new ImplantacionInicial();
		implantacionInicial.setCodArticulo(nuevoPedidoOferta.getCodArticulo());
		implantacionInicial.setCodLoc(nuevoPedidoOferta.getCodCentro());
		implantacionInicial.setFechaInicio(fechaIni);
		implantacionInicial.setFechaFin(fechaFin);
		ImplantacionInicial implantacionInicialAux = this.implantacionInicialService.getImplantacionInicial(implantacionInicial);
		nuevoPedidoOferta.setImplInicial(implantacionInicialAux.getVentaMedia());
		DateTime fIni = new DateTime(fechaIni);
		DateTime fFin = new DateTime(fechaFin);
		Integer days = Days.daysBetween(fIni.toDateMidnight() , fFin.toDateMidnight() ).getDays(); 
		if (days > 7){
			BigDecimal capacidad = new BigDecimal(nuevoPedidoOferta.getImplInicial());
			BigDecimal perctg = new BigDecimal(0.2);
			BigDecimal implantacionMinima = capacidad.multiply(perctg);
			BigDecimal im = implantacionMinima.setScale(0, RoundingMode.UP);
			nuevoPedidoOferta.setImplFinal(im.doubleValue());
		} else {
			nuevoPedidoOferta.setImplFinal(nuevoPedidoOferta.getImplInicial());
		}
	} */

	private void eliminarTablaSesion(String idSesion, Long clasePedido, Long codCentro) throws Exception{
		
		TPedidoAdicional registro = new TPedidoAdicional();
		
		registro.setIdSesion(idSesion);
		registro.setClasePedido(clasePedido);
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_OFERTAS);

		this.tPedidoAdicionalService.delete(registro);
	}
	
	private List<NuevoPedidoOferta> obtenerTablaSesionNuevoOferta(TPedidoAdicional tPedidoAdicional, Pagination pagination) throws Exception {

		List<NuevoPedidoOferta> listaNuevoOferta = new ArrayList<NuevoPedidoOferta>();

			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalService.findAllPaginate(tPedidoAdicional, pagination);
			
			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				TPedidoAdicional registro = null;
				for (int i =0;i<listaTPedidoAdicional.size();i++)
				{
					NuevoPedidoOferta articulo = new NuevoPedidoOferta();
					
					registro = (TPedidoAdicional)listaTPedidoAdicional.get(i);
					articulo.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
					//articulo.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
					articulo.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					articulo.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
					articulo.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
					articulo.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
					articulo.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
					articulo.setTipoAprov((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
					articulo.setBloqueado((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
					articulo.setFechaMinima(registro.getFechaMinima());
					articulo.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
					articulo.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
					articulo.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
					articulo.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
					articulo.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
					
					articulo.setImplInicial((registro.getCapMax() != null && !("0.0".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
					articulo.setImplFinal((registro.getCapMin() != null && !("0.0".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
					articulo.setCantidad1((registro.getCantidad1() != null && !("0.0".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
					articulo.setCantidad2((registro.getCantidad2() != null && !("0.0".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
					articulo.setCantidad3((registro.getCantidad3() != null && !("0.0".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
					
					articulo.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
					String codError = (registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null;
					if (null != codError){
						articulo.setCodError(new Long(codError));
					}
					articulo.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
					
					if (null != registro.getCodError()){
						articulo.setUsuario(registro.getUsuario());
						articulo.setPerfil(registro.getPerfil());
					}
					String[] oferta = registro.getOferta().split("-");
					articulo.setAnoOferta(new Long(oferta[0]));
					articulo.setNumOferta(new Long(oferta[1]));
					listaNuevoOferta.add(articulo);
				}
			}
			
		return listaNuevoOferta;
	}
	
	private void actualizarDatosModificadosNO(CamposModificadosNO campo, NuevoPedidoOferta nuevoPedidoOferta, HttpSession session) throws Exception{
		TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
		tPedidoAdicional.setIdSesion(session.getId());
		User user = (User) session.getAttribute("user");
		
		tPedidoAdicional.setCodCentro(user.getCentro().getCodCentro());
		tPedidoAdicional.setPantalla(Constantes.PANTALLA_OFERTAS);
		tPedidoAdicional.setCodArticulo(campo.getCodArticulo());
		//Obtenemos el registro de la lista guardada en sesión.
		tPedidoAdicional = this.tPedidoAdicionalService.findOne(tPedidoAdicional);
		
		//Se sobreescribe el valor del identificador con null porque todavía no debe tener ningún identificador asignado
		tPedidoAdicional.setIdentificador(null);
		
		if (tPedidoAdicional != null){
			//Actualizamos los valores que me llegan.
			tPedidoAdicional.setFechaInicio(campo.getFechaInicio());
			tPedidoAdicional.setFechaFin(campo.getFechaFin());
			if (Constantes.PED_ADI_TIPO_LISTADO_FRESCO_PURO.equals(nuevoPedidoOferta.getFlgTipoListado())){
				tPedidoAdicional.setFecha2(campo.getFecha2());
				tPedidoAdicional.setFecha3(campo.getFecha3());
				tPedidoAdicional.setFechaPilada(campo.getFechaPilada());
				tPedidoAdicional.setCantidad1(campo.getCantidad1());
				tPedidoAdicional.setCantidad2(campo.getCantidad2());
				tPedidoAdicional.setCantidad3(campo.getCantidad3());
			}else{
				tPedidoAdicional.setCapMax(campo.getImplInicial());
				if (campo.getImplFinal()==null){
					tPedidoAdicional.setCapMin(campo.getImplInicial());
				}else{
					tPedidoAdicional.setCapMin(campo.getImplFinal());
				}
			}
			tPedidoAdicional.setCodError(campo.getCodError().toString());
			tPedidoAdicional.setDescError(campo.getDescError());
			//Actualizamos la lista con el registro.
			this.tPedidoAdicionalService.updatePedido(tPedidoAdicional);
		}
	}
	
	@RequestMapping(value="/loadGondola", method = RequestMethod.POST)
	public @ResponseBody List<VSicFPromociones> getGondola(
			@RequestBody VSicFPromociones vSicFPromociones,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			return this.vSicFPromocionesService.findAllGondola(vSicFPromociones);
		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}	

}