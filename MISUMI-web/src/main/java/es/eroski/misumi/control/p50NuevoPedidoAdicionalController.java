package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.DiaVentasUltimasOfertas;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.PedidoAdicionalAyuda1;
import es.eroski.misumi.model.PedidoAdicionalAyuda2;
import es.eroski.misumi.model.VOfertaPaAyuda;
import es.eroski.misumi.service.iface.HistoricoUnidadesVentaService;
import es.eroski.misumi.service.iface.HistoricoVentaMediaService;
import es.eroski.misumi.service.iface.VOfertaPaAyudaService;
import es.eroski.misumi.util.Utilidades;


@Controller
@RequestMapping("/nuevoPedidoAdicional")
public class p50NuevoPedidoAdicionalController {
	
	private static Logger logger = Logger.getLogger(p50NuevoPedidoAdicionalController.class);
	
	@Autowired
	private VOfertaPaAyudaService vOfertaPaAyudaService;
	
	@Autowired
	private HistoricoUnidadesVentaService historicoUnidadesVentaService;
	
	@Autowired
	private HistoricoVentaMediaService historicoVentaMediaService;

	@Resource
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model,
			@RequestParam(required = false, defaultValue = "") String pestanaOrigen,
			@RequestParam(required = false, defaultValue = "") String codArea,
			@RequestParam(required = false, defaultValue = "") String codSeccion,
			@RequestParam(required = false, defaultValue = "") String codCategoria,
			@RequestParam(required = false, defaultValue = "") String referencia) {

		model.put("pestanaOrigen", pestanaOrigen);
		model.put("codArea", codArea);
		model.put("codSeccion", codSeccion);
		model.put("codCategoria", codCategoria);
		model.put("referencia", referencia);

		return "p50_nuevoPedidoAdicional";
	}
	
	@RequestMapping(value="/loadAyuda1", method = RequestMethod.POST)
	public @ResponseBody List<PedidoAdicionalAyuda1> loadAyuda1(
			@RequestBody VOfertaPaAyuda vVOfertaPaAyuda, 
			@RequestParam(value = "sumVentaAnticipada", required = false, defaultValue = "0") String sumVentaAnticipada,
			HttpSession session, HttpServletResponse response) throws Exception {
		
	        List<VOfertaPaAyuda> list = null;
	        List<PedidoAdicionalAyuda1> listPay1 = null;
	        try {
	        	if (null == vVOfertaPaAyuda.getCodArt()){
					list = new ArrayList<VOfertaPaAyuda>();
				} else {
					list = this.vOfertaPaAyudaService.findCountNOVigentes(vVOfertaPaAyuda, 4);
				}
	         } catch (Exception e) {
	        	 logger.error("######################## Error  ############################");
	     		logger.error("Referencia: " + vVOfertaPaAyuda.getCodArt() );
	     		logger.error("############################################################");
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	throw e;
	        } 
	       	
	        try {
				if (list != null) {
			        listPay1 = new ArrayList<PedidoAdicionalAyuda1>();
					for(VOfertaPaAyuda pAy:list){
						
						PedidoAdicionalAyuda1 aux=new PedidoAdicionalAyuda1();
						aux.setcOferta(pAy.getAnoOferta()+"-"+pAy.getNumOferta());
						aux.setcPeriodo(formatearF(pAy.getFechaIni())+" "+formatearF(pAy.getFechaFin()));
						//Se rellena el string dependiendo del tipo de oferta que se a�ade en el desplegable
						rellenarTipoOfertaString(pAy,aux);
						aux.setFechaIniPeriodo(Utilidades.formatearFecha(pAy.getFechaIni()));
						aux.setFechaFinPeriodo(Utilidades.formatearFecha(pAy.getFechaFin()));
						aux.setcPvp(pAy.getPrecio());	
						
						//Se obtienen las cantidades de los 3 siguientes días al inicio de la oferta
						ObtenerCantidadesDia(pAy, aux, sumVentaAnticipada);
						listPay1.add(aux);
					}		
				} else {
					return new ArrayList<PedidoAdicionalAyuda1>();
				}
	        } catch (Exception e) {
	        	logger.error("######################## Error  ############################");
	    		logger.error("Referencia: " + vVOfertaPaAyuda.getCodArt() );
	    		logger.error("############################################################");
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	throw e;
	        }
		 return listPay1;
		 
	}
	
	@RequestMapping(value="/loadAyuda2", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalAyuda2 loadAyuda2(
			@RequestBody PedidoAdicionalAyuda2 vPedidoAdicionalAyuda2,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			PedidoAdicionalAyuda2 pedidoAdicionalAyuda2 = vPedidoAdicionalAyuda2;			
			pedidoAdicionalAyuda2.setHistoricoVentaMedia(obtenerHistoricoVentaMedia(pedidoAdicionalAyuda2));

			return pedidoAdicionalAyuda2;
		} catch (Exception e) {
//		    logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		     throw e;
		}
	}
	
	private void ObtenerCantidadesDia(VOfertaPaAyuda pAy, PedidoAdicionalAyuda1 pa,String sumVentaAnticipada) {
		List<DiaVentasUltimasOfertas> listaFechasVentasMedias = null;
	    Float total = new Float(0);
		try {
			//Busco en BBDD la lista de fechas con sus ventas medias
		    listaFechasVentasMedias = this.historicoUnidadesVentaService.findDateListMediaSales(
		    		pAy.getCodCentro().toString(), 
		    		pAy.getCodArt().toString(), 
		    		Utilidades.formatearFecha(pAy.getFechaIni()), 
		    		Utilidades.formatearFecha(pAy.getFechaFin()),
		    		sumVentaAnticipada);
		    
		    pa.setListaDiaVentasUltimasOfertas(listaFechasVentasMedias);
		    //Día 1
		    /*if (listaFechasVentasMedias != null && listaFechasVentasMedias.size() > 0) {
		    	Map<String,Object> m = listaFechasVentasMedias.get(0);
				pa.setD1(Utilidades.convertirStringAFecha((String) m.get("FECHA_VENTA")));
				pa.setcVD1( ((BigDecimal) m.get("TOTAL_VENTAS")).floatValue() );
		    }
		    //Día 2
		    if (listaFechasVentasMedias != null && listaFechasVentasMedias.size() > 1) {
		    	Map<String,Object> m = listaFechasVentasMedias.get(1);
				pa.setD2(Utilidades.convertirStringAFecha((String) m.get("FECHA_VENTA")));
				pa.setcVD2( ((BigDecimal) m.get("TOTAL_VENTAS")).floatValue() );
		    }
		    //Día 3
		    if (listaFechasVentasMedias != null && listaFechasVentasMedias.size() > 2) {
		    	Map<String,Object> m = listaFechasVentasMedias.get(2);
				pa.setD3(Utilidades.convertirStringAFecha((String) m.get("FECHA_VENTA")));
				pa.setcVD3( ((BigDecimal) m.get("TOTAL_VENTAS")).floatValue() );
		    }*/
		    //Total Ventas
		    if (listaFechasVentasMedias != null && listaFechasVentasMedias.size() > 0) {
		    	for (DiaVentasUltimasOfertas diaVentasUltimasOfertas : listaFechasVentasMedias){
		    		total +=  diaVentasUltimasOfertas.getcVD().floatValue() ;
	    		} 
		    }
			pa.setTotalVentas(total);
		} catch (Exception e) {
			logger.error("ObtenerCantidadesDia="+e.toString());
			e.printStackTrace();
		}
	}	
	
	private String formatearF(Date d){
		Locale locale = LocaleContextHolder.getLocale();
		return Utilidades.formatearFecha(Utilidades.formatearFecha(d), locale);
		
	}
	
	private HistoricoVentaMedia obtenerHistoricoVentaMedia(
			PedidoAdicionalAyuda2 pedidoAdicionalAyuda2) throws Exception {

		HistoricoVentaMedia historicoVentaMediaRes;
		HistoricoVentaMedia historicoVentaMedia = new HistoricoVentaMedia();
		historicoVentaMedia.setCodArticulo(pedidoAdicionalAyuda2.getCodArt());
		historicoVentaMedia.setCodLoc(pedidoAdicionalAyuda2.getCodCentro());
		
		//Filtro de fecha con el día de ayer
		Calendar fechaVenta = Calendar.getInstance();
		fechaVenta.add(Calendar.DAY_OF_YEAR, -1);
		historicoVentaMedia.setFechaVentaMedia(fechaVenta.getTime());
		
		historicoVentaMediaRes = this.historicoVentaMediaService.findOneAcumuladoRef(historicoVentaMedia);
		
		if (historicoVentaMediaRes != null){
			historicoVentaMediaRes.recalcularVentasMedia();
		}

		return historicoVentaMediaRes;
	}
	
	private void rellenarTipoOfertaString(VOfertaPaAyuda pAy, PedidoAdicionalAyuda1 aux ){
		if(pAy.getTipoOferta()==2 || pAy.getTipoOferta()==7){
			if(pAy.getUnidCobro()>100){
				Long auxCalculo=100-(pAy.getUnidCobro()-100);
				aux.setTipoOfertaString("2� Al "+auxCalculo.toString()+"%");
			}else{
				aux.setTipoOfertaString(pAy.getUnidVenta().toString()+ " X "+pAy.getUnidCobro().toString());
			}
		}else{
			aux.setTipoOfertaString(pAy.getDoftip());
		}
		
	}
}