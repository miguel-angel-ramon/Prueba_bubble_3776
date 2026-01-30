package es.eroski.misumi.control;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.InventarioRotativoGisae;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosInventarioLibre;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.InventarioRotativoGisaeService;
import es.eroski.misumi.service.iface.InventarioRotativoService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.VRotacionRefService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
@RequestMapping("/pdaP46GuardarInvLib")
public class pdaP46GuardarInvLibController {

	private static Logger logger = Logger.getLogger(pdaP46GuardarInvLibController.class);

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private StockTiendaService correccionStockService;

	@Autowired
	private InventarioRotativoService inventarioRotativoService;
	
	@Autowired
	private InventarioRotativoGisaeService inventarioRotativoGisaeService;

	@Autowired
	private VRotacionRefService vRotacionRefService;
	
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	@RequestMapping(method = RequestMethod.POST)
	public String showForm(ModelMap model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String resultado = "pda_p42_inventarioLibre";
		
		
		try {
			String origenGISAE = request.getParameter("origenGISAE");
			boolean isGISAE = false;
			if (null != origenGISAE && origenGISAE.equals("SI")){
				isGISAE = true;
			}
			String seccion = request.getParameter("seccion");
			Long codSeccion = null;
			if (!seccion.isEmpty()){
				codSeccion = new Long(seccion);
			}
			this.obtenerReferenciasSesion(session,codSeccion, isGISAE);
			
			List<PdaDatosInventarioLibre> listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");

			//Ahora vamos a calcular los atributos "pdaDatosCab" y "pdaDatosInvLib"
			//que vamos a pasar al model
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			PdaDatosInventarioLibre pdaDatosInvLibResultado = new PdaDatosInventarioLibre();
			if (listaInvLib != null && !listaInvLib.isEmpty()) {
				HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
				Integer posicionMap = 1;
				for (PdaDatosInventarioLibre invLib : listaInvLib){
					mapClavesInvLib.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
					invLib.setPosicion(posicionMap);
					posicionMap++;
				}
				session.setAttribute("hashMapClavesInvLib", mapClavesInvLib);
				pdaDatosInvLibResultado = this.obtenerReferenciaPorPosicion(request, session);
				pdaDatosInvLibResultado.setOrigenGISAE(origenGISAE);
				pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosInvLibResultado.getCodArticulo()));
				if (codSeccion != null){
					pdaDatosCab.setSeccion(codSeccion.toString());
				}
			} else {
				if (codSeccion != null){
					//Obtenemos todas las referencias de session
					codSeccion = null;
					this.obtenerReferenciasSesion(session,codSeccion ,isGISAE);
					if (session.getAttribute("hashMapClavesInvLib") != null && session.getAttribute("listaInvLib") != null){
						listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
						
						//Obtenemos las claves [codArticulo, posicion]
						HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
						Integer posicionMap = 1;
						for (PdaDatosInventarioLibre invLib : listaInvLib){
							mapClavesInvLib.put(String.valueOf(invLib.getCodArticulo()), posicionMap);
							invLib.setPosicion(posicionMap);
							posicionMap++;
						}
						session.setAttribute("hashMapClavesInvLib", mapClavesInvLib);
						
						//Obtenemos la primera referencia de la lista
						pdaDatosInvLibResultado = this.obtenerReferenciaPorPosicion(request, session);
						pdaDatosInvLibResultado.setOrigenGISAE(origenGISAE);
						
						//Establecemos la cabecera
						//El código de sección de la cabecera lo ponemos a null
						//por que ya no hay referencias de esa sección y mostramos
						//todas las demás referencias
						pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosInvLibResultado.getCodArticulo()));
					}
				}
				else{
					session.removeAttribute("hashMapClavesInvLib");
					session.removeAttribute("listaInvLib");
				}
			}
			
			this.refrescarSecciones(model, pdaDatosInvLibResultado, session);
			
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			model.addAttribute("pdaDatosInvLib", pdaDatosInvLibResultado);
			
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultado;
	}
	
	private PdaDatosInventarioLibre obtenerReferenciasSesion(HttpSession session, Long codSeccion, boolean isGISAE) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();
		pdaDatosInventarioLibre.setCodCentro(codCentro);
		if (!isGISAE){
			pdaDatosInventarioLibre.setCodMac(mac);
			pdaDatosInventarioLibre.setCodSeccion(codSeccion);
		} else {
			pdaDatosInventarioLibre.setCodMac("GISAE");
			pdaDatosInventarioLibre.setCodSeccion(codSeccion);
		}
		
		List<PdaDatosInventarioLibre> listaDatosInventarioLibre = null;
		if (isGISAE){
			listaDatosInventarioLibre = this.inventarioRotativoService.findAllPdaGISAE(pdaDatosInventarioLibre);
		} else {
			listaDatosInventarioLibre = this.inventarioRotativoService.findAllPda(pdaDatosInventarioLibre, true);
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
		}
		
		return pdaDatosInventarioLibre;
	}

	private PdaDatosInventarioLibre obtenerReferenciaPorPosicion(HttpServletRequest request, HttpSession session) throws Exception{
		
		Locale locale = LocaleContextHolder.getLocale();
		
		PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre();
		List<PdaDatosInventarioLibre> listaInvLib =new ArrayList<PdaDatosInventarioLibre>();
		HashMap<String, Integer> mapClavesInvLib = new HashMap<String, Integer>();
		int indiceRegistroGuardado = 0;
		
		if (session.getAttribute("hashMapClavesInvLib") != null && session.getAttribute("listaInvLib") != null)
		{
			mapClavesInvLib = (HashMap<String, Integer>) session.getAttribute("hashMapClavesInvLib");
			
			listaInvLib = (List<PdaDatosInventarioLibre>) session.getAttribute("listaInvLib");
			
			//Si es una referencia única y da aviso o error tiene que volver a esa referencia
			if ("S".equals(request.getParameter("pda_p46_volverReferenciaActual"))){
				if (session.getAttribute("ultimaRefInvLib") != null)
				{
					String codArt = (String) session.getAttribute("ultimaRefInvLib");
					
					if (mapClavesInvLib.get(codArt) != null){ //Si la referencia se ha eliminado se mostrará la primera referencia
						indiceRegistroGuardado = ((Integer)mapClavesInvLib.get(codArt)).intValue()-1;
					}
				}
			}
			
			pdaDatosInventarioLibre = (PdaDatosInventarioLibre) listaInvLib.get(indiceRegistroGuardado);
			
			//Refrescamos el total del objeto.
			pdaDatosInventarioLibre.setTotal(mapClavesInvLib.size());

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
					logger.error("############################## CONTROLADOR: pdaP46GuardarInvLibController (1)	 ####################");
					logger.error("###########################################################################################################");
				} 
				ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest, session);
				if (null == stockTiendaResponse || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponse.getCodigoRespuesta())){
					pdaDatosInventarioLibre.setStockActual(new Double(0));
					errorConsultaStock = true;
				}else{
					if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
						stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
						ConsultarStockResponseType stockTiendaResponseBasica = null;
						
						
						if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
							logger.error("###########################################################################################################");
							logger.error("############################## CONTROLADOR: pdaP46GuardarInvLibController (2)	 ####################");
							logger.error("###########################################################################################################");
						} 
						stockTiendaResponseBasica = this.correccionStockService.consultaStock(stockTiendaRequest, session);
						if (null == stockTiendaResponseBasica || Constantes.STOCK_TIENDA_RESULTADO_KO.equals(stockTiendaResponseBasica.getCodigoRespuesta())){
							pdaDatosInventarioLibre.setStockActual(new Double(0));
							errorConsultaStock = true;
						}else{
							if (stockTiendaResponseBasica.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								ReferenciaType referencia = stockTiendaResponseBasica.getListaReferencias()[0];
								pdaDatosInventarioLibre.setStockActual(referencia.getBandejas().doubleValue());
								pdaDatosInventarioLibre.setKgs(Utilidades.convertirDoubleAString((referencia.getStock() != null ? referencia.getStock().floatValue() : 0)/((referencia.getBandejas()!=null && referencia.getBandejas().floatValue() != 0)?referencia.getBandejas().floatValue():1),"###0.000"));
							} else {
								pdaDatosInventarioLibre.setStockActual(stockTiendaResponseBasica.getListaReferencias()[0].getStock().doubleValue());
							}
						}
					}
					else{
						if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
							ReferenciaType referencia = stockTiendaResponse.getListaReferencias()[0];
							pdaDatosInventarioLibre.setStockActual(referencia.getBandejas().doubleValue());
							pdaDatosInventarioLibre.setKgs(Utilidades.convertirDoubleAString((referencia.getStock() != null ? referencia.getStock().floatValue() : 0)/((referencia.getBandejas()!=null && referencia.getBandejas().floatValue() != 0)?referencia.getBandejas().floatValue():1),"###0.000"));
						} else {
							pdaDatosInventarioLibre.setStockActual(stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue());
						}
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
				Double diferencia = roundedTotalStock.doubleValue() -  pdaDatosInventarioLibre.getStockActual();
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
		if(null != pdaDatosInventarioLibreActual.getOrigenGISAE() && pdaDatosInventarioLibreActual.getOrigenGISAE().equals("SI")){
			InventarioRotativoGisae inventarioRotativo = new InventarioRotativoGisae();
			inventarioRotativo.setCodCentro(codCentro);
			inventarioRotativo.setFechaGen(new Date());
			secciones = this.inventarioRotativoGisaeService.findAllSeccion(inventarioRotativo);
		} else {
			PdaDatosInventarioLibre pdaDatosInventarioLibreSec = new PdaDatosInventarioLibre();
			pdaDatosInventarioLibreSec.setCodCentro(codCentro);
			pdaDatosInventarioLibreSec.setCodMac(mac);
			pdaDatosInventarioLibreSec.setCodArticulo(pdaDatosInventarioLibreActual.getCodArticulo());
			
			secciones = this.inventarioRotativoService.findSeccionesPda(pdaDatosInventarioLibreSec);
		}
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