package es.eroski.misumi.control;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

import es.eroski.misumi.dao.iface.VPlanogramaService;
import es.eroski.misumi.model.Articulo;
import es.eroski.misumi.model.Aviso;
import es.eroski.misumi.model.BorrUniAux;
import es.eroski.misumi.model.CausaInactividad;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.CentroAutoservicio;
import es.eroski.misumi.model.DireccionCentro;
import es.eroski.misumi.model.InformeHuecos;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.ParamCentrosVp;
import es.eroski.misumi.model.ParamStockFinalMinimo;
import es.eroski.misumi.model.Pedido;
import es.eroski.misumi.model.PedidoBasicInfo;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.model.UltimosPedidos;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VFacingX;
import es.eroski.misumi.model.VPlanograma;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.AlarmasPLUService;
import es.eroski.misumi.service.iface.BorrUniAuxService;
import es.eroski.misumi.service.iface.CausasInactividadService;
import es.eroski.misumi.service.iface.CentroLocalizacionService;
import es.eroski.misumi.service.iface.EstadoMostradorService;
import es.eroski.misumi.service.iface.InformeListadoService;
import es.eroski.misumi.service.iface.InventarioRotativoGisaeService;
import es.eroski.misumi.service.iface.ListadoGamaRapidService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.ParamCentrosVpService;
import es.eroski.misumi.service.iface.PedidosCentroService;
import es.eroski.misumi.service.iface.PlanogramaKosmosService;
import es.eroski.misumi.service.iface.PlanogramaVigenteService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.SmfCapacidadService;
import es.eroski.misumi.service.iface.StockFinalMinimoService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.TMisMensajesService;
import es.eroski.misumi.service.iface.UserService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VAgruComerParamSfmcapService;
import es.eroski.misumi.service.iface.VCentrosPlataformasService;
import es.eroski.misumi.service.iface.VCentrosUsuariosService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VFacingXService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.AreaFacingCeroService;
import es.eroski.misumi.service.iface.AvisosSiecService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.DocumentManager;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/welcome")
public class WelcomeController {

	//private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	private static Logger logger = Logger.getLogger(WelcomeController.class);
	@Autowired
	private VCentrosPlataformasService vCentrosPlataformasService;
	@Autowired
	private VCentrosUsuariosService vCentrosUsuariosService;
	@Autowired
	private PedidosCentroService pedidosCentroService;
	@Autowired
	private SmfCapacidadService smfCapacidadService;
	@Autowired
	private ParamCentrosVpService paramCentrosVpService;
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	
	@Autowired
	private StockFinalMinimoService stockFinalMinimoService;
	@Autowired
	private StockTiendaService stockTiendaService;
	@Autowired
	private BorrUniAuxService borrUniAuxService;
	@Autowired
	private DocumentManager documentManager;
	@Autowired
	private UserService userService;
	@Autowired
	private InventarioRotativoGisaeService inventarioRotativoGisaeService;
	@Autowired
	private CausasInactividadService causasInactividadService;
	@Autowired
	private ExcelManager excelManager;
	@Autowired
	private InformeListadoService informeListadoService;
	@Autowired
	private VAgruComerParamSfmcapService vAgruComerParamSfmcapService;
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	@Autowired
	private VFacingXService vFacingXService;
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	@Autowired
	private TMisMensajesService tMisMensajes;
	@Autowired
	private AvisosSiecService avisosSiecService;
	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;
	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
	@Autowired
	private CentroLocalizacionService centroLocalizacionService;	
	@Autowired
	private VPlanogramaService vPlanogramaService;
	@Autowired
	private ListadoGamaRapidService gamaRapid;
	@Autowired
	private AlarmasPLUService alarmasPLUService;
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;
	@Autowired
	private EstadoMostradorService estadoMostradorService;
	@Autowired
	private AreaFacingCeroService areaFacingCeroService;
	
	@Value( "${tecnicRole}" )
	private String tecnicRole;

	@Value( "${adminRole}" )
	private String adminRole;

	@Value( "${PARAMETRIZAR_OPCIONES}" )
	private String parametrizarOpciones;

	//@Value( "${centerRole}" )
	//private String centerRole;

	@Autowired
	private PlanogramaVigenteService planogramaVigenteService;

	@Autowired
	private PlanogramaKosmosService planogramaKosmosService;

	@Resource
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
		return "p11_welcome";
	}


	@RequestMapping(value = "/getUserSession", method = RequestMethod.POST)
	public @ResponseBody User  getUserSession(
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User user = (User)session.getAttribute("user");

		return user;
	}	

	@RequestMapping(value = "/countHabitualNoHabitual", method = RequestMethod.POST)
	public @ResponseBody Long  countHabitualNoHabitual(
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User userSession = (User)session.getAttribute("user");
		return this.informeListadoService.countAllInformeListadoPesca(userSession.getCentro().getCodCentro());
	}

	@RequestMapping(value = "/setUserCentro", method = RequestMethod.POST)
	public @ResponseBody User  setUserCentroSession(
			@RequestBody ArrayList <Integer> codCentro,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		ArrayList<Centro> c = new ArrayList<Centro>();
		Centro centraux = new Centro();
		Centro centr = new Centro();

		User userSession = (User)session.getAttribute("user");
		for (int i = 0; i < codCentro.size(); i++) {

			Long codCentr = codCentro.get(i).longValue();
			centraux.setCodCentro(codCentr);

			centr = this.vCentrosUsuariosService.findOne(centraux, userSession.getCode());

			if (centr.getCodCentro()!=null){
				logger.info("CAMBIO DE CENTRO --> "+centr.getCodCentro());

				//Comprobación de autoservicio
				ParamCentrosVp paramCentrosVp = new ParamCentrosVp();
				paramCentrosVp.setCodLoc(centr.getCodCentro());
				if (this.paramCentrosVpService.esAutoservicio(paramCentrosVp)){
					centr.setFlgCapacidad(Constantes.FLG_CAPACIDAD_AUTOSERVICIO);
				}else{
					centr.setFlgCapacidad(Constantes.FLG_CAPACIDAD_NO_AUTOSERVICIO);
				}

				//Control del facing del centro
				if (this.paramCentrosVpService.esFacingCentro(paramCentrosVp)){
					centr.setFlgFacing(Constantes.FLG_SI_FACING_CENTRO);
				}else{
					centr.setFlgFacing(Constantes.FLG_NO_FACING_CENTRO);
				}
				String descripBotonSfmCapacidad = this.smfCapacidadService.getMetodosBoton(centr.getCodCentro());
				centr.setDescripBotonSfmCapacidad(descripBotonSfmCapacidad);

				//Control de opciones de aplicación
				if (parametrizarOpciones != null && "S".equals(parametrizarOpciones.trim())){
					centr.setControlOpciones(parametrizarOpciones);
					ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
					paramCentrosOpc.setCodLoc(centr.getCodCentro());
					ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
					if (paramCentroOpciones != null){ //Existen opciones habilitadas para el centro
						centr.setOpcHabil(paramCentroOpciones.getOpcHabil());
					}else{
						//Si no existe entrada en la tabla para el centro se considera que no tiene acceso
						centr.setControlOpciones("S");
						centr.setOpcHabil("");
					}
				}else{
					centr.setControlOpciones("N");
					centr.setOpcHabil("");
				}
				
				//Control lotesNavidad
				if (centr.getOpcHabil().toUpperCase().indexOf(Constantes.CESTAS_12) != -1){
					centr.setLotesNavidadActivos(this.vCentrosPlataformasService.isLotesCentroActivo(centr.getCodCentro()));
				} else {
					centr.setLotesNavidadActivos(false);
				}

				//Control informeListado
				if (centr.getOpcHabil().toUpperCase().indexOf(Constantes.PESCA_13) != -1){
					centr.setInformeListado(this.informeListadoService.obtenerInformeListado(centr.getCodCentro()));
				}
				
//				//Mostrador pesca
				if (centr.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_MOSTRADOR) != -1){
					centr.setEstadoEstructurasMostrador(estadoMostradorService.getEstados(centr.getCodCentro()));
				}

				//Areas con Facing cero.
				if (centr.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_FACING_0) != -1){
					centr.setAreaFacingCero(this.areaFacingCeroService.getAreasFacingCero(centr.getCodCentro()));
				}
			
			}

			c.add(centr);
		}

		if (c.size()==1){
			userSession.setCentro(c.get(0));
			userSession.setListaCentros(null);
			userSession.setMulticentro(false);

			//Obtenemos la dirección del centro. 
			String direccionGoogle = this.centroLocalizacionService.obtenerDireccionGoogle(userSession.getCentro().getCodCentro());
			if(direccionGoogle != null){
				DireccionCentro dirCentro = new DireccionCentro();
				dirCentro.setDireccion(direccionGoogle);
				userSession.getCentro().setDireccionCentro(dirCentro);
			}else{
				//Obtenemos la latitud y longitud del centro.
				userSession.getCentro().setDireccionCentro(centroLocalizacionService.obtenerCentroLocalizacion(userSession));
			}
		}else{
			userSession.setCentro(c.get(0));
			userSession.setListaCentros(null);
			userSession.setListaCentros(c);
			userSession.setMulticentro(true);
		}

		if (centr.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_GAMA_RAPID) != -1){
			// Probamos a cargar tabla temporal de listado Rapid en trhead
			final Long selectCodCentro = userSession.getCentro().getCodCentro();

			// Comprobar que no se este ejecutando un hilo para ese centro.
			// Y que no se el mismo
			Thread[] lista = new Thread[Thread.activeCount()];
			Thread.enumerate(lista);
			Boolean valid = Boolean.FALSE;
			for(Thread hilo: lista){
				if ((Long.toString(selectCodCentro)).equals(hilo.getName())){
					if(!(Thread.currentThread().getName()).equals(hilo.getName())){
						valid = Boolean.TRUE;
						break;
					}
				}
			}
			final Boolean ejecutandose = valid;

			Thread thread = new Thread(Long.toString(selectCodCentro)){
				public void run(){
					try{    		
						if (!ejecutandose){
							gamaRapid.cargarDatosCentro(selectCodCentro);
						}
					} catch (Exception e){
						logger.error("Error al cargar el listado del informe gama rapid para: "+selectCodCentro);
					}
				}
			};
			thread.start();
		}

		session.setAttribute("user", userSession);

		return userSession;
	}
	
	@RequestMapping(value = "/resetUserCentro", method = RequestMethod.POST)
	public @ResponseBody void  resetUserCentroSession(
			HttpServletResponse response,
			HttpSession session) throws Exception{

		Centro centro = null;
		User userSession = (User)session.getAttribute("user");
		userSession.setCentro(centro);

		session.setAttribute("user", userSession);

	}	


	@RequestMapping(value="/loadCentros", method = RequestMethod.GET)
	public @ResponseBody List<Centro> loadCentros(
			HttpSession session, HttpServletResponse response) throws Exception {

		List<Centro> centros = null;
		try {	
			User userSession = (User)session.getAttribute("user");
			Centro centro = userSession.getCentro(); 
			if (userSession.getPerfil().toString().equals(this.tecnicRole) || userSession.getPerfil().toString().equals(this.adminRole)){
				centro = null;
			}
			User user = (User) session.getAttribute("user");
			centros = this.vCentrosUsuariosService.findAll(centro, user.getCode());
		} catch (Exception e) {		  
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return centros;
	}
	@RequestMapping(value="/loadCentrosPattern", method = RequestMethod.GET)
	public @ResponseBody List<Centro> loadCentrosBis(@RequestParam String term,
			HttpSession session, HttpServletResponse response) throws Exception {

		List<Centro> centros = null;
		try {	
			//User userSession = (User)session.getAttribute("user");
			//Centro centro = userSession.getCentro(); 
			//if (userSession.getPerfil().toString().equals(this.tecnicRole)){
			//	centro = null;
			//}
			User user = (User) session.getAttribute("user");
			centros = this.vCentrosUsuariosService.findByCodDesc(term,user.getCode());

		} catch (Exception e) {		  
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return centros;
	}

	@RequestMapping(value="/loadDatosPlanogramaVigente", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentroIC loadDatosImagenComercial(
			@RequestBody ReferenciasCentroIC vReferenciasCentroIC,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			ReferenciasCentroIC referenciasCentroIC = vReferenciasCentroIC;

			PlanogramaVigente planogramaVigente = obtenerPlanogramaVigente(referenciasCentroIC);

			//Si no existe el planogramaVigente o la capacidad maxima lineal es nula y el stockminimocomercial es nulo, se consulta el planograma de kosmos.
			if(planogramaVigente == null || (planogramaVigente.getCapacidadMaxLineal() == null && planogramaVigente.getStockMinComerLineal() == null)){
				planogramaVigente = obtenerPlanogramaKosmos(referenciasCentroIC);				
			}
			referenciasCentroIC.setPlanogramaVigente(planogramaVigente);

			//Control de estructuras para comprobar FacingX
			ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			referenciasCentro.setCodArt(referenciasCentroIC.getCodArt());
			referenciasCentro.setCodCentro(referenciasCentroIC.getCodCentro());
			//Comprobación de si una referencia es FFPP 
			User user = (User) session.getAttribute("user");
			boolean tratamientoVegalsaAux = this.utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
			referenciasCentro = this.vRelacionArticuloService.obtenerFfppActivaOUnitaria(referenciasCentro, tratamientoVegalsaAux);

			VDatosDiarioArt vDatosDiarioArtRes = null;
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();

			vDatosDiarioArt.setCodArt(referenciasCentro.getCodArt());
			vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

			//Rellenamos si se trata de un FFPP
			referenciasCentroIC.setEsFFPP(referenciasCentro.isTieneUnitaria());

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
					//
					//Tratamiento Mensaje de IMPORTANTE 
					//
					if (estructuraArticulo.getFlgFacing() != null){
						referenciasCentroIC.setFlgFacing(estructuraArticulo.getFlgFacing());
					}
					if (estructuraArticulo.getFlgCapacidad() != null){ 
						referenciasCentroIC.setFlgCapacidad(estructuraArticulo.getFlgCapacidad());
					}
					if (estructuraArticulo.getFlgFacingCapacidad() != null){
						referenciasCentroIC.setFlgFacingCapacidad(estructuraArticulo.getFlgFacingCapacidad());
					}

					//Si no es de facing habrá que buscar por SFM o Capacidad
					if (!Constantes.FLG_SI_FACING_CENTRO.equals(referenciasCentroIC.getFlgFacing())){
						//Control para mostrado de  SFM
						ParamStockFinalMinimo paramStockFinalMinimo = new ParamStockFinalMinimo();
						paramStockFinalMinimo.setCodLoc(referenciasCentroIC.getCodCentro());
						paramStockFinalMinimo.setCodN1(vDatosDiarioArt.getGrupo1());
						paramStockFinalMinimo.setCodN2(vDatosDiarioArt.getGrupo2());
						paramStockFinalMinimo.setCodN3(vDatosDiarioArt.getGrupo3());
						paramStockFinalMinimo.setCodN4(vDatosDiarioArt.getGrupo4());
						paramStockFinalMinimo.setCodN5(vDatosDiarioArt.getGrupo5());

						Long resultadoSFM = this.stockFinalMinimoService.findFinalStockParam(paramStockFinalMinimo);
						referenciasCentroIC.setFlgStockFinal(resultadoSFM);
						if (resultadoSFM == 1) { // Es una referencia de SFM
							referenciasCentroIC.setFlgCapacidad("N");
						} else {// Es una referencia de Capacidad
							referenciasCentroIC.setFlgCapacidad("S");
						}
					}

					if (estructuraArticulo.getFlgCapacidad() != null && estructuraArticulo.getFlgCapacidad().equals("S"))
					{
						//En este caso tendremos que hacer las comprobaciones de si se ha modificado la capacidad.
						StockFinalMinimo stockFinalMinimoRes;
						StockFinalMinimo stockFinalMinimo = new StockFinalMinimo();
						stockFinalMinimo.setCodArticulo(referenciasCentroIC.getCodArt());
						stockFinalMinimo.setCodLoc(referenciasCentroIC.getCodCentro());
						stockFinalMinimoRes = this.stockFinalMinimoService.findOne(stockFinalMinimo);

						StockFinalMinimo stockFinalMinimoSIARes;
						stockFinalMinimoSIARes = this.stockFinalMinimoService.findOneSIA(stockFinalMinimo);

						if (stockFinalMinimoRes != null && stockFinalMinimoSIARes != null && 
								stockFinalMinimoRes.getCapacidad() != null && stockFinalMinimoSIARes.getCapacidad() != null &&
								(stockFinalMinimoRes.getCapacidad().compareTo(stockFinalMinimoSIARes.getCapacidad()) != 0))
						{
							//En este caso las capacidades son distintas y hay que rehacer los cálculos del lineal.

							//Obtenemos el porcentaje de la capacidad
							Float porcentajeCapacidad = null;
							CentroAutoservicio centroAutoservicio = new CentroAutoservicio();
							centroAutoservicio.setCodCentro(referenciasCentroIC.getCodCentro());
							List<CentroAutoservicio> listaCentroAutoservicio = null;
							listaCentroAutoservicio = this.paramCentrosVpService.findCentroAutoServicioAll(centroAutoservicio);

							if (listaCentroAutoservicio != null && listaCentroAutoservicio.size()>0)
							{
								CentroAutoservicio centroAutoServicio = new CentroAutoservicio();
								centroAutoServicio = listaCentroAutoservicio.get(0);

								if (centroAutoServicio != null && centroAutoServicio.getPorcentajeCapacidad() != null)
								{
									porcentajeCapacidad = centroAutoServicio.getPorcentajeCapacidad();
								}

							}

							Long nuevaCapacidad = new Long(1);
							Float nuevoFacing = new Float(1);
							if (stockFinalMinimoSIARes.getCapacidad() >1)
							{
								nuevaCapacidad = new Long((long)stockFinalMinimoSIARes.getCapacidad().floatValue());
							}

							planogramaVigente.setCapacidadMaxLinealRecal(nuevaCapacidad);

							if (porcentajeCapacidad != null && nuevaCapacidad*porcentajeCapacidad >1)
							{
								nuevoFacing = nuevaCapacidad*porcentajeCapacidad;
							}

							planogramaVigente.setStockMinComerLinealRecal(Float.valueOf((new DecimalFormat("###").format(nuevoFacing))));
							planogramaVigente.setRecalculado("SI");
						}
					}


					referenciasCentroIC.setStockFinalMinimo(this.stockFinalMinimoService.obtenerStockFinalMinimo(referenciasCentroIC));
					//Fin Tratamiento Mensaje de IMPORTANTE 

					boolean esFacingX = ( null != estructuraArticulo && 
							("S".equals(estructuraArticulo.getFlgFacingCapacidad()) || "S".equals(estructuraArticulo.getFlgCapacidad()) || 
									("N".equals(estructuraArticulo.getFlgStockFinal()) && "N".equals(estructuraArticulo.getFlgFacing()))));

					referenciasCentroIC.setEsFacingX(esFacingX);

					//if (esFacingX && planogramaVigente!=null){
					if (planogramaVigente!=null){

						Long multiplicador = new Long(1);

						//Si es un FFPP no tiene sentido el multiplicador
						if (!referenciasCentro.isTieneUnitaria()){
							VFacingX vFacingXRes = new VFacingX();
							VFacingX vFacingX = new VFacingX();
							//vFacingX.setCodArticulo(referenciasCentroIC.getCodArt());
							vFacingX.setCodArticulo(vDatosDiarioArtRes.getCodFpMadre()); //Peticion 54867
							vFacingX.setCodCentro(referenciasCentroIC.getCodCentro());
							vFacingXRes = this.vFacingXService.findOne(vFacingX);

							if (vFacingXRes != null && vFacingXRes.getFacingX()!=null){
								multiplicador = vFacingXRes.getFacingX();
							}
						}
						referenciasCentroIC.setImc((long) (planogramaVigente.getStockMinComerLineal()*multiplicador));
						referenciasCentroIC.setMultiplicadorFac(new Integer(multiplicador.toString()));
					}else{
						referenciasCentroIC.setImc(new Long(0));
						referenciasCentroIC.setMultiplicadorFac(1);
					}

				}else{
					referenciasCentroIC.setEsFacingX(true);
					if (planogramaVigente!=null){

						Long multiplicador = new Long(1);

						//Si es un FFPP no tiene sentido el multiplicador
						if (!referenciasCentro.isTieneUnitaria()){
							VFacingX vFacingXRes = new VFacingX();
							VFacingX vFacingX = new VFacingX();
							//vFacingX.setCodArticulo(referenciasCentroIC.getCodArt());
							vFacingX.setCodArticulo(vDatosDiarioArtRes.getCodFpMadre()); //Peticion 54867
							vFacingX.setCodCentro(referenciasCentroIC.getCodCentro());
							vFacingXRes = this.vFacingXService.findOne(vFacingX);

							if (vFacingXRes != null && vFacingXRes.getFacingX()!=null){
								multiplicador = vFacingXRes.getFacingX();
							}
						}
						referenciasCentroIC.setImc((long) (planogramaVigente.getStockMinComerLineal()*multiplicador));
						referenciasCentroIC.setMultiplicadorFac(new Integer(multiplicador.toString()));
					}else{
						referenciasCentroIC.setImc(new Long(0));
						referenciasCentroIC.setMultiplicadorFac(1);
					}

				}

				//Buscamos la altura y el ancho cuando el tipo de planograma es 'P'.
				VPlanograma vPlanogramaTipoP = new VPlanograma();
				vPlanogramaTipoP.setCodArt(vReferenciasCentroIC.getCodArt());
				vPlanogramaTipoP.setCodCentro(vReferenciasCentroIC.getCodCentro());

				vPlanogramaTipoP = vPlanogramaService.findTipoP(vPlanogramaTipoP);
				referenciasCentroIC.setvPlanogramaTipoP(vPlanogramaTipoP);
			}

			return referenciasCentroIC;
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
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

	private PlanogramaVigente obtenerPlanogramaKosmos(
			ReferenciasCentroIC referenciasCentroIC) throws Exception {

		PlanogramaVigente planogramaVigenteRes;
		PlanogramaVigente planogramaVigente = new PlanogramaVigente();
		planogramaVigente.setCodArt(referenciasCentroIC.getCodArt());
		planogramaVigente.setCodCentro(referenciasCentroIC.getCodCentro());

		planogramaVigenteRes = this.planogramaKosmosService.findOne(planogramaVigente);

		return planogramaVigenteRes;
	}

	@RequestMapping(value="/loadPedidoInfoBasic", method = RequestMethod.POST)
	public @ResponseBody UltimosPedidos loadPedidosInfoBasic(
			@RequestBody Articulo articulo,
			HttpSession session, HttpServletResponse response) throws Exception {
		List<PedidoBasicInfo> listBasicInfo=new ArrayList<PedidoBasicInfo>();
		try {
			Pedido pedidoAux = this.pedidosCentroService.findAllBasicInfo(new Pedido(articulo,null));
			listBasicInfo = pedidoAux.getBasicInfo();

			UltimosPedidos ultimosPedidos = new UltimosPedidos();
			Page<PedidoBasicInfo> result = null;

			if (listBasicInfo != null && listBasicInfo.size()>0) {
				int records = listBasicInfo.size() ;
				PaginationManager<PedidoBasicInfo> paginationManager = new PaginationManagerImpl<PedidoBasicInfo>();
				result = paginationManager.paginate(new Page<PedidoBasicInfo>(), listBasicInfo, listBasicInfo.size(), records, 1);
			} else {
				result = new Page<PedidoBasicInfo>();
			}

			ultimosPedidos.setUltimosPedidos(result);

			//Si no hay pedidos hay que sacar el mensaje de no existen pedidos
			if (result == null || result.getRecords() == null || result.getRecords().equals("0")){
				BorrUniAux borrUniAux = new BorrUniAux();
				borrUniAux.setNomTabla(Constantes.VALOR_CAMPO_BUSQUEDA_BORR_UNI_AUX);
				List<BorrUniAux> listaBorrUniAux = this.borrUniAuxService.findAll(borrUniAux, null);
				Locale locale = LocaleContextHolder.getLocale();
				String mensajeUltimosPedidos = "";
				if (listaBorrUniAux != null){
					Long numeroDias = new Long(0);
					if (!listaBorrUniAux.isEmpty()){
						numeroDias = listaBorrUniAux.get(0).getNumDias();
					}
					//Cálculo de número de meses
					Long numeroMeses = numeroDias / 30;
					if (numeroMeses > 1){
						mensajeUltimosPedidos = this.messageSource.getMessage("p30_popupInfoReferencia.ultimosPedidosNoExistenVariosMeses", new Object[] {numeroMeses}, locale);
					}else{
						mensajeUltimosPedidos = this.messageSource.getMessage("p30_popupInfoReferencia.ultimosPedidosNoExisten1Mes", null, locale);
					}
				}
				ultimosPedidos.setMensajeSinPedidos(mensajeUltimosPedidos);
			}

			return ultimosPedidos;


		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value="/loadStockCentroArticulo", method = RequestMethod.POST)
	public @ResponseBody String  loadStockCentroArticulo(
			@RequestBody Articulo articulo,
			HttpSession session, HttpServletResponse response) throws Exception {
		Double resultado=null;
		List<Long> referenciaLoteTextil = null;
		List<BigInteger> listaReferenciasHijasTextil = null;
		try {
			ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(articulo.getCentro().getCodCentro()));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);


			//Petición 54293
			//Se consulta la vista V_REFERENCIAS_LOTE_TEXTIL para comprobar si la referencia es una referencia lote. (Consultada previamemte)
			referenciaLoteTextil = this.relacionArticuloService.esReferenciaLote(articulo.getCodArt());

			if ((referenciaLoteTextil != null) && (referenciaLoteTextil.size() > 0)) {

				listaReferenciasHijasTextil = this.relacionArticuloService.obtenerHijasLoteBI(articulo.getCodArt());	
				//Seguido volcamos la "listaReferenciasHijasTextil"
				int indiceArray = 0;	
				BigInteger[] listaRef = new BigInteger[listaReferenciasHijasTextil.size()];
				for (BigInteger referencia : listaReferenciasHijasTextil) {
					listaRef[indiceArray] = referencia;
					indiceArray++;
				}	
				requestType.setListaCodigosReferencia(listaRef);

			} else {
				BigInteger[] listaRef = {BigInteger.valueOf(articulo.getCodArt())}; 
				requestType.setListaCodigosReferencia(listaRef);
			}

			User user = (User) session.getAttribute("user");
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("######################## CONTROLADOR: WelcomeController ###################################################");
				logger.error("###########################################################################################################");
			} 

			ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType, session);
			if (responseType.getCodigoRespuesta().equals("OK")){
				for (ReferenciaType referencia : responseType.getListaReferencias()){
					//if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(articulo.getCodArt()))){
					if (referencia.getCodigoError().equals(new BigInteger("0"))){
						if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
							resultado = referencia.getBandejas().doubleValue();
						} else {
							if (resultado==null) {
								resultado = referencia.getStock().doubleValue();
							} else {
								resultado = resultado + referencia.getStock().doubleValue();	
							}
						}
					} else {
						return "Error";
					}
					//}
				}
			} else {
				return "Error";
			}
		} catch (Exception e) {
			return "Error";
		}
		return resultado.toString();			
	}
	@RequestMapping(value="/loadStockMinParam", method = RequestMethod.POST)
	public @ResponseBody Long  loadStockMinParam(
			@RequestBody ParamStockFinalMinimo paramStockFinalMinimo,
			HttpSession session, HttpServletResponse response) throws Exception {
		return this.stockFinalMinimoService.findFinalStockParam(paramStockFinalMinimo);

	}
	@RequestMapping(value="/getStockFinalMinimo", method = RequestMethod.POST)
	public @ResponseBody StockFinalMinimo getStockFinalMinimo(
			@RequestBody Articulo articulo,
			HttpSession session, HttpServletResponse response) throws Exception {

		ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();
		referenciasCentroIC.setCodArt(articulo.getCodArt());
		referenciasCentroIC.setCodCentro(articulo.getCentro().getCodCentro());

		return this.stockFinalMinimoService.obtenerStockFinalMinimo(referenciasCentroIC);
	}

	@RequestMapping(value = "/downloadManual", method = RequestMethod.GET)
	public @ResponseBody
	void downloadManual(
			HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {
			this.documentManager.downloadManual(this.messageSource, request, response);

		}catch(Exception e) { 

			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}

	@RequestMapping(value = "/getAvisosCentro", method = RequestMethod.GET)
	public @ResponseBody User getAvisosCentro(
			HttpServletResponse response,
			HttpSession session) throws Exception{

		final User user = (User)session.getAttribute("user");
		final Centro centro = user.getCentro();
		if (null != centro){
			final Long codCentro = centro.getCodCentro();
			
			// Actualizamos mostrador pesca
			if (centro.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_MOSTRADOR) != -1){
				user.getCentro().setEstadoEstructurasMostrador(estadoMostradorService.getEstados(codCentro));
			}
			
			try {
				logger.info("get avisos centro - OPCIONES --> "+centro.getOpcHabil());
				// Comprobacion de si hay datos de Alarmas PLU
				if (centro.getOpcHabil().contains("107_ALARMAS_PLU")){
					// 1-Comprobar si hay datos en la tabla intermedia para ese centro con datos de HOY
					boolean hayDatosDeHoy = alarmasPLUService.hayDatosDeHoyEnTablaIntermedia(codCentro);
					logger.info("¿HAY DATOS EN LA TABLA INTERMEDIA? --> "+hayDatosDeHoy);
					// 2-Si no hay datos de hoy y no esta ya cargando, se llama al WS para cargar los datos
					Boolean estaCargando = alarmasPLUService.getEstaCargando(codCentro);
					if (!hayDatosDeHoy && !estaCargando){
						alarmasPLUService.setEstaCargando(codCentro, true);
						new Thread(new Runnable() {
						    public void run() {
						    	try {
									alarmasPLUService.cargarDatosEnTablaIntermedia(codCentro);
								} catch (Exception e) {
									e.printStackTrace();
								}finally{
									alarmasPLUService.setEstaCargando(codCentro, false);
								}
						    }
						}).start();
					}

				}

				
				if (centro.getOpcHabil().toUpperCase().indexOf(Constantes.PESCA_13) != -1){
					centro.setInformeListado(this.informeListadoService.obtenerInformeListado(codCentro));
				}

				//Areas con Facing cero.
				if (centro.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_FACING_0) != -1){
					centro.setAreaFacingCero(this.areaFacingCeroService.getAreasFacingCero(centro.getCodCentro()));
				}

				user.getCentro().setListaAvisosCentro(this.userService.obtenerAvisosCentro(codCentro, session));

			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return user;
	}	

	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody void exportGrid(
			HttpServletResponse response, HttpSession session) throws Exception{
		try {
			User user = (User)session.getAttribute("user");
			List<InformeHuecos> listHuecos = this.inventarioRotativoGisaeService.getInformeHuecos(user.getCentro().getCodCentro(),session);
			List<CausaInactividad> listCausas = this.causasInactividadService.findAll();
			this.excelManager.exportInformeHuecos(listHuecos, listCausas, messageSource, user.getCentro(), response);
		} catch (Exception e) {

			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value = "/getAviso", method = RequestMethod.POST)
	public @ResponseBody List<Aviso>  getAviso(
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User user = (User)session.getAttribute("user");
		Aviso aviso = new Aviso();
		List<Aviso> listAviso = new ArrayList<Aviso>();
		try {
			//Indicamos que se trata de un mensaje para pda
			aviso.setPda(false);

			//Añadimos aviso del centro 0 equivalente a TODOS LOS CENTROS
			aviso.setCodCentro(Long.valueOf(0));
			if (this.tMisMensajes.existeAviso(aviso)){		
				listAviso.add(this.tMisMensajes.obtenerAviso(aviso));
			}
			if (null != user.getCentro()){//Si el centro no está seleccionado
				aviso.setCodCentro(user.getCentro().getCodCentro());
				// Añadimos el aviso del propio del centro
				if (this.tMisMensajes.existeAviso(aviso)){		
					listAviso.add(this.tMisMensajes.obtenerAviso(aviso));
				}
				List<Aviso> listAvisosSiec = this.avisosSiecService.obtenerAvisosSiec(user.getCentro().getCodCentro());
				for (int i = 0; i < listAvisosSiec.size(); i++) {
					Aviso aux=listAvisosSiec.get(i);
					listAviso.add(aux);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return listAviso;
	}	

	@RequestMapping(value = "/getImage", method = RequestMethod.GET)
	public void doGet(@RequestParam(value = "codArticulo", required = true) Long codArticulo,
			@RequestParam(value = "flgControlCaprabo", required = false, defaultValue = "N") String flgControlCaprabo,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		User user = (User) session.getAttribute("user");

		if("S".equals(flgControlCaprabo) && user.getCentro().esCentroCaprabo()){
			codArticulo = utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), codArticulo);
		}

		try {
			Utilidades.cargarImagen(codArticulo, response, "png");
		} catch (Exception e) {
			logger.error("###############################");
			logger.error(StackTraceManager.getStackTrace(e));	
			logger.error("centro: " + user.getCentro().getCodCentro());
			logger.error("codArticulo: " + codArticulo);
			logger.error("###############################");

			Utilidades.cargarImagen(null, response, "png");	
		}
	}

	@RequestMapping(value = "/getInformesCentro", method = RequestMethod.POST)
	public @ResponseBody User  getInformesCentro(
			@RequestParam(value = "flgHabitual", required = false) String flgHabitual,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User user = (User)session.getAttribute("user");
		if (null != user.getCentro()){
			try {
				if (user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PESCA_13) != -1){
					user.getCentro().setInformeListado(this.informeListadoService.obtenerInformeListado(user.getCentro().getCodCentro(),flgHabitual));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return user;
	}	
	
	@RequestMapping(value = "/esCentroReferenciaVegalsa", params="codArticulo", method = RequestMethod.GET)
	public  @ResponseBody Boolean esCentroReferenciaVegalsa(
			@RequestParam Long codArticulo,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User user = (User) session.getAttribute("user");
		return utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), codArticulo);
		
	}

}