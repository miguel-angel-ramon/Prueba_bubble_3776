package es.eroski.misumi.control;

import java.util.Date;
import java.util.List;
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

import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.TOptima;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaRevisionHuecos;
import es.eroski.misumi.service.iface.EansService;
import es.eroski.misumi.service.iface.TOptimaService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VConfirmacionNoServidoService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class PdaP47RevisionHuecosController extends pdaConsultasController{

	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private EansService eansService;
	
	@Autowired
	private TOptimaService tOptimaService;
	
	@Autowired
	private VConfirmacionNoServidoService vConfirmacionNoServidoService;
	
	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	
	private static Logger logger = Logger.getLogger(PdaP47RevisionHuecosController.class);

	@RequestMapping(value = "/pdaP47RevisionHuecos",method = RequestMethod.GET)
	public String showForm(ModelMap model,@Valid final Long codArt,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		// Validar objeto
		String resultado = "pda_p47_revisionHuecos";
		try {
			
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			PdaRevisionHuecos pdaRevisionHuecos = new PdaRevisionHuecos();
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			model.addAttribute("pdaRevisionHuecos", pdaRevisionHuecos);

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	@RequestMapping(value = "/pdaP47RevisionHuecos",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, ModelMap model,
			HttpSession session, HttpServletRequest request, @Valid final PdaRevisionHuecos pdaRevisionHuecos,
			HttpServletResponse response) {
		String resultado = "pda_p47_revisionHuecos";
		Locale locale = LocaleContextHolder.getLocale();
		User user = (User) session.getAttribute("user");
		try {
			
			if (null != pdaRevisionHuecos.getActionReset()){
				TOptima tOptima = new TOptima();
				tOptima.setCodMac(user.getMac());
				tOptima.setCodCentro(user.getCentro().getCodCentro());
				tOptima.setFechaGen(new Date());
				tOptima.setEnviado(false);
				List<TOptima> listHuecos = this.tOptimaService.getHuecos(tOptima);
				TOptima hueco = listHuecos.get(listHuecos.size() -1 );
				listHuecos.remove(hueco);

				try {
					this.tOptimaService.deleteHuecos(hueco);
					pdaRevisionHuecos.setBorradoCorrecto("OK");
					if (listHuecos.isEmpty()){
						pdaRevisionHuecos.setArea(null);
					}
				} catch (Exception e) {
					pdaRevisionHuecos.setBorradoCorrecto("KO");
					pdaRevisionHuecos.setDescError(e.getMessage());
				}
				
				PdaDatosCabecera pdaDatCab = new PdaDatosCabecera();
				
				pdaRevisionHuecos.setRefBorrada(hueco.getDescCompleta());
				model.addAttribute("pdaRevisionHuecos", pdaRevisionHuecos);
				model.addAttribute("pdaDatosCab", pdaDatCab);
				
			} else if (null != pdaRevisionHuecos.getActionConfirmarYes()  ){
				TOptima tOptima = new TOptima();
				tOptima.setCodMac(user.getMac());
				tOptima.setCodCentro(user.getCentro().getCodCentro());
				tOptima.setFechaGen(new Date());
				tOptima.setEnviado(false);
				this.tOptimaService.deleteHuecos(tOptima);
				String redirect = null;
				if (null != pdaRevisionHuecos.getActionVolver() && !pdaRevisionHuecos.getActionVolver().isEmpty()){
					redirect = "redirect:pdaP40SelFiabilidad.do";
				} else {
					redirect = "redirect:logout.do";
				}
				resultado = redirect;
				
			} else if (null != pdaRevisionHuecos.getActionConfirmarNo()  ){
				//Obtener ultima referencia
				TOptima tOptima = new TOptima();
				tOptima.setCodMac(user.getMac());
				tOptima.setCodCentro(user.getCentro().getCodCentro());
				tOptima.setFechaGen(new Date());
				tOptima.setEnviado(false);
				List<TOptima> listHuecos = this.tOptimaService.getHuecos(tOptima);
				TOptima last = listHuecos.get(listHuecos.size() -1);
				pdaDatosCab.setCodArtCab(String.valueOf(last.getCodArt()));
				pdaDatosCab.setDescArtCab(last.getDescripcion());
				pdaRevisionHuecos.setCodArt(last.getCodArt());
				pdaRevisionHuecos.setMostrarConf(false);
				pdaRevisionHuecos.setMostrarContenido(true);
				pdaRevisionHuecos.setActionVolver(null);
				pdaRevisionHuecos.setActionLogin(null);
				model.addAttribute("pdaRevisionHuecos", pdaRevisionHuecos);
				model.addAttribute("pdaDatosCab", pdaDatosCab);
			} else if (null != pdaRevisionHuecos.getActionVolver() && !pdaRevisionHuecos.getActionVolver().isEmpty()){
				String redirect = "redirect:pdaP40SelFiabilidad.do";
				TOptima tOptima = new TOptima();
				tOptima.setCodMac(user.getMac());
				tOptima.setCodCentro(user.getCentro().getCodCentro());
				tOptima.setFechaGen(new Date());
				tOptima.setEnviado(false);
				List<TOptima> listHuecos = this.tOptimaService.getHuecos(tOptima);
				if (listHuecos.isEmpty()){
					resultado = redirect;
				} else {
					pdaRevisionHuecos.setMostrarConf(true);
					pdaRevisionHuecos.setMostrarContenido(false);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					
				}
				
			} else if (null != pdaRevisionHuecos.getActionSave()  ){
				TOptima tOptima = new TOptima();
				tOptima.setCodMac(user.getMac());
				tOptima.setCodCentro(user.getCentro().getCodCentro());
				try {
					List<TOptima> errores = this.tOptimaService.sendGISAE(tOptima);
					if (errores.isEmpty()){
						pdaRevisionHuecos.setGisaeCorrecto("OK");
					} else {
						pdaRevisionHuecos.setGisaeCorrecto("KO");
						StringBuffer descError = new StringBuffer();
						for(TOptima opc : errores){
							descError.append(opc.getCodArt()).append(" ").append(opc.getDescError());
							descError.append("<BR>");
						}
						pdaRevisionHuecos.setDescError(descError.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
					pdaRevisionHuecos.setGisaeCorrecto("KO");
					pdaRevisionHuecos.setDescError(e.getMessage());
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
						pdaRevisionHuecos.setGuardadoCorrecto("KO");
						pdaRevisionHuecos.setDescError(this.messageSource.getMessage(
								"pda_p12_datosReferencia.noExisteReferencia", null, locale));
						
						model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
						logger.info("PDA Referencia erronea");
						
					} else {
					
					pdaDatosCab.setCodArtCab(pdaArticulo.getCodArt().toString());

					VDatosDiarioArt vDatosDiarioArt =  obtenerDiarioArt(new Long(pdaDatosCab.getCodArtCab()));
					if (null == vDatosDiarioArt)
					{
						pdaRevisionHuecos.setDescError(this.messageSource.getMessage(
								"pda_p12_datosReferencia.noExisteReferencia", null, locale));
						pdaRevisionHuecos.setGuardadoCorrecto("KO");
						model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
						logger.info("PDA Referencia erronea");
						
					} else {
						pdaDatosCab.setCodArtCab(String.valueOf(vDatosDiarioArt.getCodArt()));
						pdaDatosCab.setDescArtCab(vDatosDiarioArt.getDescripArt());
						model.addAttribute("pdaDatosCab", pdaDatosCab);
						
						
						TOptima tOptima = new TOptima();
						tOptima.setCodMac(user.getMac());
						tOptima.setCodCentro(user.getCentro().getCodCentro());
						tOptima.setFechaGen(new Date());
						tOptima.setEnviado(false);
						List<TOptima> listHuecos = this.tOptimaService.getHuecos(tOptima);
						if (listHuecos.isEmpty()){
							VAgruComerRef vAgruComerRef = new VAgruComerRef();
							vAgruComerRef.setNivel("I1");
							vAgruComerRef.setGrupo1(vDatosDiarioArt.getGrupo1());
							List<VAgruComerRef> listaRef = this.vAgruComerRefService.findAll(vAgruComerRef, null);
							VAgruComerRef vAgruRefAux = listaRef.get(0);
							pdaRevisionHuecos.setArea(vAgruRefAux.getDescripcion());
							 TOptima newOptima = new TOptima();
							 newOptima.setCodMac(user.getMac());
							 newOptima.setCodCentro(user.getCentro().getCodCentro());
							 newOptima.setArea(vDatosDiarioArt.getGrupo1());
							 newOptima.setCodArt(vDatosDiarioArt.getCodArt());
							 newOptima.setDescripcion(vDatosDiarioArt.getDescripArt());
							 newOptima.setFechaGen(new Date());
							 newOptima.setCreationDate(new Date());
							 SeguimientoMiPedido seguimientoPedido = new SeguimientoMiPedido();
							 seguimientoPedido.setCodArt(vDatosDiarioArt.getCodArt());
							 seguimientoPedido.setCodCentro(user.getCentro().getCodCentro());
							 newOptima.setNsr(this.vConfirmacionNoServidoService.checkNSR(seguimientoPedido));
							 newOptima.setEnviado(false);
							 this.tOptimaService.insertHueco(newOptima);
							 pdaRevisionHuecos.setGuardadoCorrecto("OK");
							 pdaRevisionHuecos.setCodArt(vDatosDiarioArt.getCodArt());
						} else {
							TOptima tOptimaAux = listHuecos.get(0);
							VAgruComerRef vAgruComerRef = new VAgruComerRef();
							vAgruComerRef.setNivel("I1");
							vAgruComerRef.setGrupo1(tOptimaAux.getArea());
							List<VAgruComerRef> listaRef = this.vAgruComerRefService.findAll(vAgruComerRef, null);
							VAgruComerRef vAgruRefAux = listaRef.get(0);
							pdaRevisionHuecos.setArea(vAgruRefAux.getDescripcion());
							if (vDatosDiarioArt.getGrupo1().equals(tOptimaAux.getArea())){
								
								TOptima newOptima = new TOptima();
								 newOptima.setCodMac(user.getMac());
								 newOptima.setCodCentro(user.getCentro().getCodCentro());
								 newOptima.setArea(vDatosDiarioArt.getGrupo1());
								 newOptima.setCodArt(vDatosDiarioArt.getCodArt());
								 newOptima.setDescripcion(vDatosDiarioArt.getDescripArt());
								 newOptima.setFechaGen(new Date());
								 newOptima.setCreationDate(new Date());
								 SeguimientoMiPedido seguimientoPedido = new SeguimientoMiPedido();
								 seguimientoPedido.setCodArt(vDatosDiarioArt.getCodArt());
								 seguimientoPedido.setCodCentro(user.getCentro().getCodCentro());
								 newOptima.setNsr(this.vConfirmacionNoServidoService.checkNSR(seguimientoPedido));
								 newOptima.setEnviado(false);
								 if (!listHuecos.contains(newOptima)){
									 logger.info("###################INSERT##################");
									 this.tOptimaService.insertHueco(newOptima);
								 }
								 pdaRevisionHuecos.setGuardadoCorrecto("OK");
								 pdaRevisionHuecos.setCodArt(vDatosDiarioArt.getCodArt());
							} else {
								Object[] args = {pdaRevisionHuecos.getArea()};
								pdaRevisionHuecos.setDescError(this.messageSource.getMessage(
										"pda_p47_revisionHuecos.areaIncorrecta", args, locale));
								pdaRevisionHuecos.setGuardadoCorrecto("KO");
								model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
							}
						}
						
						
					}
					}
				}
				else
				{
					//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
					pdaRevisionHuecos.setDescError(this.messageSource.getMessage(
							"pda_p21_sfm.referenciaVacia", null, locale));
					pdaRevisionHuecos.setGuardadoCorrecto("KO");
					model.addAttribute("pdaDatosCab", pdaDatosCab);

					logger.info("PDA Referencia no introducida");
				}
				pdaRevisionHuecos.setMostrarConf(false);
				pdaRevisionHuecos.setMostrarContenido(true);
				model.addAttribute("pdaRevisionHuecos", pdaRevisionHuecos);
				
				
			}
			

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	
	
}