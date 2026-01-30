package es.eroski.misumi.control;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.AlarmaPLU;
import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.model.FechaProximaEntrega;
import es.eroski.misumi.model.FechaProximaEntregaRef;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoPda;
import es.eroski.misumi.model.PendientesRecibir;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.model.VReferenciasPedirSIA;
import es.eroski.misumi.model.pda.PdaDatosReferencia;
import es.eroski.misumi.service.iface.DiasServicioService;
import es.eroski.misumi.service.iface.FechaProximasEntregasService;
import es.eroski.misumi.service.iface.PedidoAdicionalEService;
import es.eroski.misumi.service.iface.PedidoPdaService;
import es.eroski.misumi.service.iface.PendientesRecibirService;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.VBloqueoEncargosPiladasService;
import es.eroski.misumi.service.iface.VFestivoCentroService;
import es.eroski.misumi.service.iface.VReferenciasPedirSIAService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP51LanzarEncargoController {

	private static Logger logger = Logger.getLogger(pdaP51LanzarEncargoController.class);

	@Autowired
	private PedidoPdaService pedidoPdaService;
	
	@Autowired
	private VReferenciasPedirSIAService vReferenciasPedirSIAService;
	
	@Autowired
	private VBloqueoEncargosPiladasService vBloqueoEncargosPiladasService;

	@Autowired
	private RelacionArticuloService relacionArticuloService;
	
	@Autowired
	private PendientesRecibirService pendientesRecibirService;
	
	@Autowired
	private FechaProximasEntregasService fechaProximasEntregasService;

	@Autowired
	private DiasServicioService diasServicioService;
	
	@Autowired
	private PedidoAdicionalEService pedidoAdicionalEService;
	
	@Autowired
	private VFestivoCentroService vFestivoCentroService;
	

	/**
	 * Mapea el objeto contenido en PedidoPda (que contiene un booleano para enseñar o no el link de esta pantalla y un objeto de tipo 
	 * pedidoAdicionalCompleto) llamado pedidoAdicionalCompleto conseguido en p12 en un objeto PedidoAdicionaE. Los datos de ese último
	 * objeto tendrán el formato especificado en la petición 55700. Ese objeto se utilizará para rellenar las distintas secciones de la 
	 * pantalla (fecha, excluir,cajas) y así mostrar al usuario la información del nuevo pedido a realizar.
	 * @throws Exception 
	 * */
	@RequestMapping(value = "/pdaP51LanzarEncargos", method = RequestMethod.GET)
	public String showForm(@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE
						  ,	ModelMap model,	HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {

		//Se crea un objeto de PedidoAdicionalCompleto vacío para insertarle las fechas con formato y demás 
		//datos.También servirá para tratar los datos vacíos (Si no tiene descripción, código de artículo etc.)
		PedidoAdicionalCompleto pedidoAdicionalCompletoPda = new PedidoAdicionalCompleto();

		//Obtiene el objeto PedidoAdicionalCompleto contenido en PedidoPda.
		PedidoAdicionalCompleto pedidoAdicionalCompleto = ((PedidoPda) session.getAttribute("pedidoPda")).getPedidoAdicionalCompleto();

		/********************************************************************************/
		/*     Código traído de la pantalla previa para obtener el primer día hábil     */
		/********************************************************************************/
		DiasServicio diasServicio = new DiasServicio();
		diasServicio.setCodCentro(pedidoAdicionalCompleto.getCodCentro());
		diasServicio.setCodArt(pedidoAdicionalCompleto.getCodArt());
		diasServicio.setClasePedido(new Long(pedidoAdicionalCompleto.getTipoPedido()));

		this.diasServicioService.cargarDiasServicio(diasServicio, session.getId(), pedidoAdicionalCompleto.getCodFpMadre(), session);
		this.diasServicioService.actualizarDiasServicio(pedidoAdicionalCompleto.getCodCentro(), pedidoAdicionalCompleto.getCodArt(), pedidoAdicionalCompleto.getCodFpMadre(), session.getId());
		
		//Hay que calcular la fecha de inicio teniendo en cuenta los días deshabilitados del calendario
		Date fechaMinimaCalendario = pedidoAdicionalCompleto.getFechaIni();
		if(pedidoAdicionalCompleto.getMinFechaIni().after(pedidoAdicionalCompleto.getFechaIni())){
			fechaMinimaCalendario = pedidoAdicionalCompleto.getMinFechaIni();
		}
		
		diasServicio.setFecha(fechaMinimaCalendario);
		String primerDiaHabilInicio = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, pedidoAdicionalCompleto.getCodFpMadre(), session.getId());
		if (primerDiaHabilInicio != null){
			pedidoAdicionalCompleto.setFechaIni(Utilidades.convertirStringAFecha(primerDiaHabilInicio));
			pedidoAdicionalCompleto.setStrFechaIni(primerDiaHabilInicio);
			if (Utilidades.convertirStringAFecha(primerDiaHabilInicio).after(fechaMinimaCalendario)){
				pedidoAdicionalCompleto.setFechaNoDisponible(true);
			}else{
				pedidoAdicionalCompleto.setFechaNoDisponible(false);
			}
		}else{
			pedidoAdicionalCompleto.setFechaNoDisponible(false);
		}

		//Hay que calcular la fecha de fin teniendo en cuenta los días deshabilitados del calendario (de momento no se calcula)
		diasServicio.setFecha(pedidoAdicionalCompleto.getFechaFin());
		String primerDiaHabilFin = this.diasServicioService.getPrimerDiaHabilitado(diasServicio, pedidoAdicionalCompleto.getCodFpMadre(), session.getId());
		if (primerDiaHabilFin != null){
			pedidoAdicionalCompleto.setFechaFin(Utilidades.convertirStringAFecha(primerDiaHabilFin));
			pedidoAdicionalCompleto.setStrFechaFin(primerDiaHabilFin);
		}

		if (null != pedidoAdicionalCompleto.getFrescoPuro() && pedidoAdicionalCompleto.getFrescoPuro().booleanValue()){
			this.setFechas(pedidoAdicionalCompleto);
		}

		//Calculamos las unidades pedidas si la fecha minima sugerida y la fecha de primerDiaHabilitado no coinciden
		if(pedidoAdicionalCompleto.getFechaNoDisponible()){
			//Guardará el objeto con las unidades pedidas en la fecha mínima.
			PedidoAdicionalE pedidoAdicionalUnidades = null;

			//Fecha a buscar
			DiasServicio diaEncargoRealizado = new DiasServicio();
			diaEncargoRealizado.setCodCentro(pedidoAdicionalCompleto.getCodCentro());
			diaEncargoRealizado.setCodArt(pedidoAdicionalCompleto.getCodArt());
			diaEncargoRealizado.setClasePedido(new Long(pedidoAdicionalCompleto.getTipoPedido()));
			diaEncargoRealizado.setFechaDesde(fechaMinimaCalendario);
			diaEncargoRealizado.setIdsesion(session.getId()+"_"+diasServicio.getCodArt());

			//Buscamos para la fecha mínima sugerida, sesión, artículo y centro, el registro que contiene el identifiacor de PBL o SIA
			DiasServicio diasServicioRes = this.diasServicioService.findOne(diaEncargoRealizado);

			//Si devuelve dato.
			if(diasServicioRes != null){	
				PedidoAdicionalE pedidoAdicionalE = new PedidoAdicionalE();

				pedidoAdicionalE.setClasePedido(new Long(Constantes.CLASE_PEDIDO_ENCARGO));
				pedidoAdicionalE.setCodCentro(diasServicioRes.getCodCentro());
				pedidoAdicionalE.setCodArticulo(diasServicioRes.getCodArt());
				pedidoAdicionalE.setIdentificador(diasServicioRes.getIdentificador());
				pedidoAdicionalE.setIdentificadorSIA(diasServicioRes.getIdentificadorSIA());																											

				//Obtenemos la lista de encargos con esa referencia, centro.
				List<PedidoAdicionalE> pedidoAdicionalELst = this.pedidoAdicionalEService.findAll(pedidoAdicionalE, session);	
				if(pedidoAdicionalELst != null && pedidoAdicionalELst.size() > 0){															
					if(pedidoAdicionalE.getIdentificador() != null){
						for(PedidoAdicionalE pedidoAdicionalElemList:pedidoAdicionalELst){
							if(pedidoAdicionalElemList.getIdentificador().equals(pedidoAdicionalE.getIdentificador())){
								pedidoAdicionalUnidades = pedidoAdicionalElemList;
								break;
							}
						}
					}
					if(pedidoAdicionalE.getIdentificadorSIA() != null){
						for(PedidoAdicionalE pedidoAdicionalElemList:pedidoAdicionalELst){
							if(pedidoAdicionalElemList.getIdentificadorSIA().equals(pedidoAdicionalE.getIdentificadorSIA())){
								pedidoAdicionalUnidades = pedidoAdicionalElemList;
								break;
							}
						}
					}															
				}
			}
			
			if(pedidoAdicionalUnidades != null){
				pedidoAdicionalCompleto.setUnidadesPedidas(pedidoAdicionalUnidades.getUnidadesPedidas().longValue());
			}
		}

		this.comprobacionBloqueos(pedidoAdicionalCompleto, session);
		this.volcadoFechas(pedidoAdicionalCompleto);
		
		/********************************************************************************/
		/* FIN del Código traído de la pantalla previa para obtener el primer día hábil */
		/********************************************************************************/
		

		//Código y descripcion de articulo. En el caso de no existir se introducirán 0 y Sin artículo.
		if(pedidoAdicionalCompleto.getCodArt() != null){
			pedidoAdicionalCompletoPda.setCodArt(pedidoAdicionalCompleto.getCodArt());
			pedidoAdicionalCompletoPda.setCodArtCaprabo(pedidoAdicionalCompleto.getCodArtCaprabo());
		}else{
			pedidoAdicionalCompletoPda.setCodArt(Long.parseLong("0"));
			pedidoAdicionalCompletoPda.setCodArtCaprabo(Long.parseLong("0"));
		}
		if(pedidoAdicionalCompleto.getDescArt() != null){			
			pedidoAdicionalCompletoPda.setDescArt(pedidoAdicionalCompleto.getDescArt());
		}else{
			pedidoAdicionalCompletoPda.setDescArt("Sin artículo");
		}

		//Fecha inicio. Se quiere con el formato L 24-ABR 2017. En el caso de no existir fecha se pondrá un guión
		if(pedidoAdicionalCompleto.getFechaIni() != null){
			SimpleDateFormat parseFormat =  new SimpleDateFormat("ddMMyyyy");
			Date date = parseFormat.parse(pedidoAdicionalCompleto.getStrFechaIni());

			pedidoAdicionalCompletoPda.setStrFechaIni(Utilidades.obtenerFechaFormateadaEEEddMMMyyyy(date));
		}else{
			pedidoAdicionalCompletoPda.setStrFechaIni("-");
		}

		//Unidad cajas y cantidad.
		if(pedidoAdicionalCompleto.getUniCajas() != null){
			pedidoAdicionalCompletoPda.setUniCajas(pedidoAdicionalCompleto.getUniCajas());
		}else{
			pedidoAdicionalCompletoPda.setUniCajas(Double.parseDouble("0.00"));
		}

		if(pedidoAdicionalCompleto.getCantidad1() != null){
			pedidoAdicionalCompletoPda.setCantidad1(pedidoAdicionalCompleto.getCantidad1());
		}else{
			pedidoAdicionalCompletoPda.setCantidad1(Double.parseDouble("1"));
		}

		//Conseguir cajas y excluir
		if(pedidoAdicionalCompleto.getCajas() != null){
			pedidoAdicionalCompletoPda.setCajas(pedidoAdicionalCompleto.getCajas());			
		}else{
			pedidoAdicionalCompletoPda.setCajas("N");			
		}

		if(pedidoAdicionalCompleto.getExcluir() != null){
			pedidoAdicionalCompletoPda.setExcluir(pedidoAdicionalCompleto.getExcluir());			
		}else{
			pedidoAdicionalCompletoPda.setExcluir("N");
		}
		
		//Conseguir flag de aviso lote
		if(pedidoAdicionalCompleto.getFlgAvisoEncargoLote() != null){
			pedidoAdicionalCompletoPda.setFlgAvisoEncargoLote(pedidoAdicionalCompleto.getFlgAvisoEncargoLote());			
		}
		
		//Conseguir flag de mostrar campos cajas y excluir.
		//No se muestran si está en la tabla V_REFERENCIAS_PEDIR_SIA y el campo TIPO es igual a HORIZONTE.
		VReferenciasPedirSIA vReferenciasPedirSIA = new VReferenciasPedirSIA();
		vReferenciasPedirSIA.setCodCentro(pedidoAdicionalCompleto.getCodCentro());
		vReferenciasPedirSIA.setCodArt(pedidoAdicionalCompleto.getCodArt());
		VReferenciasPedirSIA vReferenciasPedirSIAResponse= vReferenciasPedirSIAService.findOne(vReferenciasPedirSIA);
		if(vReferenciasPedirSIAResponse != null && Constantes.V_REFERENCIA_PEDIR_SIA_TIPO_HORIZONTE.equals(vReferenciasPedirSIAResponse.getTipo())){
			pedidoAdicionalCompletoPda.setShowExcluirAndCajas(false);			
		}else{
			pedidoAdicionalCompletoPda.setShowExcluirAndCajas(true);			
		}
		
		//Como la primera fecha disponible está ocupada por un encargo o montajes, hay que avisar al usuario de que esa fecha ya está ocupada para que no
		//haga otro encargo en caso de no necesitarlo.
		if(pedidoAdicionalCompleto.getFechaNoDisponible()){
			pedidoAdicionalCompletoPda.setFechaNoDisponible(pedidoAdicionalCompleto.getFechaNoDisponible());
			pedidoAdicionalCompletoPda.setUnidadesPedidas(pedidoAdicionalCompleto.getUnidadesPedidas());
		}
		
		if(pedidoAdicionalCompleto.getArea()==1){
			pedidoAdicionalCompletoPda.setTratamiento("S");
			
		}else{
			pedidoAdicionalCompletoPda.setTratamiento("A");
		}
		
		//Obtenemos Pendientes 1/2
		final Long codArt = pedidoAdicionalCompletoPda.getCodArt();
		User user = (User)session.getAttribute("user");
		final Long codCentro = user.getCentro().getCodCentro();
		
		final PendientesRecibir pr = getPendienteRecibir(codArt, codCentro);
		if (pr!=null){
			pedidoAdicionalCompletoPda.setCantHoy(pr.getCantHoy());
			pedidoAdicionalCompletoPda.setCantFutura(pr.getCantFutura());
		}
		final String proximaFechaEntregaString = getProximaFechaEntrega(codArt,codCentro);
		pedidoAdicionalCompletoPda.setFechaProximaEntrega(proximaFechaEntregaString);

		model.addAttribute("pedidoAdicionalCompletoPda",pedidoAdicionalCompletoPda);	
		model.addAttribute("origenGISAE",origenGISAE);
		return "pda_p51_lanzarEncargo";
	}	

	private PendientesRecibir getPendienteRecibir(Long codArt, Long codCentro) throws Exception {
		//Obtener referencias promocionales
		final RelacionArticulo relacionArticulo1 = new RelacionArticulo();
		relacionArticulo1.setCodArt(codArt);
		relacionArticulo1.setCodCentro(codCentro);
		final List<Long> referencias = this.relacionArticuloService.findAll(relacionArticulo1);
		
		PendientesRecibir pr = new PendientesRecibir(codCentro,codArt);
		if (!referencias.isEmpty()){
			pr.setReferencias(referencias);
		}
		return this.pendientesRecibirService.find(pr, null);
	}

	private String getProximaFechaEntrega(Long codArt, Long codCentro) {
		String output = "";
		final FechaProximaEntregaRef fechaProximaEntregaRef = fechaProximasEntregasService.getFechaProximasEntregas(codCentro, codArt);
		Date proximaFechaEntrega = null;
		if (fechaProximaEntregaRef!=null){
			final List<FechaProximaEntrega> listaFechasProximasEntregas = fechaProximaEntregaRef.getListaFechaProximasEntregas();
			if (listaFechasProximasEntregas.size()>0){
				// Se muestra la fecha transmision mas antigua
				Collections.sort(listaFechasProximasEntregas, new Comparator<FechaProximaEntrega>() {
				    @Override
				    public int compare(FechaProximaEntrega o1, FechaProximaEntrega o2) {
				        Date value1 = o1.getFechaTransmision();
				        Date value2 = o2.getFechaTransmision();
				        return compareDate(value1, value2, "asc");
				    }
				});
				proximaFechaEntrega = listaFechasProximasEntregas.get(0).getFechaTransmision();
			}

		}
		if (proximaFechaEntrega!=null){
			output = Utilidades.obtenerFechaFormateadaEEEddMMMyyyy(proximaFechaEntrega);
		}
		return output;
	}

	/**
	 * Función creada para obtener los datos con la cantidad, excluir y cajas. Esos tres datos que se obtienen con el
	 * objeto pedidoAdicionalE, se introducirán en el objeto PedidoAdicionalCompleto. Más tarde ese objeto se guardará
	 * en PBL o SIA. Si todo funciona correctamente, se volverá a la pantalla p12. En esa pantalla se calculará el próximo
	 * día a lanzar un encargo.
	 * 
	 * */
	@RequestMapping(value = "/pdaP51LanzarEncargos", method = RequestMethod.POST)
	public String processForm(@Valid final PedidoAdicionalCompleto pedidoAdicionalCompletoPda,
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE,
			@RequestParam(value="tratamiento", required=false, defaultValue = "NO") String tratamiento,
			BindingResult result,
			ModelMap model, HttpSession session, RedirectAttributes redirectAttributes,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Se obtiene de la sesión el objeto pedidoAdicionalCompleto de PedidoPda obtenido de la pantalla p12.
		PedidoAdicionalCompleto pedidoAdicionalCompleto = ((PedidoPda) session.getAttribute("pedidoPda")).getPedidoAdicionalCompleto();
		
		/********************************************************************************/
		/* FIN del Código traído de la pantalla previa para obtener el primer día hábil */
		/********************************************************************************/

		//Se inserta el pedido
		Integer error= pedidoPdaService.insertarPedido(pedidoAdicionalCompleto, pedidoAdicionalCompletoPda, session, response);

		//Si hay un error o más redirige a pantalla de error
		if(error>0){
			return "pda_p51_showError";
		}//Si no hay error redirige a la pantalla p12 y en p12Controller recalcula el link lanzar encargo y el encargo nuevo
		else{
			redirectAttributes.addAttribute("codArt", pedidoAdicionalCompleto.getCodArt());
			redirectAttributes.addAttribute("origenGISAE",origenGISAE);

			redirectAttributes.addAttribute("greenLink",true);
		
			return "redirect:pdaP12DatosReferencia.do";
		}
	}

	/**
	 * Al pulsar la flecha de volver regresará a la pantalla p12 con el código de artículo anteriormente seleccionado.
	 * En la cabecera pda_p01_header puede verse la llamada a éste método.
	 **/
	@RequestMapping(value = "/pdaP51ReturnToP12", method = RequestMethod.GET)
	public String returnToP12(
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE,
			ModelMap model,
			HttpSession session,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request,
			HttpServletResponse response){
		
		//Conseguir el pedido para conseguir el código de artículo
		PedidoAdicionalCompleto pedidoAdicionalCompleto = ((PedidoPda) session.getAttribute("pedidoPda")).getPedidoAdicionalCompleto();

		redirectAttributes.addAttribute("codArt", pedidoAdicionalCompleto.getCodArt());
		redirectAttributes.addAttribute("origenGISAE",origenGISAE);
		redirectAttributes.addAttribute("greenLink",false);

		//Si se pulsa la flecha para volver atrás, significa o que ha habido un error
		//en la inserción del encargo (pda_p51_showError)o que el usuario en la pantalla
		//del encargo ha pulsado la flecha para volver atrás (pda_p51_lanzarEncargo). En
		//este caso no queremos que la flecha se coloree de verde. Solo querremos colorearla
		//de verde en el caso de que la inserción se haga correctamente en el método processForm
		//de éste controlador.
		
		return "redirect:pdaP12DatosReferencia.do";
	}			
	
	private int compareDate(Date value1, Date value2, String ascDsc){
		if (value1==null && value2==null){
			return 0;
		}else{
			if (ascDsc.equalsIgnoreCase("asc")){
				if (value1==null){
					return -1;
				}else if (value2==null){
					return 1;
				}else
					return value1.compareTo(value2);	
		    }else{
		    	if (value1==null){
					return 1;
				}else if (value2==null){
					return -1;
				}else
					return value2.compareTo(value1);	
		    }
		}
	}
	
	private void setFechas(PedidoAdicionalCompleto nuevoPedidoReferencia) throws Exception{
		List<Map<String,Object>> listaFechasSiguientes = null;
		VFestivoCentro vFestivoCentro = new VFestivoCentro();
		vFestivoCentro.setCodCentro(nuevoPedidoReferencia.getCodCentro());
		vFestivoCentro.setFechaInicio(nuevoPedidoReferencia.getFechaIni());
		vFestivoCentro.setFechaFin(nuevoPedidoReferencia.getFechaFin());

		listaFechasSiguientes = this.vFestivoCentroService.getNextDays(vFestivoCentro, Constantes.NUM_DIAS_INTERVALO_FRESCOS_MODIFICACION);

		if (null != nuevoPedidoReferencia.getFechaFin()){
			if (listaFechasSiguientes != null && listaFechasSiguientes.size() > 0) {//Fecha2
				Map<String,Object> m = listaFechasSiguientes.get(0);
				String strFecha2 =  (String) m.get("SIGUIENTE_DIA");
				Date fecha2 = Utilidades.convertirStringAFecha(strFecha2);
				nuevoPedidoReferencia.setFecha2(fecha2);
				nuevoPedidoReferencia.setStrFecha2(strFecha2);

				if (listaFechasSiguientes.size() > 1) {//Fecha3
					m = listaFechasSiguientes.get(1);
					String strFecha3 =  (String) m.get("SIGUIENTE_DIA");
					Date fecha3 = Utilidades.convertirStringAFecha(strFecha3);
					nuevoPedidoReferencia.setFecha3(fecha3);
					nuevoPedidoReferencia.setStrFecha3(strFecha3);

					if (listaFechasSiguientes.size() > 2) {//Fecha Pilada{
						m = listaFechasSiguientes.get(2);
						String strFechaPilada = (String) m.get("SIGUIENTE_DIA");
						Date fechaPilada = Utilidades.convertirStringAFecha(strFechaPilada);
						nuevoPedidoReferencia.setFechaPilada(fechaPilada);
						nuevoPedidoReferencia.setStrFechaPilada(strFechaPilada);

					}else{
						nuevoPedidoReferencia.setFechaPilada(null);
						nuevoPedidoReferencia.setStrFechaPilada(null);
					}
				}else{
					nuevoPedidoReferencia.setFecha3(null);
					nuevoPedidoReferencia.setStrFecha3(null);
					nuevoPedidoReferencia.setFechaPilada(null);
					nuevoPedidoReferencia.setStrFechaPilada(null);
				}
			}else{
				nuevoPedidoReferencia.setFecha2(null);
				nuevoPedidoReferencia.setFecha3(null);
				nuevoPedidoReferencia.setStrFecha2(null);
				nuevoPedidoReferencia.setStrFecha3(null);
				nuevoPedidoReferencia.setFechaPilada(null);
				nuevoPedidoReferencia.setStrFechaPilada(null);
			}
		}else{
			nuevoPedidoReferencia.setFecha2(null);
			nuevoPedidoReferencia.setFecha3(null);
			nuevoPedidoReferencia.setStrFecha2(null);
			nuevoPedidoReferencia.setStrFecha3(null);
			nuevoPedidoReferencia.setFechaPilada(null);
			nuevoPedidoReferencia.setStrFechaPilada(null);
		}
	}

	/**
	 * 
	 * @param nuevoPedidoReferencia
	 * @param session
	 * @throws Exception
	 */
	private void comprobacionBloqueos(PedidoAdicionalCompleto nuevoPedidoReferencia, HttpSession session) throws Exception{
		int numeroRegistrosBloqueo = 0;
		int numeroRegistrosBloqueoLeyenda = 0;
		if (nuevoPedidoReferencia.getTipoPedido() != null){
			User user = (User) session.getAttribute("user");
			VBloqueoEncargosPiladas vBloqueoEncargosPiladas = new VBloqueoEncargosPiladas();
			vBloqueoEncargosPiladas.setCodCentro(user.getCentro().getCodCentro());
			vBloqueoEncargosPiladas.setCodArticulo(nuevoPedidoReferencia.getCodArt());
			vBloqueoEncargosPiladas.setFecIniDDMMYYYY((nuevoPedidoReferencia.getFechaIni()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIni()):""));
			vBloqueoEncargosPiladas.setFecha2DDMMYYYY((nuevoPedidoReferencia.getFecha2()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha2()):""));
			vBloqueoEncargosPiladas.setFecha3DDMMYYYY((nuevoPedidoReferencia.getFecha3()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha3()):""));
			vBloqueoEncargosPiladas.setFecha4DDMMYYYY((nuevoPedidoReferencia.getFecha4()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha4()):""));
			vBloqueoEncargosPiladas.setFecha5DDMMYYYY((nuevoPedidoReferencia.getFecha5()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha5()):""));
			vBloqueoEncargosPiladas.setFechaInPilDDMMYYYY((nuevoPedidoReferencia.getFechaPilada()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaPilada()):""));
			vBloqueoEncargosPiladas.setFecFinDDMMYYYY((nuevoPedidoReferencia.getFechaFin()!=null?Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFin()):""));

			VBloqueoEncargosPiladas vBloqueoEncargosPiladasLeyenda = new VBloqueoEncargosPiladas();
			vBloqueoEncargosPiladasLeyenda.setCodCentro(user.getCentro().getCodCentro());
			vBloqueoEncargosPiladasLeyenda.setCodArticulo(nuevoPedidoReferencia.getCodArt());

			if (Constantes.CLASE_PEDIDO_ENCARGO.equals(nuevoPedidoReferencia.getTipoPedido().toString()) && null == nuevoPedidoReferencia.getErrorWS()){
				vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
				numeroRegistrosBloqueo = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
				if (numeroRegistrosBloqueo > 0){
					nuevoPedidoReferencia.setFechaBloqueoEncargo("S");
				}else{
					nuevoPedidoReferencia.setFechaBloqueoEncargo("N");
				}
				nuevoPedidoReferencia.setFechaBloqueoEncargoPilada("N");

				//Para el control de mostrar las leyendas de bloqueo (Solo se mostraran las leyendas cuando un articulo este o ha estado blqueado, independientemente de las fechas)
				vBloqueoEncargosPiladasLeyenda.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
				numeroRegistrosBloqueoLeyenda = this.vBloqueoEncargosPiladasService.registrosRefBloqueadaCont(vBloqueoEncargosPiladasLeyenda).intValue();

				if (numeroRegistrosBloqueoLeyenda > 0){
					nuevoPedidoReferencia.setMostrarLeyendaBloqueo("S");
				}else{
					nuevoPedidoReferencia.setMostrarLeyendaBloqueo("N");
				}
			}
		}
	}

	/**
	 * Realiza la carga de las fechas DATE en las propiedades de tipo String.
	 * 
	 * @param nuevoPedidoReferencia
	 * @throws Exception
	 */
	private void volcadoFechas(PedidoAdicionalCompleto nuevoPedidoReferencia) throws Exception{
		nuevoPedidoReferencia.setStrFechaIni(null);
		nuevoPedidoReferencia.setStrFechaFin(null);
		nuevoPedidoReferencia.setStrFecha2(null);
		nuevoPedidoReferencia.setStrFecha3(null);
		nuevoPedidoReferencia.setStrFechaPilada(null);
		nuevoPedidoReferencia.setStrFechaIniWS(null);
		nuevoPedidoReferencia.setStrMinFechaIni(null);
		nuevoPedidoReferencia.setStrFechaIniOferta(null);
		nuevoPedidoReferencia.setStrFechaFinOferta(null);

		if (nuevoPedidoReferencia.getFechaIni() != null){
			nuevoPedidoReferencia.setStrFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIni()));
		}
		if (nuevoPedidoReferencia.getFechaFin() != null){
			nuevoPedidoReferencia.setStrFechaFin(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFin()));
		}
		if (nuevoPedidoReferencia.getFecha2() != null){
			nuevoPedidoReferencia.setStrFecha2(Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha2()));
		}
		if (nuevoPedidoReferencia.getFecha3() != null){
			nuevoPedidoReferencia.setStrFecha3(Utilidades.formatearFecha(nuevoPedidoReferencia.getFecha3()));
		}
		if (nuevoPedidoReferencia.getFechaPilada() != null){
			nuevoPedidoReferencia.setStrFechaPilada(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaPilada()));
		}
		if (nuevoPedidoReferencia.getFechaIniWS() != null){
			nuevoPedidoReferencia.setStrFechaIniWS(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniWS()));
		}
		if (nuevoPedidoReferencia.getMinFechaIni() != null){
			nuevoPedidoReferencia.setStrMinFechaIni(Utilidades.formatearFecha(nuevoPedidoReferencia.getMinFechaIni()));
		}
		if (nuevoPedidoReferencia.getFechaIniOferta() != null){
			nuevoPedidoReferencia.setStrFechaIniOferta(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaIniOferta()));
		}
		if (nuevoPedidoReferencia.getFechaFinOferta() != null){
			nuevoPedidoReferencia.setStrFechaFinOferta(Utilidades.formatearFecha(nuevoPedidoReferencia.getFechaFinOferta()));
		}
	}
	
}