package es.eroski.misumi.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.VAlbaran;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VAlbaranesService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/misPedidos/albaranesElectronicos")
public class p24MisPedidosAlbElecController {
	
	private static Logger logger = Logger.getLogger(p24MisPedidosAlbElecController.class);

	private PaginationManager<VAlbaran> paginationManager = new PaginationManagerImpl<VAlbaran>();
	
	@Autowired
	private VAlbaranesService vAlbaranesService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {

		return "p24_popupAlbaranElectronico";
	}
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<VAlbaran> loadDataGrid(
			@RequestBody SeguimientoMiPedido seguimientoMiPedido,
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
			List<VAlbaran> list = null;
			
			try {
				list = this.vAlbaranesService.findSeguimientoMiPedido(seguimientoMiPedido, pagination);
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			Page<VAlbaran> result = null;
			
			if (list != null) {
				int records = this.vAlbaranesService.findSeguimientoMiPedidoCont(seguimientoMiPedido).intValue();
				result = this.paginationManager.paginate(new Page<VAlbaran>(), list,
						max.intValue(), records, page.intValue());	
				
			} else {
				return new Page<VAlbaran>();
			}
			 return result;
	}

}