package es.eroski.misumi.control;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import es.eroski.misumi.dao.iface.VPlanogramaService;
import es.eroski.misumi.model.CentroAutoservicio;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.OfertaVigente;
import es.eroski.misumi.model.ParamCentrosVp;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VDatosDiarioCap;
import es.eroski.misumi.model.VFacingX;
import es.eroski.misumi.model.VMapaAprov;
import es.eroski.misumi.model.VMapaAprovFestivo;
import es.eroski.misumi.model.VMapaReferencia;
import es.eroski.misumi.model.VOfertaPaAyuda;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosReferencia;
import es.eroski.misumi.service.iface.EansService;
import es.eroski.misumi.service.iface.HistoricoVentaMediaService;
import es.eroski.misumi.service.iface.OfertaVigenteService;
import es.eroski.misumi.service.iface.ParamCentrosVpService;
import es.eroski.misumi.service.iface.PlanogramaKosmosService;
import es.eroski.misumi.service.iface.PlanogramaVigenteService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockFinalMinimoService;
import es.eroski.misumi.service.iface.TMisMcgCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VAgruComerParamSfmcapService;
import es.eroski.misumi.service.iface.VArtSfmService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VDatosDiarioCapService;
import es.eroski.misumi.service.iface.VFacingXService;
import es.eroski.misumi.service.iface.VMapaAprovFestivoService;
import es.eroski.misumi.service.iface.VMapaAprovService;
import es.eroski.misumi.service.iface.VMapaReferenciaService;
import es.eroski.misumi.service.iface.VOfertaPaAyudaService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

public class pdaConsultasController {


	private static Logger loggerConsultas = Logger.getLogger(pdaConsultasController.class);

	@Value( "${tipoAprovisionamiento.centralizado}" )
	private String tipoAprovisionamientoCentralizado;

	@Value( "${tipoAprovisionamiento.grupaje}" )
	private String tipoAprovisionamientoGrupaje;

	@Value( "${tipoAprovisionamiento.descentralizado}" )
	private String tipoAprovisionamientoDescentralizado;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	@Autowired
	protected TMisMcgCapraboService tMisMcgCapraboService;
	@Autowired
	private StockFinalMinimoService stockFinalMinimoService;
	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;
	@Autowired
	private OfertaVigenteService ofertaVigenteService;
	@Resource
	private MessageSource messageSource;
	@Autowired
	private HistoricoVentaMediaService historicoVentaMediaService;
	@Autowired
	private PlanogramaVigenteService planogramaVigenteService;
	@Autowired
	private PlanogramaKosmosService planogramaKosmosService;
	@Autowired
	private VPlanogramaService vPlanogramaService;
	@Autowired
	private VMapaAprovFestivoService vMapaAprovFestivoService;
	@Autowired
	private VMapaAprovService vMapaAprovService;
	
	@Autowired
	private VMapaReferenciaService vMapaReferenciaService;
	
	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;
	@Autowired
	private ParamCentrosVpService paramCentrosVpService;
	@Autowired
	private EansService eansService;
	@Autowired
	private VAgruComerParamSfmcapService vAgruComerParamSfmcapService;
	@Autowired
	private VArtSfmService vArtSfmService;
	@Autowired
	private VOfertaPaAyudaService vOfertaPaAyudaService;
	@Autowired
	private VFacingXService vFacingXService;
	@Autowired
	private VDatosDiarioCapService vDatosDiarioCapService;
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;
	
	public String obtenerTipoAprov(VSurtidoTienda surtidoTienda) throws Exception{

		String tipoAprov = "";
		Locale locale = LocaleContextHolder.getLocale();
		if (surtidoTienda != null && surtidoTienda.getTipoAprov()!= null && surtidoTienda.getTipoAprov().equals(Constantes.TIPO_APROVISIONAMIENTO_CENTRALIZADO)){
			tipoAprov = this.messageSource.getMessage("pda_p12_datosReferencia.centralizada", null, locale);
		}else if (surtidoTienda != null && surtidoTienda.getTipoAprov()!= null && surtidoTienda.getTipoAprov().equals(Constantes.TIPO_APROVISIONAMIENTO_DESCENTRALIZADO)){
			tipoAprov = this.messageSource.getMessage("pda_p12_datosReferencia.descentralizada", null, locale);
		}else if (surtidoTienda != null && surtidoTienda.getTipoAprov()!= null && surtidoTienda.getTipoAprov().equals(Constantes.TIPO_APROVISIONAMIENTO_GRUPAJE)){
			tipoAprov = this.messageSource.getMessage("pda_p12_datosReferencia.grupaje", null, locale);
		}

		return tipoAprov;
	}

	public PdaDatosReferencia cargarVariablesPedido(ReferenciasCentro referenciasCentro, PdaDatosReferencia pdaDatosRef) throws Exception{

		VSurtidoTienda variablesPedido = obtenerVariablesPedido(referenciasCentro);

		if (variablesPedido != null && variablesPedido.getUfp()!= null){
			if (referenciasCentro.isEsTratamientoVegalsa()){
				pdaDatosRef.setUfp(Utilidades.convertirDoubleAString(variablesPedido.getUfp().doubleValue(),"###0"));
			}else{
			pdaDatosRef.setUfp(Utilidades.convertirDoubleAString(variablesPedido.getUfp().doubleValue(),"###0.00"));
		}
		}
		
		if (variablesPedido != null && variablesPedido.getGamaDiscontinua()!= null && variablesPedido.getGamaDiscontinua().equals("D")){
			pdaDatosRef.setFlgGamaDiscont("S");
		}else{
			pdaDatosRef.setFlgGamaDiscont("N");
		}

		return pdaDatosRef;
	}

	public PdaDatosReferencia cargarOferta(ReferenciasCentro referenciasCentro, PdaDatosReferencia pdaDatosRef) throws Exception{

		//Obtenemos la oferta vigente
		OfertaVigente ofertaVigente = obtenerOferta(referenciasCentro);

		//Control de visualización de oferta u oferta pasada
		pdaDatosRef.setFlgOfertaVigente("S");

		if (ofertaVigente != null && ofertaVigente.getAnoOferta()!= null && ofertaVigente.getNumOferta()!= null){
			pdaDatosRef.setNumOferta(String.valueOf(ofertaVigente.getAnoOferta())+"-"+String.valueOf(ofertaVigente.getNumOferta()));
		}else{
			//Si no existe la oferta vigente hay que buscar la oferta más reciente
			VOfertaPaAyuda ofertaPasadaMasReciente = obtenerOfertaPasadaMasReciente(referenciasCentro);
			if (ofertaPasadaMasReciente != null && ofertaPasadaMasReciente.getAnoOferta()!= null && ofertaPasadaMasReciente.getNumOferta()!= null){
				pdaDatosRef.setFlgOfertaVigente("N");
				pdaDatosRef.setNumOferta(String.valueOf(ofertaPasadaMasReciente.getAnoOferta())+"-"+String.valueOf(ofertaPasadaMasReciente.getNumOferta()));
			}
		}

		return pdaDatosRef;
	}

	public PdaDatosReferencia cargarDatosVentaMedia(HistoricoVentaMedia historicoVentaMedia, PdaDatosReferencia pdaDatosRef) throws Exception{

		if (historicoVentaMedia != null && historicoVentaMedia.getTarifa()!= null){
			pdaDatosRef.setTarifa(Utilidades.convertirDoubleAString(historicoVentaMedia.getTarifa().doubleValue(),"###0.000"));
		}
		if (historicoVentaMedia != null && historicoVentaMedia.getCompetencia()!= null){
			pdaDatosRef.setCompetencia(Utilidades.convertirDoubleAString(historicoVentaMedia.getCompetencia().doubleValue(),"###0.000"));
		}
		if (historicoVentaMedia != null && historicoVentaMedia.getOferta()!= null){
			pdaDatosRef.setOferta(Utilidades.convertirDoubleAString(historicoVentaMedia.getOferta().doubleValue(),"###0.000"));			
		}
		if (historicoVentaMedia != null && historicoVentaMedia.getAnticipada()!= null){
			pdaDatosRef.setAnticipada(Utilidades.convertirDoubleAString(historicoVentaMedia.getAnticipada().doubleValue(),"###0.000"));			
		}

		return pdaDatosRef;
	}

	public PdaDatosReferencia cargarDatosCapacidad(ReferenciasCentroIC referenciasCentroIC, PdaDatosReferencia pdaDatosRef, Long codCentro, HttpSession session) throws Exception{

		pdaDatosRef.setFlgSfmCapacidad("C");
		pdaDatosRef.setFlgFacing("N");
		pdaDatosRef.setParametrizadoSfmCap("N");
		pdaDatosRef.setParametrizadoFacing("N");
		PlanogramaVigente planogramaVigente = obtenerPlanogramaVigente(referenciasCentroIC);
		
		//Si no existe el planogramaVigente o la capacidad maxima lineal es nula y el stockminimocomercial es nulo, se consulta el planograma de kosmos.
		if(planogramaVigente == null || (planogramaVigente.getCapacidadMaxLineal() == null && planogramaVigente.getStockMinComerLineal() == null)){
			planogramaVigente = obtenerPlanogramaKosmos(referenciasCentroIC);				
		}

		VDatosDiarioArt vDatosDiarioArt = obtenerDiarioArt(referenciasCentroIC.getCodArt());
		if (planogramaVigente != null){
			//Comprobamos si se trata de un centro sólo de SFM o si es de Capacidad también.

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
			if (listaAgruComerParamSfmcap != null && listaAgruComerParamSfmcap.size()>0) {
				VAgruComerParamSfmcap registro = new VAgruComerParamSfmcap();
				registro = listaAgruComerParamSfmcap.get(0);

				if (registro.getFlgCapacidad() != null && registro.getFlgCapacidad().equals("S"))
				{
					//Se marca como parametrizado
					pdaDatosRef.setParametrizadoSfmCap("S");

					//En este caso tendremos que hacer las comprobaciones de si se ha modificado la capacidad.
					StockFinalMinimo stockFinalMinimoRes;
					StockFinalMinimo stockFinalMinimoSIARes;
					StockFinalMinimo stockFinalMinimo = new StockFinalMinimo();
					stockFinalMinimo.setCodArticulo(referenciasCentroIC.getCodArt());
					stockFinalMinimo.setCodLoc(referenciasCentroIC.getCodCentro());

					stockFinalMinimoRes = this.stockFinalMinimoService.findOne(stockFinalMinimo);
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
						pdaDatosRef.setMostrarCantidadCapSIA("SI");
					}
				}
			}
		}

		//Formateo de campos de pantalla
		if (planogramaVigente != null){
			if (planogramaVigente.getCapacidadMaxLineal()!= null)
			{
				pdaDatosRef.setCapacidadLineal(Utilidades.convertirDoubleAString(planogramaVigente.getCapacidadMaxLineal().doubleValue(),"###0"));
			}
			if (planogramaVigente.getStockMinComerLineal()!= null)
			{
				pdaDatosRef.setFacingLineal(Utilidades.convertirDoubleAString(planogramaVigente.getStockMinComerLineal().doubleValue(),"###0"));
				pdaDatosRef.setFlgFacingX("S");

				Long multiplicador = new Long(1);

				//Comprobación de si una referencia es FFPP 
				ReferenciasCentro referenciasCentro = new ReferenciasCentro();
				referenciasCentro.setCodCentro(referenciasCentroIC.getCodCentro());
				referenciasCentro.setCodArt(referenciasCentroIC.getCodArt());
				User user = (User) session.getAttribute("user");
				boolean tratamientoVegalsaAux = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
				referenciasCentro = this.vRelacionArticuloService.obtenerFfppActivaOUnitaria(referenciasCentro, tratamientoVegalsaAux);

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
				pdaDatosRef.setImc((long) (planogramaVigente.getStockMinComerLineal()*multiplicador));
				pdaDatosRef.setMultiplicadorFac(new Integer(multiplicador.toString()));

			}
			if (planogramaVigente.getFechaGenMontaje1()!= null)
			{
				if (planogramaVigente.getCapacidadMontaje1()!= null)
				{
					pdaDatosRef.setCapacidad1(Utilidades.convertirDoubleAString(planogramaVigente.getCapacidadMontaje1().doubleValue(),"###0"));			
				}
				if (planogramaVigente.getFacingMontaje1()!= null)
				{
					pdaDatosRef.setFacing1(Utilidades.convertirDoubleAString(planogramaVigente.getFacingMontaje1().doubleValue(),"###0"));			
				}
			}
			else if (planogramaVigente.getFechaGenCabecera()!= null)
			{
				if (planogramaVigente.getCapacidadMaxCabecera()!= null)
				{
					pdaDatosRef.setCapacidad1(Utilidades.convertirDoubleAString(planogramaVigente.getCapacidadMaxCabecera().doubleValue(),"###0"));
				}
				if (planogramaVigente.getStockMinComerCabecera()!= null)
				{
					pdaDatosRef.setFacing1(Utilidades.convertirDoubleAString(planogramaVigente.getStockMinComerCabecera().doubleValue(),"###0"));
				}
			}
			if (planogramaVigente.getFechaGenMontaje2()!= null)
			{
				if (planogramaVigente.getCapacidadMontaje2()!= null)
				{
					pdaDatosRef.setCapacidad2(Utilidades.convertirDoubleAString(planogramaVigente.getCapacidadMontaje2().doubleValue(),"###0"));			
				}
				if (planogramaVigente.getFacingMontaje2()!= null)
				{
					pdaDatosRef.setFacing2(Utilidades.convertirDoubleAString(planogramaVigente.getFacingMontaje2().doubleValue(),"###0"));			
				}

			}
			else if (planogramaVigente.getFechaGenCabecera()!= null)
			{
				if (planogramaVigente.getCapacidadMaxCabecera()!= null)
				{
					pdaDatosRef.setCapacidad2(Utilidades.convertirDoubleAString(planogramaVigente.getCapacidadMaxCabecera().doubleValue(),"###0"));			
				}
				if (planogramaVigente.getStockMinComerCabecera()!= null)
				{
					pdaDatosRef.setFacing2(Utilidades.convertirDoubleAString(planogramaVigente.getStockMinComerCabecera().doubleValue(),"###0"));			
				}
			}
			if (planogramaVigente.getCapacidadMaxLinealRecal()!= null)
			{
				pdaDatosRef.setCantidadCapLinealSIA(Utilidades.convertirDoubleAString(planogramaVigente.getCapacidadMaxLinealRecal().doubleValue(),"###0"));			
			}
			if (planogramaVigente.getStockMinComerLinealRecal()!= null)
			{
				pdaDatosRef.setCantidadFacLinealSIA(Utilidades.convertirDoubleAString(planogramaVigente.getStockMinComerLinealRecal().doubleValue(),"###0"));			
			}
		}

		//Comprobamos si se trata de un centro de autoservicio.
		pdaDatosRef.setEsAutoServicio(esAutoServicio(codCentro));

		return pdaDatosRef;
	}

	public String esAutoServicio(Long codCentro) throws Exception{

		ParamCentrosVp paramCentrosVp = new ParamCentrosVp();
		paramCentrosVp.setCodLoc(codCentro);
		if (this.paramCentrosVpService.esAutoservicio(paramCentrosVp))
		{
			return "S";
		}else
		{
			return "N";
		}
	}

	public PdaDatosReferencia cargarDatosSFM(ReferenciasCentroIC referenciasCentroIC, PdaDatosReferencia pdaDatosRef) throws Exception{

		pdaDatosRef.setFlgSfmCapacidad("S");
		pdaDatosRef.setFlgFacing("N");
		pdaDatosRef.setParametrizadoSfmCap("N");
		pdaDatosRef.setParametrizadoFacing("N");
		//Pet. 53005
		pdaDatosRef.setFlgFacingX("N");

		StockFinalMinimo stockFinalMinimo = obtenerStockFinalMinimo(referenciasCentroIC);

		if (stockFinalMinimo != null && stockFinalMinimo.getStockFinMinS()!= null)
		{
			pdaDatosRef.setSfm(Utilidades.convertirDoubleAString(stockFinalMinimo.getStockFinMinS().doubleValue(),"###0"));

		}

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
		if (listaAgruComerParamSfmcap != null && listaAgruComerParamSfmcap.size()>0) {
			VAgruComerParamSfmcap registro = new VAgruComerParamSfmcap();
			registro = listaAgruComerParamSfmcap.get(0);

			if (registro.getFlgStockFinal() != null && registro.getFlgStockFinal().equals("S"))
			{
				//Se marca como parametrizado
				pdaDatosRef.setParametrizadoSfmCap("S");

				if (stockFinalMinimo != null && stockFinalMinimo.getCantidadManualSIA()!= null)
				{
					pdaDatosRef.setCantidaSfmSIA(Utilidades.convertirDoubleAString(stockFinalMinimo.getCantidadManualSIA().doubleValue(),"###0"));

				}

				if (stockFinalMinimo != null && stockFinalMinimo.getStockFinMinS()!= null && 
						stockFinalMinimo.getCantidadManualSIA()!= null && 
						!stockFinalMinimo.getCantidadManualSIA().equals(stockFinalMinimo.getStockFinMinS())){
					pdaDatosRef.setMostrarCantidadSfmSIA("SI");
				}
			}
		}

		return pdaDatosRef;
	}

	public String obtenerPedidoActivo(VSurtidoTienda surtidoTienda,boolean isCaprabo) throws Exception{

		String esActivo = "N";
		if (surtidoTienda != null  && surtidoTienda.getPedir() != null && surtidoTienda.getPedir().equals("S")){
			if(isCaprabo){
				esActivo = "S";
			}else{
				if (surtidoTienda.getMapaHoy() != null){
					if (surtidoTienda.getMapaHoy().equals("N")){
						if (surtidoTienda.getNumeroPedidosOtroDia() == 0){
							//No hay pedido ningún día
							esActivo = "N";
						}else{
							//Hoy no hay pedido pero si algún otro día
							esActivo = "S";
						}
					}else{
						esActivo = "S";
					}
				}else{
					esActivo = "N";
				}
			}
		}else{
			esActivo = "N";
		}

		return esActivo;
	}

	public VDatosDiarioArt obtenerDiarioArt(Long codArt) throws Exception{
		VDatosDiarioArt vDatosDiarioArtRes;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		vDatosDiarioArt.setCodArt(codArt);
		vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

		return vDatosDiarioArtRes;
	}

	public VDatosDiarioArt obtenerDiarioArt(ReferenciasCentro referenciasCentro) throws Exception{
		return this.obtenerDiarioArt(referenciasCentro.getCodArt());
	}


	public TMisMcgCaprabo obtenerMcgCaprabo(ReferenciasCentro referenciasCentro) throws Exception{
		TMisMcgCaprabo tMisMcgCapraboRes;
		TMisMcgCaprabo tMisMcgCaprabo = new TMisMcgCaprabo();
		tMisMcgCaprabo.setCodArtCaprabo(referenciasCentro.getCodArtCaprabo());
		tMisMcgCaprabo.setCodCentro(referenciasCentro.getCodCentro());
		tMisMcgCapraboRes = this.tMisMcgCapraboService.findOne(tMisMcgCaprabo);

		return tMisMcgCapraboRes;
	}

	public VDatosDiarioCap obtenerVDatDiarioCap(ReferenciasCentro referenciasCentro) throws Exception{
		VDatosDiarioCap vDatosDiarioCapRes;
		VDatosDiarioCap vDatosDiarioCap = new VDatosDiarioCap();
		vDatosDiarioCap.setCodArt(referenciasCentro.getCodArtCaprabo());
		vDatosDiarioCapRes = this.vDatosDiarioCapService.findOne(vDatosDiarioCap);

		return vDatosDiarioCapRes;
	}

	public VSurtidoTienda obtenerSurtidoTienda(ReferenciasCentro referenciasCentro,boolean isCaprabo,boolean isCapraboEspecial) throws Exception{
		VSurtidoTienda vSurtidoTiendaRes;
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		vSurtidoTienda.setCodArt(referenciasCentro.getCodArt());
		vSurtidoTienda.setCodCentro(referenciasCentro.getCodCentro());

		if (referenciasCentro.isEsTratamientoVegalsa()){
			vSurtidoTiendaRes = this.vSurtidoTiendaService.findOneVegalsa(vSurtidoTienda);
		} else {
			vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

			//Calcular campo mapaHoy
			vSurtidoTiendaRes = asignarMapaHoy(referenciasCentro, vSurtidoTiendaRes,isCaprabo,isCapraboEspecial);
		}

		return vSurtidoTiendaRes;
	}

	public VMapaReferencia obtenerMapaReferencia(ReferenciasCentro referenciasCentro) throws Exception{
		
		Long codArt = referenciasCentro.getCodArt();
		
		boolean existMapaRef = this.vMapaReferenciaService.existsMapa(codArt);
		
		VMapaReferencia mapa = null;
		if (existMapaRef){
			mapa = this.vMapaReferenciaService.findMapa(codArt);
		}

		return mapa;
	}

	
	public Long obtenerReferenciaCaprabo(ReferenciasCentro referenciasCentro) throws Exception{
		VSurtidoTienda vSurtidoTiendaRes;
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		vSurtidoTienda.setCodArt(referenciasCentro.getCodArt());
		vSurtidoTienda.setCodCentro(referenciasCentro.getCodCentro());

		vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

		return vSurtidoTiendaRes.getCodArtCaprabo();
	}

	public VSurtidoTienda obtenerSurtidoTiendaCaprabo(ReferenciasCentro referenciasCentro) throws Exception{
		VSurtidoTienda vSurtidoTiendaRes;
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		vSurtidoTienda.setCodArtCaprabo(referenciasCentro.getCodArtCaprabo());
		vSurtidoTienda.setCodCentro(referenciasCentro.getCodCentro());

		vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

		//Calcular campo mapaHoy. El primer true indica que es un centro caprabo.El 2 false indica que al ser un centro de tipo caprabo, no puede ser de tipo caprabo especial
		vSurtidoTiendaRes = asignarMapaHoy(referenciasCentro, vSurtidoTiendaRes,true,false);

		return vSurtidoTiendaRes;
	}

	public VSurtidoTienda asignarMapaHoy(ReferenciasCentro referenciasCentro, VSurtidoTienda surtidoTienda,boolean isCaprabo, boolean isCapraboEspecial) throws Exception{

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
			//Obtenci�n de estructura log�stica
			estLogN1 = vDatosDiarioArt.getEstlogN1();	
			estLogN2 = vDatosDiarioArt.getEstlogN2();	
			estLogN3 = vDatosDiarioArt.getEstlogN3();	

			//S?lo se calcula el mapaHoy en caso de aprovisionamiento Grupaje o Centralizado
			String tipoAprovisionamiento = surtidoTienda.getTipoAprov();

			if (tipoAprovisionamiento != null && !isCaprabo && !isCapraboEspecial &&(tipoAprovisionamiento.equals(this.tipoAprovisionamientoCentralizado) || tipoAprovisionamiento.equals(this.tipoAprovisionamientoGrupaje) || tipoAprovisionamiento.equals(this.tipoAprovisionamientoDescentralizado))){

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
							}else{
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
			}

			vSurtidoTiendaRes.setMapaHoy(mapaHoy);
			vSurtidoTiendaRes.setNumeroPedidosOtroDia(numeroPedidos);
		}

		return vSurtidoTiendaRes;
	}

	public VSurtidoTienda obtenerVariablesPedido(ReferenciasCentro referenciasCentro) throws Exception{
		VSurtidoTienda variablesPedidoRes;
		VSurtidoTienda variablesPedido = new VSurtidoTienda();
		variablesPedido.setCodArt(referenciasCentro.getCodArt());
		variablesPedido.setCodCentro(referenciasCentro.getCodCentro());

		variablesPedidoRes = this.vSurtidoTiendaService.findOneGama(variablesPedido);
		
		if (referenciasCentro.isEsTratamientoVegalsa()){
			VSurtidoTienda variablesPedidoResVegalsa = this.vSurtidoTiendaService.findOneVegalsa(variablesPedido);
			if (variablesPedidoRes == null){
			    variablesPedidoRes = variablesPedido;
			}
			variablesPedidoRes.setUfp(variablesPedidoResVegalsa.getUfp());
		}
			
		return variablesPedidoRes;
	}

	public OfertaVigente obtenerOferta(ReferenciasCentro referenciasCentro) throws Exception{
		OfertaVigente ofertaVigenteRes;
		OfertaVigente ofertaVigente = new OfertaVigente();
		ofertaVigente.setCodArt(referenciasCentro.getCodArt());
		ofertaVigente.setCodCentro(referenciasCentro.getCodCentro());

		ofertaVigenteRes = this.ofertaVigenteService.findOne(ofertaVigente);

		return ofertaVigenteRes;
	}

	public VOfertaPaAyuda obtenerOfertaPasadaMasReciente(ReferenciasCentro referenciasCentro) throws Exception{
		VOfertaPaAyuda vOfertaPaAyudaRes;
		VOfertaPaAyuda vOfertaPaAyuda = new VOfertaPaAyuda();
		vOfertaPaAyuda.setCodArt(referenciasCentro.getCodArt());
		vOfertaPaAyuda.setCodCentro(referenciasCentro.getCodCentro());

		vOfertaPaAyudaRes = this.vOfertaPaAyudaService.findOfertaPasadaMasReciente(vOfertaPaAyuda);

		return vOfertaPaAyudaRes;
	}

	public HistoricoVentaMedia obtenerHistoricoVentaMedia(
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

	public StockFinalMinimo obtenerStockFinalMinimo(
			ReferenciasCentroIC referenciasCentroIC) throws Exception {

		StockFinalMinimo stockFinalMinimoRes;
		StockFinalMinimo stockFinalMinimoSIARes;
		StockFinalMinimo stockFinalMinimo = new StockFinalMinimo();
		stockFinalMinimo.setCodArticulo(referenciasCentroIC.getCodArt());
		stockFinalMinimo.setCodLoc(referenciasCentroIC.getCodCentro());

		stockFinalMinimoRes = this.stockFinalMinimoService.findOne(stockFinalMinimo);
		stockFinalMinimoSIARes = this.stockFinalMinimoService.findOneSIA(stockFinalMinimo);

		if (stockFinalMinimoRes != null){
			//Asignar venta media. Se asigna la calculada a partir del histórico de ventas medias
			//Asignar venta media. Se asigna la calculada a partir del histórico de ventas medias
			HistoricoVentaMedia historicoVentaMedia = referenciasCentroIC.getHistoricoVentaMedia();
			if (historicoVentaMedia!=null && historicoVentaMedia.getMedia()!=null){
				stockFinalMinimoRes.setVentaMedia(historicoVentaMedia.getMedia());
			}else{
				stockFinalMinimoRes.setVentaMedia(new Float("0.0"));
			}
		}else{
			stockFinalMinimoRes = new StockFinalMinimo();
			stockFinalMinimoRes.setVentaMedia(new Float("0.0"));
			stockFinalMinimoRes.setStockFinMinS(new Float("0.0"));
		}
		//Recalcular dias de venta
		stockFinalMinimoRes.recalcularDiasVenta();

		//Guardado de la cantidad manual de SIA para comprobación en stock de pantalla
		if (stockFinalMinimoSIARes!=null && stockFinalMinimoSIARes.getStockFinMinS() != null){
			stockFinalMinimoRes.setCantidadManualSIA(stockFinalMinimoSIARes.getStockFinMinS());
		}else{
			stockFinalMinimoRes.setCantidadManualSIA(null);
		}

		if (stockFinalMinimoSIARes!=null && stockFinalMinimoSIARes.getFacingCentro() != null){
			stockFinalMinimoRes.setFacingCentroSIA(stockFinalMinimoSIARes.getFacingCentro());
		}else{
			stockFinalMinimoRes.setFacingCentroSIA(null);
		}



		return stockFinalMinimoRes;
	}

	public PlanogramaVigente obtenerPlanogramaVigente(
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
	
	public PlanogramaVigente rellenarDatosVegalsa(PlanogramaVigente planogramaVigente, HttpSession session) throws Exception {
		PlanogramaVigente output = planogramaVigente;
		User user = (User) session.getAttribute("user");
		Long codSoc = user.getCentro().getCodSoc();
		// Si es un centro Vegalsa (cod_soc = 13)
		if (codSoc == 13){
			output = vPlanogramaService.findDatosVegalsa(planogramaVigente);
		}
	
		return output;
	}
	
	public VRelacionArticulo obtenerRelacionArticulo(ReferenciasCentro referenciasCentro) throws Exception{
		VRelacionArticulo relacionArticuloRes = null;
		VRelacionArticulo relacionArticulo = new VRelacionArticulo();

		relacionArticulo.setCodArt(referenciasCentro.getCodArt());
		relacionArticulo.setCodCentro(referenciasCentro.getCodCentro());
		relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);

		return relacionArticuloRes;
	}

	public VRelacionArticulo obtenerRelacionArticuloUnitario(ReferenciasCentro referenciasCentro) throws Exception{
		VRelacionArticulo relacionArticuloRes = null;
		VRelacionArticulo relacionArticulo = new VRelacionArticulo();

		relacionArticulo.setCodArtRela(referenciasCentro.getCodArt());
		relacionArticulo.setCodCentro(referenciasCentro.getCodCentro());
		relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);

		return relacionArticuloRes;
	}

	public Float obtenerPorcentajeCapacidad(Long codCentro){

		//Obtenemos el porcentaje de la capacidad
		Float porcentajeCapacidad = null;
		CentroAutoservicio centroAutoservicio = new CentroAutoservicio();

		try{
			centroAutoservicio.setCodCentro(codCentro);
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
		}catch (Exception e) {
			loggerConsultas.error(StackTraceManager.getStackTrace(e));
		}
		return porcentajeCapacidad;
	}

	private PdaDatosReferencia obtenerReferenciaDesdeEan(Long codartEan) throws Exception{

		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosReferencia pdaDatosReferenciaRes = new PdaDatosReferencia();
		Long codArt = this.eansService.obtenerReferenciaEan(codartEan);
		if (codArt == null){
			pdaDatosReferenciaRes.setEsError(this.messageSource.getMessage(
					"pda_p12_datosReferencia.noExisteReferencia", null, locale));
		}else{
			pdaDatosReferenciaRes.setCodArt(codArt);
		}

		return pdaDatosReferenciaRes;
	}
	
	public PdaArticulo obtenerReferenciaTratada(String strCodArt) throws Exception{

		String strCodArtTratado = strCodArt;
		String strCodBalanza = "";
		String strPrecio = "";
		String strPeso = "";
		Float fltPrecio = null;
		Float fltPeso = null;
		PdaDatosReferencia pdaDatosRefResultado = new PdaDatosReferencia();
		PdaArticulo pdaArticuloTratado = new PdaArticulo();

		try{
			strCodArt = strCodArt.trim();

			//Si el código contiene una # hay que eliminar lo que pueda venir antes porque será erróneo
			if (strCodArt != null && strCodArt.indexOf(Constantes.REF_PISTOLA)>0){
				strCodArt = strCodArt.substring(strCodArt.indexOf(Constantes.REF_PISTOLA));
			}
			strCodArtTratado = strCodArt;

			//Si el código recibido comienza por el código de pistola, no es un código de referencia normal
			if (strCodArt.startsWith(Constantes.REF_PISTOLA)){
				//Quitamos el primer dígito
				strCodArt = strCodArt.substring(1,strCodArt.length());
				if(strCodArt.length()>13){
					pdaDatosRefResultado = this.obtenerReferenciaDesdeEan14(new Long(strCodArt));
					if (pdaDatosRefResultado.getEsError() != null && !pdaDatosRefResultado.getEsError().equals(""))
					{
						strCodArtTratado = Constantes.REF_ERRONEA_EAN;
					}else{
						strCodArtTratado = String.valueOf(pdaDatosRefResultado.getCodArt());
					}
				}else{
					if (strCodArt.length()==13 && strCodArt.startsWith(Constantes.REF_BALANZA)){
						
						//Si empieza por 26 el código de referencia recibido, se trata de balanza
						//26VVVVVPPPPPD Con el código VVVVV accedemos a V_DATOS_DIARIO_ART y obtenemos
						//el código de referencia a partir del código de balanza.
						strCodBalanza = strCodArt.substring(2,7);
						strPrecio = strCodArt.substring(7,12);
						if (StringUtils.isNumeric(strPrecio)){
							//El precio será de 3 enteros y 2 decimales
							fltPrecio = new Float(strPrecio.substring(0,3)+ "." + strPrecio.substring(3,5));
						}
	
						//Obtenemos el registro de V_DATOS_DIARIO_ART
						VDatosDiarioArt vDatosDiarioArtRes;
						VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
						vDatosDiarioArt.setBalanza(new Long(strCodBalanza));
						vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
	
						if (vDatosDiarioArtRes != null && vDatosDiarioArtRes.getCodArt() != null && !vDatosDiarioArtRes.getCodArt().equals(""))
						{
							strCodArtTratado = String.valueOf(vDatosDiarioArtRes.getCodArt());
						}
						else
						{
							strCodArtTratado = Constantes.REF_ERRONEA_EAN;
						}
					}
					else if (strCodArt.length()==13 && strCodArt.startsWith(Constantes.REF_BALANZA_ESPECIAL))
					{
						//Si empieza por 24 el código de referencia recibido, se trata de balanza especial
						//24VVVVVPPPPPD Con el código VVVVV accedemos a V_DATOS_DIARIO_ART y obtenemos
						//el código de referencia a partir del código de balanza.
						strCodBalanza = strCodArt.substring(2,7);
						strPeso = strCodArt.substring(7,12);
						if (StringUtils.isNumeric(strPrecio)){
							//El peso será de 2 enteros y 3 decimales
							fltPeso = new Float(strPeso.substring(0,2)+ "." + strPeso.substring(2,5));
						}
	
						//Obtenemos el registro de V_DATOS_DIARIO_ART
						VDatosDiarioArt vDatosDiarioArtRes;
						VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
						vDatosDiarioArt.setBalanza(new Long(strCodBalanza));
						vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
	
						if (vDatosDiarioArtRes != null && vDatosDiarioArtRes.getCodArt() != null && !vDatosDiarioArtRes.getCodArt().equals(""))
						{
							strCodArtTratado = String.valueOf(vDatosDiarioArtRes.getCodArt());
						}
						else
						{
							strCodArtTratado = Constantes.REF_ERRONEA_EAN;
						}
					}
	
					else if (strCodArt.length()==13 && strCodArt.startsWith(Constantes.REF_EROSKI))
					{
						//Si empieza por 2200 el código de referencia recibido, se trata de etiqueta propia de Eroski
						//2200NNNNNNNND, el código NNNNNNNN es el código a devolver.
						strCodArt = strCodArt.substring(4,strCodArt.length()-1);
						strCodArtTratado = strCodArt;
					}
					else
					{
						//En este caso es un EANS
						pdaDatosRefResultado = this.obtenerReferenciaDesdeEan(new Long(strCodArt));
						if (pdaDatosRefResultado.getEsError() != null && !pdaDatosRefResultado.getEsError().equals("")){
							pdaDatosRefResultado= new PdaDatosReferencia();
							pdaDatosRefResultado = this.obtenerReferenciaDesdeEan14(new Long(strCodArt));
							if (pdaDatosRefResultado.getEsError() != null && !pdaDatosRefResultado.getEsError().equals(""))
							{
								strCodArtTratado = Constantes.REF_ERRONEA_EAN;
							}else{
								strCodArtTratado = String.valueOf(pdaDatosRefResultado.getCodArt());
							}
						}else{
							strCodArtTratado = String.valueOf(pdaDatosRefResultado.getCodArt());
						}
					}
				}
			}
			
			if (strCodArtTratado.equals(Constantes.REF_ERRONEA_EAN)){
				pdaArticuloTratado.setCodigoError(strCodArtTratado);
			} else {
				Long refMadre = relacionArticuloService.findRefMadrePromocional(new Long(strCodArtTratado));
				if(refMadre != null){
					pdaArticuloTratado.setCodArtPromo(new Long(strCodArtTratado));
					pdaArticuloTratado.setCodArt(refMadre);					
				}else{
					pdaArticuloTratado.setCodArt(new Long(strCodArtTratado));					
				}				
				pdaArticuloTratado.setPrecio(Utilidades.convertirDoubleAString((fltPrecio!=null?fltPrecio.floatValue():0),"###0.000").replace("," , "."));
				pdaArticuloTratado.setKgs(Utilidades.convertirDoubleAString((fltPeso!=null?fltPeso.floatValue():0),"###0.000").replace("," , "."));
			}
		} catch (Exception e) {
			//loggerConsultas.error(StackTraceManager.getStackTrace(e));
			pdaArticuloTratado.setCodigoError(Constantes.REF_ERRONEA_EAN);
		}

		return pdaArticuloTratado;
	}
	
	private PdaDatosReferencia obtenerReferenciaDesdeEan14(Long codartEan) throws Exception{

		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosReferencia pdaDatosReferenciaRes = new PdaDatosReferencia();
		Long codArt = this.eansService.obtenerReferenciaEan14(codartEan);
		if (codArt == null || codArt == 0)
		{
			pdaDatosReferenciaRes.setEsError(this.messageSource.getMessage(
					"pda_p12_datosReferencia.noExisteReferencia", null, locale));
		}else{
			pdaDatosReferenciaRes.setCodArt(codArt);
		}

		return pdaDatosReferenciaRes;
	}
}