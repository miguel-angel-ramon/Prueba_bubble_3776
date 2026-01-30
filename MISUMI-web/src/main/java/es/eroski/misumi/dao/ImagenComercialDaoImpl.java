package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.ImagenComercialDao;
import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class ImagenComercialDaoImpl implements ImagenComercialDao {
	private static Logger logger = Logger.getLogger(ImagenComercialDaoImpl.class);
	
	private static final int OBTENER_IMC_OUT_INDEX = 3;
	private static final int MODIF_SIMUL_IMC_OUT_INDEX = 1;
	
	private static final String APR_R_IMC_REG_TYPENAME = "APR_R_IMC_REG";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	 /**
	  * <code>
	  * f_apr_pc_peso_variable  ( p_cod_articulo      IN  articulos.cod_articulo%type
                                    ) RETURN VARCHAR2
       </code>
	  */
	public String consultarPcPesoVariable(Long codArticulo){
		String query = " SELECT PK_APR_MISUMI_IMC.f_apr_pc_peso_variable(?) FROM DUAL";

		List<Object> params = new ArrayList<Object>(1);
		params.add(codArticulo);

		String salida = null;
		try{

			salida = this.jdbcTemplate.queryForObject(query, params.toArray(), String.class);
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query, params,e);
		}
		return salida;
	}
	

	/**
	 * Consulta IMC
	 * <code>
	 * PROCEDURE p_apr_consulta_IMC ( p_cod_loc            IN localizaciones.cod_loc%TYPE
                                 , p_cod_art_formlog    IN art_tp_familias_fl.cod_art_formlog%TYPE
                                 , p_tabla             OUT apr_r_imc_reg
                                 )
       </code>
	 */
	@Override
	public ImagenComercial consultaImc(final Long codLoc, final Long codArtFormlog){

		CallableStatementCreator csCreator = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) {
				CallableStatement cs = null;
				try {
					cs = con.prepareCall("{call PK_APR_MISUMI_IMC.p_apr_consulta_IMC(?, ?, ?) }");

					int paramIndex = 1;
					cs.setObject(paramIndex++, codLoc, OracleTypes.INTEGER);
					cs.setObject(paramIndex++, codArtFormlog, OracleTypes.INTEGER);

					cs.registerOutParameter(paramIndex, OracleTypes.STRUCT, APR_R_IMC_REG_TYPENAME);

				} catch (SQLException e) {
					logger.error("Fallo preparando consulta de Imc", e);             
				}
				return cs;
			}
		};
		CallableStatementCallback<ImagenComercial> csCallback = new CallableStatementCallback<ImagenComercial>() {

			public ImagenComercial doInCallableStatement(CallableStatement cs) {
				ImagenComercial ret = null;
				try {
					cs.execute();
					Struct struct = (Struct)cs.getObject(OBTENER_IMC_OUT_INDEX);
					ret = obtenerImc(struct);
				} catch (SQLException e) {
					logger.error("Fallo consultando Imc", e);
				}
				return ret;
			}
		};

		ImagenComercial salida = null;
		try {
			salida = this.jdbcTemplate.execute(csCreator,csCallback);
		} catch (Exception e) {
			logger.error("#####################################################");
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return salida;
	}

	/**
	 * Simular IMC
	 * <code>
	 * PROCEDURE p_apr_simular_IMC  ( p_tabla             IN OUT apr_r_imc_reg
                                 )
       </code>
	 */
	@Override
	public ImagenComercial simularImc(final ImagenComercial imagenComercial){

		CallableStatementCreator csCreator = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) {
				CallableStatement cs = null;
				try {
					//Crear estructura para simulacion
					Struct item = crearEstructuraImagenComercial(imagenComercial, con);
					
					cs = con.prepareCall("{call PK_APR_MISUMI_IMC.p_apr_simular_IMC(?) }");

					int paramIndex = 1;
					cs.setObject(paramIndex, item);
					cs.registerOutParameter(paramIndex, OracleTypes.STRUCT, APR_R_IMC_REG_TYPENAME);

				} catch (SQLException e) {
					logger.error("Fallo preparando simulacion de Imc", e);             
				}
				return cs;
			}
		};
		CallableStatementCallback<ImagenComercial> csCallback = new CallableStatementCallback<ImagenComercial>() {

			public ImagenComercial doInCallableStatement(CallableStatement cs) {
				ImagenComercial ret = null;
				try {
					cs.execute();
					Struct struct = (Struct)cs.getObject(MODIF_SIMUL_IMC_OUT_INDEX);
					ret = obtenerImc(struct);
				} catch (SQLException e) {
					logger.error("Fallo simulando Imc", e);
				}
				return ret;
			}
		};

		ImagenComercial salida = null;
		try {
			salida = this.jdbcTemplate.execute(csCreator,csCallback);
		} catch (Exception e) {
			logger.error("#####################################################");
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return salida;
	}

	/**
	 * Consulta IMC
	 * <code>
	 * PROCEDURE p_apr_modificar_IMC  ( p_tabla             IN OUT apr_r_imc_reg
                                   )
       </code>
	 */
	@Override
	public ImagenComercial modificarImc(final ImagenComercial imagenComercial){

		CallableStatementCreator csCreator = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) {
				CallableStatement cs = null;
				try {
					//Crear estructura para actualización
					Struct item = crearEstructuraImagenComercial(imagenComercial, con);
					
					cs = con.prepareCall("{call PK_APR_MISUMI_IMC.p_apr_modificar_IMC(?) }");

					int paramIndex = 1;
					cs.setObject(paramIndex, item);
					cs.registerOutParameter(paramIndex, OracleTypes.STRUCT, APR_R_IMC_REG_TYPENAME);

				} catch (SQLException e) {
					logger.error("Fallo preparando modificacion de Imc", e);             
				}
				return cs;
			}
		};
		CallableStatementCallback<ImagenComercial> csCallback = new CallableStatementCallback<ImagenComercial>() {

			public ImagenComercial doInCallableStatement(CallableStatement cs) {
				ImagenComercial ret = null;
				try {
					cs.execute();
					Struct struct = (Struct)cs.getObject(MODIF_SIMUL_IMC_OUT_INDEX);
					ret = obtenerImc(struct);
				} catch (SQLException e) {
					logger.error("Fallo modificando Imc", e);
				}
				return ret;
			}
		};

		ImagenComercial salida = null;
		try {
			salida = this.jdbcTemplate.execute(csCreator,csCallback);
		} catch (Exception e) {
			logger.error("#####################################################");
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return salida;
	}
	/**
	 * Mapear la estructura
	 * <code>
	 * TYPE      APR_R_IMC_REG AS OBJECT (
		centro	            NUMBER(10),
		referencia          NUMBER(12),
		facing				      NUMBER(3),
		capacidad           NUMBER(10,3),
		facing_alto			    NUMBER(4),
		facing_ancho		    NUMBER(4),
		tipo_referencia		  NUMBER(1),
		tipo_plano          VARCHAR2(50),
		multiplicador		    NUMBER(5,2),
		imc					        NUMBER(10,3),
		aviso_cambio		    VARCHAR2(32000),
		metodo				      NUMBER(2,0),
		sfm 				        NUMBER(7,3),
		dias_de_venta		    NUMBER(7,3),
		cod_error           NUMBER(5),
		descripcion_error   VARCHAR2(32000),
		flg_pistola			VARCHAR2(1),
		usuario_misumi		VARCHAR2(100)
		)
		</code>
	 * @param struct
	 * @return
	 * @throws SQLException 
	 */
	private ImagenComercial obtenerImc(Struct struct) throws SQLException{
		ImagenComercial imagenComercial = null;
		Object[] attributes = struct==null ? null : struct.getAttributes();
		if(attributes!=null){
			BigDecimal centro = (BigDecimal) attributes[0];
			BigDecimal referencia = (BigDecimal) attributes[1];
			BigDecimal facing = (BigDecimal) attributes[2];
			BigDecimal capacidad = (BigDecimal) attributes[3];
			BigDecimal facingAlto = (BigDecimal) attributes[4];
			BigDecimal facingAncho = (BigDecimal) attributes[5];
			BigDecimal tipoReferencia = (BigDecimal) attributes[6];
			String tipoPlano = (String)attributes[7];
			BigDecimal multiplicador = (BigDecimal)attributes[8];
			BigDecimal imc = (BigDecimal)attributes[9];
			String avisoCambio = (String)attributes[10];
			BigDecimal metodo = (BigDecimal)attributes[11];
			BigDecimal sfm = (BigDecimal)attributes[12];
			BigDecimal diasDeVenta = (BigDecimal)attributes[13];
			BigDecimal codError = (BigDecimal)attributes[14];
			String descripcionError = (String)attributes[15];
			String flgPistola = (String)attributes[16];
			String usuarioMisumi = (String)attributes[17];

			imagenComercial = new ImagenComercial();
			
			// Tratamiento de error
			if(codError!=null && codError.intValue() == 0){
				// Sin error
				if(centro!=null){
					imagenComercial.setCentro(centro.longValue());
				}
				if(referencia!=null){
					imagenComercial.setReferencia(referencia.longValue());
				}
				if(facing!=null){
					imagenComercial.setFacing(facing.longValue());
				}
				if(capacidad!=null) {
					imagenComercial.setCapacidad(capacidad.longValue());
				}
				if(facingAlto!=null){
					imagenComercial.setFacingAlto(facingAlto.longValue());
				}
				if(facingAncho!=null){
					imagenComercial.setFacingAncho(facingAncho.longValue());
				}
				if(tipoReferencia!=null){
					imagenComercial.setTipoReferencia(tipoReferencia.longValue());
				}
				imagenComercial.setTipoPlano(tipoPlano);
				if(multiplicador!=null){
					imagenComercial.setMultiplicador(multiplicador.intValue());
				}
				if(imc!=null){
					imagenComercial.setImc(imc.longValue());
				}else{
					imagenComercial.setImc(new Long(0));
				}
				
				imagenComercial.setAvisoCambio(avisoCambio);
				if(metodo!=null){
					imagenComercial.setMetodo(metodo.intValue());
				}
				if(sfm!=null){
					imagenComercial.setSfm(sfm.longValue());
				}else{
					imagenComercial.setSfm(new Long(0));
				}
				
				if(diasDeVenta!=null){
					imagenComercial.setDiasDeVenta(diasDeVenta.longValue());
				}
				if(codError!=null){
					imagenComercial.setCodError(codError.intValue());
				}
				if(descripcionError!=null){
					imagenComercial.setDescripcionError(descripcionError);
				}
				if(flgPistola!=null){
					imagenComercial.setDescripcionError(flgPistola);
				}
				if(usuarioMisumi!=null){
					imagenComercial.setDescripcionError(usuarioMisumi);
				}
			}else{
				// Con error
				if(codError!=null){
					imagenComercial.setCodError(codError.intValue());
				}
				imagenComercial.setDescripcionError(descripcionError);
			}
		}
		return imagenComercial;
	}

	/**
	 * Preparar el mapeo de objeto java a BD.
	 * @see #obtenerImc(Struct)
	 */
	private Struct crearEstructuraImagenComercial(ImagenComercial imagenComercial, Connection con) throws SQLException {
		//Transformación de conexión a conexión de oracle. Necesario para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();
		
		Object[] objectConsulta = new Object[18];
		objectConsulta[0] = imagenComercial.getCentro()==null?null:BigDecimal.valueOf(imagenComercial.getCentro());
		objectConsulta[1] = imagenComercial.getReferencia()==null?null:BigDecimal.valueOf(imagenComercial.getReferencia());
		objectConsulta[2] = imagenComercial.getFacing()==null?null:BigDecimal.valueOf(imagenComercial.getFacing());
		objectConsulta[3] = imagenComercial.getCapacidad()==null?null:BigDecimal.valueOf(imagenComercial.getCapacidad());
		objectConsulta[4] = imagenComercial.getFacingAlto()==null?null:BigDecimal.valueOf(imagenComercial.getFacingAlto());
		objectConsulta[5] = imagenComercial.getFacingAncho()==null?null:BigDecimal.valueOf(imagenComercial.getFacingAncho());
		objectConsulta[6] = imagenComercial.getTipoReferencia()==null?null:BigDecimal.valueOf(imagenComercial.getTipoReferencia());
		objectConsulta[7] = imagenComercial.getTipoPlano();
		objectConsulta[8] = imagenComercial.getMultiplicador()==null?null:BigDecimal.valueOf(imagenComercial.getMultiplicador());
		objectConsulta[9] = imagenComercial.getImc()==null?null:BigDecimal.valueOf(imagenComercial.getImc());
		objectConsulta[10] = imagenComercial.getAvisoCambio();
		objectConsulta[11] = imagenComercial.getMetodo()==null?null:BigDecimal.valueOf(imagenComercial.getMetodo());
		objectConsulta[12] = imagenComercial.getSfm()==null?null:BigDecimal.valueOf(imagenComercial.getSfm());
		objectConsulta[13] = imagenComercial.getDiasDeVenta()==null?null:BigDecimal.valueOf(imagenComercial.getDiasDeVenta());
		objectConsulta[14] = imagenComercial.getCodError()==null?null:BigDecimal.valueOf(imagenComercial.getCodError());
		objectConsulta[15] = imagenComercial.getDescripcionError();
		objectConsulta[16] = imagenComercial.getFlgPistola();
		objectConsulta[17] = imagenComercial.getUsuario();

		StructDescriptor itemDescriptor = StructDescriptor.createDescriptor(APR_R_IMC_REG_TYPENAME, conexionOracle);
		Struct structResultado = new STRUCT(itemDescriptor, conexionOracle, objectConsulta);
		return structResultado;
	}
}
