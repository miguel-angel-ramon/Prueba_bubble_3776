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

import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.StockNoServido;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ConfirmacionNoServidoService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/segCampanas/cantidadesNoServidasRef")
public class p29SegCampanasCantNoServController {
	
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private ConfirmacionNoServidoService confirmacionNoServidoService;
	
	private static Logger logger = Logger.getLogger(p29SegCampanasCantNoServController.class);

	private PaginationManager<StockNoServido> paginationManager = new PaginationManagerImpl<StockNoServido>();
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {

		return "p29_popupCantidadesNoServidasRef";
	}
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<StockNoServido> loadDataGrid(
			@RequestBody SeguimientoCampanas seguimientoCampanas,
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

		    List<Long> listaReferencias = null;
			try {
				RelacionArticulo relacionArticulo = new RelacionArticulo();
				relacionArticulo.setCodArt(seguimientoCampanas.getCodArt());
				relacionArticulo.setCodCentro(seguimientoCampanas.getCodCentro());
				listaReferencias = this.relacionArticuloService.findAll(relacionArticulo);
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			List<StockNoServido> list = null;
			try {
				list = this.confirmacionNoServidoService.findAllNoServidos(seguimientoCampanas, listaReferencias, pagination);
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			Page<StockNoServido> result = null;
			
			if (list != null) {
				int records = this.confirmacionNoServidoService.findAllNoServidosCont(seguimientoCampanas, listaReferencias).intValue();
				result = this.paginationManager.paginate(new Page<StockNoServido>(), list,
						max.intValue(), records, page.intValue());	
				
			} else {
				return new Page<StockNoServido>();
			}
			 return result;
	}
}