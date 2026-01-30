package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.Aviso;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.TMisMensajesService;

@Controller
public class pdaP50Controller {

	private static Logger logger = Logger.getLogger(WelcomeController.class);
	
	@Autowired
	private TMisMensajesService tMisMensajes;
	
	@RequestMapping(value = "/pdaP50Avisos", method = RequestMethod.GET)
	public String showForm(
			ModelMap model,
			HttpSession session,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception {
		User user = (User)session.getAttribute("user");
		Aviso aviso = new Aviso();
		List<Aviso> listAviso = new ArrayList<Aviso>();
		try {
			//Indicamos que se trata de un mensaje para pda
			aviso.setPda(true);
			
			//Añadimos aviso del centro 0 equivalente a TODOS LOS CENTROS
			aviso.setCodCentro(Long.valueOf(0));
			
			//Controlamos que exista un aviso para el centro 0. Si existe se inserta en la lista.
			if (this.tMisMensajes.existeAviso(aviso)){	
				listAviso.add(this.tMisMensajes.obtenerAviso(aviso));
			}
			
			//Controlamos que haya un centro seleccionado
			if (null != user.getCentro()){//Si el centro no está seleccionado
				//Insertamos el centro seleccionado al aviso
				aviso.setCodCentro(user.getCentro().getCodCentro());

				//Controlamos que exista un aviso para el centro seleccionado. Si existe se inserta en la lista.
				if (this.tMisMensajes.existeAviso(aviso)){			
					listAviso.add(this.tMisMensajes.obtenerAviso(aviso));
				} 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		//Se añade al modelo la lista de avisos
		model.put("avisosList", listAviso);
		return "pda_p50_avisos";
	}	
}
