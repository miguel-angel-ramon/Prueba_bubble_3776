package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.ListadoReposicionDaoSIA;
import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionCmbSeccion;
import es.eroski.misumi.model.ReposicionDatosTalla;
import es.eroski.misumi.model.ReposicionGuardar;
import es.eroski.misumi.model.ReposicionHayDatos;
import es.eroski.misumi.model.ReposicionLinea;
import es.eroski.misumi.model.ReposicionSeccion;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;


@Repository
public class ListadoReposicionDaoSIAImpl implements ListadoReposicionDaoSIA {

	private JdbcTemplate jdbcTemplate;

	//Posiciones PLSQL de salida de PK_APR_LISTADO_REPO_MISUMI.P_APR_MIS_HAY_DATOS	
	private static final int POSICION_PARAMETRO_SALIDA_HAY_DATOS_REPOSICION_HAY_DATOS = 5;
	private static final int POSICION_PARAMETRO_SALIDA_HAY_DATOS_REPOSICION_CODERR = 6;
	private static final int POSICION_PARAMETRO_SALIDA_HAY_DATOS_REPOSICION_MSGERR = 7;

	//Posiciones PLSQL de salida de PK_APR_LISTADO_REPO_MISUMI.P_APR_MIS_HAY_DATOS	
	private static final int POSICION_PARAMETRO_SALIDA_BORRAR_REPOSICION_CODERR = 8;
	private static final int POSICION_PARAMETRO_SALIDA_BORRAR_REPOSICION_MSGERR = 9;

	//Posiciones PLSQL de salida de PK_APR_LISTADO_REPO_MISUMI.P_APR_MIS_GUARDAR_ART_REPO
	private static final int POSICION_PARAMETRO_SALIDA_GUARDAR_REPOSICION_CODERR = 11;
	private static final int POSICION_PARAMETRO_SALIDA_GUARDAR_REPOSICION_MSGERR = 12;

	//Posiciones PLSQL de salida de PK_APR_LISTADO_REPO_MISUMI.P_APR_MIS_FINALIZAR_TAREA	
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_SECCIONES_REPOSICION_SECCIONES_LISTA = 5;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_SECCIONES_REPOSICION_CODERR = 6;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_SECCIONES_REPOSICION_MSGERR = 7;

	//Posiciones PLSQL de salida de PK_APR_LISTADO_REPO_MISUMI.P_APR_MIS_FINALIZAR_TAREA	
	private static final int POSICION_PARAMETRO_SALIDA_FINALIZAR_TAREA_REPOSICION_CODERR = 5;
	private static final int POSICION_PARAMETRO_SALIDA_FINALIZAR_TAREA_REPOSICION_MSGERR = 6;

	//Posiciones PLSQL de salida de PK_APR_LISTADO_REPO_MISUMI.p_apr_mis_obtener_repo
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_MODELO_PROVEEDOR = 4; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_DENOMINACION = 5; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_DESCR_COLO = 6; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_FOTO = 7; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_ESTADO = 8; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_REPOSICION_LISTA = 9; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_PAGINAS_TOTALES = 10;  
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_POSICION = 11; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_FLGSIGPOSVACIA = 12; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_REVISADA = 13;  
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_SUSTITUIDA = 14; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_AREA = 15;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_SECCION = 16;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_CODERR = 17;  
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_MSGERR = 18; 

	//Posiciones PLSQL de salida de PK_APR_LISTADO_REPO_MISUMI.p_apr_mis_datos_talla
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_REF = 3;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_PROV = 4;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_ESTR = 5;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_MOD = 6;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_SONAE = 7; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_CODERROR = 8; 
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_DESCERROR = 9;



	private static Logger logger = Logger.getLogger(DevolucionDaoSIAImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	} 




	private RowMapper<ReposicionLinea> rwDetPedidoMap= new RowMapper<ReposicionLinea>() {
		public ReposicionLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ReposicionLinea reposicionLinea = new ReposicionLinea();

			reposicionLinea.setCodLoc(resultSet.getLong("COD_CENTRO"));
			reposicionLinea.setCodMac(resultSet.getString("MAC"));
			reposicionLinea.setModeloProveedor(resultSet.getString("MODELO_PROVEEDOR"));
			reposicionLinea.setDescrColor(resultSet.getString("COLOR"));
			reposicionLinea.setCodArticulo(resultSet.getLong("COD_ART"));
			Double repo=resultSet.getDouble("REPO");
			reposicionLinea.setCantRepo(repo.toString());
			Double stock=resultSet.getDouble("STOCK");
			reposicionLinea.setStock(stock.toString());
			reposicionLinea.setFlgPantCorrStock(resultSet.getString("FLG_PANT_CORR_STOCK"));
			reposicionLinea.setDescrTalla(resultSet.getString("TALLA"));
			reposicionLinea.setSubPosicion(resultSet.getLong("SUB_POSICION"));

			return reposicionLinea;
		}

	};


	/***********************************          PK_APR_LISTADO_REPO_MISUMI          ***********************************/

	@Override
	public ReposicionHayDatos hayDatosReposicion(final Reposicion reposicion) throws Exception {

		ReposicionHayDatos salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_LISTADO_REPO_MISUMI.p_apr_mis_hay_datos(?,?,?,?,?,?,?) }");
						cs.setLong(1, reposicion.getCodLoc());
						cs.setString(2, reposicion.getCodMac());
						cs.setLong(3, reposicion.getTipoListado());

						if (reposicion.getArea() != null && !reposicion.getArea().equals("")){
							cs.setString(4,reposicion.getArea());
						}else{
							cs.setNull(4, OracleTypes.NULL);
						}				
						cs.registerOutParameter(5,Types.VARCHAR);
						cs.registerOutParameter(6,Types.INTEGER);
						cs.registerOutParameter(7, Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					ReposicionHayDatos ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerReposicionHayDatos(cs, POSICION_PARAMETRO_SALIDA_HAY_DATOS_REPOSICION_HAY_DATOS, POSICION_PARAMETRO_SALIDA_HAY_DATOS_REPOSICION_CODERR, POSICION_PARAMETRO_SALIDA_HAY_DATOS_REPOSICION_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (ReposicionHayDatos) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public Reposicion borrarReposicion(final Reposicion reposicion) throws Exception {

		Reposicion salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_LISTADO_REPO_MISUMI.p_apr_mis_borrar_repo(?,?,?,?,?,?,?,?,?) }");
						cs.setLong(1, reposicion.getCodLoc());
						cs.setString(2, reposicion.getCodMac());
						cs.setLong(3, reposicion.getTipoListado());


						if (reposicion.getModeloProveedor() != null && !reposicion.getModeloProveedor().equals("")){
							cs.setString(4, reposicion.getModeloProveedor());
						}else{
							cs.setNull(4, OracleTypes.NULL);
						}
						if (reposicion.getDescrColor() != null && !reposicion.getDescrColor().equals("")){
							cs.setString(5, reposicion.getDescrColor());
						}else{
							cs.setNull(5, OracleTypes.NULL);
						}
						if (reposicion.getCodArt() != null){
							cs.setLong(6, reposicion.getCodArt());
						}else{							
							cs.setNull(6, OracleTypes.INTEGER);
						}

						if (reposicion.getArea() != null && !reposicion.getArea().equals("")){
							cs.setString(7,reposicion.getArea());
						}else{
							cs.setNull(7, OracleTypes.NULL);
						}	

						cs.registerOutParameter(8,Types.INTEGER);
						cs.registerOutParameter(9, Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					Reposicion ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerBorrarReposicion(cs, POSICION_PARAMETRO_SALIDA_BORRAR_REPOSICION_CODERR, POSICION_PARAMETRO_SALIDA_BORRAR_REPOSICION_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (Reposicion) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public ReposicionGuardar guardarReposicion(final ReposicionGuardar reposicionGuardar) throws Exception {

		ReposicionGuardar salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_LISTADO_REPO_MISUMI.p_apr_mis_guardar_art_repo(?,?,?,?,?,?,?,?,?,?,?,?) }");
						cs.setLong(1, reposicionGuardar.getCodLoc());
						cs.setString(2, reposicionGuardar.getCodMac());
						cs.setLong(3, reposicionGuardar.getTipoListado());


						if (reposicionGuardar.getModeloProveedor() != null && !reposicionGuardar.getModeloProveedor().equals("")){
							cs.setString(4, reposicionGuardar.getModeloProveedor());
						}else{
							cs.setNull(4, OracleTypes.NULL);
						}
						if (reposicionGuardar.getCodArt() != null){
							cs.setLong(5, reposicionGuardar.getCodArt());
						}else{							
							cs.setNull(5, OracleTypes.INTEGER);
						}
						if (reposicionGuardar.getDescrColor() != null && !reposicionGuardar.getDescrColor().equals("")){
							cs.setString(6, reposicionGuardar.getDescrColor());
						}else{
							cs.setNull(6, OracleTypes.NULL);
						}


						if (reposicionGuardar.getCantRepo() != null){
							cs.setDouble(7, reposicionGuardar.getCantRepo());
						}else{							
							cs.setNull(7, OracleTypes.DOUBLE);
						}

						if (reposicionGuardar.getFlgRevisada() != null && !reposicionGuardar.getFlgRevisada().equals("")){
							cs.setString(8, reposicionGuardar.getFlgRevisada());
						}else{
							cs.setNull(8, OracleTypes.NULL);
						}
						if (reposicionGuardar.getFlgSustituida() != null && !reposicionGuardar.getFlgSustituida().equals("")){
							cs.setString(9, reposicionGuardar.getFlgSustituida());
						}else{
							cs.setNull(9, OracleTypes.NULL);
						}

						if (reposicionGuardar.getArea() != null && !reposicionGuardar.getArea().equals("")){
							cs.setString(10,reposicionGuardar.getArea());
						}else{
							cs.setNull(10, OracleTypes.NULL);
						}	

						cs.registerOutParameter(11, Types.INTEGER);
						cs.registerOutParameter(12, Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					ReposicionGuardar ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerGuardarReposicion(cs, POSICION_PARAMETRO_SALIDA_GUARDAR_REPOSICION_CODERR, POSICION_PARAMETRO_SALIDA_GUARDAR_REPOSICION_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (ReposicionGuardar) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public Reposicion finalizarTareaReposicion(final Reposicion reposicion) throws Exception {

		Reposicion salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_LISTADO_REPO_MISUMI.p_apr_mis_finalizar_tarea(?,?,?,?,?,?) }");
						cs.setLong(1, reposicion.getCodLoc());
						cs.setString(2, reposicion.getCodMac());
						cs.setLong(3, reposicion.getTipoListado());

						if (reposicion.getArea() != null && !reposicion.getArea().equals("")){
							cs.setString(4,reposicion.getArea());
						}else{
							cs.setNull(4, OracleTypes.NULL);
						}	

						cs.registerOutParameter(5,Types.INTEGER);
						cs.registerOutParameter(6, Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					Reposicion ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerFinalizarTareaReposicion(cs, POSICION_PARAMETRO_SALIDA_FINALIZAR_TAREA_REPOSICION_CODERR, POSICION_PARAMETRO_SALIDA_FINALIZAR_TAREA_REPOSICION_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (Reposicion) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public Reposicion obtenerReposicion(final Reposicion reposicion) throws Exception {

		Reposicion salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	   


						ARRAY itemConsulta = ListadoReposicionDaoSIAImpl.this.crearEstructuraReposicionLinea(reposicion, con);

						cs = con.prepareCall("{call PK_APR_LISTADO_REPO_MISUMI.p_apr_mis_obtener_repo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");

						//Parametros IN

						cs.setLong(1, reposicion.getCodLoc());
						cs.setString(2, reposicion.getCodMac());
						cs.setLong(3, reposicion.getTipoListado());

						cs.setArray(9, itemConsulta);

						if (reposicion.getPosicion() != null && !reposicion.getPosicion().equals("")){
							cs.setLong(11, reposicion.getPosicion());
						}else{
							cs.setNull(11, OracleTypes.INTEGER);
						}

						if (reposicion.getArea() != null && !reposicion.getArea().equals("")){
							cs.setString(15,reposicion.getArea());
						}else{
							cs.setNull(15, OracleTypes.NULL);
						}	

						if (reposicion.getFlgSigPosVacia() != null && !reposicion.getFlgSigPosVacia().equals("")){
							cs.setString(12,reposicion.getFlgSigPosVacia());
						}else{
							cs.setNull(12, OracleTypes.NULL);
						}	

						if(reposicion.getSeccion() != null && !reposicion.getSeccion().equals("")){
							cs.setString(16, reposicion.getSeccion()); //Seccion	
						}else{
							cs.setNull(16, OracleTypes.NULL);
						}

						//Parametros OUT 
						cs.registerOutParameter(4,Types.VARCHAR); //Modelo Proveedor
						cs.registerOutParameter(5,Types.VARCHAR); //Denominacion
						cs.registerOutParameter(6,Types.VARCHAR); //DescrColor
						cs.registerOutParameter(7,Types.INTEGER); //codArticuloFoto 
						cs.registerOutParameter(8,Types.INTEGER); //Estado
						cs.registerOutParameter(9,OracleTypes.ARRAY, "APR_T_R_LISTADO_REPO");
						cs.registerOutParameter(10,Types.INTEGER); //Paginas Totales
						cs.registerOutParameter(11,Types.INTEGER); //Posicion
						cs.registerOutParameter(12, Types.VARCHAR); //Sig pos vacía
						cs.registerOutParameter(13,Types.VARCHAR); //Revisada
						cs.registerOutParameter(14,Types.VARCHAR); //Sustituida
						cs.registerOutParameter(15,Types.VARCHAR); //Area		
						cs.registerOutParameter(16,Types.VARCHAR); //Sección	
						cs.registerOutParameter(17,Types.INTEGER); //Cod Error
						cs.registerOutParameter(18, Types.VARCHAR); //Descr Error

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					Reposicion ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerObtenerReposicion(cs, POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_MODELO_PROVEEDOR, POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_DENOMINACION,
								POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_DESCR_COLO, POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_FOTO,
								POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_ESTADO, POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_REPOSICION_LISTA,
								POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_PAGINAS_TOTALES, POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_POSICION,POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_FLGSIGPOSVACIA,
								POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_REVISADA, POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_SUSTITUIDA,POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_AREA, POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_SECCION,
								POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_CODERR, POSICION_PARAMETRO_SALIDA_OBTENER_REPOSICION_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (Reposicion) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		//Informamos el COD_LOC, MAC y Tipo de Listado porque el procedimiento no lo devuelve
		salida.setCodLoc(reposicion.getCodLoc());
		salida.setCodMac(reposicion.getCodMac());
		salida.setTipoListado(reposicion.getTipoListado());
		return salida;
	}

	@Override
	public ReposicionCmbSeccion obtenerSecciones(final Reposicion reposicion) throws Exception {
		// TODO Auto-generated method stub
		ReposicionCmbSeccion salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_LISTADO_REPO_MISUMI.p_apr_mis_secciones(?,?,?,?,?,?,?) }");
						cs.setLong(1, reposicion.getCodLoc());
						cs.setString(2, reposicion.getCodMac());
						cs.setLong(3, reposicion.getTipoListado());

						if (reposicion.getArea() != null && !reposicion.getArea().equals("")){
							cs.setString(4,reposicion.getArea());
						}else{
							cs.setNull(4, OracleTypes.NULL);
						}	
						cs.registerOutParameter(5,OracleTypes.ARRAY, "APR_T_R_LISTADO_REPO_SECC");
						cs.registerOutParameter(6,Types.INTEGER);
						cs.registerOutParameter(7, Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					ReposicionCmbSeccion ret = null;
					ResultSet rs = null;
					try {
						cs.execute();

						ret = obtenerSeccionesReposicion(cs, POSICION_PARAMETRO_SALIDA_OBTENER_SECCIONES_REPOSICION_SECCIONES_LISTA, POSICION_PARAMETRO_SALIDA_OBTENER_SECCIONES_REPOSICION_CODERR, POSICION_PARAMETRO_SALIDA_OBTENER_SECCIONES_REPOSICION_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (ReposicionCmbSeccion) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public ReposicionDatosTalla obtenerDatosAdicionalesTalla(final Reposicion reposicion) throws Exception {
		// TODO Auto-generated method stub
		ReposicionDatosTalla salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_LISTADO_REPO_MISUMI.p_apr_mis_datos_talla(?,?,?,?,?,?,?,?,?) }");
						cs.setLong(1, reposicion.getCodLoc());
						cs.setString(2, reposicion.getCodMac());
						cs.setLong(3, reposicion.getCodArt());


						cs.registerOutParameter(3,Types.INTEGER);
						cs.registerOutParameter(4, Types.VARCHAR);
						cs.registerOutParameter(5, Types.VARCHAR);
						cs.registerOutParameter(6, Types.VARCHAR);
						cs.registerOutParameter(7, Types.VARCHAR);
						cs.registerOutParameter(8, Types.INTEGER);
						cs.registerOutParameter(9, Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					ReposicionDatosTalla ret = null;
					ResultSet rs = null;
					try {
						cs.execute();

						ret = obtenerDatosAdicionalesTallaReposicion(cs, POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_REF, POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_PROV, POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_ESTR, POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_MOD, POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_SONAE,POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_CODERROR,POSICION_PARAMETRO_SALIDA_OBTENER_DATOS_ADICIONALES_TALLA_DESCERROR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (ReposicionDatosTalla) this.jdbcTemplate.execute(csCreator,csCallback);
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


	private ARRAY crearEstructuraReposicionLinea(Reposicion reposicion, Connection con) throws SQLException {

		OracleConnection conexionOracle = (OracleConnection)con.getMetaData().getConnection();

		Object[] objectTablaReposicionLineas = new Object[reposicion.getReposicionLineas() != null ? reposicion.getReposicionLineas().size() : 0];

		if(reposicion.getReposicionLineas() != null){
			int i = 0;
			for (ReposicionLinea reposicionLinea : reposicion.getReposicionLineas())
			{
				Object[] objectCantidadMaxima = new Object[5];

				objectCantidadMaxima[0] = reposicionLinea.getCodArticulo();

				objectCantidadMaxima[1] = reposicionLinea.getDescrTalla();

				objectCantidadMaxima[2] = reposicionLinea.getCantRepo();

				objectCantidadMaxima[3] = reposicionLinea.getStock();

				objectCantidadMaxima[4] = reposicionLinea.getFlgPantCorrStock();



				StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_LISTADO_REPO", conexionOracle);
				STRUCT itemObjectStruct = new STRUCT(itemDescriptor, conexionOracle, objectCantidadMaxima);

				objectTablaReposicionLineas[i] = itemObjectStruct;

				i++;
			}
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_LISTADO_REPO", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaReposicionLineas);

		return array;
	}


	//Obtiene un objeto de . Este objeto contiene una lista con las descripciones de las devoluciones, un código de error y una descripción del error.
	private Reposicion obtenerObtenerReposicion(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3,int idParametroResultado4,int idParametroResultado5,int idParametroResultado6,int idParametroResultado7,int idParametroResultado8,int idParametroResultado9,int idParametroResultado10,int idParametroResultado11,int idParametroResultado12,int idParametroResultado13, int idParametroResultado14, int idParametroResultado15) throws SQLException{
		//Iniciar variables
		ResultSet rsRepoLinea = null;
		List<ReposicionLinea> listaReposicionLinea = new ArrayList<ReposicionLinea>();
		Reposicion reposicion = null;

		//Obtención de los parámetros de salida en crudo
		String modeloProveedor_BD = (String)cs.getString(idParametroResultado1);
		String denominacion_BD = (String)cs.getString(idParametroResultado2);
		String descrColor_BD = (String)cs.getString(idParametroResultado3);
		BigDecimal codArtFoto_BD =  (BigDecimal)cs.getBigDecimal(idParametroResultado4);
		BigDecimal estado_BD =  (BigDecimal)cs.getBigDecimal(idParametroResultado5);

		Long paginasTotales_BD =  (Long)cs.getLong(idParametroResultado7);
		Long posicion_BD =  (Long)cs.getLong(idParametroResultado8);
		String flgSigPosVacia_BD = (String)cs.getString(idParametroResultado9);
		String revisada_BD = (String)cs.getString(idParametroResultado10);
		String sustituida_BD = (String)cs.getString(idParametroResultado11);
		String area_BD = (String)cs.getString(idParametroResultado12);
		String seccion_BD = (String)cs.getString(idParametroResultado13);
		
		Long codError_BD =  (Long)cs.getLong(idParametroResultado14);
		String descError_BD = (String)cs.getString(idParametroResultado15);


		//Transformación de datos para estructura de DevolucionCatalogoEstado
		String modeloProveedor 	= modeloProveedor_BD;
		String denominacion 	= denominacion_BD;
		String descrColor 		= descrColor_BD;
		Long codArtFoto   		= ((codArtFoto_BD != null && !("".equals(codArtFoto_BD.toString())))?new Long(codArtFoto_BD.toString()):null);
		Long estado   			= ((estado_BD != null && !("".equals(estado_BD.toString())))?new Long(estado_BD.toString()):null);
		Long paginasTotales 	= ((paginasTotales_BD != null && !("".equals(paginasTotales_BD.toString())))?new Long(paginasTotales_BD.toString()):null);
		Long posicion   		= ((posicion_BD != null && !("".equals(posicion_BD.toString())))?new Long(posicion_BD.toString()):null);
		String flgSigPosVacia = flgSigPosVacia_BD;
		String flgRevisada 		= revisada_BD;
		String flgSustituida 	= sustituida_BD;	
		String area = area_BD;
		String seccion = seccion_BD;
		Long codError   		= ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError 		= descError_BD;


		//Control de error en la obtención de datos
		if (new Long("0").equals(codError)){ //El proceso se ha ejecutado correctamente	
			//Obtención de las reposicioLinea de la lista 
			ARRAY reposicionLinesLst = (ARRAY)cs.getObject(idParametroResultado6);
			if (reposicionLinesLst != null){
				rsRepoLinea  = reposicionLinesLst.getResultSet();
				int rowNumRepoLinea = 0;

				//Recorrido de la lista de DevolucionEstado
				while (rsRepoLinea.next()) {						
					STRUCT estructuraDevolucionDescripcion= (STRUCT)rsRepoLinea.getObject(2); //¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿por que 2 ??????????????????????
					ReposicionLinea reposicionLineas = this.mapRowRepoLinea(estructuraDevolucionDescripcion, rowNumRepoLinea);

					listaReposicionLinea.add(reposicionLineas);
					rowNumRepoLinea ++;
				}
			}
		}
		reposicion = new Reposicion(null,null,null, //Corresponde a CodLoc,CodMac, tipoListado. no lo devuelve el procedimiento
				modeloProveedor,denominacion, descrColor, codArtFoto,
				estado, listaReposicionLinea, paginasTotales, posicion, flgSigPosVacia,
				flgRevisada, flgSustituida, area, seccion, codError, descError);
		return reposicion;
	}



	private ReposicionHayDatos obtenerReposicionHayDatos(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Obtención de los parámetros de salida en crudo
		String hayDatos_BD = (String)cs.getString(idParametroResultado1);
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de ReposicionHayDatos
		String hayDatos = hayDatos_BD;
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		ReposicionHayDatos reposicionHayDatos = new ReposicionHayDatos(hayDatos, codError, descError);

		return reposicionHayDatos;
	}

	private Reposicion obtenerBorrarReposicion(CallableStatement cs,int idParametroResultado1,int idParametroResultado2) throws SQLException{
		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);

		//Transformación de datos para estructura de Reposicion. Solo los campos codigo y descripccion de Error
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		Reposicion reposicion = new Reposicion();
		reposicion.setCodError(codError);
		reposicion.setDescError(descError);

		return reposicion;
	}

	private ReposicionGuardar obtenerGuardarReposicion(CallableStatement cs,int idParametroResultado1,int idParametroResultado2) throws SQLException{
		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);

		//Transformación de datos para estructura de Reposicion. Solo los campos codigo y descripccion de Error
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		ReposicionGuardar reposicionGuardar = new ReposicionGuardar();
		reposicionGuardar.setCodError(codError);
		reposicionGuardar.setDescError(descError);

		return reposicionGuardar;
	}


	private Reposicion obtenerFinalizarTareaReposicion(CallableStatement cs,int idParametroResultado1,int idParametroResultado2) throws SQLException{
		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);

		//Transformación de datos para estructura de Reposicion. Solo los campos codigo y descripccion de Error
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		Reposicion reposicion = new Reposicion();
		reposicion.setCodError(codError);
		reposicion.setDescError(descError);

		return reposicion;
	}

	private ReposicionCmbSeccion obtenerSeccionesReposicion(CallableStatement cs,int idParametroResultado1,int idParametroResultado2, int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsRepoSecciones = null;
		List<ReposicionSeccion> reposicionSeccionLst = new ArrayList<ReposicionSeccion>();
		ReposicionCmbSeccion reposicionCmbSeccion = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de Reposicion. Solo los campos codigo y descripccion de Error
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new Long("0").equals(codError)){ //El proceso se ha ejecutado correctamente	
			//Obtención de las secciones de reposicion
			ARRAY reposicionSeccionesLst = (ARRAY)cs.getObject(idParametroResultado1);
			if (reposicionSeccionesLst != null){
				rsRepoSecciones  = reposicionSeccionesLst.getResultSet();

				//Recorrido de la lista de secciones de reposicion
				while (rsRepoSecciones.next()) {						
					STRUCT estructuraReposicionSeccion = (STRUCT)rsRepoSecciones.getObject(2); 
					ReposicionSeccion reposicionSeccion = this.mapRowRepoSeccion(estructuraReposicionSeccion);

					reposicionSeccionLst.add(reposicionSeccion);
				}
			}
		}
		reposicionCmbSeccion = new ReposicionCmbSeccion(reposicionSeccionLst, codError, descError);

		return reposicionCmbSeccion;
	}

	private ReposicionDatosTalla obtenerDatosAdicionalesTallaReposicion(CallableStatement cs,int idParametroResultado1,int idParametroResultado2, int idParametroResultado3, int idParametroResultado4, int idParametroResultado5, int idParametroResultado6, int idParametroResultado7) throws SQLException{
		//Iniciar variables
		ReposicionDatosTalla reposicionDatosTalla = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codArt_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String proveedor_BD = (String)cs.getString(idParametroResultado2);
		String estrEroski_BD = (String)cs.getString(idParametroResultado3);
		String modeloEroski_BD = (String)cs.getString(idParametroResultado4);
		String refSonae_BD = (String)cs.getString(idParametroResultado5);
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado6);
		String descError_BD = (String)cs.getString(idParametroResultado7);


		//Transformación de datos para estructura de Reposicion. Solo los campos codigo y descripccion de Error
		Long codArt = ((codArt_BD != null && !("".equals(codArt_BD.toString())))?new Long(codArt_BD.toString()):null);
		String proveedor = proveedor_BD;
		String estrEroski = estrEroski_BD;
		String modeloEroski = modeloEroski_BD;
		String refSonae = refSonae_BD;
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		reposicionDatosTalla = new ReposicionDatosTalla(codArt, proveedor, estrEroski, modeloEroski, refSonae, codError, descError);

		return reposicionDatosTalla;
	}


	//Obtiene un objeto de ReposicionSeccion.
	private ReposicionSeccion mapRowRepoSeccion(STRUCT estructuraReposicionSeccion) throws SQLException {

		ReposicionSeccion reposicionSeccion = null;

		//Obtener datos de ReposicionSeccion en crudo
		Object[] objectInfo = estructuraReposicionSeccion.getAttributes();

		String seccion_BD = ((String)objectInfo[0]);
		String descripcion_BD = ((String)objectInfo[1]);


		//Transformación de datos para estructura de ReposicionLinea
		String seccion = seccion_BD;
		String descripcion = descripcion_BD;

		reposicionSeccion = new ReposicionSeccion(seccion, descripcion);

		return reposicionSeccion;
	}

	//Obtiene un objeto de ReposicionLinea. 
	private ReposicionLinea mapRowRepoLinea(STRUCT estructuraReposicionLinea, int rowNumLinea) throws SQLException {

		ReposicionLinea reposicionLinea = null;

		//Obtener datos de DevolucionLinea en crudo
		Object[] objectInfo = estructuraReposicionLinea.getAttributes();

		BigDecimal codArticulo_BD =  ((BigDecimal)objectInfo[0]);
		String descrTalla_BD = ((String)objectInfo[1]);
		BigDecimal cantRepo_BD =  ((BigDecimal)objectInfo[2]);
		BigDecimal stock_BD =  ((BigDecimal)objectInfo[3]);
		String pantCorrStock_BD = ((String)objectInfo[4]);


		//Transformación de datos para estructura de ReposicionLinea
		Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
		String descrTalla = descrTalla_BD;
		Double cantRepo = ((cantRepo_BD != null && !("".equals(cantRepo_BD.toString())))?new Double(cantRepo_BD.toString()):null);
		Double stock = ((stock_BD != null && !("".equals(stock_BD.toString())))?new Double(stock_BD.toString()):null);
		String pantCorrStock = pantCorrStock_BD;

		Long subPosicion = new Long(rowNumLinea); //Lo necesitamos para saber en que oden nos esta mandando las lineas el procedimiento, para luego poder pintarlo en el mismo orden en pantalla

		reposicionLinea = new ReposicionLinea(codArticulo, descrTalla, cantRepo, stock, pantCorrStock);
		reposicionLinea.setSubPosicion(subPosicion);


		return reposicionLinea;
	}






}
