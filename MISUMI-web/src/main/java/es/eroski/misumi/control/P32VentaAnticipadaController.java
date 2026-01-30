package es.eroski.misumi.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VentaAnticipada;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VentaAnticipadaService;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
@RequestMapping("/ventaAnticipada")
public class P32VentaAnticipadaController {

	@Resource
	private MessageSource messageSource;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private VentaAnticipadaService ventaAnticipadaService;

	private static Logger logger = Logger
			.getLogger(P32VentaAnticipadaController.class);

	@RequestMapping(value = "/getVenta", method = RequestMethod.POST)
	public @ResponseBody
	VentaAnticipada processForm(@RequestBody VentaAnticipada vAnticipada,
			HttpSession session) {
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		User user = (User) session.getAttribute("user");
		try {
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(vAnticipada.getCodArt());
			vDatosDiarioArt = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
			if (null == vDatosDiarioArt) {
				vAnticipada.setDescError(this.messageSource.getMessage(
						"pda_p12_datosReferencia.noExisteReferencia", null,
						locale));

			} else {
				vAnticipada.setCodCentro(user.getCentro().getCodCentro());
				vAnticipada = this.ventaAnticipadaService
						.obtenerVentaAnticipada(vAnticipada);
				vAnticipada.setDescArt(vDatosDiarioArt.getDescripArt());

				if (null == vAnticipada.getFechaGen()) {
					pdaError.setDescError(this.messageSource.getMessage(
							"pda_p25_ventaAnticipada.nofechaPrevision", null,
							locale));

				} else {
					vAnticipada.setFechaGenFormated(this
							.obtenerFechaTexto(vAnticipada.getFechaGen()));
					vAnticipada.setFlgEnvioAC("N");

				}
			}

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return vAnticipada;
	}

	@RequestMapping(value = "/saveVenta", method = RequestMethod.POST)
	public void saveVenta(@RequestBody VentaAnticipada vAnticipada,
			HttpServletResponse response, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		vAnticipada.setCodCentro(user.getCentro().getCodCentro());
		try {
			this.ventaAnticipadaService.guardarVentaAnticipada(vAnticipada);

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	private String obtenerFechaTexto(String fecha) {
		StringBuffer fechaFormated = new StringBuffer();
		Date f = Utilidades.convertirStringAFecha(fecha);
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("EEEE");
		fechaFormated.append(df.format(f).toUpperCase()).append(" ");
		df.applyPattern("dd");
		fechaFormated.append(df.format(f)).append(" de ");
		df.applyPattern("MMMM");
		fechaFormated.append(df.format(f).toUpperCase());
		return fechaFormated.toString();
	}

}