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

import es.eroski.misumi.model.CestasNavidad;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.CestasNavidadService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/parametrizacionCestas")
public class p104ParametrizacionCestasController {
	@Autowired
	private CestasNavidadService cestasNavidadService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model, @RequestParam(required = false, defaultValue = "") String origenPantalla,HttpServletResponse response, HttpSession session) throws Exception {
		//model.put("origenPantalla", origenPantalla);
		cestasNavidadService.resetCestasNavidad();

		return "p104_parametrizacionCestas";
	}

	//Es la recarga del grid, al paginar o reordenar el grid
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.GET)
	public  @ResponseBody Page<CestasNavidad> loadDataGrid(
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		List<CestasNavidad> list = null;
		try {
			//Si no es una recarga, es que se ha pulsado buscar
			if(recarga.equals("N")){	
				cestasNavidadService.resetCestasNavidad();
			}
			list = this.cestasNavidadService.findAll(pagination);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<CestasNavidad> result = null;

		PaginationManager<CestasNavidad> paginationManager = new PaginationManagerImpl<CestasNavidad>();

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

			List<CestasNavidad> listaAMostrar = list.subList(desdeSubList,hastaSubList);

			result = paginationManager.paginate(new Page<CestasNavidad>(), listaAMostrar, max.intValue(), records, page.intValue());	
		} else {
			return new Page<CestasNavidad>();
		}
		return result;
	}

	//Actualiza la fila
	@RequestMapping(value="/updateRowDelete",method = RequestMethod.POST)
	public @ResponseBody int updateRowDelete(
			@RequestBody CestasNavidad cestasNavidad,
			HttpSession session,HttpServletResponse response) throws Exception {

		//Actualizamos la línea en cuestión
		int codError = this.cestasNavidadService.updateBorradoLinea(cestasNavidad);
		return codError;
	}	

	//Actualiza la fila
	@RequestMapping(value="/updateFechas",method = RequestMethod.POST)
	public @ResponseBody int updateFechas(
			@RequestBody CestasNavidad cestasNavidad,
			HttpSession session,HttpServletResponse response) throws Exception {

		//Actualizamos la línea en cuestión
		return this.cestasNavidadService.updateFechas(cestasNavidad);
	}	

	//Actualiza la fila
	@RequestMapping(value="/updateRow",method = RequestMethod.POST)
	public @ResponseBody String updateRow(
			@RequestBody CestasNavidad cestasNavidad,
			HttpSession session,HttpServletResponse response){

		//Actualizamos la línea en cuestión
		String msgError = null;
		try {
			msgError = this.cestasNavidadService.updateLinea(cestasNavidad);
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
			@RequestBody CestasNavidad cestasNavidad,
			HttpSession session,HttpServletResponse response){

		//Actualizamos la línea en cuestión
		String msgError;
		try {
			msgError = this.cestasNavidadService.insertLinea(cestasNavidad);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		return msgError;
	}	

	//Elimina las filas seleccionadas
	@RequestMapping(value="/deleteRows",method = RequestMethod.DELETE)
	public @ResponseBody String deleteRows(HttpSession session,HttpServletResponse response) throws Exception {

		//Eliminamos las filas
		String codError = cestasNavidadService.delete();
		return codError;
	}

	//Contar seleccionados
	@RequestMapping(value = "/countSeleccionados", method = RequestMethod.GET)
	public  @ResponseBody int countSeleccionados(
			HttpServletResponse response,
			HttpSession session) throws Exception{

		return cestasNavidadService.countSeleccionados();
	}
}
