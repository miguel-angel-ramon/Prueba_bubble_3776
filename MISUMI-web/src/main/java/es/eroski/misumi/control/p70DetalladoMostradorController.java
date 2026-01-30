package es.eroski.misumi.control;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetalladoMostrador;
import es.eroski.misumi.model.DetalladoMostradorInfo;
import es.eroski.misumi.model.EstrucComercialMostrador;
import es.eroski.misumi.model.OfertaDetalleMostrador;
import es.eroski.misumi.model.ReferenciaNoGamaMostrador;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VMisDetalladoMostrador;
import es.eroski.misumi.model.VMisDetalladoMostradorWrapperList;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.DetalladoMostradorService;
import es.eroski.misumi.util.ParametrizacionCentros;
import es.eroski.misumi.util.iface.ExcelMostradorManager;

@Controller
@RequestMapping("/detalladoMostrador")
@SessionAttributes("loadDetalladoMostrador")
public class p70DetalladoMostradorController {

	private static Logger logger = Logger.getLogger(p70DetalladoMostradorController.class);

	@Resource
	private MessageSource messageSource;
	@Autowired
	private DetalladoMostradorService detalladoMostradorService;
	
	@SuppressWarnings("unused")
	@Autowired
	private ExcelMostradorManager excelManager;
	
	/**
	 * Navegacion a pagina principal
	 * @author BICUGUAL
	 * @param center
	 * @param model
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false, defaultValue = "0") Long center, Model model,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		return "p70_detalladoMostrador";
	}

	/**
	 * Carga de estructura comercial segun los parametros recibidos.
	 * @author BICUGUAL
	 * @param codCentro
	 * @param codN2
	 * @param codN3
	 * @param codN4
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadEstructuraComercial", method = RequestMethod.GET)
	public @ResponseBody EstrucComercialMostrador obtenerEstructuraComercial(
			@RequestParam(required = true) Long codCentro, 
			@RequestParam(value = "codSeccion", required = false) Long codN2,
			@RequestParam(value = "codCategoria", required = false) Long codN3, 
			@RequestParam(value = "codSubcategoria", required = false) Long codN4,
			@RequestParam(value = "tipoAprov", required = false) String tipoAprov,
			HttpSession session, HttpServletResponse response) throws Exception {

		EstrucComercialMostrador result = new EstrucComercialMostrador();

		try {
			result = this.detalladoMostradorService.findEstructuraComercial(codCentro, codN2, codN3, codN4, tipoAprov);
		} catch (Exception e) {
			logger.error("loadEstructuraComercial=" + e.toString());
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * Invocacion al PL de consulta que carga la tabla temporal de la que lee la busqueda 
	 * @author BICUGUAL
	 * @param codCentro
	 * @param seccion
	 * @param categoria
	 * @param subcategoria
	 * @param segmento
	 * @param soloVenta
	 * @param gamaLocal
	 * @param diaEspejo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loadDetalleMostrador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Long loadDetalleMostrador(@RequestParam(required = true) Long codCentro,
			@RequestParam(value = "codSeccion", required = false) Long seccion,
			@RequestParam(value = "codCategoria", required = false) Long categoria,
			@RequestParam(value = "codSubcategoria", required = false) Long subcategoria,
			@RequestParam(value = "codSegmento", required = false) Long segmento,
			@RequestParam(value = "tipoAprov", required = false) String tipoAprov,
			@RequestParam(value = "soloVenta", required = false) String soloVenta,
			@RequestParam(value = "gamaLocal", required = false) String gamaLocal,
			@RequestParam(value = "diaEspejo", required = false) 
			@DateTimeFormat(pattern = "dd/MM/yyyy") Date diaEspejo, 
			HttpSession session) {

		Long count = 0L;

		try {
			count = this.detalladoMostradorService.cargarDetalladoMostrador(codCentro, seccion, categoria, subcategoria, segmento, tipoAprov, soloVenta, gamaLocal, diaEspejo, session.getId());

		} catch (Exception e) {
			logger.error("loadDetalleMostrador=" + e.toString());
			e.printStackTrace();
		}

		return count;
	}

	/**
	 * Carga de datos en el grid leyendo de vista 
	 * @author BICUGUAL
	 * @param codCentro
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loadGridDetalleMostrador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DetalladoMostrador loadDetalleMostradorData(@RequestParam(required = true) Long codCentro, HttpSession session) {

		DetalladoMostrador result = new DetalladoMostrador();

		try {
			User user = (User) session.getAttribute("user");
			// Obtener los datos de la vista V_MIS_DETALLADO_MOSTRADOR.
			List<VMisDetalladoMostrador> rows = this.detalladoMostradorService.findDetalleNivel1Mostrador(codCentro, session.getId());

			// Obtener el resto de datos
			DetalladoMostradorInfo userData = this.detalladoMostradorService.obtenerDatosCabecera(codCentro, session.getId());
			// MISUMI-526
			Boolean mostrarStock=ParametrizacionCentros.contieneOpcion(user.getCentro().getOpcHabil(), "52_DETALLADO_MOSTRADOR_STOCK");
			if(mostrarStock && rows.size()>0){
				rows=this.detalladoMostradorService.setStock(codCentro,rows,session);
			}
			// FIN MISUMI-526
			result.setRows(rows);
			result.setUserData(userData);

		} catch (Exception e) {
			logger.error("loadDetalleMostrador=" + e.toString());
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * Carga de referencias del subgrid
	 * @author BICUGUAL
	 * @param idNivel1
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loadSubgridDetalleMostrador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Page<VMisDetalladoMostrador> loadSubgridDetalleMostradorData(
			@RequestParam(value = "ident", required = true) Long idNivel1, HttpSession session) {

		// Obtener los datos de la vista V_MIS_DETALLADO_MOSTRADOR.
		List<VMisDetalladoMostrador> lstDetalleMostrador = null;

		Page<VMisDetalladoMostrador> pageDetalleMostrador = new Page<VMisDetalladoMostrador>();
		try {
			lstDetalleMostrador = this.detalladoMostradorService
					.findDetalleNivel2Mostrador(session.getId(), idNivel1);
			pageDetalleMostrador.setRows(lstDetalleMostrador);

		} catch (Exception e) {
			logger.error("loadDetalleMostrador=" + e.toString());
			e.printStackTrace();
		}

		return pageDetalleMostrador;
	}

	/**
	 * Redondeo
	 * @author BICUGUAL
	 * @param codCentro
	 * @param referencia
	 * @param pdteRecibirVenta
	 * @param unidadesCaja
	 * @param propuestaPedido
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/redondeo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody VMisDetalladoMostrador getRedondeoDetalleMostradorData(
			@RequestParam(required = true) Long codCentro, 
			@RequestParam(required = false) Long referencia,
			@RequestParam(required = false) Long pdteRecibirVenta, 
			@RequestParam(required = false) Double unidadesCaja,
			@RequestParam(required = false) Long propuestaPedido, 
			HttpSession session) {

		VMisDetalladoMostrador result = null;

		try {
			result = detalladoMostradorService.redondeoDetallado(codCentro, referencia, pdteRecibirVenta, unidadesCaja,
					propuestaPedido);
		} catch (Exception e) {
			logger.error("loadRedondeoDetalleMostrador=" + e.toString());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Devuelve un ok
	 * 
	 * @param lstDetalleMostradorToSave
	 * @param codCentro
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/saveGridDetalleMostrador", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String saveDetalleMostradorData(
			@RequestBody VMisDetalladoMostradorWrapperList lstDetalleMostradorToSave,
			@RequestParam(required = true) Long codCentro, HttpSession session) {

		 try {
			detalladoMostradorService.actualizarDatosGrid(lstDetalleMostradorToSave, codCentro, session.getId());
		 } catch (Exception e) {
			 logger.error("saveGridDetalleMostrador="+e.toString());
			 e.printStackTrace();
		 }

		return "OK";
	}

	/**
	 * Calculo para mostrar la bomba
	 * 
	 * @param codCentro
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calculoCronometro", method = RequestMethod.GET)
	public @ResponseBody Cronometro calculoCronometro(@RequestParam(required = true) Long codCentro, HttpSession session) throws Exception {
		return this.detalladoMostradorService.calculoCronometroYNumeroHorasLimite(codCentro, session.getId());
	}
	
	/**
	 * Devuelve los dias festivos.
	 * 
	 * @param codCentro
	 * @return
	 */
	@RequestMapping(value = "/festivos", method = RequestMethod.GET)
	public @ResponseBody List<String> getDiasFestivos(@RequestParam(required = true) Long codCentro) throws Exception {
		return detalladoMostradorService.getFestivos(codCentro);
	}
	
	/**
	 * Devuelve la lista de ofertas de la vista v_mis_ofer_mostrador
	 * @param codCentro
	 * @param referencia
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ofertas", method = RequestMethod.GET)
	public @ResponseBody List<OfertaDetalleMostrador> getOfertas(
			@RequestParam(required = true) Long codCentro, 
			@RequestParam(required = true) Long referencia,
			HttpSession session) throws Exception {
		
		List<OfertaDetalleMostrador> lstOfertas = detalladoMostradorService.getOfertasDetalleMostrador(codCentro, referencia, session.getId());

		return lstOfertas;
	}
	
	
	/**
	 * Limpia datos de session.
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "/clearSessionCenter", method = RequestMethod.GET)
	public void clearSessionCenter(HttpServletResponse response, HttpSession session) throws Exception {

		User usuario = (User) session.getAttribute("user");
		usuario.setCentro(null);
		session.setAttribute("user", usuario);
	}
	
	 /**
     * Exportar a excel los resultados de la consulta
     * 
     * @param principal
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void generateExcel(@RequestParam String cabecera, @RequestParam String rows, HttpServletResponse response) throws Exception {

    	try {
    		detalladoMostradorService.exportDetalleMostrador(cabecera, rows, response);
			
		} catch (Exception e) {
			logger.error("generateExcel=" + e.toString());
			e.printStackTrace();
		}

    }

    
    /**
	 * Invocacion al PL de consulta que carga la tabla temporal de referencias no gama 
	 * @author BICUGUAL
	 * @param codCentro
	 * @param seccion
	 * @param categoria
	 * @param subcategoria
	 * @param segmento
	 * @param soloVenta
	 * @param gamaLocal
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loadReferenciasNoGamaCentro", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Integer loadReferenciasNoGamaCentro(
			@RequestParam(required = true) Long codCentro,
			@RequestParam(value = "descripcion") String descripcion,
			@RequestParam(value = "codSeccion", required = false) String seccion,
			@RequestParam(value = "codCategoria", required = false) String categoria,
			@RequestParam(value = "codSubcategoria", required = false) String subcategoria,
			@RequestParam(value = "codSegmento", required = false) String segmento,
			@RequestParam(value = "gamaLocal", required = false) String gamaLocal,
			HttpSession session) {

		Integer numReg = 0;

		try {
			numReg = this.detalladoMostradorService.loadReferenciasNoGama(codCentro, descripcion, seccion, categoria, subcategoria, segmento, gamaLocal, session.getId());
			
		} catch (Exception e) {
			logger.error("loadReferenciasNoGamaCentro=" + e.toString());
			e.printStackTrace();
		}

		return numReg;
	}
    
    /**
	 * Invocacion al PL de consulta que carga la tabla temporal de referencias no gama 
	 * @author BICUGUAL
	 * @param codCentro
	 * @param seccion
	 * @param categoria
	 * @param subcategoria
	 * @param segmento
	 * @param soloVenta
	 * @param gamaLocal
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getReferenciasNoGamaCentro", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Page<ReferenciaNoGamaMostrador> getReferenciasNoGamaCentro(
			@RequestParam(required = true) Long codCentro,
			@RequestParam(value = "rows", required = false, defaultValue="10") Long max,
			@RequestParam(value = "page", required = true, defaultValue="1") Long page,
			HttpSession session) {

		Page<ReferenciaNoGamaMostrador> pageReferencias = null;

		try {
			pageReferencias = this.detalladoMostradorService.getLstReferenciasNoGama(session.getId(), codCentro, page, max);
		} catch (Exception e) {
			logger.error("getReferenciasNoGamaCentro=" + e.toString());
			e.printStackTrace();
		}

		return pageReferencias;
	}
	
	
}