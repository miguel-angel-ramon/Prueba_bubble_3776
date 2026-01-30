package es.eroski.misumi.control;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
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
import es.eroski.misumi.model.ConsumoRapido;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ConsumoRapidoService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;



@Controller
@RequestMapping("/consumoRapido")
public class p112RefConsumoRapidoController {
	private static Logger logger = Logger.getLogger(p112RefConsumoRapidoController.class);

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private VAgruComerRefService vAgruComerRefService;

	@Autowired
	private ConsumoRapidoService consumoRapidoService;
	
	
	@Autowired
	private ExcelManager excelManager;
		
	@RequestMapping(value="/loadComboData", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.GET)
	public  @ResponseBody Page<ConsumoRapido> loadDataGrid(
			@RequestParam(value = "area", required = true) Long area,
			@RequestParam(value = "seccion", required = true) Long seccion,
			@RequestParam(value = "fechaIni", required = true) String fechaIni,
			@RequestParam(value = "fechaFin", required = true) String fechaFin,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		User userSession = (User)session.getAttribute("user");
		if(!NumberUtils.isNumber(fechaIni.substring(0, 1))){
			fechaIni=fechaIni.substring(2).replace("-", "/").replace(" ", "/");
		}
		if(!NumberUtils.isNumber(fechaFin.substring(0, 1))){
			fechaFin=fechaFin.substring(2).replace("-", "/").replace(" ", "/");
		}
		Centro centro = userSession.getCentro();
		Long codCentro = centro.getCodCentro();
		
        List<ConsumoRapido> listaSinPaginar = getListaSinPaginar(codCentro, area, seccion, fechaIni, fechaFin, index, sortOrder);
        return getListaPaginada(listaSinPaginar, index, sortOrder, max, page, response);

	}

	private Page<ConsumoRapido> getListaPaginada(List<ConsumoRapido> listaSinPaginar, String index, String sortOrder,
			Long max, Long page, HttpServletResponse response) throws Exception {
		Pagination pagination= new Pagination(max,page);
        if (index!=null){
            pagination.setSort(index);
        }
        if (sortOrder!=null){
            pagination.setAscDsc(sortOrder);
        }
        int records = 0;

		List<ConsumoRapido> listaRecarga = null;
		try {
			Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
			Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
			
			if (listaSinPaginar != null){ 
				records = listaSinPaginar.size();
				if (elemInicio <= records){
					if (elemFinal > records){
						elemFinal = new Long(records);
					}
					listaRecarga = (listaSinPaginar).subList(Integer.parseInt(elemInicio.toString()), Integer.parseInt(elemFinal.toString()));
				}
			}
			
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		
		Page<ConsumoRapido> output = null;
		PaginationManager<ConsumoRapido> paginationManager = new PaginationManagerImpl<ConsumoRapido>();

		if (listaRecarga != null) {
			output = paginationManager.paginate(new Page<ConsumoRapido>(), listaRecarga,
					max.intValue(), records, page.intValue());	
		} else {
			output = new Page<ConsumoRapido>();
		}
		
		return output;

	}

	private List<ConsumoRapido> getListaSinPaginar(Long codCentro, Long area, Long seccion, String fechaIni, String fechaFin, String index, String sortOrder) throws Exception {
		return consumoRapidoService.findAll(codCentro, area,seccion,fechaIni,fechaFin,index,sortOrder);
	}
	
	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody
	void exportGrid(
			@RequestParam(required = true) String[] headers,
			@RequestParam(required = true) String[] model,
			@RequestParam(required = true) Long area,
			@RequestParam(required = true) Long seccion,
			@RequestParam(required = true) String fechaIni,
			@RequestParam(required = true) String fechaFin,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {
			User userSession = (User)session.getAttribute("user");
			Centro centro = userSession.getCentro();
			Long codCentro = centro.getCodCentro();
			String nombreCentro = codCentro + "-" + centro.getDescripCentro();
			List<ConsumoRapido> listado= null;
			try {
				listado = this.getListaSinPaginar(codCentro,area,seccion,fechaIni,fechaFin,null,null);
				
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			this.excelManager.exportConsumoRapido(nombreCentro,area,seccion,fechaIni,fechaFin, listado, headers,model, this.messageSource, response);
			
			
			}catch(Exception e) {
	     	    //logger.error(StackTraceManager.getStackTrace(e));
			    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		        throw e;
			}
			
	}
	
}