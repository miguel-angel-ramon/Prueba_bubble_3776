package es.eroski.misumi.control;



import java.math.BigInteger;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.CapraboMotivoNoPedible;
import es.eroski.misumi.model.CapraboMotivoNoPedibleArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleCentroArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleMotivo;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioCap;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaResponseType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ReferenciaResponseType;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosPopupImpresora;
import es.eroski.misumi.model.pda.PdaDatosReferencia;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.service.iface.CapraboMotivosNoPedibleService;
import es.eroski.misumi.service.iface.EansService;
import es.eroski.misumi.service.iface.ImprimirEtiquetaService;
import es.eroski.misumi.service.iface.NumeroEtiquetaImpresoraService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP12DatosReferenciaCapraboController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP12DatosReferenciaCapraboController.class);
	
	@Value( "${tipoAprovisionamiento.descentralizado}" )
    private String tipoAprovisionamientoDescentralizado;

	@Autowired
	private ImprimirEtiquetaService imprimirEtiquetaService;

	@Autowired
	private NumeroEtiquetaImpresoraService numeroEtiquetaImpresoraService;
	
	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	VSurtidoTiendaService vSurtidoTiendaService;
	@Autowired
	private EansService eansService;
	@Autowired
	private CapraboMotivosNoPedibleService capraboMotivosNoPedibleService;

	@RequestMapping(value = "/pdaP12DatosReferenciaCaprabo", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@Valid final Long codArt,
			@Valid final String guardadoStockOk,
			@Valid final String guardadoSfm,
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE,
			@RequestParam(value="impresoraWS", required=false, defaultValue = "NO") String impresoraWS,
			@RequestParam(value="etiqueta", required=false, defaultValue = "false") boolean etiqueta,
			final Long codArtRel,
			final String mostrarFFPP,HttpSession session, HttpServletRequest request) {
		
		
		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");
		Locale locale = LocaleContextHolder.getLocale();
		
		PdaDatosReferencia pdaDatosRefResultado = new PdaDatosReferencia();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		String resultado = "pda_p12_datosReferenciaCaprabo";
		if (origenGISAE.equals("SI")){
			pdaDatosCab.setOrigenGISAE(origenGISAE);
		}
		model.addAttribute("origenGISAE", origenGISAE);
		
	try 
	{

		//Peticion 53662. Etiquetas número de veces.
		if (etiqueta){
			//Obtenemos el número de etiqueta del codArt
			Integer numeroEtiqueta = numeroEtiquetaImpresoraService.getNumEtiqueta(codArt,user.getCentro().getCodCentro(),user.getMac());
			//Incrementamos el número de etiqueta del codArt
			numeroEtiquetaImpresoraService.incNumEtiqueta(codArt.toString(), numeroEtiqueta.intValue(),user.getCentro().getCodCentro(),user.getMac());
		}
		pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(codArt,user.getCentro().getCodCentro(),user.getMac()));
		pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),user.getMac()));
		if(codArt != null)	
			pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(codArt.toString(),user.getCentro().getCodCentro(),user.getMac()));
		else
			pdaDatosCab.setImpresoraActiva("NO");
		
		if (impresoraWS.equals("SI")){
			
			//LLamar al WS de imprimir etiquetas
			ImprimirEtiquetaRequestType imprimirEtiquetarequest = imprimirEtiquetaService.createImprimirEtiquetaRequestType();
			
			try {
				ImprimirEtiquetaResponseType imprimirEtiquetaResponse = imprimirEtiquetaService.imprimirEtiquetaWS(imprimirEtiquetarequest);
				
				if (imprimirEtiquetaService.isOkImprimirEtiquetaResponse(imprimirEtiquetaResponse)) {  // El codigo de respuesta del WS ha sido OK.
					
					for (int i = 0; i < imprimirEtiquetaResponse.getReferencias().length; i++){ //Hacemos un bucle porque devuelve una lista pero en
																								//este caso siempre devuelve una sola referencia
						ReferenciaResponseType refResponse = imprimirEtiquetaResponse.getReferencias()[i];
						BigInteger codigoErrorRef = refResponse.getCodigoError();
						String mensajeErrorRef = refResponse.getMensajeError();

						if (codigoErrorRef.equals(BigInteger.valueOf(0))) { 
							//El WS ha devuelto OK para la referencia consultada. 
							//Ponemos el icono de la impresora a verde.
							numeroEtiquetaImpresoraService.siNumEtiquetaInSessionEnviados();
							pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getNumEtiquetaInSessionEnviados());
							resultado = "pda_p03_enviarMapNumEti";
						} else {
							//El WS no ha devuelto OK para la referecnia consultada. Abrimos una ventana con el mensaje de error del WS.
							resultado = "pda_p18_impresoraPopup";
							
							PdaDatosPopupImpresora pdaDatosPopupImpresora = new PdaDatosPopupImpresora();
							pdaDatosPopupImpresora.setCodArt(codArt);
							pdaDatosPopupImpresora.setProcede("datosRef");
							pdaDatosPopupImpresora.setMensajeErrorWS(mensajeErrorRef);
							
							model.addAttribute("pdaDatosPopupImpresora", pdaDatosPopupImpresora);
							
						}
					
					}
				}
				else {
					//El WS no ha devuelto OK. Abrimos una ventana con el mensaje de error del WS.
					resultado = "pda_p18_impresoraPopup";
					
					PdaDatosPopupImpresora pdaDatosPopupImpresora = new PdaDatosPopupImpresora();
					pdaDatosPopupImpresora.setCodArt(codArt);
					pdaDatosPopupImpresora.setProcede("datosRef");
					if (imprimirEtiquetaResponse != null && StringUtils.isNotBlank(imprimirEtiquetaResponse.getDescripcionRespuesta())){
						pdaDatosPopupImpresora.setMensajeErrorWS(imprimirEtiquetaResponse.getDescripcionRespuesta());
					}
					else{
						pdaDatosPopupImpresora.setMensajeErrorWS(messageSource.getMessage("pda_p12_datosReferenciaCaprabo.errorWSImpresion", null, LocaleContextHolder.getLocale()));
					}

					model.addAttribute("pdaDatosPopupImpresora", pdaDatosPopupImpresora);
				}
				
			} catch (Exception e) {
				
				resultado = "pda_p18_impresoraPopup";
				
				PdaDatosPopupImpresora pdaDatosPopupImpresora = new PdaDatosPopupImpresora();
				pdaDatosPopupImpresora.setCodArt(codArt);
				pdaDatosPopupImpresora.setProcede("datosRef");
				pdaDatosPopupImpresora.setMensajeErrorWS(messageSource.getMessage("pda_p12_datosReferenciaCaprabo.errorWSImpresion", null, LocaleContextHolder.getLocale()));
				
				model.addAttribute("pdaDatosPopupImpresora", pdaDatosPopupImpresora);
			}
			
		} else {
			pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getNumEtiquetaInSessionEnviados());
		}
		
		//Control de guardado de stock
		model.addAttribute("guardadoStockOk", guardadoStockOk);
		model.addAttribute("guardadoSfm", guardadoSfm);
		
		if (codArt != null && !codArt.equals(""))
		{
			//En este caso venimos del popup y tenemos que cargar los datos de la referencia recibida.
			pdaDatosRefResultado = obtenerResultado(codArt,user.getCentro().getCodCentro(),mostrarFFPP, codArtRel, session);
			
			//Obtención de motivos de no activa
			ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			referenciasCentro.setCodCentro(user.getCentro().getCodCentro());
			referenciasCentro.setCodArtCaprabo(pdaDatosRefResultado.getCodArt());

			//Se consulta V_SURTIDO_TIENDA filtrando por COD_CENTRO y COD_ART_CAPRABO
			referenciasCentro.setSurtidoTienda(obtenerSurtidoTiendaCaprabo(referenciasCentro));
			
			//Se consulta T_MIS_MCG_CAPRABO filtrando por COD_CENTRO y COD_ART_CAPRABO
			referenciasCentro.settMisMcgCaprabo(obtenerMcgCaprabo(referenciasCentro));
			
			//Obtención de motivo revisar porque ahora sólo se muestra uno
			if (referenciasCentro.gettMisMcgCaprabo()!=null && referenciasCentro.gettMisMcgCaprabo().getMotivo()!=null){
				referenciasCentro.setMotivoCaprabo(referenciasCentro.gettMisMcgCaprabo().getMotivo());
				pdaDatosRefResultado.setMotivoCaprabo(referenciasCentro.gettMisMcgCaprabo().getMotivo());
			}else{
			
				//Carga de la sesion en el objeto de motivos
				VSurtidoTienda surtidoTienda = referenciasCentro.getSurtidoTienda();
				if (null != surtidoTienda && surtidoTienda.getPedir().toUpperCase().equals("N") && surtidoTienda.getCatalogo().equals("B")) {
	    			//Si es descentralizado hay que sacar el mensaje BAJA CATALOGO
	    			if (this.tipoAprovisionamientoDescentralizado.equals(surtidoTienda.getTipoAprov())){
	    				String motivoBajaCatalogo =  this.messageSource.getMessage("p18_caprabo_referenciasCentroPopupPedir.bajaCatalogo", null, locale);
	    				MotivoTengoMuchoPoco motivoTengoMuchoPocoBaja = new MotivoTengoMuchoPoco();
	    				motivoTengoMuchoPocoBaja.setDescripcion(motivoBajaCatalogo);
	    				//listaMotivosTengoMuchoPoco.add(motivoTengoMuchoPoco);
	    				referenciasCentro.setMotivoCaprabo(motivoBajaCatalogo);
	    				pdaDatosRefResultado.setMotivoCaprabo(motivoBajaCatalogo);
			        				
	    			}else{
	    				//Parámetros de búsqueda de motivos
	    				String tipoMovimientoBusqueda = null;
	    				
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
	        		    				pdaDatosRefResultado.setMotivoCaprabo(capraboMotivoNoPedibleMotivo.getDescripcion());
	        						}
	        					}	
	    					}
	    				}
	    			}
				}
			}

			model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
			pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosRefResultado.getCodArt()));
			pdaDatosCab.setDescArtCab(pdaDatosRefResultado.getDescArt());
			model.addAttribute("pdaDatosCab", pdaDatosCab);
		}
		else
		{
			model.addAttribute("pdaDatosCab", pdaDatosCab);
		}
	
	}catch (Exception e) {
		logger.error(StackTraceManager.getStackTrace(e));
	}
		
		return resultado;
	}
	
	@RequestMapping(value = "/pdaP12FFPPActivoCaprabo", method = RequestMethod.GET)
	public String ffppActivo(ModelMap model,
			@Valid final Long codArt,
			@Valid final Long codArtRel,
			@Valid final String mostrarFFPP,HttpSession session) {
		
		
		PdaDatosReferencia pdaDatosRefResultado = new PdaDatosReferencia();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		Locale locale = LocaleContextHolder.getLocale();
		
		try 
		{
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");
			
			//Controlamos que me llega la referencia
			if (codArtRel != null && !codArtRel.equals(""))
			{
				pdaDatosRefResultado = obtenerResultado(codArtRel,user.getCentro().getCodCentro(),mostrarFFPP, codArt, session);
				
				pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(pdaDatosRefResultado.getCodArt(),user.getCentro().getCodCentro(),user.getMac()));
				pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),user.getMac()));
				if(pdaDatosRefResultado != null && pdaDatosRefResultado.getCodArt() != null)
					pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(pdaDatosRefResultado.getCodArt().toString(),user.getCentro().getCodCentro(),user.getMac()));
				else
					pdaDatosCab.setImpresoraActiva("NO");
				
				
				//Obtención de motivos de no activa
				ReferenciasCentro referenciasCentro = new ReferenciasCentro();
				referenciasCentro.setCodCentro(user.getCentro().getCodCentro());
				referenciasCentro.setCodArtCaprabo(pdaDatosRefResultado.getCodArt());

				//Se consulta V_SURTIDO_TIENDA filtrando por COD_CENTRO y COD_ART_CAPRABO
				referenciasCentro.setSurtidoTienda(obtenerSurtidoTiendaCaprabo(referenciasCentro));
				
				//Se consulta T_MIS_MCG_CAPRABO filtrando por COD_CENTRO y COD_ART_CAPRABO
				referenciasCentro.settMisMcgCaprabo(obtenerMcgCaprabo(referenciasCentro));
				
				//Obtención de motivo revisar porque ahora sólo se muestra uno
				if (referenciasCentro.gettMisMcgCaprabo()!=null && referenciasCentro.gettMisMcgCaprabo().getMotivo()!=null){
					referenciasCentro.setMotivoCaprabo(referenciasCentro.gettMisMcgCaprabo().getMotivo());
					pdaDatosRefResultado.setMotivoCaprabo(referenciasCentro.gettMisMcgCaprabo().getMotivo());
				}else{
				
					//Carga de la sesion en el objeto de motivos
					VSurtidoTienda surtidoTienda = referenciasCentro.getSurtidoTienda();
					if (null != surtidoTienda && surtidoTienda.getPedir().toUpperCase().equals("N") && surtidoTienda.getCatalogo().equals("B")) {
		    			//Si es descentralizado hay que sacar el mensaje BAJA CATALOGO
		    			if (this.tipoAprovisionamientoDescentralizado.equals(surtidoTienda.getTipoAprov())){
		    				String motivoBajaCatalogo =  this.messageSource.getMessage("p18_caprabo_referenciasCentroPopupPedir.bajaCatalogo", null, locale);
		    				MotivoTengoMuchoPoco motivoTengoMuchoPocoBaja = new MotivoTengoMuchoPoco();
		    				motivoTengoMuchoPocoBaja.setDescripcion(motivoBajaCatalogo);
		    				//listaMotivosTengoMuchoPoco.add(motivoTengoMuchoPoco);
		    				referenciasCentro.setMotivoCaprabo(motivoBajaCatalogo);
		    				pdaDatosRefResultado.setMotivoCaprabo(motivoBajaCatalogo);
				        				
		    			}else{
		    				//Parámetros de búsqueda de motivos
		    				String tipoMovimientoBusqueda = null;
		    				
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
		        		    				pdaDatosRefResultado.setMotivoCaprabo(capraboMotivoNoPedibleMotivo.getDescripcion());
		        						}
		        					}	
		    					}
		    				}
		    			}
					}
				}
			}
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
		pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosRefResultado.getCodArt()));
		pdaDatosCab.setDescArtCab(pdaDatosRefResultado.getDescArt());
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		return "pda_p12_datosReferenciaCaprabo";
	}
	
	@RequestMapping(value = "/pdaP12DatosReferenciaCaprabo", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model, HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		

		PdaDatosReferencia pdaDatosRefResultado = new PdaDatosReferencia();
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();

		try{
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");
			
			boolean existenDatosCaprabo = true;

			//Controlamos que me llega la referencia
			pdaDatosCab.setCodArtCab(pdaDatosCab.getCodArtCab().trim());
			
			//Si el código contiene una # hay que eliminar lo que pueda venir antes porque será erróneo
			if (pdaDatosCab.getCodArtCab() != null && pdaDatosCab.getCodArtCab().indexOf(Constantes.REF_PISTOLA)>0){
				existenDatosCaprabo = false;
			}
			
			if (pdaDatosCab.getCodArtCab().startsWith(Constantes.REF_PISTOLA)){
				//Quitamos el primer dígito
				pdaDatosCab.setCodArtCab(pdaDatosCab.getCodArtCab().substring(1,pdaDatosCab.getCodArtCab().length()));
			}
			
			if (existenDatosCaprabo && pdaDatosCab.getCodArtCab() != null && !pdaDatosCab.getCodArtCab().equals("")){
				//Llamamos al método que nos devuelve la referencia, con los controles, 
				//por si se trata de balanza, etiqueta propia de Eroski, de la tabla EANS o código de referencia normal.
				//PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
				PdaArticulo pdaArticulo = new PdaArticulo();
				pdaArticulo.setCodArt(new Long(pdaDatosCab.getCodArtCab()));
				
				ReferenciasCentro referenciasCentro = new ReferenciasCentro();
				referenciasCentro.setCodCentro(user.getCentro().getCodCentro());
				referenciasCentro.setCodArtCaprabo(new Long(pdaDatosCab.getCodArtCab()));

				if (pdaDatosCab.getCodArtCab().toString().length() > 8){ //si tiene mas de 8 digitos es un EAN
					//Si existe el EAN en TMisMcgCaprabo se coge de ahí
					TMisMcgCaprabo tMisMcgCapraboRes;
					TMisMcgCaprabo tMisMcgCapraboAux = new TMisMcgCaprabo();
					tMisMcgCapraboAux.setEan(new Long(pdaDatosCab.getCodArtCab())); //Lo que venia de pantalla no era un COD_ART_CAPRABO era un EAN
					tMisMcgCapraboAux.setCodCentro(user.getCentro().getCodCentro());
					
					//Consultamos T_MIS_MCG_CAPRABO por COD_CENTRO/EAN
					tMisMcgCapraboRes = this.tMisMcgCapraboService.findOne(tMisMcgCapraboAux);
					
					//Consultamos T_MIS_MCG_CAPRABO por COD_CENTRO/EAN
					tMisMcgCapraboRes = obtenerMcgCaprabo(referenciasCentro);
					
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
					
					pdaArticulo.setCodArt(referenciasCentro.getCodArtCaprabo()); 
				}

				String codigoError = pdaArticulo.getCodigoError();
						
				if ((codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN)) || !existenDatosCaprabo){
					pdaError.setDescError(this.messageSource.getMessage(
							"pda_p12_datosReferencia.noExisteReferencia", null, locale));
					model.addAttribute("pdaError", pdaError);
					model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}
				
				pdaDatosCab.setCodArtCab(pdaArticulo.getCodArt().toString());

				pdaDatosRefResultado = obtenerResultado(new Long(pdaDatosCab.getCodArtCab()),user.getCentro().getCodCentro(),"", null, session);
				
				if (existenDatosCaprabo){
					//Se consulta V_SURTIDO_TIENDA filtrando por COD_CENTRO y COD_ART_CAPRABO
					referenciasCentro.setSurtidoTienda(obtenerSurtidoTiendaCaprabo(referenciasCentro));
					
					//Se consulta T_MIS_MCG_CAPRABO filtrando por COD_CENTRO y COD_ART_CAPRABO
					referenciasCentro.settMisMcgCaprabo(obtenerMcgCaprabo(referenciasCentro));
					
					//Obtención de motivo revisar porque ahora sólo se muestra uno
					if (referenciasCentro.gettMisMcgCaprabo()!=null && referenciasCentro.gettMisMcgCaprabo().getMotivo()!=null){
						referenciasCentro.setMotivoCaprabo(referenciasCentro.gettMisMcgCaprabo().getMotivo());
						pdaDatosRefResultado.setMotivoCaprabo(referenciasCentro.gettMisMcgCaprabo().getMotivo());
					}else{
					
						//Carga de la sesion en el objeto de motivos
						VSurtidoTienda surtidoTienda = referenciasCentro.getSurtidoTienda();
						if (null != surtidoTienda && surtidoTienda.getPedir().toUpperCase().equals("N") && surtidoTienda.getCatalogo().equals("B")) {
			    			//Si es descentralizado hay que sacar el mensaje BAJA CATALOGO
			    			if (this.tipoAprovisionamientoDescentralizado.equals(surtidoTienda.getTipoAprov())){
			    				String motivoBajaCatalogo =  this.messageSource.getMessage("p18_caprabo_referenciasCentroPopupPedir.bajaCatalogo", null, locale);
			    				MotivoTengoMuchoPoco motivoTengoMuchoPocoBaja = new MotivoTengoMuchoPoco();
			    				motivoTengoMuchoPocoBaja.setDescripcion(motivoBajaCatalogo);
			    				//listaMotivosTengoMuchoPoco.add(motivoTengoMuchoPoco);
			    				referenciasCentro.setMotivoCaprabo(motivoBajaCatalogo);
			    				pdaDatosRefResultado.setMotivoCaprabo(motivoBajaCatalogo);
					        				
			    			}else{
			    				//Parámetros de búsqueda de motivos
			    				String tipoMovimientoBusqueda = null;
			    				
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
			        		    				pdaDatosRefResultado.setMotivoCaprabo(capraboMotivoNoPedibleMotivo.getDescripcion());
			        						}
			        					}	
			    					}
			    				}
			    			}
						}
					}
				}
				
				//Peticion 53662. Etiquetas número de veces.
				pdaDatosCab.setNumEti(numeroEtiquetaImpresoraService.getNumEtiqueta(new Long(pdaDatosCab.getCodArtCab()),user.getCentro().getCodCentro(),user.getMac()));
				pdaDatosCab.setExisteMapNumEti(numeroEtiquetaImpresoraService.existeMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),user.getMac()));
				if(pdaDatosCab != null && pdaDatosCab.getCodArtCab() != null)	
					pdaDatosCab.setImpresoraActiva(numeroEtiquetaImpresoraService.getImpresoraActiva(pdaDatosCab.getCodArtCab(),user.getCentro().getCodCentro(),user.getMac()));
				else
					pdaDatosCab.setImpresoraActiva("NO");
				
				if (pdaDatosRefResultado.getEsError() != null && !pdaDatosRefResultado.getEsError().equals(""))
				{
					pdaError.setDescError(pdaDatosRefResultado.getEsError());
					model.addAttribute("pdaError", pdaError);
					model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_showMessage";
				}
			}
			else
			{
				//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
				pdaError.setDescError(this.messageSource.getMessage(
						"pda_p12_datosReferenciaCaprabo.referenciaVacia", null, locale));
				model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaError", pdaError);
				logger.info("PDA Referencia no introducida");
				return "pda_p03_showMessage";
			}
			
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		model.addAttribute("pdaDatosRef", pdaDatosRefResultado);
		pdaDatosCab.setDescArtCab(pdaDatosRefResultado.getDescArt());
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		return "pda_p12_datosReferenciaCaprabo";
	}
	
	private PdaDatosReferencia obtenerResultado(Long codArt, Long codCentro, String mostradoFFPP, Long codaArtRel, HttpSession session) throws Exception{
		
		
		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosReferencia pdaDatosRef = new PdaDatosReferencia();
		pdaDatosRef.setCodArt(codArt);
		pdaDatosRef.setCodArtRel(codaArtRel);
		
		ReferenciasCentro referenciasCentro = new ReferenciasCentro();
		
		referenciasCentro.setCodArtCaprabo(codArt);
		referenciasCentro.setCodCentro(codCentro);
		TMisMcgCaprabo tMisMcgCapraboResult = obtenerMcgCaprabo(referenciasCentro);
		//pdaDatosRef.setStockActual(Utilidades.convertirDoubleAString(tMisMcgCapraboResult.getStock().doubleValue(),"###0.#"));
		//pdaDatosRef.setDiasStock(Utilidades.convertirDoubleAString(tMisMcgCapraboResult.getStockDias().doubleValue(),"###0.#"));
		referenciasCentro.settMisMcgCaprabo(tMisMcgCapraboResult);
		VSurtidoTienda surtidoTienda = obtenerSurtidoTiendaCaprabo(referenciasCentro);
		
		//Calcular 30 días de la FECHA_ACTIVACION
		boolean mostrar30dias=true;
		if (tMisMcgCapraboResult!= null && tMisMcgCapraboResult.getFechaActivacion() != null && surtidoTienda != null) { 
			
			//Obtenemos la fecha generacion de surtido tienda. Fecha en la que la referencia pasa de PEDIR N a PEDIR S.
			Date FechaGenSurtidoTienda = vSurtidoTiendaService.obtenerFechaGeneracionSurtidoTienda(surtidoTienda);
			if (FechaGenSurtidoTienda != null) {
				
				pdaDatosRef.setFechaGen(FechaGenSurtidoTienda); 
				Format formatterDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy", locale);
				Integer intDiaSemana = Utilidades.getNumeroDiaSemana(FechaGenSurtidoTienda, locale);
				StringBuffer sb = new StringBuffer();
				if (intDiaSemana != null && 1 <= intDiaSemana.intValue() && intDiaSemana.intValue() <= 7){
					StringBuffer sb1 = new StringBuffer("calendario.").append(intDiaSemana.intValue());
					String letra = messageSource.getMessage(sb1.toString(), null, locale);
					sb.append(letra);
					sb.append(" ");
				}
				sb.append(formatterDDMMYYYY.format(FechaGenSurtidoTienda));
				pdaDatosRef.setStrFechaGen(sb.toString()); 
			}	
			//Comprobamos se muestre solo durante 30 dias
		
			//Fecha de Activacion MOVIMIENTO CONTINUO GAMA CAPRABO
			Calendar calFechaActMCGCaprabo = Calendar.getInstance();
			calFechaActMCGCaprabo .setTime(tMisMcgCapraboResult.getFechaActivacion());

			//Obtenemos la fecha actual
			Calendar calFechaActual = Calendar.getInstance();
				
			//Obtenemos  la diferencia entre las dos fechas 
			long millisDay = 24*60*60*1000;
			long diffDays = (long)Math.floor((calFechaActual.getTimeInMillis() - calFechaActMCGCaprabo.getTimeInMillis())/(millisDay));
				
			mostrar30dias=diffDays <= 30;
				
			pdaDatosRef.setMostrarFechaGen(mostrar30dias);
		}
	//Tratamiento de SUSTITUTA DE y SUSTIUIDA POR
	//Se muestran los enlaces durante 30 días de la FECHA_ACTIVACION si TIPO_MOV=A/B
		boolean mostrar=(mostrar30dias && (tMisMcgCapraboResult != null && (tMisMcgCapraboResult.getTipoMov().equals("A") || tMisMcgCapraboResult.getTipoMov().equals("B"))))
						 || (!(tMisMcgCapraboResult != null && (tMisMcgCapraboResult.getTipoMov().equals("A") || tMisMcgCapraboResult.getTipoMov().equals("B"))));
		if (tMisMcgCapraboResult != null && 0 != tMisMcgCapraboResult.getSustituidaPor() && mostrar) {
			pdaDatosRef.setMostrarSustPorRef("S");
			pdaDatosRef.setMostrarSustARef("N");
		} else if (tMisMcgCapraboResult != null && 0 != tMisMcgCapraboResult.getSustitutaDe() && mostrar) {
			pdaDatosRef.setMostrarSustARef("S");
			pdaDatosRef.setMostrarSustPorRef("N");
		} else {
			pdaDatosRef.setMostrarSustARef("N");
			pdaDatosRef.setMostrarSustPorRef("N");
		}
		
		
		//Obtenemos datos de Movimiento continuoa gama Caprabo
		if (tMisMcgCapraboResult != null && tMisMcgCapraboResult.getDescripArt() != null)
		{
			pdaDatosRef.setDescArt(tMisMcgCapraboResult.getDescripArt());
			pdaDatosRef.settMisMcgCaprabo(tMisMcgCapraboResult);
		}else{
			VDatosDiarioCap vDatosDiarioCap = obtenerVDatDiarioCap(referenciasCentro);
			if (vDatosDiarioCap!=null){
				pdaDatosRef.setDescArt(vDatosDiarioCap.getDescripArt());
			}
		}
		
		//Obtenemos pedir
		if (surtidoTienda != null && surtidoTienda.getPedir()!= null && !"".equals(surtidoTienda.getPedir()))
		{
			pdaDatosRef.setPedir(surtidoTienda.getPedir());
		}else{
			pdaDatosRef.setPedir(" ");
		}
		
		if (null!=pdaDatosRef.gettMisMcgCaprabo() && null!=pdaDatosRef.gettMisMcgCaprabo().getImplantacion()){
			if (pdaDatosRef.getPedir().equals("S")){
				pdaDatosRef.setFlgColorImplantacion("VERDE");	
			} else if (pdaDatosRef.getPedir().equals("N")){
				if (pdaDatosRef.gettMisMcgCaprabo().getStock()>0){
					pdaDatosRef.setFlgColorImplantacion("ROJO");
				} else if (null != referenciasCentro.getSurtidoTienda() && vSurtidoTiendaService.comprobarStockMayorACeroCaprabo(referenciasCentro.getSurtidoTienda()) > 0 ){
			        pdaDatosRef.setFlgColorImplantacion("ROJO");                  
			    } else {
			        pdaDatosRef.setFlgColorImplantacion("");
                }			
			}
		} else {
			pdaDatosRef.setFlgColorImplantacion("");
		}
		

		return pdaDatosRef;
	}
	
	
	



}