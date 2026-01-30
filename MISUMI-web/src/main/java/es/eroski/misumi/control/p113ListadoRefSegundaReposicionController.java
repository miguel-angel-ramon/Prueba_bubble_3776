package es.eroski.misumi.control;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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

import es.eroski.misumi.model.GenericExcelFieldsVO;
import es.eroski.misumi.model.ListadoRefSegundaReposicion;
import es.eroski.misumi.model.ListadoRefSegundaReposicionExcel;
import es.eroski.misumi.model.ListadoRefSegundaReposicionSalida;
import es.eroski.misumi.model.ListadoRefSegundaReposicionSalidaExcel;
import es.eroski.misumi.model.ReferenciasSegundaReposicion;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.ListadoRefSegundaReposicionService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import net.sf.jxls.transformer.XLSTransformer;



@Controller
@RequestMapping("/listadoRefSegundaReposicion")
public class p113ListadoRefSegundaReposicionController {
	private static Logger logger = Logger.getLogger(p113ListadoRefSegundaReposicionController.class);
	
	@Resource 
	private MessageSource messageSource;

	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	
	@Autowired
	private ListadoRefSegundaReposicionService listadoRefSegundaReposicionService;
	
	private static final String LISTADO_REF_SEGUNDA_REPOSICION = "listadoRefsegundaReposicion.xls";
	
	String MES[] = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false, defaultValue = "0") Long center,
		@RequestParam(value = "locale", required = false, defaultValue = "es") String locale,
		Map<String, String> model, HttpServletResponse response, HttpSession session) {
		
		recuperarMeses(model);
		
		return "p113_listadoRefSegundaReposicion";
	}
	
	@RequestMapping(value="/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerRef> getAreaData(
			@RequestBody VAgruComerRef vAgruCommerRef,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			return this.vAgruComerRefService.findAll(vAgruCommerRef, null);
		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	public void recuperarMeses(Map<String, String> model){
		Calendar cal = Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("UTC");
		cal.setTimeZone(tz);
		int mesInt = cal.get(Calendar.MONTH);
		int mes1Int = cal.get(Calendar.MONTH) + 1;
		int mes2Int = cal.get(Calendar.MONTH) + 2;
		if(mes1Int>11){
			mes1Int=mes1Int-12;
		}
		if(mes2Int>11){
			mes2Int=mes2Int-12;
		}
		model.put("mes", MES[mesInt]);
		model.put("mes1", MES[mes1Int]);
		model.put("mes2", MES[mes2Int]);
		mesInt = mesInt+1;
		mes1Int = mes1Int+1; 
		mes2Int = mes2Int+1;
		model.put("mesValue", String.valueOf(mesInt));
		model.put("mes1Value", String.valueOf(mes1Int));
		model.put("mes2Value", String.valueOf(mes2Int));
	}
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<ListadoRefSegundaReposicionSalida> loadDataGrid(
			@RequestBody ListadoRefSegundaReposicion filtroListadoRefSegundaReposicion,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		ReferenciasSegundaReposicion referenciasSegundaReposicion = new ReferenciasSegundaReposicion();
		List<ListadoRefSegundaReposicionSalida> listadoRefSegundaReposicionSalidaList= new ArrayList<ListadoRefSegundaReposicionSalida>();
		try {
			logger.info("Searching VArtCentroAlta");		
			referenciasSegundaReposicion = this.listadoRefSegundaReposicionService.findAll(filtroListadoRefSegundaReposicion);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		Page<ListadoRefSegundaReposicionSalida> result = null;
		if (referenciasSegundaReposicion != null && referenciasSegundaReposicion.getListadoRefSegundaReposicionSalidaList() != null){
				listadoRefSegundaReposicionSalidaList = referenciasSegundaReposicion.getListadoRefSegundaReposicionSalidaList();
		}
		if (null != index && !index.equals("") ){
			listadoRefSegundaReposicionSalidaList = this.listadoRefSegundaReposicionService.ordenarLista(listadoRefSegundaReposicionSalidaList, index, sortOrder);
		}
		result = this.listadoRefSegundaReposicionService.crearListaPaginada(listadoRefSegundaReposicionSalidaList, page, max, index, sortOrder);
		return result;
	}
	
	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody
	void exportGrid(
			@RequestParam String listadoRefSegundaReposicionExcel,
			@RequestParam(required = false) String[] headers,
			@RequestParam(required = false) String[] model,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {

			ObjectMapper mapper = new ObjectMapper();

			// Realizo el marshing de JSON to Object
			ListadoRefSegundaReposicionExcel filtros = mapper.readValue(listadoRefSegundaReposicionExcel, ListadoRefSegundaReposicionExcel.class);
			VAgruComerRef vAgruComerGrupoCategory=null;
			String categorias="";
			filtros.setDescMes(MES[filtros.getMes()-1]);
			for(int i=0;i<filtros.getListadoSeleccionados().size();i++){
				Long categoria=filtros.getListadoSeleccionados().get(i);
				vAgruComerGrupoCategory =this.vAgruComerRefService.findAll(new VAgruComerRef("I3",filtros.getCodgrupo1(),filtros.getCodgrupo2(),categoria,null,null,null), null).get(0);
				categorias=categorias+vAgruComerGrupoCategory.getGrupo3().toString()+"-"+vAgruComerGrupoCategory.getDescripcion();
				if(i+1!=filtros.getListadoSeleccionados().size()){
					categorias=categorias+"\n";
				}
			}
			filtros.setListadoSeleccionadosText(categorias);
			ReferenciasSegundaReposicion referenciasSegundaReposicion = this.listadoRefSegundaReposicionService.findAllExcel(filtros);
			
			//logger.info("Exporting excel - filas:"+list.size());
			List<ListadoRefSegundaReposicionSalidaExcel> listadoRefSegundaReposicionSalidaExcel= this.toDatosExcel(referenciasSegundaReposicion.getListadoRefSegundaReposicionSalidaList());
			exportListadoRefSegundaReposicion(filtros, listadoRefSegundaReposicionSalidaExcel,model, headers, response);
		
		}catch(Exception e) {
     	    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        throw e;
		}
			
	}

	public List<ListadoRefSegundaReposicionSalidaExcel> toDatosExcel(List<ListadoRefSegundaReposicionSalida> listaBBDD ){
		List<ListadoRefSegundaReposicionSalidaExcel> listadoRefSegundaReposicionSalidaExcel= new ArrayList<ListadoRefSegundaReposicionSalidaExcel>();
		for(int i=0;i<listaBBDD.size();i++){
			ListadoRefSegundaReposicionSalida aux=listaBBDD.get(i);
			ListadoRefSegundaReposicionSalidaExcel datosExcel=new ListadoRefSegundaReposicionSalidaExcel();
			datosExcel.setArea(aux.getCodN1()+"-"+aux.getDescCodN1());
			datosExcel.setSeccion(aux.getCodN2()+"-"+aux.getDescCodN2());
			datosExcel.setCategoria(aux.getCodN3()+"-"+aux.getDescCodN3());
			datosExcel.setSubcategoria(aux.getCodN4()+"-"+aux.getDescCodN4());
			datosExcel.setSegmento(aux.getCodN5()+"-"+aux.getDescCodN5());
			datosExcel.setReferencia(aux.getReferencia());
			datosExcel.setReferenciaDesc(aux.getReferenciaDesc());
			datosExcel.setFacing(aux.getFacing());
			datosExcel.setCapacidad(aux.getCapacidad());
			datosExcel.setCajaExpositora(aux.getCajaExpositora());
			datosExcel.setTendencia(aux.getTendencia().toString().replace(".", ","));
			datosExcel.setVentaPrevista(aux.getVentaPrevista().toString().replace(".", ","));
			
			listadoRefSegundaReposicionSalidaExcel.add(datosExcel);
		}
		return listadoRefSegundaReposicionSalidaExcel;
	}
	
	private void exportListadoRefSegundaReposicion(ListadoRefSegundaReposicionExcel filtros, List<ListadoRefSegundaReposicionSalidaExcel> listadoRefSegundaReposicionSalidaExcel, String[] model, String[] headers, HttpServletResponse response) throws Exception{
		response.setContentType("text/xls; charset=utf-8");

		response.setHeader("Content-Disposition", "attachment; filename="+LISTADO_REF_SEGUNDA_REPOSICION);
		OutputStream out = null;

		try {

			InputStream templateIS = this.getClass().getClassLoader().getResourceAsStream("/excel/"+LISTADO_REF_SEGUNDA_REPOSICION);

			logger.info("ExcelManager - exportListadoRefSegundaReposicion");
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			// Campos superiores
			map.put("search", filtros);
			
			// Cabeceras
			map.put("headers", headers);
			
			// Registros
			List<GenericExcelFieldsVO> listGenericFields = parseToGenericExcelFields(listadoRefSegundaReposicionSalidaExcel, model);
			map.put("records", listGenericFields);
			
			XLSTransformer transformer = new XLSTransformer();
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer.transformXLS(templateIS, map);
			
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);
			
			//TAMAÑOS DE LAS COLUMNAS DINAMICOS
			for (int i=0; i<headers.length; i++){
				if(!headers[i].equals("Caja expositora")){
					sheet.autoSizeColumn(i);
				}
				for (int j=0; j<listGenericFields.size(); j++){
					final Object elem = listGenericFields.get(j).getField(i);
					if (elem==null){
						continue;
					}else{
						final Boolean isNumber = NumberUtils.isNumber(elem.toString());
						final Cell cell = sheet.getRow(7+j).getCell(i);

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
	
	private List<GenericExcelFieldsVO> parseToGenericExcelFields(List<ListadoRefSegundaReposicionSalidaExcel> list, String[] headers) {

		List<GenericExcelFieldsVO> output = new ArrayList<GenericExcelFieldsVO>();

	    try {
	    	for (int i=0; i<list.size(); i++){
	    		ListadoRefSegundaReposicionSalidaExcel item = list.get(i);
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
	
private Object getItemToShow(String header, ListadoRefSegundaReposicionSalidaExcel item, Integer index) {
		
		Object output = null;
		final String headerFiltered = header.toLowerCase().trim();
		if (headerFiltered.equals("desccodn1")){
			output = item.getArea();
		}else if (headerFiltered.equals("desccodn2")){
			output = item.getSeccion();
		}else if (headerFiltered.equals("desccodn3")){
			output = item.getCategoria();
		}else if (headerFiltered.equals("desccodn4")){
			output = item.getSubcategoria();
		}else if (headerFiltered.equals("desccodn5")){
			output = item.getSegmento();
		}else if (headerFiltered.equals("referencia")){
			output = item.getReferencia();
		}else if (headerFiltered.equals("referenciadesc")){
			output = item.getReferenciaDesc();
		}else if (headerFiltered.equals("facing")){
			output = item.getFacing();
		}else if (headerFiltered.equals("capacidad")){
			output = item.getCapacidad();
		}else if (headerFiltered.equals("cajaexpositora")){
			output = item.getCajaExpositora();
		}else if (headerFiltered.equals("tendencia")){
			output = item.getTendencia();
		}else if (headerFiltered.equals("ventaprevista")){
			output = item.getVentaPrevista();
		}
		
		return output;
	}
			
}
	