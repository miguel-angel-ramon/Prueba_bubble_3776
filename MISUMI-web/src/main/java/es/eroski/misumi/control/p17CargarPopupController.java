package es.eroski.misumi.control;

import java.util.GregorianCalendar;
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

import es.eroski.misumi.model.HistoricoUnidadesVenta;
import es.eroski.misumi.model.HistoricoVentaUltimoMes;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.HistoricoUnidadesVentaService;
import es.eroski.misumi.service.iface.HistoricoVentaUltimoMesService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;

@Controller
@RequestMapping("/cargarPopup")
public class p17CargarPopupController {
	
	private static Logger logger = Logger.getLogger(p17CargarPopupController.class);

	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private HistoricoVentaUltimoMesService historicoVentaUltimoMesService;
	
	@Autowired
	private HistoricoUnidadesVentaService historicoUnidadesVentaService;
	
	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
		
	@RequestMapping(value="/loadDatosPopupVentas", method = RequestMethod.POST)
	public @ResponseBody HistoricoVentaUltimoMes loadDatosPopupVentas(
			@RequestBody HistoricoVentaUltimoMes historicoVentaUltimoMes,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			HistoricoVentaUltimoMes historicoVentaUltimoMesRes;
			
			//Formatear fecha de pantalla que vendrá en formato DDMMYYYY
			String fechaPantalla = historicoVentaUltimoMes.getFechaVentaDDMMYYYY();
			int dia = Integer.valueOf(fechaPantalla.substring(0, 2));//new Integer(fechaPantalla.substring(0, 2)).intValue();
			int mes =Integer.valueOf(fechaPantalla.substring(2, 4)) - 1; //new Integer(fechaPantalla.substring(2, 4)).intValue() - 1;
			int anyo =Integer.valueOf(fechaPantalla.substring(4));//new Integer(fechaPantalla.substring(4)).intValue();
			GregorianCalendar diaVenta = new GregorianCalendar();
			diaVenta.set(anyo, mes, dia);
			
			historicoVentaUltimoMes.setFechaVenta(diaVenta.getTime());
			//Comprobar si VentaPromocional
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(historicoVentaUltimoMes.getCodArticulo());
			relacionArticulo.setCodCentro(historicoVentaUltimoMes.getCodLoc());
			List<Long> referencias = this.relacionArticuloService.findAll(relacionArticulo);
			if (referencias.isEmpty()){
				relacionArticulo.setCodArt(null);
				relacionArticulo.setCodArtRela(historicoVentaUltimoMes.getCodArticulo());
				referencias = this.relacionArticuloService.findAll(relacionArticulo);
			}
			if (!referencias.isEmpty()){
				historicoVentaUltimoMes.setReferencias(referencias);
			}
			
			historicoVentaUltimoMesRes = this.historicoVentaUltimoMesService.findOne(historicoVentaUltimoMes);
			
			if (historicoVentaUltimoMesRes != null){
				historicoVentaUltimoMesRes.recalcularTotalVentas();
			}

			return historicoVentaUltimoMesRes;

		} catch (Exception e) {
//		    logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	@RequestMapping(value="/loadDatosPopupVentasUltimasOfertas", method = RequestMethod.POST)
	public @ResponseBody HistoricoUnidadesVenta loadDatosPopupVentasUltimasOfertas(
			@RequestBody HistoricoUnidadesVenta historicoUnidadesVenta,
			@RequestParam(required = false, defaultValue ="0") String sumVentaAnticipada,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			HistoricoUnidadesVenta historicoUnidadesVentaRes;
			
			//Formatear fecha de pantalla que vendrá en formato DDMMYYYY
			String fechaPantalla = historicoUnidadesVenta.getFechaVentaDDMMYYYY();
			int dia = Integer.valueOf(fechaPantalla.substring(0, 2));//new Integer(fechaPantalla.substring(0, 2)).intValue();
			int mes =Integer.valueOf(fechaPantalla.substring(2, 4)) - 1; //new Integer(fechaPantalla.substring(2, 4)).intValue() - 1;
			int anyo =Integer.valueOf(fechaPantalla.substring(4));//new Integer(fechaPantalla.substring(4)).intValue();
			GregorianCalendar diaVenta = new GregorianCalendar();
			diaVenta.set(anyo, mes, dia);
			
			historicoUnidadesVenta.setFechaVenta(diaVenta.getTime());
			historicoUnidadesVenta.setCodArticulo(historicoUnidadesVenta.getCodArticulo());
			historicoUnidadesVenta.setCodLoc(historicoUnidadesVenta.getCodLoc());
			
			//Si el centro es de caprabo, buscar también con referencia eroski.
			User user = (User) session.getAttribute("user");
			if (utilidadesCapraboService.esCentroCaprabo(user.getCentro().getCodCentro(), user.getCode())){
				historicoUnidadesVenta.setCodArticulo(utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), historicoUnidadesVenta.getCodArticulo()));
			}
			//historicoUnidadesVentaRes = this.historicoUnidadesVentaService.findOne(historicoUnidadesVenta);
			historicoUnidadesVentaRes = this.historicoUnidadesVentaService.findVentaUltimaOferta(historicoUnidadesVenta,sumVentaAnticipada);
			
			return historicoUnidadesVentaRes;
		} catch (Exception e) {
//		    logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	
}
