package es.eroski.misumi.control;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import es.eroski.misumi.model.CapraboMotivoNoPedible;
import es.eroski.misumi.model.CapraboMotivoNoPedibleArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleCentroArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleMotivo;
import es.eroski.misumi.model.CcRefCentro;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.HistoricoVentaUltimoMes;
import es.eroski.misumi.model.Motivo;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MovimientoStock;
import es.eroski.misumi.model.OfertaVigente;
import es.eroski.misumi.model.PendientesRecibir;
import es.eroski.misumi.model.QueHacerRef;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.StockNoServido;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.TextoMotivo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VDatosDiarioCap;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.CapraboMotivosNoPedibleService;
import es.eroski.misumi.service.iface.CcRefCentroService;
import es.eroski.misumi.service.iface.EansService;
import es.eroski.misumi.service.iface.HistoricoVentaUltimoMesService;
import es.eroski.misumi.service.iface.MovimientosStockService;
import es.eroski.misumi.service.iface.OfertaVigenteService;
import es.eroski.misumi.service.iface.PendientesRecibirService;
import es.eroski.misumi.service.iface.PlanogramaCentroService;
import es.eroski.misumi.service.iface.QueHacerRefService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.TMisMcgCapraboService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.VCentrosPlataformasService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VDatosDiarioCapService;
import es.eroski.misumi.service.iface.VReferenciaActiva2Service;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/referenciasCentroCaprabo")
public class p13ReferenciasCentroCapraboController {
	
	private static Logger logger = Logger.getLogger(p13ReferenciasCentroCapraboController.class);

	@Value( "${tipoAprovisionamiento.centralizado}" )
    private String tipoAprovisionamientoCentralizado;
	
	@Value( "${tipoAprovisionamiento.grupaje}" )
    private String tipoAprovisionamientoGrupaje;
	
	@Value( "${tipoAprovisionamiento.descentralizado}" )
    private String tipoAprovisionamientoDescentralizado;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;

	@Autowired
	private OfertaVigenteService ofertaVigenteService;

	@Autowired
	private MovimientosStockService vMovimientosStockService;
	
	@Autowired
	private PendientesRecibirService vPendientesRecibirService;
	
	@Autowired
	private HistoricoVentaUltimoMesService historicoVentaUltimoMesService;
	
	@Autowired
	private PlanogramaCentroService planogramaCentroService;
	
	@Autowired
	private CcRefCentroService ccRefCentroService;
	
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private QueHacerRefService queHacerRefService;
	
	@Autowired
	private VReferenciaActiva2Service vReferenciaActiva2Service;

	@Autowired
	private VCentrosPlataformasService vCentrosPlataformasService;
	
	@Autowired
	private TMisMcgCapraboService tMisMcgCapraboService;

	@Autowired
	private VDatosDiarioCapService vDatosDiarioCapService;

	@Autowired
	private CapraboMotivosNoPedibleService capraboMotivosNoPedibleService;

	@Autowired
	private EansService eansService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@Resource
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model,
			@RequestParam(required = false, defaultValue = "") String reference,
			@RequestParam(required = false, defaultValue = "") String pagConsulta,
			HttpSession session,HttpServletResponse response
			) throws Exception {
		
		if (!"".equals(reference) || reference!=null){
			model.put("reference", reference);
			model.put("pagConsulta", pagConsulta);
		}else{
			model.put("reference", "");
		}
		
		User user = (User) session.getAttribute("user");

		try {
			if (utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode())){
				return "p13_caprabo_referenciasCentroCaprabo";
			}
		
			return "p13_caprabo_referenciasCentro";
			
		
		} catch (Exception e) {
			  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			  throw e;
		}

	}
	
	@RequestMapping(value="/loadDatosMaestrosFijo", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentro loadDatosMaestroFijo(
			@RequestBody ReferenciasCentro vReferenciasCentro,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		session.setAttribute("listMotGeneral" + vReferenciasCentro.getOrigenPantalla(),  new ArrayList<Motivo>());
		session.setAttribute("listMotMMC" + vReferenciasCentro.getOrigenPantalla(),  new ArrayList<Motivo>());
		session.setAttribute("pedible", null);
		session.setAttribute("textilPedible", false);
		session.setAttribute("performed", "F");
		try {
			ReferenciasCentro referenciasCentro = vReferenciasCentro;
			boolean existenDatosCaprabo = true;
			
			//La busqueda en T_MIS_MCG_CAPRABO hay que realizarla por COD_CENTRO/COD_ARTICULO_CAPRABO o COD_CENTRO/EAN.
			//Decidiremos segun longitud del campo  vReferenciasCentro.COD_ART_CAPRABO si es un EAN o no. 
			//Si es un EAN, buscaremos el COD_ART_CAPRABO y haremos la busqueda por COD_CENTRO/COD_ARTICULO_CAPRABO
			
			//IMPORTANTE: el tratamiento del EAN, habra que borrarlo y hacerlo de manera correcta, como se esta haciendo para Eroski
			//una vez que se meta la busqueda universal!
			
			if (referenciasCentro.getCodArtCaprabo().toString().length() > 8){ //si tiene mas de 8 digitos es un EAN
				//Si existe el EAN en TMisMcgCaprabo se coge de ahí
				TMisMcgCaprabo tMisMcgCapraboRes;
				TMisMcgCaprabo tMisMcgCapraboAux = new TMisMcgCaprabo();
				tMisMcgCapraboAux.setEan(referenciasCentro.getCodArtCaprabo()); //Lo que venia de pantalla no era un COD_ART_CAPRABO era un EAN
				tMisMcgCapraboAux.setCodCentro(referenciasCentro.getCodCentro());
				
				//Consultamos T_MIS_MCG_CAPRABO por COD_CENTRO/EAN
				tMisMcgCapraboRes = this.tMisMcgCapraboService.findOne(tMisMcgCapraboAux);
				
				if (tMisMcgCapraboRes!= null && tMisMcgCapraboRes.getCodArtCaprabo()!=null){
					//Cargamos en COD_ART_CAPRABO el codigo correcto
					referenciasCentro.setCodArtCaprabo(tMisMcgCapraboRes.getCodArtCaprabo()); 
				}else{	
					//Si no existe en TmisMcgCaprabo se busca la referencia de Eroski en la tabla de EANS
					Long codArtEroski = this.eansService.obtenerReferenciaEan(referenciasCentro.getCodArtCaprabo());
					if (codArtEroski != null){
						//Cargamos en COD_ART_CAPRABO el codigo correcto
						referenciasCentro.setCodArt(codArtEroski);
						referenciasCentro.setCodArtCaprabo(null);
						referenciasCentro.setCodArtCaprabo(obtenerReferenciaCaprabo(referenciasCentro)); 
					}else{
						existenDatosCaprabo = false;
					}
				}
			}
			
			//HASTA AQUI!!
			
			if (existenDatosCaprabo){
				//Se consulta V_SURTIDO_TIENDA filtrando por COD_CENTRO y COD_ART_CAPRABO
				referenciasCentro.setSurtidoTienda(obtenerSurtidoTienda(referenciasCentro));
				
				//Se consulta T_MIS_MCG_CAPRABO filtrando por COD_CENTRO y COD_ART_CAPRABO
				referenciasCentro.settMisMcgCaprabo(obtenerMcgCaprabo(referenciasCentro));
				
				//Obtención de motivo revisar porque ahora sólo se muestra uno
				if (referenciasCentro.gettMisMcgCaprabo()!=null && referenciasCentro.gettMisMcgCaprabo().getMotivo()!=null){
					referenciasCentro.setMotivoCaprabo(referenciasCentro.gettMisMcgCaprabo().getMotivo());
				}else{
				
					//Carga de la sesion en el objeto de motivos
					VSurtidoTienda surtidoTienda = referenciasCentro.getSurtidoTienda();
					if (null != surtidoTienda && surtidoTienda.getPedir().toUpperCase().equals("N") && surtidoTienda.getCatalogo().equals("B")) {
		    			//Si es descentralizado hay que sacar el mensaje BAJA CATALOGO
		    			if (this.tipoAprovisionamientoDescentralizado.equals(surtidoTienda.getTipoAprov())){
		    				Locale locale = LocaleContextHolder.getLocale();
		    				String motivoBajaCatalogo =  this.messageSource.getMessage("p18_caprabo_referenciasCentroPopupPedir.bajaCatalogo", null, locale);
		    				MotivoTengoMuchoPoco motivoTengoMuchoPocoBaja = new MotivoTengoMuchoPoco();
		    				motivoTengoMuchoPocoBaja.setDescripcion(motivoBajaCatalogo);
		    				//listaMotivosTengoMuchoPoco.add(motivoTengoMuchoPoco);
		    				referenciasCentro.setMotivoCaprabo(motivoBajaCatalogo);
				        				
		    			}else{
		    				//Parámetros de búsqueda de motivos
		    				String tipoMovimientoBusqueda = null;
		    				
		    				/*ReferenciasCentro referenciasCentro = new ReferenciasCentro();
		    	    		
		    	    		referenciasCentro.setCodArt(motivoTengoMuchoPoco.getCodArticulo());
		    	    		referenciasCentro.setCodCentro(motivoTengoMuchoPoco.getCodCentro());
		    	    		referenciasCentro.setDiarioArt(obtenerDiarioArt(referenciasCentro));*/
		    	    		
		    				//Búsqueda de tipo de movimiento asociado
		    				//TMisMcgCaprabo tMisMcgCaprabo = obtenerMcgCaprabo(referenciasCentro);
		    				if (referenciasCentro.gettMisMcgCaprabo()!= null && referenciasCentro.gettMisMcgCaprabo().getTipoMov()!=null){
		    					tipoMovimientoBusqueda = referenciasCentro.gettMisMcgCaprabo().getTipoMov();
		    				}
		    				//Búsqueda de motivos 
		    				CapraboMotivoNoPedible capraboMotivoNoPedibleBusqueda = new CapraboMotivoNoPedible();
		    				capraboMotivoNoPedibleBusqueda.setCodLocBusqueda(referenciasCentro.getCodCentro());
		    				capraboMotivoNoPedibleBusqueda.setCodArticuloBusqueda(referenciasCentro.getCodArtCaprabo());
		    				capraboMotivoNoPedibleBusqueda.setTipoMovimientoBusqueda(tipoMovimientoBusqueda);
		    				
		    				CapraboMotivoNoPedible capraboMotivoNoPedible = this.capraboMotivosNoPedibleService.findCentroRefTipo(capraboMotivoNoPedibleBusqueda);
		    				
		    				if (capraboMotivoNoPedible != null 
		    						&& capraboMotivoNoPedible.getDatos() != null
		    						&& capraboMotivoNoPedible.getDatos().get(0) != null){
		    					
		    					CapraboMotivoNoPedibleCentroArt capraboMotivoNoPedibleCentroArt = capraboMotivoNoPedible.getDatos().get(0);
		    					if(capraboMotivoNoPedibleCentroArt.getArticulos()!=null && capraboMotivoNoPedibleCentroArt.getArticulos().get(0)!=null){
		    						CapraboMotivoNoPedibleArt capraboMotivoNoPedibleArt = capraboMotivoNoPedibleCentroArt.getArticulos().get(0);
		        					if(capraboMotivoNoPedibleArt.getMotivos()!=null){
		        						for (CapraboMotivoNoPedibleMotivo capraboMotivoNoPedibleMotivo : capraboMotivoNoPedibleArt.getMotivos()){
		        		    				MotivoTengoMuchoPoco motivoTengoMuchoPocoProc = new MotivoTengoMuchoPoco();
		        		    				motivoTengoMuchoPocoProc.setDescripcion(capraboMotivoNoPedibleMotivo.getDescripcion());
		        		    				//listaMotivosTengoMuchoPoco.add(motivoTengoMuchoPoco);
		        		    				referenciasCentro.setMotivoCaprabo(capraboMotivoNoPedibleMotivo.getDescripcion());
		        						}
		        					}	
		    					}
		    				}
		    			}
					}
				}
				/////
				
				
				//Si la descripcion está vacía en la tabla T_MCG_CAPRABO se busca en la vista V_DAT_DIARIO_CAP
				if (referenciasCentro.gettMisMcgCaprabo()!=null && referenciasCentro.gettMisMcgCaprabo().getDescripArt()!=null &&  !"".equals(referenciasCentro.gettMisMcgCaprabo().getDescripArt())){
					referenciasCentro.setDescArtCaprabo(referenciasCentro.gettMisMcgCaprabo().getDescripArt());
				}else{
					VDatosDiarioCap vDatosDiarioCap = this.obtenerVDatDiarioCap(referenciasCentro);
					if (vDatosDiarioCap!=null){
						referenciasCentro.setDescArtCaprabo(vDatosDiarioCap.getDescripArt());
					}
				}
				
				if (null != referenciasCentro.gettMisMcgCaprabo() && null != referenciasCentro.getSurtidoTienda()){
					
					referenciasCentro.setFechaActivacion(referenciasCentro.gettMisMcgCaprabo().getFechaActivacion());
					
					if (referenciasCentro.getFechaActivacion() != null) { 
							
						//Obtenemos la fecha generacion de surtido tienda. Fecha en la que la referencia pasa de PEDIR N a PEDIR S.
						//if (null != referenciasCentro.getSurtidoTienda()){
							Date FechaGenSurtidoTienda = vSurtidoTiendaService.obtenerFechaGeneracionSurtidoTienda(referenciasCentro.getSurtidoTienda());
							if (FechaGenSurtidoTienda != null) {
								referenciasCentro.setFechaGen(FechaGenSurtidoTienda);
								referenciasCentro.setStrFechaGen(Utilidades.formatearFecha(FechaGenSurtidoTienda));
						
								//Comprobamos si la fecha de generación se debe mostrar o no
								
								//Fecha de generación de surtido tienda
								Calendar calFechaGenSurtidoTienda = Calendar.getInstance();
								calFechaGenSurtidoTienda .setTime(FechaGenSurtidoTienda);
							}
							
							//Fecha de activacion de T_MIS_MCG_CAPRABO
							Calendar calFechaActivacionMcgCaprabo = Calendar.getInstance();
							calFechaActivacionMcgCaprabo .setTime(referenciasCentro.getFechaActivacion());
	
							//Obtenemos la fecha actual
							Calendar calFechaActual = Calendar.getInstance();
							
							//Obtenemos  la diferencia entre las dos fechas 
							long millisDay = 24*60*60*1000;
							double diffDays = Math.floor((calFechaActual.getTimeInMillis() - calFechaActivacionMcgCaprabo.getTimeInMillis())/(millisDay));
							
							if (diffDays <= 30) {//Si la diferencia es menor a 30 días se mostrará en pantalla
								referenciasCentro.setMostrarFechaGen(true);
							}
							else{
								referenciasCentro.setMostrarFechaGen(false);
							}
	
						//}
						
						
							//Los link de Referencia Nueva y Referencia vieja, si el TIPO_MOV = A/B, el link solo se mostrara durante 30 dias
							if (referenciasCentro.gettMisMcgCaprabo().getTipoMov() != null 
									&&  (referenciasCentro.gettMisMcgCaprabo().getTipoMov().equals("A") || referenciasCentro.gettMisMcgCaprabo().getTipoMov().equals("B") )) {
								
								if (diffDays <= 30) {//Si la diferencia es menor a 30 días se mostrará en pantalla
									referenciasCentro.setMostrarLinkRefNuevaVieja(true);
								}
								else{
									referenciasCentro.setMostrarLinkRefNuevaVieja(false);
								}
								
							} else {
								referenciasCentro.setMostrarLinkRefNuevaVieja(true);
							}
						
					}
					
					
					referenciasCentro.setSustituidaPor(referenciasCentro.gettMisMcgCaprabo().getSustituidaPor());  //Referencia Vieja
					referenciasCentro.setSustitutaDe(referenciasCentro.gettMisMcgCaprabo().getSustitutaDe());	   //Referencia Nueva
					
					//Si existe implantación habra que comprobar si este se pinta en rojo o verde.  
					if (referenciasCentro.gettMisMcgCaprabo().getImplantacion() != null && !(referenciasCentro.gettMisMcgCaprabo().getImplantacion().equals(""))) {
						
						referenciasCentro.setImplantacion(referenciasCentro.gettMisMcgCaprabo().getImplantacion().toUpperCase());
						if ((referenciasCentro.getImplantacion() != null)  && !(referenciasCentro.getImplantacion().trim().equals(""))) {
	
								//Si PEDIR = S y existe implantación, se pinta en verde
								if (referenciasCentro.getSurtidoTienda().getPedir() != null && referenciasCentro.getSurtidoTienda().getPedir().equals("S")) { 
									
									referenciasCentro.setFlgColorImplantacion("VERDE");
									referenciasCentro.setMostrarImplantacion(true);
								
								} else {
									
									if (referenciasCentro.getSurtidoTienda().getPedir() != null && referenciasCentro.getSurtidoTienda().getPedir().equals("N") && referenciasCentro.gettMisMcgCaprabo().getStock()!=null) { 
										if (referenciasCentro.gettMisMcgCaprabo().getStock() > 0) {
											referenciasCentro.setFlgColorImplantacion("ROJO");
											referenciasCentro.setMostrarImplantacion(true);
										} else {
											if (null != referenciasCentro.getSurtidoTienda() && vSurtidoTiendaService.comprobarStockMayorACeroCaprabo(referenciasCentro.getSurtidoTienda()) > 0 ){
												referenciasCentro.setFlgColorImplantacion("ROJO");
												referenciasCentro.setMostrarImplantacion(true);
											} else {
												referenciasCentro.setMostrarImplantacion(false);
											}
										}
	
									} else {
										referenciasCentro.setMostrarImplantacion(false);
									}
								}
						}
					} else {
						referenciasCentro.setMostrarImplantacion(false);
					}
					
					//Se anula la búsqueda de stock para que no aparezca por pantalla según Pet 56211
					//referenciasCentro.getValoresStock().setStock(referenciasCentro.gettMisMcgCaprabo().getStock());
					//referenciasCentro.getValoresStock().setDiasStock(referenciasCentro.gettMisMcgCaprabo().getStockDias());
				}
			}
		    return referenciasCentro;
		
		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
		
	}
	
	@RequestMapping(value="/loadDatosMaestros", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentro loadDatosMaestro(
			@RequestBody ReferenciasCentro vReferenciasCentro,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			ReferenciasCentro referenciasCentro = vReferenciasCentro;
			referenciasCentro.setDiarioArt(obtenerDatosDiarioArt(referenciasCentro));
			referenciasCentro.setSurtidoTienda(obtenerSurtidoTienda(referenciasCentro));
			referenciasCentro.setOferta(obtenerOferta(referenciasCentro));
			referenciasCentro.setVariablesPedido(obtenerVariablesPedido(referenciasCentro));
			//Según petición los datos de ffpp pasan al mensaje de cabecera
			//referenciasCentro.setArticuloRelacionado(obtenerRelacionArticulo(referenciasCentro));
			referenciasCentro.setCc(obtenerCc(referenciasCentro));
			 User user = (User) session.getAttribute("user");
//		    if (user.getCentro().getNegocio().equals(Constantes.CENTRO_NEGOCIO_HIPER) && !referenciasCentro.getDiarioArt().getGrupo1().equals(new Long(1)) &&
//					!referenciasCentro.getDiarioArt().getGrupo1().equals(new Long(2))){
		    	QueHacerRef queHacerRef = new QueHacerRef();
			    queHacerRef.setCodLoc(user.getCentro().getCodCentro());
			    queHacerRef.setCodArtFormlog(vReferenciasCentro.getCodArt());
			    queHacerRef = this.queHacerRefService.obtenerAccionRef(queHacerRef);
			    if (null != queHacerRef && null != queHacerRef.getEstado() && (new Long(0)).equals(queHacerRef.getEstado())){
			    	if (null != queHacerRef.getAccion()){
			    		referenciasCentro.setAccion(queHacerRef.getAccion());
			    	} else {
			    		referenciasCentro.setAccion(messageSource.getMessage("p30_popupInfoReferencia.sinAccion", null, LocaleContextHolder.getLocale()));
			    	}
			    } else {
			    	referenciasCentro.setAccion(messageSource.getMessage("p30_popupInfoReferencia.sinAccion", null, LocaleContextHolder.getLocale()));
			    }
//		    }
		    
		    //Datos especificos de textil
			if (referenciasCentro.getDiarioArt().getGrupo1().longValue() == 3) {//Es una referencia de textil
				if (!(this.relacionArticuloService.esReferenciaLote(referenciasCentro.getCodArt()).size() > 0)){ 
					//Es una referenca de textil pero no lote. Recuperamos los datos especificos de textil de V_DATOS_ESPECIFICOS
					
					List<DetallePedido> listaDatosTextil = this.relacionArticuloService.findDatosEspecificosTextilPC(referenciasCentro.getCodArt());
				
					if (listaDatosTextil!= null & listaDatosTextil.size() >0) {
						referenciasCentro.setTemporada(listaDatosTextil.get(0).getTemporada().substring(0, 2) + " " + listaDatosTextil.get(0).getTemporada().substring(2));
						referenciasCentro.setNumOrden(Utilidades.rellenarIzquierda(listaDatosTextil.get(0).getNumOrden().toString(), '0', 3));
						referenciasCentro.setModeloProveedor(listaDatosTextil.get(0).getModeloProveedor());
						referenciasCentro.setTalla(listaDatosTextil.get(0).getDescrTalla());
						referenciasCentro.setColor(listaDatosTextil.get(0).getDescrColor());
					}

				}
			}
			
			return referenciasCentro;
		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}

	

	private TMisMcgCaprabo obtenerMcgCaprabo(Long codArtCaprabo, Long codCentro) throws Exception{
		TMisMcgCaprabo tMisMcgCapraboRes;
		TMisMcgCaprabo tMisMcgCaprabo = new TMisMcgCaprabo();
		tMisMcgCaprabo.setCodArtCaprabo(codArtCaprabo);
		tMisMcgCaprabo.setCodCentro(codCentro);
		tMisMcgCapraboRes = this.tMisMcgCapraboService.findOne(tMisMcgCaprabo);
		
		return tMisMcgCapraboRes;
	}

	private TMisMcgCaprabo obtenerMcgCaprabo(ReferenciasCentro referenciasCentro) throws Exception{
		return this.obtenerMcgCaprabo(referenciasCentro.getCodArtCaprabo(), referenciasCentro.getCodCentro());
	}
	
	private VDatosDiarioCap obtenerVDatDiarioCap(Long codArtCaprabo) throws Exception{
		VDatosDiarioCap vDatosDiarioCapRes;
		VDatosDiarioCap vDatosDiarioCap = new VDatosDiarioCap();
		vDatosDiarioCap.setCodArt(codArtCaprabo);
		vDatosDiarioCapRes = this.vDatosDiarioCapService.findOne(vDatosDiarioCap);
		
		return vDatosDiarioCapRes;
	}

	private VDatosDiarioCap obtenerVDatDiarioCap(ReferenciasCentro referenciasCentro) throws Exception{
		return this.obtenerVDatDiarioCap(referenciasCentro.getCodArtCaprabo());
	}

	private Long obtenerReferenciaCaprabo(ReferenciasCentro referenciasCentro) throws Exception{
		VSurtidoTienda vSurtidoTiendaRes;
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		vSurtidoTienda.setCodArt(referenciasCentro.getCodArt());
		vSurtidoTienda.setCodCentro(referenciasCentro.getCodCentro());
		
		vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

		return vSurtidoTiendaRes.getCodArtCaprabo();
	}

	private VSurtidoTienda obtenerSurtidoTienda(ReferenciasCentro referenciasCentro) throws Exception{
		VSurtidoTienda vSurtidoTiendaRes;
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		vSurtidoTienda.setCodArtCaprabo(referenciasCentro.getCodArtCaprabo());
		vSurtidoTienda.setCodCentro(referenciasCentro.getCodCentro());
		
		vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

		return vSurtidoTiendaRes;
	}

	private OfertaVigente obtenerOferta(ReferenciasCentro referenciasCentro) throws Exception{
		OfertaVigente ofertaVigenteRes;
		OfertaVigente ofertaVigente = new OfertaVigente();
		ofertaVigente.setCodArt(referenciasCentro.getCodArt());
		ofertaVigente.setCodCentro(referenciasCentro.getCodCentro());

		ofertaVigenteRes = this.ofertaVigenteService.findOne(ofertaVigente);
		
		return ofertaVigenteRes;
	}

	private VSurtidoTienda obtenerVariablesPedido(ReferenciasCentro referenciasCentro) throws Exception{
		VSurtidoTienda variablesPedidoRes;
		VSurtidoTienda variablesPedido = new VSurtidoTienda();
		variablesPedido.setCodArt(referenciasCentro.getCodArt());
		variablesPedido.setCodCentro(referenciasCentro.getCodCentro());

		variablesPedidoRes = this.vSurtidoTiendaService.findOneGama(variablesPedido);
		
		return variablesPedidoRes;
	}

	public VDatosDiarioArt obtenerDatosDiarioArt(ReferenciasCentro referenciasCentro) throws Exception{
		VDatosDiarioArt vDatosDiarioArtRes = null;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		
		vDatosDiarioArt.setCodArt(referenciasCentro.getCodArt());
		vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
		
		return vDatosDiarioArtRes;
	}

	@RequestMapping(value="/movimientoStockDiasAnteriores", method = RequestMethod.POST)
	public @ResponseBody List<MovimientoStock> movimientoStockDiasAnteriores(
			@RequestBody MovimientoStock mc,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			return this.vMovimientosStockService.findAllLastDays(mc,session);
		} catch (Exception e) {
//		    logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	
	@RequestMapping(value="/movimientoStockDetalle", method = RequestMethod.POST)
	public @ResponseBody Page<MovimientoStock> movimientoStockDetalle(
			@RequestBody MovimientoStock mc,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "7") Long max,
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
	        List<MovimientoStock> list = null;
		
	        try {
	        	//logger.info("Searching VArtCentroAlta");		
	        	list = this.vMovimientosStockService.findAllDetailsLastDays(mc, pagination);
	        } catch (Exception e) {
	        	//logger.error(StackTraceManager.getStackTrace(e));
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	throw e;
	        }
	        
	        Page<MovimientoStock> result = null;
		
			if (list != null) {
				int records = this.vMovimientosStockService.findAllDetailsLastDays(mc, null).size() ;
				PaginationManager<MovimientoStock> paginationManager = new PaginationManagerImpl<MovimientoStock>();
				result = paginationManager.paginate(new Page<MovimientoStock>(), list, max.intValue(), records, page.intValue());	
				
			} else {
				return new Page<MovimientoStock>();
			}
		 return result;
		
	
	}
	
	@RequestMapping(value="/pendientesRecibir", method = RequestMethod.POST)
	public @ResponseBody PendientesRecibir pendientesRecibir(
			@RequestBody PendientesRecibir pendientesRecibirEntrada,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "7") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpSession session, HttpServletResponse response) throws Exception {
			
	        PendientesRecibir pendientesRecibir = null;
		
	        try {
				ReferenciasCentro referenciasCentro = new ReferenciasCentro();
				referenciasCentro.setCodArt(pendientesRecibirEntrada.getCodArt());
				referenciasCentro.setCodCentro(pendientesRecibirEntrada.getCodCentro());
				VSurtidoTienda vSurtidoTienda = this.obtenerSurtidoTienda(referenciasCentro);

				pendientesRecibir = this.vPendientesRecibirService.find(pendientesRecibirEntrada);
				if (vSurtidoTienda != null) {
					pendientesRecibir.setTipoAprov(vSurtidoTienda.getTipoAprov());
				}
	        } catch (Exception e) {
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	throw e;
	        }
	        
		 return pendientesRecibir;
	}

	
	@RequestMapping(value="/loadNSR", method = RequestMethod.POST)
	public @ResponseBody Page<StockNoServido> loadNSR(
			@RequestBody StockNoServido snr,
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
	        
	        
	        List<StockNoServido> list = null;
		
	        try {
	        	//logger.info("Searching VArtCentroAlta");		
	        	list = this.vMovimientosStockService.findAllLastDays(snr, pagination);
	        } catch (Exception e) {
	        	//logger.error(StackTraceManager.getStackTrace(e));
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	throw e;
	        }
	        
	        Page<StockNoServido> result = null;
		
			if (list != null) {
				int records = this.vMovimientosStockService.findAllLastDays(snr, null).size() ;
				PaginationManager<StockNoServido> paginationManager = new PaginationManagerImpl<StockNoServido>();
				result = paginationManager.paginate(new Page<StockNoServido>(), list, max.intValue(), records, page.intValue());	
				
			} else {
				return new Page<StockNoServido>();
			}
		 return result;
		
	
	}
	
	@RequestMapping(value="/loadDatosPopupVentas", method = RequestMethod.POST)
	public @ResponseBody HistoricoVentaUltimoMes loadDatosPopupVentas(
			@RequestBody HistoricoVentaUltimoMes historicoVentaUltimoMes,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			HistoricoVentaUltimoMes historicoVentaUltimoMesRes;
			
			//Formatear fecha de pantalla que vendrá en formato DDMMYYYY
			String fechaPantalla = historicoVentaUltimoMes.getFechaVentaDDMMYYYY();
			int dia = Integer.valueOf(fechaPantalla.substring(0, 2));//new Integer(fechaPantalla.substring(0, 2)).intValue();
			int mes =Integer.valueOf(fechaPantalla.substring(2, 4)) - 1; //new Integer(fechaPantalla.substring(2, 4)).intValue() - 1;
			int anyo =Integer.valueOf(fechaPantalla.substring(4));//new Integer(fechaPantalla.substring(4)).intValue();
			GregorianCalendar diaVenta = new GregorianCalendar();
			diaVenta.set(anyo, mes, dia);
			
			historicoVentaUltimoMes.setFechaVenta(diaVenta.getTime());
			//Comprobar si VentaPromocional
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(historicoVentaUltimoMes.getCodArticulo());
			relacionArticulo.setCodCentro(historicoVentaUltimoMes.getCodLoc());
			List<Long> referencias = this.relacionArticuloService.findAll(relacionArticulo);
			if (referencias.isEmpty()){
				relacionArticulo.setCodArt(null);
				relacionArticulo.setCodArtRela(historicoVentaUltimoMes.getCodArticulo());
				referencias = this.relacionArticuloService.findAll(relacionArticulo);
			}
			if (!referencias.isEmpty()){
				historicoVentaUltimoMes.setReferencias(referencias);
			}
			
			historicoVentaUltimoMesRes = this.historicoVentaUltimoMesService.findOne(historicoVentaUltimoMes);
			
			if (historicoVentaUltimoMesRes != null){
				historicoVentaUltimoMesRes.recalcularTotalVentas();
			}

			return historicoVentaUltimoMesRes;

		} catch (Exception e) {
//		    logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	@RequestMapping(value="/loadPlanogramaWS", method = RequestMethod.POST)
	public  @ResponseBody ReferenciasCentroIC loadPlanogramaWS(			@RequestBody ReferenciasCentro vReferenciasCentro,
			HttpSession session, HttpServletResponse response) throws Exception {
		ReferenciasCentroIC  referenciasCentroIC = new ReferenciasCentroIC();
		try{
		
		ConsultaPlanogramaPorReferenciaResponseType result = this.planogramaCentroService.findPlanogramasCentroWS(vReferenciasCentro,session);
		if (!result.getCodigoRespuesta().equals("0")){
			//error
			Locale locale = LocaleContextHolder.getLocale();
			referenciasCentroIC.setDescripcionPlanograma(this.messageSource.getMessage("p15_referenciasCentroIC.webServiceBadRequest", null, locale));
		} else {
			if (result.getPlanogramaReferencia(0).getPlanogramada().equals("S")){
				referenciasCentroIC.setDescripcionPlanograma(result.getPlanogramaReferencia(0).getNombrePlanograma());
				
			}else{
				referenciasCentroIC.setDescripcionPlanograma(result.getPlanogramaReferencia(0).getMotivo());
			}
			
			//Obtenemos el surtido tienda
			VSurtidoTienda surtidoTienda = this.obtenerSurtidoTienda(vReferenciasCentro);
			
			//Obtenemos el texto que corresponde a la implantación
			if (result.getPlanogramaReferencia(0).getImplantancion() != null) {
				referenciasCentroIC.setImplantacion(result.getPlanogramaReferencia(0).getImplantancion());
			}
			//Obtenemos la fecha de Activacion
			if (result.getPlanogramaReferencia(0).getFechaActivacion() != null) {
				referenciasCentroIC.setFechaActivacion(result.getPlanogramaReferencia(0).getFechaActivacion().getTime());
				//Obtenemos la fecha generacion de surtido tienda. Fecha en la que la referencia pasa de PEDIR N a PEDIR S.
				Date FechaGenSurtidoTienda = vSurtidoTiendaService.obtenerFechaGeneracionSurtidoTienda(surtidoTienda);
				if (FechaGenSurtidoTienda != null) {
					referenciasCentroIC.setFechaGen(FechaGenSurtidoTienda);
				}
			}
					
			//Miramos si el WS devuelve el campo implantación. Si devuleve implantación habra que comprobar si este se pinta en rojo o verde 
			//en la pestaña Imagen comercial. 
			if ((referenciasCentroIC.getImplantacion() != null)  && !(referenciasCentroIC.getImplantacion().trim().equals(""))) {
					
				//comprobamos si el WS devuelve el campo Fecha Generación. 
				if (referenciasCentroIC.getFechaGen() != null) { 
						
					referenciasCentroIC.setColorImagenComercial("VERDE");		

				}else {
						//comprobamos si el STOCK es 0 desde hace mas de un mes. En ese caso lo pintamos a rojo
					
						if (vSurtidoTiendaService.comprobarStockMayorACero(surtidoTienda) > 0 ){
							referenciasCentroIC.setColorImagenComercial("ROJO");
						}	
				}

			}
		}
		
		
		} catch (Exception e) {
			Locale locale = LocaleContextHolder.getLocale();
			ReferenciasCentroIC  refCentroIC = new ReferenciasCentroIC();
			refCentroIC.setDescripcionPlanograma(this.messageSource.getMessage("p15_referenciasCentroIC.webServiceNotWorking", null, locale));
			return refCentroIC;
		}
		return referenciasCentroIC;
	}
	@RequestMapping(value="/loadMotivosNoActiva", method = RequestMethod.POST)
	public @ResponseBody Page<Motivo> loadMotivosNoActiva(
			@RequestBody Motivo motivo,
			//@RequestBody ReferenciasCentro vReferenciasCentro,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "5") Long max,
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
			
		    List<Motivo> listaRecarga = new ArrayList<Motivo>();
		
			String mapaHoy = motivo.getMapaHoy(); 
			String pedir = motivo.getPedir();
	        List<Motivo> listaMotivos = new ArrayList<Motivo>();
	        Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
			Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
	       
	        try {
	        	ReferenciasCentro referenciasCentro = new ReferenciasCentro();
				referenciasCentro.setCodArtCaprabo(motivo.getCodArt());
				referenciasCentro.setCodCentro(motivo.getCodCentro());
				
				//Se consulta V_SURTIDO_TIENDA filtrando por COD_CENTRO y COD_ART_CAPRABO
				referenciasCentro.setSurtidoTienda(obtenerSurtidoTienda(referenciasCentro));
				
				//Se consulta T_MIS_MCG_CAPRABO filtrando por COD_CENTRO y COD_ART_CAPRABO
				referenciasCentro.settMisMcgCaprabo(obtenerMcgCaprabo(referenciasCentro));
				
				//Obtención de motivo de T_MIS_MCG_CAPRABO
				if (referenciasCentro.gettMisMcgCaprabo()!=null && referenciasCentro.gettMisMcgCaprabo().getMotivo()!=null){
					referenciasCentro.setMotivoCaprabo(referenciasCentro.gettMisMcgCaprabo().getMotivo());
					Motivo motivo1 = new Motivo();
					TextoMotivo textoMotivo1 = new TextoMotivo(referenciasCentro.gettMisMcgCaprabo().getMotivo(), "");
					motivo1.setMapaHoy(mapaHoy);
	        		motivo1.setPedir(pedir);
	        		motivo1.setMotivoWebservice("N");
	        		motivo1.setTextoMotivo(textoMotivo1);
	        		motivo1.setTipoMensaje("SF"); //sin formato
	        		listaMotivos.add(motivo1);
				}
			
				//Carga de la sesion en el objeto de motivos
				VSurtidoTienda surtidoTienda = referenciasCentro.getSurtidoTienda();
				if (null != surtidoTienda && surtidoTienda.getPedir().toUpperCase().equals("N") && surtidoTienda.getCatalogo().equals("B")) {
	    			//Si es descentralizado hay que sacar el mensaje BAJA CATALOGO
	    			if (this.tipoAprovisionamientoDescentralizado.equals(surtidoTienda.getTipoAprov())){
	    				Locale locale = LocaleContextHolder.getLocale();
	    				String motivoBajaCatalogo =  this.messageSource.getMessage("p18_caprabo_referenciasCentroPopupPedir.bajaCatalogo", null, locale);
						Motivo motivo1 = new Motivo();
						TextoMotivo textoMotivo1 = new TextoMotivo(motivoBajaCatalogo, "");
						motivo1.setMapaHoy(mapaHoy);
		        		motivo1.setPedir(pedir);
		        		motivo1.setMotivoWebservice("N");
		        		motivo1.setTextoMotivo(textoMotivo1);
		        		motivo1.setTipoMensaje("SF"); //sin formato
		        		listaMotivos.add(motivo1);
			        				
	    			}else{
	    				//Parámetros de búsqueda de motivos
	    				String tipoMovimientoBusqueda = null;
	    				
	    				//Búsqueda de tipo de movimiento asociado
	    				if (referenciasCentro.gettMisMcgCaprabo()!= null && referenciasCentro.gettMisMcgCaprabo().getTipoMov()!=null){
	    					tipoMovimientoBusqueda = referenciasCentro.gettMisMcgCaprabo().getTipoMov();
	    				}
	    				//Búsqueda de motivos 
	    				CapraboMotivoNoPedible capraboMotivoNoPedibleBusqueda = new CapraboMotivoNoPedible();
	    				capraboMotivoNoPedibleBusqueda.setCodLocBusqueda(referenciasCentro.getCodCentro());
	    				capraboMotivoNoPedibleBusqueda.setCodArticuloBusqueda(referenciasCentro.getCodArtCaprabo());
	    				capraboMotivoNoPedibleBusqueda.setTipoMovimientoBusqueda(tipoMovimientoBusqueda);
	    				
	    				CapraboMotivoNoPedible capraboMotivoNoPedible = this.capraboMotivosNoPedibleService.findCentroRefTipo(capraboMotivoNoPedibleBusqueda);
	    				
	    				if (capraboMotivoNoPedible != null 
	    						&& capraboMotivoNoPedible.getDatos() != null
	    						&& capraboMotivoNoPedible.getDatos().get(0) != null){
	    					
	    					CapraboMotivoNoPedibleCentroArt capraboMotivoNoPedibleCentroArt = capraboMotivoNoPedible.getDatos().get(0);
	    					if(capraboMotivoNoPedibleCentroArt.getArticulos()!=null && capraboMotivoNoPedibleCentroArt.getArticulos().get(0)!=null){
	    						CapraboMotivoNoPedibleArt capraboMotivoNoPedibleArt = capraboMotivoNoPedibleCentroArt.getArticulos().get(0);
	        					if(capraboMotivoNoPedibleArt.getMotivos()!=null){
	        						for (CapraboMotivoNoPedibleMotivo capraboMotivoNoPedibleMotivo : capraboMotivoNoPedibleArt.getMotivos()){
	        							Motivo motivo1 = new Motivo();
	        							TextoMotivo textoMotivo1 = new TextoMotivo(capraboMotivoNoPedibleMotivo.getDescripcion(), "");
	        							motivo1.setMapaHoy(mapaHoy);
	        			        		motivo1.setPedir(pedir);
	        			        		motivo1.setMotivoWebservice("N");
	        			        		motivo1.setTextoMotivo(textoMotivo1);
	        			        		motivo1.setTipoMensaje("SF"); //sin formato
	        			        		listaMotivos.add(motivo1);
	        						}
	        					}	
	    					}
	    				}
	    			}
				}
							
				session.setAttribute("listMotGeneral" + motivo.getOrigenPantalla(), listaMotivos);
		        		        
	        } catch (Exception e) {
	        	//logger.error(StackTraceManager.getStackTrace(e));
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	throw e;
	        }
	        
	       
	        Page<Motivo> result = null;
		
			if (listaMotivos.size()>0) {
				int records = listaMotivos.size();
				if (elemInicio <= records){
					if (elemFinal > records){
						elemFinal = new Long(records);
					}
					listaRecarga = (listaMotivos).subList(Integer.parseInt(elemInicio.toString()), Integer.parseInt(elemFinal.toString()));
				}
				if (listaRecarga != null && listaRecarga.size()>0)
				{	
					PaginationManager<Motivo> paginationManager = new PaginationManagerImpl<Motivo>();
					result = paginationManager.paginate(new Page<Motivo>(), listaRecarga, max.intValue(), records, page.intValue());	
					
				} else {
					return new Page<Motivo>();
				}
			}
			else {
				return new Page<Motivo>();
			}
		 
			return result;
		
	}
	
	public MessageSource getMessageSource() {
		return this.messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	@RequestMapping(value="/loadDatosMaestrosFijoDet", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentro loadDatosMaestrosFijoDet(
			@RequestBody ReferenciasCentro vReferenciasCentro,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		
		try {
			ReferenciasCentro referenciasCentro = vReferenciasCentro;
			
			referenciasCentro.settMisMcgCaprabo(obtenerMcgCaprabo(referenciasCentro));
			referenciasCentro.setSurtidoTienda(obtenerSurtidoTienda(referenciasCentro));
			
			
			if (referenciasCentro.getOrigenPantalla().equals(Constantes.ORIGEN_DETALLADO)) {
				referenciasCentro.setNextDayPedido(this.vReferenciaActiva2Service.getNextFechaPedido(vReferenciasCentro.getCodCentro(), vReferenciasCentro.getCodArt()));
			} else {
				referenciasCentro.setNextDayPedido(this.vReferenciaActiva2Service.getNextDiaPedido(vReferenciasCentro.getCodCentro(), vReferenciasCentro.getCodArt()));
			}
			

			return referenciasCentro;
		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}	
	
	private Long obtenerCc(ReferenciasCentro referenciasCentro) throws Exception{
		//Preparar parámetros para búsqueda de cc
		CcRefCentro ccRefCentro = new CcRefCentro(referenciasCentro.getCodArt(), 
				referenciasCentro.getCodCentro(), 
				new Date());
		
		return ccRefCentroService.consultaCc(ccRefCentro);		
	}
}