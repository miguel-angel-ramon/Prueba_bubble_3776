package es.eroski.misumi.control.common;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.util.SessionUtils;

@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping(value = "/comprobarGeneracionExcel", method = RequestMethod.POST)
    public @ResponseBody String comprobarGeneracionExcel(HttpServletResponse response, HttpSession session) throws Exception {
        try {
            return SessionUtils.comprobarGeneracionExcel(session);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw e;
        }
    }
}		