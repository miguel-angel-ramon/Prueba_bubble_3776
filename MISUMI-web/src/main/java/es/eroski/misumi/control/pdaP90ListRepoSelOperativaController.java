package es.eroski.misumi.control;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.xml.bind.v2.runtime.reflect.opt.Const;

import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionHayDatos;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.service.iface.ListadoReposicionService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;


@Controller
public class pdaP90ListRepoSelOperativaController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP90ListRepoSelOperativaController.class);

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private ListadoReposicionService listadoReposicionService;

	
	
	@RequestMapping(value = "/pdaP90ListRepoSelOperativa",method = RequestMethod.POST)
	public String processForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "pda_p40_selFiabilidad";
		try {

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return resultado;
	}
	
	@RequestMapping(value = "/pdaP90ListRepoSelOperativa",method = RequestMethod.GET)
	public String showForm(ModelMap model, 
			@Valid final String area,
			@RequestParam(value="tipoListado",  required = false, defaultValue = "") Long tipoListado,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		// Validar objeto
		String resultado = "pda_p90_listRepoSelOperativa";
		try {
			//Necesitamos ir pasando el area por toda la operativa LISTADO REPOSICION 
			session.setAttribute("area", area);
			
			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();
			String codMac = user.getMac();
			
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			
			
			
			//Inicializamos el objeto reposicion con el centro, pistola y tipo de listado. 
			Reposicion reposicion = new Reposicion();
			reposicion.setCodLoc(codCentro);
			reposicion.setCodMac(codMac);
			
			//Si el valor del parametro tipoListado tiene valor 2, viene de la opción de menu LISTADO REPO VENTA, en caso contrario el tipo de listado estara metido en sesio.
			if ((tipoListado != null) && (tipoListado == 2)) {
				reposicion.setTipoListado(tipoListado);
				session.setAttribute("tipoListado", tipoListado); // y lo metemos en sesion para qu a partir de aqui lo cojamos de sesion
				
			} else { //Lo obtenemos de sesion
				tipoListado = (Long) session.getAttribute("tipoListado");
				reposicion.setTipoListado(tipoListado);
			}
			
			//En caso listado 2 no hay area en la sesión, por lo que decimos que es textil
			if(area == null){
				session.setAttribute("area", Constantes.VENTA_REPO_AREA_TEXTIL);
				reposicion.setArea(Constantes.VENTA_REPO_AREA_TEXTIL);
			}else{
				session.setAttribute("area", area);
				reposicion.setArea(area);
			}
			
			//Miramos si hay datos. Si hay datos redirijimos a una pantalla intermedio donde se elegira entres las opciones "Empezar operativa desde 0" y "Continuar con la anterior".
			//Si no hay datos sera como "Empezar operativa desde 0"
			ReposicionHayDatos reposicionHayDatos = listadoReposicionService.hayDatosReposicion(reposicion);
			
			
			if (reposicionHayDatos != null) {
				
				if (reposicionHayDatos.getHayDatos().equals("S")) {
					//Si existen datos, preguntamos al usuario si quiere empezar de 0 o con la anterior si el listado seleccionado es
					//el 1. Si es el 2 y hay datos, mandamos recuperar los datos.
					if(new Long(1).equals(tipoListado)){
						resultado = "pda_p90_listRepoSelOperativa";
						
						//Para que en p90 se sepa si ir a p91 o p92 con tipo de listado
						model.addAttribute("reposicion", reposicion);
					}else{
						return "redirect:pdaP92ListadoRepoAnt.do";
					}
					
				} else {
					//Eliminamos la temporal por si acaso se ha quedado alguna talla.
					this.listadoReposicionService.eliminarTempListadoRepo(codMac);
					
					//Si no hay datos y el listado es 1, mandamos a la búsqueda de referencias directamente.
					if(new Long(1).equals(tipoListado)){						
						resultado = "pda_p91_listRepo";
					}else{
						resultado = "pda_p92_listRepoAnt";
					}
				}				
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return resultado;
	}
	
	

}