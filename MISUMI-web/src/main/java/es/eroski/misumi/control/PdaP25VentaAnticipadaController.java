package es.eroski.misumi.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VentaAnticipada;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.model.pda.PdaVentaAnticipada;
import es.eroski.misumi.service.iface.EansService;
import es.eroski.misumi.service.iface.VentaAnticipadaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class PdaP25VentaAnticipadaController extends pdaConsultasController{

	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private EansService eansService;
	
	@Autowired
	private VentaAnticipadaService ventaAnticipadaService;

	private static Logger logger = Logger.getLogger(pdaP23LimpiarController.class);

	@RequestMapping(value = "/pdaP25VentaAnticipada",method = RequestMethod.GET)
	public String showForm(ModelMap model,@Valid final Long codArt,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		// Validar objeto
		String resultado = "pda_p25_ventaAnticipada";
		try {
			
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			//model.addAttribute("pdaVentaAnt", pdaVentaAnt);
			//Obtener Datos venta Anticipada
			//Obtener Dia Siguiente
			

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	@RequestMapping(value = "/pdaP25VentaAnticipada",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, ModelMap model,
			HttpSession session, HttpServletRequest request, @Valid final PdaVentaAnticipada pdaVentaAnt,
			HttpServletResponse response) {
		String resultado = "pda_p25_ventaAnticipada";
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		User user = (User) session.getAttribute("user");
		try {
			
			if (null != pdaVentaAnt.getActionReset()){
			
				PdaDatosCabecera pdaDatCab = new PdaDatosCabecera();
				model.addAttribute("pdaDatosCab", pdaDatCab);
			}else if (null != pdaVentaAnt.getActionSave()){
				Double cantidad = null;
				try {
					cantidad = Double.parseDouble(pdaVentaAnt.getCantidad().replace(',', '.'));
					if (cantidad.longValue() > new Long(99999)){
						throw new NumberFormatException();
					}
					VentaAnticipada ventaAnticipada = new VentaAnticipada();
					ventaAnticipada.setCodCentro(user.getCentro().getCodCentro());
					ventaAnticipada.setCodArt(pdaVentaAnt.getCodArt());
					ventaAnticipada.setDescArt(pdaVentaAnt.getDescArt());
					ventaAnticipada.setExiste(pdaVentaAnt.getExiste());
					ventaAnticipada.setCantidad(cantidad);
					ventaAnticipada.setFechaGen(pdaVentaAnt.getFechaGen());
					ventaAnticipada.setFlgEnvioAC(pdaVentaAnt.getFlgEnvio());
					this.ventaAnticipadaService.guardarVentaAnticipada(ventaAnticipada);
					VDatosDiarioArt vDatosDiarioArt =  obtenerDiarioArt(pdaVentaAnt.getCodArt());
					pdaDatosCab.setCodArtCab(String.valueOf(vDatosDiarioArt.getCodArt()));
					pdaDatosCab.setDescArtCab(vDatosDiarioArt.getDescripArt());
					pdaVentaAnt.setFecha(this.obtenerFechaTexto(ventaAnticipada.getFechaGen()));
					pdaVentaAnt.setExiste(true);
					pdaVentaAnt.setCantidad(Utilidades.convertirDoubleAString(ventaAnticipada.getCantidad().doubleValue(),"###0.000").replace(',', '.'));
					//resultado = "pda_p26_guardarVentaAnticipada";
					//Se cambia para que el mensaje de guardado se muestre en la misma pantalla
					pdaVentaAnt.setGuardadoCorrecto("S");
					model.addAttribute("pdaVentaAnt", pdaVentaAnt);
					
				} catch (NumberFormatException nfe) {
					if (pdaVentaAnt.getCantidad().isEmpty()){
						pdaVentaAnt.setEsError(this.messageSource.getMessage("pda_p25_ventaAnticipada.unidadesVacio", null, locale));
					} else if (null != cantidad && cantidad.longValue() > new Long(99999)) {
						pdaVentaAnt.setEsError(this.messageSource.getMessage("pda_p25_ventaAnticipada.unidadesRango", null, locale));
					} else {
						pdaVentaAnt.setEsError(this.messageSource.getMessage("pda_p25_ventaAnticipada.formatoUnidades", null, locale));
					}
					VDatosDiarioArt vDatosDiarioArt =  obtenerDiarioArt(pdaVentaAnt.getCodArt());
					pdaDatosCab.setCodArtCab(String.valueOf(vDatosDiarioArt.getCodArt()));
					pdaDatosCab.setDescArtCab(vDatosDiarioArt.getDescripArt());
					model.addAttribute("pdaVentaAnt", pdaVentaAnt);
				}
				
				model.addAttribute("pdaDatosCab", pdaDatosCab);
			} else {
				if (pdaDatosCab.getCodArtCab() != null && !pdaDatosCab.getCodArtCab().equals(""))
				{
					
					//Llamamos al m�todo que nos devuelve la referencia, con los controles, 
					//por si se trata de balanza, etiqueta propia de Eroski, de la tabla EANS o c�digo de referencia normal.
					PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
					String codigoError = pdaArticulo.getCodigoError();
					
					if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN))
					{
						pdaError.setDescError(this.messageSource.getMessage(
								"pda_p12_datosReferencia.noExisteReferencia", null, locale));
						model.addAttribute("pdaError", pdaError);
						model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
						logger.info("PDA Referencia erronea");
						resultado =  "pda_p03_vant_showMessage";
					} else {
					
					pdaDatosCab.setCodArtCab(pdaArticulo.getCodArt().toString());

					VDatosDiarioArt vDatosDiarioArt =  obtenerDiarioArt(new Long(pdaDatosCab.getCodArtCab()));
					if (null == vDatosDiarioArt)
					{
						pdaError.setDescError(this.messageSource.getMessage(
								"pda_p12_datosReferencia.noExisteReferencia", null, locale));
						model.addAttribute("pdaError", pdaError);
						model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
						logger.info("PDA Referencia erronea");
						resultado =  "pda_p03_vant_showMessage";
					} else {
						VentaAnticipada ventaAnticipada = new VentaAnticipada();
						ventaAnticipada.setCodCentro(user.getCentro().getCodCentro());
						ventaAnticipada.setCodArt(new Long(pdaDatosCab.getCodArtCab()));
						pdaDatosCab.setDescArtCab(vDatosDiarioArt.getDescripArt());
						ventaAnticipada = this.ventaAnticipadaService.obtenerVentaAnticipada(ventaAnticipada);
					//PdaVentaAnticipada pdaVentAnt = new PdaVentaAnticipada();
						pdaVentaAnt.setCodArt(ventaAnticipada.getCodArt());
						pdaVentaAnt.setDescArt(vDatosDiarioArt.getDescripArt());
						pdaVentaAnt.setExiste(ventaAnticipada.getExiste());
						if (null != ventaAnticipada.getCantidad() ){
							pdaVentaAnt.setCantidad(Utilidades.convertirDoubleAString(ventaAnticipada.getCantidad().doubleValue(),"###0.000").replace(',', '.'));
						}
						pdaVentaAnt.setFechaGen(ventaAnticipada.getFechaGen());
						if (null == ventaAnticipada.getFechaGen()){
							pdaError.setDescError(this.messageSource.getMessage("pda_p25_ventaAnticipada.nofechaPrevision", null, locale));
							model.addAttribute("pdaError", pdaError);
							model.addAttribute("pdaDatosCab", pdaDatosCab);
							resultado =  "pda_p03_vant_showMessage";
						} else {
							pdaVentaAnt.setFecha(this.obtenerFechaTexto(ventaAnticipada.getFechaGen()));
							pdaVentaAnt.setFlgEnvio("N");
							model.addAttribute("pdaDatosCab", pdaDatosCab);
							model.addAttribute("pdaVentaAnt", pdaVentaAnt);
						}
					}
					}
				}
				else
				{
					//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
					pdaError.setDescError(this.messageSource.getMessage(
							"pda_p21_sfm.referenciaVacia", null, locale));
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					model.addAttribute("pdaError", pdaError);
					logger.info("PDA Referencia no introducida");
					resultado =  "pda_p03_vant_showMessage";
				}
				
				
				
			}
			

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	
	private PdaVentaAnticipada obtenerReferenciaDesdeEan(Long codartEan) throws Exception{
		
		Locale locale = LocaleContextHolder.getLocale();
		PdaVentaAnticipada pdaVentaAnt = new PdaVentaAnticipada();
		Long codArt = this.eansService.obtenerReferenciaEan(codartEan);
		if (codArt == null)
		{
			pdaVentaAnt.setEsError(this.messageSource.getMessage(
					"pda_p12_datosReferencia.noExisteReferencia", null, locale));
		}else{
			pdaVentaAnt.setCodArt(codArt);
		}
		
		return pdaVentaAnt;
	}
	
	private String obtenerFechaTexto(String fecha){
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