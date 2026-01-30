/**
 * 
 */
package es.eroski.misumi.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.FacingVegalsaRequest;
import es.eroski.misumi.model.FacingVegalsaResponse;
import es.eroski.misumi.model.GenericExcelFieldsVO;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.FacingVegalsaService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.SessionUtils;
import es.eroski.misumi.util.iface.PaginationManager;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * MISUMI-281
 * 
 * @author BIARCRJO
 *
 */
@Controller
@RequestMapping("/facingVegalsa")
public class P110FacingVegalsaController {

	private static final String EXCEL_FACING_VEGALSA = "facingVegalsa.xls";
	private static final int MAX_REFERENCIAS_CONSULTA_STOCK = 300;
	
	private static Logger logger = Logger.getLogger(P110FacingVegalsaController.class);
	
	@Resource
	private MessageSource messageSource;
	
	private PaginationManager<FacingVegalsaResponse> paginationManager = new PaginationManagerImpl<FacingVegalsaResponse>();
	
	@Autowired
	private FacingVegalsaService facingVegalsaService;
	
	@Autowired
	private VAgruComerRefService vAgruComerRefService;

	@Autowired
	private StockTiendaService stockTiendaService;
	
//	@Autowired
//	private ExcelManager excelManager;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false, defaultValue = "0") Long center, Map<String, String> model,
			HttpServletResponse response,
			HttpSession session) {
		logger.info("showForm - p110_facingVegalsa");
		return "p110_facingVegalsa";
	}

	private List<FacingVegalsaResponse> cargarStocks(List<FacingVegalsaResponse> list, String codCentro, HttpSession session,Boolean conStock) throws Exception{
		List<FacingVegalsaResponse> output = new ArrayList<FacingVegalsaResponse>();
		// CARGAR STOCKS
		// 1) sacar una array con las referencias
		Long[] codArticulos = new Long[list.size()];
		for (int i=0; i<list.size(); i++) {
			final FacingVegalsaResponse item = list.get(i);
			final Long ref = Long.valueOf(item.getReferencia());
			codArticulos[i] = ref;
		}
		final Long centro = Long.valueOf(codCentro);

		// 2) consultar el WS de stockTienda para sacar los stocks de esas referencias
		// -->Esta consulta se hara por bloques ya que el WS no admite consultas de mas de 500 referencias a la vez
		for(int i=0; i<codArticulos.length; i+=MAX_REFERENCIAS_CONSULTA_STOCK){
			final int max = codArticulos.length>(i+MAX_REFERENCIAS_CONSULTA_STOCK)?(i+MAX_REFERENCIAS_CONSULTA_STOCK):codArticulos.length;
			logger.info("Cargando stocks del "+i+" al "+max);
			final Long[] codArticulosSublist = Arrays.copyOfRange(codArticulos, i, max);
			final Map<Long, Double> stocks = obtenerStocks(codArticulosSublist, centro, session);
			
			// 3) modificar el listado para establecer los stocks devueltos por el WS
			for(FacingVegalsaResponse item : list) {
				final Long ref = Long.valueOf(item.getReferencia());
				if (stocks.containsKey(ref)){
					Double stock = stocks.get(ref);
					if(conStock){
						if(null!=stock && stock!=0){
							item.setStock(stock);
							output.add(item);
						}
					}else{
						item.setStock(stock);
						output.add(item);
					}
					
				}
			}
		}
		
		return output;
	}
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<FacingVegalsaResponse> loadDataGrid(
			@RequestBody FacingVegalsaRequest facingVegalsaRequest,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		        Pagination pagination= new Pagination(max,page);
		        if (index!=null){
		            pagination.setSort(index);
		        }
		        if (sortOrder!=null){
		            pagination.setAscDsc(sortOrder);
		        }
				List<FacingVegalsaResponse> list = null;
				
				try {
					logger.info("Searching facingVegalsaService");		
					list = this.facingVegalsaService.findAll(facingVegalsaRequest,pagination);
					list = cargarStocks(list,facingVegalsaRequest.getCentro(),session,facingVegalsaRequest.getConStock());
					
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
				int records = list.size();
				int desdeSubList = ((page.intValue()-1) * max.intValue());
				int hastaSubList = ((page.intValue())*max.intValue());

				if(hastaSubList > records){
					hastaSubList = records;
				}

				List<FacingVegalsaResponse> listaAMostrar = list.subList(desdeSubList,hastaSubList);
				session.setAttribute("listadoExcelGenerado", "0"); // 0 = generando
				return this.paginationManager.paginate(new Page<FacingVegalsaResponse>(), listaAMostrar, max.intValue(), records, page.intValue());	
	}

	@RequestMapping(value="/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerRef> getAreaData(
			@RequestBody VAgruComerRef vAgruCommerRef,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			logger.info("P110FacingVegalsaController - loadAreaData");
			return this.vAgruComerRefService.findAll(vAgruCommerRef, null);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	

	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody
	void exportGrid(
			@RequestParam String facingVegalsaRequest,
			@RequestParam(required = false) String[] headers,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {

			ObjectMapper mapper = new ObjectMapper();

			// Realizo el marshing de JSON to Object
			FacingVegalsaRequest filtros = mapper.readValue(facingVegalsaRequest, FacingVegalsaRequest.class);
			
			List<FacingVegalsaResponse> list = this.facingVegalsaService.findAll(filtros, null);
			list = cargarStocks(list,filtros.getCentro(),session,filtros.getConStock());
			
			logger.info("Exporting excel - filas:"+list.size());
			
			exportFacingVegalsa(filtros, list, headers, response);
			session.setAttribute("listadoExcelGenerado", "1");
		}catch(Exception e) {
     	    e.printStackTrace();
     	    session.setAttribute("listadoExcelGenerado", "1");
     	    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        throw e;
		}
			
	}

	private List<GenericExcelFieldsVO> parseToGenericExcelFields(List<FacingVegalsaResponse> list, String[] headers) {

		List<GenericExcelFieldsVO> output = new ArrayList<GenericExcelFieldsVO>();

	    try {
	    	for (int i=0; i<list.size(); i++){
	    		FacingVegalsaResponse item = list.get(i);
	    		GenericExcelFieldsVO genericField = new GenericExcelFieldsVO();
	    		
	    		for (String header : headers){
	    			Object elem = getItemToShow(header, item, i);
	    			genericField.addField(elem);
	    		}
	    		output.add(genericField);
		    }
		} catch (Exception e){
			e.printStackTrace();
		}
	    return output;
	}
	
	private Object getItemToShow(String header, FacingVegalsaResponse item, Integer index) {
		
		Object output = null;
		final String headerFiltered = header.toLowerCase().trim();
		logger.info("headerFiltered = "+headerFiltered);
		if (headerFiltered.equals("area")){
			output = item.getArea();
		}else if (headerFiltered.equals("seccion")){
			output = item.getSeccion();
		}else if (headerFiltered.equals("categoria")){
			output = item.getCategoria();			
		}else if (headerFiltered.equals("subcategoria")){
			output = item.getSubcategoria();
		}else if (headerFiltered.equals("segmento")){
			output = item.getSegmento();
		}else if (headerFiltered.equals("referencia")){
			output = item.getReferencia();
		}else if (headerFiltered.equals("denominacion")){
			output = item.getDenominacion();
		}else if (headerFiltered.equals("marca")){
			output = item.getMarca();
		}else if (headerFiltered.equals("uc")){
			output = item.getUc();
		}else if (headerFiltered.equals("stock")){
			output = item.getStock();
		}else if (headerFiltered.equals("mmc")){
			output = item.getMmc();
		}else if (headerFiltered.equals("cc")){
			output = item.getCc();
		}else if (headerFiltered.equals("reapro")){
			output = item.getReapro();
		}else if (headerFiltered.equals("ufp")){
			output = item.getUfp();
		}else if (headerFiltered.equals("tipoaprov")){
			output = item.getTipoAprov();
		}else if (headerFiltered.equals("ffpp")){
			output = item.getFFPP();
		}else if (headerFiltered.equals("numoferta")){
			output = item.getNumeroOferta();
		}else if (headerFiltered.equals("mapa")){
			output = item.getMapa();
		}else if (headerFiltered.equals("catalogo")){
			output = item.getCatalogo();
		}else if (headerFiltered.equals("capacidad")){
			output = item.getCapacidad();
		}else if (headerFiltered.equals("fondo")){
			output = item.getFondo();
		}else if (headerFiltered.equals("facing")){
			output = item.getFacing();
		}else if (headerFiltered.equals("#")){
			output = index;
		}
		return output;
	}
	
	private void exportFacingVegalsa(FacingVegalsaRequest facingVegalsaRequest, List<FacingVegalsaResponse> list, String[] headers, HttpServletResponse response) throws Exception{
		response.setContentType("text/xls; charset=utf-8");

		response.setHeader("Content-Disposition", "attachment; filename="+EXCEL_FACING_VEGALSA);
		OutputStream out = null;

		try {

			InputStream templateIS = this.getClass().getClassLoader().getResourceAsStream("/excel/"+EXCEL_FACING_VEGALSA);

			logger.info("ExcelManager - exportFacingVegalsa");
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			// Campos superiores
			map.put("search", facingVegalsaRequest);
			
			// Cabeceras
			map.put("headers", headers);
			
			// Registros
			List<GenericExcelFieldsVO> listGenericFields = parseToGenericExcelFields(list, headers);
			map.put("records", listGenericFields);
			
			XLSTransformer transformer = new XLSTransformer();
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer.transformXLS(templateIS, map);
			
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);
			
			//TAMAÑOS DE LAS COLUMNAS DINAMICOS
			for (int i=0; i<headers.length; i++){
				sheet.autoSizeColumn(i);
				
				for (int j=0; j<listGenericFields.size(); j++){
					final Object elem = listGenericFields.get(j).getField(i);
					if (elem==null){
						continue;
					}else{
						final Boolean isNumber = NumberUtils.isNumber(elem.toString());
						final Cell cell = sheet.getRow(8+j).getCell(i+1);

						if (isNumber || ( elem.toString().length()==1 && Character.isLetter(elem.toString().charAt(0)))){
							CellUtil.setAlignment(cell, resultWorkbook, CellStyle.ALIGN_CENTER);
						}else{
							CellUtil.setAlignment(cell, resultWorkbook, CellStyle.ALIGN_LEFT);	
						}
					}
				}
			}
			
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
			   
		} catch (IOException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
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
//						logger.error("ERROR EN EL WS DE CONSULTA DE STOCK: La referencia "+referencia.getCodigoReferencia()+" respondió con error ("+referencia.getCodigoError()+"): "+referencia.getMensajeError());
					}
					out.put(codRef, stock);
				}
			} else {
				for (Long codArticulo : codArticulos){
					out.put(codArticulo, null);
				}
//				logger.error("ERROR EN EL WS DE CONSULTA DE STOCK: RESPUESTA DEL WS ("+responseType.getCodigoRespuesta()+") "+responseType.getDescripcionRespuesta());
			}
		} catch (Exception e) {
			throw e;
		}
		
		return out;
	}
}
