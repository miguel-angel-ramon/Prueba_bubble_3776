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

import es.eroski.misumi.dao.iface.EntradasDescentralizadasSIADao;
import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaAvisos;
import es.eroski.misumi.model.EntradaCatalogo;
import es.eroski.misumi.model.EntradaEstado;
import es.eroski.misumi.model.EntradaFinalizar;
import es.eroski.misumi.model.EntradaLinea;
import es.eroski.misumi.model.EntradasCatalogoEstado;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class EntradasDescentralizadasSIADaoImpl implements EntradasDescentralizadasSIADao{
	private static Logger logger = Logger.getLogger(EntradasDescentralizadasSIADaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	//Posiciones PLSQL de salida de PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_OBT_COMBO
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_ENTRADAS_DESCENTRALIZADAS_DENOMINACIONES_LISTA = 3;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_ENTRADAS_DESCENTRALZADAS_DENOMINACIONES_TIPOS_CODERR = 4;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_ENTRADAS_DESCENTRALIZADAS_DENOMINACIONES_TIPOS_MSGERR = 5;

	//Posiciones PLSQL de salida de PK_APR_ENTRADAS_MISUMI.P_APR_OBT_NUMERO_LINEAS
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_ESTADO_ENTRADAS_DESCENTRALIZADAS_LISTA = 9;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_ESTADO_ENTRADAS_DESCENTRALIZADAS_CODERR = 10;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_ESTADO_ENTRADAS_DESCENTRALIZADAS_LISTA_MSGERR = 11;

	//Posiciones PLSQL de salida de PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_OBT_ENTRADA_TOTAL
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_LISTA_ENTRADA_LINEAS_LISTA= 3;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_LISTA_ENTRADA_LINEAS_CODERR = 4;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_LISTA_ENTRADA_LINEAS_MSGERR = 5;

	//Posiciones PLSQL de salida de PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_OBT_CAB_ENTRADA
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CABECERA_ENTRADAS_DESCENTRALIZADAS_LISTA = 9;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CABECERA_ENTRADAS_DESCENTRALIZADAS_CODERR = 10;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CABECERA_ENTRADAS_DESCENTRALIZADAS_LISTA_MSGERR = 11;

	//Posiciones PLSQL de salida de PK_APR_ENTRADAS_MISUMI.P_APR_ACTUALIZAR_ENTRADA
	private static final int POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_LISTA = 2;
	private static final int POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_CODERR = 3;
	private static final int POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_MSGERR = 4;

	//Posiciones PLSQL de salida de PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_FINALIZAR_ENTRADA
	private static final int POSICION_PARAMETRO_SALIDA_FINALIZAR_ENTRADA_CODERR = 3;
	private static final int POSICION_PARAMETRO_SALIDA_FINALIZAR_ENTRADA_MSGERR = 4;
	
	//Posiciones PLSQL de salida de PK_APR_ENTRADAS_DESCEN_MISUMI.P_AVISOS_ENTRADA_PEND
	private static final int POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGPENDDARENTRADA = 2;
	private static final int POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGFRESCOS = 3;
	private static final int POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGALIMENTACION = 4;
	private static final int POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGNOALI = 5;
	private static final int POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_CODERR = 6;
	private static final int POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_MSGERR = 7;
	
	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/***********************************          LLAMADAS A LOS PROCEDIMIENTOS       ***********************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/

	@Override
	public EntradaCatalogo cargarDenominacionesEntradasDescentralizadas(final Long codLoc, final String flgHistorico) {
		// TODO Auto-generated method stub
		EntradaCatalogo salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_OBT_COMBO(?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(codLoc.toString()));
						cs.setString(2, flgHistorico);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_ENTRADAS_DESCENTRALIZADAS_DENOMINACIONES_LISTA, OracleTypes.ARRAY, "APR_T_R_MIS_ENTRADA");
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_ENTRADAS_DESCENTRALZADAS_DENOMINACIONES_TIPOS_CODERR, Types.INTEGER);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_ENTRADAS_DESCENTRALIZADAS_DENOMINACIONES_TIPOS_MSGERR, Types.VARCHAR);
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					EntradaCatalogo ret = null;
					try {
						cs.execute();
						ret = obtenerDenominacionesComboEntradas(cs, POSICION_PARAMETRO_SALIDA_CONSULTA_ENTRADAS_DESCENTRALIZADAS_DENOMINACIONES_LISTA, POSICION_PARAMETRO_SALIDA_CONSULTA_ENTRADAS_DESCENTRALZADAS_DENOMINACIONES_TIPOS_CODERR, POSICION_PARAMETRO_SALIDA_CONSULTA_ENTRADAS_DESCENTRALIZADAS_DENOMINACIONES_TIPOS_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (EntradaCatalogo)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return salida;
	} 

	@Override
	public EntradasCatalogoEstado loadEstadoEntradasDescentralizadas(final Entrada entrada, final Long codArt,
			final String flgHistorico) {
		// TODO Auto-generated method stub
		EntradasCatalogoEstado salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_OBT_NUMERO_LINEAS(?,?,?,?,?,?,?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(entrada.getCodLoc().toString()));

						if (entrada.getCodCabPedido() != null){
							cs.setLong(2, entrada.getCodCabPedido());
						}else{							
							cs.setNull(2, OracleTypes.INTEGER);
						}

						if (codArt != null){
							cs.setLong(3, codArt);
						}else{							
							cs.setNull(3, OracleTypes.INTEGER);
						}

						if (entrada.getCodProvGen() != null){
							cs.setLong(4, entrada.getCodProvGen());
						}else{							
							cs.setNull(4, OracleTypes.INTEGER);
						}

						if (entrada.getCodProvTrab() != null){
							cs.setLong(5, entrada.getCodProvTrab());
						}else{							
							cs.setNull(5, OracleTypes.INTEGER);
						}

						if(entrada.getCodAlbProv() != null && !"".equals(entrada.getCodAlbProv())){
							cs.setString(6, entrada.getCodAlbProv());
						}else{							
							cs.setNull(6, OracleTypes.NULL);
						}

						if (entrada.getTipoRecepcion() != null){
							cs.setLong(7, entrada.getTipoRecepcion());
						}else{							
							cs.setNull(7, OracleTypes.INTEGER);
						}

						if(flgHistorico != null && !"".equals(flgHistorico)){
							cs.setString(8, flgHistorico);
						}else{							
							cs.setNull(8, OracleTypes.NULL);
						}

						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_ESTADO_ENTRADAS_DESCENTRALIZADAS_LISTA, OracleTypes.ARRAY, "APR_T_R_MIS_ENTRADA_NUMERO");
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_ESTADO_ENTRADAS_DESCENTRALIZADAS_CODERR, Types.INTEGER);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_ESTADO_ENTRADAS_DESCENTRALIZADAS_LISTA_MSGERR, Types.VARCHAR);
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					EntradasCatalogoEstado ret = null;
					try {
						cs.execute();
						ret = obtenerEntradasCatalogoEstado(cs, POSICION_PARAMETRO_SALIDA_CONSULTA_ESTADO_ENTRADAS_DESCENTRALIZADAS_LISTA, POSICION_PARAMETRO_SALIDA_CONSULTA_ESTADO_ENTRADAS_DESCENTRALIZADAS_CODERR, POSICION_PARAMETRO_SALIDA_CONSULTA_ESTADO_ENTRADAS_DESCENTRALIZADAS_LISTA_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (EntradasCatalogoEstado)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return salida;
	}

	@Override
	public EntradasCatalogoEstado loadCabeceraEntradas(final Entrada entrada, final Long codArt,
			final String flgHistorico) {
		// TODO Auto-generated method stub
		EntradasCatalogoEstado salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_OBT_CAB_ENTRADA(?,?,?,?,?,?,?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(entrada.getCodLoc().toString()));

						if (entrada.getCodCabPedido() != null){
							cs.setLong(2, entrada.getCodCabPedido());
						}else{							
							cs.setNull(2, OracleTypes.INTEGER);
						}

						if (codArt != null){
							cs.setLong(3, codArt);
						}else{							
							cs.setNull(3, OracleTypes.INTEGER);
						}

						if (entrada.getCodProvGen() != null){
							cs.setLong(4, entrada.getCodProvGen());
						}else{							
							cs.setNull(4, OracleTypes.INTEGER);
						}

						if (entrada.getCodProvTrab() != null){
							cs.setLong(5, entrada.getCodProvTrab());
						}else{							
							cs.setNull(5, OracleTypes.INTEGER);
						}

						if(entrada.getCodAlbProv() != null && !"".equals(entrada.getCodAlbProv())){
							cs.setString(6, entrada.getCodAlbProv());
						}else{							
							cs.setNull(6, OracleTypes.NULL);
						}

						if (entrada.getTipoRecepcion() != null){
							cs.setLong(7, entrada.getTipoRecepcion());
						}else{							
							cs.setNull(7, OracleTypes.INTEGER);
						}

						if(flgHistorico != null && !"".equals(flgHistorico)){
							cs.setString(8, flgHistorico);
						}else{							
							cs.setNull(8, OracleTypes.NULL);
						}

						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_CABECERA_ENTRADAS_DESCENTRALIZADAS_LISTA, OracleTypes.ARRAY, "APR_T_R_MIS_ENTRADA_NUMERO");
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_CABECERA_ENTRADAS_DESCENTRALIZADAS_CODERR, Types.INTEGER);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_CABECERA_ENTRADAS_DESCENTRALIZADAS_LISTA_MSGERR, Types.VARCHAR);
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					EntradasCatalogoEstado ret = null;
					try {
						cs.execute();
						ret = obtenerEntradasCatalogoEstado(cs, POSICION_PARAMETRO_SALIDA_CONSULTA_CABECERA_ENTRADAS_DESCENTRALIZADAS_LISTA, POSICION_PARAMETRO_SALIDA_CONSULTA_CABECERA_ENTRADAS_DESCENTRALIZADAS_CODERR, POSICION_PARAMETRO_SALIDA_CONSULTA_CABECERA_ENTRADAS_DESCENTRALIZADAS_LISTA_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (EntradasCatalogoEstado)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return salida;
	}

	@Override
	public EntradaCatalogo cargarAllLineasEntrada(final Entrada entrada) {
		// TODO Auto-generated method stub
		EntradaCatalogo salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_OBT_ENTRADA_TOTAL(?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(entrada.getCodLoc().toString()));

						if (entrada.getCodCabPedido() != null){
							cs.setLong(2, entrada.getCodCabPedido());
						}else{							
							cs.setNull(2, OracleTypes.INTEGER);
						}
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_LISTA_ENTRADA_LINEAS_LISTA, OracleTypes.ARRAY, "APR_T_R_MIS_ENTRADA");
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_LISTA_ENTRADA_LINEAS_CODERR, Types.INTEGER);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_LISTA_ENTRADA_LINEAS_MSGERR, Types.VARCHAR);
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					EntradaCatalogo ret = null;
					try {
						cs.execute();
						ret = obtenerLineasEntrada(cs, POSICION_PARAMETRO_SALIDA_CONSULTA_LISTA_ENTRADA_LINEAS_LISTA, POSICION_PARAMETRO_SALIDA_CONSULTA_LISTA_ENTRADA_LINEAS_CODERR, POSICION_PARAMETRO_SALIDA_CONSULTA_LISTA_ENTRADA_LINEAS_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (EntradaCatalogo)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return salida;
	}

	@Override
	public EntradaCatalogo actualizarEntrada(final Entrada entrada) {
		// TODO Auto-generated method stub
		EntradaCatalogo salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_ACTUALIZAR_ENTRADA(?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(entrada.getCodLoc().toString()));

						//Crear estructura para actualización
						ARRAY itemConsulta = crearEstructuraActualizacionEntrada(entrada, con);

						cs.setArray(POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_LISTA,itemConsulta);

						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_LISTA, OracleTypes.ARRAY, "APR_T_R_MIS_ENTRADA");
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_CODERR,Types.INTEGER);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_MSGERR,Types.VARCHAR);
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					EntradaCatalogo ret = null;
					try {
						cs.execute();
						ret = obtenerEntradaActualizada(cs, POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_LISTA, POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_CODERR, POSICION_PARAMETRO_SALIDA_ACTUALIZAR_ENTRADA_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (EntradaCatalogo)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return salida;
	}

	@Override
	public EntradaFinalizar finalizarEntrada(final Entrada entrada) {
		// TODO Auto-generated method stub
		EntradaFinalizar salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_ENTRADAS_DESCEN_MISUMI.P_APR_FINALIZAR_ENTRADA(?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(entrada.getCodLoc().toString()));

						if (entrada.getCodCabPedido() != null){
							cs.setLong(2, entrada.getCodCabPedido());
						}else{							
							cs.setNull(2, OracleTypes.INTEGER);
						}
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_FINALIZAR_ENTRADA_CODERR,Types.INTEGER);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_FINALIZAR_ENTRADA_MSGERR,Types.VARCHAR);
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					EntradaFinalizar ret = null;
					try {
						cs.execute();
						ret = obtenerEntradaFinalizar(cs, POSICION_PARAMETRO_SALIDA_FINALIZAR_ENTRADA_CODERR, POSICION_PARAMETRO_SALIDA_FINALIZAR_ENTRADA_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (EntradaFinalizar)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return salida;
	} 

	@Override
	public EntradaAvisos cargarAvisosEntradas(final Long codLoc) throws Exception {

		EntradaAvisos salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_ENTRADAS_DESCEN_MISUMI.P_AVISOS_ENTRADA_PEND(?,?,?,?,?,?,?)}");
						cs.setInt(1, Integer.parseInt(codLoc.toString()));
						
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGPENDDARENTRADA,Types.VARCHAR);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGFRESCOS,Types.VARCHAR);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGALIMENTACION,Types.VARCHAR);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGNOALI,Types.VARCHAR);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_CODERR,Types.INTEGER);
						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_MSGERR,Types.VARCHAR);


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					EntradaAvisos ret = null;
					try {
						cs.execute();
						ret = obtenerEntradaAvisos(cs, 
								POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGPENDDARENTRADA,
								POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGFRESCOS,
								POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGALIMENTACION,
								POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_FLGNOALI,
								POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_CODERR,
								POSICION_PARAMETRO_SALIDA_AVISOS_ENTRADA_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (EntradaAvisos) this.jdbcTemplate.execute(csCreator,csCallback);
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

	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/***********************************           OBTENER DATOS PROCEDIMIENTOS       ***********************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/

	//Obtiene un objeto de EntradaCatalogo. Este objeto contiene una lista con las descripciones de las entradas, un código de error y una descripción del error.
	private EntradaCatalogo obtenerDenominacionesComboEntradas(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsEntrada = null;
		List<Entrada> listaEntrada = new ArrayList<Entrada>();
		EntradaCatalogo entradaCombo = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de EntradaCombo
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
			//Obtención de las entradas de la lista 
			ARRAY entradaLst = (ARRAY)cs.getObject(idParametroResultado1);
			if (entradaLst != null){
				rsEntrada  = entradaLst.getResultSet();
				int rowNumEntrada = 0;

				//Recorrido de la lista de Entradas
				while (rsEntrada.next()) {						
					STRUCT estructuraEntrada= (STRUCT)rsEntrada.getObject(2);
					Entrada entrada = this.mapRowEntrada(estructuraEntrada, rowNumEntrada);

					listaEntrada.add(entrada);
					rowNumEntrada ++;
				}
			}
		}
		entradaCombo = new EntradaCatalogo(listaEntrada, descError, codError);
		return entradaCombo;
	}

	//Obtiene un objeto de EntradasCatalogoEstado. Este objeto contiene una lista con la cantidad de entradas por estado.
	private EntradasCatalogoEstado obtenerEntradasCatalogoEstado(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsEntradaCatalogoEstado = null;
		List<EntradaEstado> listaEntradaEstado = new ArrayList<EntradaEstado>();
		EntradasCatalogoEstado entradasCatalogoEstado = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de EntradasCatalogoEstado
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
			//Obtención de las entradas de la lista 
			ARRAY entradaEstadoLst = (ARRAY)cs.getObject(idParametroResultado1);
			if (entradaEstadoLst != null){
				rsEntradaCatalogoEstado  = entradaEstadoLst.getResultSet();
				int rowNumEntrada = 0;

				//Recorrido de la lista de catalogos de estado
				while (rsEntradaCatalogoEstado.next()) {						
					STRUCT estructuraEntradaEstado= (STRUCT)rsEntradaCatalogoEstado.getObject(2);
					EntradaEstado entradaEstado = this.mapRowEntradaEstado(estructuraEntradaEstado);

					listaEntradaEstado.add(entradaEstado);
					rowNumEntrada ++;
				}
			}
		}
		entradasCatalogoEstado = new EntradasCatalogoEstado(listaEntradaEstado, descError, codError);
		return entradasCatalogoEstado;
	}

	//Obtiene una lista de entradas. Este objeto contiene una lista con una entrada que contiene las líneas de la entrada buscada un código de error y una descripción del error.
	private EntradaCatalogo obtenerLineasEntrada(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsEntrada = null;
		List<Entrada> listaEntrada = new ArrayList<Entrada>();
		EntradaCatalogo entradaCatalogo = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de EntradaCombo
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
			//Obtención de las entradas de la lista 
			ARRAY entradaLst = (ARRAY)cs.getObject(idParametroResultado1);
			if (entradaLst != null){
				rsEntrada  = entradaLst.getResultSet();
				int rowNumEntrada = 0;

				//Recorrido de la lista de Entradas
				while (rsEntrada.next()) {						
					STRUCT estructuraEntrada= (STRUCT)rsEntrada.getObject(2);
					Entrada entrada = this.mapRowEntrada(estructuraEntrada, rowNumEntrada);

					listaEntrada.add(entrada);
					rowNumEntrada ++;
				}
			}
		}
		entradaCatalogo = new EntradaCatalogo(listaEntrada, descError, codError);
		return entradaCatalogo;
	}

	//Obtiene una entrada actualizada. Este objeto contiene una lista con una entrada actualizada un código de error y una descripción del error.
	private EntradaCatalogo obtenerEntradaActualizada(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsEntrada = null;
		List<Entrada> listaEntrada = new ArrayList<Entrada>();
		EntradaCatalogo entradaCatalogo = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de EntradaCombo
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
			//Obtención de las entradas de la lista 
			ARRAY entradaLst = (ARRAY)cs.getObject(idParametroResultado1);
			if (entradaLst != null){
				rsEntrada  = entradaLst.getResultSet();
				int rowNumEntrada = 0;

				//Recorrido de la lista de Entradas
				while (rsEntrada.next()) {						
					STRUCT estructuraEntrada= (STRUCT)rsEntrada.getObject(2);
					Entrada entrada = this.mapRowEntrada(estructuraEntrada, rowNumEntrada);

					listaEntrada.add(entrada);
					rowNumEntrada ++;
				}
			}
		}
		entradaCatalogo = new EntradaCatalogo(listaEntrada, descError, codError);
		return entradaCatalogo;
	}

	//Obtiene un código de error y una descripción para finalizar una entrada.
	private EntradaFinalizar obtenerEntradaFinalizar(CallableStatement cs,int idParametroResultado1,int idParametroResultado2) throws SQLException{
		EntradaFinalizar entradaFinalizar = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);

		//Transformación de datos para estructura de EntradaCombo
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;
		
		entradaFinalizar = new EntradaFinalizar(codError, descError);
		
		return entradaFinalizar;
	}
	
	//Obtiene los avisos de las entradas
	private EntradaAvisos obtenerEntradaAvisos(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3,int idParametroResultado4,int idParametroResultado5,int idParametroResultado6) throws SQLException{
		EntradaAvisos entradaAvisos = null;

		//Obtención de los parámetros de salida en crudo
		String flgPendienteEntrada_BD = (String)cs.getString(idParametroResultado1);
		String flgFrescos_BD = (String)cs.getString(idParametroResultado2);
		String flgAlimentacion_BD = (String)cs.getString(idParametroResultado3);
		String flgNoAlimentacion_BD = (String)cs.getString(idParametroResultado4);
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado5);
		String descError_BD = (String)cs.getString(idParametroResultado6);

		//Transformación de datos para estructura de EntradaAvisos
		String flgPendienteEntrada = flgPendienteEntrada_BD;
		String flgFrescos = flgFrescos_BD;
		String flgAlimentacion = flgAlimentacion_BD;
		String flgNoAlimentacion = flgNoAlimentacion_BD;
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;
		
		entradaAvisos = new EntradaAvisos(flgPendienteEntrada,flgFrescos,flgAlimentacion,flgNoAlimentacion,codError,descError);
		
		return entradaAvisos;
	}
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/***********************************           MAPROW DE LOS PROCEDIMIENTOS       ***********************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/

	//Obtiene una Entrada
	private EntradaEstado mapRowEntradaEstado(STRUCT estructuraEntradaEstado) throws SQLException {
		//Iniciar variables
		List<Entrada> lstEntrada = new ArrayList<Entrada>();
		ResultSet rsEntrada = null;
		EntradaEstado entradaEstado = null;

		//Obtener datos de EntradaEstado en crudo
		Object[] objectInfo = estructuraEntradaEstado.getAttributes();

		BigDecimal numeroEntradas_BD = (BigDecimal)objectInfo[0];
		BigDecimal estado_BD = (BigDecimal)objectInfo[1];

		Long numeroEntradas = ((numeroEntradas_BD != null && !("".equals(numeroEntradas_BD.toString())))?new Long(numeroEntradas_BD.toString()):null);
		Long estado = ((estado_BD != null && !("".equals(estado_BD.toString())))?new Long(estado_BD.toString()):null);

		ARRAY entradaLst = (ARRAY)objectInfo[2];

		if (entradaLst != null){
			rsEntrada = entradaLst.getResultSet();
			int rowNumEntrada = 0;

			//Recorrido de la lista de Entrada
			while (rsEntrada.next()) {						
				STRUCT estructuraEntrada = (STRUCT)rsEntrada.getObject(2);
				Entrada entrada = this.mapRowEntrada(estructuraEntrada, rowNumEntrada);

				lstEntrada.add(entrada);
				rowNumEntrada ++;
			}
			entradaEstado = new EntradaEstado(estado,numeroEntradas,lstEntrada);		
		}
		return entradaEstado;
	}

	//Obtiene una Entrada
	private Entrada mapRowEntrada(STRUCT estructuraEntrada, int rowNumEntrada) throws SQLException {
		//Iniciar variables
		List<EntradaLinea> lstEntradaLinea = new ArrayList<EntradaLinea>();
		ResultSet rsEntradaLinea = null;
		Entrada entrada = null;

		//Obtener datos de Entrada en crudo
		Object[] objectInfo = estructuraEntrada.getAttributes();

		//BigDecimal codLoc_BD = (BigDecimal)objectInfo[0];
		Timestamp fechaEntrada_BD = (Timestamp)objectInfo[0];
		BigDecimal codCabPedido_BD = (BigDecimal)objectInfo[1];
		BigDecimal codProvGen_BD = (BigDecimal)objectInfo[2];
		BigDecimal codProvTrab_BD = (BigDecimal)objectInfo[3];
		String denomProvTrab_BD = (String)objectInfo[4] ;
		String codAlbProv_BD = (String)objectInfo[5];
		BigDecimal numIncidencia_BD = (BigDecimal)objectInfo[6];
		Timestamp fechaTarifa_BD = (Timestamp)objectInfo[7] ;
		BigDecimal estado_BD = (BigDecimal)objectInfo[8];

		//Long codLoc = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
		Date fechaEntrada = ((fechaEntrada_BD != null )?new Date(fechaEntrada_BD.getTime()):null);
		Long codCabPedido = ((codCabPedido_BD != null && !("".equals(codCabPedido_BD.toString())))?new Long(codCabPedido_BD.toString()):null);
		Long codProvGen = ((codProvGen_BD != null && !("".equals(codProvGen_BD.toString())))?new Long(codProvGen_BD.toString()):null);
		Long codProvTrab = ((codProvTrab_BD != null && !("".equals(codProvTrab_BD.toString())))?new Long(codProvTrab_BD.toString()):null);
		String denomProvTrab = denomProvTrab_BD;
		String codAlbProv = codAlbProv_BD;
		Long numIncidencia = ((numIncidencia_BD != null && !("".equals(numIncidencia_BD.toString())))?new Long(numIncidencia_BD.toString()):null);
		Date fechaTarifa = ((fechaTarifa_BD != null )?new Date(fechaTarifa_BD.getTime()):null);
		Long estado = ((estado_BD != null && !("".equals(estado_BD.toString())))?new Long(estado_BD.toString()):null);

		ARRAY entradaLineaLst = (ARRAY)objectInfo[9];

		if (entradaLineaLst != null){
			rsEntradaLinea = entradaLineaLst.getResultSet();
			int rowNumEntradaLinea = 0;

			//Recorrido de la lista de EntradaLinea
			while (rsEntradaLinea.next()) {						
				STRUCT estructuraEntradaLinea = (STRUCT)rsEntradaLinea.getObject(2);
				EntradaLinea entradaLinea = this.mapRowEntradaLinea(estructuraEntradaLinea, rowNumEntradaLinea);

				lstEntradaLinea.add(entradaLinea);
				rowNumEntradaLinea ++;
			}
			entrada = new Entrada(null, fechaEntrada,  codCabPedido, codProvGen, codProvTrab, denomProvTrab, codAlbProv, numIncidencia, fechaTarifa,  estado, lstEntradaLinea);			
		}
		return entrada;
	}

	//Obtiene una EntradaLinea
	private EntradaLinea mapRowEntradaLinea(STRUCT estructuraEntradaLinea, int rowNumEntrada) throws SQLException {
		//Iniciar variables
		EntradaLinea entradaLinea = null;

		//Obtener datos de EntradaLinea en crudo
		Object[] objectInfo = estructuraEntradaLinea.getAttributes();

		BigDecimal codArticulo_BD = (BigDecimal)objectInfo[0];
		String denomCodArticulo_BD = (String)objectInfo[1] ;
		BigDecimal numeroCajasPedidas_BD = (BigDecimal)objectInfo[2];
		BigDecimal numeroCajasRecepcionadas_BD = (BigDecimal)objectInfo[3];
		BigDecimal uc_BD = (BigDecimal)objectInfo[4];
		BigDecimal totalBandejasPedidas_BD = (BigDecimal)objectInfo[5];
		BigDecimal totalBandejasRecepcionadas_BD = (BigDecimal)objectInfo[6];
		BigDecimal totalUnidadesPedidas_BD = (BigDecimal)objectInfo[7] ;
		BigDecimal totalUnidadesRecepcionadas_BD = (BigDecimal)objectInfo[8];

		Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
		String denomCodArticulo = denomCodArticulo_BD ;
		Long numeroCajasPedidas = ((numeroCajasPedidas_BD != null && !("".equals(numeroCajasPedidas_BD.toString())))?new Long(numeroCajasPedidas_BD.toString()):null);
		Long numeroCajasRecepcionadas = ((numeroCajasRecepcionadas_BD != null && !("".equals(numeroCajasRecepcionadas_BD.toString())))?new Long(numeroCajasRecepcionadas_BD.toString()):null);
		Long uc = ((uc_BD != null && !("".equals(uc_BD.toString())))?new Long(uc_BD.toString()):null);
		Long totalBandejasPedidas = ((totalBandejasPedidas_BD != null && !("".equals(totalBandejasPedidas_BD.toString())))?new Long(totalBandejasPedidas_BD.toString()):null);
		Long totalBandejasRecepcionadas = ((totalBandejasRecepcionadas_BD != null && !("".equals(totalBandejasRecepcionadas_BD.toString())))?new Long(totalBandejasRecepcionadas_BD.toString()):null);
		Double totalUnidadesPedidas = ((totalUnidadesPedidas_BD != null && !("".equals(totalUnidadesPedidas_BD.toString())))?new Double(totalUnidadesPedidas_BD.toString()):null);
		Double totalUnidadesRecepcionadas = ((totalUnidadesRecepcionadas_BD != null && !("".equals(totalUnidadesRecepcionadas_BD.toString())))?new Double(totalUnidadesRecepcionadas_BD.toString()):null);

		entradaLinea = new EntradaLinea(codArticulo, denomCodArticulo, numeroCajasPedidas, numeroCajasRecepcionadas, uc, totalBandejasPedidas, totalBandejasRecepcionadas, totalUnidadesPedidas, totalUnidadesRecepcionadas);

		return entradaLinea;
	}

	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/*********************************                CREAR ESTRUCTURAS            **************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	//CREA OBJETO APR_T_R_MIS_ENTRADA
	private ARRAY crearEstructuraActualizacionEntrada(Entrada entrada, Connection con) throws SQLException {
		OracleConnection conexionOracle = (OracleConnection)con.getMetaData().getConnection();

		//ARRAY de entradas, en este caso solo habrá 1
		Object[] objectTablaEntradas = new Object[1];

		//Objeto de entrada que contiene 10 elementos
		Object[] objectInfo = new Object[10];
		objectInfo[0] = (entrada.getFechaEntrada()!=null?new Timestamp(entrada.getFechaEntrada().getTime()):null);
		objectInfo[1] = entrada.getCodCabPedido();
		objectInfo[2] = entrada.getCodProvGen();
		objectInfo[3] = entrada.getCodProvTrab();
		objectInfo[4] = entrada.getDenomProvTrab();
		objectInfo[5] = entrada.getCodAlbProv();
		objectInfo[6] = entrada.getNumIncidencia();
		objectInfo[7] = (entrada.getFechaTarifa() !=null?new Timestamp(entrada.getFechaTarifa().getTime()):null);
		objectInfo[8] = entrada.getTipoRecepcion();
		objectInfo[9] = crearEstructuraActualizacionEntradaLinea(entrada, conexionOracle);

		//CREA APR_R_MIS_ENTRADA
		StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_MIS_ENTRADA",conexionOracle);
		STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

		objectTablaEntradas[0] = itemObjectStruct;

		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_MIS_ENTRADA", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaEntradas);

		return array;
	}

	//CREA OBJETO APR_T_R_MIS_ENTRADA_LINEAS
	private ARRAY crearEstructuraActualizacionEntradaLinea(Entrada entrada, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de lineas de entrada. Su tamaño será el de la lista de entradas
		Object[] objectTablaDevolucionLineas = new Object[entrada.getLstEntradaLinea().size()];

		int i= 0;
		for(EntradaLinea entradaLinea:entrada.getLstEntradaLinea()){
			//Objeto de DevolucionLinea que contiene 33 elementos
			Object[] objectInfo = new Object[9];

			//Rellenamos el objeto con la línea de devolución
			objectInfo[0] = entradaLinea.getCodArticulo();
			objectInfo[1] = entradaLinea.getDenomCodArticulo();
			objectInfo[2] = entradaLinea.getNumeroCajasPedidas();
			objectInfo[3] = entradaLinea.getNumeroCajasRecepcionadas();
			objectInfo[4] = entradaLinea.getUc();
			objectInfo[5] = entradaLinea.getTotalBandejasPedidas();
			objectInfo[6] = entradaLinea.getTotalBandejasRecepcionadas();
			objectInfo[7] = entradaLinea.getTotalUnidadesPedidas();
			objectInfo[8] = entradaLinea.getTotalUnidadesRecepcionadas();

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_MIS_ENTRADA_LINEAS",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTablaDevolucionLineas[i] = itemObjectStruct;

			//Actualizamos el índice del objeto
			i++;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_MIS_ENTRADA_LINEAS", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaDevolucionLineas);

		return array;
	}
}
