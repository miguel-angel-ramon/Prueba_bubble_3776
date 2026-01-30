package es.eroski.misumi.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.FotosReferenciaDao;
import es.eroski.misumi.dao.iface.ImagenComercialDao;
import es.eroski.misumi.dao.iface.SacadaRestosDao;
import es.eroski.misumi.dao.iface.StockTiendaDao;
//import es.eroski.misumi.dao.iface.VRotacionRefDao;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaSacadaResto;
import es.eroski.misumi.model.pda.PdaSacadaRestosForm;
import es.eroski.misumi.model.pda.PdaSacadaRestosOferta;
import es.eroski.misumi.model.pda.PdaSacadaRestosStock;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.KosmosService;
import es.eroski.misumi.service.iface.OfertaVigenteService;
import es.eroski.misumi.service.iface.PlanogramaCentroService;
import es.eroski.misumi.service.iface.PlanogramaVigenteService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.SacadaRestosService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VRotacionRefService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Service
public class SacadaRestosServiceImpl implements SacadaRestosService {

//	private static Logger logger = Logger.getLogger(SacadaRestosServiceImpl.class);

	@Autowired
	private SacadaRestosDao sacadaRestosDao;
	
	@Autowired
	private FotosReferenciaDao fotosReferenciaDao;
	
//	@Autowired
//	private VRotacionRefDao vRotacionRefDao;
	
	@Autowired
	private StockTiendaDao correccionStockDao;
	
	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;
	
	@Autowired
	private VRotacionRefService vRotacionRefService;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;
	
	@Autowired
	private PlanogramaVigenteService planogramaVigenteService;
	
	@Autowired
	private PlanogramaCentroService planogramaCentroService;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private KosmosService kosmosService;
	
	@Autowired
	private ImagenComercialDao imagenComercialDao;

	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;
	
	@Autowired
	private OfertaVigenteService ofertaVigenteService;
	
	@Override
	public PdaSacadaRestosForm getPageData(final Long codCentro, Long codArt, final HttpSession session, final String guardadoStockOk ) throws Exception {

		PdaSacadaRestosForm form = new PdaSacadaRestosForm();
		PdaSacadaResto sacadaResto = new PdaSacadaResto();
		VDatosDiarioArt vDatosDiarioArt=null;
		
		Double cap1 = new Double(0);
		Double dejarCajas = new Double(0);
		Double dejarCajasSeptima = new Double(0);
		
		//Busqueda por codArt
		if (null != codArt ){
			vDatosDiarioArt = getDatosDiarioArt(codArt);
			//sacadaResto = this.getSacadaRestosByCodArt(codCentro, codArt);
		} 
		// 
		//Si existe registro cargamos los datos de la pagina
		if (null!=vDatosDiarioArt){
			sacadaResto.setCodArt(vDatosDiarioArt.getCodArt());
			sacadaResto.setCodCentro(codCentro);
			sacadaResto.setDescripArt(vDatosDiarioArt.getDescripArt());
			sacadaResto.setRotacion(this.obtenerTipoRotacion(codCentro,vDatosDiarioArt.getCodArt()));
			//final Long currentCodArt = vDatosDiarioArt.getCodArt();
			/*--Recuperar datos que no se obtienen la la tabla T_MIS_CAPTURA_RESTOS --*/ 
			//tieneFoto
			form.setTieneFoto(this.getTieneFoto(vDatosDiarioArt.getCodArt()));
			
			//STOCK Y MSG ALBARAN
			PdaSacadaRestosStock stockWs=this.getValoresStock(vDatosDiarioArt.getCodArt(), codCentro,vDatosDiarioArt.getGrupo1(), session);
			form.setStockWs(stockWs);
			
			if ("S".equalsIgnoreCase(guardadoStockOk)){
				sacadaResto.setFlgModifStock("S");
				sacadaResto.setStockDespues(stockWs.getStock());
				sacadaResto.setModificarLastUpdateDate(Boolean.TRUE);
			} else {
				if ("N".equalsIgnoreCase(sacadaResto.getFlgModifStock())){
					sacadaResto.setStockAntes(stockWs.getStock());
				}
			}
			
			//Oferta
			form.setOferta(this.getOferta(codCentro, sacadaResto.getCodArt()));

			// Datos para la imagen comercial
			final ImagenComercial imc = imagenComercialDao.consultaImc(codCentro, vDatosDiarioArt.getCodArt());
			sacadaResto.setImagenComercial(imc);
			
			// Tratamiento Vegalsa
			final User user = (User)session.getAttribute("user");
			final boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), vDatosDiarioArt.getCodArt());
			sacadaResto.setTratamientoVegalsa(tratamientoVegalsa);
			
			// Pedido Activo
			final VSurtidoTienda vSurtidoTienda = getSurtidoTienda(vDatosDiarioArt.getCodArt(), codCentro, tratamientoVegalsa);
			final String pedidoActivo = getPedidoActivo(vSurtidoTienda);
			sacadaResto.setPedidoActivo(pedidoActivo);
			
			// Unidades / Caja			
			if (vSurtidoTienda != null && vSurtidoTienda.getUniCajaServ()!= null){
				sacadaResto.setUnidadesCaja(Utilidades.convertirDoubleAString(vSurtidoTienda.getUniCajaServ().doubleValue(),"###0.00"));
			}
			
			// MMC
			form.setMmc(this.getMMC(vSurtidoTienda));
			
			// CALCULO CC
			//VDatosDiarioArt vDatosDiarioArt = getDatosDiarioArt(currentCodArt);
			form.setCalculoCC(this.getCalculoCC(vDatosDiarioArt.getCodArt(), codCentro, vDatosDiarioArt, session));
			
			// Capacidad 1 y Facing 1
			final PlanogramaVigente planogramaVigente = getPlanogramaVigente(vDatosDiarioArt.getCodArt(), codCentro);
			final String capacidad1 = getCapacidad1(planogramaVigente); 
			final String facing1 = getFacing1(planogramaVigente); 
			sacadaResto.setCapacidad1(capacidad1);
			sacadaResto.setFacing1(facing1);
			
			// Pedir
			final String pedir = getPedir(vSurtidoTienda);
			sacadaResto.setPedir(pedir);
			
			// Implantacion
			final ConsultaPlanogramaPorReferenciaResponseType consultaPlanogramaWSResponse = getPlanogramaWS(vDatosDiarioArt.getCodArt(),codCentro,session);
			final String implantacion = getImplantacion(consultaPlanogramaWSResponse);

			sacadaResto.setImplantacion(implantacion);
			// Color del enlace
			final Date fechaGen = getFechaGen(vSurtidoTienda);
			final String flgColorImplantacion = getFlgColorImplantacion(implantacion, fechaGen, vSurtidoTienda);
			sacadaResto.setFlgColorImplantacion(flgColorImplantacion);
			
			// Capacidad 1
			final Long capacidad = imc.getCapacidad();
			final Long facing = imc.getFacing();
			Integer capacidadFacing = 0;

			if (capacidad!=null && facing!=null && !facing.equals(0L)){
				capacidadFacing = (int) Math.ceil(capacidad / facing);
			}
				
			sacadaResto.setCapacidadFacing(capacidadFacing);
				
			//MOSTRAR FFPP
			
			// Controlamos si tiene un artículo unitario
			VRelacionArticulo relacionArticulo = obtenerRelacionArticulo(sacadaResto);
			if (relacionArticulo != null && relacionArticulo.getCodArtRela() != null)
			{
				if(tratamientoVegalsa){
					if(relacionArticulo.getFormatoProductivoActivo() != null && relacionArticulo.getFormatoProductivoActivo().equals("S")){
						sacadaResto.setMostrarFFPP("S");
						sacadaResto.setCodArtRel(relacionArticulo.getCodArtRela());
					}
				}else{
					sacadaResto.setMostrarFFPP("S");
					sacadaResto.setCodArtRel(relacionArticulo.getCodArtRela());
				}
			}else{ 
				//Comprobación de artículo unitario
				VRelacionArticulo relacionArticuloUnitario = obtenerRelacionArticuloUnitario(sacadaResto);
				if (relacionArticuloUnitario != null && relacionArticuloUnitario.getCodArt() != null){
					if(tratamientoVegalsa){
						if(relacionArticuloUnitario.getFormatoProductivoActivo() != null && relacionArticuloUnitario.getFormatoProductivoActivo().equals("S")){
							sacadaResto.setMostrarFFPP("N");
							sacadaResto.setCodArtRel(relacionArticuloUnitario.getCodArt());
						}
					}else{
						sacadaResto.setMostrarFFPP("N");
						sacadaResto.setCodArtRel(relacionArticuloUnitario.getCodArt());
					}
				}
			}

			sacadaResto.setFlgDejarCajasSeptima(false);

			//DEJAR CAJAS
			if(imc.getTipoReferencia().intValue()==3 || imc.getMetodo()>1 || (imc.getImc()==null || imc.getImc().intValue()==0) 
					|| sacadaResto.getMostrarFFPP()!=null || form.getStockWs().getStock() == null || imc.getCapacidad() == null){
				sacadaResto.setFlgDejarCajas(false);
			}else{
				sacadaResto.setFlgDejarCajas(true);
				
//				if(form.getOferta()!=null && form.getOferta().getMostrarCabeceraX()){
//					sacadaResto.setDejarCajas("DEJAR 0 CAJAS");
//				}else{
					if (capacidad1!=null && capacidad1!=""){
						cap1=Double.valueOf(capacidad1);
					}
	
					if (form.getStockWs().getStock()<=imc.getCapacidad().doubleValue()+cap1){
						sacadaResto.setDejarCajas("DEJAR 0 CAJAS");
					}else{
						dejarCajas=(form.getStockWs().getStock()-(imc.getCapacidad().doubleValue()+cap1))/vSurtidoTienda.getUniCajaServ().doubleValue();
						sacadaResto.setDejarCajas("Sin 7a Balda <span style='font-weight:bold;'>DEJAR "+this.redondeoCajas(dejarCajas)+" CAJAS</span>");

						// Si hay que dejar cajas en el Almacen,
						// se valorará la opción de dejar cajas en la 7ª balda. 
						if (dejarCajas > 0){
							dejarCajasSeptima=(form.getStockWs().getStock()-(imc.getCapacidad().doubleValue()+cap1+(vSurtidoTienda.getUniCajaServ().doubleValue()/2)))/vSurtidoTienda.getUniCajaServ().doubleValue();
							sacadaResto.setFlgDejarCajasSeptima(true);
							sacadaResto.setDejarCajasSeptima("Con 7a Balda <span style='font-weight:bold;'>DEJAR "+this.redondeoCajas(dejarCajasSeptima)+" CAJAS</span>");
						}
					}
//				}
			}
			
			// Finalmente, metemos el numero total de registros en el formulario
			form.setTotalPages(1L);
			
		//Si no existe registro cargamos mensaje de error
		}else{
			form.setTotalPages(0L);
		}
		
		// Guardamos el objeto "sacadaResto" en el formulario con todos los datos ya introducidos
		form.setSacadaResto(sacadaResto);		
		
		return form;
	}

	private VDatosDiarioArt getDatosDiarioArt(Long codArt) throws Exception {
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		vDatosDiarioArt.setCodArt(codArt);
		return this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
	}

	private String getPedir(VSurtidoTienda vSurtidoTienda) {
		String output = null;
		if (vSurtidoTienda!=null){
			final String pedir = vSurtidoTienda.getPedir();
			if (pedir!=null && pedir!="")
				output = pedir;
			else
				output = "N";
		}
		
		return output;
	}

	private String getFlgColorImplantacion(String implantacion, Date fechaGen, VSurtidoTienda vSurtidoTienda) throws Exception {
		String output = null;
		if (implantacion!=null  && !implantacion.trim().equals("")) {
			if (fechaGen != null) { 
				output = "VERDE";		
			} else {
				if (vSurtidoTiendaService.comprobarStockMayorACero(vSurtidoTienda) > 0 ){
					output = "ROJO";
				}
			}			
		}
		return output;
	}

	private Date getFechaGen(VSurtidoTienda vSurtidoTienda) throws Exception {
		return vSurtidoTiendaService.obtenerFechaGeneracionSurtidoTienda(vSurtidoTienda);
	}

	private String getImplantacion(ConsultaPlanogramaPorReferenciaResponseType input) {
		String output = null;
		
		if (input!=null && input.getCodigoRespuesta().equals("0")){
			output = input.getPlanogramaReferencia(0).getImplantancion();
		}
		
		return output;
	}

	private ConsultaPlanogramaPorReferenciaResponseType getPlanogramaWS(final Long codArt, final Long codCentro, final HttpSession session) throws Exception {
		ReferenciasCentro vReferenciasCentro = new ReferenciasCentro();
		vReferenciasCentro.setCodCentro(codCentro);
		vReferenciasCentro.setCodArt(codArt);

		return this.planogramaCentroService.findPlanogramasCentroWS(vReferenciasCentro,session);

	}

	private String getCapacidad1(PlanogramaVigente planogramaVigente) {
		String output = null;
		Double capacidad = null;
		if (planogramaVigente.getFechaGenMontaje1()!= null && planogramaVigente.getCapacidadMontaje1()!= null){
			capacidad = planogramaVigente.getCapacidadMontaje1().doubleValue();			
		}
		else 
			if (planogramaVigente.getFechaGenCabecera()!= null && planogramaVigente.getCapacidadMaxCabecera()!= null){
				capacidad = planogramaVigente.getCapacidadMaxCabecera().doubleValue();
			}
		
		if (capacidad != null){
			output = Utilidades.convertirDoubleAString(capacidad,"###0");			
		}
		return output;
	}
	
	private String getFacing1(PlanogramaVigente planogramaVigente) {
		String output = null;
		Double facing = null;
		if (planogramaVigente.getFechaGenMontaje1()!= null && planogramaVigente.getFacingMontaje1()!= null){
			facing = planogramaVigente.getFacingMontaje1().doubleValue();			
		}
		else 
			if (planogramaVigente.getFechaGenCabecera()!= null && planogramaVigente.getStockMinComerCabecera()!= null){
				facing = planogramaVigente.getStockMinComerCabecera().doubleValue();
			}
		
		if (facing != null){
			output = Utilidades.convertirDoubleAString(facing,"###0");			
		}
		return output;
	}
	
	private PlanogramaVigente getPlanogramaVigente(Long codArt, Long codCentro) throws Exception {

		final PlanogramaVigente input = new PlanogramaVigente();
		input.setCodArt(codArt);
		input.setCodCentro(codCentro);
		return this.planogramaVigenteService.findOne(input);
		
	}

	private String getPedidoActivo(VSurtidoTienda vSurtidoTienda) throws Exception {

		final String pedir = vSurtidoTienda.getPedir();
		final String mapaHoy = vSurtidoTienda.getMapaHoy();
		final Long numPedidosOtroDia = vSurtidoTienda.getNumeroPedidosOtroDia();
		
		String output = "N";
		if (pedir != null  && pedir.equals("S")){
			if (mapaHoy != null && mapaHoy.equals("N")){
					if (numPedidosOtroDia== 0){
						//No hay pedido ningún día
						output = "N";
					}else{
						//Hoy no hay pedido pero si algún otro día
						output = "S";
					}
			}else{
				output = "S";
			}
		}

		return output;
	}

	/**
	 * Comprueba en el tabla CATI_CARGA_FOTOS_REF si existe algun 
	 * registro para el codigo de articulo provisto
	 * @param codArt
	 * @return S existe algun registro. N en caso contrario
	 * @throws Exception
	 */
	private String getTieneFoto(Long codArt) throws Exception{
		//Miramos si existe la foto. La búsqueda se hace con código eroski.
		FotosReferencia fotosReferencia = new FotosReferencia();
		fotosReferencia.setCodReferencia(codArt);
		
		Long counter = fotosReferenciaDao.checkImage(fotosReferencia);  
		
		return null != counter && counter.longValue() > 0 ? "S" : "N";
		
	}
	
	/**
	 * Obtiene el tipo de rotacion
	 * @param codArt
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
//	private String getTipoRotacion(Long codArt, Long codCentro) throws Exception{
//
//		String tipoRotacion = "";
//
//		//Carga de parámetros de búsqueda
//		VRotacionRef vRotacionRef = new VRotacionRef();
//		vRotacionRef.setCodCentro(codCentro);
//		vRotacionRef.setCodArt(codArt);
//
//		VRotacionRef vRotacionRefRes = this.vRotacionRefDao.findOne(vRotacionRef);
//		if (vRotacionRefRes != null){
//			tipoRotacion = vRotacionRefRes.getTipoRotTotal();		
//		}
//
//		return tipoRotacion;
//	}

	/**
	 * Invoca 2 veces al WS de STOCK con diferentes parametros.
	 * La primera para obtener el valor del stock (tipoMensaje=CB).
	 * La segunda para comprobar si hay errores y debemos de mostrar menasaje ALBARAN SIN ACT (tipoMensaje="CC")
	 * Devuelve objeto con el resultado
	 * @param codArt
	 * @param codCentro
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private PdaSacadaRestosStock getValoresStock(final Long codArt, final Long codCentro, Long area, final HttpSession session) throws Exception{
		
		PdaSacadaRestosStock output = new PdaSacadaRestosStock();
		
		ConsultarStockResponseType stockResponse = getConsultaStock(codArt, codCentro, Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA, area, session);

		//Miramos que al menos una referencia sea correcta.
		boolean alMenosUnaReferenciaCorrecta = false;
		
		//Obtener stock
		if (null != stockResponse && stockResponse.getCodigoRespuesta().equals("OK")){
				
			for (ReferenciaType referencia : stockResponse.getListaReferencias()){
				
				// Para referencias TEXTIL insertamos el session la consultaStock porque se utiliza en la pantalla de edicion (PdaP28StockInicioController)
				if (null!=area && new Long(3).compareTo(area)==0){
					//Miramos que al menos una referencia sea correcta.
					if(!(new BigInteger("1")).equals(referencia.getCodigoError())){
						alMenosUnaReferenciaCorrecta = true;
					}
				}
				
				
				//Para el codArt enviado
				if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(codArt))){
					//Si no hay codigo de error
					if (referencia.getCodigoError().equals(new BigInteger("0"))){
						//seteo valores
						if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
							output.setStock(referencia.getBandejas().doubleValue());
						} else {
							output.setStock(referencia.getStock().doubleValue());
						}
						
						//seteo el calculo CC
						if(new Long(3).compareTo(area)!=0 && (stockResponse == null || stockResponse.getTipoMensaje() == null || 						
								!stockResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE))){
							output.setCalculoCC("S");
						}else{
							output.setCalculoCC("N");
						}
					} 
				}
				
			}//for
			
		}
		
		
		// Si se trata de una referecia TEXTIL y existe al menos una ref
		// correcta insertamos en session la consulta para que los datos del WS
		// sean consumidos en la pantalla de edicion de
		// STOCK(PdaP28StockInicioController)
		if (null != stockResponse && !Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockResponse.getCodigoRespuesta())
				&& alMenosUnaReferenciaCorrecta) {
			session.setAttribute("consultaStock", stockResponse);
		} else {
			session.removeAttribute("consultaStock");
		}
		
		//Comprobar mensaje error albaran
		ConsultarStockResponseType msgAlbaranResponse = getConsultaStock(codArt, codCentro, Constantes.STOCK_TIENDA_CONSULTA_CORRECION, area, session);
		
		if (null != msgAlbaranResponse && msgAlbaranResponse.getCodigoRespuesta().equals("OK")){
			for (ReferenciaType referencia : msgAlbaranResponse.getListaReferencias()) {
				// Para el codArt enviado
				if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(codArt))) {
					
					/*PARA PRUEBAS DEL TEXTO ALBARAN (solo hay datos en Prod)*/
//					referencia.setCodigoError(new BigInteger("2"));
//					referencia.setMensajeError("ALBARAN X SIN ACT.");
					
					// Si hay codigo de error 2
					if (referencia.getCodigoError().equals(new BigInteger("2"))) {
						// seteo valores
						output.setMsgError(referencia.getMensajeError());
					}
				}

			}			
		}
		
		return output;
		
	}
	
	
	
	
	private ConsultarStockResponseType getConsultaStock(Long codArt, Long codCentro,
			String stockTiendaConsultaBasicaPistola, Long area, HttpSession session) throws Exception {
		
		ConsultarStockRequestType stockRequest = new ConsultarStockRequestType();
		stockRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
		stockRequest.setTipoMensaje(stockTiendaConsultaBasicaPistola);
		List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
		listaReferencias.add(BigInteger.valueOf(codArt));

		//Para referencias textil consultamos el WS con todas las referencias del proveedor
		if (Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA.equals(stockTiendaConsultaBasicaPistola) && null!=area && new Long(3).compareTo(area)==0){
			List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(codArt);
			
			for (Long articulo : referenciasMismoModeloProveedor) {
				if (!listaReferencias.contains(BigInteger.valueOf(articulo))) {
					listaReferencias.add(BigInteger.valueOf(articulo));
				}
			}			
		}

		stockRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
		
		//Consulta al WS
		ConsultarStockResponseType stockResponse = this.correccionStockDao.consultaStock(stockRequest, session);
		
		return stockResponse;
	}

	private String getCalculoCC(final Long codArt, final Long codCentro, final VDatosDiarioArt vDatosDiarioArt, final HttpSession session) throws Exception{
		
		String output = null;
		ConsultarStockResponseType stockTiendaResponse = getConsultaStock(codArt, codCentro, Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA,vDatosDiarioArt.getGrupo1(), session);

		if(vDatosDiarioArt.getGrupo1() != 3 && (stockTiendaResponse == null || stockTiendaResponse.getTipoMensaje() == null || 						
				!stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE))){
			output = "S";
		}else{
			output = "N";
		}
		return output;
	}
	
	private String getMMC(VSurtidoTienda input){
		String output = null;
		if (input != null && input.getMarcaMaestroCentro()!= null && !"".equals(input.getMarcaMaestroCentro())){
			output = input.getMarcaMaestroCentro();
		}else{
			output = "N";
		}
		return output;
	}
	
	/**
	 * Obtiene los datos de la tabla V_SURTIDO_TIENDA para recoger los parametros
	 * de unidades caja y tipo aprovisionamiento de la captura restos.
	 * 
	 * @param codArt
	 * @param codCentro
	 * @param isTratamientoVegalsa
	 * @return
	 * @throws Exception
	 */
	private VSurtidoTienda getSurtidoTienda (Long codArt, Long codCentro, Boolean isTratamientoVegalsa) throws Exception{
		
		if (null != isTratamientoVegalsa && isTratamientoVegalsa){
			return this.vSurtidoTiendaService.findOneVegalsa(new VSurtidoTienda(codCentro, codArt));
		} else {
			return this.vSurtidoTiendaService.findOne(new VSurtidoTienda(codCentro, codArt));
		}
		
	}
	
	/**
	 * Obtiene los datos pertenecientes a la oferta
	 * @param codCentro
	 * @param codArt
	 * @return
	 * @throws Exception
	 */
	private PdaSacadaRestosOferta getOferta(Long codCentro, Long codArt) throws Exception{
		
		//Obtenemos la ofertaPVP
		OfertaPVP ofertaPVP = null;					
		ofertaPVP = new OfertaPVP();
		ofertaPVP.setCodArticulo(codArt);
		ofertaPVP.setCentro(codCentro);
		ofertaPVP.setFecha(new Date());
		ofertaPVP = this.kosmosService.obtenerDatosPVP(ofertaPVP);
		
		//Si existe oferta, buscar gondola para comprobar si mostrar Cabecera X
		if (null != ofertaPVP && null == ofertaPVP.getTarifa()){
			ofertaPVP=this.ofertaVigenteService.recuperarAnnoOferta(ofertaPVP);
		}
		
		if (null != ofertaPVP && null != ofertaPVP.getAnnoOferta() &&  null != ofertaPVP.getCodOferta()){
			Boolean mostrarCabeceraX = sacadaRestosDao.countGondolaG(codCentro, codArt, ofertaPVP.getAnnoOferta(), ofertaPVP.getCodOferta()) > 0;
			return new PdaSacadaRestosOferta(ofertaPVP.getCodOferta(), ofertaPVP.getAnnoOferta(), mostrarCabeceraX);
		}
		return null;
	}
	
	
	private String obtenerTipoRotacion(Long codCentro, Long codArt) throws Exception{

		String tipoRotacion = "";

		//Carga de parámetros de búsqueda
		VRotacionRef vRotacionRef = new VRotacionRef();
		vRotacionRef.setCodCentro(codCentro);
		vRotacionRef.setCodArt(codArt);

		VRotacionRef vRotacionRefRes = this.vRotacionRefService.findOne(vRotacionRef);
		if (vRotacionRefRes != null){
			tipoRotacion = vRotacionRefRes.getTipoRotTotal();		
		}

		return tipoRotacion;
	}
	
	public VRelacionArticulo obtenerRelacionArticulo(PdaSacadaResto sacadaResto) throws Exception{
		VRelacionArticulo relacionArticuloRes = null;
		VRelacionArticulo relacionArticulo = new VRelacionArticulo();

		relacionArticulo.setCodArt(sacadaResto.getCodArt());
		relacionArticulo.setCodCentro(sacadaResto.getCodCentro());
		relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);

		return relacionArticuloRes;
	}

	public VRelacionArticulo obtenerRelacionArticuloUnitario(PdaSacadaResto sacadaResto) throws Exception{
		VRelacionArticulo relacionArticuloRes = null;
		VRelacionArticulo relacionArticulo = new VRelacionArticulo();

		relacionArticulo.setCodArtRela(sacadaResto.getCodArt());
		relacionArticulo.setCodCentro(sacadaResto.getCodCentro());
		relacionArticuloRes = this.vRelacionArticuloService.findOne(relacionArticulo);

		return relacionArticuloRes;
	}
	
	public String redondeoCajas(Double cajas) throws Exception{
		BigDecimal calculoCajas= new BigDecimal(cajas.toString());
		Long iPart = calculoCajas.longValue();
		BigDecimal fraccion = calculoCajas.remainder(BigDecimal.ONE);
		if(fraccion.compareTo(BigDecimal.ZERO)>0){
			iPart=iPart+1;
		}
		return iPart.toString();
	}
	
	
}
