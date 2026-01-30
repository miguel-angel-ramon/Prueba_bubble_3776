package es.eroski.misumi.control.ayudaFacing;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.control.pdaConsultasController;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosImc;
import es.eroski.misumi.model.pda.PdaDatosPopupImplantacion;
import es.eroski.misumi.model.pda.ayudaFacing.RefAyudaFacingLista;
import es.eroski.misumi.service.ayudaFacing.iface.ConsultaRefsAyudaFacingService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class PdaP119AyudaFacingController extends pdaConsultasController{

	private final String AYUDA_FACING_JSP = "/pda/ayudaFacing/pda_p119_ayudaFacing";

	private static Logger logger = Logger.getLogger(PdaP119AyudaFacingController.class);
	
	@Resource 
	private MessageSource messageSource;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	
	@Autowired
	private ConsultaRefsAyudaFacingService consultaRefsAyudaFacingService;

	@RequestMapping(value = "/pdaP119AyudaFacing", method = RequestMethod.GET)
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

		PdaDatosImc pdaDatosImc = new PdaDatosImc();
		Long codArticulo = null;
		String strEstructura = "";

		if (codArt != null && !codArt.isEmpty()){
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
		
		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");
		Long centro = user.getCentro().getCodCentro();

		try{

			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(codArt);
			pdaDatosCab.setDescArtCab(descArt);

			// Obtener descripcion de la Estructura Comercial para el nivel I4.
			VDatosDiarioArt vDatosDiarioArtRes = null;
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(codArticulo);
			vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

			if (vDatosDiarioArtRes != null) {
				List<VAgruComerRef> listVAgrucomerRef =  this.vAgruComerRefService.findAll(new VAgruComerRef("I4",vDatosDiarioArtRes.getGrupo1(),vDatosDiarioArtRes.getGrupo2(),vDatosDiarioArtRes.getGrupo3(),vDatosDiarioArtRes.getGrupo4(),null,null), null);
				VAgruComerRef vAgruComeref = listVAgrucomerRef.size() > 0 ? listVAgrucomerRef.get(0) : null;
				
				//Obtener el literal con la estructura y descripcion a pintar en pantalla
				strEstructura = vDatosDiarioArtRes.getGrupo1() + "-" +  vDatosDiarioArtRes.getGrupo2() + "-" +  vDatosDiarioArtRes.getGrupo3() + "-" +  vDatosDiarioArtRes.getGrupo4() + "-" + (vAgruComeref != null? vAgruComeref.getDescripcion() : null);
			}

			PdaDatosPopupImplantacion pdaDatosPopupImplantacion = new PdaDatosPopupImplantacion();
			pdaDatosPopupImplantacion.setCodArt(codArticulo);
//			pdaDatosPopupImplantacion.setProcede(procede);
			pdaDatosPopupImplantacion.setImplantacion(implantacion);
			
			String tituloImplantacion = null;
			if (implantacion != null && implantacion.length() > 1) {
			    tituloImplantacion = implantacion.substring(0, Math.min(implantacion.length(), 20));
			}
			pdaDatosPopupImplantacion.setTituloImplantacion(tituloImplantacion);
			pdaDatosPopupImplantacion.setFlgColorImplantacion(flgColorImplantacion);
			pdaDatosPopupImplantacion.setEstructura(strEstructura);

			// Obtener la lista de referencias sugeridas para quitar el facing.
			RefAyudaFacingLista refAyudaFacing = consultaRefsAyudaFacingService.obtenerListaRefsAyudaFacing(centro, codArticulo);
			
			model.addAttribute("existeRefsAyudaFacing", (refAyudaFacing.getDatos()!=null && !refAyudaFacing.getDatos().isEmpty())?true:false);
			model.addAttribute("listaRefsAyudaFacing", refAyudaFacing.getDatos());
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			model.addAttribute("pdaDatosPopupImplantacion", pdaDatosPopupImplantacion);
			
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		model.addAttribute("pdaDatosImc", pdaDatosImc);
		
		return AYUDA_FACING_JSP;
	}

}
