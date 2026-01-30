package es.eroski.misumi.control;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.PedidoAdicionalEC;
import es.eroski.misumi.service.iface.PedidoAdicionalECService;


@Controller
@RequestMapping("/gestionAdicionalEC")
public class P67GestionAdicionalECController {
	
	@Autowired
	private PedidoAdicionalECService pedidoAdicionalECService;
	
	@RequestMapping(value = "/loadGestionAdicionalEC", method = RequestMethod.POST)
	public  @ResponseBody PedidoAdicionalEC loadGestionAdicionalEC(
			@RequestBody PedidoAdicionalEC pedidoAdicionalEC,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
			PedidoAdicionalEC resultado = new PedidoAdicionalEC();
			
			try {
				//Volvemos a obtener la lista
				resultado = this.pedidoAdicionalECService.obtenerTablaSesionECRegistro(session.getId(), pedidoAdicionalEC.getLocalizador());
			} catch (Exception e) {
				//Si ha fallado algo de la llamada al WS debemos enviar un error al JSP para que lo muestre por pantalla.
				resultado.setCodigoError(new Long("1"));
				return resultado;
			}
			
			if (resultado == null)
			{
				//No se ha encontrado la referencia, tenemos que devolver un resultado de que no existe la referencia.
				resultado = new PedidoAdicionalEC();
				resultado.setCodigoError(new Long("1"));
				return resultado;
			}
			resultado.setCodigoError(new Long("0"));
			
			return resultado;
	}
}