package es.eroski.misumi.control;

import java.math.BigInteger;
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

import es.eroski.misumi.model.Articulo;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VArtCentroAlta;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VConfirmacionPedidoService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;




@Controller
@RequestMapping("/misPedidosTextil")
public class p66AyudaTextilPopUpController {
	
	private static Logger logger = Logger.getLogger(p66AyudaTextilPopUpController.class);

	private PaginationManager<DetallePedido> paginationManager = new PaginationManagerImpl<DetallePedido>();
	
	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	@Autowired
	private VConfirmacionPedidoService vConfirmacionPedidoService;
	@Autowired
	private RelacionArticuloService relacionArticuloService;
	@Autowired
	private StockTiendaService correccionStockService;
	
	@Autowired
	private ExcelManager excelManager;
	@Resource
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {

		return "p66_ayudaTextilPopUp";
	}
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<DetallePedido> loadDataGrid(
			@RequestBody ReferenciasCentro referenciasCentro,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
			/*
	        Pagination pagination= new Pagination(max,page);
	        if (index!=null){
	            pagination.setSort(index);
	        }
	        if (sortOrder!=null){
	            pagination.setAscDsc(sortOrder);
	        }
	        */
			List<DetallePedido> listaArticulos = new ArrayList<DetallePedido>();
			
			try {
			
				//Obtener las referencias relacionadas por el mismo modelo provedor
				List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
				listaReferencias.add(BigInteger.valueOf(referenciasCentro.getCodArt()));
				
				List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoLote(referenciasCentro.getCodArt());	
						
				for (Long articuloLista : referenciasMismoModeloProveedor) {
					String strArticulo = articuloLista + "";
					if (!strArticulo.equals(referenciasCentro.getCodArt() + "")) { //El articulo consultado por el usuario  (el de la cabecera) ya esta metido en la lista
						listaReferencias.add(BigInteger.valueOf(articuloLista));
					}
				}
							
	
				//Preparar los parametros para llamar al WS que devolver los stocks de todos los articulos relacionados por modelo proveedor
				ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
				stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(referenciasCentro.getCodCentro()));
				stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA); //En este caso es una consulta basica, al WS se le pasara una lista de referencias
				stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
				
				//Llamar al WS
				ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest, session);
					
				
				//Recorrer la lista referencias tratadas y obtener la información especifica de textil, 
				//se accede a la vista V_DATOS_ESPECIFICOS_TEXTIL para completar la información de cada referencia.
				
				for (ReferenciaType referencia : stockTiendaResponse.getListaReferencias()){
					
					DetallePedido detallePedido = new DetallePedido();
				
					List<PdaArticulo> listaArticulosTextil = this.relacionArticuloService.findDatosEspecificosTextil(referencia.getCodigoReferencia().longValue());
						
					for (PdaArticulo articuloTextil : listaArticulosTextil){
						
						detallePedido.setModeloProveedor(articuloTextil.getModeloProveedor());
						detallePedido.setDescrTalla(articuloTextil.getDescrTalla());
						detallePedido.setDescrColor(articuloTextil.getDescrColor());

					}
					
					detallePedido.setCodArticulo(referencia.getCodigoReferencia().longValue());
					detallePedido.setDescriptionArt(referencia.getDescripcion());
					detallePedido.setStock(referencia.getStock().doubleValue());
					
					listaArticulos.add(detallePedido);
				}
				

				Page<DetallePedido> result = null;
				if (!listaArticulos.isEmpty()) {
					int records = listaArticulos.size();
					result = this.paginationManager.paginate(new Page<DetallePedido>(), listaArticulos,
							listaArticulos.size(), records, 1);	
					
				} else {
					return new Page<DetallePedido>();
				}
				 return result;


			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
	
	}

	
}