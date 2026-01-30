package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.CalendarioDaoSIA;
import es.eroski.misumi.model.CalendarioActualizado;
import es.eroski.misumi.model.CalendarioAvisos;
import es.eroski.misumi.model.CalendarioCambioEstacional;
import es.eroski.misumi.model.CalendarioDescripcionServicio;
import es.eroski.misumi.model.CalendarioDescripcionServicios;
import es.eroski.misumi.model.CalendarioDia;
import es.eroski.misumi.model.CalendarioDiaCambioServicio;
import es.eroski.misumi.model.CalendarioDiaWarning;
import es.eroski.misumi.model.CalendarioEjercicio;
import es.eroski.misumi.model.CalendarioEjercicios;
import es.eroski.misumi.model.CalendarioPendienteValidar;
import es.eroski.misumi.model.CalendarioProcesosDiarios;
import es.eroski.misumi.model.CalendarioServicio;
import es.eroski.misumi.model.CalendarioTotal;
import es.eroski.misumi.model.CalendarioValidacion;
import es.eroski.misumi.model.CalendarioValidado;
import es.eroski.misumi.model.CalendarioWarnings;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.TCalendarioDiaCambioServicio;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class CalendarioDaoSIAImpl implements CalendarioDaoSIA{

	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(CalendarioDaoSIAImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_DENOM_DEVOL
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_PENDIENTE_VALIDAR_EJERCICIO = 5;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_NUMERO_DIAS_VERDES = 6;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_LISTADO_FECHAS = 7;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_CODERR = 8;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_MSGERR = 9;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_DENOM_DEVOL
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_LISTA_EJERCICIOS = 2;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_LISTA_EJERCICIOS_CODERR = 3;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_LISTA_EJERCICIOS_MSGERR = 4;



	//Posiciones PLSQL de salida de PK_APR_CALENDARIO_MISUMI.P_APR_AVISO_VALIDAR_CALENDARIO
	private static final int POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO_FLG_VALIDAR_EJER = 2;
	private static final int POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO_ANO_EJERCICIO = 3;
	private static final int POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__FECHA_LIMITE = 4;
	private static final int POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__FLG_MODIF_CAL_CENTRO = 5;
	private static final int POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__AVISO_KO_PLATAFORMA = 6;
	private static final int POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__CODERR = 7;   //AVISO ! Cuando el procedimiento este hecho poner 7
	private static final int POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__MSGERR = 8;   //AVISO ! Cuando el procedimiento este hecho poner 8

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_APR_CONSUL_PROCE_DIARIOS
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_PROCESOS_DIARIOS = 5;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_PROCESOS_DIARIOS_CODERR = 6;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_PROCESOS_DIARIOS_MSGERR = 7;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_APR_CONSUL_PROCE_DIARIOS
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_SERVICIOS_COMBO = 4;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_SERVICIOS_COMBO_CODERR = 5;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_SERVICIOS_COMBO_MSGERR = 6;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_APR_CONSULTAR_CAMB_EST
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_CAMBIO_ESTACIONAL = 3;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_CAMBIO_ESTACIONAL_CODERR = 4;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_CAMBIO_ESTACIONAL_MSGERR = 5;

	//Posiciones PLSQL de salida de PK_APR_CALENDARIO_MISUMI.P_APR_VALID_CALENDARIO
	private static final int POSICION_PARAMETRO_SALIDA_VALIDAR_CALENDARIO__CODERR = 3;
	private static final int POSICION_PARAMETRO_SALIDA_VALIDAR_CALENDARIO__MSGERR = 4;

	//Posiciones PLSQL de salida de PK_APR_CALENDARIO_MISUMI.P_APR_GUARDAR_CAMB_EST
	private static final int POSICION_PARAMETRO_SALIDA_GUARDADO_CALENDARIO_SERVICIO_TEMPORAL_CODERR = 4;
	private static final int POSICION_PARAMETRO_SALIDA_GUARDADO_CALENDARIO_SERVICIO_TEMPORAL_MSGERR = 5;

	//Posiciones PLSQL de salida de PK_APR_CALENDARIO_MISUMI.P_APR_GUARDAR_CALENDARIO
	private static final int POSICION_PARAMETRO_SALIDA_GUARDADO_CALENDARIO_CODERR = 4;
	private static final int POSICION_PARAMETRO_SALIDA_GUARDADO_CALENDARIO_MSGERR = 5;

	@Override
	public CalendarioPendienteValidar pendienteValidarCalendario(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalendarioDescripcionServicios consultarServiciosCombo(final Long codCentro, final Long ejercicio, final String tipoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		CalendarioDescripcionServicios salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.P_APR_CONSUL_SERV_COMBO(?,?,?,?,?,?) }");

						//IN COD_CENTRO
						if(codCentro != null){
							cs.setInt(1, Integer.parseInt(codCentro.toString()));
						}

						//IN EJERCICIO
						if(ejercicio != null){
							cs.setLong(2, ejercicio);
						}

						//IN TIPO EJERCICIO
						if(tipoEjercicio != null){
							cs.setString(3, tipoEjercicio);
						}

						//OUT APR_R_LIST_SERV_CENTRO_REG
						cs.registerOutParameter(4,OracleTypes.STRUCT,"APR_R_LIST_SERV_CENTRO_REG"); 

						//OUT COD ERROR
						cs.registerOutParameter(5,Types.INTEGER); 

						//OUT DESC ERROR
						cs.registerOutParameter(6,Types.VARCHAR); 


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					CalendarioDescripcionServicios ret = null;
					try {
						cs.execute();
						ret = obtenersServiciosCombo(cs,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_SERVICIOS_COMBO,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_SERVICIOS_COMBO_CODERR,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_SERVICIOS_COMBO_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (CalendarioDescripcionServicios) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	//Obtiene un objeto de CalendarioDescripcionServicios. Este objeto contiene una lista con los servicios del combobox, un código de error y un mensaje de error.
	// y otro de descripción.
	private CalendarioDescripcionServicios obtenersServiciosCombo(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsCalendarioDescripcionServicios = null;
		List<CalendarioDescripcionServicio> listaCalendarioDescrServicio = new ArrayList<CalendarioDescripcionServicio>();
		CalendarioDescripcionServicios calendarioDescripcionServicios = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);


		//Transformación de datos para estructura de CalendarioDescripcionServicios
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;


		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
			//Obtenemos el objeto que contiene la lista de servicios del calendario (en otros procedimientos de la aplicación se devuelve directamente la lista, en este caso se
			//ha hecho distinto desde SIA)
			STRUCT estructuraObjetoTablaServicios = (STRUCT)cs.getObject(idParametroResultado1);

			//Obtener datos de el objeto de los servicios del calendario en crudo
			Object[] objectInfo = estructuraObjetoTablaServicios.getAttributes();

			//Obtenemos el array del objeto
			ARRAY calendarioServiciosLst = (ARRAY)objectInfo[0];

			if (calendarioServiciosLst != null){
				rsCalendarioDescripcionServicios = calendarioServiciosLst.getResultSet();

				//Recorrido de la lista de servicios del calendario
				while (rsCalendarioDescripcionServicios.next()) {						
					STRUCT estructuraCalendarioServicios = (STRUCT)rsCalendarioDescripcionServicios.getObject(2);
					CalendarioDescripcionServicio calendarioDescripcionServicio = this.mapRowCalendarioServicios(estructuraCalendarioServicios);

					listaCalendarioDescrServicio.add(calendarioDescripcionServicio);
				}
			}
			calendarioDescripcionServicios = new CalendarioDescripcionServicios(codError, descError, listaCalendarioDescrServicio);
		}
		return calendarioDescripcionServicios;
	}

	//Obtiene un objeto de CalendarioDescripcionServicio. 
	private CalendarioDescripcionServicio mapRowCalendarioServicios(STRUCT estructuraCalendarioServicios) throws SQLException {

		//Obtener datos de CalendarioDescripcionServicio en crudo
		Object[] objectInfo = estructuraCalendarioServicios.getAttributes();

		BigDecimal codServicio_BD = (BigDecimal)objectInfo[0];
		String denomServ_BD = (String)objectInfo[1];

		Long codServicio = ((codServicio_BD != null && !("".equals(codServicio_BD.toString())))?new Long(codServicio_BD.toString()):null);
		String denomServ = denomServ_BD;

		//Creamos un día del calendario.
		CalendarioDescripcionServicio calendarioDescripcionServicio = new CalendarioDescripcionServicio(denomServ,codServicio);
		return calendarioDescripcionServicio;
	}


	@Override
	public CalendarioTotal consultarCalendario(final Long centro, final Long ejercicio, final Long codigoServicio, final String tipoEjercicio) throws Exception {
		// TODO Auto-generated method stub	
		CalendarioTotal salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.P_APR_CONSULTAR_CALENDARIO(?,?,?,?,?,?,?,?,?) }");

						//IN COD_CENTRO
						if(centro != null){
							cs.setInt(1, Integer.parseInt(centro.toString()));
						}

						//IN EJERCICIO
						if(ejercicio != null){
							cs.setLong(2, ejercicio);
						}

						//IN CODIGO SERVICIO
						if(codigoServicio != null){
							cs.setLong(3, codigoServicio);
						}else{
							cs.setNull(3, OracleTypes.INTEGER);
						}

						//IN TIPO EJERCICIO
						if(tipoEjercicio != null){
							cs.setString(4, tipoEjercicio);
						}

						//OUT VALIDAR EJERCICIO
						cs.registerOutParameter(5,Types.VARCHAR);

						//OUT NUMERO ADELANTE ATRAS VERDE
						cs.registerOutParameter(6,Types.INTEGER);

						//OUT LISTADO FECHA
						cs.registerOutParameter(7,OracleTypes.STRUCT,"APR_R_LIST_FECHA_REG"); 

						//OUT COD ERROR
						cs.registerOutParameter(8,Types.INTEGER);

						//OUT MSG ERROR
						cs.registerOutParameter(9,Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					CalendarioTotal ret = null;
					try {
						cs.execute();
						ret = obtenerCalendarioTotal(cs,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_PENDIENTE_VALIDAR_EJERCICIO,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_NUMERO_DIAS_VERDES,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_LISTADO_FECHAS,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_CODERR,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_TOTAL_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (CalendarioTotal) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	//Obtiene un objeto de CalendarioTotal. Este objeto contiene una lista con los días del calendario, un código de error y un mensaje de error.
	// y otro de descripción.
	private CalendarioTotal obtenerCalendarioTotal(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3, int idParametroResultado4, int idParametroResultado5) throws SQLException{
		//Iniciar variables
		ResultSet rsCalendarioDia= null;
		List<CalendarioDia> listaCalendarioDia = new ArrayList<CalendarioDia>();
		CalendarioTotal calendarioTotal = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado4);
		String descError_BD = (String)cs.getString(idParametroResultado5);
		BigDecimal numeroDiasVerdes_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String flgPendienteValidadEjercicios_BD = (String)cs.getString(idParametroResultado1);


		//Transformación de datos para estructura de DevolucionCatalogoEstado
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;
		Long numeroDiasVerdes = ((numeroDiasVerdes_BD != null && !("".equals(numeroDiasVerdes_BD.toString())))?new Long(numeroDiasVerdes_BD.toString()):null); ;
		String flgPendienteValidadEjercicios = flgPendienteValidadEjercicios_BD;


		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
			//Obtenemos el objeto que contiene la lista de ejercicios del día del calendario (en otros procedimientos de la aplicación se devuelve directamente la lista, en este caso se
			//ha hecho distinto desde SIA)
			STRUCT estructuraObjetoTablaDia = (STRUCT)cs.getObject(idParametroResultado3);

			//Obtener datos de el objeto de dias del calendario en crudo
			Object[] objectInfo = estructuraObjetoTablaDia.getAttributes();

			//Obtenemos el array del objeto
			ARRAY calendarioDiaLst = (ARRAY)objectInfo[0];

			if (calendarioDiaLst != null){
				rsCalendarioDia = calendarioDiaLst.getResultSet();

				//Recorrido de la lista de días del calendario
				while (rsCalendarioDia.next()) {						
					STRUCT estructuraCalendarioDia = (STRUCT)rsCalendarioDia.getObject(2);
					CalendarioDia calendarioDia = this.mapRowCalendarioDia(estructuraCalendarioDia);

					listaCalendarioDia.add(calendarioDia);
				}
			}
		}
		calendarioTotal = new CalendarioTotal(codError, descError, flgPendienteValidadEjercicios, numeroDiasVerdes, listaCalendarioDia);
		return calendarioTotal;
	}
	//Obtiene un objeto de CalendarioDia. 
	private CalendarioDia mapRowCalendarioDia(STRUCT estructuraCalendarioDia) throws SQLException {

		//Obtener datos de CalendarioDia en crudo
		Object[] objectInfo = estructuraCalendarioDia.getAttributes();

		Timestamp fechaCalendario_BD = (Timestamp)objectInfo[0];
		String festivo_BD = (String)objectInfo[1];
		String ponerDiaVerde_BD = (String)objectInfo[2];
		String cerrado_BD = (String)objectInfo[3];
		String servicioHabitual_BD = (String)objectInfo[4];
		String cambioEstacional_BD = (String)objectInfo[5];
		String cambioManual_BD = (String)objectInfo[6];
	
		String eCambioPlataforma_BD = (String)objectInfo[7];
		String eSePuedeModificarServicio_BD = (String)objectInfo[8];
		String eAprobadoCambio_BD = (String)objectInfo[9];
		String verdePlataforma_BD = (String)objectInfo[10];
		String puedeSolicitarServicio_BD = (String)objectInfo[11];
		String noServicio_BD = (String)objectInfo[12];
		
		Date fechaCalendario = ((fechaCalendario_BD != null )?new Date(fechaCalendario_BD.getTime()):null);
		String festivo= festivo_BD;
		String ponerDiaVerde = ponerDiaVerde_BD;
		String cerrado = cerrado_BD;
		String servicioHabitual = servicioHabitual_BD;
		String cambioEstacional = cambioEstacional_BD;
		String cambioManual = cambioManual_BD;
		
		//DESCOMENTAR :  MISUMI-126 CUANDO ESTEN LOS CAMBIOS EN EL PROCEDIMIENTO
		String eCambioPlataforma = eCambioPlataforma_BD;
		String eSePuedeModificarServicio = eSePuedeModificarServicio_BD;
		String eAprobadoCambio = eAprobadoCambio_BD;
		
		String verdePlataforma = verdePlataforma_BD;
		String puedeSolicitarServicio = puedeSolicitarServicio_BD;
		String noServicio = noServicio_BD;
		//Creamos un día del calendario.
		CalendarioDia calDia = new CalendarioDia(fechaCalendario, festivo, ponerDiaVerde, cerrado, servicioHabitual, cambioEstacional, cambioManual, eCambioPlataforma, eSePuedeModificarServicio, eAprobadoCambio, verdePlataforma, puedeSolicitarServicio, noServicio);
		return calDia;
	}

	@Override
	public CalendarioActualizado guardarCalendario(final Long codCentro, final String tipoEjer, final List<TCalendarioDia> listTCalendarioDiaYServicios) throws Exception {
		// TODO Auto-generated method stub
		CalendarioActualizado salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	     
						STRUCT itemConsulta = crearEstructuraGuardarCalendario(listTCalendarioDiaYServicios, con);

						cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.P_APR_GUARDAR_CALENDARIO(?,?,?,?,?) }");

						//IN COD_CENTRO
						if(codCentro != null){
							cs.setInt(1, Integer.parseInt(codCentro.toString()));
						}else{
							cs.setNull(1, OracleTypes.INTEGER);
						}

						//IN EJERCICIO
						if(tipoEjer != null){
							cs.setString(2, tipoEjer);
						}else{
							cs.setNull(2, OracleTypes.VARCHAR);
						}

						//IN APR_R_LIST_FECHA_REG 
						if(listTCalendarioDiaYServicios != null && listTCalendarioDiaYServicios.size() > 0){
							cs.setObject(3, itemConsulta);
						}
						//OUT COD ERROR
						cs.registerOutParameter(4,Types.INTEGER); 

						//OUT DESC ERROR
						cs.registerOutParameter(5,Types.VARCHAR); 


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					CalendarioActualizado ret = null;
					try {
						cs.execute();
						ret = obtenerCalendarioActualizado(cs,POSICION_PARAMETRO_SALIDA_GUARDADO_CALENDARIO_CODERR,POSICION_PARAMETRO_SALIDA_GUARDADO_CALENDARIO_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (CalendarioActualizado) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return salida;
	}

	//Devuelve un objeto de lista de días a guardar del calendario.
	private STRUCT crearEstructuraGuardarCalendario(List<TCalendarioDia> listTCalendarioDiaYServicios, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesari para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		// apr_r_list_fecha_reg 
		// datos                 apr_t_r_list_fecha_dat 

		//Creamos el objeto que contendrá apr_r_list_fecha_reg 
		Object[] objectCalendarioGuardar = new Object[1];
		objectCalendarioGuardar[0] = crearArrayCalendarioAGuardar(listTCalendarioDiaYServicios,conexionOracle);

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_LIST_FECHA_REG",conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectCalendarioGuardar);

		return itemConsulta;
	}

	private ARRAY crearArrayCalendarioAGuardar(List<TCalendarioDia> listTCalendarioDiaYServicios, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de lista de calendario de dias. Su tamaño será el de la lista.
		Object[] objectTablaCalendarioDia = new Object[listTCalendarioDiaYServicios.size()];
		int i= 0;
		for(TCalendarioDia calDia: listTCalendarioDiaYServicios){
			//Objeto de CalendarioDia que contiene 8 elementos
			Object[] objectInfo = new Object[14];

			//Rellenamos el objeto con los datos de CalendarioDia
			objectInfo[0] = calDia.getFechaCalendario();
			objectInfo[1] = calDia.getFestivo();
			objectInfo[2] = calDia.getPonerDiaVerde();
			objectInfo[3] = calDia.getCerrado();
			objectInfo[4] = calDia.getServicioHabitual();
			objectInfo[5] = calDia.getCambioEstacional();
			objectInfo[6] = calDia.getCambioManual();
			objectInfo[7] = calDia.getECambioPlataforma();
			objectInfo[8] = calDia.getESePuedeModificarServicio();
			objectInfo[9] = calDia.getEAprobadoCambio();
			objectInfo[10] = calDia.getVerdePlataforma();
			objectInfo[11] = calDia.getPuedeSolicitarServicio();
			objectInfo[12] = calDia.getNoServicio();
			//Se estaba metiendo un NULL  cuando la lista no existía y cascaba porque ellos esperaban una estructura incializada. Se podría haber dejado como arriba
			//si desde PLSQL lo hubieran tratado, pero se ha tratado desde JAVA.
			objectInfo[13] = crearArrayServiciosoAGuardar(calDia,conexionOracle);

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_LIST_FECHA_DAT",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTablaCalendarioDia[i] = itemObjectStruct;

			//Actualizamos el índice del objeto
			i++;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_LIST_FECHA_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaCalendarioDia);
		return array;
	}

	private ARRAY crearArrayServiciosoAGuardar(TCalendarioDia calDia, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de lista de servicios de un dia. Su tamaño será el de la lista.
		int tamanio = calDia.gettCalendarioDiaCambioServicioLst() != null && calDia.gettCalendarioDiaCambioServicioLst().size() > 0 ? calDia.gettCalendarioDiaCambioServicioLst().size():1;
		Object[] objectTablaServicios = new Object[tamanio];
		
		//Hay que mirar si la lista es vacñía, si no lo es rellenamos la estructura. Si lo es, la estructura va vacía, NO EN NULL porque les casca.
		if(calDia.gettCalendarioDiaCambioServicioLst()!= null){
			int i= 0;
			for(TCalendarioDiaCambioServicio calDiaServicios: calDia.gettCalendarioDiaCambioServicioLst()){
				//Objeto de Servicios que contiene 2 elementos
				Object[] objectInfo = new Object[3];

				//Rellenamos el objeto con los datos de CalendarioDia
				objectInfo[0] = calDiaServicios.getCodigoServicio();
				objectInfo[1] = calDiaServicios.getCambioManual();
				objectInfo[2] = calDiaServicios.getPuedeSolicitarServicio();
				
				StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_LIST_CAMBIOS_SERV_DAT",conexionOracle);
				STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

				objectTablaServicios[i] = itemObjectStruct;

				//Actualizamos el índice del objeto
				i++;
			}
		}else{
			//Objeto de Servicios que contiene 2 elementos
			Object[] objectInfo = new Object[3];

			//Rellenamos el objeto con los datos de CalendarioDia
			objectInfo[0] = null;
			objectInfo[1] = null;
			objectInfo[2] = null;
			
			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_LIST_CAMBIOS_SERV_DAT",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
			
			objectTablaServicios[0] = itemObjectStruct;
			
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_LIST_CAMBIOS_SERV_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaServicios);
		return array;
	}

	@Override
	public CalendarioValidado validarCalendario(final Long codCentro, final Long ejercicio) throws Exception {

		CalendarioValidado calValidado = null;
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.p_apr_valid_calendario(?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(codCentro.toString()));
						cs.setInt(2, Integer.parseInt(ejercicio.toString()));
						cs.registerOutParameter(3,Types.INTEGER);
						cs.registerOutParameter(4,Types.VARCHAR);


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					CalendarioValidado ret = null;
					try {
						cs.execute();


						ret = obtenerValidarCalendario(cs, POSICION_PARAMETRO_SALIDA_VALIDAR_CALENDARIO__CODERR, POSICION_PARAMETRO_SALIDA_VALIDAR_CALENDARIO__MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				calValidado = (CalendarioValidado) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return calValidado;
	}

	@Override
	public CalendarioProcesosDiarios consultarProcesosDiarios(final Long codCentro, final Long ejercicio, final Date fecha, final String tipoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		CalendarioProcesosDiarios salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.P_APR_CONSUL_PROCE_DIARIOS(?,?,?,?,?,?,?) }");

						//IN COD_CENTRO
						if(codCentro != null){
							cs.setInt(1, Integer.parseInt(codCentro.toString()));
						}else{
							cs.setNull(1, OracleTypes.INTEGER);
						}

						//IN EJERCICIO
						if(ejercicio != null){
							cs.setLong(2, ejercicio);
						}else{
							cs.setNull(2, OracleTypes.INTEGER);
						}

						//IN CODIGO SERVICIO
						if(fecha != null){
							cs.setDate(3, new java.sql.Date(fecha.getTime()));
						}else{
							cs.setNull(3, OracleTypes.DATE);
						}

						//IN TIPO EJERCICIO
						if(tipoEjercicio != null){
							cs.setString(4, tipoEjercicio);
						}else{
							cs.setNull(4, OracleTypes.VARCHAR);
						}


						//OUT LISTADO FECHA
						cs.registerOutParameter(5,OracleTypes.STRUCT,"APR_R_LIST_SERV_CENTRO_REG"); 

						//OUT COD ERROR
						cs.registerOutParameter(6,Types.INTEGER); 

						//OUT DESC ERROR
						cs.registerOutParameter(7,Types.VARCHAR); 


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					CalendarioProcesosDiarios ret = null;
					try {
						cs.execute();
						ret = obtenerCalendariProcesosDiarios(cs,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_PROCESOS_DIARIOS,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_PROCESOS_DIARIOS_CODERR,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_PROCESOS_DIARIOS_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (CalendarioProcesosDiarios) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	//Obtiene un objeto de CalendarioProcesosDiarios. Este objeto contiene una lista con los servicios para un día del calendario, un código de error y 
	//un mensaje de error.
	private CalendarioProcesosDiarios obtenerCalendariProcesosDiarios(CallableStatement cs,int idParametroResultado1, int idParametroResultado2, int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsCalendarioDiaCambioServicio= null;
		List<CalendarioDiaCambioServicio> listaCalendarioDiaCambioServicio = new ArrayList<CalendarioDiaCambioServicio>();
		CalendarioProcesosDiarios calendarioProcesosDiarios = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de DevolucionCatalogoEstado
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correc

			//Obtenemos el objeto que contiene la lista de procesos diarios del día del calendario (en otros procedimientos de la aplicación se devuelve directamente la lista, en este caso se
			//ha hecho distinto desde SIA)
			STRUCT estructuraObjetoTablaProcesosDiarios = (STRUCT)cs.getObject(idParametroResultado1);

			//Obtener datos de el objeto de los procesos diarios del dia del calendario en crudo
			Object[] objectInfo = estructuraObjetoTablaProcesosDiarios.getAttributes();

			//Obtenemos el array del objeto
			ARRAY calendarioProcesosDiariosLst = (ARRAY)objectInfo[0];

			if (calendarioProcesosDiariosLst != null){
				rsCalendarioDiaCambioServicio = calendarioProcesosDiariosLst.getResultSet();

				//Recorrido de la lista de días del calendario
				while (rsCalendarioDiaCambioServicio.next()) {						
					STRUCT estructuraCalendarioProcesoDiario = (STRUCT)rsCalendarioDiaCambioServicio.getObject(2);
					CalendarioDiaCambioServicio calendarioDiaCambioServicio = this.mapRowCalendarioProcesoDiario(estructuraCalendarioProcesoDiario);

					listaCalendarioDiaCambioServicio.add(calendarioDiaCambioServicio);
				}
			}
		}
		calendarioProcesosDiarios = new CalendarioProcesosDiarios(codError, descError, listaCalendarioDiaCambioServicio,null);
		return calendarioProcesosDiarios;
	}

	//Obtiene un objeto de CalendarioDiaCambioServicio. 
	private CalendarioDiaCambioServicio mapRowCalendarioProcesoDiario(STRUCT estructuraCalendarioProcesoDiario) throws SQLException {

		//Obtener datos de CalendarioDiaCambioServicio en crudo
		Object[] objectInfo = estructuraCalendarioProcesoDiario.getAttributes();

		BigDecimal codigoServicio_BD = (BigDecimal)objectInfo[0];
		String denominacionServicio_BD = (String)objectInfo[1];
		String servicioHabitual_BD = (String)objectInfo[2];
		String cambioEstacional_BD = (String)objectInfo[3];
		String cambioManual_BD = (String)objectInfo[4];
		String eCambioPlataforma_BD = (String)objectInfo[5];
		String eObservacionesConfirmacionPlataforma_BD = (String)objectInfo[6];
		String puedeSolicitarServicio_BD = (String)objectInfo[7];
		
		Long codigoServicio = ((codigoServicio_BD != null && !("".equals(codigoServicio_BD.toString())))?new Long(codigoServicio_BD.toString()):null);;
		String denominacionServicio = denominacionServicio_BD;
		String servicioHabitual = servicioHabitual_BD;
		String cambioEstacional = cambioEstacional_BD;
		String cambioManual = cambioManual_BD;
		String eCambioPlataforma = eCambioPlataforma_BD;
		String eObservacionesConfirmacionPlataforma = eObservacionesConfirmacionPlataforma_BD;
		String puedeSolicitarServicio = puedeSolicitarServicio_BD;
		
		//Creamos un día del calendario.
		CalendarioDiaCambioServicio calendarioDiaCambioServicio = new CalendarioDiaCambioServicio(codigoServicio,denominacionServicio,servicioHabitual,cambioEstacional,cambioManual,eCambioPlataforma,eObservacionesConfirmacionPlataforma,puedeSolicitarServicio);
		return calendarioDiaCambioServicio;
	}

	@Override
	public CalendarioEjercicios consultarAnioCalendarioCombo(final Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		CalendarioEjercicios salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.P_APR_CONSULTAR_EJER_COMBO(?,?,?,?) }");

						//IN COD_CENTRO
						if(codCentro != null){
							cs.setInt(1, Integer.parseInt(codCentro.toString()));
						}

						//OUT LISTADO FECHA
						cs.registerOutParameter(2,OracleTypes.STRUCT,"APR_R_MIS_LIST_EJER_REG"); 

						//OUT COD ERROR
						cs.registerOutParameter(3,Types.INTEGER); 

						//OUT DESC ERROR
						cs.registerOutParameter(4,Types.VARCHAR); 


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					CalendarioEjercicios ret = null;
					try {
						cs.execute();
						ret = obtenerCalendarioEjercicios(cs,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_LISTA_EJERCICIOS,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_LISTA_EJERCICIOS_CODERR,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_LISTA_EJERCICIOS_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (CalendarioEjercicios) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	//Obtiene un objeto de CalendarioTotal. Este objeto contiene una lista con los días del calendario, un código de error y un mensaje de error.
	// y otro de descripción.
	private CalendarioEjercicios obtenerCalendarioEjercicios(CallableStatement cs,int idParametroResultado1, int idParametroResultado2, int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsCalendarioEjercicio= null;
		List<CalendarioEjercicio> listaCalendarioEjercicio = new ArrayList<CalendarioEjercicio>();
		CalendarioEjercicios calendarioEjercicios = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de DevolucionCatalogoEstado
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correc

			//Obtenemos el objeto que contiene la lista de ejercicios del día del calendario (en otros procedimientos de la aplicación se devuelve directamente la lista, en este caso se
			//ha hecho distinto desde SIA)
			STRUCT estructuraObjetoTablaEjercicio = (STRUCT)cs.getObject(idParametroResultado1);

			//Obtener datos de el objeto de dias del calendario en crudo
			Object[] objectInfo = estructuraObjetoTablaEjercicio.getAttributes();

			//Obtenemos el array del objeto
			ARRAY calendarioEjerciciosLst = (ARRAY)objectInfo[0];

			if (calendarioEjerciciosLst != null){
				rsCalendarioEjercicio = calendarioEjerciciosLst.getResultSet();

				//Recorrido de la lista de días del calendario
				while (rsCalendarioEjercicio.next()) {						
					STRUCT estructuraCalendarioEjercicio = (STRUCT)rsCalendarioEjercicio.getObject(2);
					CalendarioEjercicio calendarioEjercicio = this.mapRowCalendarioEjercicio(estructuraCalendarioEjercicio);

					//Completamos la fecha limite en formato String
					if (calendarioEjercicio.getFechaLimiteValidacion() != null) {
						calendarioEjercicio.setStrFechaLimiteValidacion(Utilidades.formatearFecha_dd_MM_yyyy(calendarioEjercicio.getFechaLimiteValidacion()));
					}


					listaCalendarioEjercicio.add(calendarioEjercicio);
				}
			}
		}
		calendarioEjercicios = new CalendarioEjercicios(codError, descError, listaCalendarioEjercicio);
		return calendarioEjercicios;
	}

	//Obtiene un objeto de CalendarioDia. 
	private CalendarioEjercicio mapRowCalendarioEjercicio(STRUCT estructuraCalendarioEjercicio) throws SQLException {

		//Obtener datos de CalendarioDia en crudo
		Object[] objectInfo = estructuraCalendarioEjercicio.getAttributes();


		String tipoEjercicio_BD = (String)objectInfo[0];
		BigDecimal anioEjercicio_BD = (BigDecimal)objectInfo[1];
		String flgPendValiEjer_BD = (String)objectInfo[2];
		Timestamp fechaLimitValid_BD = (Timestamp)objectInfo[3];
		String flgEjerValid_BD = (String)objectInfo[4];
		String flgCambioEstacional_BD = (String)objectInfo[5];
		String flgModiServCentro_BD = (String)objectInfo[6];
		String flgModiCalenCentro_BD = (String)objectInfo[7];
		String flgModiServTec_BD = (String)objectInfo[8];
		String flgModiCalenTec_BD = (String)objectInfo[9];

		String tipoEjercicio = tipoEjercicio_BD;
		Long anioEjercicio = ((anioEjercicio_BD != null && !("".equals(anioEjercicio_BD.toString())))?new Long(anioEjercicio_BD.toString()):null);
		String flgPendValiEjer = flgPendValiEjer_BD;
		Date fechaLimitValid = ((fechaLimitValid_BD != null )?new Date(fechaLimitValid_BD.getTime()):null);
		String flgEjerValid = flgEjerValid_BD;
		String flgCambioEstacional = flgCambioEstacional_BD;
		String flgModiServCentro = flgModiServCentro_BD;
		String flgModiCalenCentro = flgModiCalenCentro_BD;
		String flgModiServTec = flgModiServTec_BD;
		String flgModiCalenTec = flgModiCalenTec_BD;

		//Creamos un día del calendario.
		CalendarioEjercicio calendarioEjercicio = new CalendarioEjercicio(tipoEjercicio, anioEjercicio, flgPendValiEjer, fechaLimitValid, flgEjerValid, flgCambioEstacional, flgModiServCentro, flgModiCalenCentro, flgModiServTec, flgModiCalenTec); 
		return calendarioEjercicio;
	}


	private CalendarioAvisos obtenerCalendarioAvisos(CallableStatement cs,Long codCentro, int idParametroResultado1,int idParametroResultado2,int idParametroResultado3,int idParametroResultado4,int idParametroResultado5,int idParametroResultado6, int idParametroResultado7) throws SQLException{
		//Iniciar variables
		CalendarioAvisos calendarioAvisos = null;

		//Obtención de los parámetros de salida en crudo

		String flgPendValidarEjer_BD = (String)cs.getObject(idParametroResultado1);
		Integer anoEjerPendValid_BD = (Integer)cs.getObject(idParametroResultado2);
		Date fechaLimiteValid_BD = (Date)cs.getObject(idParametroResultado3);
		String flgModifCalendCentro_BD = (String)cs.getObject(idParametroResultado4);
		String avisoKoPlataforma_BD = (String)cs.getObject(idParametroResultado5);   //AVISO ! Descomentar cuando este el procedimiento realizado
		Integer codError_BD = (Integer)cs.getObject(idParametroResultado6);				
		String descError_BD = (String)cs.getObject(idParametroResultado7);				


		//Transformación de datos para estructura de DevolucionAvisos
		String flgPendValidarEjer = flgPendValidarEjer_BD;
		Long anoEjerPendValid = ((anoEjerPendValid_BD != null && !("".equals(anoEjerPendValid_BD.toString())))?new Long(anoEjerPendValid_BD.toString()):null);;
		Date fechaLimiteValid = ((fechaLimiteValid_BD != null )?fechaLimiteValid_BD:null);
		String flgModifCalendCentro = flgModifCalendCentro_BD;
		String avisoKoPlataforma = avisoKoPlataforma_BD;
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		calendarioAvisos = new CalendarioAvisos(codCentro, flgPendValidarEjer, anoEjerPendValid, fechaLimiteValid, flgModifCalendCentro, avisoKoPlataforma, codError, descError);

		return calendarioAvisos;
	}



	private CalendarioValidado obtenerValidarCalendario(CallableStatement cs,int idParametroResultado3,int idParametroResultado4) throws SQLException{
		//Iniciar variables
		CalendarioValidado calendarioValidado = null;

		//Obtención de los parámetros de salida en crudo
		Integer codError_BD = (Integer)cs.getObject(idParametroResultado3);
		String descError_BD = (String)cs.getObject(idParametroResultado4);


		//Transformación de datos para estructura de DevolucionAvisos
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		calendarioValidado = new CalendarioValidado(codError, descError);

		return calendarioValidado;
	}

	@Override
	public CalendarioAvisos cargarAvisosCalendario(final Long codCentro) throws Exception {

		CalendarioAvisos salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.p_apr_aviso_validar_calendario(?,?,?,?,?,?,?,?) }"); 
						cs.setInt(1, Integer.parseInt(codCentro.toString()));

						cs.registerOutParameter(2,Types.VARCHAR);
						cs.registerOutParameter(3,Types.INTEGER);
						cs.registerOutParameter(4,Types.DATE);
						cs.registerOutParameter(5,Types.VARCHAR);
					 	cs.registerOutParameter(6,Types.VARCHAR); //AVISO ! Descomentar cuando este el procedimiento realizado
						cs.registerOutParameter(7,Types.INTEGER); //AVISO ! Incrementar el numero de paramtro
						cs.registerOutParameter(8,Types.VARCHAR); //AVISO ! Incrementar el numero de paramtro


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					CalendarioAvisos ret = null;
					try {
						cs.execute();


						ret = obtenerCalendarioAvisos(cs, codCentro, POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO_FLG_VALIDAR_EJER,POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO_ANO_EJERCICIO,POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__FECHA_LIMITE, POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__FLG_MODIF_CAL_CENTRO, POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__AVISO_KO_PLATAFORMA, POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__CODERR, POSICION_PARAMETRO_SALIDA_AVISO_CALENDARIO__MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (CalendarioAvisos) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public CalendarioCambioEstacional consultarCambiosEstacionales(final Long codCentro, final Long ejercicio) throws Exception {
		// TODO Auto-generated method stub
		CalendarioCambioEstacional salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.P_APR_CONSULTAR_CAMB_EST(?,?,?,?,?) }");

						//IN COD_CENTRO
						if(codCentro != null){
							cs.setInt(1, Integer.parseInt(codCentro.toString()));
						}

						//IN COD_CENTRO
						if(ejercicio != null){
							cs.setInt(2, Integer.parseInt(ejercicio.toString()));
						}

						//OUT APR_R_LIST_VAL_SERV_REG
						cs.registerOutParameter(3,OracleTypes.STRUCT,"APR_R_LIST_VAL_SERV_REG"); 

						//OUT COD ERROR
						cs.registerOutParameter(4,Types.INTEGER); 

						//OUT DESC ERROR
						cs.registerOutParameter(5,Types.VARCHAR); 


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					CalendarioCambioEstacional ret = null;
					try {
						cs.execute();
						ret = obtenerCalendarioCambioEstacional(cs,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_CAMBIO_ESTACIONAL,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_CAMBIO_ESTACIONAL_CODERR,POSICION_PARAMETRO_SALIDA_CONSULTA_CALENDARIO_CAMBIO_ESTACIONAL_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (CalendarioCambioEstacional) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	//Obtiene un objeto de CalendarioCambioEstacional. Este objeto contiene una lista con los cambios estacionales del calendario, un código de error y un mensaje de error.
	private CalendarioCambioEstacional obtenerCalendarioCambioEstacional(CallableStatement cs,int idParametroResultado1, int idParametroResultado2, int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsCalendarioValidacion= null;
		List<CalendarioValidacion> listaCalendarioValidacion = new ArrayList<CalendarioValidacion>();
		CalendarioCambioEstacional calendarioCambioEstacional = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de CalendarioCambioEstacional
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correc

			//Obtenemos el objeto que contiene la lista de validaciones del calendario (en otros procedimientos de la aplicación se devuelve directamente la lista, en este caso se
			//ha hecho distinto desde SIA)
			STRUCT estructuraObjetoTablaValidacion = (STRUCT)cs.getObject(idParametroResultado1);

			//Obtener datos de el objeto de dias del calendario en crudo
			Object[] objectInfo = estructuraObjetoTablaValidacion.getAttributes();

			//Obtenemos el array del objeto
			ARRAY calendarioValidacionLst = (ARRAY)objectInfo[0];

			if (calendarioValidacionLst != null){
				rsCalendarioValidacion = calendarioValidacionLst.getResultSet();

				//Recorrido de la lista de días del calendario
				while (rsCalendarioValidacion.next()) {						
					STRUCT estructuraCalendarioValidacion = (STRUCT)rsCalendarioValidacion.getObject(2);
					CalendarioValidacion calendarioValidacion = this.mapRowCalendarioValidacion(estructuraCalendarioValidacion);

					listaCalendarioValidacion.add(calendarioValidacion);
				}
			}
		}
		calendarioCambioEstacional = new CalendarioCambioEstacional(codError, descError, listaCalendarioValidacion);
		return calendarioCambioEstacional;
	}

	//Obtiene un objeto de CalendarioDia. 
	private CalendarioValidacion mapRowCalendarioValidacion(STRUCT estructuraCalendarioValidacion) throws SQLException {
		//Inicializar variable
		List<CalendarioServicio> listaCalendarioServicio = new ArrayList<CalendarioServicio>();

		//ResultSet de los Servicios del calendario
		ResultSet rsCalendarioServicios= null;

		//Obtener datos de CalendarioValidacion en crudo
		Object[] objectInfo = estructuraCalendarioValidacion.getAttributes();

		BigDecimal codigoServicio_BD = (BigDecimal)objectInfo[0];
		String denominacionServicio_BD = (String)objectInfo[1];
		String servicioHabitualLunes_BD = (String)objectInfo[2];
		String servicioHabitualMartes_BD = (String)objectInfo[3];
		String servicioHabitualMiercoles_BD = (String)objectInfo[4];
		String servicioHabitualJueves_BD = (String)objectInfo[5];
		String servicioHabitualViernes_BD = (String)objectInfo[6];
		String servicioHabitualSabado_BD = (String)objectInfo[7];
		String servicioHabitualDomingo_BD = (String)objectInfo[8];
		ARRAY calendarioServiciosLst = (ARRAY)objectInfo[9];

		Long codigoServicio = ((codigoServicio_BD != null && !("".equals(codigoServicio_BD.toString())))?new Long(codigoServicio_BD.toString()):null);
		String denominacionServicio = denominacionServicio_BD;
		String servicioHabitualLunes = servicioHabitualLunes_BD;
		String servicioHabitualMartes = servicioHabitualMartes_BD;
		String servicioHabitualMiercoles = servicioHabitualMiercoles_BD;
		String servicioHabitualJueves = servicioHabitualJueves_BD;
		String servicioHabitualViernes = servicioHabitualViernes_BD;
		String servicioHabitualSabado = servicioHabitualSabado_BD;
		String servicioHabitualDomingo = servicioHabitualDomingo_BD;

		if (calendarioServiciosLst != null){
			rsCalendarioServicios = calendarioServiciosLst.getResultSet();

			//Recorrido de la lista de días del calendario
			while (rsCalendarioServicios.next()) {						
				STRUCT estructuraCalendarioServicios = (STRUCT)rsCalendarioServicios.getObject(2);
				CalendarioServicio calendarioServicio = this.mapRowCalendarioServicio(estructuraCalendarioServicios);

				listaCalendarioServicio.add(calendarioServicio);
			}
		}

		//Creamos una validacion del calendario.
		CalendarioValidacion calendarioEjercicio = new CalendarioValidacion(codigoServicio,denominacionServicio,servicioHabitualLunes,servicioHabitualMartes,servicioHabitualMiercoles,servicioHabitualJueves,servicioHabitualViernes,servicioHabitualSabado,servicioHabitualDomingo,listaCalendarioServicio); 
		return calendarioEjercicio;
	}

	//Obtiene un objeto de CalendarioServicio. 
	private CalendarioServicio mapRowCalendarioServicio(STRUCT estructuraCalendarioServicios) throws SQLException {

		//Obtener datos de CalendarioServicio en crudo
		Object[] objectInfo = estructuraCalendarioServicios.getAttributes();


		String servicioEstacionalLunes_BD = (String)objectInfo[0];
		String servicioEstacionalMartes_BD = (String)objectInfo[1];
		String servicioEstacionalMiercoles_BD = (String)objectInfo[2];
		String servicioEstacionalJueves_BD = (String)objectInfo[3];
		String servicioEstacionalViernes_BD = (String)objectInfo[4];
		String servicioEstacionalSabado_BD = (String)objectInfo[5];
		String servicioEstacionalDomingo_BD = (String)objectInfo[6];
		Timestamp fechaEstacionalInicio_BD = (Timestamp)objectInfo[7];
		Timestamp fechaEstacionalFin_BD = (Timestamp)objectInfo[8];
		String noLoQuiero_BD = (String)objectInfo[9];
		BigDecimal plazoMaxDuracionTotal_BD = (BigDecimal)objectInfo[10];
		BigDecimal plazoMaxAdelantarAtrasar_BD = (BigDecimal)objectInfo[11];
		
		String servicioEstacionalLunes = servicioEstacionalLunes_BD;
		String servicioEstacionalMartes = servicioEstacionalMartes_BD;
		String servicioEstacionalMiercoles = servicioEstacionalMiercoles_BD;
		String servicioEstacionalJueves = servicioEstacionalJueves_BD;
		String servicioEstacionalViernes = servicioEstacionalViernes_BD;
		String servicioEstacionalSabado = servicioEstacionalSabado_BD;
		String servicioEstacionalDomingo = servicioEstacionalDomingo_BD;
		Date fechaEstacionalInicio = ((fechaEstacionalInicio_BD != null )?new Date(fechaEstacionalInicio_BD.getTime()):null);
		Date fechaEstacionalFin = ((fechaEstacionalFin_BD != null )?new Date(fechaEstacionalFin_BD.getTime()):null);
		Date fechaEstacionalInicioCentro = ((fechaEstacionalInicio_BD != null )?new Date(fechaEstacionalInicio_BD.getTime()):null);;
		Date fechaEstacionalFinCentro = ((fechaEstacionalFin_BD != null )?new Date(fechaEstacionalFin_BD.getTime()):null);;
		String noLoQuiero = noLoQuiero_BD;
		String noLoQuieroCentro = noLoQuiero_BD;
		Long plazoMaxDuracionTotal = ((plazoMaxDuracionTotal_BD != null && !("".equals(plazoMaxDuracionTotal_BD.toString())))?new Long(plazoMaxDuracionTotal_BD.toString()):null);
		Long plazoMaxAdelantarAtrasar = ((plazoMaxAdelantarAtrasar_BD != null && !("".equals(plazoMaxAdelantarAtrasar_BD.toString())))?new Long(plazoMaxAdelantarAtrasar_BD.toString()):null);
		
		//Creamos una validacion del calendario.
		CalendarioServicio calendarioServicio = new CalendarioServicio(servicioEstacionalLunes,servicioEstacionalMartes,servicioEstacionalMiercoles,servicioEstacionalJueves,servicioEstacionalViernes,servicioEstacionalSabado,servicioEstacionalDomingo,fechaEstacionalInicio,fechaEstacionalFin,fechaEstacionalInicioCentro,fechaEstacionalFinCentro,noLoQuiero,noLoQuieroCentro, plazoMaxDuracionTotal, plazoMaxAdelantarAtrasar); 
		
		return calendarioServicio;
	}

	@Override
	public CalendarioActualizado guardarCambioEstacional(final Long codCentro, final Long codigoEjercicio, final List<CalendarioValidacion> calValLst) throws Exception {
		// TODO Auto-generated method stub
		CalendarioActualizado salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	   
						STRUCT itemConsulta = crearEstructuraCalendarioValidacion(calValLst, con);

						cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.P_APR_GUARDAR_CAMB_EST(?,?,?,?,?) }");

						//IN COD_CENTRO
						if(codCentro != null){
							cs.setInt(1, Integer.parseInt(codCentro.toString()));
						}

						//IN codigo ejercicio
						if(codigoEjercicio != null){
							cs.setInt(2, Integer.parseInt(codigoEjercicio.toString()));
						}

						//IN APR_R_LIST_VAL_SERV_REG
						if(calValLst != null && calValLst.size() > 0){
							cs.setObject(3, itemConsulta);
						}

						//OUT COD ERROR
						cs.registerOutParameter(4,Types.INTEGER); 

						//OUT DESC ERROR
						cs.registerOutParameter(5,Types.VARCHAR); 

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					CalendarioActualizado ret = null;
					try {
						cs.execute();
						ret = obtenerCalendarioActualizado(cs,POSICION_PARAMETRO_SALIDA_GUARDADO_CALENDARIO_SERVICIO_TEMPORAL_CODERR,POSICION_PARAMETRO_SALIDA_GUARDADO_CALENDARIO_SERVICIO_TEMPORAL_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (CalendarioActualizado) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	//Devuelve un objeto de lista de calendarios de validacion
	private STRUCT crearEstructuraCalendarioValidacion(List<CalendarioValidacion> calValLst, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesari para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		// apr_r_list_serv_in_reg 
		// datos                 apr_t_r_list_serv_in_dat 

		//Creamos el objeto que contendrá apr_t_r_list_serv_in_dat 
		Object[] objectCalendarioValidaciones = new Object[1];
		objectCalendarioValidaciones[0] = crearArrayCalendarioValidacion(calValLst,conexionOracle);

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_LIST_SERV_IN_REG",conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectCalendarioValidaciones);

		return itemConsulta;
	}

	private ARRAY crearArrayCalendarioValidacion(List<CalendarioValidacion> calValLst, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de lista de calendario de validación. Su tamaño será el de la lista
		Object[] objectTablaCalendarioValidacion = new Object[calValLst.size()];
		int i= 0;
		for(CalendarioValidacion calVal: calValLst){
			//Objeto de CalendarioValidacion que contiene 2 elementos
			Object[] objectInfo = new Object[2];

			//Rellenamos el objeto con los datos de calendarioValidacion
			objectInfo[0] = calVal.getCodigoServicio();
			objectInfo[1] = crearServicioTemporal(calVal,conexionOracle);

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_LIST_SERV_IN",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTablaCalendarioValidacion[i] = itemObjectStruct;

			//Actualizamos el índice del objeto
			i++;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_LIST_SERV_IN_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaCalendarioValidacion);
		return array;
	}

	private ARRAY crearServicioTemporal(CalendarioValidacion calVal, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de lista de servicios del calendario de validación. Su tamaño será el de la lista.
		Object[] objectTablaServicioHabitual = new Object[calVal.getCalendarioServicioLst().size()];
		int i= 0;
		for(CalendarioServicio calServ: calVal.getCalendarioServicioLst()){
			//Objeto de CalendarioServicio que contiene 5 elementos
			Object[] objectInfo = new Object[5];
			//Object[] objectInfo = new Object[4];
			
			//Rellenamos el objeto con los datos de calendarioValidacion
			objectInfo[0] = calServ.getFechaEstacionalInicio()!=null?new Timestamp(calServ.getFechaEstacionalInicio().getTime()):null;
			objectInfo[1] =  calServ.getFechaEstacionalFin()!=null?new Timestamp(calServ.getFechaEstacionalFin().getTime()):null;
			//objectInfo[2] = calServ.getFechaEstacionalInicioCentro()!=null && ("N").equals(calServ.getNoLoQuieroCentro()) ? new Timestamp(calServ.getFechaEstacionalInicioCentro().getTime()):null;
			//objectInfo[3] = calServ.getFechaEstacionalFinCentro()!=null && ("N").equals(calServ.getNoLoQuieroCentro()) ? new Timestamp(calServ.getFechaEstacionalFinCentro().getTime()):null;
			objectInfo[2] = calServ.getFechaEstacionalInicioCentro()!=null ? new Timestamp(calServ.getFechaEstacionalInicioCentro().getTime()):null;
			objectInfo[3] = calServ.getFechaEstacionalFinCentro()!=null ? new Timestamp(calServ.getFechaEstacionalFinCentro().getTime()):null;
			objectInfo[4] = calServ.getNoLoQuieroCentro();
			
			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_LIST_PERIODOS",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTablaServicioHabitual[i] = itemObjectStruct;

			//Actualizamos el índice del objeto
			i++;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_LIST_PERIODOS", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaServicioHabitual);
		return array;
	}

	//Obtiene un objeto de CalendarioCambioEstacional. Este objeto contiene una lista con los cambios estacionales del calendario, un código de error y un mensaje de error.
	private CalendarioActualizado obtenerCalendarioActualizado(CallableStatement cs,int idParametroResultado1, int idParametroResultado2) throws SQLException{

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);

		//Transformación de datos para estructura de CalendarioCambioEstacional
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		CalendarioActualizado clAct = new CalendarioActualizado(codError, descError);
		return clAct;
	}
	

	
	@Override
	public CalendarioWarnings consultaModificacionWarnings(final Long codCentro, final List<CalendarioDiaWarning> listaWarnings) throws Exception {
    	
    	CalendarioWarnings salida = null;
    	
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

	            @Override
	            public CallableStatement createCallableStatement(Connection con) {
	                CallableStatement cs = null;
	                try {

	                	//Crear estructura para actualización
	                	ARRAY itemConsulta = crearEstructuraConsultaModificacionWarnings(listaWarnings, con);
	                	
	        	    	cs = con.prepareCall("{call PK_APR_CALENDARIO_MISUMI.p_apr_consul_modif_war_cal(?, ?, ?, ?) }");
	        	    	
	        	    	cs.setObject(1, codCentro.toString());
	        	    	
	        	    	//IN LISTA DE WARNINGS PARA MODIFICACION, IRÁ LA LISTA VACÍA PARA CONSULTA 
						cs.setArray(2, itemConsulta);

	        	    	//OUT LISTA DE WARNINGS
	        	    	cs.registerOutParameter(2, OracleTypes.ARRAY, "APR_T_R_LISTA_WARNING");
	        	    	
						//OUT COD ERROR
						cs.registerOutParameter(3,Types.INTEGER); 

						//OUT DESC ERROR
						cs.registerOutParameter(4,Types.VARCHAR); 
	              
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return cs;
	            }
	        };
	        CallableStatementCallback csCallback = new CallableStatementCallback() {

	            public Object doInCallableStatement(CallableStatement cs) {
	                CalendarioWarnings ret = null;
	                ResultSet rs = null;
	                try {
		                cs.execute();
	                    ret = obtenerResultadoConsultaModificacionWarnings(cs, rs);
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	    	            logger.error(e.getMessage());
	                }
	                return ret;
	            }
	        };
	        
	        
	        try {
	        	salida = (CalendarioWarnings) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//Reinyectamos en código de centro pasado por parámetro
		salida.setCodCentro(codCentro);
		
	    return salida;
    }
	
	private ARRAY crearEstructuraConsultaModificacionWarnings(List<CalendarioDiaWarning> listaWarnings, Connection con) throws SQLException {

		OracleConnection conexionOracle = (OracleConnection)con.getMetaData().getConnection();
		
		Object[] objectTablaWarnings;
		if (listaWarnings.size()>0){
			objectTablaWarnings = new Object[listaWarnings.size()];
		}else{
			objectTablaWarnings = new Object[0];
		}

		int i = 0;
		for (CalendarioDiaWarning calendarioDiaWarning : listaWarnings)
		{
			Object[] objectWarning = new Object[3];

			objectWarning[0] = (calendarioDiaWarning.getFechaAfectada()!=null?new Timestamp(calendarioDiaWarning.getFechaAfectada().getTime()):null);

			objectWarning[1] = calendarioDiaWarning.getDescripcionAviso();

			objectWarning[2] = Constantes.CALENDARIO_WARNING_LEIDO; //Marcamos el warning como leído

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_LISTA_WARNING", conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor, conexionOracle, objectWarning);

			objectTablaWarnings[i] = itemObjectStruct;

			i++;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_LISTA_WARNING", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaWarnings);

		return array;
		
    }
	
	private CalendarioWarnings obtenerResultadoConsultaModificacionWarnings(CallableStatement cs, ResultSet rs){
    	
		ResultSet rsWarnings = null;

		List <CalendarioDiaWarning> listaWarnings = new ArrayList<CalendarioDiaWarning>();
		CalendarioWarnings calendarioWarnings = new CalendarioWarnings();
		
		try{
			ARRAY warningsLst = (ARRAY)cs.getObject(2);
			if (warningsLst != null) {
				rsWarnings = warningsLst.getResultSet();
	
				while (rsWarnings.next()) {
					STRUCT estructuraWarning = (STRUCT)rsWarnings.getObject(2);
					CalendarioDiaWarning calendarioDiaWarning = mapRowCalendarioDiaWarning(estructuraWarning);
	
					listaWarnings.add(calendarioDiaWarning);
				}
			}
			
			//Obtención de los parámetros de salida en crudo
			BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(3);
			String descrError_BD = (String)cs.getString(4);

			//Transformación de datos para estructura de CalendarioWarning
			Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
			String descrError = descrError_BD;

	        calendarioWarnings = new CalendarioWarnings();
	        calendarioWarnings.setListaWarnings(listaWarnings);
	        calendarioWarnings.setCodError(codError);
	        calendarioWarnings.setDescrError(descrError);

		} catch (Exception e) {
        	e.printStackTrace();
		}
		
		return calendarioWarnings;
		
    }

	//Obtiene un objeto de CalendarioDiaWarning. 
	private CalendarioDiaWarning mapRowCalendarioDiaWarning(STRUCT estructuraCalendarioDiaWarning) throws SQLException {

		//Obtener datos de CalendarioDiaWarning en crudo
		Object[] objectInfo = estructuraCalendarioDiaWarning.getAttributes();

		Timestamp fechaAfectada_BD = (Timestamp)objectInfo[0];
		String descripcionAviso_BD = (String)objectInfo[1];
		String leido_BD = (String)objectInfo[2];
		
		Date fechaAfectada = ((fechaAfectada_BD != null )?new Date(fechaAfectada_BD.getTime()):null);
		String descripcionAviso = descripcionAviso_BD;
		String leido = leido_BD;
		String fechaAfectadaDDMMYYYY = Utilidades.formatearFecha(fechaAfectada);

		//Creamos un warning de un día del calendario.
		CalendarioDiaWarning calDiaWarning = new CalendarioDiaWarning(fechaAfectada, descripcionAviso, leido, fechaAfectadaDDMMYYYY);
		
		return calDiaWarning;
	}

}
