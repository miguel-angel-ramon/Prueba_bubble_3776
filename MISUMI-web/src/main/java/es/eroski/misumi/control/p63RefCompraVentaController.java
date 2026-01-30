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

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/refCompraVenta")
public class p63RefCompraVentaController {

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	private static Logger logger = Logger.getLogger(P47PedidoAdicionalEncargosClienteController.class);

	private PaginationManager<VDatosDiarioArt> paginationManager = new PaginationManagerImpl<VDatosDiarioArt>();

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "codArticulo", required = true) Long codArticulo, 
			Map<String, String> model) {

		model.put("codArticulo", codArticulo.toString());

		return "p63_popupRefCompraVenta";
	}

	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<VDatosDiarioArt> loadDataGrid(
			@RequestBody VDatosDiarioArt vDatosDiarioArt,
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

		List<VDatosDiarioArt> list = null;
		User user = (User) session.getAttribute("user");

		//Miramos si es un centro caprabo
		boolean esCentroCaprabo = utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode());
		if (esCentroCaprabo){
			//Si es un centro caprabo, obtenemos el código de artículo eroski para trabajar. Ahora mismo, codArt guarda la referencia Eroski.
			vDatosDiarioArt.setCodArt(utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), vDatosDiarioArt.getCodArt()));
		}

		try {
			list = this.vDatosDiarioArtService.findAllVentaRef(vDatosDiarioArt, pagination);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		Page<VDatosDiarioArt> result = null;

		if (list != null) {
			
			//Recorremos los elementos de la lista.
			for(VDatosDiarioArt vDat:list){
				//Si el centro es de tipo caprabo, obtenemos las referencias caprabo y descripciones caprabo
				//por cada elemento de la lista.
				if(esCentroCaprabo){
					//Obtenemos el codigo caprabo.
					Long codigoCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), vDat.getCodArt());
					vDat.setCodArtGrid(codigoCaprabo);
					
					//Obtenemos la descripción de caprabo.
					String descripcionCaprabo = utilidadesCapraboService.obtenerDescArtCaprabo(codigoCaprabo);
					vDat.setDescripArtGrid(descripcionCaprabo);
				}else{
					vDat.setCodArtGrid(vDat.getCodArt());
					vDat.setDescripArtGrid(vDat.getDescripArt());
				}
			}
			int records = this.vDatosDiarioArtService.findAllVentaRefCont(vDatosDiarioArt).intValue();
			result = this.paginationManager.paginate(new Page<VDatosDiarioArt>(), list,
					max.intValue(), records, page.intValue());	

		} else {
			return new Page<VDatosDiarioArt>();
		}
		return result;
	}
}