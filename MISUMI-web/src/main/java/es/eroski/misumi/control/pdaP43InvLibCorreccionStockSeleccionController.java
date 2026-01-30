package es.eroski.misumi.control;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.InventarioRotativo;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaSeleccionStock;
import es.eroski.misumi.model.pda.PdaStock;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.InventarioRotativoService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP43InvLibCorreccionStockSeleccionController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP43InvLibCorreccionStockSeleccionController.class);
	
	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private StockTiendaService correccionStockService;

	@Autowired
	private InventarioRotativoService inventarioRotativoService;
	
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	@RequestMapping(value = "/pdaP43InvLibCorreccionStockSeleccion",method = RequestMethod.POST)
	public String processForm(@Valid final PdaSeleccionStock pdaSeleccionStock, ModelMap model,
			HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes,
			HttpServletResponse response) {
		String resultado = "pda_p43_invLibCorreccionStockSeleccion";
		int indiceArticuloInicialPagina = 0;
		int indiceArticuloFinalPagina = 0;
		int totalArticulos = 0;

		try {
			if (null != pdaSeleccionStock.getCodArt()){
				pdaSeleccionStock.setCodArtSel(pdaSeleccionStock.getCodArt());
			}
			
			
			if (null != request.getParameter("actionAnt") || null != request.getParameter("actionSig") )
			{
			
				PagedListHolder<ReferenciaType> listHolder = (PagedListHolder<ReferenciaType>) session.getAttribute("listaRelaciones");
				if (request.getParameter("actionAnt") != null)
				{
					listHolder.previousPage();
				}
				else if (request.getParameter("actionSig") != null)
				{
					listHolder.nextPage();
				}
				pdaSeleccionStock.setCodArt(pdaSeleccionStock.getCodArtSel());
				model.addAttribute("listaRelacion", listHolder);
				model.addAttribute("pdaSeleccionStock", pdaSeleccionStock);
			}
			
			if (null != request.getParameter("actionBack")){
				redirectAttributes.addAttribute("codArtAnterior", "anterior");
				redirectAttributes.addAttribute("noGuardar", pdaSeleccionStock.getNoGuardar());
				redirectAttributes.addAttribute("seccion", pdaSeleccionStock.getSeccion());
				resultado = "redirect:pdaP42InventarioLibre.do";
			}
			if (null != request.getParameter("actionNext")){
				if (null == pdaSeleccionStock.getCodArtSel()){
					Locale locale = LocaleContextHolder.getLocale();
					PagedListHolder<ReferenciaType> listHolder = (PagedListHolder<ReferenciaType>) session.getAttribute("listaRelaciones");
					String mensajeError = this.messageSource.getMessage("pda_p43_correccionStockSeleccion.noReferenceSelected", null, locale);
					model.addAttribute("mensajeError", mensajeError);
					model.addAttribute("listaRelacion", listHolder);
					model.addAttribute("pdaSeleccionStock", pdaSeleccionStock);
				} else {
					ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
					User user = (User) session.getAttribute("user");
					stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
					stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
					List<BigInteger> referencias = new ArrayList<BigInteger>();
					referencias.add(BigInteger.valueOf(pdaSeleccionStock.getCodArtSel()));
					if ("S".equals(pdaSeleccionStock.getMMC())){
						referencias.add(BigInteger.valueOf(pdaSeleccionStock.getCodArtOrig()));
					}
					stockTiendaRequest.setListaCodigosReferencia(referencias.toArray(new BigInteger[referencias.size()]));
					
					
					ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
					paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
					ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
					
					if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
						logger.error("###########################################################################################################");
						logger.error("############################## CONTROLADOR: pdaP43InvLibCorreccionStockSeleccionController  ###############");
						logger.error("###########################################################################################################");
					}
					
					ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest, session);
					
					if (!stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_KO)){
						PdaStock stock = new PdaStock();
						stock.setCodArtOrig(pdaSeleccionStock.getCodArtOrig());
						stock.setOrigen(pdaSeleccionStock.getOrigen());
						stock.setOrigenInventario(pdaSeleccionStock.getOrigenInventario());
						stock.setMMC(pdaSeleccionStock.getMMC());
						if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
							stock.setDescripcionError(stockTiendaResponse.getDescripcionRespuesta());
						}
						stock.setListaArticulos(this.obtenerListado(stockTiendaResponse.getListaReferencias()));
						
						//Cálculo de los artículos de la página 
						indiceArticuloInicialPagina = 0;
						totalArticulos = stock.getListaArticulos().size();
						if (totalArticulos >= Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS){
							indiceArticuloFinalPagina = Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
						}else{
							indiceArticuloFinalPagina = totalArticulos;
						}
						
						stock.setListaArticulosPagina(new ArrayList<PdaArticulo>(stock.getListaArticulos().subList(indiceArticuloInicialPagina, indiceArticuloFinalPagina)));
						
						//Carga de stocks de la temporal
						this.cargarDatosListaTablaTemporal(stock, session);
						
						stock.setTotalArticulos(stock.getListaArticulos().size());
						stock.setPosicionGrupoArticulos(1);
						//Cálculo de total de páginas
						int numeroPaginas = stock.getListaArticulos().size()/Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
						if ((numeroPaginas*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS)<stock.getListaArticulos().size()){
							numeroPaginas++;
						}
						stock.setTotalPaginas(numeroPaginas);
						stock.setTipoMensaje(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE);
						stock.setNoGuardar(pdaSeleccionStock.getNoGuardar());
						stock.setSeccion(pdaSeleccionStock.getSeccion());
						model.addAttribute("pdaStock", stock);
						session.setAttribute("pdaStock", stock);
						resultado = "pda_p45_invLibCorreccionStockPCN";
					} else {
						String mensajeError = stockTiendaResponse.getDescripcionRespuesta();
						PagedListHolder<ReferenciaType> listHolder = (PagedListHolder<ReferenciaType>) session.getAttribute("listaRelaciones");
						model.addAttribute("mensajeError", mensajeError);
						model.addAttribute("listaRelacion", listHolder);
						model.addAttribute("pdaSeleccionStock", pdaSeleccionStock);
					}
				}
			}

		} catch (Exception e) {
			model.addAttribute("mensajeError", "Error al cargar las referencias");
		}
		return resultado;
	}
	
	private ArrayList<PdaArticulo> obtenerListado(ReferenciaType[] listaReferencias) throws Exception{
		
		ArrayList<PdaArticulo> listaArticulos = new ArrayList<PdaArticulo>();
		for (ReferenciaType referencia : listaReferencias){
			PdaArticulo articulo = new PdaArticulo();
			articulo.setCodArt(referencia.getCodigoReferencia().longValue());
			articulo.setDescArt(referencia.getDescripcion());
			if (null != referencia.getPVP()){
				articulo.setPrecio(Utilidades.convertirDoubleAString(referencia.getPVP().floatValue(),"###0.000").replace(",", "."));
			}
			if (null != referencia.getBandejas()){
				articulo.setUnidades(referencia.getBandejas());
			}
			articulo.setStock(Utilidades.convertirDoubleAString(referencia.getStock().floatValue(),"###0.00").replace(",", "."));
			articulo.setTipo(referencia.getTipoReferencia());
			articulo.setKgs(Utilidades.convertirDoubleAString(new Float(0).floatValue(),"###0.000").replace(",", "."));
			listaArticulos.add(articulo);
		}
		return listaArticulos;
	}

	private void cargarDatosListaTablaTemporal(PdaStock pdaStock, HttpSession session) throws Exception{
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String mac = user.getMac();

		if (pdaStock != null && pdaStock.getListaArticulos()!=null){

			//Busco en InventarioRotativo el articulo origen para ver si es una referencia con flgVariasUnitarias
			InventarioRotativo inventarioRotativoOrigen = new InventarioRotativo();
			inventarioRotativoOrigen.setCodArticulo(pdaStock.getCodArtOrig());
			inventarioRotativoOrigen.setCodArticuloRela(pdaStock.getCodArtOrig());
			inventarioRotativoOrigen.setCodCentro(codCentro);
			
			if (null != pdaStock.getOrigenGISAE() && pdaStock.getOrigenGISAE().equals("SI")){
				inventarioRotativoOrigen.setCodMac("GISAE");
			} else {
				inventarioRotativoOrigen.setCodMac(mac);
			}

			inventarioRotativoOrigen = this.inventarioRotativoService.findOne(inventarioRotativoOrigen);
			
			for (PdaArticulo pdaArticulo : pdaStock.getListaArticulos()){
				InventarioRotativo inventarioRotativo = new InventarioRotativo();
				if (inventarioRotativoOrigen != null && Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(inventarioRotativoOrigen.getFlgVariasUnitarias())){
					if (pdaArticulo.getCodArt().equals(inventarioRotativoOrigen.getCodArticulo())){
						inventarioRotativo.setCodArticulo(inventarioRotativoOrigen.getCodArticulo());
					}
					else{
						inventarioRotativo.setCodArticulo(pdaArticulo.getCodArtOrig());
					}
				}
				else{
					inventarioRotativo.setCodArticulo(pdaArticulo.getCodArtOrig());
				}
				inventarioRotativo.setCodArticuloRela(pdaArticulo.getCodArt());
				inventarioRotativo.setCodCentro(codCentro);
				
				if (null != pdaStock.getOrigenGISAE() && pdaStock.getOrigenGISAE().equals("SI")){
					inventarioRotativo.setCodMac("GISAE");
					
				} else {
					inventarioRotativo.setCodMac(mac);
				}

				inventarioRotativo = this.inventarioRotativoService.findOne(inventarioRotativo);

				if (inventarioRotativo != null){
					if (Constantes.INVENTARIO_LIBRE_CAMARA_ALMACEN.equals(pdaStock.getOrigenInventario())){
						pdaArticulo.setUnidades(inventarioRotativo.getCamaraBandeja()!=null?BigInteger.valueOf(inventarioRotativo.getCamaraBandeja()):new BigInteger("0"));
						pdaArticulo.setStock(Utilidades.convertirDoubleAString(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"),"###0.00").replace(",", "."));
					}else if (Constantes.INVENTARIO_LIBRE_SALA_VENTA.equals(pdaStock.getOrigenInventario())){    			
						pdaArticulo.setUnidades(inventarioRotativo.getSalaBandeja()!=null?BigInteger.valueOf(inventarioRotativo.getSalaBandeja()):new BigInteger("0"));
						pdaArticulo.setStock(Utilidades.convertirDoubleAString(inventarioRotativo.getSalaStock()!=null?inventarioRotativo.getSalaStock():new Double("0"),"###0.00").replace(",", "."));
					}else{
						pdaArticulo.setUnidades(new BigInteger("0"));
						pdaArticulo.setStock(Utilidades.convertirDoubleAString(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"),"###0.00").replace(",", "."));		
					}
				}else{
					pdaArticulo.setUnidades(new BigInteger("0"));
					pdaArticulo.setStock(Utilidades.convertirDoubleAString(new Double("0"),"###0.00").replace(",", "."));		
				}
			}	
		}
	}
}