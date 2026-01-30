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

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VConfirmacionPedidoService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/misPedidos/cantidadesPedidas")
public class p21MisPedidosCantPedController {

	private static Logger logger = Logger.getLogger(p21MisPedidosCantPedController.class);

	private PaginationManager<SeguimientoMiPedidoDetalle> paginationManager = new PaginationManagerImpl<SeguimientoMiPedidoDetalle>();

	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	@Autowired
	private VConfirmacionPedidoService vConfirmacionPedidoService;
	@Autowired
	private ExcelManager excelManager;
	@Resource
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {

		return "p21_popupCantidadesPedidas";
	}

	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<SeguimientoMiPedidoDetalle> loadDataGrid(
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
		List<SeguimientoMiPedidoDetalle> list = null;

		try {
			list = this.vConfirmacionPedidoService.findSeguimientoMiPedido(seguimientoMiPedido, pagination);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		Page<SeguimientoMiPedidoDetalle> result = null;

		if (list != null) {
			int records = this.vConfirmacionPedidoService.findSeguimientoMiPedidoCont(seguimientoMiPedido).intValue();
			result = this.paginationManager.paginate(new Page<SeguimientoMiPedidoDetalle>(), list,
					max.intValue(), records, page.intValue());	

		} else {
			return new Page<SeguimientoMiPedidoDetalle>();
		}
		return result;
	}

	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody
	void exportGrid(
			@RequestParam(required = false) String[] headers,
			@RequestParam(required = false) String[] model,
			@RequestParam(required = false) Long codCentro,
			@RequestParam(required = false) String fechaPedidoDDMMYYYY,
			@RequestParam(required = false) Long codArea,
			@RequestParam(required = false) Long codSeccion,
			@RequestParam(required = false) Long codCategoria,
			@RequestParam(required = false) Long codArt,
			@RequestParam(required = false) String codMapa,
			@RequestParam(required = false) String descMapa,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {
			List<GenericExcelVO> list = new ArrayList<GenericExcelVO>();
			User user = (User) session.getAttribute("user");

			SeguimientoMiPedido seguimientoMiPedido = new SeguimientoMiPedido(codCentro, fechaPedidoDDMMYYYY, null, codArea,
					null, codSeccion, null, codCategoria, null, null, codArt);

			VAgruComerRef vAgruComerGrupoArea=null;
			VAgruComerRef vAgruComerGrupoSection=null;
			VAgruComerRef vAgruComerGrupoCategory=null;
			SeguimientoMiPedidoDetalle seguimientoMiPedidoDetalle = new SeguimientoMiPedidoDetalle();
		    // MISUMI-299
		    if (codMapa!=null){
		    	seguimientoMiPedido.setMapa(codMapa);
		    	seguimientoMiPedidoDetalle.setCodMapa(codMapa);
		    }
		    if (descMapa!=null){
		    	seguimientoMiPedido.setDescrMapa(descMapa);
		    	seguimientoMiPedidoDetalle.setDescMapa(descMapa);
		    }
		    //-----------
			if (codArea!=null){
				vAgruComerGrupoArea=this.vAgruComerRefService.findAll(new VAgruComerRef("I1",codArea,null,null,null,null,null), null).get(0);
			}
			if (codSeccion!=null){
				vAgruComerGrupoSection = this.vAgruComerRefService.findAll(new VAgruComerRef("I2",codArea,codSeccion,null,null,null,null), null).get(0);
			}
			if (codCategoria!=null){
				vAgruComerGrupoCategory =this.vAgruComerRefService.findAll(new VAgruComerRef("I3",codArea,codSeccion,codCategoria,null,null,null), null).get(0);
			}	

			//Obtención de los totales de referencias y cajas
			seguimientoMiPedidoDetalle.setPedTotalRefBajoPedido(this.vConfirmacionPedidoService.findTotalReferenciasBajoPedido(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalCajasBajoPedido(this.vConfirmacionPedidoService.findTotalCajasBajoPedido(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalRefEmpuje(this.vConfirmacionPedidoService.findTotalReferenciasEmpuje(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalCajasEmpuje(this.vConfirmacionPedidoService.findTotalCajasEmpuje(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalRefImplCab(this.vConfirmacionPedidoService.findTotalReferenciasImplCab(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalCajasImplCab(this.vConfirmacionPedidoService.findTotalCajasImplCab(seguimientoMiPedido));

			seguimientoMiPedidoDetalle.setPedTotalRefIntertienda(this.vConfirmacionPedidoService.findTotalReferenciasIntertienda(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalCajasIntertienda(this.vConfirmacionPedidoService.findTotalCajasIntertienda(seguimientoMiPedido));

			try {
				logger.info("Exporting excel Pedidos cantidades pedidas:"+model.toString());
				
				list = this.vConfirmacionPedidoService.findSeguimientoMiPedidoExcel(seguimientoMiPedido, model);

			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			Boolean isCentroVegalsa = this.vConfirmacionPedidoService.isCentroVegalsa(user.getCentro().getCodCentro());
			this.excelManager.exportMisPedidosCantPed(list, headers, model, this.messageSource, user.getCentro(), fechaPedidoDDMMYYYY, vAgruComerGrupoArea, vAgruComerGrupoSection, vAgruComerGrupoCategory, seguimientoMiPedidoDetalle,isCentroVegalsa,response);

		}catch(Exception e) { 

			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}

	@RequestMapping(value = "/loadDataCabecera", method = RequestMethod.POST)
	public  @ResponseBody SeguimientoMiPedidoDetalle loadDataCabecera(
			@RequestBody SeguimientoMiPedido seguimientoMiPedido,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		SeguimientoMiPedidoDetalle seguimientoMiPedidoDetalle = new SeguimientoMiPedidoDetalle();

		try {

			//Obtenci�n de los totales de referencias y cajas

			seguimientoMiPedidoDetalle.setPedTotalRefBajoPedido(this.vConfirmacionPedidoService.findTotalReferenciasBajoPedido(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalCajasBajoPedido(this.vConfirmacionPedidoService.findTotalCajasBajoPedido(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalRefEmpuje(this.vConfirmacionPedidoService.findTotalReferenciasEmpuje(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalCajasEmpuje(this.vConfirmacionPedidoService.findTotalCajasEmpuje(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalRefImplCab(this.vConfirmacionPedidoService.findTotalReferenciasImplCab(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalCajasImplCab(this.vConfirmacionPedidoService.findTotalCajasImplCab(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalRefIntertienda(this.vConfirmacionPedidoService.findTotalReferenciasIntertienda(seguimientoMiPedido));
			seguimientoMiPedidoDetalle.setPedTotalCajasIntertienda(this.vConfirmacionPedidoService.findTotalCajasIntertienda(seguimientoMiPedido));
		}catch(Exception e) { 

			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return seguimientoMiPedidoDetalle;
	}

}