package es.eroski.misumi.control;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.AlarmaPLU;
import es.eroski.misumi.model.GenericExcelFieldsVO;
import es.eroski.misumi.model.PLU;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaResponseType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ReferenciaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ReferenciaResponseType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.AlarmasPLUService;
import es.eroski.misumi.service.iface.ImprimirEtiquetaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/alarmasPLU")
public class p107AlarmasPLUController {
	
	private static Logger logger = Logger.getLogger(p107AlarmasPLUController.class);
	
	@Autowired
	private AlarmasPLUService alarmasPLUService;
	
	@Autowired
	private ImprimirEtiquetaService imprimirEtiquetaService;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	@Autowired
	private ExcelManager excelManager;
	
	@Resource
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model, @RequestParam(required = false, defaultValue = "") String origenPantalla,HttpServletResponse response, HttpSession session) throws Exception {
		User usuario= (User)session.getAttribute("user");
		//Obtenemos el centro.
		if (usuario!=null && usuario.getCentro()!=null){
			Long codCentro = usuario.getCentro().getCodCentro();
			alarmasPLUService.initModifiedAlarmList(codCentro);
			return "p107_alarmasPLU";
		}else{
			return "p11_welcome";
		}
	}

	//Es la recarga del grid, al paginar o reordenar el grid
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.GET)
	public  @ResponseBody Page<AlarmaPLU> loadDataGrid(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "filtroReferencia", required = false) String filtroReferencia,
			@RequestParam(value = "codArea", required = false) Long codArea,
			@RequestParam(value = "codSeccion", required = false) Long codSeccion,
			@RequestParam(value = "agrupacion", required = false) Long agrupacion,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		logger.info("alarmasPLU - loadDataGrid - recarga = "+recarga);
		
		if (alarmasPLUService.getEstaCargando(codCentro)){
			return null;
		}
		
		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		List<AlarmaPLU> list = null;
		try {
			if (recarga.equalsIgnoreCase("N") && filtroReferencia==null){
				list = alarmasPLUService.findAll(pagination,codCentro,codArea,codSeccion,agrupacion, session);	
				alarmasPLUService.initModifiedAlarmList(codCentro, list);
			}else{
				list = alarmasPLUService.findAllInMemory(pagination,codCentro,codArea,codSeccion,agrupacion,filtroReferencia, true, session);	
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<AlarmaPLU> result = null;

		PaginationManager<AlarmaPLU> paginationManager = new PaginationManagerImpl<AlarmaPLU>();

		if (list != null && list.size() > 0) {
			int records = list.size();
			int desdeSubList = ((page.intValue()-1) * max.intValue());
			int hastaSubList = ((page.intValue())*max.intValue());

			if(hastaSubList > records){
				hastaSubList = records;				
			}

			List<AlarmaPLU> listaAMostrar = list.subList(desdeSubList,hastaSubList);

			result = paginationManager.paginate(new Page<AlarmaPLU>(), listaAMostrar, max.intValue(), records, page.intValue());
			
			final List<AlarmaPLU> lst = result.getRows();
			final List<AlarmaPLU> lstConPendienteRecibir = alarmasPLUService.setPendienteRecibir(lst);
			result.setRows(lstConPendienteRecibir);
			
		} else {
			return new Page<AlarmaPLU>();
		}
		return result;
	}

	@RequestMapping(value="/checkCargando", method = RequestMethod.GET)
	public @ResponseBody Boolean checkCargando(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			HttpSession session, HttpServletResponse response) throws Exception {
		return alarmasPLUService.getEstaCargando(codCentro);
	}

	@RequestMapping(value="/loadAreaData", method = RequestMethod.GET)
	public @ResponseBody Map<Long, String> loadAreaData(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - loadAreaData");
		try {
			if (alarmasPLUService.getEstaCargando(codCentro)){
				return new HashMap<Long, String>();
			}else{
				return alarmasPLUService.getAreasPLUs(codCentro);
			}
			
			
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	@RequestMapping(value="/asignar", method = RequestMethod.GET)
	public @ResponseBody void asignar(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "plu", required = true) Long plu,
			@RequestParam(value = "referencia", required = true) Long referencia,
			@RequestParam(value = "grupoBalanza", required = true) Long grupoBalanza,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - asignar - plu = "+plu+" - referencia = "+referencia);
		try {
			
			alarmasPLUService.asignar(codCentro,plu,referencia,grupoBalanza);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	@RequestMapping(value="/alta", method = RequestMethod.GET)
	public @ResponseBody String alta(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "plu", required = true) Long plu,
			@RequestParam(value = "referencia", required = true) Long referencia,
			@RequestParam(value = "grupoBalanza", required = true) Long grupoBalanza,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - alta - plu = "+plu+" - referencia = "+referencia+" - codCentro = "+codCentro+" - grupoBalanza = "+grupoBalanza);
		try {
			Long codAlarma=alarmasPLUService.altaReferenciaGisae(codCentro,plu,referencia,grupoBalanza);
			if(codAlarma!=null){
				return null;
			}else{
				return messageSource.getMessage("p107_alarmas_plu.msg_error_ref_no_esta", null, LocaleContextHolder.getLocale());
			}
			
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	@RequestMapping(value="/reasignar", method = RequestMethod.GET)
	public @ResponseBody void reasignar(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "plu", required = true) Long plu,
			@RequestParam(value = "ref1", required = true) Long ref1,
			@RequestParam(value = "ref2", required = true) Long ref2,
			@RequestParam(value = "grupoBalanza", required = true) Long grupoBalanza,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - asignar - plu = "+plu+" - ref1 = "+ref1+" - ref2 = "+ref2+" - grupoBalanza = "+grupoBalanza);
		try {
			
			alarmasPLUService.asignar(codCentro,plu,ref1,grupoBalanza);
			alarmasPLUService.asignar(codCentro,0L,ref2,grupoBalanza);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	@RequestMapping(value="/eliminar", method = RequestMethod.GET)
	public @ResponseBody void eliminar(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "grupoBalanza", required = true) Long grupoBalanza,
			@RequestParam(value = "referencias", required = true) Long[] referencias,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - eliminar");
		try {
			
			alarmasPLUService.eliminar(codCentro,referencias, grupoBalanza);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	@RequestMapping(value="/guardar", method = RequestMethod.GET)
	public @ResponseBody List<Long> guardar(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "agrupacionBalanza", required = true) Long agrupacionBalanza,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - guardar - codCentro = "+codCentro);
		try {
			User usuario = (User) session.getAttribute("user");
			String nombreUsuario = usuario.getCode();
			return alarmasPLUService.modificarDatosGisae(codCentro, agrupacionBalanza, nombreUsuario);
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
		
	}
	
	@RequestMapping(value="/guardarTodos", method = RequestMethod.GET)
	public @ResponseBody List<Long> guardarTodos(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "agrupacionBalanza", required = true) Long agrupacionBalanza,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - guardar - codCentro = "+codCentro);
		try {
			User usuario = (User) session.getAttribute("user");
			String nombreUsuario = usuario.getCode();
			return alarmasPLUService.modificarDatosGisaeTodos(codCentro, agrupacionBalanza, nombreUsuario);
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
		
	}

	@RequestMapping(value="/actualizarPLU", method = RequestMethod.POST)
	public @ResponseBody String updateMaxPLU(
			@RequestParam(value = "codCentro", required = true) int codCentro,
			@RequestParam(value = "agrupacionBalanza", required = true) int agrupacionBalanza,
			@RequestParam(value = "numMaxPLU", required = true) int numMaxPLU,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU -actualizarPLU - codCentro = "+codCentro+" agrupacionBalanza: "+agrupacionBalanza+" numMaxPLU: "+numMaxPLU);
		try {
			User usuario = (User)session.getAttribute("user");
			String codigoUsuario = usuario.getCode();
			PLU nuevoPLU = new PLU(codCentro, agrupacionBalanza, numMaxPLU, codigoUsuario, codigoUsuario);
			alarmasPLUService.updateMaxPLUS(nuevoPLU);
		} catch (DataAccessException dae){
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    return dae.getMessage();
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return Integer.toString(numMaxPLU);
		
	}
	
	// Dejo este cacho de código por aqui comentado por si algun dia se deciden a poner en produccion Misumi-36 y hay que sacar un listado de impresoras
	
//	@RequestMapping(value="/listadoImpresoras", method = RequestMethod.GET)
//	public @ResponseBody ListaImpresorasResponseType mostrarListadoImpresoras(
//			HttpSession session, HttpServletResponse response) throws Exception {
//		
//		//Obtenemos de sesión la información de usuario.
//		User user = (User)session.getAttribute("user");
//				
//		logger.debug("alarmasPLU - mostrarListadoImpresoras INICIO");
//		ListaImpresorasRequestType listaImpresorasRequestType = new ListaImpresorasRequestType();
//		listaImpresorasRequestType.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
//		// Como estamos en la web, metemos un ID de pistola aleatorio (1)
//		listaImpresorasRequestType.setIdPistola(BigInteger.ONE);
//		ListaImpresorasResponseType res = imprimirEtiquetaService.listaImpresorasWS(listaImpresorasRequestType);
//		System.out.println("CODIGO RESPUESTA: "+res.getCodigoRespuesta());
//		System.out.println("DESC RESPUESTA: "+res.getDescripcionRespuesta());
//		System.out.println("IMPRESORA PREDETERMINADA: "+res.getImpresoraPredeterminada());
//		for (String impresora : res.getImpresoras()){
//			System.out.println("IMPRESORA: "+impresora);
//		}
//		
//		logger.debug("alarmasPLU - mostrarListadoImpresoras FIN");
//		return res;
//	}
	
	@RequestMapping(value="/imprimir", method = RequestMethod.GET)
	public @ResponseBody ImprimirEtiquetaResponseType imprimir(
			@RequestParam(value = "etiquetas", required = true) Long[] etiquetas,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - imprimir");
		ImprimirEtiquetaResponseType imprimirEtiquetaResponse = null;
		try {

			ImprimirEtiquetaRequestType imprimirEtiquetarequest = imprimirEtiquetaService.createImprimirEtiquetaRequestType();
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");
			imprimirEtiquetarequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
			// Como estamos en la web, metemos un ID de pistola aleatorio (1)
			imprimirEtiquetarequest.setIdPistola(BigInteger.ONE);
			// Como estamos en la web, metemos un Buzón aleatorio (Buzón1)
			imprimirEtiquetarequest.setBuzon("Buzon1");
			
			ReferenciaRequestType[] referencias = new ReferenciaRequestType[etiquetas.length];
			for (int i=0; i<etiquetas.length; i++){
				referencias[i] = new ReferenciaRequestType();
				referencias[i].setCantidad(BigInteger.ONE);
				referencias[i].setCodigoReferencia(BigInteger.valueOf(etiquetas[i]));
			}
			imprimirEtiquetarequest.setReferencias(referencias);
					
			imprimirEtiquetaResponse = imprimirEtiquetaService.imprimirEtiquetaWS(imprimirEtiquetarequest);
	
			System.out.println("CODIGO RESPUESTA: "+imprimirEtiquetaResponse.getCodigoRespuesta());
			System.out.println("DESC RESPUESTA: - "+imprimirEtiquetaResponse.getDescripcionRespuesta());
			List<Long> referenciasLongList = new ArrayList<Long>();
			for (ReferenciaResponseType referencia : imprimirEtiquetaResponse.getReferencias()){
				System.out.println("REFERENCIA - CODIGO: "+referencia.getCodigoReferencia());
				System.out.println("REFERENCIA - COD ERROR: "+referencia.getCodigoError());
				System.out.println("REFERENCIA - MSG ERROR: "+referencia.getMensajeError());
				// Marcamos las referencias como ya imprimidas en la tabla Alarmas PLU
				if (referencia.getCodigoError().intValue()==0){
					System.out.println("REFERENCIA - MARCAR COMO IMPRIMIDA: "+referencia.getCodigoReferencia());
					referenciasLongList.add(referencia.getCodigoReferencia().longValue());
				}
			}
			
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    imprimirEtiquetaResponse.setCodigoRespuesta("KO");
		    imprimirEtiquetaResponse.setDescripcionRespuesta(e.getMessage());
		}	
		return imprimirEtiquetaResponse;
						
	}
	
	@RequestMapping(value="/loadSeccionData", method = RequestMethod.GET)
	public @ResponseBody Map<Long, String> loadSeccionData(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "codArea", required = true) Long codArea,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - loadSeccionData");
		try {
			
			return alarmasPLUService.getSeccionesPLUs(codCentro,codArea);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	@RequestMapping(value="/loadAgrupacionData", method = RequestMethod.GET)
	public @ResponseBody Map<Long, String> loadAgrupacionData(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "codArea", required = false) Long codArea,
			@RequestParam(value = "codSeccion", required = false) Long codSeccion,
			HttpSession session, HttpServletResponse response) throws Exception {
		logger.debug("alarmasPLU - loadAgrupacionData");
		try {
			if (alarmasPLUService.getEstaCargando(codCentro)){
				return new HashMap<Long, String>();
			}else{
				return alarmasPLUService.getAgrupacionesPLUs(codCentro,codArea,codSeccion);	
			}
			
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    throw e;
		}
	}
	
	
	@RequestMapping(value = "/loadContadores", method = RequestMethod.GET)
	public  @ResponseBody Integer[] loadContadores(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "codArea", required = false) Long codArea,
			@RequestParam(value = "codSeccion", required = false) Long codSeccion,
			@RequestParam(value = "agrupacion", required = false) Long agrupacion,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		logger.debug("alarmasPLU - loadContadores");
		Integer[] output;
		
		try {
			output = alarmasPLUService.counters(codCentro,codArea,codSeccion,agrupacion);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return output;
	}
	
	@RequestMapping(value = "/loadPLUsLibres", method = RequestMethod.GET)
	public  @ResponseBody List<Long> loadPLUsLibres(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "codArea", required = false) Long codArea,
			@RequestParam(value = "codSeccion", required = false) Long codSeccion,
			@RequestParam(value = "agrupacion", required = false) Long agrupacion,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		logger.debug("alarmasPLU - loadPLUsLibres");
		List<Long> output;
		
		try {
			output = alarmasPLUService.plusLibres(codCentro,null,null,agrupacion);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return output;
	}
	
	@RequestMapping(value = "/checkTeclasBalanza", method = RequestMethod.GET)
	public  @ResponseBody String checkTeclasBalanza(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "codArea", required = false) Long codArea,
			@RequestParam(value = "codSeccion", required = false) Long codSeccion,
			@RequestParam(value = "agrupacion", required = false) Long agrupacion,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		logger.debug("alarmasPLU - checkTeclasBalanza");
		String output = null;
		
		try {
			output = alarmasPLUService.checkTeclasBalanza(codCentro,codArea,codSeccion, agrupacion);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return output;
	}
	
	@RequestMapping(value = "/obtenerTipoPopupStock", method = RequestMethod.GET)
	public  @ResponseBody int obtenerTipoPopupStock(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "codArt", required = false) Long codArt,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		logger.debug("alarmasPLU - checkTeclasBalanza");
		int output = 0;
		
		try {
			output = alarmasPLUService.obtenerTipoPopupStock(codCentro,codArt, session);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return output;
	}
	
	@RequestMapping(value = "/checkReferencia", method = RequestMethod.GET)
	public  @ResponseBody String checkReferencia(
			@RequestParam(value = "codCentro", required = true) Long codCentro,
			@RequestParam(value = "grupoBalanza", required = true) Long grupoBalanza,
			@RequestParam(value = "referencia", required = true) Long referencia,			HttpServletResponse response,
			HttpSession session) throws Exception{

		logger.debug("alarmasPLU - checkReferencia");
		
		try {
			// 1 - Comprobar si la referencia existe
			final VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(referencia);
			
			final VDatosDiarioArt vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
			if (vDatosDiarioArtRes==null){
				return Constantes.ERROR_NO_EXISTE + "**" + messageSource.getMessage("p107_alarmas_plu.msg_error_ref_no_existe", null, LocaleContextHolder.getLocale());
			}
			
			// 2 - Comprobar si ya esta en la tabla intermedia (hay casos en los que la ref no se muestra)
			final AlarmaPLU alarma = alarmasPLUService.findOneInMemory(codCentro, referencia, grupoBalanza);
			if (alarma==null){
				// 3 - Si la alarma no esta en la tabla, vamos a buscarla al WS
				List<AlarmaPLU> alarmas = alarmasPLUService.obtenerDatosGisae(codCentro, referencia);
				if (alarmas!=null && alarmas.size()>0){
					for (AlarmaPLU alm : alarmas){
						final Long gb = alm.getGrupoBalanza();
						if (gb!=null && gb.equals(grupoBalanza) ){
							// Si encontramos la referencia en el grupo de balanza actual, la insertamos en la tabla intermedia
							List<AlarmaPLU> lista = new ArrayList<AlarmaPLU>();
							lista.add(alm);
							alarmasPLUService.insertarDatosEnTablaIntermedia(lista);
							// 2 - Comprobar si ya esta en la tabla intermedia (hay casos en los que la ref no se muestra)
							final AlarmaPLU alarmaModif = alarmasPLUService.findOneInModify(codCentro, referencia, grupoBalanza);
							if (alarmaModif==null){
								alarmasPLUService.aniadirAlarma(codCentro,alm);
							}
							//alarmasPLUService.initModifiedAlarmList(codCentro);
							return null;
						}
					}
				}
				
				return Constantes.ERROR_NO_ESTA_WS_GISAE + "**" + messageSource.getMessage("p107_alarmas_plu.msg_error_ref_no_esta", null, LocaleContextHolder.getLocale());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return null;
	}
	
	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public void exportGrid(
			@RequestParam(required = false) String[] headers,
			@RequestParam(required = false) String filtroReferencia,
			@RequestParam(required = false) Long centro,
			@RequestParam(required = false) Long area,
			@RequestParam(required = false) Long seccion,
			@RequestParam(required = false) Long agrupacion,
			@RequestParam(required = false) String centroStr,
			@RequestParam(required = false) String areaStr,
			@RequestParam(required = false) String seccionStr,
			@RequestParam(required = false) String agrupacionStr,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {
			logger.debug("alarmasPLU - exportGrid");
			Pagination pagination= new Pagination();
			if (index!=null){
				pagination.setSort(index);
			}
			if (sortOrder!=null){
				pagination.setAscDsc(sortOrder);
			}
			
			// Convertimos el nombre de campo vacio por # (el numero de fila)
			
			for (int i=0; i<headers.length; i++){
				String elem = headers[i];
				logger.debug("alarmasPLU - exportGrid - header = " + elem);
				if (elem.equals("")){
					headers[i]="#";
				}
			}
			
		    // Obtener el listado de elementos que se mostrará en el excel
		    List<GenericExcelFieldsVO> list = alarmasPLUService.findAllExcel(pagination, headers, centro,area,seccion,agrupacion, filtroReferencia);
		    
		    this.excelManager.exportAlarmasPLU(list, headers, this.messageSource, centroStr, areaStr, seccionStr, agrupacionStr, response);
		
		}catch(Exception e) {
     	    e.printStackTrace();
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        throw e;
		}
			
	}
	
	
}
