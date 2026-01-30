package es.eroski.misumi.control;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.eroski.misumi.model.CapraboMotivoNoPedible;
import es.eroski.misumi.model.CapraboMotivoNoPedibleArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleCentroArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleMotivo;
import es.eroski.misumi.model.Motivo;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.model.TextoMotivo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioCap;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosPopupMotivosTengoMP;
import es.eroski.misumi.model.pda.PdaMotivo;
import es.eroski.misumi.model.pda.PdaMotivoTengoMP;
import es.eroski.misumi.model.pda.PdaTextoMotivo;
import es.eroski.misumi.model.pedidosCentroWS.MotivosType;
import es.eroski.misumi.model.pedidosCentroWS.ReferenciasValidadasType;
import es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.CapraboMotivosNoPedibleService;
import es.eroski.misumi.service.iface.PedidosCentroService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
public class pdaP16MotivosTengoMPPopupCapraboController extends pdaConsultasController {

	private static Logger logger = Logger.getLogger(pdaP16MotivosTengoMPPopupCapraboController.class);

	@Value( "${tipoAprovisionamiento.descentralizado}" )
	private String tipoAprovisionamientoDescentralizado;

	@Autowired
	private CapraboMotivosNoPedibleService capraboMotivosNoPedibleService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
	
	@Autowired
	private PedidosCentroService pedidosCentroService;

	@Resource 
	private MessageSource messageSource;
	
	

	//En esta función sabemos que estamos en caprabo
	@RequestMapping(value = "/pdaP16MotivosTengoMPPopupCaprabo", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final String procede,
			@Valid final String codArtRel) {

		try 
		{
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");
			Long codCentro = user.getCentro().getCodCentro();

			//Inserta el código de caprabo
			Long codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(codCentro, codArt);

			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));
			pdaDatosCab.setCodArtCaprabo(codArtCaprabo.toString());

			//Obtenemos la descripción del artículo.
			String descArt = "";
			String motivo = "";
			ReferenciasCentro referenciasCentro = new ReferenciasCentro();

			//Inserta el código de eroski
			referenciasCentro.setCodArt(codArt);

			referenciasCentro.setCodArtCaprabo(codArtCaprabo);
			referenciasCentro.setCodCentro(codCentro);

			VSurtidoTienda surtidoTienda = obtenerSurtidoTiendaCaprabo(referenciasCentro);
			String pedir = surtidoTienda.getPedir();

			TMisMcgCaprabo tMisMcgCapraboResult = obtenerMcgCaprabo(referenciasCentro);

			//Obtenemos datos de Movimiento continuo gama Caprabo. La descripción si no existe en T_MIS_MCG_CAPRABO se obtendrá de V_DAT_DIARIO_CAP
			//Se aprovecha a recuperar el motivo si existe en T_MIS_MCG_CAPRABO
			if (tMisMcgCapraboResult != null && tMisMcgCapraboResult.getDescripArt() != null)
			{
				descArt = tMisMcgCapraboResult.getDescripArt();

				if(user.getCentro().isEsCentroCapraboEspecial()){
					if(codArtRel != null && codArtRel != ""){
						motivo = utilidadesCapraboService.obtenerMotivoCapraboEspecial(tMisMcgCapraboResult.getMotivo(), utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(),new Long(codArtRel)).toString() , codArtRel);
					}else{
						motivo = tMisMcgCapraboResult.getMotivo();
					}
				}else{
					motivo = tMisMcgCapraboResult.getMotivo();
				}
			}else{
				VDatosDiarioCap vDatosDiarioCap = obtenerVDatDiarioCap(referenciasCentro);
				if (vDatosDiarioCap!=null){
					descArt = vDatosDiarioCap.getDescripArt();
				}
			}

			pdaDatosCab.setDescArtCab(descArt);
			model.addAttribute("pdaDatosCab", pdaDatosCab);

			//Llamamos al método que se encarga obtener el objeto page de la lista de motivos de tengo mucho poco
			MotivoTengoMuchoPoco motivoTengoMuchoPoco = new MotivoTengoMuchoPoco();
			motivoTengoMuchoPoco.setCodCentro(codCentro);
			motivoTengoMuchoPoco.setCodArticulo(codArtCaprabo);
			if (motivo!=null && !"".equals(motivo)){
				motivoTengoMuchoPoco.setDescripcion(motivo);
			}
			Page<PdaMotivoTengoMP> pagMotTengoMP = cargarMotivosTengoMP(session, motivoTengoMuchoPoco, pedir, surtidoTienda);

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
		return "pda_p16_motivosTengoMPPopupCaprabo";
	}

	@RequestMapping(value = "/pdaP16PaginarCaprabo", method = RequestMethod.GET)
	public String pdaP14PaginarCaprabo(ModelMap model,
			HttpSession session,
			@Valid final Long codArt,
			@Valid final Long codCentro,
			@Valid final String pgMot,
			@Valid final String pgTotMot,
			@Valid final String botPag) {


		try 
		{
			//Cargamos los datos de cabecera.
			PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
			pdaDatosCab.setCodArtCab(String.valueOf(codArt));

			//Obtenemos la descripción del artículo.
			String descArt = "";
			ReferenciasCentro referenciasCentro = new ReferenciasCentro();

			referenciasCentro.setCodArtCaprabo(codArt);
			referenciasCentro.setCodCentro(codCentro);

			TMisMcgCaprabo tMisMcgCapraboResult = obtenerMcgCaprabo(referenciasCentro);

			//Obtenemos datos de Movimiento continuo gama Caprabo. La descripción si no existe en T_MIS_MCG_CAPRABO se obtendrá de V_DAT_DIARIO_CAP
			//Se aprovecha a recuperar el motivo si existe en T_MIS_MCG_CAPRABO
			if (tMisMcgCapraboResult != null && tMisMcgCapraboResult.getDescripArt() != null)
			{
				descArt = tMisMcgCapraboResult.getDescripArt();
			}else{
				VDatosDiarioCap vDatosDiarioCap = obtenerVDatDiarioCap(referenciasCentro);
				if (vDatosDiarioCap!=null){
					descArt = vDatosDiarioCap.getDescripArt();
				}
			}

			pdaDatosCab.setDescArtCab(descArt);

			model.addAttribute("pdaDatosCab", pdaDatosCab);

			Page<PdaMotivoTengoMP> pagMotTengoMP = paginarListaMotTengoMP(session,pgMot,pgTotMot, botPag);

			PdaDatosPopupMotivosTengoMP pdaDatosPopupMotivosTengoMP = new PdaDatosPopupMotivosTengoMP();
			pdaDatosPopupMotivosTengoMP.setCodArt(codArt);
			pdaDatosPopupMotivosTengoMP.setDescArt(descArt);
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



		return "pda_p16_motivosTengoMPPopupCaprabo";
	}

	private Page<PdaMotivoTengoMP> cargarMotivosTengoMP(HttpSession session, MotivoTengoMuchoPoco motivoTengoMuchoPoco, String pedir, VSurtidoTienda surtidoTienda) throws Exception{

		Page<PdaMotivoTengoMP> result = null;
		List<PdaMotivoTengoMP> listaMotTMP = null;
		List<PdaMotivoTengoMP> sublistaMotTMP = null;

		User user = (User)session.getAttribute("user");
		
		try{
			MotivoTengoMuchoPocoLista motivoTengoMuchoPocoLista = new MotivoTengoMuchoPocoLista();
			List<MotivoTengoMuchoPoco> listaMotivosTengoMuchoPoco = new ArrayList<MotivoTengoMuchoPoco>();
			if (motivoTengoMuchoPoco!=null && motivoTengoMuchoPoco.getDescripcion()!=null && !"".equals(motivoTengoMuchoPoco.getDescripcion())){
				listaMotivosTengoMuchoPoco.add(motivoTengoMuchoPoco);
			}

			//Carga de la sesion en el objeto de motivos

			if (null != surtidoTienda && pedir.toUpperCase().equals("N") && surtidoTienda.getCatalogo().equals("B")) {
				//Si es descentralizado hay que sacar el mensaje BAJA CATALOGO
				if (this.tipoAprovisionamientoDescentralizado.equals(surtidoTienda.getTipoAprov())){
					Locale locale = LocaleContextHolder.getLocale();
					String motivoBajaCatalogo =  this.messageSource.getMessage("p18_caprabo_referenciasCentroPopupPedir.bajaCatalogo", null, locale);
					MotivoTengoMuchoPoco motivoTengoMuchoPocoBaja = new MotivoTengoMuchoPoco();
					motivoTengoMuchoPocoBaja.setDescripcion(motivoBajaCatalogo);
					listaMotivosTengoMuchoPoco.add(motivoTengoMuchoPocoBaja);

				}else{
					//Parámetros de búsqueda de motivos
					String tipoMovimientoBusqueda = null;

					ReferenciasCentro referenciasCentro = new ReferenciasCentro();
					referenciasCentro.setCodArtCaprabo(motivoTengoMuchoPoco.getCodArticulo());
					referenciasCentro.setCodCentro(motivoTengoMuchoPoco.getCodCentro());

					//Búsqueda de tipo de movimiento asociado
					TMisMcgCaprabo tMisMcgCaprabo = obtenerMcgCaprabo(referenciasCentro);
					if (tMisMcgCaprabo!= null && tMisMcgCaprabo.getTipoMov()!=null){
						tipoMovimientoBusqueda = tMisMcgCaprabo.getTipoMov();
					}
					
					if (!(user.getCentro().isEsCentroCapraboNuevo())){ //Si en un centro CAPRABO NUEVO no hay que llamar al procedimieto para 
					
						//Búsqueda de motivos 
						CapraboMotivoNoPedible capraboMotivoNoPedibleBusqueda = new CapraboMotivoNoPedible();
						capraboMotivoNoPedibleBusqueda.setCodLocBusqueda(referenciasCentro.getCodCentro());
						capraboMotivoNoPedibleBusqueda.setCodArticuloBusqueda(referenciasCentro.getCodArtCaprabo());
						capraboMotivoNoPedibleBusqueda.setTipoMovimientoBusqueda(tipoMovimientoBusqueda);
	
						CapraboMotivoNoPedible capraboMotivoNoPedible = this.capraboMotivosNoPedibleService.findCentroRefTipo(capraboMotivoNoPedibleBusqueda);
	
						if (capraboMotivoNoPedible != null 
								&& capraboMotivoNoPedible.getDatos() != null
								&& capraboMotivoNoPedible.getDatos().get(0) != null){
	
							CapraboMotivoNoPedibleCentroArt capraboMotivoNoPedibleCentroArt = capraboMotivoNoPedible.getDatos().get(0);
							if(capraboMotivoNoPedibleCentroArt.getArticulos()!=null && capraboMotivoNoPedibleCentroArt.getArticulos().get(0)!=null){
								CapraboMotivoNoPedibleArt capraboMotivoNoPedibleArt = capraboMotivoNoPedibleCentroArt.getArticulos().get(0);
								if(capraboMotivoNoPedibleArt.getMotivos()!=null){
									for (CapraboMotivoNoPedibleMotivo capraboMotivoNoPedibleMotivo : capraboMotivoNoPedibleArt.getMotivos()){
										MotivoTengoMuchoPoco motivoTengoMuchoPocoProc = new MotivoTengoMuchoPoco();
										motivoTengoMuchoPocoProc.setDescripcion(capraboMotivoNoPedibleMotivo.getDescripcion());
										listaMotivosTengoMuchoPoco.add(motivoTengoMuchoPocoProc);
									}
								}	
							}
						}
					
					} 
					
					
				}
			}

			motivoTengoMuchoPocoLista.setDatos(listaMotivosTengoMuchoPoco);

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