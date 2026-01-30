package es.eroski.misumi.control;

import java.util.ArrayList;
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

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VArtCentroAlta;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VArtCentroAltaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/altasCatalogo")
public class p12AltasCatalogoController {
	private static Logger logger = Logger.getLogger(p12AltasCatalogoController.class);
	//private static String templateFileName = "altaCatalogo.xls";
	private PaginationManager<VArtCentroAlta> paginationManager = new PaginationManagerImpl<VArtCentroAlta>();

	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	
	@Autowired
	private VArtCentroAltaService vArtCentroAltaService;
	
	@Autowired
	private ExcelManager excelManager;
		@Resource
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
		return "p12_altasCatalogo";
	}

	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<VArtCentroAlta> loadDataGrid(
			@RequestBody VArtCentroAlta vArtCentroAlta,
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
		
		List<VArtCentroAlta> list = null;
			
		try {
			//logger.info("Searching VArtCentroAlta");		
			list = this.vArtCentroAltaService.findAll(vArtCentroAlta,pagination);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		
		Page<VArtCentroAlta> result = null;
			
		int records = 0;
		if ((list != null) && (list.size()> 0)) {
				
			if (Constantes.AREA_TEXTIL.equals(list.get(0).getGrupo1().toString())) {  //PET.49318. Si es una estructura de textil, se obtendran los datos de 
			   //la vista V_ART_CENTRO_ALTA_TEX_N1 (Solo los de primer nivel)
				records = this.vArtCentroAltaService.findAllTextilN1Cont(vArtCentroAlta).intValue();
			} else { 
				records = this.vArtCentroAltaService.findAllCont(vArtCentroAlta).intValue();
			}

		}
			
		session.setAttribute("listadoExcelGenerado", "0"); // 0 = generando
		
		result = this.paginationManager.paginate(new Page<VArtCentroAlta>(), list,
				max.intValue(), records, page.intValue());	
		return result;
	}
	
	@RequestMapping(value = "/loadDataSubGridTextil", method = RequestMethod.POST)
	public  @ResponseBody Page<VArtCentroAlta> loadDataSubGridTextil(
			@RequestBody VArtCentroAlta vArtCentroAlta,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		List<VArtCentroAlta> list = null;

		list = this.vArtCentroAltaService.findAllTextilN2ByLote(vArtCentroAlta);

		Page<VArtCentroAlta> result = null;
			
		if (list != null && list.size()>0) {
			int records = list.size();
			 		
			result = this.paginationManager.paginate(new Page<VArtCentroAlta>(), list
													,list.size(), records, list.size());	
				
		} else {
			return new Page<VArtCentroAlta>();
		}

		return result;
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

	@RequestMapping(value = "/loadArea", method = RequestMethod.POST)
	List<VAgruComerRef> loadAreaData(
			//@RequestBody VAgruComerRef vAgruCommerRef,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		try {
			return new ArrayList<VAgruComerRef>();
		   // re
		} catch (Exception e) {
	
     	    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        throw e;
		}
	}
	
	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody void exportGrid(
			@RequestParam(required = false) String[] headers,
			@RequestParam(required = false) String[] model,
			@RequestParam(required = false) Long grupo1,
			@RequestParam(required = false) Long grupo2,
			@RequestParam(required = false) Long grupo3,
			@RequestParam(required = false) Long grupo4,
			@RequestParam(required = false) Long grupo5,
			@RequestParam(required = false) String marcaMaestroCentro,
			@RequestParam(required = false) String catalogo,
			@RequestParam(required = false) String pedir,
			@RequestParam(required = false) String pedible,
			@RequestParam(required = false) String loteSN,
			@RequestParam(required = false) Long codigoArticulo,
			@RequestParam(required = false) String consultaTextil,
			@RequestParam(required = false) String tipolistado,
			@RequestParam(required = false) String facingCero,
			HttpServletResponse response, HttpSession session) throws Exception{
		
		try {
			List<GenericExcelVO> list = new ArrayList<GenericExcelVO>();
		    List<VArtCentroAlta> listTextil = new ArrayList<VArtCentroAlta>();
		    User user = (User) session.getAttribute("user");

		    VArtCentroAlta vArtCentroAlta = new VArtCentroAlta(user.getCentro(),codigoArticulo,null,
		    								"I5", grupo1,grupo2,grupo3, grupo4, grupo5,null,pedir,
		    								marcaMaestroCentro,catalogo,facingCero,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
		    								null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,pedible,
		    								null,
		    								null /*Tipo Implantacion*/
		    								);
		  
		    vArtCentroAlta.setLoteSN(loteSN);
		    
		    VAgruComerRef vAgruComerGrupoArea=null;
	    	VAgruComerRef vAgruComerGrupoSection=null;
	    	VAgruComerRef vAgruComerGrupoCategory=null;
	    	VAgruComerRef vAgruComerGrupoSubCategory=null;
	    	VAgruComerRef vAgruComerGrupoSegment=null;
	    	
	    	if (grupo1!=null){
	    		vAgruComerGrupoArea=this.vAgruComerRefService.findAll(new VAgruComerRef("I1",grupo1,null,null,null,null,null), null).get(0);
	    	}
	    	if (grupo2!=null){
	    		vAgruComerGrupoSection = this.vAgruComerRefService.findAll(new VAgruComerRef("I2",grupo1,grupo2,null,null,null,null), null).get(0);
	    	}
	    	if (grupo3!=null){
	    		vAgruComerGrupoCategory =this.vAgruComerRefService.findAll(new VAgruComerRef("I3",grupo1,grupo2,grupo3,null,null,null), null).get(0);
	    	}	
	    	if (grupo4!=null){
	    		vAgruComerGrupoSubCategory=this.vAgruComerRefService.findAll(new VAgruComerRef("I4",grupo1,grupo2,grupo3,grupo4,null,null), null).get(0);
	    	}
	    	if (grupo5!=null){
	    		vAgruComerGrupoSegment=this.vAgruComerRefService.findAll(new VAgruComerRef("I5",grupo1,grupo2,grupo3,grupo4,grupo5,null), null).get(0);
	    	} 
		    
			try {
				logger.info("Exporting EXCEL Alta Catalogo:"+model.toString());
		    
				//if (consultaTextil.equals("S")) {
				if (Constantes.LIS_GAMA_TIPO_LISTADO_TEXTIL.equals(tipolistado)){
					vArtCentroAlta.setLoteSN(loteSN);
					listTextil = this.vArtCentroAltaService.findAllExcelTextil(vArtCentroAlta,model);
				} else {
					list = this.vArtCentroAltaService.findAllExcel(vArtCentroAlta,model);
				}
			
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
		
		//if (consultaTextil.equals("S")) {
			if (Constantes.LIS_GAMA_TIPO_LISTADO_TEXTIL.equals(tipolistado)){
				this.excelManager.exportAltaCatalogoTextil(listTextil, headers, model, this.messageSource,vArtCentroAlta,vAgruComerGrupoArea,
						vAgruComerGrupoSection,vAgruComerGrupoCategory,vAgruComerGrupoSubCategory,vAgruComerGrupoSegment,
						marcaMaestroCentro, catalogo, pedir, pedible, tipolistado, facingCero, response);
			} else {
				this.excelManager.exportAltaCatalogo(list, headers,model, this.messageSource,vArtCentroAlta,vAgruComerGrupoArea,
						vAgruComerGrupoSection,vAgruComerGrupoCategory,vAgruComerGrupoSubCategory,vAgruComerGrupoSegment,
						marcaMaestroCentro, catalogo, pedir, pedible, tipolistado, facingCero, response);
			}
			session.setAttribute("listadoExcelGenerado", "1");

		}catch (Exception e) {
		     	    //logger.error(StackTraceManager.getStackTrace(e));
     	    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     	    session.setAttribute("listadoExcelGenerado", "1");
		    throw e;
		}
	}
}