package es.eroski.misumi.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosSfmCap;
import es.eroski.misumi.util.Constantes;

@Controller
@RequestMapping("/pdaP24Guardar")
public class pdaP24GuardarController {

	private static Logger logger = Logger.getLogger(pdaP24GuardarController.class);

	
	@RequestMapping(method = RequestMethod.POST)
	public String showForm(ModelMap model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String resultado = "pda_p21_sfm";
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();

		if (Constantes.ORIGEN_CONSULTA.equals(request.getParameter("origenConsulta"))){ //Cuando viene de consulta sólo hay un registro
			if (session.getAttribute("sfmCapReferenciaConsulta") != null){
				PdaDatosSfmCap pdaDatosSfmCapResultado = (PdaDatosSfmCap) session.getAttribute("sfmCapReferenciaConsulta");
				pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosSfmCapResultado.getCodArt()));
				pdaDatosCab.setOrigenConsulta(Constantes.ORIGEN_CONSULTA);
				model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
				
				if ("S".equals(request.getParameter("errorGuardado"))){
					if (pdaDatosSfmCapResultado.getFlgFacing().equals("S")){
						//Se trata de un tipo Facing
						resultado =  "pda_p27_facing";
					}else{
						if (pdaDatosSfmCapResultado.getFlgCapacidad().equals("N"))
						{
							//Se trata de un tipo SFM
							resultado = "pda_p21_sfm";
						}
						else
						{
							//Se trata de un tipo Capacidad
							resultado = "pda_p22_capacidad";
						}
					}
				}else{				
						redirectAttributes.addAttribute("codArt", pdaDatosSfmCapResultado.getCodArt());
						return "redirect:pdaP12DatosReferencia.do";
				}
			}
		}else{
			if (session.getAttribute("listaSFMCap") != null){
				List<PdaDatosSfmCap> lista = (List<PdaDatosSfmCap>) session.getAttribute("listaSFMCap");
				PdaDatosSfmCap pdaDatosSfmCapResultado = lista.get(0);
				pdaDatosSfmCapResultado.setTotal(lista.size());
				model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
				
				if (pdaDatosSfmCapResultado.getFlgFacing().equals("S")){
					//Se trata de un tipo Facing
					resultado =  "pda_p27_facing";
				}else{
					if (pdaDatosSfmCapResultado.getFlgCapacidad().equals("N"))
					{
						//Se trata de un tipo SFM
						resultado = "pda_p21_sfm";
					}
					else
					{
						//Se trata de un tipo Capacidad
						resultado = "pda_p22_capacidad";
					}
				}
			}
		}		
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		return resultado;
	}
}