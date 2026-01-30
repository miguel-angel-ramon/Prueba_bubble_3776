package es.eroski.misumi.control;


import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaMedidaType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaModType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaResMedidaType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaResModType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaTypeResponse;
import es.eroski.misumi.model.pda.PdaDatosImc;
import es.eroski.misumi.model.pda.PdaDatosPopupImplantacion;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingRequest;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingResponse;
import es.eroski.misumi.service.iface.FacingAltoAnchoService;
import es.eroski.misumi.service.iface.FacingVegalsaService;
import es.eroski.misumi.service.iface.ImagenComercialService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP33ModifPlanosController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP33ModifPlanosController.class);

	@Autowired
	private ImagenComercialService imagenComercialService;
	
	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;

	@Autowired
	private FacingVegalsaService facingVegalsaService;

	@Autowired
	private FacingAltoAnchoService facingAltoAnchoService;
	
	@RequestMapping(value = "/pdaP33Imc", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="codArt", required=false, defaultValue="") Long codArt,
			@RequestParam(value="procede", required=false, defaultValue="") String procede,
			@RequestParam(value="impl", required=false, defaultValue="") String implantacion,
			@RequestParam(value="flgColorImpl", required=false, defaultValue="") String flgColorImplantacion,
			HttpSession session,HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {

		PdaDatosImc datosImc = new PdaDatosImc();
		PdaDatosPopupImplantacion datosPopupImplantacion = new PdaDatosPopupImplantacion();
		
		User usuario = (User) session.getAttribute("user");
		Long codCentro = usuario.getCentro().getCodCentro();

		// Registrar si tiene tratamiento VEGALSA.
		boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(usuario.getCentro(), codArt);

		try{
			// Si es un centro VEGALSA.
			if (tratamientoVegalsa){
				BigInteger[] arrayReferencias = new BigInteger[10];
				ConsultaFacingVegalsaRequestType facingVegalsaRequest = new ConsultaFacingVegalsaRequestType();

				arrayReferencias[0] = BigInteger.valueOf(codArt);
				facingVegalsaRequest.setCodigoCentro(BigInteger.valueOf(usuario.getCentro().getCodCentro()));
				facingVegalsaRequest.setTipo(Constantes.CONSULTAR_FACING);
				facingVegalsaRequest.setListaReferencias(arrayReferencias);

				ConsultaFacingVegalsaResponseType facingVegalsaResponse = facingVegalsaService.consultarFacing(facingVegalsaRequest, session);

				// Si la consulta al WS ha ido bien
				if (facingVegalsaResponse.getCodigoRespuesta().equals("OK")){
		
					for (ReferenciaTypeResponse referencia : facingVegalsaResponse.getReferencias()){

						// Si Referencia OK
						if (new BigInteger("0").equals(referencia.getCodigoError())){
							datosImc.setCodArt(codArt);
							datosImc.setCapacidad(referencia.getCapacidad().toString());
							datosImc.setFacing(referencia.getFacing().toString());
							datosImc.setMultiplicador(referencia.getFondo().toString());
							datosImc.setError(referencia.getCodigoError().toString());
							datosImc.setDescError(referencia.getMensajeError());

							//Obetener descripción del artículo
							VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);
		
							if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null){
								datosImc.setDescArt(vDatosDiarioArtResul.getDescripArt());
							}
						} else {
							datosImc.setError(referencia.getCodigoError().toString());
							datosImc.setDescError(referencia.getMensajeError());
						}
					}
				} else {
					datosImc.setError(facingVegalsaResponse.getCodigoRespuesta());
				}

			}else{
				ImagenComercial imagenComercial = imagenComercialService.consultaImc(codCentro, codArt);

				datosImc.setCodArt(imagenComercial.getReferencia());
		
				//Obetener descripción del artículo
				VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(imagenComercial.getReferencia());
		
				if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null){
					datosImc.setDescArt(vDatosDiarioArtResul.getDescripArt());
				}
		
				//Para probar paso en el codart el tipo
				datosImc.setTipoReferencia(imagenComercial.getTipoReferencia().toString());
				datosImc.setCapacidad(imagenComercial.getCapacidad().toString());
				datosImc.setMultiplicador(imagenComercial.getMultiplicador().toString());
				datosImc.setFacing(imagenComercial.getFacing().toString());
				datosImc.setImc(imagenComercial.getImc().toString());
				datosImc.setFacAncho(imagenComercial.getFacingAncho().toString());
				datosImc.setFacAlto(imagenComercial.getFacingAlto().toString());
		
				if(imagenComercial.getTipoReferencia().equals(Constantes.TIPO_REFERENCIA_IMC_FFPP)){
					//Obtenemos el surtido tienda
					ReferenciasCentro referenciasCentro = new ReferenciasCentro();
					referenciasCentro.setCodArt(codArt);
					referenciasCentro.setCodCentro(codCentro);
			
					VSurtidoTienda surtidoTienda = this.obtenerSurtidoTienda(referenciasCentro);
					datosImc.setUc(surtidoTienda.getUniCajaServ().toString());
				}
			}
			datosImc.setTratamientoVegalsa(tratamientoVegalsa);
			model.addAttribute("procede", procede);
			model.addAttribute("pdaDatosImc", datosImc);
		
		} catch (Exception e) {
			datosImc.setError("1");
			datosImc.setDescError("Error al consultar los datos del Facing.");
			model.addAttribute("mensajeError", "Error al consultar los datos del Facing.");
		}

		datosPopupImplantacion.setImplantacion(implantacion);
		datosPopupImplantacion.setFlgColorImplantacion(flgColorImplantacion);

		model.addAttribute("pdaDatosPopupImplantacion", datosPopupImplantacion);
		
		return "pda_p33_modifPlanos";
	}

	//TODO: Función para SIMULAR IMC
	@RequestMapping(value="/pdaP33SimularImc", method = RequestMethod.POST)
	public @ResponseBody ImagenComercial simularImc(
			@RequestBody PdaDatosImc datosImc,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		User usuario = (User) session.getAttribute("user");
		ImagenComercial imagenComercial = datosImc.parseImc();
		imagenComercial.setCentro(usuario.getCentro().getCodCentro());
		
		ImagenComercial imagenComercialSimulada = imagenComercialService.simularImc(imagenComercial);
		
		return imagenComercialSimulada;
	}
	
	@RequestMapping(value="/pdaP33ModificarImc", method = RequestMethod.POST)
	public @ResponseBody ImagenComercial modificarImc(
			@RequestBody PdaDatosImc datosImc,
			@Valid final String procede,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		User usuario = (User) session.getAttribute("user");

		ImagenComercial imagenComercialSimulada = new ImagenComercial();
		
		// Registrar si tiene tratamiento VEGALSA.
		boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(usuario.getCentro(), datosImc.getCodArt());
		
		// Si es un centro VEGALSA.
		if (tratamientoVegalsa){
			
			ImagenComercial imagenComercial = new ImagenComercial();
			imagenComercial.setProcede(datosImc.getProcede());
			ModificarFacingVegalsaResponseType facingVegalsaResponse = new ModificarFacingVegalsaResponseType();
			
			try{
				ReferenciaModType[] arrayReferencias = new ReferenciaModType[10];
				ModificarFacingVegalsaRequestType facingVegalsaRequest = new ModificarFacingVegalsaRequestType();
		
				facingVegalsaRequest.setCodigoCentro(BigInteger.valueOf(usuario.getCentro().getCodCentro()));
				facingVegalsaRequest.setTipo(Constantes.MODIFICAR_FACING);
				
				ReferenciaModType referenciaType = new ReferenciaModType();
				
				referenciaType.setCodigoReferencia(BigInteger.valueOf(datosImc.getCodArt()));
				referenciaType.setFacing(new BigInteger(datosImc.getFacing()));
				referenciaType.setCapacidad(new BigInteger(datosImc.getCapacidad()));
				
				arrayReferencias[0] = referenciaType;
				
				facingVegalsaRequest.setReferencias(arrayReferencias);
				
				imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_OK);
				imagenComercial.setTratamientoVegalsa(tratamientoVegalsa?1:0);
				
				imagenComercial.setFlgPistola(Constantes.MOD_FACING_PISTOLA);
				imagenComercial.setUsuario(usuario.getCode());
		
				facingVegalsaResponse = facingVegalsaService.modificarFacing(facingVegalsaRequest, session);
		
				// Si la consulta al WS ha ido bien
// Para PRUEBAS
//				if (facingVegalsaResponse.getCodigoRespuesta().equals("OK")){
		
					for (ReferenciaResModType referencia : facingVegalsaResponse.getReferencias()){
		
						// Si Referencia OK
						if (new BigInteger("0").equals(referencia.getCodigoError())){
							
							imagenComercial.setReferencia(referencia.getCodigoReferencia().longValue());
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
// Para PRUEBAS
//				} else {
//					imagenComercial.setCodError(new Integer(1));
//					imagenComercial.setDescripcionError(facingVegalsaResponse.getDescripcionRespuesta());
//					imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
//				}

				imagenComercial.setProcede(datosImc.getProcede());
				return imagenComercial;
			
			} catch (Exception e) {
				logger.error(StackTraceManager.getStackTrace(e));
				imagenComercial.setCodError(new Integer(1));
				imagenComercial.setDescripcionError(facingVegalsaResponse.getDescripcionRespuesta());
				imagenComercial.setFlgErrorWSFacingVegalsa(Constantes.WS_FACING_VEGALSA_KO);
				return imagenComercial;
			}

		// si NO es un Centro VEGALSA
		}else{
			ImagenComercial imagenComercial = datosImc.parseImc();
			imagenComercial.setTratamientoVegalsa(tratamientoVegalsa?1:0);
			imagenComercial.setCentro(usuario.getCentro().getCodCentro());
			
			imagenComercial.setFlgPistola(Constantes.MOD_FACING_PISTOLA);
			imagenComercial.setUsuario(usuario.getCode());
		
			try{
				imagenComercialSimulada = imagenComercialService.modificarImc(imagenComercial);
				imagenComercialSimulada.setCodError(new Integer(0));
				
			}catch (Exception e){
				imagenComercialSimulada.setCodError(new Integer(1));
			}

			boolean centroParametrizadoCapET = facingAltoAnchoService.isCentroParametrizado(usuario.getCentro().getCodCentro(), Constantes.ENVIAR_GISAE_ALTO_ANCHO_CAP_ET_47);
			imagenComercialSimulada.setCentroParametrizado(centroParametrizadoCapET?1:0);
			
			// si el centro está parametrizado con 47_ENVIAR_GISA_ALTO_ANCHO_CAP_ET
			// se deberá modificar el AltoXAnchoXCapacidad y la etiqueta a través del WS. 
			if (centroParametrizadoCapET){

				ModificarMedidasFacingVegalsaResponseType facingAltoAnchoResponse = new ModificarMedidasFacingVegalsaResponseType();
				
				try{
					ModificarMedidasFacingVegalsaRequestType facingAltoAnchoRequest = new ModificarMedidasFacingVegalsaRequestType();
					
					facingAltoAnchoRequest.setCodigoCentro(BigInteger.valueOf(usuario.getCentro().getCodCentro()));
					facingAltoAnchoRequest.setTipo(Constantes.MEDIDAS_FACING_CAP_ET);
					
					ReferenciaMedidaType referenciaType = new ReferenciaMedidaType();
					
					referenciaType.setCodigoReferencia(BigInteger.valueOf(datosImc.getCodArt()));
					referenciaType.setFacingAlto(new BigInteger(datosImc.getFacAlto()));
					referenciaType.setFacingAncho(new BigInteger(datosImc.getFacAncho()));
					
					//Aqui hay que añadir la capacidad en el caso de que el centro este parametrizado
					BigInteger capacidad=new BigInteger(datosImc.getCapacidad());
					BigInteger maxCapacidad=new BigInteger("9999");
					if(capacidad.compareTo(maxCapacidad)==1){
						capacidad=maxCapacidad;
					}
					referenciaType.setCapacidad(capacidad);

					// TODO. Pendiente de que me indique María la URL del nuevo WS
					//		 para obtener el Tipo de Etiqueta que posteriormente se pasará
					//		 como parámetro al servicio "ModificarMedidasFacing".
					ConsultaEtiquetaFacingRequest tipoEtiquetaRequest = new ConsultaEtiquetaFacingRequest();
					tipoEtiquetaRequest.setCodigoCentro(facingAltoAnchoRequest.getCodigoCentro());
					tipoEtiquetaRequest.setCodigoReferencia(referenciaType.getCodigoReferencia());
					tipoEtiquetaRequest.setFacing(referenciaType.getFacingAncho());

					try{
						ConsultaEtiquetaFacingResponse etiquetaFacingResponse = facingVegalsaService.obtenerTipoEtiquetaFacing(tipoEtiquetaRequest, session);
	
						if (etiquetaFacingResponse != null){
							
							// Si respuesta del WS para obtener el tipo de etiqueta es 0 - CORRECTO.
							if (BigInteger.valueOf(Constantes.WS_TIPO_ETIQUETA_OK).equals(etiquetaFacingResponse.getCodigoRespuesta())){
								referenciaType.setTipoEtiqueta(etiquetaFacingResponse.getTipoEtiqueta());
								
								ReferenciaMedidaType[] arrayReferencias = new ReferenciaMedidaType[10];
								arrayReferencias[0] = referenciaType;
								
								facingAltoAnchoRequest.setReferencias(arrayReferencias);
								
								imagenComercialSimulada = this.modificarMedidasFacing(imagenComercialSimulada, tratamientoVegalsa, facingAltoAnchoResponse, facingAltoAnchoRequest, session);

							// Si KO el servicio "obtenerTipoEtiqueta" --> Modificar el Facing con el Tipo "AC" - "47_ENVIAR_GISA_ALTO_ANCHO_CAP"
							}else{
								imagenComercialSimulada = this.modificarMedidasFacingResto(imagenComercialSimulada, tratamientoVegalsa, datosImc, session);
							}

						}else{
							imagenComercialSimulada = this.modificarMedidasFacingResto(imagenComercialSimulada, tratamientoVegalsa, datosImc, session);
						}

					} catch (RemoteException ex) {
					    if(ex instanceof AxisFault){
					        logger.error("Axis Fault error: " + ((AxisFault)ex).getFaultString());
							imagenComercialSimulada = this.modificarMedidasFacingResto(imagenComercialSimulada, tratamientoVegalsa, datosImc, session);
					    }
					}	

				} catch (Exception e) {
					logger.error(StackTraceManager.getStackTrace(e));
					imagenComercialSimulada.setCodError(new Integer(Constantes.WS_FACING_ALTO_ANCHO_KO));
					imagenComercialSimulada.setDescripcionError(facingAltoAnchoResponse.getDescripcionRespuesta());
					imagenComercialSimulada.setFlgErrorWSFacingAltoAncho(Constantes.WS_FACING_ALTO_ANCHO_KO);
				}
					
			}else{
				imagenComercialSimulada = this.modificarMedidasFacingResto(imagenComercialSimulada, tratamientoVegalsa, datosImc, session);
			}
		
		}
		imagenComercialSimulada.setProcede(datosImc.getProcede());

		return imagenComercialSimulada;
	}
	
	private VSurtidoTienda obtenerSurtidoTienda(ReferenciasCentro referenciasCentro) throws Exception{
		VSurtidoTienda vSurtidoTiendaRes;
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		vSurtidoTienda.setCodArtCaprabo(referenciasCentro.getCodArt());
		vSurtidoTienda.setCodCentro(referenciasCentro.getCodCentro());
		
		vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

		return vSurtidoTiendaRes;
	}
	
	// Llamar al WS para modificar las medidas del Facing.
	private ImagenComercial modificarMedidasFacing( ImagenComercial imagenComercialSimulada
												  , boolean tratamientoVegalsa
												  , ModificarMedidasFacingVegalsaResponseType facingAltoAnchoResponse
												  , ModificarMedidasFacingVegalsaRequestType facingAltoAnchoRequest
												  , HttpSession session
												  ) throws Exception {
		
		// Por ahora no se le está dando uso a "flgErrorWSFacingAltoAncho" en la parte front, pero es posible que en un futuro sea
		// necesario su uso para saber si se ha producido un error o no.
		imagenComercialSimulada.setFlgErrorWSFacingAltoAncho(Constantes.WS_FACING_ALTO_ANCHO_OK);
		imagenComercialSimulada.setTratamientoVegalsa(tratamientoVegalsa?1:0);

		facingAltoAnchoResponse = facingAltoAnchoService.modificarMedidasFacing(facingAltoAnchoRequest, session);

		//Actualizacion e implementacion MISUMI-514
		if(facingAltoAnchoResponse ==null || facingAltoAnchoResponse.getCodigoRespuesta()==null){
			User user = (User)session.getAttribute("user");
			logger.error("######################## MODIFICACION FACING ERROR ############################");
			logger.error("Centro: " + user.getCentro().getCodCentro());
			logger.error("Usuario: " + user.getCode());
			logger.error("Mac: " + user.getMac());
			logger.error("Tipo: " + facingAltoAnchoRequest.getTipo());
			logger.error("Fecha-Hora: " + Utilidades.formatearFechaHora(new Date()));
			logger.error("Referencia: " + imagenComercialSimulada.getReferencia());
			logger.error("Facing Alto: " + imagenComercialSimulada.getFacingAlto());
			logger.error("Facing Ancho: " + imagenComercialSimulada.getFacingAncho());
			

			imagenComercialSimulada.setCodError(new Integer(Constantes.WS_FACING_ALTO_ANCHO_KO));
			imagenComercialSimulada.setDescripcionError(facingAltoAnchoResponse.getDescripcionRespuesta());
			imagenComercialSimulada.setFlgErrorWSFacingAltoAncho(Constantes.WS_FACING_ALTO_ANCHO_KO);
			
		}else {
		//if (facingAltoAnchoResponse!=null){ ---- Esto estaba asi antes de implementar la MISUMI-514
			// Si la consulta al WS ha ido bien
			if (facingAltoAnchoResponse.getCodigoRespuesta().equals("OK")){
	
				for (ReferenciaResMedidaType referencia : facingAltoAnchoResponse.getReferencias()){
	
					// Si Referencia OK
					if (new BigInteger("0").equals(referencia.getCodigoError())){
						imagenComercialSimulada.setReferencia(referencia.getCodigoReferencia().longValue());
						imagenComercialSimulada.setFacing(referencia.getFacingAlto().longValue());
						imagenComercialSimulada.setCodError(referencia.getCodigoError().intValue());
						imagenComercialSimulada.setDescripcionError(referencia.getMensajeError());
					} else {
						imagenComercialSimulada.setCodError(referencia.getCodigoError().intValue());
						imagenComercialSimulada.setDescripcionError(referencia.getMensajeError());
					}
				}
	
			} //Esto estaba asi antes de implementar la MISUMI-514
			//else {
			//	imagenComercialSimulada.setCodError(new Integer(Constantes.WS_FACING_ALTO_ANCHO_KO));
			//	imagenComercialSimulada.setDescripcionError(facingAltoAnchoResponse.getDescripcionRespuesta());
			//	imagenComercialSimulada.setFlgErrorWSFacingAltoAncho(Constantes.WS_FACING_ALTO_ANCHO_KO);
			//}
		}

		return imagenComercialSimulada;
	}
	
	private ImagenComercial modificarMedidasFacingResto( ImagenComercial imagenComercialSimulada
													   , boolean tratamientoVegalsa
													   , PdaDatosImc datosImc
													   , HttpSession session
													   ) throws Exception {

		User usuario = (User) session.getAttribute("user");
		
		// Además de haber modificado en BBDD el IMC, si el centro está
		// parametrizado con 47_ENVIAR_GISA_ALTO_ANCHO_CAP se deberá modificar el AltoXAnchoXCapacidad a través del WS.
		boolean centroParametrizadoCap = facingAltoAnchoService.isCentroParametrizado(usuario.getCentro().getCodCentro(), Constantes.ENVIAR_GISAE_ALTO_ANCHO_CAP_47);
		
		// Además de haber modificado en BBDD el IMC, si el centro está
		// parametrizado se deberá modificar el AltoXAnchoXCapacidad a través del WS.
		imagenComercialSimulada.setCentroParametrizado(centroParametrizadoCap?1:0);
		if (centroParametrizadoCap){
			
			ModificarMedidasFacingVegalsaResponseType facingAltoAnchoResponse = new ModificarMedidasFacingVegalsaResponseType();
			
			try{
				ModificarMedidasFacingVegalsaRequestType facingAltoAnchoRequest = new ModificarMedidasFacingVegalsaRequestType();
		
				facingAltoAnchoRequest.setCodigoCentro(BigInteger.valueOf(usuario.getCentro().getCodCentro()));
				facingAltoAnchoRequest.setTipo(Constantes.MEDIDAS_FACING_CAP);
				//Pruebas del error de MISUMI 514, se descomenta la siguiente linea y se comenta la anterior 			
				//facingAltoAnchoRequest.setTipo("XX"); 
				
				ReferenciaMedidaType referenciaType = new ReferenciaMedidaType();
				
				referenciaType.setCodigoReferencia(BigInteger.valueOf(datosImc.getCodArt()));
				referenciaType.setFacingAlto(new BigInteger(datosImc.getFacAlto()));
				referenciaType.setFacingAncho(new BigInteger(datosImc.getFacAncho()));
				//Aqui hay que añadir la capacidad en el caso de que el centro este parametrizado
				BigInteger capacidad=new BigInteger(datosImc.getCapacidad());
				BigInteger maxCapacidad=new BigInteger("9999");
				if(capacidad.compareTo(maxCapacidad)==1){
					capacidad=maxCapacidad;
				}
				referenciaType.setCapacidad(capacidad);
				
				ReferenciaMedidaType[] arrayReferencias = new ReferenciaMedidaType[10];
				arrayReferencias[0] = referenciaType;
				
				facingAltoAnchoRequest.setReferencias(arrayReferencias);
				
				// Modificar medidas (altoxancho)
				imagenComercialSimulada = this.modificarMedidasFacing(imagenComercialSimulada, tratamientoVegalsa, facingAltoAnchoResponse, facingAltoAnchoRequest, session);
			
			} catch (Exception e) {
				logger.error(StackTraceManager.getStackTrace(e));
				imagenComercialSimulada.setCodError(new Integer(Constantes.WS_FACING_ALTO_ANCHO_KO));
				imagenComercialSimulada.setDescripcionError(facingAltoAnchoResponse.getDescripcionRespuesta());
				imagenComercialSimulada.setFlgErrorWSFacingAltoAncho(Constantes.WS_FACING_ALTO_ANCHO_KO);
			}

		}else{

			// si el centro está parametrizado con 47_ENVIAR_GISA_ALTO_ANCHO.
			boolean centroParametrizado = facingAltoAnchoService.isCentroParametrizado(usuario.getCentro().getCodCentro(), Constantes.ENVIAR_GISAE_ALTO_ANCHO_47);

			imagenComercialSimulada.setCentroParametrizado(centroParametrizado?1:0);

			// Además de haber modificado en BBDD el IMC, si el centro está
			// parametrizado se deberá modificar el AltoXAncho a través del WS.
			if (centroParametrizado){

				ModificarMedidasFacingVegalsaResponseType facingAltoAnchoResponse = new ModificarMedidasFacingVegalsaResponseType();
				
				try{
					ModificarMedidasFacingVegalsaRequestType facingAltoAnchoRequest = new ModificarMedidasFacingVegalsaRequestType();
			
					facingAltoAnchoRequest.setCodigoCentro(BigInteger.valueOf(usuario.getCentro().getCodCentro()));
					facingAltoAnchoRequest.setTipo(Constantes.MEDIDAS_FACING);
					
					
					ReferenciaMedidaType referenciaType = new ReferenciaMedidaType();
					
					referenciaType.setCodigoReferencia(BigInteger.valueOf(datosImc.getCodArt()));
					referenciaType.setFacingAlto(new BigInteger(datosImc.getFacAlto()));
					referenciaType.setFacingAncho(new BigInteger(datosImc.getFacAncho()));
					
					ReferenciaMedidaType[] arrayReferencias = new ReferenciaMedidaType[10];
					arrayReferencias[0] = referenciaType;
					
					facingAltoAnchoRequest.setReferencias(arrayReferencias);

					imagenComercialSimulada = this.modificarMedidasFacing(imagenComercialSimulada, tratamientoVegalsa, facingAltoAnchoResponse, facingAltoAnchoRequest, session);
				
				} catch (Exception e) {
					logger.error(StackTraceManager.getStackTrace(e));
					imagenComercialSimulada.setCodError(new Integer(Constantes.WS_FACING_ALTO_ANCHO_KO));
					imagenComercialSimulada.setDescripcionError(facingAltoAnchoResponse.getDescripcionRespuesta());
					imagenComercialSimulada.setFlgErrorWSFacingAltoAncho(Constantes.WS_FACING_ALTO_ANCHO_KO);
				}
			}
		}
		return imagenComercialSimulada;
	}
	
}