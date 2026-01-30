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

import es.eroski.misumi.model.ArtGamaRapid;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ListadoGamaRapidService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/altasCatalogoRapid")
public class p12AltasCatalogoRapidController {
	private static Logger logger = Logger.getLogger(p12AltasCatalogoRapidController.class);
	// private static String templateFileName = "altaCatalogo.xls";
	private PaginationManager<ArtGamaRapid> paginationManager = new PaginationManagerImpl<ArtGamaRapid>();

	@Autowired
	private VAgruComerRefService vAgruComerRefService;

	@Autowired
	private ListadoGamaRapidService gamaRapid;

	@Autowired
	private ExcelManager excelManager;
	
	@Resource
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
		return "p12_altasCatalogoRapid";
	}

	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public @ResponseBody Page<ArtGamaRapid> loadDataGrid(@RequestBody ArtGamaRapid artGamaRapid,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder, HttpServletResponse response,
			HttpSession session) throws Exception {

		Pagination pagination = new Pagination(max, page);
		if (index != null) {
			pagination.setSort(index);
		}
		if (sortOrder != null) {
			pagination.setAscDsc(sortOrder);
		}
		List<ArtGamaRapid> list = null;

		try {
			list = this.gamaRapid.findAll(artGamaRapid, pagination);
		} catch (Exception e) {
			// logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		Page<ArtGamaRapid> result = null;

		int records = 0;
		if ((list != null) && (list.size() > 0)) {
			records = this.gamaRapid.findAllCont(artGamaRapid).intValue();
		}

		result = this.paginationManager.paginate(new Page<ArtGamaRapid>(), list, max.intValue(), records,
				page.intValue());
		return result;
	}

	@RequestMapping(value = "/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerRef> getAreaData(@RequestBody VAgruComerRef vAgruCommerRef, HttpSession session,
			HttpServletResponse response) throws Exception {
		try {
			return this.vAgruComerRefService.findAll(vAgruCommerRef, null);
		} catch (Exception e) {
			// logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody void exportGrid(@RequestParam(required = false) String[] headers,
			@RequestParam(required = false) String[] model, @RequestParam(required = false) Long grupo1,
			@RequestParam(required = false) Long grupo2, @RequestParam(required = false) Long grupo3,
			@RequestParam(required = false) Long grupo4, @RequestParam(required = false) Long grupo5,
			@RequestParam(required = false) Long codigoArticulo, HttpServletResponse response, HttpSession session)
			throws Exception {
		try {
			List<GenericExcelVO> list = new ArrayList<GenericExcelVO>();
			User user = (User) session.getAttribute("user");

			ArtGamaRapid artGamaRapid = new ArtGamaRapid(user.getCentro().getCodCentro(),
					user.getCentro().getDescripCentro(), codigoArticulo, null, grupo1, grupo2, grupo3, grupo4, grupo5,
					null, null, null, null, null, null, null, null, null, null, null, null, null);

			VAgruComerRef vAgruComerGrupoArea = null;
			VAgruComerRef vAgruComerGrupoSection = null;
			VAgruComerRef vAgruComerGrupoCategory = null;
			VAgruComerRef vAgruComerGrupoSubCategory = null;
			VAgruComerRef vAgruComerGrupoSegment = null;
			if (grupo1 != null) {
				vAgruComerGrupoArea = this.vAgruComerRefService
						.findAll(new VAgruComerRef("I1", grupo1, null, null, null, null, null), null).get(0);
			}
			if (grupo2 != null) {
				vAgruComerGrupoSection = this.vAgruComerRefService
						.findAll(new VAgruComerRef("I2", grupo1, grupo2, null, null, null, null), null).get(0);
			}
			if (grupo3 != null) {
				vAgruComerGrupoCategory = this.vAgruComerRefService
						.findAll(new VAgruComerRef("I3", grupo1, grupo2, grupo3, null, null, null), null).get(0);
			}
			if (grupo4 != null) {
				vAgruComerGrupoSubCategory = this.vAgruComerRefService
						.findAll(new VAgruComerRef("I4", grupo1, grupo2, grupo3, grupo4, null, null), null).get(0);
			}
			if (grupo5 != null) {
				vAgruComerGrupoSegment = this.vAgruComerRefService
						.findAll(new VAgruComerRef("I5", grupo1, grupo2, grupo3, grupo4, grupo5, null), null).get(0);
			}

			try {
				logger.info("Exporting excel Alta Catalogo:" + model.toString());
				list = this.gamaRapid.findAllExcel(artGamaRapid, model);
			} catch (Exception e) {
				// logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}

			this.excelManager.exportGamaRapid(list, headers, model, this.messageSource, artGamaRapid,
					vAgruComerGrupoArea, vAgruComerGrupoSection, vAgruComerGrupoCategory, vAgruComerGrupoSubCategory,
					vAgruComerGrupoSegment, response);
		} catch (Exception e) {
			// logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

}