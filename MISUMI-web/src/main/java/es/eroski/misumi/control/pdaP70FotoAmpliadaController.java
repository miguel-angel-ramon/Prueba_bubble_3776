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
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP70FotoAmpliadaController {
	
	private static Logger logger = Logger.getLogger(pdaP70FotoAmpliadaController.class);
	
	@Autowired
	private FotosReferenciaService fotosReferenciaService;
	
	@RequestMapping(value = "/pdaP70FotoAmpliada",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="idDevolucion") String idDevolucion,
			@RequestParam(value="codArticulo") String codArticulo,
			@RequestParam(value="paginaActual", required=false, defaultValue = "") String paginaActual,
			@RequestParam(value="referenciaFiltro", required=false, defaultValue = "") String referenciaFiltro,
			@RequestParam(value="proveedorFiltro", required=false, defaultValue = "") String proveedorFiltro,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		TDevolucionLinea devolucionLinea = new TDevolucionLinea();
		devolucionLinea.setDevolucion(new Long(idDevolucion));
		devolucionLinea.setCodArticulo(new Long(codArticulo));
		
		//Obtener los bultos de los proveedores
		//List<String> devolucionConBultoPorProveedor = ((TDevolucionLinea) session.getAttribute("devolucionConBultoPorProveedor")).getBultoPorProveedorLst();
		//devolucionLinea.setBultoPorProveedorLst(devolucionConBultoPorProveedor);

		model.addAttribute("devolucionLinea", devolucionLinea);
		model.addAttribute("paginaActual", paginaActual);
		model.addAttribute("referenciaFiltro",referenciaFiltro);
		model.addAttribute("proveedorFiltro",proveedorFiltro);

		model.addAttribute("actionP70","pdaP62RealizarDevolucionFinCampania.do");
		
		String resultado = "pda_p70_fotoAmpliada";

		return resultado;
	}
	
	@RequestMapping(value = "/pdaGetImageP70", method = RequestMethod.GET)
	public void doGet(@RequestParam(value = "codArticulo", required = true) Long codArticulo,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		User user = (User) session.getAttribute("user");
		try {
			Utilidades.cargarImagenPistola(codArticulo, response, "gif", null, 282F, 235F, 282F);
		} catch (Exception e) {
			logger.error("###############################");
			logger.error(StackTraceManager.getStackTrace(e));	
			logger.error("centro: " + user.getCentro().getCodCentro());
			logger.error("codArticulo: " + codArticulo);
			logger.error("###############################");
		}
	}

}
