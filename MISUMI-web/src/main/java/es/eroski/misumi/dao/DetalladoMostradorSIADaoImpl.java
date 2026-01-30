package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.DetalladoMostradorSIADao;
import es.eroski.misumi.model.DetalladoMostradorRedondeo;
import es.eroski.misumi.model.DetalladoMostradorSIA;
import es.eroski.misumi.model.DetalladoMostradorSIALista;
import es.eroski.misumi.model.DetalladoRedondeo;
import es.eroski.misumi.model.DetalleMostradorModificados;
import es.eroski.misumi.model.EstrucComercialMostrador;
import es.eroski.misumi.model.FiltrosDetalleMostrador;
import es.eroski.misumi.model.GestionEuros;
import es.eroski.misumi.model.GestionEurosRefs;
import es.eroski.misumi.model.GestionEurosSIA;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.ReferenciaNoGamaMostrador;
import es.eroski.misumi.model.VMisDetalladoMostrador;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class DetalladoMostradorSIADaoImpl implements DetalladoMostradorSIADao{

	private JdbcTemplate jdbcTemplate;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTAR_DETALLADO = 10;
	private static final int POSICION_PARAMETRO_SALIDA_ACTUALIZAR_DETALLADO = 1;
	private static final int POSICION_PARAMETRO_SALIDA_NUEVA_REFERENCIA_DETALLADO= 3;

	private static final int POSICION_PARAMETRO_SALIDA_GESTION_EUROS_OBJ = 1;
	private static final int POSICION_PARAMETRO_SALIDA_GESTION_EUROS_CODERR = 2;
	private static final int POSICION_PARAMETRO_SALIDA_GESTION_EUROS_DESCERR = 3;
	
	private static final int POSICION_PARAMETRO_SALIDA_CONSULAR_REFERENCIAS_NO_GAMA= 8;

	private static Logger logger = Logger.getLogger(DetalladoMostradorSIADaoImpl.class);
	
	private String pkDetalladoByContext;
	private ServletContext context;
	
	@Autowired
	public DetalladoMostradorSIADaoImpl(ServletContext context, @Value("${pk.apr.detallado.mostrador.misumi}") String pkDetalladoMisumi,
			@Value("${pk.apr.detallado.mostrador.misumi.web2}") String pkDetalladoMisumiWeb2) {
		
		this.context = context;
		this.pkDetalladoByContext = context.getContextPath().equalsIgnoreCase(Constantes.CONTEXTO_WEB2) ? pkDetalladoMisumiWeb2 : pkDetalladoMisumi;
		
	}

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	} 

	@Override
	public DetalladoMostradorSIALista actualizarDetalladoSIA(List<DetalladoMostradorSIA> detalladoMostradorSIALista) throws Exception {

		DetalladoMostradorSIALista salida = null;
		//Obtención de parámetros de actualización
		final List<DetalladoMostradorSIA> listadoModificados = new ArrayList<DetalladoMostradorSIA>(); //Datos que se enviarán al procedimiento
		//Carga de lista de modificados
		for (int i=0; i<detalladoMostradorSIALista.size(); i++){
			DetalladoMostradorSIA detalladoMostradorSIA = detalladoMostradorSIALista.get(i);
			//Se guardan los modificados para pasarlos al procedimiento
			listadoModificados.add(detalladoMostradorSIA);
		}

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						//Crear estructura para actualización
						STRUCT itemConsulta = crearEstructuraActualizarDetallado(listadoModificados, con);

						cs = con.prepareCall("{call pk_apr_detallado_misumi.p_actualizar(?) }");
						cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_DETALLADO_REG");
						cs.setObject(1, itemConsulta);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DetalladoMostradorSIALista ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = crearEstructuraActualizarDetallado(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (DetalladoMostradorSIALista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public DetalladoMostradorRedondeo redondeoDetallado(DetalladoMostradorSIA detalladoMostradorSIA) throws Exception {

		DetalladoMostradorRedondeo salida = null;
		//Obtención de parámetros de consulta
		final String p_cod_loc = detalladoMostradorSIA.getCodLoc().toString();
		final String p_cod_art_formlog_ini = detalladoMostradorSIA.getCodArticulo().toString();
		final String p_cant_art_formlog_ini = ((detalladoMostradorSIA.getUnidPropuestasFlModif() != null)? detalladoMostradorSIA.getUnidPropuestasFlModif().toString() : "");

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_redondeo(?, ?, ?, ?, ?, ?, ?, ?) }");

						//Parametros de entrada
						cs.setInt(1, Integer.parseInt(p_cod_loc));
						cs.setInt(2, Integer.parseInt(p_cod_art_formlog_ini));
						cs.setInt(3, Integer.parseInt(p_cant_art_formlog_ini));

						//Parametros de salida
						cs.registerOutParameter(4, OracleTypes.NUMBER);
						cs.registerOutParameter(5, OracleTypes.NUMBER);
						cs.registerOutParameter(6, OracleTypes.NUMBER);
						cs.registerOutParameter(7, OracleTypes.NUMBER);
						cs.registerOutParameter(8, OracleTypes.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DetalladoRedondeo ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoRedondeoDetallado(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (DetalladoMostradorRedondeo) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public EstrucComercialMostrador obtenerEstrCom(final Long codLoc, final Long codN2, final Long codN3, final Long codN4, final String tipoAprov){
		CallableStatementCreator csCreator = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) {
				CallableStatement cs = null;
				try {
					cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_obtener_estr_com(?, ?, ?, ?, ?, ?, ?) }");

					cs.setObject(1, codLoc, OracleTypes.INTEGER);
					cs.setObject(2, codN2, OracleTypes.INTEGER);
					cs.setObject(3, codN3, OracleTypes.INTEGER);
					cs.setObject(4, codN4, OracleTypes.INTEGER);
					cs.setObject(5, tipoAprov, OracleTypes.VARCHAR);
					
					cs.registerOutParameter(6, OracleTypes.STRUCT, "APR_R_ESTRUCTURA_REG");
					cs.registerOutParameter(7, OracleTypes.DATE);
					
				} catch (SQLException e) {
					logger.error("Fallo preparando consulta de estructura com", e);             
				}
				return cs;
			}
		};
		CallableStatementCallback<EstrucComercialMostrador> csCallback = new CallableStatementCallback<EstrucComercialMostrador>() {

			public EstrucComercialMostrador doInCallableStatement(CallableStatement cs) {
				EstrucComercialMostrador ret = null;
				
				try {
					cs.execute();
					Struct struct = (Struct)cs.getObject(6); // TYPE "APR_R_ESTRUCTURA_DAT".
					ret = obtenerEstructuraCom(struct);
					ret.setFechaEspejo(cs.getString(7)); // Fecha Espejo.
				} catch (SQLException e) {
					logger.error("Fallo ejecutando consulta de estructura com", e);
				}
				return ret;
			}
		};

		EstrucComercialMostrador salida = null;
		try {
			salida = this.jdbcTemplate.execute(csCreator,csCallback);
		} catch (Exception e) {
			logger.error("#####################################################");
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return salida;
	}
	
	@Override
	public DetalladoMostradorSIALista referenciaNueva(DetalladoMostradorSIA detalladoMostradorSIA) throws Exception {

		DetalladoMostradorSIALista salida = null;
		//Obtención de parámetros de consulta
		final String p_cod_loc = detalladoMostradorSIA.getCodLoc().toString();
		final String p_cod_art_formlog = detalladoMostradorSIA.getCodArticulo().toString();

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {		                	

						cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_ref_nueva(?, ?, ?) }");

						cs.setInt(1, Integer.parseInt(p_cod_loc));
						cs.setInt(2, Integer.parseInt(p_cod_art_formlog));
						cs.registerOutParameter(3, OracleTypes.STRUCT, "APR_R_DETALLADO_REG");

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DetalladoMostradorSIALista ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoNuevaReferenciaDetallado(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (DetalladoMostradorSIALista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public GestionEurosSIA gestionEuros(final GestionEuros gestionEuros) throws Exception {			    	
		GestionEurosSIA salida = null;		

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {
						//Crear estructura para actualización
						STRUCT itemConsulta = crearEstructuraGestionEurosDetallado(gestionEuros, con);

						cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_apr_mis_gestion_euros(?,?,?) }");

						//Parametros de entrada
						cs.setObject(1, itemConsulta);					

						//Parametros de salida
						cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_GESTION_EUROS");
						cs.registerOutParameter(2, OracleTypes.NUMBER);
						cs.registerOutParameter(3, OracleTypes.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					GestionEurosSIA ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoGestionEuros(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (GestionEurosSIA) this.jdbcTemplate.execute(csCreator,csCallback);
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

	/**
	 * Mapea a objeto java.
	 * <p> Estructura a Mapear
	 * <code>
	 * <li> apr_r_estructura_reg AS OBJECT (
	    	DATOS              apr_t_r_estructura_dat
    	)
	 * <li>apr_t_r_estructura_dat IS TABLE OF apr_r_estructura_dat
	 * <li>APR_R_ESTRUCTURA_DAT AS OBJECT (
		    CODIGO    	                NUMBER(4),
		    DENOMINACION	            VARCHAR2(50 BYTE)
		)
	 * </code>
	 * @param struct
	 * @return
	 * @throws SQLException
	 */
	private EstrucComercialMostrador obtenerEstructuraCom(Struct struct) throws SQLException {
		EstrucComercialMostrador retorno = null;
		
		if (struct!=null) {
			Object[] atributes = struct.getAttributes();
			Array arrayDatos = (Array) atributes[0]; // DATOS

			List<OptionSelectBean> datos = new ArrayList<OptionSelectBean>();
			if(arrayDatos!=null){
				ResultSet rsDatos = arrayDatos.getResultSet();

				while(rsDatos.next()){
					Struct datStruct = (Struct) rsDatos.getObject(2);
					OptionSelectBean dato = this.mapEstructuraDat(datStruct);
					datos.add(dato);
				}
			}

			retorno = new EstrucComercialMostrador();
			retorno.setLstOpciones(datos);
		}
		return retorno;
	}

	/**
	 * Mapea a objeto java.
	 * <p> Estructura a Mapear
	 * <code>
	 * <li>APR_R_ESTRUCTURA_DAT AS OBJECT (
		    CODIGO    	                NUMBER(4),
		    DENOMINACION	            VARCHAR2(50 BYTE)
		)
	 * </code>
	 * @param struct
	 * @return
	 * @throws SQLException
	 */
	private OptionSelectBean mapEstructuraDat(Struct struct) throws SQLException {
		Object[] atributes = struct.getAttributes();
		BigDecimal codigo = (BigDecimal) atributes[0];
		String denominacion = (String) atributes[1];
		OptionSelectBean dato = new OptionSelectBean();
		if(codigo!=null){
			dato.setCodigo(String.valueOf(codigo));
		}
		dato.setDescripcion(dato.getCodigo() + "-" + denominacion);
		return dato;
	}
	
	private STRUCT crearEstructuraGestionEurosDetallado(GestionEuros gestionEuros, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesari para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		//COD_LOC    	                NUMBER,
		//RESPETAR_IMC                VARCHAR2(1 BYTE),
		//PRECIO_COSTO_FINAL	        NUMBER,
		//PRECIO_COSTO_FINAL_FINAL    NUMBER,
		//REFERENCIAS                 APR_T_R_GESTION_EUROS_REFS

		//Creamos el objeto que contendrá APR_R_GESTION_EUROS 
		Object[] objectGestionEuros = new Object[5];

		//Guardamos el codloc.
		objectGestionEuros[0] = gestionEuros.getCodLoc();		

		//Guardamos el IMC
		objectGestionEuros[1] = gestionEuros.getRespetarIMC();

		//Guardamos precio costo final
		objectGestionEuros[2] = gestionEuros.getPrecioCostoFinal();

		//Guardamos precio costo final final
		objectGestionEuros[3] = gestionEuros.getPrecioCostoFinalFinal();

		//Insertamos la lista de referencias de gestion de euros.
		objectGestionEuros[4] = crearEstructuraGestionEurosRefs(gestionEuros.getGestionEurosRefsLst(), conexionOracle);

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS",conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectGestionEuros);

		return itemConsulta;
	}

	private ARRAY crearEstructuraGestionEurosRefs(List<GestionEurosRefs> listaGestionEurosRefs, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesaria para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		Object[] objectTabla;
		if(listaGestionEurosRefs != null){
			int numeroElementos = listaGestionEurosRefs.size();
			objectTabla = new Object[numeroElementos];

			for (int i=0; i<numeroElementos; i++){
				GestionEurosRefs gestionEurosRefs = (GestionEurosRefs)listaGestionEurosRefs.get(i);

				Object[] objectInfo = new Object[9];

				//COD_ARTICULO   	                NUMBER,
				//COD_NECESIDAD   	                NUMBER,
				//UNIDS_CAJA                       NUMBER,
				//PRECIO_COSTO_ARTICULO            NUMBER,
				//PRECIO_COSTO_ART_LINEAL_FINAL     NUMBER,
				//UNID_PROPUESTAS_FL_MODIF         NUMBER,
				//DIFERENCIA                       NUMBER,
				//REF_CUMPLE                       VARCHAR(1),
				//AVISOS                           APR_T_R_GESTION_EUROS_REFS_AV		     
				objectInfo[0] = gestionEurosRefs.getCodArticulo() != null ? gestionEurosRefs.getCodArticulo() : null;
				objectInfo[1] = gestionEurosRefs.getCodNecesidad() != null ? gestionEurosRefs.getCodNecesidad() : null;
				objectInfo[2] = gestionEurosRefs.getUnidsCaja() != null ? gestionEurosRefs.getUnidsCaja() : null;
				objectInfo[3] = gestionEurosRefs.getPrecioCostoArticulo() != null ? gestionEurosRefs.getPrecioCostoArticulo() : null;
				objectInfo[4] = gestionEurosRefs.getPrecioCostoArtLinealFinal() != null ? gestionEurosRefs.getPrecioCostoArtLinealFinal() : null;
				objectInfo[5] = gestionEurosRefs.getUnidPropuestasFlModif() != null ? gestionEurosRefs.getUnidPropuestasFlModif() : null;
				objectInfo[6] = gestionEurosRefs.getDiferencia() != null ? gestionEurosRefs.getDiferencia() : null;	
				objectInfo[7] = gestionEurosRefs.getRefCumple();
				objectInfo[8] = gestionEurosRefs.getAvisos();

				StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS_REFS",conexionOracle);
				STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

				objectTabla[i] = itemObjectStruct;
			}
		}else{
			objectTabla = new Object[1];

			Object[] objectInfo = new Object[7];

			//COD_ARTICULO   	                NUMBER,
			//COD_NECESIDAD   	                NUMBER,
			//UNIDS_CAJA                       NUMBER,
			//PRECIO_COSTO_ARTICULO            NUMBER,
			//PRECIO_COSTO_ART_LINEAL_FINAL     NUMBER,
			//UNID_PROPUESTAS_FL_MODIF         NUMBER,
			//DIFERENCIA                       NUMBER,
			//REF_CUMPLE                       VARCHAR(1),
			//AVISOS                           APR_T_R_GESTION_EUROS_REFS_AV		     
			objectInfo[0] = null;
			objectInfo[1] = null;
			objectInfo[2] = null;
			objectInfo[3] = null;
			objectInfo[4] = null;
			objectInfo[5] = null;		
			objectInfo[6] = null;
			objectInfo[7] = null;
			objectInfo[8] = null;//crearEstructuraAvisos(null, conexionOracle);

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS_REFS",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
			
			objectTabla[0] = itemObjectStruct;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_GESTION_EUROS_REFS", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		return array;
	}

	private ARRAY crearEstructuraAvisos(List<String> listaAvisos, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesaria para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		Object[] objectTabla;
		if(listaAvisos != null){
			int numeroElementos = listaAvisos.size();
			objectTabla = new Object[numeroElementos];
		
			for (int i=0; i<numeroElementos; i++){
				String aviso = (String)listaAvisos.get(i);
	
				Object[] objectInfo = new Object[1];			
				objectInfo[0] = aviso;
	
				StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS_REFS_AV",conexionOracle);
				STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
	
				objectTabla[i] = itemObjectStruct;
			}
		}else{
			objectTabla = new Object[1];
			
			Object[] objectInfo = new Object[1];			
			objectInfo[0] = null;

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS_REFS_AV",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTabla[0] = itemObjectStruct;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_GESTION_EUROS_REFS_AV", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		return array;
	}

	/**
	 * Obtener los datos del Detallado Mostrador con los 
	 * datos obtenidos de la CONSULTA del detallado de SIA.
	 * @param cs
	 * @param rs
	 * @return
	 */
	private DetalladoMostradorSIALista obtenerResultadoConsultaDetallado(CallableStatement cs, ResultSet rs){
		return obtenerEstructuraDetalladoSIA(cs, rs, this.POSICION_PARAMETRO_SALIDA_CONSULTAR_DETALLADO);
	}

	private DetalladoMostradorSIALista crearEstructuraActualizarDetallado(CallableStatement cs, ResultSet rs){
		return obtenerEstructuraDetalladoSIA(cs, rs, this.POSICION_PARAMETRO_SALIDA_ACTUALIZAR_DETALLADO);
	}

	private GestionEurosSIA obtenerResultadoGestionEuros(CallableStatement cs, ResultSet rs){
		return obtenerEstructuraGestionEuros(cs, rs, this.POSICION_PARAMETRO_SALIDA_GESTION_EUROS_OBJ, this.POSICION_PARAMETRO_SALIDA_GESTION_EUROS_CODERR,this.POSICION_PARAMETRO_SALIDA_GESTION_EUROS_DESCERR);
	}

	private DetalladoMostradorSIALista obtenerResultadoNuevaReferenciaDetallado(CallableStatement cs, ResultSet rs){
		return obtenerEstructuraDetalladoSIA(cs, rs, this.POSICION_PARAMETRO_SALIDA_NUEVA_REFERENCIA_DETALLADO);
	}
	
	private GestionEurosSIA obtenerEstructuraGestionEuros(CallableStatement cs, ResultSet rs, int idParametroResultado1,int idParametroResultado2,int idParametroResultado3){
		GestionEurosSIA gestionEurosSIA = null;
		GestionEuros gestionEuros = null;
		List<GestionEurosRefs> listaGestionEurosRefs = new ArrayList<GestionEurosRefs>();

		try{
			//Obtención del parámetro de salida
			STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado1);

			//Obtención de los parámetros de salida en crudo
			BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
			String descError_BD = (String)cs.getString(idParametroResultado3);

			//Transformación de datos para estructura de GestionEuros
			Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
			String descError = descError_BD;

			//Control de error en la obtención de datos
			if (new Long("0").equals(codError)){ //El proceso se ha ejecutado correctamente				
				Object[] objectInfo = estructuraResultado.getAttributes();

				BigDecimal codLoc_BD = (BigDecimal) objectInfo[0];
				String respetarIMC_BD = (String) objectInfo[1];
				BigDecimal precioCostoFinal_BD = (BigDecimal) objectInfo[2];
				BigDecimal precioCostoFinalFinal_BD = (BigDecimal) objectInfo[3];
				ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[4];

				Long codLoc = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
				String respetarIMC = respetarIMC_BD;
				Double precioCostoFinal = ((precioCostoFinal_BD != null && !("".equals(precioCostoFinal_BD.toString())))?new Double(precioCostoFinal_BD.toString()):null);
				Double precioCostoFinalFinal = ((precioCostoFinalFinal_BD != null && !("".equals(precioCostoFinalFinal_BD.toString())))?new Double(precioCostoFinalFinal_BD.toString()):null);				

				if (listaDatos!=null){
					rs = listaDatos.getResultSet();

					//Recorrido del listado de datos
					while (rs.next()) {
						STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
						GestionEurosRefs gestionEurosRef = this.mapRowGestionEurosRefs(estructuraDatos);
						listaGestionEurosRefs.add(gestionEurosRef);
					}
				}   
				gestionEuros = new GestionEuros(codLoc, respetarIMC, precioCostoFinal, precioCostoFinalFinal, listaGestionEurosRefs);
			}		
			gestionEurosSIA = new GestionEurosSIA(codError, descError, gestionEuros);
		} catch (Exception e) {
			e.printStackTrace();
		}						
		return gestionEurosSIA;
	}

	private DetalladoMostradorSIALista obtenerEstructuraDetalladoSIA(CallableStatement cs, ResultSet rs, int idParametroResultado){

		DetalladoMostradorSIALista detalladoMostradorSIALista = new DetalladoMostradorSIALista();
		List<DetalladoMostradorSIA> listaDetalladoSIA = new ArrayList<DetalladoMostradorSIA>();

		try{
			//Obtención del parámetro de salida
			STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado);

			//Obtención de los datos de la estructura
			BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[1];
			String descEstado = (String)estructuraResultado.getAttributes()[2];

			//Control de error en la obtención de datos
			if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
				//Obtención de los datos de salida
				ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[0];
				if (listaDatos!=null){
					rs = listaDatos.getResultSet();
					int rowNum = 0;
					//Recorrido del listado de datos
					while (rs.next()) {
						STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
						DetalladoMostradorSIA detalladoMostradorSIA = this.mapRow(estructuraDatos, rowNum);
						listaDetalladoSIA.add(detalladoMostradorSIA);
						rowNum++;
					}
				}            		

				detalladoMostradorSIALista.setDatos(listaDetalladoSIA);
			}

			detalladoMostradorSIALista.setEstado(new Long(estado.toString()));
			detalladoMostradorSIALista.setDescEstado(descEstado);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detalladoMostradorSIALista;
	}


	private DetalladoRedondeo obtenerResultadoRedondeoDetallado(CallableStatement cs, ResultSet rs){

		DetalladoRedondeo detalladoRedondeo = new DetalladoRedondeo();

		try{
			//Obtención del parámetro de salida
			detalladoRedondeo.setCodArticulo((Long)cs.getLong(4));

			Long unidades =  (Long)cs.getLong(5);
			Double unidadesCaja =  (Double)cs.getDouble(6);

			Long cajas = Math.round(Math.ceil(new Double(unidades.toString())/new Double(unidadesCaja.toString())));

			detalladoRedondeo.setUnidCaja(unidadesCaja);	
			detalladoRedondeo.setUnidPropuestasFlModif((double)cajas);

			detalladoRedondeo.setCodError((Long)cs.getLong(7));
			detalladoRedondeo.setDescError((String)cs.getString(8));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detalladoRedondeo;
	}

	private STRUCT crearEstructuraActualizarDetallado(List<DetalladoMostradorSIA> listaModificados, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesaria para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		int numeroElementos = listaModificados.size();
		Object[] objectTabla = new Object[numeroElementos];

		for (int i=0; i<numeroElementos; i++){

			DetalladoMostradorSIA detalladoMostradorSIA = (DetalladoMostradorSIA)listaModificados.get(i);

			Object[] objectInfo = new Object[49];

			//Sólo se informan los datos necesarios
			objectInfo[0] = (detalladoMostradorSIA.getCodLoc()!=null?detalladoMostradorSIA.getCodLoc():null);
			objectInfo[1] = detalladoMostradorSIA.getCodN1Ref();
			objectInfo[2] = detalladoMostradorSIA.getCodN2Ref();
			objectInfo[3] = detalladoMostradorSIA.getCodN3Ref();
			objectInfo[4] = detalladoMostradorSIA.getCodN4Ref();
			objectInfo[5] = detalladoMostradorSIA.getCodN5Ref();		
			objectInfo[6] = (detalladoMostradorSIA.getCodArticulo()!=null?detalladoMostradorSIA.getCodArticulo():null);	
			objectInfo[7] = detalladoMostradorSIA.getDenomRef();
			objectInfo[8] = null; //(detalladoMostradorSIA.getStockTienda()!=null?detalladoMostradorSIA.getStockTienda():null); // stockTienda
			objectInfo[9] = (detalladoMostradorSIA.getPendienteTienda()!=null?detalladoMostradorSIA.getPendienteTienda():null);
			objectInfo[10] = (detalladoMostradorSIA.getPendienteTiendaManana()!=null?detalladoMostradorSIA.getPendienteTiendaManana():null);
			objectInfo[11] = (detalladoMostradorSIA.getUnidadesCaja()!=null?detalladoMostradorSIA.getUnidadesCaja():null);
			objectInfo[12] = (detalladoMostradorSIA.getUnidEncargoFl()!=null?detalladoMostradorSIA.getUnidEncargoFl():null);
			objectInfo[13] = (detalladoMostradorSIA.getUnidPropuestasFlOrigen()!=null?detalladoMostradorSIA.getUnidPropuestasFlOrigen():null);
			objectInfo[14] = (detalladoMostradorSIA.getUnidPropuestasFlAnt()!=null?detalladoMostradorSIA.getUnidPropuestasFlAnt():null);
			objectInfo[15] = (detalladoMostradorSIA.getUnidPropuestasFlModif()!=null?detalladoMostradorSIA.getUnidPropuestasFlModif():null);
			objectInfo[16] = detalladoMostradorSIA.getTipoDetallado();
			objectInfo[17] = detalladoMostradorSIA.getHoraFinBomba();
			objectInfo[18] = detalladoMostradorSIA.getEstado();
			objectInfo[19] = (detalladoMostradorSIA.getFecTransmision()!=null?new Timestamp(detalladoMostradorSIA.getFecTransmision().getTime()):null);
			objectInfo[20] = (detalladoMostradorSIA.getFechaPedidoProx()!=null?new Timestamp(detalladoMostradorSIA.getFechaPedidoProx().getTime()):null);
			objectInfo[21] = detalladoMostradorSIA.getUnidFlEmpuje();
			objectInfo[22] = detalladoMostradorSIA.getCodNecesidad();
			objectInfo[23] = (detalladoMostradorSIA.getUfp()!=null?detalladoMostradorSIA.getUfp():null);
			objectInfo[24] = detalladoMostradorSIA.getFlgOferta();
			objectInfo[25] = detalladoMostradorSIA.getOferta();
			objectInfo[26] = (detalladoMostradorSIA.getTipoOferta()!=null?detalladoMostradorSIA.getTipoOferta():null);
			objectInfo[27] = null; //detalladoMostradorSIA.getTipoAprov();
			objectInfo[28] = null; //detalladoMostradorSIA.getTipo();
			objectInfo[29] = null; //detalladoMostradorSIA.getTemporada();
			objectInfo[30] = (detalladoMostradorSIA.getNumOrden()!=null?detalladoMostradorSIA.getNumOrden():null);	
			objectInfo[31] = null; //detalladoMostradorSIA.getModeloProveedor();
			objectInfo[32] = null; //detalladoMostradorSIA.getDescrTalla();
			objectInfo[33] = null; //detalladoMostradorSIA.getDescrColor();
			objectInfo[34] = detalladoMostradorSIA.getConverArt();
			objectInfo[35] = null; //detalladoMostradorSIA.getNivelLote();
			objectInfo[36] = null; //detalladoMostradorSIA.getFacing();
			objectInfo[37] = detalladoMostradorSIA.getFlgSIA();
			objectInfo[38] = (detalladoMostradorSIA.getCodError()!=null?detalladoMostradorSIA.getCodError():null);	
			objectInfo[39] = detalladoMostradorSIA.getDescError();
			objectInfo[40] = null; //detalladoMostradorSIA.getFlgPropuesta();
			//GESTION DE EUROS
			objectInfo[41] = detalladoMostradorSIA.getDenomCategoria();
			objectInfo[42] = detalladoMostradorSIA.getDenomSeccion();
			objectInfo[43] = detalladoMostradorSIA.getDenomCategoria();
			objectInfo[44] = detalladoMostradorSIA.getDenomSubcategoria();
			objectInfo[45] = detalladoMostradorSIA.getDenomSeccion();
			objectInfo[46] = (detalladoMostradorSIA.getUfp()!=null?detalladoMostradorSIA.getUfp():null);
			objectInfo[47] = (detalladoMostradorSIA.getUfp()!=null?detalladoMostradorSIA.getUfp():null);
			objectInfo[48] = (detalladoMostradorSIA.getUfp()!=null?detalladoMostradorSIA.getUfp():null);
			
			
			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_DETALLADO_DAT",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTabla[i] = itemObjectStruct;
		}

		Object[] objectConsulta = new Object[3]; //Tiene 3 campos pero sólo nos interesa la lista
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_DETALLADO_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		objectConsulta[0] = array;

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_DETALLADO_REG",conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectConsulta);

		return itemConsulta;
	}

	@Override
	public List<DetalladoMostradorSIA> actualizarDatosGridSIA(List<VMisDetalladoMostrador> lista, Long codCentro) throws Exception {		
		
		List<DetalladoMostradorSIA> detalladoMostradorSIALista = new ArrayList<DetalladoMostradorSIA>();
		DetalladoMostradorSIA campo = new DetalladoMostradorSIA();


		for (int i=0;i<lista.size();i++){
			campo = new DetalladoMostradorSIA();
			campo.setCodLoc((codCentro != null && !("".equals(codCentro.toString())))?codCentro:null);
			campo.setCodArticulo((lista.get(i).getReferencia() != null && !("".equals(lista.get(i).getReferencia().toString())))?lista.get(i).getReferencia():null);	
			campo.setUnidPropuestasFlOrigen(0L);
			campo.setUnidPropuestasFlModif((lista.get(i).getPropuestaPedido() != null && !("".equals(lista.get(i).getPropuestaPedido().toString())))?lista.get(i).getPropuestaPedido():null);
			campo.setUnidPropuestasFlAnt((lista.get(i).getPropuestaPedidoAnt() != null && !("".equals(lista.get(i).getPropuestaPedidoAnt().toString())))?lista.get(i).getPropuestaPedidoAnt():null);
			campo.setTipoDetallado((lista.get(i).getCodNecesidad() != null && !("".equals(lista.get(i).getCodNecesidad().toString())))?null:"A");
			campo.setEstado((lista.get(i).getEstado() != null && !("".equals(lista.get(i).getEstado().toString())))?lista.get(i).getEstado() :null);
			campo.setCodNecesidad((lista.get(i).getCodNecesidad() != null && !("".equals(lista.get(i).getCodNecesidad().toString())))?new Long(lista.get(i).getCodNecesidad().toString()):null);

			detalladoMostradorSIALista.add(campo);
		}


		return detalladoMostradorSIALista;
	}

	private List<DetalleMostradorModificados> tratarDatosObtenerProc(DetalladoMostradorSIALista resultProc){

		//Transformacion de datos para estructura de PedidoAdicionalM
		List<DetalleMostradorModificados> resultado = new ArrayList<DetalleMostradorModificados>();
		List<DetalladoMostradorSIA> detalladoMostradorSIAListaAux = new ArrayList<DetalladoMostradorSIA>();

		DetalleMostradorModificados filaResultado = new DetalleMostradorModificados();

		if (null != resultProc.getDatos()){
			detalladoMostradorSIAListaAux = resultProc.getDatos();
		}

		//Nos recorremos la lista 
		for (int i=0;i<detalladoMostradorSIAListaAux.size();i++){
			filaResultado = new DetalleMostradorModificados();
			filaResultado.setCodArticulo((detalladoMostradorSIAListaAux.get(i).getCodArticulo() != null && !("".equals(detalladoMostradorSIAListaAux.get(i).getCodArticulo().toString())))?new Long(detalladoMostradorSIAListaAux.get(i).getCodArticulo().toString()):null);
			filaResultado.setEstadoPedido((detalladoMostradorSIAListaAux.get(i).getEstado() != null && !("".equals(detalladoMostradorSIAListaAux.get(i).getEstado().toString())))?detalladoMostradorSIAListaAux.get(i).getEstado().toString():null);
			filaResultado.setCodNecesidad((detalladoMostradorSIAListaAux.get(i).getCodNecesidad() != null && !("".equals(detalladoMostradorSIAListaAux.get(i).getCodNecesidad().toString())))?detalladoMostradorSIAListaAux.get(i).getCodNecesidad():null);
			filaResultado.setPropuestaPedido((detalladoMostradorSIAListaAux.get(i).getUnidPropuestasFlModif() != null && !("".equals(detalladoMostradorSIAListaAux.get(i).getUnidPropuestasFlModif().toString())))?detalladoMostradorSIAListaAux.get(i).getUnidPropuestasFlModif():null);
			filaResultado.setPropuestaPedidoAnt((detalladoMostradorSIAListaAux.get(i).getUnidPropuestasFlAnt() != null && !("".equals(detalladoMostradorSIAListaAux.get(i).getUnidPropuestasFlAnt().toString())))?detalladoMostradorSIAListaAux.get(i).getUnidPropuestasFlAnt():null);
			filaResultado.setUnidadesCaja((detalladoMostradorSIAListaAux.get(i).getUnidadesCaja() != null && !("".equals(detalladoMostradorSIAListaAux.get(i).getUnidadesCaja())))?detalladoMostradorSIAListaAux.get(i).getUnidadesCaja():null);

			if (!(new Long(0).equals(detalladoMostradorSIAListaAux.get(i).getCodError()))) {
				filaResultado.setEstadoGrid("1");
				filaResultado.setResultadoWS(detalladoMostradorSIAListaAux.get(i).getCodError()+ "-"+detalladoMostradorSIAListaAux.get(i).getDescError());	
			}else{
				filaResultado.setEstadoGrid("0");
			}
			
			filaResultado.setDescError(detalladoMostradorSIAListaAux.get(i).getDescError());
			
			resultado.add(filaResultado);
		}
		return resultado;
	}

	private DetalladoMostradorSIA mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		//Obtención de datos de la estructura de base de datos
		BigDecimal codLoc_BD = ((BigDecimal)objectInfo[0]);						// centro
		
		String codN1Ref_BD = (String)objectInfo[1]; 							// area
		String denomArea_BD = (String)objectInfo[2];							// denom_area
		String codN2Ref_BD = (String)objectInfo[3]; 							// seccion
		String denomSeccion_BD = (String)objectInfo[4];							// denom_seccion
		String codN3Ref_BD = (String)objectInfo[5];								// categoria
		String denomCategoria_BD = (String)objectInfo[6];						// denom_categoria
		String codN4Ref_BD = (String)objectInfo[7];								// subcategoria
		String denomSubcategoria_BD = (String)objectInfo[8];					// denom_subcategoria
		String codN5Ref_BD = (String)objectInfo[9];								// segmento
		String denomSegmento_BD = (String)objectInfo[10];						// denom_segmento
		
		BigDecimal codArticulo_BD = ((BigDecimal)objectInfo[11]);				// cod_art_formlog
		String denomRef_BD = (String)objectInfo[12];							// denom_ref

		String tipoGama_BD = (String)objectInfo[13]; 							// tipo_gama
		Timestamp fecTransmision_BD = (Timestamp)objectInfo[14];				// fec_transmision
		Timestamp fecVentaDiaTrans_BD = (Timestamp)objectInfo[15];				// fec_venta_dia_trans
		Timestamp fecSigTrans_BD = (Timestamp)objectInfo[16];					// fec_sig_transmision
		Timestamp fecVentaSigTrans_BD = (Timestamp)objectInfo[17];				// fec_venta_sig_transmision
		BigDecimal diasCubrePed_BD = ((BigDecimal)objectInfo[18]); 				// dias_cubre_ped
		BigDecimal pdteRecManana_BD = ((BigDecimal)objectInfo[19]); 			// pdte_rec_manana
		String empujePdteRecManana_BD = (String)objectInfo[20]; 				// empuje_pdte_rec_manana
		BigDecimal pdteRecFecVenta_BD = ((BigDecimal)objectInfo[21]); 			// pdte_rec_fec_venta
		BigDecimal unidCaja_BD = ((BigDecimal)objectInfo[22]);					// unidades_caja
		String marcaCompra_BD = (String)objectInfo[23];							// marca_compra
		String marcaVenta_BD = (String)objectInfo[24];							// marca_venta
		BigDecimal refCompra_BD = ((BigDecimal)objectInfo[25]);					// ref_compra
		BigDecimal multCompraVenta_BD = ((BigDecimal)objectInfo[26]);			// mult_compra_venta
		BigDecimal pvpTarifa_BD = ((BigDecimal)objectInfo[27]);					// pvp_tarifa
		BigDecimal costo_BD = ((BigDecimal)objectInfo[28]);						// costo
		BigDecimal iva_BD = ((BigDecimal)objectInfo[29]);						// iva
		BigDecimal tirado_BD = ((BigDecimal)objectInfo[30]);					// tirado
		BigDecimal tiradoParasitos_BD = ((BigDecimal)objectInfo[31]);			// tirado_parasitos
		BigDecimal previsionVenta_BD = ((BigDecimal)objectInfo[32]);			// prevision_venta
		BigDecimal propuestaPedido_BD = ((BigDecimal)objectInfo[33]);			// propuesta_pedido
		String redondeoPropPedido_BD = (String)objectInfo[34];					// redondeo_prop_pedido
		BigDecimal totVenFecEspejo_BD = ((BigDecimal)objectInfo[35]);			// tot_ven_fec_espejo
		BigDecimal totImpVenFecEspejo_BD = ((BigDecimal)objectInfo[36]);		// tot_imp_ven_fec_espejo
		BigDecimal numOrden_BD =  ((BigDecimal)objectInfo[37]);					// num_orden
		BigDecimal codNecesidad_BD = ((BigDecimal)objectInfo[38]);				// cod_necesidad
		String estado_BD = (String)objectInfo[39];								// estado
		String horaLimite_BD = (String)objectInfo[40];							// hora_fin_bomba
		BigDecimal ventaEspejoTarifa_BD = (BigDecimal)objectInfo[41];			// tot_ven_tar_espejo
		BigDecimal ventaEspejoOferta_BD = (BigDecimal)objectInfo[42];			// tot_ven_ofer_espejo
		String tipoAprov_BD = (String)objectInfo[43];							// tipo_aprov


		//Transformación de datos para estructura de DetalladoMostradorSIA
		Long codLoc = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
		String codN1Ref = codN1Ref_BD;
		String denomArea = denomArea_BD;
		String codN2Ref = codN2Ref_BD;
		String denomSeccion = denomSeccion_BD;  
		String codN3Ref = codN3Ref_BD;
		String denomCategoria = denomCategoria_BD;  
		String codN4Ref = codN4Ref_BD;
		String denomSubcategoria = denomSubcategoria_BD;  
		String codN5Ref = codN5Ref_BD;
		String denomSegmento = denomSegmento_BD;
		
		Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
		String denomRef = denomRef_BD;
		String tipoGama = tipoGama_BD;
		
		Date fecTransmision = ((fecTransmision_BD != null)?new Date(fecTransmision_BD.getTime()):null);
		Date fecVentaDiaTrans = ((fecVentaDiaTrans_BD != null)?new Date(fecVentaDiaTrans_BD.getTime()):null);
		Date fecSigTrans = ((fecSigTrans_BD != null)?new Date(fecSigTrans_BD.getTime()):null);
		Date fecVentaSigTrans = ((fecVentaSigTrans_BD != null)?new Date(fecVentaSigTrans_BD.getTime()):null);

		Long diasCubrePed = ((diasCubrePed_BD != null && !("".equals(diasCubrePed_BD.toString())))?new Long(diasCubrePed_BD.toString()):null);
		Double pdteRecManana = ((pdteRecManana_BD != null && !("".equals(pdteRecManana_BD.toString())))?new Double(pdteRecManana_BD.toString()):null);
		String empujePdteRecManana = empujePdteRecManana_BD;
		Double pdteRecFecVenta = ((pdteRecFecVenta_BD != null && !("".equals(pdteRecFecVenta_BD.toString())))?new Double(pdteRecFecVenta_BD.toString()):null);
		
		Double unidCaja = ((unidCaja_BD != null && !("".equals(unidCaja_BD.toString())))?new Double(unidCaja_BD.toString()):null);
		String marcaCompra = marcaCompra_BD;
		String marcaVenta = marcaVenta_BD;
		Long refCompra = ((refCompra_BD != null && !("".equals(refCompra_BD.toString()))) ? new Long(refCompra_BD.toString()):null);
		Double multCompraVenta = ((multCompraVenta_BD != null && !("".equals(multCompraVenta_BD.toString()))) ? new Double(multCompraVenta_BD.toString()):null);
		Double pvpTarifa = ((pvpTarifa_BD != null && !("".equals(pvpTarifa_BD.toString()))) ? new Double(pvpTarifa_BD.toString()):null);
		Double costo = ((costo_BD != null && !("".equals(costo_BD.toString()))) ? new Double(costo_BD.toString()):null);
		Double iva = ((iva_BD != null && !("".equals(iva_BD.toString())))?new Double(iva_BD.toString()):null);
		Double tirado = ((tirado_BD != null && !("".equals(tirado_BD.toString())))?new Double(tirado_BD.toString()):null);
		Double tiradoParasitos = ((tiradoParasitos_BD != null && !("".equals(tiradoParasitos_BD.toString()))) ? new Double(tiradoParasitos_BD.toString()):null);
		Double previsionVenta = ((previsionVenta_BD != null && !("".equals(previsionVenta_BD.toString()))) ? new Double(previsionVenta_BD.toString()):null);
		Long propuestaPedido = ((propuestaPedido_BD != null && !("".equals(propuestaPedido_BD.toString()))) ? new Long(propuestaPedido_BD.toString()):null);
		String redondeoPropPedido = redondeoPropPedido_BD;
		Double totVenFecEspejo = ((totVenFecEspejo_BD != null && !("".equals(totVenFecEspejo_BD.toString()))) ? new Double(totVenFecEspejo_BD.toString()):null);
		Double totImpVenFecEspejo = ((totImpVenFecEspejo_BD != null && !("".equals(totImpVenFecEspejo_BD.toString()))) ? new Double(totImpVenFecEspejo_BD.toString()):null);
		Long orden = ((numOrden_BD != null && !("".equals(numOrden_BD.toString()))) ? new Long(numOrden_BD.toString()):null);

		Long codNecesidad = ((codNecesidad_BD != null && !("".equals(codNecesidad_BD.toString())))?new Long(codNecesidad_BD.toString()):null);
		String estado = estado_BD;   
		String horaLimite = horaLimite_BD;
		Double ventaEspejoTarifa = ((ventaEspejoTarifa_BD != null && !("".equals(ventaEspejoTarifa_BD.toString()))) ? new Double(ventaEspejoTarifa_BD.toString()):null);
		Double ventaEspejoOferta = ((ventaEspejoOferta_BD != null && !("".equals(ventaEspejoOferta_BD.toString()))) ? new Double(ventaEspejoOferta_BD.toString()):null);
		String tipoAprov = tipoAprov_BD;

		DetalladoMostradorSIA detalladoMostradorSIA =  new DetalladoMostradorSIA( codLoc
																				, codN1Ref, denomArea
																				, codN2Ref, denomSeccion
																				, codN3Ref, denomCategoria
																				, codN4Ref, denomSubcategoria
																				, codN5Ref, denomSegmento
																				, codArticulo, denomRef
																				, tipoAprov
																				, tipoGama
																				, fecTransmision, fecVentaDiaTrans
																				, fecSigTrans, fecVentaSigTrans
																				, diasCubrePed, pdteRecManana
																				, empujePdteRecManana, pdteRecFecVenta
																				, unidCaja
																				, marcaCompra, marcaVenta
																				, refCompra
																				, multCompraVenta, pvpTarifa, costo, iva
																				, tirado, tiradoParasitos
																				, previsionVenta, propuestaPedido, redondeoPropPedido
																				, totVenFecEspejo, totImpVenFecEspejo
																				, orden
																				, codNecesidad, estado, horaLimite
																				, ventaEspejoTarifa
																				, ventaEspejoOferta
																				);
		
		detalladoMostradorSIA.setPropuestaPedidoAnt(propuestaPedido);

		return detalladoMostradorSIA;

	}

	private GestionEurosRefs mapRowGestionEurosRefs(STRUCT estructuraDatos) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		//Obtención de datos de la estructura de base de datos
		BigDecimal codArticulo_BD = ((BigDecimal)objectInfo[0]);
		BigDecimal codNecesidad_BD = ((BigDecimal)objectInfo[1]);
		BigDecimal unidsCaja_BD = ((BigDecimal)objectInfo[2]);
		BigDecimal precioCostoArticulo_BD = ((BigDecimal)objectInfo[3]);
		BigDecimal precioCostoArtLinealFinal_BD = ((BigDecimal)objectInfo[4]);
		BigDecimal unidPropuestasFlModif_BD = ((BigDecimal)objectInfo[5]);
		BigDecimal diferencia_BD = ((BigDecimal)objectInfo[6]);
		String refCumple_BD = (String)objectInfo[7];
		String aviso_BD = (String)objectInfo[8];
		
		//Transformación de datos para estructura de DetalladoSIA
		Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
		Long codNecesidad = ((codNecesidad_BD != null && !("".equals(codNecesidad_BD.toString())))?new Long(codNecesidad_BD.toString()):null);
		Double unidsCaja = ((unidsCaja_BD != null && !("".equals(unidsCaja_BD.toString())))?new Double(unidsCaja_BD.toString()):null);
		Double precioCostoArticulo = ((precioCostoArticulo_BD != null && !("".equals(precioCostoArticulo_BD.toString())))?new Double(precioCostoArticulo_BD.toString()):null);
		Double precioCostoArtLinealFinal = ((precioCostoArtLinealFinal_BD != null && !("".equals(precioCostoArtLinealFinal_BD.toString())))?new Double(precioCostoArtLinealFinal_BD.toString()):null);
		Long unidPropuestasFlModif = ((unidPropuestasFlModif_BD != null && !("".equals(unidPropuestasFlModif_BD.toString())))?new Long(unidPropuestasFlModif_BD.toString()):null);
		Long diferencia = ((diferencia_BD != null && !("".equals(diferencia_BD.toString())))?new Long(diferencia_BD.toString()):null);
		String refCumple = refCumple_BD;
		String aviso = aviso_BD;
		/*ARRAY listaDatos = (ARRAY)objectInfo[6];

		List<String> avisosLst = new ArrayList<String>();

		if (listaDatos!=null){
			ResultSet rs = listaDatos.getResultSet();			

			//Recorrido del listado de datos
			while (rs.next()) {
				STRUCT estructuraDatosAviso = (STRUCT) rs.getObject(2);
				String aviso = (String)estructuraDatosAviso.getAttributes()[0];

				avisosLst.add(aviso);
			}
		}*/  
		GestionEurosRefs gestionEurosRef =  new GestionEurosRefs(codArticulo, codNecesidad, unidsCaja, precioCostoArticulo, precioCostoArtLinealFinal, unidPropuestasFlModif, diferencia, refCumple, aviso);
		return gestionEurosRef;
	}

	
	/**
	 * @author BICUGUAL
	 * 
	 */
	@Override
	public DetalladoMostradorSIALista consultaDetallado(FiltrosDetalleMostrador filtros) throws Exception {

		DetalladoMostradorSIALista salida = null;

		//Obtención de parámetros de consulta
		final String p_cod_loc = filtros.getCodCentro();
		final String p_cod_n2 = filtros.getSeccion();
		final String p_cod_n3 = filtros.getCategoria();
		final String p_cod_n4 = filtros.getSubcategoria();
		final String p_cod_n5 = filtros.getSegmento();
		final String p_tipo_aprov = filtros.getTipoAprov();
		final String p_solo_venta = filtros.getSoloVenta();
		final String p_gama_local = filtros.getGamaLocal();
		final Date p_fecha_espejo = filtros.getDiaEspejo();
		
		try{
			
			CallableStatementCreator csCreator = new CallableStatementCreator() {				
				
				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {
						
						if (context.getContextPath().equalsIgnoreCase(Constantes.CONTEXTO_WEB2)){
							logger.error("#############################################################################");
							logger.error("El paquete de DETALLADO MOSTRADOR utilizado para este contexto (" + context.getContextPath() + ") es: "
										+ pkDetalladoByContext);
							logger.error("#############################################################################");
						}
						
  						cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_consulta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");

  						// Centro
  						cs.setInt(1, Integer.parseInt(p_cod_loc));
						
  						// Seccion
						if (p_cod_n2 != null && !p_cod_n2.equals("")){
							cs.setString(2, p_cod_n2);
						}else{
							cs.setNull(2, OracleTypes.NULL);
						}
						
  						// Categoria
						if (p_cod_n3 != null && !p_cod_n3.equals("")){
							cs.setString(3, p_cod_n3);
						}else{
							cs.setNull(3, OracleTypes.NULL);
						}
						
  						// Subcategoria
						if (p_cod_n4 != null && !p_cod_n4.equals("")){
							cs.setString(4, p_cod_n4);
						}else{
							cs.setNull(4, OracleTypes.NULL);
						}
						
  						// Segmento
						if (p_cod_n5 != null && !p_cod_n5.equals("")){
							cs.setString(5, p_cod_n5);
						}else{
							cs.setNull(5, OracleTypes.NULL);
						}

						// Solo Venta
						if (p_solo_venta != null && !p_solo_venta.equals("")){
							cs.setString(6, p_solo_venta);
						}else{
							cs.setNull(6, OracleTypes.NULL);
						}

						// Gama Local
						if (p_gama_local != null && !p_gama_local.equals("")){
							cs.setString(7, p_gama_local);
						}else{
							cs.setNull(7, OracleTypes.NULL);
						}

						// Fecha Espejo
						if (p_fecha_espejo != null){
							cs.setDate(8, new java.sql.Date(p_fecha_espejo.getTime()));
						}else{
							cs.setNull(8, OracleTypes.NULL);
						}

						// Tipo Aprov.
						if (p_tipo_aprov != null && !p_tipo_aprov.equals("")){
							cs.setString(9, p_tipo_aprov);
						}else{
							cs.setNull(9, OracleTypes.NULL);
						}

						cs.registerOutParameter(10, OracleTypes.STRUCT, "APR_R_DETALLADO_MOSTRA_REG");

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DetalladoMostradorSIALista ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoConsultaDetallado(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (DetalladoMostradorSIALista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public List<ReferenciaNoGamaMostrador> callProcedureRefNoGamaNew(final Long codCentro, final String descripcion,
			final String seccion, final String categoria, final String subcategoria, final String segmento,
			final String gamaLocal) {
		
		List<ReferenciaNoGamaMostrador> lstReferencias = new ArrayList<ReferenciaNoGamaMostrador>();

		if (context.getContextPath().equalsIgnoreCase(Constantes.CONTEXTO_WEB2)) {
			logger.info("##########################################");
			logger.info("El paquete de DETALLADO utilizado para este contexto (" + context.getContextPath() + ") es: "
					+ pkDetalladoByContext);
			logger.info("##########################################");
		}

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName(pkDetalladoByContext)
				.withoutProcedureColumnMetaDataAccess().withProcedureName("P_REF_NO_GAMA")
				.declareParameters(
						new SqlParameter("p_cod_loc", OracleTypes.NUMBER),
						new SqlParameter("p_denom_art", OracleTypes.VARCHAR),
						new SqlParameter("p_cod_n2", OracleTypes.VARCHAR),
						new SqlParameter("p_cod_n3", OracleTypes.VARCHAR),
						new SqlParameter("p_cod_n4", OracleTypes.VARCHAR),
						new SqlParameter("p_cod_n5", OracleTypes.VARCHAR),
						new SqlParameter("p_gama_local", OracleTypes.VARCHAR),
						new SqlOutParameter("p_tabla", OracleTypes.STRUCT, "APR_R_REF_MOSTRA_REG"))
				;

		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_cod_loc", codCentro, OracleTypes.NUMBER)
				.addValue("p_denom_art", descripcion, OracleTypes.VARCHAR)
				.addValue("p_cod_n2", seccion, OracleTypes.VARCHAR)
				.addValue("p_cod_n3", categoria, OracleTypes.VARCHAR)
				.addValue("p_cod_n4", subcategoria, OracleTypes.VARCHAR)
				.addValue("p_cod_n5", segmento, OracleTypes.VARCHAR)
				.addValue("p_gama_local", gamaLocal, OracleTypes.VARCHAR);
		
		try {
			Map<String, Object> out = simpleJdbcCall.execute(in);
			STRUCT estructura = (STRUCT) out.get("p_tabla");
			ARRAY listaDatos = (ARRAY) estructura.getAttributes()[0];

			if (listaDatos != null) {
				ResultSet rs = listaDatos.getResultSet();
				int rowNum = 0;
				// Recorrido del listado de datos
				while (rs.next()) {
					lstReferencias.add(new ReferenciaNoGamaMostradorRowMap().mapRow(rs, rowNum));
					rowNum++;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstReferencias;
	}

	@Override
	public List<ReferenciaNoGamaMostrador> callProcedureRefNoGama(final Long codCentro, final String descripcion,
			final String seccion, final String categoria, final String subcategoria, final String segmento,
			final String gamaLocal) {

		List<ReferenciaNoGamaMostrador> salida = null;

		try {
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						if (context.getContextPath().equalsIgnoreCase(Constantes.CONTEXTO_WEB2)) {
							logger.error("##########################################");
							logger.error("El paquete de DETALLADO utilizado para este contexto ("
									+ context.getContextPath() + ") es: " + pkDetalladoByContext);
							logger.error("##########################################");
						}

						cs = con.prepareCall(
								"{call " + pkDetalladoByContext + ".P_REF_NO_GAMA(?, ?, ?, ?, ?, ?, ?, ?) }");

						cs.setInt(1, Integer.parseInt(codCentro.toString()));
						if (!StringUtils.isEmpty(descripcion)) {
							cs.setString(2, descripcion);
						} else {
							cs.setNull(2, OracleTypes.NULL);
						}
						if (!StringUtils.isEmpty(seccion)) {
							cs.setString(3, seccion);
						} else {
							cs.setNull(3, OracleTypes.NULL);
						}
						if (!StringUtils.isEmpty(categoria)) {
							cs.setString(4, categoria);
						} else {
							cs.setNull(4, OracleTypes.NULL);
						}
						if (!StringUtils.isEmpty(subcategoria)) {
							cs.setString(5, subcategoria);
						} else {
							cs.setNull(5, OracleTypes.NULL);
						}
						if (!StringUtils.isEmpty(segmento)) {
							cs.setString(6, segmento);
						} else {
							cs.setNull(6, OracleTypes.NULL);
						}
						cs.setString(7, gamaLocal);

						cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULAR_REFERENCIAS_NO_GAMA, OracleTypes.STRUCT, "APR_R_REF_MOSTRA_REG");

					} catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					List<ReferenciaNoGamaMostrador> lstReferencias = new ArrayList<ReferenciaNoGamaMostrador>();
					ResultSet rs = null;
					try {
						cs.execute();
						STRUCT estructuraResultado = (STRUCT) cs.getObject(8);
						ARRAY listaDatos = (ARRAY) estructuraResultado.getAttributes()[0];

						if (listaDatos != null) {
							rs = listaDatos.getResultSet();
							int rowNum = 0;
							// Recorrido del listado de datos
							while (rs.next()) {
								lstReferencias.add(new ReferenciaNoGamaMostradorRowMap().mapRow(rs, rowNum));
								rowNum++;
							}
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

					return lstReferencias;
				}
			};

			try {
				salida = (List<ReferenciaNoGamaMostrador>) this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return salida;

	}

	/**
	 * Mapper para los registros procedientes del PL/SQL P_REF_NO_GAMA 
	 * @author BICUGUAL
	 *
	 */
	private class ReferenciaNoGamaMostradorRowMap implements RowMapper<ReferenciaNoGamaMostrador> {
		public ReferenciaNoGamaMostrador mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			STRUCT estructuraDatos = (STRUCT) resultSet.getObject(2);
			Object[] objectInfo = estructuraDatos.getAttributes();

			ReferenciaNoGamaMostrador item = new ReferenciaNoGamaMostrador();
			item.setCodArtFormLog(Long.valueOf(String.valueOf(objectInfo[0])));
			item.setDenomRef(String.valueOf(objectInfo[1]));
			item.setCosto(null != objectInfo[2] ? ((BigDecimal) objectInfo[2]).doubleValue() : null);
			item.setPvpTarifa(null != objectInfo[3] ? ((BigDecimal) objectInfo[3]).doubleValue() : null);
			item.setIva(null != objectInfo[4] ? ((BigDecimal) objectInfo[4]).doubleValue() : null);
			item.setMargen(null != objectInfo[5] ? ((BigDecimal) objectInfo[5]).doubleValue() : null);
			item.setCc(null != objectInfo[6] ? ((BigDecimal) objectInfo[6]).doubleValue() : null);
			item.setNumOrden(null != objectInfo[7] ? ((BigDecimal) objectInfo[7]).intValue() : null);
			item.setFoto(String.valueOf(objectInfo[8]));
			return item;
		}

	};
	
	
}
