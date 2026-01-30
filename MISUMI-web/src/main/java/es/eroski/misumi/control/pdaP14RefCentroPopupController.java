package es.eroski.misumi.control;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.EstadoConvergencia;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.TextoMotivo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VMapaReferencia;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosPopupReferencia;
import es.eroski.misumi.model.pda.PdaMotivo;
import es.eroski.misumi.model.pda.PdaTextoMotivo;
import es.eroski.misumi.model.pedidosCentroWS.MotivosType;
import es.eroski.misumi.model.pedidosCentroWS.ReferenciasValidadasType;
import es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.EstadoConvergenciaService;
import es.eroski.misumi.service.iface.PedidosCentroService;
import es.eroski.misumi.service.iface.PlataformaService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VArtCentroAltaService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
public class pdaP14RefCentroPopupController extends pdaConsultasController {

	private static Logger logger = Logger.getLogger(pdaP14RefCentroPopupController.class);

	@Value( "${tipoAprovisionamiento.descentralizado}" )
	private String tipoAprovisionamientoDescentralizado;
	
	@Value( "${tipoAprovisionamiento.grupaje}" )
	private String tipoAprovisionamientoGrupaje;
	
	@Autowired
	private PedidosCentroService pedidosCentroService;
	@Autowired
	private VArtCentroAltaService vArtCentroAltaService;
	@Autowired
	private EstadoConvergenciaService estadoConvergenciaService;
	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
	@Autowired
	private PlataformaService plataformaService;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;
	
	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;
	
	@Resource 
	private MessageSource messageSource;
	
	private int posicionNoAct = 0;
	private int posicionMMC = 0;
	
	@RequestMapping(value = "/pdaP14refCentroPopup", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final String procede,
			@Valid final String origen) {

		try{
			Locale locale = LocaleContextHolder.getLocale();
			 
			User user = (User) session.getAttribute("user");

			Page<PdaMotivo> pagNoActivo = null;
			Page<PdaMotivo> pagMMC = null;
			 
			PdaMotivo motivo = new PdaMotivo();
			PdaTextoMotivo textoMotivo = null;
			List<PdaMotivo> listaMotivos = new ArrayList<PdaMotivo>();
			 
			//Inicializamos las posiciones
			this.posicionNoAct = 0;
			this.posicionMMC = 0;
			
			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));
			
			//Obtenemos la descripción del artículo.
			VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);
			String descArt = "";
			if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null){
				descArt = vDatosDiarioArtResul.getDescripArt();
			}
			pdaDatosCab.setDescArtCab(descArt);
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			
			//Llamamos al método que se encarga de llamar al web service para guardar las listas en sesión.
			String resultado = cargaListasWSSesion(codArt,vDatosDiarioArtResul.getGrupo1(),session);
				
			//Llamamos al método que se encarga de cargar un posible motivo no activo que no viene del WS.
			pagNoActivo = cargarMotivoNoActivoNoWS(codArt,session);
				
			//Llamamos al método que se encarga obtener el objeto page de la lista de MMC
			pagMMC = cargarMotivoNoActivoMMC(session);

			PdaDatosPopupReferencia pdaDatosPopupReferencia = new PdaDatosPopupReferencia();
			pdaDatosPopupReferencia.setCodArt(codArt);
			pdaDatosPopupReferencia.setDescArt(descArt);
			pdaDatosPopupReferencia.setProcede(procede);
			pdaDatosPopupReferencia.setPagNoActivo(pagNoActivo);
			pdaDatosPopupReferencia.setPagMMC(pagMMC);
			
			if (pagNoActivo != null && pagNoActivo.getRecords() != null &&  !pagNoActivo.getRecords().equals("0")){
				pdaDatosPopupReferencia.setFlgNoActivo("S");
			}else{
				pdaDatosPopupReferencia.setFlgNoActivo("N");
			}
			
			if (pagMMC != null && pagMMC.getRecords() != null &&  !pagMMC.getRecords().equals("0")){
				pdaDatosPopupReferencia.setFlgMMC("S");
			}else{
				pdaDatosPopupReferencia.setFlgMMC("N");
			}
			
			// Tratamiento Vegalsa
			pdaDatosPopupReferencia.setTratamientoVegalsa(utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), codArt));
			if (pdaDatosPopupReferencia.isTratamientoVegalsa()){
				VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
				vSurtidoTienda.setCodArt(codArt);
				vSurtidoTienda.setCodCentro(user.getCentro().getCodCentro());
				vSurtidoTienda = this.vSurtidoTiendaService.findOneVegalsa(vSurtidoTienda);
				pdaDatosPopupReferencia.setFechaMmc(vSurtidoTienda.getFechaMmc());
				pdaDatosPopupReferencia.setSoloReparto(vSurtidoTienda.getSoloReparto());

			  	ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			  	referenciasCentro.setCodArt(codArt);
			  	referenciasCentro.setCodCentro(user.getCentro().getCodCentro());

				VMapaReferencia mapaRef = this.obtenerMapaReferencia(referenciasCentro);
							  	
				if (mapaRef != null){
					pdaDatosPopupReferencia.setMapaReferencia(mapaRef.getMapa());
				}else{
				  	pdaDatosPopupReferencia.setMapaReferencia(null);
				}
			}
			
			model.addAttribute("pdaDatosPopupReferencia", pdaDatosPopupReferencia);
			
			if (Constantes.MENU_PDA_PREHUECOS.equals(origen)){
				model.addAttribute("origen", Constantes.MENU_PDA_PREHUECOS);
			}else{
				model.addAttribute("origen", Constantes.MENU_PDA_PREHUECOSPED);
			}
		
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return "pda_p14_refCentroPopup";
	}
	
	@RequestMapping(value = "/pdaP14refCentroOkPopup", method = RequestMethod.GET)
	public String showFormOk(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final String procede,
			@Valid final String origen) {
		
		try {
			
			User user = (User) session.getAttribute("user");

			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));
			
			//Obtenemos la descripcion del articulo.
			VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);
			String descArt = "";
			if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null){
				descArt =vDatosDiarioArtResul.getDescripArt();
			}
			pdaDatosCab.setDescArtCab(descArt);
			model.addAttribute("pdaDatosCab", pdaDatosCab);			
			
			PdaDatosPopupReferencia pdaDatosPopupReferencia = new PdaDatosPopupReferencia();
			pdaDatosPopupReferencia.setCodArt(codArt);
			pdaDatosPopupReferencia.setDescArt(descArt);
			pdaDatosPopupReferencia.setProcede(procede);
			
			// Tratamiento Vegalsa
			pdaDatosPopupReferencia.setTratamientoVegalsa(utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), codArt));
			if (pdaDatosPopupReferencia.isTratamientoVegalsa()){
				VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
				vSurtidoTienda.setCodArt(codArt);
				vSurtidoTienda.setCodCentro(user.getCentro().getCodCentro());
				vSurtidoTienda = this.vSurtidoTiendaService.findOneVegalsa(vSurtidoTienda);
				pdaDatosPopupReferencia.setFechaMmc(vSurtidoTienda.getFechaMmc());
				pdaDatosPopupReferencia.setSoloReparto(vSurtidoTienda.getSoloReparto());

			  	ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			  	referenciasCentro.setCodArt(codArt);
			  	referenciasCentro.setCodCentro(user.getCentro().getCodCentro());
				
				pdaDatosPopupReferencia.setMapaReferencia(this.obtenerMapaReferencia(referenciasCentro).getMapa());
			}
			
			model.addAttribute("pdaDatosPopupReferencia", pdaDatosPopupReferencia);

			if (Constantes.MENU_PDA_PREHUECOS.equals(origen)){
				model.addAttribute("origen", Constantes.MENU_PDA_PREHUECOS);
			}else{
				model.addAttribute("origen", Constantes.MENU_PDA_PREHUECOSPED);
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return "pda_p14_refCentroOkPopup";
	}
	
	@RequestMapping(value = "/pdaP14Paginar", method = RequestMethod.GET)
	public String pdaP14Paginar(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final String procede,
			@Valid final String pgNoAct,
			@Valid final String pgTotNoAct,
			@Valid final String pgMMC,
			@Valid final String pgTotMMC,
			@Valid final String botPag) {

		try{
			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));
			
			//Obtenemos la descripci�n del art�culo.
			VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);
			String descArt = "";
			if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null){
				descArt =vDatosDiarioArtResul.getDescripArt();
			}
			pdaDatosCab.setDescArtCab(descArt);
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			
			//En funcion del boton que se haya pulsado tendremos que obtener la sublista que corresponda.
			Page<PdaMotivo> pagNoActivo = paginarListaNoActiva(session,pgNoAct,pgTotNoAct,botPag);
			
			//Llamamos al método que se encarga obtener el objeto page de la lista de MMC
			Page<PdaMotivo> pagMMC = paginarListaMMC(session,pgMMC,pgTotMMC,botPag);
			
			PdaDatosPopupReferencia pdaDatosPopupReferencia = new PdaDatosPopupReferencia();
			pdaDatosPopupReferencia.setCodArt(codArt);
			pdaDatosPopupReferencia.setDescArt(descArt);
			pdaDatosPopupReferencia.setProcede(procede);
			pdaDatosPopupReferencia.setPagNoActivo(pagNoActivo);
			pdaDatosPopupReferencia.setPagMMC(pagMMC);
			
			if (pagNoActivo != null && pagNoActivo.getRecords() != null && !pagNoActivo.getRecords().equals("0")){
				pdaDatosPopupReferencia.setFlgNoActivo("S");
			}else{
				pdaDatosPopupReferencia.setFlgNoActivo("N");
			}
			
			if (pagMMC != null && pagMMC.getRecords() != null &&  !pagMMC.getRecords().equals("0")){
				pdaDatosPopupReferencia.setFlgMMC("S");
			}else{
				pdaDatosPopupReferencia.setFlgMMC("N");
			}
			
			model.addAttribute("pdaDatosPopupReferencia", pdaDatosPopupReferencia);
		
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return "pda_p14_refCentroPopup";
	}
	
	private String cargaListasWSSesion(Long codArt, Long grupo1, HttpSession session) throws Exception{
		
		List<PdaMotivo> listMotGeneral = new ArrayList<PdaMotivo>();
		List<PdaMotivo> listMotMMC = new ArrayList<PdaMotivo>();
		String result= "";
		String pedible="";
		Locale locale = LocaleContextHolder.getLocale();
		  try {
			  	//Obtenemos de sesi�n la informaci�n de usuario.
				User user = (User)session.getAttribute("user");
				
			  	ReferenciasCentro vReferenciasCentro = new ReferenciasCentro();
			  	vReferenciasCentro.setCodArt(codArt);
			  	vReferenciasCentro.setCodCentro(user.getCentro().getCodCentro());
			  	
	        	ValidarReferenciasResponseType resultado= this.pedidosCentroService.findPedidosCentroWS(vReferenciasCentro);
	        	if (!resultado.getCodigoRespuesta().equals("0")){
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
							if(new Long(Constantes.AREA_ALIMENTACION).equals(grupo1)
									|| new Long(Constantes.AREA_BAZAR).equals(grupo1)
									|| new Long(Constantes.AREA_FRESCOS).equals(grupo1)
									|| new Long(Constantes.AREA_TEXTIL).equals(grupo1) 
									|| new Long(Constantes.AREA_ELECTRO).equals(grupo1)){
								VSurtidoTienda vSurtidoTienda = obtenerSurtidoTienda(vReferenciasCentro,false,false);
								if(vSurtidoTienda != null){
									if(("B").equals(vSurtidoTienda.getCatalogo())){
										String empuje = plataformaService.estaEnModoEmpuje(vReferenciasCentro.getCodCentro(), vReferenciasCentro.getCodArt());
										if(("S").equals(empuje)){
											if(new Long(Constantes.AREA_ELECTRO).equals(grupo1)){
												if((messageSource.getMessage("p13_referenciasCentro.msgWs.modoEmpuje.msg1", null, locale).equals(mot.getDescripcion()))
														||(messageSource.getMessage("p13_referenciasCentro.msgWs.modoEmpuje.msg2", null, locale).equals(mot.getDescripcion()))){
													mot.setDescripcion(messageSource.getMessage("p13_referenciasCentro.modoEmpuje.area.electro", null, locale));
												}
											}else if(new Long(Constantes.AREA_ALIMENTACION).equals(grupo1)
													|| new Long(Constantes.AREA_BAZAR).equals(grupo1)
													|| new Long(Constantes.AREA_FRESCOS).equals(grupo1)
													|| new Long(Constantes.AREA_TEXTIL).equals(grupo1)){
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
	    						 this.posicionNoAct++;
	    						 listMotGeneral.add(new PdaMotivo(new PdaTextoMotivo(mot.getDescripcion(),motivoAccion),"WS",this.posicionNoAct));
	    					 }else{
	    						 this.posicionMMC++;
	    						 listMotMMC.add(new PdaMotivo(new PdaTextoMotivo(mot.getDescripcion(),motivoAccion),"WS",this.posicionMMC));
	    					 }
	    				 } 
 
	    			 }
	    		}
		  }	catch (Exception e) {
			
			  session.setAttribute("listMotGeneral",  new ArrayList<PdaMotivo>());
			  session.setAttribute("listMotMMC",  new ArrayList<PdaMotivo>());
			  session.setAttribute("pedible", null);
			  result="-1";
		  }
		  session.setAttribute("listMotGeneral", listMotGeneral);
		  session.setAttribute("listMotMMC", listMotMMC);
		  session.setAttribute("pedible", pedible);
		  return result;
	}
	
	private Page<PdaMotivo> cargarMotivoNoActivoNoWS(Long codArt, HttpSession session) throws Exception{
		
		List<PdaMotivo> listaMotivosWebService =new ArrayList<PdaMotivo>();
		
	    listaMotivosWebService = (List<PdaMotivo>) session.getAttribute("listMotGeneral");
	    
		List<PdaMotivo> listaMotivosMapaHoy = new ArrayList<PdaMotivo>();
		List<PdaMotivo> listaMotivosTextoPedible = new ArrayList<PdaMotivo>();
        List<PdaMotivo> listaMotivos = new ArrayList<PdaMotivo>();
	
		boolean textilPedible = false;

        try 
        {
        	//Obtenemos de sesi�n la informaci�n de usuario.
			User user = (User)session.getAttribute("user");
			
        	ReferenciasCentro referenciasCentro = new ReferenciasCentro();
    		
    		referenciasCentro.setCodArt(codArt);
    		referenciasCentro.setCodCentro(user.getCentro().getCodCentro());
    		referenciasCentro.setDiarioArt(obtenerDiarioArt(referenciasCentro));
    		
    		//Saber si el centro es de caprabo para obtener los mapas o no	
    		boolean isCaprabo = false;
    		if(user.getCentro().esCentroCaprabo()){
    			isCaprabo = true;
    		}
    		boolean isCapraboEspecial = false;
    		if(user.getCentro().esCentroCapraboEspecial()){
    			isCapraboEspecial = true;
    		}
    		VSurtidoTienda surtidoTienda = obtenerSurtidoTienda(referenciasCentro,isCaprabo,isCapraboEspecial);
    	
    		String mapaHoy = surtidoTienda != null ? (surtidoTienda.getMapaHoy() != null ? surtidoTienda.getMapaHoy() : "N") : "N"; 
    		String pedir = surtidoTienda != null ? (surtidoTienda.getPedir() != null ? surtidoTienda.getPedir() : "N") : "N";
    		String pedible =  (String) session.getAttribute("pedible");
    		PdaMotivo motivo1 = new PdaMotivo();
    		PdaMotivo motivo2 = new PdaMotivo();
    		PdaMotivo motivoCAPRABO = new PdaMotivo();

        	//Comprobamos si la referencia es de textil pedible
		    textilPedible = this.vArtCentroAltaService.esTextilPedible(user.getCentro(), codArt); 

		    if (pedir.toUpperCase().equals("S")){        		
		    	//NO mostrar el mensaje del motivo para referencias DESCENTRALIZADAS
				if(!(this.tipoAprovisionamientoDescentralizado.equals(surtidoTienda != null ? (surtidoTienda.getTipoAprov() != null ? surtidoTienda.getTipoAprov() : "C") : "C"))){
			    	Locale locale = LocaleContextHolder.getLocale();        		        	
	    		        		
	    			//El motivo variará si es un hiper u otro tipo de centro.
					String motivoMulti;
					if((Constantes.CENTRO_NEGOCIO_HIPER).equals(user.getCentro().getNegocio())){
						if((surtidoTienda != null ? (surtidoTienda.getTipoAprov() != null ? surtidoTienda.getTipoAprov() : "C"):"C").equals(this.tipoAprovisionamientoGrupaje)){	
							motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsoc", null, locale);								
						}else{
							motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
						}
					}else{
						motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
					}
	    			
	        		PdaTextoMotivo textoMotivo1 = new PdaTextoMotivo(motivoMulti, "");
	        		
	        		motivo1.setTextoMotivo(textoMotivo1);
	        		motivo1.setTipoMensaje("SF"); //sin formato
	        		this.posicionNoAct++;
	        		motivo1.setPosicion(this.posicionNoAct);
	        		listaMotivos.add(motivo1);
	        		session.setAttribute("listMotGeneral", listaMotivos);
	    			session.setAttribute("listMotMMC",  new ArrayList<PdaMotivo>());
				}
				
        	}else{
        		
        		//Preguntamos por pedible
        		if (pedible.toUpperCase().equals("S")){
        			//pedible="S"
        			//Preguntamos por Mapa hoy
        			if (mapaHoy.toUpperCase().equals("S")){
        				Locale locale = LocaleContextHolder.getLocale();
    	    			String motivoMulti =  this.messageSource.getMessage("p18_referenciasCentroPopupPedir.manianaAct", null, locale);
    	    			PdaTextoMotivo textoMotivo1 = new PdaTextoMotivo(motivoMulti, "");
    	        		motivo1.setTextoMotivo(textoMotivo1);
    	        		motivo1.setTipoMensaje("NS"); //sin formato
    	        		this.posicionNoAct++;
    	        		motivo1.setPosicion(this.posicionNoAct);
    	        		listaMotivos.add(motivo1);
    	        		session.setAttribute("listMotGeneral", listaMotivos);
    	        		session.setAttribute("listMotMMC",  new ArrayList<PdaMotivo>());
        			}else{			    	        		
    	        		//NO mostrar el mensaje para referencias DESCENTRALIZADAS
        				if(!(this.tipoAprovisionamientoDescentralizado.equals(surtidoTienda != null ? (surtidoTienda.getTipoAprov() != null ? surtidoTienda.getTipoAprov() : "C"):"C"))){
							Locale locale = LocaleContextHolder.getLocale();
			    	        		
							//El motivo variará si es un hiper u otro tipo de centro.
							String motivoMulti;
							if((Constantes.CENTRO_NEGOCIO_HIPER).equals(user.getCentro().getNegocio())){
								if((surtidoTienda != null ? (surtidoTienda.getTipoAprov() != null ? surtidoTienda.getTipoAprov() : "C"):"C").equals(this.tipoAprovisionamientoGrupaje)){											
									motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsoc", null, locale);								
								}else{
									motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
								}
							}else{
								motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
							}
										
							PdaTextoMotivo textoMotivo1 = new PdaTextoMotivo(motivoMulti, "");
							motivo1.setTextoMotivo(textoMotivo1);
							motivo1.setTipoMensaje("SF"); //sin formato
							this.posicionNoAct++;
	    	        		motivo1.setPosicion(this.posicionNoAct);
	    	        		listaMotivos.add(motivo1);
	    	        		session.setAttribute("listMotGeneral", listaMotivos);
	    	        		session.setAttribute("listMotMMC",  new ArrayList<PdaMotivo>());
						}			    								    	        	
	        		}			        			
        		}else{
			        //pedible="N"
			    	if (mapaHoy.toUpperCase().equals("N")){			    	        		
			    	//NO mostrar el mensaje para referencias DESCENTRALIZADAS
			    		if (!(this.tipoAprovisionamientoDescentralizado.equals(surtidoTienda != null ? (surtidoTienda.getTipoAprov() != null ? surtidoTienda.getTipoAprov() : "C"):"C"))){
			    			Locale locale = LocaleContextHolder.getLocale();
			    	        		
							//El motivo variará si es un hiper u otro tipo de centro.
			    			String motivoMulti;
							if((Constantes.CENTRO_NEGOCIO_HIPER).equals(user.getCentro().getNegocio())){
								if((surtidoTienda != null ? (surtidoTienda.getTipoAprov() != null ? surtidoTienda.getTipoAprov() : "C"):"C").equals(this.tipoAprovisionamientoGrupaje)){
									motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsoc", null, locale);								
								}else{
									motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
							}
						}else{
							motivoMulti = this.messageSource.getMessage("p18_referenciasCentroPopupPedir.noMapaAsocNoHiper", null, locale);
						}
										
						PdaTextoMotivo textoMotivo1 = new PdaTextoMotivo(motivoMulti, "");
						motivo1.setTextoMotivo(textoMotivo1);
						motivo1.setTipoMensaje("SF"); //sin formato
						this.posicionNoAct++;
						motivo1.setPosicion(this.posicionNoAct);
						listaMotivosMapaHoy.add(motivo1);
					}			    					
			    }
			        			//Compruebo si es textil pedible para añadir un nuevo motivo
			    if (textilPedible) {
			    	Locale locale = LocaleContextHolder.getLocale();
			    	String texto1 =  this.messageSource.getMessage("p18_referenciasCentroPopupPedir.textilPedible.texto1", null, locale);
			    	String texto2 =  this.messageSource.getMessage("p18_referenciasCentroPopupPedir.textilPedible.texto2", null, locale);
			    	PdaTextoMotivo textoMotivo2 = new PdaTextoMotivo(texto1, texto2);
			    	motivo2.setTextoMotivo(textoMotivo2);
			    	motivo2.setTipoMensaje("WS");
			    	listaMotivosTextoPedible.add(motivo2);
			    }
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
			listaMotivos = regenerarPosicionesListaMotivos(listaMotivos);
			session.setAttribute("listMotGeneral", listaMotivos);

			//Peticion 48922. Si PEDIR es igual a N  y es Baja catalogo (V_SURTIDO_TIENDA.CATALOGO=B)         		
			VSurtidoTienda vSurtidoTienda = this.obtenerSurtidoTienda(referenciasCentro,isCaprabo,isCapraboEspecial);
				
			if (((pedir != null) && (pedir.toUpperCase().equals("N"))) && (vSurtidoTienda != null && vSurtidoTienda.getCatalogo().equals("B"))) {
	
        			//LLAMAR AL PROCEDIMIENTO PK_APR_MISUMI.ESTADOS_CONVERGENCIA
        			EstadoConvergencia estadoConvergencia = new EstadoConvergencia(referenciasCentro.getCodArt(), 
        					referenciasCentro.getCodCentro());
        			
        			String resultadoEstadoConvergencia = (String) estadoConvergenciaService.consultaEstadoConvergencia(estadoConvergencia);	
        			
        			if ((resultadoEstadoConvergencia != null) && (!resultadoEstadoConvergencia.equals(""))) {
        				//Obtener listaMotMCC de session, informar con el nuevo mensaje que ha devuelto el procedimiento y volver a meterlo a session
        				List<PdaMotivo> listMotMMC = (List<PdaMotivo>) session.getAttribute("listMotMMC");
        				this.posicionMMC++;
						listMotMMC.add(new PdaMotivo(new PdaTextoMotivo(resultadoEstadoConvergencia,""),"WS",this.posicionMMC));
						session.setAttribute("listMotMMC", listMotMMC);
        			}
        		}

        	}
        } catch (Exception e) {
        	//logger.error(StackTraceManager.getStackTrace(e));
        	throw e;
        }
        
        Page<PdaMotivo> result = null;
        
        //Si la lista tiene m�s de dos elementos tenemos que obtener la sublista s�lo con los dos primeros elementos.
        int records = listaMotivos.size();
        if (listaMotivos.size()>Constantes.PAGS_MOTIVOS){
        	listaMotivos = obtenerSubLista(listaMotivos,1,2,records);
        }

		if (listaMotivos.size()>0) {
			PaginationManager<PdaMotivo> paginationManager = new PaginationManagerImpl<PdaMotivo>();
			result = paginationManager.paginate(new Page<PdaMotivo>(), listaMotivos, 2, records, 1);
		} else {
			return new Page<PdaMotivo>();
		}
		return result;
	}
	
	private Page<PdaMotivo> cargarMotivoNoActivoMMC(HttpSession session) throws Exception{
		
		List<PdaMotivo> listaGeneral = (List<PdaMotivo>) session.getAttribute("listMotMMC");

		Page<PdaMotivo> result = null;
		
		//Si la lista tiene m�s de dos elementos tenemos que obtener la sublista s�lo con los dos primeros elementos.
		int records = listaGeneral.size();
        if (listaGeneral.size()>Constantes.PAGS_MOTIVOS){
        	listaGeneral = obtenerSubLista(listaGeneral,1,2,records);
        }
	
		if (listaGeneral.size()>0) {
			PaginationManager<PdaMotivo> paginationManager = new PaginationManagerImpl<PdaMotivo>();
			result = paginationManager.paginate(new Page<PdaMotivo>(), listaGeneral, 2, records, 1);	
			
		} else {
			return new Page<PdaMotivo>();
		}
		return result;
	}
	
	private Page<PdaMotivo> paginarListaNoActiva(HttpSession session, String pgNoAct, String pgTotNoAct, String botPag) throws Exception{
		
		List<PdaMotivo> listaGeneral = (List<PdaMotivo>) session.getAttribute("listMotGeneral");
		
		Page<PdaMotivo> result = null;
		int records = 0;
		int page = 1;
		
		if (listaGeneral.size()>0) {
		
			records = listaGeneral.size();
			page = Integer.parseInt(pgNoAct);
			int pageTot = Integer.parseInt(pgTotNoAct);
			
			if (botPag.endsWith("NoAct")){
				if (botPag.equals("firstNoAct")){
					page = 1;
					pageTot = 2;
				}else if (botPag.equals("prevNoAct")){
					page = page -1;
				}else if (botPag.equals("nextNoAct")){
					page = page +1;
				}else if (botPag.equals("lastNoAct")){
					page = pageTot;
				}
			}
			
			if (listaGeneral.size()>Constantes.PAGS_MOTIVOS){
				listaGeneral = this.obtenerSubLista(listaGeneral,page,pageTot,records);
	        }
		}
		
		if (listaGeneral.size()>0) {
			PaginationManager<PdaMotivo> paginationManager = new PaginationManagerImpl<PdaMotivo>();
			result = paginationManager.paginate(new Page<PdaMotivo>(), listaGeneral, 2, records, page);	
			
		} else {
			return new Page<PdaMotivo>();
		}

		return result;
	}
	
	private Page<PdaMotivo> paginarListaMMC(HttpSession session, String pgMMC, String pgTotMMC, String botPag) throws Exception{
		
		List<PdaMotivo> listaGeneral = (List<PdaMotivo>) session.getAttribute("listMotMMC");
		Page<PdaMotivo> result = null;
		int records = 0;
		int page = 1;
		
		if (listaGeneral.size()>0) {
			
			records = listaGeneral.size();
			page = Integer.parseInt(pgMMC);
			int pageTot = Integer.parseInt(pgTotMMC);
			
			if (botPag.endsWith("MMC")){
				if (botPag.equals("firstMMC")){
					page = 1;
					pageTot = 2;
				}else if (botPag.equals("prevMMC")){
					page = page -1;	
				}else if (botPag.equals("nextMMC")){
					page = page +1;
				}else if (botPag.equals("lastMMC")){
					page = pageTot;
				}
			}
			
			if (listaGeneral.size()>Constantes.PAGS_MOTIVOS){
				listaGeneral = this.obtenerSubLista(listaGeneral,page,pageTot,records);
	        }
		}
		
		if (listaGeneral.size()>0) {
			PaginationManager<PdaMotivo> paginationManager = new PaginationManagerImpl<PdaMotivo>();
			result = paginationManager.paginate(new Page<PdaMotivo>(), listaGeneral, 2, records, page);	
		} else {
			return new Page<PdaMotivo>();
		}

		return result;
	}
	
	private List<PdaMotivo> obtenerSubLista(List<PdaMotivo> lista, int pag, int pagTot, int records) throws Exception{
		
		List<PdaMotivo> result = null;
		
		int inicio = Constantes.PAGS_MOTIVOS*(pag-1);
		
		int fin = 0;	
		if (pag == pagTot){
			fin = records;
		}else{
			fin = Constantes.PAGS_MOTIVOS*pag;
		}
		
		result = (lista).subList(inicio, fin);
		
		return result;
	}
	
	private List<PdaMotivo> regenerarPosicionesListaMotivos (List<PdaMotivo> lista) throws Exception{
		List<PdaMotivo> listaResultado = lista;
		
		for (int i=0; i<listaResultado.size(); i++){
			((PdaMotivo) listaResultado.get(i)).setPosicion(i+1);
		}
		
		return listaResultado;
	}
}