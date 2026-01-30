package es.eroski.misumi.control;

import java.math.BigInteger;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.DevolucionAvisos;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.TMisMensajesService;
import es.eroski.misumi.service.iface.UserService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.validator.PdaUserValidator;

@Controller
@RequestMapping("/pdawelcome")
public class pdaWelcomeController {

	private static Logger logger = Logger.getLogger(pdaWelcomeController.class);

	@Resource
	private MessageSource messageSource;
	@Autowired
	private UserService userService;
	@Value("${tecnicRole}")
	private String tecnicRole;
	
	@Value("${adminRole}")
	private String adminRole;
	
	@Value("${consultaRole}")
	private String consultaRole;
	@Autowired
	private PdaUserValidator pdaUserValidator;
	@Autowired
	private TMisMensajesService tMisMensajes;



	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(pdaUserValidator);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processForm(@Valid final User user, BindingResult result,
			ModelMap model, HttpSession session,
			HttpServletResponse response) {

		// Se recoge la locale del ThreadLocal a nivel de metodo, para que sea
		// Thread Safe
		Locale locale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			logger.info("PDA Logging errors");
			return "pda_error";
		}

		try {
			if (user != null) {
				//Mirar si tiene acceso en la ddbb 
				try{
					User userAux=this.userService.findPda(user);
					if (userAux != null){
						if (userAux.getPerfil().toString().equals(this.tecnicRole) || userAux.getPerfil().toString().equals(this.adminRole)) {
							// Se trata de un usuario técnico y no debemos permitir
							// el acceso.
							ObjectError oe = new ObjectError("user", this.messageSource.getMessage("login.userTecnic", null, locale));
							result.addError(oe);
							logger.info("1 - pdawelcome - POST - PDA Invalid Login!");
							return "pda_error";
						} else {
							 if (userAux.getPerfil().toString().equals(this.consultaRole) && (userAux.getCentro() == null || userAux.getCentro().getCodCentro().equals(""))) {
								 
								// Se trata de un usuario de solo consulta y no tiene centro asgnado
								ObjectError oe = new ObjectError("user", this.messageSource.getMessage("login.userNotAuthorized", null, locale));
								result.addError(oe);
								logger.info("1 - pdawelcome - POST - PDA Invalid Login!");
								return "pda_error";
								 
							 } else {
							
								session.setAttribute("user", userAux);
								response.addCookie(new Cookie("sessionCookie", "active"));
								logger.info("Lenguage:" + LocaleContextHolder.getLocale().getLanguage());
								try {
									Boolean mostrarAvisosValidarCantidadesExtra = this.userService.mostrarAvisoValidarCantidadesExtra(userAux.getCentro().getCodCentro(), session);
									model.addAttribute("avisoValidarCantidadesExtra", mostrarAvisosValidarCantidadesExtra);
									Boolean mostrarAvisosDevoluciones = false;
									Boolean mostrarAvisosDevolucionesUrgente = false;
									if(userAux.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_26) != -1){
										DevolucionAvisos devolucionAvisos = this.userService.mostrarAvisoDevolucionesUrgente(userAux.getCentro().getCodCentro());
										if(devolucionAvisos != null && devolucionAvisos.getpCodError().longValue() == Constantes.DEVOLUCIONES_AVISOS_COD_ERROR_OK){
											mostrarAvisosDevoluciones=(Constantes.DEVOLUCIONES_AVISOS_FLG_SI.equals(devolucionAvisos.getFlgDevoluciones())?true:false);
											if(Constantes.DEVOLUCIONES_AVISOS_FLG_SI.equals(devolucionAvisos.getFlgDevoluciones())){
												boolean isUrgenteFrescos = false;
												boolean isUrgenteAlimentacion = false;
												boolean isUrgenteNoAli=false;
												isUrgenteFrescos = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_FRESCOS_SI.equals(devolucionAvisos.getFlgUrgenteFrescos());
												isUrgenteAlimentacion = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_ALI_SI.equals(devolucionAvisos.getFlgUrgenteAli());
												isUrgenteNoAli = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_NO_ALI_SI.equals(devolucionAvisos.getFlgUrgenteNoAli());
												mostrarAvisosDevolucionesUrgente=isUrgenteFrescos || isUrgenteAlimentacion || isUrgenteNoAli;
											}
										}
									}
									model.addAttribute("avisoDevoluciones", mostrarAvisosDevoluciones);
									model.addAttribute("avisoDevolucionesUrgente", mostrarAvisosDevolucionesUrgente);	
									
									Boolean mostrarAvisoCentro = this.tMisMensajes.existenAvisos(userAux,true);
									model.addAttribute("avisoCentro",mostrarAvisoCentro);
									return "pda_p11_welcome";
								} catch(Exception e){
									return "pda_p11_welcome";
								}
							 }
						}
					}
				} catch(Exception e){
					ObjectError oe = new ObjectError("user", this.messageSource.getMessage("login.userNotAuthorized", null, locale));
					result.addError(oe);
					logger.info("2 - pdawelcome - POST - PDA Invalid Login!");
					return "pda_error";
				}
			} else {
				ObjectError oe = new ObjectError("user", this.messageSource.getMessage("login.userIncorrect", null, locale));
				result.addError(oe);
				logger.info("3 - pdawelcome - POST - PDA Invalid Login!");
				return "pda_error";
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			ObjectError oe = new ObjectError("user", this.messageSource.getMessage("login.userNotAuthorized", null, locale));
			result.addError(oe);
			logger.info("1 - pdawelcome - POST - PDA Logging unexpected errors");
			return "pda_error";
		}
		return "pda_p11_welcome";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "usuario", required = false) String usuario,
			@RequestParam(value = "centro", required = false) String centro,
			@RequestParam(value = "mac", required = false) String mac,
			@RequestParam(value = "id", required = false) BigInteger id,
			@RequestParam(value = "buzon", required = false) String buzon,
			HttpSession session, HttpServletResponse response, ModelMap model) {
		Locale locale = LocaleContextHolder.getLocale();
		User user = new User();
		User userAux = null;
		
		try {
			model.addAttribute("user", user);
			if ((null != usuario && !usuario.isEmpty()) || (null != centro && !centro.isEmpty())) {
				
				if (null != usuario && !usuario.isEmpty()){
					user.setCode(usuario);
				}else if (null != centro && !centro.isEmpty()){
					Centro objCentro = new Centro(new Long(centro));
					
					user.setCentro(objCentro);
				}
				
				//Mirar si tiene acceso en la BBDD. 
				try{
					userAux=this.userService.findPda(user);
					if (userAux != null){
						if (userAux.getPerfil().toString().equals(this.tecnicRole) || userAux.getPerfil().toString().equals(this.adminRole)) {
							// Se trata de un usuario técnico y no debemos permitir
							// el acceso.
							logger.info("1 - pdawelcome - GET - PDA Invalid Login!");
							model.addAttribute("welcomeGetError", this.messageSource.getMessage("login.userNotAuthorized", null, locale));
							user.setCode("");
							return "pda_error";
						} else {
							//Se guarda la MAC para poder utilizarla en futuras consultas
							if (null != mac && !mac.isEmpty()){
								userAux.setMac(mac);
							}
							
							userAux.setBuzon(buzon);
							userAux.setIdPistola(id);
							
							
							session.setAttribute("user", userAux);
							response.addCookie(new Cookie("sessionCookie", "active"));
							try {
								Boolean mostrarAvisosValidarCantidadesExtra = this.userService.mostrarAvisoValidarCantidadesExtra(userAux.getCentro().getCodCentro(),session);
								model.addAttribute("avisoValidarCantidadesExtra", mostrarAvisosValidarCantidadesExtra);
								Boolean mostrarAvisosDevoluciones = false;
								Boolean mostrarAvisosDevolucionesUrgente = false;
								if (userAux.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_26) != -1){
									DevolucionAvisos devolucionAvisos = this.userService.mostrarAvisoDevolucionesUrgente(userAux.getCentro().getCodCentro());
									if (devolucionAvisos != null && devolucionAvisos.getpCodError().longValue() == Constantes.DEVOLUCIONES_AVISOS_COD_ERROR_OK){
										mostrarAvisosDevoluciones=(Constantes.DEVOLUCIONES_AVISOS_FLG_SI.equals(devolucionAvisos.getFlgDevoluciones())?true:false);
										if (Constantes.DEVOLUCIONES_AVISOS_FLG_SI.equals(devolucionAvisos.getFlgDevoluciones())){
											boolean isUrgenteFrescos = false;
											boolean isUrgenteAlimentacion = false;
											boolean isUrgenteNoAli=false;
											isUrgenteFrescos = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_FRESCOS_SI.equals(devolucionAvisos.getFlgUrgenteFrescos());
											isUrgenteAlimentacion = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_ALI_SI.equals(devolucionAvisos.getFlgUrgenteAli());
											isUrgenteNoAli = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_NO_ALI_SI.equals(devolucionAvisos.getFlgUrgenteNoAli());
											mostrarAvisosDevolucionesUrgente=isUrgenteFrescos || isUrgenteAlimentacion || isUrgenteNoAli;
										}
									}
								}
								model.addAttribute("avisoDevoluciones", mostrarAvisosDevoluciones);
								model.addAttribute("avisoDevolucionesUrgente", mostrarAvisosDevolucionesUrgente);	
								
								Boolean mostrarAvisoCentro = this.tMisMensajes.existenAvisos(userAux,true);
								model.addAttribute("avisoCentro",mostrarAvisoCentro);
								return "pda_p11_welcome";
							} catch(Exception e){
								return "pda_p11_welcome";
							}
						}
					}
				} catch(Exception e){
					logger.debug(StackTraceManager.getStackTrace(e));
					logger.info("2 - pdawelcome - GET - PDA Invalid Login!");
					model.addAttribute("welcomeGetError", this.messageSource.getMessage("login.userNotAuthorized", null, locale));
					user.setCode("");
					return "pda_error";
				}
			} else {
				user = (User) session.getAttribute("user");
				
				if (user == null) {
					logger.info("3 - pdawelcome - GET - PDA Invalid Login!");
					return "pda_error";
				} else {
					userAux=this.userService.findPda(user);
					if (user.getPerfil().toString().equals(this.tecnicRole) || userAux.getPerfil().toString().equals(this.adminRole)) {
						logger.info("4 - pdawelcome - GET - PDA Invalid Login!");
						user.setCode("");
						return "pda_error";
					}
				}
			}
			
			if (userAux != null){
				try {
					Boolean mostrarAvisosValidarCantidadesExtra = this.userService.mostrarAvisoValidarCantidadesExtra(userAux.getCentro().getCodCentro(), session);
					model.addAttribute("avisoValidarCantidadesExtra", mostrarAvisosValidarCantidadesExtra);
					Boolean mostrarAvisosDevoluciones = false;
					Boolean mostrarAvisosDevolucionesUrgente = false;
					if(userAux.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PDA_DEVOLUCIONES_26) != -1){
						DevolucionAvisos devolucionAvisos = this.userService.mostrarAvisoDevolucionesUrgente(userAux.getCentro().getCodCentro());
						if(devolucionAvisos != null && devolucionAvisos.getpCodError().longValue() == Constantes.DEVOLUCIONES_AVISOS_COD_ERROR_OK){
							mostrarAvisosDevoluciones=(Constantes.DEVOLUCIONES_AVISOS_FLG_SI.equals(devolucionAvisos.getFlgDevoluciones())?true:false);
							if(Constantes.DEVOLUCIONES_AVISOS_FLG_SI.equals(devolucionAvisos.getFlgDevoluciones())){
								boolean isUrgenteFrescos = false;
								boolean isUrgenteAlimentacion = false;
								boolean isUrgenteNoAli=false;
								isUrgenteFrescos = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_FRESCOS_SI.equals(devolucionAvisos.getFlgUrgenteFrescos());
								isUrgenteAlimentacion = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_ALI_SI.equals(devolucionAvisos.getFlgUrgenteAli());
								isUrgenteNoAli = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_NO_ALI_SI.equals(devolucionAvisos.getFlgUrgenteNoAli());
								mostrarAvisosDevolucionesUrgente=isUrgenteFrescos || isUrgenteAlimentacion || isUrgenteNoAli;
							}
						}
					}
					model.addAttribute("avisoDevoluciones", mostrarAvisosDevoluciones);
					model.addAttribute("avisoDevolucionesUrgente", mostrarAvisosDevolucionesUrgente);	
					
					Boolean mostrarAvisoCentro = this.tMisMensajes.existenAvisos(userAux,true);
					model.addAttribute("avisoCentro",mostrarAvisoCentro);
					return "pda_p11_welcome";
				} catch(Exception e){
					return "pda_p11_welcome";
				}
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			logger.info("1 - pdawelcome - GET - PDA Logging unexpected errors");
			user.setCode("");
			return "pda_error";
		}
		return "pda_p11_welcome";
	}
}