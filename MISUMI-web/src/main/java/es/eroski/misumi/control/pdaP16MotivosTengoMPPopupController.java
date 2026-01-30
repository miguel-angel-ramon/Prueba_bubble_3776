package es.eroski.misumi.control;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosPopupMotivosTengoMP;
import es.eroski.misumi.model.pda.PdaMotivo;
import es.eroski.misumi.model.pda.PdaMotivoTengoMP;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.MotivoTengoMuchoPocoService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
public class pdaP16MotivosTengoMPPopupController extends pdaConsultasController {

	private static Logger logger = Logger.getLogger(pdaP16MotivosTengoMPPopupController.class);

	@Autowired
	private MotivoTengoMuchoPocoService motivoTengoMuchoPocoService;
	
	@RequestMapping(value = "/pdaP16MotivosTengoMPPopup", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final String procede,
			@Valid final String muchoPoco,
			@Valid final Double stockAlto,
			@Valid final Double stockBajo,
			@Valid final Double stock) {
		
		try{
			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));
			
			//Obtenemos la descripción del artículo.
			VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);
			String descArt = "";
			if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null){
				descArt =vDatosDiarioArtResul.getDescripArt();
			}
			pdaDatosCab.setDescArtCab(descArt);
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();

			//Llamamos al método que se encarga obtener el objeto page de la lista de motivos de tengo mucho poco
			MotivoTengoMuchoPoco motivoTengoMuchoPoco = new MotivoTengoMuchoPoco();
			motivoTengoMuchoPoco.setCodCentro(codCentro);
			motivoTengoMuchoPoco.setCodArticulo(codArt);
			motivoTengoMuchoPoco.setIdSesion(session.getId()+ "_MOT_FFPP");
			motivoTengoMuchoPoco.setTipo(muchoPoco);
			motivoTengoMuchoPoco.setStockAlto(stockAlto);
			motivoTengoMuchoPoco.setStockBajo(stockBajo);
			motivoTengoMuchoPoco.setStock(stock);

			Page<PdaMotivoTengoMP> pagMotTengoMP = cargarMotivosTengoMP(session, motivoTengoMuchoPoco);
			
			PdaDatosPopupMotivosTengoMP pdaDatosPopupMotivosTengoMP = new PdaDatosPopupMotivosTengoMP();
			pdaDatosPopupMotivosTengoMP.setCodArt(codArt);
			pdaDatosPopupMotivosTengoMP.setDescArt(descArt);
			pdaDatosPopupMotivosTengoMP.setProcede(procede);
			pdaDatosPopupMotivosTengoMP.setFlgTipoListadoMotivos(muchoPoco);
			pdaDatosPopupMotivosTengoMP.setPagMotivosTengoMP(pagMotTengoMP);
			
			if (pagMotTengoMP != null && pagMotTengoMP.getRecords() != null &&  !pagMotTengoMP.getRecords().equals("0")){
				pdaDatosPopupMotivosTengoMP.setFlgMotivosTengoMP("S");
			}else{
				pdaDatosPopupMotivosTengoMP.setFlgMotivosTengoMP("N");
			}
			
			model.addAttribute("pdaDatosPopupMotivosTengoMP", pdaDatosPopupMotivosTengoMP);
		
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return "pda_p16_motivosTengoMPPopup";
	}
	
	@RequestMapping(value = "/pdaP16Paginar", method = RequestMethod.GET)
	public String pdaP14Paginar(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final String procede,
			@Valid final String muchoPoco,
			@Valid final String pgMot,
			@Valid final String pgTotMot,
			@Valid final String botPag) {
		
		
		try 
		{
			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));
			
			//Obtenemos la descripción del artículo.
			VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);
			String descArt = "";
			if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null)
			{
				descArt = vDatosDiarioArtResul.getDescripArt();
			}
			pdaDatosCab.setDescArtCab(descArt);
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			
			Page<PdaMotivoTengoMP> pagMotTengoMP = paginarListaMotTengoMP(session,pgMot,pgTotMot, botPag);
			
			PdaDatosPopupMotivosTengoMP pdaDatosPopupMotivosTengoMP = new PdaDatosPopupMotivosTengoMP();
			pdaDatosPopupMotivosTengoMP.setCodArt(codArt);
			pdaDatosPopupMotivosTengoMP.setDescArt(descArt);
			pdaDatosPopupMotivosTengoMP.setProcede(procede);
			pdaDatosPopupMotivosTengoMP.setPagMotivosTengoMP(pagMotTengoMP);
			
			if (pagMotTengoMP != null && pagMotTengoMP.getRecords() != null &&  !pagMotTengoMP.getRecords().equals("0"))
			{
				pdaDatosPopupMotivosTengoMP.setFlgMotivosTengoMP("S");
			}
			else
			{
				pdaDatosPopupMotivosTengoMP.setFlgMotivosTengoMP("N");
			}
			
			model.addAttribute("pdaDatosPopupMotivosTengoMP", pdaDatosPopupMotivosTengoMP);
		
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		
		
		return "pda_p16_motivosTengoMPPopup";
	}
	
	private Page<PdaMotivoTengoMP> cargarMotivosTengoMP(HttpSession session, MotivoTengoMuchoPoco motivoTengoMuchoPoco) throws Exception{
		
		Page<PdaMotivoTengoMP> result = null;
		List<PdaMotivoTengoMP> listaMotTMP = null;
		List<PdaMotivoTengoMP> sublistaMotTMP = null;
		
		try{
			//Carga de la sesion en el objeto de motivos
			MotivoTengoMuchoPocoLista motivoTengoMuchoPocoLista = this.motivoTengoMuchoPocoService.consultaMotivosTengoMuchoPoco(motivoTengoMuchoPoco);
			int posicionMot = 0;
			if (motivoTengoMuchoPocoLista != null && motivoTengoMuchoPocoLista.getDatos() != null && motivoTengoMuchoPocoLista.getDatos().size()>0){
				//Transformar lista del servicio a la lista simplificada de pda
				listaMotTMP = new ArrayList<PdaMotivoTengoMP>();
				
				for (MotivoTengoMuchoPoco motMuchoPoco: motivoTengoMuchoPocoLista.getDatos()){
					posicionMot++;
					PdaMotivoTengoMP pdaMotivoTengoMP = new PdaMotivoTengoMP(motMuchoPoco.getDescripcion(), posicionMot);
					listaMotTMP.add(pdaMotivoTengoMP);
				}
			}
			
			//Se obtiene una sublista con los elemento de pantalla
			int records = listaMotTMP.size();
	        if (listaMotTMP.size()>Constantes.PAGS_MOTIVOS_TENGO_MUCHO_POCO_PISTOLA)
	        {
	        	sublistaMotTMP = obtenerSubLista(listaMotTMP, 1, new Integer(Constantes.PAGS_MOTIVOS_TENGO_MUCHO_POCO_PISTOLA), records);
	        }else{
	        	sublistaMotTMP = listaMotTMP;
	        }
		
			if (sublistaMotTMP.size()>0) {
				PaginationManager<PdaMotivoTengoMP> paginationManager = new PaginationManagerImpl<PdaMotivoTengoMP>();
				result = paginationManager.paginate(new Page<PdaMotivoTengoMP>(), sublistaMotTMP, new Integer(Constantes.PAGS_MOTIVOS_TENGO_MUCHO_POCO_PISTOLA), records, 1);	
				
			} else {
				return new Page<PdaMotivoTengoMP>();
			}
		}catch (Exception e) {
			
			  session.setAttribute("listMotTMP",  new ArrayList<PdaMotivo>());
			  result= new Page<PdaMotivoTengoMP>();
		}
		session.setAttribute("listMotTMP", listaMotTMP);
		
		return result;
	}
	
	private Page<PdaMotivoTengoMP> paginarListaMotTengoMP(HttpSession session, String pgMotivosTengoMP, String pgTotMotivosTengoMP, String botPag) throws Exception{
		
		List<PdaMotivoTengoMP> listaMotTMP = (List<PdaMotivoTengoMP>) session.getAttribute("listMotTMP");
		
		Page<PdaMotivoTengoMP> result = null;
		int records = 0;
		int page = 1;
		
		if (listaMotTMP.size()>0) {
		
			records = listaMotTMP.size();
			//Peticion 55001. Corrección errores del LOG.
			page = Utilidades.convertirStringAInt(pgMotivosTengoMP, 1);
			int pageTot = Utilidades.convertirStringAInt(pgTotMotivosTengoMP, 1);
			
			if (botPag.endsWith("MotTMP"))
			{
				if (botPag.equals("firstMotTMP"))
				{
					page = 1;
					pageTot = 2;
				}
				else if (botPag.equals("prevMotTMP"))
				{
					page = page -1;
					
				}
				else if (botPag.equals("nextMotTMP"))
				{
					page = page +1;
					
				}
				else if (botPag.equals("lastMotTMP"))
				{
					page = pageTot;
				}
			}
			
			if (listaMotTMP.size()>Constantes.PAGS_MOTIVOS_TENGO_MUCHO_POCO_PISTOLA)
	        {
				listaMotTMP = this.obtenerSubLista(listaMotTMP,page,pageTot,records);
	        }
		}
		
		if (listaMotTMP.size()>0) {
			PaginationManager<PdaMotivoTengoMP> paginationManager = new PaginationManagerImpl<PdaMotivoTengoMP>();
			result = paginationManager.paginate(new Page<PdaMotivoTengoMP>(), listaMotTMP, Constantes.PAGS_MOTIVOS_TENGO_MUCHO_POCO_PISTOLA, records, page);	
			
		} else {
			return new Page<PdaMotivoTengoMP>();
		}
		
		
	 return result;
	}
	
	private List<PdaMotivoTengoMP> obtenerSubLista(List<PdaMotivoTengoMP> lista, int pag, int pagTot, int records) throws Exception{
		
		List<PdaMotivoTengoMP> result = null;
		
		int inicio = Constantes.PAGS_MOTIVOS_TENGO_MUCHO_POCO_PISTOLA*(pag-1);
		
		int fin = 0;	
		if (pag == pagTot)
		{
			fin = records;
		}
		else
		{
			fin = Constantes.PAGS_MOTIVOS_TENGO_MUCHO_POCO_PISTOLA*pag;
		}
		
		result = (lista).subList(inicio, fin);
		
		return result;
	}
}