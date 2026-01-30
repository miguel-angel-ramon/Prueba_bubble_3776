package es.eroski.misumi.control;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.InventarioRotativo;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosInventarioLibre;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.service.iface.InventarioRotativoService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.VRotacionRefService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP41InvLibSelOperativaController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP41InvLibSelOperativaController.class);

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private StockTiendaService correccionStockService;

	@Autowired
	private InventarioRotativoService inventarioRotativoService;

	@Autowired
	private VRotacionRefService vRotacionRefService;

	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	
	
	
	@RequestMapping(value = "/pdaP41InvLibSelOperativa",method = RequestMethod.POST)
	public String processForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "pda_p40_selFiabilidad";
	}
	
	@RequestMapping(value = "/pdaP41InvLibSelOperativa",method = RequestMethod.GET)
	public String showForm(ModelMap model,@Valid final String cleanAll,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		// Validar objeto
		String resultado = "pda_p41_invLibSelOperativa";
		try {

			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();
			String mac = user.getMac();
	
			//Tratamiento de secciones
			PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();
			pdaDatosInventarioLibre.setCodCentro(codCentro);
			pdaDatosInventarioLibre.setCodMac(mac);
	
			//Se borran las referencias ya guardadas para quitar las referencias con avisos
			this.borrarReferenciasGuardadasSesion(session);
			
			//Comprobación de si existen datos para mostrar la opción de continuar o empezar de cero
			if (!this.inventarioRotativoService.existenDatosInventarioLibrePda(pdaDatosInventarioLibre)){
				session.removeAttribute("hashMapClavesInvLib");
				session.removeAttribute("listaInvLib");
				PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
				model.addAttribute("pdaDatosCab", pdaDatosCab);

				resultado = "pda_p42_inventarioLibre";
			}else{
	
				if (cleanAll != null && cleanAll.equals("N")) {
					
					this.obtenerReferenciasSesion(session);
					
					List<PdaDatosInventarioLibre> listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
					if (!listaInvLib.isEmpty()) {
						HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
						Integer posicionMap = 1;
						for (PdaDatosInventarioLibre invLib : listaInvLib){
							mapClavesInvLib.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
							invLib.setPosicion(posicionMap);
							posicionMap++;
						}
						session.setAttribute("hashMapClavesInvLib", mapClavesInvLib);
						PdaDatosInventarioLibre pdaDatosInvLibResultado = this.obtenerReferenciaPorPosicion(session);
						PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
						pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosInvLibResultado.getCodArticulo()));
						model.addAttribute("pdaDatosCab", pdaDatosCab);
						model.addAttribute("pdaDatosInvLib", pdaDatosInvLibResultado);
						
						this.refrescarSecciones(model, pdaDatosInvLibResultado, session);
						
					} else {
						session.removeAttribute("hashMapClavesInvLib");
						session.removeAttribute("listaInvLib");
						PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
						model.addAttribute("pdaDatosCab", pdaDatosCab);
					}
					model.addAttribute("origenContinuar", "S");
					
					resultado =  "pda_p42_inventarioLibre";
				}
	
				if (cleanAll != null && cleanAll.equals("Y")) {
	
					this.borrarReferenciasSesion(session);
					
					session.removeAttribute("hashMapClavesInvLib");
					session.removeAttribute("listaInvLib");
					PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
					model.addAttribute("pdaDatosCab", pdaDatosCab);
	
					resultado = "pda_p42_inventarioLibre";
				}
			}

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	
	private void borrarReferenciasSesion(HttpSession session) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		InventarioRotativo inventarioRotativo = new InventarioRotativo();
		inventarioRotativo.setCodCentro(codCentro);
		inventarioRotativo.setCodMac(mac);
		
		this.inventarioRotativoService.delete(inventarioRotativo);
	}

	private void borrarReferenciasGuardadasSesion(HttpSession session) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		InventarioRotativo inventarioRotativo = new InventarioRotativo();
		inventarioRotativo.setCodCentro(codCentro);
		inventarioRotativo.setCodMac(mac);
		
		this.inventarioRotativoService.deleteGuardados(inventarioRotativo);
	}

	private PdaDatosInventarioLibre obtenerReferenciasSesion(HttpSession session) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();
		pdaDatosInventarioLibre.setCodCentro(codCentro);
		pdaDatosInventarioLibre.setCodMac(mac);
		
		List<PdaDatosInventarioLibre> listaDatosInventarioLibre = this.inventarioRotativoService.findAllPda(pdaDatosInventarioLibre, true);
		
		//Obtener MMC
		if (listaDatosInventarioLibre != null){
			Iterator<PdaDatosInventarioLibre> it = listaDatosInventarioLibre.iterator();
			while (it.hasNext()){
				PdaDatosInventarioLibre pdaDatosInvLib = it.next();
				VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(pdaDatosInvLib.getCodArticulo());
				
				ReferenciasCentro referenciasCentro = new ReferenciasCentro();
				referenciasCentro.setCodArt(pdaDatosInvLib.getCodArticulo());
				referenciasCentro.setCodCentro(codCentro);
				referenciasCentro.setDiarioArt(vDatosDiarioArtResul);
				VSurtidoTienda surtidoTienda = obtenerSurtidoTienda(referenciasCentro,false,false);
				if (surtidoTienda != null && surtidoTienda.getMarcaMaestroCentro()!= null && !"".equals(surtidoTienda.getMarcaMaestroCentro()))
				{
					pdaDatosInvLib.setMmc(surtidoTienda.getMarcaMaestroCentro());
				}else{
					pdaDatosInvLib.setMmc("N");
				}
			}
		}

		HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();

		if (listaDatosInventarioLibre == null || listaDatosInventarioLibre.isEmpty()){
			session.removeAttribute("listaInvLib");
			session.removeAttribute("hashMapClavesInvLib");
		}else{
			session.setAttribute("listaInvLib", listaDatosInventarioLibre);
			HashMap<String,Integer> mapAux = new HashMap<String,Integer>();
			Integer posicionMap = 0;
			if (listaDatosInventarioLibre != null) {
				Iterator<PdaDatosInventarioLibre> it  = listaDatosInventarioLibre.iterator();
				while(it.hasNext() && !listaDatosInventarioLibre.isEmpty() ){
					PdaDatosInventarioLibre invLib = it.next();
					invLib.setPosicion(posicionMap.intValue() + 1);
					mapAux.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
					posicionMap++;
				}
			}
			session.setAttribute("hashMapClavesInvLib", mapAux);
		}
		
		if (session.getAttribute("hashMapClavesInvLib") != null && session.getAttribute("listaInvLib") != null)
		{
			mapClavesInvLib = (HashMap<String, Integer>) session.getAttribute("hashMapClavesInvLib");
			
			listaDatosInventarioLibre = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
			
			pdaDatosInventarioLibre = (PdaDatosInventarioLibre) listaDatosInventarioLibre.get(0);
			
			//Si no es referencia única y no tiene varias unitarias hay que refrescar los datos de pantalla con la suma de cantidades
			if (!Constantes.INVENTARIO_LIBRE_UNICA_SI.equals(pdaDatosInventarioLibre.getFlgUnica()) && 
					!Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
				this.cargarDatosSumaTablaTemporal(pdaDatosInventarioLibre);
			}
			
			//Refrescamos el total del objeto.
			pdaDatosInventarioLibre.setTotal(mapClavesInvLib.size());
			
			//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
			session.setAttribute("ultimaRefInvLib", String.valueOf(pdaDatosInventarioLibre.getCodArticulo()));
		}
		
		return pdaDatosInventarioLibre;
	}

	private PdaDatosInventarioLibre obtenerReferenciaPorPosicion(HttpSession session) throws Exception{
		
		Locale locale = LocaleContextHolder.getLocale();
		
		PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();
		List<PdaDatosInventarioLibre> listaInvLib =new ArrayList<PdaDatosInventarioLibre>();
		HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();

		if (session.getAttribute("hashMapClavesInvLib") != null && session.getAttribute("listaInvLib") != null)
		{
			mapClavesInvLib = (HashMap<String, Integer>) session.getAttribute("hashMapClavesInvLib");
			
			listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
			
			pdaDatosInventarioLibre = (PdaDatosInventarioLibre) listaInvLib.get(0);
			
			//Si no es referencia única y no tiene varias unitarias hay que refrescar los datos de pantalla con la suma de cantidades
			if (!Constantes.INVENTARIO_LIBRE_UNICA_SI.equals(pdaDatosInventarioLibre.getFlgUnica()) && 
					!Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
				this.cargarDatosSumaTablaTemporal(pdaDatosInventarioLibre);
			}

			//Obtenemos el stock actual.
			ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
			stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(pdaDatosInventarioLibre.getCodCentro()));
			stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
			List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
			listaReferencias.add(BigInteger.valueOf(pdaDatosInventarioLibre.getCodArticulo()));
			stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
			boolean errorConsultaStock = false;
			try{
				
				User user = (User) session.getAttribute("user");
				ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
				paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
				ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
				
				if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
					logger.error("###########################################################################################################");
					logger.error("############################## CONTROLADOR: pdaP41InvLibSelOperativaController	 ########################");
					logger.error("###########################################################################################################");
				}
				ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest, session);
				if (null == stockTiendaResponse || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
					pdaDatosInventarioLibre.setStockActual(new Double(0));
					errorConsultaStock = true;
				}else{
					if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
						pdaDatosInventarioLibre.setStockActual(stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue());
					} else {
						pdaDatosInventarioLibre.setStockActual(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue());
					}
				}				
			}catch (Exception e) {
				errorConsultaStock = true;
				pdaDatosInventarioLibre.setStockActual(new Double(0));
			}	

			//Redondeo a 2 decimales del stock
			//Peticion 55001. Corrección errores del LOG.
			BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getSalaStock());
		    BigDecimal roundedSalaStock = bdSalaStock.setScale(2, BigDecimal.ROUND_HALF_UP);

			//Redondeo a 2 decimales del stock
			//Peticion 55001. Corrección errores del LOG.
			BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getCamaraStock());
			BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);

			BigDecimal roundedTotalStock = roundedCamaraStock.add(roundedSalaStock);
			pdaDatosInventarioLibre.setTotalStock(Utilidades.convertirDoubleAString(roundedTotalStock.doubleValue(),"###0.00").replace(',', '.'));

			if (Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS.equals(pdaDatosInventarioLibre.getFlgStockPrincipal())){
				Long lCamaraBandeja = new Long(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");
				Long lSalaBandeja = new Long(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
				
				//Cálculo de campos calculados
				Long totalBandejas = lCamaraBandeja + lSalaBandeja; 
				pdaDatosInventarioLibre.setTotalBandeja(totalBandejas.toString());
				Double diferencia = totalBandejas - pdaDatosInventarioLibre.getStockActual();
				if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
					//Si tiene referencias unitarias hay que mostrar diferencia 0
					diferencia = new Double(0);
				}
				pdaDatosInventarioLibre.setDiferencia(diferencia);
			}else{
				Double diferencia = roundedTotalStock.doubleValue() - pdaDatosInventarioLibre.getStockActual();
				if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
					//Si tiene referencias unitarias hay que mostrar diferencia 0
					diferencia = new Double(0);
				}
				pdaDatosInventarioLibre.setDiferencia(diferencia);
			}

			//Formateo de cantidades de stock
			pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(roundedSalaStock.doubleValue(),"###0.00").replace(',', '.'));
			pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(roundedCamaraStock.doubleValue(),"###0.00").replace(',', '.'));
			
			//Carga de descripciones de stock actual y diferencia
			if (errorConsultaStock){
				pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
						"pda_p42_inventarioLibre.stockActualError", null, locale));
				pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
						"pda_p42_inventarioLibre.diferenciaError", null, locale));
			}else{
				pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
						"pda_p42_inventarioLibre.stockActual", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getStockActual(),"###0.00").replace('.', ',')}, locale));
				pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
						"pda_p42_inventarioLibre.diferencia", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
			}
			
			if (null == pdaDatosInventarioLibre.getOrigenGISAE() || !"SI".equals(pdaDatosInventarioLibre.getOrigenGISAE())){
				//Obtener el tipo de rotacion
				pdaDatosInventarioLibre.setTipoRotacion(this.obtenerTipoRotacion(pdaDatosInventarioLibre));
			}

			//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
			session.setAttribute("ultimaRefInvLib", String.valueOf(pdaDatosInventarioLibre.getCodArticulo()));
		}
		
		return pdaDatosInventarioLibre;
	}
	
	private void refrescarSecciones(ModelMap model, PdaDatosInventarioLibre pdaDatosInventarioLibreActual, HttpSession session) throws Exception {
		
		LinkedHashMap<Long, String> secciones = new LinkedHashMap<Long, String>();
		
		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");
		
		//Obtener secciones
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();
	
		//Tratamiento de secciones
		PdaDatosInventarioLibre pdaDatosInventarioLibreSec = new PdaDatosInventarioLibre();
		pdaDatosInventarioLibreSec.setCodCentro(codCentro);
		pdaDatosInventarioLibreSec.setCodMac(mac);
		pdaDatosInventarioLibreSec.setCodArticulo(pdaDatosInventarioLibreActual.getCodArticulo());
		secciones = this.inventarioRotativoService.findSeccionesPda(pdaDatosInventarioLibreSec);
		model.addAttribute("secciones", secciones);
	}
	
	private void cargarDatosSumaTablaTemporal(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
		
		PdaDatosInventarioLibre pdaDatosInventarioLibreSuma = this.inventarioRotativoService.findSumaCantidades(pdaDatosInventarioLibre);

		if (pdaDatosInventarioLibreSuma != null){
			Long lCamaraBandeja = new Long(pdaDatosInventarioLibreSuma.getCamaraBandeja()!=null?pdaDatosInventarioLibreSuma.getCamaraBandeja():"0");
			Long lSalaBandeja = new Long(pdaDatosInventarioLibreSuma.getSalaBandeja()!=null?pdaDatosInventarioLibreSuma.getSalaBandeja():"0");
			
			//Redondeo a 2 decimales del stock
			//Peticion 55001. Corrección errores del LOG.
			BigDecimal bdCamaraStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibreSuma.getCamaraStock());
		    BigDecimal roundedCamaraStock = bdCamaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);

			//Redondeo a 2 decimales del stock
			//Peticion 55001. Corrección errores del LOG.
			BigDecimal bdSalaStock = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibreSuma.getSalaStock());
		    BigDecimal roundedSalaStock = bdSalaStock.setScale(2, BigDecimal.ROUND_HALF_UP);

		    pdaDatosInventarioLibre.setCamaraBandeja(lCamaraBandeja.toString());
		    pdaDatosInventarioLibre.setSalaBandeja(lSalaBandeja.toString());
		    pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(roundedCamaraStock.doubleValue(),"###0.00").replace('.', ','));
		    pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(roundedSalaStock.doubleValue(),"###0.00").replace('.', ','));
		}
	}
	
	private String obtenerTipoRotacion(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
		
		String tipoRotacion = "";
		
		//Carga de parámetros de búsqueda
		VRotacionRef vRotacionRef = new VRotacionRef();
		vRotacionRef.setCodCentro(pdaDatosInventarioLibre.getCodCentro());
		vRotacionRef.setCodArt(pdaDatosInventarioLibre.getCodArticulo());
		//Es necesario filtrar por grupo1 y grupo2 por temas de rendimiento en la búsquda
		vRotacionRef.setGrupo1(pdaDatosInventarioLibre.getCodArea());
		vRotacionRef.setGrupo2(pdaDatosInventarioLibre.getCodSeccion());
		
		VRotacionRef vRotacionRefRes = this.vRotacionRefService.findOne(vRotacionRef);
    	if (vRotacionRefRes != null){
	    	tipoRotacion = vRotacionRefRes.getTipoRotTotal();		
    	}
		
		return tipoRotacion;
	}

}