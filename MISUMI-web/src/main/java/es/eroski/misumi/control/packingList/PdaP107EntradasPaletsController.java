/**
 * Controller de la opción de "ENTRADA PALETS".
 */
package es.eroski.misumi.control.packingList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;

import es.eroski.misumi.exception.CustomException;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.model.pda.packingList.RdoRecepcionadoWrapper;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.packingList.iface.PaletService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.SessionUtils;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class PdaP107EntradasPaletsController{

	private static Logger logger = Logger.getLogger(PdaP107EntradasPaletsController.class);

	private ArrayList<String> listaBotones = new ArrayList<String>(Arrays.asList("firstPalet", "prevPalet", "nextPalet", "lastPalet"));

	private final String resultadoOK = "/pda/packingList/pda_p107_entradasPalets";
	private final String resultadoKO = "/pda/packingList/pda_p111_errorPackingList";

	@Autowired
	PaletService paletService;
	
	@RequestMapping(value = "/pdaP107EntradasPalets", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "pgPalet", defaultValue = "1") int pgPalet,
			@RequestParam(value = "pgTotPalet", defaultValue = "2") int pgTotPalet,
			@RequestParam(value = "botPag", defaultValue = "firstPalet") String botPag) {

		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		
		boolean redireccionadoAP107 = false;// Si se redirecciona a P107, significa que ha encontrado
										    // entradas de palets, por lo que hay que mostrarlas.
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();

		SessionUtils.limpiarAtributosLista(session);

		try{
			List<Palet> listaPalets = paletService.getEntradasPalets(codCentro, user.getMac());
			session.setAttribute("listaPalets", listaPalets); // La lista almacenada en sesion se emplea en "paginarListaDatos()"

			if (!listaPalets.isEmpty() && listaPalets!= null){
				redireccionadoAP107 = true;
			}

			try{
				//Inicialmente se pagina para mostrar los elementos de la primera página
				Page<Palet> pagPalet = Paginate.paginarListaDatos(session, String.valueOf(pgPalet),
						String.valueOf(pgTotPalet), botPag, Constantes.DEVOLUCIONES_PDA_PAGS, listaBotones);

				model.addAttribute("existeEntradasPalets", redireccionadoAP107);
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pagPalet", pagPalet);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch (SQLException e){
//			logger.error(StackTraceManager.getStackTrace(e));
			model.addAttribute("mensajeError", "Error al acceder a los datos de BBDD.");
			return resultadoKO;
		}catch (Exception e){
			logger.error(StackTraceManager.getStackTrace(e));
			model.addAttribute("mensajeError", "ERROR en el servicio.");
			return resultadoKO;
		}

		return resultadoOK;
	}

	@RequestMapping(value = "/pdaP107EntradasPalets",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Palet paletRecepcionado = new Palet();
		List<Palet> listaPalets = new ArrayList<Palet>();
		
		boolean redireccionadoAP107 = false;// Si se redirecciona a P107, significa que ha encontrado
										    // entradas de palets, por lo que hay que mostrarlas.

		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		
		String matriculaInput = pdaDatosCab.getMatricula();
		
		try{
			// Verificar que el campo matricula de entrada está relleno.
			if (matriculaInput!= null && !matriculaInput.isEmpty() && !"".equals(matriculaInput) && matriculaInput.length()>0){

				// Comprobar si el primer carácter de la mátricula es el carácter "#".
				// Si es así indica que se ha introducido desde la pistola y hay 
				// que eliminar ese primer carácter.
				if (matriculaInput.startsWith(Constantes.REF_PISTOLA)){
					//Quitamos el primer dígito
					matriculaInput = matriculaInput.substring(1,matriculaInput.length());
				}

				// 1º Recepcionar Palet con el API.
				RdoRecepcionadoWrapper rdoRecepcionadoPalet = paletService.recepcionarPalet(matriculaInput, (String)session.getAttribute("X-APP-KEY"));
				
				// Si PALET RECEPCIONADO CORRECTAMENTE, se almacenará en BBDD la entrada del palet.
				if (rdoRecepcionadoPalet.getRdoRecepcionadoOK().getTipoRespuesta()!= null){
					
					paletRecepcionado = rdoRecepcionadoPalet.getRdoRecepcionadoOK().getResultado();
					
					// 2º Grabar en BBDD la nueva "Entrada de Palet".
					paletService.saveEntradaPalet(paletRecepcionado, user.getMac());

					// 3º Recuperar las Entradas de Palets registradas en el día de HOY para mostrarlas en pantalla.
					// Accede a la tabla T_MIS_ENTRADAS_PALETS.
					listaPalets = paletService.getEntradasPalets(codCentro, user.getMac());
					session.setAttribute("listaPalets", listaPalets);
					
					if (!listaPalets.isEmpty() && listaPalets!= null){
						redireccionadoAP107 = true;
					}

					//Paginación a la primera página.
					Page<Palet> pagPalet = Paginate.paginarListaDatos(session, "1", "2", "firstPalet", Constantes.DEVOLUCIONES_PDA_PAGS, listaBotones);

					model.addAttribute("existeEntradasPalets", redireccionadoAP107);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					model.addAttribute("pagPalet", pagPalet);
				}else{
					model.addAttribute("mensajeError", rdoRecepcionadoPalet.getRdoRecepcionadoKO().getResultado());
					model.addAttribute("controlVolver", "pdaP107EntradasPalets.do");
					return resultadoKO;
				}
			}else{
				throw new CustomException("El campo matr\u00EDcula debe estar informado.");
			}

		}catch (CustomException e){
			model.addAttribute("mensajeError", e.getMessage());
			return resultadoKO;
		}catch (HttpServerErrorException e){
//			logger.error(StackTraceManager.getStackTrace(e));
			model.addAttribute("mensajeError", e.getMessage());
			return resultadoKO;
		}catch (Exception e){
			logger.error(StackTraceManager.getStackTrace(e));
			model.addAttribute("mensajeError", "ERROR en el servicio.");
			return resultadoKO;
		}finally{
			model.addAttribute("controlVolver", "pdaP107EntradasPalets.do");
		}

		return resultadoOK;
	}

	@RequestMapping(value = "/pdaP107Paginar", method = RequestMethod.GET)
	public String pdaP107Paginar(ModelMap model,
			HttpSession session,
			@Valid final String pgPalet,
			@Valid final String pgTotPalet,
			@Valid final String botPag) {

		//Cargamos los datos de cabecera.
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();

		try{
			//Si se redirecciona a P107, significa que ha encontrado entradas de palets, por lo que hay que enseñarlas.
//			boolean redireccionadoAP107 = true;

			//En función del botón que se haya pulsado tendremos que obtener la sublista que corresponda.
			Page<Palet> pagPalet = Paginate.paginarListaDatos(session, pgPalet, pgTotPalet, botPag, Constantes.DEVOLUCIONES_PDA_PAGS, listaBotones);

			model.addAttribute("existeEntradasPalets", true);
			model.addAttribute("pagPalet", pagPalet);
			model.addAttribute("pdaDatosCab", pdaDatosCab);

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultadoOK;
	}

}