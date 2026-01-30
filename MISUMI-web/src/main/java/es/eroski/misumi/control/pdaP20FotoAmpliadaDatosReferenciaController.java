package es.eroski.misumi.control;

import java.util.List;

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

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VDatosDiarioArt;
//import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
//import es.eroski.misumi.service.iface.VMapaReferenciaService;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP20FotoAmpliadaDatosReferenciaController {
	
	private static Logger logger = Logger.getLogger(pdaP20FotoAmpliadaDatosReferenciaController.class);
	
//	@Autowired
//	private FotosReferenciaService fotosReferenciaService;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
//	@Autowired
//	private VMapaReferenciaService vMapaReferenciaService;
	
	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	
	@RequestMapping(value = "/pdaP20FotoAmpliadaDatosReferencia",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="codArticulo") String codArticulo,
			@RequestParam(value="tieneFoto") String tieneFoto,
			@RequestParam(value="pestanaDatosRef", required=false, defaultValue = "") String pestanaDatosRef,
			@RequestParam(value="procede", required=false, defaultValue = "") String procede,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		String strEstructura = "";
		String tipoReferencia = "";
		
		String resultado = "pda_p20_fotoAmpliadaDatosReferencia";
		
		try {
			Long longCodArticulo = new Long(codArticulo);
	
			VDatosDiarioArt vDatosDiarioArtRes = null;
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(longCodArticulo);
			vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

			//Pet. 170
			if (vDatosDiarioArtRes != null) {
				
				List<VAgruComerRef> listVAgrucomerRef =  this.vAgruComerRefService.findAll(new VAgruComerRef("I3",vDatosDiarioArtRes.getGrupo1(),vDatosDiarioArtRes.getGrupo2(),vDatosDiarioArtRes.getGrupo3(),null,null,null), null);
				VAgruComerRef vAgruComeref = listVAgrucomerRef.size() > 0 ? listVAgrucomerRef.get(0) : null;
				
				//Calculamos el literal con la estructura y descripcion a pintar en pantalla
				strEstructura = vDatosDiarioArtRes.getGrupo1() + "-" +  vDatosDiarioArtRes.getGrupo2() + "-" +  vDatosDiarioArtRes.getGrupo3() + "-" + (vAgruComeref != null? vAgruComeref.getDescripcion() : null);
			
				//Calculamos el literal a mostrar en pantalla en base al valor de Tipo compra venta
				if (vDatosDiarioArtRes.getTipoCompraVenta().equals("C")) {
					tipoReferencia = "COMPRA";
				} else if  (vDatosDiarioArtRes.getTipoCompraVenta().equals("V")) {
					tipoReferencia = "VENTA";
				} else if (vDatosDiarioArtRes.getTipoCompraVenta().equals("T")) {
					tipoReferencia = "COMPRA/VENTA";
				}
			}
			
			model.addAttribute("estructura", strEstructura);
			model.addAttribute("tipoReferencia", tipoReferencia);
			
			model.addAttribute("codArticuloFoto", codArticulo);
			model.addAttribute("tieneFoto", tieneFoto);
			model.addAttribute("procede", procede);
			model.addAttribute("actionP20", pestanaDatosRef+".do");
			
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultado;

	}

	@RequestMapping(value = "/pdaGetImageP20DatosReferencia", method = RequestMethod.GET)
	public void doGet(ModelMap model,
			@RequestParam(value = "codArticulo", required = true) Long codArticulo,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		response.setContentType("image/gif");

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
