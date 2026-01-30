package es.eroski.misumi.control;

import java.text.Normalizer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.CategoriaBean;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.PopUp34ViewerManagerBean;
import es.eroski.misumi.model.QueryReferenciasByDescr;
import es.eroski.misumi.model.ReferenciasByDescr;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.ReferenciasSelectService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;

/**
 * Controller para cargar las busquedas alfanumericas y alfabeticas en las busquedas de referencias:
 * Informacion basica de referencias (desde header)
 * Consulta datos referencia
 * Nuevo pedidos adicionales
 * 
 * P49634
 * @author BICUGUAL
 */
@Controller
@RequestMapping("/seleccionReferencia")
public class P34SeleccionarReferenciasController {

	private static Logger logger = Logger.getLogger(P34SeleccionarReferenciasController.class);
	
	@Autowired
	private ReferenciasSelectService referenciaSelectService;
	
	private PaginationManager<ReferenciasByDescr> paginationManager = new PaginationManagerImpl<ReferenciasByDescr>();
	
	@Autowired
	private FotosReferenciaService fotosReferenciaService;
	
	/**
	 * Realiza una consulta con los datos de la busqueda introducidos para devolver el numero de registros encontrados
	 * 
	 * @param codSeccion
	 * @param descripcion
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/countAllReferencias", method = RequestMethod.GET)
	public @ResponseBody Long getTotalRegistrosConsulta(
			@RequestParam(value = "codSeccion", required = false) Long codSeccion,
			@RequestParam(value = "descripcion", required = false, defaultValue = "")  String descripcion,
			@RequestParam(value = "altaCatalogo", required = false, defaultValue = "N")  String altaCatalogo,
			@RequestParam(value = "activo", required = false, defaultValue = "N")  String activo,
			HttpServletResponse response, HttpSession session) throws Exception{
		
		User usuario= (User)session.getAttribute("user");
		descripcion = Utilidades.normalizar(descripcion);
		QueryReferenciasByDescr filtros=
				new QueryReferenciasByDescr(usuario.getCentro().getCodCentro(),descripcion,codSeccion,null);
		if (altaCatalogo.equals("S")){
			filtros.setAltaCatalogo(true);
		}
		
		if (activo.equals("S")){
			filtros.setActivo(true);
		}
		Long tamConsulta=new Long(0);
		
		try {
			tamConsulta=this.referenciaSelectService.findAllCount(filtros).getFilasGrid();
				
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}

		return tamConsulta;
	}
	
	/**
	 * Devuelve todos las referencias para un los parametros recibidos. Busqueda por descripcion.
	 * 
	 * @param codSeccion
	 * @param descripcion
	 * @param page
	 * @param max
	 * @param index
	 * @param sortOrder
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<ReferenciasByDescr> loadDataGrid(@RequestBody QueryReferenciasByDescr filtros,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		logger.debug("Entra en loadDataGrid");
		
		Pagination pagination= new Pagination(max,page);
		User usuario= (User)session.getAttribute("user");
				
		if (null != index && !index.equals("null")){
			pagination.setSort(index);
        }
        
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
        }
		
		//Busqueda por MODELO PROEVEEDOR;
		logger.debug("BUSQUEDA POR MODELO PROVEEDOR");
		String descripcion = filtros.getDescripcion();
		descripcion = Utilidades.normalizar(descripcion);
		filtros.setDescripcion(descripcion);
//		filtros.setModeloProveedor(true);
		filtros.getEan();
		filtros.getAlfaNumerico();
		filtros.getModeloProveedor();
		List<ReferenciasByDescr> list = null;
		Page<ReferenciasByDescr> result = null;
		
		//En caso de tengo codSeccion recupero su Grupo1 para saber si es TEXTIL o no
		if (filtros.getCodSeccion()!=null && filtros.getCodSeccion()!=0){
			try {	
				List<Long> area = this.referenciaSelectService.findArea(filtros);
				filtros.setCodArea(area.get(0).longValue());
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
		}
		
		//Busqueda ALFANUMERICA
		if (filtros.getAlfaNumerico()){
			
			try {
				
				list = this.referenciaSelectService.findAll(filtros,pagination);
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			if (list.size()!=0) {
				//Comprobar si tiene Foto
				FotosReferencia fotosReferencia = new FotosReferencia();
				for(ReferenciasByDescr ref:list){					
					fotosReferencia.setCodReferencia(ref.getCodArticulo());
					if (fotosReferenciaService.checkImage(fotosReferencia)){
						ref.setTieneFoto("S");
					} else {
						ref.setTieneFoto("N");
					}
				}
				
				//Obtengo el numero total del registros mediante otra consulta porque la lista de referencias obtenida está ya paginada y no incluye todos los registros.
				int records = this.referenciaSelectService.findAllCount(filtros).getFilasGrid().intValue();
		
				result = this.paginationManager.paginate(new Page<ReferenciasByDescr>(), list,
					max.intValue(), records, page.intValue());	
			} 
			else {
				return new Page<ReferenciasByDescr>();
			}
			
		}else{
			List<ReferenciasByDescr> ean=null;
		    //Busqueda NUMERICA (EAN o REFERENCIA)
			if(filtros.getEan()){
				try {
					 ean = this.referenciaSelectService.findEAN(filtros);
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		
			//Si existe como EAN se recupera la referencia
			if(ean != null && ean.size() > 0 ){
				descripcion = ean.get(0).getCodArticulo().toString();
				filtros.setDescripcion(ean.get(0).getCodArticulo().toString());
			}
			
			//Obtengo el numero total del registros mediante otra consulta porque la lista de referencias obtenida estÃ¡ ya paginada y no incluye todos los registros.
			try {
				list = this.referenciaSelectService.findAll(filtros, pagination);
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			if (list != null && list.size()!=0) {
				//Comprobar si tiene Foto
				FotosReferencia fotosReferencia = new FotosReferencia();
				for(ReferenciasByDescr ref:list){					
					fotosReferencia.setCodReferencia(ref.getCodArticulo());
					if (fotosReferenciaService.checkImage(fotosReferencia)){
						ref.setTieneFoto("S");
					} else {
						ref.setTieneFoto("N");
					}
				}
//				filtros.setModeloProveedor(false); 
	
//				filtros.setCodArticulo(descripcion);
//				filtros.setDescripcion(list.get(0).getDescripcion());				
				
				//Obtengo el numero total del registros mediante otra consulta porque la lista de referencias obtenida estÃ¡ ya paginada y no incluye todos los registros.
				int records = this.referenciaSelectService.findAllCount(filtros).getFilasGrid().intValue();
	
				result = this.paginationManager.paginate(new Page<ReferenciasByDescr>(), list,
					max.intValue(), records, page.intValue());	
			} 
			else {
				
				if ((null == ean) || (ean.size() == 0)){
					
					//MISUMI-517 - Si la descripcion es numerica y muy larga, cambiamos por el numero recortado 
					//para evitar que nos saque de la aplicacion 
					//al introducir un numero demasiado largo (A peticion de Maria)
					//ya que no existen referencias ni ean tan largos

					String recortada="";
					if(filtros.getDescripcion().length()>18){ 
						recortada=filtros.getDescripcion().substring(0, 17);
						filtros.setDescripcion(recortada); 
					} 
					
					logger.debug("NO EXISTEN DATOS");
					ReferenciasByDescr ref = new ReferenciasByDescr();
					ref.setCodArticulo(Long.valueOf(filtros.getDescripcion()));
					ref.setNivel_lote(Long.valueOf("0"));
					list.add(ref);
					result = this.paginationManager.paginate(new Page<ReferenciasByDescr>(), list,
							max.intValue(), 1, page.intValue());	
				} else {
					//return new Page<ReferenciasByDescr>();
					
					//No existen datos pero la coversionde EAN a codArticulo existe. PAsamos el codArticulo para que a partir de aquisiga el mismo camino que el codArticulo
					ReferenciasByDescr ref = new ReferenciasByDescr();
					ref.setCodArticulo(ean.get(0).getCodArticulo());
					ref.setNivel_lote(Long.valueOf("0"));
					list.add(ref);
					result = this.paginationManager.paginate(new Page<ReferenciasByDescr>(), list,
					max.intValue(), 1, page.intValue());	
				}
				
			}
		}
		
		result.getRows().get(0).setPaginaConsulta(filtros.getPaginaConsulta());
		logger.debug("Fin de loadDataGrid");	
		return result;		
	}
	
	@RequestMapping(value = "/loadDataSubGridTextil", method = RequestMethod.POST)
	public  @ResponseBody Page<ReferenciasByDescr> loadDataSubGridTextil(
			@RequestBody QueryReferenciasByDescr referenciaByDescr,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
    
		    
			List<ReferenciasByDescr> list = null;
			
			list = this.referenciaSelectService.findAllTextilN2ByLote(referenciaByDescr);
			
			
			Page<ReferenciasByDescr> result = null;
			
			if (list.size() > 0) {
				
				int records = list.size();
			
				
				result = this.paginationManager.paginate(new Page<ReferenciasByDescr>(), list,
						list.size(), records, list.size());	
				
			} else {
				return new Page<ReferenciasByDescr>();
			}

			 return result;
	}
	
	/**
	 * Devuelve la lista de options a ser cargados en el combo de secciones
	 * 
	 * @param codSeccion
	 * @param descripcion
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/loadCombo", method = RequestMethod.POST)
//	public @ResponseBody List<OptionSelectBean> loadCombo(@RequestBody QueryReferenciasByDescr filtros,
//			HttpServletResponse response,
//			HttpSession session) throws Exception{
//		
//		logger.debug("Entra en loadCombo");
//		
//		User usuario= (User)session.getAttribute("user");
//		
//		String descripcion = filtros.getDescripcion();
//		descripcion = Normalizer.normalize(descripcion, Normalizer.Form.NFD);
//		descripcion = descripcion.replaceAll("[^\\p{ASCII}]", "");
//		filtros.setDescripcion(descripcion);
//		List<OptionSelectBean> lstOptionSelectBean= referenciaSelectService.findLstOptions(filtros);
//		
//		logger.debug("Fin de loadCombo");
//		
//		return lstOptionSelectBean;
//	}
	
	@RequestMapping(value = "/loadCombo", method = RequestMethod.POST)
	public @ResponseBody PopUp34ViewerManagerBean loadCombo(@RequestBody QueryReferenciasByDescr filtros,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		
		
		PopUp34ViewerManagerBean management= null;
		 try {
			String descripcion = filtros.getDescripcion();
			descripcion = Normalizer.normalize(descripcion, Normalizer.Form.NFD);
			descripcion = descripcion.replaceAll("[^\\p{ASCII}]", "");
			filtros.setDescripcion(descripcion);
			management= referenciaSelectService.findLstOptions(filtros);
		
		 } catch (Exception e) {
	        	logger.error("######################## Error  ############################");
	    		logger.error("Referencia: " + filtros.getCodArticulo() );
	    		logger.error("############################################################");
	    		
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	throw e;
	     }
		
		return management;
	}
	
	@RequestMapping(value = "/loadCategoria", method = RequestMethod.POST)
	public @ResponseBody List<CategoriaBean> loadCategoria(@RequestBody QueryReferenciasByDescr filtros,
			HttpServletResponse response,
			HttpSession session) throws Exception{		
		
		List<CategoriaBean> lstCategoria= null;
		 try {
			String descripcion = filtros.getDescripcion();
			descripcion = Normalizer.normalize(descripcion, Normalizer.Form.NFD);
			descripcion = descripcion.replaceAll("[^\\p{ASCII}]", "");
			filtros.setDescripcion(descripcion);
			filtros.setFlgCategoria("S");
			
			//En caso de tengo codSeccion recupero su Grupo1 para saber si es TEXTIL o no
			if (filtros.getCodSeccion()!=null && filtros.getCodSeccion()!=0){
				try {	
					List<Long> area = this.referenciaSelectService.findArea(filtros);
					filtros.setCodArea(area.get(0).longValue());
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
			
			lstCategoria= referenciaSelectService.findLstCategoria(filtros);
		
		 } catch (Exception e) {
	        	logger.error("######################## Error  ############################");
	    		logger.error("Referencia: " + filtros.getCodArticulo() );
	    		logger.error("############################################################");
	    		
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	throw e;
	     }
		
		return lstCategoria;
	}
	
	@RequestMapping(value = "/loadArea", method = RequestMethod.POST)
	public  @ResponseBody Long loadArea(
			@RequestBody QueryReferenciasByDescr filtros,
			HttpServletResponse response,
			HttpSession session) throws Exception{
  			
			List<Long> area = null;
			try {	
				area = this.referenciaSelectService.findArea(filtros);
				filtros.setCodArea(area.get(0).longValue());
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			return area.get(0).longValue();
	}

}
