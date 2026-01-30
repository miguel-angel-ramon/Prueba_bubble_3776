package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;

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

import es.eroski.misumi.model.MotivosBloqueos;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.VBloqueoEncargosPiladasService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/motivosBloqueo")
public class p74MotivosBloqueoController {
	
	private static Logger logger = Logger.getLogger(p74MotivosBloqueoController.class);

	private PaginationManager<VBloqueoEncargosPiladas> paginationManagerMotivos = new PaginationManagerImpl<VBloqueoEncargosPiladas>();
	private PaginationManager<TPedidoAdicional> paginationManagerPedidos = new PaginationManagerImpl<TPedidoAdicional>();
	
	@Autowired
	private VBloqueoEncargosPiladasService vBloqueoEncargosPiladasService;

	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@RequestMapping(value="/loadMotivosBloqueos", method = RequestMethod.POST)
	public @ResponseBody MotivosBloqueos loadMotivosBloqueo(
			@RequestBody VBloqueoEncargosPiladas vBloqueoEncargosPiladas,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "4") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "buscarPedidos", required = false, defaultValue = "N") String buscarPedidos,
			HttpSession session, HttpServletResponse response) throws Exception {
		
		MotivosBloqueos motivosBloqueos = new MotivosBloqueos();
		
        Pagination pagination= new Pagination(max,page);
		List<VBloqueoEncargosPiladas> listMotivos = null;
		List<TPedidoAdicional> listPedidos = null;
		
		User user = (User) session.getAttribute("user");
		
		//Búsqueda de motivos
		try {
			vBloqueoEncargosPiladas.setCodCentro(user.getCentro().getCodCentro());
			
			listMotivos = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueada(vBloqueoEncargosPiladas, pagination);
			
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		Page<VBloqueoEncargosPiladas> motivos = null;
		
		if (listMotivos != null && listMotivos.size() > 0) {
			int records = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
			motivos = this.paginationManagerMotivos.paginate(new Page<VBloqueoEncargosPiladas>(), listMotivos,
					max.intValue(), records, page.intValue());	
			
		} else {
			motivos = new Page<VBloqueoEncargosPiladas>();
		}
		
		//Búsqueda de pedidos
		if ("S".equals(buscarPedidos)){
			try {
				vBloqueoEncargosPiladas.setCodCentro(user.getCentro().getCodCentro());
				
				listPedidos = this.obtenerPedidosBloqueo(vBloqueoEncargosPiladas, session.getId(), pagination);
				
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
		}
		
		Page<TPedidoAdicional> pedidos = null;
		
		if (listPedidos != null && listPedidos.size() > 0) {
			int records = this.obtenerPedidosBloqueoCont(vBloqueoEncargosPiladas, session.getId());
			pedidos = this.paginationManagerPedidos.paginate(new Page<TPedidoAdicional>(), listPedidos,
					max.intValue(), records, page.intValue());	
			
		} else {
			pedidos = new Page<TPedidoAdicional>();
		}

		motivosBloqueos.setMotivos(motivos);
		motivosBloqueos.setPedidos(pedidos);
		
		return motivosBloqueos;
	}
	
	@RequestMapping(value="/loadMotivos", method = RequestMethod.POST)
	public @ResponseBody Page<VBloqueoEncargosPiladas> loadMotivos(
			@RequestBody VBloqueoEncargosPiladas vBloqueoEncargosPiladas,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "4") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpSession session, HttpServletResponse response) throws Exception {
		
        Pagination pagination= new Pagination(max,page);
        if (index!=null){
            pagination.setSort(index);
        }
        if (sortOrder!=null){
            pagination.setAscDsc(sortOrder);
        }
		List<VBloqueoEncargosPiladas> list = null;
		
		try {
			User user = (User) session.getAttribute("user");
			
			vBloqueoEncargosPiladas.setCodCentro(user.getCentro().getCodCentro());
			
		    list = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueada(vBloqueoEncargosPiladas, pagination);
			
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		Page<VBloqueoEncargosPiladas> result = null;
		
		if (list != null) {
			int records = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
			result = this.paginationManagerMotivos.paginate(new Page<VBloqueoEncargosPiladas>(), list,
					max.intValue(), records, page.intValue());	
			
		} else {
			return new Page<VBloqueoEncargosPiladas>();
		}
		
		return result;
	}
	
	@RequestMapping(value="/loadBloqueos", method = RequestMethod.POST)
	public @ResponseBody Page<TPedidoAdicional> loadBloqueos(
			@RequestBody VBloqueoEncargosPiladas vBloqueoEncargosPiladas,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "4") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpSession session, HttpServletResponse response) throws Exception {
		
        Pagination pagination= new Pagination(max,page);
        if (index!=null){
            pagination.setSort(index);
        }
        if (sortOrder!=null){
            pagination.setAscDsc(sortOrder);
        }
		List<TPedidoAdicional> list = null;
		
		//Búsqueda de pedidos
		try {
			User user = (User) session.getAttribute("user");
			vBloqueoEncargosPiladas.setCodCentro(user.getCentro().getCodCentro());
			
			list = this.obtenerPedidosBloqueo(vBloqueoEncargosPiladas, session.getId(), pagination);
			
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		Page<TPedidoAdicional> result = null;
		
		if (list != null) {
			int records = this.obtenerPedidosBloqueoCont(vBloqueoEncargosPiladas, session.getId());
			result = this.paginationManagerPedidos.paginate(new Page<TPedidoAdicional>(), list,
					max.intValue(), records, page.intValue());	
			
		} else {
			return new Page<TPedidoAdicional>();
		}

		return result;
	}	
	
	private List<TPedidoAdicional> obtenerPedidosBloqueo(VBloqueoEncargosPiladas vBloqueoEncargosPiladas, String idSesion, Pagination pagination){

		List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();
		
		//Carga de búsqueda de pedido
		TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
		tPedidoAdicional.setIdSesion(idSesion);
		tPedidoAdicional.setCodCentro(vBloqueoEncargosPiladas.getCodCentro());
		tPedidoAdicional.setPantalla(Constantes.PANTALLA_LISTADOS);
		tPedidoAdicional.setCodArticulo(vBloqueoEncargosPiladas.getCodArticulo());
		tPedidoAdicional.setPerfil(new Long(1)); //Pet. 58538
		tPedidoAdicional.setFechaInicio(vBloqueoEncargosPiladas.getFecIniDDMMYYYY());
		tPedidoAdicional.setFechaFin(vBloqueoEncargosPiladas.getFecFinDDMMYYYY());
		tPedidoAdicional.setClasePedido(vBloqueoEncargosPiladas.getClasePedido());

		try {
			listaTPedidoAdicional = this.tPedidoAdicionalService.findAllBloqueos(tPedidoAdicional, pagination);
		} catch (Exception e) {
			logger.error("obtenerPedidosBloqueo="+e.toString());
			e.printStackTrace();
		}
			
		return listaTPedidoAdicional;
	}
	
	private int obtenerPedidosBloqueoCont(VBloqueoEncargosPiladas vBloqueoEncargosPiladas, String idSesion){

		int records = 0;
		
		//Carga de búsqueda de pedido
		TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
		tPedidoAdicional.setIdSesion(idSesion);
		tPedidoAdicional.setCodCentro(vBloqueoEncargosPiladas.getCodCentro());
		tPedidoAdicional.setPantalla(Constantes.PANTALLA_LISTADOS);
		tPedidoAdicional.setCodArticulo(vBloqueoEncargosPiladas.getCodArticulo());
		tPedidoAdicional.setPerfil(new Long(1));//Pet. 58538
		tPedidoAdicional.setFechaInicio(vBloqueoEncargosPiladas.getFecIniDDMMYYYY());
		tPedidoAdicional.setFechaFin(vBloqueoEncargosPiladas.getFecFinDDMMYYYY());
		tPedidoAdicional.setClasePedido(vBloqueoEncargosPiladas.getClasePedido());

		try {
			
			records = this.tPedidoAdicionalService.findAllBloqueosCont(tPedidoAdicional).intValue();
		} catch (Exception e) {
			logger.error("obtenerPedidosBloqueo="+e.toString());
			e.printStackTrace();
		}
		
		return records;
	}
}