package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import es.eroski.misumi.dao.iface.ExclusionVentasSIADao;
import es.eroski.misumi.model.ExclusionVentas;
import es.eroski.misumi.model.ExclusionVentasSIA;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class ExclusionVentasSIADaoImpl implements ExclusionVentasSIADao {
	private JdbcTemplate jdbcTemplate;
	
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_EXCLUSION = 2;
	private static final int POSICION_PARAMETRO_SALIDA_MODIFICACION_EXCLUSION = 1;

	private static Logger logger = Logger.getLogger(ExclusionVentasSIADaoImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	private ExclusionVentas mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		ExclusionVentas exclusionVentas = new ExclusionVentas();
		if (null != objectInfo[0]) {
			exclusionVentas.setIdentificadorSIA(((BigDecimal) objectInfo[0]).longValue());
		}else{
			exclusionVentas.setIdentificadorSIA(new Long(0)); 
		}

		if (null != objectInfo[1]) {
			exclusionVentas.setCodCentro(((BigDecimal) objectInfo[1]).longValue());
		}else{
			exclusionVentas.setCodCentro(new Long(0)); 			
		}

		if (null != objectInfo[2]) {
			exclusionVentas.setGrupo1(((BigDecimal) objectInfo[2]).longValue());
		}else{
			exclusionVentas.setGrupo1(new Long(0)); 
		}

		if (null != objectInfo[3]) {
			exclusionVentas.setDescripGrupo1((String) objectInfo[3]);
		}else{
			exclusionVentas.setDescripGrupo1("");
		}

		if (null != objectInfo[4]) {
			exclusionVentas.setGrupo2(((BigDecimal) objectInfo[4]).longValue());
		}else{
			exclusionVentas.setGrupo2(new Long(0)); 			
		}

		if (null != objectInfo[5]) {
			exclusionVentas.setDescripGrupo2((String) objectInfo[5]);
		}else{
			exclusionVentas.setDescripGrupo2("");
		}

		if (null != objectInfo[6]) {
			exclusionVentas.setGrupo3(((BigDecimal) objectInfo[6]).longValue());
		}else{
			exclusionVentas.setGrupo3(new Long(0)); 			
		}

		if (null != objectInfo[7]) {
			exclusionVentas.setDescripGrupo3((String) objectInfo[7]);
		}else{
			exclusionVentas.setDescripGrupo3("");
		}
		
		if (null != objectInfo[8]) {
			exclusionVentas.setGrupo4(((BigDecimal) objectInfo[8]).longValue());
		}else{
			exclusionVentas.setGrupo4(new Long(0)); 			
		}

		if (null != objectInfo[9]) {
			exclusionVentas.setDescripGrupo4((String) objectInfo[9]);
		}else{
			exclusionVentas.setDescripGrupo4("");
		}

		if (null != objectInfo[10]) {
			exclusionVentas.setGrupo5(((BigDecimal) objectInfo[10]).longValue());
		}else{
			exclusionVentas.setGrupo5(new Long(0)); 			
		}

		if (null != objectInfo[11]) {
			exclusionVentas.setDescripGrupo5((String) objectInfo[11]);
		}else{
			exclusionVentas.setDescripGrupo5("");
		}

		if (null != objectInfo[12]) {
			exclusionVentas.setCodArt(((BigDecimal) objectInfo[12]).longValue());
		}else{
			exclusionVentas.setCodArt(new Long(0)); 
		}

		if (null != objectInfo[13]) {
			exclusionVentas.setDescripArt((String) objectInfo[13]);
		}else{
			exclusionVentas.setDescripArt("");
		}

		if (null != objectInfo[14]) {
			SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
			exclusionVentas.setFecha(df.format(new Date(((Timestamp) objectInfo[14]).getTime())));
		}

		if (null != objectInfo[15]) {
			exclusionVentas.setCodError(((BigDecimal) objectInfo[15]).abs().longValue());
		}else{
			exclusionVentas.setCodError(new Long(0)); 				
		}

		if (null != objectInfo[16]) {
			exclusionVentas.setDescripError((String) objectInfo[16]);
		}else{
			exclusionVentas.setDescripError("");
		}

		return exclusionVentas;
	}

	private STRUCT crearEstructuraExclusionVentas(List<ExclusionVentas> listaExclusion, Connection con)
			throws SQLException {

		// Transformación de conexión a conexión de oracle. Necesari para
		// definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		Object[] objectTabla = new Object[listaExclusion.size()];

		for (int i = 0; i < listaExclusion.size(); i++) {

			ExclusionVentas exclusionVentas = listaExclusion.get(i);

			Object[] objectInfo = new Object[17];

			// Sólo se informan los datos necesarios
			if (null != exclusionVentas.getIdentificadorSIA()){
				objectInfo[0] = new BigDecimal(exclusionVentas.getIdentificadorSIA());
			}

			objectInfo[1] = new BigDecimal(exclusionVentas.getCodCentro());

			if (null != exclusionVentas.getGrupo1()) {
				objectInfo[2] = new BigDecimal(exclusionVentas.getGrupo1());
				objectInfo[3] = exclusionVentas.getDescripGrupo1();
			}
			if (null != exclusionVentas.getGrupo2()) {
				objectInfo[4] = new BigDecimal(exclusionVentas.getGrupo2());
				objectInfo[5] = exclusionVentas.getDescripGrupo2();
			}
			if (null != exclusionVentas.getGrupo3()) {
				objectInfo[6] = new BigDecimal(exclusionVentas.getGrupo3());
				objectInfo[7] = exclusionVentas.getDescripGrupo3();
			}
			if (null != exclusionVentas.getGrupo4()) {
				objectInfo[8] = new BigDecimal(exclusionVentas.getGrupo4());
				objectInfo[9] = exclusionVentas.getDescripGrupo4();
			}
			if (null != exclusionVentas.getGrupo5()) {
				objectInfo[10] = new BigDecimal(exclusionVentas.getGrupo5());
				objectInfo[11] = exclusionVentas.getDescripGrupo5();
			}
			if (null != exclusionVentas.getCodArt()) {
				objectInfo[12] = new BigDecimal(exclusionVentas.getCodArt());
				objectInfo[13] = exclusionVentas.getDescripArt();
			}

			SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
			try {
				objectInfo[14] = new java.sql.Date(df.parse(exclusionVentas.getFecha()).getTime());
			} catch (ParseException e) {

			}

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("T_EXCL_VENTAS", conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor, conexionOracle, objectInfo);

			objectTabla[i] = itemObjectStruct;

		}

		Object[] objectConsulta = new Object[3]; // Tiene 3 campos pero sólo
													// nos interesa la lista
		ArrayDescriptor desc = new ArrayDescriptor("T_TABEXCL_VENTAS", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		objectConsulta[0] = array;

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_EXCL_VENTAS",
				conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta, conexionOracle, objectConsulta);

		return itemConsulta;
	}
	
	private ExclusionVentasSIA obtenerEstructuraExclusion(CallableStatement cs, ResultSet rs, int idParametroResultado){
    	
		ExclusionVentasSIA exclusionVentasSIA = new ExclusionVentasSIA();
    	List<ExclusionVentas> listaVArtSfm = new ArrayList<ExclusionVentas>();
    	
	    	try {
				//Obtención del parámetro de salida
				STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado);
				logger.debug(estructuraResultado.dump());
				if (null != estructuraResultado){
					//Obtención de los datos de la estructura
					BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[1];
					String descEstado = (String)estructuraResultado.getAttributes()[2];
					
					//Control de error en la obtenci�n de datos
					if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
						//Obtención de los datos de salida
						ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[0];
						if (listaDatos!=null){
							rs = listaDatos.getResultSet();
					        int rowNum = 0;
							//Recorrido del listado de datos
					        while (rs.next()) {
					        	STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
					            ExclusionVentas exclusion = this.mapRow(estructuraDatos, rowNum);
					            listaVArtSfm.add(exclusion);
					            rowNum++;
					        }
						}            		
						
						
						exclusionVentasSIA.setDatos(listaVArtSfm);
					}
					if (null != estado){
						exclusionVentasSIA.setEstado(estado.abs().longValue());
						exclusionVentasSIA.setDescEstado(descEstado);
					}
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
    	
    
        return exclusionVentasSIA;
    }

	@Override
	public ExclusionVentasSIA consultaExclusion(ExclusionVentas exclusionVentas) throws Exception {

		ExclusionVentasSIA salida = null;
		// Obtención de parámetros de consulta
		final Long p_cod_loc =exclusionVentas.getCodCentro();

		try {
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						cs = con.prepareCall("{call PK_APR_EXCLVTAS_MISUMI.OBT_EXC_VENTA(?, ?) }");

						cs.setInt(1, p_cod_loc.intValue());
						cs.registerOutParameter(2, OracleTypes.STRUCT, "APR_R_EXCL_VENTAS");

					} catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					ExclusionVentasSIA ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoConsultaExclusion(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};

			
			try {
			    salida = (ExclusionVentasSIA) this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public ExclusionVentasSIA insertarExclusion(List<ExclusionVentas> listaExclusion) throws Exception {

		ExclusionVentasSIA salida = null;
		// Obtención de par�metros de actualización
		final List<ExclusionVentas> listadoModificadosActualizacion = new ArrayList<ExclusionVentas>(); // Datos
																										// que
																										// se
																										// enviar�n
																										// al
																										// procedimiento
		listadoModificadosActualizacion.addAll(listaExclusion);

		try {
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						// Crear estructura para actualización
						STRUCT itemConsulta = crearEstructuraExclusionVentas(listadoModificadosActualizacion, con);

						cs = con.prepareCall("{call PK_APR_EXCLVTAS_MISUMI.INS_EXC_VENTA(?) }");
						cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_EXCL_VENTAS");
						cs.setObject(1, itemConsulta);

					} catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					ExclusionVentasSIA ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoActualizacionExclusion(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};

			
			
			try {
				salida = (ExclusionVentasSIA) this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public ExclusionVentasSIA borrarExclusion(List<ExclusionVentas> listaExclusion) throws Exception {

		ExclusionVentasSIA salida = null;
		// Obtención de par�metros de actualización
		final List<ExclusionVentas> listadoModificadosActualizacion = new ArrayList<ExclusionVentas>(); 
		listadoModificadosActualizacion.addAll(listaExclusion);

		try {
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						// Crear estructura para actualización
						STRUCT itemConsulta = crearEstructuraExclusionVentas(listadoModificadosActualizacion, con);

						cs = con.prepareCall("{call PK_APR_EXCLVTAS_MISUMI.BOR_EXC_VENTA(?) }");
						cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_EXCL_VENTAS");
						cs.setObject(1, itemConsulta);

					} catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					ExclusionVentasSIA ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoActualizacionExclusion(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};

			
			try {
				salida = (ExclusionVentasSIA) this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	private ExclusionVentasSIA obtenerResultadoConsultaExclusion(CallableStatement cs, ResultSet rs) {
		return obtenerEstructuraExclusion(cs, rs, this.POSICION_PARAMETRO_SALIDA_CONSULTA_EXCLUSION);
	}

	private ExclusionVentasSIA obtenerResultadoActualizacionExclusion(CallableStatement cs, ResultSet rs) {
		return obtenerEstructuraExclusion(cs, rs, this.POSICION_PARAMETRO_SALIDA_MODIFICACION_EXCLUSION);
	}

}
