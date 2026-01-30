package es.eroski.misumi.control.prehuecos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.control.pdaConsultasController;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionLinea;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.prehuecos.AlmacenPrehuecos;
import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaModType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.ListadoReposicionService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.prehuecos.iface.PrehuecosAlmacenService;
import es.eroski.misumi.service.prehuecos.iface.PrehuecosStockAlmacenService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.SessionUtils;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class PdaP118PrehuecosAlmacenController extends pdaConsultasController{
	
	private final String STOCK_ALMACEN = "/pda/prehuecos/pda_p118_AlmacenPrehuecos";

	private static Logger logger = Logger.getLogger(PdaP118PrehuecosAlmacenController.class);
	
	@Autowired
	private PrehuecosStockAlmacenService prehuecosStockAlmacenService;
	
	@Autowired
	private PrehuecosAlmacenService prehuecosAlmacenService;
	
	@Autowired
	private StockTiendaService stockTiendaService;
	
	@Autowired
	private StockTiendaService correccionStockService;
	
	@Autowired
	private ListadoReposicionService listadoReposicionService;
	
	@Autowired
	private FotosReferenciaService fotosReferenciaService; 

	private final String resultadoKO = "/pda/prehuecos/pda_p119_errorAlmacen";
	
	@RequestMapping(value = "/pdaP118PrehuecosAlmacen.do", method = RequestMethod.GET)
	public String showForm(@Valid final PdaDatosCabecera pdaDatosCab,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="codArt", required=false, defaultValue = "")String codArtCab,
			@RequestParam(value="tipoListado", required=false, defaultValue = "")String tipoListado,
			@RequestParam(value ="actionRef", required = false) String actionRef,
			@RequestParam(value="pageSubList", required=false, defaultValue = "1") Long pageSubList,
			@RequestParam(value="maxSubList", required=false, defaultValue = "4") Long maxSubList,
			@RequestParam(value="soyPaletInput", required=false, defaultValue = "false") boolean soyPaletInput,
			@RequestParam(value ="pagActual", required = false) String pagActual,
			@RequestParam(value="guardadoOK", required=false, defaultValue = "false") boolean guardadoOK,
			@RequestParam(value ="pagTotal", required = false) String pagTotal) {
		String articulo = (pdaDatosCab.getCodArtCab() != null && !pdaDatosCab.getCodArtCab().isEmpty()) 
			    ? pdaDatosCab.getCodArtCab() 
			    : codArtCab;
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String codMac = user.getMac();
		
		
		List<AlmacenPrehuecos> listaAlmacen = new ArrayList<AlmacenPrehuecos>();
		
		List<StockAlmacen> listaRef = new ArrayList<StockAlmacen>();
		
		String origenPantalla = "pdaP118PrehuecosAlmacen.do";
		
		if (tipoListado != null && !tipoListado.trim().isEmpty()) {
			 
		if(soyPaletInput == true){
			    String fallbackUrl = actionRef;
			    if (fallbackUrl != null && fallbackUrl.contains("pdaP115PrehuecosLineal.do")) {
			    	SessionUtils.limpiarAtributosLista(session);
			        fallbackUrl += (fallbackUrl.contains("?") ? "&" : "?") + "codArt=" + articulo;
			        session.setAttribute("urlBasePalet", fallbackUrl);
			        model.addAttribute("urlInputPalet", fallbackUrl);
			    }
			}

		if(soyPaletInput == false){
			if (actionRef != null) {
			    try {
			        String nuevaUrl = actionRef;
	
			        if (actionRef.contains("pdaP116Paginar.do")) {
			        	SessionUtils.limpiarAtributosLista(session);
			            nuevaUrl += (nuevaUrl.contains("?") ? "&" : "?");
			            if (pagActual != null) {
			                nuevaUrl += "&pagActual=" + pagActual;
			            }
			            if (pagTotal != null) {
			                nuevaUrl += "&pagTotal=" + pagTotal;
			            }
			        }
	
			        session.setAttribute("urlBasePalet", nuevaUrl);
			        model.addAttribute("urlInputPalet", nuevaUrl);
	
			    } catch (Exception e) {
			        logger.error(StackTraceManager.getStackTrace(e));
			    }
			}
		}
		}else{
			tipoListado = "1";
			 Object urlFromSession = session.getAttribute("urlBasePalet");
			 model.addAttribute("urlInputPalet", urlFromSession);
		}

		try{
			
			listaRef = prehuecosAlmacenService.getReferenciasPrehuecosStockAlmacen(codCentro, codMac, articulo);
			
			listaAlmacen = prehuecosAlmacenService.getReferenciasPrehuecosAlmacen(codCentro, codMac, articulo);
			
			try{
			listaAlmacen = cargarStocks(listaAlmacen,codCentro,session);
			}catch(Exception e) {
				logger.error(StackTraceManager.getStackTrace(e));
			}
				
			if (null != session.getAttribute("consultaStock")){
				listaAlmacen.get(0).setStockActivo("S");
		}else{
			listaAlmacen.get(0).setStockActivo("N");
		}
			
			Reposicion reposicionFiltro = new Reposicion();
	        reposicionFiltro.setCodLoc(codCentro);
	        reposicionFiltro.setCodMac(codMac);
	        reposicionFiltro.setCodArt(Long.parseLong(articulo));
	        String area = "NO_TEXTIL";
	        reposicionFiltro.setArea(area);
	        reposicionFiltro.setTipoListado(Long.parseLong(tipoListado));
	        ReposicionLinea reposicionLinea = new ReposicionLinea();
	        reposicionLinea.setCodArticulo(Long.parseLong(articulo));
	        reposicionLinea.setCodLoc(codCentro);
	        reposicionLinea.setCodMac(codMac);
	        List<ReposicionLinea> reposicionLineas = new ArrayList<ReposicionLinea>();
	        reposicionLineas.add(reposicionLinea);

	        reposicionFiltro.setReposicionLineas(reposicionLineas);
	        
	        try {
	        	Reposicion reposicion = listadoReposicionService.obtenerReposicion(reposicionFiltro);
	        	
	        	if (reposicion.getReposicionLineas() == null || reposicion.getReposicionLineas().isEmpty() || reposicion.getReposicionLineas().get(0).getFlgPantCorrStock() == null){
	        		reposicion.setCodArt(Long.parseLong(articulo));
		        	Pagination pagination= new Pagination(maxSubList,pageSubList);
		        	List<ReposicionLinea> reposicionLineasPaginada =  this.listadoReposicionService.findTempListadoRepo(reposicion, pagination);
		        	listaAlmacen.get(0).setFlgPantCorrStock(reposicionLineasPaginada.get(0).getFlgPantCorrStock());
	        	}
	        	listaAlmacen.get(0).setFlgPantCorrStock(reposicion.getReposicionLineas().get(0).getFlgPantCorrStock());
	        	
	        	model.addAttribute("reposicion", reposicion);

	        } catch (IndexOutOfBoundsException e){
	        	
			} catch (Exception e) {
				logger.error(StackTraceManager.getStackTrace(e));
			}
		
	        if(listaAlmacen.get(0).getFlgPantCorrStock() == null){
	        	listaAlmacen.get(0).setFlgPantCorrStock("S");
        	}
	        
	        FotosReferencia fotosReferencia = new FotosReferencia();
			fotosReferencia.setCodReferencia(Long.parseLong(articulo));

			if (fotosReferenciaService.checkImage(fotosReferencia)) {

				listaAlmacen.get(0).setTieneFoto(true);
			} else {

				listaAlmacen.get(0).setTieneFoto(false);
			}

	        ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			referenciasCentro.setCodArt(Long.parseLong(articulo));
			boolean isCaprabo = false;
			boolean isCapraboEspecial = false;
			VDatosDiarioArt vDatosDiarioArtResul = this.obtenerDiarioArt(Long.parseLong(articulo));
			
			ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
			stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
			List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
			listaReferencias.add(BigInteger.valueOf(Long.parseLong(articulo)));
			stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA);
			
			stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
			
			ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);

			if (stockTiendaResponse.getCodigoRespuesta().equals("OK")){
				if(vDatosDiarioArtResul.getGrupo1() != 3 && (stockTiendaResponse == null || stockTiendaResponse.getTipoMensaje() == null || 						
						!stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE))){
					
					    listaAlmacen.get(0).setCalculoCC("S");
				}
			}
			
			if (!listaAlmacen.isEmpty()) {
			    if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)) {
			        listaAlmacen.get(0).setStockActual(
			            Utilidades.convertirDoubleAString(
			                stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue(),
			                "###0.#"
			            )
			        );
			    } else {
			        listaAlmacen.get(0).setStockActual(
			            Utilidades.convertirDoubleAString(
			                stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue(),
			                "###0.#"
			            )
			        );
			    }
			}

	        
	        VSurtidoTienda surtidoTienda = this.obtenerSurtidoTienda(referenciasCentro,isCaprabo,isCapraboEspecial);
	        listaAlmacen.get(0).setMMC(surtidoTienda.getMarcaMaestroCentro());
	        
	        model.addAttribute("surtidoTienda", surtidoTienda);

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			return resultadoKO;
			
		}
		
		model.addAttribute("guardadoOK", guardadoOK);
		model.addAttribute("origenPantalla", origenPantalla);
		model.addAttribute("soyPaletInput", soyPaletInput);
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("listaRef", listaRef);
		model.addAttribute("listaAlmacen", listaAlmacen);
		return STOCK_ALMACEN;
	}
	
	@RequestMapping(value = "/pdaP118ActualizarStock.do", method = RequestMethod.POST)
	public @ResponseBody ModificarStockResponseType pdaP118ActualizarStock(
			@RequestParam(value = "stock", required = true) String stock,
			@RequestParam(value = "codArt", required = true) String codArt,
			HttpServletResponse response,
			HttpSession session) throws Exception{


		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		
		ModificarStockRequestType modificarStockRequest = new ModificarStockRequestType();
		modificarStockRequest.setCodigoCentro(BigInteger.valueOf(codCentro));

		ReferenciaModType ref = new ReferenciaModType();
		ref.setCodigoReferencia(new BigInteger(codArt));
		ref.setStock(new BigDecimal(stock));

		List<ReferenciaModType> referencias = new ArrayList<ReferenciaModType>();
		referencias.add(ref);

		modificarStockRequest.setListaReferencias(referencias.toArray(new ReferenciaModType[referencias.size()]));
		ModificarStockResponseType modificarStockResponseType = this.correccionStockService.modificarStock(modificarStockRequest,session);

		return modificarStockResponseType;
	}


	@RequestMapping(value = "/pdaP118PrehuecosAlmacen.do", method = RequestMethod.POST)
	public String showForm(@Valid final PdaDatosCabecera pdaDatosCab, ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("estadoSeleccionado") int estadoSeleccionado,
			@RequestParam("codArtSeleccionado") String codArtSeleccionado,
			@RequestParam(value="pageSubList", required=false, defaultValue = "1") Long pageSubList,
			@RequestParam(value="maxSubList", required=false, defaultValue = "4") Long maxSubList,
			@RequestParam(value = "urlInputPalet", defaultValue = "") String urlInputPalet,
			@RequestParam(value="guardadoOK", required=false, defaultValue = "false") boolean guardadoOK) {

		String articulo = codArtSeleccionado;
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String codMac = user.getMac();
		
		
		String origenPantalla = "pdaP118PrehuecosAlmacen.do";
		List<AlmacenPrehuecos> listaAlmacen = new ArrayList<AlmacenPrehuecos>();		
		
		List<StockAlmacen> listaRef = new ArrayList<StockAlmacen>();
		
		try {
	        if (urlInputPalet == null || urlInputPalet.isEmpty()) {
	            Object urlFromSession = session.getAttribute("urlBasePalet");
	            if (urlFromSession != null) {
	                urlInputPalet = urlFromSession.toString();
	            }
	        }
	        
	        
	        model.addAttribute("urlInputPalet", urlInputPalet);

	    } catch (Exception e) {
	        logger.error("Error al obtener el parámetro urlInputPalet: " + e.getMessage());
	        urlInputPalet = "";
	    }
		
		
		try{
			listaAlmacen = prehuecosAlmacenService.getReferenciasPrehuecosAlmacenActualizada(codCentro, codMac, articulo);
			
			prehuecosStockAlmacenService.updateEstadoPrehueco(codCentro, codMac, articulo, estadoSeleccionado);
			
			listaRef = prehuecosAlmacenService.getReferenciasPrehuecosStockAlmacen(codCentro, codMac, articulo);
			if(listaAlmacen.size()>0){
				
				listaAlmacen = cargarStocks(listaAlmacen,codCentro,session);
				
				Reposicion reposicionFiltro = new Reposicion();
		        reposicionFiltro.setCodLoc(codCentro);
		        reposicionFiltro.setCodMac(codMac);
	
		        String area = "NO_TEXTIL";
		        reposicionFiltro.setArea(area);
	
		        String tipoListado = "1";
		        reposicionFiltro.setTipoListado(Long.parseLong(tipoListado));
	
		        ReposicionLinea reposicionLinea = new ReposicionLinea();
		        reposicionLinea.setCodArticulo(Long.parseLong(articulo)); // O cambia tipo si es String
		        reposicionLinea.setCodLoc(codCentro);
		        reposicionLinea.setCodMac(codMac);
	
		        List<ReposicionLinea> reposicionLineas = new ArrayList<ReposicionLinea>();
		        reposicionLineas.add(reposicionLinea);
	
		        reposicionFiltro.setReposicionLineas(reposicionLineas);
	
		        try{
		        Reposicion reposicion = listadoReposicionService.obtenerReposicion(reposicionFiltro);
		        
		      	if (reposicion.getReposicionLineas() == null || reposicion.getReposicionLineas().isEmpty() || reposicion.getReposicionLineas().get(0).getFlgPantCorrStock() == null){
	        		reposicion.setCodArt(Long.parseLong(articulo));
	        	Pagination pagination= new Pagination(maxSubList,pageSubList);
	        	List<ReposicionLinea> reposicionLineasPaginada =  this.listadoReposicionService.findTempListadoRepo(reposicion, pagination);
	        	listaAlmacen.get(0).setFlgPantCorrStock(reposicionLineasPaginada.get(0).getFlgPantCorrStock());
	        	
	        	}
	        	listaAlmacen.get(0).setFlgPantCorrStock(reposicion.getReposicionLineas().get(0).getFlgPantCorrStock());
	        	
	        	model.addAttribute("reposicion", reposicion);
		    }catch (IndexOutOfBoundsException e){
		    	
		    }catch (Exception e) {
				logger.error(StackTraceManager.getStackTrace(e));
			}
			
		        FotosReferencia fotosReferencia = new FotosReferencia();
				fotosReferencia.setCodReferencia(Long.parseLong(articulo));
	
				if (fotosReferenciaService.checkImage(fotosReferencia)) {
					listaAlmacen.get(0).setTieneFoto(true);
				} else {
					listaAlmacen.get(0).setTieneFoto(false);
				}
	        	
	        	 if(listaAlmacen.get(0).getFlgPantCorrStock() == null){
	 	        	listaAlmacen.get(0).setFlgPantCorrStock("N");
	         	}
	 	        
	 	        ReferenciasCentro referenciasCentro = new ReferenciasCentro();
	 			referenciasCentro.setCodArt(Long.parseLong(articulo));
	 	        
	 			boolean isCaprabo = false;
	 			
	 			boolean isCapraboEspecial = false;
	 			
	 			VDatosDiarioArt vDatosDiarioArtResul = this.obtenerDiarioArt(Long.parseLong(articulo));
	 			
	 			ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
	 			stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(codCentro));
	 			List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
	 			listaReferencias.add(BigInteger.valueOf(Long.parseLong(articulo)));
	 			
	 			stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA);
	 			
	 			stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
	 			
	 			ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);
	
	 			if (stockTiendaResponse.getCodigoRespuesta().equals("OK")){//Pet. 53787
	 				//Si la referencia no es madre y además no es textil, se indica que se calculará el stock mediante el WS de stock con el valor CC
	 				//al pinchar el link del stock.
	 				if(vDatosDiarioArtResul.getGrupo1() != 3 && (stockTiendaResponse == null || stockTiendaResponse.getTipoMensaje() == null || 						
	 						!stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE))){
	 					
	 					    listaAlmacen.get(0).setCalculoCC("S");
	 					
	 				}
	 				else{
	 					listaAlmacen.get(0).setCalculoCC("N");
	 				}
					
	 			}
	 			
	 			if (!listaAlmacen.isEmpty()) {
	 			    if (stockTiendaResponse.getListaReferencias()[0].getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)) {
	 			        listaAlmacen.get(0).setStockActual(
	 			            Utilidades.convertirDoubleAString(
	 			                stockTiendaResponse.getListaReferencias()[0].getBandejas().doubleValue(),
	 			                "###0.#"
	 			            )
	 			        );
	 			    } else {
	 			        listaAlmacen.get(0).setStockActual(
	 			            Utilidades.convertirDoubleAString(
	 			                stockTiendaResponse.getListaReferencias()[0].getStock().doubleValue(),
	 			                "###0.#"
	 			            )
	 			        );
	 			    }
	 			}
	
	 	        
	 	        VSurtidoTienda surtidoTienda = this.obtenerSurtidoTienda(referenciasCentro,isCaprabo,isCapraboEspecial);
	 	        listaAlmacen.get(0).setMMC(surtidoTienda.getMarcaMaestroCentro());
	 	        
	 	        model.addAttribute("surtidoTienda", surtidoTienda);
	 	       model.addAttribute("guardadoOK", guardadoOK);
	 			model.addAttribute("origenPantalla", origenPantalla);
	 			model.addAttribute("pdaDatosCab", pdaDatosCab);
	 			model.addAttribute("listaRef", listaRef);
	 			model.addAttribute("listaAlmacen", listaAlmacen);
	 			return STOCK_ALMACEN;
			}else{
				return "redirect:pdaP116PrehuecosStockAlmacen.do";
			}
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			return resultadoKO;
		}
		
		
	}
	
	private List<AlmacenPrehuecos> cargarStocks(List<AlmacenPrehuecos> listaRef, Long codCentro, HttpSession session) throws Exception{
		List<AlmacenPrehuecos> output = new ArrayList<AlmacenPrehuecos>();

		Long[] codArticulos = new Long[listaRef.size()];
		for (int i=0; i<listaRef.size(); i++) {
			final AlmacenPrehuecos item = listaRef.get(i);
			final Long ref = Long.valueOf(item.getCodArt());
			codArticulos[i] = ref;
		}

		final Map<Long, Double> stocks = obtenerStocks(codArticulos, codCentro, session);
		

		for(AlmacenPrehuecos item : listaRef) {
			final Long ref = Long.valueOf(item.getCodArt());
			if (stocks.containsKey(ref)){
				item.setStock(formatearStock(stocks.get(ref)));
				output.add(item);
			}
		}
		return output;
	}
	
	private String formatearStock(Double stock) throws Exception {
		BigDecimal bd = new BigDecimal(String.valueOf(stock));
		long iPart = bd.longValue();
		double p_dec= (stock - iPart);
		if(p_dec==0){
			return String.valueOf(iPart);
		}else{
			return String.format("%.1f", stock);
		}
	}
	
	private Map<Long,Double> obtenerStocks(Long[] codArticulos, Long centro,  HttpSession session) throws Exception {

		Map<Long,Double> out = new HashMap<Long,Double>();
		
		try {
			final ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(centro));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
			
			final BigInteger[] listaRef = new BigInteger[codArticulos.length];
			for (int i=0; i<codArticulos.length; i++){
				listaRef[i] = BigInteger.valueOf(codArticulos[i]);
			}
			
			requestType.setListaCodigosReferencia(listaRef);
			
			final ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
			if (responseType != null && responseType.getCodigoRespuesta().equals("OK")){
				final ReferenciaType[] listaRefResponse = responseType.getListaReferencias();
				
				for (int i=0; i<listaRefResponse.length; i++){
					final ReferenciaType referencia = listaRefResponse[i];
					final Long codRef = referencia.getCodigoReferencia().longValue();
					Double stock = null;
					if (referencia.getCodigoError().equals(new BigInteger("0"))){
						if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
							stock = referencia.getBandejas().doubleValue();
						} else {
							stock = referencia.getStock().doubleValue();
						}
					} else {
						stock = referencia.getStock().doubleValue();
					}
					out.put(codRef, stock);
				}
			} else {
				for (Long codArticulo : codArticulos){
					out.put(codArticulo, null);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		
		return out;
	}
	
}
