package es.eroski.misumi.control;

import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.exception.LDAPException;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.LoginService;
import es.eroski.misumi.service.iface.UserService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static Logger logger = Logger.getLogger(LoginController.class);//LoggerFactory.getLogger(p12AltasCatalogoController.class);

	@Resource 
	private MessageSource messageSource;
	@Autowired
	private LoginService loginService;
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private ServletContext context;
		
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, User> model,HttpServletRequest request) {
		
		
		User user = new User();
		model.put("user", user);
		
		String pda = "";
		
		if (request.getParameter("pda") != null){
			pda = (String)request.getParameter("pda");
		}
		
		if (pda.equals("si")){
			return "pda_p10_login";
		}else{
			
			if (!isClientWindowsCE(request)){
				logger.info(
						"Loginnn");
				
				return "p10_login";
			}else{
				logger.info("Loginnn PDA");
				
				return "pda_p10_login";
			}
		}
	}


//	@RequestMapping(value = "/validateUser", method = RequestMethod.POST)
//	public @ResponseBody FeedBack  validateUser(
//				@RequestBody User usuario,
//				HttpServletResponse response,
//				HttpSession session) throws Exception{
//		FeedBack feedBack = null;
//	    Locale locale = LocaleContextHolder.getLocale();
//		try {
//			User usu = loginService.validateLogin(usuario);
//							
//			if (usu == null){
//				feedBack = new FeedBack(messageSource.getMessage("login.userIncorrect", null, locale));
//			    feedBack.setMessageType(feedBack.getERROR());
//			    feedBack.setRedirect(null);
//			} else {
//				feedBack = new FeedBack(null);
//				
//				session.setAttribute("user", usu);
//				feedBack.setRedirect("./welcome.do");
//			}
//		} catch(LDAPException e){
//			feedBack = new FeedBack(messageSource.getMessage("login.pwdIncorrect", null, locale));
//		    feedBack.setMessageType(feedBack.getERROR());
//		    feedBack.setRedirect(null);
//			
//		} catch (Exception e) {
//					logger.error(StackTraceManager.getStackTrace(e));
//					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//					throw e;
//		}
//		return feedBack;
//	}	

	@RequestMapping(method = RequestMethod.POST)
	public String processForm(@Valid final User user, BindingResult result,
			Map<String, User> model, HttpSession session,
			HttpServletResponse response) {
		// Se recoge la locale del ThreadLocal a nivel de metodo, para que sea
		// Thread Safe
		Locale locale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			logger.info("Logging errors");
			return "p10_login";
		}
		
		try {
			User usu = this.loginService.validateLogin(user);
			//,mock 
//			User usu = user;
//			usu.setUserName("usuario prueba");
			if (usu == null){
			    
			    ObjectError oe = new ObjectError("user", this.messageSource.getMessage(
						"login.userIncorrect", null, locale));
				result.addError(oe);
				logger.info("Invalid Login!");
				return "p10_login";
			} else {
				//Mirar si tiene acceso en la ddbb 
				try{
					User userAux=this.userService.find(usu);
					if (userAux != null){
						//Obtener el usuario de la WEB2 que se está logeando
						String contextMisumiWeb = context.getContextPath();
						
						if(contextMisumiWeb.equalsIgnoreCase(Constantes.CONTEXTO_WEB2)){							
							logger.error("##########################################");
							logger.error("USUARIO WEB2 LOGUEADO: " + userAux.getCode());
							logger.error("##########################################");
						}
						userAux.setUserName(usu.getUserName());
						userAux.setUserNameCab(Utilidades.obtenerCadenaRestringida(usu.getUserName(), Constantes.NUMERO_CARACTERES_USUARIO_CABECERA));
						
						//Tratamiento para el perfil 3 (solo consulta)
						if (userAux.getPerfil() ==3) {
							if (userAux.getCentro() != null  && !(userAux.getCentro().getCodCentro().equals(""))) {
								//Tiene centro asignado, no debe mostrarse el combo de seleccion de centros
								userAux.setSelCentro("0");
							} else {
								//No tiene centro asignado, debe mostrarse el combo de seleccion de centros
								userAux.setSelCentro("1");
							}
						}
						
						session.setAttribute("user", userAux);
						response.addCookie(new Cookie("sessionCookie", "active"));
						logger.info("Lenguage:" + LocaleContextHolder.getLocale().getLanguage());

						response.sendRedirect("welcome.do?locale=es" );
					}	
				}catch(Exception e){
					  ObjectError oe = new ObjectError("user", this.messageSource.getMessage(
								"login.userNotAuthorized", null, locale));
						result.addError(oe);
						logger.info("Invalid Login!");
						return "p10_login";
				}
				
			}
		} catch(LDAPException e){
		    ObjectError oe = new ObjectError("user", this.messageSource.getMessage(
					"login.pwdIncorrect", null, locale));
			result.addError(oe);
			logger.info("Invalid Login!");
			return "p10_login";
			
		} catch (Exception e) {
					logger.error(StackTraceManager.getStackTrace(e));
		}
		return "p11_welcome";
	}
	
	private static boolean isClientWindowsCE(HttpServletRequest request)
	{
		//Se obtiene la plataforma del cliente
		String userAgent = request.getHeader ( "User-Agent" );
		System.out.println(">>>>>>>>>>>>>>>>>>User-Agent>>>>>" + userAgent + "<<<<<<<<");
		
		//Si el cliente es una PDA (Windows CE)
		if (userAgent != null && 
			userAgent.indexOf(Constantes.WINDOWS_CE) != -1)
		{
			return true;
		} else 
		{
			return false;
		}
	}
//	@RequestMapping(method = RequestMethod.POST)
//	public String processForm(@Valid User user, BindingResult result,
//			Map<String, User> model, HttpSession session,
//			HttpServletResponse response) {
//		// Se recoge la locale del ThreadLocal a nivel de metodo, para que sea
//		// Thread Safe
//		Locale locale = LocaleContextHolder.getLocale();
//		try {
//		
//			//,mock 
//			User usu = user;
//			usu.setUserName("usuario prueba");
//			if (usu == null){
//			    
//			    ObjectError oe = new ObjectError("user", messageSource.getMessage(
//						"login.userIncorrect", null, locale));
//				result.addError(oe);
//				logger.info("Invalid Login!");
//				return "p10_login";
//			} else {
//				//Mirar si tiene acceso en la ddbb 
//				try{
//					User userAux=userService.find(usu);
//					if (userAux != null){
//						userAux.setUserName(usu.getUserName());
//						session.setAttribute("user", userAux);
//						response.addCookie(new Cookie("sessionCookie", "active"));
//						logger.info("Lenguage:" + LocaleContextHolder.getLocale().getLanguage());
//						response.sendRedirect("welcome.do?locale=es" );
//					}	
//				}catch(Exception e){
//					  ObjectError oe = new ObjectError("user", messageSource.getMessage(
//								"login.userNotAuthorized", null, locale));
//						result.addError(oe);
//						logger.info("Invalid Login!");
//						return "p10_login";
//				}
//				
//			}
//		
//		} catch (Exception e) {
//					logger.error(StackTraceManager.getStackTrace(e));
//		}
//		return "p11_welcome";
//	}

}