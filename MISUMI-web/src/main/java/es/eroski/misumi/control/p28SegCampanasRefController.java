package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.SeguimientoCampanasDetalle;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VMisCampanaOfer;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VMisCampanaOferService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/segCampanas/referencias")
public class p28SegCampanasRefController {
	
	private static Logger logger = Logger.getLogger(p28SegCampanasRefController.class);

	private PaginationManager<SeguimientoCampanasDetalle> paginationManager = new PaginationManagerImpl<SeguimientoCampanasDetalle>();
	
	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	@Autowired
	private VMisCampanaOferService vMisCampanaOferService;
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;

	@Autowired
	private ExcelManager excelManager;
	@Resource
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {

		return "p28_popupRefCampanas";
	}
	
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody Page<SeguimientoCampanasDetalle> loadDataGrid(
			@RequestBody SeguimientoCampanas seguimientoCampanas,
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
			List<SeguimientoCampanasDetalle> list = null;
			VMisCampanaOfer vMisCampanaOfer = null;
			
			try {
				Long nivel = (long) 0;
				String identificador = null;
				Long anoOferta = null;
				Long numOferta = null;
				
				if (Constantes.SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_CAMPANA.equals(seguimientoCampanas.getTipoOC())){
					identificador = (seguimientoCampanas.getCampana() != null && !"".equals(seguimientoCampanas.getCampana())?seguimientoCampanas.getCampana():null);
				}else{
					String oferta = (seguimientoCampanas.getOferta() != null && !"".equals(seguimientoCampanas.getOferta())?seguimientoCampanas.getOferta():null);
					if (oferta != null){
						anoOferta =((oferta != null && !"".equals(oferta))?new Long(oferta.substring(0, oferta.indexOf("-"))):null);
						numOferta = ((oferta != null && !"".equals(oferta))?new Long(oferta.substring(oferta.indexOf("-") + 1)):null);
					}
				}
			    if (seguimientoCampanas.getCodSubcategoria() != null) { nivel = (long) 3; }
			    else if (seguimientoCampanas.getCodCategoria() != null) { nivel = (long) 2; }
			    else if (seguimientoCampanas.getCodSeccion() != null) { nivel = (long) 1; }
			    else if (seguimientoCampanas.getCodArea() != null) { nivel = (long) 0; }
			    
				Date fechaInicioDate = ((seguimientoCampanas.getFechaInicioDDMMYYYY() != null && !"".equals(seguimientoCampanas.getFechaInicioDDMMYYYY()))? Utilidades.convertirStringAFecha(seguimientoCampanas.getFechaInicioDDMMYYYY()):null);
				Date fechaFinDate = ((seguimientoCampanas.getFechaFinDDMMYYYY() != null && !"".equals(seguimientoCampanas.getFechaFinDDMMYYYY()))? Utilidades.convertirStringAFecha(seguimientoCampanas.getFechaFinDDMMYYYY()):null);

			    vMisCampanaOfer = new VMisCampanaOfer(session.getId(),
			    		nivel, null,
						null, identificador, seguimientoCampanas.getCodCentro(),
						seguimientoCampanas.getCodArea(), seguimientoCampanas.getCodSeccion(), seguimientoCampanas.getCodCategoria(), seguimientoCampanas.getCodSubcategoria(),
						null, seguimientoCampanas.getTipoOC(), Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA, seguimientoCampanas.getCodArt(),
						anoOferta, numOferta, fechaInicioDate, fechaFinDate,
						null, null, null, null,
						null, null, null,
						null, null);
			    
			    list = this.vMisCampanaOferService.findAllReferenciasCampanaOferPopup(vMisCampanaOfer, pagination);
				
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			Page<SeguimientoCampanasDetalle> result = null;
			
			if (list != null) {
				int records = this.vMisCampanaOferService.findAllReferenciasCampanaOferPopupCont(vMisCampanaOfer).intValue();
				result = this.paginationManager.paginate(new Page<SeguimientoCampanasDetalle>(), list,
						max.intValue(), records, page.intValue());	
				
			} else {
				return new Page<SeguimientoCampanasDetalle>();
			}
			 return result;
	}

	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody
	void exportGrid(
			@RequestParam(required = false) String[] headers,
			@RequestParam(required = false) String[] model,
			@RequestParam(required = false) Long codCentro,
			@RequestParam(required = false) String campana,
			@RequestParam(required = false) String campanaCompleta,
			@RequestParam(required = false) String oferta,
			@RequestParam(required = false) String ofertaCompleta,
			@RequestParam(required = false) String tipoOC,
			@RequestParam(required = false) Long codArea,
			@RequestParam(required = false) Long codSeccion,
			@RequestParam(required = false) Long codCategoria,
			@RequestParam(required = false) Long codSubcategoria,
			@RequestParam(required = false) Long codSegmento,
			@RequestParam(required = false) Long codArt,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {
		    List<GenericExcelVO> list = new ArrayList<GenericExcelVO>();
		    User user = (User) session.getAttribute("user");

		    VAgruComerRef vAgruComerGrupoArea=null;
		    VAgruComerRef vAgruComerGrupoSection=null;
		    VAgruComerRef vAgruComerGrupoCategory=null;
		    VAgruComerRef vAgruComerGrupoSubcategory=null;
		    VAgruComerRef vAgruComerGrupoSegmento=null;
		    
		    Long nivel = (long) 0;
			String identificador = null;
			Long anoOferta = null;
			Long numOferta = null;

			Date fechaInicioDate = null;
			Date fechaFinDate = null;
			
			String textil = null;

			if (Constantes.SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_CAMPANA.equals(tipoOC)){
				identificador = (campana != null && !"".equals(campana)?campana:null);
				String [] campanaCompletaConFechas = campanaCompleta.split("\\*\\*");
				campanaCompleta = campanaCompletaConFechas[0];
				fechaInicioDate = ((campanaCompletaConFechas[1] != null && !"".equals(campanaCompletaConFechas[1]))? Utilidades.convertirStringAFecha(campanaCompletaConFechas[1]):null);
				fechaFinDate = ((campanaCompletaConFechas[2] != null && !"".equals(campanaCompletaConFechas[2]))? Utilidades.convertirStringAFecha(campanaCompletaConFechas[2]):null);
						
			}else{
				String ofertaCompuesta = (oferta != null && !"".equals(oferta)?oferta:null);
				String [] ofertaCompletaConFechas = ofertaCompleta.split("\\*\\*");
				ofertaCompleta = ofertaCompletaConFechas[0];
				fechaInicioDate = ((ofertaCompletaConFechas[1] != null && !"".equals(ofertaCompletaConFechas[1]))? Utilidades.convertirStringAFecha(ofertaCompletaConFechas[1]):null);
				fechaFinDate = ((ofertaCompletaConFechas[2] != null && !"".equals(ofertaCompletaConFechas[2]))? Utilidades.convertirStringAFecha(ofertaCompletaConFechas[2]):null);

				if (ofertaCompuesta != null){
					anoOferta =((ofertaCompuesta != null && !"".equals(ofertaCompuesta))?new Long(ofertaCompuesta.substring(0, ofertaCompuesta.indexOf("-"))):null);
					numOferta = ((ofertaCompuesta != null && !"".equals(ofertaCompuesta))?new Long(ofertaCompuesta.substring(ofertaCompuesta.indexOf("-") + 1)):null);
				}
			}
		    
		    if (codSubcategoria != null) { nivel = (long) 3; }
		    else if (codCategoria != null) { nivel = (long) 2; }
		    else if (codSeccion != null) { nivel = (long) 1; }
		    else if (codArea != null) { nivel = (long) 0; }
		    
		    VMisCampanaOfer vMisCampanaOfer = new VMisCampanaOfer(session.getId(),
		    		nivel, null,
					null, identificador, codCentro,
					codArea, codSeccion, codCategoria, codSubcategoria,
					null, tipoOC, Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA, codArt,
					anoOferta, numOferta, fechaInicioDate, fechaFinDate,
					null, null, null, null,
					null, null, null,
					null, null);
		   
		    
		    if (codArea!=null){
		    	vAgruComerGrupoArea=this.vAgruComerRefService.findAll(new VAgruComerRef("I1",codArea,null,null,null,null,null), null).get(0);
		    	//para textil - fomrato de plantilla excell
		    	if(codArea==3)
		    	{
		    	  textil = "textil";
		    	}
		    }
		    
		    if (codSeccion!=null){
		    	vAgruComerGrupoSection = this.vAgruComerRefService.findAll(new VAgruComerRef("I2",codArea,codSeccion,null,null,null,null), null).get(0);
		    }
		    if (codCategoria!=null){
		    	vAgruComerGrupoCategory =this.vAgruComerRefService.findAll(new VAgruComerRef("I3",codArea,codSeccion,codCategoria,null,null,null), null).get(0);
		    }	
		    if (codSubcategoria!=null){
		    	vAgruComerGrupoSubcategory =this.vAgruComerRefService.findAll(new VAgruComerRef("I4",codArea,codSeccion,codCategoria,codSubcategoria,null,null), null).get(0);
		    }	
		    if (codCategoria!=null){
		    	vAgruComerGrupoSegmento =this.vAgruComerRefService.findAll(new VAgruComerRef("I5",codArea,codSeccion,codCategoria,codSubcategoria,codSegmento,null), null).get(0);
		    }	
		    
		    //Comprobación de la parametrización del centro para realizar la búsqueda de las UC
		    //Si en la parametrización aparece indicado el centro como CAMPAÑA no se mostrarán las columnas de cajas en el excel
		    boolean mostrarColumnasSegCamp = true;
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(codCentro);
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
			if (paramCentroOpciones != null && 
					paramCentroOpciones.getOpcHabil() != null && 
					paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.CAJAS_CAMPANA_14) != -1){ //Existen opciones habilitadas para el centro
				mostrarColumnasSegCamp = false;
			}

			try {
				logger.info("Exporting excel Referencias de una campaña:"+model.toString());
			    
				//String empujeLabel = messageSource.getMessage("p28_popUpRefCampanas.empuje", null, LocaleContextHolder.getLocale());
				String empujeLabel = "";
				list = this.vMisCampanaOferService.findAllReferenciasCampanaOferExcel(vMisCampanaOfer, model, empujeLabel, textil, mostrarColumnasSegCamp);
				
			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}
			
			this.excelManager.exportMisCampanaOfer(list, headers, model, this.messageSource, user.getCentro(), campana, campanaCompleta, oferta, ofertaCompleta, tipoOC, vAgruComerGrupoArea, vAgruComerGrupoSection, vAgruComerGrupoCategory, vAgruComerGrupoSubcategory, vAgruComerGrupoSegmento, textil, mostrarColumnasSegCamp, response);

		}catch(Exception e) {
			
					//logger.error(StackTraceManager.getStackTrace(e));
				    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			        throw e;
		}
			
	}
}