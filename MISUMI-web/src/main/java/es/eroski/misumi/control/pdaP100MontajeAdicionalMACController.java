package es.eroski.misumi.control;

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

import es.eroski.misumi.dao.iface.VPlanogramaService;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.service.iface.PlanogramaKosmosService;
import es.eroski.misumi.service.iface.PlanogramaService;
import es.eroski.misumi.service.iface.PlanogramaVigenteService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class pdaP100MontajeAdicionalMACController {
	
	private static Logger logger = Logger.getLogger(pdaP100MontajeAdicionalMACController.class);
	
	@Autowired
	private PlanogramaVigenteService planogramaVigenteService;

	@Autowired
	private PlanogramaKosmosService planogramaKosmosService;
	
	@Autowired
	private VPlanogramaService vPlanogramaService;
	
	@RequestMapping(value = "/pdaP100MontajeAdicionalMAC",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@RequestParam(value="codArt") Long codArt,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		User user = (User) session.getAttribute("user");
		String resultado = "pda_p100_montajeAdicionalMAC";
		model.addAttribute("codArt", codArt);
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
		try {
			ReferenciasCentroIC referenciasCentroIC = new ReferenciasCentroIC();
			referenciasCentroIC.setCodCentro(user.getCentro().getCodCentro());
			referenciasCentroIC.setCodArt(codArt);
			PlanogramaVigente planogramaVigente = obtenerPlanogramaVigente(referenciasCentroIC); 
			//Si no existe el planogramaVigente o la capacidad maxima lineal es nula y el stockminimocomercial es nulo, se consulta el planograma de kosmos.
			if(planogramaVigente == null || (planogramaVigente.getCapacidadMaxLineal() == null && planogramaVigente.getStockMinComerLineal() == null)){
				planogramaVigente = obtenerPlanogramaKosmos(referenciasCentroIC);
			}
			if(planogramaVigente!=null){
				planogramaVigente = rellenarDatosVegalsa(planogramaVigente, session);
				if(planogramaVigente!=null && planogramaVigente.getOfertaProm()!=null &&  planogramaVigente.getEspacioProm()!=null){
					int cantidad=0;
					if(planogramaVigente.getFechaGenMontaje1()!=null){
						cantidad=planogramaVigente.getCapacidadMontaje1().intValue();
					}else if(planogramaVigente.getFechaGenCabecera()!=null){
						cantidad=planogramaVigente.getCapacidadMaxCabecera().intValue();
					}
					model.addAttribute("capacidad", cantidad);
					model.addAttribute("oferta", planogramaVigente.getOfertaProm());
					model.addAttribute("espacioProm", planogramaVigente.getEspacioProm());
				}
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return resultado;
		
		
	}
	
	private PlanogramaVigente obtenerPlanogramaVigente(
			ReferenciasCentroIC referenciasCentroIC) throws Exception {

		PlanogramaVigente planogramaVigenteRes;
		PlanogramaVigente planogramaVigente = new PlanogramaVigente();
		planogramaVigente.setCodArt(referenciasCentroIC.getCodArt());
		planogramaVigente.setCodCentro(referenciasCentroIC.getCodCentro());

		planogramaVigenteRes = this.planogramaVigenteService.findOne(planogramaVigente);

		return planogramaVigenteRes;
	}

	private PlanogramaVigente obtenerPlanogramaKosmos(
			ReferenciasCentroIC referenciasCentroIC) throws Exception {

		PlanogramaVigente planogramaVigenteRes;
		PlanogramaVigente planogramaVigente = new PlanogramaVigente();
		planogramaVigente.setCodArt(referenciasCentroIC.getCodArt());
		planogramaVigente.setCodCentro(referenciasCentroIC.getCodCentro());

		planogramaVigenteRes = this.planogramaKosmosService.findOne(planogramaVigente);

		return planogramaVigenteRes;
	}
	
	private PlanogramaVigente rellenarDatosVegalsa(PlanogramaVigente planogramaVigente, HttpSession session) throws Exception {
		PlanogramaVigente output = planogramaVigente;
		User user = (User) session.getAttribute("user");
		Long codSoc = user.getCentro().getCodSoc();
		// Si es un centro Vegalsa (cod_soc = 13)
		if (codSoc == 13){
			output = vPlanogramaService.findDatosVegalsa(planogramaVigente);
		}
	
		return output;
	}
	
}
