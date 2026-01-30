package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoPda;
import es.eroski.misumi.model.StockPlataforma;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.model.VOfertaPa;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.service.iface.DiasServicioService;
import es.eroski.misumi.service.iface.EncargosReservasService;
import es.eroski.misumi.service.iface.KosmosService;
import es.eroski.misumi.service.iface.PedidoAdicionalEService;
import es.eroski.misumi.service.iface.PedidoPdaService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockPlataformaService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.UserService;
import es.eroski.misumi.service.iface.VBloqueoEncargosPiladasService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VFestivoCentroService;
import es.eroski.misumi.service.iface.VOfertaPaService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Service(value = "PedidoPdaService")
public class PedidoPdaServiceImpl implements PedidoPdaService {
	private static Logger logger = Logger.getLogger(PedidoPdaServiceImpl.class);

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private UserService userService;

	@Autowired
	private EncargosReservasService encargosReservasService;

// Inicio codigo trasladado a "pdaP51LanzarEncargoController.java
//	@Autowired
//	private DiasServicioService diasServicioService;

//	@Autowired
//	private PedidoAdicionalEService pedidoAdicionalEService;
// FIN codigo trasladado a "pdaP51LanzarEncargoController.java

	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private RelacionArticuloService relacionArticuloService;

	@Autowired
	private VBloqueoEncargosPiladasService vBloqueoEncargosPiladasService;

	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;

//	@Autowired
//	private VOfertaPaService vOfertaPaService;

	@Autowired
	private StockPlataformaService stockPlataformaService;

	@Autowired
	private VFestivoCentroService vFestivoCentroService;

	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@Autowired
	private KosmosService kosmosService;

	/*FALTA CONTROLAR ENSEÑAR LINK SI O NO*/
	@Override
	public PedidoPda lanzarEncargoPda3(final HttpSession session,final String codArt, final String codArtCaprabo, final String descArticulo, final VDatosDiarioArt vDatosDiarioArt, HttpServletResponse response) throws Exception {
		
		// TODO Auto-generated method stub
		//55700 petición
		PedidoPda pedidoPda = new PedidoPda();
		pedidoPda.setMostrasLinkLanzarEncargo(true);
		PedidoAdicionalCompleto nuevoPedidoAdicional = new PedidoAdicionalCompleto();
		Locale locale = LocaleContextHolder.getLocale();

		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");

		//Mirar si existe el usuario
		if(null != user){
			//Buscar los OPC para mirar si el del tipo ENC-PISTOLA
			User userAux=this.userService.findPda(user);  
			if(userAux != null){
				if(userAux.getCentro() != null){
					//Si no existen OPCS, es imposible que sea de tipo ENC-PISTOLA
					if(null != userAux.getCentro().getOpcHabil()){
						//Si es de tipo ENC-PISTOLA, mirar si es de SIA o de PBL
						if(userAux.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_ENCARGO_PISTOLA_22) != -1){
							
							Boolean cargaLanzarEncargo = false; // Variable que me permite conocer si
																// se ha realizado el tratamiento en la pantalla de
																// "LANZAR ENC."

							try{			
								//PET. 48889 Nuevo por referencia - Montaje adicional de textil. 
								//Busca un artículo. Más tarde se controla que tipo de artículo es.
								VDatosDiarioArt vDatosDiarioArtCompTextil;
								if (vDatosDiarioArt==null){
									vDatosDiarioArtCompTextil = new VDatosDiarioArt();
									vDatosDiarioArtCompTextil.setCodArt(Long.parseLong(codArt));
									vDatosDiarioArtCompTextil = this.vDatosDiarioArtService.findOne(vDatosDiarioArtCompTextil);
								}else{
									vDatosDiarioArtCompTextil = vDatosDiarioArt;
								}
								
								//Para calcular la fecha de venta
								Date fechaVenta = null;

								//Se comprueba que el artículo buscado no sea nulo
								if(vDatosDiarioArtCompTextil != null){
									//Indicamos que el pedido es un encargo
									nuevoPedidoAdicional.setTipoPedido(1);
									
									//Indicamos el artículo caprabo con el que vamos a realizar el pedido
									nuevoPedidoAdicional.setCodArtCaprabo(Long.parseLong(codArtCaprabo));
									
									//Indicamos el centro para el que vamos a realizar el pedido
									nuevoPedidoAdicional.setCodCentro(user.getCentro().getCodCentro());
									
									//Indicamos la descripción del artículo
									nuevoPedidoAdicional.setDescArt(descArticulo);
									
									//Si es de SIA poner que este objeto será de tipo SIA
									nuevoPedidoAdicional.setFlgGestionaSIA("S");
									
									//Indicamos la estructura comercial
									nuevoPedidoAdicional.setCodArt(vDatosDiarioArtCompTextil.getCodArt());										
									nuevoPedidoAdicional.setGrupo1(vDatosDiarioArtCompTextil.getGrupo1());
									nuevoPedidoAdicional.setArea(vDatosDiarioArtCompTextil.getGrupo1());
									nuevoPedidoAdicional.setSeccion(vDatosDiarioArtCompTextil.getGrupo2());
									nuevoPedidoAdicional.setCategoria(vDatosDiarioArtCompTextil.getGrupo3());
									nuevoPedidoAdicional.setSubcategoria(vDatosDiarioArtCompTextil.getGrupo4());
																	
									//Calculamos VSurtidoTienda
									VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
									vSurtidoTienda.setCodCentro(nuevoPedidoAdicional.getCodCentro());
									vSurtidoTienda.setCodArt(nuevoPedidoAdicional.getCodArt());
									VSurtidoTienda surtidoAux = this.vSurtidoTiendaService.findOne(vSurtidoTienda);									
									if (null != surtidoAux){
										nuevoPedidoAdicional.setUniCajas(surtidoAux.getUniCajaServ());
										nuevoPedidoAdicional.setTipoAprovisionamiento(surtidoAux.getTipoAprov());
										nuevoPedidoAdicional.setCatalogo(surtidoAux.getCatalogo() != null ? surtidoAux.getCatalogo() : Constantes.REF_CATALOGO_BAJA);
									} else {
										nuevoPedidoAdicional.setUniCajas(new Double(1));
										nuevoPedidoAdicional.setCatalogo(Constantes.REF_CATALOGO_BAJA);
									}	
									
									//Obtenemos la ofertaPVP
									OfertaPVP ofertaPVP = null;					
									ofertaPVP = new OfertaPVP();
									ofertaPVP.setCodArticulo(nuevoPedidoAdicional.getCodArt());
									ofertaPVP.setCentro(user.getCentro().getCodCentro());
									ofertaPVP.setFecha(new Date());
									ofertaPVP = kosmosService.obtenerDatosPVP(ofertaPVP);
									
									//Si es oferta 4000 de anno 2010 o 9999 y mmc N no mostrar el link. En caso contrario, 
									//continuar con los calculos.
									if (!(surtidoAux != null && ("N").equals(surtidoAux.getMarcaMaestroCentro()) &&
											ofertaPVP != null 
											&& new Long(Constantes.OFERTA_4000).equals(ofertaPVP.getCodOferta())
											&& Constantes.OFERTA_ANNO.contains((ofertaPVP.getAnnoOferta())))){
										//Se comprueba si la referencia es TEXTIL
										if (vDatosDiarioArtCompTextil.getGrupo1() == 3) {
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

																//Indicamos que cajas es de tipo S
																nuevoPedidoAdicional.setCajas("S");

																//Si bloqueo encargo es true no se muestra lanzar encargo Inc. 63662-18.
																if(resultProc.getBloqueoEncargo()){
																	pedidoPda.setMostrasLinkLanzarEncargo(false);
																}															
															}														
														} catch  (Exception e){
															e.printStackTrace();
															nuevoPedidoAdicional.setErrorWS(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorWSValidar", null, locale));
														}
													}
												}
											}else {
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

																	//fechaVenta = resultProc.getFechaVenta();	
																	nuevoPedidoAdicional.setCajas("N");

																	//Si bloqueo encargo es true no se muestra lanzar encargo Inc. 63662-18.
																	if(resultProc.getBloqueoEncargo()){
																		pedidoPda.setMostrasLinkLanzarEncargo(false);
																	}																
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
										//Control Bloqueo Fecha Transporte
										VBloqueoEncargosPiladas vBloqueoAux = new VBloqueoEncargosPiladas();
										vBloqueoAux.setCodCentro(nuevoPedidoAdicional.getCodCentro());
										vBloqueoAux.setCodArticulo(nuevoPedidoAdicional.getCodArt());
										vBloqueoAux.setModo(Constantes.BLOQUEOS_MODO_FECHA_TRANSMISION);
										vBloqueoAux.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
										vBloqueoAux.setFechaControl(new Date());

										if (vDatosDiarioArtCompTextil.getGrupo1().equals(new Long(1))){										
											if (vDatosDiarioArtCompTextil.getGrupo2().equals(new Long(1)) || vDatosDiarioArtCompTextil.getGrupo2().equals(new Long(2)) || (vDatosDiarioArtCompTextil.getGrupo2().equals(new Long(7)) && vDatosDiarioArtCompTextil.getGrupo3().equals(new Long(13)) && !(new Long(7)).equals(vDatosDiarioArtCompTextil.getGrupo4()) && !(new Long(20)).equals(vDatosDiarioArtCompTextil.getGrupo4())) || ((new Long(6)).equals(vDatosDiarioArtCompTextil.getGrupo2()) && (new Long(19)).equals(vDatosDiarioArtCompTextil.getGrupo3()) && (new Long(4)).equals(vDatosDiarioArtCompTextil.getGrupo4()))) {
												vBloqueoAux.setEsFresco(true);
											} else {
												vBloqueoAux.setEsFresco(false);	
											}
										} else {
											vBloqueoAux.setEsFresco(false);	
										}

										VBloqueoEncargosPiladas vBloqueoRes = null;
										vBloqueoRes = this.vBloqueoEncargosPiladasService.getBloqueoFecha(vBloqueoAux);
										if (null == vBloqueoRes){
											//Datos que se van a recupera en el Validar (tanto SIA como PBL)
											Boolean bloqueoEncargo = false;
											Boolean bloqueoPilada = false;
											Boolean bloqueoDetallado = false;
											
											cargaLanzarEncargo = false; // Variable que me permite conocer si
																		// se ha realizado el tratamiento en la pantalla de
																		// "LANZAR ENC."
											
											int codigoRespuestaValidar = 1;
											String descripcionRespuestaValidar = null;

											//Se valida la referencia llamando al procedimiento de SIA
											try {
												EncargoReserva encargoReserva = new EncargoReserva();
												encargoReserva.setCodArtFormlog(nuevoPedidoAdicional.getCodArt());
												encargoReserva.setCodLoc(nuevoPedidoAdicional.getCodCentro());

												EncargoReserva resultProc = new EncargoReserva();

												//Llamamos procedimiento para obtener el resultado.
												resultProc = this.encargosReservasService.validarArticulo(encargoReserva);

												if (resultProc.getCodError() == 0) {													
													fechaVenta = resultProc.getFechaVenta();
													bloqueoEncargo = resultProc.getBloqueoEncargo();
													bloqueoPilada = resultProc.getBloqueoPilada();
													bloqueoDetallado = resultProc.getBloqueoDetallado();
													codigoRespuestaValidar = resultProc.getCodError().intValue();
													descripcionRespuestaValidar = resultProc.getDescError();

													//Si bloqueo encargo es true no se muestra lanzar encargo Inc. 63662-18.
													if(resultProc.getBloqueoEncargo()){
														pedidoPda.setMostrasLinkLanzarEncargo(false);
													}
												}															
											} catch (Exception e){
												e.printStackTrace();
												nuevoPedidoAdicional.setErrorWS(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorWSValidar", null, locale));
											}																																					

// Inicio Código trasladado a "pdaP51LanzarEncargoController.java" 
//											DiasServicio diasServicio = new DiasServicio();
//											diasServicio.setCodCentro(nuevoPedidoAdicional.getCodCentro());
//											diasServicio.setCodArt(nuevoPedidoAdicional.getCodArt());
//											diasServicio.setClasePedido(new Long(nuevoPedidoAdicional.getTipoPedido()));

//											this.diasServicioService.cargarDiasServicio(diasServicio, session.getId(), vDatosDiarioArt.getCodFpMadre(), session);
//											this.diasServicioService.actualizarDiasServicio(nuevoPedidoAdicional.getCodCentro(), nuevoPedidoAdicional.getCodArt(), vDatosDiarioArt.getCodFpMadre(), session.getId());
// FIN traslado a "pdaP51LanzarEncargoController.java" 
											
											//Si el WS no devuelve errores
											if (codigoRespuestaValidar == 0) {														
												nuevoPedidoAdicional.setFechaIniWS(fechaVenta);
												nuevoPedidoAdicional.setStrFechaIniWS(Utilidades.formatearFecha(fechaVenta));
												nuevoPedidoAdicional.setBloqueoEncargo(bloqueoEncargo);
												nuevoPedidoAdicional.setBloqueoPilada(bloqueoPilada);
												
												if (vDatosDiarioArtCompTextil.getGrupo1().equals(new Long(1))){														
													nuevoPedidoAdicional.setFrescoPuro(true);
												}else{
													nuevoPedidoAdicional.setFrescoPuro(false);
												}
												VRelacionArticulo relacionArticuloRes = null;
												VRelacionArticulo relacionArticulo = new VRelacionArticulo();

												relacionArticulo.setCodArtRela(nuevoPedidoAdicional.getCodArt());
												relacionArticulo.setCodCentro(nuevoPedidoAdicional.getCodCentro());
												relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);

												if (null != relacionArticuloRes){
													nuevoPedidoAdicional.setReferenciaUnitaria(relacionArticuloRes.getCodArt());
												}else{
													nuevoPedidoAdicional.setReferenciaUnitaria(nuevoPedidoAdicional.getCodArt());
												}
												relacionArticuloRes = null;
												relacionArticulo = new VRelacionArticulo();

												relacionArticulo.setCodArt(nuevoPedidoAdicional.getCodArt());
												relacionArticulo.setCodCentro(nuevoPedidoAdicional.getCodCentro());
												relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);
												
												if (null != relacionArticuloRes){
													nuevoPedidoAdicional.setReferenciaFFPP(relacionArticuloRes.getCodArtRela());
												}else{
													nuevoPedidoAdicional.setReferenciaFFPP(nuevoPedidoAdicional.getCodArt());
												}
												HistoricoVentaMedia historicoVentaMedia = new HistoricoVentaMedia();
												historicoVentaMedia.setCodLoc(nuevoPedidoAdicional.getCodCentro());
												historicoVentaMedia.setCodArticulo(nuevoPedidoAdicional.getReferenciaUnitaria());
												nuevoPedidoAdicional.setReferenciaNueva(false);

												if (user.getCentro().getNegocio().equals(Constantes.CENTRO_NEGOCIO_HIPER) && !vDatosDiarioArtCompTextil.getGrupo1().equals(new Long(1)) &&
														!vDatosDiarioArtCompTextil.getGrupo1().equals(new Long(2))){
													nuevoPedidoAdicional.setEsStock(true);
													Double stockPlataforma = this.obtenerStockPlataforma(nuevoPedidoAdicional.getReferenciaFFPP(), user.getCentro().getCodCentro());
													nuevoPedidoAdicional.setStockPlataforma(stockPlataforma);
												}else{
													nuevoPedidoAdicional.setEsStock(false);
												}

												//Tras hablarlo con María y a raiz de la incidencia 112, siempre se muestra el combo de cajas para encargos.
												nuevoPedidoAdicional.setConCajas(true);
												nuevoPedidoAdicional.setMinFechaIni(nuevoPedidoAdicional.getFechaIniWS());
												nuevoPedidoAdicional.setStrMinFechaIni(nuevoPedidoAdicional.getStrFechaIniWS());
												nuevoPedidoAdicional.setFechaIni(nuevoPedidoAdicional.getFechaIniWS());
												nuevoPedidoAdicional.setFechaFin(nuevoPedidoAdicional.getFechaIniWS());												

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
									
// Inicio Código trasladado a "pdaP51LanzarEncargoController.java".
//												//Hay que calcular la fecha de inicio teniendo en cuenta los días deshabilitados del calendario
//												Date fechaMinimaCalendario = nuevoPedidoAdicional.getFechaIni();
//												if(nuevoPedidoAdicional.getMinFechaIni().after(nuevoPedidoAdicional.getFechaIni())){
//													fechaMinimaCalendario = nuevoPedidoAdicional.getMinFechaIni();
//												}
// FIN traslado a "pdaP51LanzarEncargoController.java"
												
												nuevoPedidoAdicional.setCodFpMadre(vDatosDiarioArtCompTextil.getCodFpMadre());
												
												cargaLanzarEncargo = true; // Se activa para indicar que la carga de las fechas se va a realizar en la pantalla "LANZAR ENC."
												
												// Almaceno el contenido para poder usarlo en la pantalla 
												// de LANZAR ENC. y poder continuar con el tratamiento.
												pedidoPda.setPedidoAdicionalCompleto(nuevoPedidoAdicional);
												
// Inicio Código trasladado a "pdaP51LanzarEncargoController.java".
//												diasServicio.setFecha(fechaMinimaCalendario);
//												String primerDiaHabilInicio = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, vDatosDiarioArt.getCodFpMadre(), session.getId());
//												if (primerDiaHabilInicio != null){
//													nuevoPedidoAdicional.setFechaIni(Utilidades.convertirStringAFecha(primerDiaHabilInicio));
//													nuevoPedidoAdicional.setStrFechaIni(primerDiaHabilInicio);
//													if (Utilidades.convertirStringAFecha(primerDiaHabilInicio).after(fechaMinimaCalendario)){
//														nuevoPedidoAdicional.setFechaNoDisponible(true);
//													}else{
//														nuevoPedidoAdicional.setFechaNoDisponible(false);
//													}
//												}else{
//													nuevoPedidoAdicional.setFechaNoDisponible(false);
//												}
//
//												//Hay que calcular la fecha de fin teniendo en cuenta los días deshabilitados del calendario (de momento no se calcula)
//												diasServicio.setFecha(nuevoPedidoAdicional.getFechaFin());
//												String primerDiaHabilFin = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, vDatosDiarioArt.getCodFpMadre(), session.getId());
//												if (primerDiaHabilFin != null){
//													nuevoPedidoAdicional.setFechaFin(Utilidades.convertirStringAFecha(primerDiaHabilFin));
//													nuevoPedidoAdicional.setStrFechaFin(primerDiaHabilFin);
//												}
//
//												if (null != nuevoPedidoAdicional.getFrescoPuro() && nuevoPedidoAdicional.getFrescoPuro().booleanValue()){
//													this.setFechas(nuevoPedidoAdicional);
//												}
//
//												//Calculamos las unidades pedidas si la fecha minima sugerida y la fecha de primerDiaHabilitado no coinciden
//												if(nuevoPedidoAdicional.getFechaNoDisponible()){
//													//Guardará el objeto con las unidades pedidas en la fecha mínima.
//													PedidoAdicionalE pedidoAdicionalUnidades = null;
//
//													//Fecha a buscar
//													DiasServicio diaEncargoRealizado = new DiasServicio();
//													diaEncargoRealizado.setCodCentro(nuevoPedidoAdicional.getCodCentro());
//													diaEncargoRealizado.setCodArt(nuevoPedidoAdicional.getCodArt());
//													diaEncargoRealizado.setClasePedido(new Long(nuevoPedidoAdicional.getTipoPedido()));
//													diaEncargoRealizado.setFechaDesde(fechaMinimaCalendario);
//													diaEncargoRealizado.setIdsesion(session.getId()+"_"+diasServicio.getCodArt());
//
//													//Buscamos para la fecha mínima sugerida, sesión, artículo y centro, el registro que contiene el identifiacor de PBL o SIA
//													DiasServicio diasServicioRes = this.diasServicioService.findOne(diaEncargoRealizado);
//
//													//Si devuelve dato.
//													if(diasServicioRes != null){	
//														PedidoAdicionalE pedidoAdicionalE = new PedidoAdicionalE();
//
//														pedidoAdicionalE.setClasePedido(new Long(Constantes.CLASE_PEDIDO_ENCARGO));
//														pedidoAdicionalE.setCodCentro(diasServicioRes.getCodCentro());
//														pedidoAdicionalE.setCodArticulo(diasServicioRes.getCodArt());
//														pedidoAdicionalE.setIdentificador(diasServicioRes.getIdentificador());
//														pedidoAdicionalE.setIdentificadorSIA(diasServicioRes.getIdentificadorSIA());																											
//
//														//Obtenemos la lista de encargos con esa referencia, centro.
//														List<PedidoAdicionalE> pedidoAdicionalELst = this.pedidoAdicionalEService.findAll(pedidoAdicionalE, session);	
//														if(pedidoAdicionalELst != null && pedidoAdicionalELst.size() > 0){															
//															if(pedidoAdicionalE.getIdentificador() != null){
//																for(PedidoAdicionalE pedidoAdicionalElemList:pedidoAdicionalELst){
//																	if(pedidoAdicionalElemList.getIdentificador().equals(pedidoAdicionalE.getIdentificador())){
//																		pedidoAdicionalUnidades = pedidoAdicionalElemList;
//																		break;
//																	}
//																}
//															}
//															if(pedidoAdicionalE.getIdentificadorSIA() != null){
//																for(PedidoAdicionalE pedidoAdicionalElemList:pedidoAdicionalELst){
//																	if(pedidoAdicionalElemList.getIdentificadorSIA().equals(pedidoAdicionalE.getIdentificadorSIA())){
//																		pedidoAdicionalUnidades = pedidoAdicionalElemList;
//																		break;
//																	}
//																}
//															}															
//														}
//													}
//													if(pedidoAdicionalUnidades != null){
//														nuevoPedidoAdicional.setUnidadesPedidas(pedidoAdicionalUnidades.getUnidadesPedidas().longValue());
//													}
//												}
// FIN traslado a "pdaP51LanzarEncargoController.java" 
												

											//VALIDAR referencia ENCARGOS CLIENTE
											}else{
												if (null == descripcionRespuestaValidar){
													nuevoPedidoAdicional.setErrorWS(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.errorWSValidar", null, locale));
												} else {
													nuevoPedidoAdicional.setErrorWS(descripcionRespuestaValidar);
												}																																									
												this.comprobacionBloqueos(nuevoPedidoAdicional, session);
											}
											
// Se traslada la invocación y el metodo a "pdaP51LanzarEncargoController.java" y se mueve al ELSE superior que aparece arriba. 
//											this.comprobacionBloqueos(nuevoPedidoAdicional, session);
											
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
									}else{
										//MMC N y oferta 4000 con anno 2010 o 9999
										
										// MISUMI-278: 9999-4000 con MMC=NO. En lugar de bloquear el link de lanzar encargo, mostrar un mensaje indicando que para lanzar el encargo tiene que incluirlo en gama libre
										if (ofertaPVP.getAnnoOferta().intValue() == Constantes.ANIO_9999){
											Object[] argsMsgfGamaLibre = new Object[] {ofertaPVP.getTarifaStr()};
											final String msgGamaLibre = messageSource.getMessage("p52_nuevoPedidoAdicionalREF.msgGamaLibre",argsMsgfGamaLibre, locale);
											pedidoPda.setMsgGamaLibre(msgGamaLibre);
										}else{
											pedidoPda.setMostrasLinkLanzarEncargo(false);
										}
										
									}
								}else{
									pedidoPda.setMostrasLinkLanzarEncargo(false);
									nuevoPedidoAdicional.setError(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.referenciaNoExiste", null, locale));
								}
								
								if(fechaVenta == null){
									pedidoPda.setMostrasLinkLanzarEncargo(false);
								}
								
							} catch (Exception e) {
								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								throw e;
							}
							
// Se añade la condición para comprobar que no se ha realizado el "volcadoFechas" en la pantalla de "LANZAR ENC.". 
//							this.volcadoFechas(nuevoPedidoAdicional);
							if (!cargaLanzarEncargo){
								this.volcadoFechas(nuevoPedidoAdicional);
							}
							
						}else{
							pedidoPda.setMostrasLinkLanzarEncargo(false);
						}
					}else{
						pedidoPda.setMostrasLinkLanzarEncargo(false);
					}
				}else{	
					pedidoPda.setMostrasLinkLanzarEncargo(false);							
					nuevoPedidoAdicional.setError(messageSource.getMessage("p52_nuevoPedidoAdicionalREF.centroNoExiste", null, locale));
				}
			}else{			
				pedidoPda.setMostrasLinkLanzarEncargo(false);
			}
		}else{
			pedidoPda.setMostrasLinkLanzarEncargo(false);
		}			
		pedidoPda.setPedidoAdicionalCompleto(nuevoPedidoAdicional);
		return pedidoPda;
	}

	@Override
	public Integer insertarPedido(PedidoAdicionalCompleto pedidoAdicionalCompleto, PedidoAdicionalCompleto pedidoAdicionalCompletoPda,HttpSession session, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//Se inicia un contador de errores, si ocurre un error en la inserción aumentará
		//este índice y devolverá una pantalla de error.
		Integer error = 0;

		//Si el pedido adicional que se rellena en el jsp pdaP51 mediante un formulario
		//contiene datos, se rellenan los datos que faltan del pedidoAdicionalCompleto y
		//se inserta.
		if(pedidoAdicionalCompletoPda != null){

			//Se actualiza la descripción porque en caso de que sea una referencia Caprabo hay que hacer la transformación
			//de la descripción a la de Eroski

			pedidoAdicionalCompleto.setDescArt(vDatosDiarioArtService.obtenerDescripcion(pedidoAdicionalCompleto.getCodArt()));

			//Se obtiene cajas si o cajas no según lo seleccionado en pdap51
			if(pedidoAdicionalCompletoPda.getCajas() != null){
				pedidoAdicionalCompleto.setCajas(pedidoAdicionalCompletoPda.getCajas());
			}else{
				pedidoAdicionalCompleto.setCajas("N");
			}

			//Se obtiene excluir si o excluir no según lo seleccionado en pdap51
			if(pedidoAdicionalCompletoPda.getExcluir() != null){
				pedidoAdicionalCompleto.setExcluir(pedidoAdicionalCompletoPda.getExcluir());
			}else{
				pedidoAdicionalCompleto.setExcluir("N");
			}			

			//Se obtiene la cantidad de unidades pedidas
			pedidoAdicionalCompleto.setCantidad1(pedidoAdicionalCompletoPda.getCantidad1());			
			pedidoAdicionalCompleto.setTratamiento(pedidoAdicionalCompletoPda.getTratamiento());
			try{
				//Introduce el pedido en una lista. La lista se inicializa de nuevo por cada pedido
				//por lo que siempre tendrá como máximo un pedido. En principio en la aplicación web
				//permite introducir varios pedidos en un grid al hacer nuevo por referencia, y la lista
				//debería tener todos los pedidos dentro y no solo uno. Pero la empresa que ofrece los servicios
				//no permite insertarlos en modo lista, por lo que se hace uno a uno. Se ha optado por la
				//solución de la lista, por si el día de mañana permiten introducir listas en el servicio.
				//de esta forma no habría que realizar grandes cambios para que funcione.
				List<PedidoAdicionalCompleto> listAux = new ArrayList<PedidoAdicionalCompleto>();
				listAux.add(pedidoAdicionalCompleto);

				EncargosReservasLista encargosReservasLista = this.encargosReservasService.insertarPedido(listAux);
				List<EncargoReserva> listaPedidosInsertados = null;

				//Control de errores. Si hay error se incrementa la variable error y redirige a la pantalla de error
				/*if (encargosReservasLista!=null && encargosReservasLista.getDatos()!=null){
						listaPedidosInsertados = encargosReservasLista.getDatos();
					}
					for (EncargoReserva encargoReservaInsertado : listaPedidosInsertados){
						if (new Long(0).equals(encargoReservaInsertado.getCodError())){
							pedidoAdicionalCompleto.setIdSession(session.getId());
							this.tPedidoAdicionalService.deleteNuevoReferencia(pedidoAdicionalCompleto);
						} else{							
							error++;							
						}
					}*/
				if (encargosReservasLista!=null && encargosReservasLista.getDatos()!=null){
					listaPedidosInsertados = encargosReservasLista.getDatos();

					for (EncargoReserva encargoReservaInsertado : listaPedidosInsertados){
						if (new Long(0).equals(encargoReservaInsertado.getCodError())){
							pedidoAdicionalCompleto.setIdSession(session.getId());
							this.tPedidoAdicionalService.deleteNuevoReferencia(pedidoAdicionalCompleto);
						} else{
							error++;
						}
					}
				}else{
					error ++;
				}

			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				error++;
				throw e;
			}
		}
		return error;
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
			}
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

		if (nuevoPedidoReferencia.getFechaIni() != null){
			nuevoPedidoReferencia.setStrFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIni()));
		}
		if (nuevoPedidoReferencia.getFechaFin() != null){
			nuevoPedidoReferencia.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFin()));
		}
		if (nuevoPedidoReferencia.getFecha2() != null){
			nuevoPedidoReferencia.setStrFecha2(Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha2()));
		}
		if (nuevoPedidoReferencia.getFecha3() != null){
			nuevoPedidoReferencia.setStrFecha3(Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha3()));
		}
		if (nuevoPedidoReferencia.getFechaPilada() != null){
			nuevoPedidoReferencia.setStrFechaPilada(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaPilada()));
		}
		if (nuevoPedidoReferencia.getFechaIniWS() != null){
			nuevoPedidoReferencia.setStrFechaIniWS(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniWS()));
		}
		if (nuevoPedidoReferencia.getMinFechaIni() != null){
			nuevoPedidoReferencia.setStrMinFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getMinFechaIni()));
		}
		if (nuevoPedidoReferencia.getFechaIniOferta() != null){
			nuevoPedidoReferencia.setStrFechaIniOferta(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniOferta()));
		}
		if (nuevoPedidoReferencia.getFechaFinOferta() != null){
			nuevoPedidoReferencia.setStrFechaFinOferta(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFinOferta()));
		}
	}
	
}

