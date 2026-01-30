package es.eroski.misumi.control;


import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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

import es.eroski.misumi.dao.iface.VPlanogramaService;
import es.eroski.misumi.model.Articulo;
import es.eroski.misumi.model.CambioGama;
import es.eroski.misumi.model.CapraboMotivoNoPedible;
import es.eroski.misumi.model.CapraboMotivoNoPedibleArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleCentroArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleMotivo;
import es.eroski.misumi.model.CcRefCentro;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.CentroAutoservicio;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoLista;
import es.eroski.misumi.model.DiaVentasUltimasOfertas;
import es.eroski.misumi.model.EstadoConvergencia;
import es.eroski.misumi.model.FechaProximaEntregaRef;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.HistoricoUnidadesVenta;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.HistoricoVentaUltimoMes;
import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.model.IncluirExcluirRefGama;
import es.eroski.misumi.model.MesAnioPeriodo;
import es.eroski.misumi.model.MontajeVegalsa;
import es.eroski.misumi.model.Motivo;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.model.MovimientoStock;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.OfertaVigente;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.ParamStockFinalMinimo;
import es.eroski.misumi.model.PedidoAdicionalAyuda1;
import es.eroski.misumi.model.PedidoAdicionalContadores;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PendientesRecibir;
import es.eroski.misumi.model.Planograma;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.QueHacerRef;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.model.RefEnDepositoBrita;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.model.StockNoServido;
import es.eroski.misumi.model.StockPlataforma;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.TextoMotivo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VFacingX;
import es.eroski.misumi.model.VMapaAprov;
import es.eroski.misumi.model.VMapaAprovFestivo;
import es.eroski.misumi.model.VMapaReferencia;
import es.eroski.misumi.model.VOfertaPaAyuda;
import es.eroski.misumi.model.VPlanograma;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.ValoresStock;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaModType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaResModType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaTypeResponse;
import es.eroski.misumi.model.pedidosCentroWS.MotivosType;
import es.eroski.misumi.model.pedidosCentroWS.ReferenciasValidadasType;
import es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.model.ventasTiendaWS.ReferenciaRespuetaType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType;
import es.eroski.misumi.service.iface.CambioGamaService;
import es.eroski.misumi.service.iface.CapraboMotivosNoPedibleService;
import es.eroski.misumi.service.iface.CcRefCentroService;
import es.eroski.misumi.service.iface.DetalladoPedidoService;
import es.eroski.misumi.service.iface.EstadoConvergenciaService;
import es.eroski.misumi.service.iface.FacingVegalsaService;
import es.eroski.misumi.service.iface.FechaProximasEntregasService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.HistoricoUnidadesVentaService;
import es.eroski.misumi.service.iface.HistoricoVentaMediaService;
import es.eroski.misumi.service.iface.HistoricoVentaUltimoMesService;
import es.eroski.misumi.service.iface.ImagenComercialService;
import es.eroski.misumi.service.iface.IncluirExcluirRefGamaService;
import es.eroski.misumi.service.iface.KosmosService;
import es.eroski.misumi.service.iface.MotivoTengoMuchoPocoService;
import es.eroski.misumi.service.iface.MovimientosStockService;
import es.eroski.misumi.service.iface.OfertaVigenteService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.ParamCentrosVpService;
import es.eroski.misumi.service.iface.PedidoAdicionalService;
import es.eroski.misumi.service.iface.PedidosCentroService;
import es.eroski.misumi.service.iface.PendientesRecibirService;
import es.eroski.misumi.service.iface.PlanogramaCentroService;
import es.eroski.misumi.service.iface.PlanogramaKosmosService;
import es.eroski.misumi.service.iface.PlanogramaService;
import es.eroski.misumi.service.iface.PlanogramaVigenteService;
import es.eroski.misumi.service.iface.PlataformaService;
import es.eroski.misumi.service.iface.QueHacerRefService;
import es.eroski.misumi.service.iface.RefAsociadasService;
import es.eroski.misumi.service.iface.RefEnDepositoBritaService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockFinalMinimoService;
import es.eroski.misumi.service.iface.StockPlataformaService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.TMisMcgCapraboService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VAgruComerParamSfmcapService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VArtCentroAltaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VFacingXService;
import es.eroski.misumi.service.iface.VMapaAprovFestivoService;
import es.eroski.misumi.service.iface.VMapaAprovService;
import es.eroski.misumi.service.iface.VMapaReferenciaService;
import es.eroski.misumi.service.iface.VReferenciaActiva2Service;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.service.iface.VentasTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/referenciasCentro")
public class p13ReferenciasCentroController {

	private static Logger logger = Logger.getLogger(p13ReferenciasCentroController.class);

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
	private VRelacionArticuloService vRelacionArticuloService;

	@Autowired
	private VMapaReferenciaService vMapaReferenciaService;

	@Autowired
	private VMapaAprovService vMapaAprovService;

	@Autowired
	private VMapaAprovFestivoService vMapaAprovFestivoService;

	@Autowired
	private MovimientosStockService vMovimientosStockService;

	@Autowired
	private PendientesRecibirService vPendientesRecibirService;

	@Autowired
	private StockFinalMinimoService stockFinalMinimoService;

	@Autowired
	private PlanogramaVigenteService planogramaVigenteService;

	@Autowired
	private VAgruComerRefService vAgruComerRefService;

	@Autowired
	private PlanogramaService planogramaService;

	@Autowired
	private PlanogramaKosmosService planogramaKosmosService;

	@Autowired
	private HistoricoVentaMediaService historicoVentaMediaService;

	@Autowired
	private HistoricoVentaUltimoMesService historicoVentaUltimoMesService;
	@Autowired
	private PedidosCentroService pedidosCentroService;
	@Autowired
	private PlanogramaCentroService planogramaCentroService;
	@Autowired
	private VAgruComerParamSfmcapService vAgruComerParamSfmcapService;
	@Autowired
	private ParamCentrosVpService paramCentrosVpService;

	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@Autowired
	private CcRefCentroService ccRefCentroService;

	@Autowired
	private EstadoConvergenciaService estadoConvergenciaService;

	@Autowired
	private RelacionArticuloService relacionArticuloService;

	@Autowired
	private HistoricoUnidadesVentaService historicoUnidadesVentaService;

	@Autowired
	private VentasTiendaService ventasTiendaService;

	@Autowired
	private VArtCentroAltaService vArtCentroAltaService;

	@Autowired
	private MotivoTengoMuchoPocoService motivoTengoMuchoPocoService;

	@Autowired
	private RefAsociadasService refAsociadasService;

	@Autowired
	private StockTiendaService stockTiendaService;

	@Autowired
	private QueHacerRefService queHacerRefService;

	@Autowired
	private FotosReferenciaService fotosReferenciaService;

	@Autowired
	private VReferenciaActiva2Service vReferenciaActiva2Service;

	@Autowired
	private RefEnDepositoBritaService refEnDepositoBritaService;

	@Autowired
	private VFacingXService vFacingXService;

	@Autowired
	private PedidoAdicionalService pedidoAdicionalService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@Autowired
	private TMisMcgCapraboService tMisMcgCapraboService;

	@Autowired
	private CapraboMotivosNoPedibleService capraboMotivosNoPedibleService;

	@Autowired
	private DetalladoPedidoService detalladoPedidoService;

	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	@Autowired
	private VPlanogramaService vPlanogramaService;

	@Autowired
	private KosmosService kosmosService;

	@Autowired
	private PlataformaService plataformaService;

	@Autowired
	private StockPlataformaService stockPlataformaService;

	@Autowired
	private ImagenComercialService imagenComercialService;
	
	@Autowired
	private FechaProximasEntregasService fechaProximasEntregasService;
	
	@Autowired
	private CambioGamaService cambioGamaService;

	@Resource
	private MessageSource messageSource;

	@Value( "${adminRole}" )
	private String adminRole;

	@Value( "${tecnicRole}" )
	private String tecnicRole;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;

	@Autowired
	private FacingVegalsaService facingVegalsaService;
	
	@Autowired
	private IncluirExcluirRefGamaService incluirExcluirRefGamaService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model,
			@RequestParam(required = false, defaultValue = "") String reference,
			@RequestParam(required = false, defaultValue = "") String pagConsulta,
			HttpSession session,HttpServletResponse response
			) throws Exception {

		User user = (User) session.getAttribute("user");

		try {

			if (!"".equals(reference) && reference!=null){
				model.put("reference", reference);
				if (user.getCentro() != null && user.getCentro().getCodCentro() != null && utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode())){
					model.put("referenceEroski", utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), new Long(reference)).toString());
				}else{
					model.put("referenceEroski", reference);
				}
				model.put("pagConsulta", pagConsulta);
			}else{
				model.put("reference", "");
				model.put("referenceEroski", "");
			}

			return "p13_referenciasCentro";


		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}

	/****************************** PETICIÓN 55119******************************/
	@RequestMapping(value="/loadMesesAnioPeriodo", method = RequestMethod.POST)
	public @ResponseBody List<MesAnioPeriodo> loadMesesAnioPeriodo(
			@RequestParam(value = "locale", required = false, defaultValue = "es") String locale,
			HttpSession session, HttpServletResponse response) throws Exception {

		//Conseguir nombre del mes reducido
		String mesTexto;

		//Conseguir el número de días del mes, mes y año
		String mesNumero;
		String anioNumero;
		String diaTotalMesNumero;

		//Conseguir periodo mes actual
		String periodoMes;
		String periodoIni;
		String periodoFin;

		//Objeto con mes, año y periodo (AnioPeriodo)
		MesAnioPeriodo mesAnioPeriodo; 

		//Lista meses con año y periodo
		List<MesAnioPeriodo> mesAnioPeriodoList = new ArrayList<MesAnioPeriodo>();

		//Conseguir día de hoy
		Calendar calendario = Calendar.getInstance();
		//Para pruebas calendario.set(2017, Calendar.MARCH, 1);

		//Calcular qué día es hoy
		int diaHoy  = Integer.parseInt(new SimpleDateFormat("dd",new Locale(locale)).format(calendario.getTime()));

		//Si hoy no es día 1 calcular mes actual y dos meses anteriores. En caso contrario, calcular 3 meses anteriores
		int ayer = diaHoy -1;
		if(ayer>0){
			for(int j = 0;j>-2;j--){
				//Cambiar año actual, año actual -1
				calendario.add(Calendar.YEAR, j);

				for(int i = 0;i>-3;i--){
					//Cambiar año -1, año -2
					calendario.add(Calendar.MONTH, i);

					//Conseguir texto del mes, FEB, ENE, ETC
					mesTexto  = new SimpleDateFormat("MMM",new Locale(locale)).format(calendario.getTime()).toUpperCase();

					//Conseguir dias totales del mes, año y mes
					mesNumero  = new SimpleDateFormat("MM",new Locale(locale)).format(calendario.getTime());
					anioNumero = new SimpleDateFormat("yyyy",new Locale(locale)).format(calendario.getTime());
					diaTotalMesNumero = String.valueOf(calendario.getActualMaximum(Calendar.DAY_OF_MONTH));

					//Conseguir periodo del mes (Ej.01012016-31012016)
					periodoIni = "01"+mesNumero+anioNumero;
					periodoFin = diaTotalMesNumero+mesNumero+anioNumero;
					periodoMes = periodoIni+"-"+periodoFin;

					//Conseguir periodo del mes (Ej.01012016-31012016)
					mesAnioPeriodo = new MesAnioPeriodo(mesTexto,anioNumero, periodoMes,periodoIni,periodoFin);

					mesAnioPeriodoList.add(mesAnioPeriodo);

					//Reestablece el mes actual. Sin esto se guardaría el mes anterior calculado y no haría bien la cuenta.
					//FEBRERO(MES ACTUAL) -> -1 -> ENERO (MES ACTUAL DEL CALENDARIO) -> -2 -> NOVIEMBRE MAL
					//FEBRERO(MES ACTUAL) -> -1 -> ENERO -> +1 REESTABLECER MES ACTUAL-> FEBRERO -> -2 -> DICIEMBRE BIEN
					calendario.add(Calendar.MONTH, -(i));
				}
			}		
		}else{
			//Cambiar año -1, año -2
			calendario.add(Calendar.MONTH, -1);
			for(int j = 0;j>-2;j--){
				//Cambiar año actual, año actual -1
				calendario.add(Calendar.YEAR, j);

				for(int i = 0;i>-3;i--){
					//Cambiar año -1, año -2
					calendario.add(Calendar.MONTH, i);

					//Conseguir texto del mes, FEB, ENE, ETC
					mesTexto  = new SimpleDateFormat("MMM",new Locale(locale)).format(calendario.getTime()).toUpperCase();

					//Conseguir dias totales del mes, año y mes
					mesNumero  = new SimpleDateFormat("MM",new Locale(locale)).format(calendario.getTime());
					anioNumero = new SimpleDateFormat("yyyy",new Locale(locale)).format(calendario.getTime());
					diaTotalMesNumero = String.valueOf(calendario.getActualMaximum(Calendar.DAY_OF_MONTH));

					//Conseguir periodo del mes (Ej.01012016-31012016)
					periodoIni = "01"+mesNumero+anioNumero;
					periodoFin = diaTotalMesNumero+mesNumero+anioNumero;
					periodoMes = periodoIni+"-"+periodoFin;

					//Conseguir periodo del mes (Ej.01012016-31012016)
					mesAnioPeriodo = new MesAnioPeriodo(mesTexto,anioNumero, periodoMes,periodoIni,periodoFin);

					mesAnioPeriodoList.add(mesAnioPeriodo);

					//Reestablece el mes actual. Sin esto se guardaría el mes anterior calculado y no haría bien la cuenta.
					//FEBRERO(MES ACTUAL) -> -1 -> ENERO (MES ACTUAL DEL CALENDARIO) -> -2 -> NOVIEMBRE MAL
					//FEBRERO(MES ACTUAL) -> -1 -> ENERO -> +1 REESTABLECER MES ACTUAL-> FEBRERO -> -2 -> DICIEMBRE BIEN
					calendario.add(Calendar.MONTH, -(i));
				}
			}	
		}
		return mesAnioPeriodoList;
	}

	@RequestMapping(value="/loadDiasPeriodo", method = RequestMethod.POST)
	private @ResponseBody PedidoAdicionalAyuda1 loadDiasPeriodo(
			@RequestBody VOfertaPaAyuda vVOfertaPaAyuda, 
			@RequestParam(value = "sumVentaAnticipada", required = false, defaultValue = "1") String sumVentaAnticipada,
			HttpSession session, HttpServletResponse response){

		//Crear objeto con lista de fechas.
		PedidoAdicionalAyuda1 aux=new PedidoAdicionalAyuda1();

		//Crear lista de fechas
		List<DiaVentasUltimasOfertas> listaFechasVentasMedias = null;
		Float total = new Float(0);
		try {
			//Si el centro es de caprabo, buscar también con referencia eroski.
			User user = (User) session.getAttribute("user");
			if (utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode())){
				vVOfertaPaAyuda.setCodArt(utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), vVOfertaPaAyuda.getCodArt()));
			}

			//Busco en BBDD la lista de fechas con sus ventas medias
			listaFechasVentasMedias = this.historicoUnidadesVentaService.findDateListMediaSales(
					vVOfertaPaAyuda.getCodCentro().toString(), 
					vVOfertaPaAyuda.getCodArt().toString(), 
					Utilidades.formatearFecha(vVOfertaPaAyuda.getFechaIni()), 
					Utilidades.formatearFecha(vVOfertaPaAyuda.getFechaFin()),
					sumVentaAnticipada);

			aux.setListaDiaVentasUltimasOfertas(listaFechasVentasMedias);

			//Total Ventas
			if (listaFechasVentasMedias != null && listaFechasVentasMedias.size() > 0) {
				for (DiaVentasUltimasOfertas diaVentasUltimasOfertas : listaFechasVentasMedias){
					total +=  diaVentasUltimasOfertas.getcVD().floatValue() ;
				} 
			}
			aux.setTotalVentas(total);
		} catch (Exception e) {
			logger.error("ObtenerCantidadesDia="+e.toString());
			e.printStackTrace();
		}
		return aux;
	}

	/****************************************************************************/

	@RequestMapping(value="/loadDatosMaestrosFijo", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentro loadDatosMaestroFijo(
			@RequestBody ReferenciasCentro vReferenciasCentro,
			@RequestParam(value = "origen", required = false, defaultValue = "") String origen,
			HttpSession session, HttpServletResponse response) throws Exception {

		Locale locale = LocaleContextHolder.getLocale();

		boolean textilPedible = false;
		boolean esCentroCaprabo = false;
		boolean esCentroCapraboEspecial = false;
		boolean esCentroCapraboNuevo = false;

		session.removeAttribute("p13respuestaWS");
		User user = (User) session.getAttribute("user");
		session.setAttribute("listMotGeneral" + vReferenciasCentro.getOrigenPantalla(),  new ArrayList<Motivo>());
		session.setAttribute("listMotMMC" + vReferenciasCentro.getOrigenPantalla(),  new ArrayList<Motivo>());

		HashMap<Long, String> mapPedible = (HashMap<Long, String>) session.getAttribute("pedible");
		if(mapPedible != null){
			mapPedible.put(vReferenciasCentro.getCodArt(), null);
		}else{
			mapPedible = new HashMap<Long, String>();
			mapPedible.put(vReferenciasCentro.getCodArt(), null);
		}

		session.setAttribute("pedible", mapPedible);
		session.setAttribute("textilPedible", false);
		session.setAttribute("performed", "F");
		try {
			ReferenciasCentro referenciasCentro = vReferenciasCentro;

			esCentroCaprabo = utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode());
			referenciasCentro.setEsCentroCaprabo(esCentroCaprabo);
			esCentroCapraboEspecial = utilidadesCapraboService.esCentroCapraboEspecial(user.getCentro().getCodCentro(), user.getCode());
			referenciasCentro.setEsCentroCapraboEspecial(esCentroCapraboEspecial);
			esCentroCapraboNuevo = utilidadesCapraboService.esCentroCapraboNuevo(user.getCentro().getCodCentro(), user.getCode());
			referenciasCentro.setEsCentroCapraboNuevo(esCentroCapraboNuevo);

			// Miramos si tiene tratamiento Vegalsa
			boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
			referenciasCentro.setEsTratamientoVegalsa(tratamientoVegalsa);

			Object[] argsMsgUnidadesCajas = new Object[] {"En Unidades:"};
			String msgUNIDADESCAJAS = messageSource.getMessage("p15_referenciasCentroIC.enUnidadesMod",argsMsgUnidadesCajas, locale);

			// Sólo para los casos en los que el centro es VEGALSA.
			if (tratamientoVegalsa){
				VMapaReferencia vMapaReferencia = this.obtenerMapaReferencia(referenciasCentro);
				
				if (vMapaReferencia != null){
					// Se asigna el Mapa de la referencia con formato: código mapa - descripción mapa.
					referenciasCentro.setMapaReferencia(vMapaReferencia.getMapa());
					referenciasCentro.setBloqueo(vMapaReferencia.getBloqueo());

					if (Constantes.MAPA_5.equals(vMapaReferencia.getCodMapa()) || Constantes.MAPA_52.equals(vMapaReferencia.getCodMapa())){
						argsMsgUnidadesCajas = new Object[] {Constantes.NUM_CAJAS_IC};
						msgUNIDADESCAJAS = messageSource.getMessage("p15_referenciasCentroIC.enUnidadesMod",argsMsgUnidadesCajas, locale);
					}
				}
			}
			referenciasCentro.setMsgUNIDADESCAJAS(msgUNIDADESCAJAS);

			//El código Eroski es el mismo que viene desde pantalla
			referenciasCentro.setCodArtEroski(referenciasCentro.getCodArt());
			referenciasCentro.setDiarioArt(obtenerDiarioArt(referenciasCentro));

			if ((referenciasCentro != null) &&  (referenciasCentro.getDiarioArt() != null)) {
				//Devuelve código vacío.
				List<VAgruComerRef> listVAgrucomerRef =  this.vAgruComerRefService.findAll(new VAgruComerRef("I3",referenciasCentro.getDiarioArt().getGrupo1(),referenciasCentro.getDiarioArt().getGrupo2(),referenciasCentro.getDiarioArt().getGrupo3(),null,null,null), null);
				VAgruComerRef vAgruComeref = listVAgrucomerRef.size() > 0 ? listVAgrucomerRef.get(0) : null;
				if(vAgruComeref != null){
					referenciasCentro.setDescGrupo3(vAgruComeref.getDescripcion());
				}
			}
			referenciasCentro.setSurtidoTienda(obtenerSurtidoTienda(referenciasCentro));
				
			//Obtener flag de deposito brita
			referenciasCentro.setFlgDepositoBrita(obtenerFlgDepositoBrita(referenciasCentro));

			//Obtener flag de referencia por catálogo
			referenciasCentro.setFlgPorCatalogo(obtenerFlgPorCatalogo(referenciasCentro));

			if (null != referenciasCentro.getDiarioArt()){
				//Tratamiento para el mensaje de Ffpp activo o unitaria
				boolean tratamientoVegalsaAux = this.utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
				referenciasCentro = this.vRelacionArticuloService.obtenerFfppActivaOUnitaria(referenciasCentro,tratamientoVegalsaAux);

				//Tratamiento pedido Adicional
				if ((origen != null) && !(origen.equals("BUSCADOR"))) {

					TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
					tPedidoAdicional.setIdSesion(session.getId());
					tPedidoAdicional.setCodCentro(referenciasCentro.getCodCentro());
					tPedidoAdicional.setCodArticulo(referenciasCentro.getCodArtEroski());
//					referenciasCentro.setPedidoAdicionalContadores(obtenerPedidoAdicional(tPedidoAdicional,session,response));
					PedidoAdicionalContadores resultado = new PedidoAdicionalContadores();
					resultado=obtenerPedidoAdicional(tPedidoAdicional,session,response);
					if (esCentroVegalsa(session)){
						PedidoAdicionalContadores resultadoVegalsa = this.pedidoAdicionalService.loadContadoresVegalsaReferencia(tPedidoAdicional);
						resultado = sumarContadores(resultado, resultadoVegalsa);
					}
					referenciasCentro.setPedidoAdicionalContadores(resultado);
				}

				RelacionArticulo relacionArticulo = new RelacionArticulo();
				relacionArticulo.setCodArtRela(referenciasCentro.getCodArtEroski());
				relacionArticulo.setCodCentro(vReferenciasCentro.getCodCentro());
				List<Long> referencias = this.relacionArticuloService.findAll(relacionArticulo);

				if (!referenciasCentro.isTieneUnitaria() && referencias.isEmpty()){
					referenciasCentro.setValoresStock(this.obtenerValoresStock(referenciasCentro, session));
					if (session.getAttribute("p13respuestaWS")!=null){
						referenciasCentro.setRespuestaWSStock((String)session.getAttribute("p13respuestaWS"));
					}
				}

					//Petición 48890. Movimimiento continuo de gama. Se llama al ws de PlanogramaCentro.
					ConsultaPlanogramaPorReferenciaResponseType result = this.planogramaCentroService.findPlanogramasCentroWS(referenciasCentro,session);

					if (result != null){

						if (result.getCodigoRespuesta().equals("0")){
							if (!esCentroCaprabo){
								if (result.getPlanogramaReferencia(0).getSustituidaPor() != null) { referenciasCentro.setSustituidaPor(result.getPlanogramaReferencia(0).getSustituidaPor().longValue()); }
								if (result.getPlanogramaReferencia(0).getSustitutaDe() != null) { referenciasCentro.setSustitutaDe(result.getPlanogramaReferencia(0).getSustitutaDe().longValue()); }
							}
							if (result.getPlanogramaReferencia(0).getImplantancion() != null){
								referenciasCentro.setImplantacion(result.getPlanogramaReferencia(0).getImplantancion());
							}
							if (result.getPlanogramaReferencia(0).getFechaActivacion() != null) { 
								referenciasCentro.setFechaActivacion(result.getPlanogramaReferencia(0).getFechaActivacion().getTime()); 
								//Obtenemos la fecha generacion de surtido tienda. Fecha en la que la referencia pasa de PEDIR N a PEDIR S.
								if (null != referenciasCentro.getSurtidoTienda()){
									Date FechaGenSurtidoTienda = vSurtidoTiendaService.obtenerFechaGeneracionSurtidoTienda(referenciasCentro.getSurtidoTienda());
									if (FechaGenSurtidoTienda != null) {
										referenciasCentro.setFechaGen(FechaGenSurtidoTienda);
										referenciasCentro.setStrFechaGen(Utilidades.formatearFecha(FechaGenSurtidoTienda));

										//Begin: Petición 55001.
										//Comprobamos si la fecha de generación se debe mostrar o no

										//Fecha de generación de surtido tienda
										Calendar calFechaGenSurtidoTienda = Calendar.getInstance();
										calFechaGenSurtidoTienda.setTime(FechaGenSurtidoTienda);

										//Obtenemos la fecha actual
										Calendar calFechaActual = Calendar.getInstance();

										//Obtenemos  la diferencia entre las dos fechas 
										long millisDay = 24*60*60*1000;
										long diffDays = Math.round((calFechaActual.getTimeInMillis() - calFechaGenSurtidoTienda.getTimeInMillis())/(millisDay));

										if (diffDays <= 30) {//Si la diferencia es menor a 30 días se mostrará en pantalla
											referenciasCentro.setMostrarFechaGen(true);
										}else{
											referenciasCentro.setMostrarFechaGen(false);
										}
										//End: Petición 55001.
									}
								}
							}

							//Miramos si el WS devuelve el campo implantación. Si devuelve implantación habra que comprobar si este se pinta en rojo o verde 
							//en la pestaña Imagen comercial. 
							if ((referenciasCentro.getImplantacion() != null)  && !(referenciasCentro.getImplantacion().trim().equals(""))) {

								//comprobamos si el WS devuelve el campo Fecha Activacion. 
								if (referenciasCentro.getFechaGen() != null) { 
									referenciasCentro.setFlgColorImplantacion("VERDE");
								} else {

									//comprobamos si el STOCK es mayor a 0 o si es 0 pero no ha pasado mas de un mes desde que se ha puesto a cero. En ese caso lo pintamos a rojo

									if (null != referenciasCentro.getSurtidoTienda() && vSurtidoTiendaService.comprobarStockMayorACero(referenciasCentro.getSurtidoTienda()) > 0 ){
										referenciasCentro.setFlgColorImplantacion("ROJO");
									}
								}
							}
						} 
					}
				}

				//Comprobamos si la referencia es de textil pedible
				textilPedible = this.vArtCentroAltaService.esTextilPedible(user.getCentro(), referenciasCentro.getCodArtEroski()); 
				session.setAttribute("textilPedible", Boolean.valueOf(textilPedible));

				//Mostrar Accion
				//			    if (user.getCentro().getNegocio().equals(Constantes.CENTRO_NEGOCIO_HIPER) && null != referenciasCentro.getDiarioArt() &&  !referenciasCentro.getDiarioArt().getGrupo1().equals(new Long(1)) &&
				//						!referenciasCentro.getDiarioArt().getGrupo1().equals(new Long(2))){
				QueHacerRef queHacerRef = new QueHacerRef();
				queHacerRef.setCodLoc(user.getCentro().getCodCentro());
				queHacerRef.setCodArtFormlog(referenciasCentro.getCodArtEroski());
				queHacerRef = this.queHacerRefService.obtenerAccionRef(queHacerRef);
				if (null != queHacerRef && null != queHacerRef.getEstado() && (new Long(0)).equals(queHacerRef.getEstado())){
					if (null != queHacerRef.getAccion()){
						referenciasCentro.setAccion(queHacerRef.getAccion());
					} else {
						referenciasCentro.setAccion(messageSource.getMessage("p14_referenciasCentro.sinAccion", null, LocaleContextHolder.getLocale()));
					}
				} else {
					referenciasCentro.setAccion(messageSource.getMessage("p14_referenciasCentro.sinAccion", null, LocaleContextHolder.getLocale()));
				}
			//Comprobar si tiene Foto
			FotosReferencia fotosReferencia = new FotosReferencia();
			fotosReferencia.setCodReferencia(referenciasCentro.getCodArtEroski());
			if (fotosReferenciaService.checkImage(fotosReferencia)){
				referenciasCentro.setTieneFoto("S");
			} else {
				referenciasCentro.setTieneFoto("N");
			}
			//			}


			//Obtenemos los datos de Ofertas y Precios accediendo a KOSMOS

			/*OfertaPVP ofertaPVP = null;
			if(user.getCentro().isEsCentroCapraboNuevo() || (!user.getCentro().isEsCentroCapraboEspecial() && !user.getCentro().getEsCentroCaprabo())){	
				ofertaPVP = new OfertaPVP();
				ofertaPVP.setCentro(user.getCentro().getCodCentro());
				ofertaPVP.setCodArticulo(referenciasCentro.getCodArtEroski());
				Date fechaHoy = new Date();
				ofertaPVP.setFecha(fechaHoy);
				ofertaPVP  = this.kosmosService.obtenerDatosPVP(ofertaPVP);
				if (null != ofertaPVP){
					referenciasCentro.setOfertaPVP(ofertaPVP);	
					ofertaPVP.setFlgMostrarPVP("S");
				} 

			}*/
			//Queremos que sea para todos los centros. 20/10/2020 Inc.Misumi-244.
			OfertaPVP ofertaPVP = null;	
			ofertaPVP = new OfertaPVP();
			ofertaPVP.setCentro(user.getCentro().getCodCentro());
			ofertaPVP.setCodArticulo(referenciasCentro.getCodArtEroski());
			Date fechaHoy = new Date();
			ofertaPVP.setFecha(fechaHoy);
			ofertaPVP  = this.kosmosService.obtenerDatosPVP(ofertaPVP);
			
			// MISUMI-330: MISUMI-JAVA VEGALSA MISUMI-JAVA VEGALSA Recoger el precio del Ws de Stock cuando NO tenga PVP
			if (ofertaPVP!=null && ofertaPVP.getTarifa()==null){
				Double pvpWS = stockTiendaService.consultaPVP(user.getCentro().getCodCentro(), referenciasCentro.getCodArtEroski(), session);
				if (pvpWS!=null){
					ofertaPVP.setTarifa(pvpWS);
					ofertaPVP.setTarifaStr(pvpWS.toString().replace(".", ","));
				}
				// MISUMI-391 MISUMIS-JAVA VEGALSA-FAMILIA sacar las ofertas de oferta vigente
				ofertaPVP=this.ofertaVigenteService.recuperarAnnoOferta(ofertaPVP);
			}
			
			if (null != ofertaPVP){
				referenciasCentro.setOfertaPVP(ofertaPVP);	
				ofertaPVP.setFlgMostrarPVP("S");
			} 
			//MISUMI-428
			//HABRÁ Q COMPROBAR SI ES MAC O MA PARA SABER Q DATOS DEVOLVER
			ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();
			referenciasCentroIC.setCodCentro(referenciasCentro.getCodCentro());
			referenciasCentroIC.setCodArt(referenciasCentro.getCodArtEroski());
			PlanogramaVigente planogramaVigente = obtenerPlanogramaVigente(referenciasCentroIC); 
			if(planogramaVigente!=null){
				planogramaVigente = rellenarDatosVegalsa(planogramaVigente, session);
				if(planogramaVigente!=null && planogramaVigente.getOfertaProm()!=null &&  planogramaVigente.getEspacioProm()!=null){
					int cantidad=0;
					if(planogramaVigente.getFechaGenMontaje1()!=null){
						cantidad=planogramaVigente.getCapacidadMontaje1().intValue();
					}else if(planogramaVigente.getFechaGenCabecera()!=null){
						cantidad=planogramaVigente.getCapacidadMaxCabecera().intValue();
					}
					referenciasCentro.setMAC(cantidad + " " + planogramaVigente.getOfertaProm());
					referenciasCentro.setEspacio(planogramaVigente.getEspacioProm());
				}
			}
			MontajeVegalsa montajeVegalsa=tPedidoAdicionalService.getPedidosVegalsa(referenciasCentroIC.getCodCentro(), referenciasCentroIC.getCodArt());
			if(montajeVegalsa!=null){
				referenciasCentro.setCantidadMA(montajeVegalsa.getCantidad().toString());
				referenciasCentro.setFechaInicioMA(Utilidades.formatearFecha_dd_MM_yyyy(montajeVegalsa.getFechaInicio()));
				referenciasCentro.setFechaFinMA(Utilidades.formatearFecha_dd_MM_yyyy(montajeVegalsa.getFechaFin()));
			}

			//Datos especificos de textil
			if (null != referenciasCentro.getDiarioArt()){
				if (referenciasCentro.getDiarioArt().getGrupo1().longValue() == 3) {//Es una referencia de textil
					if (!(this.relacionArticuloService.esReferenciaLote(referenciasCentro.getCodArtEroski()).size() > 0)){ 
						//Es una referenca de textil pero no lote. Recuperamos los datos especificos de textil de V_DATOS_ESPECIFICOS

						List<DetallePedido> listaDatosTextil = this.relacionArticuloService.findDatosEspecificosTextilPC(referenciasCentro.getCodArtEroski());

						if (listaDatosTextil!= null & listaDatosTextil.size() >0) {
							referenciasCentro.setTemporada(listaDatosTextil.get(0).getTemporada().substring(0, 2) + " " + listaDatosTextil.get(0).getTemporada().substring(2));
							referenciasCentro.setNumOrden(Utilidades.rellenarIzquierda(listaDatosTextil.get(0).getNumOrden().toString(), '0', 3));
							referenciasCentro.setModeloProveedor(listaDatosTextil.get(0).getModeloProveedor());
							referenciasCentro.setTalla(listaDatosTextil.get(0).getDescrTalla());
							referenciasCentro.setColor(listaDatosTextil.get(0).getDescrColor());
						}

					}
				}else if(referenciasCentro.getDiarioArt().getGrupo1().longValue() == new Long(Constantes.AREA_BAZAR).longValue() || referenciasCentro.getDiarioArt().getGrupo1().longValue() == new Long(Constantes.AREA_ELECTRO).longValue()) {
					if(referenciasCentro.getDiarioArt().getCodArt() != null){
						Double stockPlataforma = this.obtenerStockPlataforma(referenciasCentro.getDiarioArt().getCodArt(), user.getCentro().getCodCentro());
						referenciasCentro.setStockPlataforma(stockPlataforma);
					}
				}
			}
			//MISUMI-518
			if(referenciasCentro.getSurtidoTienda() != null && referenciasCentro.getSurtidoTienda().getMarcaMaestroCentro() != null){
				if(referenciasCentro.getSurtidoTienda().getMarcaMaestroCentro().equals("S")){
					CambioGama cambioGama=cambioGamaService.existeGama(referenciasCentro.getCodCentro(), referenciasCentro.getCodArt());
					if(cambioGama != null && cambioGama.getTipo().equals(Constantes.EXCLUIR)){
						referenciasCentro.setExisteGama(true);
					}else{
						referenciasCentro.setExisteGama(false);
					}
				}else if(referenciasCentro.getSurtidoTienda().getMarcaMaestroCentro().equals("N")){
					CambioGama cambioGama=cambioGamaService.existeGama(referenciasCentro.getCodCentro(), referenciasCentro.getCodArt());
					if(cambioGama != null && cambioGama.getTipo().equals(Constantes.INCLUIR)){
						referenciasCentro.setExisteGama(true);
					}else{
						referenciasCentro.setExisteGama(false);
					}
				}
			}
			return referenciasCentro;
		} catch (Exception e) {

			logger.error("######################## Error  ############################");
			logger.error("Referencia: " + vReferenciasCentro.getCodArt() );
			logger.error("############################################################");
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
			if ((referenciasCentro != null) && (referenciasCentro.getDiarioArt() != null)) {
				//VAgruComerRef vAgruComerref = this.vAgruComerRefService.findAll(new VAgruComerRef("I3",referenciasCentro.getDiarioArt().getGrupo1(),referenciasCentro.getDiarioArt().getGrupo2(),referenciasCentro.getDiarioArt().getGrupo3(),null,null,null), null).get(0);
				//referenciasCentro.setDescGrupo3(vAgruComerref.getDescripcion());

				List<VAgruComerRef> listVAgrucomerRef =  this.vAgruComerRefService.findAll(new VAgruComerRef("I3",referenciasCentro.getDiarioArt().getGrupo1(),referenciasCentro.getDiarioArt().getGrupo2(),referenciasCentro.getDiarioArt().getGrupo3(),null,null,null), null);
				VAgruComerRef vAgruComeref = listVAgrucomerRef.size() > 0 ? listVAgrucomerRef.get(0) : null;
				if(vAgruComeref != null){
					referenciasCentro.setDescGrupo3(vAgruComeref.getDescripcion());
				}

			}
			User user = (User) session.getAttribute("user");
			// Miramos si tiene tratamiento Vegalsa
			boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
			referenciasCentro.setEsTratamientoVegalsa(tratamientoVegalsa);
			
			referenciasCentro.setSurtidoTienda(obtenerSurtidoTienda(referenciasCentro));
			referenciasCentro.setOferta(obtenerOferta(referenciasCentro));
			referenciasCentro.setVariablesPedido(obtenerVariablesPedido(referenciasCentro));
			//Según petición los datos de ffpp pasan al mensaje de cabecera
			//referenciasCentro.setArticuloRelacionado(obtenerRelacionArticulo(referenciasCentro));
			if (referenciasCentro.isEsTratamientoVegalsa()){
				VMapaReferencia vMapaReferencia = this.obtenerMapaReferencia(referenciasCentro);
				referenciasCentro.setCc(referenciasCentro.getSurtidoTienda().getCc());
				
				if (vMapaReferencia != null){
					referenciasCentro.setMapaReferencia(vMapaReferencia.getMapa());
					referenciasCentro.setBloqueo(vMapaReferencia.getBloqueo());
				}
			} else {
				referenciasCentro.setCc(obtenerCc(referenciasCentro));
			}
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

	@RequestMapping(value="/loadDatosImagenComercial", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentroIC loadDatosImagenComercial(
			@RequestBody ReferenciasCentroIC vReferenciasCentroIC,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			ReferenciasCentroIC referenciasCentroIC = vReferenciasCentroIC;

			//Se inicializa al valor de no facing para cambiarlo en caso de que cumpla con las condiciones 
			referenciasCentroIC.setFlgFacing(Constantes.FLG_NO_FACING_CENTRO);

			User user = (User) session.getAttribute("user");
			//Obtención de estructura comercial a partir de la referencia
			VDatosDiarioArt vDatosDiarioArt = obtenerDiarioArt(referenciasCentroIC.getCodArt());

			//Obtención de si es una estructura de facing. Se mira si es un centro con facing y si la estructura a la
			//que pertenece el artículo tiene activado el flag de facing
			if (user.getCentro().getFlgFacing()!=null && Constantes.FLG_SI_FACING_CENTRO.equals(user.getCentro().getFlgFacing())){
				VAgruComerParamSfmcap vAgruComerParamSfmcap = new VAgruComerParamSfmcap();
				vAgruComerParamSfmcap.setCodCentro(referenciasCentroIC.getCodCentro());
				vAgruComerParamSfmcap.setGrupo1(vDatosDiarioArt.getGrupo1());
				vAgruComerParamSfmcap.setGrupo2(vDatosDiarioArt.getGrupo2());
				vAgruComerParamSfmcap.setGrupo3(vDatosDiarioArt.getGrupo3());
				vAgruComerParamSfmcap.setGrupo4(vDatosDiarioArt.getGrupo4());
				vAgruComerParamSfmcap.setGrupo5(vDatosDiarioArt.getGrupo5());
				List<VAgruComerParamSfmcap> listaEstructuras = this.vAgruComerParamSfmcapService.findAll(vAgruComerParamSfmcap, null);

				if (listaEstructuras != null && listaEstructuras.size() > 0){
					VAgruComerParamSfmcap estructuraArticulo = listaEstructuras.get(0);
					if (estructuraArticulo.getFlgFacing() != null){
						referenciasCentroIC.setFlgFacing(estructuraArticulo.getFlgFacing());
					}
					if (estructuraArticulo.getFlgCapacidad() != null){ 
						referenciasCentroIC.setFlgCapacidad(estructuraArticulo.getFlgCapacidad());
					}
					if (estructuraArticulo.getFlgFacingCapacidad() != null){
						referenciasCentroIC.setFlgFacingCapacidad(estructuraArticulo.getFlgFacingCapacidad());
					}
				}
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

			return referenciasCentroIC;
		} catch (Exception e) {
			//		    logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value="/loadDatosImagenComercialNuevo", method = RequestMethod.POST)
	public @ResponseBody ImagenComercial loadDatosImagenComercialNuevo(
			@RequestBody ReferenciasCentroIC vReferenciasCentroIC,
			HttpSession session, HttpServletResponse response) throws Exception {

		ImagenComercial imagenComercial = new ImagenComercial();
		
		User user = (User)session.getAttribute("user");

		// Registrar si tiene tratamiento VEGALSA.
		boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), vReferenciasCentroIC.getCodArt());

		try{
			imagenComercial = imagenComercialService.consultaImc(vReferenciasCentroIC.getCodCentro(), vReferenciasCentroIC.getCodArt());
			
//			imagenComercial.setFlgPistola(null);
//			
//			logger.debug("Nombre usuario: "+user.getUserName()
//						+ " Código Usuario: "+user.getCode()
//						);
//			imagenComercial.setUsuario(user.getCode());
	
			// Si es un centro VEGALSA.
			if (tratamientoVegalsa){
				BigInteger[] arrayReferencias = new BigInteger[500];
				ConsultaFacingVegalsaRequestType facingVegalsaRequest = new ConsultaFacingVegalsaRequestType();

				arrayReferencias[0] = BigInteger.valueOf(vReferenciasCentroIC.getCodArt());
				facingVegalsaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
				facingVegalsaRequest.setTipo(Constantes.CONSULTAR_FACING);
				facingVegalsaRequest.setListaReferencias(arrayReferencias);

				ConsultaFacingVegalsaResponseType facingVegalsaResponse = facingVegalsaService.consultarFacing(facingVegalsaRequest, session);

				// Si la consulta al WS ha ido bien
				if (facingVegalsaResponse.getCodigoRespuesta().equals("OK")){
	
					for (ReferenciaTypeResponse referencia : facingVegalsaResponse.getReferencias()){

						// Si Referencia OK
						if (new BigInteger("0").equals(referencia.getCodigoError())){
							
							imagenComercial.setCentro(user.getCentro().getCodCentro());
							imagenComercial.setReferencia(vReferenciasCentroIC.getCodArt());
							imagenComercial.setCapacidad(referencia.getCapacidad().longValue());
							imagenComercial.setFacing(referencia.getFacing().longValue());
							imagenComercial.setMultiplicador(referencia.getFondo().intValue());
							imagenComercial.setCodError(referencia.getCodigoError().intValue());
							imagenComercial.setDescripcionError(referencia.getMensajeError());
							
							// Registramos si el centro tiene permiso de modificación del Facing.
							// En el caso de ser un centro VEGALSA, no debería tener permiso de moficación.
							boolean facingModificable = facingVegalsaService.isFacingModificable(user.getCentro().getCodCentro());
							imagenComercial.setFacingModificable(facingModificable?1:0);
							
						} else {
							imagenComercial.setCodError(referencia.getCodigoError().intValue());
							imagenComercial.setDescripcionError(referencia.getMensajeError());
							imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
						}
					}
				} else {
					imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
				}

			}
			
			vReferenciasCentroIC.setTratamientoVegalsa(tratamientoVegalsa?1:0);
			imagenComercial.setTratamientoVegalsa(vReferenciasCentroIC.isTratamientoVegalsa());

			return imagenComercial;
			
		} catch (Exception e) {
			imagenComercial.setTratamientoVegalsa(tratamientoVegalsa?1:0);
			imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
			return imagenComercial;
		}
	}
		
	@RequestMapping(value="/simularImc", method = RequestMethod.POST)
	public @ResponseBody ImagenComercial simularImc(
			@RequestBody ImagenComercial imagenComercial,
			HttpSession session, HttpServletResponse response) throws Exception {

		ImagenComercial imagenComercialSimulada = imagenComercialService.simularImc(imagenComercial);
		return imagenComercialSimulada;
	}

	@RequestMapping(value="/modificarImc", method = RequestMethod.POST)
	public @ResponseBody ImagenComercial modificarImc(
			@RequestBody ImagenComercial imagenComercial,
			HttpSession session, HttpServletResponse response) throws Exception {

		User user = (User)session.getAttribute("user");
		imagenComercial.setFlgPistola(null);
		
		logger.debug("Nombre usuario: "+user.getUserName()
					+ " Código Usuario: "+user.getCode()
					);
		imagenComercial.setUsuario(user.getCode());

		ImagenComercial imagenComercialSimulada = imagenComercialService.modificarImc(imagenComercial);
		return imagenComercialSimulada;
	}

	@RequestMapping(value="/modificarFacingVegalsa", method = RequestMethod.POST)
	public @ResponseBody ImagenComercial modificarFacingVegalsa(
			@RequestBody ImagenComercial imagenComercial,
			HttpSession session, HttpServletResponse response) throws Exception {

		User user = (User)session.getAttribute("user");

		ModificarFacingVegalsaResponseType facingVegalsaResponse = new ModificarFacingVegalsaResponseType();
		try{
			
			ReferenciaModType[] arrayReferencias = new ReferenciaModType[10];
			ModificarFacingVegalsaRequestType facingVegalsaRequest = new ModificarFacingVegalsaRequestType();

			facingVegalsaRequest.setCodigoCentro(BigInteger.valueOf(imagenComercial.getCentro()));
			facingVegalsaRequest.setTipo(Constantes.MODIFICAR_FACING);
			
			ReferenciaModType referenciaType = new ReferenciaModType();
			
			referenciaType.setCodigoReferencia(BigInteger.valueOf(imagenComercial.getReferencia()));
			referenciaType.setFacing(BigInteger.valueOf(imagenComercial.getFacing()));
			referenciaType.setCapacidad(BigInteger.valueOf(imagenComercial.getCapacidad()));
			
			arrayReferencias[0] = referenciaType;
			
			facingVegalsaRequest.setReferencias(arrayReferencias);
			imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_OK);
	
			facingVegalsaResponse = facingVegalsaService.modificarFacing(facingVegalsaRequest, session);
	
			// Si la consulta al WS ha ido bien
// Para PRUEBAS. Por ahora se comenta hasta que se corrija en el WS la manera de gestionar los errores.
//			     Cuando esté corregido en el WS se podrá descomentar este código (if) y la rama del ELSE.
//			if (facingVegalsaResponse.getCodigoRespuesta().equals("OK")){
	
				for (ReferenciaResModType referencia : facingVegalsaResponse.getReferencias()){
	
					// Si Referencia OK
					if (new BigInteger("0").equals(referencia.getCodigoError())){
						
						imagenComercial.setCapacidad(referencia.getCapacidad().longValue());
						imagenComercial.setFacing(referencia.getFacing().longValue());
						imagenComercial.setMultiplicador(referencia.getFondo().intValue());
						imagenComercial.setCodError(referencia.getCodigoError().intValue());
						imagenComercial.setDescripcionError(referencia.getMensajeError());
						
					} else {
						imagenComercial.setCodError(referencia.getCodigoError().intValue());
						imagenComercial.setDescripcionError(referencia.getMensajeError());
						return imagenComercial;
					}
				}
// Para PRUEBAS. Por ahora se comenta hasta que se corrija en el WS la manera de gestionar los errores.
//			     Cuando esté corregido en el WS se podrá descomentar este código (if) y la rama del ELSE.
//			} else {
//				imagenComercial.setCodError(new Integer(1));
//				imagenComercial.setDescripcionError(facingVegalsaResponse.getDescripcionRespuesta());
//				imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
//			}
			imagenComercial.setFlgPistola(null);
				
			logger.debug("Nombre usuario: "+user.getUserName()
						+ " Código Usuario: "+user.getCode()
						);
			imagenComercial.setUsuario(user.getCode());

			return imagenComercial;
		
		} catch (Exception e) {
		//	logger.error(StackTraceManager.getStackTrace(e));
			imagenComercial.setCodError(new Integer(1));
			if (facingVegalsaResponse != null){
				imagenComercial.setDescripcionError(facingVegalsaResponse.getDescripcionRespuesta());
			}else{
				imagenComercial.setDescripcionError("Error al guardar los datos del Facing.");
			}
			imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
			return imagenComercial;
		}
	}
		
	@RequestMapping(value="/loadUnidadesCaja", method = RequestMethod.POST)
	public @ResponseBody VSurtidoTienda loadUnidadesCaja(
			@RequestBody ReferenciasCentro referenciasCentro,
			HttpSession session, HttpServletResponse response) throws Exception {

		VSurtidoTienda vSurtidoTienda = this.obtenerSurtidoTienda(referenciasCentro);
		return vSurtidoTienda;
	}

	@RequestMapping(value="/loadDatosImagenComercialVentasMedias", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentroIC loadDatosImagenComercialVentasMedias(
			@RequestBody ReferenciasCentroIC vReferenciasCentroIC,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			ReferenciasCentroIC referenciasCentroIC = vReferenciasCentroIC;

			referenciasCentroIC.setHistoricoVentaMedia(obtenerHistoricoVentaMedia(referenciasCentroIC));

			return referenciasCentroIC;
		} catch (Exception e) {
			//		    logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value="/loadDatosImagenComercialVentasUltMes", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentroIC loadDatosImagenComercialVentasUltMes(
			@RequestBody ReferenciasCentroIC vReferenciasCentroIC,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			ReferenciasCentroIC referenciasCentroIC = vReferenciasCentroIC;

			//Comprobar si VentaPromocional
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(vReferenciasCentroIC.getCodArt());
			relacionArticulo.setCodCentro(vReferenciasCentroIC.getCodCentro());
			List<Long> referencias = this.relacionArticuloService.findAll(relacionArticulo);
			if (!referencias.isEmpty()){
				vReferenciasCentroIC.setPromocional("MP");
			} else {
				relacionArticulo.setCodArt(null);
				relacionArticulo.setCodArtRela(vReferenciasCentroIC.getCodArt());
				referencias = this.relacionArticuloService.findAll(relacionArticulo);
				if (!referencias.isEmpty()){
					vReferenciasCentroIC.setPromocional("P");
					referencias.clear();
				}
			}

			referenciasCentroIC.setListaHistoricoVentaUltimoMes(obtenerListaHistoricoVentaUltimoMes(referenciasCentroIC, referencias));

			return referenciasCentroIC;
		} catch (Exception e) {
			//		    logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value="/loadDatosImagenComercialSfm", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentroIC loadDatosImagenComercialSfm(
			@RequestBody ReferenciasCentroIC vReferenciasCentroIC,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			ReferenciasCentroIC referenciasCentroIC = vReferenciasCentroIC;

			referenciasCentroIC.setStockFinalMinimo(stockFinalMinimoService.obtenerStockFinalMinimo(referenciasCentroIC));

			return referenciasCentroIC;
		} catch (Exception e) {
			//		    logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value="/loadDatosImagenComercialFacing", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentroIC loadDatosImagenComercialFacing(
			@RequestBody ReferenciasCentroIC vReferenciasCentroIC,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			ReferenciasCentroIC referenciasCentroIC = vReferenciasCentroIC;

			referenciasCentroIC.setStockFinalMinimo(stockFinalMinimoService.obtenerStockFinalMinimo(referenciasCentroIC));

			return referenciasCentroIC;
		} catch (Exception e) {
			//		    logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value="/loadDatosImagenComercialPlanogramas", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentroIC loadDatosImagenComercialPlanogramas(
			@RequestBody ReferenciasCentroIC vReferenciasCentroIC,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {

			User user = (User)session.getAttribute("user");
			
			ReferenciasCentroIC referenciasCentroIC = vReferenciasCentroIC;

			PlanogramaVigente planogramaVigente = obtenerPlanogramaVigente(referenciasCentroIC);

			boolean buscadoEnKosmos = false;
			
			//Si no existe el planogramaVigente o la capacidad maxima lineal es nula y el stockminimocomercial es nulo, se consulta el planograma de kosmos.
			if (planogramaVigente == null
				|| (planogramaVigente.getCapacidadMaxLineal() == null && planogramaVigente.getStockMinComerLineal() == null)){
				planogramaVigente = obtenerPlanogramaKosmos(referenciasCentroIC);

				//Indicamos que se han buscado los datos en kosmos. MISUMI-100
				buscadoEnKosmos = true;
			}

			//Comprobación de si una referencia es FFPP 
			ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			referenciasCentro.setCodCentro(referenciasCentroIC.getCodCentro());
			referenciasCentro.setCodArt(referenciasCentroIC.getCodArt());
			boolean tratamientoVegalsaAux = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
			referenciasCentro = this.vRelacionArticuloService.obtenerFfppActivaOUnitaria(referenciasCentro, tratamientoVegalsaAux);

			//Control de FFPP para no mostrar el multiplicador
			referenciasCentroIC.setEsFFPP(referenciasCentro.isTieneUnitaria());

			if (planogramaVigente != null){
				//Comprobamos si se trata de un centro sólo de SFM o si es de Capacidad también.
				VDatosDiarioArt vDatosDiarioArt = obtenerDiarioArt(referenciasCentroIC.getCodArt());
				VAgruComerParamSfmcap vAgruCommerAgruComerParamSfmcap = new VAgruComerParamSfmcap();
				vAgruCommerAgruComerParamSfmcap.setCodCentro(referenciasCentroIC.getCodCentro());
				vAgruCommerAgruComerParamSfmcap.setNivel("I5");
				vAgruCommerAgruComerParamSfmcap.setGrupo1(vDatosDiarioArt.getGrupo1());
				vAgruCommerAgruComerParamSfmcap.setGrupo2(vDatosDiarioArt.getGrupo2());
				vAgruCommerAgruComerParamSfmcap.setGrupo3(vDatosDiarioArt.getGrupo3());
				vAgruCommerAgruComerParamSfmcap.setGrupo4(vDatosDiarioArt.getGrupo4());
				vAgruCommerAgruComerParamSfmcap.setGrupo5(vDatosDiarioArt.getGrupo5());
				List<VAgruComerParamSfmcap> listaAgruComerParamSfmcap = null;
				listaAgruComerParamSfmcap = this.vAgruComerParamSfmcapService.findAll(vAgruCommerAgruComerParamSfmcap, null);
				VAgruComerParamSfmcap estructuraArticulo = null;
				if (listaAgruComerParamSfmcap != null && listaAgruComerParamSfmcap.size()>0) {

					estructuraArticulo = listaAgruComerParamSfmcap.get(0);

					//Si cualquiera 
					boolean esPlanogramaCalculado = ( null != estructuraArticulo && 
							("S".equals(estructuraArticulo.getFlgFacingCapacidad())
									|| "S".equals(estructuraArticulo.getFlgCapacidad())
									|| "S".equals(estructuraArticulo.getFlgStockFinal())
									|| "S".equals(estructuraArticulo.getFlgFacing())));

					planogramaVigente.setEsPlanogramaCalculado(esPlanogramaCalculado);									

					boolean esFacingX = ( null != estructuraArticulo
										&& ("S".equals(estructuraArticulo.getFlgFacingCapacidad())
											|| "S".equals(estructuraArticulo.getFlgCapacidad())
											|| ("N".equals(estructuraArticulo.getFlgStockFinal())
												&& "N".equals(estructuraArticulo.getFlgFacing()))));

					planogramaVigente.setEsFacingX(esFacingX);

					//if (esFacingX){
					Long multiplicador = new Long(1);

					//Si es un FFPP no tiene sentido el multiplicador
					if (!referenciasCentro.isTieneUnitaria()){
						VFacingX vFacingXRes = new VFacingX();
						VFacingX vFacingX = new VFacingX();
						//vFacingX.setCodArticulo(referenciasCentroIC.getCodArt());
						vFacingX.setCodArticulo(vDatosDiarioArt.getCodFpMadre()); //Peticion 54867
						vFacingX.setCodCentro(referenciasCentroIC.getCodCentro());
						vFacingXRes = this.vFacingXService.findOne(vFacingX);

						if (vFacingXRes != null && vFacingXRes.getFacingX()!=null){
							multiplicador = vFacingXRes.getFacingX();
						}
					}
					planogramaVigente.setImc((long) (planogramaVigente.getStockMinComerLineal()*multiplicador));
					planogramaVigente.setMultiplicadorFac(new Integer(multiplicador.toString()));
					//}else{
					//	planogramaVigente.setImc(new Long(0));
					//	planogramaVigente.setMultiplicadorFac(1);
					//}

					VAgruComerParamSfmcap registro = new VAgruComerParamSfmcap();
					registro = listaAgruComerParamSfmcap.get(0);

					if (registro.getFlgCapacidad() != null && registro.getFlgCapacidad().equals("S")){
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
								(stockFinalMinimoRes.getCapacidad().compareTo(stockFinalMinimoSIARes.getCapacidad()) != 0)){
							//En este caso las capacidades son distintas y hay que rehacer los cálculos del lineal.

							//Obtenemos el porcentaje de la capacidad
							Float porcentajeCapacidad = null;
							CentroAutoservicio centroAutoservicio = new CentroAutoservicio();
							centroAutoservicio.setCodCentro(referenciasCentroIC.getCodCentro());
							List<CentroAutoservicio> listaCentroAutoservicio = null;
							listaCentroAutoservicio = this.paramCentrosVpService.findCentroAutoServicioAll(centroAutoservicio);

							if (listaCentroAutoservicio != null && listaCentroAutoservicio.size()>0){
								CentroAutoservicio centroAutoServicio = new CentroAutoservicio();
								centroAutoServicio = listaCentroAutoservicio.get(0);

								if (centroAutoServicio != null && centroAutoServicio.getPorcentajeCapacidad() != null){
									porcentajeCapacidad = centroAutoServicio.getPorcentajeCapacidad();
								}

							}

							Long nuevaCapacidad = new Long(1);
							Float nuevoFacing = new Float(1);
							if (stockFinalMinimoSIARes.getCapacidad() >1){
								nuevaCapacidad = new Long((long)stockFinalMinimoSIARes.getCapacidad().floatValue());
							}

							planogramaVigente.setCapacidadMaxLinealRecal(nuevaCapacidad);

							if (porcentajeCapacidad != null && nuevaCapacidad*porcentajeCapacidad >1){
								nuevoFacing = nuevaCapacidad*porcentajeCapacidad;
							}

							planogramaVigente.setStockMinComerLinealRecal(Float.valueOf((new DecimalFormat("###").format(nuevoFacing))));
							planogramaVigente.setRecalculado("SI");
						}
					}

				}else{

					planogramaVigente.setEsFacingX(true);

					Long multiplicador = new Long(1);

					//Si es un FFPP no tiene sentido el multiplicador
					if (!referenciasCentro.isTieneUnitaria()){
						VFacingX vFacingXRes = new VFacingX();
						VFacingX vFacingX = new VFacingX();
						//vFacingX.setCodArticulo(referenciasCentroIC.getCodArt());
						vFacingX.setCodArticulo(vDatosDiarioArt.getCodFpMadre()); //Peticion 54867
						vFacingX.setCodCentro(referenciasCentroIC.getCodCentro());
						vFacingXRes = this.vFacingXService.findOne(vFacingX);

						if (vFacingXRes != null && vFacingXRes.getFacingX()!=null){
							multiplicador = vFacingXRes.getFacingX();
						}
					}
					planogramaVigente.setImc((long) (planogramaVigente.getStockMinComerLineal()*multiplicador));
					planogramaVigente.setMultiplicadorFac(new Integer(multiplicador.toString()));

				}

				//Obtenemos de sesión el perfil del usuario.
				String esTecnico = "N";
//				User user = (User) session.getAttribute("user");
				if (user.getPerfil().toString().equals(this.tecnicRole) || user.getPerfil().toString().equals(this.adminRole)){
					//En este caso se trata de un perfil técnico
					esTecnico = "S";
				}
				planogramaVigente.setEsTecnico(esTecnico);
				
			}

			VPlanograma vPlanogramaTipoP = null;

			//Si no se ha buscado el tipoPlano, facingAlto, facingAncho y esCajaExp en kosmos,
			//se busca en V_PLANOGRAMAS. MISUMI-100.
			if(!buscadoEnKosmos){
				//Buscamos la altura y el ancho cuando el tipo de planograma es 'P'.
				vPlanogramaTipoP = new VPlanograma();
				vPlanogramaTipoP.setCodArt(vReferenciasCentroIC.getCodArt());
				vPlanogramaTipoP.setCodCentro(vReferenciasCentroIC.getCodCentro());
				vPlanogramaTipoP = vPlanogramaService.findTipoP(vPlanogramaTipoP);
			}else{
				if(planogramaVigente != null){
					//Buscamos la altura y el ancho cuando el tipo de planograma es 'P'.
					vPlanogramaTipoP = new VPlanograma();
					vPlanogramaTipoP.setCodArt(vReferenciasCentroIC.getCodArt());
					vPlanogramaTipoP.setCodCentro(vReferenciasCentroIC.getCodCentro());

					vPlanogramaTipoP.setTipoPlano(planogramaVigente.getTipoPlano());
					vPlanogramaTipoP.setFacingAlto(planogramaVigente.getFacingAlto());
					vPlanogramaTipoP.setFacingAncho(planogramaVigente.getFacingAncho());
					vPlanogramaTipoP.setEsCajaExp(planogramaVigente.getEsCajaExp());
				}
			}
			// Datos de VEGALSA. En este método rellena los datos de oferta y espacio promocional.
			planogramaVigente = rellenarDatosVegalsa(planogramaVigente, session);
			
			referenciasCentroIC.setvPlanogramaTipoP(vPlanogramaTipoP);
			referenciasCentroIC.setPlanogramaVigente(planogramaVigente);

			referenciasCentroIC.setStockFinalMinimo(stockFinalMinimoService.obtenerStockFinalMinimo(referenciasCentroIC));

			// Registrar si tiene tratamiento VEGALSA.
			boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), vReferenciasCentroIC.getCodArt());

			vReferenciasCentroIC.setTratamientoVegalsa(tratamientoVegalsa?1:0);

			referenciasCentroIC.setTratamientoVegalsa(vReferenciasCentroIC.isTratamientoVegalsa());
			
			//MISUMI-428
			MontajeVegalsa montajeVegalsa=tPedidoAdicionalService.getPedidosVegalsa(referenciasCentroIC.getCodCentro(), referenciasCentroIC.getCodArt());
			if(montajeVegalsa!=null){
				referenciasCentroIC.setCapacidadMontajeAdicionalCentro(montajeVegalsa.getCantidad().toString());
				referenciasCentroIC.setFechaInicioMontajeAdicionalCentro(Utilidades.formatearFecha_dd_MM_yyyy(montajeVegalsa.getFechaInicio()));
				referenciasCentroIC.setFechaFinMontajeAdicionalCentro(Utilidades.formatearFecha_dd_MM_yyyy(montajeVegalsa.getFechaFin()));
				referenciasCentroIC.setOfertaMontajeAdicionalCentro(montajeVegalsa.getOferta());
			}
			
			return referenciasCentroIC;
		} catch (Exception e) {
			//		    logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value="/loadFechaProximasEntregas", method = RequestMethod.POST)
	public @ResponseBody FechaProximaEntregaRef loadDatosImagenComercialNuevo(
			@RequestBody ReferenciasCentro vReferenciasCentro,
			HttpSession session, HttpServletResponse response) throws Exception {

		FechaProximaEntregaRef fechaProximaEntregaRef = fechaProximasEntregasService.getFechaProximasEntregas(vReferenciasCentro.getCodCentro(), vReferenciasCentro.getCodArt());
		return fechaProximaEntregaRef;
	}

	private PlanogramaVigente rellenarDatosVegalsa(PlanogramaVigente planogramaVigente, HttpSession session) throws Exception {
		PlanogramaVigente output = planogramaVigente;
		User user = (User) session.getAttribute("user");
		Long codSoc = user.getCentro().getCodSoc();
		// Si es un centro Vegalsa (cod_soc = 13)
		if (codSoc == 13){
			output = vPlanogramaService.findDatosVegalsa(planogramaVigente);
		}
	
		return output;
	}

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

	
	private VSurtidoTienda obtenerSurtidoTienda(ReferenciasCentro referenciasCentro) throws Exception{
		VSurtidoTienda vSurtidoTiendaRes;
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		vSurtidoTienda.setCodArt(referenciasCentro.getCodArt());
		vSurtidoTienda.setCodCentro(referenciasCentro.getCodCentro());

		if (referenciasCentro.isEsTratamientoVegalsa()){
			vSurtidoTiendaRes = this.vSurtidoTiendaService.findOneVegalsa(vSurtidoTienda);
		} else {
			vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

			//Calcular campo mapaHoy
			vSurtidoTiendaRes = asignarMapaHoy(referenciasCentro, vSurtidoTiendaRes);
		}
		
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

	private VMapaReferencia obtenerMapaReferencia(ReferenciasCentro referenciasCentro) throws Exception{
		
		Long codArt = referenciasCentro.getCodArt();
		
		boolean existMapaRef = this.vMapaReferenciaService.existsMapa(codArt);
		
		VMapaReferencia vMapaReferencia = null;
		if (existMapaRef){
			vMapaReferencia = this.vMapaReferenciaService.findMapa(codArt);
		}

		return vMapaReferencia;
	}

	
	public VDatosDiarioArt obtenerDatosDiarioArt(ReferenciasCentro referenciasCentro) throws Exception{
		VDatosDiarioArt vDatosDiarioArtRes = null;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();

		vDatosDiarioArt.setCodArt(referenciasCentro.getCodArt());
		vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

		return vDatosDiarioArtRes;
	}

	private VSurtidoTienda asignarMapaHoy(ReferenciasCentro referenciasCentro, VSurtidoTienda surtidoTienda) throws Exception{

		//Se cogen los valores de SurtidoTienda del objeto actualizado.
		//En referenciasCentro los datos de SurtidoTienda no están todavía actualizados.
		VSurtidoTienda vSurtidoTiendaRes = surtidoTienda;
		VDatosDiarioArt vDatosDiarioArt = referenciasCentro.getDiarioArt();
		VMapaAprovFestivo vMapaAprovFestivo = new VMapaAprovFestivo();
		VMapaAprov vMapaAprov = new VMapaAprov();

		String mapaHoy = "";
		Long estLogN1;
		Long estLogN2;
		Long estLogN3;
		Long numeroPedidos = new Long(0);
		Long pedLun, pedMar, pedMie, pedJue, pedVie, pedSab, pedDom;
		Date fechaHoy = new Date();
		GregorianCalendar diaCalendario = new GregorianCalendar();
		diaCalendario.setTime(fechaHoy);
		diaCalendario.setFirstDayOfWeek(Calendar.MONDAY);
		int diaSemana = diaCalendario.get(Calendar.DAY_OF_WEEK);

		if (surtidoTienda != null && vDatosDiarioArt != null){
			//Obtención de estructura logística
			estLogN1 = vDatosDiarioArt.getEstlogN1();	
			estLogN2 = vDatosDiarioArt.getEstlogN2();	
			estLogN3 = vDatosDiarioArt.getEstlogN3();	

			//S?lo se calcula el mapaHoy en caso de aprovisionamiento Grupaje o Centralizado
			String tipoAprovisionamiento = surtidoTienda.getTipoAprov();

			//Si es un centro de Caprabo no debe accederse a los mapas
			//		if (!utilidadesCapraboService.esCentroCaprabo(referenciasCentro.getCodCentro()) && tipoAprovisionamiento != null && !utilidadesCapraboService.esCentroCapraboEspecial(referenciasCentro.getCodCentro()) &&
			//				(tipoAprovisionamiento.equals(this.tipoAprovisionamientoCentralizado) || 
			//						tipoAprovisionamiento.equals(this.tipoAprovisionamientoDescentralizado) ||
			//						tipoAprovisionamiento.equals(this.tipoAprovisionamientoGrupaje))){

			//B?squeda de festivo
			//B?squeda por art?culo
			vMapaAprovFestivo.setCodCentro(referenciasCentro.getCodCentro());
			vMapaAprovFestivo.setCodArt(referenciasCentro.getCodArt());
			vMapaAprovFestivo.setFechaCambio(fechaHoy);
			numeroPedidos = this.vMapaAprovFestivoService.count(vMapaAprovFestivo);
			if (numeroPedidos > 0){
				mapaHoy = "S";
			}else{

				//B?squeda por estructura log?stica
				vMapaAprovFestivo.setCodArt(null);
				vMapaAprovFestivo.setCodN1(estLogN1.toString());
				vMapaAprovFestivo.setCodN2(estLogN2.toString());
				vMapaAprovFestivo.setCodN3(estLogN3.toString());
				numeroPedidos = this.vMapaAprovFestivoService.count(vMapaAprovFestivo);
				if (numeroPedidos > 0){
					mapaHoy = "S";
				}else{

					//B?squeda por mapa normal
					//B?squeda por art?culo
					vMapaAprov.setCodCentro(referenciasCentro.getCodCentro());
					vMapaAprov.setCodArt(referenciasCentro.getCodArt());
					numeroPedidos = this.vMapaAprovService.count(vMapaAprov);
					if (numeroPedidos ==0){

						//B?squeda por estructura log?stica
						vMapaAprov.setCodArt(null);
						vMapaAprov.setCodN1(estLogN1.toString());
						vMapaAprov.setCodN2(estLogN2.toString());
						vMapaAprov.setCodN3(estLogN3.toString());
						numeroPedidos = this.vMapaAprovService.count(vMapaAprov);
						if (numeroPedidos == 0){
							//No existe registro
							mapaHoy = "N";
						}
					}

					if ("".equals(mapaHoy)){ //No se ha tratado el resultado del mapa. Existe alg?n pedido.
						vMapaAprov = this.vMapaAprovService.findOne(vMapaAprov);
						//Recarga de pedidos diarios
						pedLun = vMapaAprov.getPedLun();
						pedMar = vMapaAprov.getPedMar();
						pedMie = vMapaAprov.getPedMie();
						pedJue = vMapaAprov.getPedJue();
						pedVie = vMapaAprov.getPedVie();
						pedSab = vMapaAprov.getPedSab();
						pedDom = vMapaAprov.getPedDom();

						if (pedLun==0 && pedMar==0 && pedMie==0 && pedJue==0 && pedVie==0 && pedSab==0 && pedDom==0){
							mapaHoy = "N";
							numeroPedidos = new Long(0);
						}else{//Cambio 13/11/2013 En caso de que haya alguna cantidad distinta de 0 mapa de hoy será S
							mapaHoy = "S";

							//Existe alg�n pedido alg�n d�a
							//Control del d�a de la semana
							//Existe alg?n pedido alg?n d?a
							//Control del d?a de la semana
							/*switch (diaSemana) {
								case 1: //Lunes
									if(pedLun > 0){
										mapaHoy = "S";
										break;	
									}
								case 2: //Martes
									if(pedMar > 0){
										mapaHoy = "S";
										break;	
									}
								case 3: //Mi�rcoles

									if(pedMie > 0){
										mapaHoy = "S";
										break;	
									}
								case 4: //Jueves
									if(pedJue > 0){
										mapaHoy = "S";
										break;	
									}
								case 5: //Viernes
									if(pedVie > 0){
										mapaHoy = "S";
										break;	
									}

								case 6: //S?bado
									if(pedSab > 0){
										mapaHoy = "S";
										break;	
									}
								case 7: //Domingo
									if(pedDom > 0){
										mapaHoy = "S";
										break;	
									}
								default:
									mapaHoy = "N";
									break;
								}*/

						}
					}
				}
			}
			//}

			vSurtidoTiendaRes.setMapaHoy(mapaHoy);
			vSurtidoTiendaRes.setNumeroPedidosOtroDia(numeroPedidos);
		}

		return vSurtidoTiendaRes;
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

	private PedidoAdicionalContadores obtenerPedidoAdicional(
			TPedidoAdicional tPedidoAdicional,HttpSession session, HttpServletResponse response) throws Exception {

		PedidoAdicionalContadores resultado = null;
		PedidoAdicionalContadores resultadoCarga = null;

		try {


			PedidoAdicionalE pedidoAdicionalE = new PedidoAdicionalE();

			pedidoAdicionalE.setCodArticulo(tPedidoAdicional.getCodArticulo());
			pedidoAdicionalE.setCodCentro(tPedidoAdicional.getCodCentro());

			//Se calculan los ditintos contadores sin MAC
			pedidoAdicionalE.setMca("N");
			resultadoCarga = this.pedidoAdicionalService.loadContadores(pedidoAdicionalE, session, response);

			Long totalEncargos = resultadoCarga.getContadorEncargos();
			Long totalMontajeAdicional = resultadoCarga.getContadorMontaje() + resultadoCarga.getContadorMontajeOferta() + resultadoCarga.getContadorValidarCantExtra();


			//Se calculan los ditintos contadores con MAC
			pedidoAdicionalE.setMca("S");
			resultadoCarga = this.pedidoAdicionalService.loadContadores(pedidoAdicionalE, session, response);

			Long totalMAC = resultadoCarga.getContadorMontaje() + resultadoCarga.getContadorMontajeOferta() + resultadoCarga.getContadorEmpuje();

			// Clase pedidos 3 de perfil central
			if (totalEncargos != 0 || totalMontajeAdicional != 0
					|| totalMAC != 0) {
				resultado = new PedidoAdicionalContadores();
				resultado.setContadorEncargos(totalEncargos);
				resultado.setContadorMontaje(totalMontajeAdicional);
				resultado.setContadorMAC(totalMAC);
				resultado.setCodError(new Long(0));

			}else{
				resultado = new PedidoAdicionalContadores();
				resultado.setContadorEncargos(totalEncargos);
				resultado.setContadorMontaje(totalMontajeAdicional);
				resultado.setContadorMAC(totalMAC);
				resultado.setCodError(new Long(0));
			}

		} catch (Exception e) {
			Locale locale = LocaleContextHolder.getLocale();
			resultado = new PedidoAdicionalContadores();
			resultado.setCodError(new Long(1));
			resultado.setDescError(this.messageSource.getMessage("p13_referenciasCentro.errorPedidoAdicionales",null, locale));

		}
		return resultado;
	}

	private List<HistoricoVentaUltimoMes> obtenerListaHistoricoVentaUltimoMes(
			ReferenciasCentroIC referenciasCentroIC, List<Long> referencias) throws Exception {

		List<HistoricoVentaUltimoMes> listaHistoricoVentaUltimoMesRes;
		HistoricoVentaUltimoMes historicoVentaUltimoMes = new HistoricoVentaUltimoMes();
		historicoVentaUltimoMes.setCodArticulo(referenciasCentroIC.getCodArt());
		historicoVentaUltimoMes.setCodLoc(referenciasCentroIC.getCodCentro());
		if (!referencias.isEmpty()){
			historicoVentaUltimoMes.setReferencias(referencias);
		}

		listaHistoricoVentaUltimoMesRes = this.historicoVentaUltimoMesService.findAllLastDays(historicoVentaUltimoMes);

		return listaHistoricoVentaUltimoMesRes;
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
		User user = (User) session.getAttribute("user");

		try {
			ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			referenciasCentro.setCodArt(pendientesRecibirEntrada.getCodArt());
			referenciasCentro.setCodCentro(pendientesRecibirEntrada.getCodCentro());
			// Miramos si tiene tratamiento Vegalsa
			boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
			referenciasCentro.setEsTratamientoVegalsa(tratamientoVegalsa);
			
			VSurtidoTienda vSurtidoTienda = this.obtenerSurtidoTienda(referenciasCentro);

			pendientesRecibir = this.vPendientesRecibirService.find(pendientesRecibirEntrada);
			if (vSurtidoTienda != null) {
				pendientesRecibir.setTipoAprov(vSurtidoTienda.getTipoAprov());
			}
		} catch (Exception e) {
			logger.error("######################## Error: pendientesRecibir  ############################");
			logger.error("Referencia: " + pendientesRecibirEntrada.getCodArt() );
			logger.error("############################################################");

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
	public @ResponseBody ReferenciasCentroIC loadPlanogramaWS(@RequestBody ReferenciasCentro vReferenciasCentro
															 , HttpSession session, HttpServletResponse response) throws Exception {
		
		ReferenciasCentroIC  referenciasCentroIC = new ReferenciasCentroIC();
		User user = (User) session.getAttribute("user");
		try{

//			boolean esCentroCaprabo = utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode());
//			boolean esCentroCapraboEspecial = utilidadesCapraboService.esCentroCapraboEspecial(user.getCentro().getCodCentro(), user.getCode());

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

				//if (!esCentroCaprabo && !esCentroCapraboEspecial) {
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
	
	@RequestMapping(value="/loadMotivosFromWS", method = RequestMethod.POST)
	public @ResponseBody String loadMotivosFromWS(
			@RequestBody ReferenciasCentro vReferenciasCentro,
			HttpSession session, HttpServletResponse response) throws Exception {
		List<Motivo> listMotGeneral = new ArrayList<Motivo>();
		List<Motivo> listMotMMC = new ArrayList<Motivo>();
		String result= "";
		String pedible="";
		try {
			ValidarReferenciasResponseType resultado= this.pedidosCentroService.findPedidosCentroWS(vReferenciasCentro);
			if (!resultado.getCodigoRespuesta().equals("0")){
				//error
				result = resultado.getCodigoRespuesta();
			}else{
				ReferenciasValidadasType resultList= resultado.getReferenciasValidadas(0);
				pedible=resultList.getPedible();
				if (!resultList.getPedible().equals("S")){
					List<MotivosType> listAux = new ArrayList<MotivosType>();
					listAux = Arrays.asList(resultList.getMotivos());
					for (int i=0;i<listAux.size();i++){
						MotivosType mot = listAux.get(i);
						String motivoAccion=mot.getAccion();
						if (mot.getAccion()==null){
							motivoAccion="";
						}
						VDatosDiarioArt vDatosDiarioArtRes = this.obtenerDatosDiarioArt(vReferenciasCentro);
						if(new Long(Constantes.AREA_ALIMENTACION).equals(vDatosDiarioArtRes.getGrupo1())
								|| new Long(Constantes.AREA_BAZAR).equals(vDatosDiarioArtRes.getGrupo1())
								|| new Long(Constantes.AREA_FRESCOS).equals(vDatosDiarioArtRes.getGrupo1())
								|| new Long(Constantes.AREA_TEXTIL).equals(vDatosDiarioArtRes.getGrupo1()) 
								|| new Long(Constantes.AREA_ELECTRO).equals(vDatosDiarioArtRes.getGrupo1())){
							VSurtidoTienda vSurtidoTienda = this.obtenerSurtidoTienda(vReferenciasCentro);

							if(vSurtidoTienda != null) {
								if(("B").equals(vSurtidoTienda.getCatalogo())){
									String empuje = plataformaService.estaEnModoEmpuje(vReferenciasCentro.getCodCentro(), vReferenciasCentro.getCodArt());
									if(("S").equals(empuje)){
										Locale locale = LocaleContextHolder.getLocale();
										if(new Long(Constantes.AREA_ELECTRO).equals(vDatosDiarioArtRes.getGrupo1())){
											if((messageSource.getMessage("p13_referenciasCentro.msgWs.modoEmpuje.msg1", null, locale).equals(mot.getDescripcion()))
													||(messageSource.getMessage("p13_referenciasCentro.msgWs.modoEmpuje.msg2", null, locale).equals(mot.getDescripcion()))){
												mot.setDescripcion(messageSource.getMessage("p13_referenciasCentro.modoEmpuje.area.electro", null, locale));
											}
										}else if(new Long(Constantes.AREA_ALIMENTACION).equals(vDatosDiarioArtRes.getGrupo1())
												|| new Long(Constantes.AREA_BAZAR).equals(vDatosDiarioArtRes.getGrupo1())
												|| new Long(Constantes.AREA_FRESCOS).equals(vDatosDiarioArtRes.getGrupo1())
												|| new Long(Constantes.AREA_TEXTIL).equals(vDatosDiarioArtRes.getGrupo1())){
											if((messageSource.getMessage("p13_referenciasCentro.msgWs.modoEmpuje.msg1", null, locale).equals(mot.getDescripcion()))
													||(messageSource.getMessage("p13_referenciasCentro.msgWs.modoEmpuje.msg2", null, locale).equals(mot.getDescripcion()))){
												mot.setDescripcion(messageSource.getMessage("p13_referenciasCentro.modoEmpuje.area.otros", null, locale));
											}
										}
									}
								}

							}

						}
						if (mot.getTipo().equals("GENERAL")){
							listMotGeneral.add(new Motivo("S",new TextoMotivo(mot.getDescripcion(),motivoAccion),"S"));
						}else{
							listMotMMC.add(new Motivo("S",new TextoMotivo(mot.getDescripcion(),motivoAccion),"S"));
						}
					}
				}
			}
			//result
		}	catch (Exception e) {

			session.setAttribute("listMotGeneral" + vReferenciasCentro.getOrigenPantalla(),  new ArrayList<Motivo>());
			session.setAttribute("listMotMMC" + vReferenciasCentro.getOrigenPantalla(),  new ArrayList<Motivo>());

			HashMap<Long,String> mapPedible = (HashMap<Long, String>) session.getAttribute("pedible");
			if(mapPedible != null){
				mapPedible.put(vReferenciasCentro.getCodArt(), null);
			}else{
				mapPedible = new HashMap<Long, String>();
				mapPedible.put(vReferenciasCentro.getCodArt(), null);
			}

			session.setAttribute("pedible", mapPedible);
			session.setAttribute("performed", "F");
			result="-1";
			// resultado = messageSource.getMessage("p15_referenciasCentroIC.webServiceNotWorking", null, locale);
		}
		session.setAttribute("listMotGeneral" + vReferenciasCentro.getOrigenPantalla(), listMotGeneral);
		session.setAttribute("listMotMMC" + vReferenciasCentro.getOrigenPantalla(), listMotMMC);

		//Obtenemos el HashMap de pedible y se inserta el nuevo valor. Si no existe se crea uno nuevo con la nueva referencia.
		//Esto se hace, porque puede que se esté utilizando el buscador universal a la vez en dos sitios de la aplicación.
		//Por ejemplo, metemos la referencia 54015 en Consutla datos referencia. Pedible es N. Metemos en informacion basica la referencia
		//1222 y obtiene pedible = null, por lo que machaca el pedible = N de 54015. Vas a ver por qué no está activa la referencia
		//54015, pero el valor de pedible es el de 1222, por lo que casca. Con un hashMap se soluciona el problema.
		HashMap<Long,String> mapPedible = (HashMap<Long, String>) session.getAttribute("pedible");
		if(mapPedible != null){
			mapPedible.put(vReferenciasCentro.getCodArt(), pedible);
		}else{
			mapPedible = new HashMap<Long, String>();
			mapPedible.put(vReferenciasCentro.getCodArt(), pedible);
		}
		session.setAttribute("pedible", mapPedible);
		session.setAttribute("performed", "F");
		return result;

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

		Page<Motivo> result = null;

		User user = (User) session.getAttribute("user");

		//if ((utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro())) && !(utilidadesCapraboService.esCentroCapraboNuevo(user.getCentro().getCodCentro()))){
		if (false){
			result = loadMotivosNoActivaCaprabo(motivo, page, max, index, sortOrder, false,utilidadesCapraboService.esCentroCapraboNuevo(user.getCentro().getCodCentro(),user.getCode()), session, response);
		}else{

			//if ((utilidadesCapraboService.esCentroCapraboEspecial(user.getCentro().getCodCentro())) && !(utilidadesCapraboService.esCentroCapraboNuevo(user.getCentro().getCodCentro()))){ //Si es un caprabo especial, para obtener los motivos debera llamar al procedimiento, igual que los centros Caprabo
			if (false){ //Si es un caprabo especial, para obtener los motivos debera llamar al procedimiento, igual que los centros Caprabo
				result = loadMotivosNoActivaCaprabo(motivo, page, max, index, sortOrder, true, utilidadesCapraboService.esCentroCapraboNuevo(user.getCentro().getCodCentro(), user.getCode()), session, response);
			} else {
				result = loadMotivosNoActivaEroski(motivo, page, max, index, sortOrder, session, response);
			}
		}

		return result;
	}

	public Page<Motivo> loadMotivosNoActivaEroski(
			Motivo motivo,
			Long page,
			Long max,
			String index,
			String sortOrder,
			HttpSession session, HttpServletResponse response) throws Exception {

		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}

		List<Motivo> listaMotivosWebService =new ArrayList<Motivo>();

		listaMotivosWebService = (List<Motivo>) session.getAttribute("listMotGeneral" + motivo.getOrigenPantalla());
		String performed=(String) session.getAttribute("performed");

		List<Motivo> listaRecarga = new ArrayList<Motivo>();

		String mapaHoy = motivo.getMapaHoy(); 
		String pedir = motivo.getPedir();
		HashMap<Long, String> pedibleHash =  (HashMap<Long, String>) session.getAttribute("pedible");
		String pedible = pedibleHash.get(motivo.getCodArt());
		Boolean textilPedible = (Boolean) session.getAttribute("textilPedible");

		List<Motivo> listaMotivosMapaHoy = new ArrayList<Motivo>();
		List<Motivo> listaMotivosTextoPedible = new ArrayList<Motivo>();
		List<Motivo> listaMotivos = new ArrayList<Motivo>();
		Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
		Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;

		User user = (User) session.getAttribute("user");
		try {
			Motivo motivo1 = new Motivo();
			Motivo motivo2 = new Motivo();

			//preguntar por pedir
			if (performed!=null && performed.equals("F")){

				//Peticion 48922. Si PEDIR es igual a N  y es Baja catalogo (V_SURTIDO_TIENDA.CATALOGO=B) 
				//Peticion 148.  NO mostrar el mensaje para referencias DESCENTRALIZADAS
				ReferenciasCentro referenciasCentro = new ReferenciasCentro();
				referenciasCentro.setCodArt(motivo.getCodArt());
				referenciasCentro.setCodCentro(motivo.getCodCentro());
				// Calculamos si tiene tratamiento para Vegalsa
				referenciasCentro.setEsTratamientoVegalsa(utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt()));
				
				VSurtidoTienda vSurtidoTienda = this.obtenerSurtidoTienda(referenciasCentro);

				if (pedir.toUpperCase().equals("S") && !(referenciasCentro.isEsTratamientoVegalsa())){
					//NO mostrar el mensaje del motivo para referencias DESCENTRALIZADAS
					if(!(this.tipoAprovisionamientoDescentralizado.equals(vSurtidoTienda != null ? (vSurtidoTienda.getTipoAprov() != null ? vSurtidoTienda.getTipoAprov() : "C"):"C"))){						
						Locale locale = LocaleContextHolder.getLocale();

						//El motivo variará si es un hiper u otro tipo de centro.
						String motivoMulti;
						if((Constantes.CENTRO_NEGOCIO_HIPER).equals(user.getCentro().getNegocio())){
							if((vSurtidoTienda != null ? (vSurtidoTienda.getTipoAprov() != null ? vSurtidoTienda.getTipoAprov() : "C"):"C").equals(this.tipoAprovisionamientoGrupaje)){
								motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsoc", null, locale);								
							}else{
								motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
							}
						}else{
							motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
						}

						TextoMotivo textoMotivo1 = new TextoMotivo(motivoMulti, "");

						motivo1.setMapaHoy(mapaHoy);
						motivo1.setPedir(pedir);
						motivo1.setMotivoWebservice("N");
						motivo1.setTextoMotivo(textoMotivo1);
						motivo1.setTipoMensaje("SF"); //sin formato
						listaMotivos.add(motivo1);
						session.setAttribute("listMotGeneral" + motivo.getOrigenPantalla(), listaMotivos);
						session.setAttribute("listMotMMC" + motivo.getOrigenPantalla(),  new ArrayList<Motivo>());
					}
				}else{
					//Preguntamos por pedible. MISUMI-239 - Si es null equivale a N
					if (pedible != null && pedible.toUpperCase().equals("S")){
						//pedible="S"
						//Preguntamos por Mapa hoy
						if (mapaHoy.toUpperCase().equals("S")){
							Locale locale = LocaleContextHolder.getLocale();
							String motivoMulti =  this.messageSource.getMessage("p18_referenciasCentroPopupPedir.manianaAct", null, locale);

							TextoMotivo textoMotivo1 = new TextoMotivo(motivoMulti, "");
							motivo1.setMapaHoy(mapaHoy);
							motivo1.setPedir(pedir);
							motivo1.setMotivoWebservice("N");
							motivo1.setTextoMotivo(textoMotivo1);
							motivo1.setTipoMensaje("NS"); //sin formato
							listaMotivos.add(motivo1);
							session.setAttribute("listMotGeneral" + motivo.getOrigenPantalla(), listaMotivos);
							session.setAttribute("listMotMMC" + motivo.getOrigenPantalla(),  new ArrayList<Motivo>());

						}else{

							//NO mostrar el mensaje del motivo para referencias DESCENTRALIZADAS
							if(!(this.tipoAprovisionamientoDescentralizado.equals(vSurtidoTienda != null ? (vSurtidoTienda.getTipoAprov() != null ? vSurtidoTienda.getTipoAprov() : "C"):"C"))){
								Locale locale = LocaleContextHolder.getLocale();

								//El motivo variará si es un hiper u otro tipo de centro.
								String motivoMulti;
								if((Constantes.CENTRO_NEGOCIO_HIPER).equals(user.getCentro().getNegocio())){
									if((vSurtidoTienda != null ? (vSurtidoTienda.getTipoAprov() != null ? vSurtidoTienda.getTipoAprov() : "C"):"C").equals(this.tipoAprovisionamientoGrupaje)){
										motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsoc", null, locale);								
									}else{
										motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
									}
								}else{
									motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
								}

								TextoMotivo textoMotivo1 = new TextoMotivo(motivoMulti, "");
								motivo1.setMapaHoy(mapaHoy);
								motivo1.setPedir(pedir);
								motivo1.setMotivoWebservice("N");
								motivo1.setTextoMotivo(textoMotivo1);
								motivo1.setTipoMensaje("SF"); //sin formato
								listaMotivos.add(motivo1);
								session.setAttribute("listMotGeneral" + motivo.getOrigenPantalla(), listaMotivos);
								session.setAttribute("listMotMMC" + motivo.getOrigenPantalla(),  new ArrayList<Motivo>());
							}
						}
					}else{
						//pedible="N"
						if (mapaHoy.toUpperCase().equals("N") && !referenciasCentro.isEsTratamientoVegalsa()){
							//NO mostrar el mensaje del motivo para referencias DESCENTRALIZADAS
							if(!(this.tipoAprovisionamientoDescentralizado.equals(vSurtidoTienda != null ? (vSurtidoTienda.getTipoAprov() != null ? vSurtidoTienda.getTipoAprov() : "C"):"C"))){
								Locale locale = LocaleContextHolder.getLocale();

								//El motivo variará si es un hiper u otro tipo de centro.
								String motivoMulti;
								if((Constantes.CENTRO_NEGOCIO_HIPER).equals(user.getCentro().getNegocio())){
									if((vSurtidoTienda != null ? (vSurtidoTienda.getTipoAprov() != null ? vSurtidoTienda.getTipoAprov() : "C"):"C").equals(this.tipoAprovisionamientoGrupaje)){
										motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsoc", null, locale);								
									}else{
										motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
									}
								}else{
									motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
								}

								TextoMotivo textoMotivo1 = new TextoMotivo(motivoMulti, "");
								motivo1.setMapaHoy(mapaHoy);
								motivo1.setPedir(pedir);
								motivo1.setMotivoWebservice("N");
								motivo1.setTextoMotivo(textoMotivo1);
								motivo1.setTipoMensaje("SF"); //sin formato
								listaMotivosMapaHoy.add(motivo1);
							}
						}
						//Compruebo si es textil pedible para añadir un nuevo motivo
						if (textilPedible != null && textilPedible.booleanValue()) {
							Locale locale = LocaleContextHolder.getLocale();
							String texto1 =  this.messageSource.getMessage("p18_referenciasCentroPopupPedir.textilPedible.texto1", null, locale);
							String texto2 =  this.messageSource.getMessage("p18_referenciasCentroPopupPedir.textilPedible.texto2", null, locale);
							TextoMotivo textoMotivo2 = new TextoMotivo(texto1, texto2);
							motivo2.setMapaHoy(mapaHoy);
							motivo2.setPedir(pedir);
							motivo2.setMotivoWebservice("N");
							motivo2.setTextoMotivo(textoMotivo2);
							motivo2.setTipoMensaje(""); //sin formato
							listaMotivosTextoPedible.add(motivo2);
						}
						//Se unen las listas de motivos para mostrarlas como sólo una lista de motivos con el siguiente orden:
						//	1 - lista de motivos de mapas
						//	2 - lista de motivos de textil pedible
						//	3 - lista de motivos de web service
						if (listaMotivosMapaHoy.size() > 0) {
							listaMotivos.addAll(listaMotivosMapaHoy);
						}
						if (listaMotivosTextoPedible.size() > 0) {
							listaMotivos.addAll(listaMotivosTextoPedible);
						}
						if (listaMotivosWebService.size() > 0) {
							listaMotivos.addAll(listaMotivosWebService);
						}
						
						
						if (listaMotivos.size() == 0 && referenciasCentro.isEsTratamientoVegalsa()) {
							//Si la lista de motivos esta vacia en el caso de VEGALSA, añadimos el mensaje "Datos NO activos"
							Locale locale = LocaleContextHolder.getLocale();
							String motivoMulti =  this.messageSource.getMessage("p18_referenciasCentroPopupPedir.sinDatos", null, locale);
							TextoMotivo textoMotivoManAct = new TextoMotivo(motivoMulti, "");
							Motivo motivoManAct = new Motivo();
							motivoManAct.setMotivoWebservice("N");
							motivoManAct.setTextoMotivo(textoMotivoManAct);
							motivoManAct.setTipoMensaje("NS"); //sin formato
							listaMotivos.add(motivoManAct);
						}
						
						session.setAttribute("listMotGeneral" + motivo.getOrigenPantalla(), listaMotivos);


					}

					if ((pedir != null && null != vSurtidoTienda && pedir.toUpperCase().equals("N")) && "B".equals(vSurtidoTienda.getCatalogo())) {

						//LLAMAR AL PROCEDIMIENTO PK_APR_MISUMI.ESTADOS_CONVERGENCIA
						EstadoConvergencia estadoConvergencia = new EstadoConvergencia(referenciasCentro.getCodArt(), 
								referenciasCentro.getCodCentro());

						String resultadoEstadoConvergencia = (String) estadoConvergenciaService.consultaEstadoConvergencia(estadoConvergencia);		
						//String resultadoEstadoConvergencia = "Mensaje de estado de convergencia";

						if  ((resultadoEstadoConvergencia != null) && (!resultadoEstadoConvergencia.equals(""))) {
							//Obtener listaMotMCC de session, informar con el nuevo mensaje que ha devuelto el procedimiento y volver a meterlo a session
							List<Motivo> listMotMMC = (List<Motivo>) session.getAttribute("listMotMMC" + motivo.getOrigenPantalla());
							listMotMMC.add(new Motivo("N",new TextoMotivo(resultadoEstadoConvergencia,""),"N"));
							session.setAttribute("listMotMMC" + motivo.getOrigenPantalla(), listMotMMC);
						}
					}

				}
				session.setAttribute("performed", "T");
			}else{
				listaMotivos = listaMotivosWebService;
			}

			//////fin datos de prueba/////////////////////////////////////////////////

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

	public Page<Motivo> loadMotivosNoActivaCaprabo(
			Motivo motivo,
			Long page,
			Long max,
			String index,
			String sortOrder,
			boolean esCapraboEspecial,
			boolean esCapraboNuevo,
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

			List<Motivo> listaMotivosWebService =new ArrayList<Motivo>();
			listaMotivosWebService = (List<Motivo>) session.getAttribute("listMotGeneral" + motivo.getOrigenPantalla());

			String performed=(String) session.getAttribute("performed");


			ReferenciasCentro referenciasCentro = new ReferenciasCentro();


			referenciasCentro.setCodArt(motivo.getCodArt());
			referenciasCentro.setCodArtEroski(motivo.getCodArt());
			referenciasCentro.setCodArtCaprabo(utilidadesCapraboService.obtenerCodigoCaprabo(motivo.getCodCentro(), motivo.getCodArt()));
			referenciasCentro.setCodCentro(motivo.getCodCentro());



			if (performed!=null && performed.equals("F")){


				//Se consulta V_SURTIDO_TIENDA filtrando por COD_CENTRO y COD_ART_CAPRABO
				referenciasCentro.setSurtidoTienda(obtenerSurtidoTienda(referenciasCentro));

				//Se consulta T_MIS_MCG_CAPRABO filtrando por COD_CENTRO y COD_ART_CAPRABO
				referenciasCentro.settMisMcgCaprabo(obtenerMcgCaprabo(referenciasCentro));

				//Obtención de motivo de T_MIS_MCG_CAPRABO
				if (referenciasCentro.gettMisMcgCaprabo()!=null && referenciasCentro.gettMisMcgCaprabo().getMotivo()!=null){
					referenciasCentro.setMotivoCaprabo(referenciasCentro.gettMisMcgCaprabo().getMotivo());
					Motivo motivo1 = new Motivo();

					TextoMotivo textoMotivo1;
					//Si el centro es caprabo especial, cambiamos el código del mensaje caprabo por uno eroski. MISUMI - 30
					if(esCapraboEspecial){
						if(motivo.getCodArtSustituidaPorEroski() != null && motivo.getCodArtSustituidaPorEroski().toString() != ""){
							String textoMotivoNoActivoCpbEspecial = utilidadesCapraboService.obtenerMotivoCapraboEspecial(referenciasCentro.gettMisMcgCaprabo().getMotivo(),utilidadesCapraboService.obtenerCodigoCaprabo(motivo.getCodCentro(),motivo.getCodArtSustituidaPorEroski()).toString(),motivo.getCodArtSustituidaPorEroski().toString());
							textoMotivo1 = new TextoMotivo(textoMotivoNoActivoCpbEspecial, "");
						}else{
							textoMotivo1 = new TextoMotivo(referenciasCentro.gettMisMcgCaprabo().getMotivo(), "");	
						}
					}else{
						textoMotivo1 = new TextoMotivo(referenciasCentro.gettMisMcgCaprabo().getMotivo(), "");					
					}				
					motivo1.setMapaHoy(mapaHoy);
					motivo1.setPedir(pedir);
					motivo1.setMotivoWebservice("N");
					motivo1.setTextoMotivo(textoMotivo1);
					motivo1.setTipoMensaje("SF"); //sin formato
					listaMotivos.add(motivo1);
				}

				if (!(esCapraboNuevo)){ //SI ES UN CENTRO CAPRABO NUEVO, NO LLAMAMOS AL PROCEDIMIENTO. 

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

				} else {

					//Obtenemos los motivos del WS


					if (listaMotivosWebService.size() > 0) {
						listaMotivos.addAll(listaMotivosWebService);
					}



					if (listaMotivos.size() == 0) {
						//Si la lista de motivos esta vacia en el caso de CAPRABO NUEVO, añadimos el mensaje "Mañana estará activa"
						Locale locale = LocaleContextHolder.getLocale();
						String motivoMulti =  this.messageSource.getMessage("p18_referenciasCentroPopupPedir.manianaAct", null, locale);
						TextoMotivo textoMotivoManAct = new TextoMotivo(motivoMulti, "");
						Motivo motivoManAct = new Motivo();
						motivoManAct.setMotivoWebservice("N");
						motivoManAct.setTextoMotivo(textoMotivoManAct);
						motivoManAct.setTipoMensaje("NS"); //sin formato
						listaMotivos.add(motivoManAct);
					}

				}


				session.setAttribute("listMotGeneral" + motivo.getOrigenPantalla(), listaMotivos);


				session.setAttribute("performed", "T");
			}else{
				listaMotivos = listaMotivosWebService;
			}


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

	@RequestMapping(value="/loadMotivosNoActivaMMC", method = RequestMethod.POST)
	public @ResponseBody Page<Motivo> loadMotivosNoActivaMMC(
			@RequestBody Motivo motivo,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "5") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpSession session, HttpServletResponse response) throws Exception {

		List<Motivo> listaGeneral = (List<Motivo>) session.getAttribute("listMotMMC" + motivo.getOrigenPantalla());

		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		List<Motivo> listaRecarga = new ArrayList<Motivo>();
		Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
		Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;

		Page<Motivo> result = null;

		if (listaGeneral.size()>0) {
			int records = listaGeneral.size();
			if (elemInicio <= records){
				if (elemFinal > records){
					elemFinal = new Long(records);
				}
				listaRecarga = (listaGeneral).subList(Integer.parseInt(elemInicio.toString()), Integer.parseInt(elemFinal.toString()));
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

			referenciasCentro.setDiarioArt(obtenerDiarioArt(referenciasCentro));
			referenciasCentro.setSurtidoTienda(obtenerSurtidoTienda(referenciasCentro));

			User user = (User) session.getAttribute("user");
			
			//Si el centro es de caprabo obtenemos el código y la descripción del artículo de caprabo.
			if (utilidadesCapraboService.esCentroCaprabo(referenciasCentro.getCodCentro(), user.getCode())){
				Long codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(referenciasCentro.getCodCentro(), referenciasCentro.getCodArt());
				if (codArtCaprabo != null){
					referenciasCentro.setCodArtCaprabo(codArtCaprabo);
					referenciasCentro.setDescArtCaprabo(utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo));
				}
			}

			if (referenciasCentro.getOrigenPantalla().equals(Constantes.ORIGEN_DETALLADO)) {
				referenciasCentro.setNextDayPedido(this.vReferenciaActiva2Service.getNextFechaPedido(vReferenciasCentro.getCodCentro(), vReferenciasCentro.getCodArt()));


				if (referenciasCentro.getSurtidoTienda().getUfp() != null){
					if ( (referenciasCentro.getDiarioArt().getGrupo2().intValue() == Constantes.SECCION_CARNICERIA || 
							(referenciasCentro.getDiarioArt().getGrupo2().intValue() == Constantes.SECCION_CHARCUTERIA && referenciasCentro.getDiarioArt().getGrupo3() == Constantes.CATEGORIA_QUESOS_RECIEN_CORTADOS)  ||
							(referenciasCentro.getDiarioArt().getGrupo2().intValue() == Constantes.SECCION_PESCADERIA && referenciasCentro.getDiarioArt().getGrupo3() == Constantes.CATEGORIA_ESPECIALIDADES)) && 
							! new Double(referenciasCentro.getSurtidoTienda().getUfp()).equals(new Double(0))){
						referenciasCentro.setTipoUFP("U");
					}

				}


				if ((referenciasCentro.getSurtidoTienda().getUniCajaServ()!=null) && (referenciasCentro.getSurtidoTienda().getUfp() != null)) {

					//Estos campos que se estan calculando se utilizaran cuando la referencia ses tipoUfp == "U"
					Double dexenx = Math.ceil(referenciasCentro.getSurtidoTienda().getUfp()/referenciasCentro.getSurtidoTienda().getUniCajaServ());
					referenciasCentro.setDexenx(dexenx); //Para pintarlo en el Grid

					//Así si se hace P.ej 5/2 devuelve 2 y no 2.5
					Double dexenxEntero = Math.ceil(referenciasCentro.getSurtidoTienda().getUfp()/referenciasCentro.getSurtidoTienda().getUniCajaServ());
					referenciasCentro.setDexenxEntero(dexenxEntero); //Para calcular los multiplos
				}

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
		Double totalVentaHoy = new Double(0);
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
				
				Double totalVentaTarifa = new Double(0);
				Double totalVentaOferta = new Double(0);
				Double totalVentaAnticipada = new Double(0);
				Double totalVentaCompetencia = new Double(0);
				Double totalEntradas = new Double(0);
				Double totalModifAjuste = new Double(0);
				Double totalModifRegul = new Double(0);

				for (ReferenciaRespuetaType referencia : ventasTiendaResponse.getListaReferencias()) {
					//Si da error en la consulta de la referencia se ignora salvo si se trata de la referencia padre
					if (new BigInteger("0").equals(referencia.getCodigoError()) || referenciasCentro.getCodArt().equals(referencia.getCodigoReferencia())){
						valoresStock.setStockInicial(stockTiendaService.consultarStockInicial(referenciasCentro.getCodCentro(), referenciasCentro.getCodArt()));
						
						final Long cantidad = hshRefAsociadasCantidad.get(new Long(referencia.getCodigoReferencia().toString()));

						// Valores parciales de una referencia
						final Double ventaTarifa = referencia.getTotalVentaTarifa()!=null?referencia.getTotalVentaTarifa().doubleValue():new Double(0.000);
						final Double ventaOferta = referencia.getTotalVentaOferta()!=null?referencia.getTotalVentaOferta().doubleValue():new Double(0.000);
						final Double ventaAnticipada = referencia.getTotalVentaAnticipada()!=null?referencia.getTotalVentaAnticipada().doubleValue():new Double(0.000);
						final Double ventaCompetencia = referencia.getTotalVentaCompetencia()!=null?referencia.getTotalVentaCompetencia().doubleValue():new Double(0.000);
						final Double entradas = referencia.getTotalEntradas()!=null?referencia.getTotalEntradas().doubleValue():new Double(0.000);
						final Double modifAjuste = referencia.getTotalSalidasAjustes()!=null?referencia.getTotalSalidasAjustes().doubleValue():new Double(0.000);
						final Double modifRegul = referencia.getTotalRegulaciones()!=null?referencia.getTotalRegulaciones().doubleValue():new Double(0.000);
						
						final Double ventaHoy = (ventaAnticipada + ventaCompetencia + ventaOferta + ventaTarifa);

						// Sumatorio de los valores de cada referencia por la cantidad
						totalVentaTarifa = totalVentaTarifa + (cantidad * ventaTarifa);
						totalVentaOferta = totalVentaOferta + (cantidad * ventaOferta);
						totalVentaAnticipada = totalVentaAnticipada + (cantidad * ventaAnticipada);
						totalVentaCompetencia = totalVentaCompetencia + (cantidad * ventaCompetencia);
						totalEntradas = totalEntradas + (cantidad * entradas);
						totalModifAjuste = totalModifAjuste + (cantidad * modifAjuste);
						totalModifRegul = totalModifRegul + (cantidad * modifRegul);
						totalVentaHoy = totalVentaHoy + (cantidad * ventaHoy);
						
					}
				}

				valoresStock.setTotalVentaTarifa(totalVentaTarifa);
				valoresStock.setTotalVentaOferta(totalVentaOferta);
				valoresStock.setTotalVentaAnticipada(totalVentaAnticipada);
				valoresStock.setTotalVentaCompetencia(totalVentaCompetencia);
				valoresStock.setTotalEntradas(totalEntradas);
				valoresStock.setTotalModifAjuste(totalModifAjuste);
				valoresStock.setTotalModifRegul(totalModifRegul);
				valoresStock.setSalidas(totalVentaTarifa+totalVentaCompetencia);

			}
		} catch (Exception e) {
			valoresStock.setFlgErrorWSVentasTienda(1);
			return valoresStock;
		}
		
		valoresStock.setCentroParametrizado(ventasTiendaService.isCentroParametrizado(referenciasCentro.getCodCentro(), Constantes.MOVIMIENTOS_STOCK_130)?1:0);
		
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
				logger.error("############################## CONTROLADOR: p13ReferenciasCentroController	 ############################");
				logger.error("###########################################################################################################");
			}
			ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
			if (responseType.getCodigoRespuesta().equals("OK")){

				for (ReferenciaType referencia : responseType.getListaReferencias()){
					//if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(referenciasCentro.getCodArt()))){
					if (referencia.getCodigoError().equals(new BigInteger("0"))){
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
			consultaMensajeAlabaranes(requestType,session);

			
		} catch (Exception e) {
			valoresStock.setFlgErrorWSVentasTienda(1);
			return valoresStock;
		}

		//Calculo del Velocímetro.
		ImagenComercial imagenComercial = imagenComercialService.consultaImc(referenciasCentro.getCodCentro(), referenciasCentro.getCodArt());
		VDatosDiarioArt vDatosDiarioArtRes = this.obtenerDatosDiarioArt(referenciasCentro);
		if (null != vDatosDiarioArtRes && imagenComercial != null && imagenComercial.getMetodo() != null){
			Double stockBajo;
			Double sobreStockInferior;
			Double sobreStockSuperior;
			StockFinalMinimo stockFinalMinimo = this.stockFinalMinimoService.obtenerStockFinalMinimo(referenciasCentroIC);

			//Calculo el stockBajo, el sobreStockInferior y el sobreStockSuperior
			boolean esSfm = (Constantes.IMC_SFM == imagenComercial.getMetodo());
			if (!esSfm){
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

				//Calculamos el stock bajo para los que no son SFM -> MAX(IMC, VM) – VENTA HOY.
				Long imc = imagenComercial.getImc() != null ? imagenComercial.getImc() : new Long(0);
				stockBajo = imc > ventaMedia ? (imc - totalVentaHoy) : (ventaMedia - totalVentaHoy);

				//Calculamos el stock superior e inferior del velocímetro
				sobreStockInferior = (ventasMax * tipoServicio) - totalVentaHoy;
				sobreStockSuperior = ((ventasMax * tipoServicio) - totalVentaHoy) * 2;
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

				//Calculamos el stock bajo para los que no son SFM -> MAX(SFM, VM) – VENTA HOY.
				Long sfm = imagenComercial.getSfm() != null ? imagenComercial.getSfm() : 0;
				stockBajo = sfm > ventaMedia ? (sfm - totalVentaHoy) : (ventaMedia - totalVentaHoy);

				//Calculamos el stock superior e inferior del velocímetro
				sobreStockInferior = (ventasMax * vidaUtil) - totalVentaHoy;
				sobreStockSuperior = ((ventasMax * vidaUtil) - totalVentaHoy) * 2;
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

	private void consultaMensajeAlabaranes(ConsultarStockRequestType requestType, HttpSession session) throws Exception {
		requestType.setTipoMensaje("CC");
		ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);

		if (responseType.getListaReferencias()!=null && responseType.getListaReferencias().length>0){
			ReferenciaType ref = responseType.getListaReferencias()[0];
			if (ref.getCodigoError()!=null && ref.getCodigoError().equals(BigInteger.valueOf(2))){
				Locale locale = LocaleContextHolder.getLocale();
				final String msgPendientesActualizar =  this.messageSource.getMessage("p30_popupInfoReferencia.msgAlabaranesPendientes", null, locale);
				session.setAttribute("p13respuestaWS", msgPendientesActualizar);
			}else{
				session.removeAttribute("p13respuestaWS");
			}
		}
		
		
	}

	private String obtenerFlgDepositoBrita(ReferenciasCentro referenciasCentro) throws Exception{

		RefEnDepositoBrita refEnDepositoBrita = new RefEnDepositoBrita();
		refEnDepositoBrita.setCodArt(referenciasCentro.getCodArt());
		refEnDepositoBrita.setCodCentro(referenciasCentro.getCodCentro());

		return (this.refEnDepositoBritaService.enDepositoBrita(refEnDepositoBrita)?"S":"N");
	}

	private String obtenerFlgPorCatalogo(ReferenciasCentro referenciasCentro) throws Exception{

		String esRefPorCatalogo = "N";

		//Obtención de estructura comercial a partir de la referencia
		VDatosDiarioArt vDatosDiarioArt = obtenerDiarioArt(referenciasCentro.getCodArt());

		//Obtención del CC
		Long cc = obtenerCc(referenciasCentro);

		if (vDatosDiarioArt != null && vDatosDiarioArt.getGrupo1()!=null && cc != null &&
				Constantes.REFERENCIA_POR_CATALOGO_AREA.equals(vDatosDiarioArt.getGrupo1().toString()) && 
				Constantes.REFERENCIA_POR_CATALOGO_CC.equals(cc.toString())){

			esRefPorCatalogo = "S";
		}

		return esRefPorCatalogo;
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

	@RequestMapping(value="/loadDatosMaestrosFijoDetSIA", method = RequestMethod.POST)
	public @ResponseBody ReferenciasCentro loadDatosMaestrosFijoDetSIA(
			@RequestBody ReferenciasCentro vReferenciasCentro,
			HttpSession session, HttpServletResponse response) throws Exception {

		try {
			ReferenciasCentro referenciasCentro = vReferenciasCentro;

			referenciasCentro.setDiarioArt(obtenerDiarioArt(referenciasCentro));
			referenciasCentro.setSurtidoTienda(obtenerSurtidoTienda(referenciasCentro));

			User user = (User) session.getAttribute("user");
			//Si el centro es de caprabo obtenemos el código y la descripción del artículo de caprabo.
			if (utilidadesCapraboService.esCentroCaprabo(referenciasCentro.getCodCentro(), user.getCode())){
				Long codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(referenciasCentro.getCodCentro(), referenciasCentro.getCodArt());
				if (codArtCaprabo != null){
					referenciasCentro.setCodArtCaprabo(codArtCaprabo);
					referenciasCentro.setDescArtCaprabo(utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo));
				}
			}

			DetallePedidoLista listaDetallePedido = (DetallePedidoLista) detalladoPedidoService.referenciaNuevaSIA(referenciasCentro.getCodArt(), referenciasCentro.getCodCentro());
			DetallePedido detallePedido = null;
			if (listaDetallePedido!=null ){
				referenciasCentro.setCodError(listaDetallePedido.getCodError());
				referenciasCentro.setDescError(listaDetallePedido.getDescError());
				if (listaDetallePedido.getDatos() != null && listaDetallePedido.getDatos().size()>0)
				{
					detallePedido = listaDetallePedido.getDatos().get(0);
					if (referenciasCentro.getSurtidoTienda() != null){
						referenciasCentro.getSurtidoTienda().setUniCajaServ(detallePedido.getUnidadesCaja());
					}
					referenciasCentro.setNextDayPedido(detallePedido.getNextDayPedido());
					referenciasCentro.setStockTienda(detallePedido.getStock());

					if(detallePedido.getEnCurso1() != null && detallePedido.getEnCurso2() != null){
						if (detallePedido.getUnidadesCaja()!= null && !(new Double("0").equals(detallePedido.getUnidadesCaja()))) {

							referenciasCentro.setPendienteTienda((double)Math.round((detallePedido.getEnCurso1()/detallePedido.getUnidadesCaja()) * 10) / 10d);
							referenciasCentro.setPendienteTiendaManana((double)Math.round((detallePedido.getEnCurso2()/detallePedido.getUnidadesCaja()) * 10) / 10d);
						} else {

							referenciasCentro.setPendienteTienda((double)Math.round((detallePedido.getEnCurso1()/new Double(1)) * 10) / 10d);
							referenciasCentro.setPendienteTiendaManana((double)Math.round((detallePedido.getEnCurso2()/new Double(1)) * 10) / 10d);

						}
					}
					referenciasCentro.setGrupo1(detallePedido.getGrupo1());
					referenciasCentro.setGrupo2(detallePedido.getGrupo2());
					referenciasCentro.setGrupo3(detallePedido.getGrupo3());
					referenciasCentro.setGrupo4(detallePedido.getGrupo4());
					referenciasCentro.setGrupo5(detallePedido.getGrupo5());

					referenciasCentro.setDexenx(detallePedido.getDexenx());
					referenciasCentro.setDexenxEntero(detallePedido.getDexenxEntero());
					referenciasCentro.setTipoUFP(detallePedido.getTipoUFP());

					referenciasCentro.setFlgOferta(detallePedido.getFlgOferta());

					//Buscamos el estado de la temporal.
					String sessionId =  session.getId();

					List<DetallePedido> lstDetallePedido = this.findSessionInfo(detallePedido,sessionId, null);
					if(lstDetallePedido != null && lstDetallePedido.size() > 0)  {
						referenciasCentro.setEstadoPedido(lstDetallePedido.get(0).getEstadoPedido());
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

	private List<DetallePedido> findSessionInfo (DetallePedido detallePedido,String sesionID, Pagination pagination) {

		List<DetallePedido> lstDetallePedido = null;
		try {
			lstDetallePedido = this.detalladoPedidoService.findSessionInfo(detallePedido,sesionID, pagination, null);
		} catch (Exception e) {
			logger.error("findSessionInfo="+e.toString());
			e.printStackTrace();
		}
		return lstDetallePedido;
	}

	//Función que actualiza la tabla planograma_vigente. Hay 4 tipos de error
	// -> 0. Todo bien.
	// -> 1. Error al actualizar.
	// -> 2. Han cambiado el stockMinComerLineal o el capacidadMaxLineal antes de que el usuario lo actualizara y ya no corresponde
	//       con tu original.
	// -> 3. Error inesperado.
	@RequestMapping(value="/actualizarPlanogramaVigente", method = RequestMethod.POST)
	public @ResponseBody PlanogramaVigente actualizarPlanogramaVigente(
			@RequestBody PlanogramaVigente planogramaVigente,
			HttpSession session, HttpServletResponse response) throws Exception {
		//Para guardar el mensaje de error.
		String msgError;

		//Respuesta de planogramaVigente
		PlanogramaVigente planogramaVigenteResp = new PlanogramaVigente();
		try {
			//Miramos si existe un planograma que contenga los datos originales
			PlanogramaVigente planogramaVigenteReq = new PlanogramaVigente();
			planogramaVigenteReq.setCodArt(planogramaVigente.getCodArt());
			planogramaVigenteReq.setCodCentro(planogramaVigente.getCodCentro());

			PlanogramaVigente planogramaVigenteOrig = planogramaVigenteService.findOne(planogramaVigenteReq);
			if(planogramaVigente.getStockMinComerLinealOrig().equals(planogramaVigenteOrig.getStockMinComerLineal())
					&& planogramaVigente.getCapacidadMaxLinealOrig().equals(planogramaVigente.getCapacidadMaxLineal())){
				msgError = planogramaVigenteService.updatePlanogramaVigente(planogramaVigente);

				//Después de insertar, indicamos que el original es el nuevo dato.
				planogramaVigenteResp.setCapacidadMaxLinealOrig(planogramaVigente.getCapacidadMaxLineal());
				planogramaVigenteResp.setStockMinComerLinealOrig(planogramaVigente.getStockMinComerLineal());
			}else{
				msgError = "2"; 

				//Introducimos los nuevos datos originales.
				planogramaVigenteResp.setCapacidadMaxLinealOrig(planogramaVigenteOrig.getCapacidadMaxLineal());
				planogramaVigenteResp.setStockMinComerLinealOrig(planogramaVigenteOrig.getStockMinComerLineal());
			}
		} catch (Exception e) {
			msgError = "3";
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		planogramaVigenteResp.setMsgError(msgError);
		return planogramaVigenteResp;
	}	


	@RequestMapping(value="/actualizarModificarPlanograma", method = RequestMethod.POST)
	public @ResponseBody String actualizarModificarPlanograma(
			@RequestBody PlanogramaVigente planogramaVigente,
			HttpSession session, HttpServletResponse response) throws Exception {
		String msgError;
		try {
			//Miramos si existe un planograma que contenga los datos originales
			Planograma planograma = planogramaService.findOne(planogramaVigente);

			//Si existe el planograma, se actualiza el existente, si no. Se crea un nuevo registro.
			if(planograma != null){
				msgError = planogramaService.updatePlanograma(planogramaVigente);
			}else{
				VPlanograma vPlanograma = new VPlanograma();
				vPlanograma.setCodCentro(planogramaVigente.getCodCentro());
				vPlanograma.setCodArt(planogramaVigente.getCodArt());

				vPlanograma = vPlanogramaService.findOne(vPlanograma);				
				msgError = planogramaService.insertPlanograma(planogramaVigente,vPlanograma);
			}
		} catch (Exception e) {
			msgError = "3";
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return msgError;
	}	

	private Double obtenerStockPlataforma(Long codArt, Long codCentro) throws Exception {
		Double result = null;
		try{
			StockPlataforma sp = new StockPlataforma();
			sp.setCodCentro(codCentro);
			sp.setCodArt(codArt);


			StockPlataforma spSalida = this.stockPlataformaService.find(sp);
			if (spSalida!=null && !spSalida.equals("") && spSalida.getStock()!=null && !spSalida.getStock().equals("")){

				result = spSalida.getStock().doubleValue();
			}
		} catch (Exception e) {
			result = new Double(-9999);
		}
		return result;
	}
	
	@RequestMapping(value="/vieneDeSIA", method = RequestMethod.GET)
	public @ResponseBody Boolean vieneDeSIA(
			@RequestParam(value = "codCentro") Long codCentro,
			@RequestParam(value = "codArt") Long codArt) throws Exception {
		return vPlanogramaService.vieneDeSIA(codCentro, codArt);
	}
	
	private PedidoAdicionalContadores sumarContadores(PedidoAdicionalContadores resultado,
			PedidoAdicionalContadores resultadoVegalsa) {
		
		final PedidoAdicionalContadores output = new PedidoAdicionalContadores();
		output.setCodError(resultado.getCodError());
		output.setDescError(resultado.getDescError());
		output.setDefaultDescription(resultado.getDefaultDescription());
		
		Long totalEncargos = resultadoVegalsa.getContadorEncargos();
		Long totalMontajeAdicional = resultadoVegalsa.getContadorMontaje() + resultadoVegalsa.getContadorMontajeOferta() + resultadoVegalsa.getContadorValidarCantExtra();
		
		final Long contadorEncargos = resultado.getContadorEncargos() + totalEncargos;
		final Long contadorMontaje = resultado.getContadorMontaje() + totalMontajeAdicional;
		
		
		output.setContadorEmpuje(0L);
		output.setContadorMAC(resultado.getContadorMAC());
		output.setContadorEncargos(contadorEncargos);
		output.setContadorMontaje(contadorMontaje);
		output.setContadorMontajeOferta(0L);
		output.setContadorValidarCantExtra(0L);
		output.setContadorEncargosCliente(0L);
		
		return output;
	}

	private boolean esCentroVegalsa(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return user.getCentro().getCodSoc().equals(new Long(13));
	}
	
	@RequestMapping(value="/incluirExluirRefGama", method = RequestMethod.POST)
	public @ResponseBody IncluirExcluirRefGama incluirExluirRefGama(
			@RequestBody ReferenciasCentro vReferenciasCentro,
			@RequestParam(value = "incluirExluir", required = false, defaultValue = "") String incluirExluir,
			HttpSession session, HttpServletResponse response) throws Exception {
		User user = (User) session.getAttribute("user");
		
		return incluirExcluirRefGamaService.incluirExluirRefGama(vReferenciasCentro, incluirExluir, user.getCode());
	}

}