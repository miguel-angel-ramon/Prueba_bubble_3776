package es.eroski.misumi.control;


import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.Intertienda;
import es.eroski.misumi.model.ListaIntertienda;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.Region;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.IntertiendaService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.RegionService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.VCentrosUsuariosService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/intertienda")
public class p72IntertiendaController {
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private VCentrosUsuariosService vCentrosUsuariosService;
	
	@Autowired
	private IntertiendaService intertiendaService;

	@Autowired
	private StockTiendaService stockTiendaService;
	
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	
	
	@Autowired
	private ExcelManager excelManager;
	
	@Resource
	private MessageSource messageSource;
	
	private Intertienda inter;

	private static Logger logger = Logger.getLogger(p72IntertiendaController.class);
	private PaginationManager<Intertienda> paginationManager = new PaginationManagerImpl<Intertienda>();

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model,
			@RequestParam(required = false, defaultValue = "") String codReferencia,
			@RequestParam(required = false, defaultValue = "") String codCentro,
			@RequestParam(required = false, defaultValue = "") String codRegion,
			@RequestParam(required = false, defaultValue = "") String codZona,
			@RequestParam(required = false, defaultValue = "") String codProvincia
			) {
		if (!"".equals(codReferencia) || codReferencia!=null){
			model.put("codReferencia", codReferencia);
		}else{
			model.put("codReferencia", "");
		}
		if (!"".equals(codCentro) || codCentro!=null){
			model.put("codCentro", codCentro);
		}else{
			model.put("codCentro", "");
		}
		if (!"".equals(codRegion) || codRegion!=null){
			model.put("codRegion", codRegion);
		}else{
			model.put("codRegion", "");
		}
		if (!"".equals(codZona) || codZona!=null){
			model.put("codZona", codZona);
		}else{
			model.put("codZona", "");
		}
		if (!"".equals(codProvincia) || codProvincia!=null){
			model.put("codProvincia", codProvincia);
		}else{
			model.put("codProvincia", "");
		}
		return "p72_intertienda";
	}
	
	@RequestMapping(value="/findCentroRelacionadoIntertienda", method = RequestMethod.GET)
	public @ResponseBody Long findCentroRelacionadoIntertienda(	
			HttpSession session, 
			HttpServletResponse response) throws Exception {
		try {
			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();
			return intertiendaService.findOne(codCentro);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}
	
	@RequestMapping(value="/loadReferencia", method = RequestMethod.POST)
	public @ResponseBody VDatosDiarioArt loadReferencia(
			@RequestBody ReferenciasCentro vReferenciasCentro,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		try {
			VDatosDiarioArt vDatosDiarioArtRes;
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(vReferenciasCentro.getCodArt());
			vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
			
			if (vDatosDiarioArtRes == null){
				//Si es null devolvemos una respuesta vacía.
				vDatosDiarioArtRes = new VDatosDiarioArt();
			}
			return vDatosDiarioArtRes;
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		
	}
	
	@RequestMapping(value="/loadListaRegion", method = RequestMethod.POST)
	public @ResponseBody List<Region> loadListaRegion(
			@RequestBody Region region,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		try {
			List<Region> listaRegionRes;
			listaRegionRes = this.regionService.findAll(region);
			
			if (listaRegionRes == null){
				//Si es null devolvemos una respuesta vacía.
				listaRegionRes = new ArrayList<Region>();
			}
			return listaRegionRes;
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		
	}
	
	@RequestMapping(value="/loadListaZona", method = RequestMethod.POST)
	public @ResponseBody List<Centro> loadListaZona(
			@RequestBody Centro centro,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		try {
			User user = (User) session.getAttribute("user");
			
			List<Centro> listaZonaRes;
			listaZonaRes = this.vCentrosUsuariosService.listZonaByRegion(centro, user.getCode());
			
			if (listaZonaRes == null){
				//Si es null devolvemos una respuesta vacía.
				listaZonaRes = new ArrayList<Centro>();
			}
			return listaZonaRes;
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		
	}
	
	@RequestMapping(value="/loadDataGrid", method = RequestMethod.POST)
	public @ResponseBody Page<Intertienda> loadDataGrid(
			@RequestBody Intertienda intertienda,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false, defaultValue = "") String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		
		Page<Intertienda> resultado = new Page<Intertienda>();
		
		User user = (User) session.getAttribute("user");
		intertienda.setCodZonaSession(user.getCentro().getCodZona());
		intertienda.setCodRegionSession(user.getCentro().getCodRegion());
		
		this.inter = intertienda;
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
	        	resultado = this.paginationManager.paginate(new Page<Intertienda>(), lista, max.intValue(), records, page.intValue());
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
    				Centro cen = inter.toCentro();
    				Long referencia = intertienda.getCodReferencia();

    				//Calculo el stock actual del centro y lo añado a la intertienda
		        	Double stock = new Double(0);
    				if (inter.isExisteArticulo()){
        		        try {
        		        	stock = this.obtenerStock(referencia, cen.getCodCentro(), session);
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

	private Double obtenerStock(Long codArticulo, Long centro,  HttpSession session) throws Exception {

		Double stock = new Double(0);
		
		try {
			ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(centro));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
			BigInteger[] listaRef = {BigInteger.valueOf(codArticulo)}; 
			requestType.setListaCodigosReferencia(listaRef);
			
			User user = (User) session.getAttribute("user");
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
			
			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("###########################################################################################################");
				logger.error("############################## CONTROLADOR: p72IntertiendaController (obtenerStock)	 #################");
				logger.error("###########################################################################################################");
			}
			
			ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
			if (responseType.getCodigoRespuesta().equals("OK")){
				for (ReferenciaType referencia : responseType.getListaReferencias()){
					if (referencia.getCodigoError().equals(new BigInteger("0"))){
						if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(codArticulo))){
							if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
								stock = referencia.getBandejas().doubleValue();
							} else {
								stock = referencia.getStock().doubleValue();
							}
						}
					} else {
						throw new Exception();
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
	
	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody void exportGrid(
			@RequestParam(required = false) String[] headers,
			@RequestParam(required = false) String[] model,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false, defaultValue = "") String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "codReferencia", required = false) Long codReferencia,
			@RequestParam(value = "descRef", required = false) String descRef,
			@RequestParam(value = "centro", required = false) String centro,
			@RequestParam(value = "region", required = false) String region,
			@RequestParam(value = "zona", required = false) String zona,
			@RequestParam(value = "provincia", required = false) String provincia,
			@RequestParam(value = "tipoB", required = false) String tipoB,
			HttpServletResponse response, HttpSession session) throws Exception{

			try {
				Intertienda intertienda = new Intertienda();
				intertienda.setCodReferencia(codReferencia);
				if(null != centro){
					intertienda.setCodCentro(new Long(centro.substring(0, centro.indexOf('-')).trim()));
				}
				if (null != region){
					intertienda.setCodRegion(new Long(region.substring(0, region.indexOf('-')).trim()));
				}
				if (null != zona){
					intertienda.setCodZona(new Long(zona.substring(0, zona.indexOf('-')).trim()));
				}
				intertienda.setDescProvincia(provincia);
				User user = (User) session.getAttribute("user");
				intertienda.setCodZonaSession(user.getCentro().getCodZona());
				intertienda.setCodRegionSession(user.getCentro().getCodRegion());
				
				 Pagination pagination= new Pagination(max,page);
			        
			        if (index!=null && !"".equals(index)){
			            pagination.setSort(index);
			        }
			        if (sortOrder!=null){
			            pagination.setAscDsc(sortOrder);
			        }
			        int centroPosition = Arrays.asList(model).indexOf("descCentro");
				    

			    List<GenericExcelVO> list = this.intertiendaService.listCentroIntertiendaExcel(intertienda, model, pagination);
			    int stockPosition = Arrays.asList(model).indexOf("stock");
			    
			    if (stockPosition != -1){
			    	for (GenericExcelVO excel : list) {
					    
			    		Class cls = excel.getClass();
			    		
			    		centroPosition = Arrays.asList(model).indexOf("descCentro");
			    		if (centroPosition == -1){
					    	centroPosition = 40;
					    }
			    		String campo = "getField"+(centroPosition + 1);
			    		//call the printIt method
			    		Method method = cls.getMethod(campo, new Class[] {});
			    		
			    		Object ret = method.invoke(excel, new Object[] {});
			    		String descCentro = ret.toString();
			    		Long codigoCentro = new Long(descCentro.substring(0, descCentro.indexOf('-')).trim());
			    		Double stock = new Double(0);
	        		        try {
	        		        	stock = this.obtenerStock(codReferencia,codigoCentro,session);
	        		        } catch (Exception e) {
	        		      }
	    				
				    	
				    	
				    	campo = "setField"+(stockPosition + 1);
				    	 method = cls.getMethod(campo, new Class[] {Object.class});
				    	 method.invoke(excel, BigDecimal.valueOf(stock));
					}
			    }
			    
			    
			    
			    //Centro centro = new Centro();
			    //centro.setCodCentro(user.getCentro().getCodCentro());
			    //centro.setDescripCentro(user.getCentro().getDescripCentro());
			    this.excelManager.exportIntertienda(list,headers,model,this.messageSource,centro, region ,zona,provincia,codReferencia.toString(),descRef, tipoB, response);
				logger.info("Exporting excel Intertienda:"+model.toString());
			    	
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
	}

}