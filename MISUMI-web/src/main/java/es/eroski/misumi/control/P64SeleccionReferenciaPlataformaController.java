package es.eroski.misumi.control;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.EncargosClientePlataforma;
import es.eroski.misumi.model.EncargosClientePlataformaLista;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.ValidarReferenciaEncargo;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.EncargosClientePlataformaService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/seleccionReferenciaPlataforma")
public class P64SeleccionReferenciaPlataformaController {

	private static Logger logger = Logger.getLogger(P64SeleccionReferenciaPlataformaController.class);

	@Resource
	private MessageSource messageSource;
	
	@Autowired
	private EncargosClientePlataformaService encargosClientePlataformaService;

	@Autowired
	private FotosReferenciaService fotosReferenciaService;
	
	private PaginationManager<EncargosClientePlataforma> paginationManager = new PaginationManagerImpl<EncargosClientePlataforma>();

	@RequestMapping(value = "/loadReferencias", method = RequestMethod.POST)
	public @ResponseBody EncargosClientePlataformaLista loadReferencias(@RequestParam(value = "descripcion", required = false, defaultValue = "")  String descripcion,HttpSession session, HttpServletResponse response) throws Exception {


		try {
			User usuario= (User)session.getAttribute("user");
			
			EncargosClientePlataforma encargosCliente = new EncargosClientePlataforma();
			encargosCliente.setCodCentro(usuario.getCentro().getCodCentro());
			encargosCliente.setDescripcionArt(descripcion);
			encargosCliente.setIdSession(session.getId());
			EncargosClientePlataformaLista encargos = this.encargosClientePlataformaService.cargarReferencias(encargosCliente);
			if (descripcion.isEmpty()){
				encargos.setGenerico(true);
			} else {
				encargos.setGenerico(false);
			}
			encargos.getDatos().clear();
			return encargos;
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}

	@RequestMapping(value = "/loadSeccion", method = RequestMethod.POST)
	public @ResponseBody List<String> loadSeccion(HttpSession session, HttpServletResponse response) throws Exception {

		List<String> listaSeccion = null;
		try {
			
			EncargosClientePlataforma encargosCliente = new EncargosClientePlataforma();
			encargosCliente.setIdSession(session.getId());
			
			User user = (User) session.getAttribute("user");
			encargosCliente.setCodCentro(user.getCentro().getCodCentro());
			
			listaSeccion = this.encargosClientePlataformaService.getSecciones(encargosCliente);

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return listaSeccion;
	}
	


	@RequestMapping(value = "/loadCategoria", method = RequestMethod.POST)
	public @ResponseBody List<String> loadCategoria(@RequestParam String seccion,
			HttpServletResponse response, HttpSession session) throws Exception {

		List<String> listaCategoria = null;
		try {
			
			EncargosClientePlataforma encargosCliente = new EncargosClientePlataforma();
			encargosCliente.setIdSession(session.getId());
			encargosCliente.setSeccion(seccion);
			listaCategoria = this.encargosClientePlataformaService.getCategorias(encargosCliente);

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return listaCategoria;
	}
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<EncargosClientePlataforma> loadDataGrid(
			@RequestBody EncargosClientePlataforma encargosCliente,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder, 
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		   Pagination pagination= new Pagination(max,page);
	        if (index!=null){
	            pagination.setSort(index);
	        }
	        if (sortOrder!=null){
	            pagination.setAscDsc(sortOrder);
	        }
			List<EncargosClientePlataforma> list = null;
			
			try {
				User user = (User) session.getAttribute("user");
				encargosCliente.setCodCentro(user.getCentro().getCodCentro());
				encargosCliente.setIdSession(session.getId());
			    list = this.encargosClientePlataformaService.getReferencias(encargosCliente, pagination);
				
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			Page<EncargosClientePlataforma> result = null;
			
			if (!list.isEmpty()) {
				//Comprobar si tiene Foto
				FotosReferencia fotosReferencia = new FotosReferencia();
				for(EncargosClientePlataforma enCliPlat:list){					
					fotosReferencia.setCodReferencia(enCliPlat.getCodReferencia());
					if (fotosReferenciaService.checkImage(fotosReferencia)){
						enCliPlat.setTieneFoto("S");
					} else {
						enCliPlat.setTieneFoto("N");
					}
				}
				Long totalReg= this.encargosClientePlataformaService.getReferenciasCount(encargosCliente);
				result = this.paginationManager.paginate(new Page<EncargosClientePlataforma>(), list,
						max.intValue(), totalReg.intValue(), page.intValue());	
				
			} else {
				result = new Page<EncargosClientePlataforma>();
			}
			 return result;
	}
	
	@RequestMapping(value = "/validarReferencia", method = RequestMethod.POST)
	public @ResponseBody ValidarReferenciaEncargo validarReferencia(@RequestParam(value = "codArt", required = false)  Long codArt,HttpSession session, HttpServletResponse response) throws Exception {

		ValidarReferenciaEncargo referenciaEncargoRes = new ValidarReferenciaEncargo();
		try {
			User usuario= (User)session.getAttribute("user");
			
			ValidarReferenciaEncargo validarReferenciaEncargo = new ValidarReferenciaEncargo();
			validarReferenciaEncargo.setCodCentro(usuario.getCentro().getCodCentro());
			validarReferenciaEncargo.setCodReferencia(codArt);
			validarReferenciaEncargo.setGenerico("N");
			referenciaEncargoRes = this.encargosClientePlataformaService.validarReferencia(validarReferenciaEncargo, usuario.getCode());

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		
		return referenciaEncargoRes;
	}
	
	

}
