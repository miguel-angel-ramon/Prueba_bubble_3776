package es.eroski.misumi.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/logout")
public class LogoutController {

         //private static Logger logger = LoggerFactory.getLogger(LogoutController.class);
	private static Logger logger = Logger.getLogger(LogoutController.class);//LoggerFactory.getLogger(p12AltasCatalogoController.class);


	@Resource
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showForm(HttpSession session) {
		logger.info("Logging out user");
		session.setAttribute("user", null);
		session.invalidate();
		return new ModelAndView("redirect:login.do");
	}

	public MessageSource getMessageSource() {
		return this.messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
