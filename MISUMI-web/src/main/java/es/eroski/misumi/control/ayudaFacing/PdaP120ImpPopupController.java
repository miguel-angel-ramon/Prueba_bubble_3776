package es.eroski.misumi.control.ayudaFacing;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.control.pdaConsultasController;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosImc;
import es.eroski.misumi.model.pda.PdaDatosPopupImplantacion;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class PdaP120ImpPopupController extends pdaConsultasController{

	private final String IMPLANTACION_JSP = "/pda/ayudaFacing/pda_p120_implantacionPopup";

	private static Logger logger = Logger.getLogger(PdaP120ImpPopupController.class);
	
	@Resource 
	private MessageSource messageSource;

	@RequestMapping(value = "/pdaP120ImpPopup", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session,
			@RequestParam(value="codArt", required=false, defaultValue="") String codArt,
			@RequestParam(value="descArt", required=false, defaultValue="") String descArtConCod,
			@RequestParam(value="impl", required=false, defaultValue="") String implantacion,
			@RequestParam(value="flgColorImpl", required=false, defaultValue="") String flgColorImplantacion,
			@RequestParam(value="tipoRef", required=false, defaultValue="") String tipoReferencia,
			@RequestParam(value="guardadoImc", required=false, defaultValue="") String guardadoImc,
			@RequestParam(value="facAncho", required=false, defaultValue="") String facingAncho,
			@RequestParam(value="facAlto", required=false, defaultValue="") String facingAlto,
			@RequestParam(value="cap", required=false, defaultValue="") String capacidad,
			@RequestParam(value="fac", required=false, defaultValue="") String facing,
			@RequestParam(value="imc", required=false, defaultValue="") String imc,
			@RequestParam(value="mult", required=false, defaultValue="") String multiplicador
			) {

		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		PdaDatosPopupImplantacion pdaDatosPopupImplantacion = new PdaDatosPopupImplantacion();
		PdaDatosImc pdaDatosImc = new PdaDatosImc();
		Long codArticulo = null;

		if (codArt != null && codArt!=""){
			codArticulo = Long.parseLong(codArt);
		}

		//Gestionar si el centro es caprabo o no y obtener la descripción 
		//del artículo según el tipo de centro.
		String descArt = descArtConCod;
		
		if (descArt != null && !descArt.isEmpty()){
			int pos = descArtConCod.indexOf("-");
			if (pos >= 0) {
			    descArt = descArtConCod.substring(pos+1);
			}
		}

		pdaDatosImc.setCodArt(codArticulo);
		pdaDatosImc.setDescArt(descArt);
		pdaDatosImc.setDescArtConCodigo(descArtConCod);
		pdaDatosImc.setTipoReferencia(tipoReferencia);
		pdaDatosImc.setGuardadoImc(guardadoImc);
		pdaDatosImc.setFacAncho(facingAncho);
		pdaDatosImc.setFacAlto(facingAlto);
		pdaDatosImc.setCapacidad(capacidad);
		pdaDatosImc.setFacing(facing);
		pdaDatosImc.setImc(imc);
		pdaDatosImc.setMultiplicador(multiplicador);

		try{
			//Cargamos los datos de cabecera.
			pdaDatosCab.setCodArtCab(codArt);

			model.addAttribute("pdaDatosCab", pdaDatosCab);

			pdaDatosPopupImplantacion.setCodArt(codArticulo);
			pdaDatosPopupImplantacion.setImplantacion(implantacion);
			pdaDatosPopupImplantacion.setProcede("pdaP120ImplantacionPopup"); 
			pdaDatosPopupImplantacion.setFlgColorImplantacion(flgColorImplantacion);

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		model.addAttribute("pdaDatosImc", pdaDatosImc);
		model.addAttribute("pdaDatosPopupImplantacion", pdaDatosPopupImplantacion);

		return IMPLANTACION_JSP;
	}

}
