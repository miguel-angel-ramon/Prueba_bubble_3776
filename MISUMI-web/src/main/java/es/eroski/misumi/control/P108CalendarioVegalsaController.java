/**
 * 
 */
package es.eroski.misumi.control;

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

import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.calendariovegalsa.DiaCalendarioVegalsa;
import es.eroski.misumi.model.calendariovegalsa.DiaDetalleCalendarioVegalsa;
import es.eroski.misumi.model.ui.GridFilterBean;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.CalendarioVegalsaService;
import es.eroski.misumi.util.iface.ExcelManager;

/**
 * MISUMI-301
 * 
 * @author BICUGUAL
 *
 */
@Controller
@RequestMapping("/calendarioVegalsa")
public class P108CalendarioVegalsaController {

	private static Logger logger = Logger.getLogger(P108CalendarioVegalsaController.class);

	@Resource
	private MessageSource messageSource;

	@Autowired
	private CalendarioVegalsaService calendarioService;

	@Autowired
	private ExcelManager excelManager;

	/**
	 * Carga la pantalla
	 * 
	 * @param center
	 * @param model
	 * @param origenPantalla
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false, defaultValue = "0") Long center, Map<String, String> model,
			@RequestParam(required = false, defaultValue = "") String origenPantalla, HttpServletResponse response,
			HttpSession session) {

		// model.put("origenPantalla", origenPantalla);//????? Para que?

		return "p108_calendarioVegalsa";
	}

	/**
	 * Sirve para cargar los valores del combobox ordenados
	 * 
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadMapasCombo", method = RequestMethod.GET)
	public @ResponseBody List<OptionSelectBean> getLstMapasCombo(HttpServletResponse response, HttpSession session)
			throws Exception {

		logger.debug("Entra en loadMapasCombo");

		List<OptionSelectBean> lstMapas = calendarioService.getLstMapasVegalsa();

		logger.debug("Fin de loadMapasCombo");

		return lstMapas;

	}

	/**
	 * Carga una pagina del calendario Vegalsa para un mes y mapa dados.
	 * 
	 * @param mesAnio
	 * @param codMapa
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadMesCalendario", method = RequestMethod.GET)
	public @ResponseBody List<DiaCalendarioVegalsa> consultarCalendario(
			@RequestParam(value = "mesAnio", required = true) String mesAnio,
			@RequestParam(value = "codMapa", required = true) Integer codMapa, HttpServletResponse response,
			HttpSession session) throws Exception {

		// Consultamos los días del calendario.
		User usuario = (User) session.getAttribute("user");

		// Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		// Objeto de respuesta.
		List<DiaCalendarioVegalsa> lstDias = calendarioService.getLstDiasCalendarioVegalsa(codCentro, codMapa, mesAnio);
		;

		return lstDias;
	}

	/**
	 * Devuelve una pagina del grid con la consulta recibida 
	 * @param codMapa
	 * @param filtros
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadDetalleCalendario", method = RequestMethod.POST)
	public @ResponseBody Page<DiaDetalleCalendarioVegalsa> consultarDetalleCalendarioGrid(
			@RequestParam(value = "codMapa", required = false) Integer codMapa, @RequestBody GridFilterBean filtros,
			HttpServletResponse response, HttpSession session) throws Exception {

		// Consultamos los días del calendario.
		User usuario = (User) session.getAttribute("user");

		// Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		// Objeto de respuesta.
		Page<DiaDetalleCalendarioVegalsa> lstPagedDias = null;

		try {
			lstPagedDias = calendarioService.getLstDiasDetalleCalendarioVegalsaGrid(codCentro, codMapa, filtros);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return lstPagedDias;
	}

	
	/**
	 * Replica la consulta que se ha realizado en el grid para exportar los datos a Excel
	 * @param centro
	 * @param codMapa
	 * @param filtrosJson
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportData", method = RequestMethod.GET)
	public void exportDataGrid(
			@RequestParam(required = false) String centro,
			@RequestParam(required = false) Integer codMapa,
			@RequestParam (required = false) String filtrosJson,
			HttpServletResponse response, HttpSession session) throws Exception {

		logger.info("exportdata -->");

		// Consultamos los días del calendario.
		User usuario = (User) session.getAttribute("user");
		
		// Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();
		
		try {

			List<DiaDetalleCalendarioVegalsa> lstDiasCalendarioVegalsa =calendarioService.getLstDiasDetalleCalendarioVegalsaExport(codCentro, codMapa, filtrosJson); 
			
			this.excelManager.exportDetalleCalendarioVegalsa(lstDiasCalendarioVegalsa, centro, response);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}
}
