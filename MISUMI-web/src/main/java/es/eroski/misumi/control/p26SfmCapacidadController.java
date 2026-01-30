package es.eroski.misumi.control;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

import es.eroski.misumi.model.CamposModificadosSFM;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.SfmCapacidadFacingPagina;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.HuecosElectroService;
import es.eroski.misumi.service.iface.MapClavesSfmService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.VAgruComerParamSfmcapService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VArtSfmService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.PaginationManager;


@Controller
@RequestMapping("/sfmCapacidad")
public class p26SfmCapacidadController {
	private static Logger logger = Logger.getLogger(p26SfmCapacidadController.class);
	private PaginationManager<VArtSfm> paginationManager = new PaginationManagerImpl<VArtSfm>();

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private VAgruComerParamSfmcapService vAgruComerParamSfmcapService;
	@Autowired
	private VArtSfmService vArtSfmService;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired	
	private HuecosElectroService huecosElectroService;

	@Autowired
	private VAgruComerRefService vAgruComerRefService;

	@Autowired
	private ExcelManager excelManager;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@Autowired
	private MapClavesSfmService mapClavesSfmService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model) {
		return "p26_sfmCapacidad";
	}

	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	public  @ResponseBody SfmCapacidadFacingPagina loadDataGrid(
			@RequestBody VArtSfm vArtSfm,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "tipoListado", required = false, defaultValue = Constantes.SFMCAP_TIPO_LISTADO_SFM) String tipoListado,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		Locale locale = LocaleContextHolder.getLocale();
		User user = (User) session.getAttribute("user");
		if ("N".equals(recarga)){
			SfmCapacidadFacingPagina sfmCapacidadFacingPagina = new SfmCapacidadFacingPagina();
			SfmCapacidadFacingPagina sfmCapacidadFacingPaginaDos = new SfmCapacidadFacingPagina();
			
			SfmCapacidadFacing sfmCapacidadFacing = null;
			List<VArtSfm> listaSfm = null;
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			VDatosDiarioArt vDatosDiarioArtRes =  new VDatosDiarioArt();


			try {
				//logger.info("Searching VArtSfm");	

				if (vArtSfm.getCodArticulo() != null && !"".equals(vArtSfm.getCodArticulo())){
					//Insertamos el código de artículo recibido como referencia eroski
					Long codArtEroski = vArtSfm.getCodArticulo();

					//Si el código de artículo es de caprabo, obtener su código de eroski
					if(user.getCentro().esCentroCaprabo()){
						codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), vArtSfm.getCodArticulo());						
					}
					//Insertar el código de eroski
					vArtSfm.setCodArticulo(codArtEroski);

					//Insertar el código de eroski
					vDatosDiarioArt.setCodArt(codArtEroski);
					vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

					if (null != vDatosDiarioArtRes){

						VAgruComerParamSfmcap vAgruComerParamSfmcap = new VAgruComerParamSfmcap();
						vAgruComerParamSfmcap.setCodCentro(user.getCentro().getCodCentro());
						vAgruComerParamSfmcap.setGrupo1(vDatosDiarioArtRes.getGrupo1());
						vAgruComerParamSfmcap.setGrupo2(vDatosDiarioArtRes.getGrupo2());
						vAgruComerParamSfmcap.setGrupo3(vDatosDiarioArtRes.getGrupo3());
						vAgruComerParamSfmcap.setGrupo4(vDatosDiarioArtRes.getGrupo4());
						vAgruComerParamSfmcap.setGrupo5(vDatosDiarioArtRes.getGrupo5());
						List<VAgruComerParamSfmcap> listaEstructuras = this.vAgruComerParamSfmcapService.findAll(vAgruComerParamSfmcap, null);

						if (listaEstructuras != null && listaEstructuras.size() > 0){
							VAgruComerParamSfmcap estructuraArticulo = listaEstructuras.get(0);
							if (estructuraArticulo.getFlgFacing() != null && estructuraArticulo.getFlgFacing().equals("S") 
									&& Constantes.TIPO_COMPRA_VENTA_SOLO_VENTA.equals(vDatosDiarioArtRes.getTipoCompraVenta())){
								sfmCapacidadFacingPagina.setEstado(new Long(1));
								sfmCapacidadFacingPagina.setDescEstado(this.messageSource.getMessage(
										"p26_sfmCapacidad.errorReferenciaSoloVenta", null, locale));
								return sfmCapacidadFacingPagina;
							} else if (null != estructuraArticulo.getFlgFacing() && estructuraArticulo.getFlgFacing().equals("S")){
								sfmCapacidadFacing = this.vArtSfmService.consultaFac(vArtSfm);
							} else if (null != estructuraArticulo.getFlgStockFinal() && estructuraArticulo.getFlgStockFinal().equals("S")){
								sfmCapacidadFacing = this.vArtSfmService.consultaSfm(vArtSfm);
							} else {
								sfmCapacidadFacing = this.vArtSfmService.consultaCap(vArtSfm);
							}
							sfmCapacidadFacingPagina.setEstructuraArticulo(estructuraArticulo);
						}

					} else {

						sfmCapacidadFacingPagina.setEstado(new Long(1));
						sfmCapacidadFacingPagina.setDescEstado(this.messageSource.getMessage(
								"p26_sfmCapacidad.errorReferenciaNoValida", null, locale));
						return sfmCapacidadFacingPagina;
					}

				}else{
					if (Constantes.SFMCAP_TIPO_LISTADO_FAC.equals(tipoListado)){
						sfmCapacidadFacing = this.vArtSfmService.consultaFac(vArtSfm);
					}else if (Constantes.SFMCAP_TIPO_LISTADO_CAP.equals(tipoListado)){
						sfmCapacidadFacing = this.vArtSfmService.consultaCap(vArtSfm);
					}else{
						sfmCapacidadFacing = this.vArtSfmService.consultaSfm(vArtSfm);

					}
				}
				if (sfmCapacidadFacing != null){
					if (sfmCapacidadFacing.getDatos() != null){
						listaSfm = sfmCapacidadFacing.getDatos();
					}
				}

				//Ordenamos la lista
				listaSfm = this.vArtSfmService.ordenarLista(listaSfm, index, sortOrder);
				if (sfmCapacidadFacing != null){
					sfmCapacidadFacing.setDatos(listaSfm);
				}

				session.setAttribute("listaSfmCapacidadFacing", sfmCapacidadFacing);

				//Además la primera vez que cargamos tenemos que crear un hashmap que relacione clave con índice de la lista.
				//HashMap<String, Integer> mapClaves = new HashMap<String, Integer>();
				if (listaSfm != null && listaSfm.size()>0) {

					//Formateamos el campo Lsf segun fecha
					listaSfm = this.vArtSfmService.formatearLsf(listaSfm);

					//Creamos el hashMap
					this.mapClavesSfmService.updateMapClavesInSession(listaSfm);
				}

				//La primera vez se dejará credo el HashMap que se utilizará para guardar la relación entre referencia lote y las referencias hijas.
				//session.removeAttribute("hashMapRelLoteHijas");
				HashMap<String, SfmCapacidadFacing> mapRelLoteHijas = new HashMap<String, SfmCapacidadFacing>();
				session.setAttribute("hashMapRelLoteHijas", mapRelLoteHijas);



			} catch (Exception e) {
				//logger.error(StackTraceManager.getStackTrace(e));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw e;
			}

			/*
			 * Antes de paginar vamos a calcular el FLG_NSR de todos los registros.
			 * Calculamos todos los registros porque es un campo ordenable.
			 */
			sfmCapacidadFacing = this.vArtSfmService.calcularCampoNSR(sfmCapacidadFacing);

			/*
			 * Si se está filtrando por referencia calculamos el campo pedir.
			 */
			sfmCapacidadFacingPagina.setPedir(this.vArtSfmService.calcularPedir(vArtSfm));

			/*
			 * Creamos una sublista paginada sólo con los registros que se muestran en el grid.
			 */
			sfmCapacidadFacingPaginaDos = this.vArtSfmService.crearListaPaginada(sfmCapacidadFacing, listaSfm, page, max, index, sortOrder);
			sfmCapacidadFacingPagina.setDatos(sfmCapacidadFacingPaginaDos.getDatos());
			sfmCapacidadFacingPagina.setDescEstado(sfmCapacidadFacingPaginaDos.getDescEstado());
			sfmCapacidadFacingPagina.setEstado(sfmCapacidadFacingPaginaDos.getEstado());
			sfmCapacidadFacingPagina.setFlgCapacidad(sfmCapacidadFacingPaginaDos.getFlgCapacidad());
			sfmCapacidadFacingPagina.setFlgFacing(sfmCapacidadFacingPaginaDos.getFlgFacing());

			/*
			 * Una vez que hemos paginado vamos a calcular el FLG_MUCHO_POCO para los registros de la primera página, 
			 * es decir, aquellos que se muestran en el grid de la pantalla.
			 * Esto lo podemos hacer porque no es un campo ordenable, es un link que se muestra en el campo stock.
			 */
			sfmCapacidadFacingPagina = this.vArtSfmService.calcularCampoMuchoPoco(sfmCapacidadFacingPagina, vDatosDiarioArtRes.getCodFpMadre(), session);

			//	sfmCapacidadFacingPagina.getDatos().getRows().get(22).getLsf()

			return sfmCapacidadFacingPagina;
		}else{
			return loadDataGridRecarga(vArtSfm, page, max, index, sortOrder, response, session);
		}
	}


	@RequestMapping(value = "/loadDataSubGridTextil", method = RequestMethod.POST)
	public  @ResponseBody  SfmCapacidadFacingPagina loadDataSubGridTextil(
			@RequestBody VArtSfm vArtSfm,
			@RequestParam(value = "btnGuardarClick", required = false, defaultValue = "N") String btnGuardarClick,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		User user = (User) session.getAttribute("user");

		SfmCapacidadFacing sfmCapacidadFacing = null;
		List<VArtSfm> listaSfm = null;		

		//Insertamos el código de artículo recibido como referencia eroski
		Long codArtEroski = vArtSfm.getCodArticulo();

		//Si el código de artículo es de caprabo, obtener su código de eroski
		if(user.getCentro().esCentroCaprabo()){
			codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), vArtSfm.getCodArticulo());						
		}

		//Insertamos el código de artículo de Eroski.Si el centro es Eroski no habrá cambios.
		//Si es caprabo sin embargo, se realizará la transformación a Eroski.
		vArtSfm.setCodArticulo(codArtEroski);

		//Para que el procedimiento nos devuelva los datos de la referencia lote y sus hijas, hay que informar el campo codArticuloLote
		vArtSfm.setCodArticuloLote(vArtSfm.getCodArticulo());

		//La Sublista obtenida la metemos en la hashtable de sesion relacionandolo con el codArticulo de la referencia lote.
		//Primero obtenemos lo que tenemos en sesion, comprobamos si la sublista ya ha sido metida en la hastable anteriormente, si es asi,
		//no lo volvemos a cargar, mantenemos lo que tenemos, ya que ha podido ser actualizado con datos del usuario.

		HashMap<String, SfmCapacidadFacing> mapRelLoteHijas =  (HashMap<String, SfmCapacidadFacing>) session.getAttribute("hashMapRelLoteHijas");

		if (!(mapRelLoteHijas.containsKey(String.valueOf(vArtSfm.getCodArticulo())))) {
			//La sublista no ha sido cargada con anterioridad en la Hashtable. La obtenemos y la cargamos

			vArtSfm.setPedir(""); //Al buscar las hijas el parametro Pedirdebe ir vacio, sino el procedimiento falla.

			sfmCapacidadFacing = this.vArtSfmService.consultaFac(vArtSfm);

			if (sfmCapacidadFacing.getEstado() == 0) {

				List<VArtSfm> listaSfmAux = sfmCapacidadFacing.getDatos();

				for (int i =0;i<listaSfmAux.size();i++)
				{
					if ((vArtSfm.getFlgActualizarFacing() != null) && (vArtSfm.getFlgActualizarFacing().equals("S"))) {

						//El procedimiento no nos devuelve cual es la referencia lote para cada una de las referencias hijas.
						//Necesitamos asignarselo para guardarlo en el campo estado de cada referencia hija
						listaSfmAux.get(i).setCodArticuloLote(vArtSfm.getCodArticulo()); 

						//Se ha modificado el facing de la madre, asignamos el mismo facing a las hijas y pintamos el icono de modificado.
						//Simepre y cuando el flgSfmFijo sea distinto de 'B', si es igual 'B', las cajas de facing no aparecen, el facing 
						//no es editable.
						if (!(listaSfmAux.get(i).getFlgSfmFijo().equals("B"))) {
							listaSfmAux.get(i).setFacingCentro(vArtSfm.getFacingCentro());
							listaSfmAux.get(i).setCodError(new Long(9)); //codigo de modificado
						}

					} else {

						//El procedimiento no nos devuelve cual es la referencia lote para cada una de las referencias hijas.
						//Necesitamos asignarselo para guardarlo en el campo estado de cada referencia hija
						listaSfmAux.get(i).setCodArticuloLote(vArtSfm.getCodArticulo()); 

					}
					Long codArtCapraboOEroski = listaSfmAux.get(i).getCodArticulo();
					listaSfmAux.get(i).setCodArtCaprabo(codArtCapraboOEroski);
				}

				sfmCapacidadFacing.setDatos(listaSfmAux);   
				mapRelLoteHijas.put(String.valueOf(vArtSfm.getCodArticulo()), sfmCapacidadFacing);
			}

		} else {
			//Si la hemos cargado con anterioridad en la hashtable, la obtenemos de esta.
			sfmCapacidadFacing = mapRelLoteHijas.get(String.valueOf(vArtSfm.getCodArticulo()));

			if ((vArtSfm.getFlgActualizarFacing() != null) && (vArtSfm.getFlgActualizarFacing().equals("S"))) {

				List<VArtSfm> listaSfmAux = sfmCapacidadFacing.getDatos();

				for (int i =0;i<listaSfmAux.size();i++)
				{
					if (!(listaSfmAux.get(i).getFlgSfmFijo().equals("B"))) {
						//Petición MISUMI-34 Como esta función se llama tanto al cambiar la referencia madre, para que las hijas obtengan su mismo valor, 
						//como cuando realizas un guardado, en el caso de un guardado no queremos que las hijas obtengan el valor facing 
						//que llega a la función, pues es nulo y queremos que se quede con el que tenían. Por ello en el guardado el btnGuardarClick
						//será 'S' y al cambiar el valor de la madre será 'N'
						if(("N").equals(btnGuardarClick)){
							listaSfmAux.get(i).setFacingCentro(vArtSfm.getFacingCentro());
							listaSfmAux.get(i).setCodError(new Long(9)); //codigo de modificado
						}
					}
				}

				sfmCapacidadFacing.setDatos(listaSfmAux);
				mapRelLoteHijas.put(String.valueOf(vArtSfm.getCodArticulo()), sfmCapacidadFacing);

			}
		}

		session.setAttribute("hashMapRelLoteHijas", mapRelLoteHijas);

		SfmCapacidadFacingPagina sfmCapacidadFacingPagina = new SfmCapacidadFacingPagina();

		if (sfmCapacidadFacing != null){
			if (sfmCapacidadFacing.getDatos() != null){
				listaSfm = sfmCapacidadFacing.getDatos();
			}
		}

		Page<VArtSfm> listaSfmPaginada = null;
		if ((listaSfm != null) &&  (listaSfm.size()> 0)) {

			int records = listaSfm.size();

			listaSfmPaginada = this.paginationManager.paginate(new Page<VArtSfm>(), listaSfm,
					max.intValue(), records, page.intValue());	
			sfmCapacidadFacingPagina.setDatos(listaSfmPaginada);
			sfmCapacidadFacingPagina.setEstado(sfmCapacidadFacing.getEstado());
			sfmCapacidadFacingPagina.setDescEstado(sfmCapacidadFacing.getDescEstado());
			sfmCapacidadFacingPagina.setFlgCapacidad(sfmCapacidadFacing.getFlgCapacidad());
			sfmCapacidadFacingPagina.setFlgFacing(sfmCapacidadFacing.getFlgFacing());

		} else {
			sfmCapacidadFacingPagina.setDatos(new Page<VArtSfm>());
			sfmCapacidadFacingPagina.setEstado(new Long(0));
			sfmCapacidadFacingPagina.setDescEstado("");
		}

		return sfmCapacidadFacingPagina;
	}


	@RequestMapping(value = "/actualizarFacingSubgrid", method = RequestMethod.POST)
	public  @ResponseBody  SfmCapacidadFacingPagina actualizarFacingSubgrid(
			@RequestBody VArtSfm vArtSfm,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{


		SfmCapacidadFacing sfmCapacidadFacing = null;
		List<VArtSfm> listaSfm = null;


		//La Sublista la obtenemos de la Hashtable y la actualiza.

		HashMap<String, SfmCapacidadFacing> mapRelLoteHijas =  (HashMap<String, SfmCapacidadFacing>) session.getAttribute("hashMapRelLoteHijas");

		if (mapRelLoteHijas.containsKey(String.valueOf(vArtSfm.getCodArticuloLote()))) {

			//Si la hemos cargado con anterioridad en la hashtable, la obtenemos de esta.
			sfmCapacidadFacing = mapRelLoteHijas.get(String.valueOf(vArtSfm.getCodArticuloLote()));

			//Si el facing introducido por el usuario para una de las referencias hijas es >0, actualizamos el facing de la hija
			// if ((vArtSfm.getFacingCentro() != null) && (vArtSfm.getFacingCentro() > 0)) {
			if (vArtSfm.getFacingCentro() != null) {

				List<VArtSfm> listaSfmAux = sfmCapacidadFacing.getDatos();

				for (int i =0;i<listaSfmAux.size();i++)
				{
					if (listaSfmAux.get(i).getCodArticulo().equals(vArtSfm.getCodArticulo())) { // Solo se actualiza la referencia que 
						if (!(listaSfmAux.get(i).getFlgSfmFijo().equals("B"))) {
							listaSfmAux.get(i).setFacingCentro(vArtSfm.getFacingCentro());
							listaSfmAux.get(i).setCodError(new Long(9)); //codigo de modificado
						}
					}
				}

				sfmCapacidadFacing.setDatos(listaSfmAux);
				mapRelLoteHijas.put(String.valueOf(vArtSfm.getCodArticuloLote()), sfmCapacidadFacing);
			}

		}

		session.setAttribute("hashMapRelLoteHijas", mapRelLoteHijas);

		SfmCapacidadFacingPagina sfmCapacidadFacingPagina = new SfmCapacidadFacingPagina();

		if (sfmCapacidadFacing != null){
			if (sfmCapacidadFacing.getDatos() != null){
				listaSfm = sfmCapacidadFacing.getDatos();
			}
		}

		Page<VArtSfm> listaSfmPaginada = null;
		if ((listaSfm != null) &&  (listaSfm.size()> 0)) {

			int records = listaSfm.size();

			listaSfmPaginada = this.paginationManager.paginate(new Page<VArtSfm>(), listaSfm,
					max.intValue(), records, page.intValue());	
			sfmCapacidadFacingPagina.setDatos(listaSfmPaginada);
			sfmCapacidadFacingPagina.setEstado(sfmCapacidadFacing.getEstado());
			sfmCapacidadFacingPagina.setDescEstado(sfmCapacidadFacing.getDescEstado());
			sfmCapacidadFacingPagina.setFlgCapacidad(sfmCapacidadFacing.getFlgCapacidad());
			sfmCapacidadFacingPagina.setFlgFacing(sfmCapacidadFacing.getFlgFacing());

		} else {
			sfmCapacidadFacingPagina.setDatos(new Page<VArtSfm>());
			sfmCapacidadFacingPagina.setEstado(new Long(0));
			sfmCapacidadFacingPagina.setDescEstado("");
		}

		return sfmCapacidadFacingPagina;
	}

	@RequestMapping(value = "/loadDataGridRecarga", method = RequestMethod.POST)
	public  @ResponseBody SfmCapacidadFacingPagina loadDataGridRecarga(
			@RequestBody VArtSfm vArtSfm,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		SfmCapacidadFacing sfmCapacidadGuardada = null;
		SfmCapacidadFacingPagina sfmCapacidadFacingPagina = new SfmCapacidadFacingPagina();
		List<VArtSfm> listaGuardada = null;
		List<CamposModificadosSFM> listaCamposModificar = null;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		VDatosDiarioArt vDatosDiarioArtRes =  new VDatosDiarioArt();
		User user = (User) session.getAttribute("user");
		try {
			sfmCapacidadGuardada = (SfmCapacidadFacing) session.getAttribute("listaSfmCapacidadFacing");
			if (sfmCapacidadGuardada != null){
				listaGuardada = sfmCapacidadGuardada.getDatos();
			}

			if (vArtSfm.getCodArticulo() != null && !"".equals(vArtSfm.getCodArticulo())){
				//Insertamos el código de artículo recibido como referencia eroski
				Long codArtEroski = vArtSfm.getCodArticulo();

				//Si el código de artículo es de caprabo, obtener su código de eroski
				if(user.getCentro().esCentroCaprabo()){
					codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), vArtSfm.getCodArticulo());						
				}

				//Insertar el código de eroski
				vDatosDiarioArt.setCodArt(codArtEroski);
				vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
			}

			//Tenemos que recorrer la lista que recibimos con los registros a actualizar.
			listaCamposModificar = (List<CamposModificadosSFM>)  vArtSfm.getListadoModificados();

			if (listaCamposModificar != null) {
				//Nos recorremos la lista.
				CamposModificadosSFM campo = new CamposModificadosSFM();
				VArtSfm registro = new VArtSfm();
				int indice = 0;
				for (int i =0;i<listaCamposModificar.size();i++)
				{
					campo = (CamposModificadosSFM)listaCamposModificar.get(i);

					indice = campo.getIndice();

					//Obtenemos el registro de la lista guardada en sesi�n.
					registro = (VArtSfm) listaGuardada.get(indice);

					//Actualizamos los valores que me llegan.
					registro.setCapacidad(campo.getCapacidad());
					registro.setSfm(campo.getSfm());
					registro.setCoberturaSfm(campo.getCoberturaSfm());
					registro.setCodError(campo.getCodError());
					registro.setFacingCentro(campo.getFacingCentro());

					Long codArtCapraboOEroski = registro.getCodArticulo();
					registro.setCodArtCaprabo(codArtCapraboOEroski);

					//Actualizamos la lista con el registro.
					listaGuardada.set(indice, registro);
				}

				//Actualizamos la lista en sesi�n.
				sfmCapacidadGuardada.setDatos(listaGuardada);
				session.setAttribute("listaSfmCapacidadFacing", sfmCapacidadGuardada);
			}

			//Sólo tendremos que ordenar cuando recarguemos al pulsar sobre la columna de iconos,
			//al paginar no debe ordenar.
			if (null != index && !index.equals(""))
			{
				//Ordenamos la lista
				listaGuardada = this.vArtSfmService.ordenarLista(listaGuardada, index, sortOrder);

				//Actualizamos el mapa de claves en sessión
				this.mapClavesSfmService.updateMapClavesInSession(listaGuardada);

				//Actualizamos la lista en sesión.
				sfmCapacidadGuardada.setDatos(listaGuardada);
				session.setAttribute("listaSfmCapacidadFacing", sfmCapacidadGuardada);
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		/*
		 * Creamos una sublista paginada sólo con los registros que se muestran en el grid.
		 */
		sfmCapacidadFacingPagina = this.vArtSfmService.crearListaPaginada(sfmCapacidadGuardada, listaGuardada, page, max, index, sortOrder);

		/*
		 * Una vez que hemos paginado vamos a calcular el FLG_MUCHO_POCO para los registros de la primera página, 
		 * es decir, aquellos que se muestran en el grid de la pantalla.
		 */
		sfmCapacidadFacingPagina = this.vArtSfmService.calcularCampoMuchoPoco(sfmCapacidadFacingPagina, vDatosDiarioArtRes.getCodFpMadre(), session);

		return sfmCapacidadFacingPagina;
	}

	@RequestMapping(value = "/saveDataGrid", method = RequestMethod.POST)
	public  @ResponseBody SfmCapacidadFacingPagina saveDataGrid(
			@RequestBody VArtSfm vArtSfm,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "tipoListado", required = false, defaultValue = Constantes.SFMCAP_TIPO_LISTADO_SFM) String tipoListado,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}

		SfmCapacidadFacing sfmCapacidadGuardada = null;
		SfmCapacidadFacingPagina sfmCapacidadFacingPagina = new SfmCapacidadFacingPagina();
		List<VArtSfm> listaModificadosActualizacion = new ArrayList<VArtSfm>();
		List<VArtSfm> listaRecarga = new ArrayList<VArtSfm>();
		List<VArtSfm> listaGuardada = null;
		List<CamposModificadosSFM> listaCamposModificar = null;
		List<Long> listaReferenciasLoteModificadas = new ArrayList<Long>();
		HashMap<String, SfmCapacidadFacing> mapRelLoteHijas = null;

		int records = 0;
		try {
			Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
			Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
			sfmCapacidadGuardada = (SfmCapacidadFacing) session.getAttribute("listaSfmCapacidadFacing");
			if (sfmCapacidadGuardada != null){
				listaGuardada = sfmCapacidadGuardada.getDatos();
			}

			User userSession = (User)session.getAttribute("user");

			//Tenemos que recorrer la lista que recibimos con los registros a actualizar.
			listaCamposModificar = (List<CamposModificadosSFM>)  vArtSfm.getListadoModificados();

			if (listaCamposModificar != null) {
				//Nos recorremos la lista.
				CamposModificadosSFM campo = new CamposModificadosSFM();
				VArtSfm registro = new VArtSfm();
				int indice = 0;
				for (int i =0;i<listaCamposModificar.size();i++)
				{
					campo = (CamposModificadosSFM)listaCamposModificar.get(i);

					indice = campo.getIndice();

					//Obtenemos el registro de la lista guardada en sesi�n.
					registro = (VArtSfm) listaGuardada.get(indice);

					//Actualizamos los valores que me llegan.
					registro.setCapacidad(campo.getCapacidad());
					registro.setSfm(campo.getSfm());
					registro.setCoberturaSfm(campo.getCoberturaSfm());
					registro.setCodError(campo.getCodError());
					registro.setFacingCentro(campo.getFacingCentro());

					//Si el centro es de caprabo, el codigoArticulo
					//del objeto registro (VArtSfm) llega relleno con
					//el valor que debería tener codigoArticuloCaprabo.
					//En ese caso habrá que insertar el valor de codigoArticulo
					//en codigoArticuloCaprabo. 

					//Si el centro es eroski el codigoArticulo llega correctamente
					//relleno con el codigoArticulo de eroski y codigoArticuloCaprabo 
					//guardará la referencia de eroski también. 

					//Como codigoArticuloCaprabo es nulo en los dos casos hay que actualizarlos.

					//Esto se debe a que la función crearEstructuraActualizacionSfmCap
					//de VArtSfmDaoImpl utiliza el atributo codigoArticuloCaprabo
					//para crear el objeto del plsql. Al actualizar el fac,sfm o capacidad
					//si es centro es caprabo el objeto utiliza referencias caprabo y si
					//el centro es eroski las referencias eroski.

					//En este caso en el caso de ser un centro caprabo codigoArticulo y codigoArticuloCaprabo
					//guardan el codigo de articulo de Caprabo y en caso de ser centro eroski guardan el codigo
					//de artículo eroski las dos. Se podría haber utilizado en la función crearEstructuraActualizacionSfmCap
					//directamente codigoArticulo, pero como es una función compartida con pdap21 y como en ese caso 
					//codigoArticulo y codigoArticuloCaprabo  utilizan dos referencias distintas en el caso de ser centro
					//caprabo y nos interesa la de caprabo, ha sido necesario utilizar ese atributo. En el caso de eroski
					//codigoArticulo y codigoArticuloCaprabo siempre tienen el mismo valor, por lo cual funciona bien.

					//Lo lógico hubiera sido que codigoArticulo y codigoArticuloCaprabo fueran distintos en este caso también,
					//en caprabo, pero como el grid enseña lo que existe en codigoArticulo y en este caso es correcto (guarda el
					//articulo de caprabo), se ha decidido no tocarlo por si se rompe algo, aunque realmente debería tener el codigo
					//de eroski.

					Long codArtCapraboOEroski = registro.getCodArticulo();
					registro.setCodArtCaprabo(codArtCapraboOEroski);

					//Actualizamos la lista con el registro.
					listaGuardada.set(indice, registro);
				}
			}

			if (listaGuardada != null) {
				//Nos recorremos la lista actualizada para obtener los registros modificados
				VArtSfm registroGuardado = new VArtSfm();

				for (int i =0;i<listaGuardada.size();i++)
				{
					registroGuardado = (VArtSfm)listaGuardada.get(i);
					if (registroGuardado != null && registroGuardado.getCodError() != null && registroGuardado.getCodError().equals(Constantes.SFMCAP_CODIGO_MENSAJE_MODIFICADO)){

						/* Peticion: 49285 (Solo Textil) */
						if ((Constantes.SFMCAP_TIPO_LISTADO_FAC.equals(tipoListado)) && (registroGuardado.getNivelLote() > 0)) { 
							//En Textil, las referencias madres aunque hayan  sido modificada siempre se 
							// visualizan en blanco, esto hace que llege un null.

							if ((registroGuardado.getFacingCentro() != null) && (registroGuardado.getFacingCentro() == 0)) {
								registroGuardado.setFacingCentro(new Long(0));
							}else  {                  
							//Como se pide en la MISUMI-468, se le mantiene el valor introducido en lugar de ponerlo a 1 como hacia antes
							  //registroGuardado.setFacingCentro(new Long(1));
								registroGuardado.setFacingCentro(new Long(registroGuardado.getFacingCentro()));
							}

							//Aprovechamos para guardar en una lista las referencias lote que hayan sido modificadas. Mas tarde se recorrera esta lista
							//para actualizar las referencias hijas de cada referencia lote modificada.
							listaReferenciasLoteModificadas.add(registroGuardado.getCodArticulo());
						}

						listaModificadosActualizacion.add(registroGuardado);
					}
				}
			}

			//Obtenemos el usuario para enviarlo a la actualizaci�n
			String usuario = "";
			//User userSession = (User)session.getAttribute("user");
			if (userSession.getCode() != null && !userSession.getCode().equals(""))
			{
				usuario = userSession.getCode();
			}

			//Actualizaci�n de los datos
			if (listaModificadosActualizacion != null){
				//Obtenci�n del mapa de claves para la actualizaci�n
				HashMap<String, Integer> mapClaves = this.mapClavesSfmService.getMapClavesFromSession();

				SfmCapacidadFacing sfmCapacidadFacingActualizacion = null;
				if (Constantes.SFMCAP_TIPO_LISTADO_FAC.equals(tipoListado)){
					sfmCapacidadFacingActualizacion = this.vArtSfmService.actualizacionFac(listaModificadosActualizacion, usuario);
				}else if (Constantes.SFMCAP_TIPO_LISTADO_CAP.equals(tipoListado)){
					sfmCapacidadFacingActualizacion = this.vArtSfmService.actualizacionCap(listaModificadosActualizacion, usuario);
				}else{
					sfmCapacidadFacingActualizacion = this.vArtSfmService.actualizacionSfm(listaModificadosActualizacion, usuario);
				}
				//Recarga de la lista actualizada en la lista guardada
				if (sfmCapacidadFacingActualizacion != null && sfmCapacidadFacingActualizacion.getDatos() != null && sfmCapacidadFacingActualizacion.getDatos().size() > 0){
					List<VArtSfm> listaDatosActualizados = sfmCapacidadFacingActualizacion.getDatos();
					for (int i=0; i<listaDatosActualizados.size(); i++){
						VArtSfm varSfmActualizado = listaDatosActualizados.get(i);
						String claveRegistroActualizado = obtenerClaveRegistro(	varSfmActualizado.getCodN1(),
								varSfmActualizado.getCodN2(), 
								varSfmActualizado.getCodN3(),
								varSfmActualizado.getCodN4(),
								varSfmActualizado.getCodN5(),
								varSfmActualizado.getCodArticulo());
						int indiceRegistroGuardado = ((Integer)mapClaves.get(claveRegistroActualizado)).intValue();

						VArtSfm registroGuardado = (VArtSfm) listaGuardada.get(indiceRegistroGuardado);

						//Actualizamos los valores que me llegan.
						registroGuardado.setFacingCentro(varSfmActualizado.getFacingCentro());
						registroGuardado.setFacingCentroOrig(varSfmActualizado.getFacingCentroOrig());
						registroGuardado.setFacingPrevio(varSfmActualizado.getFacingPrevio());
						registroGuardado.setCapacidad(varSfmActualizado.getCapacidad());
						registroGuardado.setCapacidadOrig(varSfmActualizado.getCapacidadOrig());
						registroGuardado.setLmin(varSfmActualizado.getLmin());

						//							//Si la fecha de creacion es mayor que sysdate-20
						//							if(this.vArtSfmService.comprobarFechaDeCreacion(varSfmActualizado)){
						//								registroGuardado.setLsf((double) -100);
						//							}else{
						//								registroGuardado.setLsf(varSfmActualizado.getLsf());
						//							}

						registroGuardado.setSfm(varSfmActualizado.getSfm());
						registroGuardado.setSfmOrig(varSfmActualizado.getSfmOrig());
						registroGuardado.setCoberturaSfm(varSfmActualizado.getCoberturaSfm());
						registroGuardado.setCoberturaSfmOrig(varSfmActualizado.getCoberturaSfmOrig());
						if (varSfmActualizado.getCodError() == null || varSfmActualizado.getCodError().equals(Constantes.SFMCAP_CODIGO_GUARDADO_CORRECTO)){
							registroGuardado.setCodError(Constantes.SFMCAP_CODIGO_MENSAJE_GUARDADO);
						}else{
							registroGuardado.setCodError(varSfmActualizado.getCodError());
							registroGuardado.setDescError(varSfmActualizado.getDescError());
						}


						//Actualizamos la lista con el registro.
						listaGuardada.set(indiceRegistroGuardado, registroGuardado);
					}
				}
				else{
					if (sfmCapacidadFacingActualizacion != null && sfmCapacidadFacingActualizacion.getEstado() != null && sfmCapacidadFacingActualizacion.getEstado().longValue() != 0){
						sfmCapacidadFacingPagina.setEstado(sfmCapacidadFacingActualizacion.getEstado());
						sfmCapacidadFacingPagina.setDescEstado(sfmCapacidadFacingActualizacion.getDescEstado());
					}
				}
			}


			/* Peticion: 49285 (Solo Textil)
			 * Tratamos el gurdado de las referencias hijas de las referencias lote que anteriormente han sido actualizadas.
			 * Todo subgrid que haya sido consultado o modificado se ha gurdado en una hastable que se guarda en sesion. La 
			 * clave es la referencia lote que corresponde a cada subgrid (relacion de rerencias hijas). 
			 * Si alguna de las referencias hijas ha sido modificada (con un valor <> 0), la referencia lote habra sido 
			 * actualizada con facingCentro = 1 y el codArticulo de esta referencia la habremos guardado en la lista 
			 * "listaReferenciasLoteModificadas". Recorremos esta ultima lista para saber que subgrid (relacion de rerencias 
			 * hijas)ha sido modificado y actualizarlo
			 */

			if (listaReferenciasLoteModificadas != null) {

				mapRelLoteHijas =  (HashMap<String, SfmCapacidadFacing>) session.getAttribute("hashMapRelLoteHijas");

				for (int i=0; i<listaReferenciasLoteModificadas.size(); i++){

					if (mapRelLoteHijas.containsKey(String.valueOf(listaReferenciasLoteModificadas.get(i)))) {

						SfmCapacidadFacing sfmCapacidadFacingActualizacionSubgrid = null;

						SfmCapacidadFacing sfmCapacidadFacingGuardadoSubgrid = mapRelLoteHijas.get(String.valueOf(listaReferenciasLoteModificadas.get(i)));
						List<VArtSfm> listaGuardadaSesionSubgrid = sfmCapacidadFacingGuardadoSubgrid.getDatos();

						if (Constantes.SFMCAP_TIPO_LISTADO_FAC.equals(tipoListado)){

							sfmCapacidadFacingActualizacionSubgrid = this.vArtSfmService.actualizacionFac(listaGuardadaSesionSubgrid, usuario);
						}

						//Recarga de la lista actualizada en la lista guardada
						if (sfmCapacidadFacingActualizacionSubgrid != null && sfmCapacidadFacingActualizacionSubgrid.getDatos() != null && sfmCapacidadFacingActualizacionSubgrid.getDatos().size() > 0){

							List<VArtSfm> listaDatosActualizadosSubgrid = sfmCapacidadFacingActualizacionSubgrid.getDatos();

							for (int j=0; j<listaDatosActualizadosSubgrid.size(); j++){

								VArtSfm varSfmActualizadoSubgrid = (VArtSfm) listaDatosActualizadosSubgrid.get(j); //Lo que ha devuelto el procedimiento
								VArtSfm aux = new VArtSfm();
								aux.setCodLoc(varSfmActualizadoSubgrid.getCodLoc());
								aux.setCodArticulo(varSfmActualizadoSubgrid.getCodArticulo());
								int indice = listaGuardadaSesionSubgrid.indexOf(aux);
								VArtSfm varSfmGuardadoSubgrid = (VArtSfm) listaGuardadaSesionSubgrid.get(indice); // Lo que esta guardado en sesion

								//Actualizamos los valores que me llegan.
								varSfmGuardadoSubgrid.setFacingCentro(varSfmActualizadoSubgrid.getFacingCentro());
								varSfmGuardadoSubgrid.setFacingCentroOrig(varSfmActualizadoSubgrid.getFacingCentroOrig());
								varSfmGuardadoSubgrid.setFacingPrevio(varSfmActualizadoSubgrid.getFacingPrevio());
								varSfmGuardadoSubgrid.setCapacidad(varSfmActualizadoSubgrid.getCapacidad());
								varSfmGuardadoSubgrid.setCapacidadOrig(varSfmActualizadoSubgrid.getCapacidadOrig());
								varSfmGuardadoSubgrid.setLmin(varSfmActualizadoSubgrid.getLmin());


								varSfmGuardadoSubgrid.setSfm(varSfmActualizadoSubgrid.getSfm());
								varSfmGuardadoSubgrid.setSfmOrig(varSfmActualizadoSubgrid.getSfmOrig());
								varSfmGuardadoSubgrid.setCoberturaSfm(varSfmActualizadoSubgrid.getCoberturaSfm());
								varSfmGuardadoSubgrid.setCoberturaSfmOrig(varSfmActualizadoSubgrid.getCoberturaSfmOrig());
								if (varSfmActualizadoSubgrid.getCodError() == null || varSfmActualizadoSubgrid.getCodError().equals(Constantes.SFMCAP_CODIGO_GUARDADO_CORRECTO)){
									varSfmGuardadoSubgrid.setCodError(Constantes.SFMCAP_CODIGO_MENSAJE_GUARDADO);
								}else{
									varSfmGuardadoSubgrid.setCodError(varSfmActualizadoSubgrid.getCodError());
								}

								//Actualizamos la lista de sesion con el registro actualizado.
								listaGuardadaSesionSubgrid.set(indice, varSfmGuardadoSubgrid);
								sfmCapacidadFacingGuardadoSubgrid.setDatos(listaGuardadaSesionSubgrid);

								//Actualizamos la hastable de subgrids de sesion
								mapRelLoteHijas.put(String.valueOf(listaReferenciasLoteModificadas.get(i)), sfmCapacidadFacingGuardadoSubgrid);
							}
						}

					}
				}

				session.setAttribute("hashMapRelLoteHijas", mapRelLoteHijas);

			}



			/**
			 * P-50987
			 * Sumatorio de facing para la gestion de huecos libres
			 * @author BICUGUAL 
			 */
			Long sumatorio=new Long(0);
			for (VArtSfm item:listaGuardada){
				sumatorio+=item.getFacingCentro()!=null ? item.getFacingCentro(): 0;
			}
			sfmCapacidadFacingPagina.setSumatorio(sumatorio);				


			//Actualizamos la lista en sesi�n.
			sfmCapacidadGuardada.setDatos(listaGuardada);
			session.setAttribute("listaSfmCapacidadFacing", sfmCapacidadGuardada);

			if (listaGuardada != null){ 
				records = listaGuardada.size();
				if (elemInicio <= records){
					if (elemFinal > records){
						elemFinal = new Long(records);
					}
					listaRecarga = (listaGuardada).subList(Integer.parseInt(elemInicio.toString()), Integer.parseInt(elemFinal.toString()));
				}
			}


		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<VArtSfm> listaSfmPaginada = null;

		if (listaRecarga != null) {
			listaSfmPaginada = this.paginationManager.paginate(new Page<VArtSfm>(), listaRecarga,
					max.intValue(), records, page.intValue());	
			sfmCapacidadFacingPagina.setDatos(listaSfmPaginada);
			if (sfmCapacidadFacingPagina.getEstado() == null){
				sfmCapacidadFacingPagina.setEstado(sfmCapacidadGuardada.getEstado());
			}
			if (sfmCapacidadFacingPagina.getDescEstado() == null){
				sfmCapacidadFacingPagina.setDescEstado(sfmCapacidadGuardada.getDescEstado());
			}
			sfmCapacidadFacingPagina.setFlgCapacidad(sfmCapacidadGuardada.getFlgCapacidad());
			sfmCapacidadFacingPagina.setFlgFacing(sfmCapacidadGuardada.getFlgFacing());

		} else {
			sfmCapacidadFacingPagina.setDatos(new Page<VArtSfm>());
			if (sfmCapacidadFacingPagina.getEstado() == null){
				sfmCapacidadFacingPagina.setEstado(new Long(0));
			}
			if (sfmCapacidadFacingPagina.getDescEstado() == null){
				sfmCapacidadFacingPagina.setDescEstado("");
			}
		}


		return sfmCapacidadFacingPagina;
	}

	//Se utiliza para cargar los combos de AGRUPACION COMERCIAL
	@RequestMapping(value="/loadAreaData", method = RequestMethod.POST)
	public @ResponseBody List<VAgruComerParamSfmcap> getAreaData(
			@RequestBody VAgruComerParamSfmcap vAgruCommerAgruComerParamSfmcap,
			HttpSession session, HttpServletResponse response) throws Exception {
		try {
			return this.vAgruComerParamSfmcapService.findAll(vAgruCommerAgruComerParamSfmcap, null);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value = "/loadArea", method = RequestMethod.POST)
	List<VAgruComerParamSfmcapService> loadAreaData(
			HttpServletResponse response,
			HttpSession session) throws Exception{
		try {
			return new ArrayList<VAgruComerParamSfmcapService>();
			// re
		} catch (Exception e) {

			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	/**
	 * P-50987
	 * Devuelve el numero de huecos para los parametros seleccionados en los selectores
	 * @param vArtSfm
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 * @author BICUGUAL
	 */
	@RequestMapping(value="/getHuecos", method = RequestMethod.POST)
	public @ResponseBody Integer getHuecos(	
			@RequestBody VArtSfm vArtSfm,
			HttpSession session, 
			HttpServletResponse response) throws Exception {
		try {
			User user=(User) session.getAttribute("user");

			if (vArtSfm.getCodN5()==null) { //Mostrar los huecos a nivel de subcategoria

				return huecosElectroService.getNumHuecosFinalSubCat(
						user.getCentro().getCodCentro(),
						vArtSfm.getCodN1()!=null?new Long(vArtSfm.getCodN1()):null,
								vArtSfm.getCodN2()!=null?new Long(vArtSfm.getCodN2()):null,
										vArtSfm.getCodN3()!=null?new Long(vArtSfm.getCodN3()):null,
												vArtSfm.getCodN4()!=null?new Long(vArtSfm.getCodN4()):null);

			} else { //Mostrar los huecos a nivel de segmento

				return huecosElectroService.getNumHuecosFinalSeg(
						user.getCentro().getCodCentro(),
						vArtSfm.getCodN1()!=null?new Long(vArtSfm.getCodN1()):null,
								vArtSfm.getCodN2()!=null?new Long(vArtSfm.getCodN2()):null,
										vArtSfm.getCodN3()!=null?new Long(vArtSfm.getCodN3()):null,
												vArtSfm.getCodN4()!=null?new Long(vArtSfm.getCodN4()):null,
														vArtSfm.getCodN5()!=null?new Long(vArtSfm.getCodN5()):null);
			}


		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}



	@RequestMapping(value="/recalcularCapacidad", method = RequestMethod.POST)
	public @ResponseBody Long recalcularCapacidad(	
			@RequestBody VArtSfm vArtSfm,
			HttpSession session, 
			HttpServletResponse response) throws Exception {
		try {
			User user=(User) session.getAttribute("user");

			Long capacidad = null;

			//Si el centro es de caprabo, calculamos su código de eroski, ya que los procedimientos plsql solo aceptan 
			//códigos de eroski.
			if(user.getCentro().esCentroCaprabo()){
				Long codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), vArtSfm.getCodArticulo());
				vArtSfm.setCodArticulo(codArtEroski);
			}
			capacidad = this.vArtSfmService.recalcularCapacidad(vArtSfm);

			return capacidad;

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	private String obtenerClaveRegistro(String codN1, String codN2, String codN3, String codN4, String codN5, Long codArticulo){

		StringBuffer claveRegistroBuff = new StringBuffer();

		claveRegistroBuff.append((codN1 != null && !"".equals(codN1))? Integer.parseInt(codN1)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN2 != null && !"".equals(codN2))? Integer.parseInt(codN2)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN3 != null && !"".equals(codN3))? Integer.parseInt(codN3)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN4 != null && !"".equals(codN4))? Integer.parseInt(codN4)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN5 != null && !"".equals(codN5))? Integer.parseInt(codN5)+"" : "");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codArticulo!=null)?codArticulo:"");

		return claveRegistroBuff.toString();
	}

//	public List<VArtSfm> formatearLsfExport(List<VArtSfm> listaSfm){
//
//		/* aplicar condiciones limite superior*/
//		for (int i = 0; i < listaSfm.size();i++) {
//
//			if(listaSfm.get(i).getFechaSfmDDMMYYYY().length()!=0){
//				//Formateamos la fechaSFM a date
//				String getfechaSfm = listaSfm.get(i).getFechaSfmDDMMYYYY().substring(0,2) +""+  listaSfm.get(i).getFechaSfmDDMMYYYY().substring(2,4)  +""+  listaSfm.get(i).getFechaSfmDDMMYYYY().substring(4);
//				Calendar fechaSfm = Calendar.getInstance();
//				fechaSfm .setTime(Utilidades.convertirStringAFecha(getfechaSfm));
//
//
//				//Obtenemos la fecha actual
//				Calendar fechaActual = Calendar.getInstance();
//
//
//				//Obtenemos  la diferencia entre las dos fechas 
//				long c = 24*60*60*1000;
//
//				long diffDays = (long) Math.floor((fechaActual.getTimeInMillis() - fechaSfm.getTimeInMillis())/(c));
//
//				if (diffDays <= 21) {
//					//Si la diferencia entre fechas es menor o igual que 21, entonces el limite superior se pondra a ""
//					listaSfm.get(i).setLsf(null);
//				}else {
//					//sfmCapacidadFacingPagina.getDatos().getRows().get(i).setLsf(sfmCapacidadFacingPagina.getDatos().getRows().get(i).getLmin());
//				}
//			}
//		}
//
//		return listaSfm;
//
//	}

	@RequestMapping(value = "/obtenerEstructura", method = RequestMethod.POST)
	public @ResponseBody	
	VAgruComerParamSfmcap obtenerEstructura(
			@RequestParam(required = false) Long codigoArticulo,
			HttpServletResponse response, HttpSession session) throws Exception{
		try{
			User user = (User) session.getAttribute("user");

			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			VDatosDiarioArt vDatosDiarioArtRes =  new VDatosDiarioArt();
			VAgruComerParamSfmcap estructuraArticulo = new VAgruComerParamSfmcap();
			if (codigoArticulo != null && !"".equals(codigoArticulo)){

				Long codArtEroski = codigoArticulo;
				if(user.getCentro().esCentroCaprabo()){
					codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), codigoArticulo);
				}
				vDatosDiarioArt.setCodArt(codArtEroski);
				vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

				if (null != vDatosDiarioArtRes){

					VAgruComerParamSfmcap vAgruComerParamSfmcap = new VAgruComerParamSfmcap();
					vAgruComerParamSfmcap.setCodCentro(user.getCentro().getCodCentro());
					vAgruComerParamSfmcap.setGrupo1(vDatosDiarioArtRes.getGrupo1());
					vAgruComerParamSfmcap.setGrupo2(vDatosDiarioArtRes.getGrupo2());
					vAgruComerParamSfmcap.setGrupo3(vDatosDiarioArtRes.getGrupo3());
					vAgruComerParamSfmcap.setGrupo4(vDatosDiarioArtRes.getGrupo4());
					vAgruComerParamSfmcap.setGrupo5(vDatosDiarioArtRes.getGrupo5());
					List<VAgruComerParamSfmcap> listaEstructuras = this.vAgruComerParamSfmcapService.findAll(vAgruComerParamSfmcap, null);

					if (listaEstructuras != null && listaEstructuras.size() > 0){
						estructuraArticulo = listaEstructuras.get(0);
					}
				} 
			}
			return estructuraArticulo;

		}catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@RequestMapping(value = "/exportGrid", method = RequestMethod.GET)
	public @ResponseBody
	void exportGrid(
			@RequestParam(required = false) String[] model,
			@RequestParam(required = false) Integer[] widths,
			@RequestParam(required = false) Long centro,
			@RequestParam(required = false) Long grupo1,
			@RequestParam(required = false) Long grupo2,
			@RequestParam(required = false) Long grupo3,
			@RequestParam(required = false) Long grupo4,
			@RequestParam(required = false) Long grupo5,
			@RequestParam(required = false) String marcaMaestroCentro,
			@RequestParam(required = false) String pedir,
			@RequestParam(required = false) String loteSN,
			@RequestParam(required = false) Long codigoArticulo,
			@RequestParam(required = false) String tipoListado,
			HttpServletResponse response, HttpSession session) throws Exception{
		try {

			User user = (User) session.getAttribute("user");

			VArtSfm vArtSfm = new VArtSfm();
			vArtSfm.setCodLoc(centro);
			if (null!=grupo1) vArtSfm.setCodN1(Long.toString(grupo1));
			if (null!=grupo2) vArtSfm.setCodN2(Long.toString(grupo2));
			if (null!=grupo3) vArtSfm.setCodN3(Long.toString(grupo3));
			if (null!=grupo4) vArtSfm.setCodN4(Long.toString(grupo4));
			if (null!=grupo5) vArtSfm.setCodN5(Long.toString(grupo5));
			if ((null!=pedir) && (!pedir.equals("null"))) vArtSfm.setPedir(pedir);
			if ((null!=loteSN)  && (!loteSN.equals("null")))vArtSfm.setLoteSN(loteSN);

			//Busqueda
			SfmCapacidadFacing sfmCapacidadFacing = null;
			List<VArtSfm> listaSfm = null;
			// Referencia
			if (codigoArticulo != null && !"".equals(codigoArticulo)){
				VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
				VDatosDiarioArt vDatosDiarioArtRes =  new VDatosDiarioArt();

				Long codArtEroski = codigoArticulo;
				if(user.getCentro().getEsCentroCaprabo()){
					codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(), codigoArticulo);
				}

				vArtSfm.setCodArticulo(codArtEroski);
				vDatosDiarioArt.setCodArt(codArtEroski);
				vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

				if (null != vDatosDiarioArtRes){

					VAgruComerParamSfmcap vAgruComerParamSfmcap = new VAgruComerParamSfmcap();
					vAgruComerParamSfmcap.setCodCentro(user.getCentro().getCodCentro());
					vAgruComerParamSfmcap.setGrupo1(vDatosDiarioArtRes.getGrupo1());
					vAgruComerParamSfmcap.setGrupo2(vDatosDiarioArtRes.getGrupo2());
					vAgruComerParamSfmcap.setGrupo3(vDatosDiarioArtRes.getGrupo3());
					vAgruComerParamSfmcap.setGrupo4(vDatosDiarioArtRes.getGrupo4());
					vAgruComerParamSfmcap.setGrupo5(vDatosDiarioArtRes.getGrupo5());
					String tipoEstructura = this.vAgruComerParamSfmcapService.findTipoEstructura(vAgruComerParamSfmcap);

					boolean bolFlgFacing = Constantes.SFMCAP_TIPO_LISTADO_FAC.equals(tipoEstructura) || Constantes.SFMCAP_TIPO_LISTADO_FACCAP.equals(tipoEstructura); 

					if (bolFlgFacing){
						sfmCapacidadFacing = this.vArtSfmService.consultaFac(vArtSfm);
					} else if (Constantes.SFMCAP_TIPO_LISTADO_SFM.equals(tipoEstructura)){
						sfmCapacidadFacing = this.vArtSfmService.consultaSfm(vArtSfm);
					} else if(Constantes.SFMCAP_TIPO_LISTADO_CAP.equals(tipoEstructura)){
						sfmCapacidadFacing = this.vArtSfmService.consultaCap(vArtSfm);
					}
				}
			} 
			// Estructura
			else {
				if (Constantes.SFMCAP_TIPO_LISTADO_FAC.equals(tipoListado)){
					sfmCapacidadFacing = this.vArtSfmService.consultaFac(vArtSfm);
				}else if (Constantes.SFMCAP_TIPO_LISTADO_CAP.equals(tipoListado)){
					sfmCapacidadFacing = this.vArtSfmService.consultaCap(vArtSfm);
				}else{
					sfmCapacidadFacing = this.vArtSfmService.consultaSfm(vArtSfm);

				}
			}

			if (sfmCapacidadFacing != null){
				if (sfmCapacidadFacing.getDatos() != null){
					listaSfm = sfmCapacidadFacing.getDatos();	
				}
			}

			if (listaSfm != null && listaSfm.size()>0) {
				//Formateamos el campo Lsf segun fecha
				listaSfm = Utilidades.formatearLsfExport(listaSfm);
			}

			//Insertamos el código de artículo caprabo o eroski, ya que en la cabecera del excell es el que pone.
			//Si no realizaramos este paso, siempre tendría en la cabecera el código de Eroski.
			vArtSfm.setCodArticulo(codigoArticulo);


			VAgruComerRef vAgruComerGrupoArea=null;
			VAgruComerRef vAgruComerGrupoSection=null;
			VAgruComerRef vAgruComerGrupoCategory=null;
			VAgruComerRef vAgruComerGrupoSubCategory=null;
			VAgruComerRef vAgruComerGrupoSegment=null;

			if (grupo1!=null){
				vAgruComerGrupoArea=this.vAgruComerRefService.findAll(new VAgruComerRef("I1",grupo1,null,null,null,null,null), null).get(0);
			}
			if (grupo2!=null){
				vAgruComerGrupoSection = this.vAgruComerRefService.findAll(new VAgruComerRef("I2",grupo1,grupo2,null,null,null,null), null).get(0);
			}
			if (grupo3!=null){
				vAgruComerGrupoCategory =this.vAgruComerRefService.findAll(new VAgruComerRef("I3",grupo1,grupo2,grupo3,null,null,null), null).get(0);
			}	
			if (grupo4!=null){
				vAgruComerGrupoSubCategory=this.vAgruComerRefService.findAll(new VAgruComerRef("I4",grupo1,grupo2,grupo3,grupo4,null,null), null).get(0);
			}
			if (grupo5!=null){
				vAgruComerGrupoSegment=this.vAgruComerRefService.findAll(new VAgruComerRef("I5",grupo1,grupo2,grupo3,grupo4,grupo5,null), null).get(0);
			} 

			logger.info("Exporting excel SFM/FAC/CAP:"+model.toString());


			this.excelManager.exportSfmCapacidad(listaSfm, model, widths, this.messageSource,user.getCentro(),vArtSfm,vAgruComerGrupoArea,
					vAgruComerGrupoSection,vAgruComerGrupoCategory,vAgruComerGrupoSubCategory,vAgruComerGrupoSegment,
					marcaMaestroCentro, pedir, loteSN, tipoListado,  response);


		}catch(Exception e) {

			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

	}

}