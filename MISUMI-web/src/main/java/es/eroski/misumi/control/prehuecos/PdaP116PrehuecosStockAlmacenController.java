package es.eroski.misumi.control.prehuecos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.prehuecos.iface.PrehuecosStockAlmacenService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class PdaP116PrehuecosStockAlmacenController {
	
	private final String STOCK_ALMACEN = "/pda/prehuecos/pda_p116_stockAlmacen";
	
	private static Logger logger = Logger.getLogger(PdaP116PrehuecosStockAlmacenController.class);
	
	@Autowired
	PrehuecosStockAlmacenService prehuecosStockAlmacenService;
	
	@Autowired
	private StockTiendaService stockTiendaService;

	@RequestMapping(value = "/pdaP116PrehuecosStockAlmacen", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		int pagActual=1;
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		List<StockAlmacen> listaRef = new ArrayList<StockAlmacen>();
		try{
			listaRef = prehuecosStockAlmacenService.getReferenciasPrehuecosStockAlmacen(codCentro, user.getMac(),pagActual);
			listaRef = cargarStocks(listaRef,codCentro,session);
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		if(listaRef!=null && listaRef.size()>0){
			model.addAttribute("fechaStockAlmacen", listaRef.get(0).getFecha());
			model.addAttribute("pagTotal", listaRef.get(0).getPagTotal());
		}else{
			model.addAttribute("fechaStockAlmacen", "");
			model.addAttribute("pagTotal", 0);
		}
		
		model.addAttribute("pagActual", pagActual);
		
		model.addAttribute("listaRef", listaRef);
		return STOCK_ALMACEN;
	}
	
	@RequestMapping(value = "/pdaP116PrehuecosStockAlmacen", method = RequestMethod.POST)
	public String processForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String codArt = request.getParameter("codArt");
		redirectAttributes.addAttribute("codArt", codArt);
		return "redirect:pdaP116PrehuecosStockAlmacen.do";
	}

	@RequestMapping(value = "/pdaP116Paginar", method = RequestMethod.GET)
	public String pdaP116Paginar(ModelMap model
								, HttpSession session,
								@Valid final int pagActual,
								@Valid final String pagTotal) {

		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		List<StockAlmacen> listaRef = new ArrayList<StockAlmacen>();
		try{
			listaRef = prehuecosStockAlmacenService.getReferenciasPrehuecosStockAlmacen(codCentro, user.getMac(),pagActual);
			listaRef = cargarStocks(listaRef,codCentro,session);
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		if(listaRef!=null && listaRef.size()>0){
			model.addAttribute("fechaStockAlmacen", listaRef.get(0).getFecha());
			model.addAttribute("pagTotal", listaRef.get(0).getPagTotal());
		}else{
			model.addAttribute("fechaStockAlmacen", "");
			model.addAttribute("pagTotal", 0);
		}
		
		model.addAttribute("pagActual", pagActual);
		
		model.addAttribute("listaRef", listaRef);
		return STOCK_ALMACEN;
	}
	
	private List<StockAlmacen> cargarStocks(List<StockAlmacen> listaRef, Long codCentro, HttpSession session) throws Exception{
		List<StockAlmacen> output = new ArrayList<StockAlmacen>();
		// CARGAR STOCKS
		// 1) sacar una array con las referencias
		Long[] codArticulos = new Long[listaRef.size()];
		for (int i=0; i<listaRef.size(); i++) {
			final StockAlmacen item = listaRef.get(i);
			final Long ref = Long.valueOf(item.getCodArt());
			codArticulos[i] = ref;
		}
		// 2) consultar el WS de stockTienda para sacar los stocks de esas referencias
		final Map<Long, Double> stocks = obtenerStocks(codArticulos, codCentro, session);
		
		// 3) modificar el listado para establecer los stocks devueltos por el WS
		for(StockAlmacen item : listaRef) {
			final Long ref = Long.valueOf(item.getCodArt());
			if (stocks.containsKey(ref)){
				item.setStock(formatearStock(stocks.get(ref)));
				output.add(item);
			}
		}
		return output;
	}
	
	private String formatearStock(Double stock) throws Exception {
		BigDecimal bd = new BigDecimal(String.valueOf(stock));
		long iPart = bd.longValue();
		double p_dec= (stock - iPart);
		if(p_dec==0){
			return String.valueOf(iPart);
		}else{
			return String.format("%.1f", stock);
		}
	}
	
	private Map<Long,Double> obtenerStocks(Long[] codArticulos, Long centro,  HttpSession session) throws Exception {

		Map<Long,Double> out = new HashMap<Long,Double>();
		
		try {
			final ConsultarStockRequestType requestType = new ConsultarStockRequestType();
			requestType.setCodigoCentro(BigInteger.valueOf(centro));
			requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
			
			final BigInteger[] listaRef = new BigInteger[codArticulos.length];
			for (int i=0; i<codArticulos.length; i++){
				listaRef[i] = BigInteger.valueOf(codArticulos[i]);
			}
			
			requestType.setListaCodigosReferencia(listaRef);
			
			final ConsultarStockResponseType responseType = this.stockTiendaService.consultaStock(requestType,session);
			if (responseType != null && responseType.getCodigoRespuesta().equals("OK")){
				final ReferenciaType[] listaRefResponse = responseType.getListaReferencias();
				
				for (int i=0; i<listaRefResponse.length; i++){
					final ReferenciaType referencia = listaRefResponse[i];
					final Long codRef = referencia.getCodigoReferencia().longValue();
					Double stock = null;
					if (referencia.getCodigoError().equals(new BigInteger("0"))){
						if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
							stock = referencia.getBandejas().doubleValue();
						} else {
							stock = referencia.getStock().doubleValue();
						}
					} else {
						stock = referencia.getStock().doubleValue();
					}
					out.put(codRef, stock);
				}
			} else {
				for (Long codArticulo : codArticulos){
					out.put(codArticulo, null);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		
		return out;
	}

}
