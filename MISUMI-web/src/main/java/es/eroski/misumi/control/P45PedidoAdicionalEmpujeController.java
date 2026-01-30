package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Arrays;
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

import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoAdicionalEM;
import es.eroski.misumi.model.PedidoAdicionalEMPagina;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.VDatosEspecificosTextilService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/pedidoAdicionalEmpuje")
public class P45PedidoAdicionalEmpujeController {
	
	private static Logger logger = Logger.getLogger(P45PedidoAdicionalEmpujeController.class);
	
	
	private PaginationManager<PedidoAdicionalEM> paginationManagerEM = new PaginationManagerImpl<PedidoAdicionalEM>();
	
	
	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;
	
	@Autowired
	private VDatosEspecificosTextilService datosTextil;
	
	
	@RequestMapping(value = "/loadDataGridEM", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalEMPagina loadDataGrid(
			@RequestBody PedidoAdicionalEM pedidoAdicionalEM,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		Pagination pagination = new Pagination(max, page);
		if (index != null) {
			pagination.setSort(index);
		}
		if (sortOrder != null) {
			pagination.setAscDsc(sortOrder);
		}

		TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
		tPedidoAdicional.setIdSesion(session.getId());
		//tPedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_EMPUJE));
		tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_EMPUJE)));
		tPedidoAdicional.setCodCentro(pedidoAdicionalEM.getCodCentro());
		tPedidoAdicional.setPantalla(Constantes.PANTALLA_LISTADOS);
		tPedidoAdicional.setMAC(pedidoAdicionalEM.getMca());
		List<PedidoAdicionalEM> list = this.obtenerTablaSesionEM(
				tPedidoAdicional, pagination);

		PedidoAdicionalEMPagina pedidoAdicionalEMPagina = new PedidoAdicionalEMPagina();
		Page<PedidoAdicionalEM> listaPedidoAdicionalEMPaginada = null;

		if (list != null) {
			int records = this.tPedidoAdicionalService.findAllCount(
					tPedidoAdicional).intValue();
			listaPedidoAdicionalEMPaginada = this.paginationManagerEM.paginate(
					new Page<PedidoAdicionalEM>(), list, max.intValue(),
					records, page.intValue());
			pedidoAdicionalEMPagina.setDatos(listaPedidoAdicionalEMPaginada);

		} else {
			pedidoAdicionalEMPagina.setDatos(new Page<PedidoAdicionalEM>());
		}
		return pedidoAdicionalEMPagina;

	}
	
	private List<PedidoAdicionalEM> obtenerTablaSesionEM(TPedidoAdicional tPedidoAdicional, Pagination pagination) throws Exception{

		List<PedidoAdicionalEM> listaPedidoAdicionalEM = new ArrayList<PedidoAdicionalEM>();

		List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalService.findAllPaginate(tPedidoAdicional, pagination);

		for (TPedidoAdicional registro : listaTPedidoAdicional) {
			PedidoAdicionalEM nuevoRegistro = new PedidoAdicionalEM();
			nuevoRegistro.setCodCentro(registro.getCodCentro());
			nuevoRegistro.setIdentificador(registro.getIdentificador());
			nuevoRegistro.setCodArticulo(registro.getCodArticulo());
			nuevoRegistro.setDescriptionArt(registro.getDescriptionArt());
			nuevoRegistro.setCodArticuloGrid(registro.getCodArticuloGrid());
			nuevoRegistro.setDescriptionArtGrid(registro.getDescriptionArtGrid());
			nuevoRegistro.setUniCajaServ(registro.getUniCajaServ());
			nuevoRegistro.setUsuario(registro.getUsuario());
			nuevoRegistro.setPerfil(registro.getPerfil());
			nuevoRegistro.setAgrupacion(registro.getAgrupacion());
			nuevoRegistro.setOferta(registro.getOferta());
			nuevoRegistro.setTipoAprovisionamiento(registro.getTipoAprovisionamiento());
			nuevoRegistro.setFechaInicio(registro.getFechaInicio());
			nuevoRegistro.setFechaFin(registro.getFechaFin());
			nuevoRegistro.setClasePedido(registro.getClasePedido());
			nuevoRegistro.setCapMax(registro.getCapMax());
			nuevoRegistro.setCapMin(registro.getCapMin());
			nuevoRegistro.setCantidad1(registro.getCantidad1());
			nuevoRegistro.setCantidad2(registro.getCantidad2());
			nuevoRegistro.setCantidad3(registro.getCantidad3());
			nuevoRegistro.setTipoPedido(registro.getTipoPedido());
			//nuevoRegistro.setDenominacionOferta(registro.getd)
			nuevoRegistro.setCantMax(registro.getCantMax());
			nuevoRegistro.setCantMin(registro.getCantMin());
			nuevoRegistro.setDescOferta(registro.getDescOferta());
			nuevoRegistro.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
			nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
			nuevoRegistro.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
			nuevoRegistro.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
			nuevoRegistro.setTratamiento(registro.getTratamiento());
			nuevoRegistro.setFechaHasta((registro.getFechaHasta() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
			nuevoRegistro.setEstado(registro.getEstado());
			
			
			String agr = nuevoRegistro.getAgrupacion();
			String area = agr.substring(1, 2);
			//TEXTIL
			if(area!=null){
				if("3".equals(area)){
				
					PedidoAdicionalE pedidoAdicional = new PedidoAdicionalE();
					pedidoAdicional.setCodArticulo(nuevoRegistro.getCodArticulo());		
					pedidoAdicional = datosTextil.findAll(pedidoAdicional);
					
					nuevoRegistro.setColor(pedidoAdicional.getColor());
					nuevoRegistro.setTalla(pedidoAdicional.getTalla());
					nuevoRegistro.setModeloProveedor(pedidoAdicional.getModeloProveedor());
				}
			}
			
			listaPedidoAdicionalEM.add(nuevoRegistro);
		}
			
		return listaPedidoAdicionalEM;
	}
	
}