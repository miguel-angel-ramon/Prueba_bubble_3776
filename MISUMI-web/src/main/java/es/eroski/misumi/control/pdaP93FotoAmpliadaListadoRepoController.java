package es.eroski.misumi.control;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP93FotoAmpliadaListadoRepoController {
	
	private static Logger logger = Logger.getLogger(pdaP93FotoAmpliadaListadoRepoController.class);
	
	@Autowired
	private FotosReferenciaService fotosReferenciaService;
	
	@RequestMapping(value = "/pdaP93FotoAmpliada",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="codArtFoto") String codArtFoto,
			@RequestParam(value="paginaActual") String paginaActual,
			@RequestParam(value="origen", required=true) String origen,
			@RequestParam(value="pgSubList") String pgSubList,
			@RequestParam(value="seccion", required=false) String seccion,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {		
		
		String resultado = "pda_p93_fotoAmpliada";
		
		model.addAttribute("codArtFoto", codArtFoto);
		model.addAttribute("paginaActual", paginaActual);
		model.addAttribute("origen", origen+".do");
		model.addAttribute("pgSubList", pgSubList);
		model.addAttribute("seccion", seccion);
		
		return resultado;
	}
	

	@RequestMapping(value = "/pdaGetImageP93", method = RequestMethod.GET)
	public void doGet(@RequestParam(value = "codArtFoto", required = true) Long codArtFoto,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		User user = (User) session.getAttribute("user");
		try {
			Utilidades.cargarImagenPistola(codArtFoto, response, "gif", null, 282F, 235F, 282F);
		} catch (Exception e) {
			logger.error("###############################");
			logger.error(StackTraceManager.getStackTrace(e));	
			logger.error("centro: " + user.getCentro().getCodCentro());
			logger.error("codArticulo: " + codArtFoto);
			logger.error("###############################");
		}
	}

}
