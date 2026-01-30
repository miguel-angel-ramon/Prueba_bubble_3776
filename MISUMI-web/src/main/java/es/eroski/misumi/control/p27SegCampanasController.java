package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.CampanasOfertasMisumi;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.TreeGridCampana;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VMisCampanaOfer;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.TreeGridPost;
import es.eroski.misumi.service.iface.CampanasOfertasMisumiService;
import es.eroski.misumi.service.iface.VMisCampanaOferService;
import es.eroski.misumi.util.Constantes;

@Controller 
@RequestMapping("/segCampanas")
public class p27SegCampanasController {
	
	//private static Logger logger = Logger.getLogger(p27SegCampanasController.class);

	@Autowired
	private CampanasOfertasMisumiService campanasOfertasMisumiService;

	@Autowired
	private VMisCampanaOferService vMisCampanaOferService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
	
		return "p27_segCampanas";
	}
	
	@RequestMapping(value = "/recargaDatosCampanaOfer", method = RequestMethod.POST)
	public  @ResponseBody VMisCampanaOfer recargaDatosCampanaOfer(
			@RequestBody VMisCampanaOfer vMisCampanaOferRecarga,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		//Desglose del campo oferta
		String oferta = null;
		if (vMisCampanaOferRecarga!=null && vMisCampanaOferRecarga.getOferta()!=null){
			oferta = vMisCampanaOferRecarga.getOferta();
		}
		vMisCampanaOferRecarga.setAnoOferta((oferta != null && !"".equals(oferta))?new Long(oferta.substring(0, oferta.indexOf("-"))):null);
		vMisCampanaOferRecarga.setNumOferta((oferta != null && !"".equals(oferta))?new Long(oferta.substring(oferta.indexOf("-") + 1)):null);
		vMisCampanaOferRecarga.setIdSesion(session.getId());
		if (vMisCampanaOferRecarga.getCodArt() != null && !"".equals(vMisCampanaOferRecarga.getCodArt())){
			vMisCampanaOferRecarga.setTipo(Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA);
		}else{
			vMisCampanaOferRecarga.setTipo(Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_ESTRUCTURA);
		}
		
		return this.vMisCampanaOferService.recargaDatosCampanasOfer(vMisCampanaOferRecarga);
	}

	@RequestMapping(value = "/loadAllData", method = RequestMethod.POST)
	public  @ResponseBody Page<SeguimientoCampanas> loadAllData(
			@RequestBody TreeGridCampana rbody,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		List<SeguimientoCampanas> listaCampanas = new ArrayList<SeguimientoCampanas>();
		
		VMisCampanaOfer vMisCampanaOfer = rbody.getDatosCampana();
		//Desglose del campo oferta
		String oferta = null;
		if (vMisCampanaOfer!=null && vMisCampanaOfer.getOferta()!=null){
			oferta = vMisCampanaOfer.getOferta();
		}
		if (vMisCampanaOfer!=null && vMisCampanaOfer.getIdentificador()!=null && "".equals(vMisCampanaOfer.getIdentificador())){
			vMisCampanaOfer.setIdentificador(null);
		}
		vMisCampanaOfer.setAnoOferta((oferta != null && !"".equals(oferta))?new Long(oferta.substring(0, oferta.indexOf("-"))):null);
		vMisCampanaOfer.setNumOferta((oferta != null && !"".equals(oferta))?new Long(oferta.substring(oferta.indexOf("-") + 1)):null);
		vMisCampanaOfer.setIdSesion(session.getId());
		
		//Si me llega la referencia, tendremos que realizar un filtrado especial,
		//filtrando por centro+referencia+tipo=A.
		if (vMisCampanaOfer.getCodArt() != null && !"".equals(vMisCampanaOfer.getCodArt())){
			vMisCampanaOfer.setTipo(Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA);
		}else{
			vMisCampanaOfer.setTipo(Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_ESTRUCTURA);
		}		
		if (rbody.getParentid().equals("") && rbody.getNodeid().equals("")) //Filtrado según el nivel
		{
			//nivel 0
			vMisCampanaOfer.setNivel(new Long(0));
			vMisCampanaOfer.setParentident("0");

		} else {
			Long nivel = new Long(rbody.getN_level()) + 1;
			vMisCampanaOfer.setNivel(nivel);
			vMisCampanaOfer.setParentident(rbody.getNodeid());
		}
		listaCampanas = this.vMisCampanaOferService.findAllReferenciasCampanaOfer(vMisCampanaOfer);
		
		Page<SeguimientoCampanas> campanasOfer = new Page<SeguimientoCampanas>(); 
		
		campanasOfer.setPage("1");
		campanasOfer.setRecords("1");
		campanasOfer.setTotal("1");
		campanasOfer.setRows(listaCampanas);
		
		return campanasOfer;
	}
	
	@RequestMapping(value="/loadCampanas", method = RequestMethod.POST)
	public @ResponseBody List<CampanasOfertasMisumi> getCampanas(
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			User user = (User) session.getAttribute("user");
			Centro centro = user.getCentro();
			
			CampanasOfertasMisumi campanasOfertasMisumi = new CampanasOfertasMisumi();
			campanasOfertasMisumi.setCodLoc(centro.getCodCentro());
			campanasOfertasMisumi.setTipo(Constantes.SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_CAMPANA);
		
			return this.campanasOfertasMisumiService.findCampanas(campanasOfertasMisumi);

		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	@RequestMapping(value="/loadOfertas", method = RequestMethod.POST)
	public @ResponseBody List<CampanasOfertasMisumi> getOfertas(
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			User user = (User) session.getAttribute("user");
			Centro centro = user.getCentro();
			
			CampanasOfertasMisumi campanasOfertasMisumi = new CampanasOfertasMisumi();
			campanasOfertasMisumi.setCodLoc(centro.getCodCentro());
			campanasOfertasMisumi.setTipo(Constantes.SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_OFERTA);
		
			return this.campanasOfertasMisumiService.findOfertas(campanasOfertasMisumi);

		} catch (Exception e) {
		    //logger.error(StackTraceManager.getStackTrace(e));
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}

	@RequestMapping(value = "/loadDataInitSession", method = RequestMethod.POST)
	public  @ResponseBody VMisCampanaOfer loadDataInitSession(
			@RequestParam(value = "centerId", required = true) String centerId,
			@RequestParam(value = "referencia", required = false) String referencia,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		VMisCampanaOfer vMisCampanaOfer = new VMisCampanaOfer();
		vMisCampanaOfer.setCodCentro(new Long(centerId));
		
		if (referencia != null && !referencia.equals(""))
		{
			vMisCampanaOfer.setCodArt(new Long(referencia));
		}
		
		//Obtención de datos desde el procedimiento de carga
		vMisCampanaOfer = this.vMisCampanaOferService.recargaDatosCampanasOfer(vMisCampanaOfer);
		
		return vMisCampanaOfer;
	}
}