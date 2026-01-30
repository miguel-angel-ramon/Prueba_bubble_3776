package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PlataformaOrigenSuministroDaoSIA;
import es.eroski.misumi.model.PlataformaAprovisionamientoMercancia;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;

@Repository
public class PlataformaOrigenSuministroDaoSIAImpl implements PlataformaOrigenSuministroDaoSIA{

	private static Logger logger = Logger.getLogger(PlataformaOrigenSuministroDaoSIAImpl.class);

	private JdbcTemplate jdbcTemplate;

	//Posiciones PLSQL de salida de PK_APR_SN_GENERAL.P_OBT_OOSS
	private static final int POSICION_PARAMETRO_SALIDA_OBT_OSS_P_TP_APROV_SIA= 5;
	private static final int POSICION_PARAMETRO_SALIDA_OBT_OSS_P_COD_LOC_ORI= 6;
	private static final int POSICION_PARAMETRO_SALIDA_OBT_OSS_P_DESC_LOC_ORI= 7;
	private static final int POSICION_PARAMETRO_SALIDA_OBT_OSS_P_COD_PROVR_GEN= 8;
	private static final int POSICION_PARAMETRO_SALIDA_OBT_OSS_P_COD_PROVR_TRABAJO= 9;
	private static final int POSICION_PARAMETRO_SALIDA_OBT_OSS_P_DESC_PROVR = 10;

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	@Override
	public PlataformaAprovisionamientoMercancia obtOss(final Long codArtMadre, final Long codLoc, final Date fechaHoy, final Long contador) throws Exception {
		PlataformaAprovisionamientoMercancia salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						cs = con.prepareCall("{call PK_APR_SN_GENERAL.P_OBT_OOSS(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");

						if (codArtMadre != null){
							cs.setLong(1, codArtMadre);
						}else{							
							cs.setNull(1, OracleTypes.INTEGER);
						}
						if (codLoc != null){
							cs.setLong(2, codLoc);
						}else{							
							cs.setNull(2, OracleTypes.INTEGER);
						}

						if(fechaHoy != null){
							cs.setDate(3, new java.sql.Date(fechaHoy.getTime()));
						}else{
							cs.setNull(3, OracleTypes.DATE);
						}

						if (contador != null){
							cs.setLong(4, contador);
						}else{							
							cs.setNull(4, OracleTypes.INTEGER);
						}

						//OUT p_tp_aprov_sia
						cs.registerOutParameter(5,Types.VARCHAR); 

						//OUT p_cod_loc_ori
						cs.registerOutParameter(6,Types.INTEGER); 

						//OUT p_desc_loc_ori
						cs.registerOutParameter(7,Types.VARCHAR); 

						//OUT p_cod_provr_gen
						cs.registerOutParameter(8,Types.INTEGER); 

						//OUT p_cod_provr_trabajo
						cs.registerOutParameter(9,Types.INTEGER); 

						//OUT p_desc_provr
						cs.registerOutParameter(10,Types.VARCHAR); 

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					PlataformaAprovisionamientoMercancia ret = null;
					try {
						cs.execute();
						ret = obtenerResultadoObtOss(cs,POSICION_PARAMETRO_SALIDA_OBT_OSS_P_TP_APROV_SIA,POSICION_PARAMETRO_SALIDA_OBT_OSS_P_COD_LOC_ORI,
								POSICION_PARAMETRO_SALIDA_OBT_OSS_P_DESC_LOC_ORI,POSICION_PARAMETRO_SALIDA_OBT_OSS_P_COD_PROVR_GEN,
								POSICION_PARAMETRO_SALIDA_OBT_OSS_P_COD_PROVR_TRABAJO,POSICION_PARAMETRO_SALIDA_OBT_OSS_P_DESC_PROVR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (PlataformaAprovisionamientoMercancia) this.jdbcTemplate.execute(csCreator,csCallback);
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

	private PlataformaAprovisionamientoMercancia obtenerResultadoObtOss(CallableStatement cs,int idParametroResultado1, int idParametroResultado2, int idParametroResultado3, int idParametroResultado4, int idParametroResultado5, int idParametroResultado6) throws SQLException{
		
		String pTpAprovSIA_BD = (String)cs.getString(idParametroResultado1);
		BigDecimal pCodLocOri_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String pDescLocOri_BD = (String)cs.getString(idParametroResultado3);
		BigDecimal pCodProbrGen_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado4);
		BigDecimal pCodProvrTrabajo_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado5);
		String pDescProvr_BD = (String)cs.getString(idParametroResultado6);

		//Transformación de datos para estructura de PlataformaAprovisionamientoMercancia	
		String pTpAprovSIA = pTpAprovSIA_BD;
		Long pCodLocOri = ((pCodLocOri_BD != null && !("".equals(pCodLocOri_BD.toString())))?new Long(pCodLocOri_BD.toString()):null);
		String pDescLocOri = pDescLocOri_BD;
		Long pCodProbrGen = ((pCodProbrGen_BD != null && !("".equals(pCodProbrGen_BD.toString())))?new Long(pCodProbrGen_BD.toString()):null);
		Long pCodProvrTrabajo = ((pCodProvrTrabajo_BD != null && !("".equals(pCodProvrTrabajo_BD.toString())))?new Long(pCodProvrTrabajo_BD.toString()):null);
		String pDescProvr = pDescProvr_BD;

		PlataformaAprovisionamientoMercancia plataformaAprovisionamientoMercancia = new PlataformaAprovisionamientoMercancia(pTpAprovSIA, pCodLocOri, pDescLocOri, pCodProbrGen, pCodProvrTrabajo, pDescProvr);
		
		return plataformaAprovisionamientoMercancia;
	}
}
