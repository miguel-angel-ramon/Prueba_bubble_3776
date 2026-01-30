package es.eroski.misumi.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.AvisosSiec;
import es.eroski.misumi.model.CestasNavidad;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.AvisosSiecService;
import es.eroski.misumi.service.iface.CestasNavidadService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/gestionAvisosSiec")
public class p117GestionAvisosSiecController {
	@Autowired
	private AvisosSiecService avisosSiecService;
	
	@Autowired
	private CestasNavidadService cestasNavidadService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model,HttpServletResponse response, HttpSession session) throws Exception {
		return "p117_gestionAvisosSiec";
	}

	//Es la carga del grid, al paginar o reordenar el grid
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.GET)
	public  @ResponseBody Page<AvisosSiec> loadDataGrid(
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
		List<AvisosSiec> list = null;
		try {
			list = this.avisosSiecService.findAll(pagination);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<AvisosSiec> result = null;

		PaginationManager<AvisosSiec> paginationManager = new PaginationManagerImpl<AvisosSiec>();

		if (list != null && list.size() > 0) {
			int records = list.size();
			int desdeSubList = ((page.intValue()-1) * max.intValue());
			int hastaSubList = ((page.intValue())*max.intValue());

			//Hay que ver desde que elemento hasta que elemento hay que hacer la sublista. Para ello utilizamos
			//el elemento desdeSubList y hastaSubList. Puede darse la situación de que hastaSubList sea mayor a los
			//elementos de la lista, por ejemplo existen 6 paginaciones y se enseñan 10 elementos por paginación,
			//en la paginación 6 el elemento desdeSubList será 50 y hastaSubList 60, pero si la sublista tiene
			//53 elementos da error! Por lo cual hay que hacer un caso en el que hastaSubList sea igual al número
			//de elementos de la lista!
			if(hastaSubList > records){
				hastaSubList = records;				
			}

			List<AvisosSiec> listaAMostrar = list.subList(desdeSubList,hastaSubList);

			result = paginationManager.paginate(new Page<AvisosSiec>(), listaAMostrar, max.intValue(), records, page.intValue());	
		} else {
			return new Page<AvisosSiec>();
		}
		return result;
	}

	//Actualiza la fila
	@RequestMapping(value="/updateRow",method = RequestMethod.POST)
	public @ResponseBody String updateRow(
			@RequestBody AvisosSiec avisosSiec,
			HttpSession session,HttpServletResponse response){
		User user = (User) session.getAttribute("user");
		//Actualizamos la línea en cuestión
		String msgError = null;
		try {
			msgError = this.avisosSiecService.updateLinea(avisosSiec,user.getCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		return msgError;
	}	

	//Actualiza la fila
	@RequestMapping(value="/insertRow",method = RequestMethod.POST)
	public @ResponseBody String insertRow(
			@RequestBody AvisosSiec avisosSiec,
			HttpSession session,HttpServletResponse response){

		//Actualizamos la línea en cuestión
		String msgError = null;
		User user = (User) session.getAttribute("user");
		try {
			msgError = this.avisosSiecService.insertLinea(avisosSiec,user.getCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		return msgError;
	}	

	//Elimina las filas seleccionadas
	@RequestMapping(value="/deleteRows",method = RequestMethod.POST)
	public @ResponseBody int deleteRows(@RequestBody AvisosSiec avisosSiec) throws Exception {
		//Eliminamos las filas
		return this.avisosSiecService.deleteRows(avisosSiec);
	}

}
