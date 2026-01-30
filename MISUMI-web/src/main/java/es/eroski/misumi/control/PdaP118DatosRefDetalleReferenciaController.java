package es.eroski.misumi.control;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.model.MontajeVegalsa;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaTypeResponse;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosImc;
import es.eroski.misumi.model.pda.PdaDatosReferencia;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;
import es.eroski.misumi.service.iface.FacingVegalsaService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.ImagenComercialService;
import es.eroski.misumi.service.iface.KosmosService;
import es.eroski.misumi.service.iface.OfertaVigenteService;
import es.eroski.misumi.service.iface.PlanogramaCentroService;
import es.eroski.misumi.service.iface.PlanogramaKosmosService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VRotacionRefService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;


@Controller
public class PdaP118DatosRefDetalleReferenciaController extends pdaConsultasController{

	@Autowired
	private KosmosService kosmosService;
	
	@Autowired
	private StockTiendaService stockTiendaService;
	
	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	
	@Autowired
	private FotosReferenciaService fotosReferenciaService; 
	
	@Autowired
	private OfertaVigenteService ofertaVigenteService;
	
	@Autowired
	private VRotacionRefService vRotacionRefService;
	
	@Autowired
	private ImagenComercialService imagenComercialService;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;
	
	@Autowired
	private PlanogramaCentroService planogramaCentroService;
	
	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;
	
	@Autowired
	private FacingVegalsaService facingVegalsaService;
	
	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;
	
	@Autowired
	private PlanogramaKosmosService planogramaKosmosService;
	
	@RequestMapping(value = "/pdaP118DatosRefDetalleReferencia", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="codArt", required=false, defaultValue="") String codArt,
			@RequestParam(value="descArt", required=false, defaultValue="") String descArtConCod,
			@RequestParam(value="impl", required=false, defaultValue="") String implantacion,
			@RequestParam(value="flgColorImpl", required=false, defaultValue="") String flgColorImplantacion,
			@RequestParam(value="tipoRef", required=false, defaultValue="") String tipoReferencia,
			@RequestParam(value="guardadoImc", required=false, defaultValue="") String guardadoImc,
			@RequestParam(value="facAncho", required=false, defaultValue="") String facingAncho,
			@RequestParam(value="facAlto", required=false, defaultValue="") String facingAlto,
			@RequestParam(value="cap", required=false, defaultValue="") String capacidad,
			@RequestParam(value="fac", required=false, defaultValue="") String facing,
			@RequestParam(value="imc", required=false, defaultValue="") String imc,
			@RequestParam(value="mult", required=false, defaultValue="") String multiplicador,
			@RequestParam(value="codArtProc", required=false, defaultValue="") String codArtProc,
			@Valid final String guardadoSfm,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response)throws Exception{ 

		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");

		//Cargamos los datos de cabecera.
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		pdaDatosCab.setCodArtCab(codArt);
		pdaDatosCab.setDescArtCab(descArtConCod);

		if(codArtProc.equals("")){
			codArt=(String)session.getAttribute("detalleReferenciaCodArt");
			descArtConCod=(String)session.getAttribute("detalleReferenciaDescArtConCod");
			implantacion=(String)session.getAttribute("detalleReferenciaImplantacion");
			flgColorImplantacion=(String)session.getAttribute("detalleReferenciaFlgColorImplantacion");
			tipoReferencia=(String)session.getAttribute("detalleReferenciaTipoReferencia");
			guardadoImc=(String)session.getAttribute("detalleReferenciaGuardadoImc");
			facingAncho=(String)session.getAttribute("detalleReferenciaFacingAncho");
			facingAlto=(String)session.getAttribute("detalleReferenciaFacingAlto");
			capacidad=(String)session.getAttribute("detalleReferenciaCapacidad");
			facing=(String)session.getAttribute("detalleReferenciaFacing");
			imc=(String)session.getAttribute("detalleReferenciaImc");
			multiplicador=(String)session.getAttribute("detalleReferenciaMultiplicador");
			codArtProc=(String)session.getAttribute("detalleReferenciaCodArtProc");
		}else{
			session.setAttribute("detalleReferenciaCodArt", codArt);
			session.setAttribute("detalleReferenciaDescArtConCod", descArtConCod);
			session.setAttribute("detalleReferenciaImplantacion", implantacion);
			session.setAttribute("detalleReferenciaFlgColorImplantacion", flgColorImplantacion);
			session.setAttribute("detalleReferenciaTipoReferencia", tipoReferencia);
			session.setAttribute("detalleReferenciaGuardadoImc", guardadoImc);
			session.setAttribute("detalleReferenciaFacingAncho", facingAncho);
			session.setAttribute("detalleReferenciaFacingAlto", facingAlto);
			session.setAttribute("detalleReferenciaCapacidad", capacidad);
			session.setAttribute("detalleReferenciaFacing", facing);
			session.setAttribute("detalleReferenciaImc", imc);
			session.setAttribute("detalleReferenciaMultiplicador", multiplicador);
			session.setAttribute("detalleReferenciaCodArtProc", codArtProc);
		}
		PdaDatosReferencia pdaDatosRef = new PdaDatosReferencia();
		String resultado = "pda_p118_detalleReferencia";
		PdaDatosImc pdaDatosImc = new PdaDatosImc();
		Long codArticulo = Long.parseLong(codArt);
		
		//Gestionar si el centro es caprabo o no y obtener la descripción 
		//del artículo según el tipo de centro.
		String descArt = descArtConCod;
		
		if (descArt != null && !descArt.isEmpty()){
			int pos = descArtConCod.indexOf("-");
			if (pos >= 0) {
			    descArt = descArtConCod.substring(pos+1);
			}
		}

		pdaDatosImc.setCodArt(codArticulo);
		pdaDatosImc.setDescArt(descArt);
		pdaDatosImc.setDescArtConCodigo(descArtConCod);
		pdaDatosImc.setTipoReferencia(tipoReferencia);
		pdaDatosImc.setGuardadoImc(guardadoImc);
		pdaDatosImc.setFacAncho(facingAncho);
		pdaDatosImc.setFacAlto(facingAlto);
		pdaDatosImc.setCapacidad(capacidad);
		pdaDatosImc.setFacing(facing);
		pdaDatosImc.setImc(imc);
		pdaDatosImc.setMultiplicador(multiplicador);
		
		//Recuperamos los datos diarios del articulo
		VDatosDiarioArt vDatosDiarioArt =this.obtenerDiarioArt(codArticulo);
		pdaDatosRef.setCodArt(vDatosDiarioArt.getCodArt());
		pdaDatosRef.setDescArt(vDatosDiarioArt.getDescripArt());
		
		//Con los datos diarios del artículo sacamos la descripción de la subcategoria
		List<VAgruComerRef> listVAgrucomerRef =  this.vAgruComerRefService.findAll(new VAgruComerRef("I3",vDatosDiarioArt.getGrupo1(),vDatosDiarioArt.getGrupo2(),vDatosDiarioArt.getGrupo3(),null,null,null), null);
		pdaDatosRef.setDescSubcategoria(listVAgrucomerRef.get(0).getDescripcion());
		//Miramos si existe la foto. La búsqueda se hace con código eroski.
		FotosReferencia fotosReferencia = new FotosReferencia();
		fotosReferencia.setCodReferencia(codArticulo);
		if (fotosReferenciaService.checkImage(fotosReferencia)){
			pdaDatosRef.setTieneFoto("S");
		}else{
			pdaDatosRef.setTieneFoto("N");
		}
		
		//Obtenemos la ofertaPVP
		OfertaPVP ofertaPVP = null;					
		
		ofertaPVP = new OfertaPVP();
		ofertaPVP.setCodArticulo(codArticulo);
		ofertaPVP.setCentro(user.getCentro().getCodCentro());
		ofertaPVP.setFecha(new Date());
		ofertaPVP = kosmosService.obtenerDatosPVP(ofertaPVP);
							
		if (ofertaPVP!=null && ofertaPVP.getTarifa()==null){
			Double pvpWS = stockTiendaService.consultaPVP(user.getCentro().getCodCentro(), codArticulo, session);
			if (pvpWS!=null){
				ofertaPVP.setTarifa(pvpWS);
				ofertaPVP.setTarifaStr(pvpWS.toString().replace(".", ","));
			}
			ofertaPVP=this.ofertaVigenteService.recuperarAnnoOferta(ofertaPVP);
		}
		model.addAttribute("ofertaPVP", ofertaPVP);
		
		pdaDatosRef.setTipoRotacion(this.obtenerTipoRotacion(codArticulo, user.getCentro().getCodCentro()));
		//Cargamos los datos de venta media.
		ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();

		referenciasCentroIC.setCodArt(codArticulo);
		referenciasCentroIC.setCodCentro(user.getCentro().getCodCentro());
		// Tratamiento Vegalsa
		final boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), vDatosDiarioArt.getCodArt());
		pdaDatosRef.setTratamientoVegalsa(tratamientoVegalsa);
				
		//Añadimos la imagen comercial
		ImagenComercial imagenComercial = imagenComercialService.consultaImc(user.getCentro().getCodCentro(), codArticulo);

		if(imagenComercial.getMetodo() != null){
			imagenComercial.setTratamientoVegalsa(tratamientoVegalsa?1:0);
			pdaDatosRef.setImagenComercial(imagenComercial);

			boolean esSfm = (Constantes.IMC_SFM == imagenComercial.getMetodo());
			if (!esSfm){
				//Se trata de Capacidad.
				pdaDatosRef = cargarDatosCapacidad(referenciasCentroIC,pdaDatosRef,user.getCentro().getCodCentro(),session);
			}
		}

		if (tratamientoVegalsa){
			
			BigInteger[] arrayReferencias = new BigInteger[10];
			ConsultaFacingVegalsaRequestType facingVegalsaRequest = new ConsultaFacingVegalsaRequestType();

			arrayReferencias[0] = BigInteger.valueOf(codArticulo);
			facingVegalsaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
			facingVegalsaRequest.setTipo(Constantes.CONSULTAR_FACING);
			facingVegalsaRequest.setListaReferencias(arrayReferencias);

			try{
				ConsultaFacingVegalsaResponseType facingVegalsaResponse = facingVegalsaService.consultarFacing(facingVegalsaRequest, session);
	
				// Si la consulta al WS ha ido bien
				if (facingVegalsaResponse.getCodigoRespuesta().equals("OK")){
	
					for (ReferenciaTypeResponse referencia : facingVegalsaResponse.getReferencias()){
	
						// Si Referencia OK
						if (new BigInteger("0").equals(referencia.getCodigoError())){
							
							imagenComercial.setCentro(user.getCentro().getCodCentro());
							imagenComercial.setReferencia(codArticulo);
							imagenComercial.setCapacidad(referencia.getCapacidad().longValue());
							imagenComercial.setFacing(referencia.getFacing().longValue());
							imagenComercial.setMultiplicador(referencia.getFondo().intValue());
							imagenComercial.setCodError(referencia.getCodigoError().intValue());
							imagenComercial.setDescripcionError(referencia.getMensajeError());
							
							// Registramos si el centro tiene permiso de modificación del Facing.
							// En el caso de ser un centro VEGALSA, no debería tener permiso de moficación.
							boolean facingModificable = facingVegalsaService.isFacingModificable(user.getCentro().getCodCentro());
							imagenComercial.setFacingModificable(facingModificable?1:0);
							imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_OK);
							
						} else {
							imagenComercial.setCodError(referencia.getCodigoError().intValue());
							imagenComercial.setDescripcionError(referencia.getMensajeError());
							imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
						}
					}
				} else {
					imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
				}
	
				imagenComercial.setTratamientoVegalsa(tratamientoVegalsa?1:0);
				pdaDatosRef.setImagenComercial(imagenComercial);
				
			}catch(Exception e){
				imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
				pdaDatosRef.setImagenComercial(imagenComercial);
			}
		}
		
		// Pedido Activo
		final VSurtidoTienda vSurtidoTienda = getSurtidoTienda(vDatosDiarioArt.getCodArt(), user.getCentro().getCodCentro(), tratamientoVegalsa);
		final String pedidoActivo = getPedidoActivo(vSurtidoTienda);
		pdaDatosRef.setPedidoActivo(pedidoActivo);
		
		// MMC
		pdaDatosRef.setMMC(this.getMMC(vSurtidoTienda));
		
		// Pedir
		final String pedir = getPedir(vSurtidoTienda);
		pdaDatosRef.setPedir(pedir);
		
		ReferenciasCentro vReferenciasCentro = new ReferenciasCentro();
		vReferenciasCentro.setCodCentro(user.getCentro().getCodCentro());
		vReferenciasCentro.setCodArt(codArticulo);
		
		ConsultaPlanogramaPorReferenciaResponseType result = this.planogramaCentroService.findPlanogramasCentroWS(vReferenciasCentro,session);

		if (result!=null){
			
			if (result.getCodigoRespuesta().equals("0")){

				//Miramos si el WS devuelve el campo implantaciÃ³n. Si devuleve implantaciÃ³n habra que comprobar si este se pinta en rojo o verde 
				//en la pestaÃ±a Imagen comercial. 
				if ((pdaDatosRef.getImplantacion() != null)  && !(pdaDatosRef.getImplantacion().trim().equals(""))) {

					//comprobamos si existe Fecha Generacion. 
					if (pdaDatosRef.getFechaGen() != null) { 

						pdaDatosRef.setFlgColorImplantacion("VERDE");		

					} else {

						//comprobamos si el STOCK es mayor a 0 o si es 0 pero no ha pasado mas de un mes desde que se ha puesto a cero. En ese caso lo pintamos a rojo
						if (vSurtidoTiendaService.comprobarStockMayorACero(vSurtidoTienda) > 0 ){
							pdaDatosRef.setFlgColorImplantacion("ROJO");
						}
					}			
				}
			}
		} 

		PlanogramaVigente planogramaVigente = obtenerPlanogramaVigente(referenciasCentroIC);

		//Si no existe el planogramaVigente o la capacidad maxima lineal es nula y el stockminimocomercial es nulo, se consulta el planograma de kosmos.
		if(planogramaVigente == null || (planogramaVigente.getCapacidadMaxLineal() == null && planogramaVigente.getStockMinComerLineal() == null)){
			planogramaVigente = obtenerPlanogramaKosmos(referenciasCentroIC);
		}

		if(planogramaVigente!=null){
			planogramaVigente = rellenarDatosVegalsa(planogramaVigente, session);
			if(planogramaVigente!=null && planogramaVigente.getOfertaProm()!=null &&  planogramaVigente.getEspacioProm()!=null){
				int cantidad=0;
				if(planogramaVigente.getFechaGenMontaje1()!=null){
					cantidad=planogramaVigente.getCapacidadMontaje1().intValue();
				}else if(planogramaVigente.getFechaGenCabecera()!=null){
					cantidad=planogramaVigente.getCapacidadMaxCabecera().intValue();
				}
				pdaDatosRef.setCantidadMac(String.valueOf(cantidad));
			}
		}
		MontajeVegalsa montajeVegalsa=tPedidoAdicionalService.getPedidosVegalsa(referenciasCentroIC.getCodCentro(), referenciasCentroIC.getCodArt());
		if(montajeVegalsa!=null){
			pdaDatosRef.setCantidadMa(montajeVegalsa.getCantidad().toString());
		}
		model.addAttribute("implantacion", implantacion);
		model.addAttribute("flgColorImplantacion", flgColorImplantacion);
		model.addAttribute("codArtProc", codArtProc);
		model.addAttribute("pdaDatosImc", pdaDatosImc);
		model.addAttribute("guardadoSfm", guardadoSfm);
		model.addAttribute("guardadoImc", guardadoImc);
		model.addAttribute("pdaDatosRef", pdaDatosRef);
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		
		return resultado;
		
	}
	
	private String obtenerTipoRotacion( Long codArt, Long codCentro) throws Exception{

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
	
	private String getMMC(VSurtidoTienda input){
		String output = null;
		if (input != null && input.getMarcaMaestroCentro()!= null && !"".equals(input.getMarcaMaestroCentro())){
			output = input.getMarcaMaestroCentro();
		}else{
			output = "N";
		}
		return output;
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
	
	private PlanogramaVigente obtenerPlanogramaKosmos(
			ReferenciasCentroIC referenciasCentroIC) throws Exception {

		PlanogramaVigente planogramaVigenteRes;
		PlanogramaVigente planogramaVigente = new PlanogramaVigente();
		planogramaVigente.setCodArt(referenciasCentroIC.getCodArt());
		planogramaVigente.setCodCentro(referenciasCentroIC.getCodCentro());

		planogramaVigenteRes = this.planogramaKosmosService.findOne(planogramaVigente);

		return planogramaVigenteRes;
	}
	
}
