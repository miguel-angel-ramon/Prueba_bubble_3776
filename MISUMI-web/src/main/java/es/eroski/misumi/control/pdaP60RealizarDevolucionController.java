package es.eroski.misumi.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionEstado;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.RefAsociadasServiceImpl;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
public class pdaP60RealizarDevolucionController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP60RealizarDevolucionController.class);

	@Autowired
	private DevolucionService devolucionesService;

	@Autowired
	private RefAsociadasServiceImpl refAsociadasServiceImpl;

	@RequestMapping(value = "/pdaP60RealizarDevolucion",method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		String resultado = "pda_p60_realizarDevolucion";

		try{
			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();

			//Si se redirecciona a P60, significa que ha encontrado devoluciones, por lo que hay que enseñarlas.
			boolean redireccionadoAP60 = true;

			// Llamada para conseguir las devoluciones. 
			Devolucion devolucion = new Devolucion();
			devolucion.setCentro(codCentro);
			devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);
			devolucion.setEstadoCab(Constantes.DEVOLUCIONES_PDA_DEVOLUCIONES_PENDIENTES_ESTADO);

			//Se obtiene lista de devoluciones después de la llamada al servicio
			// Se debe invocar SIEMPRE, ya que puede que se haya FINALIZADO alguna de las devoluciones
			// y no debe aparecer en el listado de devoluciones pendientes de revisar.
			DevolucionCatalogoEstado listaDevolucion = devolucionesService.cargarCabeceraDevoluciones(devolucion);

			List<DevolucionEstado> listDevolucionEstado = null;
			List<Devolucion> listDevolucion = null;
			if (listaDevolucion != null && listaDevolucion.getpCodError() == 0){
				listDevolucionEstado = listaDevolucion.getListDevolucionEstado();
				if(listDevolucionEstado.size() > 0){
					if(listDevolucionEstado != null && listDevolucionEstado.get(0) != null && listDevolucionEstado.get(0).getListDevolucion()!=null){
						listDevolucion = listDevolucionEstado.get(0).getListDevolucion();
						if(listDevolucion != null){
							if(listDevolucion.size() < 1){
								resultado = "pda_p66_noHayDevolucionesError";
								model.addAttribute("origenErrorDevolucion","pda_p60_realizarDevolucion");
							}
						}else{
							resultado = "pda_p66_noHayDevolucionesError";
							model.addAttribute("origenErrorDevolucion","pda_p60_realizarDevolucion");
						}
					}
				}else{
					resultado = "pda_p66_noHayDevolucionesError";
					model.addAttribute("origenErrorDevolucion","pda_p60_realizarDevolucion");
				}
				model.addAttribute("codError",listaDevolucion.getpCodError());
			}else{
				resultado = "pda_p66_noHayDevolucionesError";
				model.addAttribute("origenErrorDevolucion","pda_p60_realizarDevolucion");
				model.addAttribute("codError",listaDevolucion.getpCodError());
			}

			session.setAttribute("listaDevolucion", listDevolucion);
			session.setAttribute("existenDevoluciones", "false");

			//Inicialmente se pagina para mostrar los elementos de la primera página
			Page<Devolucion> pagDevolucion = this.paginarListaDevoluciones(session,"1","2","firstDev");

			model.addAttribute("pagDevolucion", pagDevolucion);
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			model.addAttribute("existeDevolucion", redireccionadoAP60);
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultado;
	}

	@RequestMapping(value = "/pdaP60RealizarDevolucion",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab,
			ModelMap model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "pda_p60_realizarDevolucion";
		boolean bolErrorEnCargaReferencia = false;
		try{
			User user = (User) session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();

			//Controlamos si existe la devolucion
			boolean existeDevolucion = true;

			// Llamada para conseguir las devoluciones. 
			Devolucion devolucion = new Devolucion();
			devolucion.setCentro(codCentro);
			devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);
			devolucion.setEstadoCab(Constantes.DEVOLUCIONES_PDA_DEVOLUCIONES_PENDIENTES_ESTADO);

			//Se mantiene el criterio de cabecera entre las diferentes pantallas de las devoluciones
			if (pdaDatosCab != null && pdaDatosCab.getCodArtCab() != null && !"".equals(pdaDatosCab.getCodArtCab().trim())){
				try{
					//Obtención de la referencia a filtrar
					PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
					String codigoError = pdaArticulo.getCodigoError();
					if (codigoError == null || codigoError.equals("0")){
						devolucion.setCodArticulo(new Long(pdaArticulo.getCodArt()));
					}else{
						bolErrorEnCargaReferencia = true;
					}
				}catch(Exception e){ //Si hay error en carga de referencia se devuelve la lista vacía
					bolErrorEnCargaReferencia = true;
				}
			}			

			if(devolucion.getCodArticulo() != null){
				//Obtenemos las referencias asociadas a una devolución
				//Y obtenemos la referencia madre
				RefAsociadas referenciasAsociadas = new RefAsociadas();
				referenciasAsociadas.setCodArticuloHijo(devolucion.getCodArticulo());
				List<RefAsociadas> refAsociadasLst = refAsociadasServiceImpl.findAll(referenciasAsociadas);
				
				if(refAsociadasLst != null && refAsociadasLst.size() > 0){
					devolucion.setCodArticulo(refAsociadasLst.get(0).getCodArticulo());
				}
			}

			List<Devolucion> listDevolucion = null;
			if (!bolErrorEnCargaReferencia){
				//Se obtiene lista de devoluciones después de la llamada al servicio
				DevolucionCatalogoEstado listaDevolucion = devolucionesService.cargarCabeceraDevoluciones(devolucion);
				List<DevolucionEstado> listDevolucionEstado = null;

				if (listaDevolucion != null && listaDevolucion.getpCodError() == 0){
					listDevolucionEstado = listaDevolucion.getListDevolucionEstado();
					if(listDevolucionEstado.size() > 0){
						if(listDevolucionEstado != null && listDevolucionEstado.get(0) != null && listDevolucionEstado.get(0).getListDevolucion()!=null){
							listDevolucion = listDevolucionEstado.get(0).getListDevolucion();
							if(listDevolucion != null){
								if(listDevolucion.size() < 1){
									existeDevolucion = false;
								}
							}else{
								existeDevolucion = false;
							}
						}
					}else{
						existeDevolucion = false;
					}
				}
			}			
			session.setAttribute("listaDevolucion", listDevolucion);

			//Inicialmente se pagina para mostrar los elementos de la primera página
			Page<Devolucion> pagDevolucion = this.paginarListaDevoluciones(session,"1","2","firstDev");

			model.addAttribute("pagDevolucion", pagDevolucion);
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			model.addAttribute("existeDevolucion", existeDevolucion);
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}		

		return resultado;
	}

	@RequestMapping(value = "/pdaP60Paginar", method = RequestMethod.GET)
	public String pdaP60Paginar(ModelMap model,
			HttpSession session,
			@Valid final String codArtCab,
			@Valid final String pgDev,
			@Valid final String pgTotDev,
			@Valid final String botPag) {

		String resultado = "pda_p60_realizarDevolucion";

		try{
			//Si se redirecciona a P60, significa que ha encontrado devoluciones, por lo que hay que enseñarlas.
			boolean redireccionadoAP60 = true;

			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(codArtCab);
			model.addAttribute("pdaDatosCab", pdaDatosCab);

			//En función del botón que se haya pulsado tendremos que obtener la sublista que corresponda.
			Page<Devolucion> pagDevolucion = this.paginarListaDevoluciones(session,pgDev,pgTotDev,botPag);

			model.addAttribute("pagDevolucion", pagDevolucion);
			model.addAttribute("existeDevolucion", redireccionadoAP60);

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultado;
	}

	private Page<Devolucion> paginarListaDevoluciones(HttpSession session, String pgDev, String pgTotDev, String botPag) throws Exception{

		List<Devolucion> listaDevolucion = (List<Devolucion>) session.getAttribute("listaDevolucion");

		Page<Devolucion> result = null;
		int records = 0;
		int page = 1;

		if (listaDevolucion!= null && listaDevolucion.size()>0) {

			records = listaDevolucion.size();
			page = Integer.parseInt(pgDev);
			int pageTot = Integer.parseInt(pgTotDev);

			if (botPag.equals("firstDev")){
				page = 1;
			}else if (botPag.equals("prevDev")){
				page = page -1;
			}else if (botPag.equals("nextDev")){
				page = page +1;
			}else if (botPag.equals("lastDev")){
				page = pageTot;
			}

			if (listaDevolucion.size()>Constantes.DEVOLUCIONES_PDA_PAGS_DEVOLUCIONES){
				listaDevolucion = this.obtenerSubLista(listaDevolucion,page,pageTot,records);
			}
		}

		if (listaDevolucion != null && listaDevolucion.size()>0) {
			PaginationManager<Devolucion> paginationManager = new PaginationManagerImpl<Devolucion>();
			result = paginationManager.paginate(new Page<Devolucion>(), listaDevolucion, Constantes.DEVOLUCIONES_PDA_PAGS_DEVOLUCIONES, records, page);	

		} else {
			return new Page<Devolucion>();
		}


		return result;
	}

	private List<Devolucion> obtenerSubLista(List<Devolucion> lista, int pag, int pagTot, int records) throws Exception{

		List<Devolucion> result = null;

		int inicio = Constantes.DEVOLUCIONES_PDA_PAGS_DEVOLUCIONES*(pag-1);

		int fin = 0;	
		if (pag == pagTot){
			fin = records;
		}else{
			fin = Constantes.DEVOLUCIONES_PDA_PAGS_DEVOLUCIONES*pag;
		}

		//Si el registro final calculado es mayor que el número de registros se corrige para hacerlo coincidir con el 
		//registro final
		if (fin>records){
			fin = records;
		}

		result = (lista).subList(inicio, fin);

		return result;
	}
}
