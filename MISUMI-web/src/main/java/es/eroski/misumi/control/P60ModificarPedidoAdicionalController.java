package es.eroski.misumi.control;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.PedidoAdicionalNuevo;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.model.VReferenciasNuevasVegalsa;
import es.eroski.misumi.service.iface.EncargosReservasService;
import es.eroski.misumi.service.iface.PedidoAdicionalNuevoService;
import es.eroski.misumi.service.iface.PedidoAdicionalService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VBloqueoEncargosPiladasService;
import es.eroski.misumi.service.iface.VFestivoCentroService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Controller
@RequestMapping("/modificarPedidoAdicional")
public class P60ModificarPedidoAdicionalController {

	private static Logger logger = Logger.getLogger(P60ModificarPedidoAdicionalController.class);

	@Resource
	private MessageSource messageSource;

	@Autowired
	private PedidoAdicionalService pedidoAdicionalService;

	@Autowired
	private VFestivoCentroService vFestivoCentroService;

	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

	@Autowired
	private VBloqueoEncargosPiladasService vBloqueoEncargosPiladasService;

	@Autowired
	private EncargosReservasService encargosReservasService;

	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;
	
	@RequestMapping(value = "/loadDates", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalCompleto loadDates(@RequestBody PedidoAdicionalCompleto pedidoAdicionalModificar,
			HttpSession session, HttpServletResponse response) throws Exception {

		try {

			if (null!= pedidoAdicionalModificar.getReferenciaNueva() && pedidoAdicionalModificar.getReferenciaNueva()){
				Integer numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA;
				if (pedidoAdicionalModificar.getBloqueoPilada() != null && pedidoAdicionalModificar.getBloqueoPilada()){
					numDias = Constantes.NUM_DIAS_REFERENCIA_NUEVA_BLOQUEO_PILADA;
				}
				VFestivoCentro vFestivoCentro = new VFestivoCentro();
				vFestivoCentro.setCodCentro(pedidoAdicionalModificar.getCodCentro());
				vFestivoCentro.setFechaInicio(pedidoAdicionalModificar.getFechaIni());
				pedidoAdicionalModificar.setFechaFin(Utilidades.convertirStringAFecha(this.vFestivoCentroService.getFechaFinNuevaReferencia(vFestivoCentro, numDias)));
				pedidoAdicionalModificar.setStrFechaFin(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaFin()));
			}
			if (pedidoAdicionalModificar.getFrescoPuro()){
				if (null != pedidoAdicionalModificar.getCalcularFechaIniFin() && pedidoAdicionalModificar.getCalcularFechaIniFin().equals("F")){
					//Recalcular la fecha fin
					Integer numDias = Constantes.NUM_DIAS_INTERVALO_FRESCOS_MODIFICACION;
					VFestivoCentro vFestivoCentro = new VFestivoCentro();
					vFestivoCentro.setCodCentro(pedidoAdicionalModificar.getCodCentro());
					vFestivoCentro.setFechaInicio(pedidoAdicionalModificar.getFechaIni());
					Date fechaFinMin = Utilidades.convertirStringAFecha(this.vFestivoCentroService.getFechaFinNuevaReferencia(vFestivoCentro, numDias));
					DateTime dtFechaFinMin = new DateTime(fechaFinMin).withMillisOfDay(0);
					pedidoAdicionalModificar.setStrFechaFinMin(Utilidades.formatearFecha(fechaFinMin));
					if ((dtFechaFinMin.isAfter(pedidoAdicionalModificar.getFechaFin().getTime()))){
						pedidoAdicionalModificar.setFechaFin(fechaFinMin);
						pedidoAdicionalModificar.setStrFechaFin(Utilidades.formatearFecha(fechaFinMin));
					}
				}
			}			
			
			this.volcadoFechas(pedidoAdicionalModificar);
			if (pedidoAdicionalModificar.getFrescoPuro()){
				List<Map<String,Object>> listaFechasSiguientes = null;
				VFestivoCentro vFestivoCentro = new VFestivoCentro();
				vFestivoCentro.setCodCentro(pedidoAdicionalModificar.getCodCentro());
				vFestivoCentro.setFechaInicio(pedidoAdicionalModificar.getFechaIni());
				vFestivoCentro.setFechaFin(pedidoAdicionalModificar.getFechaFin());

				listaFechasSiguientes = this.vFestivoCentroService.getNextDays(vFestivoCentro, Constantes.NUM_DIAS_INTERVALO_FRESCOS_MODIFICACION);

				if ( null != pedidoAdicionalModificar.getFechaFin()){
					if (listaFechasSiguientes != null && listaFechasSiguientes.size() > 0) {//Fecha2
						Map<String,Object> m = listaFechasSiguientes.get(0);
						String strFecha2 =  (String) m.get("SIGUIENTE_DIA");
						Date fecha2 = Utilidades.convertirStringAFecha(strFecha2);
						pedidoAdicionalModificar.setFecha2(fecha2);
						pedidoAdicionalModificar.setStrFecha2(strFecha2);
						if (listaFechasSiguientes.size() > 1) {//Fecha3
							m = listaFechasSiguientes.get(1);
							String strFecha3 =  (String) m.get("SIGUIENTE_DIA");
							Date fecha3 = Utilidades.convertirStringAFecha(strFecha3);
							pedidoAdicionalModificar.setFecha3(fecha3);
							pedidoAdicionalModificar.setStrFecha3(strFecha3);
							if (listaFechasSiguientes.size() > 2) {//Fecha Pilada
								m = listaFechasSiguientes.get(2);
								String strFechaPilada =  (String) m.get("SIGUIENTE_DIA");
								Date fechaPilada = Utilidades.convertirStringAFecha(strFechaPilada);
								pedidoAdicionalModificar.setFechaPilada(fechaPilada);
								pedidoAdicionalModificar.setStrFechaPilada(strFechaPilada);
							}else{
								pedidoAdicionalModificar.setFechaPilada(null);
								pedidoAdicionalModificar.setStrFechaPilada(null);
							}
						}else{
							pedidoAdicionalModificar.setFecha3(null);
							pedidoAdicionalModificar.setStrFecha3(null);
							pedidoAdicionalModificar.setFechaPilada(null);
							pedidoAdicionalModificar.setStrFechaPilada(null);

						}
					}else{
						pedidoAdicionalModificar.setFecha2(null);
						pedidoAdicionalModificar.setStrFecha2(null);
						pedidoAdicionalModificar.setFecha3(null);
						pedidoAdicionalModificar.setStrFecha3(null);
						pedidoAdicionalModificar.setFechaPilada(null);
						pedidoAdicionalModificar.setStrFechaPilada(null);
					}
				}else{
					pedidoAdicionalModificar.setFecha2(null);
					pedidoAdicionalModificar.setStrFecha2(null);
					pedidoAdicionalModificar.setFecha3(null);
					pedidoAdicionalModificar.setStrFecha3(null);
					pedidoAdicionalModificar.setFechaPilada(null);
					pedidoAdicionalModificar.setStrFechaPilada(null);
				}
			}/* else {
				this.setImplantacionInicial(pedidoAdicionalModificar);
			}*/

			//Comprobación de bloqueos

			if (!Constantes.CLASE_PEDIDO_ENCARGO.equals(pedidoAdicionalModificar.getTipoPedido())){
				this.comprobacionBloqueos(pedidoAdicionalModificar, session);
			}


		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return pedidoAdicionalModificar;
	}

	private void volcadoFechas(PedidoAdicionalCompleto pedidoAdicionalModificar) throws Exception{
		pedidoAdicionalModificar.setStrFechaIni(null);
		pedidoAdicionalModificar.setStrFechaFin(null);
		pedidoAdicionalModificar.setStrFecha2(null);
		pedidoAdicionalModificar.setStrFecha3(null);
		pedidoAdicionalModificar.setStrFechaPilada(null);
		pedidoAdicionalModificar.setStrFechaIniWS(null);
		pedidoAdicionalModificar.setStrMinFechaIni(null);
		pedidoAdicionalModificar.setStrFechaIniOferta(null);
		pedidoAdicionalModificar.setStrFechaFinOferta(null);

		if (pedidoAdicionalModificar.getFechaIni() != null)
		{
			pedidoAdicionalModificar.setStrFechaIni(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaIni()));
		}
		if (pedidoAdicionalModificar.getFechaFin() != null)
		{
			pedidoAdicionalModificar.setStrFechaFin(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaFin()));
		}
		if (pedidoAdicionalModificar.getFecha2() != null)
		{
			pedidoAdicionalModificar.setStrFecha2(Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha2()));
		}
		if (pedidoAdicionalModificar.getFecha3() != null)
		{
			pedidoAdicionalModificar.setStrFecha3(Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha3()));
		}
		if (pedidoAdicionalModificar.getFechaPilada() != null)
		{
			pedidoAdicionalModificar.setStrFechaPilada(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaPilada()));
		}
		if (pedidoAdicionalModificar.getFechaIniWS() != null)
		{
			pedidoAdicionalModificar.setStrFechaIniWS(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaIniWS()));
		}
		if (pedidoAdicionalModificar.getMinFechaIni() != null)
		{
			pedidoAdicionalModificar.setStrMinFechaIni(Utilidades.formatearFecha(pedidoAdicionalModificar.getMinFechaIni()));
		}
		if (pedidoAdicionalModificar.getFechaIniOferta() != null)
		{
			pedidoAdicionalModificar.setStrFechaIniOferta(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaIniOferta()));
		}
		if (pedidoAdicionalModificar.getFechaFinOferta() != null)
		{
			pedidoAdicionalModificar.setStrFechaFinOferta(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaFinOferta()));
		}
	}

	@RequestMapping(value = "/modifyPedido", method = RequestMethod.POST)
	public @ResponseBody String modifyPedido(@RequestBody PedidoAdicionalCompleto pedidoAdicionalModificar,
			HttpServletResponse response, HttpSession session) throws Exception {

		Locale locale = LocaleContextHolder.getLocale();
		Boolean valido = true;
		String errorMsg = null;

		User user = (User) session.getAttribute("user");
		
		// MISUMI-352.
		final Boolean tratamientoVegalsa = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), pedidoAdicionalModificar.getCodArt()); 
		if (tratamientoVegalsa){
			return modifyPedidoVegalsa(pedidoAdicionalModificar, session);
		}
		
		if (pedidoAdicionalModificar.getNoGestionaPbl() != null && "S".equals(pedidoAdicionalModificar.getNoGestionaPbl()) && pedidoAdicionalModificar.getIdentificadorSIA()==null){
			TPedidoAdicional registro = new TPedidoAdicional();

			registro.setIdSesion(session.getId());
			if(pedidoAdicionalModificar.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_MONTAJE))){
				registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)));
			}else{
				registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA)));
			}
			registro.setCodCentro(user.getCentro().getCodCentro());
			registro.setCodArticulo(pedidoAdicionalModificar.getCodArt());
			registro.setIdentificador(pedidoAdicionalModificar.getIdentificador());
			registro.setPantalla(Constantes.PANTALLA_LISTADOS);
			registro.setMAC("S");
			TPedidoAdicional pedidoAdicional =  this.tPedidoAdicionalService.findAll(registro).get(0);

			pedidoAdicional.setCantidad1(pedidoAdicionalModificar.getCantidad1());
			pedidoAdicional.setCantidad2(pedidoAdicionalModificar.getCantidad2());
			pedidoAdicional.setCantidad3(pedidoAdicionalModificar.getCantidad3());
			pedidoAdicional.setCantidad4(pedidoAdicionalModificar.getCantidad4());
			pedidoAdicional.setCantidad5(pedidoAdicionalModificar.getCantidad5());


			Long clasePedidoOriginal = pedidoAdicional.getClasePedido();
			if(pedidoAdicionalModificar.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_MONTAJE))){
				pedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL));
			}else{
				pedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA));
			}
			this.pedidoAdicionalService.modifyPedidoAdCentral(pedidoAdicional,session);

			//Restauramos la clase de pedido original
			pedidoAdicional.setClasePedido(clasePedidoOriginal);
			this.tPedidoAdicionalService.updatePedido(pedidoAdicional);

		}else{
			if (!pedidoAdicionalModificar.getTipoPedido().equals(new Integer(1)) && !pedidoAdicionalModificar.getFrescoPuro()) {


				if (pedidoAdicionalModificar.getCapacidadMaxima() > 9999) {
					//Incidencia 119 se muestra el mensaje pero se deja seguir
					valido = false;

					//errorMsg = messageSource.getMessage("p60_modificarPedidoUnico.implantacionInicialError",null, locale);
					errorMsg = "1** ";

				}
				if (valido) {
					DateTime fIni = new DateTime(pedidoAdicionalModificar.getFechaIni());
					DateTime fFin = new DateTime(pedidoAdicionalModificar.getFechaFin());
					Integer days = Days.daysBetween(fIni.toDateMidnight(),fFin.toDateMidnight()).getDays();
					if (days > 7) {
						if (pedidoAdicionalModificar.getImplantacionMinima() < (pedidoAdicionalModificar.getCapacidadMaxima() * 0.2) ||  pedidoAdicionalModificar.getImplantacionMinima() > 9999){
							//Incidencia 119 se muestra el mensaje pero se deja seguir
							valido = false;

							//errorMsg = messageSource.getMessage("p52_nuevoPedidoAdicionalREF.implantacionFinalError", null, locale);
							errorMsg = "2** ";
						}
					} else {
						pedidoAdicionalModificar.setImplantacionMinima(pedidoAdicionalModificar.getCapacidadMaxima().doubleValue());
					}

				}
			}
			if (valido){

				try {

					pedidoAdicionalModificar.setUser(user.getCode());
					pedidoAdicionalModificar.setPerfil(Constantes.PERFIL_CENTRO);
					pedidoAdicionalModificar.setSesion(session.getId());

					//boolean procesoGuardadoCorrecto = true;
					boolean procesoGuardadoCorrecto = false; //Pet. 55674
					EncargosReservasLista encargosReservasLista = null;

					//Encargos reservas NO-ALI
					encargosReservasLista = this.encargosReservasService.modificarPedido(pedidoAdicionalModificar);
					if (encargosReservasLista != null){
						procesoGuardadoCorrecto = (encargosReservasLista.getEstado()!=null &&  new Long(0).equals(encargosReservasLista.getEstado()));

						if (!procesoGuardadoCorrecto){
							errorMsg = encargosReservasLista.getEstado()+"**"+encargosReservasLista.getDescEstado();
						}else{
							if (encargosReservasLista.getDatos()!=null && encargosReservasLista.getDatos().size() >0 && encargosReservasLista.getDatos().get(0)!= null && Constantes.CODIGO_ERROR_BLOQUEO_PILADA_WS.equals(encargosReservasLista.getDatos().get(0).getCodError())){
								errorMsg = "L"+Constantes.CODIGO_ERROR_BLOQUEO_PILADA_WS+"**"+encargosReservasLista.getDatos().get(0).getDescError();
							}else{
								// MISUMI-318: CORRECTIVO PARA QUE SE REGISTREN TODOS LOS ERRORES Y QUE NO SE DIGA QUE SE HA GUARDADO CON EXITO CUANDO NO ES ASI
								final Long codError = encargosReservasLista.getDatos().get(0).getCodError();
								final String descError = encargosReservasLista.getDatos().get(0).getDescError();
								if (!codError.equals(0L)){
									errorMsg = "3**Error al modificar (COD_ERROR = "+codError+") - "+descError;
								}
							}
						}
					}

					if (procesoGuardadoCorrecto){
						TPedidoAdicional registro = new TPedidoAdicional();

						registro.setIdSesion(session.getId());
						//registro.setClasePedido(pedidoAdicionalModificar.getTipoPedido().longValue());
						registro.setListaFiltroClasePedido(Arrays.asList(pedidoAdicionalModificar.getTipoPedido().longValue()));
						registro.setCodCentro(user.getCentro().getCodCentro());
						registro.setCodArticulo(pedidoAdicionalModificar.getCodArt());
						registro.setIdentificador(pedidoAdicionalModificar.getIdentificador());
						registro.setIdentificadorSIA(pedidoAdicionalModificar.getIdentificadorSIA());
						registro.setPantalla(Constantes.PANTALLA_LISTADOS);
						if (pedidoAdicionalModificar.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)) ||
								pedidoAdicionalModificar.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA))	){
							registro.setMAC("S");
						}

						TPedidoAdicional pedidoAdicional =  this.tPedidoAdicionalService.findAll(registro).get(0);

						//Antes de empezar tenemos que limpiar los posibles guardados mostrados anteriormente.
						this.resetearGuardados(session.getId(), Arrays.asList(pedidoAdicional.getClasePedido()), pedidoAdicional.getCodCentro());

						switch (pedidoAdicionalModificar.getTipoPedido()){
						case 1: pedidoAdicional.setFecEntrega(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaIni()));
						pedidoAdicional.setCajasPedidas(pedidoAdicionalModificar.getCantidad1());
						pedidoAdicional.setCajas(pedidoAdicionalModificar.getCajas());
						pedidoAdicional.setExcluir(pedidoAdicionalModificar.getExcluir());
						break;
						case 2: 
						case 3:	
						case 7:
						case 8: pedidoAdicional.setFechaInicio(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaIni()));
						pedidoAdicional.setFechaFin(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaFin()));
						if (pedidoAdicionalModificar.getFrescoPuro()){
							pedidoAdicional.setCantidad1(pedidoAdicionalModificar.getCantidad1());
							pedidoAdicional.setCantidad2(pedidoAdicionalModificar.getCantidad2());
							pedidoAdicional.setCantidad3(pedidoAdicionalModificar.getCantidad3());
							pedidoAdicional.setCantidad4(pedidoAdicionalModificar.getCantidad4());
							pedidoAdicional.setCantidad5(pedidoAdicionalModificar.getCantidad5());
							if (pedidoAdicionalModificar.getFecha2() != null)
							{
								pedidoAdicional.setFecha2(Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha2()));
							}
							else
							{
								pedidoAdicional.setFecha2(null);
								pedidoAdicional.setCantidad2(new Double(0));
							}
							if (pedidoAdicionalModificar.getFecha3() != null)
							{
								pedidoAdicional.setFecha3(Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha3()));
							}
							else
							{
								pedidoAdicional.setFecha3(null);
								pedidoAdicional.setCantidad3(new Double(0));
							}
							if (pedidoAdicionalModificar.getFecha4() != null)
							{
								pedidoAdicional.setFecha4(Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha4()));
							}
							else
							{
								pedidoAdicional.setFecha4(null);
								pedidoAdicional.setCantidad4(new Double(0));
							}
							if (pedidoAdicionalModificar.getFecha5() != null)
							{
								pedidoAdicional.setFecha5(Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha5()));
							}
							else
							{
								pedidoAdicional.setFecha5(null);
								pedidoAdicional.setCantidad5(new Double(0));
							}
							if (pedidoAdicionalModificar.getFechaPilada() != null)
							{
								pedidoAdicional.setFechaPilada(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaPilada()));
							} else {
								pedidoAdicional.setFechaPilada(null);
							}
							pedidoAdicional.setExcluir(pedidoAdicionalModificar.getExcluir());

							//Modificar valores de pedidoAdCentral
							if(pedidoAdicionalModificar.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)) ||
									pedidoAdicionalModificar.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA))){
								this.pedidoAdicionalService.modifyPedidoAdCentral(pedidoAdicional,session);
							}

						} else {
							pedidoAdicional.setCapMax(pedidoAdicionalModificar.getCapacidadMaxima().doubleValue());
							pedidoAdicional.setCapMin(pedidoAdicionalModificar.getImplantacionMinima());
						}
						break;
						}

						if (pedidoAdicionalModificar.getIdentificadorSIA()!=null && 
								encargosReservasLista!=null &&
								encargosReservasLista.getDatos()!=null &&
								encargosReservasLista.getDatos().get(0)!=null){//Actualización de guardado de SIA. El procedimiento puede cambiar las fechas de más de 360 días

							EncargoReserva encargoReservaGuardado = encargosReservasLista.getDatos().get(0);

							pedidoAdicional.setFechaInicio(encargoReservaGuardado.getFechaInicio() != null?Utilidades.formatearFecha(encargoReservaGuardado.getFechaInicio()):null);
							pedidoAdicional.setFechaFin(encargoReservaGuardado.getFechaFin() != null?Utilidades.formatearFecha(encargoReservaGuardado.getFechaFin()):null);
						}

						pedidoAdicional.setCodError(Constantes.MODIFICADO_CORRECTO_PANTALLA);

						this.tPedidoAdicionalService.updatePedido(pedidoAdicional);
					} 

				} catch (Exception e) {
					e.printStackTrace();
					errorMsg = " **"+messageSource.getMessage("p60_modificarPedidoUnico.errorWSModificar",null, locale);
				}
			}
		}		
		return errorMsg;
	}

	/*private void setImplantacionInicial(PedidoAdicionalCompleto nuevoPedidoAdicional) throws Exception{
		DateTime dt = new DateTime(nuevoPedidoAdicional.getFechaFin()).withMillisOfDay(0);
		if (dt.isBefore(nuevoPedidoAdicional.getFechaIni().getTime())){
			nuevoPedidoAdicional.setFechaFin(nuevoPedidoAdicional.getFechaIni());
		}
		ImplantacionInicial implantacionInicial = new ImplantacionInicial();
		implantacionInicial.setCodArticulo(nuevoPedidoAdicional.getCodArt());
		implantacionInicial.setCodLoc(nuevoPedidoAdicional.getCodCentro());
		implantacionInicial.setFechaInicio(nuevoPedidoAdicional.getFechaIni());
		implantacionInicial.setFechaFin(nuevoPedidoAdicional.getFechaFin());
		ImplantacionInicial implantacionInicialAux = this.implantacionInicialService.getImplantacionInicial(implantacionInicial);
		nuevoPedidoAdicional.setCapacidadMaxima(implantacionInicialAux.getVentaMedia());
		DateTime fIni = new DateTime(nuevoPedidoAdicional.getFechaIni());
		DateTime fFin = new DateTime(nuevoPedidoAdicional.getFechaFin());
		Integer days = Days.daysBetween(fIni.toDateMidnight() , fFin.toDateMidnight() ).getDays(); 
		if (days > 7){
			BigDecimal capacidad = new BigDecimal(nuevoPedidoAdicional.getCapacidadMaxima());
			BigDecimal perctg = new BigDecimal(0.2);
			BigDecimal implantacionMinima = capacidad.multiply(perctg);
			BigDecimal im = implantacionMinima.setScale(0, RoundingMode.UP);
			nuevoPedidoAdicional.setImplantacionMinima(im.doubleValue());
		}
	}*/

	private String modifyPedidoVegalsa(PedidoAdicionalCompleto pedidoAdicionalModificar, HttpSession session) throws Exception {
		String output = null;
		try{
			// 1 - Guardar en la tabla T_PEDIDO_ADICIONAL con un 8 para que ponga un diskete
			User user = (User) session.getAttribute("user");
			
			TPedidoAdicional registro = new TPedidoAdicional();
	
			registro.setIdSesion(session.getId());
			//registro.setClasePedido(pedidoAdicionalModificar.getTipoPedido().longValue());
			registro.setListaFiltroClasePedido(Arrays.asList(pedidoAdicionalModificar.getTipoPedido().longValue()));
			registro.setCodCentro(user.getCentro().getCodCentro());
			registro.setCodArticulo(pedidoAdicionalModificar.getCodArt());
			registro.setIdentificador(pedidoAdicionalModificar.getIdentificador());
			registro.setIdentificadorSIA(pedidoAdicionalModificar.getIdentificadorSIA());
			registro.setPantalla(Constantes.PANTALLA_LISTADOS);
			if (pedidoAdicionalModificar.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)) ||
					pedidoAdicionalModificar.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA))	){
				registro.setMAC("S");
			}
			registro.setIdentificadorVegalsa(pedidoAdicionalModificar.getIdentificadorVegalsa());
			TPedidoAdicional pedidoAdicional =  this.tPedidoAdicionalService.findAll(registro).get(0);
	
			//Antes de empezar tenemos que limpiar los posibles guardados mostrados anteriormente.
			this.resetearGuardados(session.getId(), Arrays.asList(pedidoAdicional.getClasePedido()), pedidoAdicional.getCodCentro());
			
			pedidoAdicional.setFechaInicio(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaIni()));
			pedidoAdicional.setFechaFin(Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaFin()));
			pedidoAdicional.setCapMax(pedidoAdicionalModificar.getCapacidadMaxima().doubleValue());
			pedidoAdicional.setCapMin(pedidoAdicionalModificar.getImplantacionMinima());
			
			pedidoAdicional.setCodError(Constantes.MODIFICADO_CORRECTO_PANTALLA);
			this.tPedidoAdicionalService.updatePedido(pedidoAdicional);
			
			// 2 - Guardar en la tabla T_MIS_MONTAJES_VEGALSA
			this.tPedidoAdicionalService.updatePedidoVegalsa(pedidoAdicionalModificar.getIdentificadorVegalsa(), pedidoAdicionalModificar.getImplantacionMinima(), Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaIni()), Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaFin()), user.getCode());
			
		} catch (Exception e) {
			e.printStackTrace();
			output = "Error al modifiar el pedido Vegalsa: "+e.getMessage();
		}		
		
		return output;
	}

	private void resetearGuardados(String idSesion, List<Long> listaFiltroClasePedido, Long codCentro){

		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setListaFiltroClasePedido(listaFiltroClasePedido);
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);

		try {
			this.tPedidoAdicionalService.resetearGuardados(registro);
		} catch (Exception e) {
			logger.error("resetearGuardados="+e.toString());
			e.printStackTrace();
		}
	}

	private void comprobacionBloqueos(PedidoAdicionalCompleto pedidoAdicionalModificar, HttpSession session) throws Exception{
		int numeroRegistrosBloqueo = 0;
		if (pedidoAdicionalModificar.getTipoPedido() != null){
			User user = (User) session.getAttribute("user");
			VBloqueoEncargosPiladas vBloqueoEncargosPiladas = new VBloqueoEncargosPiladas();
			vBloqueoEncargosPiladas.setCodCentro(user.getCentro().getCodCentro());
			vBloqueoEncargosPiladas.setCodArticulo(pedidoAdicionalModificar.getCodArt());
			vBloqueoEncargosPiladas.setFecIniDDMMYYYY((pedidoAdicionalModificar.getFechaIni()!=null?Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaIni()):""));
			vBloqueoEncargosPiladas.setFecha2DDMMYYYY((pedidoAdicionalModificar.getFecha2()!=null?Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha2()):""));
			vBloqueoEncargosPiladas.setFecha3DDMMYYYY((pedidoAdicionalModificar.getFecha3()!=null?Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha3()):""));
			vBloqueoEncargosPiladas.setFecha4DDMMYYYY((pedidoAdicionalModificar.getFecha4()!=null?Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha4()):""));
			vBloqueoEncargosPiladas.setFecha5DDMMYYYY((pedidoAdicionalModificar.getFecha5()!=null?Utilidades.formatearFecha(pedidoAdicionalModificar.getFecha5()):""));
			vBloqueoEncargosPiladas.setFechaInPilDDMMYYYY((pedidoAdicionalModificar.getFechaPilada()!=null?Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaPilada()):""));
			vBloqueoEncargosPiladas.setFecFinDDMMYYYY((pedidoAdicionalModificar.getFechaFin()!=null?Utilidades.formatearFecha(pedidoAdicionalModificar.getFechaFin()):""));

			//Se guarda la fecha de fin para restaurarla tras la búsqueda
			String fechaFinMontaje = vBloqueoEncargosPiladas.getFecFinDDMMYYYY(); 
			//Obtener la última fecha de encargo
			String fechaFinEncargoDateDDMMYYYY = null;
			if(vBloqueoEncargosPiladas.getFecha5DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha5DDMMYYYY())){
				fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha5DDMMYYYY(); 
			}else if(vBloqueoEncargosPiladas.getFecha4DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha4DDMMYYYY())){
				fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha4DDMMYYYY();
			}else if(vBloqueoEncargosPiladas.getFecha3DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha3DDMMYYYY())){
				fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha3DDMMYYYY();
			}else if(vBloqueoEncargosPiladas.getFecha2DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha2DDMMYYYY())){
				fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha2DDMMYYYY();
			}
			//Bloqueos de encargo
			if (pedidoAdicionalModificar.getFrescoPuro()){//Sólo se comprueba bloqueo de encargo para frescos
				vBloqueoEncargosPiladas.setFecFinDDMMYYYY(fechaFinEncargoDateDDMMYYYY);
				vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
				numeroRegistrosBloqueo = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
				if (numeroRegistrosBloqueo > 0){
					pedidoAdicionalModificar.setFechaBloqueoEncargo("S");
				}else{
					pedidoAdicionalModificar.setFechaBloqueoEncargo("N");
				}
			}else{
				pedidoAdicionalModificar.setFechaBloqueoEncargo("N");
			}
			//Bloqueos de encargo y pilada
			vBloqueoEncargosPiladas.setFecFinDDMMYYYY(fechaFinMontaje);
			vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_MONTAJE);
			numeroRegistrosBloqueo = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
			if (numeroRegistrosBloqueo > 0){
				pedidoAdicionalModificar.setFechaBloqueoEncargoPilada("S");
			}else{
				pedidoAdicionalModificar.setFechaBloqueoEncargoPilada("N");
			}
		}
	}

}
