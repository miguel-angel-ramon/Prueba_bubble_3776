package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.UserDao;
import es.eroski.misumi.dao.iface.VAgruComerRefDao;
import es.eroski.misumi.dao.iface.VPlanPedidoAdicionalDao;
import es.eroski.misumi.model.CalendarioAvisos;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionAvisos;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionEstado;
import es.eroski.misumi.model.ECAreaFacingCero;
import es.eroski.misumi.model.EntradaAvisos;
import es.eroski.misumi.model.EstructuraArtMostrador;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.ParamCentrosVp;
import es.eroski.misumi.model.PedidoAdicionalVC;
import es.eroski.misumi.model.PedidoHTNoPbl;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VPlanPedidoAdicional;
import es.eroski.misumi.service.iface.AlarmasPLUService;
import es.eroski.misumi.service.iface.CalendarioService;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.service.iface.EntradasDescentralizadasService;
import es.eroski.misumi.service.iface.InformeListadoService;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.ParamCentrosVpService;
import es.eroski.misumi.service.iface.PedidoHTNoPblService;
import es.eroski.misumi.service.iface.SmfCapacidadService;
import es.eroski.misumi.service.iface.UserService;
import es.eroski.misumi.service.iface.VCentrosPlataformasService;
import es.eroski.misumi.service.iface.VCentrosUsuariosService;
import es.eroski.misumi.service.iface.VConfirmacionPedidoService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {

	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private VCentrosPlataformasService vCentrosPlataformasService;

	@Autowired
	private VCentrosUsuariosService vCentrosUsuariosService;

	@Autowired
	private ParamCentrosVpService paramCentrosVpService;

	@Autowired
	private SmfCapacidadService smfCapacidadService;

	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;

	@Autowired
	private PedidoHTNoPblService pedidoHTNoPblService;

	@Autowired
	private VPlanPedidoAdicionalDao vPlanPedidoAdicionalDao;

	@Autowired
	private InformeListadoService informeListadoService;

	@Autowired
	private DevolucionService devolucionesService;

	@Autowired
	private CalendarioService calendarioService;

	@Autowired
	private EntradasDescentralizadasService entradasDescentralizadasService;

	@Autowired
	private AlarmasPLUService alarmasPLUService;

	@Autowired
	private VAgruComerRefDao agruComerRefDao;

	@Autowired
	private VConfirmacionPedidoService vConfirmacionPedidoService;

	@Resource
	private MessageSource messageSource;

	@Value("${PARAMETRIZAR_OPCIONES}")
	private String parametrizarOpciones;

	@Value("${tecnicRole}")
	private String tecnicRole;
	@Value("${centerRole}")
	private String centerRole;
	@Value("${consultaRole}")
	private String consultaRole;
	@Value("${adminRole}")
	private String adminRole;

	@Override
	public User find(User user) throws Exception {
		return find(user, true);
	}

	@Override
	public User findPda(User user) throws Exception {
		return find(user, false);
	}

	private User find(User user, boolean obtenerAvisos) throws Exception {

		User userAux = this.userDao.find(user);
		if (userAux.getCentro() != null && userAux.getCentro().getCodCentro() != 0) {

			Centro centro = this.vCentrosUsuariosService.findAll(userAux.getCentro(), userAux.getCode()).get(0);

			ParamCentrosVp paramCentrosVp = new ParamCentrosVp();
			paramCentrosVp.setCodLoc(userAux.getCentro().getCodCentro());
			if (this.paramCentrosVpService.esAutoservicio(paramCentrosVp)) {
				centro.setFlgCapacidad(Constantes.FLG_CAPACIDAD_AUTOSERVICIO);
			} else {
				centro.setFlgCapacidad(Constantes.FLG_CAPACIDAD_NO_AUTOSERVICIO);
			}

			// Control del facing del centro
			if (this.paramCentrosVpService.esFacingCentro(paramCentrosVp)) {
				centro.setFlgFacing(Constantes.FLG_SI_FACING_CENTRO);
			} else {
				centro.setFlgFacing(Constantes.FLG_NO_FACING_CENTRO);
			}

			String descripBotonSfmCapacidad = this.smfCapacidadService.getMetodosBoton(centro.getCodCentro());
			centro.setDescripBotonSfmCapacidad(descripBotonSfmCapacidad);

			// Control de opciones de aplicación
			if (parametrizarOpciones != null && "S".equals(parametrizarOpciones.trim())) {
				centro.setControlOpciones(parametrizarOpciones);
				ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
				paramCentrosOpc.setCodLoc(userAux.getCentro().getCodCentro());
				ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
				if (paramCentroOpciones != null) { // Existen opciones habilitadas para el centro
					centro.setOpcHabil(paramCentroOpciones.getOpcHabil());
				} else {
					// Si no existe entrada en la tabla para el centro se considera que no tiene
					// acceso
					centro.setControlOpciones("S");
					centro.setOpcHabil("");
				}
			} else {
				centro.setControlOpciones("N");
				centro.setOpcHabil("");
			}

			// Control lotesNavidad
			if (centro.getOpcHabil().toUpperCase().indexOf(Constantes.CESTAS_12) != -1 && obtenerAvisos) {
				centro.setLotesNavidadActivos(
						this.vCentrosPlataformasService.isLotesCentroActivo(centro.getCodCentro()));
			} else {
				centro.setLotesNavidadActivos(false);
			}

			// Control informeListado
			if (centro.getOpcHabil().toUpperCase().indexOf(Constantes.PESCA_13) != -1 && obtenerAvisos) {
				centro.setInformeListado(this.informeListadoService.obtenerInformeListado(centro.getCodCentro()));
			}
			// Obtener avisos del centro
			/*
			 * if (obtenerAvisos) {
			 * centro.setListaAvisosCentro(obtenerAvisosCentro(userAux.getCentro().
			 * getCodCentro()));
			 * centro.setCuentaAvisosCentro(centro.getListaAvisosCentro().size()); } else {
			 * centro.setListaAvisosCentro(new ArrayList<String>());
			 * centro.setCuentaAvisosCentro(centro.getListaAvisosCentro().size()); }
			 */
			userAux.setCentro(centro);

			// Obtenemos la dirección del centro.
			// MISUMI-655
			// String direccionGoogle =
			// this.centroLocalizacionService.obtenerDireccionGoogle(userAux.getCentro().getCodCentro());
			// if(direccionGoogle != null){
			// DireccionCentro dirCentro = new DireccionCentro();
			// dirCentro.setDireccion(direccionGoogle);
			// userAux.getCentro().setDireccionCentro(dirCentro);
			// }else{
			// //Obtenemos la latitud y longitud del centro.
			// userAux.getCentro().setDireccionCentro(centroLocalizacionService.obtenerCentroLocalizacion(userAux));
			// }
		} else {
			userAux.setCentro(null);
		}

		return userAux;
	}

	@Override
	public List<String> obtenerAvisosCentro(Long codCentro, HttpSession session) throws Exception {
		// En este método se tratan todos los avisos que aparecerán en el tablón.
		// Se guardan en una lista todos los códigos de los mensajes para mostrarlos.

		ArrayList<String> listaAvisos = new ArrayList<String>();
		List<String> listaAvisosPedidosAdicionales = new ArrayList<String>();

		// Control de aviso para validar cantidades extra
		if (mostrarAvisoValidarCantidadesExtra(codCentro, session)) {
			listaAvisos.add(Constantes.TABLON_ANUNCIOS_VALIDAR_CANTIDADES_EXTRA + "**");
		}

		listaAvisosPedidosAdicionales = mostrarAvisoPedidoAdicional(codCentro);
		if (listaAvisosPedidosAdicionales.size() > 0) {

			for (String aviso : listaAvisosPedidosAdicionales) {

				// Añadir codigo 2 + descripcion
				listaAvisos.add(Constantes.TABLON_ANUNCIOS_PEDIDO_ADICIONAL + "**" + aviso);
			}
		}

		User user = (User) session.getAttribute("user");
		logger.info("OPCIONES DEL USUARIO: " + user.getCentro().getOpcHabil().toUpperCase());
		if (user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_AVISOS_PLU) != -1) {
			String avisoPLU = mostrarAvisoAlarmasPLU(codCentro);
			if (!avisoPLU.isEmpty()) {
				listaAvisos.add(Constantes.TABLON_ANUNCIOS_AVISO_PLU + "**" + avisoPLU);
			}
		}

		// Mostrar avisos de devoluciones
		if (user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.DEVOLUCIONES_15) != -1) {
			DevolucionAvisos devAvisos = devolucionesService.cargarAvisosDevoluciones(codCentro);
			if (devAvisos != null
					&& devAvisos.getpCodError().longValue() == Constantes.DEVOLUCIONES_AVISOS_COD_ERROR_OK) {
				if (Constantes.DEVOLUCIONES_AVISOS_FLG_SI.equals(devAvisos.getFlgDevoluciones())) {
					boolean isFrescos = Constantes.DEVOLUCIONES_AVISOS_FLG_FRESCOS_SI.equals(devAvisos.getFlgFrescos());
					boolean isAlimentacion = Constantes.DEVOLUCIONES_AVISOS_FLG_ALI_SI.equals(devAvisos.getFlgAli());
					boolean isNoAli = Constantes.DEVOLUCIONES_AVISOS_FLG_NO_ALI_SI.equals(devAvisos.getFlgNoAli());
					StringBuilder sbAviso = new StringBuilder();
					Locale locale = LocaleContextHolder.getLocale();
					if (isFrescos) {
						sbAviso.append(this.messageSource.getMessage(
								"p11_welcome.mensajeDevolucionesDisponiblesDeEstadoUno.FRESCOS", null, locale));
					}
					if (isAlimentacion) {
						if (sbAviso.length() > 0) {
							if (isNoAli) {
								sbAviso.append(", ");
							} else {
								sbAviso.append(" y ");
							}
						}
						sbAviso.append(this.messageSource.getMessage(
								"p11_welcome.mensajeDevolucionesDisponiblesDeEstadoUno.ALIMENTACION", null, locale));
					}
					if (isNoAli) {
						if (sbAviso.length() > 0)
							sbAviso.append(" y ");
						sbAviso.append(this.messageSource.getMessage(
								"p11_welcome.mensajeDevolucionesDisponiblesDeEstadoUno.NO_ALI", null, locale));
					}
					if (sbAviso.length() > 0) {
						listaAvisos.add(Constantes.TABLON_ANUNCIOS_DEVOLUCIONES + "**" + sbAviso.toString());
					}

					boolean isUrgenteFrescos = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_FRESCOS_SI
							.equals(devAvisos.getFlgUrgenteFrescos());
					boolean isUrgenteAlimentacion = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_ALI_SI
							.equals(devAvisos.getFlgUrgenteAli());
					boolean isUrgenteNoAli = Constantes.DEVOLUCIONES_AVISOS_FLG_URGENTE_NO_ALI_SI
							.equals(devAvisos.getFlgUrgenteNoAli());

					StringBuilder sbAvisoUrgente = new StringBuilder();
					if (isUrgenteFrescos) {
						sbAvisoUrgente.append(this.messageSource.getMessage(
								"p11_welcome.mensajeDevolucionesDisponiblesDeEstadoUno.FRESCOS", null, locale));
					}
					if (isUrgenteAlimentacion) {
						if (sbAvisoUrgente.length() > 0) {
							if (isUrgenteNoAli) {
								sbAvisoUrgente.append(", ");
							} else {
								sbAvisoUrgente.append(" y ");
							}
						}
						sbAvisoUrgente.append(this.messageSource.getMessage(
								"p11_welcome.mensajeDevolucionesDisponiblesDeEstadoUno.ALIMENTACION", null, locale));
					}
					if (isUrgenteNoAli) {
						if (sbAvisoUrgente.length() > 0)
							sbAvisoUrgente.append(" y ");
						sbAvisoUrgente.append(this.messageSource.getMessage(
								"p11_welcome.mensajeDevolucionesDisponiblesDeEstadoUno.NO_ALI", null, locale));
					}
					if (sbAvisoUrgente.length() > 0) {
						listaAvisos.add(
								Constantes.TABLON_ANUNCIOS_DEVOLUCIONES_URGENTE + "**" + sbAvisoUrgente.toString());
					}
				}
			}
		}

		if (!(user.getPerfil().toString().equals(this.consultaRole))
				&& user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_CALENDARIO) != -1) { // si el
																												// usuario
																												// NO es
																												// de
																												// SOLO
																												// CONSULTA
																												// Y
																												// EXISTE
																												// EL
																												// PERMISO
																												// DE
																												// CALENDARIO,
																												// se
																												// obtienen
																												// los
																												// avisos
																												// del
																												// calendario
			// Obtener Avisos del Calendario
			CalendarioAvisos calendarioAvisos = calendarioService.cargarAvisosCalendario(codCentro);

			if (calendarioAvisos != null
					&& calendarioAvisos.getpCodError().longValue() == Constantes.CALENDARIO_AVISOS_COD_ERROR_OK
					&& ("S").equals(calendarioAvisos.getFlgPendValidarEjer())
					&& (user.getPerfil().toString().equals(this.tecnicRole)
							|| user.getPerfil().toString().equals(this.adminRole)
							|| (("S").equals(calendarioAvisos.getFlgModifCalendarioCentro())
									&& user.getPerfil().toString().equals(this.centerRole)))) {

				String flgPendienteValidarEjercicio = (String) calendarioAvisos.getFlgPendValidarEjer();
				String flgModificarCalendarioCentro = (String) calendarioAvisos.getFlgModifCalendarioCentro();
				Long anoEjercicio = (Long) calendarioAvisos.getAnoEjerPendValid();
				Date fechaLimiteVallidar = (Date) calendarioAvisos.getFechaLimiteValid();

				if ((user.getPerfil().toString().equals(this.tecnicRole)
						|| user.getPerfil().toString().equals(this.adminRole))) { // Si el usuaiorio es un TECNICO

					if ((Constantes.CALENDARIO_AVISOS_FLG_SI.equals(flgPendienteValidarEjercicio))
							&& (anoEjercicio != null)) {
						// Añadir codigo 4 + fecha limite
						listaAvisos.add(Constantes.TABLON_ANUNCIOS_CALENDARIO + "**" + fechaLimiteVallidar.toString());
					}

				} else {
					if ((user.getPerfil().toString().equals(this.centerRole))) { // Si el usuaiorio es un CENTRO

						if ((Constantes.CALENDARIO_AVISOS_FLG_SI.equals(flgPendienteValidarEjercicio))
								&& (Constantes.CALENDARIO_AVISOS_FLG_SI.equals(flgModificarCalendarioCentro))
								&& (anoEjercicio != null)) {
							// Añadir codigo 4 + fecha limite
							listaAvisos
									.add(Constantes.TABLON_ANUNCIOS_CALENDARIO + "**" + fechaLimiteVallidar.toString());
						}

					}
				}
			}

			if (calendarioAvisos.getAvisoKoPlataforma() != null) {
				// Añadir codigo 4 + fecha limite
				listaAvisos.add(Constantes.TABLON_ANUNCIOS_CALENDARIO_AVISO_KO + "**"
						+ calendarioAvisos.getAvisoKoPlataforma());
			}
		}

		// Mostrar avisos de ENTRADAS
		if (user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_ENTRADAS) != -1) {
			EntradaAvisos entradaAvisos = entradasDescentralizadasService.cargarAvisosEntradas(codCentro);

			if (entradaAvisos != null
					&& entradaAvisos.getCodError().longValue() == Constantes.ENTRADAS_AVISOS_COD_ERROR_OK) {
				if (Constantes.ENTRADAS_AVISOS_FLG_SI.equals(entradaAvisos.getFlgPendienteEntrada())) {

					boolean isFrescos = Constantes.ENTRADAS_AVISOS_FLG_FRESCOS_SI.equals(entradaAvisos.getFlgFrescos());
					boolean isAlimentacion = Constantes.ENTRADAS_AVISOS_FLG_ALI_SI
							.equals(entradaAvisos.getFlgAlimentacion());
					boolean isNoAli = Constantes.ENTRADAS_AVISOS_FLG_NO_ALI_SI
							.equals(entradaAvisos.getFlgNoAlimentacion());
					StringBuilder sbAviso = new StringBuilder();
					Locale locale = LocaleContextHolder.getLocale();
					if (isFrescos) {
						sbAviso.append(
								this.messageSource.getMessage("p11_welcome.mensajeEntrada.FRESCOS", null, locale));
					}
					if (isAlimentacion) {
						if (sbAviso.length() > 0) {
							if (isNoAli) {
								sbAviso.append(", ");
							} else {
								sbAviso.append(" y ");
							}
						}
						sbAviso.append(
								this.messageSource.getMessage("p11_welcome.mensajeEntrada.ALIMENTACION", null, locale));
					}
					if (isNoAli) {
						if (sbAviso.length() > 0)
							sbAviso.append(" y ");
						sbAviso.append(
								this.messageSource.getMessage("p11_welcome.mensajeEntrada.NO_ALI", null, locale));
					}
					if (sbAviso.length() > 0) {
						listaAvisos.add(Constantes.TABLON_ANUNCIOS_ENTRADAS + "**" + sbAviso.toString());
					}
				}
			}
		}

		// Avisos de mostrador pesca
		if (null != user.getCentro().getEstadoEstructurasMostrador()) {
			String secciones = "";
			for (EstructuraArtMostrador estado : user.getCentro().getEstadoEstructurasMostrador()
					.getLstEstadoEstructurasMostrador()) {
				if (4 == estado.getEstado()) {
					secciones += agruComerRefDao.getDescripcionSeccion(estado.getNivel1(), estado.getNivel2()) + ", ";
				}
			}

			if (StringUtils.isNotBlank(secciones)) {
				listaAvisos.add(
						Constantes.TABLON_ANUNCIOS_AVISO_MOSTRADOR + "**" + StringUtils.removeEnd(secciones, ", "));
			}

		}

		// Avisos de Areas de Facing Cero.
		if (null != user.getCentro().getAreaFacingCero()
				&& new Long(0).equals(user.getCentro().getAreaFacingCero().getCodError())) {
			String areas = "";
			for (ECAreaFacingCero area : user.getCentro().getAreaFacingCero().getLstAreaReferenciasFacingCero()) {
				areas += agruComerRefDao.getDescripcionArea(area.getNivel1()) + ", ";
			}

			if (StringUtils.isNotBlank(areas)) {
				listaAvisos
						.add(Constantes.TABLON_ANUNCIOS_AVISO_FACING_CERO + "**" + StringUtils.removeEnd(areas, ", "));
			}

		}

		
		/* MISUMI 674 
		 * Avisos Ajuste de Corte de pedidos
		 */
		if (user.getCentro().getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_31_CORTE_PEDIDO) != -1
				&& vConfirmacionPedidoService.hasAjustePedido(codCentro)) {
			listaAvisos.add(Constantes.TABLON_AJUSTE_PEDIDOS + "**");
		}

		return listaAvisos;
	}

	@Override
	public boolean mostrarAvisoValidarCantidadesExtra(Long codCentro, HttpSession session) throws Exception {
		// En este método se tratan todos los avisos que aparecerán en el tablón.
		// Se guardan en una lista todos los códigos de los mensajes para mostrarlos.

		int counListaValidar = 0;
		boolean mostrarAviso = false;

		try {
			// Clase pedido 4
			PedidoAdicionalVC pedidoAdicionalVC = new PedidoAdicionalVC();
			pedidoAdicionalVC.setClasePedido(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4));
			pedidoAdicionalVC.setCodCentro(codCentro);

			// Clase pedido 5
			pedidoAdicionalVC.setClasePedido(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_5));

			// Pedidos no PBL
			PedidoHTNoPbl pedidoHTNoPblVC = new PedidoHTNoPbl();
			pedidoHTNoPblVC.setCodCentro(pedidoAdicionalVC.getCodCentro());
			pedidoHTNoPblVC.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_NO);
			int countListNoPbl = this.pedidoHTNoPblService.countPedidosHTNoPbl(pedidoHTNoPblVC);

			counListaValidar = countListNoPbl;

			if (counListaValidar > 0) {
				mostrarAviso = true;
			}
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			e.printStackTrace();
		}
		return mostrarAviso;
	}

	@Override
	public List<String> mostrarAvisoPedidoAdicional(Long codCentro) throws Exception {

		// En este método se tratan todos los avisos que aparecerán en el tablón.
		// Se guardan en una lista todos los códigos de los mensajes para mostrarlos.

		// Control de aviso para mostrar avisos de pedido adicional
		List<String> listAvisos = new ArrayList<String>();

		VPlanPedidoAdicional vPlanPedidoAdicional = new VPlanPedidoAdicional();
		vPlanPedidoAdicional.setCodCentro(codCentro);

		// Obtenemos el número de pedidos de clase pedido 4 del WS
		listAvisos = this.vPlanPedidoAdicionalDao.findUltimosPedidos(vPlanPedidoAdicional);

		return listAvisos;

	}

	@Override
	public boolean mostrarAvisoDevoluciones(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		// Añadir avisos de Devoluciones en caso de que el estado 1 de las devoluciones
		// tenga devoluciones
		Devolucion devolucion = new Devolucion();
		devolucion.setCentro(codCentro);
		devolucion.setEstadoCab(Constantes.DEVOLUCIONES_PDA_DEVOLUCIONES_PENDIENTES_ESTADO);
		devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);

		boolean mostrarDevoluciones = false;
		DevolucionCatalogoEstado devolucionCatalogoEstado = devolucionesService.cargarCabeceraDevoluciones(devolucion);

		if (devolucionCatalogoEstado.getListDevolucionEstado() != null
				&& devolucionCatalogoEstado.getListDevolucionEstado().size() > 0) {
			DevolucionEstado devolucionEstado = devolucionCatalogoEstado.getListDevolucionEstado().get(0);
			if (devolucionEstado.getNumeroRegistros() > 0) {
				mostrarDevoluciones = true;
			}
		}
		return mostrarDevoluciones;
	}

	@Override
	public DevolucionAvisos mostrarAvisoDevolucionesUrgente(Long codCentro) throws Exception {
		return devolucionesService.cargarAvisosDevoluciones(codCentro);
	}

	@Override
	public String mostrarAvisoAlarmasPLU(Long codCentro) throws Exception {
		String output = "";

		final List<String> list = alarmasPLUService.hayAvisosPLU(codCentro);
		if (list.size() > 0) {
			if (list.size() == 1) {
				output = list.get(0);
			} else {
				for (int i = 0; i < list.size(); i++) {
					if (i > 0 && i < (list.size() - 1)) {
						output += ", ";
					} else if (i == list.size() - 1) {
						output += " y ";
					}
					output += list.get(i);
				}
			}
		}
		return output;
	}

}