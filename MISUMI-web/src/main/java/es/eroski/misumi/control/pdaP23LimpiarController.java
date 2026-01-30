package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosSfmCap;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class pdaP23LimpiarController {

	private static Logger logger = Logger.getLogger(pdaP23LimpiarController.class);

	
	@RequestMapping(value = "/pdaP23Limpiar",method = RequestMethod.POST)
	public String processForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		// Validar objeto
		String resultado = "pda_p23_limpiarSfmCapacidad";
		try {
			Integer posicion = Integer.parseInt(request.getParameter("posicion"));

					PdaDatosSfmCap pdaDatosSfmCapResultado = this.obtenerReferenciaPorPosicion(session, posicion);
					PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
					pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosSfmCapResultado.getCodArt()));
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
					if (pdaDatosSfmCapResultado.getFlgFacing().equals("S")){
						//Se trata de un tipo Facing
						resultado =  "pda_p27_facing";
					}else{
						if (pdaDatosSfmCapResultado.getFlgCapacidad().equals("N"))
						{
							//Se trata de un tipo SFM
							resultado =  "pda_p21_sfm";
						}
						else
						{
							//Se trata de un tipo Capacidad
							resultado = "pda_p22_capacidad";
						}
					}
				

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	
	@RequestMapping(value = "/pdaP23Limpiar",method = RequestMethod.GET)
	public String showForm(ModelMap model,@Valid final String cleanAll, @Valid final Integer posicion,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		// Validar objeto
		String resultado = "pda_p23_limpiarSfmCapacidad";
		try {

			if (cleanAll != null && cleanAll.equals("N")) {
				List<PdaDatosSfmCap> listaSfmCap = (List<PdaDatosSfmCap>) session.getAttribute("listaSFMCap");
				listaSfmCap.remove(posicion - 1);
				if (!listaSfmCap.isEmpty()) {
					HashMap<String, Integer> mapClavesSFMCap = new HashMap<String, Integer>();
					Integer posicionMap = 1;
					for (PdaDatosSfmCap sfmCap : listaSfmCap){
						mapClavesSFMCap.put(String.valueOf(sfmCap.getCodArt()), posicionMap);
						sfmCap.setPosicion(posicionMap);
						posicionMap++;
					}
					Integer posicionIni = posicion;
					if (posicionIni > listaSfmCap.size()){
						posicionIni = listaSfmCap.size();
					}
					session.setAttribute("hashMapClavesSFMCap", mapClavesSFMCap);
					PdaDatosSfmCap pdaDatosSfmCapResultado = this.obtenerReferenciaPorPosicion(session, posicionIni);
					PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
					pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosSfmCapResultado.getCodArt()));
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
					if (pdaDatosSfmCapResultado.getFlgFacing().equals("S")){
						//Se trata de un tipo Facing
						resultado =  "pda_p27_facing";
					}else{
	
						if (pdaDatosSfmCapResultado.getFlgCapacidad().equals("N"))
						{
							//Se trata de un tipo SFM
							resultado =  "pda_p21_sfm";
						}
						else
						{
							//Se trata de un tipo Capacidad
							resultado = "pda_p22_capacidad";
						}
					}
				} else {
					session.removeAttribute("hashMapClavesSFMCap");
					session.removeAttribute("listaSFMCap");
					PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
					model.addAttribute("pdaDatosCab", pdaDatosCab);

					resultado =  "pda_p21_sfm";
				}
			}

			if (cleanAll != null && cleanAll.equals("Y")) {

				session.removeAttribute("hashMapClavesSFMCap");
				session.removeAttribute("listaSFMCap");
				PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
				model.addAttribute("pdaDatosCab", pdaDatosCab);

				resultado = "pda_p21_sfm";

			}


		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	
	private PdaDatosSfmCap obtenerReferenciaPorPosicion(HttpSession session, int posicion) throws Exception{
		
		
		PdaDatosSfmCap pdaDatosSfmCap = new PdaDatosSfmCap();
		List<PdaDatosSfmCap> listaSfmCap =new ArrayList<PdaDatosSfmCap>();
		HashMap<String, Integer> mapClavesSFMCap = new HashMap<String, Integer>();

		if (session.getAttribute("hashMapClavesSFMCap") != null && session.getAttribute("listaSFMCap") != null)
		{
			mapClavesSFMCap = (HashMap<String, Integer>) session.getAttribute("hashMapClavesSFMCap");
			
			listaSfmCap = (List<PdaDatosSfmCap>) session.getAttribute("listaSFMCap");
			
			pdaDatosSfmCap = (PdaDatosSfmCap) listaSfmCap.get(posicion-1);
			
			//Refrescamos el total del objeto.
			pdaDatosSfmCap.setTotal(mapClavesSFMCap.size());
			
			//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
			session.setAttribute("ultimaRef", String.valueOf(pdaDatosSfmCap.getCodArt()));
		}
		
		return pdaDatosSfmCap;
	}
}