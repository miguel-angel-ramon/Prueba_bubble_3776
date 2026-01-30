package es.eroski.misumi.dao;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.UtilidadesCapraboDao;

@Repository
public class UtilidadesCapraboDaoImpl implements UtilidadesCapraboDao{
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(UtilidadesCapraboDaoImpl.class);
	 
	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	} 

	@Override
	public Long obtenerCodigoEroski(final Long codCentro, final Long codArtCaprabo) throws Exception {
		// TODO Auto-generated method stub
		/*Long salida = null;
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	          	               	        	    	
						cs = con.prepareCall("{? = call PK_APR_MISUMI_CAPRABO.F_REF_EROSKI(?,?)}");
						cs.registerOutParameter(1, Types.INTEGER);
						if (codCentro != null && !"".equals(codCentro)){
							cs.setLong(2, codCentro);
						}else{
							cs.setNull(2, OracleTypes.INTEGER);
						}
						if (codArtCaprabo != null && !"".equals(codArtCaprabo)){
							cs.setLong(3, codArtCaprabo);
						}else{
							cs.setNull(3, OracleTypes.INTEGER);
						}		        
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					Long ret = null;
					try {
						cs.execute();
						ret = obtenerCodigo(cs);
					} catch (SQLException e) {
						e.printStackTrace();                
						logger.error(e.getMessage()); //Peticion 55001. Corrección errores del LOG. Para saber que error da.
					}
					return ret;
				}
			};


			try {
				salida = (Long) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return salida;*/
		

		//Se crea la consulta
		StringBuffer query = new StringBuffer("SELECT APPS.PK_APR_MISUMI_CAPRABO.F_REF_EROSKI(?,?) REF_EROSKI FROM DUAL");
	
		//Se añaden los parámetros
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(codArtCaprabo);
	
		return this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
	}

	private Long obtenerCodigo(CallableStatement cs){
		Long resultado = null;
		try{
			resultado = cs.getLong(1);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return resultado;
	}
	
    public String obtenerMotivoCapraboEspecial(String motivoTxt, String codArtSustituidaPorEroski, String codArtSustituidaPorCaprabo) throws Exception{
    	StringBuffer query = new StringBuffer("select replace(?,?,?) from dual");
    	
    	//Se añaden los parámetros
    	List<Object> params = new ArrayList<Object>();
    	params.add(motivoTxt);
    	params.add(codArtSustituidaPorEroski);
    	params.add(codArtSustituidaPorCaprabo);
    	
    	return this.jdbcTemplate.queryForObject(query.toString(), String.class, params.toArray());
    }
}
