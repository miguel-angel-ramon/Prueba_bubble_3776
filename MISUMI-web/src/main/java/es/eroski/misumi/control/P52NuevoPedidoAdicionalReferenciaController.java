package es.eroski.misumi.control;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.Intertienda;
import es.eroski.misumi.model.ListaIntertienda;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.PedidoAdicionalNuevo;
import es.eroski.misumi.model.StockPlataforma;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.model.VOfertaPa;
import es.eroski.misumi.model.VPlanogramasPiladas;
import es.eroski.misumi.model.VReferenciasNuevasVegalsa;
import es.eroski.misumi.model.VReferenciasPedirSIA;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.ValidarReferenciaEncargo;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.AuxiliarPedidosService;
import es.eroski.misumi.service.iface.DiasServicioService;
import es.eroski.misumi.service.iface.EncargosClientePlataformaService;
import es.eroski.misumi.service.iface.EncargosReservasService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.IntertiendaService;
import es.eroski.misumi.service.iface.KosmosService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.PedidoAdicionalNuevoService;
import es.eroski.misumi.service.iface.PedidoAdicionalService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockPlataformaService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VBloqueoEncargosPiladasService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VFestivoCentroService;
import es.eroski.misumi.service.iface.VOfertaPaService;
import es.eroski.misumi.service.iface.VPlanogramasPiladasService;
import es.eroski.misumi.service.iface.VReferenciasPedirSIAService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/nuevoPedidoAdicionalReferencia")
public class P52NuevoPedidoAdicionalReferenciaController {

	private static Logger logger = Logger.getLogger(P52NuevoPedidoAdicionalReferenciaController.class);


	private PaginationManager<PedidoAdicionalCompleto> paginationManager = new PaginationManagerImpl<PedidoAdicionalCompleto>();

	private PaginationManager<Intertienda> paginationManagerStock = new PaginationManagerImpl<Intertienda>();

	@Resource
	private MessageSource messageSource;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private VOfertaPaService vOfertaPaService;

	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;

	@Autowired
	private PedidoAdicionalNuevoService pedidoAdicionalNuevoService;

	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@Autowired
	private StockTiendaService stockTiendaService;

	@Autowired
	private VFestivoCentroService vFestivoCentroService;

	@Autowired
	private VPlanogramasPiladasService vPlanogramasPiladasService;

	@Autowired
	private DiasServicioService diasServicioService;

	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;

	@Autowired
	private StockPlataformaService stockPlataformaService;

	@Autowired
	private IntertiendaService intertiendaService;

	@Autowired
	private EncargosClientePlataformaService encargosClientePlataformaService;

	@Autowired
	private RelacionArticuloService relacionArticuloService;

	@Autowired
	private VBloqueoEncargosPiladasService vBloqueoEncargosPiladasService;

	@Autowired
	private VReferenciasPedirSIAService vReferenciasPedirSIAService;

	@Autowired
	private EncargosReservasService encargosReservasService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@Autowired
	private FotosReferenciaService fotosReferenciaService;

	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;	
	
	@Autowired
	private KosmosService kosmosService;
	
	@Autowired
	private AuxiliarPedidosService auxiliarPedidosService;

	
	
	@RequestMapping(value="/loadReferencia", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalCompleto loadReferencia(@RequestBody PedidoAdicionalCompleto nuevoPedidoReferencia,
			HttpSession session, HttpServletResponse response) throws Exception {

		Locale locale = LocaleContextHolder.getLocale();
		PedidoAdicionalCompleto nuevoPedidoAdicional = new PedidoAdicionalCompleto();
		
		nuevoPedidoAdicional.setShowPopUpTextil(false);

		Long codArticuloPantalla = nuevoPedidoReferencia.getCodArt();

		try {
			User user = (User) session.getAttribute("user");
			final boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), codArticuloPantalla); // MISUMI-352.
			nuevoPedidoAdicional.setTratamientoVegalsa(tratamientoVegalsa);
			
			VReferenciasNuevasVegalsa vReferenciaNuevaVegalsa = null;
			
			PedidoAdicionalCompleto pedidoAnterior = (PedidoAdicionalCompleto) session.getAttribute("p52PedidoAnterior");

			if (null != pedidoAnterior && nuevoPedidoReferencia.getTipoPedido() != pedidoAnterior.getTipoPedido()){
				pedidoAnterior = null;
			}

			//Miramos si es un centro Caprabo, si es un centro CAPRABO. Si es un centro Caprabo, el codigo de articulo que vendra en el objeto nuevoPedidoReferencia
			//sera el codigo de articulo de Caprabo. Obtendremos el corresponiente codigo de articulo de Eroski para realizar toda la operativa.
			Long codArtCaprabo = null;
			if ((utilidadesCapraboService.esCentroCaprabo(nuevoPedidoReferencia.getCodCentro(), user.getCode())) && !(nuevoPedidoReferencia.isEsGenerica())) {//Es un centro Caprabo . si es una referencia generica no hay que traducirla.
				codArtCaprabo = nuevoPedidoReferencia.getCodArt(); 
				nuevoPedidoReferencia.setCodArtEroski(utilidadesCapraboService.obtenerCodigoEroski(nuevoPedidoReferencia.getCodCentro(), nuevoPedidoReferencia.getCodArt())); //Se guardara en un hidden en pantalla
				nuevoPedidoReferencia.setCodArt(nuevoPedidoReferencia.getCodArtEroski()); //Se utilizara para la operativa en el controlador. Siempre sera el de Eroski
				//Antes de enviarlo a pantalla se modificara con lo que venia en un inicio.

			} else {
				nuevoPedidoReferencia.setCodArtEroski(nuevoPedidoReferencia.getCodArt()); //Es el de Eroski
			}

			//Comprobación de si es una referencia NO_ALI tratada por SIA
			VReferenciasPedirSIA vReferenciasPedirSIA = new VReferenciasPedirSIA();
			vReferenciasPedirSIA.setCodCentro(nuevoPedidoReferencia.getCodCentro());
			vReferenciasPedirSIA.setCodArt(nuevoPedidoReferencia.getCodArt());
			vReferenciasPedirSIA.setTipoPedido(nuevoPedidoReferencia.getTipoPedido());

			VReferenciasPedirSIA vReferenciasPedirSIARes = this.vReferenciasPedirSIAService.findOne(vReferenciasPedirSIA); 

			if((vReferenciasPedirSIARes == null) && 
					((nuevoPedidoReferencia.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_ENCARGO))) || (nuevoPedidoReferencia.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_MONTAJE)))) ){

				nuevoPedidoAdicional.setErrorPedirSIA(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorPedirSIA", null, locale));
			}else{
				//Mira si hay que enseñar los campos cajas y excluir.
				if(vReferenciasPedirSIARes != null && Constantes.V_REFERENCIA_PEDIR_SIA_TIPO_HORIZONTE.equals(vReferenciasPedirSIARes.getTipo())){
					nuevoPedidoAdicional.setShowExcluirAndCajas(false);
				}else{
					nuevoPedidoAdicional.setShowExcluirAndCajas(true);			
				}
			}

//			boolean esReferenciaPedirSIA = vReferenciasPedirSIARes != null; 

			nuevoPedidoAdicional.setFlgGestionaSIA("S");

		//*********************************************
			// Gestión para una referencia de TEXTIL
		//*********************************************
			//PET. 48889 Nuevo por referencia - Montaje adicional de textil
			VDatosDiarioArt vDatosDiarioArtCompTextil = new VDatosDiarioArt();
			vDatosDiarioArtCompTextil.setCodArt(nuevoPedidoReferencia.getCodArt());
			vDatosDiarioArtCompTextil = this.vDatosDiarioArtService.findOne(vDatosDiarioArtCompTextil);

			if (null != vDatosDiarioArtCompTextil) {
				//Se comprueba si la referencia es TEXTIL
				if (vDatosDiarioArtCompTextil.getGrupo1() == 3) {

					// Se comprueba si la petición ha sido un MONTAJE ADICIONAL. Si es montaje comprobamos si la referencia consultada es TEXTIL
					if (nuevoPedidoReferencia.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_MONTAJE))){

						//Se consulta la vista V_REFERENCIAS_LOTE_TEXTIL
						List<Long> referenciaLoteTextil = this.relacionArticuloService.findLoteReferenciaHija(vDatosDiarioArtCompTextil.getCodArt());	

						if (referenciaLoteTextil != null) {

							for (Long articuloLote : referenciaLoteTextil) {

								if (articuloLote != null) { //Miramos si la referencia lote es una referencia valida	

									try {
										EncargoReserva encargoReserva = new EncargoReserva();
										encargoReserva.setCodArtFormlog(nuevoPedidoAdicional.getCodCentro());
										encargoReserva.setCodLoc(nuevoPedidoAdicional.getCodCentro());

										EncargoReserva resultProc = new EncargoReserva();

										//Llamamos procedimiento para obtener el resultado.
										resultProc = this.encargosReservasService.validarArticulo(encargoReserva);

										if (resultProc.getCodError() == 0) {
											//Si el procedimiento de SIA no devuelve errores, la referencia lote es valida. Damos el cambiazo y a partir de ahora la referencia
											//a consultar sera la la referencia lote
											nuevoPedidoReferencia.setCodArt(articuloLote);
											nuevoPedidoAdicional.setMantieneReferenciaTextil(false);
										}

									} catch (Exception e){
										e.printStackTrace();
										nuevoPedidoAdicional.setErrorWS(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorWSValidar", null, locale));
									}

								}

							}
						} 

					} else if (nuevoPedidoReferencia.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_ENCARGO))){

						List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoLote(vDatosDiarioArtCompTextil.getCodArt());	
						if (!referenciasMismoModeloProveedor.isEmpty()){
							nuevoPedidoAdicional.setShowPopUpTextil(true);
						}

						//PET.49333 Aviso Encargo lotes. comprobamos en la tabla V_REFERENCIAS_LOTE_TEXTIL si la referencia consultada 
						//corresponde a una referencia lote o a una hija dentro de un lote.

						//Se consulta la vista V_REFERENCIAS_LOTE_TEXTIL para comprobar si la referencia es una referencia lote.
						List<Long> referenciaLoteTextil = this.relacionArticuloService.esReferenciaLote(vDatosDiarioArtCompTextil.getCodArt());	

						if ((referenciaLoteTextil != null) && (referenciaLoteTextil.size() > 0)) {
							for (Long articuloLote : referenciaLoteTextil) {

								if (articuloLote != null) { //Miramos si la referencia lote es una referencia valida	

									try {
										EncargoReserva encargoReserva = new EncargoReserva();
										encargoReserva.setCodArtFormlog(nuevoPedidoAdicional.getCodCentro());
										encargoReserva.setCodLoc(nuevoPedidoAdicional.getCodCentro());

										EncargoReserva resultProc = new EncargoReserva();

										//Llamamos procedimiento para obtener el resultado.
										resultProc = this.encargosReservasService.validarArticulo(encargoReserva);

										if (resultProc.getCodError() == 0) {
											//a consultar sera la la referencia lote
											//Activamos el flag para mostrar el mensaje de referencia lote ypara desactivar el combo 'cajas' con el valor 'S'
											nuevoPedidoAdicional.setFlgAvisoEncargoLote("L"); //Es una referencia Lote de Textil

										}

									} catch  (Exception e){
										e.printStackTrace();
										nuevoPedidoAdicional.setErrorWS(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorWSValidar", null, locale));
									}

								}

							}
						} else {

							//Se consulta la vista V_REFERENCIAS_LOTE_TEXTIL para comprobar si la referencia es una referencia hija que pertenece a un lote.
							List<Long> referenciaHijaLoteTextil = this.relacionArticuloService.esReferenciaHijaDeLote(vDatosDiarioArtCompTextil.getCodArt());	

							if ((referenciaHijaLoteTextil != null) && (referenciaHijaLoteTextil.size() > 0)) {

								for (Long articuloHijaLote : referenciaHijaLoteTextil) {

									if (articuloHijaLote != null) { //Miramos si la referencia hija es una referencia valida	


										try {
											EncargoReserva encargoReserva = new EncargoReserva();
											encargoReserva.setCodArtFormlog(nuevoPedidoAdicional.getCodCentro());
											encargoReserva.setCodLoc(nuevoPedidoAdicional.getCodCentro());

											EncargoReserva resultProc = new EncargoReserva();

											//Llamamos procedimiento para obtener el resultado.
											resultProc = this.encargosReservasService.validarArticulo(encargoReserva);

											if (resultProc.getCodError() == 0) {
												//Activamos el flag para mostrar el mensaje de referencia hija parte de un lote y
												//para desactivar el combo 'cajas' con el valor 'N'
												nuevoPedidoAdicional.setFlgAvisoEncargoLote("H"); //Es una referencia Hija de un Lote de Textil

											}

										} catch  (Exception e){
											e.printStackTrace();
											nuevoPedidoAdicional.setErrorWS(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorWSValidar", null, locale));
										}

									}
								}
							}
						}
					}
				}
			}
		//***************************************************
			// FIN Gestión para una referencia de TEXTIL
		//***************************************************

			nuevoPedidoAdicional.setCodArt(nuevoPedidoReferencia.getCodArt());
			nuevoPedidoAdicional.setTipoPedido(nuevoPedidoReferencia.getTipoPedido());
			nuevoPedidoAdicional.setEsGenerica(nuevoPedidoReferencia.isEsGenerica());

			if (user != null && user.getCentro() != null && user.getCentro().getCodCentro() != null) {
				nuevoPedidoAdicional.setCodCentro(user.getCentro().getCodCentro());
				List<PedidoAdicionalCompleto> listaPedReferencia = (List<PedidoAdicionalCompleto>) session.getAttribute("gridP52PedidosAdicionales");
				
				if (null == listaPedReferencia || (null != listaPedReferencia && !listaPedReferencia.contains(nuevoPedidoAdicional))){
					VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
					vDatosDiarioArt.setCodArt(nuevoPedidoAdicional.getCodArt());
					VDatosDiarioArt vDatosAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
					if (null != vDatosAux) {
						nuevoPedidoAdicional.setFormatoArticulo(vDatosAux.getFormato());
						//MISUMI-270 Creamos variable MMC. Será útil para calcular el oferta PVP
						String MMC = "-1";
						
						nuevoPedidoAdicional.setCodArt(vDatosAux.getCodArt());
						if (nuevoPedidoAdicional.isEsGenerica()){
							nuevoPedidoAdicional.setDescArt(nuevoPedidoReferencia.getDescArt());
						} else {
							nuevoPedidoAdicional.setDescArt(vDatosAux.getDescripArt());
							nuevoPedidoAdicional.setDescArtEroski(vDatosAux.getDescripArt());
						}

						nuevoPedidoAdicional.setGrupo1(vDatosAux.getGrupo1());
						nuevoPedidoAdicional.setArea(vDatosAux.getGrupo1());
						nuevoPedidoAdicional.setSeccion(vDatosAux.getGrupo2());
						nuevoPedidoAdicional.setCategoria(vDatosAux.getGrupo3());
						nuevoPedidoAdicional.setSubcategoria(vDatosAux.getGrupo4());
						VBloqueoEncargosPiladas vBloqueoRes = null;
						//Control Bloqueo Fecha Transporte
						if (nuevoPedidoAdicional.getTipoPedido() <=2){
							VBloqueoEncargosPiladas vBloqueoAux = new VBloqueoEncargosPiladas();
							vBloqueoAux.setCodCentro(nuevoPedidoReferencia.getCodCentro());
							vBloqueoAux.setCodArticulo(nuevoPedidoAdicional.getCodArt());
							vBloqueoAux.setModo(Constantes.BLOQUEOS_MODO_FECHA_TRANSMISION);
							if (nuevoPedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_ENCARGO))){
								vBloqueoAux.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
							} else {
								vBloqueoAux.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_MONTAJE);
							}
							vBloqueoAux.setFechaControl(new Date());
							if (vDatosAux.getGrupo1().equals(new Long(1))){
								if (vDatosAux.getGrupo2().equals(new Long(1)) || vDatosAux.getGrupo2().equals(new Long(2)) || (vDatosAux.getGrupo2().equals(new Long(7)) && vDatosAux.getGrupo3().equals(new Long(13)) && !(new Long(7)).equals(vDatosAux.getGrupo4()) && !(new Long(20)).equals(vDatosAux.getGrupo4())) || ((new Long(6)).equals(vDatosAux.getGrupo2()) && (new Long(19)).equals(vDatosAux.getGrupo3()) && (new Long(4)).equals(vDatosAux.getGrupo4()))) {
									vBloqueoAux.setEsFresco(true);
								} else {
									vBloqueoAux.setEsFresco(false);	
								}
							} else {
								vBloqueoAux.setEsFresco(false);	
							}

							vBloqueoRes = this.vBloqueoEncargosPiladasService.getBloqueoFecha(vBloqueoAux);
						}

						if (null == vBloqueoRes){

							//Datos que se van a recupera en el Validar (tanto SIA como PBL)
							Date fechaVenta = null;
							Boolean bloqueoEncargo = false;
							Boolean bloqueoPilada = false;
							int codigoRespuestaValidar = 1;
							String descripcionRespuestaValidar = null;

							String msgUNIDADESCAJAS = Constantes.UNIDADES;
							
							//Se valida la referencia llamando al procedimiento de SIA
							try {
								EncargoReserva encargoReserva = new EncargoReserva();
								encargoReserva.setCodArtFormlog(nuevoPedidoAdicional.getCodArt());
								encargoReserva.setCodLoc(nuevoPedidoAdicional.getCodCentro());

								EncargoReserva resultProc = new EncargoReserva();

								//Si centro VEGALSA.
								if (tratamientoVegalsa){

									// Sólo se tratará como VEGALSA si el Tipo de Pedido Adicional es Montaje Adicional.
									if (nuevoPedidoReferencia.getTipoPedido() != 2){
										// No se continúa con el tratamiento.
										return nuevoPedidoAdicional;
									}else{

										// MISUMI-352
										// Obtener datos mapa, días máximos, primera fecha reposición, ...
										vReferenciaNuevaVegalsa = comprobarTratamientoVegalsa(nuevoPedidoReferencia);
											
										if (vReferenciaNuevaVegalsa == null || (vReferenciaNuevaVegalsa != null && vReferenciaNuevaVegalsa.getPrimeraFechaReposicion() == null)){
											nuevoPedidoAdicional.setErrorPedirSIA(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorPedirSIA", null, locale));
										} else {
											// Para adaptar el tratamiento VEGALSA a lo que existía para SIA, se indica
											// que no ha existido error de WS aunque para VEGALSA no se invoque al WS.
											codigoRespuestaValidar = 0;
											nuevoPedidoAdicional.setErrorPedirSIA(null);
											fechaVenta = vReferenciaNuevaVegalsa.getPrimeraFechaReposicion();
										}
									}
									
									if (new Long(Constantes.MAPA_5).equals(vReferenciaNuevaVegalsa.getCodMapa()) || new Long(Constantes.MAPA_52).equals(vReferenciaNuevaVegalsa.getCodMapa())){
										msgUNIDADESCAJAS = Constantes.CAJAS;
									}

								// Si centro EROSKI.
								} else {
									resultProc = this.encargosReservasService.validarArticulo(encargoReserva);

									if (resultProc.getCodError() == 0) {
	
//										Boolean bloqueoDetallado = false;
										
										fechaVenta = resultProc.getFechaVenta(); // TODO fecha_Venta = MAX de las tres fechas de la SELECT y esto es la fecha de inicio.
										bloqueoEncargo = resultProc.getBloqueoEncargo();
										bloqueoPilada = resultProc.getBloqueoPilada();
//										bloqueoDetallado = resultProc.getBloqueoDetallado();
										codigoRespuestaValidar = resultProc.getCodError().intValue();
										descripcionRespuestaValidar = resultProc.getDescError();
	
									} else {
										if ((resultProc.getCodError() == 4) && (nuevoPedidoReferencia.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_MONTAJE)))) {
											codigoRespuestaValidar = resultProc.getCodError().intValue();
											descripcionRespuestaValidar = messageSource.getMessage("p52_nuevoPedidoAdicionalREF.referenciaBloqueada", null, locale);
										} else {
											codigoRespuestaValidar = resultProc.getCodError().intValue();
											descripcionRespuestaValidar = resultProc.getDescError();
										}
	
									}
								}

							} catch (Exception e){
								e.printStackTrace();
								if (!tratamientoVegalsa){
									nuevoPedidoAdicional.setErrorWS(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorWSValidar", null, locale));
								}
							}

							nuevoPedidoAdicional.setMsgUNIDADESCAJAS(msgUNIDADESCAJAS);
							
							DiasServicio diasServicio = new DiasServicio();
							diasServicio.setCodCentro(nuevoPedidoAdicional.getCodCentro());
							diasServicio.setCodArt(nuevoPedidoAdicional.getCodArt());
							diasServicio.setClasePedido(new Long(nuevoPedidoAdicional.getTipoPedido()));
							if (null != nuevoPedidoReferencia.getUuid()){
								diasServicio.setUuid(nuevoPedidoReferencia.getUuid().toString());
							}
							//diasServicio.setFecha(fechaMinimaCalendario);
							this.diasServicioService.cargarDiasServicio(diasServicio, session.getId(),vDatosAux.getCodFpMadre(),session);
							if(!tratamientoVegalsa && (nuevoPedidoAdicional.getTipoPedido() ==1 || nuevoPedidoAdicional.getTipoPedido() ==2)){
								this.diasServicioService.actualizarDiasServicio(nuevoPedidoAdicional.getCodCentro(),nuevoPedidoAdicional.getCodArt(),vDatosAux.getCodFpMadre(),session.getId());
							}
							
							//Si el WS no devuelve errores
							if (codigoRespuestaValidar == 0) {
								
								nuevoPedidoAdicional.setFechaIniWS(fechaVenta);
								nuevoPedidoAdicional.setStrFechaIniWS(Utilidades.formatearFecha(fechaVenta));
								nuevoPedidoAdicional.setBloqueoEncargo(bloqueoEncargo);
								nuevoPedidoAdicional.setBloqueoPilada(bloqueoPilada);

								if (vDatosAux.getGrupo1().equals(new Long(1)) && !tratamientoVegalsa){
									if (vDatosAux.getGrupo2().equals(new Long(1)) || vDatosAux.getGrupo2().equals(new Long(2)) || (vDatosAux.getGrupo2().equals(new Long(7)) && vDatosAux.getGrupo3().equals(new Long(13)) && !(new Long(7)).equals(vDatosAux.getGrupo4()) && !(new Long(20)).equals(vDatosAux.getGrupo4())) || ((new Long(6)).equals(vDatosAux.getGrupo2()) && (new Long(19)).equals(vDatosAux.getGrupo3()) && (new Long(4)).equals(vDatosAux.getGrupo4()))) {
										// MISUMI-438 Se añade nueva validación para comprobar si es fresco puro. Si cumple con los requisitos anteriores no tiene que existir 
										// en AUXILIAR_PEDIDOS para que se siga considerabdi fresco puro
										if(this.auxiliarPedidosService.existsAuxiliarPedidos()){
											nuevoPedidoAdicional.setFrescoPuro(false);
										}else{
											nuevoPedidoAdicional.setFrescoPuro(true);
										}
									} else {
										nuevoPedidoAdicional.setFrescoPuro(false);
									}
								} else {
									nuevoPedidoAdicional.setFrescoPuro(false);
								}


								VRelacionArticulo relacionArticuloRes = null;
								VRelacionArticulo relacionArticulo = new VRelacionArticulo();

								relacionArticulo.setCodArtRela(nuevoPedidoAdicional.getCodArt());
								relacionArticulo.setCodCentro(nuevoPedidoAdicional.getCodCentro());
								relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);

								if (null != relacionArticuloRes){
									nuevoPedidoAdicional.setReferenciaUnitaria(relacionArticuloRes.getCodArt());
								} else {
									nuevoPedidoAdicional.setReferenciaUnitaria(nuevoPedidoAdicional.getCodArt());
								}
								relacionArticuloRes = null;
								relacionArticulo = new VRelacionArticulo();

								relacionArticulo.setCodArt(nuevoPedidoAdicional.getCodArt());
								relacionArticulo.setCodCentro(nuevoPedidoAdicional.getCodCentro());
								relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);
								if (null != relacionArticuloRes){
									nuevoPedidoAdicional.setReferenciaFFPP(relacionArticuloRes.getCodArtRela());
								} else {
									nuevoPedidoAdicional.setReferenciaFFPP(nuevoPedidoAdicional.getCodArt());
								}

								VOfertaPa vOfertaPa = new VOfertaPa();
								vOfertaPa.setCodCentro(nuevoPedidoAdicional.getCodCentro());
								vOfertaPa.setCodArt(nuevoPedidoAdicional.getReferenciaUnitaria());
								vOfertaPa.setFechaFin(fechaVenta);
								List<VOfertaPa> listOferta = this.vOfertaPaService.findAllVigentes(vOfertaPa);
								List<String> listaOfertas = new ArrayList<String>();
								String ofertaSelecc = "";
								if (!listOferta.isEmpty()) {
									for (VOfertaPa vOferPaAux : listOferta) {
										String oferta = vOferPaAux.getAnoOferta() + "-"+ vOferPaAux.getNumOferta();
										if (null != pedidoAnterior && oferta.equals(pedidoAnterior.getOferta())){
											nuevoPedidoAdicional.setTipoOferta(pedidoAnterior.getTipoOferta());
											nuevoPedidoAdicional.setOferta(oferta);
										} else {
											ofertaSelecc = oferta;
										}
										
										listaOfertas.add(oferta);
									}
								} else if (null != pedidoAnterior && "0".equals(pedidoAnterior.getOferta())){
									nuevoPedidoAdicional.setTipoOferta(pedidoAnterior.getTipoOferta());
									nuevoPedidoAdicional.setOferta(pedidoAnterior.getOferta());
								}
								nuevoPedidoAdicional.setListaOfertas(listaOfertas);
								HistoricoVentaMedia historicoVentaMedia = new HistoricoVentaMedia();
								historicoVentaMedia.setCodLoc(nuevoPedidoAdicional.getCodCentro());
								historicoVentaMedia.setCodArticulo(nuevoPedidoAdicional.getReferenciaUnitaria());
								nuevoPedidoAdicional.setReferenciaNueva(false); 
								
								if (!tratamientoVegalsa){
									VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
									vSurtidoTienda.setCodCentro(nuevoPedidoAdicional.getCodCentro());
									vSurtidoTienda.setCodArt(nuevoPedidoAdicional.getCodArt());
									VSurtidoTienda surtidoAux = this.vSurtidoTiendaService.findOne(vSurtidoTienda);
									if (null != surtidoAux){
										nuevoPedidoAdicional.setUniCajas(surtidoAux.getUniCajaServ());
										nuevoPedidoAdicional.setTipoAprovisionamiento(surtidoAux.getTipoAprov());
										nuevoPedidoAdicional.setCatalogo(surtidoAux.getCatalogo() != null ? surtidoAux.getCatalogo() : Constantes.REF_CATALOGO_BAJA);
										MMC = surtidoAux.getMarcaMaestroCentro();
									} else {
										nuevoPedidoAdicional.setUniCajas(new Double(1));
										nuevoPedidoAdicional.setCatalogo(Constantes.REF_CATALOGO_BAJA);
									}
								// MISUMI-352. Si Centro VEGALSA.
								} else {
									nuevoPedidoAdicional.setUniCajas(vReferenciaNuevaVegalsa.getUc());
									nuevoPedidoAdicional.setCatalogo(vReferenciaNuevaVegalsa.getCatalogoCen());
									nuevoPedidoAdicional.setTipoAprovisionamiento(vReferenciaNuevaVegalsa.getTipoAprov());
									// MISUMI-409.
									nuevoPedidoAdicional.setDiasMaximos(vReferenciaNuevaVegalsa.getDiasMaximos());
									nuevoPedidoAdicional.setCantidadMaxima(vReferenciaNuevaVegalsa.getCantidadMaxima());
								}

								try {
									ConsultarStockRequestType requestType = new ConsultarStockRequestType();
									requestType.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
									requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
									BigInteger[] listaRef = {BigInteger.valueOf(nuevoPedidoAdicional.getCodArt())}; 
									requestType.setListaCodigosReferencia(listaRef);


									ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
									paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
									ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

									if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
										logger.error("###########################################################################################################");
										logger.error("############################## CONTROLADOR: P52NuevoPedidoAdicionalReferenciaController (loadReferencia) ###");
										logger.error("###########################################################################################################");
									}

									ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
									if (responseType.getCodigoRespuesta().equals("OK")){
										for (ReferenciaType referencia : responseType.getListaReferencias()){
											if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(nuevoPedidoAdicional.getCodArt()))){
												if (referencia.getCodigoError().equals(new BigInteger("0"))){
													if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
														nuevoPedidoAdicional.setStock(referencia.getBandejas().doubleValue());
													} else {
														nuevoPedidoAdicional.setStock(referencia.getStock().doubleValue());
													}
												} else {
													nuevoPedidoAdicional.setStock(new Double(-9999));
												}
											}
										}
									} else {
										nuevoPedidoAdicional.setStock(new Double(-9999));
									}
								} catch (Exception e) {
									nuevoPedidoAdicional.setStock(new Double(-9999));
								}


								if (user.getCentro().getNegocio().equals(Constantes.CENTRO_NEGOCIO_HIPER) && !vDatosAux.getGrupo1().equals(new Long(1)) &&
										!vDatosAux.getGrupo1().equals(new Long(2)) && !tratamientoVegalsa){
									nuevoPedidoAdicional.setEsStock(true);
									Double stockPlataforma = this.obtenerStockPlataforma(nuevoPedidoAdicional.getReferenciaFFPP(), user.getCentro().getCodCentro());
									nuevoPedidoAdicional.setStockPlataforma(stockPlataforma);
								} else {
									nuevoPedidoAdicional.setEsStock(false);
								}

								//Tras hablarlo con María y a raiz de la incidencia 112, siempre se muestra el combo de cajas para encargos.
								nuevoPedidoAdicional.setConCajas(true);

								//Por último sí existe una oferta seleccionada tenemos que cargar sus fechas.
								if (!ofertaSelecc.equals("") && null == nuevoPedidoAdicional.getOferta() )
								{
									nuevoPedidoAdicional.setOferta(ofertaSelecc);
									this.loadOferta(nuevoPedidoAdicional, session, response);
								}

								nuevoPedidoAdicional.setMinFechaIni(nuevoPedidoAdicional.getFechaIniWS());
								nuevoPedidoAdicional.setStrMinFechaIni(nuevoPedidoAdicional.getStrFechaIniWS());



								/*
								 * CONTROL FECHAS
								 */
								if (null != pedidoAnterior && nuevoPedidoAdicional.getTipoPedido().equals(pedidoAnterior.getTipoPedido()) && null == nuevoPedidoReferencia.getUuid()){
									if (nuevoPedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_ENCARGO))){
										nuevoPedidoAdicional.setFechaIni(pedidoAnterior.getFechaIni());
										nuevoPedidoAdicional.setFechaFin(pedidoAnterior.getFechaIni());
									} else {
										if (null != nuevoPedidoAdicional.getOferta() && nuevoPedidoAdicional.getOferta().equals(pedidoAnterior.getOferta())){
											nuevoPedidoAdicional.setFechaIni(pedidoAnterior.getFechaIni());
											nuevoPedidoAdicional.setFechaFin(pedidoAnterior.getFechaFin());
										} else {
											if (!ofertaSelecc.equals("")){
												if (nuevoPedidoAdicional.getFechaIniWS().after(nuevoPedidoAdicional.getFechaIniOferta())) {
													nuevoPedidoAdicional.setFechaIni(nuevoPedidoAdicional.getFechaIniWS());
												} else {
													nuevoPedidoAdicional.setFechaIni(nuevoPedidoAdicional.getFechaIniOferta());
												}
												nuevoPedidoAdicional.setFechaFin(nuevoPedidoAdicional.getFechaFinOferta());
											} else {
												nuevoPedidoAdicional.setFechaIni(nuevoPedidoAdicional.getFechaIniWS());
												nuevoPedidoAdicional.setFechaFin(nuevoPedidoAdicional.getFechaIniWS());
											}
										}
									}
								} else {
									if (nuevoPedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_ENCARGO))){
										nuevoPedidoAdicional.setFechaIni(nuevoPedidoAdicional.getFechaIniWS());
										nuevoPedidoAdicional.setFechaFin(nuevoPedidoAdicional.getFechaIniWS());
									} else {
										if (!ofertaSelecc.equals("")){
											if (nuevoPedidoAdicional.getFechaIniWS().after(nuevoPedidoAdicional.getFechaIniOferta())) {
												nuevoPedidoAdicional.setFechaIni(nuevoPedidoAdicional.getFechaIniWS());
											} else {
												nuevoPedidoAdicional.setFechaIni(nuevoPedidoAdicional.getFechaIniOferta());
											}
											nuevoPedidoAdicional.setFechaFin(nuevoPedidoAdicional.getFechaFinOferta());
										} else {
											nuevoPedidoAdicional.setFechaIni(nuevoPedidoAdicional.getFechaIniWS());
											nuevoPedidoAdicional.setFechaFin(nuevoPedidoAdicional.getFechaIniWS());
										}
									}
								}

								if (nuevoPedidoAdicional.getReferenciaNueva() && null == nuevoPedidoAdicional.getOferta()){
									Integer numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA;
									if (nuevoPedidoAdicional.getBloqueoPilada()){
										numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA_BLOQUEO_PILADA;
									}
									VFestivoCentro vFestivoCentro = new VFestivoCentro();
									vFestivoCentro.setCodCentro(nuevoPedidoAdicional.getCodCentro());
									vFestivoCentro.setFechaInicio(nuevoPedidoAdicional.getFechaIni());
									nuevoPedidoAdicional.setFechaFin(Utilidades.convertirStringAFecha(this.vFestivoCentroService.getFechaFinNuevaReferencia(vFestivoCentro, numDias)));
									nuevoPedidoAdicional.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoAdicional.getFechaFin()));
								}

								//Hay que calcular la fecha de inicio teniendo en cuenta los días deshabilitados del calendario
								Date fechaMinimaCalendario = nuevoPedidoAdicional.getFechaIni();
								if(nuevoPedidoAdicional.getMinFechaIni().after(nuevoPedidoAdicional.getFechaIni())){
									fechaMinimaCalendario = nuevoPedidoAdicional.getMinFechaIni();
								}

								diasServicio.setFecha(fechaMinimaCalendario);
								String primerDiaHabilInicio = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, vDatosAux.getCodFpMadre(), session.getId());
								if (primerDiaHabilInicio != null){
									nuevoPedidoAdicional.setFechaIni(Utilidades.convertirStringAFecha(primerDiaHabilInicio));
									nuevoPedidoAdicional.setStrFechaIni(primerDiaHabilInicio);
									if (Utilidades.convertirStringAFecha(primerDiaHabilInicio).after(fechaMinimaCalendario)){
										nuevoPedidoAdicional.setFechaNoDisponible(true);
									} else {
										nuevoPedidoAdicional.setFechaNoDisponible(false);
									}
								} else {
									nuevoPedidoAdicional.setFechaNoDisponible(false);
								}

								//Hay que calcular la fecha de fin teniendo en cuenta los días deshabilitados del calendario (de momento no se calcula)
								diasServicio.setFecha(nuevoPedidoAdicional.getFechaFin());
								String primerDiaHabilFin = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, vDatosAux.getCodFpMadre(), session.getId());
								if (primerDiaHabilFin != null){
									nuevoPedidoAdicional.setFechaFin(Utilidades.convertirStringAFecha(primerDiaHabilFin));
									nuevoPedidoAdicional.setStrFechaFin(primerDiaHabilFin);
								}

								if (null != nuevoPedidoAdicional.getFrescoPuro() && nuevoPedidoAdicional.getFrescoPuro().booleanValue()){
									this.setFechas(nuevoPedidoAdicional);
								}
							} else {
								if (null == descripcionRespuestaValidar){
									nuevoPedidoAdicional.setErrorWS(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorWSValidar", null, locale));
								} else {
									nuevoPedidoAdicional.setErrorWS(descripcionRespuestaValidar);
								}

							}
							if (nuevoPedidoAdicional.getTipoPedido() == 3){
								ValidarReferenciaEncargo validarReferenciaEncargo = new ValidarReferenciaEncargo();
								validarReferenciaEncargo.setCodCentro(user.getCentro().getCodCentro());
								if ((utilidadesCapraboService.esCentroCaprabo(nuevoPedidoReferencia.getCodCentro(), user.getCode())) && !(nuevoPedidoReferencia.isEsGenerica())) {//Es un centro Caprabo . si es una referencia generica no hay que traducirla.
									validarReferenciaEncargo.setCodReferencia(codArtCaprabo);
								} else {
									validarReferenciaEncargo.setCodReferencia(nuevoPedidoAdicional.getCodArt());
								}

								validarReferenciaEncargo.setGenerico(nuevoPedidoAdicional.isEsGenerica()?"S":"N");
								ValidarReferenciaEncargo referenciaEncargoRes = this.encargosClientePlataformaService.validarReferencia(validarReferenciaEncargo, user.getCode());
								nuevoPedidoAdicional.setFormatoArticulo(referenciaEncargoRes.getTipoReferencia());
								if (!referenciaEncargoRes.getCodError().equals(0)){
									nuevoPedidoAdicional.setBloqueoEncargoCliente("S");
									nuevoPedidoAdicional.setErrorValidar(referenciaEncargoRes.getDescError());
								} else {
									nuevoPedidoAdicional.setBloqueoEncargoCliente("N");
									nuevoPedidoAdicional.setFechasVenta(referenciaEncargoRes.getFechasVenta());
									nuevoPedidoAdicional.setPrimeraFechaEntrega(referenciaEncargoRes.getFechaEntrega());
									nuevoPedidoAdicional.setFlgEspec(referenciaEncargoRes.getFlgEspec());
								}
								if (null != pedidoAnterior){
									nuevoPedidoAdicional.setNombreCliente(pedidoAnterior.getNombreCliente());
									nuevoPedidoAdicional.setApellidoCliente(pedidoAnterior.getApellidoCliente());
									nuevoPedidoAdicional.setTelefonoCliente(pedidoAnterior.getTelefonoCliente());
									nuevoPedidoAdicional.setContactoCentro(pedidoAnterior.getContactoCentro());

								}
							}
							if (!tratamientoVegalsa){ // MISUMI-369: Si es Vegalsa no se hace la comprobacion de bloqueos
								//Comprobación de bloqueos
								VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
								vSurtidoTienda.setCodCentro(nuevoPedidoAdicional.getCodCentro());
								vSurtidoTienda.setCodArt(nuevoPedidoAdicional.getCodArt());
								VSurtidoTienda surtidoAux = this.vSurtidoTiendaService.findOne(vSurtidoTienda);
								if (null != surtidoAux){
									nuevoPedidoAdicional.setUniCajas(surtidoAux.getUniCajaServ());
									nuevoPedidoAdicional.setTipoAprovisionamiento(surtidoAux.getTipoAprov());
									nuevoPedidoAdicional.setCatalogo(surtidoAux.getCatalogo() != null ? surtidoAux.getCatalogo() : Constantes.REF_CATALOGO_BAJA);
									MMC = surtidoAux.getMarcaMaestroCentro();
									if(surtidoAux.getPedir().equals("N") && nuevoPedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_MONTAJE))){
										codigoRespuestaValidar = 1;
										nuevoPedidoAdicional.setErrorWS(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.referenciaBloqueada", null, locale));
									}
									
								} else {
									nuevoPedidoAdicional.setUniCajas(new Double(1));
									nuevoPedidoAdicional.setCatalogo(Constantes.REF_CATALOGO_BAJA);
								}
							}
							try {
								ConsultarStockRequestType requestType = new ConsultarStockRequestType();
								requestType.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
								requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
								BigInteger[] listaRef = {BigInteger.valueOf(nuevoPedidoAdicional.getCodArt())}; 
								requestType.setListaCodigosReferencia(listaRef);


								ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
								paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
								ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

								if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
									logger.error("###########################################################################################################");
									logger.error("############################## CONTROLADOR: p13ReferenciasCentroController (loadReferencia) ########");
									logger.error("###########################################################################################################");
								}

								ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
								if (responseType.getCodigoRespuesta().equals("OK")){
									for (ReferenciaType referencia : responseType.getListaReferencias()){
										if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(nuevoPedidoAdicional.getCodArt()))){
											if (referencia.getCodigoError().equals(new BigInteger("0"))){
												if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
													nuevoPedidoAdicional.setStock(referencia.getBandejas().doubleValue());
												} else {
													nuevoPedidoAdicional.setStock(referencia.getStock().doubleValue());
												}
											} else {
												nuevoPedidoAdicional.setStock(new Double(-9999));
											}
										}
									}
								} else {
									nuevoPedidoAdicional.setStock(new Double(-9999));
								}
							} catch (Exception e) {
								nuevoPedidoAdicional.setStock(new Double(-9999));
							}


							this.comprobacionBloqueos(nuevoPedidoAdicional, session);
						} else {
							String fechaIni = Utilidades.obtenerFechaFormateada(vBloqueoRes.getFecIni());
							String fechaFin = Utilidades.obtenerFechaFormateada(vBloqueoRes.getFecFin());
							String tipoPedido = messageSource.getMessage("p52_nuevoPedidoAdicionalREF."+nuevoPedidoAdicional.getTipoPedido().toString(), null, locale);
							List<Object> args = new ArrayList<Object>();
							args.add(tipoPedido);
							args.add(fechaIni);
							args.add(fechaFin);
							nuevoPedidoAdicional.getTipoPedido();
							nuevoPedidoAdicional.setError(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.referenciaBloqueadaTransmision", args.toArray(), locale));
						}
						//MISUMI-270. Solo miramos en kosmos para esos dos tipos de pedido, ya que solo en esos dos tipos de pedido se tiene
						//que sacar mensaje de error si la oferta es 4000 y mmc = N. 
						if (!tratamientoVegalsa){
							
							if (nuevoPedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_ENCARGO)) || nuevoPedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_ENCARGO_CLIENTE))){
								//Si MMC es -1 no ha sido buscado todavía. Lo buscamos.
								if (MMC != null && MMC.equals("-1")){
									VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
									vSurtidoTienda.setCodCentro(nuevoPedidoAdicional.getCodCentro());
									vSurtidoTienda.setCodArt(nuevoPedidoAdicional.getCodArt());
									VSurtidoTienda surtidoAux = this.vSurtidoTiendaService.findOne(vSurtidoTienda);
	
									MMC = surtidoAux != null ? surtidoAux.getMarcaMaestroCentro() : null;
								}
	
								if (MMC != null && MMC.equals("N")){
									//Queremos que sea para todos los centros. 20/10/2020 Inc.Misumi-244.
									OfertaPVP ofertaPVP = null;	
									ofertaPVP = new OfertaPVP();
									ofertaPVP.setCentro(user.getCentro().getCodCentro());
									ofertaPVP.setCodArticulo(nuevoPedidoAdicional.getCodArt());
									Date fechaHoy = new Date();
									ofertaPVP.setFecha(fechaHoy);
									ofertaPVP  = this.kosmosService.obtenerDatosPVP(ofertaPVP);
									if (null != ofertaPVP && new Long(Constantes.OFERTA_4000).equals(ofertaPVP.getCodOferta())){
										if(Constantes.OFERTA_ANNO.contains((ofertaPVP.getAnnoOferta()))){
											nuevoPedidoAdicional.setOfertaErr(messageSource.getMessage("p50_nuevoPedidoAdicional.mensajeOferta." + ofertaPVP.getAnnoOferta().toString(), null, locale));
											
											//Si el texto tiene ${0} se sustituye
											nuevoPedidoAdicional.setOfertaErr(nuevoPedidoAdicional.getOfertaErr().replace("${0}", ofertaPVP.getTarifaStr()));
										} 
									}  
								}
							}
						}
					} else {
						nuevoPedidoAdicional.setError(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.referenciaNoExiste", null, locale));
					}
				} else {
					nuevoPedidoAdicional.setError(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.referenciaExistente", null, locale));
				}
			} else {
				nuevoPedidoAdicional.setError(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.centroNoExiste", null, locale));
			}					

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		this.volcadoFechas(nuevoPedidoAdicional);

		//Antes de devolver a pantalla, tenemos que volver a poner el codigo de articulo que venia inicialmente de pantalla. Tambien cargamos el CodArtEroski
		nuevoPedidoAdicional.setCodArt(codArticuloPantalla);
		nuevoPedidoAdicional.setCodArtEroski(nuevoPedidoReferencia.getCodArtEroski());

		//Comprobar si tiene Foto
		FotosReferencia fotosReferencia = new FotosReferencia();
		fotosReferencia.setCodReferencia(nuevoPedidoAdicional.getCodArtEroski());
		if (fotosReferenciaService.checkImage(fotosReferencia)){
			nuevoPedidoAdicional.setTieneFoto("S");
		} else {
			nuevoPedidoAdicional.setTieneFoto("N");
		}

		User user = (User) session.getAttribute("user");

		//Si estamos en Caprabo, tambien tenemos que obtener la descripción de Caprabo
		if ((utilidadesCapraboService.esCentroCaprabo(nuevoPedidoReferencia.getCodCentro(), user.getCode())) && !(nuevoPedidoReferencia.isEsGenerica())) {//Es un centro Caprabo . si es una referencia generica no hay que traducirla.
			nuevoPedidoAdicional.setDescArt(utilidadesCapraboService.obtenerDescArtCaprabo(nuevoPedidoAdicional.getCodArt()));		
		}

		return nuevoPedidoAdicional;
	}

	private VReferenciasNuevasVegalsa comprobarTratamientoVegalsa(PedidoAdicionalCompleto nuevoPedidoReferencia) {
		VReferenciasNuevasVegalsa referenciasNuevasVegalsa = pedidoAdicionalNuevoService.getDatosValidacionVegalsa(new PedidoAdicionalNuevo(nuevoPedidoReferencia.getCodCentro(), nuevoPedidoReferencia.getCodArt()));
		return referenciasNuevasVegalsa;
	}

	@RequestMapping(value="/obtainReferencia", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalCompleto obtainReferencia(@RequestBody PedidoAdicionalCompleto nuevoPedidoReferencia,
			HttpSession session, HttpServletResponse response) throws Exception {

		PedidoAdicionalCompleto nuevoPedidoAdicional = null;
		try {
			List<PedidoAdicionalCompleto> listaPedReferencia = (List<PedidoAdicionalCompleto>) session.getAttribute("gridP52PedidosAdicionales");
			nuevoPedidoAdicional = listaPedReferencia.get(listaPedReferencia.indexOf(nuevoPedidoReferencia));
			VOfertaPa vOfertaPa = new VOfertaPa();
			vOfertaPa.setCodCentro(nuevoPedidoAdicional.getCodCentro());
			vOfertaPa.setCodArt(nuevoPedidoAdicional.getReferenciaUnitaria());
			vOfertaPa.setFechaFin(nuevoPedidoAdicional.getFechaIniWS());
			List<String> listaOfertas = new ArrayList<String>();
			List<VOfertaPa> listOferta = this.vOfertaPaService.findAllVigentes(vOfertaPa);
			for (VOfertaPa vOferPaAux : listOferta) {
				String oferta = vOferPaAux.getAnoOferta() + "-" + vOferPaAux.getNumOferta();
				listaOfertas.add(oferta);
			}
			nuevoPedidoAdicional.setListaOfertas(listaOfertas);
			this.volcadoFechas(nuevoPedidoAdicional);
			//nuevoPedidoAdicional.setEstado("1");
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return nuevoPedidoAdicional;
	}

	@RequestMapping(value="/obtainStockPlataforma", method = RequestMethod.POST)
	public @ResponseBody Double obtainStockPlataforma(@RequestBody PedidoAdicionalCompleto nuevoPedidoReferencia,
			HttpSession session, HttpServletResponse response) throws Exception {

		Double stockPlataforma = null;
		try {
			nuevoPedidoReferencia.getCajas();
			Long codArt = null;
			if (nuevoPedidoReferencia.getCajas().equals("S")){
				codArt = nuevoPedidoReferencia.getReferenciaUnitaria();
			} else {
				codArt = nuevoPedidoReferencia.getReferenciaFFPP();
			}
			stockPlataforma = this.obtenerStockPlataforma(codArt, nuevoPedidoReferencia.getCodCentro());
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return stockPlataforma;
	}

	@RequestMapping(value="/deleteDataGrid", method = RequestMethod.POST)
	public @ResponseBody int deleteDataGrid(@RequestBody PedidoAdicionalCompleto nuevoPedidoReferencia,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			List<PedidoAdicionalCompleto> listaPedReferencia = (List<PedidoAdicionalCompleto>) session.getAttribute("gridP52PedidosAdicionales");
			nuevoPedidoReferencia.setIdSession(session.getId());
			int correcto=this.tPedidoAdicionalService.deleteNuevoReferencia(nuevoPedidoReferencia);
			listaPedReferencia.remove(nuevoPedidoReferencia);
			return correcto;
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value="/loadOferta", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalCompleto loadOferta(@RequestBody PedidoAdicionalCompleto nuevoPedidoReferencia,
			HttpSession session, HttpServletResponse response) throws Exception {

		try {	

			if (nuevoPedidoReferencia.getOferta().equals("0")){
				nuevoPedidoReferencia.setTipoOferta(new Long(1));
				nuevoPedidoReferencia.setFechaIni(nuevoPedidoReferencia.getFechaIniWS());
				nuevoPedidoReferencia.setStrFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIni()));
				if (nuevoPedidoReferencia.getReferenciaNueva()){
					Integer numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA;
					if (nuevoPedidoReferencia.getBloqueoPilada()){
						numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA_BLOQUEO_PILADA;
					}
					VFestivoCentro vFestivoCentro = new VFestivoCentro();
					vFestivoCentro.setCodCentro(nuevoPedidoReferencia.getCodCentro());
					vFestivoCentro.setFechaInicio(nuevoPedidoReferencia.getFechaIni());
					nuevoPedidoReferencia.setFechaFin(Utilidades.convertirStringAFecha(this.vFestivoCentroService.getFechaFinNuevaReferencia(vFestivoCentro, numDias)));
					nuevoPedidoReferencia.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFin()));
				} else {
					nuevoPedidoReferencia.setFechaFin(nuevoPedidoReferencia.getFechaIniWS());
					nuevoPedidoReferencia.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFin()));
				}
			} else {
				User user = (User) session.getAttribute("user");
				VOfertaPa vOferta = new VOfertaPa();
				vOferta.setCodCentro(user.getCentro().getCodCentro());
				String[] oferta = nuevoPedidoReferencia.getOferta().split("-");
				vOferta.setAnoOferta(new Long(oferta[0]));
				vOferta.setNumOferta(new Long(oferta[1]));
				vOferta.setCodArt(nuevoPedidoReferencia.getReferenciaUnitaria());
				VOfertaPa vOfertaAux = this.vOfertaPaService.findOneVigente(vOferta);
				nuevoPedidoReferencia.setTipoOferta(vOfertaAux.getTipoOferta());
				nuevoPedidoReferencia.setFechaIniOferta(vOfertaAux.getFechaIni());
				nuevoPedidoReferencia.setStrFechaIniOferta(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniOferta()));
				nuevoPedidoReferencia.setFechaFinOferta(vOfertaAux.getFechaFin());
				nuevoPedidoReferencia.setStrFechaFinOferta(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFinOferta()));
				nuevoPedidoReferencia.setFechaFin(nuevoPedidoReferencia.getFechaFinOferta());
				nuevoPedidoReferencia.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFinOferta()));
				if (null != nuevoPedidoReferencia.getFechaIniOferta() && nuevoPedidoReferencia.getFechaIniOferta().after(nuevoPedidoReferencia.getFechaIniWS())){
					nuevoPedidoReferencia.setFechaIni(nuevoPedidoReferencia.getFechaIniOferta()); 
					nuevoPedidoReferencia.setStrFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniOferta()));
				} else {
					nuevoPedidoReferencia.setFechaIni(nuevoPedidoReferencia.getFechaIniWS()); 
					nuevoPedidoReferencia.setStrFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniWS()));
				}
			}

			DiasServicio diasServicio = new DiasServicio();
			diasServicio.setCodCentro(nuevoPedidoReferencia.getCodCentro());
			diasServicio.setCodArt(nuevoPedidoReferencia.getCodArt());
			diasServicio.setClasePedido(new Long(nuevoPedidoReferencia.getTipoPedido()));
			if (null != nuevoPedidoReferencia.getUuid()){
				diasServicio.setUuid(nuevoPedidoReferencia.getUuid().toString());
			}
			diasServicio.setFecha(nuevoPedidoReferencia.getFechaIni());
			this.diasServicioService.cargarDiasServicio(diasServicio, session.getId(), null, session);
			if(!nuevoPedidoReferencia.isTratamientoVegalsa() && (nuevoPedidoReferencia.getTipoPedido() ==1 || nuevoPedidoReferencia.getTipoPedido() ==2)){
				this.diasServicioService.actualizarDiasServicio(nuevoPedidoReferencia.getCodCentro(),nuevoPedidoReferencia.getCodArt(), null, session.getId());
			}
			String primerDiaHabilInicio = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, null, session.getId());
			if (primerDiaHabilInicio != null){
				nuevoPedidoReferencia.setFechaIni(Utilidades.convertirStringAFecha(primerDiaHabilInicio));
				nuevoPedidoReferencia.setStrFechaIni(primerDiaHabilInicio);
				if (Utilidades.convertirStringAFecha(primerDiaHabilInicio).after(diasServicio.getFecha())){
					nuevoPedidoReferencia.setFechaNoDisponible(true);
				} else {
					nuevoPedidoReferencia.setFechaNoDisponible(false);
				}
			}



			//Hay que calcular la fecha de fin teniendo en cuenta los días deshabilitados del calendario (de momento no se calcula)
			diasServicio.setFecha(nuevoPedidoReferencia.getFechaFin());
			String primerDiaHabilFin = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, null, session.getId());
			if (primerDiaHabilFin != null){
				nuevoPedidoReferencia.setFechaFin(Utilidades.convertirStringAFecha(primerDiaHabilFin));
				nuevoPedidoReferencia.setStrFechaFin(primerDiaHabilFin);
			}
			/****Incidencia 96, al cambiar de oferta se tienen que mostrar siempre las fechas propias de la oferta y no las
			fechas mayores de las ofertas, se cambia tras hablarlo con María.****/
			//if (null == nuevoPedidoReferencia.getFechaFin()){
			//	nuevoPedidoReferencia.setFechaFin(nuevoPedidoReferencia.getFechaFinOferta());
			//	nuevoPedidoReferencia.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFinOferta()));
			//}
			//if (nuevoPedidoReferencia.getFechaFin().before(nuevoPedidoReferencia.getFechaFinOferta())){

			//}
			//if (nuevoPedidoReferencia.getFechaIni().before(nuevoPedidoReferencia.getFechaIniOferta())){

			//}
			/*if (nuevoPedidoReferencia.getMinFechaIni().before(nuevoPedidoReferencia.getFechaIniOferta())){
				nuevoPedidoReferencia.setMinFechaIni(nuevoPedidoReferencia.getFechaIniOferta());
				nuevoPedidoReferencia.setStrMinFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniOferta()));
			}*/

			if (null != nuevoPedidoReferencia.getFrescoPuro() && nuevoPedidoReferencia.getFrescoPuro()){
				this.setFechas(nuevoPedidoReferencia);
			}/* else {
				this.setImplantacionInicial(nuevoPedidoReferencia);
			}*/
			this.volcadoFechas(nuevoPedidoReferencia);

			//Comprobación de bloqueos
			this.comprobacionBloqueos(nuevoPedidoReferencia, session);

		} catch (Exception e) {		  
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return nuevoPedidoReferencia;
	}

	@RequestMapping(value="/loadDates", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalCompleto loadDates(@RequestBody PedidoAdicionalCompleto nuevoPedidoReferencia,
			HttpSession session, HttpServletResponse response) throws Exception {


		try {	
			if ((null != nuevoPedidoReferencia.getReferenciaNueva() && nuevoPedidoReferencia.getReferenciaNueva().booleanValue())  && (null == nuevoPedidoReferencia.getOferta() || nuevoPedidoReferencia.getOferta().equals("0"))){
				Integer numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA;
				if (nuevoPedidoReferencia.getBloqueoPilada()){
					numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA_BLOQUEO_PILADA;
				}
				VFestivoCentro vFestivoCentro = new VFestivoCentro();
				vFestivoCentro.setCodCentro(nuevoPedidoReferencia.getCodCentro());
				vFestivoCentro.setFechaInicio(nuevoPedidoReferencia.getFechaIni());
				nuevoPedidoReferencia.setFechaFin(Utilidades.convertirStringAFecha(this.vFestivoCentroService.getFechaFinNuevaReferencia(vFestivoCentro, numDias)));
				nuevoPedidoReferencia.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFin()));
			}

			if (null != nuevoPedidoReferencia.getFrescoPuro() && nuevoPedidoReferencia.getFrescoPuro()){
				this.setFechas(nuevoPedidoReferencia);
			} /*else {
				this.setImplantacionInicial(nuevoPedidoReferencia);
			}*/

			//Comprobación de bloqueos
			this.comprobacionBloqueos(nuevoPedidoReferencia, session);

		} catch (Exception e) {		  
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return nuevoPedidoReferencia;
	}

	@RequestMapping(value="/recalcularDiasHabilitados", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalCompleto recalcularDiasHabilitados(@RequestBody PedidoAdicionalCompleto nuevoPedidoReferencia,
			HttpSession session, HttpServletResponse response) throws Exception {


		try {	
			if (nuevoPedidoReferencia.getReferenciaNueva() && (null == nuevoPedidoReferencia.getOferta() || nuevoPedidoReferencia.getOferta().equals("0"))){

				Integer numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA;
				if (nuevoPedidoReferencia.getBloqueoPilada()){
					numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA_BLOQUEO_PILADA;
				}
				VFestivoCentro vFestivoCentro = new VFestivoCentro();
				vFestivoCentro.setCodCentro(nuevoPedidoReferencia.getCodCentro());
				vFestivoCentro.setFechaInicio(nuevoPedidoReferencia.getFechaIni());
				nuevoPedidoReferencia.setFechaFin(Utilidades.convertirStringAFecha(this.vFestivoCentroService.getFechaFinNuevaReferencia(vFestivoCentro, numDias)));
				nuevoPedidoReferencia.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFin()));
			}

			//Hay que calcular la fecha de inicio teniendo en cuenta los días deshabilitados del calendario
			Date fechaMinimaCalendario = nuevoPedidoReferencia.getFechaIni();
			if(nuevoPedidoReferencia.getMinFechaIni().after(nuevoPedidoReferencia.getFechaIni())){
				fechaMinimaCalendario = nuevoPedidoReferencia.getMinFechaIni();
			}

			DiasServicio diasServicio = new DiasServicio();
			diasServicio.setCodCentro(nuevoPedidoReferencia.getCodCentro());
			diasServicio.setCodArt(nuevoPedidoReferencia.getCodArt());
			diasServicio.setClasePedido(new Long(nuevoPedidoReferencia.getTipoPedido()));
			if (null != nuevoPedidoReferencia.getUuid()){
				diasServicio.setUuid(nuevoPedidoReferencia.getUuid().toString());
			}
			diasServicio.setFecha(fechaMinimaCalendario);
			this.diasServicioService.cargarDiasServicio(diasServicio, session.getId(), null, session);
			String primerDiaHabilInicio = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, null, session.getId());
			if (primerDiaHabilInicio != null){
				nuevoPedidoReferencia.setFechaIni(Utilidades.convertirStringAFecha(primerDiaHabilInicio));
				nuevoPedidoReferencia.setStrFechaIni(primerDiaHabilInicio);
				if (Utilidades.convertirStringAFecha(primerDiaHabilInicio).after(fechaMinimaCalendario)){
					nuevoPedidoReferencia.setFechaNoDisponible(true);
				} else {
					nuevoPedidoReferencia.setFechaNoDisponible(false);
				}
			} else {
				nuevoPedidoReferencia.setFechaNoDisponible(false);
			}

			//Hay que calcular la fecha de fin teniendo en cuenta los días deshabilitados del calendario (de momento no se calcula)
			diasServicio.setFecha(nuevoPedidoReferencia.getFechaFin());
			String primerDiaHabilFin = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, null, session.getId());
			if (primerDiaHabilFin != null){
				nuevoPedidoReferencia.setFechaFin(Utilidades.convertirStringAFecha(primerDiaHabilFin));
				nuevoPedidoReferencia.setStrFechaFin(primerDiaHabilFin);
			}

			if (nuevoPedidoReferencia.getFrescoPuro()){
				this.setFechas(nuevoPedidoReferencia);
			} else {
				//this.setImplantacionInicial(nuevoPedidoReferencia);
			}

		} catch (Exception e) {		  
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return nuevoPedidoReferencia;
	}

	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<PedidoAdicionalCompleto> loadDataGrid(
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
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
			List<PedidoAdicionalCompleto> list = (List<PedidoAdicionalCompleto>) session.getAttribute("gridP52PedidosAdicionales");


			Page<PedidoAdicionalCompleto> result = null;
			if (null != list){
				Integer startIndex = max.intValue() * (page.intValue()-1);
				Integer endIndex = max.intValue() * (page.intValue());
				if (endIndex > list.size()){
					endIndex = list.size();
				}
				List<PedidoAdicionalCompleto> sublista = list.subList(startIndex, endIndex);
				int records = list.size();
				result = this.paginationManager.paginate(new Page<PedidoAdicionalCompleto>(), sublista,
						max.intValue(), records, page.intValue());	
			} else {
				result = new Page<PedidoAdicionalCompleto>();
			}
			return result;
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value = "/saveDataGrid", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> saveDataGrid( HttpServletResponse response
														  , HttpSession session
														  ) throws Exception{

		Map<String, Integer> resultado = new HashMap<String, Integer>();
		try {

			User user = (User) session.getAttribute("user");

			List<PedidoAdicionalCompleto> list = (List<PedidoAdicionalCompleto>) session.getAttribute("gridP52PedidosAdicionales");
			Integer total = list.size();
			Integer error = 0;
			List<PedidoAdicionalCompleto> listClone = new ArrayList<PedidoAdicionalCompleto>(list);
			for(PedidoAdicionalCompleto pedido : listClone){
			
				// MISUMI-352
				// Comprobar si se trata de un centro-referencia VEGALSA para darle un tratamiento u otro.
				final boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), pedido.getCodArt());
				// Sólo si tiene tratamiento VEGALSA y el Tipo de Pedido es Montaje Adicional(2).
				if (tratamientoVegalsa && pedido.getTipoPedido().equals(2)){
					String respuesta = null;
					PedidoAdicionalNuevo pedidoAdicionalNuevoSave = new PedidoAdicionalNuevo( user.getCentro().getCodCentro(), pedido.getCodArt()
																							, "0".equals(pedido.getOferta())?"":pedido.getOferta(), pedido.getStrFechaIni()
																							, pedido.getStrFechaFin(), pedido.getImplantacionMinima()
																							, user.getCode()
																							);
					try{
						pedidoAdicionalNuevoService.savePedidoAdicionalNuevo(pedidoAdicionalNuevoSave);
						if (list != null) {
							list.remove(pedido);
						}
					} catch (Exception e){
						respuesta = "Error al guardar el pedido adicional.";
						int indice = list.indexOf(pedido);
						if (list != null) {
							if (indice != -1) {
								PedidoAdicionalCompleto pedidoReferencia = list.get(indice);
								pedidoReferencia.setEstado("2");
								pedidoReferencia.setDescError(respuesta);
							}
						}
						pedido.setEstado("2");
						error++;
					}
						
				} else {

					if (pedido.getTipoPedido().equals(3)){
						EncargosClienteLista encargosLista = this.encargosClientePlataformaService.insertarReferencia(pedido, user.getCode());
						if (null!= encargosLista && encargosLista.getEstado().equals(new Long(0)) && 
								encargosLista.getDatos().get(0).getCodError().equals(new Long(0))) {
							if (list != null) {
								list.remove(pedido);
							}
						} else {
							if (list != null) {
								int indice = list.indexOf(pedido);
								if (indice != -1) {
									PedidoAdicionalCompleto pedidoReferencia = list.get(indice);
									pedidoReferencia.setEstado("2");
									String respuesta = null;
									if (null!= encargosLista){
										if (null != encargosLista.getDescEstado()){
											respuesta = encargosLista.getDescEstado();
										} else {
											respuesta = encargosLista.getDatos().get(0).getDescError();
										}
									} else {
										respuesta = "Error al guardar el encargo";
									}
		
									pedidoReferencia.setDescError(respuesta);
									error++;
								}
							}
						}
						
					} else {
		
						//Comprobación de si es una referencia NO_ALI tratada por SIA
						//VReferenciasPedirSIA vReferenciasPedirSIA = new VReferenciasPedirSIA();
						//vReferenciasPedirSIA.setCodCentro(pedido.getCodCentro());
						//vReferenciasPedirSIA.setCodArt(pedido.getCodArt());
						//boolean esReferenciaPedirSIA = this.vReferenciasPedirSIAService.tratarReferenciaSIA(vReferenciasPedirSIA); 
		
						List<PedidoAdicionalCompleto> listAux = new ArrayList<PedidoAdicionalCompleto>();
						listAux.add(pedido);
		
						//if (esReferenciaPedirSIA){
		
						EncargosReservasLista encargosReservasLista = this.encargosReservasService.insertarPedido(listAux);
						List<EncargoReserva> listaPedidosInsertados = null;
						if (encargosReservasLista!=null && encargosReservasLista.getDatos()!=null){
							listaPedidosInsertados = encargosReservasLista.getDatos();
						}

						if (null!=listaPedidosInsertados){
							for (EncargoReserva encargoReservaInsertado : listaPedidosInsertados){
								if (new Long(0).equals(encargoReservaInsertado.getCodError())){
									if (list != null) {
										pedido.setIdSession(session.getId());
										this.tPedidoAdicionalService.deleteNuevoReferencia(pedido);
										list.remove(pedido);
									}
								} else{
									if (list != null) {
										int indice = list.indexOf(pedido);
										if (indice != -1) {
											PedidoAdicionalCompleto pedidoReferencia = list.get(indice);
											pedidoReferencia.setEstado("2");
											pedidoReferencia.setDescError(encargoReservaInsertado.getDescError());
											error++;
										}
									}
								}
							}
						} else {
							pedido.setEstado("2");
							error++;		
						}				
					}
				}
				resultado.put("total", total);
				resultado.put("error", error);
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		
		return resultado;
	}

	@RequestMapping(value = "/addDataGrid", method = RequestMethod.POST)
	public @ResponseBody String addDataGrid(
			@RequestBody PedidoAdicionalCompleto nuevoPedidoReferencia,
			HttpServletResponse response,
			HttpSession session) throws Exception{


		Long codArticuloPantalla = nuevoPedidoReferencia.getCodArt();
		
		User user = (User) session.getAttribute("user");
		// MISUMI-352
		final Boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), codArticuloPantalla);
		if (tratamientoVegalsa){
			nuevoPedidoReferencia.setIdentificadorVegalsa(0L);
		}
		
		nuevoPedidoReferencia.setCodArtGrid(nuevoPedidoReferencia.getCodArt());//Lo que viene de pantalla sera lo se muestre en el grid en la columna Referencia
		nuevoPedidoReferencia.setDescArtGrid(nuevoPedidoReferencia.getDescArt());//Lo que viene de pantalla sera lo se muestre en el grid en la columna denominacion

		if (utilidadesCapraboService.esCentroCaprabo(nuevoPedidoReferencia.getCodCentro(), user.getCode())) {//Es un centro Caprabo


			nuevoPedidoReferencia.setCodArtEroski(utilidadesCapraboService.obtenerCodigoEroski(nuevoPedidoReferencia.getCodCentro(), nuevoPedidoReferencia.getCodArt())); //Se guardara en un hidden en pantalla
			nuevoPedidoReferencia.setCodArt(nuevoPedidoReferencia.getCodArtEroski()); //Se utilizara para la operativa en el controlador. Siempre sera el de Eroski
			//Antes de enviarlo a pantalla se modificara con lo que venia en un inicio.

			nuevoPedidoReferencia.setDescArt(nuevoPedidoReferencia.getDescArtEroski()); //Se utilizara para la operativa en el controlador. Siempre sera el de Eroski
			//Antes de enviarlo a pantalla se modificara con lo que venia en un inicio.

		} else {
			nuevoPedidoReferencia.setCodArtEroski(nuevoPedidoReferencia.getCodArt()); //Es el de Eroski
			nuevoPedidoReferencia.setDescArtEroski(nuevoPedidoReferencia.getDescArt()); 
		}


		this.volcadoFechas(nuevoPedidoReferencia);

		Locale locale = LocaleContextHolder.getLocale();
		Boolean valido = true;
		String errorMsg = null;
		if (nuevoPedidoReferencia.getTipoPedido().equals(new Integer(2)) && !nuevoPedidoReferencia.getFrescoPuro()){
			//TODO validar Capacidad Maxima
			if ( nuevoPedidoReferencia.getCapacidadMaxima() > 9999){
				//Incidencia 119 se muestra el mensaje pero se deja seguir
				valido = false;

				//errorMsg = messageSource.getMessage("p52_nuevoPedidoAdicionalREF.implantacionInicialError", null, locale);
				errorMsg = "1";
			}
			if (valido){
				DateTime fIni = new DateTime(nuevoPedidoReferencia.getFechaIni());
				DateTime fFin = new DateTime(nuevoPedidoReferencia.getFechaFin());
				Integer days = Days.daysBetween(fIni.toDateMidnight() , fFin.toDateMidnight() ).getDays(); 
				if (days > 7){
					if (nuevoPedidoReferencia.getImplantacionMinima() < (nuevoPedidoReferencia.getCapacidadMaxima() * 0.2) ||  nuevoPedidoReferencia.getImplantacionMinima() > 9999){
						//Incidencia 119 se muestra el mensaje pero se deja seguir
						valido = false;

						//errorMsg = messageSource.getMessage("p52_nuevoPedidoAdicionalREF.implantacionFinalError", null, locale);
						errorMsg = "2";
					}
				} else {
					nuevoPedidoReferencia.setImplantacionMinima(nuevoPedidoReferencia.getCapacidadMaxima().doubleValue());
				}
			}
			nuevoPedidoReferencia.setCantidad1(null);
			nuevoPedidoReferencia.setCantidad2(null);
			nuevoPedidoReferencia.setCantidad3(null);
		} else if (nuevoPedidoReferencia.getTipoPedido().equals(new Integer(1))) {
			nuevoPedidoReferencia.setCantidad2(null);
			nuevoPedidoReferencia.setCantidad3(null);
			nuevoPedidoReferencia.setCapacidadMaxima(null);
			nuevoPedidoReferencia.setImplantacionMinima(null);
		} else {
			nuevoPedidoReferencia.setCapacidadMaxima(null);
			nuevoPedidoReferencia.setImplantacionMinima(null);
		}
		if (valido){
			if(nuevoPedidoReferencia.getTipoPedido().equals(new Integer(2))){
				VPlanogramasPiladas vPlanogramasPiladas = new VPlanogramasPiladas();
				vPlanogramasPiladas.setCodCentro(nuevoPedidoReferencia.getCodCentro());
				vPlanogramasPiladas.setCodArt(nuevoPedidoReferencia.getCodArt());
				vPlanogramasPiladas.setfInicio(nuevoPedidoReferencia.getFechaIni());
				vPlanogramasPiladas.setfFin(nuevoPedidoReferencia.getFechaFin());
				Long total = this.vPlanogramasPiladasService.findDatosCabecera(vPlanogramasPiladas);
				if (!total.equals(new Long(0))) {
					valido = false;

					errorMsg = messageSource.getMessage("p52_nuevoPedidoAdicionalREF.cabeceraPilada", null, locale);
				}
			}
		}

		if (valido){

			nuevoPedidoReferencia.setUser(user.getCode());
			nuevoPedidoReferencia.setPerfil(Constantes.PERFIL_CENTRO);

			PedidoAdicionalCompleto pedidoAnterior = new PedidoAdicionalCompleto();
			pedidoAnterior.setFechaIni(nuevoPedidoReferencia.getFechaIni());
			pedidoAnterior.setTipoPedido(nuevoPedidoReferencia.getTipoPedido());
			if (pedidoAnterior.getTipoPedido().equals(new Integer(2))){
				pedidoAnterior.setOferta(nuevoPedidoReferencia.getOferta());
				pedidoAnterior.setFechaFin(nuevoPedidoReferencia.getFechaFin());
				pedidoAnterior.setTipoOferta(nuevoPedidoReferencia.getTipoOferta());
			} else if (pedidoAnterior.getTipoPedido().equals(new Integer(3))){
				pedidoAnterior.setNombreCliente(nuevoPedidoReferencia.getNombreCliente());
				pedidoAnterior.setApellidoCliente(nuevoPedidoReferencia.getApellidoCliente());
				pedidoAnterior.setTelefonoCliente(nuevoPedidoReferencia.getTelefonoCliente());
				pedidoAnterior.setContactoCentro(nuevoPedidoReferencia.getContactoCentro());
			}


			session.setAttribute("p52PedidoAnterior", pedidoAnterior);
			if (null ==  session.getAttribute("gridP52PedidosAdicionales")){
				List<PedidoAdicionalCompleto> listaPedReferencia = new ArrayList<PedidoAdicionalCompleto>();
				nuevoPedidoReferencia.setUuid(UUID.randomUUID());
				nuevoPedidoReferencia.setIdSession(session.getId());
				if (!nuevoPedidoReferencia.getTipoPedido().equals(Constantes.TIPO_PEDIDO_ENCARGO_CLIENTE.intValue())){
					this.tPedidoAdicionalService.insertNuevoReferencia(nuevoPedidoReferencia);
				}


				listaPedReferencia.add(nuevoPedidoReferencia);
				session.setAttribute("gridP52PedidosAdicionales", listaPedReferencia);
			} else {
				List<PedidoAdicionalCompleto> listaPedReferencia = (List<PedidoAdicionalCompleto>) session.getAttribute("gridP52PedidosAdicionales");
				Boolean modificar = false;
				if (null != nuevoPedidoReferencia.getEstado() && nuevoPedidoReferencia.getEstado().equals("1")){
					modificar = true;
				}
				if (modificar){
					Integer indexOf = listaPedReferencia.indexOf(nuevoPedidoReferencia);
					if (indexOf == -1){
						nuevoPedidoReferencia.setEstado(null);
						nuevoPedidoReferencia.setIdSession(session.getId());
						if (!nuevoPedidoReferencia.getTipoPedido().equals(Constantes.TIPO_PEDIDO_ENCARGO_CLIENTE.intValue())){
							this.tPedidoAdicionalService.insertNuevoReferencia(nuevoPedidoReferencia);
						}
						listaPedReferencia.add(0,nuevoPedidoReferencia);
					} else {
						nuevoPedidoReferencia.setIdSession(session.getId());
						if (!nuevoPedidoReferencia.getTipoPedido().equals(Constantes.TIPO_PEDIDO_ENCARGO_CLIENTE.intValue())){
							this.tPedidoAdicionalService.updateNuevoReferencia(nuevoPedidoReferencia);
						} else {
							this.tPedidoAdicionalService.deleteNuevoReferencia(nuevoPedidoReferencia);
						}

						listaPedReferencia.set(indexOf,nuevoPedidoReferencia);
					}
				} else {
					nuevoPedidoReferencia.setUuid(UUID.randomUUID());
					nuevoPedidoReferencia.setIdSession(session.getId());
					if (!nuevoPedidoReferencia.getTipoPedido().equals(Constantes.TIPO_PEDIDO_ENCARGO_CLIENTE.intValue())){
						this.tPedidoAdicionalService.insertNuevoReferencia(nuevoPedidoReferencia);
					}
					listaPedReferencia.add(0,nuevoPedidoReferencia);
				}

			}
		}

		return errorMsg;
	}

	@RequestMapping(value = "/getImplantacionInicial", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalCompleto getImplantacionInicial(
			@RequestBody PedidoAdicionalCompleto nuevoPedidoReferencia,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		//this.setImplantacionInicial(nuevoPedidoReferencia);

		return nuevoPedidoReferencia;
	}

	@RequestMapping(value = "/cleanGrid", method = RequestMethod.POST)
	public  void cleanGrid(HttpServletResponse response,
			HttpSession session) throws Exception{
		User user = (User) session.getAttribute("user");
		if (null != user.getCentro()){
			TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
			tPedidoAdicional.setIdSesion(session.getId());
			tPedidoAdicional.setCodCentro(user.getCentro().getCodCentro());
			tPedidoAdicional.setPantalla(Constantes.PANTALLA_NUEVO_REFERENCIA);
			this.tPedidoAdicionalService.deleteAllNuevoReferencia(tPedidoAdicional);
			session.removeAttribute("gridP52PedidosAdicionales");
			session.removeAttribute("p52PedidoAnterior");
		}
	}
	private void volcadoFechas(PedidoAdicionalCompleto nuevoPedidoReferencia) throws Exception{
		nuevoPedidoReferencia.setStrFechaIni(null);
		nuevoPedidoReferencia.setStrFechaFin(null);
		nuevoPedidoReferencia.setStrFecha2(null);
		nuevoPedidoReferencia.setStrFecha3(null);
		nuevoPedidoReferencia.setStrFechaPilada(null);
		nuevoPedidoReferencia.setStrFechaIniWS(null);
		nuevoPedidoReferencia.setStrMinFechaIni(null);
		nuevoPedidoReferencia.setStrFechaIniOferta(null);
		nuevoPedidoReferencia.setStrFechaFinOferta(null);

		if (nuevoPedidoReferencia.getFechaIni() != null)
		{
			nuevoPedidoReferencia.setStrFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIni()));
		} else {
			nuevoPedidoReferencia.setStrFechaIni(null);
		}

		if (nuevoPedidoReferencia.getFechaFin() != null)
		{
			nuevoPedidoReferencia.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFin()));
		} else {
			nuevoPedidoReferencia.setStrFechaFin(null);
		}
		if (nuevoPedidoReferencia.getFecha2() != null)
		{
			nuevoPedidoReferencia.setStrFecha2(Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha2()));
		} else {
			nuevoPedidoReferencia.setStrFecha2(null);
		}
		if (nuevoPedidoReferencia.getFecha3() != null)
		{
			nuevoPedidoReferencia.setStrFecha3(Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha3()));
		} else {
			nuevoPedidoReferencia.setStrFechaIniOferta(null);
		}
		if (nuevoPedidoReferencia.getFechaPilada() != null)
		{
			nuevoPedidoReferencia.setStrFechaPilada(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaPilada()));
		} else {
			nuevoPedidoReferencia.setStrFechaIniOferta(null);
		}
		if (nuevoPedidoReferencia.getFechaIniWS() != null)
		{
			nuevoPedidoReferencia.setStrFechaIniWS(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniWS()));
		} else {

		}
		if (nuevoPedidoReferencia.getMinFechaIni() != null)
		{
			nuevoPedidoReferencia.setStrMinFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getMinFechaIni()));
		} else {
			nuevoPedidoReferencia.setStrFechaIniOferta(null);
		}
		if (nuevoPedidoReferencia.getFechaIniOferta() != null)
		{
			nuevoPedidoReferencia.setStrFechaIniOferta(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniOferta()));
		} else {
			nuevoPedidoReferencia.setStrFechaIniOferta(null);
		}
		if (nuevoPedidoReferencia.getFechaFinOferta() != null)
		{
			nuevoPedidoReferencia.setStrFechaFinOferta(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFinOferta()));
		} else {
			nuevoPedidoReferencia.setStrFechaFinOferta(null);
		}
	}
	private void setFechas(PedidoAdicionalCompleto nuevoPedidoReferencia) throws Exception{
		List<Map<String,Object>> listaFechasSiguientes = null;
		VFestivoCentro vFestivoCentro = new VFestivoCentro();
		vFestivoCentro.setCodCentro(nuevoPedidoReferencia.getCodCentro());
		vFestivoCentro.setFechaInicio(nuevoPedidoReferencia.getFechaIni());
		vFestivoCentro.setFechaFin(nuevoPedidoReferencia.getFechaFin());


		listaFechasSiguientes = this.vFestivoCentroService.getNextDays(vFestivoCentro, Constantes.NUM_DIAS_INTERVALO_FRESCOS_MODIFICACION);

		if ( null != nuevoPedidoReferencia.getFechaFin()){
			if (listaFechasSiguientes != null && listaFechasSiguientes.size() > 0) {//Fecha2
				Map<String,Object> m = listaFechasSiguientes.get(0);
				String strFecha2 =  (String) m.get("SIGUIENTE_DIA");
				Date fecha2 = Utilidades.convertirStringAFecha(strFecha2);
				nuevoPedidoReferencia.setFecha2(fecha2);
				nuevoPedidoReferencia.setStrFecha2(strFecha2);

				if (listaFechasSiguientes.size() > 1) {//Fecha3
					m = listaFechasSiguientes.get(1);
					String strFecha3 =  (String) m.get("SIGUIENTE_DIA");
					Date fecha3 = Utilidades.convertirStringAFecha(strFecha3);
					nuevoPedidoReferencia.setFecha3(fecha3);
					nuevoPedidoReferencia.setStrFecha3(strFecha3);

					if (listaFechasSiguientes.size() > 2) {//Fecha Pilada{
						m = listaFechasSiguientes.get(2);
						String strFechaPilada =  (String) m.get("SIGUIENTE_DIA");
						Date fechaPilada = Utilidades.convertirStringAFecha(strFechaPilada);
						nuevoPedidoReferencia.setFechaPilada(fechaPilada);
						nuevoPedidoReferencia.setStrFechaPilada(strFechaPilada);

					}else{
						nuevoPedidoReferencia.setFechaPilada(null);
						nuevoPedidoReferencia.setStrFechaPilada(null);
					}
				}else{
					nuevoPedidoReferencia.setFecha3(null);
					nuevoPedidoReferencia.setStrFecha3(null);
					nuevoPedidoReferencia.setFechaPilada(null);
					nuevoPedidoReferencia.setStrFechaPilada(null);
				}
			}else{
				nuevoPedidoReferencia.setFecha2(null);
				nuevoPedidoReferencia.setFecha3(null);
				nuevoPedidoReferencia.setStrFecha2(null);
				nuevoPedidoReferencia.setStrFecha3(null);
				nuevoPedidoReferencia.setFechaPilada(null);
				nuevoPedidoReferencia.setStrFechaPilada(null);
			}
		}else{
			nuevoPedidoReferencia.setFecha2(null);
			nuevoPedidoReferencia.setFecha3(null);
			nuevoPedidoReferencia.setStrFecha2(null);
			nuevoPedidoReferencia.setStrFecha3(null);
			nuevoPedidoReferencia.setFechaPilada(null);
			nuevoPedidoReferencia.setStrFechaPilada(null);
		}

	}

	/*private void setImplantacionInicial(PedidoAdicionalCompleto nuevoPedidoAdicional) throws Exception{
		DateTime dt = new DateTime(nuevoPedidoAdicional.getFechaFin()).withMillisOfDay(0);
		if (dt.isBefore(nuevoPedidoAdicional.getFechaIni().getTime())){
			nuevoPedidoAdicional.setFechaFin(nuevoPedidoAdicional.getFechaIni());
		}
		ImplantacionInicial implantacionInicial = new ImplantacionInicial();
		implantacionInicial.setCodArticulo(nuevoPedidoAdicional.getCodArt());
		implantacionInicial.setCodLoc(nuevoPedidoAdicional.getCodCentro());
		implantacionInicial.setFechaInicio(nuevoPedidoAdicional.getFechaIni());
		implantacionInicial.setFechaFin(nuevoPedidoAdicional.getFechaFin());
		ImplantacionInicial implantacionInicialAux = this.implantacionInicialService.getImplantacionInicial(implantacionInicial);
		nuevoPedidoAdicional.setCapacidadMaxima(implantacionInicialAux.getVentaMedia());
		DateTime fIni = new DateTime(nuevoPedidoAdicional.getFechaIni());
		DateTime fFin = new DateTime(nuevoPedidoAdicional.getFechaFin());
		Integer days = Days.daysBetween(fIni.toDateMidnight() , fFin.toDateMidnight() ).getDays(); 
		if (days > 7){
			BigDecimal capacidad = new BigDecimal(nuevoPedidoAdicional.getCapacidadMaxima());
			BigDecimal perctg = new BigDecimal(0.2);
			BigDecimal implantacionMinima = capacidad.multiply(perctg);
			BigDecimal im = implantacionMinima.setScale(0, RoundingMode.UP);
			nuevoPedidoAdicional.setImplantacionMinima(im.doubleValue());
		}
	}*/

	@RequestMapping(value="/loadDataStock", method = RequestMethod.GET)
	public @ResponseBody Page<Intertienda> loadDataStock(
			@RequestParam(value = "codArt", required = true) Long codArt,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false, defaultValue = "") String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		Page<Intertienda> resultado = new Page<Intertienda>();

		User user = (User) session.getAttribute("user");
		Intertienda intertienda = new Intertienda();
		intertienda.setCodReferencia(codArt);
		intertienda.setDescProvinciaSession(user.getCentro().getProvincia());
		intertienda.setCodZonaSession(user.getCentro().getCodZona());
		intertienda.setCodRegionSession(user.getCentro().getCodRegion());
		intertienda.setNegocio(Constantes.CENTRO_NEGOCIO_HIPER);
		try{
			Pagination pagination= new Pagination(max,page);

			if (index!=null && !"".equals(index)){
				pagination.setSort(index);
			}
			if (sortOrder!=null){
				pagination.setAscDsc(sortOrder);
			}
			ListaIntertienda listaIntertienda = null;

			//Obtenemos la lista de Intertienda
			listaIntertienda = this.obtenerListaIntertienda(intertienda, pagination, session);

			//Montaje de la lista paginada
			if (listaIntertienda != null && listaIntertienda.getLista() != null && listaIntertienda.getNumeroRegistros() != null) {
				List<Intertienda> lista = listaIntertienda.getLista();
				int records = listaIntertienda.getNumeroRegistros().intValue();
				resultado = this.paginationManagerStock.paginate(new Page<Intertienda>(), lista, max.intValue(), records, page.intValue());
			}
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return resultado;

	}

	@RequestMapping(value="/loadStockTitle", method = RequestMethod.GET)
	public @ResponseBody String loadStockTitle(
			@RequestParam(value = "codArt", required = true) Long codArt,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		String resultado = null;
		try{
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(codArt);
			VDatosDiarioArt vDatosAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
			if (null != vDatosAux){
				resultado = vDatosAux.getCodArt() + " " + vDatosAux.getDescripArt();
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return resultado;

	}

	private ListaIntertienda obtenerListaIntertienda(Intertienda intertienda, Pagination pagination,  HttpSession session) throws Exception {

		ListaIntertienda listaIntertienda = new ListaIntertienda();

		try {
			//Inicializo resultado
			listaIntertienda.setLista(new ArrayList<Intertienda>());
			listaIntertienda.setNumeroRegistros(new Long(0));

			//Calculo la lista de Intertienda
			listaIntertienda = this.intertiendaService.listCentroIntertienda(intertienda, pagination);

			//Creo la intertienda para cada centro
			List<Intertienda> lista = listaIntertienda.getLista();
			if (lista != null && lista.size() > 0){
				for (int i = 0; i < lista.size(); i++){
					Intertienda inter = lista.get(i);
					Centro cen = lista.get(i).toCentro();
					Long referencia = intertienda.getCodReferencia();

					//Calculo el stock actual del centro y lo añado a la intertienda
					Double stock = new Double(0);
					if (inter.isExisteArticulo()){
						try {
							stock = this.obtenerStock(referencia, cen, session);
						} catch (Exception e) {
							//Si se produce error lo controlo
							lista.get(i).setDescError("stock");
						}
					}
					lista.get(i).setStock(stock);

				}
			}
			if (lista != null){
				listaIntertienda.setLista(lista);
			}
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			throw e;
		}

		return listaIntertienda;
	}

	private Double obtenerStock(Long codArticulo, Centro centro,  HttpSession session) throws Exception {

		Double stock = new Double(0);

		try {
			ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(centro.getCodCentro()));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
			BigInteger[] listaRef = {BigInteger.valueOf(codArticulo)}; 
			requestType.setListaCodigosReferencia(listaRef);

			User user = (User) session.getAttribute("user");
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: p13ReferenciasCentroController (obtenerStock)	 ###############");
				logger.error("###########################################################################################################");
			} 

			ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
			if (responseType.getCodigoRespuesta().equals("OK")){
				for (ReferenciaType referencia : responseType.getListaReferencias()){
					if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(codArticulo))){
						if (referencia.getCodigoError().equals(new BigInteger("0"))){
							if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								stock =referencia.getBandejas().doubleValue();
							} else {
								stock =  referencia.getStock().doubleValue();
							}
						} else {
							throw new Exception();
						}
					}
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw e;
		}


		return stock;
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

	private void comprobacionBloqueos(PedidoAdicionalCompleto nuevoPedidoReferencia, HttpSession session) throws Exception{
		int numeroRegistrosBloqueo = 0;
		int numeroRegistrosBloqueoLeyenda = 0;
		if (nuevoPedidoReferencia.getTipoPedido() != null){
			User user = (User) session.getAttribute("user");
			VBloqueoEncargosPiladas vBloqueoEncargosPiladas = new VBloqueoEncargosPiladas();
			vBloqueoEncargosPiladas.setCodCentro(user.getCentro().getCodCentro());
			vBloqueoEncargosPiladas.setCodArticulo(nuevoPedidoReferencia.getCodArt());
			vBloqueoEncargosPiladas.setFecIniDDMMYYYY((nuevoPedidoReferencia.getFechaIni()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIni()):""));
			vBloqueoEncargosPiladas.setFecha2DDMMYYYY((nuevoPedidoReferencia.getFecha2()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha2()):""));
			vBloqueoEncargosPiladas.setFecha3DDMMYYYY((nuevoPedidoReferencia.getFecha3()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha3()):""));
			vBloqueoEncargosPiladas.setFecha4DDMMYYYY((nuevoPedidoReferencia.getFecha4()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha4()):""));
			vBloqueoEncargosPiladas.setFecha5DDMMYYYY((nuevoPedidoReferencia.getFecha5()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha5()):""));
			vBloqueoEncargosPiladas.setFechaInPilDDMMYYYY((nuevoPedidoReferencia.getFechaPilada()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaPilada()):""));
			vBloqueoEncargosPiladas.setFecFinDDMMYYYY((nuevoPedidoReferencia.getFechaFin()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFin()):""));

			VBloqueoEncargosPiladas vBloqueoEncargosPiladasLeyenda = new VBloqueoEncargosPiladas();
			vBloqueoEncargosPiladasLeyenda.setCodCentro(user.getCentro().getCodCentro());
			vBloqueoEncargosPiladasLeyenda.setCodArticulo(nuevoPedidoReferencia.getCodArt());

			if (Constantes.CLASE_PEDIDO_ENCARGO.equals(nuevoPedidoReferencia.getTipoPedido().toString()) && null == nuevoPedidoReferencia.getErrorWS()){
				vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
				numeroRegistrosBloqueo = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
				if (numeroRegistrosBloqueo > 0){
					nuevoPedidoReferencia.setFechaBloqueoEncargo("S");
				}else{
					nuevoPedidoReferencia.setFechaBloqueoEncargo("N");
				}
				nuevoPedidoReferencia.setFechaBloqueoEncargoPilada("N");



				//Para el control de mostrar las leyendas de bloqueo (Solo se mostraran las leyendas cuando un articulo este o ha estado blqueado, independientemente de las fechas)
				vBloqueoEncargosPiladasLeyenda.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
				numeroRegistrosBloqueoLeyenda = this.vBloqueoEncargosPiladasService.registrosRefBloqueadaCont(vBloqueoEncargosPiladasLeyenda).intValue();

				if (numeroRegistrosBloqueoLeyenda > 0){
					nuevoPedidoReferencia.setMostrarLeyendaBloqueo("S");
				}else{
					nuevoPedidoReferencia.setMostrarLeyendaBloqueo("N");
				}



			}else if (Constantes.CLASE_PEDIDO_MONTAJE.equals(nuevoPedidoReferencia.getTipoPedido().toString())  && null == nuevoPedidoReferencia.getErrorWS()){
				if(null != nuevoPedidoReferencia.getFrescoPuro() && nuevoPedidoReferencia.getFrescoPuro()){
					//Se guarda la fecha de fin para restaurala tras la búsqueda
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
					numeroRegistrosBloqueo = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
					if (numeroRegistrosBloqueo > 0){
						nuevoPedidoReferencia.setFechaBloqueoEncargo("S");
					}else{
						nuevoPedidoReferencia.setFechaBloqueoEncargo("N");
					}

					//Bloqueos de pilada
					vBloqueoEncargosPiladas.setFecFinDDMMYYYY(fechaFinMontaje);
					vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_MONTAJE);
					numeroRegistrosBloqueo = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
					if (numeroRegistrosBloqueo > 0){
						nuevoPedidoReferencia.setFechaBloqueoEncargoPilada("S");
					}else{
						nuevoPedidoReferencia.setFechaBloqueoEncargoPilada("N");
					}


					//Para el control de mostrar las leyendas de bloqueo (Solo se mostraran las leyendas cuando un articulo este o ha estado blqueado, independientemente de las fechas)
					vBloqueoEncargosPiladasLeyenda.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
					numeroRegistrosBloqueoLeyenda = this.vBloqueoEncargosPiladasService.registrosRefBloqueadaCont(vBloqueoEncargosPiladasLeyenda).intValue();

					if (numeroRegistrosBloqueoLeyenda == 0) {
						// Si no hay bloqueos para encargos, comprobaos si hay bloqueos para montajes
						vBloqueoEncargosPiladasLeyenda.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_MONTAJE);
						numeroRegistrosBloqueoLeyenda = this.vBloqueoEncargosPiladasService.registrosRefBloqueadaCont(vBloqueoEncargosPiladasLeyenda).intValue();
					}

					if (numeroRegistrosBloqueoLeyenda > 0){
						nuevoPedidoReferencia.setMostrarLeyendaBloqueo("S");
					}else{
						nuevoPedidoReferencia.setMostrarLeyendaBloqueo("N");
					}



				}else{//Alimentación
					//Bloqueos de pilada
					vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_MONTAJE);
					numeroRegistrosBloqueo = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
					if (numeroRegistrosBloqueo > 0){
						nuevoPedidoReferencia.setFechaBloqueoEncargoPilada("S");
					}else{
						nuevoPedidoReferencia.setFechaBloqueoEncargoPilada("N");
					}


					//Para el control de mostrar las leyendas de bloqueo (Solo se mostraran las leyendas cuando un articulo este o ha estado blqueado, independientemente de las fechas)
					vBloqueoEncargosPiladasLeyenda.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_MONTAJE);
					numeroRegistrosBloqueoLeyenda = this.vBloqueoEncargosPiladasService.registrosRefBloqueadaCont(vBloqueoEncargosPiladasLeyenda).intValue();

					if (numeroRegistrosBloqueoLeyenda > 0){
						nuevoPedidoReferencia.setMostrarLeyendaBloqueo("S");
					}else{
						nuevoPedidoReferencia.setMostrarLeyendaBloqueo("N");
					}


				}
			}
		}
	}
}
